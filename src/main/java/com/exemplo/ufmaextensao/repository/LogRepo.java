package com.exemplo.ufmaextensao.repository;

import com.exemplo.ufmaextensao.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepo extends JpaRepository<Log, Integer> {
}
