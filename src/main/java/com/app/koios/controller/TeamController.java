package com.app.koios.controller;

import com.app.koios.bean.PlayersYearPair;
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
@Api(value = "Team API", tags = "Team")
@RestController
@Validated
@RequestMapping("/teams")
public class TeamController {

	private final TeamService teamService;

	public TeamController(TeamService teamService) {
		this.teamService = teamService;
	}

	@ApiOperation(value = "Get team by id", notes = "Throws EntityNotFoundException")
	@GetMapping("/{id}")
	public ServiceResponse<Team> getTeam(@NotNull @Positive @PathVariable Long id) throws EntityNotFoundException {
		return ServiceResponse.from(teamService.getById(id));
	}

	@ApiOperation("Create new team")
	@PostMapping
	public ServiceResponse<Team> createNewTeam(@Validated @RequestBody CreateNewTeamRequest request) {
		return ServiceResponse.from(teamService.createTeam(request));
	}

	@ApiOperation(value = "Update team by id", notes = "Throws EntityNotFoundException")
	@PutMapping
	public ServiceResponse<Team> updateTeam(@Validated @RequestBody UpdateTeamRequest request)
		throws EntityNotFoundException {
		return ServiceResponse.from(teamService.updateTeam(request));
	}

	@ApiOperation("Delete team by id")
	@DeleteMapping("/{id}")
	public ServiceResponse<Object> deleteTeam(@NotNull @Positive @PathVariable Long id) {
		teamService.deleteById(id);
		return ServiceResponse.successful();
	}

	@ApiOperation(value = "Get all teams", notes = "Throws EntityNotFoundException")
	@GetMapping("/all")
	public ServiceResponse<List<Team>> getAllTeams() throws EntityNotFoundException {
		return ServiceResponse.from(teamService.findAllTeams());
	}

	@ApiOperation(value = "Get all players of teams by name and year", notes = "Throws EntityNotFoundException")
	@GetMapping("/players")
	public ServiceResponse<List<PlayersYearPair>> getAllPlayers(@NotBlank @RequestParam String name,
																															@NotNull @RequestParam String year)
		throws EntityNotFoundException {
		List<PlayersYearPair> allPlayers = teamService.getAllPlayers(name, year);
		return ServiceResponse.from(allPlayers);
	}
}
