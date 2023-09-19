<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품목록</title>
</head>
<%-- selProduct.jsp --%>
<body>
   <h3>상품목록</h3>
   <hr>
    <% 
        request.setCharacterEncoding("UTF-8");
        
        //userName을 세션에 저장하자!!
        //왜? 로그인폼에서 (입력)전달된 값을 같은 브라우저에서 유지하기 위해!!
        //로그인폼을 통해 username을 전달 받았을때
       

        //장바구니 비우기 요청시 장바구니를 비우자
    %>

   []님이 로그인한 상태입니다.<br><br>
   <form action="add.jsp" method="post">
      <select name="product">
        <option value="사과">사과</option>      
        <option>수박</option>      
        <option>딸기</option>      
        <option>바나나</option>      
        <option>복숭아</option>      
      </select>
      <button>추가</button>
   </form>
   <br><br>
   <a href="checkOut.jsp">장바구니 보기</a>
</body>
</html>





