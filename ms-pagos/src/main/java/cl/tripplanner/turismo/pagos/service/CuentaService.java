package cl.tripplanner.turismo.pagos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.tripplanner.turismo.pagos.modelo.Cuenta;
import cl.tripplanner.turismo.pagos.repository.CuentaRepository;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository repository;

    public List<Cuenta> listar(){
        return repository.findAll();
    }

    public Cuenta guardar(Cuenta cuenta){
        return repository.save(cuenta);
    }

    public Cuenta buscar(Integer id){
        return repository.findById(id).orElse(null);
    }

    public void eliminar(Integer id){
        repository.deleteById(id);
    }
}