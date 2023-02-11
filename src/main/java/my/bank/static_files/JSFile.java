package my.bank.static_files;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class JSFile {

    @GetMapping("/js/{file_name}")
    public String read(@PathVariable String file_name) throws IOException {
        Path js_path = Path.of(System.getenv("JAVA_RESOURCES_E") + String.format("/web_app_test/Static/js/%s", file_name));
        return Files.readString(js_path);
    }

}
