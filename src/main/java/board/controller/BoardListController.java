package board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import board.model.BoardBean;
import board.model.BoardDao;
import utility.Paging;

@Controller
public class BoardListController {
	private final String command ="/blist.bo";
	private final String getPage ="BoardList";
	
	@Autowired
	private BoardDao boardDao;
	
	@RequestMapping(command)
	public ModelAndView boardList(@RequestParam(value="whatColumn", required=false) String whatColumn, 
								@RequestParam(value="keyword",required=false) String keyword,
								@RequestParam(value="pageNumber",required=false) String pageNumber,
								HttpServletRequest request) {
		
		//�Ѿ�� map ����
		Map<String,String> map = new HashMap<String,String>();
		map.put("whatColumn", whatColumn);
		map.put("keyword", "%"+keyword+"%");

		//paging 

		int totalCount= boardDao.getTotalCount(map);
		String url = request.getContextPath()+"/"+command;
		System.out.println("request.getContextPath(): "+request.getContextPath());
		Paging pageInfo = new Paging(pageNumber,null,totalCount,url,whatColumn,keyword,null);		 

		

		// ��� �ҷ�����, JSP�� ������ �� ì���.
		ModelAndView mav = new ModelAndView();
		List<BoardBean> lists = boardDao.getBoardList(map,pageInfo);
				//new ArrayList<BoardBean>();
		mav.addObject("lists",lists); 
		
		mav.addObject("pageInfo",pageInfo);
			   // pageNumber�� �ƴ����� : ������ ����� �ٸ� �κе� JSP���� ���� �� �ִ�.
		
		mav.setViewName(getPage);
		return mav;
	}
	 
	
}
