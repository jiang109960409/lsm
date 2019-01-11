package com.lsm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import com.lsm.domain.PrivilegeDO;
import com.lsm.domain.RoleDO;
import com.lsm.domain.RolePrivilegeDO;
import com.lsm.dto.CreatePrivilegeInput;
import com.lsm.dto.DeletePrivilegeInputAndOutput;
import com.lsm.dto.UpdatePrivilegeInput;

@Mapper
public interface PrivilegeMapper {

	@Select("selct role_id from rbac_role_privilege where privilege_id = #{arg1}")
	List<Long> listRole(Long privilegeId);

	@Results(id = "privilegeDO", value = { @Result(column = "privilege_category_id", property = "privilegeCategoryId"),
			@Result(column = "create_date", property = "createDate"),
			@Result(column = "creator_id", property = "creatorId"),
			@Result(column = "modify_date", property = "modifyDate"),
			@Result(column = "modifier_id", property = "modifierId") })
	@Select("call p_add_privilege_child(#{pid}, #{privilegeCategoryId}, #{name}, #{description}, #{creator})")
	List<PrivilegeDO> add(CreatePrivilegeInput input);

	@ResultMap("privilegeDO")
	@Select("select * from rbac_privilege where pid = 0 order by id asc")
	List<PrivilegeDO> listRootPrivilege();

	@ResultMap("privilegeDO")
	@Select("select * from rbac_privilege where pid=#{arg1} order by id asc")
	List<PrivilegeDO> listChildPrivilege(long pid);

	@Select("select * from rbac_privilege where (rgt - lft) = (select max(rgt - lft) from rbac_privilege where pid = 0  and privilege_category_id = #{arg1})  and privilege_category_id = #{arg1} limit 1")
	PrivilegeDO getPrivilegeWithMaximumChildNodes(int privilegeCategoryId);

	@Select("select ceiling(log(count(*))) from rbac_privilege where lft <= #{lft} and rgt <= #{rgt}")
	int getPrivilegeHeight(@Param("lft") long lft, @Param("rgt") long rgt);

	@Update("update rbac_privilege set name = #{name}, privilege_category_id=#{privilegeCategoryId}, description=#{description} where id = #{id}")
	int updatePrivilege(UpdatePrivilegeInput input);

	@Select("call p_del_privilege(#{id, jdbcType=BIGINT, mode=IN}," + " #{result, jdbcType=INTEGER, mode=OUT})")
	@Options(statementType = StatementType.CALLABLE)
	void deletePrivilege(DeletePrivilegeInputAndOutput iao);

	@Results(id = "role", value = { @Result(column = "is_enabled", property = "isEnabled"),
			@Result(column = "is_buildin", property = "isBuildin"),
			@Result(column = "create_date", property = "createDate"),
			@Result(column = "creator_id", property = "creatorId"),
			@Result(column = "modify_date", property = "modifyDate"),
			@Result(column = "modifier_id", property = "modifierId") })
	@Select("select * from rbac_role order by id asc")
	List<RoleDO> listRoles();

	@Select("select distinct(privilege_id) from rbac_role_privilege where role_id = #{arg1}")
	List<Long> listRolePrivilegeId(long roleId);

	@Insert("<script>" + "insert into rbac_role_privilege(role_id, privilege_id) values"
			+ "<foreach collection='list' item='privilegeId' separator=','>" + "(#{roleId}, #{privilegeId})"
			+ "</foreach></script>")
	Integer addRolePrivilege(@Param("roleId") long roleId, @Param("list") List<String> privilegeIds);

	@Delete("<script>" + "delete from rbac_role_privilege where role_id=#{roleId} and privilege_id in"
			+ "<foreach collection='list' item='privilegeId' open='(' close=')' separator=','>" + "#{privilegeId}"
			+ "</foreach></script>")
	Integer deleteRolePrivilege(@Param("roleId") long roleId, @Param("list") List<String> privilegeIds);
}
