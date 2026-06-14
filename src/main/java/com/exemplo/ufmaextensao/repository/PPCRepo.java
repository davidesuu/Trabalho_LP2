package com.exemplo.ufmaextensao.repository;

import com.exemplo.ufmaextensao.entity.PPC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PPCRepo extends JpaRepository<PPC, Integer> {
    public Optional<PPC> findPPCById(Integer id);
}
