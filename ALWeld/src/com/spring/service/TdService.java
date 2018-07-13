package com.spring.service;

import java.math.BigInteger;
import java.util.List;

import com.spring.page.Page;
import com.spring.model.Td;

public interface TdService {

	List<Td> findAll(Page page, String str);
	List<Td> findAllpro(long ins);
	List<Td> findAllcom();
	List<Td> findAlldiv(long ins);
	List<Td> getAllPosition();
	String findweld(String weldid);
	String findInsname(long uid);
	long findInsid(String insname);
	long findIns(long uid);
	String findPosition(String equip);
	List<Td> allWeldname();
	List<Td> getAllMachine(String ins);
}