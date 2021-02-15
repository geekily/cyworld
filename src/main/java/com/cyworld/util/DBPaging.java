package com.cyworld.util;

public class DBPaging {
	// 전체 페이지 수 구하기
	public int getPageCount(int numPerPage, int dataCount) {
		// 페이지당갯수 , 전체데이터갯수
		int page = 0;

		page = dataCount / numPerPage;

		if (dataCount % numPerPage != 0)
			page++;

		return page;
	}

	public String pageIndexList(int currentPage, int totalPage, String listUrl) {
		int numPerBlock = 5;
		int currentPageSetup;
		int page;

		StringBuffer sb = new StringBuffer();
		if (currentPage == 0 || totalPage == 0) {
			return null;
		}
		if (listUrl.indexOf("?") != -1) {
			listUrl = listUrl + "&";
		} else {
			listUrl = listUrl + "?";
		}
		// currentPageSetup( 표시한 페이지 -1 )
		// 이전버튼 공식	
		
		currentPageSetup = (currentPage / numPerBlock) * numPerBlock;
		if (currentPage % numPerBlock == 0) {
			currentPageSetup = currentPageSetup - numPerBlock;
		}
		if (totalPage > numPerBlock && currentPageSetup > 0) {
			sb.append("<a href=\"" + listUrl + "pageNum=" + currentPageSetup + "\">◀이전　</a>");

		}
		// 바로가기 페이지
		page = currentPageSetup + 1;
		while (page <= totalPage && page <= (currentPageSetup + numPerBlock)) {
			if (page == currentPage) {
				sb.append("<font color='RED'><b>　" + page + "　</b></font>");
			} 
			else {
				sb.append("<a href=\"" + listUrl + "pageNum=" + page + "\">　" + page + "　</a>");
			}
			page++;
		}

		if ((currentPageSetup + numPerBlock + 1) <= totalPage) {
			// (totalPage-currentPageSetup > numPerBlock)
			sb.append("<a href=\"" + listUrl + "pageNum=" + (currentPageSetup + numPerBlock + 1) + "\">　다음▶</a>");
			// ("<a href=\"" + listUrl + "pageNum=" + page + "\"> 다음▶</a>;)"
		}
		return sb.toString();
	}

}
