package xd.webmovies.user.authority;

import javax.persistence.*;

@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Enumerated(EnumType.STRING)
    private AuthorityType type;

    public Authority() {
    }

    public Authority(AuthorityType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AuthorityType getType() {
        return type;
    }

    public void setType(AuthorityType type) {
        this.type = type;
    }
}
