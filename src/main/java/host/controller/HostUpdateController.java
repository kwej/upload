package host.controller;

import java.io.IOException;
import java.io.PrintWriter;

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
import host.model.HostBean;
import host.model.HostDao;

@Controller
public class HostUpdateController {

	private final String command = "update.ht";
	private final String getPage = "update";
	
	@Autowired
	private HostDao hostDao;
	
	@RequestMapping(value=command, method=RequestMethod.GET)
	public String update() {
		return getPage;
	}
	
	@RequestMapping(value=command, method=RequestMethod.POST)
	public String update(@ModelAttribute("host") HostBean host,
						 @RequestParam("newPassword") String newPassword,
						 HttpSession session,
						 HttpServletResponse response,
						 Model model) {
		
		System.out.println("HUC�� newPassword:"+newPassword);
		System.out.println("HUC�� host.getEmail:"+host.getEmail().trim());
		System.out.println("HUC�� host.getPassword:"+host.getPassword());
		
		host.setEmail(host.getEmail().trim());
		boolean check = hostDao.checkEmailPassword(host);
		
		GuestBean normalGuest;
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		
		if(check == false) { //����
			//���� ��й�ȣ�� ���� ������
			System.out.println("���� ��й�ȣ�� ���� ����");
			
			try {
				out = response.getWriter();
				out.println("<script>");
				out.println("alert('���� ��й�ȣ�� ��ġ���� �ʽ��ϴ�')");
				out.println("</script>");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return getPage;
			
		}
		else {
			//���� ��й�ȣ�� ������
			System.out.println("���� ��й�ȣ�� ����");
			host.setPassword(newPassword);
			hostDao.updateHost(host);
			
			HostBean hb = hostDao.selectHostOne(host);
			session.setAttribute("hostInfo", hb);
			
			try {
				out = response.getWriter();
				out.println("<script>");
				out.println("alert('ȸ�� ������ �����Ǿ����ϴ�')");
				out.println("location.href='mypage.ht'");
				out.println("</script>");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
			
		}
		
	}
	
}
