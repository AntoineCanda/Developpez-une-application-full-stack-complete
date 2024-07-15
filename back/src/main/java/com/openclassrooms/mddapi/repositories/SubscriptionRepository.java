package com.openclassrooms.mddapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.User;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<List<Subscription>> findByUser(User user);

    Optional<Subscription> findByUserAndSubject(User user, Subject subject);

}
