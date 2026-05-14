package co.edu.unbosque.revista.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.unbosque.revista.dto.UsuarioDTO;
import co.edu.unbosque.revista.entity.Usuario;
import co.edu.unbosque.revista.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRep;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioService() {
    }

    public long count() {
        return usuarioRep.count();
    }

    public boolean exist(Long id) {
        return usuarioRep.existsById(id);
    }

    public int create(UsuarioDTO data) {
        if (data.getNombre() == null || data.getNombre().isBlank()) return 1;
        if (data.getCorreo() == null || data.getCorreo().isBlank()) return 1;
        if (data.getContrasenia() == null || data.getContrasenia().isBlank()) return 1;

        Usuario entity = mapper.map(data, Usuario.class);
        entity.setContrasenia(passwordEncoder.encode(data.getContrasenia()));
        usuarioRep.save(entity);
        return 0;
    }

    public List<UsuarioDTO> getAll() {
        Iterable<Usuario> lista = usuarioRep.findAll();
        List<UsuarioDTO> dtoList = new ArrayList<>();
        lista.forEach(u -> dtoList.add(mapper.map(u, UsuarioDTO.class)));
        return dtoList;
    }

    public int updateById(Long id, UsuarioDTO newData) {
        Optional<Usuario> existente = usuarioRep.findById(id);
        if (existente.isPresent()) {
            Usuario u = existente.get();
            u.setNombre(newData.getNombre());
            u.setCorreo(newData.getCorreo());
            u.setRol(newData.getRol());
            if (newData.getContrasenia() != null && !newData.getContrasenia().isBlank()) {
                u.setContrasenia(passwordEncoder.encode(newData.getContrasenia()));
            }
            usuarioRep.save(u);
            return 0;
        }
        return 1;
    }

    public int deleteById(Long id) {
        if (usuarioRep.existsById(id)) {
            usuarioRep.deleteById(id);
            return 0;
        }
        return 1;
    }

    // --- CORRECCIÓN DE MÉTODOS DE BÚSQUEDA ---

    public List<UsuarioDTO> findByNombre(String nombre) {
        Optional<Usuario> encontrado = usuarioRep.findByNombre(nombre);
        List<UsuarioDTO> dtoList = new ArrayList<>();
        encontrado.ifPresent(u -> dtoList.add(mapper.map(u, UsuarioDTO.class)));
        return dtoList;
    }

    public List<UsuarioDTO> findByCorreo(String correo) {
        Optional<Usuario> encontrado = usuarioRep.findByCorreo(correo);
        List<UsuarioDTO> dtoList = new ArrayList<>();
        encontrado.ifPresent(u -> dtoList.add(mapper.map(u, UsuarioDTO.class)));
        return dtoList;
    }

    public List<UsuarioDTO> findByRol(String rol) {
        Optional<Usuario> encontrado = usuarioRep.findByRol(rol);
        List<UsuarioDTO> dtoList = new ArrayList<>();
        encontrado.ifPresent(u -> dtoList.add(mapper.map(u, UsuarioDTO.class)));
        return dtoList;
    }

    public int deleteByUsername(String username) {
        Optional<Usuario> found = usuarioRep.findByNombre(username);
        if (found.isPresent()) {
            usuarioRep.delete(found.get());
            return 0;
        }
        return 1;
    }

    public UsuarioDTO getById(Long id) {
        Optional<Usuario> found = usuarioRep.findById(id);
        return found.map(u -> mapper.map(u, UsuarioDTO.class)).orElse(null);
    }

    public boolean findUsernameAlreadyTaken(Usuario newUser) {
        return usuarioRep.findByNombre(newUser.getNombre()).isPresent();
    }

    public boolean findUsernameAlreadyTaken(String username) {
        return usuarioRep.findByNombre(username).isPresent();
    }

    public int validateCredentials(String username, String password) {
        Optional<Usuario> userOpt = usuarioRep.findByNombre(username);
        if (userOpt.isPresent()) {
            Usuario user = userOpt.get();
            if (passwordEncoder.matches(password, user.getContrasenia())) {
                return 0;
            }
        }
        return 1;
    }
}