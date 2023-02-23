package guest.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import guest.model.CouponBean;
import guest.model.CouponDao;
import guest.model.GuestBean;
import guest.model.GuestDao;
import guest.model.KakaoProfile;
import guest.model.OAuthToken;

@Controller
public class KakaoController {
	
	private final String command = "kakao.gt";
	private final String gotoPage = "redirect:/home.gt";
		
	private String client_id = "2408fd0a173459c3585561d416f29ec3";
	private String redirect_uri = "http://localhost:8080/project/kakao.gt";
	//private String request_url = "https://kauth.kakao.com/oauth/token";
	
	@Autowired
	private GuestDao guestDao;
	
	@Autowired
	@Qualifier("GCouponDao")
	private CouponDao couponDao;
	
	@RequestMapping(value=command, method=RequestMethod.GET)
	public String kakao(@RequestParam("code") String code,
						HttpServletResponse servletResponse,
						Model model,
						HttpSession session) {
		//System.out.println("KLC");
		//System.out.println("code: "+ code); 
		
		String access_token = getAccessToken(code);
		System.out.println("access_token:"+access_token);
		
		RestTemplate rt = new RestTemplate();
		
		//HttpHeader ������Ʈ ����
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer "+ access_token);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpHeader�� HttpBody�� �ϳ��� ������Ʈ�� ���
		HttpEntity<String> kakaoProfileRequest = new HttpEntity<String>(headers);
		
		//��û�ϱ� => response ������ ������ �޾ƿ´�
		ResponseEntity<String> response = rt.exchange(
				"https://kapi.kakao.com/v2/user/me", //��ū �߱� ��û �ּ�
				HttpMethod.POST, //POST���
				kakaoProfileRequest, //header,param
				String.class //StringŬ������ ���Ϲ���
		);
		
		System.out.println("response.getBody():"+response.getBody());
		
		ObjectMapper objectMapper = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper.readValue(response.getBody(), KakaoProfile.class);
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("kakaoProfile.getId():"+kakaoProfile.getId());
		System.out.println("kakaoProfile.getNickname():"+kakaoProfile.getProperties().getNickname());
		System.out.println("kakaoProfile.getEmail():"+kakaoProfile.getKakao_account().getEmail());
		
		//guest DB���� email, password, name 
		//name=> kakao_nickname()
		//email=> email()
		//password=> id()
		
		GuestBean guest = new GuestBean();
		guest.setEmail(kakaoProfile.getKakao_account().getEmail());
		guest.setName("kakao_"+kakaoProfile.getProperties().getNickname());
		guest.setKakao(1);
		guest.setPassword(String.valueOf(kakaoProfile.getId()));
		
		//���������� Ȯ��
		GuestBean kakaoGuest = guestDao.selectKakaoGuestOne(guest);
		
		//Ȯ���ϰ� ������ insert. ������ insert ���ϱ�
		if(kakaoGuest == null) {
			guestDao.insertKakaoGuest(guest);
			//�Խ�Ʈ ���� �������� -> �α��� �Ǿ��ٰ� ���� ����
			GuestBean guestInfo = guestDao.selectKakaoGuestOne(guest);
			session.setAttribute("guestInfo", guestInfo); //���� ����
			
			
			CouponBean coupon = new CouponBean();
			coupon.setGno(guestInfo.getGno());
			coupon.setCode("welcome");
			coupon.setUse(1);
			
			String joindate = guestInfo.getJoindate();
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
			
			servletResponse.setContentType("text/html charset=utf-8");
			try {
				PrintWriter writer = servletResponse.getWriter();
				writer.println("<script>");
				writer.println("alert('ȸ�������� �����մϴ�. \\n\\n�������������� �߱޵Ǿ����ϴ�. \\n(�������������� ������ Ȯ���� �� �ֽ��ϴ�.)')");
				writer.println("location.href='home.gt'");
				writer.println("</script>");
				
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		else {
			session.setAttribute("guestInfo", kakaoGuest); //���� ����
		}
		
		//System.out.println("KC�� kakaoGuest:"+kakaoGuest);
		//System.out.println("kakaoGuest.email:"+kakaoGuest.getEmail());
		
		System.out.println("KC�� guestInfo:"+session.getAttribute("guestInfo"));
		
		return gotoPage;
		
	}

	private String getAccessToken(String code) {
		String access_token = "";
		
		//POST ������� key=value �������� ������ ��û (īī�� ����������)
		RestTemplate rt = new RestTemplate();

		// HttpHeader ������Ʈ ����
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpBody ������Ʈ ����
		// 4���� ������ ����
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", client_id);
		params.add("redirect_uri", redirect_uri);
		params.add("code", code);

		// HttpHeader�� HttpBody�� �ϳ��� ������Ʈ�� ���
		// kakaoTokenRequest�� header�� body�� ������ �ִ� ������Ʈ�� �ȴ�
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<MultiValueMap<String, String>>(
				params, headers);

		// ��û�ϱ� => response ������ ������ �޾ƿ´�
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", // ��ū �߱� ��û �ּ�
				HttpMethod.POST, // POST���
				kakaoTokenRequest, // header,param
				String.class // StringŬ������ ���Ϲ���
		);

		//System.out.println("response:" + response);
		// 200 : ��û�� ���������� �Ǿ���

		//System.out.println("response.getBody():" + response.getBody());
		// 200 �ٷ� ������ �ִ� ������

		// ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		access_token = oauthToken.getAccess_token();
		//System.out.println("access_token:"+access_token);
		
		return access_token;
	}

}
