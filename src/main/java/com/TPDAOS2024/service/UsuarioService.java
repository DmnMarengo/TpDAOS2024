package com.TPDAOS2024.service;

import java.util.List;

import com.TPDAOS2024.domain.Usuario;

public interface UsuarioService {

    Usuario guardarUsuario(Usuario usuario);

    Usuario editarUsuario(Usuario usuario);

    void borrarUsuario(Long dni);

    List<Usuario> obtenerUsuarios();

    Usuario obtenerUsuarioPorPatente(String patente);

    Usuario obtenerUsuarioPorDni(Long dni);
    
    Usuario editarPatenteUsuario(Usuario usuario);
}