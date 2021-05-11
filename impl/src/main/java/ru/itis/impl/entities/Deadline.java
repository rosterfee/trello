package ru.itis.impl.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Deadline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Date date;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private boolean done;

    @OneToOne
    @JoinColumn(name = "card_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Card card;

}
