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
    private long id;

    private String title;
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "cheklist_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CheckList checkList;

}
