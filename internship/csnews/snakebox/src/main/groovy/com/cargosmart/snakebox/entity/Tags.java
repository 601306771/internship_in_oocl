package com.cargosmart.snakebox.entity;

public class Tags {
	private String key;
	private long id;
	private String value;
	private String position;
	
	public Tags(){}
	
	@Override
	public String toString() {
		return "Tags [key=" + key + ", id=" + id + ", value=" + value
				+ ", position=" + position + "]";
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	
}
