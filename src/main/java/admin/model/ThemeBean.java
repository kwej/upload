package admin.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class ThemeBean {
	private int tno;
	
	@NotBlank(message="�׸� �̸��� �Է��ϼ���")
	private String name;
	
	@NotBlank(message="�̹����� �����ϼ���")
	private String img;
	
	@NotBlank(message="�׸� ������ �Է��ϼ���")
	private String contents;
	
	private MultipartFile upload;
	
	
	
	public MultipartFile getUpload() {
		return upload;
	}
	public void setUpload(MultipartFile upload) {
		this.upload = upload;
		System.out.println("setUpload upload : "+upload);
		System.out.println("upload.getName() : "+upload.getName());
		System.out.println("upload.getOriginalFilename() : "+upload.getOriginalFilename());
		this.img = upload.getOriginalFilename();
	}
	
	public ThemeBean() {
		super();
	}
	public ThemeBean(int tno, String name, String img, String contents) {
		super();
		this.tno = tno;
		this.name = name;
		this.img = img;
		this.contents = contents;
	}
	public int getTno() {
		return tno;
	}
	public void setTno(int tno) {
		this.tno = tno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	
}
