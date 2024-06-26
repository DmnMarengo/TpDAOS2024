package com.TPDAOS2024.service;

import com.TPDAOS2024.domain.Patente;

public interface PatenteService {
	
	Patente guardarPatente(Patente patente);
	
	Patente editarPatente(Patente patente);
	
	void borrarPatente(String patente);
	
	Patente obtenerPorId(String patente);

}
