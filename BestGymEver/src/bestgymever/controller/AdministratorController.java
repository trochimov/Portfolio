package bestgymever.controller;

import static bestgymever.controller.AdministratorCreationState.*;
import static bestgymever.controller.AdministratorState.*;
import bestgymever.models.*;
import bestgymever.repository.*;
import bestgymever.view.*;

public class AdministratorController implements IController {

    SuperModel model;
    ConsoleView view;
    Repository repository;
    AdministratorState state;
    AdministratorCreationState creationState;

    FunInt logIn = (m) -> repository.AdministratorlogIn(m, model.getUsername(), model.getPassword());
    FunInt createMember = (m) -> repository.addMember(m, model.getName(), model.getUsername(), model.getPassword());
    FunInt createReceptionist = (m) -> repository.addReceptionist(m, model.getUsername(), model.getPassword());
    FunInt createPersonalTrainer = (m) -> repository.addPersonalTrainer(m, model.getName(), model.getUsername(), model.getPassword());
    FunInt createWorkout = (m) -> repository.createWorkout(m,
            model.getTempWorkoutInput().get(5),
            model.getTempWorkoutInput().get(4),
            model.getTempWorkoutInput().get(3),
            model.getTempWorkoutInput().get(2),
            model.getTempWorkoutInput().get(1),
            model.getTempWorkoutInput().get(0));
    
    FunInt loadWorkoutRooms = (m) -> repository.getWorkoutRooms(m, "");
    FunInt loadWorkoutTypes = (m) -> repository.getWorkoutTypes(m, "");
    FunInt loadPersonalTrainers = (m) -> repository.getPersonalTrainers(m, "");

    public AdministratorController(SuperModel model, ConsoleView view, Repository repository) {
        this.model = model;
        this.view = view;
        this.repository = repository;
        state = START;
    }

    @Override
    public void updateModel(String input) {
        try {
            
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
                        model.getViewList().add("Logged in as Admin");
                        AddMenyOptions();
                        state = MENU;
                    }
                    break;
                case MENU:
                    switch (input) {
                        case "1":
                            model.update(loadPersonalTrainers.andThen(loadWorkoutRooms).andThen(loadWorkoutTypes));
                            
                            model.getTempWorkoutInput().clear();
                            model.getTempWorkoutRooms().clear();
                            model.getTempWorkoutTypes().clear();
                            model.getTempPersonalTrainers().clear();
                            
                            model.getViewList().add("Choose Trainer");
                            model.getPersonalTrainers().values().forEach((personalTrainer) -> {
                                model.getTempPersonalTrainers().add(personalTrainer);
                                model.getViewList().add("[" + model.getTempPersonalTrainers().size() + "]" + personalTrainer.toString());
                            });
                            state = CHOOSETYPE;
                            break;
                        case "2":
                            model.getViewList().add("Choose Name");
                            state = CHOOSEUSERNAME;
                            creationState = MEMBER;
                            break;
                        case "3":
                            model.getViewList().add("Choose Name");
                            state = CHOOSEUSERNAME;
                            creationState = PERSONALTRAINER;
                            break;
                        case "4":
                            model.getViewList().add("Choose Username");
                            state = CHOOSEPASSWORD;
                            creationState = RECEPTIONIST;
                            break;
                        case "5":
                            state = USERNAME;
                            model.getViewList().add("Username");
                            break;
                    }
                    break;
                case CHOOSETYPE:
                    model.getTempWorkoutInput().add(String.valueOf(model.getTempPersonalTrainers().get(Integer.parseInt(input) - 1).getId()));
                    
                    model.getViewList().add("Choose Workout Type");
                    model.getWorkoutTypes().values().forEach((workoutType) -> {
                        model.getTempWorkoutTypes().add(workoutType);
                        model.getViewList().add("[" + model.getTempWorkoutTypes().size() + "]" + workoutType.toString());
                    });
                    state = CHOOSEROOM;
                    break;
                case CHOOSEROOM:
                    model.getTempWorkoutInput().add(String.valueOf(model.getTempWorkoutTypes().get(Integer.parseInt(input) - 1).getId()));
                    
                    model.getViewList().add("Choose Workout Room");
                    model.getWorkoutRooms().values().forEach((workoutRoom) -> {
                        model.getTempWorkoutRooms().add(workoutRoom);
                        model.getViewList().add("[" + model.getTempWorkoutRooms().size() + "]" + workoutRoom.toString());
                    });
                    state = CHOOSEAVAILABLESLOTS;
                    break;
                case CHOOSEAVAILABLESLOTS:
                    model.getTempWorkoutInput().add(String.valueOf(model.getTempWorkoutRooms().get(Integer.parseInt(input) - 1).getId()));
                    model.getViewList().add("Choose amount of slots");
                    state = CHOOSESTARTDATE;
                    break;
                case CHOOSESTARTDATE:
                    model.getTempWorkoutInput().add(input);
                    model.getViewList().add("Choose Start Date \"yyyy-MM-dd HH:mm:ss\"");
                    state = CHOOSEENDDATE;
                    break;
                case CHOOSEENDDATE:
                    model.getTempWorkoutInput().add(input);
                    model.getViewList().add("Choose End Date \"yyyy-MM-dd HH:mm:ss\"");
                    state = CREATEWORKOUT;
                    break;
                case CREATEWORKOUT:
                    model.getTempWorkoutInput().add(input);
                    model.update(createWorkout);
                    
                    model.getViewList().add(model.getReturnStatement());
                    state = MENU;
                    AddMenyOptions();
                    break;
//--------------------------------Workout---------------------------------------
//--------------------------------User------------------------------------------
                case CHOOSEUSERNAME:
                    model.setName(input);
                    model.getViewList().add("Choose Username");
                    state = CHOOSEPASSWORD;
                    break;
                case CHOOSEPASSWORD:
                    model.setUsername(input);
                    model.getViewList().add("Choose Password");
                    state = CREATEUSER;
                    break;
                case CREATEUSER:
                    model.setPassword(input);
                    switch (creationState) {
                        case MEMBER:
                            model.update(createMember);
                            break;
                        case PERSONALTRAINER:
                            model.update(createPersonalTrainer);
                            break;
                        case RECEPTIONIST:
                            model.update(createReceptionist);
                            break;
                        default:
                    }
                    model.getViewList().add(model.getReturnStatement());
                    state = MENU;
                    AddMenyOptions();
                    break;
//--------------------------------User------------------------------------------                
                default:
                    model.getTempWorkoutInput().add(input);
                    state = USERNAME;
                    model.getViewList().add("Username");
            }
            
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            model.getViewList().add("You can't select that");
            state = MENU;
            AddMenyOptions();
        }
        updateView();
    }

    @Override
    public void updateView() {
        updateModel(view.display(model.getViewList()));
    }

    private void AddMenyOptions() {
        model.getViewList().add("");
        model.getViewList().add("What do you wan't to do?");
        model.getViewList().add("[1] Create Workout");
        model.getViewList().add("[2] Create Member");
        model.getViewList().add("[3] Create Trainer");
        model.getViewList().add("[4] Create Receptionist");
        model.getViewList().add("[5] Log out");
    }
}
