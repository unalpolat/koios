package com.app.koios.bean;

import java.util.List;

/**
 * @author unalpolat
 */
public class PlayersYearPair {

	private List<String> players;

	private String year;

	public PlayersYearPair(List<String> players, String year) {
		this.players = players;
		this.year = year;
	}

	public List<String> getPlayers() {
		return players;
	}

	public void setPlayers(List<String> players) {
		this.players = players;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "PlayersYearPair{" +
					 "players=" + players +
					 ", year='" + year + '\'' +
					 '}';
	}
}
