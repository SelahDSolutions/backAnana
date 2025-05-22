package com.example.backAnana.Controllers;

import com.example.backAnana.Entities.Domicilio;
import com.example.backAnana.Services.Impl.DomicilioServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/domicilio")
public class DomicilioController extends BaseControllerImpl<Domicilio, DomicilioServiceImpl> {

    private DomicilioController(DomicilioServiceImpl service) {
        super(service);
    }

}
