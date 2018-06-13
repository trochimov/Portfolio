package se.group.projektarbete.data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

@Entity
public final class Team {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "team", cascade = {CascadeType.DETACH, CascadeType.PERSIST})
    List<User> users;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Boolean active;

    protected Team() {
    }

    public Team(String name, Boolean active) {
        this.name = name;
        this.active = active;
        users = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @XmlTransient
    public List<User> getUsers() {
        return users;
    }

    public void setUser(User user) {
        users.add(user);
        user.setTeam(this);
    }
}