package com.example.backAnana.Controllers;

import com.example.backAnana.Entities.DetalleVenta;
import com.example.backAnana.Services.Impl.DetalleVentaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/detalleVenta")
public class DetalleVentaController extends BaseControllerImpl<DetalleVenta, DetalleVentaServiceImpl> {

    @Autowired
    private DetalleVentaServiceImpl detalleVentaServiceImpl;

    private DetalleVentaController(DetalleVentaServiceImpl service) {
        super(service);
    }

    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<List<DetalleVenta>> findAllByVentaId(@PathVariable Long ventaId) throws Exception {
        List<DetalleVenta> detalles = detalleVentaServiceImpl.findAllByVentaId(ventaId);
        return new ResponseEntity<>(detalles, HttpStatus.OK);
    }

}
