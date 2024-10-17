package com.example.app.service;

import com.example.app.Coment;
import com.example.app.Post;
import com.example.app.User;
import com.example.app.repository.ComentRepository;
import com.example.app.repository.PostRepository;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentService {
    private final ComentRepository comentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public ComentService(ComentRepository comentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.comentRepository = comentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public Coment createComent(Coment coment) {
        if (coment.getUser() == null || coment.getUser().getId() == null) {
            throw new IllegalArgumentException("El comentario debe estar asociado a un usuario");
        }

        if (coment.getPost() == null || coment.getPost().getId() == null) {
            throw new IllegalArgumentException("El comentario debe estar asociado a un post");
        }

        User user = userRepository.findById(coment.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Post post = postRepository.findById(coment.getPost().getId())
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));

        coment.setUser(user);
        coment.setPost(post);

        return comentRepository.save(coment);
    }

    public List<Coment> getAllComents() {
        return comentRepository.findAll();
    }

    public Coment getComentById(Long id) {
        return comentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));
    }

    public Coment updateComent(Long id, Coment updatedComent) {
        Coment coment = getComentById(id);
        coment.setDescription(updatedComent.getDescription());
        return comentRepository.save(coment);
    }

    public void deleteComent(Long id) {
        Coment coment = getComentById(id);
        comentRepository.delete(coment);
    }
}
