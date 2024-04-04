package com.example.OneclickDonation.post.dto;

import com.example.OneclickDonation.post.entity.News;
import com.example.OneclickDonation.post.entity.Post;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class NewsDto {
    private Long id;
    private String content;
    private String newsImage;
    private Post postId;

    public NewsDto(String imageUrl, String newsContent) {
        this.newsImage = imageUrl;
        this.content = newsContent;
    }

    public static NewsDto fromEntity(News entity) {
        return NewsDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .newsImage(entity.getNewsImage())
                .postId(entity.getPost())
                .build();
    }
}