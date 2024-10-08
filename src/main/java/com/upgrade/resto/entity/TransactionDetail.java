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
@Table(name = ConstantTable.TRANSACTIONDETAIL)
public class TransactionDetail {
    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.upgrade.resto.utils.TransactionDetailCustomId")
    @Column(name = "trx_detail_id")
    private String trxDetailId;
    @ManyToOne
    @JoinColumn(name = "trx_id")
    @JsonBackReference
    private Transaction transaction;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
    @Column(name = "menu_price")
    private Long menuPrice;
    @Column(name = "qty")
    private Integer qty;
}
