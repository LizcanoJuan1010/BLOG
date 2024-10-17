package com.example.app.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.app.Draft;
import com.example.app.User;
import com.example.app.repository.DraftRepository;
import com.example.app.repository.UserRepository;
import com.example.app.service.DraftService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class DraftServiceTest {
    @Mock
    private DraftRepository draftRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DraftService draftService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDraftById_ShouldReturnDraft_WhenDraftExists() {
        Long draftId = 1L;
        Draft expectedDraft = new Draft();
        expectedDraft.setId(draftId);
        when(draftRepository.findById(draftId)).thenReturn(Optional.of(expectedDraft));

        Draft actualDraft = draftService.getDraftById(draftId);

        assertNotNull(actualDraft);
        assertEquals(expectedDraft, actualDraft);
        verify(draftRepository, times(1)).findById(draftId);
    }

    @Test
    void testGetDraftById_ShouldThrowException_WhenDraftDoesNotExist() {
        Long draftId = 2L;
        when(draftRepository.findById(draftId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            draftService.getDraftById(draftId);
        });

        verify(draftRepository, times(1)).findById(draftId);
    }

    @Test
    void testCreateDraft_ShouldReturnSavedDraft() {
        Draft draft = new Draft();
        User user = new User();
        user.setId(1L);
        draft.setUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(draftRepository.save(any(Draft.class))).thenReturn(draft);

        Draft savedDraft = draftService.createDraft(draft);

        assertNotNull(savedDraft);
        assertEquals(draft, savedDraft);
        verify(draftRepository, times(1)).save(draft);
    }
}
