package com.app.koios;

import com.app.koios.bean.EmbeddedPlayerNames;
import com.app.koios.controller.TeamController;
import com.app.koios.entity.Team;
import com.app.koios.request.CreateNewTeamRequest;
import com.app.koios.request.UpdateTeamRequest;
import com.app.koios.service.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TeamController.class)
public class TeamControllerTests {

  private static Team team = new Team();

  @MockBean
  TeamService teamService;

  ObjectMapper mapper = new ObjectMapper();

  @Autowired
  MockMvc mockMvc;

  @BeforeClass
  public static void init() {
    team.setName("Test123");
  }

  @Test
  public void createPlayerTest() throws Exception {
    EmbeddedPlayerNames embeddedPlayerNames = new EmbeddedPlayerNames(asList("Unal", "Hakan", "Fatma"));
    CreateNewTeamRequest createRequest = new CreateNewTeamRequest("Test123", "2019", embeddedPlayerNames, "euro");
    when(teamService.createTeam(any(CreateNewTeamRequest.class))).thenReturn(team);
    mockMvc.perform(post("/teams")
                        .content(mapper.writeValueAsString(createRequest))
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.successful").value(TRUE))
           .andExpect(jsonPath("$.data").exists())
           .andExpect(jsonPath("$.data.name").value(team.getName()));
  }

  @Test
  public void createPlayerFailTest() throws Exception {
    CreateNewTeamRequest createRequest = new CreateNewTeamRequest("Test123", "2019", null, "euro");
    when(teamService.createTeam(any(CreateNewTeamRequest.class))).thenReturn(team);
    mockMvc.perform(post("/teams")
                        .content(mapper.writeValueAsString(createRequest))
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.successful").value(FALSE))
           .andExpect(jsonPath("$.data").doesNotExist())
           .andExpect(jsonPath("$.errorCode").value("1002"));
  }

  @Test
  public void updatePlayerTest() throws Exception {
    EmbeddedPlayerNames embeddedPlayerNames = new EmbeddedPlayerNames(asList("Unal", "Hakan", "Fatma"));
    UpdateTeamRequest updateRequest =
        new UpdateTeamRequest("Test123", "2019", 1L, embeddedPlayerNames, "euro");
    when(teamService.updateTeam(any(UpdateTeamRequest.class))).thenReturn(team);
    mockMvc.perform(put("/teams")
                        .content(mapper.writeValueAsString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.successful").value(TRUE))
           .andExpect(jsonPath("$.data").exists())
           .andExpect(jsonPath("$.data.name").value(team.getName()));
  }

  @Test
  public void getPlayerTest() throws Exception {
    when(teamService.getById(any(Long.class))).thenReturn(team);
    mockMvc.perform(get("/teams/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.successful").value(TRUE))
           .andExpect(jsonPath("$.data").exists())
           .andExpect(jsonPath("$.data.name").value(team.getName()));
  }

  @Test
  public void deletePlayerTest() throws Exception {
    mockMvc.perform(delete("/teams/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.successful").value(TRUE));
  }
}
