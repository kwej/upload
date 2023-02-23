package admin.model;

import org.hibernate.validator.constraints.NotBlank;

public class CouponBean {
	//������
	@NotBlank(message="���� �ڵ带 �Է��ϼ���")
	private String code;
	
	private String oldCode; //���� ���� �ڵ�
	
	@NotBlank(message="���� �̸��� �Է��ϼ���")
	private String name;
	
	private int percent;
	private int sale;
	
	public CouponBean() {
		super();
	}
	public CouponBean(String code, String name, int percent, int sale) {
		super();
		this.code = code;
		this.name = name;
		this.percent = percent;
		this.sale = sale;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOldCode() {
		return oldCode;
	}
	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
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
	
}
