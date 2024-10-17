package com.example.app.controller;

import com.example.app.Coment;
import com.example.app.service.ComentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coments")
public class ComentController {
    private final ComentService comentService;

    @Autowired
    public ComentController(ComentService comentService) {
        this.comentService = comentService;
    }

    @PostMapping
    public ResponseEntity<Coment> createComent(@RequestBody Coment coment) {
        Coment newComent = comentService.createComent(coment);
        return ResponseEntity.status(HttpStatus.CREATED).body(newComent);
    }

    @GetMapping
    public List<Coment> getAllComents() {
        return comentService.getAllComents();
    }

    @GetMapping("/{id}")
    public Coment getComentById(@PathVariable Long id) {
        return comentService.getComentById(id);
    }

    @PutMapping("/{id}")
    public Coment updateComent(@PathVariable Long id, @RequestBody Coment updatedComent) {
        return comentService.updateComent(id, updatedComent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComent(@PathVariable Long id) {
        comentService.deleteComent(id);
        return ResponseEntity.noContent().build();
    }
}
