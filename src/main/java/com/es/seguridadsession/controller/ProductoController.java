package com.es.seguridadsession.controller;

import com.es.seguridadsession.dto.ProductoDTO;
import com.es.seguridadsession.dto.UsuarioDTO;
import com.es.seguridadsession.model.Session;
import com.es.seguridadsession.service.ProductoService;
import com.es.seguridadsession.service.SessionService;
import com.es.seguridadsession.service.UsuarioService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * CLASE CONTROLLER DE PRODUCTOS
 * ESTOS RECURSOS ESTÁN PROTEGIDOS, Y SÓLO SE PUEDE ACCEDER AQUÍ SI EL USUARIO TIENE UNA SESSION ACTIVA
 */
@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UsuarioService usuarioService;


    /**
     * GET PRODUCTO POR SU ID
     * A este método pueden acceder todo tipo de usuarios
     * tanto los que tengan ROL USER como los que tengan ROL ADMIN
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getById(
            HttpServletRequest request,
            @PathVariable String id
    ) {

        // Tenemos que obtener la cookie -> y de la cookie vamos a obtener el tokenSession
        String token = "";
        for(Cookie cookie: request.getCookies()) {
            if(cookie.getName().equals("tokenSession")) {
                token = cookie.getValue();

            }
        }

        // Una vez tenemos el tokenSession
        if (sessionService.checkToken(token)) {

            //Obtenemos la sesion específica para conseguir el ID del usuario relacionado y la fecha de expiración
            Session session = sessionService.getByToken(token);
            LocalDateTime expirationDate = session.getExpirationDate();

            //Si la fecha ha expirado, no se permite entrar
            if (LocalDateTime.now().isAfter(expirationDate)) {

                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

            }

            //Compruebo que el id no es null
            if (id == null) {
              //Lanzo excepción
            }

            ProductoDTO p = productoService.getById(id);

            //3º Compruebo la validez de s para devolver una respuesta
            if (p == null) {
                //Lanzo excepción
            } else {
                return new ResponseEntity<>(p, HttpStatus.OK);
            }

        } else {

            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        }

        return null;

    }

    /**
     * INSERTAR PRODUCTO
     * A este método sólo pueden acceder los usuarios que tengan ROL ADMIN
     * @param productoDTO
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<ProductoDTO> insert(
            HttpServletRequest request,
            @RequestBody ProductoDTO productoDTO
    ) {

        // Tenemos que obtener la cookie -> y de la cookie vamos a obtener el tokenSession
        String token = "";
        for(Cookie cookie: request.getCookies()) {
            if(cookie.getName().equals("tokenSession")) {
                token = cookie.getValue();

            }
        }

        // Una vez tenemos el tokenSession
        if (sessionService.checkToken(token)) {

            //Obtenemos la sesion específica para conseguir el ID del usuario relacionado y la fecha de expiración
            Session session = sessionService.getByToken(token);
            Long id = session.getId();
            LocalDateTime expirationDate = session.getExpirationDate();

            //Si la fecha ha expirado, no se permite entrar
            if (LocalDateTime.now().isAfter(expirationDate)) {

                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

            }

            //Buscamos el usuario y nos aseguramos de que es Admin
            UsuarioDTO usuarioDTO = usuarioService.getById(id);

            if (usuarioDTO.getAdmin()) {

                ProductoDTO p = productoService.insert(productoDTO);

                return new ResponseEntity<>(p, HttpStatus.CREATED);

            } else {

                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

            }


        } else {

            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        }

    }


}
