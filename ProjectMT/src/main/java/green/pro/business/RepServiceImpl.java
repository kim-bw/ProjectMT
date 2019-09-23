//댓글 입력,수정,삭제,호출을 위한 컨트롤러

package green.pro.business;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import green.pro.domain.BoardVO;
import green.pro.domain.PageVO;
import green.pro.domain.ReplyVO;

@Service("reply")
public class RepServiceImpl implements RepService {

	@Autowired
	SqlSession ss;
	
	private static final String RM = "green.pro.business.RepServiceImpl.";
	
	@Override
	public int insertReply(ReplyVO rvo) {
		return ss.insert(RM+"rInsert",rvo);
	}
	@Override
	public int deleteReply(ReplyVO rvo) {
		return ss.delete(RM+"rDelete",rvo);
	}
	@Override
	public int updateReply(ReplyVO rvo) {
		return ss.update(RM+"rUpdate",rvo);
	}
	@Override
	public ArrayList<ReplyVO> showMyReply(PageVO pvo) {
		return (ArrayList)ss.selectList(RM+"rMyReply",pvo);
	}
	@Override
	public ArrayList<ReplyVO> showAllReply(PageVO pvo) {
		return (ArrayList)ss.selectList(RM+"rAllReply",pvo);
	}
	@Override
	public int totalRow(PageVO pvo) {
		return ss.selectOne(RM+"rTotalRow",pvo);
	}
	@Override
	public int changeRoot(ReplyVO rvo) {
		return ss.update(RM+"rChangeRoot",rvo);
	}
	@Override
	public int myTotalRow(PageVO pvo) {
		return ss.selectOne(RM+"rMyTotalRow",pvo);
	}
	
//-----------  이 아래는 test 공간입니다.---------------------------
	
}
