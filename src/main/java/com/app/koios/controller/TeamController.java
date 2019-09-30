package com.app.koios.controller;

import com.app.koios.bean.EmbeddedInfo;
import com.app.koios.bean.EmbeddedPlayerNames;
import com.app.koios.bean.PlayersYearBean;
import com.app.koios.entity.Team;
import com.app.koios.exception.EntityNotFoundException;
import com.app.koios.request.CreateNewTeamRequest;
import com.app.koios.request.UpdateTeamRequest;
import com.app.koios.response.ServiceResponse;
import com.app.koios.service.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

import static com.app.koios.entity.Team.from;
import static java.util.stream.Collectors.toList;

/**
 * @author unalpolat
 */
@Api(value = "Team API", tags = "Team")
@RestController
@Validated
@RequestMapping("/teams")
public class TeamController {

	private static final ServiceResponse DEFAULT_SUCCESS_RESPONSE = ServiceResponse.from(null);

	private final TeamService teamService;

	public TeamController(TeamService teamService) {
		this.teamService = teamService;
	}

	@ApiOperation(value = "Get team by id", notes = "Throws EntityNotFoundException")
	@GetMapping
	public ServiceResponse<Team> getPlayer(@NotNull @Positive @RequestParam Long id) throws EntityNotFoundException {
		return ServiceResponse.from(teamService.getById(id));
	}

	@ApiOperation("Create new team")
	@PostMapping
	public ServiceResponse<Team> createNewPlayer(@Validated @RequestBody CreateNewTeamRequest request) {
		return ServiceResponse.from(teamService.create(from(request)));
	}

	@ApiOperation(value = "Update team by id", notes = "Throws EntityNotFoundException")
	@PutMapping
	public ServiceResponse<Team> updatePlayer(@Validated @RequestBody UpdateTeamRequest request)
		throws EntityNotFoundException {
		return ServiceResponse.from(teamService.updateTeam(request));
	}

	@ApiOperation("Delete team by id")
	@DeleteMapping
	public ServiceResponse<Object> deletePlayer(@NotNull @Positive @RequestParam Long id) {
		teamService.deleteById(id);
		return DEFAULT_SUCCESS_RESPONSE;
	}

	@ApiOperation(value = "Get all teams", notes = "Throws EntityNotFoundException")
	@GetMapping("/all")
	public ServiceResponse<List<Team>> getAllTeams() throws EntityNotFoundException {
		return ServiceResponse.from(teamService.findAllTeams());
	}

	@ApiOperation(value = "Get all players", notes = "Throws EntityNotFoundException")
	@GetMapping("/all-players")
	public ServiceResponse<List<PlayersYearBean>> getAllPlayers(@NotBlank @RequestParam String name,
																															@NotNull @RequestParam String year)
		throws EntityNotFoundException {
		List<Team> allTeams = teamService.findAllTeams();
		List<PlayersYearBean> playersYearBeans = allTeams.stream()
																										 .filter(t -> name.equals(t.getName()) && year.equals(t.getYear()))
																										 .map(t -> new PlayersYearBean(
																											 createPlayerNames(t.getEmbeddedPlayerNames()),
																											 t.getYear()))
																										 .collect(toList());
		return ServiceResponse.from(playersYearBeans);
	}

	private List<String> createPlayerNames(String embeddedPlayerNames) {
		return EmbeddedInfo.deserialize(EmbeddedPlayerNames.class, embeddedPlayerNames).getNames();
	}
}
