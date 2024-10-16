package com.upgrade.resto.specification;

import com.upgrade.resto.dto.request.SearchRestaurantRequest;
import com.upgrade.resto.entity.Restaurant;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RestaurantSpecification {
    public static Specification<Restaurant> getSpecification (SearchRestaurantRequest searchRestaurantRequest){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (searchRestaurantRequest.getCity() != null) {
                Predicate cityPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("city")), "%" + searchRestaurantRequest.getCity().toLowerCase() + "%");
                predicates.add(cityPredicate);
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
