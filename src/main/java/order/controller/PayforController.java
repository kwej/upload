package order.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import guest.model.CouponBean;
import guest.model.CouponDao;
import guest.model.GuestBean;
import guest.model.GuestDao;
import guest.model.SeasonTBean;
import guest.model.SeasonTDao;
import order.model.MailService;
import order.model.PayforBean;
import order.model.PayforDao;

@Controller
public class PayforController {

	private final String command = "payfor.od";
	//private final String gotoPage = "redirect:/myReservation.gt";
	
	@Autowired
	private PayforDao payforDao;

	@Autowired
	private GuestDao guestDao;

	@Autowired
	private SeasonTDao seasonTDao;
	
	@Autowired
	@Qualifier("GCouponDao")
	private CouponDao couponDao;
	
	@Autowired
	private MailService mailService;
	
	@RequestMapping(command)
	public void payfor(@ModelAttribute("payfor") PayforBean payfor,
						 HttpServletResponse response,HttpSession session) {
		System.out.println("PayforController");
		System.out.println("gno:"+payfor.getGno());
		System.out.println("house_no:"+payfor.getHouse_no());
		System.out.println("wpay:"+payfor.getWpay());
		System.out.println("coupon_no:"+payfor.getCoupon_no());
		System.out.println("coupon_price:"+payfor.getCoupon_price());
		System.out.println("use_point:"+payfor.getUse_point()); 
		System.out.println("st_no:"+payfor.getSt_no()); 
		System.out.println("st_price:"+payfor.getSt_price()); 
		System.out.println("house_price:"+payfor.getHouse_price());
		System.out.println("extra_price:"+payfor.getExtra_price());
		System.out.println("discount_price:"+payfor.getDiscount_price()); //X
		System.out.println("totalPoint:"+payfor.getTotalPoint());
		System.out.println("totalPrice:"+payfor.getTotalPrice()); //X
		System.out.println("order_no:"+payfor.getOrder_no());
		
		//���� �ݾ�, �� �����ݾ� ����ϱ� -> form���� �� �Ѿ��
		int discount = payfor.getCoupon_price() + payfor.getUse_point() + payfor.getSt_price();
		int total = payfor.getHouse_price()+payfor.getExtra_price()-discount;
		
		payfor.setDiscount_price(discount);
		payfor.setTotalPrice(total);
		payfor.setReview_use(0);
		
		//payfor ���̺��� insert �ϱ�
		payforDao.insertPayfor(payfor);
		
		//coupon ���̺��� use ������Ʈ �ϱ� (��������� 0���� �ϱ�)
		CouponBean coupon = couponDao.selectCouponByCouponNo(payfor.getCoupon_no());
		if(coupon != null) { //������ �ִ� ���
			
			if(payfor.getCoupon_price() == 0) { //coupon_price�� 0�� ��� => ����ߴٰ� �ϸ� �ȵ�
				coupon.setUse(1);
			}
			else { 
				//couponDao.deleteCoupon(coupon);
				coupon.setUse(0);
			}
			
			couponDao.updateUse(coupon);
			System.out.println("updateUse ����");
			
		}
		
		//���� ����
		GuestBean gbean = (GuestBean) session.getAttribute("guestInfo");
		CouponBean cbean = new CouponBean();
		cbean.setGno(gbean.getGno());
		cbean.setCoupon_no(payfor.getCoupon_no());
		couponDao.deleteCoupon_ByCouponNo(cbean);
		
		//guest ���̺��� mypoint ������Ʈ �ϱ� (����� ����Ʈ ����/ �������� ����Ʈ ���ϱ�)
		GuestBean guest = guestDao.selectGuestByGno(payfor.getGno());
		int point = guest.getMypoint(); //���� �ִ� ����Ʈ
		String email = guest.getEmail();//���Ϲ߼��� �Խ�Ʈ ����
		guest.setMypoint(point - payfor.getUse_point() + payfor.getTotalPoint());
		guestDao.updateMyPoint(guest);
		System.out.println("updateMyPoint ����");
		
		//season_t ���̺��� money ������Ʈ �ϱ� (����� �ݾ� ����)
		SeasonTBean stb = seasonTDao.selectSeasonTByStNo(payfor.getSt_no());
		if(stb != null) { //�������� �ִ� ���
			int money = stb.getMoney(); //���� ���� �ݾ�
			stb.setMoney(money - payfor.getSt_price()); //�����ݾ׿��� ��������� �� �ݾ� ����
			seasonTDao.updateSeasonTMoney(stb);
			System.out.println("updateSeasonTMoney ����");
		}
		
		//���Ͽ� �߼��� ���������� payfor + order_house ���̺��� ��������
		
		payfor = new PayforBean();
		GuestBean gb = (GuestBean)session.getAttribute("guestInfo");
		int gno = gb.getGno();
		System.out.println("gno"+gno);
		
		int pno = payforDao.selectMaxPno();
		payfor.setPno(pno);
		System.out.println("pno"+pno);
		
		payfor.setGno(gno);
		payfor.setPno(pno);
		PayforBean pb = (PayforBean)payforDao.selectPayforByPno(payfor);
		
		String addr = "rlaskrud98@gmail.com";
		String subject = "�����ݷ�-����Ȯ�� �����Դϴ�";
		String body ="���� �����ݷ� ����Ʈ�� �̿����ּż� �����մϴ�\r\n"
				+ "\r\n ���Բ��� �����Ͻ� ���� �󼼳��� �Դϴ�\r\n"
				+ "�����̸� : "+pb.getName()+"\r\n üũ�� : "+pb.getCheckin()+"\r\n"+
				"üũ�ƿ� : "+pb.getCheckout()+"\r\n ���� : "+pb.getAdult()+"�� \r\n"+
				"���� : "+pb.getKid()+"�� \r\n --------------------\r\n"+
				"������� : "+pb.getWpay()+"\r\n �Ѱ����ݾ� : "+pb.getTotalPrice()+"��"+
				"\r\n\r\n �� �ڼ��� ���೻���� ���÷��� ����Ʈ�� �湮�� �ּ���";
		
		mailService.sendEmail(email, addr, subject, body);

		response.setContentType("text/html; charset=utf-8");
		try {
			PrintWriter writer = response.getWriter();
			writer.println("<script>");
			writer.println("alert('������ �Ϸ�Ǿ����ϴ�.\\n���� ������ ���ϰ� �������������� Ȯ���Ͻ� �� �ֽ��ϴ�.')");
			writer.println("location.href='myReservation.gt'");
			writer.println("</script>");
			
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
