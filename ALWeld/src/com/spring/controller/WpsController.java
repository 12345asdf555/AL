package com.spring.controller;

import java.awt.Robot;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
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
import com.spring.model.User;
import com.spring.model.Wps;
import com.spring.page.Page;
import com.spring.service.WpsService;
import com.spring.util.IsnullUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/wps",produces = { "text/json;charset=UTF-8" })
public class WpsController {
	
	private Page page;
	private int pageIndex = 1;
	private int pageSize = 10;
	private int total = 0;
	private String wpsfid;
	private String wpspre;
	@Autowired
	private WpsService wpsService;
	
    public static final String IP_ADDR = "121.196.222.216";//服务器地址   
    public static final int PORT = 5555;//服务器端口号  
	
	IsnullUtil iutil = new IsnullUtil();
	private SocketChannel socketChannel;
	
	/**
	 * 获取所有用户列表
	 * @param request
	 * @return
	 */
	
	@RequestMapping("/AllWps")
	public String AllUser(HttpServletRequest request){
		return "weldwps/allWps";
	}

	@RequestMapping("/getAllWps")
	@ResponseBody
	public String getAllWps(HttpServletRequest request){
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		String search = request.getParameter("searchStr");
		String parentId = request.getParameter("parent");
		BigInteger parent = null;
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
		}
		page = new Page(pageIndex,pageSize,total);
		List<Wps> findAll = wpsService.findAll(page,parent,search);
		long total = 0;
		
		if(findAll != null){
			PageInfo<Wps> pageinfo = new PageInfo<Wps>(findAll);
			total = pageinfo.getTotal();
		}

		request.setAttribute("wpsList", findAll);
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		try{
			for(Wps wps:findAll){
				String creat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(wps.getFcreatedate());
				String update = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(wps.getFupdatedate());
				json.put("FID", wps.getFid());
				json.put("FWPSNum", wps.getFwpsnum());
				json.put("Fweld_I", wps.getFweld_i());
				json.put("Fweld_V", wps.getFweld_v());
				json.put("Fweld_I_MAX",wps.getFweld_i_max());
				json.put("Fweld_I_MIN", wps.getFweld_i_min());
				json.put("Fweld_V_MAX", wps.getFweld_v_max());
				json.put("Fweld_V_MIN", wps.getFweld_v_min());
				json.put("Fweld_Alter_I", wps.getFweld_alter_i());
				json.put("Fweld_Alter_V", wps.getFweld_alter_v());
				json.put("Fweld_PreChannel", wps.getFweld_prechannel());
				json.put("FCReateDate",creat);
				json.put("FUpdateDate", update);
				json.put("Fowner",wps.getInsname());
				json.put("Fback", wps.getFback());
				json.put("Fname", wps.getFname());
				json.put("Fdiameter", wps.getFdiameter());
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
	}
	
	@RequestMapping("/toAddWps")
	public String toAddUser(HttpServletRequest request){
		return "weldwps/addWps";
	}
	
	@RequestMapping("/toUpdateWps")
	public String toUpdateWps(@RequestParam BigInteger fid,HttpServletRequest request){
		Wps wps = wpsService.findById(fid);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		request.setAttribute("wps", wps);
		request.setAttribute("update", sdf.format(wps.getFupdatedate()));
		request.setAttribute("create", sdf.format(wps.getFcreatedate()));
		return "weldwps/editWps";
	}
	
	@RequestMapping("/toDestroyWps")
	public String toDestroyWps(@RequestParam BigInteger fid,HttpServletRequest request){
		Wps wps = wpsService.findById(fid);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		request.setAttribute("wps", wps);
		request.setAttribute("update", sdf.format(wps.getFupdatedate()));
		request.setAttribute("create", sdf.format(wps.getFcreatedate()));
		return "weldwps/destroyWps";
	}
	
	@RequestMapping("/addWps")
	@ResponseBody
	public String addUser(HttpServletRequest request){
		Wps wps = new Wps();
		MyUser myuser = (MyUser) SecurityContextHolder.getContext()  
			    .getAuthentication()  
			    .getPrincipal();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject obj = new JSONObject();
		try{
	        wps.setFweld_i( Integer.parseInt(request.getParameter("Fweld_I")));
	        wps.setFweld_v( Integer.parseInt(request.getParameter("Fweld_V")));
	        wps.setFweld_i_max(Integer.parseInt(request.getParameter("Fweld_I_MAX")));
	        wps.setFweld_i_min(Integer.parseInt(request.getParameter("Fweld_I_MIN")));
	        wps.setFweld_v_max(Integer.parseInt(request.getParameter("Fweld_V_MAX")));
	        wps.setFweld_v_min(Integer.parseInt(request.getParameter("Fweld_V_MIN")));
	        wps.setFweld_alter_i(Integer.parseInt(request.getParameter("Fweld_Alter_I")));
	        wps.setFweld_alter_v(Integer.parseInt(request.getParameter("Fweld_Alter_V")));
	        wps.setFweld_prechannel(Integer.parseInt(request.getParameter("Fweld_PreChannel")));
	        wps.setFback(request.getParameter("Fback"));
	        wps.setFname(request.getParameter("Fname"));
			wps.setFwpsnum(request.getParameter("fwn"));
			wps.setFcreater(myuser.getId());
			wps.setFupdater(myuser.getId());
			wps.setFowner(Long.parseLong(request.getParameter("ins")));
			wps.setFcreatedate(sdf.parse(sdf.format((new Date()).getTime())));
			wps.setFupdatedate(sdf.parse(sdf.format((new Date()).getTime())));
			wps.setFdiameter(Double.valueOf(request.getParameter("Fdiameter")));
			wpsService.save(wps);
			obj.put("success", true);
		}catch(Exception e){
			obj.put("success", false);
			obj.put("errorMsg", e.getMessage());
		}
		return obj.toString();
/*		return "redirect:/user/AllUser";*/
	}
	
	@RequestMapping("/updateWps")
	@ResponseBody
	public String updateWps(Wps wps,HttpServletRequest request){
		MyUser myuser = (MyUser) SecurityContextHolder.getContext()  
			    .getAuthentication()  
			    .getPrincipal();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject obj = new JSONObject();
		try{
			wps.setFid(Long.parseLong(request.getParameter("FID")));
			wps.setFupdater(myuser.getId());
	        wps.setFowner(Long.parseLong(request.getParameter("ins")));
	        wps.setFcreatedate(sdf.parse(request.getParameter("FCReateDate")));
	        wps.setFwpsnum(request.getParameter("FWPSNum"));
	        wps.setFweld_i( Integer.parseInt(request.getParameter("Fweld_I")));
	        wps.setFweld_v( Integer.parseInt(request.getParameter("Fweld_V")));
	        wps.setFweld_i_max(Integer.parseInt(request.getParameter("Fweld_I_MAX")));
	        wps.setFweld_i_min(Integer.parseInt(request.getParameter("Fweld_I_MIN")));
	        wps.setFweld_v_max(Integer.parseInt(request.getParameter("Fweld_V_MAX")));
	        wps.setFweld_v_min(Integer.parseInt(request.getParameter("Fweld_V_MIN")));
	        wps.setFweld_alter_i(Integer.parseInt(request.getParameter("Fweld_Alter_I")));
	        wps.setFweld_alter_v(Integer.parseInt(request.getParameter("Fweld_Alter_V")));
	        wps.setFweld_prechannel(Integer.parseInt(request.getParameter("Fweld_PreChannel")));
	        wps.setFupdatedate(sdf.parse(sdf.format((new Date()).getTime())));
	        wps.setFback(request.getParameter("Fback"));
	        wps.setFname(request.getParameter("Fname"));
	        wps.setFdiameter(Double.valueOf(request.getParameter("Fdiameter")));
		    wpsService.update(wps);
			obj.put("success", true);
			}catch(Exception e){
				obj.put("success", false);
				obj.put("errorMsg", e.getMessage());
			}
			return obj.toString();

	}
	
	@RequestMapping("/destroyWps")
	@ResponseBody
	public String destroyWps(@RequestParam BigInteger fid){

			JSONObject obj = new JSONObject();
			try{
				wpsService.delete(fid);
				wpsService.deleteHistory(fid);
				 obj.put("success", true);
			}catch(Exception e){
				obj.put("success", false);
				obj.put("errorMsg", e.getMessage());
			}
			return obj.toString();
	}
	
	@RequestMapping("/wpsvalidate")
	@ResponseBody
	private String wpsvalidate(@RequestParam String fwpsnum){
		boolean data = true;
		int count = wpsService.getUsernameCount(fwpsnum);
		if(count>0){
			data = false;
		}
		return data + "";
	}
	
	@RequestMapping("/selectwps")
	public String selectwps(HttpServletRequest request){
		return "weldwps/selectWps";
	}
	
	@RequestMapping("/selectmachine")
	public String selectmachine(HttpServletRequest request){
		 wpsfid = request.getParameter("fid");
		 wpspre = request.getParameter("pre");
		return "weldwps/selectMachine";
	}
	
	@RequestMapping("/giveWM")
	@ResponseBody
	public String giveWM(HttpServletRequest request){
		Wps wps = new Wps();
		Socket socket = null;
		MyUser myuser = (MyUser) SecurityContextHolder.getContext()  
			    .getAuthentication()  
			    .getPrincipal();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] wfid = wpsfid.split(",");
		String[] wpre = wpspre.split(",");
		String mid = request.getParameter("mid");
		String[] mmid = mid.split(",");
		JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject json1 = new JSONObject();
		JSONArray ary1 = new JSONArray();
		try{
			for(int i=0;i<wfid.length;i++){
				wps.setFid(Long.parseLong(wfid[i]));
		        wps.setFweld_prechannel(Integer.parseInt(wpre[i]));
				wps.setFcreater(myuser.getId());
				wps.setFupdater(myuser.getId());
				wps.setInsid(wpsService.findByUid(myuser.getId()));
				wps.setFcreatedate(sdf.parse(sdf.format((new Date()).getTime())));
				wps.setFupdatedate(sdf.parse(sdf.format((new Date()).getTime())));
				for(int j=0;j<mmid.length;j++){
					wps.setMacid(new BigInteger(mmid[j]));
					wpsService.give(wps);
				}
			}
			obj.put("success", true);
			
			try {    
                //if(socketChannel==null){
                  socketChannel = SocketChannel.open(); 
                    SocketAddress socketAddress = new InetSocketAddress(IP_ADDR, 5555);    
                     socketChannel.connect(socketAddress);
                //}
         			for(int ii=0;ii<wfid.length;ii++){
        				for(int jj=0;jj<mmid.length;jj++){
            				Wps w = wpsService.findById(new BigInteger(wfid[ii]));
            				int weld_i=w.getFweld_i();
            				int weld_v=w.getFweld_v()*10;
            				int weld_i_tx = (w.getFweld_i_max()-w.getFweld_i_min())/2;
            				int weld_v_tx = (w.getFweld_v_max()-w.getFweld_v_min())/2*10;
            				int pre = w.getFweld_prechannel();
            				String str1;
            				if(pre<16){
            					str1 = "7E7C205213010"+Integer.toHexString(pre);
            				}else{
            					str1 = "7E7C20521301"+Integer.toHexString(pre);
            				}
            				String gather_no = wpsService.findIpById(new BigInteger(mmid[jj]));
							//String str1 = "7E7C20521301";
							//01通道
							String str2;
							String str22;
							String str3;
							String str4;
							if(w.getFweld_i()<16){
							str2 = "7C201E7C200A7C20647C20BE7C207C207C20"+"0"+Integer.toHexString(weld_i);
							}else if(w.getFweld_i()>=16&&w.getFweld_i()<256){
							str2 = "7C201E7C200A7C20647C20BE7C207C207C20" + Integer.toHexString(weld_i);
							}else if(w.getFweld_i()==256){
							str2 = "7C201E7C200A7C20647C20BE7C207C2001" + "7C20";
							}else{
							str2 = "7C201E7C200A7C20647C20BE7C207C200" + Integer.toHexString(weld_i);
							}
							if(weld_v<16){
								str22="7C200"+Integer.toHexString(weld_v);
							}else if(weld_v>=16&&weld_v<256){
								str22="7C20"+Integer.toHexString(weld_v);
							}else if(weld_v==256){
								str22="01"+"7C20";
							}else{
								str22="0"+Integer.toHexString(weld_v);
							}
							if(weld_i_tx==0){
								str3 = "7C207C207C20647C20BE7C207C207C200A7C207C207C200A7C207C207C20"+"7C20"+"7C20";
							}else if(weld_i_tx>0&&weld_i_tx<16){
								str3 = "7C207C207C20647C20BE7C207C207C200A7C207C207C200A7C207C207C20"+"0"+Integer.toHexString(weld_i_tx)+"0"+Integer.toHexString(weld_i_tx);
							}else{
								str3 = "7C207C207C20647C20BE7C207C207C200A7C207C207C200A7C207C207C20"+Integer.toHexString(weld_i_tx)+Integer.toHexString(weld_i_tx);
							}
							if(weld_v_tx==0){
								str4="7C20"+"7C20"+"2C7D";
							}else if(weld_v_tx>0&&weld_v_tx<16){
								str4="0"+Integer.toHexString(weld_v_tx)+"0"+Integer.toHexString(weld_v_tx)+"2C7D";
							}else{
								str4=Integer.toHexString(weld_v_tx)+Integer.toHexString(weld_v_tx)+"2C7D";
							}
							//C8 //电流
							//012C//电压
//							String str3 = "7C207C207C20647C20BE7C207C207C200A7C207C207C200A7C207C207C20";
							//1E//微调电流
							//32//微调电压
//							String str4 = "2C7D";
							String test;
/*							if(weld_v<16){
								if(weld_v_tx<16){
									if(weld_i_tx<16){
										test =str1+Integer.toHexString(pre)+str2+Integer.toHexString(weld_i)+"7C200"+Integer.toHexString(weld_v)+str3+"0"+Integer.toHexString(weld_i_tx)+"0"+Integer.toHexString(weld_i_tx)+"0"+Integer.toHexString(weld_v_tx)+"0"+Integer.toHexString(weld_v_tx)+str4;
									}else if(weld_i_tx==0){
										test =str1+Integer.toHexString(pre)+str2+Integer.toHexString(weld_i)+"7C200"+Integer.toHexString(weld_v)+str3+"7C20"+"7C20"+"0"+Integer.toHexString(weld_v_tx)+"0"+Integer.toHexString(weld_v_tx)+str4;
									}
									else{
										test =str1+Integer.toHexString(pre)+str2+Integer.toHexString(weld_i)+"7C200"+Integer.toHexString(weld_v)+str3+Integer.toHexString(weld_i_tx)+Integer.toHexString(weld_i_tx)+Integer.toHexString(weld_v_tx)+Integer.toHexString(weld_v_tx)+str4;
									}
								}else if(weld_v_tx==0){
									if(weld_i_tx<16){
										test =str1+Integer.toHexString(pre)+str2+Integer.toHexString(weld_i)+"7C200"+Integer.toHexString(weld_v)+str3+"0"+Integer.toHexString(weld_i_tx)+"0"+Integer.toHexString(weld_i_tx)+"7C20"+"7C20"+str4;
									}else if(weld_i_tx==0){
										test =str1+Integer.toHexString(pre)+str2+Integer.toHexString(weld_i)+"7C200"+Integer.toHexString(weld_v)+str3+"7C20"+"7C20"+"7C20"+"7C20"+str4;
									}
									else{
										test =str1+Integer.toHexString(pre)+str2+Integer.toHexString(weld_i)+"7C200"+Integer.toHexString(weld_v)+str3+Integer.toHexString(weld_i_tx)+Integer.toHexString(weld_i_tx)+Integer.toHexString(weld_v_tx)+Integer.toHexString(weld_v_tx)+str4;
									}
								}else{
									if(weld_i_tx<16){
										test =str1+Integer.toHexString(pre)+str2+Integer.toHexString(weld_i)+"7C200"+Integer.toHexString(weld_v)+str3+"0"+Integer.toHexString(weld_i_tx)+"0"+Integer.toHexString(weld_i_tx)+Integer.toHexString(weld_v_tx)+Integer.toHexString(weld_v_tx)+str4;
									}else if(weld_i_tx==0){
										test =str1+Integer.toHexString(pre)+str2+Integer.toHexString(weld_i)+"7C200"+Integer.toHexString(weld_v)+str3+"7C20"+"7C20"+Integer.toHexString(weld_i_tx)+Integer.toHexString(weld_v_tx)+Integer.toHexString(weld_v_tx)+str4;
									}else{
										test =str1+Integer.toHexString(pre)+str2+Integer.toHexString(weld_i)+"7C200"+Integer.toHexString(weld_v)+str3+Integer.toHexString(weld_i_tx)+Integer.toHexString(weld_i_tx)+Integer.toHexString(weld_v_tx)+Integer.toHexString(weld_v_tx)+str4;
									}
									}
							}else{
								if(weld_v_tx<256){
								test =str1+Integer.toHexString(pre)+str2+Integer.toHexString(weld_i)+"0"+Integer.toHexString(weld_v)+str3+Integer.toHexString(weld_i_tx)+Integer.toHexString(weld_i_tx)+Integer.toHexString(weld_v_tx)+Integer.toHexString(weld_v_tx)+str4;
								}else if(weld_v_tx>=256){
									test =str1+Integer.toHexString(pre)+str2+Integer.toHexString(weld_i)+"0"+Integer.toHexString(weld_v)+str3+Integer.toHexString(weld_i_tx)+Integer.toHexString(weld_i_tx)+Integer.toHexString(weld_v_tx)+Integer.toHexString(weld_v_tx)+str4;
								}else{
									test =str1+Integer.toHexString(pre)+str2+Integer.toHexString(weld_i)+"0"+Integer.toHexString(weld_v)+str3+Integer.toHexString(weld_i_tx)+Integer.toHexString(weld_i_tx)+Integer.toHexString(weld_v_tx)+Integer.toHexString(weld_v_tx)+str4;
								}
								}*/
							test=str1+str2+str22+str3+str4;
							String ch=test.substring(2,test.length()-4);
							String strdata2=ch.replaceAll("7C20", "00");
		                    String strdata3=strdata2.replaceAll("7C5E", "7E");
		                    String strdata4=strdata3.replaceAll("7C5C", "7C");
		                    String strdata =strdata4.replaceAll("7C5D", "7D");
		                    int check = 0;
		                    byte[] data1=new byte[strdata.length()/2];
							for (int i = 0; i < data1.length; i++)
							{
								String tstr1=strdata.substring(i*2, i*2+2);
								Integer k=Integer.valueOf(tstr1, 16);
								check += k;
							}
	       
							String checksend = Integer.toHexString(check);
							checksend = checksend.substring(1,3);
							checksend = checksend.toUpperCase();
							test=ch+checksend;

		                    strdata="7E"+test+"7D";
		                    strdata=strdata.toUpperCase();
//		                    strdata="7E7C20521301027C201E7C200A7C20647C20BE7C207C207C20FA7C20FA7C207C207C20647C20BE7C207C207C200A7C207C207C200A7C207C207C201E327C207C202C7D";
//		                    strdata="7E7C20521301027C201E7C200A7C20647C20BE7C207C207C20FA7C20FA7C207C207C20647C20BE7C207C207C200A7C207C207C200A7C207C207C201E327C207C202C7D";
							SendAndReceiveUtil.sendData(socketChannel, strdata);
					         Robot  r   =   new   Robot();
					         r.delay(500);
        				}
        			}

                  /*String msg = SendAndReceiveUtil.receiveData(socketChannel);    
                  if(msg != null) 
                    System.out.println(msg);*/

              
              } catch (Exception ex) {    
                  ex.printStackTrace();  
              } /*finally {    
                  try {            
                      socketChannel.close();    
                  } catch(Exception ex) {  
                      ex.printStackTrace();  
                  }    
              }*/

			/*socket = new Socket(IP_ADDR, PORT);
			String gy = ary.toString();
			String hj = ary1.toString();
			byte[] mainwps = gy.getBytes();
			byte[] machine = hj.getBytes();

			InputStream input = socket.getInputStream();    
            //向服务器端发送数据    
            OutputStream out = socket.getOutputStream();   
            String test = "7E0156136A7D";
            byte[] datas1  = new byte[2048];
            byte[] te = new byte[test.length()/2];
            for (int i = 0; i < test.length()/2; i++)
            {
              String tstr1=test.substring(i*2, i*2+2);
              Integer k=Integer.valueOf(tstr1,16);
              te[i]=(byte)k.byteValue();
            }
            out.write(te);*/
            
            /*7E015213017C207C201E7C200A7C20647C20BE7C
			207C207C20647C20BE7C207C207C20647C20BE7C
			207C207C200A7C207C207C200A7C207C207C201E
			327C207C20597D*/            
            
            
/*            out.write(mainwps);
            out.write(machine);*/
            
/*            socket.getInputStream().read(datas1);
            String str = "";
            int linecount=0;
            int zerocount=0;
            for(int i1=0;i1<datas1.length;i1++){
              //判断为数字还是字母，若为字母+256取正数
              if(datas1[i1]<0){
                String r = Integer.toHexString(datas1[i1]+256);
                String rr=r.toUpperCase();
                  //数字补为两位数
                  if(rr.length()==1){
                  rr='0'+rr;
                  }
                  //strdata为总接收数据
                str += rr;
              }
              else{
                String r = Integer.toHexString(datas1[i1]);

                  if(r.length()==1)
                  r='0'+r;
                  r=r.toUpperCase();
                str+=r;  
              }
              linecount+=2;
              //去掉后面的0
              if(datas1[i1]==0){
                zerocount++;
                if(zerocount>25){
                  str=str.substring(0, linecount-52);
                  break;
                }  
              }else{
                zerocount=0;
              }
            }*/
		}catch(Exception e){
			e.printStackTrace();
			obj.put("success", false);
			obj.put("errorMsg", e.getMessage());
		}
		return obj.toString();
/*		return "redirect:/user/AllUser";*/
	}
	
	@RequestMapping("/findHistory")
	@ResponseBody
	public String findHistory(HttpServletRequest request){
		pageIndex = Integer.parseInt(request.getParameter("page"));
		pageSize = Integer.parseInt(request.getParameter("rows"));
		String parentId = request.getParameter("parent");
		BigInteger parent = null;
		if(iutil.isNull(parentId)){
			parent = new BigInteger(parentId);
		}
		page = new Page(pageIndex,pageSize,total);
		JSONObject json = new JSONObject();
		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		page = new Page(pageIndex,pageSize,total);
		List<Wps> findHistory = wpsService.findHistory(page,parent);
		long total = 0;	
		if(findHistory != null){
			PageInfo<Wps> pageinfo = new PageInfo<Wps>(findHistory);
			total = pageinfo.getTotal();
		}
		try{
			for(Wps wps:findHistory){
				json.put("FWPSNum", wps.getFwpsnum());
				json.put("Fweld_PreChannel", wps.getFweld_prechannel());
				json.put("FCReateDate",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(wps.getFcreatedate()));
				json.put("Fname", wps.getFname());
				ary.add(json);
			}
		}catch(Exception e){
			e.getMessage();
		}
		obj.put("total", total);
		obj.put("rows", ary);
		return obj.toString();
/*		return "redirect:/user/AllUser";*/
	}
}