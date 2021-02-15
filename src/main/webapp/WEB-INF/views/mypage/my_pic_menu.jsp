<%@ page  contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style>
input::placeholder {
  color: #EAEAEA;
  font-style: italic;
}
</style>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script type="text/javascript">

	
	
	function PicsOfThisMenu(folderName){
		
		var encodedFolderName=encodeURIComponent(folderName);
		
		var iframePic = document.getElementById("iframePic");
		iframePic.src="${contextPath }/cy/picture/my_r_pic_page.action?folderName="+encodedFolderName;
		
		 var url = "${contextPath }/cy/picture/menuList.action";
		  var value=folderName;
		  
		$.post(url,{folderName:value},function(args){
			$("#menuData").html(args); 
		});
	}
	
	function deleteFolder(folderName){
		
		
		var encodedFolderName=encodeURIComponent(folderName);
		var currentFolderName=document.getElementById("currentFolderName");
		
		var url = "${contextPath }/cy/picture/deleteOneFolder.action";
		var value1=folderName;
		var value2=currentFolderName.value;
			
		if(folderName==currentFolderName.value){
			
			$.post(url,{folderName:value1,currentFolderName:value2},function(args){
				location.replace('my_picture.action?mode=deleteMode&menu=menu3');
			});
		}
			
		 var url = "${contextPath }/cy/picture/deleteOneFolder.action";
		 var value1=folderName;
		 var value2=currentFolderName.value;
		 
		$.post(url,{folderName:value1,currentFolderName:value2},function(args){
			$("#menuData").html(args); 
		});
	}
	

	function openNewFolder() {
		
		var btn1 = document.getElementById("newFolder1");
		var btn2 = document.getElementById("newFolder2");
		
		btn1.style.display="none";
		btn2.style.display="block";

	}
	
	function closeNewFolder() {
		
		var btn1 = document.getElementById("newFolder1");
		var btn2 = document.getElementById("newFolder2");

		btn2.style.display="none";
		btn1.style.display="block";
	}
	
	$(document).ready(function(){
		 $("#addMenu").click(function(){
			 var str=$("#newFolderNameId").val()
			 str = str.replace(/(<([^>]+)>)/gi, "");
			 str = str.replace(/\n/gi, "<br/>");
			 
			 var params = "folderName=" + str + "&currentFolderName=" + $("#currentFolderName").val()
			 	+ "&privacy=" + $("#privacy").val();
			 
			 $.ajax({
				 type:"POST",
				 url:"${contextPath}/cy/picture/createMenu_ok.action",
				 data:params,
				 success:function(args){
					 	$("#menuData").html(args);
				 },
				 beforeSend:checkCreateMenu(),
				 error:function(e){
					 alert(e.responseText);
				 }			 
			 });		 		 
		 });
		 
		 $("#deleteMenu").click(function(){
			 
			 var params = "currentFolderName=" + $("#currentFolderName").val();

			 $.ajax({
				 type:"POST",
				 url:"${contextPath}/cy/picture/showDeleteMenuButton.action",
				 data:params,
				 success:function(args){
					 	$("#menuData").html(args);
					 	
					 	var btn1 = document.getElementById("deleteMenu");
					 	var btn2 = document.getElementById("newFolder");
						var btn3 = document.getElementById("stopDeleteMenu");
						
						btn1.style.display="none";
						btn2.style.display="none";
						btn3.style.display="block";
				 },
				 beforeSend:checkDeleteMenu(),
				 error:function(e){
					 alert(e.responseText);
				 }			 
			 });		 		 
		 });
		$("#stopDeleteMenu").click(function(){
	
			 var params = "folderName=" + $("#currentFolderName").val();

			 $.ajax({
				 type:"POST",
				 url:"${contextPath}/cy/picture/menuList.action",
				 data:params,
				 success:function(args){
					 	$("#menuData").html(args);
					 	
					 	var btn1 = document.getElementById("deleteMenu");
					 	var btn2 = document.getElementById("newFolder");
						var btn3 = document.getElementById("stopDeleteMenu");
						btn2.style.display="block";
						btn3.style.display="none";
						btn1.style.display="block";
				 },
				 error:function(e){
					 alert(e.responseText);
				 }			 
			 });		 		 
		 });
	});
	
	function checkCreateMenu(){

		f = document.main;
		
		var newFolderNameId = document.getElementById("newFolderNameId");
		var str=newFolderNameId.value;
		 str = str.replace(/(<([^>]+)>)/gi, "");
		
		if(!str){
			swal("사진첩 이름을 입력하세요.", "", "${contextPath}/resources/images/cy_logo.png");
			return true;
		}
		
		var privacy = document.getElementById("privacy").value;
		if(privacy==-1){
			swal("공개범위를 선택해 주세요.", "", "${contextPath}/resources/images/cy_logo.png");
			return true;
		}
		
		newFolderNameId.value="";
		
		return false;
	}

	
	function checkDeleteMenu(){
			
			var totalFolderData=${totalFolderData};
			
			if(totalFolderData==0){
				swal("삭제할 사진첩이 없습니다.", "", "${contextPath}/resources/images/cy_logo.png");
				return true;
			}
			
			return false;
		}
	
	/* 사진첩 이름 수정 ---------------------------------------------------------------------------------------------------------------- */
		
	function openEditFolderNameTextBar(index){
		
		for(var i=0;i<${list.size()};i++){
			
			if(i==index){
				document.getElementById("editFolderNameTextBar"+i).style.display="block";
			}else{
				document.getElementById("editFolderNameTextBar"+i).style.display="none";
			}
			
			
		}
	}
	
	function closeEditFolderNameTextBar(index){
		
		var btn1 = document.getElementById("editFolderNameTextBar"+index);
	
		btn1.style.display="none";
	}
	
	function sendEditedFolderName(index, originalFolderNameToBeEdited){
		
		var editedFolderName = document.getElementById("editedFolderName"+index).value;		
		var whetherEditedFolderNameIsCurrentFolder = document.getElementById("whetherEditedFolderNameIsCurrentFolder"+index).value;
		var url = "${contextPath}/cy/picture/editFolderName.action";
		
		if(!editedFolderName){
			swal("수정할 사진첩 이름을 입력하세요.", "", "${contextPath}/resources/images/cy_logo.png");
			return
		}
		
		if(whetherEditedFolderNameIsCurrentFolder=="same"){
			
			var encodedFolderName=encodeURIComponent(editedFolderName);
			var iframePic = document.getElementById("iframePic");
			
			$.post(url,
				{editedFolderName:editedFolderName,
				whetherEditedFolderNameIsCurrentFolder:whetherEditedFolderNameIsCurrentFolder,
				originalFolderNameToBeEdited:originalFolderNameToBeEdited,
				editedPrivacy:document.getElementById("editedPrivacy"+index).value,
				currentFolderName:$("#currentFolderName").val()},
				function(args){
			  		$("#menuData").html(args);
			  		iframePic.src="picture/my_r_pic_page.action?folderName="+encodedFolderName;
			});
			return
		}
		
		if(whetherEditedFolderNameIsCurrentFolder=="different"){
	
			$.post(url,
				{editedFolderName:editedFolderName,
				whetherEditedFolderNameIsCurrentFolder:whetherEditedFolderNameIsCurrentFolder,
				currentFolderName:document.getElementById("currentFolderName").value,
				originalFolderNameToBeEdited:originalFolderNameToBeEdited,
				editedPrivacy:document.getElementById("editedPrivacy"+index).value,
				currentFolderName:$("#currentFolderName").val()},
				function(args){
					$("#menuData").html(args); 
			});
			return
		}
	}
	
	
	function characterCheck() {
		
		 var RegExp = /[\/?:|*<>\"\\]/gi;
		    var obj = document.getElementById("newFolderNameId");
		    if (RegExp.test(obj.value)) {
		        obj.value = obj.value.substring(0, obj.value.length - 1);
		    }
	}
	
	function characterCheckk(index) {
		
		 var RegExp = /[\/?:|*<>\"\\]/gi;
		    var obj = document.getElementById("editedFolderName"+index);
		    if (RegExp.test(obj.value)) {
		        obj.value = obj.value.substring(0, obj.value.length - 1);
		    }
	}
	
	function openToWhom(index){
		
		for(var i=0;i<=2;i++){
			if(i==index){
				document.getElementById('fontOpen'+i).style.fontWeight="bold";
				var spanOpen=document.getElementById('spanOpen'+i);
				spanOpen.style.color="#FF5E00";
				spanOpen.setAttribute("onmouseout","this.style.color='#FF5E00';");
				document.getElementById('privacy').value=index;
			}else{
				document.getElementById('fontOpen'+i).style.fontWeight="normal";
				var spanOpen=document.getElementById('spanOpen'+i)
				spanOpen.style.color="#1294AB";
				spanOpen.setAttribute("onmouseout","this.style.color='#1294AB';");
			}
		}
	}
	
	function editOpenToWhom(privacyNumber,index){
		
		for(var i=0;i<=2;i++){
			if(i==privacyNumber){
				document.getElementById('editedFontOpen'+i+"-"+index).style.fontWeight="bold";
				var editedSpanOpen=document.getElementById('editedSpanOpen'+i+"-"+index);
				editedSpanOpen.style.color="#FF5E00";
				editedSpanOpen.setAttribute("onmouseout","this.style.color='#FF5E00';");
				document.getElementById('editedPrivacy'+index).value=privacyNumber;
			}else{
				document.getElementById('editedFontOpen'+i+"-"+index).style.fontWeight="normal";
				var editedSpanOpen=document.getElementById('editedSpanOpen'+i+"-"+index)
				editedSpanOpen.style.color="#1294AB";
				editedSpanOpen.setAttribute("onmouseout","this.style.color='#1294AB';");
			}
		}
	}


</script>
<form name="main">
	<c:if test="${mode=='normalMode' }">
	<div style="border-top: 3px solid #EBEBEB;border-bottom: 3px solid #EBEBEB;width: 160px;height: 270px;word-break:break-all;overflow: auto;" align="left">
		<c:forEach var="dto" items="${list }" varStatus="status">
		<c:set var="index" value="${status.index }"/>
				<c:if test="${dto.folderName==folderName }">
				<div style="border: 0px solid black;float: left;width: 80%;">
					<img alt="no found" src="${contextPath }/resources/images/folder_open.png" height="15px" width="15px">
					<font  style="font-size:10pt;font-weight: bold;">			
						<a href="javascript:PicsOfThisMenu('${dto.folderName }')" style="text-decoration: none;color: #1294AB;">
							${dto.folderName }
						</a>
					</font>
				</div>
				<div style="border: 0px solid black;float: right;width: 10%;">
						<a href="javascript:openEditFolderNameTextBar(${index })"> <!-- ----------------------------------------------------- -->
							<img alt="no found" src="${contextPath }/resources/images/folder_edit.png" height="13px" width="13px">
						</a>
				</div><br/>
				<div id="editFolderNameTextBar${index }" style="display: none;">
					<div style="float: left;">
						<font  style="font-size:8pt;color: #1294AB;font-weight: bold;">
							사진첩 이름
						</font>
						<input type="text" id="editedFolderName${index }" Placeholder="${dto.folderName }" value="${dto.folderName }" size="13" maxlength="10" onkeydown="characterCheckk(${index})" style="font-size: 7pt;padding-left: 4px;">
					</div>
					<div style="border: 0px solid black;float: left;width: ">
						<c:if test="${dto.privacy==2 }">
							<font id="editedFontOpen2-${index }" style="font-size:8pt;color: #FF5E00;font-weight: bold;">
								<span id="editedSpanOpen2-${index }" onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#FF5E00';"
									onclick="javascript:editOpenToWhom(2,${index})">
										전체공개
								</span>
							</font>
						</c:if>
						<c:if test="${dto.privacy!=2 }">
							<font id="editedFontOpen2-${index }" style="font-size:8pt;color: #1294AB;">
								<span id="editedSpanOpen2-${index }" onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#1294AB';"
									onclick="javascript:editOpenToWhom(2,${index})">
										전체공개
								</span>
							</font>
						</c:if>
						<c:if test="${dto.privacy==1 }">
							<font id="editedFontOpen1-${index }" style="font-size:8pt;color: #FF5E00;font-weight: bold;">
								<span id="editedSpanOpen1-${index }" onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#FF5E00';"
									onclick="javascript:editOpenToWhom(1,${index})">
										일촌공개
								</span>
							</font>
						</c:if>
						<c:if test="${dto.privacy!=1 }">
							<font id="editedFontOpen1-${index }" style="font-size:8pt;color: #1294AB;">
								<span id="editedSpanOpen1-${index }" onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#1294AB';"
									onclick="javascript:editOpenToWhom(1,${index})">
										일촌공개
								</span>
							</font>
						</c:if>
						<c:if test="${dto.privacy==0 }">
							<font id="editedFontOpen0-${index }" style="font-size:8pt;color: #FF5E00;font-weight: bold;">
								<span id="editedSpanOpen0-${index }" onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#FF5E00';"
									onclick="javascript:editOpenToWhom(0,${index})">
										비공개
								</span>							
							</font>
						</c:if>
						<c:if test="${dto.privacy!=0 }">
							<font id="editedFontOpen0-${index }" style="font-size:8pt;color: #1294AB;">
								<span id="editedSpanOpen0-${index }" onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#1294AB';"
									onclick="javascript:editOpenToWhom(0,${index})">
										비공개
								</span>							
							</font>
						</c:if>
						<input type="hidden" id="editedPrivacy${index }" value="${dto.privacy }">
					</div>
					<div align="right" style="float: right;">
						<font style="font-size:8pt;color: #1294AB;font-weight: bold;">
							<span onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#1294AB';"
							onclick="javascript:sendEditedFolderName(${index},'${dto.folderName }')">
								■ 수정
							</span>
						</font>
						<font  style="font-size:8pt;color: #1294AB;font-weight: bold;">
							<span onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#1294AB';"
							onclick="javascript:closeEditFolderNameTextBar(${index})">
								■ 취소
							</span>
						</font>
					</div>
					<br/><br/><br/>
					<div style="padding-top: 5px; border-bottom: 1px solid #EAEAEA;"></div>
					<br/>
				</div>
				<input type="hidden" id="whetherEditedFolderNameIsCurrentFolder${index }" value="same">
				</c:if>
				
				
				<c:if test="${dto.folderName!=folderName }">
					<div style="border: 0px solid black;float: left;width: 80%;">
					<img alt="no found" src="${contextPath }/resources/images/folder_closed.png" height="15px" width="15px">
					<font  style="font-size:9pt;">
						<a href="javascript:PicsOfThisMenu('${dto.folderName }')" style="text-decoration: none;color: #1294AB;">
							${dto.folderName }
						</a>
					</font>
					</div>
					<div style="border: 0px solid black;float: right;width: 10%;">
						<a href="javascript:openEditFolderNameTextBar('${index }')">  <!-- ----------------------------------------------------- -->
							<img alt="no found" src="${contextPath }/resources/images/folder_edit.png" height="13px" width="13px">
						</a>
					</div><br/>
					<div id="editFolderNameTextBar${index }" style="display: none;">
					<div style="float: left;">
						<font  style="font-size:8pt;color: #1294AB;font-weight: bold;">
							사진첩 이름
						</font>
						<input type="text" id="editedFolderName${index }" Placeholder="${dto.folderName }" value="${dto.folderName }" size="13" maxlength="10" style="font-size: 7pt;padding-left: 4px;">
					</div>
					<div style="border: 0px solid black;float: left;width: ">
						<c:if test="${dto.privacy==2 }">
							<font id="editedFontOpen2-${index }" style="font-size:5pt;color: #FF5E00;font-weight: bold;">
								<span id="editedSpanOpen2-${index }" onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#FF5E00';"
									onclick="javascript:editOpenToWhom(2,${index})">
										전체공개
								</span>
							</font>
						</c:if>
						<c:if test="${dto.privacy!=2 }">
							<font id="editedFontOpen2-${index }" style="font-size:5pt;color: #1294AB;">
								<span id="editedSpanOpen2-${index }" onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#1294AB';"
									onclick="javascript:editOpenToWhom(2,${index})">
										전체공개
								</span>
							</font>
						</c:if>
						<c:if test="${dto.privacy==1 }">
							<font id="editedFontOpen1-${index }" style="font-size:8pt;color: #FF5E00;font-weight: bold;">
								<span id="editedSpanOpen1-${index }" onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#FF5E00';"
									onclick="javascript:editOpenToWhom(1,${index})">
										일촌공개
								</span>
							</font>
						</c:if>
						<c:if test="${dto.privacy!=1 }">
							<font id="editedFontOpen1-${index }" style="font-size:8pt;color: #1294AB;">
								<span id="editedSpanOpen1-${index }" onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#1294AB';"
									onclick="javascript:editOpenToWhom(1,${index})">
										일촌공개
								</span>
							</font>
						</c:if>
						<c:if test="${dto.privacy==0 }">
							<font id="editedFontOpen0-${index }" style="font-size:8pt;color: #FF5E00;font-weight: bold;">
								<span id="editedSpanOpen0-${index }" onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#FF5E00';"
									onclick="javascript:editOpenToWhom(0,${index})">
										비공개
								</span>							
							</font>
						</c:if>
						<c:if test="${dto.privacy!=0 }">
							<font id="editedFontOpen0-${index }" style="font-size:8pt;color: #1294AB;">
								<span id="editedSpanOpen0-${index }" onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#1294AB';"
									onclick="javascript:editOpenToWhom(0,${index})">
										비공개
								</span>							
							</font>
						</c:if>
						<input type="hidden" id="editedPrivacy${index }" value="${dto.privacy }">
					</div>
					<div align="right" style="float: right;">
						<font style="font-size:8pt;color: #1294AB;font-weight: bold;">
							<span onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#1294AB';"
							onclick="javascript:sendEditedFolderName(${index},'${dto.folderName }')">
								■ 수정
							</span>
						</font>
						<font  style="font-size:8pt;color: #1294AB;font-weight: bold;">
							<span onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#1294AB';"
							onclick="javascript:closeEditFolderNameTextBar(${index})">
								■ 취소
							</span>
						</font>
					</div>
					<br/><br/><br/>
					<div style="border-bottom: 1px solid #EAEAEA;"></div>
					<br/>
				</div>
				<input type="hidden" id="whetherEditedFolderNameIsCurrentFolder${index }" value="different">
				</c:if>
		</c:forEach>
		<input type="hidden" id="currentFolderName" value="${folderName=='기본 폴더'?'기본 폴더':folderName }"/>
	</div>
	</c:if>
	
	<c:if test="${mode=='deleteMode' }">
	<div style="border-top: 1px solid #EBEBEB;border-bottom: 3px solid #EBEBEB;width: 150px;height: 270px;word-break:break-all;overflow: auto;" align="left">
		<c:forEach var="dto" items="${list }">
				<c:if test="${dto.folderName==folderName }">
					<div style="border: 0px solid black;float: left;width: 80%;">
						<font  style="font-size:10pt;font-weight: bold;color: black;">${dto.folderName }</font>
					</div>
					<div style="border: 0px solid black;float: left;width: 10%;">
						<a href="javascript:deleteFolder('${dto.folderName }')">
							<img alt="no found" src="${contextPath }/resources/images/folder_deleted.png" height="15px" width="15px">
						</a>
					</div><br/>
				</c:if>
				<c:if test="${dto.folderName!=folderName }">
					<div style="border: 0px solid black;float: left;width: 80%;">
						<font  style="font-size:10pt;color: black;">${dto.folderName }</font>
					</div>
					<div style="border: 0px solid black;float: left;width: 10%;">
						<a href="javascript:deleteFolder('${dto.folderName }')">
							<img alt="no found" src="${contextPath }/resources/images/folder_deleted.png" height="15px" width="15px">
						</a>
					</div><br/>
				</c:if>
		</c:forEach>
		<input type="hidden" id="currentFolderName" value="${folderName=='기본 폴더'?'기본 폴더':folderName }"/>
	</div>
	</c:if>
	
</form>

	<div id="newFolder1" style="border: 0px solid black;width: 150px;height: 30px;text-align: left;border: none;">
		<c:if test="${mode!='deleteMode' }">
		<div id="newFolder" style="border: 0px solid black;">
			<font  style="font-size:8pt;color: #1294AB;font-weight: bold;">
				<span  onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#1294AB';"
				onclick="javascript:openNewFolder()">새 사진첩 추가</span>
			</font>
		</div>
		<div id="deleteMenu" style="border: 0px solid black;">
			<font  style="font-size:8pt;color: #1294AB;font-weight: bold;">
				<span  onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#1294AB';">사진첩 삭제</span>
			</font>
		</div>
		</c:if>
	</div>
							
	<c:if test="${mode=='deleteMode' }">
		<div id="stopDeleteMenu" style="border: 0px solid black;text-align: left;padding-left: 13px;">
			<font  style="font-size:8pt;color: #1294AB;font-weight: bold;">
				<span onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#1294AB';" >사진첩 삭제 취소</span>
			</font>
		</div>
	</c:if>
						
							<div id="newFolder2" style="border: 1px solid black;width: 153px;height: 30px;display: none;border: none;padding-top: 5px;" align="left">
								<div style="float: left;">
									<font  style="font-size:8pt;color: #1294AB;font-weight: bold;">사진첩 이름</font><br/>
								</div>
								<div style="padding-left: 60px;">
									<input type="text" id="newFolderNameId" size="12" maxlength="10" style="font-size: 7pt;"
										onkeyup="characterCheck()" onkeydown="characterCheck()">
								</div>
								<div>
									<font  style="font-size:8pt;color: #1294AB;font-weight: bold;">공개범위</font>
								</div>
								<div>
									<font id="fontOpen2" style="font-size:8pt;color: #1294AB;">
										<span id="spanOpen2" onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#1294AB';"
											onclick="javascript:openToWhom(2)">
											전체공개
										</span>
									</font>
									<font id="fontOpen1" style="font-size:8pt;color: #1294AB;">
										<span id="spanOpen1" onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#1294AB';"
											onclick="javascript:openToWhom(1)">
											일촌공개
										</span>
									</font>
									<font id="fontOpen0" style="font-size:8pt;color: #1294AB;">
										<span id="spanOpen0" onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#1294AB';"
											onclick="javascript:openToWhom(0)">
											비공개
										</span>
									</font>
									<input type="hidden" id="privacy" value="-1">
								</div>
								<font  style="font-size:8pt;color: #1294AB;font-weight: bold;">
									<span id="addMenu" onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#1294AB';">■ 추가</span>
								</font>
								<font  style="font-size:8pt;color: #1294AB;font-weight: bold;">
									<span onmouseover="this.style.color='#FF5E00';" onmouseout="this.style.color='#1294AB';"
									onclick="javascript:closeNewFolder()">
										■ 취소
									</span>
								</font>
							</div>
							
							
	<!-- 공개범위 설정 -------------------------------------------------------------------------------------------------- -->




