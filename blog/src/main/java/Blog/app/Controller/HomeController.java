package Blog.app.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // Maneja la ruta raíz
    public String home() {
        return "index"; // Retorna una vista llamada "index.html"
    }
}
