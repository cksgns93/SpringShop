package com.tis.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpSession;

import lombok.Data;

@Data
public class PagingVO {
	// ����¡ ó������ ������Ƽ
	private int cpage;// ���� ������ ������ ��ȣ
	private int pageSize;// �� �������� ������ ��� ����
	private int totalCount;// �� �Խñ� ��
	private int pageCount;// ������ ��

	// DB���� ���ڵ带 ������� ���� ������Ƽ
	private int start;
	private int end;

	// ����¡ �� ó�� ���� ������Ƽ
	private int pagingBlock = 5;
	private int prevBlock; // ���� 5��
	private int nextBlock; // ���� 5��

	// �˻� ���� ������Ƽ
	private String findType;// �˻� ����
	private String findKeyword;// �˻���

	// ����¡ ���� ������ �����ϴ� �޼ҵ�
	public void init(HttpSession ses) {
		if (pageSize < 0) {
			pageSize = 10;
		}
		if (pageSize == 0) {
			// �Ķ���ͷ� pageSize�� �Ѿ���� �ʴ� ��� ���ǿ��� ��������
			Integer ps = (Integer) ses.getAttribute("pageSize");
			if (ps == null) {
				pageSize = 10;
			} else {
				pageSize = ps;
			}
		}
		ses.setAttribute("pageSize", pageSize);

		pageCount = (totalCount - 1) / pageSize + 1;
		if (cpage < 1) {
			cpage = 1;
		}
		if (cpage > pageCount) {
			cpage = pageCount;
		}
		end = cpage * pageSize;
		start = end - pageSize;

		// ����¡ �� ����
		prevBlock = (cpage - 1) / pagingBlock * pagingBlock;
		nextBlock = prevBlock + (pagingBlock + 1);

	}

	/** ������ �׺���̼� ���ڿ��� ��ȯ�ϴ� �޼ҵ� */
	public String getPageNavi(String myctx, String loc) {
		// myctx : ���ؽ�Ʈ��
		// loc : �Խ��� ��� ��� "/board/list"
		// qStr : Query String
		findType = (findType == null) ? "" : findType;
		try {
			findKeyword = (findKeyword == null) ? "" : URLEncoder.encode(findKeyword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}

		String qStr = "?pageSize=" + pageSize + "&findType=" + findType + "&findKeyword=" + findKeyword;
		// String�� �Һ���(immutability) ������ StringBuffer/StringBuilder
		// �� �̿��Ͽ� ���ڿ��� ������ �Ŀ� String���� ����� ��ȯ����.
		StringBuilder buf = new StringBuilder().append("<ul class='pagination'>");
		if (prevBlock > 0) {
			// ���� 5��
			buf.append("<li class='page-item'><a class='page-link' href='" + myctx + "/" + loc + qStr + "&cpage=" + prevBlock + "'>");
			buf.append("Prev");
			buf.append("</a></li>");
		}
		for (int i = prevBlock + 1; i <= nextBlock - 1 && i <= pageCount; i++) {
			String css = "";
			if (i == cpage) {
				css = "active";
			} else {
				css = "";
			}

			buf.append("<li class='page-item " + css + "'><a class='page-link' href='" + myctx + "/" + loc + qStr + "&cpage=" + i + "'>");
			buf.append(i);
			buf.append("</a></li>");
		} // for--------

		if (nextBlock < pageCount) {
			// ���� 5��
			buf.append("<li class='page-item'><a class='page-link' href='" + myctx + "/" + loc + qStr + "&cpage=" + nextBlock + "'>");
			buf.append("Next");
			buf.append("</a></li>");
		}

		buf.append("</ul>");
		String str = buf.toString();
		// System.out.println(str);
		return str;
	}

}
