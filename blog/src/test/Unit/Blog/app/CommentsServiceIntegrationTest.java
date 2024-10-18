package Blog.app;

import Blog.app.Blog.Comments;
import Blog.app.Blog.Posts;
import Blog.app.Blog.Users;
import Blog.app.Repository.CommentsRepository;
import Blog.app.Repository.PostsRepository;
import Blog.app.Repository.UsersRepository;
import Blog.app.Service.CommentsService;
import Blog.app.Service.PostsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class CommentsServiceIntegrationTest {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CommentsService commentsService;

    private Users testUser;
    private Posts testPost;
    @Autowired
    private PostsService postsService;

    @BeforeEach
    void setUp() {
        // Crear y guardar un usuario de prueba
        testUser = new Users();
        testUser.setName("Test User");
        testUser.setPassword("password");
        testUser.setFriends(0L); // Set to 0 for friends as requested
        usersRepository.save(testUser);

        // Crear y guardar un post de prueba
        testPost = new Posts();
        testPost.setLabel("Test Post");
        testPost.setDescription("Descripción del post");
        testPost.setUser(testUser);
        postsRepository.save(testPost);
    }

    @Test
    void testCreateComments() {
        // Crear y guardar un comentario de prueba
        Comments comment = new Comments();
        comment.setDescription("Comentario de prueba");
        comment.setUser(testUser);
        comment.setPost(testPost);

        Comments savedComment = commentsService.createComments(comment);

        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getDescription()).isEqualTo("Comentario de prueba");
        assertThat(savedComment.getPost().getId()).isEqualTo(testPost.getId());
    }

    @Test
    void testCreateComments_UserOrPostNull() {
        // Intentar crear un comentario con usuario nulo
        Comments comment = new Comments();
        comment.setDescription("Comentario sin usuario ni post");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            commentsService.createComments(comment);
        });

        assertThat(exception.getMessage()).isEqualTo("El usuario no existe");
    }


    @Test
    void testUpdateComments() {
        // Crear y guardar un comentario de prueba
        Comments comment = new Comments();
        comment.setDescription("Comentario original");
        comment.setUser(testUser);
        comment.setPost(testPost);
        commentsRepository.save(comment);

        // Actualizar el comentario
        comment.setDescription("Comentario actualizado");
        Comments updatedComment = commentsService.updateComments(comment.getId(), comment);

        assertThat(updatedComment.getDescription()).isEqualTo("Comentario actualizado");
    }

    @Test
    void testDeleteComments() {
        // Crear y guardar un comentario de prueba
        Comments comment = new Comments();
        comment.setDescription("Comentario a eliminar");
        comment.setUser(testUser);
        comment.setPost(testPost);
        commentsRepository.save(comment);

        // Eliminar el comentario
        commentsService.deleteComments(comment.getId());

        // Verificar que el comentario fue eliminado
        Optional<Comments> deletedComment = commentsRepository.findById(comment.getId());
        assertThat(deletedComment).isEmpty();
    }
}
