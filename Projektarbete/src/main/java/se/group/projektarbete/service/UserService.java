package se.group.projektarbete.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se.group.projektarbete.data.Team;
import se.group.projektarbete.data.User;
import se.group.projektarbete.data.WorkItem;
import se.group.projektarbete.repository.TeamRepository;
import se.group.projektarbete.repository.UserRepository;
import se.group.projektarbete.repository.WorkItemRepository;
import se.group.projektarbete.service.exceptions.BadTeamException;
import se.group.projektarbete.service.exceptions.BadUserException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public final class UserService {

    private final UserRepository userRepository;
    private final WorkItemRepository workItemRepository;
    private final TeamRepository teamRepository;
    private AtomicLong userNumbers;


    public UserService(UserRepository userRepository, WorkItemRepository workItemRepository, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.workItemRepository = workItemRepository;
        this.teamRepository = teamRepository;
        userNumbers = new AtomicLong(this.userRepository.getHighestUserNumber().orElse(1000L));
    }

    public User createUser(User user) {
        validateUser(user);
        return userRepository.save(new User(
                user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                userNumbers.incrementAndGet()));
    }

    public Optional<User> getUserByUsernumber(Long userNumber) {
        return userRepository.findUserByuserNumber(userNumber);
    }

    public boolean updateUser(Long userNumber, User user) {
        validateUser(user);
        if (userRepository.findUserByuserNumber(userNumber).isPresent()) {
            Optional<User> users = userRepository.findUserByuserNumber(userNumber);
            updateUserInfo(users.get(), user);
            userRepository.save(users.get());
            return true;
        }
        return false;
    }

    public boolean inactivateUser(Long userNumber) {
        if (userRepository.findUserByuserNumber(userNumber).isPresent()) {
            Optional<User> users = userRepository.findUserByuserNumber(userNumber);
            users.get().setActive(false);
            userRepository.save(users.get());
            setWorkItemsToUnstarted(workItemRepository.findAllByUser(users.get()), users.get());
            return true;
        }
        return false;
    }

    public List<User> findUsersByFirstNameAndLastNameAndUserName(String firstName, String lastName, String userName) {
        return userRepository.findAll().stream()
                .filter(u ->
                        firstName != null && firstName.equalsIgnoreCase(u.getFirstName()) ||
                                lastName != null && lastName.equalsIgnoreCase(u.getLastName()) ||
                                userName != null && userName.equalsIgnoreCase(u.getUserName()))
                .collect(Collectors.toList());

    }

    public List<User> findAllUsersAtTeamByTeamName(String teamName) {
        Optional<Team> team = teamRepository.findByName(teamName);

        if (team.isPresent()) {
            return userRepository.getAllByTeamId(team.get().getId());
        }
        throw new BadTeamException("No team with teamname: " + teamName);
    }

    private void setWorkItemsToUnstarted(List<WorkItem> workItems, User user) {
        if (!workItems.isEmpty()) {
            user.setWorkItemsToUnstarted(workItems);
            saveWorkItems(workItems);
        }
    }

    private void saveWorkItems(List<WorkItem> workItems) {
        for (WorkItem workItem : workItems) {
            workItemRepository.save(workItem);
        }
    }

    private void validateUser(User user) {
        if (user.getUserName().length() < 10) {
            throw new BadUserException("Username cannot be shorter than 10 characters");
        }
    }
    private void updateUserInfo(User user, User newInfo) {
        user.setFirstName(newInfo.getFirstName());
        user.setLastName(newInfo.getLastName());
        user.setUserName(newInfo.getUserName());
        user.setActive(newInfo.getActive());
    }
}
