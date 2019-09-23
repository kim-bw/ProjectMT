//자유,푸드,여행지게시판의 호출 및 입력,수정,삭제 및 상세보기 기능

package green.pro.business;

import java.util.ArrayList;

import green.pro.domain.BoardVO;
import green.pro.domain.PageVO;
import green.pro.domain.ResultVO;
import green.pro.domain.StyleVO;

public interface BodService {

	public int insertBoard(BoardVO vo);

	public int deleteBoard(BoardVO vo);

	public int updateBoard(BoardVO vo);

	public ArrayList showMyBoard(PageVO pvo);

	public ArrayList<ResultVO> showAllBoard(PageVO pvo);

	public int totalRow(PageVO pvo);

	public ResultVO showDeatailBoard(PageVO pvo);

	public StyleVO selectStyle(PageVO pvo);

	public int myTotalRow(PageVO pvo);


	
//-----------  이 아래는 test 공간입니다.---------------------------

}