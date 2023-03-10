<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Semicolon Profile_Update page</title>
	
	<meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="author" content="Untree.co" />
    <link rel="shortcut icon" href="favicon.png" />

    <meta name="description" content="" />
    <meta name="keywords" content="bootstrap, bootstrap5" />

    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />

	<link
	href="https://fonts.googleapis.com/css2?family=Work+Sans:wght@400;500;600;700&display=swap"
	rel="stylesheet" />
	
    <link rel="stylesheet" href="resources/fonts/icomoon/style.css" />
    <link rel="stylesheet" href="resources/fonts/flaticon/font/flaticon.css" />

    <link rel="stylesheet" href="resources/css/tiny-slider.css" />
    <link rel="stylesheet" href="resources/css/aos.css" />
    <link rel="stylesheet" href="resources/css/style.css" />
    
    <link rel="stylesheet" href="resources/assets/vendor/fonts/boxicons.css" />
    
    <style type="text/css">
    	.padding {
    		padding: 100px;
    	}
    	.vertical {
			padding: 13.5px;
    	}
    	.font {
    		padding: 15.5px 13px;
    		font-size: 13px;
    		font-weight: bold;
    		
    	}
    	.left {
    		text-align: left;
    	}
    	.border-bottom {
    		border-bottom: 1px;
    	}
    	.profile {
    		padding: 24px !important;
    	}
		.btn {
			display: inline-block;
		    font-weight: 400;
		    line-height: 1.5;
		    text-align: center;
	        vertical-align: middle;
		    border-radius: 30px;
		    border-width: 2px;
		    padding-top: 15px;
		    padding-bottom: 15px;
		    padding-left: 30px;
		    padding-right: 30px;
		}
		label {
			text-align:right;
		}
		.err {
    		font-size: 10px;
    		font-weight: bold;
    		color: red;
    		text-align: left;
    	}
    	.card-header:first-child {
		    border-radius: 0.5rem 0.5rem 0 0;
		}
		.align-items-center {
		    align-items: center !important;
		}
		.justify-content-between {
		    justify-content: space-between !important;
		}
		.card-header {
		    padding: 1.5rem 1.5rem;
		    margin-bottom: 0;
		    background-color: transparent;
		    border-bottom: 0 solid #d9dee3;
		}
		.card-header:first-child {
		    border-radius: 0.5rem 0.5rem 0 0;
		    border-top-left-radius: 0.5rem;
		    border-top-right-radius: 0.5rem;
		    border-bottom-right-radius: 0px;
		    border-bottom-left-radius: 0px;
		}
		h1, h2, h3, h4, h5, h6 {
			font-family: "Work Sans", sans-serif;
			font-weight: 500;
		    line-height: 1.1;
		    color: #566a7f;
		}
		h4 {
			font-size: calc(1.2625rem + 0.15vw);
		}
		.card {
		    border: 0 solid #d9dee3;
		    border-top-color: rgb(217, 222, 227);
		    border-top-style: solid;
		    border-top-width: 0px;
		    border-right-color: rgb(217, 222, 227);
		    border-right-style: solid;
		    border-right-width: 0px;
		    border-bottom-color: rgb(217, 222, 227);
		    border-bottom-style: solid;
		    border-bottom-width: 0px;
		    border-left-color: rgb(217, 222, 227);
		    border-left-style: solid;
		    border-left-width: 0px;
		    border-image-source: initial;
		    border-image-slice: initial;
		    border-image-width: initial;
		    border-image-outset: initial;
		    border-image-repeat: initial;
		    border-radius: 0.5rem;
		    border-top-left-radius: 0.5rem;
		    border-top-right-radius: 0.5rem;
		    border-bottom-right-radius: 0.5rem;
		    border-bottom-left-radius: 0.5rem;
		    box-shadow: 0 0.125rem 0.25rem rgba(161, 172, 184, 0.4);
		}
		body {
			background-color: rgba(0, 32, 74, 0.05);
		}
		.input-group-text {
			color: #526e8a;
			background-color: #fff;
		}
    </style>
    
</head>
<body>

<%@ include file="header.jsp" %>

<div
  class="hero page-inner overlay"
  style="background-image: url('resources/images/hero_bg_1.jpg')"
>
  <div class="container">
    <div class="row justify-content-center align-items-center">
      <div class="col-lg-9 text-center mt-5">
        <h1 class="heading" data-aos="fade-up">???????????? ??????</h1>

        <nav
          aria-label="breadcrumb"
          data-aos="fade-up"
          data-aos-delay="200"
        >
          <ol class="breadcrumb text-center justify-content-center">
            <li class="breadcrumb-item"><a href="myProfile.gt">Profile</a></li>
            <li
              class="breadcrumb-item active text-white-50"
              aria-current="page"
            >
              Update Profile
            </li>
          </ol>
        </nav>
      </div>
    </div>
  </div>
</div>

<section class="features-1">
<br><br>

<!-- Content -->
<div class="container">

  <!-- Basic Layout & Basic with Icons -->
  <div class="row">
    <!-- Basic with Icons -->
    <div class="col-xxl">
      <div class="card mb-4">
        <div class="card-header d-flex align-items-center justify-content-between border-bottom">
          <h4 class="mb-0">?????? ????????????</h4>
        </div>
        <div class="card-body profile">
          
          
        <c:if test="${kakaoGuest != null}">
          <form action="kakaoUpdate.gt" method="post">
            <div class="row mb-3">
              <label class="col-sm-2 col-form-label font vertical" for="basic-icon-default-fullname">??????</label>
              <div class="col-sm-10">
                <div class="input-group input-group-merge">
                  <span id="basic-icon-default-fullname" class="input-group-text">
                  <i class="bx bx-user"></i></span>
                  <input
                     type="text" name="name" value="${kakaoGuest.name }"
                     id="basic-default-fullname"
                     class="form-control left"
                     disabled
                   />
                    
                </div>
              </div>
            </div>
            
            <div class="row mb-3">
              <label class="col-sm-2 col-form-label font" for="basic-icon-default-email">?????????</label>
              <div class="col-sm-10">
                <div class="input-group input-group-merge">
                  <span id="basic-icon-default-email" class="input-group-text">
                  <i class="bx bx-envelope"></i></span>
                  <input
                     type="text" name="email" value="${kakaoGuest.email }"
                     id="basic-default-fullname"
                     class="form-control left"
                     readonly
                   />
                </div>
              </div>
            </div>
            
            <div class="row mb-3">
              <label class="col-sm-2 col-form-label font" for="basic-icon-default-phone">????????????</label>
              <div class="col-sm-10">
                <div class="input-group input-group-merge">
                  <span id="basic-icon-default-phone" class="input-group-text">
                  <i class="bx bx-phone"></i></span>
                  <input
                     type="text" name="phone" value="${kakaoGuest.phone }"
                     id="basic-default-fullname"
                     class="form-control left"
                     required
                   />
                </div>
              </div>
            </div>
            
            <div class="row justify-content-end">
              <div class="col-sm-10">
                <button type="submit" class="btn btn-primary float-end">????????????</button>
              </div>
            </div>
          </form>
          </c:if>
          
          <c:if test="${normalGuest != null}">
          <form:form commandName="normalGuest" action="update.gt" method="post">
            <div class="row mb-3">
              <label class="col-sm-2 col-form-label font" for="basic-icon-default-fullname">??????</label>
              <div class="col-sm-10">
                <div class="input-group input-group-merge">
                  <span id="basic-icon-default-fullname" class="input-group-text">
                  <i class="bx bx-user"></i></span>
                  <input
                     type="text" name="name" value="${normalGuest.name }"
                     id="basic-default-fullname"
                     class="form-control left"
                     required
                   />
                    
                </div>
              </div>
            </div>
            
            <div class="row mb-3">
              <label class="col-sm-2 col-form-label font" for="basic-icon-default-email">?????????</label>
              <div class="col-sm-10">
                <div class="input-group input-group-merge">
                  <span id="basic-icon-default-email" class="input-group-text">
                  <i class="bx bx-envelope"></i></span>
                  <input
                     type="text" name="email" value="${normalGuest.email }"
                     id="basic-default-fullname"
                     class="form-control left"
                     readonly
                   />
                </div>
              </div>
            </div>
            
            <div class="row mb-3">
              <label class="col-sm-2 col-form-label font" for="basic-icon-default-password">?????? ????????????</label>
              <div class="col-sm-10">
                <div class="input-group input-group-merge">
                  <span id="basic-icon-default-password" class="input-group-text">
                  <i class="bx bxs-lock"></i></span>
                  <input
                     type="password" name="originalPassword" 
                     id="basic-default-fullname"
                     class="form-control left"
                     required
                   />
                </div>
              </div>
            </div>
            
            <div class="row mb-3">
              <label class="col-sm-2 col-form-label font" for="basic-icon-default-new-password">????????? ????????????</label>
              <div class="col-sm-10">
                <div class="input-group input-group-merge">
                  <span id="basic-icon-default-new-password" class="input-group-text">
                  <i class="bx bxs-lock"></i></span>
                  <input
                     type="password" name="password"
                     id="basic-default-fullname"
                     class="form-control left"
                     required
                   />
                </div>
                <div class="err">
                   &nbsp;&nbsp;<form:errors cssClass="err" path="password" />
                </div>
              </div>
            </div>
            
            <div class="row mb-3">
              <label class="col-sm-2 col-form-label font" for="basic-icon-default-phone">????????????</label>
              <div class="col-sm-10">
                <div class="input-group input-group-merge">
                  <span id="basic-icon-default-phone" class="input-group-text">
                  <i class="bx bx-phone"></i></span>
                  <input
                     type="text" name="phone" value="${normalGuest.phone }"
                     id="basic-default-fullname"
                     class="form-control left"
                     required
                   />
                </div>
              </div>
            </div>
            
            <div class="row justify-content-end">
              <div class="col-sm-10">
                <button type="submit" class="btn btn-primary float-end">????????????</button>
              </div>
            </div>
          </form:form>
          </c:if>
          
        </div>
      </div>
    </div>
  </div>
</div>
<!-- / Content -->
</div>
    
<br><br>
</section>

<%@ include file="footer.jsp" %>
   
</body>