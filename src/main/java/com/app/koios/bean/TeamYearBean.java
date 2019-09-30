package com.app.koios.bean;

/**
 * @author unalpolat
 */
public class TeamYearBean {

	private String team;

	private String year;

	public TeamYearBean(String team, String year) {
		this.team = team;
		this.year = year;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "TeamYearBean{" +
					 "team='" + team + '\'' +
					 ", year='" + year + '\'' +
					 '}';
	}
}
