package com.upgrade.resto.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.upgrade.resto.constant.ConstantTable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = ConstantTable.CUSTOMER)
public class Customer implements UserDetails {
    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.upgrade.resto.utils.CustomerCustomId")
    @Column(name = "customer_id")
    private String customerId;

    @Pattern(
            regexp = "^[A-Za-z0-9.]+@(gmail|yahoo|hotmail|outlook)\\.(com|co\\.id)$",
            message = "Invalid email format. Must end with .com or .co.id and use domain gmail, yahoo, or hotmail."
    )
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;


    @ManyToOne
    @JoinColumn(name = "waiter_id")
    @JsonBackReference
    private Waiter waiter;
    @Column(name = "resto_id")
    private String restoId;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "phone_no")
    @Pattern(regexp = "^08\\d{8}(\\d{2})?$", message = "Invalid contact number. It must start with '08' and be 10 or 12 digits long.")
    private String phoneNo;
    @ManyToOne
    @JoinColumn(name = "membership_id")
    private Membership membership;
    @Column(name = "poin")
    private Integer poin;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
