package green.pro.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import green.pro.business.ActService;
import green.pro.business.BodService;
import green.pro.business.MemService;
import green.pro.business.RepService;
import green.pro.vo.BoardVO;
import green.pro.vo.MemberVO;
import green.pro.vo.PageVO;
import green.pro.vo.PlaceVO;
import green.pro.vo.ReplyVO;

@Controller
public class TestController {

	@Autowired
	@Qualifier("board")
	public BodService bs;
	
	@Autowired
	@Qualifier("member")
	public MemService ms;
	
	@Autowired
	@Qualifier("reply")
	public RepService rs;
	
	@Autowired
	@Qualifier("action")
	public ActService as;
//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― T1/댓글작성/김병우
	//테스트 홈으로 가기
	@RequestMapping("test-home") 
	public ModelAndView goTestHome(ModelAndView mv) {
		mv.setViewName("test/testhome");
		return mv;
	}
//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― T1/댓글작성/김병우
	@RequestMapping("test-showMyPage") 
	public ModelAndView showMyPage(ModelAndView mv,HttpServletRequest request,PageVO pvo) {
		
		pvo = SystemClass.sessionCheck(pvo, request);
		
		ArrayList<BoardVO> myBoard = new ArrayList<BoardVO>();
		PageVO bod_pvo = SystemClass.countPage(bs, pvo,3);
		myBoard = bs.showMyBoard(pvo);
		
		ArrayList<ReplyVO> myReply = new ArrayList<ReplyVO>();
		PageVO rep_pvo = SystemClass.countPage(rs, pvo,4);
		myReply = rs.showMyReply(pvo);
		
		System.out.println("bod_pvo :"+bod_pvo);
		System.out.println("rep_pvo :"+rep_pvo);
		System.out.println("보드 :"+myBoard);
		System.out.println("댓글 :"+myReply);
		
		mv.setViewName("test/myPage");
		mv.addObject("myBoard", myBoard);
		mv.addObject("myReply", myReply);
		return mv;
	}
//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― T1/댓글작성/김병우
	@RequestMapping("test-goMission") 
	public ModelAndView mission(ModelAndView mv,HttpServletRequest request,MemberVO mvo) {
	
		mvo = SystemClass.sessionCheck(mvo, request);
		
		ArrayList<PlaceVO> pList = new ArrayList<PlaceVO>();

		pList = as.goMission();
System.out.println(pList);

		if(pList!=null) {
			mv.addObject("mvo", mvo); //사용자 정보
			mv.addObject("pList", pList); //랜덤여행지 리스트
			mv.addObject("pListResult", 1); //지도홈을 자주 가기 때문에 이 값이 1일때는 미션버튼을 눌러서 온 것이라고 인식하게 끔
			mv.setViewName("jidohome");
		}else {
			mv.addObject("msg", "미션 날리기 실패");
			mv.setViewName("result");
		}
		return mv;
	}//m end
//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― T1/지도홈/최건영
	

	@RequestMapping("test-jidohome") 
	public ModelAndView gotestjidohome(ModelAndView mv) {
		mv.setViewName("test/testJidohome");
		return mv;
	}	
	
	
}// class end
