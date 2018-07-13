package com.spring.model;

import java.math.BigInteger;
/**
 * 设备厂商
 * @author gpyf16
 *
 */
public class EquipmentManufacturer {
	private BigInteger id;
	private String name;
	private String type;
	private String typeValue;
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeValue() {
		return typeValue;
	}
	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}
	
	
}
