package guest.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import guest.model.GuestBean;
import guest.model.GuestDao;

@Controller
public class GuestUpdateController {

	private final String command = "update.gt";
	private final String getPage = "update";
	//private final String gotoPage = "redirect:/myProfile.gt";
	
	@Autowired
	private GuestDao guestDao;
	
	@RequestMapping(value=command, method=RequestMethod.GET)
	public String update(HttpSession session, Model model) {
		
		GuestBean guest = (GuestBean)session.getAttribute("guestInfo");
		
		//īī���� �Ϲ� => ���� form �ٸ��� �ϱ�
		if(guest.getKakao() == 1) {
			System.out.println("īī�� �α����� ���");
			model.addAttribute("kakaoGuest",guest); 
		} else {
			System.out.println("�Ϲ� �α����� ���");
			model.addAttribute("normalGuest",guest);
		}
		
		return getPage;
	}
	
	
	//�Ϲ� �Խ�Ʈ�� �����ϱ� Ŭ��������
	@RequestMapping(value=command, method=RequestMethod.POST)
	public String update(@ModelAttribute("normalGuest") @Valid GuestBean guest,
						 BindingResult result,
						 @RequestParam("originalPassword") String originalPassword,
						 HttpSession session,
						 HttpServletResponse response,
						 Model model) {
		
		//System.out.println("GUC�� newPassword:"+newPassword);
		//System.out.println("GUC�� guest.getEmail:"+guest.getEmail().trim());
		//System.out.println("GUC�� guest.getPassword:"+guest.getPassword());
	
		
		String newPassword = guest.getPassword();
		guest.setEmail(guest.getEmail().trim());
		guest.setPassword(originalPassword);
		boolean check = guestDao.checkEmailPassword(guest);
		
		GuestBean normalGuest;
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		
		if(check == false) { //����
			//���� ��й�ȣ�� ���� ������
			//System.out.println("���� ��й�ȣ�� ���� ����");
			
			try {
				out = response.getWriter();
				out.println("<script>");
				out.println("alert('���� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.')");
				out.println("</script>");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			normalGuest = (GuestBean)session.getAttribute("guestInfo");
			model.addAttribute("normalGuest", normalGuest);
			return getPage;
			
		}
		else {
			//���� ��й�ȣ�� ������
			//System.out.println("���� ��й�ȣ�� ����");
			
			if(result.hasErrors()) {
				System.out.println("����");
				return getPage;
			}
			
			guest.setPassword(newPassword);
			guestDao.updateNormalGuest(guest);
			
			normalGuest = guestDao.selectNormalGuestOne(guest);
			session.setAttribute("guestInfo", normalGuest);
			
			try {
				out = response.getWriter();
				out.println("<script>");
				out.println("alert('ȸ�� ������ �����Ǿ����ϴ�.')");
				out.println("location.href='myProfile.gt'");
				out.println("</script>");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
			
		}
		
	}
}
