package com.example.OneclickDonation.admin.dto;

import com.example.OneclickDonation.member.entity.MemberUpgrade;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpgradeAdminDto {
    private Long id;
    private String organization;
    private Integer businessNumber;
    private String applicationReason;
    private String rejectReason;
    private Boolean approved;


    public static UpgradeAdminDto fromEntity(MemberUpgrade entity) {
        return UpgradeAdminDto.builder()
                .id(entity.getId())
                .organization(entity.getOrganization())
                .businessNumber(entity.getBusinessNumber())
                .applicationReason(entity.getApplicationReason())
                .approved(entity.getApproved())
                .rejectReason(entity.getRejectReason())
                .build();
    }
}
