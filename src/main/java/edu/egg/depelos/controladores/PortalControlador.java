package edu.egg.depelos.controladores;

import edu.egg.depelos.entidades.Mascota;
import edu.egg.depelos.entidades.Zona;
import edu.egg.depelos.enumeraciones.ColorPrimario;
import edu.egg.depelos.enumeraciones.ColorSecundario;
import edu.egg.depelos.enumeraciones.Estado;
import edu.egg.depelos.enumeraciones.Raza;
import edu.egg.depelos.enumeraciones.Sexo;
import edu.egg.depelos.enumeraciones.Tamanio;
import edu.egg.depelos.errores.ErrorServicio;
import edu.egg.depelos.repositorios.MascotaRepositorio;
import edu.egg.depelos.repositorios.ZonaRepositorio;
import edu.egg.depelos.servicios.MascotaServicio;
import edu.egg.depelos.servicios.UsuarioServicio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ZonaRepositorio zonaRepositorio;

    @Autowired
    private MascotaServicio mascotaServicio;
    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/buscar")
    public String buscar() {
        return "perdidos.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/success")
    public String success(ModelMap modelo) {
        modelo.put("titulo", "Bienvenido nuevamente");
        List<Mascota> mascotas;
        mascotas=mascotaRepositorio.buscarMascotas();
        modelo.put("mascotas", mascotas);
        return "exitoregistro.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/adopcion")
    public String adopcion(ModelMap modelo) {
        
        return "adopcion.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/donacion")
    public String donacion(ModelMap modelo) {
        
        return "donacion.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/encontrados")
    public String encontrados(ModelMap modelo) {
      
        return "encontrados.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/perdidos")
    public String perdidos(ModelMap modelo) {
        
        return "perdidos.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/transito")
    public String transito(ModelMap modelo) {
        
        return "transito.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
        if (error != null) {
            model.put("error", "Nombre de usuario o contraseña incorrectos");
        }

        if (logout != null) {
            model.put("logout", "Has cerrado sesion correctamente");
        }
        return "login.html";
    }
    
    @GetMapping("/recuperarconstraseña")
    public String olvido(){
        return "olvidocontraseña.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/terminosycondiciones")
    public String tyc(ModelMap modelo) {
        
        return "terminosycondiciones.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/quienessomos")
    public String qs(ModelMap modelo) {
        
        return "quienessomos.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/perfilm")
    public String perfilm(ModelMap modelo) {
        
        return "perfil.html";
    }
    
    
    /**
     *
     * @param modelo
     * @return
     */
    @GetMapping("/registro")
    public String register(ModelMap modelo) {
        List<Zona> zonas = zonaRepositorio.findAll();
        modelo.put("zonas", zonas);

        return "registro.html";
    }


    @PostMapping("/registrarusuario")
    public String registred(ModelMap model, MultipartFile archivo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail, @RequestParam String telefono, @RequestParam String clave1, @RequestParam String clave2, @RequestParam String idZona) {
        try {
            usuarioServicio.registrar(archivo, nombre, apellido, mail, telefono, clave1, clave2, idZona);
        } catch (ErrorServicio ex) {
            List<Zona> zonas = zonaRepositorio.findAll();
            model.put("zonas", zonas);
            model.put("error", ex.getMessage());
            model.put("nombre", nombre);
            model.put("apellido", apellido);
            model.put("mail", mail);
            model.put("telefono", telefono);
            model.put("clave1", clave1);
            model.put("clave2", clave2);
            return "registro.html";
        }

        model.put("titulo", "Bienvenido a DePelos!");
        List<Mascota> mascotas;
        mascotas=mascotaRepositorio.buscarMascotas();
        model.put("mascotas", mascotas);
        return "exitoregistro.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/registrom")
    public String register1(ModelMap model) {
        List<Zona> zonas = zonaRepositorio.findAll();
        model.put("zonas", zonas);
        List<String> estados = mascotaServicio.listaEstado();
        model.put("estados", estados);
        List<String> sexos = mascotaServicio.listaSexo();
        model.put("sexos", sexos);
        List<String> tamanios = mascotaServicio.listaTamanios();
        model.put("tamanios", tamanios);
        List<String> colorPrimarios = mascotaServicio.listaColoresPrimarios();
        model.put("colorPrimarios", colorPrimarios);
        List<String> colorSecundarios = mascotaServicio.listaColoresSecundarios();
        model.put("colorSecundarios", colorSecundarios);
        List<String> razas = mascotaServicio.listaRaza();
        model.put("razas", razas);
        
        
        return "registrarmascota.html";
    }
@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/registrarmascota")
    public String registredmas(@RequestParam(required = false) String q, ModelMap modelo, ModelMap model, MultipartFile archivo, @RequestParam String nombre, @RequestParam String sexo, @RequestParam String estado, @RequestParam Integer edad, @RequestParam String tamanio, @RequestParam String colorPrimario, @RequestParam String colorSecundario, @RequestParam String raza, @RequestParam String idZona, @RequestParam String mail) {
        System.out.println("-----------ENTRAR CONTROLADOR-----------");
        try {
            
            mascotaServicio.agregarMascota(archivo, mail , nombre, sexo, edad, tamanio, colorPrimario, colorSecundario, raza, estado, idZona, mail);

        } catch (ErrorServicio ex) {

            model.put("error", ex.getMessage());
            model.put("nombre", nombre);
            model.put("mail", mail);
            model.put("edad", edad);
            List<String> estados = mascotaServicio.listaEstado();
            model.put("estados", estados);
            List<String> sexos = mascotaServicio.listaSexo();
            model.put("sexos", sexos);
            List<String> tamanios = mascotaServicio.listaTamanios();
            model.put("tamanios", tamanios);
            List<String> colorPrimarios = mascotaServicio.listaColoresPrimarios();
            model.put("colorPrimarios", colorPrimarios);
            List<String> colorSecundarios = mascotaServicio.listaColoresSecundarios();
            model.put("colorSecundarios", colorSecundarios);
            List<String> razas = mascotaServicio.listaRaza();
            model.put("razas", razas);
            List<Zona> zonas = zonaRepositorio.findAll();
            model.put("zonas", zonas);

            return "registrarmascota.html";
        }
        modelo.put("titulo", "Bienvenido nuevamente");
        List<Mascota> mascotas;
        mascotas=mascotaRepositorio.buscarMascotas();
        modelo.put("mascotas", mascotas);
        return "redirect:/success";
    }
}