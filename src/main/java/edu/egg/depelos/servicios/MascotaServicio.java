
package edu.egg.depelos.servicios;

import edu.egg.depelos.entidades.Foto;
import edu.egg.depelos.entidades.Mascota;
import edu.egg.depelos.entidades.Usuario;
import edu.egg.depelos.enumeraciones.ColorPrimario;
import edu.egg.depelos.enumeraciones.ColorSecundario;
import edu.egg.depelos.enumeraciones.Estado;
import static edu.egg.depelos.enumeraciones.Estado.ADOPTAR;
import edu.egg.depelos.enumeraciones.Raza;
import edu.egg.depelos.enumeraciones.Sexo;
import edu.egg.depelos.enumeraciones.Tamanio;
import edu.egg.depelos.errores.ErrorServicio;
import edu.egg.depelos.repositorios.MascotaRepositorio;
import edu.egg.depelos.repositorios.UsuarioRepositorio;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MascotaServicio {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private MascotaRepositorio mascotaRepositorio;
    
    @Autowired
    private FotoServicio fotoServicio;
    
    @Transactional
    public void agregarMascota(MultipartFile archivo, String idUsuario, String nombre, Sexo sexo, Integer edad, Tamanio tamanio, ColorPrimario colorPrimario, ColorSecundario colorSecundario, Raza raza, Estado estado ) throws ErrorServicio{
    
        
        
       validar(nombre, sexo, edad, tamanio, colorPrimario, colorSecundario, raza, estado);
       
        Usuario usuario = usuarioRepositorio.findById(idUsuario).get();
        
        Mascota mascota = new Mascota();
        
      
        
        mascota.setEstado(estado);
        mascota.setNombre(nombre);
        mascota.setSexo(sexo);
        mascota.setEdad(edad);
        mascota.setTamanio(tamanio);
        mascota.setColorPrimario(colorPrimario);
        mascota.setColorSecundario(colorSecundario);
        mascota.setRaza(raza);
        mascota.setAlta(new Date());
        mascota.setUsuario(usuario);
        
        Foto foto = fotoServicio.guardar(archivo);
        mascota.setFoto(foto);
        
        
        
        mascotaRepositorio.save(mascota);
    
        
      
        
    }
    
    @Transactional
    public void modificar(MultipartFile archivo, String idUsuario,String idMascota , String nombre, Sexo sexo, Integer edad, Tamanio tamanio, ColorPrimario colorPrimario, ColorSecundario colorSecundario, Raza raza, Estado estado) throws ErrorServicio{
        validar(nombre, sexo, edad, tamanio, colorPrimario, colorSecundario, raza, estado);
        
        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota);
        if(respuesta.isPresent()){
            Mascota mascota = respuesta.get();
            if(mascota.getUsuario().getId().equals(idUsuario)){
                mascota.setEstado(estado);
                mascota.setNombre(nombre);
                mascota.setSexo(sexo);
                mascota.setEdad(edad);
                mascota.setTamanio(tamanio);
                mascota.setColorPrimario(colorPrimario);
                mascota.setColorSecundario(colorSecundario);
                mascota.setRaza(raza);
                
                String idFoto = null; 
                if(mascota.getFoto() != null){
                    idFoto = mascota.getFoto().getId();
                }
                
                Foto foto = fotoServicio.actualizar(idFoto, archivo);
                mascota.setFoto(foto);
                
                mascotaRepositorio.save(mascota);
            } else {
                throw new ErrorServicio("No tiene permisos suficientes para realizar la operación.");
            }
        } else {
            throw new ErrorServicio("No existe una mascota con el identificador solicitado.");
        }
    }
    
    @Transactional
    public void eliminar(String idUsuario, String idMascota) throws ErrorServicio{
        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota);
        if(respuesta.isPresent()){
            Mascota mascota = respuesta.get();
            if(mascota.getUsuario().getId().equals(idUsuario)){
                mascota.setBaja(new Date());
                mascotaRepositorio.save(mascota);
            }
        } else {
            throw new ErrorServicio("No existe una mascota con el identificador solicitado.");
        }
    }
    
    
    
    public void validar(String nombre, Sexo sexo, Integer edad, Tamanio tamanio, ColorPrimario colorPrimario, ColorSecundario colorSecundario, Raza raza, Estado estado ) throws ErrorServicio{
        if(nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("El nombre de la mascota no puede ser nulo o vacío.");
        }
        
        if(sexo == null){
            throw new ErrorServicio("El sexo de la mascota no puede ser nulo.");
        }
        
        if(edad == null){
            throw new ErrorServicio("La edad de la mascota no puede ser nulo.");
        }
        
        if(tamanio == null){
            throw new ErrorServicio("El tamaño de la mascota no puede ser nulo.");
        }
        
        if(colorPrimario == null){
            throw new ErrorServicio("La color de la mascota no puede ser nulo.");
        }
        
        if(colorSecundario == null){
            throw new ErrorServicio("La color de la mascota no puede ser nulo.");
        }
        
        if(raza == null){
            throw new ErrorServicio("La raza de la mascota no puede ser nulo.");
        }
        
        if(estado == null){
            throw new ErrorServicio("La raza de la mascota no puede ser nulo.");
        }
        
        
    }
    
}
