package bestgymever.models;

public class Note implements IModel { 

    private final int id;
    private String note;
    private final Member member;

    public Note(int id, String note, Member member) {
        this.id = id;
        this.note = note;
        this.member = member;
    }

    public int getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    @Override
    public String toString(){
        return String.valueOf(note);
    }
}
