package com.app.koios.controller;

import com.app.koios.bean.PlayerContractFeePair;
import com.app.koios.bean.TeamYearPair;
import com.app.koios.entity.Player;
import com.app.koios.exception.EntityNotFoundException;
import com.app.koios.request.CreateNewPlayerRequest;
import com.app.koios.request.UpdatePlayerRequest;
import com.app.koios.response.ServiceResponse;
import com.app.koios.service.PlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author unalpolat
 */
@Api(value = "Player API", tags = "Player")
@RestController
@Validated
@RequestMapping("/players")
public class PlayerController {

  private final PlayerService playerService;

  public PlayerController(PlayerService playerService) {
    this.playerService = playerService;
  }

  @ApiOperation(value = "Get player by id", notes = "Throws EntityNotFoundException")
  @GetMapping("/{id}")
  public ServiceResponse<Player> getPlayer(@NotNull @Positive @PathVariable Long id) throws EntityNotFoundException {
    return ServiceResponse.from(playerService.getById(id));
  }

  @ApiOperation("Create new player")
  @PostMapping
  public ServiceResponse<Player> createNewPlayer(@Validated @RequestBody CreateNewPlayerRequest request) {
    return ServiceResponse.from(playerService.createPlayer(request));
  }

  @ApiOperation(value = "Update player by id", notes = "Throws EntityNotFoundException")
  @PutMapping
  public ServiceResponse<Player> updatePlayer(@Validated @RequestBody UpdatePlayerRequest request)
      throws EntityNotFoundException {
    return ServiceResponse.from(playerService.updatePlayer(request));
  }

  @ApiOperation("Delete player by id")
  @DeleteMapping("/{id}")
  public ServiceResponse<Object> deletePlayer(@NotNull @Positive @PathVariable Long id) {
    playerService.deleteById(id);
    return ServiceResponse.successful();
  }

  @ApiOperation(value = "Get all players", notes = "Throws EntityNotFoundException")
  @GetMapping("/all")
  public ServiceResponse<List<Player>> getAllPlayers() throws EntityNotFoundException {
    return ServiceResponse.from(playerService.findAllPlayers());
  }

  @ApiOperation(value = "Get teams of player by name", notes = "Throws EntityNotFoundException")
  @GetMapping("/teams")
  public ServiceResponse<List<TeamYearPair>> getPlayerTeams(@NotBlank @RequestParam String name)
      throws EntityNotFoundException {
    List<TeamYearPair> playerTeams = playerService.getPlayerTeams(name);
    return ServiceResponse.from(playerTeams);
  }

  @ApiOperation(value = "Get player transfer fee for all players", notes = "Throws EntityNotFoundException")
  @GetMapping("/fees")
  public ServiceResponse<List<PlayerContractFeePair>> getTransferFees() throws EntityNotFoundException {
    List<PlayerContractFeePair> playerContractFeePairs = playerService.getPlayerTransferFee();
    return ServiceResponse.from(playerContractFeePairs);
  }
}
