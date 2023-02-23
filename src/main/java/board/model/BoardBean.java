package board.model;

import java.sql.Timestamp;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class BoardBean {
	private int bno;

	@NotBlank(message = "*�ۼ��ڸ� �Է��� �ּ���.")
	private String writer;

	private String email;
	@NotBlank(message = "*������ �Է��� �ּ���.")
	private String title;
	@Length(min = 5, message = "�ּ� 5�� �̻��� �Է��ؾ� �մϴ�.")
	private String content;
	@NotBlank(message = "*��й�ȣ�� �ʼ��Դϴ�.")
	@Length(min=4, max=10, message="4~10�ڸ��� ��й�ȣ�� �Է��� �ּ���.")
	private String pw;

	private Timestamp write_date;
	private int readcount;
	private int ref;
	private int re_step;
	private int re_level;

	// ��ȿ�� �˻�.

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public Timestamp getWrite_date() {
		return write_date;
	}

	public void setWrite_date(Timestamp write_date) {
		this.write_date = write_date;
	}

	public int getReadcount() {
		return readcount;
	}

	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}

	public int getRef() {
		return ref;
	}

	public void setRef(int ref) {
		this.ref = ref;
	}

	public int getRe_step() {
		return re_step;
	}

	public void setRe_step(int re_step) {
		this.re_step = re_step;
	}

	public int getRe_level() {
		return re_level;
	}

	public void setRe_level(int re_level) {
		this.re_level = re_level;
	}

}