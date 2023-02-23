package host.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import host.model.HostBean;
import host.model.HouseDao;
import host.model.ReserveBean;

@Controller
public class H_ReservController {
	private final String command="reservation.ht";
	private final String getPage="HostReservList";
	private final String getPage2="HostReservList2";
	
	@Autowired
	private HouseDao hdao;
	
	
	@RequestMapping(command)
	public String hostReserv (Model model, HttpSession session) {
			
		HostBean Info =(HostBean) session.getAttribute("hostInfo");
			
				int hostno = Info.getHno();
				System.out.println("hostno: "+hostno);
				//� host���� ���� ������
				
				List<ReserveBean> rhList = hdao.hostPageReserve(hostno);
				// ��� ������ �ϳ��� �Ѵ�.
				
				model.addAttribute("rhList",rhList);
				
			return getPage;
		
	}
}
