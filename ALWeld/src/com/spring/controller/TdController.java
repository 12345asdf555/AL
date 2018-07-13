package com.spring.controller;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.spring.page.Page;
import com.spring.util.IsnullUtil;
import com.spring.model.MyUser;
import com.spring.model.Td;
import com.spring.model.User;
import com.spring.service.TdService;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/td",produces = { "text/json;charset=UTF-8" })
public class TdController {
	
	private Page page;
	private int pageIndex = 1;
	private int pageSize = 10;
	private int total = 0;
	
	@Autowired
	private TdService tdService;
	private Td td;
	
	IsnullUtil iutil = new IsnullUtil();
	/**
	 * 获取所有用户列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/AllTdbf")
	@ResponseBody
	public String Alltdbf(HttpServletRequest request){
		String websocket = request.getSession().getServletContext().getInitParameter("websocket");
//		request.setAttribute("web_socket", websocket);
		JSONObject obj = new JSONObject();
		obj.put("web_socket", websocket);
		return obj.toString();
	}
	
	@RequestMapping("/AllTd")
	public String Alltd(HttpServletRequest request){
/*		MyUser myuser = (MyUser) SecurityContextHolder.getContext()  
			    .getAuthentication()  
			    .getPrincipal();
		long uid = myuser.getId();
		String insname = tdService.findInsname(tdService.findIns(uid));
		request.setAttribute("insname", insname);*/
		return "td/BackUp";
	}
	
	@RequestMapping("/AllTdd")
	public String AllTdd(HttpServletRequest request){
		request.setAttribute("divi", request.getParameter("value"));
		return "/division";
	}
	
	@RequestMapping("/AllTddp")
	public String AllTddp(HttpServletRequest request){
		request.setAttribute("proj", request.getParameter("value"));
		return "/project";
	}
	
	@RequestMapping("/AllTdp")
	public String AllTdp(HttpServletRequest request){
		return "/project";
	}
	
	@RequestMapping("/AllTdad")
	@ResponseBody
	public String AllTdad(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		String eq = request.getParameter("e");
		String an = request.getParameter("a");
		String vo = request.getParameter("v");
		String value = request.getParameter("value");
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			String[] equ = eq.split(",");
			String[] anp = an.split(",");
			String[] vol = vo.split(",");
			System.out.println(equ);
			for(int i = 0;i < equ.length;i++)
			{
				if(value.equals(equ[i])){
				json.put("voltage",vol[i]);
				json.put("electricity",anp[i]);
				ary.add(json);
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
		
	}
	
	@RequestMapping("/AllTda")
	public String AllTda(HttpServletRequest request){
		request.setAttribute("av", request.getParameter("value"));
		return "/AV";
	}
	
	@RequestMapping("/getAllTd")
	@ResponseBody
	public String getAllTd(HttpServletRequest request){
		
		JSONObject obj = new JSONObject();
		String da = request.getParameter("data");
/*		System.out.println(da);*/
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			for(int i = 0;i < da.length();i+=53)
			{
				json.put("fstatus_id", da.substring(0+i, 2+i));
				json.put("finsframework_id", da.substring(2+i, 4+i));
				json.put("fequipment_no", da.substring(4+i, 8+i));
				json.put("fwelder_no", da.substring(8+i, 12+i));
				json.put("voltage", da.substring(12+i, 16+i));
				json.put("electricity", da.substring(16+i, 20+i));
				json.put("time", da.substring(20+i, 41+i));
				json.put("maxele", da.substring(41+i, 44+i));
				json.put("minele", da.substring(44+i, 47+i));
				json.put("maxvol", da.substring(47+i, 50+i));
				json.put("minvol", da.substring(50+i, 53+i));
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getAllTddiv")
	@ResponseBody
	public String getAllTddiv(HttpServletRequest request){
		
		MyUser myuser = (MyUser) SecurityContextHolder.getContext()  
			    .getAuthentication()  
			    .getPrincipal();
		JSONObject obj = new JSONObject();
		long uid = myuser.getId();
		String insname = tdService.findInsname(uid);
		List<Td> findAlld = tdService.findAlldiv(tdService.findIns(uid));
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			for(Td td:findAlld)
			{
				json.put("fid", td.getFdi());
				json.put("fname", td.getFdn());
				json.put("fparent", td.getFdp());
				json.put("ftype", td.getFdt());
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getAllTdp")
	@ResponseBody
	public String getAllTdp(HttpServletRequest request){
		
		JSONObject obj = new JSONObject();
		String da = request.getParameter("data");
		System.out.println(da);
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			for(int i = 0;i < da.length();i+=53)
			{
				json.put("fstatus_id", da.substring(0+i, 2+i));
				json.put("finsframework_id", da.substring(2+i, 4+i));
				json.put("fequipment_no", da.substring(4+i, 8+i));
				json.put("fwelder_no", da.substring(8+i, 12+i));
				String weldname = tdService.findweld(da.substring(8+i, 12+i));
				json.put("fname", weldname);
				json.put("voltage", da.substring(12+i, 16+i));
				json.put("electricity", da.substring(16+i, 20+i));
				json.put("time", da.substring(20+i, 41+i));
				json.put("maxele", da.substring(41+i, 44+i));
				json.put("minele", da.substring(44+i, 47+i));
				json.put("maxvol", da.substring(47+i, 50+i));
				json.put("minvol", da.substring(50+i, 53+i));
				String position = tdService.findPosition(da.substring(4+i,8+i));
				json.put("fposition", position);
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getAllTdp1")
	@ResponseBody
	public String getAllTdp1(HttpServletRequest request){
		
		JSONObject obj = new JSONObject();
		long uid = Long.parseLong(request.getParameter("ins"));
		List<Td> findAllpro = tdService.findAlldiv(uid);
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			for(Td td:findAllpro){
				json.put("fid", td.getFdi());
				json.put("fname", td.getFdn());
				json.put("fparent", td.getFdp());
				json.put("ftype", td.getFdt());
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getAllTdp2")
	@ResponseBody
	public String getAllTdp2(HttpServletRequest request){
		
		JSONObject obj = new JSONObject();
		String insname = request.getParameter("div");
		long insid = tdService.findInsid(insname);
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			
				json.put("fid", insid);
				ary.add(json);
			
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getAllTdd")
	@ResponseBody
	public String getAllTdd(HttpServletRequest request){
		
		JSONObject obj = new JSONObject();
		String insname = request.getParameter("div");
		long insid = tdService.findInsid(insname);
		List<Td> findAlld = tdService.findAlldiv(insid);
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			for(Td td:findAlld)
			{
				json.put("fid", td.getFdi());
				json.put("fname", td.getFdn());
				json.put("fparent", td.getFdp());
				json.put("ftype", td.getFdt());
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getAllTdd1")
	@ResponseBody
	public String getAllTdd1(HttpServletRequest request){
		
		JSONObject obj = new JSONObject();
		List<Td> findAllcom = tdService.findAllcom();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			for(Td td:findAllcom){
/*				json.put("fpname",td.getFinsp());
				json.put("fdname",td.getFinsd());
				json.put("fcname",td.getFinsc());*/
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getAllTdd2")
	@ResponseBody
	public String getAllTdd2(HttpServletRequest request){
		
		JSONObject obj = new JSONObject();
		List<Td> findAllcom = tdService.findAllcom();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			for(Td td:findAllcom){
/*				json.put("fpname",td.getFinsp());
				json.put("fdname",td.getFinsd());
				json.put("fcname",td.getFinsc());*/
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getWeld")
	@ResponseBody
	public String getWeld(HttpServletRequest request){
		
		String weldid = request.getParameter("weldid");
		JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
				json.put("fweldname",tdService.findweld(weldid));
				ary.add(json);
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getPosition")
	@ResponseBody
	public String getPosition(HttpServletRequest request){
		
		String equip = request.getParameter("equip");
		String eee = tdService.findPosition(equip);
		JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
				json.put("fpositin",eee);
				ary.add(json);
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/getAllPosition")
	@ResponseBody
	public String getAllPosition(HttpServletRequest request){
		
		List<Td> getAP = tdService.getAllPosition();		
		JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			for(Td td:getAP){
				json.put("fpositin",td.getPosition());
				json.put("fequip", td.getFequipment_no());
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/isnull")
	@ResponseBody
	public String isnull(HttpServletRequest request){
		JSONObject obj = new JSONObject();
		List<Td> getAP = tdService.getAllPosition();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			for(Td td:getAP){
				json.put("fequipment_no",td.getFequipment_no());
				json.put("fposition", td.getPosition());
				ary.add(json);
			}
		}catch(Exception e){
			/*e.getMessage();*/
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/geInsname")
	@ResponseBody
	public String geInsname(HttpServletRequest request){
		
		int iid =  Integer.parseInt(request.getParameter("iid"));
		String insname = tdService.findInsname(iid);;
		JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
				json.put("fid", insname);
				ary.add(json);
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/allWeldname")
	@ResponseBody
	public String allWeldname(HttpServletRequest request){
		
		List<Td> fwn = tdService.allWeldname();	
		JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		try{
			for(Td td:fwn){
				json.put("fname",td.getFname());
				json.put("fwelder_no", td.getFwelder_no());
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("rows", ary);
		return obj.toString();
	}
}