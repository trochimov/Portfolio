package bestgymever.models;

import java.util.*;

public class PersonalTrainer implements IPerson, IModel{

    private final int id;
    private String name;
    private final Map<Integer, Workout> workouts;

    public PersonalTrainer(int id, String name) {
        this.workouts = new HashMap<>();
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, Workout> getWorkouts() {
        return workouts;
    }

    public void addWorkout(Workout workout) {
        workouts.put(workout.getId(), workout);
    }

    public void removeWorkout(Workout workout) {
        workouts.remove(workout.getId());
    }
    
    @Override
    public String toString(){
        return String.valueOf(name);
    }
}
