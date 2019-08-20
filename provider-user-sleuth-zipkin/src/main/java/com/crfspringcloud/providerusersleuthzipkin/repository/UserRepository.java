package com.crfspringcloud.providerusersleuthzipkin.repository;

import com.crfspringcloud.providerusersleuthzipkin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author caoruifeng
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
