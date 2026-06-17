package cl.tripplanner.turismo.pagos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.tripplanner.turismo.pagos.modelo.Transaccion;
import cl.tripplanner.turismo.pagos.repository.TransaccionRepository;

@Service
public class TransaccionService {

    @Autowired
    private TransaccionRepository repository;

    public List<Transaccion> listar(){
        return repository.findAll();
    }

    public Transaccion guardar(Transaccion transaccion){
        return repository.save(transaccion);
    }

    public Transaccion buscar(Integer id){
        return repository.findById(id).orElse(null);
    }

    public void eliminar(Integer id){
        repository.deleteById(id);
    }
}