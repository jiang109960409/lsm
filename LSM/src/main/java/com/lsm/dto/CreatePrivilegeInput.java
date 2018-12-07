package com.lsm.dto;

import javax.validation.constraints.NotNull;

public class CreatePrivilegeInput {

	private int privilegeCategoryId;
	private String name;
	private String description;

	private long pid;
	private long creator;

	public int getPrivilegeCategoryId() {
		return privilegeCategoryId;
	}

	public void setPrivilegeCategoryId(int privilegeCategoryId) {
		this.privilegeCategoryId = privilegeCategoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public long getCreator() {
		return creator;
	}

	public void setCreator(long creator) {
		this.creator = creator;
	}

}
