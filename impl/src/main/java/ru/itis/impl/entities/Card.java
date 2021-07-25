package ru.itis.impl.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "column_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Column column;

    private String title;
    private String description;

    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER)
    private Set<Photo> photos;

    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER)
    private Set<CheckList> checkLists;

    @OneToOne(mappedBy = "card", fetch = FetchType.EAGER)
    private Deadline deadline;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "card_participant",
                joinColumns = @JoinColumn(name = "card_id"),
                inverseJoinColumns = @JoinColumn(name = "participant_id"))
    private Set<User> participants;

}
