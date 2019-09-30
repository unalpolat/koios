package com.app.koios.request;

import com.app.koios.validation.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author unalpolat
 */
public class UpdatePlayerRequest extends Request {

  @NotNull
  @Positive(message = "You should provide positive id.")
  private Long id;

  private String team;

  @Date
  private String birthday;

  public UpdatePlayerRequest(Long id, String name, String year, String team, String birthday) {
    super(name, year);
    this.id = id;
    this.team = team;
    this.birthday = birthday;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
    return "UpdatePlayerRequest{" +
           "id=" + id +
           ", team='" + team + '\'' +
           ", birthday='" + birthday + '\'' +
           "} " + super.toString();
  }
}
