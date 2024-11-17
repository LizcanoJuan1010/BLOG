package Blog.app.Service;

import Blog.app.Blog.Comments;
import Blog.app.Blog.Posts;
import Blog.app.Blog.Users;
import Blog.app.Repository.PostsRepository;
import Blog.app.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final UsersRepository usersRepository;
    private final CommentsService commentsService;
    @Autowired
    public PostsService(PostsRepository postsRepository, UsersRepository usersRepository, CommentsService commentsService) {
        this.postsRepository = postsRepository;
        this.usersRepository = usersRepository;
        this.commentsService = commentsService;
    }


    public Posts createPosts(Posts posts) {

        if (posts.getUser() == null) {
            throw new IllegalArgumentException("El usuario no existe");
        }

        return postsRepository.save(posts);
    }
    public List<Posts> getAllPosts() {
        return postsRepository.findAll();
    }

    public Posts updatePosts(Long id, Posts posts) {
        Posts post = postsRepository.findById(id).get();
        post.setLabel(posts.getLabel());
        post.setDescription(posts.getDescription());

        return postsRepository.save(posts);
    }

    public Comments addCommentToPost(Long postId, Long userId, String description) {

        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        Comments comment = new Comments();
        comment.setDescription(description);
        comment.setPost(post);
        comment.setUser(user);

        // Guarda el comentario usando el CommentsService
        return commentsService.createComments(comment);
    }
    public List<Posts> getAllPostsbyUser(Long user_id) {
        try {
            Users user = usersRepository.findById(user_id)
                    .orElseThrow(() -> new Exception("User not found"));
            return user.getPosts();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void deletePosts(Long id) {

        postsRepository.deleteById(id);
    }


}
