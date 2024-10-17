package com.example.app.service;

import com.example.app.Draft;
import com.example.app.User;
import com.example.app.repository.DraftRepository;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DraftService {
    private final DraftRepository draftRepository;
    private final UserRepository userRepository;

    @Autowired
    public DraftService(DraftRepository draftRepository, UserRepository userRepository) {
        this.draftRepository = draftRepository;
        this.userRepository = userRepository;
    }

    public Draft createDraft(Draft draft) {
        if (draft.getUser() == null || draft.getUser().getId() == null) {
            throw new IllegalArgumentException("El borrador debe estar asociado a un usuario");
        }

        User user = userRepository.findById(draft.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        draft.setUser(user);
        return draftRepository.save(draft);
    }

    public List<Draft> getAllDrafts() {
        return draftRepository.findAll();
    }

    public Draft getDraftById(Long id) {
        return draftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrador no encontrado"));
    }

    public Draft updateDraft(Long id, Draft updatedDraft) {
        Draft draft = getDraftById(id);
        draft.setDescription(updatedDraft.getDescription());
        return draftRepository.save(draft);
    }

    public void deleteDraft(Long id) {
        Draft draft = getDraftById(id);
        draftRepository.delete(draft);
    }
}
