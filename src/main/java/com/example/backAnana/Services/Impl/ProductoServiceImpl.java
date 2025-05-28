package com.example.backAnana.Services.Impl;

import com.example.backAnana.Entities.Producto;
import com.example.backAnana.Repositories.ProductoRepository;
import com.example.backAnana.Services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl extends BaseServiceImpl<Producto, Long> implements ProductoService {

    @Autowired
    private ProductoRepository repository;

    public ProductoServiceImpl(ProductoRepository repository) {
        super(repository);
    }

    // Método para actualizar stock
    public Producto updateStock(String codigo, int stock) {
        // Buscar el producto por código
        Producto producto = repository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con código: " + codigo));

        // Actualizar el stock
        producto.setStock(producto.getStock() + stock);

        // Guardar los cambios
        return repository.save(producto);
    }

    // Método para validar código único al crear/actualizar
    public void validarCodigoUnico(String codigo, Long idExcluir) {
        if (repository.existsByCodigo(codigo)) {
            // Si estamos actualizando (idExcluir != null), verificar que no sea el mismo producto
            if (idExcluir == null || !repository.findByCodigo(codigo).get().getId().equals(idExcluir)) {
                throw new RuntimeException("El código de producto ya está en uso");
            }
        }
    }

}
