package com.TPDAOS2024.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TPDAOS2024.dao.PatenteRepository;
import com.TPDAOS2024.domain.Patente;

@Service
public class PatenteServiceImp implements PatenteService {

	@Autowired
	private PatenteRepository patenteRepository;
	
	@Override
	public Patente guardarPatente(Patente patente) {
	    Patente patenteExistente = patenteRepository.findById(patente.getNumeroPatente()).orElse(null);
	    if (patenteExistente != null) {
	        throw new IllegalArgumentException("La patente ya existe: " + patente.getNumeroPatente());
	    }
	    return patenteRepository.save(patente);
	}

	@Override
	public Patente editarPatente(Patente patente) {
	    Patente patenteExistente = patenteRepository.findById(patente.getNumeroPatente()).orElse(null);
	    if (patenteExistente != null) {
	        throw new IllegalArgumentException("La patente ya existe: " + patente.getNumeroPatente());
	    }
	    return patenteRepository.save(patente);
	}

	@Override
	public void borrarPatente(String patente) {
		patenteRepository.deleteById(patente);
		
	}

	@Override
	public Patente obtenerPorId(String patente) {
		return patenteRepository.getById(patente);
	}

}
