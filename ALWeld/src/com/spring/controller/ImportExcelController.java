package com.spring.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.model.EquipmentManufacturer;
import com.spring.model.Gather;
import com.spring.model.Insframework;
import com.spring.model.MaintenanceRecord;
import com.spring.model.MyUser;
import com.spring.model.Person;
import com.spring.model.WeldedJunction;
import com.spring.model.WeldingMachine;
import com.spring.model.WeldingMaintenance;
import com.spring.service.DictionaryService;
import com.spring.service.GatherService;
import com.spring.service.MaintainService;
import com.spring.service.PersonService;
import com.spring.service.WeldedJunctionService;
import com.spring.service.WeldingMachineService;
import com.spring.util.IsnullUtil;
import com.spring.util.UploadUtil;

import net.sf.json.JSONObject;

/**
 * excel导入数据库
 * @author gpyf16
 *
 */

@Controller
@RequestMapping(value = "/import", produces = { "text/json;charset=UTF-8" })
public class ImportExcelController {
	@Autowired
	private WeldingMachineService wmm;
	@Autowired
	private MaintainService mm;
	@Autowired
	private GatherService g;
	@Autowired
	private PersonService ps;
	@Autowired
	private DictionaryService dm;
	@Autowired
	private WeldedJunctionService wjs;
	
	IsnullUtil iutil = new IsnullUtil();
	
	/**
	 * 导入焊机设备
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/importWeldingMachine")
	@ResponseBody
	public String importWeldingMachine(HttpServletRequest request,
			HttpServletResponse response){
		UploadUtil u = new UploadUtil();
		JSONObject obj = new JSONObject();
		String path = "";
		try{
			path = u.uploadFile(request, response);
			List<WeldingMachine> list = xlsxWm(path);
			//删除已保存的excel文件
			File file  = new File(path);
			file.delete();
			for(WeldingMachine wm : list){
				wm.setTypeId(dm.getvaluebyname(4,wm.getTypename()));
				wm.setStatusId(dm.getvaluebyname(3,wm.getStatusname()));
				wm.getManufacturerId().setId(wmm.getManuidByValue(wm.getManufacturerId().getName(),wm.getManufacturerId().getType()));
				String name = wm.getInsframeworkId().getName();
				wm.getInsframeworkId().setId(wmm.getInsframeworkByName(name));
				Gather gather = wm.getGatherId();
				int count2 = 0;
				if(gather!=null){
					int count3 = g.getGatherNoByItemCount(gather.getGatherNo(), wm.getInsframeworkId().getId()+"");
					if(count3 == 0){
						obj.put("msg","导入失败，请检查您的采集序号是否存在或是否属于该部门！");
						obj.put("success",false);
						return obj.toString();
					}
					gather.setId(g.getGatherByNo(gather.getGatherNo()));
					wm.setGatherId(gather);
					count2 = wmm.getGatheridCount(wm.getInsframeworkId().getId(),gather.getGatherNo());
				}
				if(isInteger(wm.getEquipmentNo())){
					wm.setEquipmentNo(wm.getEquipmentNo());
				}
				wm.setGatherId(gather);
				//编码唯一
				int count1 = wmm.getEquipmentnoCount(wm.getEquipmentNo());
				if(count1>0 || count2>0){
					obj.put("msg","导入失败，请检查您的设备编码、采集序号是否已存在！");
					obj.put("success",false);
					return obj.toString();
				}
				wmm.addWeldingMachine(wm);
			};
			obj.put("success",true);
			obj.put("msg","导入成功！");
		}catch(Exception e){
			e.printStackTrace();
			obj.put("msg","导入失败，请检查您的文件格式以及数据是否符合要求！");
			obj.put("success",false);
		}
		return obj.toString();
	}
	
	/**
	 * 导入维修记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/importMaintain")
	@ResponseBody
	public String importMaintain(HttpServletRequest request,
			HttpServletResponse response){
		UploadUtil u = new UploadUtil();
		JSONObject obj = new JSONObject();
		try{
			String path = u.uploadFile(request, response);
			List<WeldingMaintenance> wt = xlsxMaintain(path);
			//删除已保存的excel文件
			File file  = new File(path);
			file.delete();
			for(int i=0;i<wt.size();i++){
				wt.get(i).getMaintenance().setTypeId(dm.getvaluebyname(5,wt.get(i).getMaintenance().getTypename()));
				BigInteger wmid = null;
				if(isInteger(wt.get(i).getWelding().getEquipmentNo())){
					wmid = wmm.getWeldingMachineByEno(wt.get(i).getWelding().getEquipmentNo());
				}else{
					wmid = wmm.getWeldingMachineByEno(wt.get(i).getWelding().getEquipmentNo());
				}
				wt.get(i).getWelding().setId(wmid);
				//插入数据库
				mm.addMaintian( wt.get(i),wt.get(i).getMaintenance(),wmid);
			};
			obj.put("success",true);
			obj.put("msg","导入成功！");
		}catch(Exception e){
			e.printStackTrace();
			obj.put("success",false);
			obj.put("msg","导入失败，请检查您的文件格式以及数据是否符合要求！");
		}
		return obj.toString();
	}
	
	
	/**
	 * 导入焊工记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/importWelder")
	@ResponseBody
	public String importWelder(HttpServletRequest request,
			HttpServletResponse response){
		UploadUtil u = new UploadUtil();
		JSONObject obj = new JSONObject();
		try{
			String path = u.uploadFile(request, response);
			List<Person> we = xlsxWelder(path);
			//删除已保存的excel文件
			File file  = new File(path);
			file.delete();
			for(Person w:we){
				w.setLeveid(dm.getvaluebyname(8,w.getLevename()));
				w.setQuali(dm.getvaluebyname(7, w.getQualiname()));
				w.setOwner(wmm.getInsframeworkByName(w.getInsname()));
				MyUser user = (MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				w.setCreater(new BigInteger(user.getId()+""));
				w.setUpdater(new BigInteger(user.getId()+""));
				w.setWelderno(w.getWelderno());
				//编码唯一
				int count1 = ps.getUsernameCount(w.getWelderno());
				if(count1>0){
					obj.put("msg","导入失败，请检查您的焊工编号是否已存在！");
					obj.put("success",false);
					return obj.toString();
				}
				
				ps.save(w);
			};
			obj.put("success",true);
			obj.put("msg","导入成功！");
		}catch(Exception e){
			e.printStackTrace();
			obj.put("success",false);
			obj.put("msg","导入失败，请检查您的文件格式以及数据是否符合要求！");
		}
		return obj.toString();
	}
	

	/**
	 * 导入焊口记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/importWeldedJunction")
	@ResponseBody
	public String importWeldedJunction(HttpServletRequest request,
			HttpServletResponse response){
		UploadUtil u = new UploadUtil();
		JSONObject obj = new JSONObject();
		try{
			String path = u.uploadFile(request, response);
			List<WeldedJunction> we = xlsxWeldedJunction(path);
			//删除已保存的excel文件
			File file  = new File(path);
			file.delete();
			for(WeldedJunction w:we){
				int count = wjs.getWeldedjunctionByNo(w.getWeldedJunctionno());
				w.setInsfid(wmm.getInsframeworkByName(w.getItemid().getName()));
				MyUser user = (MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				w.setCreater(new BigInteger(user.getId()+""));
				w.setUpdater(new BigInteger(user.getId()+""));
				w.setWeldedJunctionno("00"+w.getWeldedJunctionno());
				//编码唯一
				if(count>0){
					obj.put("msg","导入失败，请检查您的焊口编号是否已存在！");
					obj.put("success",false);
					return obj.toString();
				}
				wjs.addJunction(w);
			};
			obj.put("success",true);
			obj.put("msg","导入成功！");
		}catch(Exception e){
			e.printStackTrace();
			obj.put("success",false);
			obj.put("msg","导入失败，请检查您的文件格式以及数据是否符合要求！");
		}
		return obj.toString();
	}
	
	/**
	 * 导入WeldingMaintenance表数据
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static List<WeldingMaintenance> xlsxMaintain(String path) throws IOException, InvalidFormatException{
		List<WeldingMaintenance> wm = new ArrayList<WeldingMaintenance>();
		InputStream stream = new FileInputStream(path);
		Workbook workbook = create(stream);
		Sheet sheet = workbook.getSheetAt(0);
		
		int rowstart = sheet.getFirstRowNum()+1;
		int rowEnd = sheet.getLastRowNum();
	    
		for(int i=rowstart;i<=rowEnd;i++){
			Row row = sheet.getRow(i);
			if(null == row){
				continue;
			}
			int cellStart = row.getFirstCellNum();
			int cellEnd = row.getLastCellNum();
			WeldingMaintenance dit = new WeldingMaintenance();
			MaintenanceRecord mr = new MaintenanceRecord();
			for(int k = cellStart; k<= cellEnd;k++){
				Cell cell = row.getCell(k);
				if(null == cell){
					continue;
				}
				
				String cellValue = "";
				
				switch (cell.getCellType()){
				case HSSFCell.CELL_TYPE_NUMERIC://数字
					if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
		                SimpleDateFormat sdf = null;  
		                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat  
		                        .getBuiltinFormat("h:mm")) {  
		                    sdf = new SimpleDateFormat("HH:mm");  
		                } else {// 日期  
		                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		                }  
		                Date date = cell.getDateCellValue();  
		                cellValue = sdf.format(date);  
		            } else if (cell.getCellStyle().getDataFormat() == 58) {  
		                // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
		                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		                double value = cell.getNumericCellValue();  
		                Date date = org.apache.poi.ss.usermodel.DateUtil  
		                        .getJavaDate(value);  
		                cellValue = sdf.format(date);  
		            } else {
                        double value = cell.getNumericCellValue();
                        int intValue = (int) value;
                        cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
                    }
					if(k == 0){
						WeldingMachine welding = new WeldingMachine();
						welding.setEquipmentNo(cellValue);
						dit.setWelding(welding);//设备编码
						break;
					}
					if(k == 2){
						mr.setStartTime(cellValue);//维修起始时间
						break;
					}
					if(k == 3){
						mr.setEndTime(cellValue);//维修结束时间
						break;
	    			}
					break;
				case HSSFCell.CELL_TYPE_STRING://字符串
					cellValue = cell.getStringCellValue();
					if(k == 0){
						WeldingMachine welding = new WeldingMachine();
						welding.setEquipmentNo(cellValue);
						dit.setWelding(welding);//设备编码
						break;
					}
					if(k == 1){
						mr.setViceman(cellValue);//维修人员
						break;
					}
					if(k == 4){
						mr.setTypename(cellValue);
						break;
 					}
 					if(k == 5){
 						mr.setDesc(cellValue);//维修说明
						break;
 					}
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
					cellValue = String.valueOf(cell.getBooleanCellValue());
					break;
				case HSSFCell.CELL_TYPE_FORMULA: // 公式
					cellValue = String.valueOf(cell.getCellFormula());
					break;
				case HSSFCell.CELL_TYPE_BLANK: // 空值
					cellValue = "";
					break;
				case HSSFCell.CELL_TYPE_ERROR: // 故障
					cellValue = "";
					break;
				default:
					cellValue = cell.toString().trim();
					break;
				}
			}
			dit.setMaintenance(mr);
			wm.add(dit);
		}
		
		return wm;
	}
	
	/**
	 * 导入Wedlingmachine表数据
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static List<WeldingMachine> xlsxWm(String path) throws IOException, InvalidFormatException{
		List<WeldingMachine> wm = new ArrayList<WeldingMachine>();
		InputStream stream = new FileInputStream(path);
		Workbook workbook = create(stream);
		Sheet sheet = workbook.getSheetAt(0);
		
		int rowstart = sheet.getFirstRowNum()+1;
		int rowEnd = sheet.getLastRowNum();
	    
		for(int i=rowstart;i<=rowEnd;i++){
			Row row = sheet.getRow(i);
			if(null == row){
				continue;
			}
			int cellStart = row.getFirstCellNum();
			int cellEnd = row.getLastCellNum();
			WeldingMachine dit = new WeldingMachine();
			EquipmentManufacturer manu = new EquipmentManufacturer();
			for(int k = cellStart; k<= cellEnd;k++){
				Cell cell = row.getCell(k);
				if(null == cell){
					continue;
				}
				
				String cellValue = "";
				
				switch (cell.getCellType()){
				case HSSFCell.CELL_TYPE_NUMERIC://数字
					if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
		                SimpleDateFormat sdf = null;  
		                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat  
		                        .getBuiltinFormat("h:mm")) {  
		                    sdf = new SimpleDateFormat("HH:mm");  
		                } else {// 日期  
		                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		                }  
		                Date date = cell.getDateCellValue();  
		                cellValue = sdf.format(date);  
		            } else if (cell.getCellStyle().getDataFormat() == 58) {  
		                // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
		                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		                double value = cell.getNumericCellValue();  
		                Date date = org.apache.poi.ss.usermodel.DateUtil  
		                        .getJavaDate(value);  
		                cellValue = sdf.format(date);  
		            } else {
		            	 //处理数字过长时出现x.xxxE9
		            	 BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
		            	 cellValue = big.toString();
                    }
					if(k == 0){
						dit.setEquipmentNo(cellValue);//设备编码
						break;
					}
					if(k == 2){
						dit.setJoinTime(cellValue);//入厂时间
						break;
					}
					//采集序号机设备序号只能是数字
					if(k == 8){
						Gather g = new Gather();
						g.setGatherNo(cellValue);
						dit.setGatherId(g);//采集序号
						break;
					}
					break;
				case HSSFCell.CELL_TYPE_STRING://字符串
					cellValue = cell.getStringCellValue();
					if(k == 0){
						dit.setEquipmentNo(cellValue);//设备编码
						break;
					}
					if(k == 1){
						dit.setTypename(cellValue);//设备类型
						break;
					}
					if(k == 3){
 						Insframework ins = new Insframework();
 						ins.setName(cellValue);
 						dit.setInsframeworkId(ins);//所属项目
						break;
	    			}
			        if(k == 4){
			        	dit.setStatusname(cellValue);//状态
						break;
 					}
 					if(k == 5){
 						manu.setName(cellValue);
 						dit.setManufacturerId(manu);//厂家
						break;
 					}
 					if(k == 6){
 						manu.setType(cellValue);
 						dit.setManufacturerId(manu);//厂家类型
						break;
 					}
					if(k == 7){
						if(cellValue.equals("是")){
	 						dit.setIsnetworking(0);//是否在网
						}else{
	 						dit.setIsnetworking(1);
						}
						break;
 					}
					//采集序号机设备序号只能是数字
					if(k == 8){
						Gather g = new Gather();
						g.setGatherNo(cellValue);
						dit.setGatherId(g);//采集序号
						break;
					}
					if(k == 9){
						dit.setPosition(cellValue);//位置
						break;
					}
					if(k == 10){
						dit.setIp(cellValue);//ip地址
						break;
					}
					if(k == 11){
						dit.setModel(cellValue);//设备型号
						break;
					}
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
					cellValue = String.valueOf(cell.getBooleanCellValue());
					break;
				case HSSFCell.CELL_TYPE_FORMULA: // 公式
					cellValue = String.valueOf(cell.getCellFormula());
					break;
				case HSSFCell.CELL_TYPE_BLANK: // 空值
					cellValue = "";
					break;
				case HSSFCell.CELL_TYPE_ERROR: // 故障
					cellValue = "";
					break;
				default:
					cellValue = cell.toString().trim();
					break;
				}
			}
			wm.add(dit);
		}
		
		return wm;
	}
	
	/**
	 * 导入Welder表数据
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static List<Person> xlsxWelder(String path) throws IOException, InvalidFormatException{
		List<Person> welder = new ArrayList<Person>();
		InputStream stream = new FileInputStream(path);
		Workbook workbook = create(stream);
		Sheet sheet = workbook.getSheetAt(0);
		
		int rowstart = sheet.getFirstRowNum()+1;
		int rowEnd = sheet.getLastRowNum();
	    
		for(int i=rowstart;i<=rowEnd;i++){
			Row row = sheet.getRow(i);
			if(null == row){
				continue;
			}
			int cellStart = row.getFirstCellNum();
			int cellEnd = row.getLastCellNum();
			Person p = new Person();
			for(int k = cellStart; k<= cellEnd;k++){
				Cell cell = row.getCell(k);
				if(null == cell){
					continue;
				}
				
				String cellValue = "";
				
				switch (cell.getCellType()){
				case HSSFCell.CELL_TYPE_NUMERIC://数字
					if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
		                SimpleDateFormat sdf = null;  
		                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat  
		                        .getBuiltinFormat("h:mm")) {  
		                    sdf = new SimpleDateFormat("HH:mm");  
		                } else {// 日期  
		                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		                }  
		                Date date = cell.getDateCellValue();  
		                cellValue = sdf.format(date);  
		            } else if (cell.getCellStyle().getDataFormat() == 58) {  
		                // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
		                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		                double value = cell.getNumericCellValue();  
		                Date date = org.apache.poi.ss.usermodel.DateUtil  
		                        .getJavaDate(value);  
		                cellValue = sdf.format(date);  
		            } else {
		            	 //处理数字过长时出现x.xxxE9
		            	 BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
		            	 cellValue = big.toString();
                   }
					if(k == 1){
						p.setWelderno(cellValue);//焊工编号
						break;
					}
					if(k == 2){
						p.setCellphone(cellValue);//手机
						break;
 					}
					if(k == 4){
						p.setCardnum(cellValue);//卡号
						break;
 					}
					break;
				case HSSFCell.CELL_TYPE_STRING://字符串
					cellValue = cell.getStringCellValue();
					if(k == 0){
						p.setName(cellValue);//姓名
						break;
					}
					if(k == 1){
						p.setWelderno(cellValue);//焊工编号
						break;
					}
					if(k == 3){
						p.setLevename(cellValue);//级别
						break;
 					}
					if(k == 4){
						p.setCardnum(cellValue);//卡号
						break;
 					}
					if(k == 5){
						p.setQualiname(cellValue);//资质
						break;
 					}
					if(k == 6){
						p.setInsname(cellValue);//部门
						break;
 					}
					if(k == 7){
						p.setBack(cellValue);//备注
						break;
 					}
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
					cellValue = String.valueOf(cell.getBooleanCellValue());
					break;
				case HSSFCell.CELL_TYPE_FORMULA: // 公式
					cellValue = String.valueOf(cell.getCellFormula());
					break;
				case HSSFCell.CELL_TYPE_BLANK: // 空值
					cellValue = "";
					break;
				case HSSFCell.CELL_TYPE_ERROR: // 故障
					cellValue = "";
					break;
				default:
					cellValue = cell.toString().trim();
					break;
				}
			}
			welder.add(p);
		}
		
		return welder;
	}
	
	
	/**
	 * 导入Weldedjunction表数据
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static List<WeldedJunction> xlsxWeldedJunction(String path) throws IOException, InvalidFormatException{
		List<WeldedJunction> junction = new ArrayList<WeldedJunction>();
		InputStream stream = new FileInputStream(path);
		Workbook workbook = create(stream);
		Sheet sheet = workbook.getSheetAt(0);
		
		int rowstart = sheet.getFirstRowNum()+1;
		int rowEnd = sheet.getLastRowNum();
	    
		for(int i=rowstart;i<=rowEnd;i++){
			Row row = sheet.getRow(i);
			if(null == row){
				continue;
			}
			int cellStart = row.getFirstCellNum();
			int cellEnd = row.getLastCellNum();
			WeldedJunction p = new WeldedJunction();
			for(int k = cellStart; k<= cellEnd;k++){
				Cell cell = row.getCell(k);
				if(null == cell){
					continue;
				}
				
				String cellValue = "";
				
				switch (cell.getCellType()){
				case HSSFCell.CELL_TYPE_NUMERIC://数字
					if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
		                SimpleDateFormat sdf = null;  
		                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat  
		                        .getBuiltinFormat("h:mm")) {  
		                    sdf = new SimpleDateFormat("HH:mm");  
		                } else {// 日期  
		                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		                }  
		                Date date = cell.getDateCellValue();  
		                cellValue = sdf.format(date);  
		            } else if (cell.getCellStyle().getDataFormat() == 58) {  
		                // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
		                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		                double value = cell.getNumericCellValue();  
		                Date date = org.apache.poi.ss.usermodel.DateUtil  
		                        .getJavaDate(value);  
		                cellValue = sdf.format(date);  
		            } else {
		            	String num = String.valueOf(cell.getNumericCellValue());
		            	 //处理数字过长时出现x.xxxE9
		            	BigDecimal big=new BigDecimal(cell.getNumericCellValue());
		            	//判断数字是否是小数
		            	Pattern pattern = Pattern.compile("^\\d+\\.\\d+$");
		            	Matcher isNum = pattern.matcher(big+"");
		            	if(isNum.matches()){
		            		//为小数时不进行过长处理否则小数位会自动补位，例：21.3变为21.39999999999999857891452847979962825775146484375
		            		cellValue = num;
		            	}else{
		            		cellValue = big.toString();
		            	}
//		            	 BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
//		            	 cellValue = big.toString();
                   }
					if(k == 0){
						p.setWeldedJunctionno(cellValue);//编号
						break;
					}
					if(k == 1){
						p.setSerialNo(cellValue);//序列号
						break;
					}
					if(k == 6){
						p.setDyne(Integer.parseInt(cellValue));//达因
						break;
					}
					if(k == 8){
						p.setPipelineNo(cellValue);//管线号
						break;
					}
					if(k == 9){
						p.setRoomNo(cellValue);//房间号
						break;
					}
					if(k == 10){
						p.setExternalDiameter(cellValue);//上游外径
						break;
					}
					if(k == 11){
						p.setNextexternaldiameter(cellValue);//下游外径
						break;
					}
					if(k == 12){
						p.setWallThickness(cellValue);//上游璧厚
						break;
					}
					if(k == 13){
						p.setNextwall_thickness(cellValue);//下游璧厚
						break;
					}
					if(k == 16){
						p.setMaxElectricity(Double.valueOf(cellValue));//电流上限
						break;
					}
					if(k == 17){
						p.setMinElectricity(Double.valueOf(cellValue));//电流下限
						break;
					}
					if(k == 18){
						p.setMaxValtage(Double.valueOf(cellValue));//电压上限
						break;
					}
					if(k == 19){
						p.setMinValtage(Double.valueOf(cellValue));//电压下限
						break;
					}
					if(k == 22){
						p.setStartTime(cellValue);//开始时间
						break;
					}
					if(k == 23){
						p.setEndTime(cellValue);//结束时间
						break;
					}
					break;
				case HSSFCell.CELL_TYPE_STRING://字符串
					cellValue = cell.getStringCellValue();
					if(k == 0){
						p.setWeldedJunctionno(cellValue);//编号
						break;
					}
					if(k == 1){
						p.setSerialNo(cellValue);//序列号
						break;
					}
					if(k == 2){
						p.setUnit(cellValue);//机组
						break;
					}
					if(k == 3){
						p.setArea(cellValue);//区域
						break;
					}
					if(k == 4){
						p.setSystems(cellValue);//系统
						break;
					}
					if(k == 5){
						p.setChildren(cellValue);//子项
						break;
					}
					if(k == 7){
						p.setSpecification(cellValue);//规格
						break;
					}
					if(k == 8){
						p.setPipelineNo(cellValue);//管线号
						break;
					}
					if(k == 9){
						p.setRoomNo(cellValue);//房间号
						break;
					}
					if(k == 14){
						p.setMaterial(cellValue);//上游材质
						break;
					}
					if(k == 15){
						p.setNext_material(cellValue);//下游材质
						break;
					}
					if(k == 20){
						p.setElectricity_unit(cellValue);//电流单位
						break;
					}
					if(k == 21){
						p.setValtage_unit(cellValue);//电压单位
						break;
					}
					if(k == 24){
						Insframework insf = new Insframework();
						insf.setName(cellValue);
						p.setItemid(insf);//所属部门
						break;
					}
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
					cellValue = String.valueOf(cell.getBooleanCellValue());
					break;
				case HSSFCell.CELL_TYPE_FORMULA: // 公式
					cellValue = String.valueOf(cell.getCellFormula());
					break;
				case HSSFCell.CELL_TYPE_BLANK: // 空值
					cellValue = "";
					break;
				case HSSFCell.CELL_TYPE_ERROR: // 故障
					cellValue = "";
					break;
				default:
					cellValue = cell.toString().trim();
					break;
				}
			}
			junction.add(p);
		}
		
		return junction;
	}
	
	
	public static Workbook create(InputStream in) throws IOException,InvalidFormatException {
		if (!in.markSupported()) {
            in = new PushbackInputStream(in, 8);
        }
        if (POIFSFileSystem.hasPOIFSHeader(in)) {
            return new HSSFWorkbook(in);
        }
        if (POIXMLDocument.hasOOXMLHeader(in)) {
            return new XSSFWorkbook(OPCPackage.open(in));
        }
        throw new IllegalArgumentException("你的excel版本目前poi解析不了");
    }
	
	public static boolean isInteger(String str) {  
	     Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
	     return pattern.matcher(str).matches();  
	 }
}
