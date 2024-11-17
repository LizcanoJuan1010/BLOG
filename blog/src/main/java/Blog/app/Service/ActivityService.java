package Blog.app.Service;

import Blog.app.Blog.Activity;
import Blog.app.Blog.Comments;
import Blog.app.Blog.Posts;
import Blog.app.Blog.Users;
import Blog.app.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList; // Importación añadida
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ActivityService {

    @Autowired
    private UsersRepository usersRepository;

    public Activity getActivityByUserId(Long userId) {
        // Recuperar el usuario desde la base de datos
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Asegurar que las listas no sean nulas
        if (user.getPosts() == null) {
            user.setPosts(new ArrayList<>());
        }

        if (user.getAmigos() == null) {
            user.setAmigos(new ArrayList<>());
        }

        if (user.getDrafts() == null) {
            user.setDrafts(new ArrayList<>());
        }

        // 1. Calcular las métricas básicas
        int totalEntradas = user.getPosts().size(); // Número de entradas publicadas
        int totalAmix = user.getAmigos().size();   // Número de amigos
        int totalDrafts = user.getDrafts().size(); // Número de borradores

        // 2. Inicializar variables para cálculos avanzados
        int totalComments = 0;
        Map<String, Integer> postsByLabel = new HashMap<>(); // Totales de posts por etiqueta
        Map<Long, Integer> commentsByPost = new HashMap<>(); // Totales de comentarios por post
        Map<Long, Map<String, Integer>> commentLabelsByPost = new HashMap<>(); // Etiquetas en comentarios por post

        // 3. Iterar sobre los posts del usuario
        for (Posts post : user.getPosts()) {
            // Incrementar el total de comentarios
            int postComments = post.getComentarios().size();
            totalComments += postComments;

            // Contar posts por etiqueta
            String postLabel = post.getLabel();
            postsByLabel.put(postLabel, postsByLabel.getOrDefault(postLabel, 0) + 1);

            // Guardar el total de comentarios por post
            commentsByPost.put(post.getId(), postComments);

            // Contar etiquetas dentro de los comentarios
            Map<String, Integer> labelsInComments = new HashMap<>();
            for (Comments comment : post.getComentarios()) {
                // Validar la etiqueta del comentario
                String commentLabel = (comment.getPost() != null) ? comment.getPost().getLabel() : "Sin etiqueta";
                labelsInComments.put(commentLabel, labelsInComments.getOrDefault(commentLabel, 0) + 1);
            }
            commentLabelsByPost.put(post.getId(), labelsInComments);
        }

        // 4. Crear y retornar la actividad
        Activity activity = new Activity(totalEntradas, totalAmix, totalComments, totalDrafts);
        activity.setPostsByLabel(postsByLabel);
        activity.setCommentsByPost(commentsByPost);
        activity.setCommentLabelsByPost(commentLabelsByPost);

        return activity;
    }
}