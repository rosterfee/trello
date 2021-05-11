package ru.itis.impl.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
    private long id;

//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
    private String text;

    @CreationTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "author_id")
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
    private User author;

    @ManyToOne
    @JoinColumn(name = "card_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Card card;

}
