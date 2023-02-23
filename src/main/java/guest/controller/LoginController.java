package guest.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import guest.model.GuestBean;
import guest.model.GuestDao;

@Controller
public class LoginController {

	private final String command = "login.gt";
	private final String getPage = "login";
	private final String gotoPage = "redirect:/home.gt";
	
	@Autowired
	private GuestDao guestDao;
	
	@Autowired
	private ServletContext application;
	
	@RequestMapping(value=command, method=RequestMethod.GET)
	public String login(@RequestParam(value="destination", required=false) String destination,
						@RequestParam(value="name", required=false) String name,
						@RequestParam(value="house_no", required=false) String house_no,
						HttpSession session) {
		
		//String destination = (String) session.getAttribute("destination");
		System.out.println("LC�� destination:" + destination); //������
		System.out.println("LC�� name:" + name); //theme, subtheme�� �̸�
		System.out.println("LC�� house_no"+house_no);
		

		if(destination != null) {
			
			if(destination.equals("theme") || destination.equals("subtheme")) {
				session.setAttribute("destination",destination+".gt");
				session.setAttribute("name",name);
			}
			else if(destination.equals("detail")) {
				
				session.setAttribute("destination",destination+".gt");
				session.setAttribute("house_no",house_no);
			}
			else if(destination.equals("buyTicket")) {
	               
	               session.setAttribute("destination","seasonT.gt");
	        }
		}
		
		return getPage;
	}
	
	@RequestMapping(value=command, method=RequestMethod.POST)
	public String login(@ModelAttribute("guest") GuestBean guest,
						HttpServletResponse response,
						HttpSession session,
						Model model) {
		
		//System.out.println("LC");
		
		boolean check = guestDao.checkEmailPassword(guest);
		if(check == false) {
			System.out.println("�������� ���� ȸ��");
			try {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter writer = response.getWriter();
				writer.println("<script>");
				writer.println("alert('�������� ���� ȸ���Դϴ�.\\n�̸��ϰ� ��й�ȣ�� �ٽ� �Է����ּ���.');");
				writer.println("location.href='login.gt'");
				writer.println("</script>");
				
				writer.flush(); //script�±� �ۼ��Ѱ� ��������
			} catch (IOException e) {
				e.printStackTrace();
			}
			return getPage;
		}
		else {
			System.out.println("������ ȸ��");
			GuestBean normalGuest = guestDao.selectNormalGuestOne(guest);

			session.setAttribute("guestInfo", normalGuest);
			// ���� �����ð� 1�ð� 
			session.setMaxInactiveInterval(60*60) ;
			
			if(normalGuest.getEmail().equals("admin@a")) { //������
				application.setAttribute("admin",normalGuest);
				return "redirect:/home.ad";
			} 
			else { //�Ϲ�ȸ��
				String destination = (String) session.getAttribute("destination");
				String name = (String) session.getAttribute("name");
				String house_no = (String) session.getAttribute("house_no");
				
				System.out.println("LC�� �α��� �� destination:"+destination);
				System.out.println("LC�� �α��� �� name:"+name);
				System.out.println("LC�� �α��� �� house_no:"+house_no);
				
				
				
				if(destination != null) {
					model.addAttribute("name",name);
					model.addAttribute("house_no",house_no);
					return "redirect:/"+destination;
				}
				
				return gotoPage;
			}
		}
	}
}
