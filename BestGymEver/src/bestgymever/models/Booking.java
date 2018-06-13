package bestgymever.models;

public class Booking implements IModel {

    private final int id;
    private boolean checkedIn;
    private final Member member;
    private Workout workout;

    public Booking(int id, boolean checkedIn, Member member) {
        this.id = id;
        this.checkedIn = checkedIn;
        this.member = member;
    }

    public int getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }
    
    public Workout getWorkout() {
        return workout;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }
    
    @Override
    public String toString(){
        return String.valueOf(checkedIn);
    }
}
