package com.upgrade.resto.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = ConstantTable.TRADEDETAIL)
public class TradeDetail {
    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.upgrade.resto.utils.TradeDetailCustomId")
    @Column(name = "trd_detail_id")
    private String trdDetailId;
    @ManyToOne
    @JoinColumn(name = "trd_id")
    @JsonBackReference
    private Trade trade;
    @ManyToOne
    @JoinColumn(name = "bonus_id")
    private Bonus bonus;
    @Column(name = "poin_cost")
    private Integer poinCost;
}
