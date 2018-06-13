package bestgymever.models;

import bestgymever.controller.*;
import java.util.*;

public class SuperModel {

    private IPerson user;
    private String username, password, returnStatement, name, bookingID;
    private int tempMemberID;
    private final List<String> viewList;
    private final Map<Integer, Member> members;
    private final Map<Integer, PersonalTrainer> personalTrainers;
    private final Map<Integer, Workout> workouts;
    private final Map<Integer, WorkoutRoom> workoutRooms;
    private final Map<Integer, WorkoutType> workoutTypes;
    private final Map<Integer, Booking> bookings;
    private final Map<Integer, Note> notes;
    private final List<Booking> tempBookings;
    private final List<Workout> tempWorkouts;
    private final List<PersonalTrainer> tempPersonalTrainers;
    private final List<WorkoutRoom> tempWorkoutRooms;
    private final List<WorkoutType> tempWorkoutTypes;
    private final List<String> tempWorkoutInput;
    private final List<Member> tempMembers; 
    private final List<Note> tempNotes;

    public SuperModel() {
        this.bookings = new HashMap<>();
        this.viewList = new ArrayList<>();
        this.tempWorkouts = new ArrayList<>();
        this.tempPersonalTrainers = new ArrayList<>();
        this.tempWorkoutRooms = new ArrayList<>();
        this.tempWorkoutTypes = new ArrayList<>();
        this.tempWorkoutInput = new ArrayList<>();
        this.tempMembers = new ArrayList<>();
        this.tempNotes = new ArrayList<>();

        this.members = new HashMap<>();
        this.personalTrainers = new HashMap<>();
        this.workouts = new HashMap<>();
        this.workoutRooms = new HashMap<>();
        this.workoutTypes = new HashMap<>();
        this.notes = new HashMap<>();
        this.tempBookings = new ArrayList<>();
    }

    public Map<Integer, Member> getMembers() {
        return members;
    }

    public Map<Integer, Note> getNotes() {
        return notes;
    }

    public Map<Integer, Booking> getBookings() {
        return bookings;
    }

    public Map<Integer, PersonalTrainer> getPersonalTrainers() {
        return personalTrainers;
    }

    public Map<Integer, Workout> getWorkouts() {
        return workouts;
    }

    public Map<Integer, WorkoutRoom> getWorkoutRooms() {
        return workoutRooms;
    }

    public Map<Integer, WorkoutType> getWorkoutTypes() {
        return workoutTypes;
    }

    public List<String> getViewList() {
        return viewList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }
    

    public IPerson getUser() {
        return user;
    }

    public void setUser(IPerson user) {
        this.user = user;
    }

    public void update(FunInt funInt) {
        funInt.update(this);
    }

    public List<Booking> getTempBookings() {
        return tempBookings;
    }

    public List<Workout> getTempWorkouts() {
        return tempWorkouts;
    }

    public List<PersonalTrainer> getTempPersonalTrainers() {
        return tempPersonalTrainers;
    }

    public List<WorkoutRoom> getTempWorkoutRooms() {
        return tempWorkoutRooms;
    }

    public List<WorkoutType> getTempWorkoutTypes() {
        return tempWorkoutTypes;
    }

    public List<String> getTempWorkoutInput() {
        return tempWorkoutInput;
    }
    
    public List<Member> getTempMembers() {
        return tempMembers;
    }
    
    public List<Note> getTempNotes() {
        return tempNotes;
    }

    public String getReturnStatement() {
        return returnStatement;
    }

    public void setReturnStatement(String returnStatement) {
        this.returnStatement = returnStatement;
    }

    public int getTempMemberID() {
        return tempMemberID;
    }

    public void setTempMemberID(int tempMemberID) {
        this.tempMemberID = tempMemberID;
    }
    
    
    public void clearUser() {
        getMembers().remove(user.getId());
        user=null;
        members.clear();
        personalTrainers.clear();
        workouts.clear();
        workoutRooms.clear();
        workoutTypes.clear();
        bookings.clear();
        notes.clear();
    }
}
