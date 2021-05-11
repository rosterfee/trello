package ru.itis.impl.entities;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class CheckListTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private String title;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "cheklist_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CheckList checkList;

}
