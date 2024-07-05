package com.TPDAOS2024.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.TPDAOS2024.domain.Comercio;
import com.TPDAOS2024.service.ComercioService;




@RestController
@RequestMapping("/comercios")
@Tag(name = "Comercio", description = "Comercios")
public class ComercioController {

    @Autowired
    private ComercioService comercioService;

   
    
    /**
	 * Inserta un nuevo comercio en la base de datos
	 * 			curl --location --request POST 'http://localhost:8080/comercios'
	 *			--header 'Accept: application/json' 
	 * 			--header 'Content-Type: application/json' 
	 *			--data-raw '{
	 *			    "cuit": 28278371712,
	 *			    "razonSocial": "Kiosko ElEjemplo",
	 *			    "direccion": "San Juan 2002",
	 *			    "estado": true
	 *			}'
	 * @param c Comercio  a guardarComercio
	 * @return Comercio insertado o error en otro caso
	 * @throws Exception 
	 */
    @PostMapping
    public ResponseEntity<Object> guardar(@Valid @RequestBody ComercioForm form, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
        }

        Comercio c = form.toPojo();
        comercioService.guardarComercio(c);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{cuit}")
                            .buildAndExpand(c.getCuit()).toUri();

        return ResponseEntity.created(location).body(c);
    }
	

}