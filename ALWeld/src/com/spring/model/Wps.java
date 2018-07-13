package com.spring.model;

import java.math.BigInteger;
import java.util.Date;


public class Wps {
	private long fid;
	private BigInteger insid;
	private BigInteger macid;
	private String insname;
	private BigInteger welderid;
	private String weldername;
	private String updatename;
	private String fwpsnum;
	private int fweld_i;
	private int fweld_v;
	private int fweld_i_max;
	private int fweld_i_min;
	private int fweld_v_max;
	private int fweld_v_min;
	private int fweld_alter_i;
	private int fweld_alter_v;
	private int fweld_prechannel;
	private Date fcreatedate;
	private Date fupdatedate;
	private long fcreater;
	private long fupdater;
	private long fowner;
	private String fback;
	private String fname;
	private double fdiameter;
	public Wps(){
		super();
	}
	public long getFid() {
		return fid;
	}
	public void setFid(long fid) {
		this.fid = fid;
	}
	public BigInteger getWelderid() {
		return welderid;
	}
	public void setWelderid(BigInteger welderid) {
		this.welderid = welderid;
	}
	public BigInteger getMacid() {
		return macid;
	}
	public void setMacid(BigInteger macid) {
		this.macid = macid;
	}
	public BigInteger getInsid() {
		return insid;
	}
	public void setInsid(BigInteger insid) {
		this.insid = insid;
	}
	public String getWeldername(){
		return weldername;
	}
	public void setWeldername(String weldername){
		this.weldername = weldername;
	}
	public String getUpdatename(){
		return updatename;
	}
	public void setUpdatename(String updatename){
		this.updatename = updatename;
	}
	public String getInsname(){
		return insname;
	}
	public void setInsname(String insname){
		this.insname = insname;
	}
	public String getFwpsnum(){
		return fwpsnum;
	}
	public void setFwpsnum(String fwpsnum){
		this.fwpsnum = fwpsnum;
	}
	public int getFweld_i() {
		return fweld_i;
	}
	public void setFweld_i(int fweld_i) {
		this.fweld_i = fweld_i;
	}
	public int getFweld_v() {
		return fweld_v;
	}
	public void setFweld_v(int fweld_v) {
		this.fweld_v = fweld_v;
	}
	public int getFweld_i_max() {
		return fweld_i_max;
	}
	public void setFweld_i_max(int fweld_i_max) {
		this.fweld_i_max = fweld_i_max;
	}
	public int getFweld_i_min() {
		return fweld_i_min;
	}
	public void setFweld_i_min(int fweld_i_min) {
		this.fweld_i_min = fweld_i_min;
	}
	public int getFweld_v_max() {
		return fweld_v_max;
	}
	public void setFweld_v_max(int fweld_v_max) {
		this.fweld_v_max = fweld_v_max;
	}
	public int getFweld_v_min() {
		return fweld_v_min;
	}
	public void setFweld_v_min(int fweld_v_min) {
		this.fweld_v_min = fweld_v_min;
	}
	public int getFweld_alter_i() {
		return fweld_alter_i;
	}
	public void setFweld_alter_i(int fweld_alter_i) {
		this.fweld_alter_i = fweld_alter_i;
	}
	public int getFweld_alter_v() {
		return fweld_alter_v;
	}
	public void setFweld_alter_v(int fweld_alter_v) {
		this.fweld_alter_v = fweld_alter_v;
	}
	public int getFweld_prechannel() {
		return fweld_prechannel;
	}
	public void setFweld_prechannel(int fweld_prechannel) {
		this.fweld_prechannel = fweld_prechannel;
	}
	public Date getFcreatedate(){
		return fcreatedate;
	}
	public void setFcreatedate(Date fcreatedate){
		this.fcreatedate = fcreatedate;
	}
	public Date getFupdatedate(){
		return fupdatedate;
	}
	public void setFupdatedate(Date fupdatedate){
		this.fupdatedate = fupdatedate;
	}
	public long getFcreater() {
		return fcreater;
	}
	public void setFcreater(long fcreater) {
		this.fcreater = fcreater;
	}
	public long getFupdater() {
		return fupdater;
	}
	public void setFupdater(long fupdater) {
		this.fupdater = fupdater;
	}
	public long getFowner() {
		return fowner;
	}
	public void setFowner(long fowner) {
		this.fowner = fowner;
	}
	public String getFback(){
		return fback;
	}
	public void setFback(String fback){
		this.fback = fback;
	}
	public String getFname(){
		return fname;
	}
	public void setFname(String fname){
		this.fname = fname;
	}
	public double getFdiameter(){
		return fdiameter;
	}
	public void setFdiameter(double fdiameter){
		this.fdiameter = fdiameter;
	}
	public Wps(long fid,BigInteger insid,BigInteger macid,String insname,BigInteger welderid,String weldername,String updatename,String fwpsnum,int fweld_i,int fweld_v,int fweld_i_max,int fweld_i_min,int fweld_v_max,int fweld_v_min,int fweld_alter_i,int fweld_alter_v,int fweld_prechannel,Date fcreatedate,Date fupdatedate,long fcreater,long fupdater,long fowner,String fback,String fname,double fdiameter) {
		super();
		this.macid = macid;
		this.insname = insname;
		this.weldername = weldername;
		this.updatename = updatename;
		this.insid = insid;
		this.welderid = welderid;
		this.fid = fid;
		this.fwpsnum = fwpsnum;
		this.fweld_i = fweld_i;
		this.fweld_v = fweld_v;
		this.fweld_i_max = fweld_i_max;
		this.fweld_i_min = fweld_i_min;
		this.fweld_v_max = fweld_v_max;
		this.fweld_v_min = fweld_v_min;
		this.fweld_alter_i = fweld_alter_i;
		this.fweld_alter_v = fweld_alter_v;
		this.fweld_prechannel = fweld_prechannel;
		this.fcreatedate = fcreatedate;
		this.fupdatedate = fupdatedate;
		this.fcreater = fcreater;
		this.fupdater = fupdater;
		this.fowner = fowner;
		this.fback = fback;
		this.fname = fname;
		this.fdiameter = fdiameter;
	}
}
