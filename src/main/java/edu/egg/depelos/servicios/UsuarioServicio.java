package edu.egg.depelos.servicios;

import edu.egg.depelos.entidades.Foto;
import edu.egg.depelos.entidades.Usuario;
import edu.egg.depelos.entidades.Zona;
import edu.egg.depelos.errores.ErrorServicio;
import edu.egg.depelos.repositorios.UsuarioRepositorio;
import edu.egg.depelos.repositorios.ZonaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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
    public void registrar(MultipartFile archivo, String nombre, String apellido, String mail, String telefono, String clave, String clave2, String idZona) throws ErrorServicio{
        if (validarMail(mail)) {
            
        validar( nombre, apellido, mail, telefono, clave, clave2, idZona);
        Zona zona=zonaRepositorio.getOne(idZona);
        if (zona==null) {
            throw new ErrorServicio("No se encontro la Zona");
        }
        
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setTelefono(telefono);
        usuario.setZona(zona);
        
        String enciptada = new BCryptPasswordEncoder().encode(clave);
        usuario.setClave(enciptada);
        
        usuario.setAlta(new Date());
        
        Foto foto = fotoServicio.guardar(archivo);
        usuario.setFoto(foto);
        
        usuarioRepositorio.save(usuario);
        }else{
            throw new ErrorServicio("Ya hay un usuario registrado con ese mail.");
        }
            
//        notificacionServicio.enviar("Bienvenidos al DePelos!", "DePelos", usuario.getMail());
    }
    
    @Transactional
    public void modificar( String id,String nombre, String apellido, String mail, String telefono, String clave, String clave2, String idZona) throws ErrorServicio{
         Zona zona=zonaRepositorio.getOne(idZona);
          if (zona==null) {
            throw new ErrorServicio("No se encontro la cabeZona");
        }
         validar( nombre, apellido, mail, telefono, clave,clave2, idZona);
        
        Optional <Usuario> respuesta=usuarioRepositorio.findById(id); //busca en el repositorio el usuario por ID, y lo trae con get
        if(respuesta.isPresent()){
        Usuario usuario = respuesta.get();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setTelefono(telefono);
        usuario.setZona(zona);
        String encriptada=new BCryptPasswordEncoder().encode(clave);
        usuario.setClave(encriptada);
        usuario.setAlta(new Date());
        usuario.setClave(clave);
//        
//        
//            
//        Foto foto = fotoServicio.actualizar(idFoto, archivo);
//        usuario.setFoto(foto);
        
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
    
    
    public boolean validarMail(String mail){
        try{
        Usuario usuario = usuarioRepositorio.buscarPorMail(mail);
        return (usuario.getId()==null);
        }catch(NullPointerException e){
            return true;
        }
        
    }
    private void validar(String nombre, String apellido, String mail, String telefono, String clave, String clave2, String zona) throws ErrorServicio{
        
            
        if(nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("El nombre del usuario no puede ser nulo.");
        }

        if(apellido == null || apellido.isEmpty()){
            throw new ErrorServicio("El apellido del usuario no puede ser nulo.");
        }
        
        if(mail == null || mail.isEmpty()){
            throw new ErrorServicio("El mail del usuario no puede ser nulo.");
        }
        
        if(clave == null || clave.isEmpty()){
            throw new ErrorServicio("La clave del usuario no puede ser nulo.");
        }
        
        if(!clave2.equals(clave)){
            throw new ErrorServicio("Las contraseña no son las mismas");
        }
        
        if(zona == null){
            throw new ErrorServicio("No se encontrÃ³ la zona solicitada.");
        }
    }
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorMail(mail);
        if(usuario != null){
            
            List<GrantedAuthority> permisos = new ArrayList<>();
            
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
            permisos.add(p1);
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true); 
            session.setAttribute("usuariosession", usuario);

            User user = new User(usuario.getMail(), usuario.getClave(), permisos);
            return user;
        } else {
            return null;
        }
    }
    
}