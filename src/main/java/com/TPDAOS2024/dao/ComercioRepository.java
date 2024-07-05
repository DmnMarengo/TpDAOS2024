package com.TPDAOS2024.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TPDAOS2024.domain.Comercio;
//@AUTHOR Damian Mateo Marengo
@Repository
public interface ComercioRepository extends JpaRepository<Comercio, Long> {

}
