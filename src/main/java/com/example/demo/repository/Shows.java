package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.NovoShow;

public interface Shows extends JpaRepository<NovoShow, Long> {

}
