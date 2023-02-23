package host.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import host.model.AddressBean;
import host.model.AllBean;
import host.model.ContentBean;
import host.model.HostBean;
import host.model.HouseBean;
import host.model.HouseDao;

@Controller
public class H_Update_House_Controller {

   public final String command = "hupdate.ht";
   public final String updateSubTheme = "updateSubTheme.ht";
   public String getPage="/updateHouseForm";
   public String gotoPage="redirect:/hlist.ht";
   
   @Autowired
   private HouseDao housedao;
   
   @Autowired
   private ThemeDao tdao;
   
   @Autowired
   private SubThemeDao sdao;
   
   @Autowired
   ServletContext servletContext;
   
   @Autowired
   ServletContext application;
   
   @RequestMapping(value=command,method=RequestMethod.GET)
   public String update(@RequestParam("house_no") String house_no,
                  Model model,HttpServletRequest request) {
      
      //no�� �ش��ϴ� house��������
      AllBean allbean = housedao.selectHouse_ByNo(house_no);
      model.addAttribute("all",allbean);
      
      //�׸� �ҷ�����
      List<ThemeBean> tlists = tdao.getThemeCate();
      model.addAttribute("tlists",tlists);
      
      //�����׸� �ҷ�����
      List<SubThemeBean> slists = sdao.getAllSub();
      model.addAttribute("slists",slists);
      

      String fullPath = request.getContextPath()+"/resources/house_images";
      model.addAttribute("fullPath",fullPath);
      
      
      
      return getPage;
   }
   
   @RequestMapping(value=updateSubTheme,method=RequestMethod.GET)
   public String updateSubTheme(@RequestParam("house_no") String house_no,
                        @RequestParam("t_selValue") String keyword,
                           Model model,HttpServletRequest request) {
      System.out.println("updateSubTheme ����");
      System.out.println("t_selValue : "+keyword);
      
      //no�� �ش��ϴ� house��������
      AllBean allbean = housedao.selectHouse_ByNo(house_no);
      model.addAttribute("all",allbean);
      
      List<ThemeBean> tlists = tdao.getThemeCate();
      model.addAttribute("tlists",tlists);
      
      model.addAttribute("keyword",keyword);
      
      List<SubThemeBean> slists = sdao.getAllSub_ByKeyword(keyword);
      model.addAttribute("slists",slists);
      
      String fullPath = request.getContextPath()+"/resources/house_images";
      model.addAttribute("fullPath",fullPath);
      
      return getPage;
   }
   
   @RequestMapping(value=command,method=RequestMethod.POST)
   public String update(@RequestParam String originalImg,
                  @RequestParam MultipartFile[] Insert_images,
                  @ModelAttribute("all") @Valid AllBean all,
                     BindingResult result,
                     Model model,
                     HttpServletRequest request,HttpSession session,
                     HttpServletResponse response) throws IOException {
      
      if(result.hasErrors()) {
         
         //�׸� �ҷ�����
         List<ThemeBean> tlists = tdao.getThemeCate();
         model.addAttribute("tlists",tlists);
         
         //�����׸� �ҷ�����
         List<SubThemeBean> slists = sdao.getAllSub();
         model.addAttribute("slists",slists);
         
         model.addAttribute("all",all);
         
         return getPage;
      }
      
      /* �̹��� ���� */
      if(originalImg == null || originalImg == "" ) {
         originalImg = "Null";
      }
      
      if(!originalImg.equals("Null")) {
         String url = application.getRealPath("/resources/house_images");
         
         String[] images = originalImg.split(",");
         for(String name : images) {
            
            File file = new File(url,name);
            if(file.exists()) {
               file.delete();
            }
         }
      }
      
      //���ο� �̹��� update
      if(Insert_images != null) {
         
         for(MultipartFile multipartFile : Insert_images) {
            
            String uploadFolder = application.getRealPath("/resources/house_images");
            
            String uploadFileName = multipartFile.getOriginalFilename();
   
            System.out.println("uploadFileName:"+uploadFileName);
            
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
            
            File saveFile = new File(uploadFolder, uploadFileName);
            
            try {
               multipartFile.transferTo(saveFile);
            } catch (Exception e) {
               /* log.error(e.getMessage()); */
            } 
         }
         
   
         String names = "";
         
         for(MultipartFile multipartFile : Insert_images) {
            for(int i=0;i<Insert_images.length; i++) {
               System.out.println(multipartFile.getOriginalFilename());
               names += multipartFile.getOriginalFilename();
               System.out.println("i:"+i);
               if(i+1 != Insert_images.length) { 
                  names += ","; //����
                  break;
               }
            }
         }
         
         all.setPhoto(names);
      }
      
      //ȣ��Ʈ ��ȣ �ҷ�����
      HostBean hbean = (HostBean)session.getAttribute("hostInfo");
      int hno = hbean.getHno();
      
      //����Ʈ = ���� *0.1
      int point = (int) (Integer.parseInt(all.getPrice())*0.01);
            
      //house DB�� �ֱ�
      HouseBean house = new HouseBean(all.getHouse_no(),hno,all.getTname(),all.getSname(),all.getName(),
      all.getFacility(),all.getNum(),all.getMaxnum(),all.getPrice(),point,null,all.getPhoto());
            
      //�̹��� ȭ�� ���� DB�� �ִ� �۾�
      housedao.updateHouse(house);   
      
      //address DB�� �ֱ�
      AddressBean address = new AddressBean(0,all.getHouse_no(),all.getAddress(),all.getDetailed());
            
      housedao.updateAddress(address);
            
      //content DB�� �ֱ�
      ContentBean content = new ContentBean(0,all.getHouse_no(),all.getCon1(),all.getCon2(),all.getCon3(),
      all.getNotice(),all.getRule(),all.getCheckinTime(),all.getCheckoutTime());
      housedao.updateContent(content);
            
      System.out.println("alert �߱� ��");
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      out.println("alert('���������� �����Ͽ����ϴ�')");
      out.println("</script>");
      
      return gotoPage;
   }
}