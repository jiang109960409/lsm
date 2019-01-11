package com.lsm.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lsm.domain.PrivilegeDO;
import com.lsm.domain.RoleDO;
import com.lsm.dto.CreatePrivilegeInput;
import com.lsm.dto.DeletePrivilegeInputAndOutput;
import com.lsm.dto.UpdatePrivilegeInput;
import com.lsm.dto.UpdatePrivilegeOfRoleInput;
import com.lsm.mapper.PrivilegeMapper;
import com.lsm.service.PrivilegeService;
import com.mysql.jdbc.StringUtils;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

	@Autowired
	private PrivilegeMapper privilegeMapper;

	@Override
	public List<PrivilegeDO> add(CreatePrivilegeInput input) {
		return privilegeMapper.add(input);
	}

	@Override
	public String listPrivilegeJSON() {
		List<PrivilegeDO> rootPrivileges = privilegeMapper.listRootPrivilege();
		ObjectMapper om = new ObjectMapper();
		ArrayNode anRoot = om.createArrayNode();
		if (!rootPrivileges.isEmpty()) {
			for (PrivilegeDO pRoot : rootPrivileges) {// 根节点
				ObjectNode onRoot = om.createObjectNode();
				onRoot.put("id", pRoot.getId());
				onRoot.put("categoryId", pRoot.getPrivilegeCategoryId());
				onRoot.put("text", pRoot.getName());
				onRoot.put("description", pRoot.getDescription());
				onRoot.put("icon", "glyphicon");
				onRoot.put("selectedIcon", "glyphicon");
				onRoot.put("selectedBackColor", "#FFFFFF");
				List<PrivilegeDO> privileges = privilegeMapper.listChildPrivilege(pRoot.getId());
				if (!privileges.isEmpty()) {
					ArrayNode an = om.createArrayNode();
					for (PrivilegeDO p : privileges) { // 第二层
						ObjectNode on = om.createObjectNode();
						on.put("id", p.getId());
						on.put("categoryId", p.getPrivilegeCategoryId());
						on.put("text", p.getName());
						on.put("description", p.getDescription());
						on.put("icon", "glyphicon");
						on.put("selectedIcon", "glyphicon");
						on.put("selectedBackColor", "#FFFFFF");
						List<PrivilegeDO> privileges2 = privilegeMapper.listChildPrivilege(p.getId());
						if (!privileges2.isEmpty()) {
							ArrayNode an2 = om.createArrayNode();
							for (PrivilegeDO p2 : privileges2) { // 第三层
								ObjectNode on2 = om.createObjectNode();
								on2.put("id", p2.getId());
								on2.put("categoryId", p2.getPrivilegeCategoryId());
								on2.put("text", p2.getName());
								on2.put("description", p2.getDescription());
								on2.put("icon", "glyphicon");
								on2.put("selectedIcon", "glyphicon");
								on2.put("selectedBackColor", "#FFFFFF");
								List<PrivilegeDO> privileges3 = privilegeMapper.listChildPrivilege(p2.getId());
								if (!privileges3.isEmpty()) {
									ArrayNode an3 = om.createArrayNode();
									for (PrivilegeDO p3 : privileges3) { // 第四层
										ObjectNode on3 = om.createObjectNode();
										on3.put("id", p3.getId());
										on3.put("categoryId", p3.getPrivilegeCategoryId());
										on3.put("text", p3.getName());
										on3.put("description", p3.getDescription());
										on3.put("icon", "glyphicon");
										on3.put("selectedIcon", "glyphicon");
										on3.put("selectedBackColor", "#FFFFFF");
										List<PrivilegeDO> privileges4 = privilegeMapper.listChildPrivilege(p3.getId());
										if (!privileges4.isEmpty()) {
											ArrayNode an4 = om.createArrayNode();
											for (PrivilegeDO p4 : privileges4) {
												ObjectNode on4 = om.createObjectNode();
												on4.put("id", p4.getId());
												on4.put("categoryId", p4.getPrivilegeCategoryId());
												on4.put("text", p4.getName());
												on4.put("description", p4.getDescription());
												on4.put("icon", "glyphicon");
												on4.put("selectedIcon", "glyphicon");
												on4.put("selectedBackColor", "#FFFFFF");
												an4.add(on4);
											}
											on3.set("nodes", an4);
										}
										an3.add(on3);
									}
									on2.set("nodes", an3);
								}
								an2.add(on2);
							}
							on.set("nodes", an2);
						}
						an.add(on);
					}
					onRoot.set("nodes", an);
				}
				anRoot.add(onRoot);
			}
		}
		String result = null;
		try {
			result = om.writeValueAsString(anRoot);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String updatePrivilege(UpdatePrivilegeInput input) {
		int result = privilegeMapper.updatePrivilege(input);
		if (result > 0)
			return listPrivilegeJSON();
		else
			return "";
	}

	@Override
	public String deletePrivilege(DeletePrivilegeInputAndOutput iao) {
		privilegeMapper.deletePrivilege(iao);
		if (iao.getResult() > 0)
			return listPrivilegeJSON();
		return "";
	}

	@Override
	public List<RoleDO> listRole() {
		return privilegeMapper.listRoles();
	}

	@Override
	public String listPrivilegeOfRoleJSON(long roleId) {
		List<Long> rolePrivilegeIds = privilegeMapper.listRolePrivilegeId(roleId);

		List<PrivilegeDO> rootPrivileges = privilegeMapper.listRootPrivilege();
		ObjectMapper om = new ObjectMapper();
		ArrayNode anRoot = om.createArrayNode();
		if (!rootPrivileges.isEmpty()) {
			for (PrivilegeDO pRoot : rootPrivileges) {// 根节点
				ObjectNode onRoot = om.createObjectNode();
				onRoot.put("id", pRoot.getId());
				onRoot.put("categoryId", pRoot.getPrivilegeCategoryId());
				onRoot.put("text", pRoot.getName());
				onRoot.put("description", pRoot.getDescription());
				onRoot.put("icon", "glyphicon");
				onRoot.put("selectedIcon", "glyphicon");
				onRoot.put("selectedBackColor", "#FFFFFF");
				Map<String, Boolean> stateRootMap = new HashMap<>();
				stateRootMap.put("checked", rolePrivilegeIds.contains(pRoot.getId()));
				onRoot.set("state", om.valueToTree(stateRootMap));
				List<PrivilegeDO> privileges = privilegeMapper.listChildPrivilege(pRoot.getId());
				if (!privileges.isEmpty()) {
					ArrayNode an = om.createArrayNode();
					for (PrivilegeDO p : privileges) { // 第二层
						ObjectNode on = om.createObjectNode();
						on.put("id", p.getId());
						on.put("categoryId", p.getPrivilegeCategoryId());
						on.put("text", p.getName());
						on.put("description", p.getDescription());
						on.put("icon", "glyphicon");
						on.put("selectedIcon", "glyphicon");
						on.put("selectedBackColor", "#FFFFFF");
						Map<String, Boolean> stateMap = new HashMap<>();
						stateMap.put("checked", rolePrivilegeIds.contains(p.getId()));
						on.set("state", om.valueToTree(stateMap));
						List<PrivilegeDO> privileges2 = privilegeMapper.listChildPrivilege(p.getId());
						if (!privileges2.isEmpty()) {
							ArrayNode an2 = om.createArrayNode();
							for (PrivilegeDO p2 : privileges2) { // 第三层
								ObjectNode on2 = om.createObjectNode();
								on2.put("id", p2.getId());
								on2.put("categoryId", p2.getPrivilegeCategoryId());
								on2.put("text", p2.getName());
								on2.put("description", p2.getDescription());
								on2.put("icon", "glyphicon");
								on2.put("selectedIcon", "glyphicon");
								on2.put("selectedBackColor", "#FFFFFF");
								Map<String, Boolean> state2Map = new HashMap<>();
								state2Map.put("checked", rolePrivilegeIds.contains(p2.getId()));
								on2.set("state", om.valueToTree(state2Map));
								List<PrivilegeDO> privileges3 = privilegeMapper.listChildPrivilege(p2.getId());
								if (!privileges3.isEmpty()) {
									ArrayNode an3 = om.createArrayNode();
									for (PrivilegeDO p3 : privileges3) { // 第四层
										ObjectNode on3 = om.createObjectNode();
										on3.put("id", p3.getId());
										on3.put("categoryId", p3.getPrivilegeCategoryId());
										on3.put("text", p3.getName());
										on3.put("description", p3.getDescription());
										on3.put("icon", "glyphicon");
										on3.put("selectedIcon", "glyphicon");
										on3.put("selectedBackColor", "#FFFFFF");
										Map<String, Boolean> state3Map = new HashMap<>();
										state3Map.put("checked", rolePrivilegeIds.contains(p3.getId()));
										on3.set("state", om.valueToTree(state3Map));
										List<PrivilegeDO> privileges4 = privilegeMapper.listChildPrivilege(p3.getId());
										if (!privileges4.isEmpty()) {
											ArrayNode an4 = om.createArrayNode();
											for (PrivilegeDO p4 : privileges4) {
												ObjectNode on4 = om.createObjectNode();
												on4.put("id", p4.getId());
												on4.put("categoryId", p4.getPrivilegeCategoryId());
												on4.put("text", p4.getName());
												on4.put("description", p4.getDescription());
												on4.put("icon", "glyphicon");
												on4.put("selectedIcon", "glyphicon");
												on4.put("selectedBackColor", "#FFFFFF");
												Map<String, Boolean> state4Map = new HashMap<>();
												state4Map.put("checked", rolePrivilegeIds.contains(p4.getId()));
												on4.set("state", om.valueToTree(state4Map));
												an4.add(on4);
											}
											on3.set("nodes", an4);
										}
										an3.add(on3);
									}
									on2.set("nodes", an3);
								}
								an2.add(on2);
							}
							on.set("nodes", an2);
						}
						an.add(on);
					}
					onRoot.set("nodes", an);
				}
				anRoot.add(onRoot);
			}
		}
		String result = null;
		try {
			result = om.writeValueAsString(anRoot);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String updatePrivilegeOfRole(Long roleId, UpdatePrivilegeOfRoleInput input) {
		String checkedIds = input.getCheckedIds();
		String unCheckedIds = input.getUnCheckedIds();
		if (StringUtils.isNullOrEmpty(checkedIds) && StringUtils.isNullOrEmpty(unCheckedIds))
			return null;
		if (!StringUtils.isNullOrEmpty(checkedIds)) {
			Integer result = privilegeMapper.addRolePrivilege(roleId, Arrays.asList(checkedIds.split(",")));
			if (result == 0)
				return null;
		}
		if (!StringUtils.isNullOrEmpty(unCheckedIds)) {
			Integer result2 = privilegeMapper.deleteRolePrivilege(roleId, Arrays.asList(unCheckedIds.split(",")));
			if (result2 == 0)
				return null;
		}
		return listPrivilegeOfRoleJSON(roleId);
	}

}
