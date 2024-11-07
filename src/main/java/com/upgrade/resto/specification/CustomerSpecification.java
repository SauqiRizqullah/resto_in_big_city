package com.upgrade.resto.specification;

import com.upgrade.resto.dto.request.SearchCustomerRequest;
import com.upgrade.resto.dto.request.SearchMenuRequest;
import com.upgrade.resto.entity.Customer;
import com.upgrade.resto.entity.Menu;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CustomerSpecification {
    public static Specification<Customer> getSpecification (SearchCustomerRequest request){
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (request.getCustomerName() != null) {
                Predicate menuCustomerPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("customerName")), "%" + request.getCustomerName().toLowerCase() + "%");
                predicates.add(menuCustomerPredicate);
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
