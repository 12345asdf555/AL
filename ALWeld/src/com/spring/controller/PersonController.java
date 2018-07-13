package com.spring.controller;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.spring.model.MyUser;
import com.spring.model.Person;
import com.spring.page.Page;
import com.spring.service.DictionaryService;
import com.spring.service.InsframeworkService;
import com.spring.service.PersonService;
import com.spring.util.IsnullUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/welders",produces = { "text/json;charset=UTF-8" })
public class PersonController {
	
	private Page page;
	private int pageIndex = 1;
	private int pageSize = 10;
	private int total = 0;
	@Autowired
	private PersonService welderService;
	
	@Autowired
	private InsframeworkService im;
	
	@Autowired
	private DictionaryService dm;
	
	IsnullUtil iutil = new IsnullUtil();
	
	/**
	 * 获取所有用户列表
	 * @param request
	 * @return
	 */
	
	@RequestMapping("/AllWelder")
	public String AllUser(HttpServletRequest request){
		return "welder/allWelder";
	}

	@RequestMapping("/getAllWelder")
	@ResponseBody
	public String getAllWelder(HttpServletRequest request){
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		String search = request.getParameter("searchStr");
/*		if(search!=null&&search!="null"){
		String ss[] = search.split("'");
		System.out.println(ss[0].substring(0, 11));
		if(ss[0].substring(0, 11).equals(" fwelder_no")){
			String sea = Integer.toHexString(Integer.valueOf(ss[1]));
			if(sea.length()!=4){
                int lenth=4-sea.length();
                for(int i=0;i<lenth;i++){
                	sea="0"+sea;
                }
              }
			search = " fwelder_no = '"+sea+"'";
		}
		}*/
		String parentId = request.getParameter("parent");
		BigInteger parent = null;
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
		}
		page = new Page(pageIndex,pageSize,total);
		List<Person> findAll = welderService.findAll(page,parent,search);
		long total = 0;
		
		if(findAll != null){
			PageInfo<Person> pageinfo = new PageInfo<Person>(findAll);
			total = pageinfo.getTotal();
		}
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			for(Person welder:findAll){
//				String creat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(welder.getCreatedate());
//				String update = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(welder.getUpdatedate());
				json.put("id", welder.getId());
				json.put("name", welder.getName());
				json.put("welderno", welder.getWelderno());
				json.put("cellphone", welder.getCellphone());
				json.put("cardnum", welder.getCardnum());
//				json.put("createdate", welder.getCreatedate());
//				json.put("updatedate", format(welder.getUpdatedate());
				json.put("owner", welder.getInsname());
//				json.put("leveid", welder.getValuename());
//				json.put("quali", welder.getValuenamex());
				json.put("back", welder.getBack());
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/toAddWelder")
	public String toAddWelder(HttpServletRequest request){
		return "welder/addWelder";
	}
	
	@RequestMapping("/getLeve")
	@ResponseBody
	public String getIns(HttpServletRequest request){
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		int we = Integer.parseInt(request.getParameter("we"));
		List<Person> findLeve = welderService.findLeve(we);
		try{
			if(we==8){
			for(Person welder:findLeve){
				json.put("leveid", welder.getVal());
				json.put("levename", welder.getValuename());
				ary.add(json);
			}
			}else{
				for(Person welder:findLeve){
					json.put("quaid", welder.getVal());
					json.put("quaname", welder.getValuename());
					ary.add(json);
				}
			}
				
		}catch(Exception e){
			e.printStackTrace();
		}
		obj.put("rows", ary);
		return obj.toString();
/*		return "redirect:/user/AllUser";*/
	}
	
	@RequestMapping("/addWelder")
	@ResponseBody
	public String addWelder(HttpServletRequest request){
		Person welder = new Person();
		MyUser myuser = (MyUser) SecurityContextHolder.getContext()  
			    .getAuthentication()  
			    .getPrincipal();
		String creat = String.valueOf(myuser.getId());
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject obj = new JSONObject();
		try{
//			welder.setQuali(Integer.parseInt(request.getParameter("qua")));
//			welder.setLeveid(Integer.parseInt(request.getParameter("leve")));
			welder.setOwner(new BigInteger(request.getParameter("ins")));
/*			String sea = Integer.toHexString(Integer.valueOf(request.getParameter("welderno")));
			if(sea.length()!=4){
                int lenth=4-sea.length();
                for(int i=0;i<lenth;i++){
                	sea="0"+sea;
                }
              }*/
			welder.setWelderno(request.getParameter("welderno"));
			welder.setName(request.getParameter("name"));
			welder.setCellphone(request.getParameter("cellphone"));
			welder.setCardnum(request.getParameter("cardnum"));
			welder.setBack(request.getParameter("back"));
			welder.setCreater(new BigInteger(creat));
			welder.setUpdater(new BigInteger(creat));
//			welder.setCreatedate(sdf.parse(sdf.format((new Date()).getTime())));
//			welder.setUpdatedate(sdf.parse(sdf.format((new Date()).getTime())));
			welderService.save(welder);
			obj.put("success", true);
		}catch(Exception e){
			obj.put("success", false);
			obj.put("errorMsg", e.getMessage());
		}
		return obj.toString();
/*		return "redirect:/user/AllUser";*/
	}
	
	@RequestMapping("/toUpdateWelder")
	public String toUpdateWps(@RequestParam BigInteger fid,HttpServletRequest request){
		Person Welder = welderService.findById(fid);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		request.setAttribute("welder", Welder);
//		request.setAttribute("update", sdf.format(Welder.getUpdatedate()));
//		request.setAttribute("create", sdf.format(Welder.getCreatedate()));
		return "welder/editWelder";
	}
	
	@RequestMapping("/updateWelder")
	@ResponseBody
	public String updateWelder(HttpServletRequest request){
		Person welder = new Person();
		MyUser myuser = (MyUser) SecurityContextHolder.getContext()  
			    .getAuthentication()  
			    .getPrincipal();
		String creat = String.valueOf(myuser.getId());
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject obj = new JSONObject();
		try{
			welder.setId(new BigInteger(request.getParameter("FID")));
//			welder.setQuali(Integer.parseInt(request.getParameter("qua")));
//			welder.setLeveid(Integer.parseInt(request.getParameter("leve")));
			welder.setOwner(new BigInteger(request.getParameter("ins")));
/*			String sea = Integer.toHexString(Integer.valueOf(request.getParameter("welderno")));
			if(sea.length()!=4){
                int lenth=4-sea.length();
                for(int i=0;i<lenth;i++){
                	sea="0"+sea;
                }
              }*/
			welder.setWelderno(request.getParameter("welderno"));
			welder.setName(request.getParameter("name"));
			welder.setCellphone(request.getParameter("cellphone"));
			welder.setCardnum(request.getParameter("cardnum"));
			welder.setBack(request.getParameter("back"));
			welder.setUpdater(new BigInteger(creat));
//			welder.setUpdatedate(sdf.parse(sdf.format((new Date()).getTime())));
//			welder.setCreatedate(sdf.parse(request.getParameter("createdate")));
		    welderService.update(welder);
			obj.put("success", true);
			}catch(Exception e){
				obj.put("success", false);
				obj.put("errorMsg", e.getMessage());
			}
			return obj.toString();

	}
	
	@RequestMapping("/toDestroyWelder")
	public String toDestroyWps(@RequestParam BigInteger fid,HttpServletRequest request){
		Person Welder = welderService.findById(fid);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		request.setAttribute("welder", Welder);
//		request.setAttribute("update", sdf.format(Welder.getUpdatedate()));
//		request.setAttribute("create", sdf.format(Welder.getCreatedate()));
		return "welder/destroyWelder";
	}
	
	@RequestMapping("/destroyWelder")
	@ResponseBody
	public String destroyWelder(@RequestParam BigInteger fid){

			JSONObject obj = new JSONObject();
			try{
				welderService.delete(fid);
				 obj.put("success", true);
			}catch(Exception e){
				obj.put("success", false);
				obj.put("errorMsg", e.getMessage());
			}
			return obj.toString();
	}
	
	@RequestMapping("/weldersvalidate")
	@ResponseBody
	private String weldersvalidate(@RequestParam String welderno){
		boolean data = true;
		int count = welderService.getUsernameCount(welderno);
		if(count>0){
			data = false;
		}
		return data + "";
	}
}