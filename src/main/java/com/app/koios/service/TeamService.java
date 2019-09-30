package com.app.koios.service;

import com.app.koios.entity.Team;
import com.app.koios.exception.EntityNotFoundException;
import com.app.koios.repository.TeamRepository;
import com.app.koios.request.UpdateTeamRequest;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author unalpolat
 */
@Service
public class TeamService extends AbstractService<Team, TeamRepository> {

	public TeamService(TeamRepository repository) {
		super(repository);
	}

	public Team updateTeam(UpdateTeamRequest request) throws EntityNotFoundException {
		Team team = setTeamFields(request);
		return create(team);
	}

	private Team setTeamFields(UpdateTeamRequest request) throws EntityNotFoundException {
		Team team = getById(request.getId());
		team.setName(request.getName());
		team.setYear(request.getYear());
		team.setEmbeddedPlayerNames(request.getEmbeddedPlayerNames().serialize());
		team.setCurrency(request.getCurrency());
		return team;
	}

	public List<Team> findAllTeams() throws EntityNotFoundException {
		return findAll();
	}
}
