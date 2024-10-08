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
@Table(name = ConstantTable.MENU)
public class Menu {
    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.upgrade.resto.utils.MenuCustomId")
    @Column(name = "menu_id")
    private String menuId;
    @Column(name = "menu_name")
    private String menuName;
    @Column(name = "menu_price")
    private Long menuPrice;
}
