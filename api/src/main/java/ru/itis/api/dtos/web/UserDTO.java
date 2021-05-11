package ru.itis.api.dtos.web;

import lombok.*;
import ru.itis.api.enums.AccountStatus;
import ru.itis.api.enums.Role;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -2160362670988227323L;

    private Long id;
    private String name;
    private String initials;
    private String email;
    private String confirmCode;
    private Role role;
    private AccountStatus accountStatus;
    private String avatar;
    private Long vkId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<BoardDTO> createdBoards;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CommentDTO> comments;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<BoardDTO> boards;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CardDTO> cards;

}
