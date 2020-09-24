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
	 <!-- MultipartResolver빈 등록 [주의사항 id를 반드시 multipartResolver로 등록해야 한다.] -->
	<beans:bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
		<beans:property name="defaultEncoding" value="UTF-8"></beans:property>
		<beans:property name="maxUploadSize" value="-1" />
		<!-- 업로드 최대 용량은 무제한으로 설정(-1은 무제한) -->
	</beans:bean>
	파일 업로드를 위해 servlet-context.xml에 위 빈을 등록하자.
	  */
	
	@PostMapping("/write")
	public String boardWrite(Model m, 
			HttpServletRequest req,
			@ModelAttribute("board") BoardVO board,
			@RequestParam("mfilename") MultipartFile mfilename) {
		log.info("board==="+board);
		log.info("mfilename==="+mfilename);
		//업로드할 디렉토리의 절대경로 얻어오기
		ServletContext ctx=req.getServletContext();
		String upDir=ctx.getRealPath("/Upload");
		log.info("upDir==="+upDir);
		File dir=new File(upDir);
		if(! dir.exists()) {
			dir.mkdirs(); //디렉토리 생성
		}
		//파일을 첨부했다면
		if(!mfilename.isEmpty()) {//mfilename이 비어있지 않다면(첨부했다면)
			//첨부파일명, 파일크기를 알아내자.
			String fname=mfilename.getOriginalFilename(); //원본 파일명
			long fsize=mfilename.getSize();//파일크기
			//첨부파일이 이미 존재하는 파일일 경우 덮어쓰기를 방지하기 위해
			UUID uuid=UUID.randomUUID(); //랜덤한 문자열을 발생시키기 위해 UUID객체를 얻어오자.
			String str=uuid.toString();
			String fname2=str+"_"+fname; //물리적 파일명
			
			board.setOriginFilename(fname); //원본 파일명    board에 넣는 이유는 DB에서 처리하는게 board에 있기에
			board.setFilename(fname2); //물리적 파일명
			board.setFilesize(fsize);
			
			//파일 업로드 처리 ==> transferTo()를 이용해서 업로드처리
			try {
				///////////////////////////업로드 처리해주는 핵심
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
			n=this.boardSvc.insertBoard(board);//글쓰기
			str="글쓰기 ";
		}else if(mode.equals("rewrite")) {
			n=this.boardSvc.rewriteBoard(board);//답변글쓰기
			str="답변 글쓰기 ";
		}else if(mode.equals("edit")){
			n=this.boardSvc.updateBoard(board);//글수정
			str="글수정 ";
		}
		
		String msg=(n>0)? (str+="성공"):(str+="실패");
		String loc=(n>0)?"list":"javascript:history.back()";
		return util.addMsgLoc(m, msg, loc);
	}
	@GetMapping("list")
	public String boardListPaging(Model model,HttpServletRequest req,
			@ModelAttribute("paging") PagingVO paging) {
		log.info("paging=="+paging);
		int totalCount=boardSvc.getTotalCount(paging);
		paging.setTotalCount(totalCount);
		paging.setPagingBlock(5);//페이징 블럭 단위 설정
		paging.init(req.getSession());//페이징 연산처리 메소드 호출
		log.info("연산 후 paging=="+paging);
		
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
		//1.총 게시글 수
		int totalCount = this.boardSvc.getTotalCount(null);

		//2. 게시 목록
		int pageCount=(totalCount-1)/pageSize+1;
		if(cpage<1) {
			cpage=1;
		}
		if(cpage>pageCount) {
			cpage=pageCount;
		}
		//DB에서 끊어오기 위한 값 구하기
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
		//조회수 증가
		boardSvc.updateReadnum(idx);
		//게시글 가져오기
		BoardVO board = boardSvc.selectBoardByIdx(idx);
		model.addAttribute("board",board);
		return "board/boardView";
		
	}

	/** 첨부파일 다운로드 처리 */
	// ResponseEntity타입: 데이터와 함께 헤더 상태 메시지를 전달하고자 할 때 사용
	// HTTP헤더를 다뤄야 할 경우 ResponseEntity를 통해 헤더정보나 데이터를 전달
	// 할 수 있다.
	@RequestMapping(value = "/fileDown", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> download(HttpServletRequest req,
			@RequestHeader("User-Agent") String userAgent,
			@RequestParam("filename") String fname,
			@RequestParam("origin") String originFilename) {
		log.info("fname=" + fname + ", originFilename==" + originFilename);
		// 업로드된 디렉토리 절대 경로 얻어오기
		String up_dir = req.getServletContext().getRealPath("/Upload");
		//fname:실제 다운로드할 물리적인 파일명
		//originFilename: 사용자에게 보여줄 원본파일명
		String filePath = up_dir + File.separator + fname;
		log.info("filePath=" + filePath);
		FileSystemResource resource = new FileSystemResource(filePath);

		if (!resource.exists()) {
			// 해당 파일이 없다면
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		boolean checkIE = (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1);

		String downloadName = null;
		try {
			if (checkIE) {
				// IE인 경우
				downloadName = URLEncoder.encode(originFilename, "UTF8").replaceAll("\\+", " ");
			} else {
				// 그 외 브라우저인 경우
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
	
	//답변글 달기 form 보여주기
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
