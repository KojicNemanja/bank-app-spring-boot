package my.bank.static_files;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class CSSFile {

    @GetMapping("css/style.css")
    public String read() throws IOException {
        Path css_path = Path.of(System.getenv("JAVA_RESOURCES") + "/bank_app/Static/css/style.css");
        return Files.readString(css_path);
    }


}
