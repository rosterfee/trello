package ru.itis.impl.entities;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "board_column")
@EntityListeners(AuditingEntityListener.class)
public class Column {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @javax.persistence.Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private long createdDate;

    @ManyToOne
    @JoinColumn(name = "board_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Board board;

    @OneToMany(mappedBy = "column", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Card> cards;

}
