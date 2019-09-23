package green.pro.domain;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BoardVO {

	private int b_city;           
	private int b_seq;			
	private int b_select;			
	private String b_title;		
	private String b_id;			
	private Date b_date;		//글쓴날짜	
	private Date b_updateDate;
	private String b_content;		//글내용
	private int b_cnt;			//조회수
	private MultipartFile b_upimage;	//이미지파일
	private String b_adrimage;		//이미지 주소
	
	
	
}
