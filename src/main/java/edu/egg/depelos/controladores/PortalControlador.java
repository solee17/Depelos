
package edu.egg.depelos.controladores;

import edu.egg.depelos.errores.ErrorServicio;
import edu.egg.depelos.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/")
    public String index() {
        return "index.html";
    }
    
    @GetMapping("/success")
    public String success() {
        return "success.html";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login.html";
    }
    @GetMapping("/register") 
    public String register() {
        return "register.html";
    }

    @PostMapping("/registred") 
    public String registred(ModelMap model ,@RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail, @RequestParam String telefono, @RequestParam String clave, @RequestParam String idZona, MultipartFile archivo) throws ErrorServicio{
        try {
            usuarioServicio.registrar(archivo, nombre, apellido, mail, telefono, clave, idZona);
        } catch (ErrorServicio ex) {
            model.put("error", ex.getMessage());
            model.put("nombre", nombre);
            return "register.html";
        }
        
        model.put("titulo", "Bienvenido a Depelos!");
        model.put("descripcion", "Tu cuenta ha sido registrada satisfactoriamente");
        model.put("descripcion1", "Que quieres hacer ahora?");
        model.put("descripcion1", "Adoptar/Perdido/Encontrado/Ir al inicio");
        return "success.html";
    }
}