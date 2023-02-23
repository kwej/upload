package board.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import board.model.BoardBean;
import board.model.BoardDao;

@Controller
public class BoardDeleteController {
	private final String command="/bdelete.bo";
	private final String getPage="BdeletepwChcekForm";
	private final String gotoPage="redirect:/blist.bo";
	
	@Autowired
	private BoardDao boardDao;
	
	@RequestMapping(value=command, method=RequestMethod.GET)
	public String deleteBoard(@RequestParam("bno")String bno,
							@RequestParam("pageNumber")String pageNumber,
							Model model) {
		
		model.addAttribute("bno",bno);
		model.addAttribute("pageNumber",pageNumber);
		//��й�ȣ Ȯ�� �� �����ؾ� ��.
		return getPage;
	}
	
	@RequestMapping(value=command, method=RequestMethod.POST)
	public ModelAndView deleteBoard(@RequestParam("bno")String bno,
									@RequestParam("pageNumber")String pageNumber,
									@RequestParam("pw")String pw,
									HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		
		//record�ϳ� �����ͼ�, ��й�ȣ Ȯ��.
		BoardBean bee = boardDao.getOneRecord(bno);
		String original_pw = bee.getPw();
		
		if(original_pw.equals(pw)) {//db�Էº�� = �����Էº��
			System.out.println("��ġ");
			boardDao.deleteBoard(bno);
			mav.addObject("pageNumber",pageNumber);
			mav.setViewName(gotoPage);
			return mav;
		}
		else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.println("<script>");
				writer.println("alert('��й�ȣ�� ��ġ���� �ʽ��ϴ�.');");
				writer.println("location.href='bdelete.bo?bno="+bno+"&pageNumber="+pageNumber+"'");
				writer.println("</script>");
				
				writer.flush(); //script�±� �ۼ��Ѱ� ��������
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("����ġ");
			mav.addObject("pageNumber",pageNumber);
			mav.addObject("bno",bno);
			mav.setViewName(getPage);
			return mav;
		}
	}//
}
