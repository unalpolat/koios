package com.app.koios.bean;

import com.app.koios.entity.Player;

/**
 * @author unalpolat
 */
public class PlayerContractFeePair {

	private Player player;

	private Integer contractFee;

	public PlayerContractFeePair(Player player, Integer contractFee) {
		this.player = player;
		this.contractFee = contractFee;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Integer getContractFee() {
		return contractFee;
	}

	public void setContractFee(Integer contractFee) {
		this.contractFee = contractFee;
	}

	@Override
	public String toString() {
		return "PlayerContractFeePair{" +
					 "player=" + player +
					 ", contractFee=" + contractFee +
					 '}';
	}
}
