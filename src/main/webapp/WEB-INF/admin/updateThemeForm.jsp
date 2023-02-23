<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="author" content="Untree.co" />
    <link rel="shortcut icon" href="favicon.png" />

    <meta name="description" content="" />
    <meta name="keywords" content="bootstrap, bootstrap5" />

    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    

    <link rel="stylesheet" href="resources/fonts/icomoon/style.css" />
    <link rel="stylesheet" href="resources/fonts/flaticon/font/flaticon.css" />

    <link rel="stylesheet" href="resources/css/tiny-slider.css" />
    <link rel="stylesheet" href="resources/css/aos.css" />
    <link rel="stylesheet" href="resources/css/style.css" />

    <title>
      SEMICOLON(;)&nbsp; ADMIN-UPDATE THEME page
    </title>
  </head>
  
  <style>
  	body{
  		margin-top : 120px;
  	}
	.err{
		color:red;
		font-size: 5pt;
	}
  </style>
 <%@ include file="ad_top.jsp" %><br>
    <!-- --------------------- -->
    
    <div class="section">
      <div class="container">
        <div class="row">
          <div
            class="col-lg-4 mb-5 mb-lg-0"
            data-aos="fade-up"
            data-aos-delay="100"
          >
            <div class="contact-info">
              <div class="address mt-2">
                <i class="icon-add"></i>
                <h4 class="mb-2"><a href="addTheme.ad">테마 추가</a></h4>
                <p>
                  <br />
                  
                </p>
              </div>

              <div class="open-hours mt-4">
                <i class="icon-list"></i>
                <h4 class="mb-2"><a href="themeList.ad">테마 목록</a></h4>
                <p>
                  <br />
                  
                </p>
              </div>

              <div class="email mt-4">
                <i class="icon-add"></i>
                <h4 class="mb-2"><a href="addSubTheme.ad">서브테마 추가</a></h4>
                <p>
                  <br />
                  
                </p>
              </div>

              <div class="phone mt-4">
                <i class="icon-list"></i>
                <h4 class="mb-2"><a href="subList.ad">서브테마 목록</a></h4>
                <p>
                  <br />
                  
                </p>
              </div>
            </div>
          </div>
          <!-- 시작 -->
          <div class="col-lg-8" data-aos="fade-up" data-aos-delay="200">
            <form:form commandName="theme" action="updateTheme.ad" method="post" enctype="multipart/form-data">
              <input type="hidden" name="tno" value="${theme.tno }">
              <input type="hidden" name="originalImg" value="${theme.img }">
              <input type="hidden" name="originalTname" value="${original_name }">
              <div class="row">
                <div class="col-6 mb-3">
                  <input
                    type="text"
                    class="form-control"
                    value="${theme.name }"
                    name="name"
                  />
                  <form:errors cssClass="err" path="name"/>
                </div>
                <div class="col-12 mb-3">
                  <img src="${fullpath }/${theme.img }" width=200 hieght=200><br>
                  <input type="file" name="upload"><br>
                  <form:errors cssClass="err" path="upload"/>
                </div>
                <div class="col-12 mb-3">
                  <textarea
                    cols="30"
                    rows="7"
                    class="form-control"
                    name="contents"
                  >${theme.contents }</textarea>
                  <form:errors cssClass="err" path="contents"/>
                </div>

                <div class="col-12">
                  <input
                    type="submit"
                    value="테마 수정하기"
                    class="btn btn-primary"
                  />
                </div>
              </div>
            </form:form>
          </div>
          <!-- 끝 -->
        </div>
      </div>
    </div>
    <!-- /.untree_co-section -->
    
    <!-- --------------------- -->
   <br><%@ include file="ad_bottom.jsp" %> 
    