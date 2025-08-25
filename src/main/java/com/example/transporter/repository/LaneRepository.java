package com.example.transporter.repository;

import com.example.transporter.model.Lane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaneRepository extends JpaRepository<Lane, Long> {
}
