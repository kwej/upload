package guest.model;

public class CouponBean {
	//�Խ�Ʈ
	private int coupon_no; 
	private int gno; //� �Խ�Ʈ����
	private String code; //� ��������
	private String name; //������ : �����̸�
	private int percent; //������ : �����ۼ�Ʈ
	private int sale; //������ : ��������
	private int use; //����ߴ��� ���޴��� (��밡�� : 1 / ���Ұ� : 0)
	private String period; //���Ⱓ
	
	
	
	public CouponBean() {
		super();
	}
	public CouponBean(int coupon_no, int gno, String code, String name, int percent, int sale, int use, String period) {
		super();
		this.coupon_no = coupon_no;
		this.gno = gno;
		this.code = code;
		this.name = name;
		this.percent = percent;
		this.sale = sale;
		this.use = use;
		this.period = period;
	}
	public int getCoupon_no() {
		return coupon_no;
	}
	public void setCoupon_no(int coupon_no) {
		this.coupon_no = coupon_no;
	}
	public int getGno() {
		return gno;
	}
	public void setGno(int gno) {
		this.gno = gno;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPercent() {
		return percent;
	}
	public void setPercent(int percent) {
		this.percent = percent;
	}
	public int getSale() {
		return sale;
	}
	public void setSale(int sale) {
		this.sale = sale;
	}
	public int getUse() {
		return use;
	}
	public void setUse(int use) {
		this.use = use;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	
}
