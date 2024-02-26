package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.helpers.AmountMapper;
import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.models.filters.WalletFilterOptions;
import com.apex.tech3.wallt_app.repositories.WalletRepository;
import com.apex.tech3.wallt_app.services.contracts.WalletService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    private static final int PAGE_SIZE = 2;
    private final WalletRepository repository;

    @Autowired
    public WalletServiceImpl(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public Wallet get(int id) {
        return repository.getReferenceById(id);
    }

    @Override
    public Page<Wallet> getAll(WalletFilterOptions filterOptions) {
        fixAmountsToCents(filterOptions);
        return repository.findByHolderAndCurrency(filterOptions.getHolderId(), filterOptions.getAmountGreaterThan(),
                filterOptions.getAmountLessThan(), filterOptions.getCurrencyId(), createPageable(filterOptions));
    }

    private Pageable createPageable(WalletFilterOptions filterOptions) {
        if (filterOptions.getSortBy() == null || filterOptions.getSortBy().isEmpty())
            return PageRequest.of(filterOptions.getPage(), PAGE_SIZE);

        Sort sort = Sort.by(filterOptions.getSortBy());
        sort = filterOptions.getSortOrder().equals("desc") ? sort.descending() : sort.ascending();
        return PageRequest.of(filterOptions.getPage(), PAGE_SIZE, sort);
    }


    private void fixAmountsToCents(WalletFilterOptions filterOptions) {
        filterOptions.setAmountGreaterThan(AmountMapper.amountToCents(filterOptions.getAmountGreaterThan()));
        filterOptions.setAmountLessThan(AmountMapper.amountToCents(filterOptions.getAmountLessThan()));
    }
}
