package com.example.springbatchguide.processor;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

public class Teacher {
	@Id
	private Long id;
	private String name;

	protected Teacher() {}

	public Teacher(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
