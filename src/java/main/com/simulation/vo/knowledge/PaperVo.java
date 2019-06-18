package com.simulation.vo.knowledge;

import com.simulation.model.knowledge.Paper;

public class PaperVo extends Paper {
	private String user_name;

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getKey_wordString() {
		return key_wordString;
	}

	public void setKey_wordString(String key_wordString) {
		this.key_wordString = key_wordString;
	}
	private String key_wordString;
	
}
