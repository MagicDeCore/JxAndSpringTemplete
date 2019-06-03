package app.entity.data.user.domain;

import javax.persistence.*;

@Entity
@Table(name = "PUBLIC.INFO")
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "ADDRESS")
    private String address;
    @OneToOne(mappedBy = "info")
    private User user;

    public Info() {
    }

    public Info(String email, String address) {
        this.email = email;
        this.address = address;
    }

    public Info(String email, String address, User user) {
        this.email = email;
        this.address = address;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
