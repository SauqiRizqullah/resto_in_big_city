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
@Table(name = ConstantTable.BONUS)
public class Bonus {
    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.upgrade.resto.utils.BonusCustomId")
    @Column(name = "bonus_id")
    private String bonusId;
    @Column(name = "bonus_name")
    private String bonusName;
    @Column(name = "poin_cost")
    private Integer poinCost;
}
