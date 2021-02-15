<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setCharacterEncoding("UTF-8");
String cp = request.getContextPath();
%>
<%
	
%>
<!DOCTYPE html>
<html>
<head><link rel="stylesheet" href="${resourcePath }/img${userFontCss}"/>
<meta charset="UTF-8">
<title>★${userName }님의 미니홈피★</title>
<link rel="stylesheet" href="//apps.bdimg.com/libs/jqueryui/1.10.4/css/jquery-ui.min.css">
<style>
  [id*='blue'] {
    float: left;
    clear: left;
    width: 140px;
    height: 2px;
    margin-top: 9px;
    margin-left: 5px;
    float: left;
  }
  #volumeDiv {
    float: left;
    clear: left;
    width: 60px;
    height: 2px;
    margin-top: 7px;
    margin-left: 5px;
  }
  
  iframe {
  	border-width: 0px;
  }

  [id*='volumeDiv'] .ui-slider-range { background: #8ae234; }
  [id*='volumeDiv'] .ui-slider-handle { border-color: #8ae234;height: 10px;width: 8px;}
  [id*='blue'] .ui-slider-range { background: #ff6600; }
  [id*='blue'] .ui-slider-handle { border-color: #ff6600;height: 10px;width: 8px;}
  
  
  
</style>
<script src="//apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="//apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
<script type="text/javascript">
	
	function clieckedPause(index){
	
	    var currentPlayer=document.getElementById('audioPlayer'+index);
	    var pauseButtonDiv=document.getElementById('pauseButtonDiv'+index);
	    var playButtonDiv=document.getElementById('playButtonDiv'+index);
	
	    if(currentPlayer.paused){
	        currentPlayer.play();
	        pauseButtonDiv.style.display="block";
	        playButtonDiv.style.display="none";
	
	    }else{
	        currentPlayer.pause();
	        pauseButtonDiv.style.display="none";
	        playButtonDiv.style.display="block";
	    }
	}

	function nextPlay(index){
		
		var currentPlayerDiv=document.getElementById('audioPlayerDiv'+index);
		var currentPlayer=document.getElementById('audioPlayer'+index);
		
		currentPlayerDiv.style.display="none";
		currentPlayer.currentTime = 0;
		
		if(${songList.size()}-1==index){
			var firstPlayerDiv=document.getElementById('audioPlayerDiv'+(${songList.size()-1}-index));
			var firstPlayer=document.getElementById('audioPlayer'+(${songList.size()-1}-index));
			
			firstPlayerDiv.style.display="block";
			firstPlayer.play();
			return;
		}
		
		var nextPlayerDiv=document.getElementById('audioPlayerDiv'+(index+1));
		var nextPlayer = document.getElementById('audioPlayer'+(index+1));

		nextPlayerDiv.style.display="block";
		nextPlayer.play();
		  
	}
	
	function clickedNextPlay(index){
		
		var currentPlayerDiv=document.getElementById('audioPlayerDiv'+index);
		var currentPlayer=document.getElementById('audioPlayer'+index);
		
		currentPlayerDiv.style.display="none";
		currentPlayer.currentTime = 0;
		currentPlayer.pause();
		
		if(${songList.size()}-1==index){
			var firstPlayerDiv=document.getElementById('audioPlayerDiv'+(${songList.size()-1}-index));
			var firstPlayer=document.getElementById('audioPlayer'+(${songList.size()-1}-index));
			
			firstPlayerDiv.style.display="block";
			firstPlayer.play();
			return;
		}
		
		var nextPlayerDiv=document.getElementById('audioPlayerDiv'+(index+1));
		var nextPlayer = document.getElementById('audioPlayer'+(index+1));

		nextPlayerDiv.style.display="block";
		nextPlayer.play();
		
	}
	
	function clickedPreviousPlay(index){
		
		var currentPlayerDiv=document.getElementById('audioPlayerDiv'+index);
		var currentPlayer=document.getElementById('audioPlayer'+index);
		
		currentPlayerDiv.style.display="none";
		currentPlayer.currentTime = 0;
		currentPlayer.pause();
		
		if(index==0){
			var lastPlayerDiv=document.getElementById('audioPlayerDiv'+(${songList.size()}-1));
			var lastPlayer=document.getElementById('audioPlayer'+(${songList.size()}-1));
			
			lastPlayerDiv.style.display="block";
			lastPlayer.play();
	
			return;
		}
		
		var previousPlayerDiv=document.getElementById('audioPlayerDiv'+(index-1));
		var previousPlayer = document.getElementById('audioPlayer'+(index-1));

		previousPlayerDiv.style.display="block";
		previousPlayer.play();
	}
	
	function changeAttributeIntoRandomPlay(){
		
		for(var i=0;i<${songList.size()};i++){
			document.getElementById('buttonNextPlay'+i).setAttribute("onClick","randomPlay("+i+")");
			document.getElementById('audioPlayer'+i).setAttribute("onEnded","randomPlay("+i+")");
		}
		document.getElementById('randomPlayDiv').style.display="none";
		document.getElementById('regularPlayDiv').style.display="block";
	}
	
	function changeAttributeIntoRegularPlay(){
		
		for(var i=0;i<${songList.size()};i++){
			document.getElementById('buttonNextPlay'+i).setAttribute("onClick","clickedNextPlay("+i+")");
			document.getElementById('audioPlayer'+i).setAttribute("onEnded","nextPlay("+i+")");
		}
		document.getElementById('randomPlayDiv').style.display="block";
		document.getElementById('regularPlayDiv').style.display="none";
	}
	
	function randomPlay(index){
		
		var currentPlayerDiv=document.getElementById('audioPlayerDiv'+index);
		var currentPlayer=document.getElementById('audioPlayer'+index);
		
		currentPlayerDiv.style.display="none";
		currentPlayer.currentTime = 0;
		currentPlayer.pause();
		
		do{
			var randomNum=Math.floor(Math.random()*${songList.size()});
		}while(randomNum==index)
		
		var randomPlayerDiv=document.getElementById('audioPlayerDiv'+randomNum);
		var randomPlayer = document.getElementById('audioPlayer'+randomNum);

		randomPlayerDiv.style.display="block";
		randomPlayer.play();
	}
	
	$(function(){
		setInterval(function(){
			for(var i=0;i<${songList.size()};i++){
				$( "#blue"+i ).slider({
					orientation: "horizontal",
					range: "min",
					max: document.getElementById('audioPlayer'+i).duration,
					change: playingTimeOfSong(i)
				});
				$( "#blue"+i ).slider( "value", document.getElementById('audioPlayer'+i).currentTime);
				$( "#volumeDiv").slider({
					orientation: "horizontal",
					range: "min",
					max: 100
				});
				$( "#volumeDiv" ).slider( "value", document.getElementById('audioPlayer'+i).volume*100);
			}
		},1000);
		
	});
	
	$(document).ready(function(){
		$("[id^='blue']").click(function(){
			for(var i=0;i<${songList.size()};i++){
				var currentPlayer=document.getElementById('audioPlayer'+i);
				currentPlayer.currentTime=$( "#blue"+i ).slider( "value" );
			}
		});
		$("#volumeDiv").click(function(){
			for(var i=0;i<${songList.size()};i++){
				var currentPlayerb=document.getElementById('audioPlayer'+i);
				currentPlayerb.volume=$( "#volumeDiv" ).slider( "value" )/100;
			}
		});
		$("[id^='buttonSound']").click(function(){
			for(var i=0;i<${songList.size()};i++){
				var buttonSoundDiv=document.getElementById('buttonSoundDiv'+i);
				buttonSoundDiv.style.display="none";
				var buttonMuteDiv=document.getElementById('buttonMuteDiv'+i);
				buttonMuteDiv.style.display="block";
				var currentPlayer=document.getElementById('audioPlayer'+i);
				currentPlayer.muted=true;
			}
		});
		$("[id^='buttonMute']").click(function(){
			for(var i=0;i<${songList.size()};i++){
				var buttonSoundDiv=document.getElementById('buttonSoundDiv'+i);
				buttonSoundDiv.style.display="block";
				var buttonMuteDiv=document.getElementById('buttonMuteDiv'+i);
				buttonMuteDiv.style.display="none";
				var currentPlayer=document.getElementById('audioPlayer'+i);
				currentPlayer.muted=false;
			}
		});
	});
	
	function playingTimeOfSong(index) {
		var duration=Math.floor(document.getElementById('audioPlayer'+index).duration);
		var currentTime=Math.floor(document.getElementById('audioPlayer'+index).currentTime);
		var time=Math.floor(currentTime/60)+":"+leadingZeros(currentTime%60,2)+" / "+Math.floor(duration/60)+":"+leadingZeros(duration%60,2);
		$("#playingTimeSpan"+index).html(time);
	}
	
	function leadingZeros(n, digits) {
		var zero='';
		n=n.toString();

		if(n.length < digits) {
			for(var i=0;i<digits-n.length;i++)
			zero+='0';
		}
		  
		return zero + n;
	}

	
</script>
</head>
<body style="background: url(${resourcePath }/img/${imageFilePath}) no-repeat;">
	<table border="0" align="left" valign="top" cellspacing="0"	cellpadding="0">
		<tr>
			<td>
				<iframe src="user_home.action?userId=${userId }" width="1060" height="550" style="position:absolute; top: 10px; left:33px;"></iframe>
			</td>
		</tr>
	</table>
	<c:if test="${songList.size()==0 }">
			<div align="center" style="position:absolute; top: 20px; left:800px; background-color: #EAEAEA; height: 60px; width: 220px;
				border: 1px solid black;padding-top: 5px;" >
				<div style="text-align: left;padding-left: 8px;background-color: #F6F6F6;border: 2px solid #BDBDBD;height: 20px; width: 200px;">
					<div style="float: left;">
						<img alt="no found" src="<%=cp%>/resources/images/musicPlayer_cd.png" height="13px" width="13px" style="padding-top: 3px;"> 
					</div>
					<div style="margin-left: 20px;">
						<font  style="font-size:9pt;color: #5D5D5D;">
							재생할 음악이 없습니다.
						</font>
					</div>
				</div>
			</div>
	</c:if>
	
	<c:if test="${songList.size()!=0 }">
	<div align="center" style="position:absolute; top: 20px; left:800px; background-color: #EAEAEA; height: 60px; width: 220px;
		border: 1px solid black;padding-top: 5px;" >
		<c:forEach var="dto" items="${songList }" varStatus="status">
		<c:set var="index" value="${status.index }"/>
			<c:if test="${dto.songOrder==1 }">
				<div id="audioPlayerDiv${index }" >
					<div style="display: none;">
						<audio autoplay="autoplay" onended="nextPlay(${index });" id="audioPlayer${index }" 
							src="/img${dto.originalFileName }"></audio>
					</div>
					<div style="text-align: left;padding-left: 8px;background-color: #F6F6F6;border: 2px solid #BDBDBD;height: 20px; width: 200px;">
						<div style="float: left;">
							<img alt="no found" src="<%=cp%>/resources/images/musicPlayer_cd.png" height="13px" width="13px" style="padding-top: 3px;"> 
						</div>
						<div style="margin-left: 20px;">
							<font  style="font-size:9pt;color: #5D5D5D;">
								${dto.originalFileName.substring(dto.originalFileName.lastIndexOf("/")+1,dto.originalFileName.lastIndexOf(".")) }
							</font>
						</div>
					</div>
					<div id="blue${index }"></div>
					<div style="float: right;padding-right: 5px;">
						<font  style="font-size:9pt;color: #5D5D5D;">
							<span id="playingTimeSpan${index }"></span>
						</font>
					</div>
					<div style="padding-top: 15px;">
						<div id="pauseButtonDiv${index }" style="float: left;margin-left: 10px;">
							<img alt="no found" src="<%=cp%>/resources/images/musicPlayer_pause.png" height="17px" width="14px" 
								onclick="clieckedPause(${index });" style="padding-top: 1px;">
						</div>
						<div id="playButtonDiv${index }" style="float: left;margin-left: 10px;display: none;width: 14px;">
							<img alt="no found" src="<%=cp%>/resources/images/musicPlayer_play.png" height="11px" width="11px" 
								onclick="clieckedPause(${index });" style="padding-top: 4px;">
						</div>
						<div style="float: left;margin-left: 10px;">
							<img alt="no found" src="<%=cp%>/resources/images/musicPlayer_previous.png" height="13px" width="13px" 
								onclick="clickedPreviousPlay(${index });" style="padding-top: 3px;">
						</div>
						<div style="float: left;margin-left: 10px;">
							<img alt="no found" src="<%=cp%>/resources/images/musicPlayer_next.png" height="13px" width="13px"
								id="buttonNextPlay${index }" onclick="clickedNextPlay(${index });" style="padding-top: 3px;">
						</div>
						<div style="float: left;margin-left: 10px;width: 11px;">
							&nbsp;&nbsp;
						</div>
						<div id="buttonSoundDiv${index }" style="float: left;margin-left: 10px;">
							<img alt="no found" src="<%=cp%>/resources/images/musicPlayer_sound.png" height="13px" width="13px"
								id="buttonSound${index }" onclick="clickedSound(${index });" style="padding-top: 3px;">
						</div>
						<div id="buttonMuteDiv${index }" style="float: left;margin-left: 10px;display: none;">
							<img alt="no found" src="<%=cp%>/resources/images/musicPlayer_mute.png" height="13px" width="13px"
								id="buttonMute${index }" onclick="clickedMute(${index });" style="padding-top: 3px;">
						</div>
						<div style="float: left;margin-left: 3px;">
							
						</div>
					</div>
						
						
				</div>
			</c:if>
			<c:if test="${dto.songOrder!=1 }">
				<div id="audioPlayerDiv${index }" style="display: none;">
					<div style="display: none;">
						<audio onended="nextPlay(${index });" id="audioPlayer${index }" 
							src="/img${dto.originalFileName }"></audio>
					</div>
					<div style="text-align: left;padding-left: 8px;background-color: #F6F6F6;border: 2px solid #BDBDBD;height: 20px; width: 200px;">
						<div style="float: left;">
							<img alt="no found" src="<%=cp%>/resources/images/musicPlayer_cd.png" height="13px" width="13px" style="padding-top: 3px;"> 
						</div>
						<div style="margin-left: 20px;">
							<font  style="font-size:9pt;color: #5D5D5D;">
								${dto.originalFileName.substring(dto.originalFileName.lastIndexOf("/")+1,dto.originalFileName.lastIndexOf(".")) }
							</font>
						</div>
					</div>
					<div id="blue${index }"></div>
					<div style="float: right;padding-right: 5px;">
						<font  style="font-size:9pt;color: #5D5D5D;">
							<span id="playingTimeSpan${index }"></span>
						</font>
					</div>
					<div style="padding-top: 15px;">
						<div id="pauseButtonDiv${index }" style="float: left;margin-left: 10px;">
							<img alt="no found" src="<%=cp%>/resources/images/musicPlayer_pause.png" height="17px" width="14px" 
								onclick="clieckedPause(${index });" style="padding-top: 1px;">
						</div>
						<div id="playButtonDiv${index }" style="float: left;margin-left: 10px;display: none;width: 14px;">
							<img alt="no found" src="<%=cp%>/resources/images/musicPlayer_play.png" height="11px" width="11px" 
								onclick="clieckedPause(${index });" style="padding-top: 4px;">
						</div>
						<div style="float: left;margin-left: 10px;">
							<img alt="no found" src="<%=cp%>/resources/images/musicPlayer_previous.png" height="13px" width="13px" 
								onclick="clickedPreviousPlay(${index });" style="padding-top: 3px;">
						</div>
						<div style="float: left;margin-left: 10px;">
							<img alt="no found" src="<%=cp%>/resources/images/musicPlayer_next.png" height="13px" width="13px"
								id="buttonNextPlay${index }" onclick="clickedNextPlay(${index });" style="padding-top: 3px;">
						</div>
						<div style="float: left;margin-left: 10px;width: 11px;">
							&nbsp;&nbsp;
						</div>
						<div id="buttonSoundDiv${index }" style="float: left;margin-left: 10px;">
							<img alt="no found" src="<%=cp%>/resources/images/musicPlayer_sound.png" height="13px" width="13px"
								id="buttonSound${index }" onclick="clickedSound(${index });" style="padding-top: 3px;">
						</div>
						<div id="buttonMuteDiv${index }" style="float: left;margin-left: 10px;display: none;">
							<img alt="no found" src="<%=cp%>/resources/images/musicPlayer_mute.png" height="13px" width="13px"
								id="buttonMute${index }" onclick="clickedMute(${index });" style="padding-top: 3px;">
						</div>
						<div style="float: left;margin-left: 3px;">
							
						</div>
					</div>
				</div>
			</c:if>
		</c:forEach>
		<div id="volumeDiv" style="position: absolute;left: 115px;top: 45px;"></div>
		<img id="randomPlayDiv" alt="no found" src="<%=cp%>/resources/images/musicPlayer_regular.png" height="11px" width="11px"
								onclick="changeAttributeIntoRandomPlay();" style="position: absolute;left: 80px;top: 48px;">
		<img id="regularPlayDiv" alt="no found" src="<%=cp%>/resources/images/musicPlayer_random.png" height="11px" width="11px"
								onclick="changeAttributeIntoRegularPlay();" style="position: absolute;left: 80px;top: 48px;display: none;">
	</div>
	</c:if>
	
</body>