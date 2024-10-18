package Blog.app;

import Blog.app.Blog.Posts;
import Blog.app.Blog.Users;
import Blog.app.Blog.Comments;
import Blog.app.Repository.PostsRepository;
import Blog.app.Repository.UsersRepository;
import Blog.app.Repository.CommentsRepository;
import Blog.app.Service.PostsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.springframework.data.domain.Sort;
@SpringBootTest
@Transactional
public class PostsServiceIntegrationTest {

    @Autowired
    private PostsService postsService;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    private Users testUser;


    @BeforeEach
    void setup() {
        postsRepository.deleteAll();  // Limpia la tabla antes de cada prueba
        testUser = new Users();
        testUser.setName("Test User");
        usersRepository.save(testUser);
    }

    @Test
    void testCreatePosts() {
        // Crear un post de prueba
        Posts post = new Posts();
        post.setLabel("Post de prueba");
        post.setDescription("Descripción del post de prueba");
        post.setUser(testUser);

        // Guardar el post
        Posts savedPost = postsService.createPosts(post);

        // Verificar que el post fue guardado correctamente
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getId()).isNotNull();
        assertThat(savedPost.getLabel()).isEqualTo("Post de prueba");
        assertThat(savedPost.getUser()).isEqualTo(testUser);
    }

    @Test
    void testGetAllPosts() {
        // Crear y guardar un post de prueba
        Posts post = new Posts();
        post.setId(1L);
        post.setLabel("Post 1");
        post.setDescription("Descripción del post 1");
        post.setUser(testUser);
        postsRepository.save(post);

        // Obtener todos los posts en orden ascendente por ID
        List<Posts> postsList = postsRepository.findAll();

        // Verificar que se ha recuperado al menos un post
        assertThat(postsList).isNotEmpty();

    }

    @Test
    void testUpdatePosts() {
        // Crear y guardar un post de prueba
        Posts post = new Posts();
        post.setLabel("Post a actualizar");
        post.setDescription("Descripción inicial");
        post.setUser(testUser);
        postsRepository.save(post);

        // Actualizar el post
        Posts updatedPost = new Posts();
        updatedPost.setLabel("Post actualizado");
        updatedPost.setDescription("Descripción actualizada");

        Posts result = postsService.updatePosts(post.getId(), updatedPost);

        // Verificar que los datos fueron actualizados correctamente
        assertThat(result.getLabel()).isEqualTo("Post actualizado");
        assertThat(result.getDescription()).isEqualTo("Descripción actualizada");
    }

    @Test
    void testDeletePosts() {
        // Crear y guardar un post de prueba
        Posts post = new Posts();
        post.setLabel("Post para eliminar");
        post.setDescription("Descripción del post");
        post.setUser(testUser);
        postsRepository.save(post);

        // Verificar que el post existe antes de eliminarlo
        Optional<Posts> foundPost = postsRepository.findById(post.getId());
        assertThat(foundPost).isPresent();

        // Eliminar el post
        postsService.deletePosts(post.getId());

        // Verificar que el post fue eliminado
        Optional<Posts> deletedPost = postsRepository.findById(post.getId());
        assertThat(deletedPost).isNotPresent();
    }

    @Test
    void testAddCommentToPost() {
        // Crear y guardar un post de prueba
        Posts post = new Posts();
        post.setLabel("Post con comentario");
        post.setDescription("Descripción del post con comentario");
        post.setUser(testUser);
        postsRepository.save(post);

        // Crear un comentario para el post
        Comments comment = new Comments();
        comment.setDescription("Este es un comentario");

        Comments savedComment = postsService.addCommentToPost(post.getId(), comment);

        // Verificar que el comentario fue agregado al post correctamente
        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getPost()).isEqualTo(post);
        assertThat(savedComment.getDescription()).isEqualTo("Este es un comentario");
    }

}
