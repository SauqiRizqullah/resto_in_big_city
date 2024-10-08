package com.upgrade.resto.service.impl;

import com.upgrade.resto.dto.request.BonusRequest;
import com.upgrade.resto.dto.request.SearchBonusRequest;
import com.upgrade.resto.dto.response.BonusResponse;
import com.upgrade.resto.entity.Bonus;
import com.upgrade.resto.entity.Menu;
import com.upgrade.resto.repository.BonusRepository;
import com.upgrade.resto.service.BonusService;
import com.upgrade.resto.specification.BonusSpecification;
import com.upgrade.resto.specification.MenuSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class BonusServiceImpl implements BonusService {

    private final BonusRepository bonusRepository;

    @Override
    public BonusResponse createNewBonus(BonusRequest bonusRequest) {
        Bonus bonus = Bonus
                .builder()
                .bonusName(bonusRequest.getBonusName())
                .poinCost(bonusRequest.getPoinCost())
                .build();

        bonusRepository.saveAndFlush(bonus);

        return parseBonusToBonusReponse(bonus);
    }

    private BonusResponse parseBonusToBonusReponse(Bonus bonus) {
        String id;
        if (bonus.getBonusId() == null){
            id = null;
        } else {
            id = bonus.getBonusId();
        }

        return BonusResponse.builder()
                .bonusId(id)
                .bonusName(bonus.getBonusName())
                .poinCost(bonus.getPoinCost())
                .build();
    }

    @Override
    public BonusResponse getById(String bonusId) {
        Bonus bonus = bonusRepository.findById(bonusId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"The data was not there"));

        return parseBonusToBonusReponse(bonus);
    }

    @Override
    public Page<BonusResponse> getAllBonus(SearchBonusRequest searchBonusRequest) {
        // 1. Ketika nilai halaman 0, maka buatlah menjadi 1
        if(searchBonusRequest.getPage() <= 0){
            searchBonusRequest.setPage(1);
        }

        // 2. Membuat validasi pengurutan halaman dengan kolom - kolom yang tersedia
        String validSortBy;
        if("bonusName".equalsIgnoreCase(searchBonusRequest.getSortBy()) || "poinCost".equalsIgnoreCase(searchBonusRequest.getSortBy())) {
            validSortBy = searchBonusRequest.getSortBy();
        } else {
            validSortBy = "bonusId";
        }

        // 3. Membuat aturan sortBy dengan objek sort
        Sort sort = Sort.by(Sort.Direction.fromString(searchBonusRequest.getDirection()), validSortBy);

        //4. Membuat objek halaman Pageable untuk membuat sebuah halaman
        Pageable pageable = PageRequest.of(searchBonusRequest.getPage() - 1, searchBonusRequest.getSize(), sort);

        //5. Menyelaraskan dengan rule query dari specification milik objek itu
        Specification<Bonus> specification = BonusSpecification.getSpecification(searchBonusRequest);

        return bonusRepository.findAll(specification,pageable);
    }

    @Override
    public String updatePoinById(String bonusId, Integer newPoin) {
        Bonus bonus = bonusRepository.findById(bonusId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"The data was not there"));

        bonus.setPoinCost(newPoin);

        bonusRepository.saveAndFlush(bonus);
        return bonusId + "'s data has been updated";
    }

    @Override
    public String deleteById(String bonusId) {
        Bonus bonus = bonusRepository.findById(bonusId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"The data was not there"));

        bonusRepository.delete(bonus);

        return bonusId + "'s data has been deleted";
    }

    @Override
    public long count() {
        return bonusRepository.count();
    }
}
