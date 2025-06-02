package com.example.backAnana.Controllers;

import com.example.backAnana.Entities.Producto;
import com.example.backAnana.Services.Impl.ProductoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/producto")
public class ProductoController extends BaseControllerImpl<Producto, ProductoServiceImpl> {

    @Autowired
    private ProductoServiceImpl service;

    private ProductoController(ProductoServiceImpl service) {
        super(service);
    }

    @PatchMapping("/{codigo}/stock")
    public ResponseEntity<?> actualizarStock(@PathVariable String codigo, @RequestParam int cantidad) {
        try {
            Producto producto = service.updateStock(codigo, cantidad);
            return ResponseEntity.ok(producto);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Producto producto) {
        try {
            service.validarCodigoUnico(producto.getCodigo(), null);
            return super.save(producto);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, por favor intente mas tarde.\"}");
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Producto producto) {
        try {
            service.validarCodigoUnico(producto.getCodigo(), id);
            return super.update(id, producto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    // 🔍 GET para buscar por código
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<?> buscarPorCodigo(@PathVariable String codigo) {
        try {
            Producto producto = service.findByCodigo(codigo);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // 🔍 GET para buscar por nombre/denominación
    @GetMapping("/nombre/{denominacion}")
    public ResponseEntity<?> buscarPorDenominacion(@PathVariable String denominacion) {
        try {
            Producto producto = service.findByDenominacion(denominacion);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Archivo vacío");
        }

        String uploadDir = "uploads/";
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if (fileName.contains("..")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nombre de archivo no válido");
        }

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok("{\"fileName\":\"" + fileName + "\"}");
        } catch (IOException e) {
            e.printStackTrace(); // 🪵 útil para ver el error exacto en consola
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la imagen.");
        }
    }


}
