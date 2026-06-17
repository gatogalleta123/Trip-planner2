package cl.tripplanner.turismo.hoteles.controller;

import cl.tripplanner.turismo.hoteles.dto.*;
import cl.tripplanner.turismo.hoteles.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hoteles")
@RequiredArgsConstructor

public class HotelController {

    private final HotelService service;

    @GetMapping
    public List<HotelResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public HotelResponse obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @PostMapping
    public HotelResponse crear(@Valid @RequestBody HotelRequest dto) {
        return service.crear(dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}