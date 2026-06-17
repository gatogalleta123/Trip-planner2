package cl.tripplanner.turismo.pagos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cl.tripplanner.turismo.pagos.modelo.MetodoPago;
import cl.tripplanner.turismo.pagos.service.MetodoPagoService;

@RestController
@RequestMapping("/metodos")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService service;

    @GetMapping
    public List<MetodoPago> listar(){
        return service.listar();
    }

    @PostMapping
    public MetodoPago guardar(@RequestBody MetodoPago metodo){
        return service.guardar(metodo);
    }

    @GetMapping("/{id}")
    public MetodoPago buscar(@PathVariable Integer id){
        return service.buscar(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id){
        service.eliminar(id);
    }
}