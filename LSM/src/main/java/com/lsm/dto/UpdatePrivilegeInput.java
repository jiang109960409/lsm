package com.lsm.dto;

import javax.validation.constraints.NotNull;

public class UpdatePrivilegeInput {

	@NotNull(message="id不能为空")
	private int id;
	@NotNull(message="权限类别不能为空")
	private int privilegeCategoryId;
	@NotNull(message="权限名称不能为空")
	private String name;
	private String description;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
