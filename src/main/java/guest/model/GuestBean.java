package guest.model;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class GuestBean {
	private int gno;
	@NotBlank(message="�̸����� �Է��ϼ���")
	private String email;

	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[a-zA-Z0-9]{5,10}$", message = "����, ���ڸ� �����Ͽ� 5~10�ڸ��� ��й�ȣ�� �Է��ϼ���")
	private String password;

	@NotBlank(message="�̸��� �Է��ϼ���")
	private String name;

	@NotBlank(message="��ȭ��ȣ�� �Է��ϼ���")
	private String phone;

	private String joindate;
	private int kakao;
	private int mypoint;

	private String coupon_code; //����
	
	private int cnt; //����
	
	public int getGno() {
		return gno;
	}
	public void setGno(int gno) {
		this.gno = gno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getJoindate() {
		return joindate;
	}
	public void setJoindate(String joindate) {
		this.joindate = joindate;
	}
	public int getKakao() {
		return kakao;
	}
	public void setKakao(int kakao) {
		this.kakao = kakao;
	}
	public int getMypoint() {
		return mypoint;
	}
	public void setMypoint(int mypoint) {
		this.mypoint = mypoint;
	}
	
	public String getCoupon_code() {
		return coupon_code;
	}

	public void setCoupon_code(String coupon_code) {
		this.coupon_code = coupon_code;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
}
