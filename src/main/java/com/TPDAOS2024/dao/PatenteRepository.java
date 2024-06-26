package com.TPDAOS2024.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.TPDAOS2024.domain.Patente;

@Repository
public interface PatenteRepository extends JpaRepository<Patente, String>{

}
