package com.itsp.common;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.itsp.dao.MemberDao;
import com.itsp.vo.MemberVO;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

	@Autowired
	MemberDao MemberDao;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String uri = request.getRequestURI();
		ArrayList<String> exceptURIList = new ArrayList<String>();
		/**
		 * exceptURIList.add("/cms/main.do"); 
		 * exceptURIList.add("/cms/loginProc.do");
		 * exceptURIList.add("/cms/logout.do");
		 ***/
		// URL예외등록
		if (exceptURIList.contains(uri))
			return true;

		boolean isOK = false;
		MemberVO memberVO = (MemberVO) request.getSession().getAttribute(Config.SESSION_MEMBER);
		if (memberVO != null) {
			logger.info("Intercepted!, getId : " + memberVO.getId());
			isOK = true;
		}

		if (isOK == false) {
			request.getSession().removeAttribute(Config.SESSION_MEMBER);
			response.sendRedirect("/main.do");
			return false;
		}
		return true;
	}

}
