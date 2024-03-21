package com.example.OneclickDonation.post.dto;

import com.example.OneclickDonation.post.entity.Post;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String description;
    private Integer targetAmount;
    private String postImage;

    public PostDto(String title, String description, Integer targetAmount, String postImage) {
        this.title = title;
        this.description = description;
        this.targetAmount = targetAmount;
        this.postImage = postImage;
    }

    public static PostDto fromEntity(Post entity) {
        return new PostDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getTargetAmount(),
                entity.getPostImage()
        );
    }
}