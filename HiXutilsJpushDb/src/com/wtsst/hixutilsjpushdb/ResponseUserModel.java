package com.wtsst.hixutilsjpushdb;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
@Table(name = "responseusermodel")
public class ResponseUserModel {
	@Column(name = "id", isId = true, autoGen = true)
	private int id;
	@Column(name="name")
	private String name;
	@Column(name = "url")
	private String url;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
