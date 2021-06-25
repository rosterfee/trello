package ru.itis.impl.entities;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "board_column")
public class Column {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "board_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Board board;

    @OneToMany(mappedBy = "column", fetch = FetchType.EAGER)
    private Set<Card> cards;

}
