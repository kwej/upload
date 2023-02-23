package notice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import notice.model.NoticeBean;
import notice.model.NoticeDao;
import utility.Paging;

@Controller
public class NoticeListController {
	private final String command="/nlist.nt";
	private final String getPage="noticeList";
	
	@Autowired
	private NoticeDao noticeDao;
	
	@RequestMapping(command)
	public ModelAndView nlist(@RequestParam(value="whatColumn", required=false) String whatColumn, 
							 @RequestParam(value="keyword",required=false) String keyword,
						  	 @RequestParam(value="pageNumber",required=false) String pageNumber,
						 	 HttpServletRequest request) {
		
		//�Ѿ�� map ����
		Map<String,String> map = new HashMap<String,String>();
		map.put("whatColumn", whatColumn);
		map.put("keyword", "%"+keyword+"%");

		//paging 
		int totalCount= noticeDao.getNTotalCount(map);
		String url = request.getContextPath()+"/"+command;
		System.out.println("request.getContextPath(): "+request.getContextPath());
		Paging pageInfo = new Paging(pageNumber,null,totalCount,url,whatColumn,keyword,null);		 

		

		// ��� �ҷ�����, JSP�� ������ �� ì���.
		ModelAndView mav = new ModelAndView();
		List<NoticeBean> lists = noticeDao.getAllNotice(map,pageInfo);
		mav.addObject("lists",lists); 
		mav.addObject("pageInfo",pageInfo);
			   // pageNumber�� �ƴ����� : ������ ����� �ٸ� �κе� JSP���� ���� �� �ִ�.
		//notice�� ����̳� ����� ����.
		mav.setViewName(getPage);
		return mav;
	}


}
