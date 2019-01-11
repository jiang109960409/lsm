package com.lsm.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.lsm.dto.CreatePrivilegeInput;
import com.lsm.dto.DeletePrivilegeInputAndOutput;
import com.lsm.dto.RestRespBody;
import com.lsm.dto.UpdatePrivilegeInput;
import com.lsm.dto.UpdatePrivilegeOfRoleInput;
import com.lsm.security.JwtManager;
import com.lsm.security.TokenPayload;
import com.lsm.service.PrivilegeService;
import com.lsm.util.HttpUtils;
import com.lsm.util.ValidateUtils;
import com.mysql.jdbc.StringUtils;

@RestController
@RequestMapping("/privilege")
public class PrivilegeController {

	@Autowired
	private PrivilegeService privilegeService;
	@Autowired
	private JwtManager jwtManager;

	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("data", new RestRespBody(true, "", null, false, "privilege"));
		return mav;
	}

	@GetMapping(value = "/list", produces = "application/json;charset=UTF-8")
	public String list() {
		return privilegeService.listPrivilegeJSON();
	}

	@PostMapping(value = "/add", produces = "application/json;charset=UTF-8")
	public RestRespBody add(@RequestBody CreatePrivilegeInput input, HttpSession session, HttpServletResponse response)
			throws Exception {
		String authorization = HttpUtils.authenticate(session, response);
		TokenPayload payload = jwtManager.deserialize(jwtManager.parseToken(authorization.substring(7)).getSubject());
		input.setCreator(payload.getId());
		privilegeService.add(input);
		String result = privilegeService.listPrivilegeJSON();
		return new RestRespBody(true, "添加成功", result);
	}

	@PutMapping(value = "/update", produces = "application/json;charset=UTF-8")
	public RestRespBody update(@RequestBody UpdatePrivilegeInput input) {
		RestRespBody restRespBody = ValidateUtils.validatePOJO(input);
		if (restRespBody != null)
			return new RestRespBody(false, "更新权限失败，请重新尝试");
		return new RestRespBody(true, "更新成功", privilegeService.updatePrivilege(input));
	}

	@DeleteMapping("/{id}/delete")
	public RestRespBody delete(@PathVariable("id") long id) {
		String result = privilegeService.deletePrivilege(new DeletePrivilegeInputAndOutput(id));
		if (StringUtils.isNullOrEmpty(result))
			return new RestRespBody(false, "删除失败");
		return new RestRespBody(true, "删除成功", result);
	}

	@GetMapping("/role/list")
	public RestRespBody listRole() {
		return new RestRespBody(true, "获取成功", privilegeService.listRole());
	}

	@GetMapping("/role/{roleId}/list")
	public RestRespBody listPrivilegeOfRole(@PathVariable Long roleId) {
		if (roleId == null)
			return new RestRespBody(false, "请选择角色");
		String result = privilegeService.listPrivilegeOfRoleJSON(roleId);
		return new RestRespBody(true, "获取成功", result);
	}

	@PutMapping("/role/{roleId}/update")
	public RestRespBody updatePrivilegeOfRole(@PathVariable Long roleId, @RequestBody UpdatePrivilegeOfRoleInput input,
			HttpSession session, HttpServletResponse response) {
		if (roleId == null)
			return new RestRespBody(false, "更新失败，请重新尝试");
		String result = privilegeService.updatePrivilegeOfRole(roleId, input);
		if (StringUtils.isNullOrEmpty(result))
			return new RestRespBody(false, "更新失败，请重新尝试");
		return new RestRespBody(true, "更新成功", result);
	}

	@ModelAttribute("updatePrivilegeInput")
	public CreatePrivilegeInput getUpdatePrivilegeInput() {
		return new CreatePrivilegeInput();
	}

	@ModelAttribute("createPrivilegeInput")
	public CreatePrivilegeInput getCreatePrivilegeInput() {
		return new CreatePrivilegeInput();
	}
}
