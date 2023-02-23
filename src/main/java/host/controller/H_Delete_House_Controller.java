package host.controller;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import host.model.AllBean;
import host.model.HouseDao;

@Controller
public class H_Delete_House_Controller {

	public final String command = "delete.ht";
	public String gotoPage="redirect:/hlist.ht";
	
	@Autowired
	private HouseDao hdao;
	
	@Autowired
	private ServletContext servletContext;
	
	@RequestMapping(command)
	public String delete(@RequestParam("house_no") String house_no,
						Model model,HttpServletRequest request) {
		System.out.println("delete(house_no) :"+house_no);
		
		//�̹��� ȭ���� ������� ���
		String deletePath = servletContext.getRealPath("/resources/house_images");
		//�̹��� ȭ�� �̸��� ������ ����
		AllBean allbean = hdao.selectHouse_ByNo(house_no);
		//�̹��� ȭ�� ���� �����ϱ�
		File delFile = new File(deletePath,allbean.getPhoto());
		delFile.delete();
		
		//������ ����  �����ϱ�
		hdao.deleteHouse(house_no);
		
		return gotoPage;
	}
}
