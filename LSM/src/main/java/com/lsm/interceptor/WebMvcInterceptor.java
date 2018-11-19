package com.lsm.interceptor;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lsm.anno.PrivilegeId;
import com.lsm.mapper.PrivilegeMapper;
import com.lsm.security.JwtManager;
import com.lsm.security.TokenPayload;

import io.jsonwebtoken.Claims;

/**
 * 权限控制的拦截器
 *
 */
@Component
public class WebMvcInterceptor implements HandlerInterceptor {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebMvcInterceptor.class);

	private JwtManager jwtManager;
	private PrivilegeMapper privilegeMapper;

	public WebMvcInterceptor(JwtManager jwtManager, PrivilegeMapper privilegeMapper) {
		this.jwtManager = jwtManager;
		this.privilegeMapper = privilegeMapper;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String authorization = (String) session.getAttribute("Authorization");
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
			response.sendRedirect("/401");
			return false;
		}
		String token = authorization.substring(7);
		Claims claims;
		try {
			claims = jwtManager.parseToken(token);
		} catch (RuntimeException e) {
			LOGGER.warn("Invalid token format.", e);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
			response.sendRedirect("/401");
			return false;
		}
		if (claims.getExpiration().getTime() < System.currentTimeMillis()) { // not test
			LOGGER.info("Token was expired.");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
			response.sendRedirect("/401");
			return false;
		}

		TokenPayload payload = jwtManager.deserialize(claims.getSubject());
		String newToken = jwtManager.createToken(payload.getId(), payload.getName(), payload.getRoles());
		session.removeAttribute("Authorization");
		session.setAttribute("Authorization", "Bearer " + newToken);

		HandlerMethod hanlerMethod = (HandlerMethod) handler;
		PrivilegeId privilegeId = hanlerMethod.getMethodAnnotation(PrivilegeId.class);
		if (privilegeId == null) {
			return true;
		} else {
			List<Integer> roles = payload.getRoles();
			if (roles == null || roles.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
				response.sendRedirect("/401");
				return false;
			}

			if (roles.contains(1)) { // 教务部，超级管理员
				return true;
			}

			List<Integer> rolesOfAuthorized = null;
			List<Long> rs = privilegeMapper.listRole(Long.parseLong(privilegeId.id()));
			if (rs != null && !rs.isEmpty()) {
				rolesOfAuthorized = rs.parallelStream().map(l -> Integer.parseInt(l + "")).collect(Collectors.toList());
			}

			if (rolesOfAuthorized == null || rolesOfAuthorized.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
				response.sendRedirect("/401");
				return false;
			}

			for (Integer role : roles) {
				if (rolesOfAuthorized.contains(role)) {
					return true;
				}
			}

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
			response.sendRedirect("/401");
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	public JwtManager getJwtManager() {
		return jwtManager;
	}

	public void setJwtManager(JwtManager jwtManager) {
		this.jwtManager = jwtManager;
	}

	public PrivilegeMapper getPrivilegeMapper() {
		return privilegeMapper;
	}

	public void setPrivilegeMapper(PrivilegeMapper privilegeMapper) {
		this.privilegeMapper = privilegeMapper;
	}

}
