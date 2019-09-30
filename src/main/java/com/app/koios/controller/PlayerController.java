package com.app.koios.controller;

import com.app.koios.bean.PlayerTransferFeeBean;
import com.app.koios.bean.TeamYearBean;
import com.app.koios.entity.Player;
import com.app.koios.exception.EntityNotFoundException;
import com.app.koios.request.CreateNewPlayerRequest;
import com.app.koios.request.UpdatePlayerRequest;
import com.app.koios.response.ServiceResponse;
import com.app.koios.service.PlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.app.koios.entity.Player.from;
import static java.lang.Integer.valueOf;
import static java.time.LocalDate.of;
import static java.time.LocalDate.parse;
import static java.time.Period.between;
import static java.util.stream.Collectors.toList;

/**
 * @author unalpolat
 */
@Api(value = "Player API", tags = "Player")
@RestController
@Validated
@RequestMapping("/players")
public class PlayerController {

  private static final ServiceResponse DEFAULT_SUCCESS_RESPONSE = ServiceResponse.from(null);

  private static final Integer MULTIPLIER = 100000;

  private static final Integer DIVIDER = 10;

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  private final PlayerService playerService;

  public PlayerController(PlayerService playerService) {
    this.playerService = playerService;
  }

  @ApiOperation(value = "Get player by id", notes = "Throws EntityNotFoundException")
  @GetMapping
  public ServiceResponse<Player> getPlayer(@NotNull @Positive @RequestParam Long id) throws EntityNotFoundException {
    return ServiceResponse.from(playerService.getById(id));
  }

  @ApiOperation("Create new player")
  @PostMapping
  public ServiceResponse<Player> createNewPlayer(@Validated @RequestBody CreateNewPlayerRequest request) {
    return ServiceResponse.from(playerService.create(from(request)));
  }

  @ApiOperation(value = "Update player by id", notes = "Throws EntityNotFoundException")
  @PutMapping
  public ServiceResponse<Player> updatePlayer(@Validated @RequestBody UpdatePlayerRequest request)
      throws EntityNotFoundException {
    return ServiceResponse.from(playerService.updatePlayer(request));
  }

  @ApiOperation("Delete player by id")
  @DeleteMapping
  public ServiceResponse<Object> deletePlayer(@NotNull @Positive @RequestParam Long id) {
    playerService.deleteById(id);
    return DEFAULT_SUCCESS_RESPONSE;
  }

  @ApiOperation(value = "Get all players", notes = "Throws EntityNotFoundException")
  @GetMapping("/all")
  public ServiceResponse<List<Player>> getAllPlayers() throws EntityNotFoundException {
    return ServiceResponse.from(playerService.findAllPlayers());
  }

  @ApiOperation(value = "Get player teams by name", notes = "Throws EntityNotFoundException")
  @GetMapping("/team")
  public ServiceResponse<List<TeamYearBean>> getPlayersByName(@NotBlank @RequestParam String name)
      throws EntityNotFoundException {
    List<Player> allPlayers = playerService.findAllPlayers();
    List<TeamYearBean> teamYearBeans = allPlayers.stream()
                                                 .filter(p -> name.equals(p.getName()))
                                                 .map(p -> new TeamYearBean(p.getTeam(), p.getYear()))
                                                 .collect(toList());
    return ServiceResponse.from(teamYearBeans);
  }

  @ApiOperation(value = "Get players transfer fees", notes = "Throws EntityNotFoundException")
  @GetMapping("/fee")
  public ServiceResponse<List<PlayerTransferFeeBean>> getTransferFees()
      throws EntityNotFoundException {
    List<PlayerTransferFeeBean> playerTransferFeeBeans = new ArrayList<>();
    List<Player> allPlayers = playerService.findAllPlayers();
    for (Player player : allPlayers) {
      List<TeamYearBean> teamYearBeans = getPlayersByName(player.getName()).getData();
      TeamYearBean teamYearBean = teamYearBeans.stream()
                                               .min(Comparator.comparing(tyb -> Long.valueOf(tyb.getYear())))
                                               .orElseThrow(EntityNotFoundException::new);
      Integer contractFee = getContractFee(player, teamYearBean);
      playerTransferFeeBeans.add(new PlayerTransferFeeBean(player, contractFee));
    }
    return ServiceResponse.from(playerTransferFeeBeans);
  }

  private Integer getContractFee(Player player, TeamYearBean teamYearBean) {
    LocalDate startDate = of(valueOf(teamYearBean.getYear()), Month.JANUARY, 01);
    LocalDate birthday = parse(player.getBirthday(), formatter);
    LocalDate now = LocalDate.now();
    Period period = between(startDate, now);
    Integer age = between(birthday, now).getYears();
    Integer transferFee = (Math.multiplyExact(MULTIPLIER, period.getMonths())) / age;
    Integer commission = transferFee / DIVIDER;
    return Math.addExact(transferFee, commission);
  }
}
