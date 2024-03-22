package com.example.OneclickDonation.post.dto;

import com.example.OneclickDonation.post.entity.Comment;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentDto {
    private Long id;
    private String content;

    public CommentDto(String content) {
        this.content = content;
    }

    public static CommentDto fromEntity(Comment entity) {
        CommentDto dto = new CommentDto();
        dto.id = entity.getId();
        dto.content = entity.getContent();
        return dto;
    }
}
