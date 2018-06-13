package bestgymever.models;

import java.util.*;

public class Member implements IPerson, IModel{

    private final int id;
    private String name;
    private final Map<Integer, Booking> bookings;
    private final Map<Integer, Note> notes;

    public Member(int id, String name) {
        this.bookings = new HashMap<>();
        this.notes = new HashMap<>();
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, Booking> getBookings() {
        return bookings;
    }

    public void addBooking(Booking booking) {
        bookings.put(booking.getId(), booking);
    }

    public void removeBooking(Booking booking) {
        bookings.remove(booking.getId());
    }

    public Map<Integer, Note> getNotes() {
        return notes;
    }

    public void addNote(Note note) {
        notes.put(note.getId(), note);
    }

    public void removeNote(Note note) {
        notes.remove(note.getId());
    }
    
    @Override
    public String toString(){
        return String.valueOf(name);
    }
}
