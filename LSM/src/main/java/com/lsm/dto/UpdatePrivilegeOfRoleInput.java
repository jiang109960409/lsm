package com.lsm.dto;

public class UpdatePrivilegeOfRoleInput {

	private String checkedIds;
	private String unCheckedIds;

	public String getCheckedIds() {
		return checkedIds;
	}

	public void setCheckedIds(String checkedIds) {
		this.checkedIds = checkedIds;
	}

	public String getUnCheckedIds() {
		return unCheckedIds;
	}

	public void setUnCheckedIds(String unCheckedIds) {
		this.unCheckedIds = unCheckedIds;
	}

}
