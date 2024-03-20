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
    @Setter
    private Integer targetAmount;
    @Setter
    private String postImage; // imageUrl을 postImage로 변경

    public PostDto(String title, String description, Integer targetAmount, String postImage) {
        this.title = title;
        this.description = description;
        this.targetAmount = targetAmount;
        this.postImage = postImage;
    }

    public static PostDto fromEntity(Post entity) {
        return PostDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .targetAmount(entity.getTargetAmount())
                .postImage(entity.getPostImage()) // imageUrl을 postImage로 변경
                .build();
    }
}