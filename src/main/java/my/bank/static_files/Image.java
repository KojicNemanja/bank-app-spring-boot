package my.bank.static_files;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class Image {

    @GetMapping("/image/{img_name}")
    @ResponseBody
    public byte[] read(@PathVariable(value = "img_name") String img) throws IOException {
        Path css_path = Path.of(System.getenv("JAVA_RESOURCES") + "/bank_app/Static/images/" + img);
        return Files.readAllBytes(css_path);
    }
}
