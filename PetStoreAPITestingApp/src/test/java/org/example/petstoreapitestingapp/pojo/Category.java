package org.example.petstoreapitestingapp.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category{

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