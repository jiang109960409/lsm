package com.lsm.service;

import java.util.List;

import com.lsm.domain.PrivilegeDO;
import com.lsm.domain.RoleDO;
import com.lsm.dto.CreatePrivilegeInput;
import com.lsm.dto.DeletePrivilegeInputAndOutput;
import com.lsm.dto.UpdatePrivilegeInput;

public interface PrivilegeService {
	
	List<PrivilegeDO> add(CreatePrivilegeInput input);
	
	String listPrivilegeJSON();
	
	String updatePrivilege(UpdatePrivilegeInput input);
	
	String deletePrivilege(DeletePrivilegeInputAndOutput iao);
	
	List<RoleDO> listRole();
}
