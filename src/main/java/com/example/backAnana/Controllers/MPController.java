package com.example.backAnana.Controllers;

import com.example.backAnana.Entities.PreferenceMP;
import com.example.backAnana.Entities.Venta;
import com.example.backAnana.Services.MPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mercadoPago")
public class MPController {

    @Autowired
    private MPService mercadoPagoService;

    @PostMapping("/createPreference")
    public PreferenceMP createPreference(@RequestBody Venta venta) {
        return mercadoPagoService.createPreference(venta);
    }

}
