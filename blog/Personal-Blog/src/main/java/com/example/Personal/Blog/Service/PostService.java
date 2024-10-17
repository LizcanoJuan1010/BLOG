package com.example.app.service;

import com.example.app.Post;
import com.example.app.User;
import com.example.app.repository.PostRepository;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(Post post) {
        if (post.getUser() == null || post.getUser().getId() == null) {
            throw new IllegalArgumentException("El post debe estar asociado a un usuario");
        }

        User user = userRepository.findById(post.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        post.setUser(user);
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));
    }

    public Post updatePost(Long id, Post updatedPost) {
        Post post = getPostById(id);
        post.setLabel(updatedPost.getLabel());
        post.setDescription(updatedPost.getDescription());
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        Post post = getPostById(id);
        postRepository.delete(post);
    }
}
