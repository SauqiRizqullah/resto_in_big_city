package com.upgrade.resto.utils;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class TransactionDetailCustomId implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        String prefix = "TRXDETAIL";
        String query = "SELECT COALESCE(MAX(CAST(SUBSTRING(trx_detail_id, 10) AS INTEGER)), 0) FROM t_transaction_details";
        Integer max = (Integer) sharedSessionContractImplementor.createNativeQuery(query).getSingleResult();
        int nextId = (max == null ? 1 : max + 1);
        return prefix + String.format("%03d", nextId);
    }
}
