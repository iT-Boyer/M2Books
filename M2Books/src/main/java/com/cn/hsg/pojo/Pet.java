package com.cn.hsg.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class Pet {

	
	private String name; 
	private Calendar birthday; 
	private Pet friend;
	
	//监听对象属性
	private int fetchCount;
	
	public Pet() {}
	
	public Pet(String name) {
		this.name = name; 
	}
	public static Pet getInstance(ResultSet rs) throws SQLException{
		Pet pet = new Pet();
		pet.name = rs.getString("name");
		pet.birthday =(Calendar) rs.getObject("birthday");
		return pet;
		}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getBirthday() {
		return birthday;
	}

	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}

	public Pet getFriend() {
		return friend;
	}

	public void setFriend(Pet friend) {
		this.friend = friend;
	}

	public int getFetchCount() {
		return fetchCount;
	}

	public void setFetchCount(int fetchCount) {
		this.fetchCount = fetchCount;
	}
	
	public void onFetch() { 
		this.fetchCount++;
	}
}
