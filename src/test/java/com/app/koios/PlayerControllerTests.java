package com.app.koios;

import com.app.koios.controller.PlayerController;
import com.app.koios.entity.Player;
import com.app.koios.request.CreateNewPlayerRequest;
import com.app.koios.request.UpdatePlayerRequest;
import com.app.koios.service.PlayerService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PlayerController.class)
public class PlayerControllerTests {

  private static Player player = new Player();

  @MockBean
  PlayerService playerService;

  ObjectMapper mapper = new ObjectMapper();

  @Autowired
  MockMvc mockMvc;

  @BeforeClass
  public static void init() {
    player.setTeam("TestTakım");
  }

  @Test
  public void createPlayerTest() throws Exception {
    CreateNewPlayerRequest createRequest = new CreateNewPlayerRequest("Test", "2019", "TestTakım", "12/06/1994");
    when(playerService.createPlayer(any(CreateNewPlayerRequest.class))).thenReturn(player);
    mockMvc.perform(post("/players")
                        .content(mapper.writeValueAsString(createRequest))
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.successful").value(TRUE))
           .andExpect(jsonPath("$.data").exists())
           .andExpect(jsonPath("$.data.team").value(player.getTeam()));
  }

  @Test
  public void createPlayerFailTest() throws Exception {
    CreateNewPlayerRequest createRequest = new CreateNewPlayerRequest(null, "2019", "TestTakım", "12/06/1994");
    when(playerService.createPlayer(any(CreateNewPlayerRequest.class))).thenReturn(player);
    mockMvc.perform(post("/players")
                        .content(mapper.writeValueAsString(createRequest))
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.successful").value(FALSE))
           .andExpect(jsonPath("$.data").doesNotExist())
           .andExpect(jsonPath("$.errorCode").value("1002"));
  }

  @Test
  public void updatePlayerTest() throws Exception {
    UpdatePlayerRequest updateRequest = new UpdatePlayerRequest(1L, "Test", "2019", "TestTakım", "12/06/1994");
    when(playerService.updatePlayer(any(UpdatePlayerRequest.class))).thenReturn(player);
    mockMvc.perform(put("/players")
                        .content(mapper.writeValueAsString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.successful").value(TRUE))
           .andExpect(jsonPath("$.data").exists())
           .andExpect(jsonPath("$.data.team").value(player.getTeam()));
  }

  @Test
  public void updatePlayerBirthdayFailTest() throws Exception {
    UpdatePlayerRequest updateRequest = new UpdatePlayerRequest(1L, "Test", "2019", "TestTakım", "12.06.1994");
    when(playerService.updatePlayer(any(UpdatePlayerRequest.class))).thenReturn(player);
    mockMvc.perform(put("/players")
                        .content(mapper.writeValueAsString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.successful").value(FALSE))
           .andExpect(jsonPath("$.data").doesNotExist())
           .andExpect(jsonPath("$.errorCode").value("1002"));
  }

  @Test
  public void getPlayerTest() throws Exception {
    when(playerService.getById(any(Long.class))).thenReturn(player);
    mockMvc.perform(get("/players/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.successful").value(TRUE))
           .andExpect(jsonPath("$.data").exists())
           .andExpect(jsonPath("$.data.team").value(player.getTeam()));
  }

  @Test
  public void deletePlayerTest() throws Exception {
    mockMvc.perform(delete("/players/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.successful").value(TRUE));
  }
}
