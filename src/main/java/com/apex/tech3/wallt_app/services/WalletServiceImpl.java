package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.models.Wallet;
import com.apex.tech3.wallt_app.models.filters.WalletFilterOptions;
import com.apex.tech3.wallt_app.repositories.contracts.WalletRepository;
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
    private final WalletRepository repository;

    @Autowired
    public WalletServiceImpl(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public Wallet get(int id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Page<Wallet> getAll(WalletFilterOptions filterOptions) {
        amountToCents(filterOptions);
        return repository.findByHolderAndCurrency(filterOptions.getHolderId(), filterOptions.getAmountGreaterThan(),
                filterOptions.getAmountLessThan(), filterOptions.getCurrencyId(), createPageable(filterOptions));
    }

    private Pageable createPageable(WalletFilterOptions filterOptions) {
        Sort sort = filterOptions.getSortBy() != null && !filterOptions.getSortBy().isEmpty() ?
                Sort.by(filterOptions.getSortBy()) : null;
        sort = sort != null ? filterOptions.getSortOrder().equals("desc") ? sort.descending() : sort.ascending() : null;
        return sort != null ? PageRequest.of(filterOptions.getPage(), 2, sort) : PageRequest.of(filterOptions.getPage(), 2);
    }


    private void amountToCents(WalletFilterOptions filterOptions) {
        filterOptions.setAmountLessThan(filterOptions.getAmountLessThan() != null ? filterOptions.getAmountLessThan() * 100 : null);
        filterOptions.setAmountGreaterThan(filterOptions.getAmountGreaterThan() != null ? filterOptions.getAmountGreaterThan() * 100 : null);
    }
}
