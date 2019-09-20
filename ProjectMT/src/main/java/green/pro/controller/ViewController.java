package green.pro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import green.pro.business.BodService;
import green.pro.vo.BoardVO;
import green.pro.vo.PageVO;
import green.pro.vo.ReplyVO;
import green.pro.vo.StyleVO;

//단순한 페이지 이동을 위한 controller

@Controller
public class ViewController {
	
	@Autowired
	@Qualifier("board")
	public BodService bs;

	
//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― 로그인 폼 출력/김병우
	@RequestMapping("showLoginForm")
	public ModelAndView showLoginForm(ModelAndView mv) {
		mv.setViewName("loginForm");
		return mv;
	}
//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― 회원가입 폼 출력/김병우
	
	@RequestMapping("showJoinForm")
	public ModelAndView showJoinForm(ModelAndView mv) {
		mv.setViewName("member/joinForm");
		return mv;
	}
	
//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	글 작성 폼 출력/김병우
	@RequestMapping("showBoardWriting")
	public ModelAndView showBoardWriting(ModelAndView mv,BoardVO vo) {
		mv.setViewName("board/boardWriting");
		return mv;
	}
//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	
	@RequestMapping("showBoardList")
	public ModelAndView showBoardList(ModelAndView mv) {
		mv.setViewName("board/boardList");
		return mv;
	}
//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	
	@RequestMapping("jidohome") 
	public ModelAndView jidoHome(ModelAndView mv) {
		mv.setViewName("jidohome");
		return mv;
		}
//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	
	@RequestMapping("showInsertForm") 
	public ModelAndView showInsertForm(ModelAndView mv,PageVO pvo) {
		mv.addObject("pvo", pvo);
		mv.setViewName("board/insertForm");
		return mv;
		}
//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	
	@RequestMapping("showInsertReply") 
	public ModelAndView showInsertReply(ModelAndView mv,ReplyVO rvo) {
		System.out.println("여기까지 왔음");
		mv.addObject("rvo", rvo);
		System.out.println(rvo);
		mv.setViewName("board/rereply");
		return mv;
		}
//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	
	@RequestMapping("showAreaMain") 
	public ModelAndView showAreaMain(ModelAndView mv,StyleVO svo,PageVO pvo) {
System.out.println(pvo);
		svo = bs.selectStyle(pvo);
		
		mv.addObject("svo", svo);
		mv.setViewName("area/areaMain");
		return mv;
		}
//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	

}
