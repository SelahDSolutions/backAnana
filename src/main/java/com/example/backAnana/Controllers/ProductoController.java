package com.example.backAnana.Controllers;

import com.example.backAnana.Entities.Producto;
import com.example.backAnana.Services.Impl.ProductoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/producto")
public class ProductoController extends BaseControllerImpl<Producto, ProductoServiceImpl> {

    private ProductoController(ProductoServiceImpl service) {
        super(service);
    }

}
