package dummydata;

import java.util.ArrayList;
import java.util.List;

import com.intut.luckylottery.domain.Lottery;

public class GetDummyData {

	public static List<Lottery> getLotteryData() {
		List<Lottery> list = new ArrayList<Lottery>();
		Lottery type = new Lottery();
		type.setId(1);
		type.setName("Monthly");
		type.setType("Monthly");
		list.add(type);
		type = new Lottery();
		type.setId(2);
		type.setType("Bumper");
		type.setName("Diwali");
		list.add(type);
		type = new Lottery();
		type.setId(3);
		type.setType("Bumper");
		type.setName("Holi");
		list.add(type);
		return list;
	}

}
