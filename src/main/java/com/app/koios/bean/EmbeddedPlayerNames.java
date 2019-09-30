package com.app.koios.bean;

import java.util.List;

/**
 * @author unalpolat
 */
public class EmbeddedPlayerNames implements EmbeddedInfo {

	private List<String> names;

	public EmbeddedPlayerNames() {
	}

	public EmbeddedPlayerNames(List<String> names) {
		this.names = names;
	}

	public List<String> getNames() {
		return names;
	}

	@Override
	public String toString() {
		return "EmbeddedPlayerNames{" +
					 "names=" + names +
					 '}';
	}
}
