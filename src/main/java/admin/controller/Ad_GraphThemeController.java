package admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import admin.model.ThemeBean;
import admin.model.ThemeDao;
import host.model.AllBean;
import host.model.HouseDao;

@Controller
public class Ad_GraphThemeController {

	private final String command = "graphTheme.ad";
	private String gotoPage = "graphTheme";

	@Autowired
	private HouseDao houseDao;
	
	@Autowired
	private ThemeDao themeDao;

	@RequestMapping(value = command, method = RequestMethod.GET)
	public String graphTheme(Model model) {

		//�׸� �ֱ�
		List<ThemeBean> tlists = themeDao.getThemeCate();
		model.addAttribute("tlists",tlists);
		
		// ������ �� ���ϱ�
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		// System.out.println(date);
		String today = sdf.format(date);
		// System.out.println(today);
		model.addAttribute("today", today);

		// ���� ��¥ ���ϱ�
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		String yesterday = s.format(cal.getTime());

		// ����,���� ����� ���Ұ��� ���ϱ�
		int today_house = houseDao.todayHouse();
		int yesterday_house = houseDao.yesterdayHouse(yesterday);
		model.addAttribute("today_house", today_house);
		model.addAttribute("yesterday_house", yesterday_house);

		// �̹��� ����,�ְ� ���Ұ���
		int min_house = houseDao.minHouse();
		int max_house = houseDao.maxHouse();
		model.addAttribute("min_house", min_house);
		model.addAttribute("max_house", max_house);

		return gotoPage;
	}

	@RequestMapping(value = command, method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody Map<String, Object> graph() {

		HashMap<String, Object> map = new HashMap<String, Object>();

		ArrayList<AllBean> theme = (ArrayList<AllBean>) houseDao.graphByTheme();
		map.put("theme", theme);
		
		String tname = theme.get(0).getTname();
		
		ArrayList<AllBean> subtheme = (ArrayList<AllBean>) houseDao.graphBySubTheme(tname);
		map.put("subtheme", subtheme);

		return map;
	}

}
