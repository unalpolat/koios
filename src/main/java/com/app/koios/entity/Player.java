package com.app.koios.entity;

import com.app.koios.request.CreateNewPlayerRequest;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author unalpolat
 */
@Entity
public class Player extends CoreEntity {

  @Column
  private String team;

  @Column(nullable = false)
  private String birthday;

  public Player() {
  }

  public Player(String name, String year, String team, String birthday) {
    super(name, year);
    this.team = team;
    this.birthday = birthday;
  }

  public static Player from(CreateNewPlayerRequest request) {
    return new Player(request.getName(),
                      request.getTeam(),
                      request.getYear(),
                      request.getBirthday());
  }

  public String getTeam() {
    return team;
  }

  public void setTeam(String team) {
    this.team = team;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }
}
