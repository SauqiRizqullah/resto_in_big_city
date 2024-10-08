package com.upgrade.resto.entity;

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
@Table(name = ConstantTable.ROLE)
public class Role {
    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.upgrade.resto.utils.RoleCustomId")
    @Column(name = "role_id")
    private String roleId;
    @Column(name = "role_name")
    private String roleName;
}
