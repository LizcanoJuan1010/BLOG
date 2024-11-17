package Blog.app.Blog;
import java.util.HashMap;
import java.util.Map;

public class Activity {
    private Integer totalEntradas;
    private Integer totalAmix;
    private Integer totalComments;
    private Integer totalDrafts;

    // Nuevos atributos
    private Map<String, Integer> postsByLabel; // Totales de posts por etiqueta
    private Map<Long, Integer> commentsByPost; // Totales de comentarios por post
    private Map<Long, Map<String, Integer>> commentLabelsByPost; // Etiquetas en comentarios por post

    // Constructor básico
    public Activity(Integer totalEntradas, Integer totalAmix, Integer totalComments, Integer totalDrafts) {
        this.totalEntradas = totalEntradas;
        this.totalAmix = totalAmix;
        this.totalComments = totalComments;
        this.totalDrafts = totalDrafts;

        // Inicializar mapas vacíos
        this.postsByLabel = new HashMap<>();
        this.commentsByPost = new HashMap<>();
        this.commentLabelsByPost = new HashMap<>();
    }

    // Getters y Setters para las métricas básicas
    public Integer getTotalEntradas() {
        return totalEntradas;
    }

    public void setTotalEntradas(Integer totalEntradas) {
        this.totalEntradas = totalEntradas;
    }

    public Integer getTotalAmix() {
        return totalAmix;
    }

    public void setTotalAmix(Integer totalAmix) {
        this.totalAmix = totalAmix;
    }

    public Integer getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(Integer totalComments) {
        this.totalComments = totalComments;
    }

    public Integer getTotalDrafts() {
        return totalDrafts;
    }

    public void setTotalDrafts(Integer totalDrafts) {
        this.totalDrafts = totalDrafts;
    }

    // Getters y Setters para los nuevos atributos
    public Map<String, Integer> getPostsByLabel() {
        return postsByLabel;
    }

    public void setPostsByLabel(Map<String, Integer> postsByLabel) {
        this.postsByLabel = postsByLabel;
    }

    public Map<Long, Integer> getCommentsByPost() {
        return commentsByPost;
    }

    public void setCommentsByPost(Map<Long, Integer> commentsByPost) {
        this.commentsByPost = commentsByPost;
    }

    public Map<Long, Map<String, Integer>> getCommentLabelsByPost() {
        return commentLabelsByPost;
    }

    public void setCommentLabelsByPost(Map<Long, Map<String, Integer>> commentLabelsByPost) {
        this.commentLabelsByPost = commentLabelsByPost;
    }
}