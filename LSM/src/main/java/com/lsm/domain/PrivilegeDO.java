package com.lsm.domain;

import java.io.Serializable;
import java.util.Date;

public class PrivilegeDO implements Serializable {

	private static final long serialVersionUID = -3655284579686526389L;

	// 自增主键
	private Long id;
	// 父权限主键
	private Long pid;
	// 权限分类代码
	private Long privilegeCategoryId;
	// 权限名称
	private String name;
	// 左值
	private Long lft;
	// 右值
	private Long rgt;
	// 权限描述
	private String description;
	// 创建日期
	private Date createDate;
	// 创建者
	private Long creatorId;
	// 修改日期
	private Date modifyDate;
	// 修改者
	private Long modifierId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getPrivilegeCategoryId() {
		return privilegeCategoryId;
	}

	public void setPrivilegeCategoryId(Long privilegeCategoryId) {
		this.privilegeCategoryId = privilegeCategoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getLft() {
		return lft;
	}

	public void setLft(Long lft) {
		this.lft = lft;
	}

	public Long getRgt() {
		return rgt;
	}

	public void setRgt(Long rgt) {
		this.rgt = rgt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Long getModifierId() {
		return modifierId;
	}

	public void setModifierId(Long modifierId) {
		this.modifierId = modifierId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
