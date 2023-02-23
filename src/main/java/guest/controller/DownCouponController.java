package guest.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import admin.model.CouponBean;
import guest.model.CouponDao;
import guest.model.GuestBean;
import guest.model.GuestDao;

@Controller
public class DownCouponController {

	public final String command = "downCoupon.gt";
	public String rePage = "gndetail.gt";
	public String gotoPage = "redirect:/home.gt";

	@Autowired
	@Qualifier("GCouponDao")
	private CouponDao guest_cdao;

	@Autowired
	@Qualifier("ACouponDao")
	private admin.model.CouponDao admin_cdao;

	@Autowired
	private GuestDao gdao;

	@RequestMapping(command)
	public String updateCoupon(@RequestParam("code") String code, @RequestParam("nno") int nno,
			@RequestParam("pageNumber") String pageNumber, Model model, HttpSession session,
			HttpServletResponse response, HttpServletRequest request) throws IOException {

		CouponBean a_cbean = admin_cdao.selectCoupon_ByCode(code);

		if (a_cbean == null) {
			System.out.println("a_cbean null��");

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�ڵ尡 �����ʽ��ϴ�')");
			out.println("location.href='gndetail.gt?nno=" + nno + "&pageNumber=" + pageNumber + "'");
			out.println("</script>");
			out.flush();

			// return rePage+"&nno="+nno;

			return null;
		}

		GuestBean gbean = (GuestBean) session.getAttribute("guestInfo");
		int gno = gbean.getGno();

		// ���� �ߺ��ٿ� ���� �ڵ�
		String coupon_code = "";
		if (gbean.getCoupon_code() == null) { // ������ �ƿ����ٸ�
			System.out.println("������ ����ִ�");
			gbean.setCoupon_code(code);
			gdao.updateCoupon_code(gbean);
			
			//���� �ֱ�
			LocalDate now = LocalDate.now();
			System.out.println("now : " + now);

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String period = sdf.format(cal.getTime());

			guest.model.CouponBean g_cbean = new guest.model.CouponBean(0, gno, a_cbean.getCode(), a_cbean.getName(),
					a_cbean.getPercent(), a_cbean.getSale(), 1, period);
			guest_cdao.douwnLoadC_insertCoupon(g_cbean);

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��������� �Ǿ����ϴ�')");
			out.println("location.href='gndetail.gt?nno=" + nno + "&pageNumber=" + pageNumber + "'");
			out.println("</script>");
			out.flush();

			return gotoPage;

			
		} else {// ������ �ִٸ�
			System.out.println("gbean.getCoupon_code() : " + gbean.getCoupon_code());
			System.out.println("������ �ִ�");
			String[] one_code = gbean.getCoupon_code().split(",");
			for (int i = 0; i < one_code.length; i++) {
				System.out.println("one_code[" + i + "]: " + one_code[i]);
				if (one_code[i].equals(code)) { // �ִٸ�
					System.out.println("������ �ߺ��Ǵ°� �ִ�.");
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('������ �̹� �����̽��ϴ�')");
					out.println("location.href='gndetail.gt?nno=" + nno + "&pageNumber=" + pageNumber + "'");
					out.println("</script>");
					out.flush();

					return null;
				}
			}
			System.out.println("������ �ߺ��Ǵ°� ����");
			coupon_code = gbean.getCoupon_code() + "," + code;
			gbean.setCoupon_code(coupon_code);
			gdao.updateCoupon_code(gbean);

			LocalDate now = LocalDate.now();
			System.out.println("now : " + now);

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String period = sdf.format(cal.getTime());

			guest.model.CouponBean g_cbean = new guest.model.CouponBean(0, gno, a_cbean.getCode(), a_cbean.getName(),
					a_cbean.getPercent(), a_cbean.getSale(), 1, period);
			guest_cdao.douwnLoadC_insertCoupon(g_cbean);

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��������� �Ǿ����ϴ�')");
			out.println("location.href='gndetail.gt?nno=" + nno + "&pageNumber=" + pageNumber + "'");
			out.println("</script>");
			out.flush();

			return null;

		}

		/*----------------------------------------------------*/

		//return null;

		//return gotoPage;
	}
}