<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
    
<head>
   <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
   
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
    
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
   <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>

    <title>Semicolon Reservation page</title>
    
   <style>
      .img-fluid {
         width: 100%;
         height: 330px;
      }
      .form-check {
          display: inline-block;
          min-height: 1.5rem;
          padding-left: 2.5em;
          margin-bottom: 0.125rem;
      }
      .pay {
         float: left;
      }
      .agent-box {
         margin-top: 0px;
      }
      
      .table > :not(caption) > * > * {
          padding: 25px;
       }
       .form-control {
          height: 40px;
       }
       .label {
          text-align: right;
       }
       .modalBtn {
          width: 200px;
          height: 40px;
          color: #8592a3;
          border-color: #8592a3;
          background: transparent;
          display: inline-block;
          font-weight: 400;
          line-height: 1.5;
          text-align: center;
          vertical-align: middle;
          -moz-user-select: none;
          user-select: none;
          padding: 0.4375rem 1.25rem;
          font-size: 0.9375rem;
          border-radius: 0.375rem;
          transition: all .2s ease-in-out;
          border: 1px solid;
       }
       .modalBtn:hover, .pointBtn:hover {
         color: #fff;
         background-color: #8592a3;
      }
      .h60 {
         height: 60px;
      }
      .h80 {
         height: 80px;
      }
      .group {
         height: 30px;
         vertical-align: middle;
         text-align: right;
         padding: auto 0;
         margin: 5px 0;
      }
      .pointBtn {
         width: 120px;
          height: 30px;
          color: #8592a3;
          border-color: #8592a3;
          background: transparent;
          display: inline-block;
          font-weight: 400;
          line-height: 1.5;
          text-align: center;
          vertical-align: middle;
          -moz-user-select: none;
          user-select: none;
          font-size: 0.9375rem;
          border-radius: 0.375rem;
          transition: all .2s ease-in-out;
          border: 1px solid;
      }
      .right {
         text-align: right;
      } 
      .won {
         display: block;
          width: 100%;
          padding: 0.375rem 0.75rem;
          font-size: 1rem;
          font-weight: 400;
          line-height: 1.5;
          color: #212529;
          background-color: #fff;
          background-clip: padding-box;
          appearance: none;
          text-align: right;
      }
      .button-group {
         text-align: right;
      }
      .payfor th{
         border-bottom-width: 0;
         padding-bottom: 0px;
      }
      .d-block {
         background-color:f1f3f5;
      }
      .btn-light-secondary {
         background-color: #e6eaee;
          color: #181e24;
      }
      .small {
         padding-top: 2px;
         font-size: 12px;
      }
      .form-control[readonly] {
         background: #fff;
      }
      .discount {
         display: inline-block;
         text-align: right;
      }
   </style>
   
   <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script>
      $(document).ready(function(){
         
         //disabled => ??????
         /* if($('input[name="coupon_no"]').is(':disabled')) {
            $('#couponDisabled').addClass("Cdisabled");
            $('Cdisabled').css('background-color','#e9ecef');
         }
         
         if($('input[name="st_no"]').is(':disabled')) {
            $('STdisabled').css('background-color','#e9ecef');
         } */

         //??????????????? ?????? ??????
         $('#couponSelect').click(function(){
            
            var c_sel = $('input[name="coupon_no"]:checked').val();
            //alert(c_sel);
            
            var price = $('#house_price').val();
            //alert(price.replace(",",""));
            var housePrice = Number(price.replace(",",""));
            
            var usePoint = $('#myPoint').val();
            
            if(usePoint == "") {
               usePoint = 0;
            }
            var seasonTPrice = $('#seasonT_price').val();
            
            $.ajax({
               url : "coupon.od",
               data : ({
                  selectCouponNo : c_sel,
                  housePrice : price
               }),
               success : function(data) {
                  //alert("?????? ??????");
                  //alert(data);
                  
                  var housePrice = $('#house_price').val();
                  var extraPrice = $('#extra_price').val();
                  var discount = parseInt(data)+parseInt(usePoint)+parseInt(seasonTPrice); 
                  $("#sale_price").html(discount);
                  $("#coupon_price").val(data);
                  $("#totalPrice").html(parseInt(housePrice)+parseInt(extraPrice)-parseInt(discount));
                  
                  //??? ?????? ????????? 0?????? ?????? ??????
                  if(parseInt($('#totalPrice').text()) < 0) {
                     //alert("coupon");
                     alert("??? ?????? ????????? 0?????? ?????? ?????? ?????? ????????? ??? ??? ????????????. \n?????? ????????? ?????? ????????????.");
                     
                     $('#coupon_price').val(0);
                     $('#myPoint').val(0);
                     $('#seasonT_price').val(0);
                     
                     $("#sale_price").html(0);
                     $("#totalPrice").html(parseInt(housePrice)+parseInt(extraPrice));
                  }
                  
                  $('#couponModal').modal('hide');
               },
               error : function() {
                  //alert("?????? ??????");                  
               }
               
            });
            
         });
         
         
         //?????????????????? ?????? ??????
         $('#seasonTSelect').click(function(){
            
            var st_sel = $('input[name="st_no"]:checked').val();
            //alert(st_sel);
            
            var price = $('#totalPrice').text();
            //alert(price.replace(",",""));
            var totalPrice = Number(price.replace(",",""));
            //alert(totalPrice);
            
            var usePoint = $('#myPoint').val();
            if(usePoint == "") {
               usePoint = 0;
            }
            var couponPrice = $('#coupon_price').val();
            
            $.ajax({
               url : "seasonTicket.od",
               data : ({
                  selectSeasonTNo : st_sel,
                  totalPrice : price
               }),
               success : function(data) {
                  //alert("????????? ??????");
                  //alert(data);
                  
                  var housePrice = $('#house_price').val();
                  var extraPrice = $('#extra_price').val();
                  var discount = parseInt(couponPrice)+parseInt(usePoint)+parseInt(data);
                  $("#sale_price").html(parseInt(couponPrice)+parseInt(usePoint)+parseInt(data));
                  $("#seasonT_price").val(data);
                  $("#totalPrice").html(parseInt(housePrice)+parseInt(extraPrice)-parseInt(discount));
                  
                  //??? ?????? ????????? 0?????? ?????? ??????
                  if(parseInt($('#totalPrice').text()) < 0) {
                     //alert("season");
                     alert("??? ?????? ????????? 0?????? ?????? ????????? ?????? ????????? ??? ??? ????????????. \n?????? ????????? ?????? ????????????.");
                     
                     $('#coupon_price').val(0);
                     $('#myPoint').val(0);
                     $('#seasonT_price').val(0);
                     
                     var housePrice = $('#house_price').val();
                     var extraPrice = $('#extra_price').val();
                     
                     $("#sale_price").html(0);
                     $("#totalPrice").html(parseInt(housePrice)+parseInt(extraPrice));
                  }
                  
                  $('#seasonTModal').modal('hide');
               },
               error : function() {
                  //alert("????????? ??????");                  
               }
               
            });
            
         });
         
         //????????? ??? ??????????????? , ?????? -> ??????
         /* $('#totalPrice').on('change',function(){
            var total = $('#totalPrice').val();
            total = numbersWithCommas(total);
            $('#totalPrice').html(total);
         }); */
         
      });
      
      //????????? ???????????? ??????
      function point(mypoint) {
         $('#myPoint').val(mypoint);
         //alert(mypoint);
         
         var couponPrice = $('#coupon_price').val();
         var seasonTPrice = $('#seasonT_price').val();
         
         var housePrice = $('#house_price').val();
         var extraPrice = $('#extra_price').val();
         
         var discount = parseInt(couponPrice)+parseInt(mypoint)+parseInt(seasonTPrice);
         $("#sale_price").html(parseInt(couponPrice)+parseInt(mypoint)+parseInt(seasonTPrice));
         $("#totalPrice").html(parseInt(housePrice)+parseInt(extraPrice)-parseInt(discount));
         
         //??? ?????? ????????? 0?????? ?????? ??????
         if(parseInt($('#totalPrice').text()) < 0) {
            //alert("point ????????????");
            alert("??? ?????? ????????? 0?????? ?????? ????????? ?????? ????????? ??? ??? ????????????. \n?????? ????????? ?????? ????????????.");
            
            $('#coupon_price').val(0);
            $('#myPoint').val(0);
            $('#seasonT_price').val(0);
            
            $("#sale_price").html(0);
            $("#totalPrice").html(parseInt(housePrice)+parseInt(extraPrice));
         }
         
      }
    
      function pointChange() {
         var usePoint = $('#myPoint').val();
         //alert(usePoint);
         
         var couponPrice = $('#coupon_price').val();
         var seasonTPrice = $('#seasonT_price').val();
         
         var housePrice = $('#house_price').val();
         var extraPrice = $('#extra_price').val();
         
         if(usePoint == "") {
            var discount = parseInt(couponPrice)+parseInt(seasonTPrice);
            $("#sale_price").html(parseInt(couponPrice)+parseInt(seasonTPrice));
            $("#totalPrice").html(parseInt(housePrice)+parseInt(extraPrice)-parseInt(discount));
         }
         else {
            var discount = parseInt(couponPrice)+parseInt(usePoint)+parseInt(seasonTPrice);
            $("#sale_price").html(parseInt(couponPrice)+parseInt(usePoint)+parseInt(seasonTPrice));
            $("#totalPrice").html(parseInt(housePrice)+parseInt(extraPrice)-parseInt(discount));
         }
         
         //??? ?????? ????????? 0?????? ?????? ??????
         if(parseInt($('#totalPrice').text()) < 0) {
            //alert("point");
            alert("??? ?????? ????????? 0?????? ?????? ????????? ?????? ????????? ??? ??? ????????????. \n?????? ????????? ?????? ????????????.");
            
            $('#coupon_price').val(0);
            $('#myPoint').val(0);
            $('#seasonT_price').val(0);
            
            $("#sale_price").html(0);
            $("#totalPrice").html(parseInt(housePrice)+parseInt(extraPrice));
         }
      }
      
      //????????????????????? , ??????
      function numbersWithCommas(x) {
         return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      }
      
    </script>
   
</head>
<body>

<%@ include file="header.jsp"%>

<div
  class="hero page-inner overlay"
  style="background-image: url('resources/images/hero_bg_1.jpg')"
>
  <div class="container">
    <div class="row justify-content-center align-items-center">
      <div class="col-lg-9 text-center mt-5">
        <h1 class="heading" data-aos="fade-up">????????????</h1>

        <nav
          aria-label="breadcrumb"
          data-aos="fade-up"
          data-aos-delay="200"
        >
          <ol class="breadcrumb text-center justify-content-center">
            <li class="breadcrumb-item"><a href="detail.gt?house_no=${house_no}">House</a></li>
            <li
              class="breadcrumb-item active text-white-50"
              aria-current="page"
            >
              Reservation
            </li>
          </ol>
        </nav>
      </div>
    </div>
  </div>
</div>

<div class="section">
  <div class="container">
  
  <form action="payfor.od" method="post" >
     <input type="hidden" name="house_no" value="${ob.house_no }"/>
     <input type="hidden" name="gno" value="${guestInfo.gno }"/>
     <input type="hidden" name="order_no" value="${ob.order_no }"/>

  
    <div class="row justify-content-between">
   
   <div class="col-lg-12">
      <table class="table site-block-order-table mb-5">
          <thead>
            <tr>
              <th><h4><b>?????? ??????</b></h4></th>
            </tr>
          </thead>
          
          <tbody>
            <tr>
              <td class="col-sm-5">
              
              	<c:forEach var="img" items="${ob.photo}" varStatus="status">
                  <c:if test="${status.count eq 1}">
                     <img src="${fullpath}/${img }" alt="Image" class="img-fluid"/>
                  </c:if>
                </c:forEach>
              
              </td>
              <td class="col-sm-6">
                <div class="row mb-3">
                 <label class="col-sm-3 col-form-label font label" for="basic-icon-default-fullname">?????? ??????</label>
                    <div class="col-sm-9">
                      <div class="input-group input-group-merge">
                        <span id="basic-icon-default-fullname2" class="input-group-text">
                        <i class="bx bx-user"></i></span>
                        <span id="basic-icon-default-fullname" class="form-control">
                        ${oname }</span>
                          
                      </div>
                    </div>
            </div>
              <div class="row mb-3">
                 <label class="col-sm-3 col-form-label font label" for="basic-icon-default-fullname">?????????</label>
                    <div class="col-sm-9">
                      <div class="input-group input-group-merge">
                        <span id="basic-icon-default-fullname2" class="input-group-text">
                        <i class="bx bx-user"></i></span>
                        <span id="basic-icon-default-fullname" class="form-control vertical">
                        <fmt:parseDate var="checkin" value="${ob.checkin }" pattern="yyyy-MM-dd"/>
                        <fmt:formatDate value="${checkin }" pattern="yyyy-MM-dd"/></span>
                          
                      </div>
                    </div>
            </div>
            <div class="row mb-3">
                 <label class="col-sm-3 col-form-label font label" for="basic-icon-default-fullname">????????????</label>
                    <div class="col-sm-9">
                      <div class="input-group input-group-merge">
                        <span id="basic-icon-default-fullname2" class="input-group-text">
                        <i class="bx bx-user"></i></span>
                        <span id="basic-icon-default-fullname" class="form-control vertical">
                        <fmt:parseDate var="checkout" value="${ob.checkout }" pattern="yyyy-MM-dd"/>
                        <fmt:formatDate value="${checkout }" pattern="yyyy-MM-dd"/></span>
                          
                      </div>
                    </div>
            </div>
            <div class="row mb-3">
                 <label class="col-sm-3 col-form-label font label" for="basic-icon-default-fullname">??????</label>
                    <div class="col-sm-9">
                      <div class="input-group input-group-merge">
                        <span id="basic-icon-default-fullname2" class="input-group-text">
                        <i class="bx bx-user"></i></span>
                        <span id="basic-icon-default-fullname" class="form-control vertical">
                        ${ob.adult }???</span>
                          
                      </div>
                    </div>
            </div>
            <div class="row mb-3">
                 <label class="col-sm-3 col-form-label font label" for="basic-icon-default-fullname">??????</label>
                    <div class="col-sm-9">
                      <div class="input-group input-group-merge">
                        <span id="basic-icon-default-fullname2" class="input-group-text">
                        <i class="bx bx-user"></i></span>
                        <span id="basic-icon-default-fullname" class="form-control vertical">
                        ${ob.kid }???</span>

                      </div>
                    </div>
            </div>
            <div class="row mb-3">
                 <label class="col-sm-3 col-form-label font label" for="basic-icon-default-fullname">?????? ??????</label>
                    <div class="col-sm-9">
                      <div class="input-group input-group-merge">
                        <span id="basic-icon-default-fullname2" class="input-group-text">
                        <i class="bx bx-user"></i></span>
                        <span id="basic-icon-default-fullname" class="form-control vertical">
                        <fmt:formatNumber value="${ob.house_price }" pattern="###,###"/>??? / ${date }???</span>
                      
                      </div>
                    </div>
            </div>
              </td>
            </tr>
          </tbody>
        </table>
   </div>
   <!-- ?????? ?????? (???) -->
   
   
   <div class="col-lg-12">
      <table class="table site-block-order-table mb-5">
          <thead>
            <tr>
              <th><h4><b>?????? ??????</b></h4></th>
            </tr>
          </thead>
          
          <tbody>
            <tr>
              <td class="col-sm-6">
                <div class="row mb-3">
                 <label class="col-sm-3 col-form-label font" for="basic-icon-default-fullname">??????</label>
                    <div class="col-sm-9">
                      <div class="input-group input-group-merge">
                        <input type="text" readonly id="coupon_price" class="form-control right" name="coupon_price" value="0">
                        
                      </div>
                    </div>
            </div>
              <div class="row mb-3">
                 <label class="col-sm-3 col-form-label font" for="basic-icon-default-fullname">?????????</label>
                    <div class="col-sm-9">
                      <div class="input-group input-group-merge">
                        <input type="number" id="myPoint" class="form-control right" name="use_point" value="0"
                        placeholder="0 point" min="0" max="${mypoint }" onchange="pointChange()" required/>
                      </div>
                      <div class="group">
                        <div class="point">
                         <span class="text-black-50">?????? ????????? : ${mypoint } point</span> &nbsp;&nbsp;
                         <button type="button" class="pointBtn" onclick="point('${mypoint}')">?????? ??????</button>
                        </div>
                      </div>
                    </div>
            </div>
            <div class="row mb-3">
                 <label class="col-sm-3 col-form-label font" for="basic-icon-default-fullname">?????????</label>
                    <div class="col-sm-9">
                      <div class="input-group input-group-merge">
                        <input type="text" readonly id="seasonT_price" class="form-control right" name="st_price" value="0">
                        
                      </div>
                    </div>
            </div>
              </td>
              
              <td class="col-sm-6">
                 <div class="row mb-3">
                <!-- Button modal -->
                <button type="button" class="modalBtn" data-bs-toggle="modal" data-bs-target="#couponModal">
                   ?????? ??????
                </button>
               
                 <!-- Modal -->
                 <div class="modal fade" id="couponModal" tabindex="-1" style="display: none;" aria-hidden="true" role="dialog">
                   <div class="modal-dialog modal-dialog-centered" role="document">
                     <div class="modal-content">
                       <div class="modal-header">
                         <h5 class="modal-title" id="modalCenterTitle">??? ?????????</h5>
                           <button type="button" id="modalClose" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                           </button>
                       </div>
                       <div class="modal-body">
                       
                       <c:if test="${empty couponList}" >
                          <div class="row">
                           <div class="col mb-3">
                             <div class="input-group">
                                  ???????????? ???????????????.
                             </div>
                           </div>
                         </div>
                       </c:if>
                       
                       <c:if test="${couponList != null }" >
                       <c:forEach var="coupon" items="${couponList}">
                        
                         <div class="row">
                           <div class="col mb-3">
                             <div class="input-group h60">
                               <div class="input-group-text">
                             <input class="form-check-input" type="radio" id="${coupon.coupon_no }" name="coupon_no" value="${coupon.coupon_no }" <c:if test="${coupon.use == 0}"> disabled="disabled" </c:if> />
                          </div>
                          <div class="form-control h60" >
                             <label for="${coupon.coupon_no }" >
                             ${coupon.name }  ( <c:if test='${coupon.percent != 0}'>${coupon.percent}%</c:if><c:if test='${coupon.sale != 0}'>${coupon.sale}???</c:if> ?????? )
                             <br>
                             <fmt:parseDate var="period" value="${coupon.period }" pattern="yyyy-MM-dd"/>
                             <span class="small">???????????? : ~<fmt:formatDate value="${period }" pattern="yyyy??? MM??? dd???"/>?????? </span>
                             </label>
                          </div>
                             </div>
                           </div>
                         </div>
                       
                       </c:forEach>
                       </c:if>
                       
                       
                       </div>
                       <div class="modal-footer">
                         <button type="button" class="btn btn-light-secondary" data-bs-dismiss="modal">??????</button>
                         <button type="button" id="couponSelect" class="btn btn-primary">??????</button>
                       </div>
                     </div>
                   </div>
                 </div>
                 <!-- Modal ??? -->
             </div>
             
             <div class="row mb-3 h80">&nbsp;<br></div>
             
             <div class="row mb-3">
                <!-- Button modal -->
                <button type="button" class="modalBtn" data-bs-toggle="modal" data-bs-target="#seasonTModal">
                   ????????? ??????
                </button>
               
                 <!-- Modal -->
                 <div class="modal fade" id="seasonTModal" tabindex="-1" style="display: none;" aria-hidden="true" role="dialog">
                   <div class="modal-dialog modal-dialog-centered" role="document">
                     <div class="modal-content">
                       <div class="modal-header">
                         <h5 class="modal-title" id="modalCenterTitle">?????? ?????????</h5>
                           <button type="button" id="modalClose" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                           </button>
                       </div>
                       <div class="modal-body">
                       
                       <c:if test="${empty seasonTList}" >
                          <div class="row">
                           <div class="col mb-3">
                             <div class="input-group">
                                  ????????? ???????????? ????????????.
                             </div>
                           </div>
                         </div>
                       </c:if>
                       
                       <c:if test="${seasonTList != null}" >
                       <c:forEach var="seasonT" items="${seasonTList}">
                       
                         <div class="row">
                           <div class="col mb-3">
                             <div class="input-group">
                               <div class="input-group-text">
                             <input class="form-check-input" type="radio" id="seasonT_${seasonT.st_no }" name="st_no" value="${seasonT.st_no }" <c:if test="${seasonT.money == 0}"> disabled="disabled" </c:if> />
                          </div>
                          <div class="form-control h60 STdisabled">
                             <label for="seasonT_${seasonT.st_no }">
                             <fmt:formatNumber value="${seasonT.ticket_choice }" /> ??????
                             <br>
                             <span class="small">( ?????? ?????? : <fmt:formatNumber value="${seasonT.money }"/>??? ) </span>
                             </label>
                          </div>
                             </div>
                           </div>
                         </div>
                       
                       </c:forEach>
                       </c:if>
                       
                       
                       </div>
                       <div class="modal-footer">
                         <button type="button" class="btn btn-light-secondary" data-bs-dismiss="modal">??????</button>
                         <button type="button" id="seasonTSelect" class="btn btn-primary" >??????</button>
                       </div>
                     </div>
                   </div>
                 </div>
                 <!-- Modal ??? -->
             </div>
              </td>
              
            </tr>
          </tbody>
        </table>
   </div>
   <!-- ?????? ?????? (???) -->
   
   <div class="col-lg-12">
      <table class="table site-block-order-table mb-5">
          <thead>
            <tr>
              <th><h4><b>?????? ??????</b></h4></th>
            </tr>
          </thead>
          
          <tbody>
             <tr class="payfor">
               <th colspan="2" class="col-sm-12"><h5>????????????</h5></th>
             </tr>
         <tr>             
               <td colspan="2" class="col-sm-12">
               <div class="d-block agent-box p-5">
            <div class="div">
               <div class="row mb-3">
                     <div class="form-check">
                       <input name="wpay" class="form-check-input" type="radio" value="??????/????????????" id="defaultRadio1" checked>
                       <label class="form-check-label" for="defaultRadio1">
                         ??????/????????????
                       </label>
                     </div>
                 </div>
                 <div class="row">
                     <div class="form-check">
                       <input name="wpay" class="form-check-input" type="radio" value="???????????????" id="defaultRadio2">
                       <label class="form-check-label" for="defaultRadio2">
                         ????????? ??????
                       </label>
                     </div>
                 </div>
             </div>
            </div>
            </td>
             </tr>
            
            <tr>
              <th class="col-sm-5"><h5>????????????</h5></th>
              <td class="col-sm-6">
                <div class="row mb-3">
                 <label class="col-sm-3 col-form-label font" for="basic-icon-default-fullname">????????????</label>
                    <div class="col-sm-9">
                      <div class="input-group input-group-merge">
                        <span id="basic-icon-default-fullname" class="won">
                        <fmt:formatNumber value="${ob.house_price }" pattern="###,###"/> ???</span>
                        <input type="hidden" name="house_price" id="house_price" value="${ob.house_price }"/>
                        
                      </div>
                    </div>
            </div>
              <div class="row mb-3">
                 <label class="col-sm-3 col-form-label font" for="basic-icon-default-fullname">??????????????????</label>
                    <div class="col-sm-9">
                      <div class="input-group input-group-merge">
                        <span id="basic-icon-default-fullname" class="won">
                        <fmt:formatNumber value="${extra_price }"/> ???</span>
                        <input type="hidden" name="extra_price" id="extra_price" value="${extra_price }"/>
                      
                      </div>
                    </div>
            </div>
            <div class="row mb-3">
                 <label class="col-sm-3 col-form-label font" for="basic-icon-default-fullname">????????????</label>
                    <div class="col-sm-9">
                      <div class="input-group input-group-merge">
                        <span id="basic-icon-default-fullname" class="won">
                        (-) <span id="sale_price">0</span> ???
                        </span>
                        
                        <!-- <input type="hidden" name="discount_price" value="" /> -->
                      
                      </div>
                    </div>
            </div>
            <div class="row mb-3">
                 <label class="col-sm-3 col-form-label font" for="basic-icon-default-fullname">?????????????????????</label>
                    <div class="col-sm-9">
                      <div class="input-group input-group-merge">
                        <span id="basic-icon-default-fullname" class="won">
                        (+) <fmt:formatNumber value="${totalPoint }"/> point</span>
                      </div>
                      <input type="hidden" name="totalPoint" value="${totalPoint }" />
                    </div>
            </div>
            
            <hr>
            
            <div class="row mb-3">
                 <label class="col-sm-3 col-form-label font" for="basic-icon-default-fullname"><strong>??? ????????????</strong></label>
                    <div class="col-sm-9">
                      <div class="input-group input-group-merge">
                        <span id="basic-icon-default-fullname" class="won">
                        <strong><font id="totalPrice" color="red"><fmt:formatNumber value="${totalPrice }"/></font> ???</strong></span>
                        <!-- <input type="hidden" name="totalPrice" value=""/> -->
                        
                      </div>
                    </div>
            </div>
              </td>
            </tr>
          </tbody>
        </table>
   </div>
   <!-- ?????? ?????? (???) -->
   
   </div>
   
   <div class="button-group">
      <button type="reset" class="btn btn-light-secondary me-1 mb-1">????????????</button>
      <button type="submit" class="btn btn-primary">????????????</button>
   </div>
  
  </form>
   
   
  </div>
</div>

<%@ include file="footer.jsp"%>
</body>