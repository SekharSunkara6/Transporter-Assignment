package com.example.transporter.repository;

import com.example.transporter.model.LaneQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaneQuoteRepository extends JpaRepository<LaneQuote, Long> {
    List<LaneQuote> findByLaneId(Long laneId);

    List<LaneQuote> findByTransporterId(Long transporterId);
}
