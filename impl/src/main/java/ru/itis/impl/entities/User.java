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
    private Long id;

    private String name;

    private String initials;

    private String email;

    private String hashPassword;

    private String confirmCode;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    private AccountStatus accountStatus;

    private String avatar;

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
