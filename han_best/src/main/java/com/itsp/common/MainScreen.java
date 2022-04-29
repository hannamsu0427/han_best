package com.itsp.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.itsp.dao.PopUpDao;
import com.itsp.vo.PopUpVO;

public class MainScreen {

	public void getToday(HttpServletRequest request, Model model) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7); // 현재 날짜에서 7일전의 날짜 가져오기
		Date currentTime = cal.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String timestr = formatter.format(currentTime);
		int today = Integer.parseInt(timestr);
		model.addAttribute("today", today);
	}

	public void getPopUp(HttpServletRequest request, Model model, PopUpDao PopUpDao) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("useYn", "Y");

		int totalCount = PopUpDao.totalCount(params);
		model.addAttribute("popUpCount", totalCount);

		List<PopUpVO> dataList = PopUpDao.selectDataList(params);
		model.addAttribute("PopUpVO_List", dataList);

	}

}
