package green.pro.mt;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import green.pro.business.*;
import green.pro.vo.*;


@Controller
public class ReplyController {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	Date sys_date = new Date();
	String time;
	
	@Autowired
	@Qualifier("reply")
	RepService rs;

	int result1; // int 결과값을 위한 변수
	int result; // int 결과값을 위한 변수

	HttpSession session; // session값을 위한 변수


//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― R1/댓글작성/김병우
	@RequestMapping("insertReply")
	public ModelAndView insertReply(ModelAndView mv, ReplyVO rvo,HttpServletRequest request) {
	//대응 파라미터 rvo
	
	if(rvo.getR_seq()!=0) {
		rvo.setR_step(1);
		rvo.setR_root(rvo.getR_root());
	}
	
	//내용이 null일때 오류방지
	if(rvo.getR_content()==null) {
		rvo.setR_content("오류방지코드 작동!");
	}
		
	//로그인 아이디 저장. 현재는 테스트용
	// rvo.setR_id((String)session.getAttribute("USERID"));	
	session = request.getSession();
	rvo.setR_id((String)session.getAttribute("USERID"));
		
	//글쓴 시간을 서버에서 정해준다. data 클래스 사용
	time = format.format(sys_date);
	rvo.setR_date(time);

	
System.out.println(rvo);

	//DB에 저장

	result = rs.insertReply(rvo);
	
	//원댓글 r_root=0
	//원댓글이면 seq=root를 강제적으로 해주고
	//댓댓글이면 seq=root가 되어서 들어온다.
	if(rvo.getR_root()==0) {			
		rs.changeRoot(rvo);
		System.out.println("아빠댓글 : root 동기화 성공");
	}else {
		
		System.out.println("아들댓글 : root 동기화 하지 않았습니다.");
	}
	
System.out.println(result); 

		if (result > 0) { 
			
			mv.addObject("insertreply","result");
		} else { 
			mv.setViewName("result");
			mv.addObject("msg","댓글등록 실패!");
		}
	mv.setViewName("jsonView");
	return mv;
	}
//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― R2/댓글삭제/김병우
	@RequestMapping("deleteReply")
	public ModelAndView deleteReply(ModelAndView mv, ReplyVO rvo) {
		System.out.println("딜리트 리플 들어옴");
System.out.println(rvo);
		result = rs.deleteReply(rvo);
System.out.println(result);
		
		if (result > 0) { 
			mv.addObject("deletereply","result");
		} else { 
			mv.setViewName("result");
			mv.addObject("msg","댓글삭제실패");
		}
		mv.setViewName("jsonView");
		return mv;
	}
//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――R3/댓글수정/김병우
	@RequestMapping("updateReply")
	@ResponseBody
	public ModelAndView updateReply(ModelAndView mv, ReplyVO vo, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("업데이트 리플 들어옴");
		System.out.println("vo는 =>"+vo);
		result = rs.updateReply(vo);
		System.out.println("result 는 =>"+result);
		
		if (result > 0) { 
		//	mv.setViewName("board/boardDetail");
			mv.addObject("update","result");
		} else { 
			mv.setViewName(null);
			mv.addObject(null);
		}
		mv.setViewName("jsonView");
		return mv;
	}
	//―――――――――――――――――――――――――――――――――――――――――――――――――――――――― R4/게시글열람시 댓글도 함께 출력/김병우
	public ArrayList<ReplyVO> showReply(BoardVO bvo,PageVO pvo) {
		
		ArrayList<ReplyVO> rList = new ArrayList<ReplyVO>();
		System.out.println(bvo);
		
		rList = rs.showAllReply(pvo);
		System.out.println(bvo);
		
		return rList;
	}
//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― R4/게시글열람시 댓글도 함께 출력/김병우
	@RequestMapping("showMyReply") //내가 쓴 글 보기
	public ModelAndView showMyReply(ModelAndView mv, PageVO pvo,HttpServletRequest request) {
		
		ArrayList<ReplyVO> myReply = new ArrayList<ReplyVO>();
		
		session = request.getSession();
		if(session!=null) {
			pvo.setP_id((String)session.getAttribute("USERID"));
		}else {
			pvo.setP_id("NoUser");
		}

		myReply = rs.showMyReply(pvo);
		System.out.println(myReply);
		
		if(myReply!=null) {
			mv.addObject("myReply", myReply);
			mv.setViewName("board/myReply");
		}else {
			mv.addObject("msg", "내글보기 실패");
			mv.setViewName("result");
		}
		return mv;
	}
	
}
