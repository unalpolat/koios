package com.app.koios.request;

import com.app.koios.validation.Date;
import javax.validation.constraints.NotBlank;

/**
 * @author unalpolat
 */
public class CreateNewPlayerRequest extends Request {

  private String team;

  @Date
  private String birthday;

  public CreateNewPlayerRequest(String name, String year, String team, String birthday) {
    super(name, year);
    this.team = team;
    this.birthday = birthday;
  }

  @NotBlank
  @Override
  public String getName() {
    return super.getName();
  }

  @NotBlank
  @Override
  public String getYear() {
    return super.getYear();
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

  @Override
  public String toString() {
    return "CreateNewPlayerRequest{" +
           "team='" + team + '\'' +
           ", birthday='" + birthday + '\'' +
           "} " + super.toString();
  }
}
