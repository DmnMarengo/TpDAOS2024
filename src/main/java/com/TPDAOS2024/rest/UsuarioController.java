package com.TPDAOS2024.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.TPDAOS2024.domain.Patente;
import com.TPDAOS2024.domain.Usuario;
import com.TPDAOS2024.service.PatenteService;
import com.TPDAOS2024.service.UsuarioService;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PatenteService patenteService;

	@GetMapping("/usuarios")
	public String listaUsuarios(Model model) {
		model.addAttribute("usuarios", usuarioService.obtenerUsuarios());
		return "usuarios";
	}

	@PostMapping("/usuarios")
	public String crearUsuario(@ModelAttribute("usuario") Usuario usuario) {
		usuarioService.guardarUsuario(usuario);

		Usuario usuarioGuardado = usuarioService.obtenerUsuarioPorDni(usuario.getDNI());
		
		Patente nuevaPatente = new Patente(usuario.getPatenteVehiculo().getNumeroPatente(), usuarioGuardado);
		patenteService.guardarPatente(nuevaPatente);

		usuarioGuardado.setPatenteVehiculo(nuevaPatente);
		usuarioService.editarUsuario(usuarioGuardado);

		return "redirect:/usuarios";
	}

	@GetMapping("/crear_usuario")
	public String formularioCrearUsuario(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "crear_usuario";
	}

	@GetMapping("/usuarios/editar/{DNI}")
	public String editarUsuario(@PathVariable("DNI") Long DNI, Model model) {
		Usuario usuario = usuarioService.obtenerUsuarioPorDni(DNI);
		model.addAttribute("usuario", usuario);
		return "editar_usuario";
	}

	@PostMapping("/usuarios/{DNI}")
	public String actualizarUsuario(@PathVariable("DNI") Long DNI, @ModelAttribute("usuario") Usuario usuario) {
	    Usuario usuarioExistente = usuarioService.obtenerUsuarioPorDni(DNI);

	    Patente patenteActual = usuarioExistente.getPatenteVehiculo();
	    if (patenteActual != null) {
	        patenteActual.setUsuario(null);
	        patenteService.guardarPatente(patenteActual);
	    }

	    Patente nuevaPatente = new Patente(usuario.getPatenteVehiculo().getNumeroPatente(), usuarioExistente);
	    patenteService.guardarPatente(nuevaPatente);

	    usuarioExistente.setNombre(usuario.getNombre());
	    usuarioExistente.setApellido(usuario.getApellido());
	    usuarioExistente.setDomicilio(usuario.getDomicilio());
	    usuarioExistente.setMail(usuario.getMail());
	    usuarioExistente.setFechaNacimiento(usuario.getFechaNacimiento());
	    usuarioExistente.setPatenteVehiculo(nuevaPatente);
	    usuarioExistente.setContrasenia(usuario.getContrasenia());
	    usuarioExistente.setSaldoCuenta(usuario.getSaldoCuenta());

	    usuarioService.editarUsuario(usuarioExistente);

		return "redirect:/usuarios";
	}
	
	@GetMapping("/usuarios/{DNI}")
	public String borrarUsuario(@PathVariable("DNI") Long DNI) {
		usuarioService.borrarUsuario(DNI);
		return "redirect:/usuarios";
	}
}