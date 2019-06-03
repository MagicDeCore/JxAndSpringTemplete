package app.entity.data.user.domain;


import javax.persistence.*;

@Entity
@Table(name = "PUBLIC.USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "CODE", nullable = false, unique = true)
    private String code;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "INFO_ID", referencedColumnName = "id")
    private Info info;

    public User() {
    }

    public User(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public User(String name, String code, Info info) {
        this.name = name;
        this.code = code;
        this.info = info;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
