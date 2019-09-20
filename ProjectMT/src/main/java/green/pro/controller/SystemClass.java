package green.pro.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import green.pro.business.BodService;
import green.pro.business.RepService;
import green.pro.vo.BoardVO;
import green.pro.vo.MemberVO;
import green.pro.vo.PageVO;
import green.pro.vo.ReplyVO;

public class SystemClass {

	
	//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― bs-pvo : row카운팅과 페이지 셋팅
		public static PageVO countPage(BodService bs,PageVO pvo, int num) {
			
			int currentPage = 1;
			int totalPage = 0;
			//현재 페이지가 1이 아니고 1보다 크면(=2~마지막 페이지 = 페이지를 클릭해서 들어왔을 경우)
			if(pvo.getP_curpage() > 1) {       
				currentPage = pvo.getP_curpage(); //현재페이지 = 클릭한 페이지 숫자  
			}					
			//1. pvo에 현제페이지 셋
			pvo.setP_curpage(currentPage);

			//db에 쓰일 시작 글 row번호
			//페이지당 10개를 가져올 것
			// 1페이지의 경우 1* 10 = 10 = row 10 번까지 가져옴
			int start = pvo.getP_curpage() * PageVO.getpOnepage(); 
			//2.pvo에 시작하는 rownum 셋
			pvo.setP_start(start);
			
			//끝나는row번호
			//1페이지의 경우 start=10 이므로 10 - 10 = 0
			//row 0번부터 10번까지 선택
			int end = start - PageVO.getpOnepage();
			
			//3.pvo에 마지막 row넘버 셋
			pvo.setP_end(end+1);
			
			//4. pvo에 해당 게시글 전체 개수 셋
			//num=1 이면  게시글 카운팅
			//num=2이면 댓글 개수 카운팅
			//num=3이면 내 게시글 카운팅
			//num=4이면 내 댓글 카운팅
			
			switch (num) {
			case 1: pvo.setP_totrow(bs.totalRow(pvo));break;
			case 3: pvo.setP_totrow(bs.myTotalRow(pvo));break;
			}
			
			totalPage = pvo.getP_totrow() / PageVO.getpOnepage(); 
			if(pvo.getP_totrow() % PageVO.getpOnepage() != 0) { 
				totalPage++; 
			}	
			//5.pvo에 전체 페이지 개수 셋
			pvo.setP_totpage(totalPage); //=totblock
			pvo.setP_firblock(1); //삭제가능 = 항상 1
			
			return pvo;
		}
	//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― rs-pvo : row카운팅과 페이지 셋팅
		
	public static PageVO countPage(RepService rs,PageVO pvo, int num) {
			
			int currentPage = 1;
			int totalPage = 0;
			//현재 페이지가 1이 아니고 1보다 크면(=2~마지막 페이지 = 페이지를 클릭해서 들어왔을 경우)
			if(pvo.getP_curpage() > 1) {       
				currentPage = pvo.getP_curpage(); //현재페이지 = 클릭한 페이지 숫자  
			}					
			//1. pvo에 현제페이지 셋
			pvo.setP_curpage(currentPage);

			//db에 쓰일 시작 글 row번호
			//페이지당 10개를 가져올 것
			// 1페이지의 경우 1* 10 = 10 = row 10 번까지 가져옴
			int start = pvo.getP_curpage() * PageVO.getpOnepage(); 
			//2.pvo에 시작하는 rownum 셋
			pvo.setP_start(start);
			
			//끝나는row번호
			//1페이지의 경우 start=10 이므로 10 - 10 = 0
			//row 0번부터 10번까지 선택
			int end = start - PageVO.getpOnepage();
			
			//3.pvo에 마지막 row넘버 셋
			pvo.setP_end(end+1);
			
			//4. pvo에 해당 게시글 전체 개수 셋
			//num=1 이면  게시글 카운팅
			//num=2이면 댓글 개수 카운팅
			//num=3이면 내 게시글 카운팅
			//num=4이면 내 댓글 카운팅
			
			switch (num) {
			case 2: pvo.setP_totrow(rs.totalRow(pvo));break;
			case 4: pvo.setP_totrow(rs.myTotalRow(pvo));break;
			}
			
			totalPage = pvo.getP_totrow() / PageVO.getpOnepage(); 
			if(pvo.getP_totrow() % PageVO.getpOnepage() != 0) { 
				totalPage++; 
			}	
			//5.pvo에 전체 페이지 개수 셋
			pvo.setP_totpage(totalPage); //=totblock
			pvo.setP_firblock(1); //삭제가능 = 항상 1
			
			return pvo;
		}
		
	//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― pvo : session에 USERID가 null이면 NoUSer/null이 아니면 id에 셋
		
		public static PageVO sessionCheck(PageVO pvo,HttpServletRequest request) {
			
			HttpSession session = request.getSession();
			String id = (String)session.getAttribute("USERID");
			if(id!=null) {
				pvo.setP_id(id);
			}else {
				pvo.setP_id("NoUser");
			}
			return pvo;
		}
	//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― bvo : session에 USERID가 null이면 NoUSer/null이 아니면 id에 셋
		public static BoardVO sessionCheck(BoardVO bvo,HttpServletRequest request) {
				
				HttpSession session = request.getSession();
				String id = (String)session.getAttribute("USERID");
				if(id!=null) {
					bvo.setB_id(id);
				}else {
					bvo.setB_id("NoUser");
				}
				return bvo;
			}
	//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― mvo : session에 USERID가 null이면 NoUSer/null이 아니면 id에 셋
		public static MemberVO sessionCheck(MemberVO mvo,HttpServletRequest request) {
			
			HttpSession session = request.getSession();
			String id = (String)session.getAttribute("USERID");
			if(id!=null) {
				mvo.setM_id(id);
			}else {
				mvo.setM_id("NoUser");
			}
			return mvo;
		}
	//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― mvo : session에 USERID가 null이면 NoUSer/null이 아니면 id에 셋
		public static ReplyVO sessionCheck(ReplyVO rvo,HttpServletRequest request) {
			
			HttpSession session = request.getSession();
	System.out.println(session.getAttribute("USERID"));

			String id = (String)session.getAttribute("USERID");
	System.out.println(id);

			System.out.println(rvo.getR_id());
			rvo = SystemClass.sessionCheck(rvo, request);
			System.out.println(rvo.getR_id());

			if(id!=null) {
				rvo.setR_id(id);
			}else {
				rvo.setR_id("NoUser");
			}
			return rvo;
		}

	//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― 로그아웃 : 세션 종료시킴
			
		public static HttpSession getSession(HttpServletRequest request) {
			return request.getSession();
		}
		
	//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― 로그아웃 : 세션 종료시킴

		public static PageVO selectName(PageVO pvo) {
			
			switch (pvo.getP_select()) {
			case 1: pvo.setP_selectname("board"); break;
			case 2: pvo.setP_selectname("food");  break;
			case 3: pvo.setP_selectname("place"); break;
			}
			return pvo;
		}
	//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― 로그아웃 : 세션 종료시킴
	public static ModelAndView selectAreaView(ModelAndView mv,PageVO pvo) {
			
			switch (pvo.getP_select()) {
			case 1: mv.setViewName("area/freeBoard"); break;
			case 2: mv.setViewName("area/foodBoard"); break;
			case 3: mv.setViewName("area/placeBoard"); break;
			}
			return mv;
		}
	//――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――― 로그아웃 : 세션 종료시킴
	public static ModelAndView selectBoardView(ModelAndView mv,PageVO pvo) {
		
		switch (pvo.getP_select()) {
		case 1: mv.setViewName("board/boardDetail"); break;
		case 2: mv.setViewName("board/foodDetail"); break;
		case 3: mv.setViewName("board/placeDetail"); break;
		}
		return mv;
	}
	}
