package com.example.backAnana.Controllers;

import com.example.backAnana.Entities.Producto;
import com.example.backAnana.Services.Impl.ProductoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
