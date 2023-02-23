package admin.model;

import org.hibernate.validator.constraints.NotBlank;

public class SubThemeBean {
	private int sno;
	
	@NotBlank(message="�����׸� �̸��� �Է��ϼ���")
	private String name;
	private String theme_cate;
	
	@NotBlank(message="�����׸� �󼼼����� �Է��ϼ���")
	private String contents;
	
	public int getSno() {
		return sno;
	}
	public String getTheme_cate() {
		return theme_cate;
	}
	public void setTheme_cate(String theme_cate) {
		this.theme_cate = theme_cate;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	
}
