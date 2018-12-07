package com.lsm.domain;

import java.util.Date;

public class RoleDO {
	// 自增主键
	private long id;
	// 角色名称
	private String name;
	// 角色说明
	private String description;
	// 是否可用
	private int isEnabled;
	// 是否内置
	private int isBuildin;
	// 创建日期
	private Date createDate;
	// 创建者
	private long creatorId;
	// 修改日期
	private Date modifyDate;
	// 修改者
	private long modifierId;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(int isEnabled) {
		this.isEnabled = isEnabled;
	}

	public int getIsBuildin() {
		return this.isBuildin;
	}

	public void setIsBuildin(int isBuildin) {
		this.isBuildin = isBuildin;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public long getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public long getModifierId() {
		return this.modifierId;
	}

	public void setModifierId(long modifierId) {
		this.modifierId = modifierId;
	}
}
