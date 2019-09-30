package com.app.koios.request;

import com.app.koios.validation.Year;

/**
 * @author unalpolat
 */
public class Request {

	private String name;

	@Year
	private String year;

	public Request(String name, String year) {
		this.name = name;
		this.year = year;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "Request{" +
					 "name='" + name + '\'' +
					 ", year='" + year + '\'' +
					 '}';
	}
}
