package com.cyworld.util;

public class DBPaging {
	// ��ü ������ �� ���ϱ�
	public int getPageCount(int numPerPage, int dataCount) {
		// �������簹�� , ��ü�����Ͱ���
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
		// currentPageSetup( ǥ���� ������ -1 )
		// ������ư ����	
		
		currentPageSetup = (currentPage / numPerBlock) * numPerBlock;
		if (currentPage % numPerBlock == 0) {
			currentPageSetup = currentPageSetup - numPerBlock;
		}
		if (totalPage > numPerBlock && currentPageSetup > 0) {
			sb.append("<a href=\"" + listUrl + "pageNum=" + currentPageSetup + "\">��������</a>");

		}
		// �ٷΰ��� ������
		page = currentPageSetup + 1;
		while (page <= totalPage && page <= (currentPageSetup + numPerBlock)) {
			if (page == currentPage) {
				sb.append("<font color='RED'><b>��" + page + "��</b></font>");
			} 
			else {
				sb.append("<a href=\"" + listUrl + "pageNum=" + page + "\">��" + page + "��</a>");
			}
			page++;
		}

		if ((currentPageSetup + numPerBlock + 1) <= totalPage) {
			// (totalPage-currentPageSetup > numPerBlock)
			sb.append("<a href=\"" + listUrl + "pageNum=" + (currentPageSetup + numPerBlock + 1) + "\">��������</a>");
			// ("<a href=\"" + listUrl + "pageNum=" + page + "\"> ������</a>;)"
		}
		return sb.toString();
	}

}
