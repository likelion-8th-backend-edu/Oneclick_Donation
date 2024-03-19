package com.example.OneclickDonation.dto;

import com.example.OneclickDonation.entity.Post;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    @Setter
    private String title;
    @Setter
    private String description;

//    private ArticleWriterDto writer;
//    @Setter
//    private String writer;

    public PostDto(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public static PostDto fromEntity(Post entity) {
        return PostDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .build();
    }
}
