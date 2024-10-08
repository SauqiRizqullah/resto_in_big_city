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
@Table(name = ConstantTable.TRANSACTION)
public class Transaction {
    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.upgrade.resto.utils.TransactionCustomId")
    @Column(name = "trx_id")
    private String trxId;

    @ManyToOne
    @JoinColumn(name = "waiter_id")
    @JsonBackReference
    private Waiter waiter;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @Column(name = "total_price")
    private Long totalPrice;
    @Temporal(TemporalType.DATE)
    @Column(name = "trans_date", updatable = false)
    private Date transDate;

    @OneToMany
    @JsonManagedReference
    private List<TransactionDetail> transactionDetails;
}
