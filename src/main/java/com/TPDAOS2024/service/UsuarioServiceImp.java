package com.TPDAOS2024.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TPDAOS2024.dao.PatenteRepository;
import com.TPDAOS2024.dao.UsuarioRepository;
import com.TPDAOS2024.domain.Patente;
import com.TPDAOS2024.domain.Usuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class UsuarioServiceImp implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PatenteRepository patenteRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Usuario guardarUsuario(Usuario usuario) {
		Patente patenteExistente = patenteRepository.findById(usuario.getPatenteVehiculo().getNumeroPatente()).orElse(null);
		if (patenteExistente != null) {
			throw new IllegalArgumentException("La patente ya existe: " + usuario.getPatenteVehiculo().getNumeroPatente());
		} else {
			Usuario usuarioExistente = usuarioRepository.findById(usuario.getDNI()).orElse(null);
			if (usuarioExistente != null) {
				throw new IllegalArgumentException("El usuario ya existe: " + usuario.getDNI());
			}
			return usuarioRepository.save(usuario);
		}
	}

	@Override
	public Usuario editarUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Override
	public void borrarUsuario(Long dni) {
		usuarioRepository.deleteById(dni);
	}

	@Override
	public List<Usuario> obtenerUsuarios() {
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario obtenerUsuarioPorPatente(String patente) {
		String jpql = "SELECT u FROM Usuario u WHERE u.patente = :patente";
		Query query = entityManager.createQuery(jpql, Usuario.class);
		query.setParameter("patente", patente);

		try {
			return (Usuario) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Usuario obtenerUsuarioPorDni(Long dni) {
		return usuarioRepository.getById(dni);
	}

	@Override
	public Usuario editarPatenteUsuario(Usuario usuario) {
		Patente p = patenteRepository.getById(usuario.getPatenteVehiculo().getNumeroPatente());

		if (p == null) {
			throw new IllegalArgumentException(
					"La patente ya existe: " + usuario.getPatenteVehiculo().getNumeroPatente());
		}
		return usuarioRepository.save(usuario);
	}

}