package com.lsm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.lsm.domain.UserDO;

@Mapper
public interface AppMapper {
	
	@Results(id = "user", value= {
			@Result(column="job_number", property="jobNumber"),
			@Result(column="role_ids", property="roleIds"),
			@Result(column="create_date", property="createDate")
	})
	@Select("select * from org_user where account=#{account} and password=#{password}")
	UserDO getUser(@Param("account") String account, @Param("password") String password);
	
}
