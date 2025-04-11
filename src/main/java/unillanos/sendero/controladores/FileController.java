package unillanos.sendero.controladores;


import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class FileController {

public FileController(){try {
    Files.createDirectories(rootLocation);
} catch (IOException e) {
    throw new RuntimeException("No se pudo crear el directorio de uploads");
}}

    private final Path rootLocation = Paths.get("uploads");

    @PostMapping("/especimen/")
    public ResponseEntity<Map<String, Object>> handleFileUpload(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Crea el directorio si no existe
            Files.createDirectories(rootLocation);

            // Guarda el archivo
            Files.copy(file.getInputStream(), rootLocation.resolve(file.getOriginalFilename()));

            response.put("success", true);
            response.put("message", "Archivo subido con éxito: " + file.getOriginalFilename());
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            response.put("success", false);
            response.put("message", "Error al subir el archivo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/files")
    public ResponseEntity<List<Map<String, Object>>> listFiles() {
        try {
            List<Map<String, Object>> files = new ArrayList<>();
            Files.list(rootLocation).forEach(path -> {
                Map<String, Object> fileInfo = new HashMap<>();
                fileInfo.put("name", path.getFileName().toString());
                fileInfo.put("size", path.toFile().length());
                fileInfo.put("modified", new Date(path.toFile().lastModified()));
                files.add(fileInfo);
            });
            return ResponseEntity.ok(files);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Endpoint para servir imágenes
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(file))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
