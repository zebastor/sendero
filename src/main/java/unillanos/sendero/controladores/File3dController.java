package unillanos.sendero.controladores;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class File3dController {
    private final Path rootLocation = Paths.get("uploads3d");

    public File3dController(){try {
        Files.createDirectories(rootLocation);
    } catch (IOException e) {
        throw new RuntimeException("No se pudo crear el directorio de uploads3d");
    }}


    @PostMapping("/upload3d")
    public ResponseEntity<Map<String, Object>> handleFileUpload(
            @RequestParam("file") MultipartFile[] files) {

        Map<String, Object> response = new HashMap<>();
        List<String> uploadedFiles = new ArrayList<>();
        List<String> duplicates = new ArrayList<>();

        try {
            Files.createDirectories(rootLocation);

            // 1. Verificar archivos duplicados
            for (MultipartFile file : files) {
                Path destination = rootLocation.resolve(file.getOriginalFilename());
                if (Files.exists(destination)) {
                    duplicates.add(file.getOriginalFilename());
                }
            }

            // 2. Si hay duplicados, retornar error con detalles
            if (!duplicates.isEmpty()) {
                response.put("success", false);
                response.put("message", "Archivos duplicados detectados");
                response.put("duplicates", duplicates);
                return ResponseEntity
                        .status(HttpStatus.CONFLICT) // Código 409
                        .body(response); // Incluir detalles en el cuerpo
            }

            // 3. Guardar archivos si no hay duplicados
            for (MultipartFile file : files) {
                Path destination = rootLocation.resolve(file.getOriginalFilename());
                Files.copy(file.getInputStream(), destination);
                uploadedFiles.add(file.getOriginalFilename());
            }

            response.put("success", true);
            response.put("files", uploadedFiles);
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            response.put("success", false);
            response.put("message", "Error interno: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }


    @GetMapping("/files3d")
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
    @GetMapping("/files3d/{filename:.+}")
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

    @DeleteMapping("/files3d/{filename:.+}")
    public ResponseEntity<Map<String, Object>> deleteFile(@PathVariable String filename) {
        Map<String, Object> response = new HashMap<>();
        try {
            Path file = rootLocation.resolve(filename);
            boolean deleted = Files.deleteIfExists(file);

            if (deleted) {
                response.put("success", true);
                response.put("message", "Archivo eliminado con éxito");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "El archivo no existe");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (IOException e) {
            response.put("success", false);
            response.put("message", "Error al eliminar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
