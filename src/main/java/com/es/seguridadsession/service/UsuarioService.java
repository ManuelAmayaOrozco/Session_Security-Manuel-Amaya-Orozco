package com.es.seguridadsession.service;

import com.es.seguridadsession.dto.UsuarioDTO;
import com.es.seguridadsession.dto.UsuarioInsertDTO;
import com.es.seguridadsession.model.Session;
import com.es.seguridadsession.model.Usuario;
import com.es.seguridadsession.repository.SessionRepository;
import com.es.seguridadsession.repository.UsuarioRepository;
import com.es.seguridadsession.utils.CipherUtils;
import com.es.seguridadsession.utils.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private CipherUtils cipherUtils;

    public String login(UsuarioDTO userLogin) {

        // Comprobar si user y pass son correctos -> obtener de la BDD el usuario
        String nombreUser = userLogin.getNombre();
        String passUser = userLogin.getPassword();

        List<Usuario> users = usuarioRepository.findByNombre(nombreUser);

        Usuario u = users
                .stream()
                .filter(user -> user.getNombre().equals(nombreUser) && cipherUtils.checkPassword(passUser, user.getPassword()))
                .findFirst()
                .orElseThrow(); // LANZAR EXCEPCION PROPIA

        // Si coincide -> Insertar una sesión
        // Genero un TOKEN
        String token = null; // Esto genera un token aleatorio
        try {
            token = cipherUtils.encrypt(nombreUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Token generado: "+token);
        // Almaceno la Session en la base de datos
        Session s = new Session();
        s.setToken(token);
        s.setUsuario(u);
//        s.setExpirationDate(
//                LocalDateTime.of(
//                        LocalDate.of(2024, 11, 20),
//                        LocalTime.now()
//                ));
        s.setExpirationDate(
                LocalDateTime.now().plusMinutes(2) //Las sesiones expiran en 1 minuto
        );

        //Compruebo si la sesión está duplicada
        List<Session> sessions = sessionRepository.findAll();
        boolean found = false;

        for (Session session: sessions) {

            if (s.getUsuario().getId().equals(session.getUsuario().getId())) {

                found = true;

                break;

            }

        }

        if (!found) {

            sessionRepository.save(s);

        }

        return token;

    }

    public UsuarioInsertDTO insert(UsuarioInsertDTO nuevoUser) {

        nuevoUser.setPassword1(cipherUtils.hashPassword(nuevoUser.getPassword1()));

        Usuario usuario = mapToUser(nuevoUser);

        usuario = usuarioRepository.save(usuario);

        return mapToInsertDTO(usuario);

    }

    public UsuarioDTO getById(Long id) {

        Usuario u = null;
        u = usuarioRepository
                .findById(id)
                .orElseThrow();

        return mapToDTO(u);

    }

    private UsuarioDTO mapToDTO(Usuario usuario) {

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setPassword(usuario.getPassword());
        usuarioDTO.setAdmin(usuario.getAdmin());
        return usuarioDTO;

    }

    private UsuarioInsertDTO mapToInsertDTO(Usuario usuario) {

        UsuarioInsertDTO usuarioInsertDTO = new UsuarioInsertDTO();
        usuarioInsertDTO.setNombre(usuario.getNombre());
        usuarioInsertDTO.setPassword1(usuario.getPassword());
        usuarioInsertDTO.setAdmin(usuario.getAdmin());
        return usuarioInsertDTO;

    }

    private Usuario mapToUser(UsuarioDTO usuarioDTO) {

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setAdmin(usuarioDTO.getAdmin());
        return usuario;

    }

    private Usuario mapToUser(UsuarioInsertDTO usuarioInsertDTO) {

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioInsertDTO.getNombre());
        usuario.setPassword(usuarioInsertDTO.getPassword1());
        usuario.setAdmin(usuarioInsertDTO.getAdmin());
        return usuario;

    }
}
