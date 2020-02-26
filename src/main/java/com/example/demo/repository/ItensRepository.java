package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.ItensCompras;

public interface ItensRepository extends JpaRepository<ItensCompras, Long> {

}
