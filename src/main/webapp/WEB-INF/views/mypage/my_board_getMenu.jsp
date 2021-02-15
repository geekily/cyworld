<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%
%>
<ul>
<c:if test="${menuLength ne 0 }">
	<c:forEach var="i" begin="0" end="${menuList.size()-1 }" step="1">
	<li><font size="2pt">
	<a href="javascript:void(0)" onclick="sendFolder('${menuList.get(i).getFolderName() }','${userId }')">
	${menuList.get(i).getFolderName() }
	</a><img src="${pageContext.request.contextPath}/resources/images/folder_edit.png" width="10px" class="" height="10px" onclick="editFolder('${menuList.get(i).getNum() }','${menuList.get(i).getFolderName() }','${menuList.get(i).getType() }');">
	<img src="${pageContext.request.contextPath}/resources/images/folder_deleted.png" width="10px" height="10px" onclick="deletedFolder('${menuList.get(i).getNum() }','${menuList.get(i).getFolderName() }');">
	</font></li>
	</c:forEach>
</c:if>
</ul>
<img src="${pageContext.request.contextPath}/resources/images/folder_add.png" width="10px" height="10px" onclick="addFolder();">
<div id = "addFolder">
<input type="text" id="addFolderName" style="width: 100px;"/><br/>
<font size="1pt">
<input type="radio" name="setType" value="2" checked="checked">전체공개<br/>
<input type="radio" name="setType" value="1">일촌공개<br/>
<input type="radio" name="setType" value="0">비공개<br/>
</font>
<input type="button" value="추가" onclick="addFolder_ok();"/><input type="button" value="취소" onclick="cancelFolder();"/>
</div>
//${result}