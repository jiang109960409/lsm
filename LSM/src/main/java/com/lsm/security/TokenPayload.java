package com.lsm.security;

import java.util.List;

public class TokenPayload {

	private long id;
	private String name;
	private List<Integer> roles;
	
	public TokenPayload() {
	}

	public TokenPayload(long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public TokenPayload(long id, String name, List<Integer> roles) {
		this.id = id;
		this.name = name;
		this.roles = roles;
	}

	public List<Integer> getRoles() {
		return roles;
	}

	public void setRoles(List<Integer> roles) {
		this.roles = roles;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
