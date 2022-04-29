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
		if ("itsp".equals(userId) && "itsp1231".equals(pwd)) {
			action = "login";
			user_nm = "운영자";
			
			HjMember.setUser_id("itsp");
			HjMember.setUser_nm("운영자");
			
			session.setAttribute(Config.SESSION_MEMBER, HjMember);
				
			model.addAttribute("redirectUrl", temp);
		} else if ("conservatory".equals(userId) && "conservatory#2019".equals(pwd)) {
			action = "login";
			user_nm = "관리자";
			
			HjMember.setUser_id("conservatory");
			HjMember.setUser_nm("관리자");
			session.setAttribute(Config.SESSION_MEMBER, HjMember);
		
			model.addAttribute("redirectUrl", temp);
		} else {
			String functionRresult = FuncMemberDao.functionCheck(params);
			logger.info("functionRresult =" + functionRresult);
			if("OK".equalsIgnoreCase(functionRresult)) {
				logger.info("1");
				Member = MemberDao.selectData(params);
				logger.info("2");
				if (Member != null) {
					logger.info("3");
					HjMember = HjMemberDao.selectData(params);
					logger.info("4");
					session.setAttribute(Config.SESSION_MEMBER, HjMember);
					
					//카운트 후 0이 아닐때 0으로 초기화
					params.put("count", "0");
					MemberDao.updateFailCountProc(params);
					logger.info("5");
					action = "login";
					user_nm = HjMember.getUser_nm();
					
					model.addAttribute("redirectUrl", temp);
					logger.info("6");
				} else {
					model.addAttribute("msg", "사용 권한이 없습니다.");
					model.addAttribute("redirectUrl", temp);
				}
			}else {
				if("LOCK".equalsIgnoreCase(functionRresult)) {
					model.addAttribute("msg", "로그인 시도 회수 초과로 계정을 사용할 수 없습니다.\\n관리자에게 문의하시기 바랍니다.");
				}else if("EXPIRE".equalsIgnoreCase(functionRresult)) {
					model.addAttribute("msg", "비밀번호는 주기적으로 변경을 하여야 합니다.\\n시도 회수 초과로 계정을 사용할 수 없습니다.\\n비밀번호를 변경하시기 바랍니다.");
				}else if("NO".equalsIgnoreCase(functionRresult)) {
					if(null == MemberDao.failCount(params)) {
						MemberDao.insertFailCountProc(params);
					}else{
						String count = MemberDao.failCount(params);
						int cnt = Integer.parseInt(count);
						cnt = cnt+1;
						count = String.valueOf(cnt);
						params.put("count", count);
						MemberDao.updateFailCountProc(params);
						if(cnt > 4) {
							FuncMemberDao.functionUpdate(params);
						}
					}
					action = "fail";
					model.addAttribute("msg", "계정을 확인하세요");
				}
				model.addAttribute("redirectUrl", "/logIn.do");
				viewName = "redirect";
			}
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
