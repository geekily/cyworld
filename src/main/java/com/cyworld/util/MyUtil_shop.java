package com.cyworld.util;

public class MyUtil_shop {
	
	//전체 페이지수 구하기
	//numPerPage : 한화면에 표시할 데이터의 갯수
	//dataCount : 전체 데이터의 갯수
	public int getPageCount(int numPerPage, int dataCount){
		
		int pageCount = 0;
		pageCount = dataCount / numPerPage;
		
		if(dataCount % numPerPage != 0)
			pageCount++;
		
		return pageCount;	
		
	}
	
	//페이징 처리 메소드
	//currentPage :현재 표시할 페이지
	//totalPage : 전체 페이지수
	//listUrl : 링크를 설정할 url
	public String pageIndexList(int currentPage, int totalPage, String listUrl){
		
		int numPerBlock = 5; //1◀이전 6 7 8 9 10 다음▶11(6-10까지 표시되는 페이지 갯수)
		int currentPageSetup; //표시할 첫 페이지(6)의 – 1 해준 값(5,10,15,20...)
		int page;
		
		StringBuffer sb = new StringBuffer();
		
		if(currentPage==0 || totalPage==0)	//데이터가 없을 경우
			return "";
		
		//abc.jsp?a=1
		if(listUrl.indexOf("?") != -1)  //주소줄에 ?표가 있다면
			listUrl = listUrl + "&";
		else
			listUrl = listUrl + "?";
		
		//표시할 첫 페이지의 – 1 해준 값
		currentPageSetup = (currentPage/numPerBlock)*numPerBlock;
		
		if(currentPage % numPerBlock == 0)
			currentPageSetup = currentPageSetup - numPerBlock;
		
		//◀이전
		if(totalPage > numPerBlock && currentPageSetup > 0){
						
			sb.append("<a href=\"" + listUrl + "pageNum=" 
					+ currentPageSetup + "\">◀이전</a>&nbsp;");
			
		}
		
		//바로가기 페이지
		page = currentPageSetup + 1;
		
		while(page <= totalPage && page <= (currentPageSetup + numPerBlock)){
			
			if(page == currentPage){				
				
				sb.append("<font color=\"Fuchsia\">" + page + "</font>&nbsp;");				
			
			}else{
				
				sb.append("<a href=\"" + listUrl + "pageNum=" + page + "\">"
						+ page + "</a>&nbsp;");
				
			}
			
			page++;
			
		}		
		
		//다음▶
		if(totalPage - currentPageSetup > numPerBlock){
						
			sb.append("<a href=\"" + listUrl + "pageNum=" + page + "\">다음▶</a>&nbsp;");
			
		}
		
		
		return sb.toString();
		
	}
}
