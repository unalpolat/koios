package com.app.koios.bean;

import com.app.koios.entity.Player;

/**
 * @author unalpolat
 */
public class PlayerTransferFeeBean {

	private Player player;

	private Integer transferFee;

	public PlayerTransferFeeBean(Player player, Integer transferFee) {
		this.player = player;
		this.transferFee = transferFee;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Integer getTransferFee() {
		return transferFee;
	}

	public void setTransferFee(Integer transferFee) {
		this.transferFee = transferFee;
	}

	@Override
	public String toString() {
		return "PlayerTransferFeeBean{" +
					 "player=" + player +
					 ", transferFee=" + transferFee +
					 '}';
	}
}
