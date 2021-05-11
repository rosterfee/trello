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
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private long id;

    @ManyToOne
    @JoinColumn(name = "column_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Column column;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private String title;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private String description;

    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Comment> comments;

    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Photo> photos;

    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<CheckList> checkLists;

    @OneToOne(mappedBy = "card", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Deadline deadline;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "card_participant",
                joinColumns = @JoinColumn(name = "card_id"),
                inverseJoinColumns = @JoinColumn(name = "participant_id"))
    private Set<User> participants;

}
