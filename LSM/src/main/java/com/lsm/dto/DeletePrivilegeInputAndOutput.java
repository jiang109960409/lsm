package com.lsm.dto;

public class DeletePrivilegeInputAndOutput {

	private long id;
	private int result;

	public DeletePrivilegeInputAndOutput() {
		super();
	}

	public DeletePrivilegeInputAndOutput(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

}
