package com.malloc.usuario.business;

import com.malloc.usuario.infrastructure.entity.Usuario;
import com.malloc.usuario.infrastructure.exceptions.ConflictException;
import com.malloc.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario salvaUsuario(Usuario usuario){
        try{
            emailExiste(usuario.getEmail());
            return usuarioRepository.save(usuario);
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado", e.getCause());
        }
    }

    public void emailExiste(String email){
        try{
           boolean existe = verificaEmailExitente(email);
           if (existe){
               throw new ConflictException("Email já cadastrado" + email);
           }
        }catch (ConflictException e){
            throw new ConflictException("Email ja cadastrado", e.getCause());
        }
    }

    public boolean verificaEmailExitente(String email){
        return usuarioRepository.existsByEmail(email);
    }
}
