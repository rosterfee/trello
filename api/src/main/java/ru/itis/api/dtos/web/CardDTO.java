package ru.itis.api.dtos.web;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CardDTO {

    private long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ColumnDTO column;

    private String title;
    private String description;
    private List<CommentDTO> comments;
    private List<PhotoDTO> photos;
    private List<CheckListDTO> checkLists;
    private DeadlineDTO deadline;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<UserDTO> participants;

}
