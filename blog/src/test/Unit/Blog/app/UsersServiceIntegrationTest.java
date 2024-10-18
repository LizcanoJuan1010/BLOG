package Blog.app;

import Blog.app.Blog.Posts;
import Blog.app.Blog.Users;
import Blog.app.Repository.DraftsRepository;
import Blog.app.Repository.PostsRepository;
import Blog.app.Repository.UsersRepository;
import Blog.app.Service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional // Esto asegura que los datos no persistan entre pruebas
class UsersServiceIntegrationTest {

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private DraftsRepository draftsRepository;

    private Users testUser;

    @BeforeEach
    void setUp() {
        testUser = new Users();
        testUser.setName("Test User");
        testUser.setPassword("password");
        testUser.setFriends(0L); // Asignar valor numérico
        usersRepository.save(testUser);
    }

    @Test
    void testCreateUser() {
        Users newUser = new Users();
        newUser.setName("New User");
        newUser.setPassword("newpassword");
        newUser.setFriends(0L); // Asignar valor numérico

        Users savedUser = usersService.createUser(newUser);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("New User");
        assertThat(savedUser.getFriends()).isEqualTo(0);
    }

    @Test
    void testGetUserById() {
        Users retrievedUser = usersService.getUserById(testUser.getId());

        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getName()).isEqualTo("Test User");
        assertThat(retrievedUser.getFriends()).isEqualTo(0); // Verificar que `friends` es 0
    }

    @Test
    void testGetUserById_NotFound() {
        Long nonExistingId = 999L;
        assertThrows(IllegalArgumentException.class, () -> usersService.getUserById(nonExistingId));
    }

    @Test
    void testUpdateUser() {
        Users userDetails = new Users();
        userDetails.setName("Updated Name");
        userDetails.setPassword("newpassword");
        userDetails.setFriends(0L); // Asignar valor numérico en actualización

        Users updatedUser = usersService.updateUser(testUser.getId(), userDetails);

        assertThat(updatedUser.getName()).isEqualTo("Updated Name");
        assertThat(updatedUser.getPassword()).isEqualTo("newpassword");
        assertThat(updatedUser.getFriends()).isEqualTo(0); // Verificar que `friends` es 0
    }

    @Test
    void testDeleteUser() {
        Long userId = testUser.getId();

        usersService.deleteUser(userId);

        Optional<Users> deletedUser = usersRepository.findById(userId);
        assertThat(deletedUser).isEmpty();
    }

    @Test
    void testCreatePostForUser() {
        Posts newPost = new Posts();
        newPost.setLabel("New Post");
        newPost.setDescription("Description of the new post");

        Posts savedPost = usersService.createPostForUser(testUser.getId(), newPost);

        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getUser().getId()).isEqualTo(testUser.getId());
        assertThat(savedPost.getLabel()).isEqualTo("New Post");
    }


    @Test
    void testFindByName() {
        Users foundUser = usersService.findByName("Test User");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo("Test User");
        assertThat(foundUser.getFriends()).isEqualTo(0); // Verificar que `friends` es 0
    }
}
