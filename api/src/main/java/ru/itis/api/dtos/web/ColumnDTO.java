package ru.itis.api.dtos.web;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ColumnDTO {

    private long id;
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private BoardDTO board;

    private List<CardDTO> cards;

}
