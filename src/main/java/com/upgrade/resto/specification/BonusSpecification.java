package com.upgrade.resto.specification;

import com.upgrade.resto.dto.request.SearchBonusRequest;
import com.upgrade.resto.entity.Bonus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BonusSpecification {
    public static Specification<Bonus> getSpecification (SearchBonusRequest request){
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (request.getBonusName() != null) {
                Predicate bonusNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("bonusName")), "%" + request.getBonusName().toLowerCase() + "%");
                predicates.add(bonusNamePredicate);
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
