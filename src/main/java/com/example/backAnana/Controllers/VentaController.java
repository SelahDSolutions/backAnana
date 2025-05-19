package com.example.backAnana.Controllers;

import com.example.backAnana.Entities.Venta;
import com.example.backAnana.Services.Impl.VentaServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/venta")
public class VentaController extends BaseControllerImpl<Venta, VentaServiceImpl> {

    private VentaController(VentaServiceImpl service) {
        super(service);
    }

}
