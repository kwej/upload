package board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import board.model.BoardBean;
import board.model.BoardDao;

@Controller
public class BoardUpdateController {
	private final String command = "/bupdate.bo";
	private final String getPage="BoradUpdateForm";
	private final String gotoPage="redirect:/blist.bo";
	
	@Autowired
	private BoardDao boardDao;
	
	@RequestMapping(value=command,method=RequestMethod.GET)
	public String updateForm(@RequestParam("bno") String bno,
							@RequestParam("email") String email,
							@RequestParam("pageNumber") String pageNumber,
							Model model) {
		//��ȣ�� ��Ʈ�� ������ ���ڵ� �� �� �������� �Ѵ�.
		
		BoardBean bee = boardDao.getOneRecord(bno);
		
		model.addAttribute("bee",bee);
		model.addAttribute("email",email);
		model.addAttribute("pageNumber",pageNumber);
		return getPage;
	}
	
	
	@RequestMapping(value=command,method=RequestMethod.POST)
	public ModelAndView update(@ModelAttribute("bee") @Valid BoardBean bb,BindingResult result,
								@RequestParam("pageNumber")String pageNumber,
								HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
			System.out.println("bb.getPw:"+bb.getPw());
		if(result.hasErrors()) {
			
			mav.addObject("pageNumber",pageNumber);
			mav.addObject("email",bb.getEmail());
			mav.setViewName(getPage);
			return mav;
		}
		
		//record�ϳ� �����ͼ�, ��й�ȣ Ȯ��.
		System.out.println("bb.getBno():"+bb.getBno());
		BoardBean bee = boardDao.getOneRecord(String.valueOf(bb.getBno()));
		String original_pw = bee.getPw();
	
	
		if(original_pw.equals(bb.getPw())) {
			System.out.println("BUC���� ��ġ");
			bb.setWrite_date(new Timestamp(System.currentTimeMillis()));
			boardDao.updateBoard(bb);
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
				writer.println("location.href='bupdate.bo?bno="+bee.getBno()+"&email="+bee.getEmail()+"&pageNumber="+pageNumber+"'");
				writer.println("</script>");
				
				writer.flush(); //script�±� �ۼ��Ѱ� ��������
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("����ġ");
			mav.addObject("pageNumber",pageNumber);
			mav.addObject("email",bb.getEmail());
			mav.setViewName(getPage);
			return mav;
		}
		
	}
}
