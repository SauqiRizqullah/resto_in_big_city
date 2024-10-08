package com.upgrade.resto.service;


import com.upgrade.resto.dto.request.MenuRequest;
import com.upgrade.resto.dto.request.SearchMenuRequest;
import com.upgrade.resto.dto.response.MenuResponse;
import org.springframework.data.domain.Page;

public interface MenuService {
    MenuResponse createNewMenu(MenuRequest menuRequest);

    MenuResponse getById(String menuId);

    Page<MenuResponse> getAllMenus(SearchMenuRequest searchMenuRequest);

    String updatePriceById (String menuId, Long newMenuPrice);

    String deleteByMenuId (String menuId);

    Long count();
}
