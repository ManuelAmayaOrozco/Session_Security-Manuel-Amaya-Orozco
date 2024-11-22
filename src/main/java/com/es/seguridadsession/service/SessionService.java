package com.es.seguridadsession.service;

import com.es.seguridadsession.model.Session;
import com.es.seguridadsession.repository.SessionRepository;
import com.es.seguridadsession.utils.CipherUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private CipherUtils cipherUtils;

    public boolean checkToken(String token) {

        // Comprobamos que el token existe y es válido
        Session s = null;
        try {
            s = sessionRepository
                    .findByToken(token)
                    .orElseThrow();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // SI ESTOY EN ESTE PUNTO, es que ha encontrado el TOKEN
        // Compruebo si la fecha es correcta
        LocalDateTime ahora = LocalDateTime.now();
        if(ahora.isAfter(s.getExpirationDate())) {
            // LANZO UNA EXCEPCION
        }

        return true;
    }

    public Session getByToken(String token) {

        // Comprobamos que el token existe y es válido
        Session s = null;
        try {
            s = sessionRepository
                    .findByToken(token)
                    .orElseThrow();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // SI ESTOY EN ESTE PUNTO, es que ha encontrado el TOKEN
        // Compruebo si la fecha es correcta
        LocalDateTime ahora = LocalDateTime.now();
        if(ahora.isAfter(s.getExpirationDate())) {
            // LANZO UNA EXCEPCION
        }

        return s;

    }

}
