    package com.upgrade.resto.entity;

    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import com.upgrade.resto.constant.ConstantTable;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.Pattern;
    import lombok.*;
    import org.hibernate.annotations.GenericGenerator;

    import java.util.List;

    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @Builder
    @Table(name = ConstantTable.RESTAURANT)
    public class Restaurant {
        @Id
        @GeneratedValue(generator = "custom-id")
        @GenericGenerator(name = "custom-id", strategy = "com.upgrade.resto.utils.RestaurantCustomId")
        @Column(name = "resto_id")
        private String restoId;
        @Column(name = "outlet")
        private String outlet;


//        @Pattern(
//                regexp = "^[A-Za-z0-9.]+@(gmail|yahoo|hotmail|outlook)\\.(com|co\\.id)$",
//                message = "Invalid email format. Must end with .com or .co.id and use domain gmail, yahoo, or hotmail."
//        )
//        @Column(name = "email", nullable = false, unique = true)
//        private String email;
//
//        @Column(name = "username", nullable = false, unique = true)
//        private String username;
//
//        @Column(name = "password", nullable = false)
//        private String password;

        @Column(name = "city")
        private String city;
        @Column(name = "province")
        private String province;
        @Pattern(regexp = "^08\\d{8}(\\d{2})?$", message = "Invalid contact number. It must start with '08' and be 10 or 12 digits long.")
        @Column(name = "contact")
        private String contact;

        @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonManagedReference
        private List<Waiter> waiters;

    }
