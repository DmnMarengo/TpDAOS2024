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

/*Para utilizar el CRUD de Usuario se necesita POSTMAN.
GET http://localhost:8080/usuarios para obtener todos los usuarios
GET http://localhost:8080/usuarios/{DNI} para obtener usuario por DNI
POST http://localhost:8080/usuarios
        {
        "nombre": "Ejemplo",
        "apellido": "Ejemplo",
        "domicilio": "Ejemplo",
        "mail": "Ejemplo@gmail.com",
        "fechaNacimiento": "2024-06-02T03:00:00.000+00:00",
        "patenteVehiculo": {
        "numeroPatente": "999"
        },
        "contrasenia": "Ejemplo",
        "saldoCuenta": null,
        "dni": 999
        }

ejemplo para poder cargar un usuario por el post.

DELETE http://localhost:8080/usuarios/{DNI} en el parámetro dni se debe poner el dni del usuario cargado para poder borrarlo.

PUT http://localhost:8080/usuarios/{DNI} en el par[ametro dni se debe poner un DNI ya registrado para editar, podemos utilizar el ejemplo de antes
http://localhost:8080/usuarios/999
        {
        "nombre": "Alfredo",
        "apellido": "Pérez",
        "domicilio": "Av. Corrientes 1559",
        "mail": "Alfredoperez@gmail.com",
        "fechaNacimiento": "2024-06-02T03:00:00.000+00:00",
        "patenteVehiculo": {
        "numeroPatente": "999"
        },
        "contrasenia": "alfreditoperez81528",
        "saldoCuenta": null,
        "dni": 999
        }

        (el único error que hasta ahora no encontré solución es que al editar cualquier dato menos dni se edita correctamente, pero al editar la patente no me deja y me tira error)*/
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