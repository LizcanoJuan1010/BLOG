package Blog.app.Blog;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
@Entity

@NoArgsConstructor
public class Drafts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String label;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Users user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
