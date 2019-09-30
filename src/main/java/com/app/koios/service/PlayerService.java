package com.app.koios.service;

import com.app.koios.entity.Player;
import com.app.koios.exception.EntityNotFoundException;
import com.app.koios.repository.PlayerRepository;
import com.app.koios.request.UpdatePlayerRequest;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author unalpolat
 */
@Service
public class PlayerService extends AbstractService<Player, PlayerRepository> {

  public PlayerService(PlayerRepository repository) {
    super(repository);
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
}
