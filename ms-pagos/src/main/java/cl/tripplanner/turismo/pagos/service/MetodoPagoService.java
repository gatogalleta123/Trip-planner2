package cl.tripplanner.turismo.pagos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.tripplanner.turismo.pagos.modelo.MetodoPago;
import cl.tripplanner.turismo.pagos.repository.MetodoPagoRepository;

@Service
public class MetodoPagoService {

    @Autowired
    private MetodoPagoRepository repository;

    public List<MetodoPago> listar(){
        return repository.findAll();
    }

    public MetodoPago guardar(MetodoPago metodo){
        return repository.save(metodo);
    }

    public MetodoPago buscar(Integer id){
        return repository.findById(id).orElse(null);
    }

    public void eliminar(Integer id){
        repository.deleteById(id);
    }
}