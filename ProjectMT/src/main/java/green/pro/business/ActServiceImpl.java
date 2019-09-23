//미션 등 여러가지 게임 요소를 작동을 위한 컨트롤러

package green.pro.business;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import green.pro.domain.PlaceVO;

@Service("action")
public class ActServiceImpl implements ActService {

	@Autowired
	private SqlSession ss;
	
	//mapper 위치
	private static final String AM = "green.pro.business.ActServiceImpl.";
	
	@Override
	public ArrayList<PlaceVO> goMission(){
		return (ArrayList)ss.selectList(AM+"aGoMission");
	}
	
	
}
