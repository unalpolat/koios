package com.app.koios.request;

import com.app.koios.bean.EmbeddedPlayerNames;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author unalpolat
 */
public class CreateNewTeamRequest extends Request {

  @NotNull
  private EmbeddedPlayerNames embeddedPlayerNames;

  @NotBlank
  private String currency;

  public CreateNewTeamRequest(String name, String year, EmbeddedPlayerNames embeddedPlayerNames, String currency) {
    super(name, year);
    this.embeddedPlayerNames = embeddedPlayerNames;
    this.currency = currency;
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
    return "CreateNewTeamRequest{" +
           "embeddedPlayerNames=" + embeddedPlayerNames +
           ", currency='" + currency + '\'' +
           "} " + super.toString();
  }
}
