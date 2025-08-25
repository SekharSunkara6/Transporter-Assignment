package com.example.transporter.repository;

import com.example.transporter.model.Transporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransporterRepository extends JpaRepository<Transporter, Long> {
}
