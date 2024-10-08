package com.upgrade.resto.specification;

import com.upgrade.resto.dto.request.SearchMenuRequest;
import com.upgrade.resto.entity.Menu;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class MenuSpecification {
    public static Specification<Menu> getSpecification (SearchMenuRequest request){
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (request.getMenuName() != null) {
                Predicate menuNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("menuName")), "%" + request.getMenuName().toLowerCase() + "%");
                predicates.add(menuNamePredicate);
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
