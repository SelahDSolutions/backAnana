package com.example.backAnana.Controllers;

import com.example.backAnana.Entities.Venta;
import com.example.backAnana.Services.Impl.VentaServiceImpl;
import com.example.backAnana.Services.VentaPrintManager;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/venta")
public class VentaController extends BaseControllerImpl<Venta, VentaServiceImpl> {

    private VentaController(VentaServiceImpl service) {
        super(service);
    }



    @GetMapping("/downloadExcel")
    public ResponseEntity<byte[]> downloadExcelVentas(
            @RequestParam(name = "fechaDesde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(name = "fechaHasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta)
            throws IOException, SQLException {

        // Generar el libro de Excel
        VentaPrintManager ventaPrintManager = new VentaPrintManager();
        SXSSFWorkbook workbook = ventaPrintManager.imprimirExcelVentas(fechaDesde, fechaHasta);

        // Convertir el workbook a un array de bytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.dispose(); // Liberar recursos

        // Configurar los headers de la respuesta
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "reporte_ventas.xlsx");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(
                outputStream.toByteArray(),
                headers,
                HttpStatus.OK
        );
    }


}
