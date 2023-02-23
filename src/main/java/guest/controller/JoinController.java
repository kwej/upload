package guest.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import guest.model.CouponBean;
import guest.model.CouponDao;
import guest.model.GuestBean;
import guest.model.GuestDao;

@Controller
public class JoinController {

	private final String command = "join.gt";
	private final String getPage = "join";
	//private final String gotoPage = "redirect:/login.gt";
	
	@Autowired
	private GuestDao guestDao;
	
	@Autowired
	@Qualifier("GCouponDao")
	private CouponDao couponDao;
	
	@RequestMapping(value=command, method=RequestMethod.GET)
	public String join() {
		return getPage;
	}
	
	@RequestMapping(value=command, method=RequestMethod.POST)
	public String join(@ModelAttribute("guest") @Valid GuestBean gb,
					   BindingResult result,
					   HttpServletResponse response) {
		//System.out.println("JC");
		
		if(result.hasErrors()) {
			return getPage;
		}
		
		guestDao.insertGuest(gb);
		
		//insert�� �Խ�Ʈ
		GuestBean guest = guestDao.selectNormalGuestOne(gb);
		
		CouponBean coupon = new CouponBean();
		coupon.setGno(guest.getGno());
		coupon.setCode("welcome");
		coupon.setUse(1);
		
		String joindate = guest.getJoindate();
		System.out.println("JC�� joindate:"+joindate);
		
		String monthDate = null;
		
		//������ ��ȿ�Ⱓ�� ������ �������� 1�� �ȿ� ��� �����ϰԲ�
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//String -> Date
			Date date = sdf.parse(joindate);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date); 
			
			cal.add(Calendar.MONTH, 1); //1�� ���ϱ�
			
			monthDate = sdf.format(cal.getTime());
			
			System.out.println("JC�� cal:"+monthDate);
			
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		
		coupon.setPeriod(monthDate);
		
		couponDao.insertCoupon(coupon);
		
		response.setContentType("text/html charset=utf-8");
		try {
			PrintWriter writer = response.getWriter();
			writer.println("<script>");
			writer.println("alert('ȸ�������� �����մϴ�. \\n\\n�������������� �߱޵Ǿ����ϴ�. \\n(�α��� �� �������������� ������ Ȯ���� �� �ֽ��ϴ�.)')");
			writer.println("location.href='login.gt'");
			writer.println("</script>");
			
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
