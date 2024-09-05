package org.example.petstoreapitestingapp.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TagsItem{

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private long id;

	public String getName(){
		return name;
	}

	public long getId(){
		return id;
	}
}