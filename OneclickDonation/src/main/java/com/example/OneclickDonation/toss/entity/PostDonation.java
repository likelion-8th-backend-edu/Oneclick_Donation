package com.example.OneclickDonation.toss.entity;

import com.example.OneclickDonation.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PostDonation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String tossPaymentKey;
    private String tossDonationId;
    @Setter
    private String status;
    private Integer amount;
}
