package com.app.koios.repository;

import com.app.koios.entity.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author unalpolat
 */
@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {

}
