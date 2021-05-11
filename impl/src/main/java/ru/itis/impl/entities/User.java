package ru.itis.impl.entities;

import lombok.*;
import org.springframework.stereotype.Component;
import ru.itis.api.enums.AccountStatus;
import ru.itis.api.enums.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "account")
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = -2178291171303770599L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String initials;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private String email;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private String hashPassword;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private String confirmCode;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Enumerated(value = EnumType.STRING)
    private AccountStatus accountStatus;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private String avatar;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long vkId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "creator", fetch = FetchType.EAGER)
    private Set<Board> createdBoards;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private Set<Comment> comments;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "participants", fetch = FetchType.EAGER)
    private Set<Board> boards;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "participants", fetch = FetchType.EAGER)
    private Set<Card> cards;

}
