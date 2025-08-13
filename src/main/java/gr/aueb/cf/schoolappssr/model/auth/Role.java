package gr.aueb.cf.schoolappssr.model.auth;

import gr.aueb.cf.schoolappssr.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<User> users =  new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_capabilities",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "capability_id")
    )
    private Set<Capability> capabilities = new HashSet<>();

    public void addCapability( Capability capability) {
        this.capabilities.add(capability);
        capability.getRoles().add(this);
    }
     public void removeCapability( Capability capability) {
        this.capabilities.remove(capability);
        capability.getRoles().remove(this);
     }

    public void addUser( User user) {
        this.users.add(user);
        user.setRole(this);
    }
    public void removeUser( User user){
        this.users.remove(user);
        user.setRole(null);
    }

    public void addUsers(Collection<User> users) {
        users.forEach(this::addUser);
    }


}
