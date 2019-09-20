<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>

</head>
<body>
<h2>freeboard list</h2>
<table width = "800" border = "-1">
	<tr align = "center" height = "30">
		<td>b_city</td>
		<td>b_seq</td>
		<td>b_title</td>
		<td>b_id</td>
		<td>b_date</td>
		<td>b_content</td>
		<td>b_cnt</td>
		<td>bl.b_select</td>
	</tr>
	<c:forEach var = "bl" items = "${list}">
		<tr align = "left" height = "30">
			<td>${bl.b_city}</td>
			<td>${bl.b_seq}</td>
			<td><a href = "showDetailBoard?p_city=${bl.b_city}&p_select=${bl.b_select}&p_seq=${bl.b_seq}&p_curpage=1">${bl.b_title}</a></td>
			<td>${bl.b_id}</td>
			<td>${bl.b_date}</td>
			<td>${bl.b_content}</td>
			<td>${bl.b_cnt}</td>
			<td>${bl.b_select}</td>
		</tr>
	</c:forEach>
</table>
<hr>
<c:forEach var="i" begin="1" end="${pvo.p_totpage}">
	<c:choose>
		<c:when test="${pvo.p_curpage==i}">
			<font size="5" color="red"> ${i} </font>&nbsp;
		</c:when>
		
		<c:otherwise>
			<a href="selectBoard?p_curpage=${i}&p_select=${pvo.p_select}&p_city=${pvo.p_city}">${i}</a>&nbsp;
		</c:otherwise>
	</c:choose>
</c:forEach>
<c:if test="${USERID!=null}"><a href="showInsertForm?p_city=${pvo.p_city}&p_select=${pvo.p_select}">글쓰기</a></c:if>

</body>
</html>