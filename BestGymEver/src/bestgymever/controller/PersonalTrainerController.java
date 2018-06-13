package bestgymever.controller;

import static bestgymever.controller.PersonalTrainerState.*;
import bestgymever.models.*;
import bestgymever.repository.*;
import bestgymever.view.*;
import java.time.LocalDateTime;

public class PersonalTrainerController implements IController {

    SuperModel model;
    ConsoleView view;
    Repository repository;
    PersonalTrainerState state;
    String input;

    private final FunInt logIn = (m) -> repository.PersonalTrainerlogIn(m, m.getUsername(), m.getPassword());

    private final FunInt loadMembers = (m) -> repository.getMembers(m, "");
    private final FunInt mapNotesToMembers = (m) -> repository.mapNotesToMembers(m, "");
    private final FunInt mapBookingsToMembers = (m) -> repository.mapBookingsToMembers(m, "");
    private final FunInt mapWorkoutsToBookings = (m) -> repository.mapWorkoutsToBookings(m, "");
    private final FunInt addNote = (m) -> repository.addNote(m, String.valueOf(m.getTempMemberID()), input);

    public PersonalTrainerController(SuperModel model, ConsoleView view, Repository repository) {

        this.state = START;
        this.model = model;
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void updateModel(String input) {
        this.input = input;
        model.getViewList().clear();
        switch (state) {
            case USERNAME:
                model.setUsername(input);
                model.getViewList().add("Password");
                state = PASSWORD;
                break;

            case PASSWORD:
                model.setPassword(input);
                model.update(logIn);
                if (model.getUser() == null) {
                    model.getViewList().add("Wrong Username/Password");
                    state = USERNAME;
                    model.getViewList().add("Username");
                } else {
                    model.getViewList().add("Logged in as " + model.getPersonalTrainers().get(model.getUser().getId()).getName());
                    AddMenyOptions();
                    state = MENU;
                }
                break;

            case MENU:
                switch (input) {
                    case "1":
                        chooseMember();
                        state = SHOWMEMBERWORKOUT;
                        break;
                    case "2":
                        chooseMember();
                        state = SHOWMEMBERNOTE;
                        break;
                    case "3":
                        chooseMember();
                        state = GETMEMBERFORADDNOTE;
                        break;
                    case "4":
                        state = USERNAME;
                        model.getViewList().add("Username");
                        break;
                }
                break;

            case SHOWMEMBERWORKOUT:
                if (checkForExit(input)) break;
                model.update(loadMembers.andThen(mapBookingsToMembers).andThen(mapWorkoutsToBookings));

                model.getMembers().values().forEach((member) -> {
                    if (member.getId() == model.getTempMembers().get(Integer.parseInt(input) - 1).getId()) {
                        member.getBookings().values().stream()
                                .filter(booking -> booking.isCheckedIn() && booking.getWorkout().getEndDate().isBefore(LocalDateTime.now()))
                                .forEach((t) -> {
                                    model.getViewList().add(t.getWorkout().BookingsAccessToString());
                                    model.getTempWorkouts().add(t.getWorkout());
                                });
                    }
                });
                if (model.getTempWorkouts().isEmpty()) {
                    model.getViewList().add("Member has not participated in any workouts");
                }

                model.getViewList().add("Write exit to return to menu");
                state = RETURNTOMENUOPTION;
                break;

            case SHOWMEMBERNOTE:
                if (checkForExit(input)) break;
                model.update(loadMembers.andThen(mapNotesToMembers));

                model.getMembers().values().forEach((member) -> {
                    if (member.getId() == model.getTempMembers().get(Integer.parseInt(input) - 1).getId()) {
                        member.getNotes().forEach((t, note) -> {
                            model.getViewList().add(note.toString());
                            model.getTempNotes().add(note);
                        });
                    }
                });
                if (model.getTempNotes().isEmpty()) {
                    model.getViewList().add("Member has no notes");
                }

                model.getViewList().add("Write exit to return to menu");
                state = RETURNTOMENUOPTION;
                break;

            case GETMEMBERFORADDNOTE:
                if (checkForExit(input)) break;

                model.getMembers().values().forEach((member) -> {
                    if (member.getId() == model.getTempMembers().get(Integer.parseInt(input) - 1).getId()) {
                        model.setTempMemberID(model.getTempMembers().get(Integer.parseInt(input) - 1).getId());
                    }
                });
                
                model.getViewList().add("Write note on " + model.getTempMembers().get(Integer.parseInt(input) - 1).toString() + " or exit");
                state = ADDMEMBERNOTE;
                
                break;

            case ADDMEMBERNOTE:
                if (checkForExit(input)) break;
                model.update(addNote);
                model.getViewList().add(model.getReturnStatement());
                AddMenyOptions();
                state = MENU;
                break;

            case RETURNTOMENUOPTION:
                if (input.equalsIgnoreCase("exit")) {
                    AddMenyOptions();
                    state = MENU;
                }
                break;

            default:
                state = USERNAME;
                model.getViewList().add("Username");
                break;
        }
        updateView();
    }

    private boolean checkForExit(String input1) {
        if (input1.equalsIgnoreCase("exit")) {
            AddMenyOptions();
            state = MENU;
            return true;
        }
        return false;
    }

    private void chooseMember() {
        model.update(loadMembers);
        
        model.getTempMembers().clear();
        model.getTempWorkouts().clear();
        model.getTempBookings().clear();
        
        model.getViewList().add("Choose Member or exit");
        model.getMembers().values().forEach((member) -> {
            model.getTempMembers().add(member);
            model.getViewList().add("[" + model.getTempMembers().size() + "]" + member.toString());
        });
    }

    private void AddMenyOptions() {
        model.getViewList().add("");
        model.getViewList().add("What do you wan't to do?");
        model.getViewList().add("[1] Check member workouts");
        model.getViewList().add("[2] Check member notes");
        model.getViewList().add("[3] Add member note");
        model.getViewList().add("[4] Log out");
    }

    @Override
    public void updateView() {
        updateModel(view.display(model.getViewList()));
    }

}
