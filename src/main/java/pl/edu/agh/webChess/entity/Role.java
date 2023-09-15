package pl.edu.agh.webChess.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String roleName;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "id_role"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private Collection<User> users;

    public Role() {}

    public Role(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return roleName;
    }

    public void setName(String roleName) {
        this.roleName = roleName;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "roles{" +
                "id=" + id +
                ", name='" + roleName + '\'' +
                '}';
    }
}
