package bestgymever.models;

public class Administrator implements IPerson, IModel{

    private final int id;

    public Administrator(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }
    
    @Override
    public String toString(){
        return String.valueOf(id);
    }
}
