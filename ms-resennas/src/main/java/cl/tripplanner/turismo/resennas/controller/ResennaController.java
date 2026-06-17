package cl.tripplanner.turismo.resennas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cl.tripplanner.turismo.resennas.modelo.Resenna;
import cl.tripplanner.turismo.resennas.service.ResennaService;

@RestController
@RequestMapping("/resennas")
public class ResennaController {

    @Autowired
    private ResennaService service;

    @GetMapping
    public List<Resenna> listar(){
        return service.listar();
    }

    @PostMapping
    public Resenna guardar(@RequestBody Resenna resenna){
        return service.guardar(resenna);
    }

    @GetMapping("/{id}")
    public Resenna buscar(@PathVariable Integer id){
        return service.buscar(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id){
        service.eliminar(id);
    }
}