package green.pro.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import green.pro.business.BodService;
import green.pro.business.RepService;
import green.pro.vo.BoardVO;
import green.pro.vo.PageVO;
import green.pro.vo.ReplyVO;
import green.pro.vo.ResultVO;
import green.pro.vo.StyleVO;

@Controller
public class BoardController {
	//날짜 포맷을 아래와 같은 형식으로 지정
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//Date객체 생성
	Date sys_date = new Date();
	String time;
	//Board Service
	@Autowired
	@Qualifier("board")
	public BodService bs;
	
	//Reply Service
	@Autowired
	@Qualifier("reply")
	public	RepService rs;
	
	//int로 return되는 결과값 저장용
	public int result;

//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	
	//BC-1.게시글 등록하기
	//자유,맛집,여행지 게시판 글 등록을 1개의 컨트롤러 공유함
	//input : bvo(id,city,select)
	//output : result
	//from : board/insertForm.jsp
	// to : result.jsp
//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	
	@RequestMapping("insertBoard")
	public ModelAndView insertBoard(ModelAndView mv, BoardVO bvo,HttpServletRequest request) {
		
		bvo = SystemClass.sessionCheck(bvo, request);
		System.out.println(bvo.getB_id());
		
		//시간을 위에 지정한 format형식으로 저장
		time = format.format(sys_date);
		//bvo에 셋
		bvo.setB_date(time);
		
		//mapper
		result = bs.insertBoard(bvo);
	
		if (result > 0) { 
			mv.setViewName("result");
			mv.addObject("msg","글 등록 성공");
		} else { 
			mv.setViewName("result");
			mv.addObject("msg","글 등록 실패");
		}
		return mv;
}

//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	
	//BC-2.게시글 삭제하기
	//USERID와 같다면 글 삭제 버튼이 노출
	//input : bvo(seq,city,select)
	//output : result
	//from : board/boardDetail.jsp
	// to : result.jsp
//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	
	@RequestMapping("deleteBoard")
	public ModelAndView deleteBoard(ModelAndView mv, BoardVO bvo) {
		
System.out.println(bvo);
		result = bs.deleteBoard(bvo);
System.out.println(result);

		if (result > 0) { 
			mv.setViewName("result");
			mv.addObject("msg","게시글이 삭제되었습니다.");
		} else { 
			mv.setViewName("result");
			mv.addObject("msg","게시글이 삭제를 실패하였습니다.");
		}
		return mv;
	}

//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	
	//BC-3.게시글 수정폼 띄우기(내용포함)
	//USERID와 같다면 글 수정 버튼이 노출
	//input : pvo(seq,city,select)
	//output : bvo
	//from : board/boardDetail.jsp
	// to : board/updateForm.jsp
//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	
	@RequestMapping("showUpdateBoardForm")
	public ModelAndView showUpdateBoardForm(ModelAndView mv, PageVO pvo) {
		
		//p_select(1~3)에 따라 쿼리문에 치환될 문자를 pvo에 셋팅
		pvo = SystemClass.selectName(pvo);
		
		BoardVO bvo = bs.showDeatailBoard(pvo);

		if (bvo!=null) { 
			mv.setViewName("board/updateForm");
			mv.addObject("bvo",bvo);
		} else { 
			mv.setViewName("result");
			mv.addObject("msg","게시글 불러오기 실패");
		}
		return mv;
	}

	
//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	
	//BC-4.게시글 수정하기(3번 폼에서)
	//수정폼에서 제목과 내용을 수정함
	//input : bvo
	//output : result
	//from : board/updateForm.jsp
	// to : result
//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	
	@RequestMapping("updateBoard")
	public ModelAndView updateBoard(ModelAndView mv, BoardVO bvo) {

		//시스템 시간으로 b_date 셋
		time = format.format(sys_date);
		bvo.setB_date(time);
		
		//오류방지 : b_adrimage가 null이면 noimage 삽입해서 오류방지
		if(bvo.getB_adrimage()==null) {
			bvo.setB_adrimage("noimage");
		}
		
		result = bs.updateBoard(bvo);

		if (result>0) {
			mv.setViewName("result");
			mv.addObject("msg", "게시글 수정에 성공하였습니다.");
		} else {
			mv.setViewName("result");
			mv.addObject("msg", "게시글 수정 실패");
		}
		return mv;

	}

//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	
		//BC-5.게시글 리스트 출력
		//자유,맛집,여행지 모두 1개 컨트롤러 공유
		//input : bvo
		//output : result
		//from : board/boardDetail.jsp
		// to : result
//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	
	@RequestMapping("selectBoard")
	public ModelAndView selectBoard(ModelAndView mv,PageVO pvo,StyleVO svo) {
		
		ArrayList<ResultVO> list = new ArrayList<ResultVO>();
		
		//p_select에 따라 p_selectname이 셋팅되어 문자열치환
		pvo = SystemClass.selectName(pvo);
		
		//p_select에 따라 파라미터를 보낼 게시판을 선택하여 setView
		mv = SystemClass.selectAreaView(mv, pvo);

		//p_curPage에 따라 전체 게시글 수와 페이징 정보 세팅
		pvo = SystemClass.countPage(bs, pvo,1);
	
		//pvo의 정보에 따라 게시글 list 리턴
		list = bs.showAllBoard(pvo); 
		
		//p_city에 따라 지역에 따른 스타일 선택
		svo = bs.selectStyle(pvo);
	
		mv.addObject("pvo", pvo); //pvo 페이지 정보
		mv.addObject("svo", svo); //svo 스타일 정보
		mv.addObject("list", list); //list 게시판리스트
	
		return mv;
	}

//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	
		//BC-6.게시글 상세 보기
		//자유,맛집,여행지 모두 1개 컨트롤러 공유. 글 내용과 댓글들 보기
		//input : bvo
		//output : Rvo,pvo,list(ReplyVO)
		//from : board/boardDetail.jsp
		// to : result
//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
	@RequestMapping("showDetailBoard")

	public ModelAndView detailBoard(ModelAndView mv, BoardVO bvo, PageVO pvo,HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");

		//p_select에 따라 파라미터를 보낼 게시판을 선택해서 mv에 셋
		mv = SystemClass.selectBoardView(mv, pvo);
		
		//p_select에 따라 mapper에서 문자열 치환에 필요한 selectname을 선택
		pvo = SystemClass.selectName(pvo);
		
		//pvo의 정보에 따라 조건에 맞는 Rvo 1개 선택
		//Rvo는 ResultVO이며 모든 게시판의 최하 자손 => 모든 멤버변수 사용가능 =>mapper에서 resultType으로 사용가능(mapper에서 resulttype은 1개만 사용가능해서 상속한 것임:우리는 1개 mapper에서 3개를 다룸)
		ResultVO Rvo = bs.showDeatailBoard(pvo);
		
		//pvo의 정보에 따라 댓글개수 카운트 및 페이징 정보 세팅
		pvo = SystemClass.countPage(rs,pvo,2);
		
		//댓글의 ArrayList를 위한 생성
		ArrayList<ReplyVO> list = new ArrayList<ReplyVO>();
		
		//mapper에서 해당 게시판종류-지역번호-글번호에 따른 댓글 선택하여 리스트에 저장
		list = rs.showAllReply(pvo);
		
		if(list!=null) {
			mv.addObject("list", list); //댓글 리스트
			mv.addObject("Rvo", Rvo); //글1개 bvo
			mv.addObject("pvo", pvo); //페이지 정보
		}else {
			mv.setViewName("result");
			mv.addObject("msg", "상세글보기 실패");
		}
		
		return mv;
	}
	

//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	
			//BC-7.내가 쓴 게시물 보기
			//자유,맛집,여행지 모두 1개 컨트롤러 공유. 글 내용과 댓글들 보기
			//input : pvo
			//output : 
			//from : board/boardDetail.jsp
			// to : result
//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
	@RequestMapping("showMyBoard")
	public ModelAndView showMyBoard(ModelAndView mv, PageVO pvo,HttpServletRequest request) {
		
		//내가 쓴 board 보기위한 리스트
		ArrayList<BoardVO> myList = new ArrayList<BoardVO>();
		
		//내 id를 조건으로 모든 게시판 카운팅하여 pvo 셋
		pvo = SystemClass.sessionCheck(pvo, request);

		//mapper를 통해 모든 board게시판 탐색하여 내 글 리스트로 저장
		myList = bs.showMyBoard(pvo);
		
		
		if(myList!=null) {
			mv.addObject("myList", myList);
			mv.setViewName("board/myBoard");
		}else {
			mv.addObject("msg", "내글보기 실패");
			mv.setViewName("result");
		}
		return mv;
	}
		
//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― 내 정보 페이지 보기

//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	
	

		

}
