//댓글 입력,수정,삭제,호출을 위한 컨트롤러

package green.pro.business;

import java.util.ArrayList;

import green.pro.domain.BoardVO;
import green.pro.domain.PageVO;
import green.pro.domain.ReplyVO;

public interface RepService {

	public int insertReply(ReplyVO vo);

	public int deleteReply(ReplyVO vo);

	public int updateReply(ReplyVO vo);

	public ArrayList<ReplyVO> showMyReply(PageVO pvo);

	public int totalRow(PageVO pvo);
	
	public ArrayList<ReplyVO> showAllReply(PageVO pvo);

	public int changeRoot(ReplyVO rvo);

	public int myTotalRow(PageVO pvo);

	//-----------  이 아래는 test 공간입니다.---------------------------
	
}