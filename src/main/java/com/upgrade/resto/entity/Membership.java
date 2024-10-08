package com.upgrade.resto.entity;

import com.upgrade.resto.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = ConstantTable.MEMBERSHIP)
public class Membership {
    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.upgrade.resto.utils.MembershipCustomId")
    @Column(name = "membership_id")
    private String membershipId;
    @Column(name = "membership_name")
    private String membershipName;
    @Column(name = "benefit")
    private String benefit;
}
