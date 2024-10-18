package Blog.app;

import Blog.app.Blog.Comments;
import Blog.app.Blog.Posts;
import Blog.app.Repository.CommentsRepository;
import Blog.app.Repository.PostsRepository;
import Blog.app.Repository.UsersRepository;
import Blog.app.Service.CommentsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import Blog.app.Blog.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CommentsServiceTest {

    @Mock
    private CommentsRepository commentsRepository;
    @Mock
    private PostsRepository postsRepository;
    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private CommentsService commentsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createComments_validComment_savesComment() {

        Users user = new Users();
        user.setId(1L);
        user.setName("Test User");

        Posts post = new Posts();
        post.setId(1L);
        post.setLabel("Test Post");

        Comments comment = new Comments();
        comment.setDescription("Nuevo comentario");
        comment.setUser(user);
        comment.setPost(post);

        when(commentsRepository.save(any(Comments.class))).thenReturn(comment);


        Comments savedComment = commentsService.createComments(comment);

        assertNotNull(savedComment);
        verify(commentsRepository, times(1)).save(comment);
    }


    @Test
    void getAllCommentsbyPost_existingPost_returnsComments() throws Exception {
        Long postId = 1L;
        Posts post = new Posts();
        List<Comments> comments = new ArrayList<>();
        comments.add(new Comments());
        post.setComentarios(comments);

        when(postsRepository.findById(postId)).thenReturn(Optional.of(post));

        List<Comments> retrievedComments = commentsService.getAllCommentsbyPost(postId);

        assertEquals(1, retrievedComments.size());
        verify(postsRepository, times(1)).findById(postId);
    }

    @Test
    void updateComments_existingComment_updatesDescription() {
        Long commentId = 1L;


        Comments existingComment = new Comments();
        existingComment.setDescription("Descripción original");
        existingComment.setId(commentId);


        when(commentsRepository.findById(commentId)).thenReturn(Optional.of(existingComment));

        existingComment.setDescription("Nueva descripción");

        when(commentsRepository.save(existingComment)).thenReturn(existingComment);

        Comments result = commentsService.updateComments(commentId, existingComment);

        assertEquals("Nueva descripción", result.getDescription());

        verify(commentsRepository, times(1)).findById(commentId);
        verify(commentsRepository, times(1)).save(existingComment);
    }


    @Test
    void deleteComments_existingComment_deletesComment() {
        Long commentId = 1L;

        Comments existingComment = new Comments();
        existingComment.setId(commentId);
        when(commentsRepository.findById(commentId)).thenReturn(Optional.of(existingComment));

        doNothing().when(commentsRepository).deleteById(commentId);

        commentsService.deleteComments(commentId);

        verify(commentsRepository, times(1)).findById(commentId);

        verify(commentsRepository, times(1)).deleteById(commentId);
    }

    @Test
    void createComments_postOrUserDoesNotExist_throwsException() {

        Comments comment = new Comments();

        when(usersRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(postsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> commentsService.createComments(comment));

        verify(commentsRepository, never()).save(any(Comments.class));
    }

    @Test
    void getAllCommentsbyPost_nonExistingPost_throwsException() throws Exception {
        Long postId = 1L;

        when(postsRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> commentsService.getAllCommentsbyPost(postId));

        verify(commentsRepository, never()).findAll();
    }

    @Test
    void updateComments_nonExistingComment_throwsException() {
        Long commentId = 1L;


        when(commentsRepository.findById(commentId)).thenReturn(Optional.empty());

        Comments updatedComment = new Comments();
        updatedComment.setDescription("Nueva descripción");
        assertThrows(RuntimeException.class, () -> commentsService.updateComments(commentId, updatedComment));

        verify(commentsRepository, never()).save(any(Comments.class));
    }

    @Test
    void deleteComments_nonExistingComment_throwsException() {
        Long commentId = 1L;

        when(commentsRepository.findById(commentId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> commentsService.deleteComments(commentId));

        verify(commentsRepository, never()).deleteById(commentId);
    }
}
