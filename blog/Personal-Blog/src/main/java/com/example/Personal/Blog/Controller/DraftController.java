package com.example.app.controller;

import com.example.app.Draft;
import com.example.app.service.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drafts")
public class DraftController {
    private final DraftService draftService;

    @Autowired
    public DraftController(DraftService draftService) {
        this.draftService = draftService;
    }

    @PostMapping
    public ResponseEntity<Draft> createDraft(@RequestBody Draft draft) {
        Draft newDraft = draftService.createDraft(draft);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDraft);
    }

    @GetMapping
    public List<Draft> getAllDrafts() {
        return draftService.getAllDrafts();
    }

    @GetMapping("/{id}")
    public Draft getDraftById(@PathVariable Long id) {
        return draftService.getDraftById(id);
    }

    @PutMapping("/{id}")
    public Draft updateDraft(@PathVariable Long id, @RequestBody Draft updatedDraft) {
        return draftService.updateDraft(id, updatedDraft);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDraft(@PathVariable Long id) {
        draftService.deleteDraft(id);
        return ResponseEntity.noContent().build();
    }
}
