package com.app.koios.repository;

import com.app.koios.entity.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author unalpolat
 */
@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

}
