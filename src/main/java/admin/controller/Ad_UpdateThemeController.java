package admin.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import admin.model.SubThemeBean;
import admin.model.SubThemeDao;
import admin.model.ThemeBean;
import admin.model.ThemeDao;
import host.model.HouseBean;
import host.model.HouseDao;

@Controller
public class Ad_UpdateThemeController {

	public final String command = "updateTheme.ad";
	public String getPage = "/updateThemeForm";
	public String gotoPage = "redirect:/themeList.ad";
	
	@Autowired
	private ThemeDao themeDao;
	
	@Autowired
	private SubThemeDao subthemeDao;
	
	@Autowired
	private HouseDao houseDao;
	
	@Autowired
	ServletContext servletContext;
	
	@RequestMapping(value=command,method=RequestMethod.GET)
	public String update(@RequestParam("tno") String tno,
						Model model,
						HttpServletRequest request) {
		
		ThemeBean theme = themeDao.selectByTno(tno);
		model.addAttribute("original_name",theme.getName());
		
		String fullpath = request.getContextPath()+"/resources/theme_images";
		
		model.addAttribute("fullpath",fullpath);
		model.addAttribute("theme",theme);
		return getPage;
	}
	
	@RequestMapping(value=command,method=RequestMethod.POST)
	public String update(@ModelAttribute("theme") @Valid ThemeBean theme,
						BindingResult result, Model model,
						@RequestParam("originalImg") String originalImg,
						@RequestParam("originalTname") String original_name) {
		
		if(result.hasErrors()) {
			model.addAttribute("original_name",original_name);
			return getPage;
		}
		
		//�̹��� ȭ�ϱ��� �����ϱ�
		String uploadPath =servletContext.getRealPath("/resources/theme_images");
		File delFile = new File(uploadPath,originalImg);
		delFile.delete();
		
		//������ �̹��� ȭ���� multi ������ ��´�
		MultipartFile multi = theme.getUpload();
		
		File file = new File(uploadPath+"/" + multi.getOriginalFilename());
		try {
			//ȭ�� ���ε�
			multi.transferTo(file);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//DB update�ϱ�
		themeDao.updateTheme(theme);
		
		//�׸��̸� ����� => �����׸��� �׸��̸��� ����, ���ҿ����� �׸��̸� ����
		SubThemeBean stb = new SubThemeBean();
		stb.setTheme_cate(theme.getName());
		stb.setName(original_name);
		subthemeDao.updateSubTheme(stb);
		
		HouseBean hb = new HouseBean();
		hb.setTname(theme.getName());
		hb.setOriginal_name(original_name);
		houseDao.updateHouseTheme(hb);
		
		
		return gotoPage;
	}
}
