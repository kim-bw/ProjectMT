package green.pro.domain;
import lombok.Data;

@Data
public class PlaceVO extends FoodVO{

	private int l_city;
	private int l_seq;
	private int l_select;
	private String l_name;
	private String l_address;
	private String l_detailaddress;
	private int l_areacode;
	private String l_location;
	private int l_good;
	private int l_bad;
	private int l_point;
	private int l_rank;
	

}
