package org.example.petstoreapitestingapp.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category{

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private int id;

	public Category(int i, String cats) {
		this.name = cats;
		this.id = i;
	}
	public Category(){}
	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}
}
