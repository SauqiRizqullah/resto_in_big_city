package com.upgrade.resto.service.impl;

import com.upgrade.resto.dto.request.BonusRequest;
import com.upgrade.resto.dto.request.MembershipRequest;
import com.upgrade.resto.dto.request.MenuRequest;
import com.upgrade.resto.dto.request.RoleRequest;
import com.upgrade.resto.dto.response.MembershipResponse;
import com.upgrade.resto.dto.response.MenuResponse;
import com.upgrade.resto.dto.response.RoleResponse;
import com.upgrade.resto.service.BonusService;
import com.upgrade.resto.service.MembershipService;
import com.upgrade.resto.service.MenuService;
import com.upgrade.resto.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder {

    private final RoleService roleService;

    private final MenuService menuService;

    private final MembershipService membershipService;

    private final BonusService bonusService;

    @PostConstruct
    public void init(){
        if(roleService.count() == 0){
            RoleRequest roleCustomer = new RoleRequest();
            roleCustomer.setRoleName("CUSTOMER");

            RoleResponse roleCustomerResponse = roleService.createNewRole(roleCustomer);

            RoleRequest roleWaiter = new RoleRequest();
            roleWaiter.setRoleName("WAITER");

            RoleResponse roleWaiterResponse = roleService.createNewRole(roleWaiter);

            RoleRequest roleCaptain = new RoleRequest();
            roleCaptain.setRoleName("CAPTAIN");

            RoleResponse roleCaptainResponse = roleService.createNewRole(roleCaptain);
            log.info("Successfully created role data");
        } else {
            log.info("Role data had already been created");
        }
        if(menuService.count() == 0){
            MenuRequest wagyuA5 = new MenuRequest();
            wagyuA5.setMenuName("Wagyu A5");
            wagyuA5.setMenuPrice(Long.valueOf(169000));

            MenuResponse wagyuResponse = menuService.createNewMenu(wagyuA5);

            MenuRequest chickenCurryOmeleteRice = new MenuRequest();
            chickenCurryOmeleteRice.setMenuName("Chicken Curry Omelete Rice");
            chickenCurryOmeleteRice.setMenuPrice(Long.valueOf(104000));

            MenuResponse chickenCurryOmeleteRiceResponse = menuService.createNewMenu(chickenCurryOmeleteRice);

            MenuRequest karageBentoBBQSauce = new MenuRequest();
            karageBentoBBQSauce.setMenuName("Karage Bento BBQ Sauce");
            karageBentoBBQSauce.setMenuPrice(Long.valueOf(74000));

            MenuResponse karageBentoBBQSauceResponse = menuService.createNewMenu(karageBentoBBQSauce);
            log.info("Successfully created menu data");
        } else {
            log.info("Menu data had already been created");
        }

        if (membershipService.count() == 0){
            MembershipRequest none = new MembershipRequest();
            none.setMembershipName("NONE");
            none.setBenefit("-");

            MembershipResponse noneResponse = membershipService.createNewMembership(none);

            MembershipRequest silver = new MembershipRequest();
            silver.setMembershipName("SILVER");
            silver.setBenefit("Get 3 points per transaction");

            MembershipResponse silverResponse = membershipService.createNewMembership(silver);

            MembershipRequest GOLD = new MembershipRequest();
            GOLD.setMembershipName("GOLD");
            GOLD.setBenefit("Get 7 points per transaction");

            MembershipResponse goldResponse = membershipService.createNewMembership(GOLD);
            log.info("Successfully created membership data");
        } else {
            log.info("Membership data had been created");
        }

        if(bonusService.count() == 0){
            BonusRequest topiLebar = new BonusRequest();
            topiLebar.setBonusName("Topi Lebar");
            topiLebar.setPoinCost(15);

            bonusService.createNewBonus(topiLebar);

            log.info("Successfully created bonus data");
        } else {
            log.info("Bonus data had been created");
        }

    }
}
