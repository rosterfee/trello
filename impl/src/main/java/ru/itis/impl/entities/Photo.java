package ru.itis.impl.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Photo {

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
    private String content;

    @CreatedDate
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Date loadedAt;

    @ManyToOne
    @JoinColumn(name = "card_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Card card;

}
