package com.upgrade.resto.service;

import com.upgrade.resto.dto.request.BonusRequest;
import com.upgrade.resto.dto.request.SearchBonusRequest;
import com.upgrade.resto.dto.response.BonusResponse;
import org.springframework.data.domain.Page;

public interface BonusService {
    BonusResponse createNewBonus(BonusRequest bonusRequest);

    BonusResponse getById(String bonusId);

    Page<BonusResponse> getAllBonus(SearchBonusRequest searchBonusRequest);

    String updatePoinById(String bonusId, Integer newPoin);

    String deleteById(String bonusId);

    long count();
}
