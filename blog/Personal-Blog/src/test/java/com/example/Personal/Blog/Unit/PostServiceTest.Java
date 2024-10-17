package com.example.app.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.app.Post;
import com.example.app.User;
import com.example.app.repository.PostRepository;
import com.example.app.repository.UserRepository;
import com.example.app.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPostById_ShouldReturnPost_WhenPostExists() {
        Long postId = 1L;
        Post expectedPost = new Post();
        expectedPost.setId(postId);
        when(postRepository.findById(postId)).thenReturn(Optional.of(expectedPost));

        Post actualPost = postService.getPostById(postId);

        assertNotNull(actualPost);
        assertEquals(expectedPost, actualPost);
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void testGetPostById_ShouldThrowException_WhenPostDoesNotExist() {
        Long postId = 2L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            postService.getPostById(postId);
        });

        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void testCreatePost_ShouldReturnSavedPost() {
        Post post = new Post();
        User user = new User();
        user.setId(1L);
        post.setUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post savedPost = postService.createPost(post);

        assertNotNull(savedPost);
        assertEquals(post, savedPost);
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void testGetAllPosts_ShouldReturnAllPosts() {
        List<Post> expectedPosts = new ArrayList<>();
        expectedPosts.add(new Post());
        expectedPosts.add(new Post());

        when(postRepository.findAll()).thenReturn(expectedPosts);

        List<Post> actualPosts = postService.getAllPosts();

        assertEquals(expectedPosts.size(), actualPosts.size());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    void testUpdatePost_ShouldUpdatePost_WhenPostExists() {
        Long postId = 1L;
        Post post = new Post();
        post.setId(postId);
        post.setLabel("Old Label");

        Post updatedPost = new Post();
        updatedPost.setLabel("New Label");

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        Post resultPost = postService.updatePost(postId, updatedPost);

        assertNotNull(resultPost);
        assertEquals("New Label", resultPost.getLabel());
        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void testUpdatePost_ShouldThrowException_WhenPostDoesNotExist() {
        Long postId = 1L;
        Post updatedPost = new Post();
        updatedPost.setId(postId);

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            postService.updatePost(postId, updatedPost);
        });

        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void testDeletePost_ShouldDeletePost_WhenPostExists() {
        Long postId = 1L;
        Post post = new Post();
        post.setId(postId);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        postService.deletePost(postId);

        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, times(1)).delete(post);
    }

    @Test
    void testDeletePost_ShouldThrowException_WhenPostDoesNotExist() {
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            postService.deletePost(postId);
        });

        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, never()).delete(any(Post.class));
    }
}
