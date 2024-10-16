package com.upgrade.resto.utils;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class UserAccountCustomId implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        String prefix = "ACC";
        String query = "SELECT COALESCE(MAX(CAST(SUBSTRING(user_account_id, 4) AS INTEGER)), 0) FROM m_accounts";
        Integer max = (Integer) sharedSessionContractImplementor.createNativeQuery(query).getSingleResult();
        int nextId = (max == null ? 1 : max + 1);
        return prefix + String.format("%03d", nextId);
    }
}
