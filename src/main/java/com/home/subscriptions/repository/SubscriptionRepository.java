package com.home.subscriptions.repository;

import com.home.subscriptions.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @EntityGraph(attributePaths = {"user"})
    Page<Subscription> findByUserId(Long userId, Pageable pageable);

    @Query("""
        SELECT s.serviceName
        FROM Subscription s
        GROUP BY s.serviceName
        ORDER BY COUNT(s.serviceName) DESC
        LIMIT 3
""")
    List<String> findTopThreeSubscriptions();

}
