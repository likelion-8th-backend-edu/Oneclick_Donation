package com.example.OneclickDonation.post.dto;

import com.example.OneclickDonation.Enum.Status;
import com.example.OneclickDonation.member.entity.Member;
import com.example.OneclickDonation.post.entity.Post;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String description;
    private Integer targetAmount;
    private String postImage;
    private String organization;
    private Status status;  // 상태(진행중, 종료)
    private final List<CommentDto> comments = new ArrayList<>();
    private String startDate;
    private String endDate;
    private String news;
    private Integer donationPeople;
    private Integer supportAmount;


    public PostDto(String title, String description, Integer targetAmount, String postImage, String startDate, String endDate, String news, String organization) {
        this.title = title;
        this.description = description;
        this.targetAmount = targetAmount;
        this.postImage = postImage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.news = news;
        this.organization = organization;
    }

    // 빌더로 변경해도 좋을것 같습니다.
    public static PostDto fromEntity(Post entity) {
        return PostDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .targetAmount(entity.getTargetAmount())
                .postImage(entity.getPostImage())
                .status(entity.getStatus())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .news(entity.getNews())
                .donationPeople(entity.getDonationPeople())
                .supportAmount(entity.getSupportAmount())
                .organization(entity.getOrganization())
                .build();
    }

}