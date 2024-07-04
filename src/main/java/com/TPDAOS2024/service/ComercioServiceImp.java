package com.TPDAOS2024.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TPDAOS2024.dao.ComercioRepository;
import com.TPDAOS2024.domain.Comercio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class ComercioServiceImp implements ComercioService {
	
	@Autowired
    private ComercioRepository comercioRepository;

	@Override
	public Comercio guardarComercio(Comercio Comercio) {
	    Comercio.setEstado(true);
        return comercioRepository.save(Comercio);
    }


	@Override
	public Comercio editarComercio(Long cuit, Comercio comercioActualizado) {
		  Comercio comercio = comercioRepository.findById(cuit)
	                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));

	        comercio.setRazonSocial(comercioActualizado.getRazonSocial());
	        comercio.setDireccion(comercioActualizado.getDireccion());

	        return comercioRepository.save(comercio);
	    }

	@Override
	public void borrarComercio(Long cuit) {
		 Comercio comercio = comercioRepository.findById(cuit)
	                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));

	        comercio.setEstado(false); 
	        comercioRepository.save(comercio);
	}

	@Override
	public Comercio consultarComercio(Long cuit) {
	    return comercioRepository.findById(cuit)
                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));
    }


	@Override
	public List<Comercio> listarComercios() {
	  return comercioRepository.findAll();
	  
	  
	}
	


}
