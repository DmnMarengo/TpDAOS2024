package com.TPDAOS2024.rest;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.TPDAOS2024.domain.Patente;
import com.TPDAOS2024.domain.Usuario;
import com.TPDAOS2024.service.PatenteService;
import com.TPDAOS2024.service.UsuarioService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PatenteService patenteService;

    @GetMapping
    public List<Usuario> listaUsuarios(@RequestParam(value = "dni", required = false) Long dni) {
        if (dni != null) {
            Usuario usuario = usuarioService.obtenerUsuarioPorDni(dni);
            if (usuario != null) {
                return Collections.singletonList(usuario);
            } else {
                return Collections.emptyList();
            }
        } else {
            return usuarioService.obtenerUsuarios();
        }
    }

    @GetMapping("/{DNI}")
    public ResponseEntity<Usuario> obtenerUsuarioPorDni(@PathVariable Long DNI) {
        Usuario usuario = usuarioService.obtenerUsuarioPorDni(DNI);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        usuarioService.guardarUsuario(usuario);

        Usuario usuarioGuardado = usuarioService.obtenerUsuarioPorDni(usuario.getDNI());

        Patente nuevaPatente = new Patente(usuario.getPatenteVehiculo().getNumeroPatente(), usuarioGuardado);

        usuarioGuardado.setPatenteVehiculo(nuevaPatente);
        usuarioService.editarPatenteUsuario(usuarioGuardado);

        return usuarioGuardado;
    }

    @GetMapping("/nuevo")
    public Usuario formularioCrearUsuario() {
        return new Usuario();
    }

    @GetMapping("/editar/{DNI}")
    public Usuario editarUsuario(@PathVariable("DNI") Long DNI) {
        return usuarioService.obtenerUsuarioPorDni(DNI);
    }

    @PutMapping("/{DNI}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable("DNI") Long DNI, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioExistente = usuarioService.obtenerUsuarioPorDni(DNI);

            if (usuarioExistente == null) {
                return ResponseEntity.notFound().build();
            }

            Patente patenteActual = usuarioExistente.getPatenteVehiculo();

                if (!patenteActual.getNumeroPatente().equalsIgnoreCase(usuario.getPatenteVehiculo().getNumeroPatente())) {
                    Patente p = new Patente();
                    p.setNumeroPatente(usuario.getPatenteVehiculo().getNumeroPatente());
                    p.setUsuario(usuario);

                    patenteService.borrarPatente(patenteActual.getNumeroPatente());
                    patenteService.guardarPatente(p);

                    /*Patente p = patenteService.obtenerPorId(usuarioExistente.getPatenteVehiculo().getNumeroPatente());
                    p.setNumeroPatente(usuario.getPatenteVehiculo().getNumeroPatente());
                    usuarioExistente.setPatenteVehiculo(p);
                    patenteService.borrarPatente(usuarioExistente.getPatenteVehiculo().getNumeroPatente());
                    patenteService.guardarPatente(p);*/
                }

            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setApellido(usuario.getApellido());
            usuarioExistente.setDomicilio(usuario.getDomicilio());
            usuarioExistente.setMail(usuario.getMail());
            usuarioExistente.setFechaNacimiento(usuario.getFechaNacimiento());
            usuarioExistente.setContrasenia(usuario.getContrasenia());
            usuarioExistente.setSaldoCuenta(usuario.getSaldoCuenta());

            usuarioService.editarUsuario(usuarioExistente);

            return ResponseEntity.ok(usuarioExistente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{DNI}")
    public void borrarUsuario(@PathVariable("DNI") Long DNI) {
        usuarioService.borrarUsuario(DNI);
    }
}