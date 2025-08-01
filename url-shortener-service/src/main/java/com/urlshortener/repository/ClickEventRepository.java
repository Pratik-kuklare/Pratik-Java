package com.urlshortener.repository;

import com.urlshortener.entity.ClickEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClickEventRepository extends JpaRepository<ClickEvent, Long> {
    
    @Query("SELECT CAST(c.clickedAt AS date) as date, COUNT(c) as clicks " +
           "FROM ClickEvent c WHERE c.url.shortCode = :shortCode " +
           "AND c.clickedAt >= :startDate " +
           "GROUP BY CAST(c.clickedAt AS date) ORDER BY CAST(c.clickedAt AS date)")
    List<Object[]> findDailyClickStats(@Param("shortCode") String shortCode, 
                                      @Param("startDate") LocalDateTime startDate);
    
    Long countByUrlShortCode(String shortCode);
}