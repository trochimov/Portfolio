package bestgymever.models;

public class Receptionist implements IPerson, IModel{

    private final int id;

    public Receptionist(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    @Override
    public String toString(){
        return String.valueOf(id);
    }
}
