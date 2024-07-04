package com.TPDAOS2024.rest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.TPDAOS2024.domain.Comercio;
import com.TPDAOS2024.service.ComercioService;


@RestController
@RequestMapping("/api/comercios")
@Controller
public class ComercioController {

    @Autowired
    private ComercioService comercioService;

   
    @GetMapping("/crear_comercio")
    public String crearComercio() {
        return "crear_comercio";
    }

    @PostMapping("/crear_comercio")
    public ResponseEntity<Comercio> crearComercio(@RequestBody Comercio comercio) {
        comercio.setEstado(true); 
        Comercio nuevoComercio = comercioService.guardarComercio(comercio);
        return new ResponseEntity<>(nuevoComercio, HttpStatus.CREATED);
    }

  
    @PutMapping("/{cuit}")
    public ResponseEntity<Comercio> actualizarComercio(@PathVariable Long cuit, @RequestBody Comercio comercioActualizado) {
        Comercio comercio = comercioService.consultarComercio(cuit);
        if (comercio == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        comercio.setRazonSocial(comercioActualizado.getRazonSocial());
        comercio.setDireccion(comercioActualizado.getDireccion());
        comercioService.editarComercio(cuit, comercio);
        return new ResponseEntity<>(comercio, HttpStatus.OK);
    }


    @GetMapping("/{cuit}")
    public ResponseEntity<Comercio> consultarComercio(@PathVariable Long cuit) {
        Comercio comercio = comercioService.consultarComercio(cuit);
        if (comercio == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comercio, HttpStatus.OK);
    }

    
    @GetMapping
    public ResponseEntity<List<Comercio>> listarComercios() {
        List<Comercio> comercios = comercioService.listarComercios();
        return new ResponseEntity<>(comercios, HttpStatus.OK);
    }

 
    @DeleteMapping("/{cuit}")
    public ResponseEntity<Void> eliminarComercio(@PathVariable Long cuit) {
        Comercio comercio = comercioService.consultarComercio(cuit);
        if (comercio == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        comercioService.borrarComercio(cuit);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
