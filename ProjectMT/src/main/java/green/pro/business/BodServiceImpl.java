//자유,푸드,여행지게시판의 호출 및 입력,수정,삭제 및 상세보기 기능


package green.pro.business;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import green.pro.domain.BoardVO;
import green.pro.domain.PageVO;
import green.pro.domain.ResultVO;
import green.pro.domain.StyleVO;

@Service("board")
public class BodServiceImpl implements BodService {

	@Autowired
	SqlSession ss;

	private static final String BM = "green.pro.business.BodServiceImpl.";
	
	//BC-1
	@Override
	public int insertBoard(BoardVO bvo) {
		return ss.insert(BM+"bInsert",bvo);
	}
	
	//BC-2
	@Override
	public int deleteBoard(BoardVO bvo) {
		return ss.delete(BM+"bDelete",bvo);
	}
	
	//BC-3
	@Override
	public int updateBoard(BoardVO bvo) {
		return ss.update(BM+"bUpdate",bvo);
	}
	
	//BC-4
	@Override
	public ArrayList showMyBoard(PageVO pvo) {
		return (ArrayList)ss.selectList(BM+"bMyBoard",pvo);
	}
	
	//BC-5
	@Override
	public ArrayList<ResultVO> showAllBoard(PageVO pvo) {
		return (ArrayList)ss.selectList(BM+"bAllBoard",pvo);
	}
	
	//BC-6
	@Override
	public int totalRow(PageVO pvo) {
		return ss.selectOne(BM+"bTotalRow",pvo);
	}
	
	//BC-7
	@Override
	public ResultVO showDeatailBoard(PageVO pvo) {
		return ss.selectOne(BM+"bDetailBoard",pvo);
	}
	
	//BC-8
	@Override
	public StyleVO selectStyle(PageVO pvo) {
		return ss.selectOne(BM+"bSelectStyle",pvo);
	}
	
	//BC-9
	@Override
	public int myTotalRow(PageVO pvo) {
		return ss.selectOne(BM+"bMyTotalRow",pvo);
	}
//-----------  이 아래는 test 공간입니다.---------------------------	
}
