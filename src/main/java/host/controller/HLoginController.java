package host.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import host.model.HostBean;
import host.model.HostDao;

@Controller
public class HLoginController {
	private final String command = "login.ht";
	private final String getPage = "login";
	private final String gotoPage = "redirect:/home.ht";

	@Autowired
	private HostDao hostDao;
	
	@Autowired
	private ServletContext application;
	
	@RequestMapping(value=command, method=RequestMethod.GET)
	public String login(@RequestParam(value="destination",required=false) String destination,
					HttpSession session) {
		
		if(destination != null) {
			if(destination.equals("addHouse")){
				
				session.setAttribute("destination", "addHouse.ht");
			}
		}
		
		return getPage;
	}

	@RequestMapping(value=command, method=RequestMethod.POST)
	public String login(@ModelAttribute("host") HostBean host,
			HttpServletResponse response,
			HttpSession session) {

		boolean check = hostDao.checkEmailPassword(host);
		if(check == false) {
			System.out.println("�������� ���� ȸ��");
			try {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter writer = response.getWriter();
				writer.println("<script>");
				writer.println("alert('�������� ���� ȸ���Դϴ�. \\n�̸��ϰ� ��й�ȣ�� �ٽ� �Է����ּ���.');");
				writer.println("</script>");

				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return getPage;
		}
		else {
			System.out.println("������ ȸ��");
			String destination = (String) session.getAttribute("destination");
			HostBean loginHost = hostDao.selectHostOne(host);

			session.setAttribute("hostInfo", loginHost);
			// ���� �����ð� 1�ð� 
			session.setMaxInactiveInterval(60*60) ;
			
			if(loginHost.getEmail().equals("admin@a")) { //������
				application.setAttribute("admin",loginHost);
				return "redirect:/home.ad";
			} 
			else{ //�Ϲ�ȣ��Ʈ
				//System.out.println("hostInfo:"+session.getAttribute("hostInfo"));
				if(destination != null) {
					return "redirect:/"+destination;
				}
				return gotoPage;
			}
		}
	}
}
