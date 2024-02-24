package com.apex.tech3.wallt_app.repositories;

import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.repositories.contracts.UserRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends AbstractCRUDRepository<User> implements UserRepository {
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        super(User.class, sessionFactory);
    }

}
