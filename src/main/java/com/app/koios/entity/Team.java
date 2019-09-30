package com.app.koios.entity;

import com.app.koios.request.CreateNewTeamRequest;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author unalpolat
 */
@Entity
public class Team extends CoreEntity {

	@Column(length = 1024, nullable = false)
	private String embeddedPlayerNames;

	@Column(nullable = false)
	private String currency;

	public Team() {
	}

	public Team(String name, String year, String embeddedPlayerNames, String currency) {
		super(name, year);
		this.embeddedPlayerNames = embeddedPlayerNames;
		this.currency = currency;
	}

	public static Team from(CreateNewTeamRequest request) {
		return new Team(request.getName(),
										request.getYear(),
										request.getEmbeddedPlayerNames().serialize(),
										request.getCurrency());
	}

	public String getEmbeddedPlayerNames() {
		return embeddedPlayerNames;
	}

	public void setEmbeddedPlayerNames(String embeddedPlayerNames) {
		this.embeddedPlayerNames = embeddedPlayerNames;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "Team{" +
					 "embeddedPlayerNames='" + embeddedPlayerNames + '\'' +
					 ", currency='" + currency + '\'' +
					 "} " + super.toString();
	}
}
