package com.app.koios.service;

import com.app.koios.bean.PlayerContractFeePair;
import com.app.koios.bean.TeamYearPair;
import com.app.koios.entity.Player;
import com.app.koios.exception.EntityNotFoundException;
import com.app.koios.repository.PlayerRepository;
import com.app.koios.request.CreateNewPlayerRequest;
import com.app.koios.request.UpdatePlayerRequest;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import static java.lang.Integer.valueOf;
import static java.time.LocalDate.of;
import static java.time.LocalDate.parse;
import static java.time.Period.between;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * @author unalpolat
 */
@Service
public class PlayerService extends AbstractService<Player, PlayerRepository> {

  private static final Integer MULTIPLIER = 100000;

  private static final Integer DIVIDER = 10;

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  public PlayerService(PlayerRepository repository) {
    super(repository);
  }

  public Player createPlayer(CreateNewPlayerRequest request) {
    Player player = Player.from(request);
    return create(player);
  }

  public Player updatePlayer(UpdatePlayerRequest request) throws EntityNotFoundException {
    Player player = setPlayerFields(request);
    return create(player);
  }

  private Player setPlayerFields(UpdatePlayerRequest request) throws EntityNotFoundException {
    Player player = getById(request.getId());
    player.setName(request.getName());
    player.setTeam(request.getTeam());
    player.setYear(request.getYear());
    player.setBirthday(request.getBirthday());
    return player;
  }

  public List<Player> findAllPlayers() throws EntityNotFoundException {
    return findAll();
  }

  public List<TeamYearPair> getPlayerTeams(String name) throws EntityNotFoundException {
    return findAll().stream()
                    .filter(p -> name.equals(p.getName()))
                    .map(p -> new TeamYearPair(p.getTeam(), p.getYear()))
                    .collect(toList());
  }

  public List<PlayerContractFeePair> getPlayerTransferFee() throws EntityNotFoundException {
    List<PlayerContractFeePair> playerContractFeePairs = new ArrayList<>();
    List<Player> allPlayers = findAll();
    for (Player player : allPlayers) {
      List<TeamYearPair> teamYearPairs = getPlayerTeams(player.getName());
      TeamYearPair teamYearPair = teamYearPairs.stream()
                                               .min(comparing(tyb -> Long.valueOf(tyb.getYear()))).get();
      Integer contractFee = getContractFee(player, teamYearPair);
      playerContractFeePairs.add(new PlayerContractFeePair(player, contractFee));
    }
    return playerContractFeePairs;
  }

  private Integer getContractFee(Player player, TeamYearPair teamYearPair) {
    LocalDate startDate = of(valueOf(teamYearPair.getYear()), Month.JANUARY, 01);
    LocalDate birthday = parse(player.getBirthday(), formatter);
    LocalDate now = LocalDate.now();
    Period period = between(startDate, now);
    Integer age = between(birthday, now).getYears();
    Integer transferFee = (Math.multiplyExact(MULTIPLIER, period.getMonths())) / age;
    Integer commission = transferFee / DIVIDER;
    return Math.addExact(transferFee, commission);
  }
}
