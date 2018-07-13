package com.spring.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.model.Wps;
import com.spring.page.Page;

public interface WpsMapper {
	List<Wps> findAll(@Param("parent")BigInteger parent,@Param("str")String str);
	List<Wps> findHistory(@Param("parent")BigInteger parent);
	void save(Wps wps);
	void give(Wps wps);
	BigInteger findByUid(long uid);
	void update(Wps wps);
	int getUsernameCount(@Param("fwpsnum")String fwpsnum);
	Wps findById(BigInteger fid);
	void delete(BigInteger fid);
	String findIpById(BigInteger fid);
	void deleteHistory(BigInteger fid);
}
