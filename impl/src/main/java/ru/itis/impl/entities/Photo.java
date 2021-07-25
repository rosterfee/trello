package ru.itis.impl.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String content;

    @CreatedDate
    private long loadedAt;

    @ManyToOne
    @JoinColumn(name = "card_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Card card;

}
