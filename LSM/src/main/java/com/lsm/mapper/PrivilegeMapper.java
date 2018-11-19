package com.lsm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PrivilegeMapper {

	@Select("selct role_id from rbac_role_privilege where privilege_id = #{arg1}")
	List<Long> listRole(Long privilegeId);
}
