package org.example.petstoreapitestingapp.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TagsItem{

	@JsonProperty("name")
	private String name;
	@JsonProperty("id")
	private int id;

	public String getName(){
		return name;
	}
	public int getId(){
		return id;
	}

	public TagsItem(int id, String name) {
		this.name = name;
		this.id = id;
	}
}
