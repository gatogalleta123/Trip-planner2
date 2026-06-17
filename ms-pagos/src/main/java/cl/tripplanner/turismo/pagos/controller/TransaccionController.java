package cl.tripplanner.turismo.pagos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cl.tripplanner.turismo.pagos.modelo.Transaccion;
import cl.tripplanner.turismo.pagos.service.TransaccionService;

@RestController
@RequestMapping("/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionService service;

    @GetMapping
    public List<Transaccion> listar(){
        return service.listar();
    }

    @PostMapping
    public Transaccion guardar(@RequestBody Transaccion transaccion){
        return service.guardar(transaccion);
    }

    @GetMapping("/{id}")
    public Transaccion buscar(@PathVariable Integer id){
        return service.buscar(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id){
        service.eliminar(id);
    }
}