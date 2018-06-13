package bestgymever.controller;

import static bestgymever.controller.MemberState.*;
import bestgymever.models.*;
import bestgymever.repository.*;
import bestgymever.view.*;
import java.time.*;

public class MemberController implements IController {

    private Repository repository;

    private final SuperModel model;
    private final ConsoleView view;
    private MemberState state;
    private String input;

    private final FunInt login = (m) -> repository.MemberlogIn(m, m.getUsername(), m.getPassword());
    private final FunInt getMyBookings = (m) -> repository.mapBookingsToMembers(m, String.valueOf(m.getUser().getId()));
    private final FunInt getMyWorkouts = (m) -> repository.mapWorkoutsToBookings(m, "");
    private final FunInt getWorkouts = (m) -> repository.getWorkouts(m, "");
    private final FunInt CreateBooking = (m) -> repository.createBooking(m, String.valueOf(m.getUser().getId()), String.valueOf(m.getTempWorkouts().get(Integer.parseInt(input)-1).getId()));
    private final FunInt CancelBooking = (m) -> repository.cancelBooking(m, String.valueOf(m.getUser().getId()), String.valueOf(m.getTempBookings().get(Integer.parseInt(input)-1).getId()));

    public MemberController(SuperModel model, ConsoleView view, Repository repository) {
        this.model = model;
        this.state = START;
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void updateModel(String input) {
        try {
            
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
                    model.update(login);
                    if (model.getUser() == null) {
                        model.getViewList().add("Wrong Username/Password");
                        state = USERNAME;
                        model.getViewList().add("Username");
                    } else {
                        model.getViewList().add("Welcome " + model.getMembers().get(model.getUser().getId()).getName());
                        AddMenyOptions();
                        state = OPTION;
                    }
                    break;
                case OPTION:
                    switch (input) {
                        case "1":
                            model.update(getWorkouts);
                            model.getWorkouts().forEach((t, working) -> {
                                if (working.getStartDate().isAfter(LocalDateTime.now())) {
                                    if(working.getAvailableSlots()>0){
                                    model.getTempWorkouts().add(working);
                                    model.getViewList().add("[" + model.getTempWorkouts().size() + "] " + working);
                                    }
                                }
                            });
                            model.getViewList().add("Choose workout to book or write exit to get to menu");
                            state = BOOKING;
                            break;
                        case "2":
                            model.update(getMyBookings);
                            model.update(getMyWorkouts);
                            model.getBookings().forEach((t, booking) -> {
                                if (booking.getWorkout().getStartDate().isAfter(LocalDateTime.now())) {
                                    model.getTempBookings().add(booking);
                                    model.getViewList().add("[" + model.getTempBookings().size() + "] " + booking.getWorkout().BookingsAccessToString());
                                }
                            });
                            if (model.getTempBookings().size() == 0) {
                                model.getViewList().add("You have no bookings");
                                AddMenyOptions();
                                state = OPTION;
                            } else {
                                model.getViewList().add("Choose workout to cancel or write exit to get to menu");
                                state = BOOKINGS;
                            }
                            break;
                        case "3":
                            state = USERNAME;
                            model.clearUser();
                            model.getViewList().add("Username");
                            break;
                    }
                    break;
                
                case BOOKING:
                    switch (input) {
                        case "exit":
                            AddMenyOptions();
                            state = OPTION;
                            break;
                        default:
                            model.update(CreateBooking);
                            model.getViewList().add(model.getReturnStatement());
                            AddMenyOptions();
                            state = OPTION;
                            break;
                    }
                    model.getTempWorkouts().clear();
                    break;
                case BOOKINGS:
                    switch (input) {
                        case "exit":
                            AddMenyOptions();
                            state = OPTION;
                            break;
                        default:
                            model.update(CancelBooking);
                            model.getViewList().add(model.getReturnStatement());
                            AddMenyOptions();
                            state = OPTION;
                            break;
                    }
                    model.getTempBookings().clear();
                    break;
                default:
                    state = USERNAME;
                    model.getViewList().add("Username");
                    break;
            }
            
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            model.getViewList().add("you can't select that");
            AddMenyOptions();
            state=OPTION;
        }
        updateView();
    }

    private void AddMenyOptions() {
        model.getViewList().add("");
        model.getViewList().add("What do you wan't to do?");
        model.getViewList().add("[1] Book a workout");
        model.getViewList().add("[2] Go to booked workouts");
        model.getViewList().add("[3] Log out");
    }

    @Override
    public void updateView() {
        updateModel(view.display(model.getViewList()));
    }

}
