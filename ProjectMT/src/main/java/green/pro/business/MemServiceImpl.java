//회원가입,정보수정,탈퇴,로그인,로그아웃,개인미션정보 등을 위한 고객 중심 컨트롤러

package green.pro.business;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import green.pro.vo.MemberVO;

@Service("member")
public class MemServiceImpl implements MemService {

	@Autowired
	SqlSession ss;
	
	private static final String MM = "green.pro.business.MemServiceImpl.";
	
	@Override
	public MemberVO checkLogin(MemberVO vo) {
		return ss.selectOne(MM+"mLogin",vo);
	}
	@Override
	public int joinMember(MemberVO vo) {
		return ss.insert(MM+"mJoin",vo);
	}
	@Override
	public MemberVO showDetailMember(MemberVO vo) {
		return ss.selectOne(MM+"mDetail",vo);
	}
	@Override
	public int deleteMember(MemberVO vo) {
		return ss.delete(MM+"mDelete",vo);
	}
	@Override
	public int updateMember(MemberVO vo) {
		return ss.update(MM+"mUpdate",vo);
	}
//-----------  이 아래는 test 공간입니다.---------------------------	
	
}
