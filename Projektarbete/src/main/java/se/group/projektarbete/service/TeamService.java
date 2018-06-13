package se.group.projektarbete.service;

import org.springframework.stereotype.Service;
import se.group.projektarbete.data.Team;
import se.group.projektarbete.data.User;
import se.group.projektarbete.repository.TeamRepository;
import se.group.projektarbete.repository.UserRepository;
import se.group.projektarbete.service.exceptions.BadTeamException;
import se.group.projektarbete.service.exceptions.BadUserException;

import java.util.List;
import java.util.Optional;

@Service
public final class TeamService {

    private TeamRepository teamRepository;
    private UserRepository userRepository;

    public TeamService(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public boolean updateTeam(Long id, Team team) {
        if (teamRepository.findById(id).isPresent()) {
            Optional<Team> teams = teamRepository.findById(id);
            teams.get().setName(team.getName());
            teamRepository.save(teams.get());
            return true;
        }
        return false;
    }

    public boolean inactivateTeam(Long id) {
        if (teamRepository.findById(id).isPresent()) {
            Optional<Team> teams = teamRepository.findById(id);
            teams.get().setActive(false);
            teamRepository.save(teams.get());
            return true;
        }
        return false;
    }

    public List<Team> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        return teams;
    }

    public void setUserToTeam(Long id, Long userNumber) {
        Optional<Team> team = teamRepository.findById(id);
        Optional<User> user = userRepository.findUserByuserNumber(userNumber);

        if (!team.isPresent()) {
            throw new BadTeamException("No team matching that ID was found");
        } else if (!user.isPresent()) {
            throw new BadUserException("No user matching that ID was found");
        } else if (user.get().getTeam() == null) {
            validateTeam(team.get());
            user.ifPresent(u -> {
                team.get().setUser(u);
                userRepository.save(user.get());
            });
        } else
            throw new BadUserException("User is already assigned to a team");
    }

    private void validateTeam(Team team) {
        if (team.getUsers().size() > 9) {
            throw new BadTeamException("Team cannot have more than 10 users");
        }
    }
}