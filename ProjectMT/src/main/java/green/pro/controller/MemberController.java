package green.pro.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import green.pro.business.MemService;
import green.pro.vo.MemberVO;



@Controller
public class MemberController {

	@Autowired
	@Qualifier("member")
	MemService ms;

	int result;  //int 결과값을 위한 변수

	HttpSession session; // session값을 위한 변수

//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― M0김병우
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

//―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― 로그인 체크
	@RequestMapping("checkLogin") 
	public ModelAndView checkLogin(ModelAndView mv, MemberVO mvo,HttpServletRequest request) {
		
		//아이디 저장을 위한 세션객체 주입
		session = request.getSession();
		
		//mapper에서 아이디 비밀번호 체킹
		mvo = ms.checkLogin(mvo); 
		
		if (mvo != null) { 
			mv.setViewName("result");
			mv.addObject("msg","로그인 성공");
			
			//세션에 id 저장
			session.setAttribute("USERID", mvo.getM_id());
		} else { 
			mv.setViewName("result");
			mv.addObject("msg","로그인 실패");
		}

		return mv;
	}// m1 end

//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― M2/회원가입/김병우
	@RequestMapping("joinMember") 
	public ModelAndView joinMember(ModelAndView mv, MemberVO mvo, HttpServletRequest request,HttpSession req) throws IllegalStateException, IOException {
		//1.밑에 코딩하기 편하게 이미지 파일을 멀티파트타입으로 저장
		MultipartFile upfile = mvo.getM_upimage();
		//2.파라미터로 온 파일이 저장되는 실제 주소 따기
		ServletContext sc = request.getSession().getServletContext();
		String path = sc.getRealPath("/resources/upload/")+upfile.getOriginalFilename();
		System.out.println(sc.getRealPath("/"));
		System.out.println("파일저장 주소 :"+path);
		
		//String path = "C:/Users/toyob/git/projectMT/ProjectMT/src/main/webapp/resources/image"+upfile.getOriginalFilename();
		//병우 : "C:/Users/toyob/git/projectMT/ProjectMT/src/main/webapp/resources/image"+upfile.getOriginalFilename();
		//규원 : "C:/Users/CherryBerryCherry/eclipse-workspace/projectMT/ProjectMT/src/main/webapp/resources/upload"+upfile.getOriginalFilename();
		//건영 : "C:/MyTest/eclipse-workspace/projectMT/ProjectMT/src/main/webapp/resources/upload"++upfile.getOriginalFilename();
		
		
		//3. 주소에 있는 파일을 파일 객체로 만들기 - 예외처리
		upfile.transferTo(new File(path));
		//4. 업로드될때 참조하는 주소 따기 - DB에 저장되는 주소
		mvo.setM_adrimage("resources/upload/"+upfile.getOriginalFilename());
		result = ms.joinMember(mvo);

		if (result > 0) {
			mv.setViewName("result");
			mv.addObject("msg","회원가입 성공");
		} else {
			mv.setViewName("result");
			mv.addObject("msg","회원가입 실패");
		}
		return mv;
	}

//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― M3/상세정보/김병우
	@RequestMapping("showDetailMember")
	public ModelAndView showDetailMember(ModelAndView mv, MemberVO mvo,HttpServletRequest request) {
		
		session = request.getSession();
		
		if(session!=null) {
			mvo.setM_id((String)session.getAttribute("USERID"));
		}else {
			mvo.setM_id("NoUser");
		}
		
		System.out.println(mvo);
		mvo = ms.showDetailMember(mvo);
		System.out.println(mvo);

		if (mvo != null) { 
			mv.setViewName("member/detailInfo");
			mv.addObject("mvo",mvo);
		} else {
			mv.setViewName("result");
			mv.addObject("msg","회원상세정보 보기 실패");
		}

		return mv;
	}
//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	M4/회원삭제/김병우
	@RequestMapping("deleteMember") 
	public ModelAndView deleteMember(ModelAndView mv, MemberVO mvo) {
		
System.out.println(mvo);
		result = ms.deleteMember(mvo);
System.out.println(result);

		if (result > 0) {
			mv.setViewName("result");
			mv.addObject("msg","회원탈퇴 성공");
		} else {
			mv.setViewName("result");
			mv.addObject("msg","회원탈퇴 실패");
		}

		return mv;
	}
	
//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	M5/정보수정/김병우
		@RequestMapping("updateMember") 
		public ModelAndView updateMember(ModelAndView mv, MemberVO mvo) {
			
System.out.println("정보수정시작 "+mvo);
			result = ms.updateMember(mvo);
System.out.println(result);

			if (result > 0) {
				mv.setViewName("result");
				mv.addObject("msg","회원정보수정 성공");
			} else {
				mv.setViewName("result");
				mv.addObject("msg","회원정보수정 실패");
			}

			return mv;
		}
//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	M6/아이디중복체크/한규원
		
		@RequestMapping("logout") 
		public ModelAndView logout(ModelAndView mv, HttpServletRequest request) {
			SystemClass.getSession(request).invalidate();
			mv.setViewName("result");
			mv.addObject("msg", "로그아웃 성공");
		return mv;	
		}
//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――	M6/아이디중복체크/한규원
		@RequestMapping("idcheck")
		public ModelAndView idCheck(ModelAndView mv, MemberVO mvo) { 
			System.out.println("아이디체크");
		     String id = mvo.getM_id();
		      // or mv.addObject("id", vo.getId());  가능
		     mvo = ms.showDetailMember(mvo);
		      
		      if(mvo != null) { //이미 있는 사용 불가능한 id
		         mv.addObject("idCheck", "UA");
		      } else { //존재하지 않는 id = 사용 가능
		         mv.addObject("idCheck", "A");
		         
		      }
		      // 결과 출력
		      mv.addObject("m_id", id);
		      mv.setViewName("member/idCheck");
		      return mv;
		   } 

}
