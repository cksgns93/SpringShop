package com.tis.myshop;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tis.common.util.CommonUtil;
import com.tis.domain.BoardVO;
import com.tis.domain.PagingVO;
import com.tis.service.BoardService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/board")
@Log4j
public class BoardController {
	
	@Inject
	private BoardService boardSvc;
	
	@Inject
	private CommonUtil util;
	
	@GetMapping("/write")
	public String boardForm() {
		return "board/boardWrite";
	}
	/*
	 <!-- MultipartResolver�� ��� [���ǻ��� id�� �ݵ�� multipartResolver�� ����ؾ� �Ѵ�.] -->
	<beans:bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
		<beans:property name="defaultEncoding" value="UTF-8"></beans:property>
		<beans:property name="maxUploadSize" value="-1" />
		<!-- ���ε� �ִ� �뷮�� ���������� ����(-1�� ������) -->
	</beans:bean>
	���� ���ε带 ���� servlet-context.xml�� �� ���� �������.
	  */
	
	@PostMapping("/write")
	public String boardWrite(Model m, 
			HttpServletRequest req,
			@ModelAttribute("board") BoardVO board,
			@RequestParam("mfilename") MultipartFile mfilename) {
		log.info("board==="+board);
		log.info("mfilename==="+mfilename);
		//���ε��� ���丮�� ������ ������
		ServletContext ctx=req.getServletContext();
		String upDir=ctx.getRealPath("/Upload");
		log.info("upDir==="+upDir);
		File dir=new File(upDir);
		if(! dir.exists()) {
			dir.mkdirs(); //���丮 ����
		}
		//������ ÷���ߴٸ�
		if(!mfilename.isEmpty()) {//mfilename�� ������� �ʴٸ�(÷���ߴٸ�)
			//÷�����ϸ�, ����ũ�⸦ �˾Ƴ���.
			String fname=mfilename.getOriginalFilename(); //���� ���ϸ�
			long fsize=mfilename.getSize();//����ũ��
			//÷�������� �̹� �����ϴ� ������ ��� ����⸦ �����ϱ� ����
			UUID uuid=UUID.randomUUID(); //������ ���ڿ��� �߻���Ű�� ���� UUID��ü�� ������.
			String str=uuid.toString();
			String fname2=str+"_"+fname; //������ ���ϸ�
			
			board.setOriginFilename(fname); //���� ���ϸ�    board�� �ִ� ������ DB���� ó���ϴ°� board�� �ֱ⿡
			board.setFilename(fname2); //������ ���ϸ�
			board.setFilesize(fsize);
			
			//���� ���ε� ó�� ==> transferTo()�� �̿��ؼ� ���ε�ó��
			try {
				///////////////////////////���ε� ó�����ִ� �ٽ�
				mfilename.transferTo(new File(upDir,fname2));
				///////////////////////////
			}catch (IOException e) {
				e.printStackTrace();
			}
			
		}//if end-------
		String mode=board.getMode();
		int n=0;
		String str = "";
		if(mode.equals("write")) {
			n=this.boardSvc.insertBoard(board);//�۾���
			str="�۾��� ";
		}else if(mode.equals("rewrite")) {
			n=this.boardSvc.rewriteBoard(board);//�亯�۾���
			str="�亯 �۾��� ";
		}else if(mode.equals("edit")){
			n=this.boardSvc.updateBoard(board);//�ۼ���
			str="�ۼ��� ";
		}
		
		String msg=(n>0)? (str+="����"):(str+="����");
		String loc=(n>0)?"list":"javascript:history.back()";
		return util.addMsgLoc(m, msg, loc);
	}
	@GetMapping("list")
	public String boardListPaging(Model model,HttpServletRequest req,
			@ModelAttribute("paging") PagingVO paging) {
		log.info("paging=="+paging);
		int totalCount=boardSvc.getTotalCount(paging);
		paging.setTotalCount(totalCount);
		paging.setPagingBlock(5);//����¡ �� ���� ����
		paging.init(req.getSession());//����¡ ����ó�� �޼ҵ� ȣ��
		log.info("���� �� paging=="+paging);
		
		List<BoardVO> boardList=boardSvc.selectBoardAllPaging(paging);
		String myctx=req.getContextPath();
		String loc="board/list";
		String naviStr=paging.getPageNavi(myctx, loc);
		
		model.addAttribute("paging",paging);
		model.addAttribute("navi",naviStr);
		model.addAttribute("boardList",boardList);
		
		return "board/boardList3";
	}
	
	@GetMapping("/list_old")
	public String boardList(Model model, @RequestParam(defaultValue="1") int cpage,
			@RequestParam(defaultValue="10") int pageSize) {
		//1.�� �Խñ� ��
		int totalCount = this.boardSvc.getTotalCount(null);

		//2. �Խ� ���
		int pageCount=(totalCount-1)/pageSize+1;
		if(cpage<1) {
			cpage=1;
		}
		if(cpage>pageCount) {
			cpage=pageCount;
		}
		//DB���� ������� ���� �� ���ϱ�
		int end = cpage*pageSize;
		int start = end-(pageSize);
		Map<String, Integer> map = new HashMap<>();
		map.put("start",start);
		map.put("end",end);
		
		List<BoardVO> boardList = this.boardSvc.selectBoardAll(map);
		
		model.addAttribute("totalCount",totalCount);
		model.addAttribute("pageCount",pageCount);
		model.addAttribute("pageSize",pageSize);
		model.addAttribute("boardList",boardList);
		model.addAttribute("cpage",cpage);
		
		return "board/boardList";
	}
	
	@GetMapping("/view")
	public String boardView(Model model,@RequestParam(defaultValue="0") int idx) {
		if(idx==0) {
			return "redirect:list";
		}
		//��ȸ�� ����
		boardSvc.updateReadnum(idx);
		//�Խñ� ��������
		BoardVO board = boardSvc.selectBoardByIdx(idx);
		model.addAttribute("board",board);
		return "board/boardView";
		
	}

	/** ÷������ �ٿ�ε� ó�� */
	// ResponseEntityŸ��: �����Ϳ� �Բ� ��� ���� �޽����� �����ϰ��� �� �� ���
	// HTTP����� �ٷ�� �� ��� ResponseEntity�� ���� ��������� �����͸� ����
	// �� �� �ִ�.
	@RequestMapping(value = "/fileDown", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> download(HttpServletRequest req,
			@RequestHeader("User-Agent") String userAgent,
			@RequestParam("filename") String fname,
			@RequestParam("origin") String originFilename) {
		log.info("fname=" + fname + ", originFilename==" + originFilename);
		// ���ε�� ���丮 ���� ��� ������
		String up_dir = req.getServletContext().getRealPath("/Upload");
		//fname:���� �ٿ�ε��� �������� ���ϸ�
		//originFilename: ����ڿ��� ������ �������ϸ�
		String filePath = up_dir + File.separator + fname;
		log.info("filePath=" + filePath);
		FileSystemResource resource = new FileSystemResource(filePath);

		if (!resource.exists()) {
			// �ش� ������ ���ٸ�
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		boolean checkIE = (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1);

		String downloadName = null;
		try {
			if (checkIE) {
				// IE�� ���
				downloadName = URLEncoder.encode(originFilename, "UTF8").replaceAll("\\+", " ");
			} else {
				// �� �� �������� ���
				downloadName = new String(originFilename.getBytes("UTF-8"), "ISO-8859-1");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=" + downloadName);

		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}//-------------------------------
	
	//�亯�� �ޱ� form �����ֱ�
	@PostMapping("/rewrite")
	public String rewriteForm(Model model,@RequestParam(defaultValue="0") int idx,
			@RequestParam(defaultValue="") String subject) {
		if(idx==0||subject.isEmpty()) {
			return "redirect:list";
		}
		model.addAttribute("idx",idx);
		model.addAttribute("subject",subject);
		return "board/boardRewrite";
	}
}
