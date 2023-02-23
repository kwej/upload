package notice.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import admin.model.CouponBean;
import admin.model.CouponDao;
import notice.model.NoticeBean;
import notice.model.NoticeDao;

@Controller
public class NoticeUpdateController {
	private final String command ="nupdate.nt";
	private final String updateCate="updateCate.nt";
	private final String updateCoupon="updateCoupon.nt";
	private final String getPage ="noticeUpdateForm";
	private final String gotoPage ="redirect:/nlist.nt";
	
	@Autowired
	ServletContext serv;
	
	@Autowired
	private NoticeDao noticeDao;
	
	@Autowired
	private CouponDao cdao;
	
	@RequestMapping(value=command,method=RequestMethod.GET)
	public String updateNotice(@RequestParam("nno") String nno,
							   @RequestParam("pageNumber") String pageNumber,
							   HttpServletRequest request,
							   Model model) {
		//���� ������
		NoticeBean notice = noticeDao.getOneRecord(nno);
		
		System.out.println("ó������getNfile"+notice.getNfile());
		System.out.println("ó������getUpload"+notice.getUpload());
		
		String loadingPath = request.getContextPath()+"/resources";
		System.out.println("loadingPath:"+loadingPath);
		
		List<CouponBean> lists = cdao.getAllCoupon();
		model.addAttribute("lists",lists);
		
		String c_selValue = notice.getCate();
		String content = notice.getContent();
		model.addAttribute("notice",notice); //��ü�� ������� ������Ʈ ������ �̿� ����.
		model.addAttribute("c_selValue",c_selValue); //ī�װ�
		model.addAttribute("content",content); //����
		model.addAttribute("loadingPath",loadingPath);
		model.addAttribute("pageNumber",pageNumber);
		model.addAttribute("nno",nno);

		return getPage;
	}
	
	@RequestMapping(value=updateCate,method=RequestMethod.GET)
	public String selectCate(@RequestParam("c_selValue") String c_selValue,
							Model model,
							@RequestParam("nno")String nno) {
		
		//���� ������
		NoticeBean notice = noticeDao.getOneRecord(nno);
		
		if(c_selValue.equals("����")) {
			
			List<CouponBean> lists = cdao.getAllCoupon();
			model.addAttribute("lists",lists);
			
			model.addAttribute("c_selValue",c_selValue);
			model.addAttribute("notice",notice);
			
			return getPage;
		}
		
		model.addAttribute("c_selValue",c_selValue);
		model.addAttribute("notice",notice);
		
		return getPage;
	}
	
	@RequestMapping(value=updateCoupon,method=RequestMethod.GET)
	public String selectedCoupon(
								@RequestParam("cou_selValue") String cou_selValue,
								@RequestParam("c_selValue") String c_selValue,
								@RequestParam("nno")String nno,
								Model model) {
		
		NoticeBean notice = noticeDao.getOneRecord(nno);
			
		String code = cdao.getCoupon_ByName(cou_selValue);
		model.addAttribute("notice",notice);
		
		List<CouponBean> lists = cdao.getAllCoupon();
		model.addAttribute("lists",lists);
		
		model.addAttribute("code",code);
		
		model.addAttribute("c_selValue",c_selValue);
		model.addAttribute("cou_selValue",cou_selValue);
			
			
		return getPage;
	}
	
	@RequestMapping(value=command,method=RequestMethod.POST)
	public ModelAndView updateN(@ModelAttribute("notice") @Valid NoticeBean nb,
								BindingResult result, @RequestParam("originalImg") String originalImg) {
	
		ModelAndView mav = new ModelAndView();
		
		if(result.hasErrors()) {
			System.out.println("����");
			System.out.println("����getNfile"+nb.getNfile());
			System.out.println("����getNno"+nb.getNno());
			System.out.println("����getTitle"+nb.getTitle());
			System.out.println("����getContent"+nb.getContent());
			System.out.println("����getCate"+nb.getCate());
			System.out.println("����getCoupon"+nb.getCoupon());
			System.out.println("����getNt_date"+nb.getNt_date());
			System.out.println("����getN_readcount"+nb.getN_readcount());
			System.out.println("����getUpload"+nb.getUpload());
			mav.setViewName(getPage);
			return mav;
		}
		

		System.out.println("�������getUpload"+nb.getUpload());
		if(nb.getUpload()!=null) {
		//1. �̹��� ���� �� �� �̹��� ���ý� ���ε�
		//2. DB�� Update 
		
		System.out.println("���� �ƴ�/�̹��� ���� - ����");
		//�̹��� ���ϵ� ���� �������� ���, ���������̸�
		String uploadPath=serv.getRealPath("/resources");
		File deleteFile= new File(uploadPath,originalImg);
		Boolean de  = deleteFile.delete();
		System.out.println("de"+de);
		
		
		
		System.out.println("getTitle: "+nb.getTitle());
		System.out.println("getCate: "+nb.getCate());
		//���� ������ �̹��� ������ ���.
		MultipartFile mul = nb.getUpload();
		System.out.println("mul"+nb.getUpload());
		
		//�̹��� �ø���
		File file = new File(uploadPath +"/"+ mul.getOriginalFilename());
		System.out.println("44");
		try {
			System.out.println("55");
			mul.transferTo(file); // ��¥������ �ø��� �۾�
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		noticeDao.updateNotice(nb);
		
		mav.setViewName(gotoPage);
		return mav;
		
		}
		
		System.out.println("���� �ƴ�/�̹��� ����");
		noticeDao.updateNotice_NoImg(nb);
		mav.setViewName(gotoPage);
		return mav;
	}
	
}
