package com.cyworld.util;

import org.springframework.stereotype.Service;

@Service("myUtil")
public class MyUtil_pic {

	//전체 페이지수 구하기
	public int getPageCount(int numPerPage,int dataCount) {

		int pageCount=0;

		pageCount=dataCount/numPerPage;

		if(dataCount%numPerPage!=0) {
			pageCount++;
		}

		return pageCount;
	}

	//페이징 처리 메소드
	public String pageIndexList(int currentPage,int totalPage,String listUrl) {

		int numPerBlock=5;
		int currentPageSetup;
		int page;

		StringBuffer sb=new StringBuffer();

		if(currentPage==0||totalPage==0) {
			return "";
		}

		if(listUrl.indexOf("?")!=-1) {
			listUrl+="&";
		}else {
			listUrl+="?";
		}

		//currentPageSetup(표시한 페이지 -1)
		currentPageSetup=(currentPage/numPerBlock)*numPerBlock;
		if(currentPage%numPerBlock==0) {
			currentPageSetup=currentPageSetup-numPerBlock;
		}

		//◀이전
		if(totalPage>numPerBlock&&currentPageSetup>0) {
			sb.append("<a href=\""+listUrl+"pageNum="+currentPageSetup+"\">◀이전</a>&nbsp;");
			//<a href="list.jsp?pageNum=5">◀이전</a>&nbsp;
		}

		//바로가기 페이지
		page=currentPageSetup+1;

		while(page<=totalPage&&page<=(currentPageSetup+numPerBlock)) {

			if(page==currentPage) {

				sb.append("<font color=\"Fuchisa\">"+page+"</font>&nbsp;");
				//<font color="Fuchisa">9</font>&nbsp;
			}else {
				sb.append("<a href=\""+listUrl+"pageNum="+page+"\">"+page+"</a>&nbsp;");
				//<a href="list.jsp?pageNum=6">6</a>&nbsp;
			}

			page++;
		}

		//다음▶
		if(totalPage-currentPageSetup>numPerBlock) {

			sb.append("<a href=\""+listUrl+"pageNum="+page+"\">다음▶</a>&nbsp;");
			//<a href="list.jsp?pageNum=11">다음▶</a>
		}

		return sb.toString();
	}

	//자바스크립트로 페이지 처리
	public String pageIndexList(int currentPage, int totalPage, String userId, int picNum, int index) {

		int numPerBlock=5;
		int currentPageSetup;
		int n;
		int page;
		String strList="";

		if(currentPage==0) {
			return "";
		}

		//표시할 첫페이지
		currentPageSetup=(currentPage/numPerBlock)*numPerBlock;
		if(currentPage%numPerBlock==0) {
			currentPageSetup=currentPageSetup-numPerBlock;
		}

		//1페이지
		if((totalPage>numPerBlock)&&(currentPageSetup>0)) {
			strList="<a href=\"javascript:listPage(1,'"+userId+"',"+picNum+","+index+");\">1</a>···&nbsp;";
		}

		//◀이전 : 총 페이지수가 numPerBlock 이상인 경우 numPerBlock을 보여줌
		if(totalPage>numPerBlock&&currentPageSetup>0) {
			strList+="<a href=\"javascript:listPage("+currentPageSetup+",\'"+userId+"',"+picNum+","+index+");\">◀이전</a>&nbsp;";
		}

		//바로가기 페이지
		page=currentPageSetup+1;

		while((page<=totalPage)&&(page<=(currentPageSetup+numPerBlock))) {
			if(page==currentPage) {
				strList+="<font color='Fuchsia'>"+page+"</font>&nbsp;";
			}else {
				strList+="<a href=\"javascript:listPage("+page+",'"+userId+"',"+picNum+","+index+");\">"+page+"</a>&nbsp;";
			}
			page++;
		}

		//다음▶
		if(totalPage-currentPageSetup>numPerBlock) {
			strList+="<a href=\"javascript:listPage("+page+",'"+userId+"',"+picNum+","+index+");\">다음▶</a>&nbsp;";
		}
		

		//마지막페이지
		if((totalPage>numPerBlock)&&(currentPageSetup+numPerBlock<totalPage)) {
			strList+="···<a href=\"javascript:listPage("+totalPage+",'"+userId+"',"+picNum+","+index+");\">"+totalPage+"</a>";
		}

		return strList;
	}

}
