package com.apex.tech3.wallt_app.models.filters;

import com.apex.tech3.wallt_app.models.Transfer;
import com.apex.tech3.wallt_app.models.enums.DirectionEnum;
import org.springframework.data.jpa.domain.Specification;

public class TransferSpecification {


    public static Specification<Transfer> filterByWalletOwner(Integer ownerId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("wallet").get("holder").get("id"), ownerId);
    }

    public static Specification<Transfer> filterByWalletOwnerAndDirection(Integer ownerId, DirectionEnum direction) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("walletId").get("holderId"), ownerId),
                criteriaBuilder.equal(root.get("direction"), direction));
    }

}
