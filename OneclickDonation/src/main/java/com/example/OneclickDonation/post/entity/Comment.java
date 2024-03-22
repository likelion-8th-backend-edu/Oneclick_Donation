    package com.example.OneclickDonation.post.entity;
    
    import jakarta.persistence.*;
    import lombok.*;

    @Entity
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Comment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Setter
        private String content;
        @Setter
        @ManyToOne
        private Post post;
    
    }
