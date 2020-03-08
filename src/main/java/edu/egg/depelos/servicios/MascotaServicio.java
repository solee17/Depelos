package edu.egg.depelos.servicios;

import edu.egg.depelos.entidades.Foto;
import edu.egg.depelos.entidades.Mascota;
import edu.egg.depelos.entidades.Usuario;
import edu.egg.depelos.entidades.Zona;
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
import edu.egg.depelos.repositorios.ZonaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private ZonaRepositorio zonaRepositorio;

    @Transactional
    public void agregarMascota(MultipartFile archivo, String idUsuario, String nombre, String sexo, Integer edad, String tamanio, String colorPrimario, String colorSecundario, String raza, String estado, String idZona, String mail) throws ErrorServicio {

        Sexo sexoenum;
        switch (sexo) {
            case "MACHO":
                sexoenum = Sexo.MACHO;
                break;
            case "HEMBRA":
                sexoenum = Sexo.HEMBRA;
                break;
            default:
                throw new AssertionError();
        }
        Tamanio tamanioenum;
        switch (tamanio) {
            case "PEQUEÑO":
                tamanioenum = Tamanio.PEQUEÑO;
                break;
            case "MEDIANO":
                tamanioenum = Tamanio.MEDIANO;
                break;
            case "GRANDE":
                tamanioenum = Tamanio.GRANDE;
                break;
            default:
                throw new AssertionError();
        }
        ColorPrimario colorPrimenum;
        switch (colorPrimario) {
            case "BLANCO":
                colorPrimenum = ColorPrimario.BLANCO;
                break;
            case "GRIS":
                colorPrimenum = ColorPrimario.GRIS;
                break;
            case "MARRON":
                colorPrimenum = ColorPrimario.MARRON;
                break;
            case "NEGRO":
                colorPrimenum = ColorPrimario.NEGRO;
                break;
            default:
                throw new AssertionError();
        }
        ColorSecundario colorSecenum;
        switch (colorSecundario) {
            case "BLANCO":
                colorSecenum = ColorSecundario.BLANCO;
                break;
            case "GRIS":
                colorSecenum = ColorSecundario.GRIS;
                break;
            case "MARRON":
                colorSecenum = ColorSecundario.MARRON;
                break;
            case "NEGRO":
                colorSecenum = ColorSecundario.NEGRO;
                break;
            default:
                throw new AssertionError();
        }

        Raza razaenum;
        switch (raza) {
            case "BEAGLE":
                razaenum = Raza.BEAGLE;
                break;
            case "BOXER":
                razaenum = Raza.BOXER;
                break;
            case "BULLDOG":
                razaenum = Raza.BULLDOG;
                break;
            case "CANICHE":
                razaenum = Raza.CANICHE;
                break;
            case "CHIHUAHUA":
                razaenum = Raza.CHIHUAHUA;
                break;
            case "DOBERMAN":
                razaenum = Raza.DOBERMAN;
                break;
            case "GALGO":
                razaenum = Raza.GALGO;
                break;
            case "GOLDENRETRIEVER":
                razaenum = Raza.GOLDENRETRIEVER;
                break;
            case "LABRADOR":
                razaenum = Raza.LABRADOR;
                break;
            case "OTRA":
                razaenum = Raza.OTRA;
                break;
            case "PASTORALEMAN":
                razaenum = Raza.PASTORALEMAN;
                break;
            case "PITBULL":
                razaenum = Raza.PITBULL;
                break;
            case "PUG":
                razaenum = Raza.PUG;
                break;
            case "ROTTWEILER":
                razaenum = Raza.ROTTWEILER;
                break;
            default:
                throw new AssertionError();
        }

        Estado estadoenum;

        switch (estado) {
            case "ADOPTAR":
                estadoenum = Estado.ADOPTAR;
                break;
            case "ENCONTRADO":
                estadoenum = Estado.ENCONTRADO;
                break;
            case "PERDIDO":
                estadoenum = Estado.PERDIDO;
                break;
            default:
                throw new AssertionError();
        }
        if (!usuarioServicio.validarMail(mail)) {
            
        Zona zona = zonaRepositorio.getOne(idZona);

        Usuario usuario = usuarioRepositorio.buscarPorMail(mail);
        validar(nombre, sexoenum, edad, tamanioenum, colorPrimenum, colorSecenum, razaenum, estadoenum, usuario);

        Mascota mascota = new Mascota();

        mascota.setEstado(estadoenum);
        mascota.setNombre(nombre);
        mascota.setSexo(sexoenum);
        mascota.setEdad(edad);
        mascota.setTamanio(tamanioenum);
        mascota.setColorPrimario(colorPrimenum);
        mascota.setColorSecundario(colorSecenum);
        mascota.setRaza(razaenum);
        mascota.setAlta(new Date());
        mascota.setUsuario(usuario);
        mascota.setZona(zona);

        Foto foto = fotoServicio.guardar(archivo);
        mascota.setFoto(foto);

        mascotaRepositorio.save(mascota);
        }else{
            throw new ErrorServicio("No hay un usuario con ese mail.");
        }

    }

    @Transactional
    public void modificar(MultipartFile archivo, String idUsuario, String idMascota, String nombre, Sexo sexo, Integer edad, Tamanio tamanio, ColorPrimario colorPrimario, ColorSecundario colorSecundario, Raza raza, Estado estado) throws ErrorServicio {

        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota);
        if (respuesta.isPresent()) {
            Mascota mascota = respuesta.get();
            if (mascota.getUsuario().getId().equals(idUsuario)) {
                mascota.setEstado(estado);
                mascota.setNombre(nombre);
                mascota.setSexo(sexo);
                mascota.setEdad(edad);
                mascota.setTamanio(tamanio);
                mascota.setColorPrimario(colorPrimario);
                mascota.setColorSecundario(colorSecundario);
                mascota.setRaza(raza);

                String idFoto = null;
                if (mascota.getFoto() != null) {
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
    public void eliminar(String idUsuario, String idMascota) throws ErrorServicio {
        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota);
        if (respuesta.isPresent()) {
            Mascota mascota = respuesta.get();
            if (mascota.getUsuario().getId().equals(idUsuario)) {
                mascota.setBaja(new Date());
                mascotaRepositorio.save(mascota);
            }
        } else {
            throw new ErrorServicio("No existe una mascota con el identificador solicitado.");
        }
    }

    public List<String> listaEstado() {
        List<String> estados = new ArrayList();
        estados.add("PERDIDO");
        estados.add("ENCONTRADO");
        estados.add("ADOPTAR");
        return estados;
    }

    public List<String> listaSexo() {
        List<String> sexos = new ArrayList();
        sexos.add("MACHO");
        sexos.add("HEMBRA");

        return sexos;
    }

    public List<String> listaTamanios() {
        List<String> tamanios = new ArrayList();
        tamanios.add("PEQUEÑO");
        tamanios.add("MEDIANO");
        tamanios.add("GRANDE");
        return tamanios;
    }

    public List<String> listaColoresPrimarios() {
        List<String> colorPrimarios = new ArrayList();//BLANCO, NEGRO, MARRON, GRIS;
        colorPrimarios.add("BLANCO");
        colorPrimarios.add("NEGRO");
        colorPrimarios.add("MARRON");
        colorPrimarios.add("GRIS");
        return colorPrimarios;
    }

    public List<String> listaColoresSecundarios() {
        List<String> colorSecundarios = new ArrayList();//BLANCO, NEGRO, MARRON, GRIS, NINGUNO
        colorSecundarios.add("BLANCO");
        colorSecundarios.add("NEGRO");
        colorSecundarios.add("MARRON");
        colorSecundarios.add("GRIS");
        colorSecundarios.add("NINGUNO");
        return colorSecundarios;
    }

    public List<String> listaRaza() {
        List<String> razas = new ArrayList();//LABRADOR, BULLDOG, PUG, GOLDENRETRIEVER, PASTORALEMAN, PITBULL, CHIHUAHUA, BEAGLE, ROTTWEILER, BOXER, DOBERMAN, GALGO, CANICHE, OTRA; 
        razas.add("LABRADOR");
        razas.add("BULLDOG");
        razas.add("PUG");
        razas.add("GOLDENRETRIEVER");
        razas.add("PASTORALEMAN");
        razas.add("PITBULL");
        razas.add("CHIHUAHUA");
        razas.add("BEAGLE");
        razas.add("ROTTWEILER");
        razas.add("BOXER");
        razas.add("DOBERMAN");
        razas.add("GALGO");
        razas.add("CANICHE");
        razas.add("OTRA");
        return razas;
    }

    public void validar(String nombre, Sexo sexo, Integer edad, Tamanio tamanio, ColorPrimario colorPrimario, ColorSecundario colorSecundario, Raza raza, Estado estado, Usuario usuario) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre de la mascota no puede ser nulo o vacío.");
        }

        if (usuario == null) {
            throw new ErrorServicio("El no corresponde a un abonado en servicio.");

        }

        if (sexo == null) {
            throw new ErrorServicio("El sexo de la mascota no puede ser nulo.");
        }

        if (edad == null) {
            throw new ErrorServicio("La edad de la mascota no puede ser nulo.");
        }

        if (tamanio == null) {
            throw new ErrorServicio("El tamaño de la mascota no puede ser nulo.");
        }

        if (colorPrimario == null) {
            throw new ErrorServicio("La color de la mascota no puede ser nulo.");
        }

        if (colorSecundario == null) {
            throw new ErrorServicio("La color de la mascota no puede ser nulo.");
        }

        if (raza == null) {
            throw new ErrorServicio("La raza de la mascota no puede ser nulo.");
        }

        if (estado == null) {
            throw new ErrorServicio("La raza de la mascota no puede ser nulo.");
        }

    }

}
