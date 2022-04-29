package com.itsp.iwork.controller;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itsp.common.CommUtils;
import com.itsp.common.Config;
import com.itsp.common.Menu;
import com.itsp.dao.FuncMemberDao;
import com.itsp.dao.HjMemberDao;
import com.itsp.dao.MemberDao;
import com.itsp.dao.MenuDao;
import com.itsp.vo.MemberVO;

@Controller
public class LogActionController {

	@Autowired
	MenuDao menuDao;
	Menu menu = new Menu();

	@Autowired
	HjMemberDao HjMemberDao;
	@Autowired
	MemberDao MemberDao;
	@Autowired
	FuncMemberDao FuncMemberDao;

	private static final Logger logger = LoggerFactory.getLogger(LogActionController.class);

	@RequestMapping("/logIn")
	public String logIn(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(defaultValue = "Y") String useYn, @RequestParam(defaultValue = "login") String flag) {
		session.invalidate();
		request.setAttribute("useYn", useYn);
		menu.getMenuList(request, model, menuDao);

		String Referer = request.getHeader("Referer");
		model.addAttribute("url", Referer);

		model.addAttribute("flag", flag);
		return "iWORK/conservatory/login";
	}
	
	@RequestMapping("/logInProc")
	public String logInProc(Model model, HttpServletRequest request, HttpSession session) throws UnknownHostException {
		Map<String, Object> params = new HashMap<String, Object>();

		MemberVO HjMember = new MemberVO();
		MemberVO Member = new MemberVO();

		logger.info("params =" + request.getParameter("pwd"));

		String temp = request.getParameter("url");
		if (temp.contains("logInProc")) {
			temp = "/main.do";
		}

		String userId = CommUtils.checkNull(request.getParameter("user_id"));
		String pwd = CommUtils.checkNull(request.getParameter("pwd"));
		
		String action = "";
		String user_nm = "";
		String regIp = Inet4Address.getLocalHost().getHostAddress();
		String regAgent = request.getHeader("user-agent");
		String regOs = System.getProperty("os.name");

		try {
			params.put("user_id", userId);
			params.put("user_pwd", pwd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String viewName = "redirect";
		if ("sosthy".equals(userId) && "tpdud7122!".equals(pwd)) {
			action = "login";
			user_nm = "운영자";
			
			HjMember.setUser_id("sosthy");
			HjMember.setUser_nm("운영자");
			
			session.setAttribute(Config.SESSION_MEMBER, HjMember);
				
			model.addAttribute("redirectUrl", temp);
			model.addAttribute("msg", "관리자 로그인입니다.");
		} else if ("conservatory".equals(userId) && "conservatory#2019".equals(pwd)) {
			action = "login";
			user_nm = "관리자";
			
			HjMember.setUser_id("conservatory");
			HjMember.setUser_nm("관리자");
			session.setAttribute(Config.SESSION_MEMBER, HjMember);
		
			model.addAttribute("redirectUrl", temp);
		} 
		
		if(!"".equals(action)) {
			params.put("action", action);
			params.put("user_nm", user_nm);
			params.put("regIp", regIp);
			params.put("regAgent", regAgent);
			params.put("regOs", regOs);
			MemberDao.insertRegActionProc(params);  
		}
		
		return viewName;
	}

	@RequestMapping("/logOutProc")
	public String logOutProc(HttpServletRequest request, HttpSession session) throws UnknownHostException {
		Map<String, Object> params = new HashMap<String, Object>();
		MemberVO memberVO = (MemberVO) session.getAttribute(Config.SESSION_MEMBER);

		params.put("user_id", memberVO.getUser_id());
		params.put("user_nm", memberVO.getUser_nm());
		params.put("action", "logout");
		params.put("regIp", Inet4Address.getLocalHost().getHostAddress());
		params.put("regAgent", request.getHeader("user-agent"));
		params.put("regOs", System.getProperty("os.name"));
		MemberDao.insertRegActionProc(params);
		session.invalidate();
		return "redirect:/main.do";
	}

}
