package com.smartlab.access_control.repository;

import com.smartlab.access_control.entity.BookingToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface BookingTokenRepository extends JpaRepository<BookingToken, Long> {

    @Query("SELECT t FROM BookingToken t WHERE t.token = :token AND t.doorId = :doorId AND t.validFrom <= :now AND t.validUntil >= :now")
    Optional<BookingToken> findValidToken(@Param("token") String token, @Param("doorId") String doorId, @Param("now") LocalDateTime now);
}