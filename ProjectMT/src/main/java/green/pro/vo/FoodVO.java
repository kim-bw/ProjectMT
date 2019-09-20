package green.pro.vo;
import lombok.Data;

@Data
public class FoodVO extends BoardVO{

	private int f_city;
	private int f_seq;
	private int f_select;
	private String f_name;
	private String f_address;
	private String f_detailaddress;
	private int f_areacode;
	private String f_location;
	private int f_good;
	private int f_bad;
	private int f_point;
	private int f_rank;
	private String f_adrimage;
	
}
