//회원가입,정보수정,탈퇴,로그인,로그아웃,개인미션정보 등을 위한 고객 중심 컨트롤러

package green.pro.business;

import green.pro.domain.MemberVO;

public interface MemService {

	public MemberVO checkLogin(MemberVO vo);

	public MemberVO showDetailMember(MemberVO vo);

	public int joinMember(MemberVO vo);

	public int deleteMember(MemberVO vo);

	public int updateMember(MemberVO vo);
//-----------  이 아래는 test 공간입니다.---------------------------
}