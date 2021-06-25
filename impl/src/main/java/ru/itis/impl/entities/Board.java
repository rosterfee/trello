package ru.itis.impl.entities;

import lombok.*;
import ru.itis.api.enums.BoardType;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private String name;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User creator;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Column> columns;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "board_participant",
                joinColumns = @JoinColumn(name = "board_id"),
                inverseJoinColumns = @JoinColumn(name = "participant_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<User> participants;

    @Enumerated(value = EnumType.STRING)
    private BoardType boardType;

}
