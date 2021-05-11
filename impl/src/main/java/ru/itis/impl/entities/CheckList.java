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
public class CheckList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "card_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Card card;

    @OneToMany(mappedBy = "checkList", fetch = FetchType.EAGER)
    private Set<CheckListTask> checkListTasks;

}
