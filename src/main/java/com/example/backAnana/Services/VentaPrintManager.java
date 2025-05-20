package com.example.backAnana.Services;

import com.example.backAnana.Entities.DetalleVenta;
import com.example.backAnana.Entities.Enums.FormaPago;
import com.example.backAnana.Entities.Producto;
import com.example.backAnana.Entities.Venta;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaPrintManager {

    @Autowired
    private VentaService ventaService;

    String urlConexion = "jdbc:mysql://localhost:3306/ananadb";
    String usuario = "root";
    String clave = "Cin9227";

    public SXSSFWorkbook imprimirExcelVentas(LocalDate fechaDesde, LocalDate fechaHasta) throws IOException, SQLException {
        // Se crea el libro
        SXSSFWorkbook libro = new SXSSFWorkbook(50);
        // Se crea una hoja dentro del libro
        SXSSFSheet hoja = libro.createSheet();

        // Estilo genérico para las celdas
        XSSFCellStyle style = (XSSFCellStyle) libro.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        // Estilo para las fechas
        CellStyle dateCellStyle = libro.createCellStyle();
        CreationHelper createHelper = libro.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        // Encabezados
        int nroColumna = 0;
        SXSSFRow row = hoja.createRow(0);

        String[] headers = {
                "Id", "Fecha Venta", "Forma de Pago", "Envío",
                "Producto", "Marca", "Código", "Cantidad",
                "Precio Unitario", "Subtotal", "Total Venta"
        };

        for (String header : headers) {
            SXSSFCell cell = row.createCell(nroColumna++);
            cell.setCellValue(header);
            cell.setCellStyle(style);
        }

        int nroFila = 1;

        List<Venta> ventas = getVentasFromRangeOfDates(fechaDesde, fechaHasta);
        System.out.println("Ventas obtenidas: " + ventas.size());

        for (Venta venta : ventas) {
            for (DetalleVenta detalle : venta.getDetalleVentas()) {
                nroColumna = 0;
                row = hoja.createRow(nroFila);

                // Datos de la venta
                row.createCell(nroColumna++).setCellValue(venta.getId());

                Cell fechaCell = row.createCell(nroColumna++);
                fechaCell.setCellValue(Date.valueOf(venta.getFecha()));
                fechaCell.setCellStyle(dateCellStyle);

                row.createCell(nroColumna++).setCellValue(venta.getFormaPago().toString());
                row.createCell(nroColumna++).setCellValue(venta.getEnvio());

                // Datos del producto
                Producto producto = detalle.getProducto();
                row.createCell(nroColumna++).setCellValue(producto.getDenominacion());
                row.createCell(nroColumna++).setCellValue(producto.getMarca());
                row.createCell(nroColumna++).setCellValue(producto.getCodigo());
                row.createCell(nroColumna++).setCellValue(detalle.getCantidad());
                row.createCell(nroColumna++).setCellValue(producto.getPrecio());
                row.createCell(nroColumna++).setCellValue(detalle.getSubTotal());
                row.createCell(nroColumna++).setCellValue(venta.getTotal());

                nroFila++;
            }
        }

        // Autoajustar columnas
        for (int i = 0; i < headers.length; i++) {
            hoja.autoSizeColumn(i);
        }

        return libro;
    }

    public List<Venta> getVentasFromRangeOfDates(final LocalDate fechaDesde, final LocalDate fechaHasta) {
        List<Venta> allVentas = new ArrayList<>();
        LocalDate fechaHastaIncrementada = fechaHasta.plusDays(1);

        try {
            allVentas = getVentasFromRangeOfDatesUsingSQL(fechaDesde, fechaHastaIncrementada);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return allVentas.stream()
                .filter(venta -> !venta.getFecha().isBefore(fechaDesde) && !venta.getFecha().isAfter(fechaHastaIncrementada))
                .collect(Collectors.toList());
    }

    private List<Venta> getVentasFromRangeOfDatesUsingSQL(LocalDate fechaDesde, LocalDate fechaHasta)
            throws SQLException, ClassNotFoundException {

        List<Venta> ventas = new ArrayList<>();
        Connection conexion = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(urlConexion, usuario, clave);

            String query = "SELECT v.*, p.denominacion, p.marca, p.codigo, p.precio, dv.cantidad, fp.descripcion as forma_pago " +
                    "FROM venta v " +
                    "JOIN detalle_venta dv ON v.id = dv.ventaId " +
                    "JOIN producto p ON dv.productoId = p.id " +
                    "JOIN forma_pago fp ON v.formaPago = fp.id " +
                    "WHERE v.fecha >= ? AND v.fecha <= ?";

            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setDate(1, Date.valueOf(fechaDesde));
            ps.setDate(2, Date.valueOf(fechaHasta));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Venta venta = new Venta();
                venta.setId(rs.getLong("id"));
                venta.setFecha(rs.getDate("fecha").toLocalDate());
                venta.setTotal(rs.getDouble("total"));
                venta.setEnvio(rs.getDouble("envio"));
                venta.setFormaPago(FormaPago.valueOf(rs.getString("forma_pago")));

                DetalleVenta detalle = new DetalleVenta();
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setProducto(new Producto(
                        rs.getString("denominacion"),
                        rs.getString("marca"),
                        rs.getString("codigo"),
                        rs.getDouble("precio")
                ));
                detalle.setVenta(venta);

                venta.getDetalleVentas().add(detalle);
                ventas.add(venta);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (conexion != null) {
                conexion.close();
            }
        }
        return ventas;
    }
}