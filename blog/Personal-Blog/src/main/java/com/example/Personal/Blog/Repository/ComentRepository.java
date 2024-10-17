package com.example.app.repository;

import com.example.app.Coment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentRepository extends JpaRepository<Coment, Long> {
}
