package com.lsm.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HttpUtils {

	public static void flushData(HttpServletResponse response, Object data) throws Exception {
		PrintWriter out = response.getWriter();
		out.print(data);
		out.flush();
		out.close();
	}
	
	/**
	 * 验证Session是否有效
	 * @param session
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static String authenticate(HttpSession session, HttpServletResponse response) throws Exception {
		String authorization = session.getAttribute("Authorization").toString();
		if (authorization == null || !authorization.startsWith("Bearer "))
			response.sendRedirect("/401");
		return authorization;
	}
}
