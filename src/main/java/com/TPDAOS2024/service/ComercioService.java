package com.TPDAOS2024.service;
import java.util.List;

import com.TPDAOS2024.domain.Comercio;


public interface ComercioService {

    Comercio guardarComercio(Comercio comercio);

    Comercio editarComercio(Long cuit, Comercio comercioActualizado);

    void borrarComercio(Long cuit);

    Comercio consultarComercio(Long cuit);

    List<Comercio> listarComercios();
}
