package com.app.koios.request;

import com.app.koios.bean.EmbeddedPlayerNames;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author unalpolat
 */
public class UpdateTeamRequest extends Request {

  @NotNull
  @Positive(message = "You should provide positive id.")
  private Long id;

  @NotEmpty
  private EmbeddedPlayerNames embeddedPlayerNames;

  @NotBlank
  private String currency;

  public UpdateTeamRequest(String name, String year, Long id, EmbeddedPlayerNames embeddedPlayerNames,
                           String currency) {
    super(name, year);
    this.id = id;
    this.embeddedPlayerNames = embeddedPlayerNames;
    this.currency = currency;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public EmbeddedPlayerNames getEmbeddedPlayerNames() {
    return embeddedPlayerNames;
  }

  public void setEmbeddedPlayerNames(EmbeddedPlayerNames embeddedPlayerNames) {
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
    return "UpdateTeamRequest{" +
           "id=" + id +
           ", embeddedPlayerNames=" + embeddedPlayerNames +
           ", currency='" + currency + '\'' +
           "} " + super.toString();
  }
}
