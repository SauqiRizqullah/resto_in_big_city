package com.upgrade.resto.service.impl;

import com.upgrade.resto.dto.request.MenuRequest;
import com.upgrade.resto.dto.request.SearchMenuRequest;
import com.upgrade.resto.dto.response.MenuResponse;
import com.upgrade.resto.entity.Menu;
import com.upgrade.resto.repository.MenuRepository;
import com.upgrade.resto.service.MenuService;
import com.upgrade.resto.specification.MenuSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MenuResponse createNewMenu(MenuRequest menuRequest) {
        // 1. Membuat objek menu

        Menu newMenu = Menu.builder()
                .menuName(menuRequest.getMenuName())
                .menuPrice(menuRequest.getMenuPrice())
                .build();

        // 2. Melakukan penyimpanan data di database (saveAndFlush)

        menuRepository.saveAndFlush(newMenu);

        // 3. Mengembalikan objek menjadi MenuResponse melalui parse

        return parseMenuToMenuResponse(newMenu);
    }

    private MenuResponse parseMenuToMenuResponse(Menu newMenu) {
        // 1. Mengecek apakah id objek tersebut ada
        String id;
        if (newMenu.getMenuId() == null) {
            id = null;
        } else {
            id = newMenu.getMenuId();
        }

        return MenuResponse.builder()
                .menuId(id)
                .menuName(newMenu.getMenuName())
                .menuPrice(newMenu.getMenuPrice())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public MenuResponse getById(String menuId) {
        return parseMenuToMenuResponse(menuRepository.findById(menuId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Id was not found!!!")));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<MenuResponse> getAllMenus(SearchMenuRequest searchMenuRequest) {
        // 1. Ketika nilai halaman 0, maka buatlah menjadi 1
        if(searchMenuRequest.getPage() <= 0){
            searchMenuRequest.setPage(1);
        }

        // 2. Membuat validasi pengurutan halaman dengan kolom - kolom yang tersedia
        String validSortBy;
        if("menuName".equalsIgnoreCase(searchMenuRequest.getSortBy()) || "menuPrice".equalsIgnoreCase(searchMenuRequest.getSortBy())) {
            validSortBy = searchMenuRequest.getSortBy();
        } else {
            validSortBy = "menuId";
        }

        // 3. Membuat aturan sortBy dengan objek sort
        Sort sort = Sort.by(Sort.Direction.fromString(searchMenuRequest.getDirection()), validSortBy);

        //4. Membuat objek halaman Pageable untuk membuat sebuah halaman
        Pageable pageable = PageRequest.of(searchMenuRequest.getPage() - 1, searchMenuRequest.getSize(), sort);

        //5. Menyelaraskan dengan rule query dari specification milik objek itu
        Specification<Menu> specification = MenuSpecification.getSpecification(searchMenuRequest);

        return menuRepository.findAll(specification,pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updatePriceById(String menuId, Long newMenuPrice) {

        Menu menuObject = menuRepository.findById(menuId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu Id tidak ditemukan"));

        menuObject.setMenuPrice(newMenuPrice);

        menuRepository.saveAndFlush(menuObject);
        return "The new price of " + menuObject.getMenuName() + " is " + newMenuPrice;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String deleteByMenuId(String menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new RuntimeException("Menu Id tidak ditemukan"));
        menuRepository.delete(menu);
        return "We are so sad that " + menuId + "'s data is gone forever";
    }


    @Override
    public Long count() {
        return menuRepository.count();
    }
}
