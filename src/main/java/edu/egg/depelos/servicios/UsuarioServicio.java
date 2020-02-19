package edu.egg.depelos.servicios;

import edu.egg.depelos.entidades.Foto;
import edu.egg.depelos.entidades.Usuario;
import edu.egg.depelos.entidades.Zona;
import edu.egg.depelos.errores.ErrorServicio;
import edu.egg.depelos.repositorios.UsuarioRepositorio;
import edu.egg.depelos.repositorios.ZonaRepositorio;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
public class UsuarioServicio implements UserDetailsService {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private ZonaRepositorio zonaRepositorio;
    
    @Autowired
    private NotificacionServicio notificacionServicio;
    
    @Autowired
    private FotoServicio fotoServicio;
    
    @Transactional
    public void registrar(MultipartFile archivo,String nombre, String apellido, String mail, String telefono, String clave, String idZona) throws ErrorServicio{
    
        Zona zona=zonaRepositorio.getOne(idZona);
        validar( nombre, apellido, mail, telefono, clave, idZona);
        
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setZona(zona);
        
        String enciptada = new BCryptPasswordEncoder().encode(clave);
        usuario.setClave(enciptada);
        
        usuario.setAlta(new Date());
        
        Foto foto = fotoServicio.guardar(archivo);
        usuario.setFoto(foto);
        
        usuarioRepositorio.save(usuario);
        
        notificacionServicio.enviar("Bienvenidos al DePelos!", "DePelos", usuario.getMail());
    }
    
    @Transactional
    public void modificar(MultipartFile archivo, String id,String nombre, String apellido, String mail, String telefono, String clave, String idZona) throws ErrorServicio{
         Zona zona=zonaRepositorio.getOne(idZona);
         validar( nombre, apellido, mail, telefono, clave, idZona);
        
        Optional <Usuario> respuesta=usuarioRepositorio.findById(id); //busca en el repositorio el usuario por ID, y lo trae con get
        if(respuesta.isPresent()){
        Usuario usuario = respuesta.get();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setZona(zona);
        String encriptada=new BCryptPasswordEncoder().encode(clave);
        usuario.setClave(clave);
        usuario.setClave(clave);
        
        String idFoto=null;
            if (usuario.getFoto()!= null) {
                idFoto = usuario.getFoto().getId();
            }
            
        Foto foto = fotoServicio.actualizar(idFoto, archivo);
        usuario.setFoto(foto);
        
        }else{
            throw new ErrorServicio("No se encontro el usuario solicitado");
        }
    }
    
    @Transactional
    public void darBaja(String id) throws ErrorServicio {
        Optional<Usuario> reply = usuarioRepositorio.findById(id);
        if (reply.isPresent()) {
            Usuario usuario = reply.get();

            usuario.setBaja(new Date());

            usuarioRepositorio.save(usuario);
        } else {
            throw new ErrorServicio("Can't find the requested user.");
        }
    }
    
    
    private void validar(String nombre, String apellido, String mail, String telefono, String clave, String zona) throws ErrorServicio{
        
        
        if(nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("El nombre del usuario no puede ser nulo.");
        }

        if(apellido == null || apellido.isEmpty()){
            throw new ErrorServicio("El apellido del usuario no puede ser nulo.");
        }
        
        if(mail == null || mail.isEmpty()){
            throw new ErrorServicio("El mail del usuario no puede ser nulo.");
        }
        
        if(telefono == null || telefono.isEmpty()){
            throw new ErrorServicio("El mail del usuario no puede ser nulo.");
        }
        
        if(clave == null || clave.isEmpty() || clave.length() <= 6){
            throw new ErrorServicio("La clave del usuario no puede ser nula y tiene que tener mas de seis dÃ­gitos.");
        }
        
        
        if(zona == null){
            throw new ErrorServicio("No se encontrÃ³ la zona solicitada.");
        }
    }
    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}