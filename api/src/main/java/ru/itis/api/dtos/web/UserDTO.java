package ru.itis.api.dtos.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.itis.api.enums.AccountStatus;
import ru.itis.api.enums.Role;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

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
    @JsonIgnore
    private Set<BoardDTO> createdBoards;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<CommentDTO> comments;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<BoardDTO> boards;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<CardDTO> cards;

}
