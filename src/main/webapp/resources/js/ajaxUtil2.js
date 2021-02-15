function getXMLHttpRequest(){	
	if(window.XMLHttpRequest){
		return new XMLHttpRequest();  //Non-Microsoft Browser
	}else if(window.ActiveXObject){   //Microsoft Borwer
		try{
			return ActiveXObject("Msxml2.XMLHTTP");    // version5.0 이후
		}catch(e){ 
			return ActiveXObject("Microsoft.XMLHTTP"); // version5.0 이전
		}
	}
}

//Ajax요청

var httpRequest = null;

function sendRequest(url, params, callback, method){
	
	//XMLHttpRequest 객체 생성
	httpRequest = getXMLHttpRequest();
	
	//Method처리
	var httpMethod = method ? method : "GET";
	
	if(httpMethod!="GET" && httpMethod!="POST"){
		httpMethod ="GET";
	}
	
	//Data처리	
	var httpParams = (params==null || params=="") ? null : params;
	
	//URL처리
	var httpUrl = url;
	if(httpMethod=="GET" && httpParams != null) {
		httpUrl += "?"+httpParams;
	}
	httpRequest.open(httpMethod,httpUrl,true);
	httpRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");  //post방식일 수도있으니.
	httpRequest.onreadystatechange = callback;
	httpRequest.send(httpMethod=="POST"?httpParams:null);
}
