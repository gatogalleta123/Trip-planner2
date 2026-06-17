package cl.tripplanner.turismo.resennas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.tripplanner.turismo.resennas.modelo.Resenna;
import cl.tripplanner.turismo.resennas.repository.ResennaRepository;

@Service
public class ResennaService {

    @Autowired
    private ResennaRepository repository;

    public List<Resenna> listar(){
        return repository.findAll();
    }

    public Resenna guardar(Resenna resenna){
        return repository.save(resenna);
    }

    public Resenna buscar(Integer id){
        return repository.findById(id).orElse(null);
    }

    public void eliminar(Integer id){
        repository.deleteById(id);
    }
}