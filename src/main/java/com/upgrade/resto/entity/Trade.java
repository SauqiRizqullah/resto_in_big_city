package com.upgrade.resto.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.upgrade.resto.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = ConstantTable.TRADE)
public class Trade {
    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.upgrade.resto.utils.TradeCustomId")
    @Column(name = "trd_id")
    private String trdId;
    @ManyToOne
    @JoinColumn(name = "waiter_id")
    @JsonBackReference
    private Waiter waiter;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @Column(name = "total_poin_cost")
    private Integer totalPoinCost;
    @Temporal(TemporalType.DATE)
    @Column(name = "trade_date", updatable = false)
    private Date tradeDate;

    @OneToMany(mappedBy = "trade", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<TradeDetail> tradeDetails;
}
