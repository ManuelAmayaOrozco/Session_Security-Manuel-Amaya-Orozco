package com.es.seguridadsession.service;

import com.es.seguridadsession.dto.ProductoDTO;
import com.es.seguridadsession.model.Producto;
import com.es.seguridadsession.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Obtiene un producto buscándolo por su ID
     * @param id
     * @return
     */
    public ProductoDTO getById(String id) {

        // Parsear el id a Long
        Long idL = 0L;
        try {
            idL = Long.parseLong(id);
        } catch (NumberFormatException e) {
            //Lanza excepción
        }

        Producto p = productoRepository
                        .findById(idL)
                        .orElseThrow();

        return mapToDTO(p);
    }

    /**
     * Inserta un producto dentro la tabla productos
     * @param productoDTO
     * @return
     */
    public ProductoDTO insert(ProductoDTO productoDTO) {

        // 1º Asegurarnos que productoDTO no viene null
        if (productoDTO == null) {
            // Lanza excepcion
        }

        Producto p = mapToProducto(productoDTO);

        productoRepository.save(p);

        return mapToDTO(p);
    }

    private ProductoDTO mapToDTO(Producto producto) {

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setStock(producto.getStock());
        productoDTO.setPrecio(producto.getPrecio());
        return productoDTO;

    }

    private Producto mapToProducto(ProductoDTO productoDTO) {

        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setStock(productoDTO.getStock());
        producto.setPrecio(productoDTO.getPrecio());
        return producto;

    }

}
