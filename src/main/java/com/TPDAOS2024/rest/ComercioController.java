package com.TPDAOS2024.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import TPDAOS2024.dto.ComercioResponseDTO;


//@AUTHOR Damian Mateo Marengo

@RestController
@RequestMapping("/comercios")
@Tag(name = "Comercio", description = "Comercios")
public class ComercioController {

    @Autowired
    private ComercioService comercioService;

    //ingresar en la terminal el curl de la siguiente forma, de lo contrario genera error y no pude encontrar otra solucion
    //curl --location --request POST "http://localhost:8080/comercios" --header "Accept: application/json" --header "Content-Type: application/json" --data-raw "{\"cuit\":\"20286043212\", \"razonSocial\":\"Impresiones SunCop\", \"direccion\":\"Av. Colon 205\", \"estado\":true}"
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Inserta un nuevo comercio en la base de datos")
    public ResponseEntity<Object> guardar(@Valid @RequestBody ComercioForm form, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
        }

        Comercio c = form.toPojo();
        comercioService.guardarComercio(c);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{cuit}")
                .buildAndExpand(c.getCuit()).toUri();

        return ResponseEntity.created(location).body(new ComercioResponseDTO(c));
    }

    //para actualizar un comercio me encontre con el mismo error que tenia con el push para cargar uno asique utilice la misma solucion
    //curl --location --request PUT "http://localhost:8080/comercios/20286043212" --header "Accept: application/json" --header "Content-Type: application/json" --data-raw "{\"cuit\":\"20286043212\", \"razonSocial\":\"Nueva Razón Social\", \"direccion\":\"Nueva Dirección\", \"estado\":true}"
    //al ingresar un comando de esta manera si funciona.
    @PutMapping(value = "/{cuit}"
    		+ "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Actualiza un comercio existente")
    public ResponseEntity<Object> editar(@PathVariable Long cuit, @Valid @RequestBody ComercioForm form,
            BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
        }

        Comercio comercioActualizado = form.toPojo();
        Comercio comercio = comercioService.editarComercio(cuit, comercioActualizado);

        return ResponseEntity.ok(new ComercioResponseDTO(comercio));
    }
    
    
//Para Eliminar un comercio (cambiar su estado a false) realizar el siguiente curl
//curl --location --request DELETE "http://localhost:8080/comercios/{29354125321052}" --header "Accept: application/json"
    
    @DeleteMapping("/{cuit}")
    @Operation(summary = "Marca como suspendido un comercio existente")
    public ResponseEntity<Void> borrar(@PathVariable Long cuit) {
        comercioService.borrarComercio(cuit);
        return ResponseEntity.noContent().build();
    }
//Buscar comercio funciona de la siguiente forma
//curl --location --request GET http://localhost:8080/comercios/28278371712 
    @GetMapping("/{cuit}")
    @Operation(summary = "Consulta un comercio por CUIT")
    public ResponseEntity<Object> consultar(@PathVariable Long cuit) {
        Comercio comercio = comercioService.consultarComercio(cuit);
        return ResponseEntity.ok(new ComercioResponseDTO(comercio));
    }

    //Para traer todos los comercios utilizar el siguiente curl
    //curl --location --request GET "http://localhost:8080/comercios" --header "Accept: application/json"
    @GetMapping
    @Operation(summary = "Lista todos los comercios")
    public ResponseEntity<List<ComercioResponseDTO>> listar() {
        List<Comercio> comercios = comercioService.listarComercios();
        List<ComercioResponseDTO> responseDTOs = comercios.stream().map(ComercioResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
}