package com.example.app.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.app.Coment;
import com.example.app.Post;
import com.example.app.User;
import com.example.app.repository.ComentRepository;
import com.example.app.repository.PostRepository;
import com.example.app.repository.UserRepository;
import com.example.app.service.ComentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class ComentServiceTest {
    @Mock
    private ComentRepository comentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private ComentService comentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetComentById_ShouldReturnComent_WhenComentExists() {
        Long comentId = 1L;
        Coment expectedComent = new Coment();
        expectedComent.setId(comentId);
        when(comentRepository.findById(comentId)).thenReturn(Optional.of(expectedComent));

        Coment actualComent = comentService.getComentById(comentId);

        assertNotNull(actualComent);
        assertEquals(expectedComent, actualComent);
        verify(comentRepository, times(1)).findById(comentId);
    }

    @Test
    void testGetComentById_ShouldThrowException_WhenComentDoesNotExist() {
        Long comentId = 2L;
        when(comentRepository.findById(comentId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            comentService.getComentById(comentId);
        });

        verify(comentRepository, times(1)).findById(comentId);
    }

    @Test
    void testCreateComent_ShouldReturnSavedComent() {
        Coment coment = new Coment();
        User user = new User();
        Post post = new Post();
        user.setId(1L);
        post.setId(1L);
        coment.setUser(user);
        coment.setPost(post);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(comentRepository.save(any(Coment.class))).thenReturn(coment);

        Coment savedComent = comentService.createComent(coment);

        assertNotNull(savedComent);
        assertEquals(coment, savedComent);
        verify(comentRepository, times(1)).save(coment);
    }
}
