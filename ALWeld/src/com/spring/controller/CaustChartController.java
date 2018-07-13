package com.spring.controller;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.spring.dto.ModelDto;
import com.spring.dto.WeldDto;
import com.spring.model.Insframework;
import com.spring.model.LiveData;
import com.spring.page.Page;
import com.spring.service.InsframeworkService;
import com.spring.service.LiveDataService;
import com.spring.service.WeldingMachineService;
import com.spring.util.IsnullUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/caustChart", produces = { "text/json;charset=UTF-8" })
public class CaustChartController {
	private Page page;
	private int pageIndex = 1;
	private int pageSize = 10;
	private int total = 0;
	
	@Autowired
	private LiveDataService lm;
	@Autowired
	private InsframeworkService insm;
	@Autowired
	private WeldingMachineService wm;
	
	IsnullUtil iutil = new IsnullUtil();
	
	/**
	 * 跳转事业部工时页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goCaustHour")
	public String goCaustHour(HttpServletRequest request){
		String parent = request.getParameter("parent");
		insm.showParent(request, parent);
		lm.getUserId(request);
		request.setAttribute("parent",parent);
		return "caustchart/caustHour";
	}
	
	/**
	 * 跳转事业部超标页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goCaustOverproof")
	public String goCaustOverproof(HttpServletRequest request){
		String parent = request.getParameter("parent");
		insm.showParent(request, parent);
		lm.getUserId(request);
		request.setAttribute("parent",parent);
		return "caustchart/caustoverproof";
	}
	
	/**
	 * 跳转事业部超时页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goCaustOvertime")
	public String goCaustOvertime(HttpServletRequest request){
		String parent = request.getParameter("parent");
		insm.showParent(request, parent);
		request.setAttribute("parent",parent);
		lm.getUserId(request);
		return "caustchart/caustovertime";
	}
	
	/**
	 * 跳转事业部闲置率页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goCaustIdle")
	public String goCaustIdle(HttpServletRequest request){
		String parent = request.getParameter("parent");
		insm.showParent(request, parent);
		request.setAttribute("parent",parent);
		lm.getUserId(request);
		return "caustchart/caustidle";
	}
	
	/**
	 * 跳转事业部焊机负荷率页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goCaustLoads")
	public String goCaustLoads(HttpServletRequest request){
		String parent = request.getParameter("parent");
		insm.showParent(request, parent);
		lm.getUserId(request);
		request.setAttribute("parent",parent);
		return "caustchart/caustloads";
	}
	
	/**
	 * 跳转事业部空载率页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goCaustNoLoads")
	public String goCaustNoLoads(HttpServletRequest request){
		String parent = request.getParameter("parent");
		insm.showParent(request, parent);
		lm.getUserId(request);
		request.setAttribute("parent",parent);
		return "caustchart/caustnoloads";
	}
	
	/**
	 * 跳转事业部单台设备运行数据统计
	 * @param request
	 * @return
	 */
	@RequestMapping("/goCaustUse")
	public String goCaustUse(HttpServletRequest request){
		lm.getUserId(request);
		return "caustchart/caustuse";
	}
	

	/**
	 * 跳转事业部工效页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goCaustEfficiency")
	public String goCaustEfficiency(HttpServletRequest request){
		String nextparent = request.getParameter("nextparent");
		insm.showParent(request, nextparent);
		lm.getUserId(request);
		request.setAttribute("nextparent",nextparent);
		return "caustchart/caustefficiency";
	}
	
	/**
	 * 事业部工时报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCaustHour")
	@ResponseBody
	public String getCaustHour(HttpServletRequest request){
		if(iutil.isNull(request.getParameter("page"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
		}
		if(iutil.isNull(request.getParameter("rows"))){
			pageSize = Integer.parseInt(request.getParameter("rows"));
		}
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String parentId = request.getParameter("parent");
		String search = request.getParameter("search");
		WeldDto dto = new WeldDto();
		if(!iutil.isNull(parentId)){
			//数据权限处理
			BigInteger uid = lm.getUserId(request);
			String afreshLogin = (String)request.getAttribute("afreshLogin");
			if(iutil.isNull(afreshLogin)){
				return "0";
			}
			int type = insm.getUserInsfType(uid);
			if(type==21){
				dto.setParent(insm.getUserInsfId(uid));
			}else if(type==22){
				parentId = insm.getUserInsfId(uid).toString();
			}
		}
		BigInteger parent = null;
		String s = (String)request.getSession().getAttribute("s");
		if(iutil.isNull(s)){
			dto.setSearch(s);
		}
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
		}
		if(iutil.isNull(search)){
			dto.setSearch(search);
		}
		page = new Page(pageIndex,pageSize,total);
		List<ModelDto> list = lm.getCausehour(page,dto,parent);
		long total = 0;
		if(list != null){
			PageInfo<ModelDto> pageinfo = new PageInfo<ModelDto>(list);
			total = pageinfo.getTotal();
		}
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			for(ModelDto l:list){
				String[] str = l.getJidgather().split(",");
				if(l.getJidgather().equals("0")){
					json.put("jidgather", "0");
					json.put("dyne",0);
				}else{
					json.put("jidgather", str.length);
					String strsql = "and (";
					for(int i=0;i<str.length;i++){
						strsql += " fid = "+str[i];
						if(i<str.length-1){
							strsql += " or";
						}
					}
					strsql += " )";
					BigInteger dyne = lm.getDyneByJunctionno(strsql);
					json.put("dyne",dyne);
				}
				json.put("manhour", l.getHous());
				json.put("name",l.getFname());
				json.put("itemid",l.getFid());
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}
	
	/**
	 * 事业部超标报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCaustOverproof")
	@ResponseBody
	public String getCaustOverproof(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String parentId = request.getParameter("parent");
		String type = request.getParameter("otype");
		WeldDto dto = new WeldDto();
		BigInteger pid = null;
		if(!iutil.isNull(parentId)){
			//数据权限处理
			BigInteger uid = lm.getUserId(request);
			String afreshLogin = (String)request.getAttribute("afreshLogin");
			if(iutil.isNull(afreshLogin)){
				return "0";
			}
			int types = insm.getUserInsfType(uid);
			if(types==21){
				dto.setCompanyid(insm.getUserInsfId(uid));
				pid = insm.getUserInsfId(uid);
			}else if(types==22){
				parentId = insm.getUserInsfId(uid).toString();
				pid = insm.getUserInsfId(uid);
			}
		}
		BigInteger parent = null;
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
			pid = new BigInteger(parentId);
		}
		if(iutil.isNull(type)){
			if(type.equals("1")){
				dto.setYear("year");
			}else if(type.equals("2")){
				dto.setMonth("month");
			}else if(type.equals("3")){
				dto.setDay("day");
			}else if(type.equals("4")){
				dto.setWeek("week");
			}
		}
		List<LiveData> time = null;
		if(iutil.isNull(request.getParameter("page")) && iutil.isNull(request.getParameter("rows"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
			pageSize = Integer.parseInt(request.getParameter("rows"));
			page = new Page(pageIndex,pageSize,total);
			time = lm.getAllTime(page,dto);
		}else{
			time = lm.getAllTimes(dto);
		}
		long total = 0;
		if(time != null){
			PageInfo<LiveData> pageinfo = new PageInfo<LiveData>(time);
			total = pageinfo.getTotal();
		}
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONArray arys = new JSONArray();
		JSONArray arys1 = new JSONArray();
		JSONObject object = new JSONObject();
		try{
			List<ModelDto> list = lm.getCauseOverproof(dto,parent);
			List<LiveData> ins = lm.getAllInsf(pid,23);
			BigInteger[] num = null;
			for(LiveData live :time){
				json.put("weldTime",live.getWeldTime());
				arys.add(json);
			}
			for(int i=0;i<ins.size();i++){
				num = new BigInteger[time.size()];
				for(int j=0;j<time.size();j++){
					num[j] = new BigInteger("0");
					for(ModelDto l:list){
						if(ins.get(i).getFname().equals(l.getFname()) && time.get(j).getWeldTime().equals(l.getWeldTime())){
							num[j] = l.getOverproof();
						}
					}
				}
				json.put("overproof",num);
				json.put("name",ins.get(i).getFname());
				json.put("itemid",ins.get(i).getFid());
				arys1.add(json);
			}
			
			for(int i=0;i<time.size();i++){
				for(int j=0;j<arys1.size();j++){
					JSONObject js = (JSONObject)arys1.get(j);
					String overproof = js.getString("overproof").substring(1, js.getString("overproof").length()-1);
					String[] str = overproof.split(",");
					object.put("a"+j, str[i]);
				}
				object.put("w",time.get(i).getWeldTime());
				ary.add(object);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		obj.put("arys", arys);
		obj.put("arys1", arys1);
		return obj.toString();
	}
	
	/**
	 * 事业部超时报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCaustOvertime")
	@ResponseBody
	public String getCaustOvertime(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String parentId = request.getParameter("parent");
		String type = request.getParameter("otype");
		String number = request.getParameter("number");
		WeldDto dto = new WeldDto();
		BigInteger pid = null;
		if(!iutil.isNull(parentId)){
			//数据权限处理
			BigInteger uid = lm.getUserId(request);
			String afreshLogin = (String)request.getAttribute("afreshLogin");
			if(iutil.isNull(afreshLogin)){
				return "0";
			}
			int types = insm.getUserInsfType(uid);
			if(types==21){
				parentId = insm.getUserInsfId(uid).toString();
				pid = new BigInteger(parentId);
			}else if(types==22){
				parentId = insm.getUserInsfId(uid).toString();
				pid = new BigInteger(parentId);
			}
		}
		BigInteger parent = null;
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
			pid = new BigInteger(parentId);
		}
		if(iutil.isNull(type)){
			if(type.equals("1")){
				dto.setYear("year");
			}else if(type.equals("2")){
				dto.setMonth("month");
			}else if(type.equals("3")){
				dto.setDay("day");
			}else if(type.equals("4")){
				dto.setWeek("week");
			}
		}
		if(!iutil.isNull(number)){
			number = "0";
		}
		List<LiveData> time = null;
		if(iutil.isNull(request.getParameter("page")) && iutil.isNull(request.getParameter("rows"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
			pageSize = Integer.parseInt(request.getParameter("rows"));
			page = new Page(pageIndex,pageSize,total);
			time = lm.getAllTime(page,dto);
		}else{
			time = lm.getAllTimes(dto);
		}
		long total = 0;
		if(time != null){
			PageInfo<LiveData> pageinfo = new PageInfo<LiveData>(time);
			total = pageinfo.getTotal();
		}
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONArray arys = new JSONArray();
		JSONArray arys1 = new JSONArray();
		try{
			List<ModelDto> list = lm.getCaustOvertime(dto, number, parent);
			List<LiveData> ins = lm.getAllInsf(pid,23);
			int[] num = null;
			for(LiveData live :time){
				json.put("weldTime",live.getWeldTime());
				arys.add(json);
			}
			for(int i=0;i<ins.size();i++){
				num = new int[time.size()];
				for(int j=0;j<time.size();j++){
					num[j] = 0;
					for(ModelDto l:list){
						if(ins.get(i).getFname().equals(l.getFname()) && time.get(j).getWeldTime().equals(l.getWeldTime())){
							num[j] = Integer.parseInt(l.getOvertime().toString());;
						}
					}
				}
				json.put("overtime",num);
				json.put("name",ins.get(i).getFname());
				json.put("itemid",ins.get(i).getId());
				arys1.add(json);
			}
			JSONObject object = new JSONObject();
			
			for(int i=0;i<time.size();i++){
				for(int j=0;j<arys1.size();j++){
					JSONObject js = (JSONObject)arys1.get(j);
					String overproof = js.getString("overtime").substring(1, js.getString("overtime").length()-1);
					String[] str = overproof.split(",");
					object.put("a"+j, str[i]);
				}
				object.put("w",time.get(i).getWeldTime());
				ary.add(object);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		obj.put("arys", arys);
		obj.put("arys1", arys1);
		return obj.toString();
	}

	/**
	 * 事业部负荷率报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCaustLoads")
	@ResponseBody
	public String getCaustLoads(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String parentId = request.getParameter("parent");
		String type = request.getParameter("otype");
		WeldDto dto = new WeldDto();
		BigInteger pid = null;
		if(!iutil.isNull(parentId)){
			//数据权限处理
			BigInteger uid = lm.getUserId(request);
			String afreshLogin = (String)request.getAttribute("afreshLogin");
			if(iutil.isNull(afreshLogin)){
				return "0";
			}
			int types = insm.getUserInsfType(uid);
			if(types==21){
				parentId = insm.getUserInsfId(uid).toString();
				pid = new BigInteger(parentId);
			}else if(types==22){
				parentId = insm.getUserInsfId(uid).toString();
				pid = new BigInteger(parentId);
			}
		}
		BigInteger parent = null;
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
			pid = new BigInteger(parentId);
		}
		if(iutil.isNull(type)){
			if(type.equals("1")){
				dto.setYear("year");
			}else if(type.equals("2")){
				dto.setMonth("month");
			}else if(type.equals("3")){
				dto.setDay("day");
			}else if(type.equals("4")){
				dto.setWeek("week");
			}
		}
		List<LiveData> time = null;
		if(iutil.isNull(request.getParameter("page")) && iutil.isNull(request.getParameter("rows"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
			pageSize = Integer.parseInt(request.getParameter("rows"));
			page = new Page(pageIndex,pageSize,total);
			time = lm.getAllTime(page,dto);
		}else{
			time = lm.getAllTimes(dto);
		}
		long total = 0;
		if(time != null){
			PageInfo<LiveData> pageinfo = new PageInfo<LiveData>(time);
			total = pageinfo.getTotal();
		}
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONArray arys = new JSONArray();
		JSONArray arys1 = new JSONArray();
		try{
			List<ModelDto> list = lm.getCaustLoads(dto,parent);
			List<ModelDto> machine = lm.getCaustMachineCount(dto, parent);
			List<LiveData> ins = lm.getAllInsf(pid,23);
			double[] num = null;
			for(LiveData live :time){
				json.put("weldTime",live.getWeldTime());
				arys.add(json);
			}
			for(int i=0;i<ins.size();i++){
				num = new double[time.size()];
				for(int j=0;j<time.size();j++){
					num[j] = 0;
					for(ModelDto l:list){
						for(ModelDto m:machine){
							if(m.getWeldTime().equals(l.getWeldTime()) && m.getFid().equals(l.getIid())){
								if(ins.get(i).getFname().equals(l.getFname()) && time.get(j).getWeldTime().equals(l.getWeldTime())){
									num[j] = (double)Math.round(l.getLoads()/m.getLoads()*100*100)/100;
								}
							}
						}
					}
				}
				json.put("loads",num);
				json.put("name",ins.get(i).getFname());
				json.put("itemid",ins.get(i).getId());
				arys1.add(json);
			}
			JSONObject object = new JSONObject();
			
			for(int i=0;i<time.size();i++){
				for(int j=0;j<arys1.size();j++){
					JSONObject js = (JSONObject)arys1.get(j);
					String overproof = js.getString("loads").substring(1, js.getString("loads").length()-1);
					String[] str = overproof.split(",");
					object.put("a"+j, str[i]+"%");
				}
				object.put("w",time.get(i).getWeldTime());
				ary.add(object);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		obj.put("arys", arys);
		obj.put("arys1", arys1);
		return obj.toString();
	}
	
	/**
	 * 事业部空载率报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCaustNoLoads")
	@ResponseBody
	public String getCaustNoLoads(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String parentId = request.getParameter("parent");
		String type = request.getParameter("otype");
		WeldDto dto = new WeldDto();
		BigInteger pid = null;
		if(!iutil.isNull(parentId)){
			//数据权限处理
			BigInteger uid = lm.getUserId(request);
			String afreshLogin = (String)request.getAttribute("afreshLogin");
			if(iutil.isNull(afreshLogin)){
				return "0";
			}
			int types = insm.getUserInsfType(uid);
			if(types==21){
				parentId = insm.getUserInsfId(uid).toString();
				pid = new BigInteger(parentId);
			}else if(types==22){
				parentId = insm.getUserInsfId(uid).toString();
				pid = new BigInteger(parentId);
			}
		}
		BigInteger parent = null;
		dto.setDtoStatus(1);
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
			pid = new BigInteger(parentId);
		}
		if(iutil.isNull(type)){
			if(type.equals("1")){
				dto.setYear("year");
			}else if(type.equals("2")){
				dto.setMonth("month");
			}else if(type.equals("3")){
				dto.setDay("day");
			}else if(type.equals("4")){
				dto.setWeek("week");
			}
		}
		List<LiveData> time = null;
		if(iutil.isNull(request.getParameter("page")) && iutil.isNull(request.getParameter("rows"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
			pageSize = Integer.parseInt(request.getParameter("rows"));
			page = new Page(pageIndex,pageSize,total);
			time = lm.getAllTime(page,dto);
		}else{
			time = lm.getAllTimes(dto);
		}
		long total = 0;
		if(time != null){
			PageInfo<LiveData> pageinfo = new PageInfo<LiveData>(time);
			total = pageinfo.getTotal();
		}
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONArray arys = new JSONArray();
		JSONArray arys1 = new JSONArray();
		try{
			List<ModelDto> list = lm.getCaustNOLoads(dto, parent);
			List<ModelDto> machine = lm.getCaustMachineCount(dto, parent);
			List<LiveData> ins = lm.getAllInsf(pid,23);
			double[] num = null;
			for(LiveData live :time){
				json.put("weldTime",live.getWeldTime());
				arys.add(json);
			}
			for(int i=0;i<ins.size();i++){
				num = new double[time.size()];
				for(int j=0;j<time.size();j++){
					num[j] = 0;
					for(ModelDto l:list){
						for(ModelDto m:machine){
							if(m.getWeldTime().equals(l.getWeldTime()) && m.getFid().equals(l.getIid())){
								if(ins.get(i).getFname().equals(l.getFname()) && time.get(j).getWeldTime().equals(l.getWeldTime())){
									BigInteger livecount = lm.getCountByTime(l.getIid(), "%"+l.getWeldTime()+"%",null);
									num[j] = (double)Math.round(l.getLoads()/livecount.doubleValue()/m.getLoads()*100*100)/100;
								}
							}
						}
					}
				}
				json.put("loads",num);
				json.put("name",ins.get(i).getFname());
				json.put("itemid",ins.get(i).getId());
				arys1.add(json);
			}
			JSONObject object = new JSONObject();
			
			for(int i=0;i<time.size();i++){
				for(int j=0;j<arys1.size();j++){
					JSONObject js = (JSONObject)arys1.get(j);
					String overproof = js.getString("loads").substring(1, js.getString("loads").length()-1);
					String[] str = overproof.split(",");
					object.put("a"+j, str[i]+"%");
				}
				object.put("w",time.get(i).getWeldTime());
				ary.add(object);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		obj.put("arys", arys);
		obj.put("arys1", arys1);
		return obj.toString();
	}
	/**
	 * 事业部闲置率报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCaustIdle")
	@ResponseBody
	public String getCaustIdle(HttpServletRequest request){
		if(iutil.isNull(request.getParameter("page"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
		}
		if(iutil.isNull(request.getParameter("rows"))){
			pageSize = Integer.parseInt(request.getParameter("rows"));
		}
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String type = request.getParameter("otype");
		String parent = request.getParameter("parent");
		WeldDto dto = new WeldDto();
		if(!iutil.isNull(parent)){
			//数据权限处理
			BigInteger uid = lm.getUserId(request);
			String afreshLogin = (String)request.getAttribute("afreshLogin");
			if(iutil.isNull(afreshLogin)){
				return "0";
			}
			int types = insm.getUserInsfType(uid);
			if(types==21){
				parent = insm.getUserInsfId(uid).toString();
			}else if(types==22){
				parent = insm.getUserInsfId(uid).toString();
			}
		}
		BigInteger parentid = null;
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(type)){
			if(type.equals("1")){
				dto.setYear("year");
			}else if(type.equals("2")){
				dto.setMonth("month");
			}else if(type.equals("4")){
				dto.setWeek("week");
			}
		}
		if(iutil.isNull(parent)){
			parentid = new BigInteger(parent);
		}
		List<LiveData> time = null;
		if(iutil.isNull(request.getParameter("page")) && iutil.isNull(request.getParameter("rows"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
			pageSize = Integer.parseInt(request.getParameter("rows"));
			page = new Page(pageIndex,pageSize,total);
			time = lm.getAllTime(page,dto);
		}else{
			time = lm.getAllTimes(dto);
		}
		long total = 0;
		if(time != null){
			PageInfo<LiveData> pageinfo = new PageInfo<LiveData>(time);
			total = pageinfo.getTotal();
		}
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONArray arys = new JSONArray();
		JSONArray arys1 = new JSONArray();
		try{
			List<ModelDto> list = lm.getCaustIdle(dto, parentid);
			List<LiveData> ins = lm.getAllInsf(parentid,23);
			double[] num = null;
			for(LiveData live :time){
				json.put("weldTime",live.getWeldTime());
				arys.add(json);
			}
			for(int i=0;i<ins.size();i++){
				num = new double[time.size()];
				int count = lm.getMachineCount(ins.get(i).getFid());
				for(int j=0;j<time.size();j++){
					num[j] = count;
					for(ModelDto l:list){
						if(ins.get(i).getFname().equals(l.getFname()) && time.get(j).getWeldTime().equals(l.getWeldTime())){
							num[j] = count - l.getNum().doubleValue();
						}
					}
				}
				json.put("idle",num);
				json.put("name",ins.get(i).getFname());
				json.put("id",ins.get(i).getFid());
				arys1.add(json);
			}
			JSONObject object = new JSONObject();
			
			for(int i=0;i<time.size();i++){
				for(int j=0;j<arys1.size();j++){
					JSONObject js = (JSONObject)arys1.get(j);
					String overproof = js.getString("idle").substring(1, js.getString("idle").length()-1);
					String[] str = overproof.split(",");
					object.put("a"+j, str[i]);
				}
				object.put("w",time.get(i).getWeldTime());
				ary.add(object);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		obj.put("arys", arys);
		obj.put("arys1", arys1);
		return obj.toString();
	}
	
	/**
	 * 事业部单台设备运行数据统计信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCaustUse")
	@ResponseBody
	public String getCaustUse(HttpServletRequest request){
		if(iutil.isNull(request.getParameter("page"))){
			pageIndex = Integer.parseInt(request.getParameter("page"));
		}
		if(iutil.isNull(request.getParameter("rows"))){
			pageSize = Integer.parseInt(request.getParameter("rows"));
		}
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String type = request.getParameter("type");
		WeldDto dto = new WeldDto();
		lm.getUserId(request);
		String afreshLogin = (String)request.getAttribute("afreshLogin");
		if(iutil.isNull(afreshLogin)){
			return "0";
		}
		BigInteger typeid = null;
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(type)){
			typeid = new BigInteger(type);
		}
		page = new Page(pageIndex,pageSize,total);
		List<ModelDto> list = lm.getCaustUse(page, dto, typeid);
		long total = 0;
		if(list != null){
			PageInfo<ModelDto> pageinfo = new PageInfo<ModelDto>(list);
			total = pageinfo.getTotal();
		}
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			for(ModelDto l:list){
				double num = wm.getMachineCountByManu(l.getFid(),typeid).doubleValue();
				double time = (double)Math.round(l.getTime()/num*100)/100;
				json.put("time", time);
				json.put("fname", l.getFname()+" - "+l.getType());
				json.put("type", l.getType());
				json.put("fid",l.getFid());
				json.put("num", num);
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}
	
	/**
	 * 事业部下拉框
	 * @return
	 */
	@RequestMapping("getItem")
	@ResponseBody
	public String getItem(HttpServletRequest request){
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		BigInteger parent = null;
		//数据权限处理
		BigInteger uid = lm.getUserId(request);
		String afreshLogin = (String)request.getAttribute("afreshLogin");
		if(iutil.isNull(afreshLogin)){
			json.put("id", 0);
			json.put("name", "无");
			ary.add(json);
			obj.put("ary", ary);
			return obj.toString();
		}
		int type = insm.getUserInsfType(uid);
		if(type==21){
			parent = insm.getUserInsfId(uid);
		}else if(type==22){
			parent = insm.getUserInsfId(uid);
		}
		try{
			List<Insframework> list = insm.getInsByType(23,parent);
			for(Insframework i:list){
				json.put("id", i.getId());
				json.put("name", i.getName());
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("ary", ary);
		return obj.toString();
	}
	

	/**
	 * 事业部工效报表信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCaustEfficiency")
	@ResponseBody
	public String getCaustEfficiency(HttpServletRequest request){
		String time1 = request.getParameter("dtoTime1");
		String time2 = request.getParameter("dtoTime2");
		String parentId = request.getParameter("parent");
		String nextparent = request.getParameter("nextparent");
		int min = -1,max = -1;
		if(iutil.isNull(request.getParameter("min"))){
			min = Integer.parseInt(request.getParameter("min"));
		}
		if(iutil.isNull(request.getParameter("max"))){
			max = Integer.parseInt(request.getParameter("max"));
		}
		WeldDto dto = new WeldDto();
		BigInteger parent = null;
		if(iutil.isNull(time1)){
			dto.setDtoTime1(time1);
		}
		if(iutil.isNull(time2)){
			dto.setDtoTime2(time2);
		}
		if(iutil.isNull(nextparent)){
			parent = new BigInteger(nextparent);
		}else if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
		}
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		page = new Page(pageIndex,pageSize,total);
		List<ModelDto> list = lm.caustEfficiency(page, parent, dto, min, max);
		PageInfo<ModelDto> pageinfo = new PageInfo<ModelDto>(list);
		long total = pageinfo.getTotal();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			for(ModelDto m : list){
				json.put("id",m.getFid());
				json.put("iname",m.getIname());
				json.put("wname",m.getWname());
				json.put("wid",m.getFwelder_id());
				String[] str = m.getJidgather().split(",");
				String search = "and (";
				for(int i=0;i<str.length;i++){
					search += " fid = "+str[i];
					if(i<str.length-1){
						search += " or";
					}
				}
				search += " )";
				BigInteger dyne = lm.getDyneByJunctionno(search);
				json.put("dyne",dyne);
				json.put("weldtime",m.getWeldTime());
				json.put("num",str.length);
				ary.add(json);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}

	/**
	 * 获取工效图表信息
	 * @param request
	 * @param parent
	 * @return
	 */
	@RequestMapping("/getCaustEfficiencyChart")
	@ResponseBody
	public String getCaustEfficiency(HttpServletRequest request,@RequestParam BigInteger parent){
		JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			String time1 = request.getParameter("dtoTime1");
			String time2 = request.getParameter("dtoTime2");
			String nextparent = request.getParameter("nextparent");
			WeldDto dto = new WeldDto();
			if(iutil.isNull(time1)){
				dto.setDtoTime1(time1);
			}
			if(iutil.isNull(time2)){
				dto.setDtoTime2(time2);
			}
			if(iutil.isNull(nextparent)){
				parent = new BigInteger(nextparent);
			}
			List<ModelDto> list = lm.getEfficiencyChartNum(dto, parent);
			List<ModelDto> efficiency = null;
			String[] num1 = new String[10];
			double[] num2 = new double[10];
			int oldnum = 0,newnum = 0,maxnum = 0;
			for(ModelDto m:list){
				if(m!=null){
					if(m.getAvgnum()==0){
						m.setAvgnum(2);
						num1[0] = m.getMinnum()+"-"+(m.getMinnum()+m.getAvgnum());
						for(int i=1;i<10;i++){
							oldnum = m.getMinnum()+m.getAvgnum()*i+1;
							newnum = m.getMinnum()+m.getAvgnum()*(i+1);
							num1[i] = oldnum+"-"+newnum;
						}
					}else{
						num1[0] = m.getMinnum()+"-"+(m.getMinnum()+m.getAvgnum());
						for(int i=1;i<9;i++){
							oldnum = m.getMinnum()+m.getAvgnum()*i+1;
							newnum = m.getMinnum()+m.getAvgnum()*(i+1);
							num1[i] = oldnum+"-"+newnum;
						}
						maxnum = m.getMinnum()+m.getAvgnum()*10+10;
						num1[9] = newnum+"-"+maxnum;
					}
					efficiency = lm.getEfficiencyChart(dto, parent, m.getMinnum(), m.getAvgnum());
					for(ModelDto e:efficiency){
						double sum = e.getSum1()+e.getSum2()+e.getSum3()+e.getSum4()+e.getSum5()+e.getSum6()+e.getSum7()+e.getSum8()+e.getSum9()+e.getSum10();
						num2[0] = e.getSum1()/sum*100;num2[1] = e.getSum2()/sum*100;
						num2[2] = e.getSum3()/sum*100;num2[3] = e.getSum4()/sum*100;
						num2[4] = e.getSum5()/sum*100;num2[5] = e.getSum6()/sum*100;
						num2[6] = e.getSum7()/sum*100;num2[7] = e.getSum7()/sum*100;
						num2[8] = e.getSum9()/sum*100;num2[9] = e.getSum10()/sum*100;
					}
					for(int i=0;i<num2.length;i++){
						num2[i] = (double)Math.round(num2[i]*100)/100;
					}
					json.put("num1", num1);
					json.put("num2", num2);
					ary.add(json);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("ary", ary);
		return obj.toString();
	}
	
}
