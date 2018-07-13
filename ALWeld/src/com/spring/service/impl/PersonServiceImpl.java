package com.spring.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.spring.dao.PersonMapper;
import com.spring.dao.UserMapper;
import com.spring.dao.WelderMapper;
import com.spring.model.Person;
import com.spring.model.User;
import com.spring.page.Page;
import com.spring.service.PersonService;

@Service
@Transactional
public class PersonServiceImpl implements PersonService{

	@Resource
	private PersonMapper mapper;
	
	@Override
	public List<Person> findAll(Page page, BigInteger parent, String str) {
		PageHelper.startPage(page.getPageIndex(), page.getPageSize());
		List<Person> findAllList = mapper.findAll(parent,str);
		return findAllList;
	}

	@Override
	public List<Person> findLeve(int type) {
		// TODO Auto-generated method stub
		return mapper.findLeve(type);
	}

	@Override
	public void save(Person welder) {
		// TODO Auto-generated method stub
		mapper.save(welder);
	}

	@Override
	public Person findById(BigInteger fid) {
		// TODO Auto-generated method stub
		return mapper.findById(fid);
	}

	@Override
	public void update(Person welder) {
		// TODO Auto-generated method stub
		mapper.update(welder);
	}

	@Override
	public void delete(BigInteger fid) {
		// TODO Auto-generated method stub
		mapper.delete(fid);
	}

	@Override
	public List<Person> dic() {
		// TODO Auto-generated method stub
		return mapper.dic();
	}

	@Override
	public List<Person> ins() {
		// TODO Auto-generated method stub
		return mapper.ins();
	}

	@Override
	public int getUsernameCount(String welderno) {
		// TODO Auto-generated method stub
		return mapper.getUsernameCount(welderno);
	}

}