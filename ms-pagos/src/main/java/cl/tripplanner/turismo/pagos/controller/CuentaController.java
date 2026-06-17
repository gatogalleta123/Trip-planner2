package cl.tripplanner.turismo.pagos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cl.tripplanner.turismo.pagos.modelo.Cuenta;
import cl.tripplanner.turismo.pagos.service.CuentaService;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService service;

    @GetMapping
    public List<Cuenta> listar(){
        return service.listar();
    }

    @PostMapping
    public Cuenta guardar(@RequestBody Cuenta cuenta){
        return service.guardar(cuenta);
    }

    @GetMapping("/{id}")
    public Cuenta buscar(@PathVariable Integer id){
        return service.buscar(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id){
        service.eliminar(id);
    }
}