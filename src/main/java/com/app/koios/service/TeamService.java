package com.app.koios.service;

import com.app.koios.bean.EmbeddedInfo;
import com.app.koios.bean.EmbeddedPlayerNames;
import com.app.koios.bean.PlayersYearPair;
import com.app.koios.entity.Team;
import com.app.koios.exception.EntityNotFoundException;
import com.app.koios.repository.TeamRepository;
import com.app.koios.request.CreateNewTeamRequest;
import com.app.koios.request.UpdateTeamRequest;
import java.util.List;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

/**
 * @author unalpolat
 */
@Service
public class TeamService extends AbstractService<Team, TeamRepository> {

	public TeamService(TeamRepository repository) {
		super(repository);
	}

	public Team createTeam(CreateNewTeamRequest request) {
		Team team = Team.from(request);
		return create(team);
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

	public List<PlayersYearPair> getAllPlayers(String name, String year) throws EntityNotFoundException {
		List<Team> allTeams = findAll();
		return allTeams.stream()
									 .filter(t -> name.equals(t.getName()) && year.equals(t.getYear()))
									 .map(t -> new PlayersYearPair(createPlayerNames(t.getEmbeddedPlayerNames()), t.getYear()))
									 .collect(toList());
	}

	private List<String> createPlayerNames(String embeddedPlayerNames) {
		return EmbeddedInfo.deserialize(EmbeddedPlayerNames.class, embeddedPlayerNames).getNames();
	}
}
