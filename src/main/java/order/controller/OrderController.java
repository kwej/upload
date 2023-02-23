package order.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import guest.model.CouponBean;
import guest.model.CouponDao;
import guest.model.GuestBean;
import guest.model.GuestDao;
import guest.model.SeasonTBean;
import guest.model.SeasonTDao;
import host.model.AllBean;
import host.model.HouseDao;
import order.model.OrderBean;
import order.model.OrderDao;
import wish.model.WishDao;

@Controller
public class OrderController {

	private final String command = "order.od";
	private String getPage = "orderForm";
	private String gotoPage = "detailForm";
	
	@Autowired
	private GuestDao guestDao;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	@Qualifier("GCouponDao")
	private CouponDao couponDao;
	
	@Autowired
	private SeasonTDao seasonTDao;
	
	@Autowired
	private HouseDao houseDao;
	
	@Autowired
	private WishDao wishDao;
	
	
	
	@RequestMapping(value=command,method=RequestMethod.POST)
	public String order(HttpServletRequest request, Model model,
						@ModelAttribute("order") @Valid OrderBean order,
						BindingResult result,
						@RequestParam("num") int num, //�����ο���
						@RequestParam("price") int price, //���� 1�� ����
						@RequestParam("name") String name, //���� �̸�
						HttpSession session) {
		
		if(result.hasErrors()) {
			int house_no  = order.getHouse_no();
			AllBean hb = houseDao.selectHouse_ByNo(String.valueOf(house_no));
			String fullpath = request.getContextPath() + "/resources/house_images";
			int wishCount = wishDao.countWishByHouseNo(String.valueOf(house_no));
			
			ArrayList<String> numberList = new ArrayList<String>();
			int maxnum = Integer.parseInt(hb.getMaxnum());
			
			for(int i=1; i<=maxnum; i++) {
				numberList.add(String.valueOf(i));
			}
			
			model.addAttribute("numberList",numberList);
			model.addAttribute("hb", hb);
			model.addAttribute("fullpath", fullpath);
			model.addAttribute("wishCount", wishCount);
			return gotoPage;
		}
		
		int extra_price = 0;
		int adult = Integer.parseInt(order.getAdult());
		int kid = Integer.parseInt(order.getKid());
		if((adult+kid) <= num) { //���� �ο����� ����
			System.out.println("���� �ο� ����");
			
		}else { //���� �ο� �ʰ� ��
			if(adult == num) { //���� = �����ο�
				System.out.println("���� = �����ο�");
				extra_price = 10000 * kid;
				System.out.println("extra:"+extra_price);	
			}
			else if(adult < num) { //���� < �����ο�
				System.out.println("���� < �����ο�");
				num = num-adult; //������ �����ο�
				System.out.println("num : "+num);
				if(kid > num) {
					extra_price = 10000*(kid-num);
				}
				System.out.println("extra:"+extra_price);	
			}
			else { //���� > �����ο�
				System.out.println("���� > �����ο�");
				extra_price = 20000*(adult-num);
				extra_price += 10000*kid;
				System.out.println("extra:"+extra_price);	
			}
			
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date checkin_date = null;
		Date checkout_date = null;
		try {
			checkin_date = sdf.parse(order.getCheckin());
			checkout_date = sdf.parse(order.getCheckout());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//�� �������
		long in_date = checkin_date.getTime();
		long out_date = checkout_date.getTime();
		  
		long diffSec = (out_date - in_date) / 1000; //�� ����
		long diffDays = diffSec / (24*60*60); //���ڼ� ����
		
		//��ڿ� ���� ����
		int house_totalprice = (int) (price*diffDays);
		order.setHouse_price(house_totalprice);
		
		//��ڿ� ���� ����Ʈ
		int totalPoint = (int) (house_totalprice*0.01);
		System.out.println("totalPoint:"+totalPoint);
		
		//���αݾ� ���� �� ����
		int totalPrice = house_totalprice + extra_price;
		
		//�Խ�Ʈ ����
		GuestBean guest = (GuestBean)session.getAttribute("guestInfo");
		int gno = guest.getGno();
		System.out.println("gno : "+gno);
		order.setGno(gno);
		GuestBean gb = guestDao.selectGuestByGno(gno);
		
		//����
		order.setPhoto(order.getPhoto());
		
		//int cnt = orderDao.countOrder(order);
		//order ���̺� insert
		orderDao.insertOrder(order);
		
		//order ���̺��� insert�Ѱ� �������� (=> ���� �������� insert�� ���� order_no�� ���� ū��)
		int max = orderDao.maxOrderNo();
		order.setOrder_no(max);
		OrderBean orderbean = orderDao.selectOrder(order);
		
		String fullpath = request.getContextPath()+"/resources/house_images";
		
		model.addAttribute("fullpath",fullpath);
		model.addAttribute("oname",name);
		model.addAttribute("ob",orderbean);
		model.addAttribute("extra_price",extra_price);
		model.addAttribute("totalPrice",totalPrice);
		model.addAttribute("totalPoint",totalPoint);
		model.addAttribute("date",(int)diffDays);
		model.addAttribute("mypoint",gb.getMypoint());
		
		//�� ���� ����Ʈ ��������
		ArrayList<CouponBean> couponList = (ArrayList<CouponBean>)couponDao.selectCouponByGno(gno);
		model.addAttribute("couponList",couponList);
		//System.out.println("period:"+couponList.get(0).getPeriod());
		
		//�� ����� ����Ʈ ��������
		ArrayList<SeasonTBean> seasonTList = (ArrayList<SeasonTBean>)seasonTDao.selectSeasonTByGno(gno);
		model.addAttribute("seasonTList",seasonTList);
		System.out.println("seasonTList:"+seasonTList.size());
		
		return getPage;
				
	}
	
	
}
