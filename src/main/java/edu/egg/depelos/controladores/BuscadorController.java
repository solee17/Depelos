/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import edu.egg.depelos.repositorios.FotoRepositorio;
import edu.egg.depelos.repositorios.MascotaRepositorio;
import edu.egg.depelos.repositorios.ZonaRepositorio;
import edu.egg.depelos.servicios.MascotaServicio;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Toms
 */
@Controller
@RequestMapping("/buscar")
public class BuscadorController {

    @Autowired
    private MascotaRepositorio mascotaRepositorio;
    
    @Autowired
    private MascotaServicio mascotaServicio;
    
    @Autowired
    private ZonaRepositorio zonaRepositorio;
    
    @Autowired
    private FotoRepositorio fotoRepositorio;
    
    
    
    @GetMapping("/AdoptadosporZona1")
    public String listado1(@RequestParam(required = false) String q, ModelMap modelo, String idZona) {
        
        System.out.println("ENTRE AL CONTROLADOR ---------------------------");
        List<Mascota> mascotas;
        List<Zona> zonas=zonaRepositorio.findAll();
        modelo.put("zonas", zonas);
        if (idZona != null) {
            q=idZona;
            mascotas=mascotaRepositorio.buscarMascotasPorZonayEstado(q);
              Zona zona = zonaRepositorio.getOne(idZona);
        modelo.put("error", zona.getNombre());
        } else {
            mascotas=mascotaRepositorio.buscarMascotasPorAdoptar();
        }
       

        modelo.put("q", q);
        modelo.put("mascotas", mascotas);
        return "adopcion.html";
    }
    @GetMapping("/foto/{idFoto}")
    public ResponseEntity<byte[]>foto(@PathVariable("idFoto")String idFoto){
        byte[]foto=fotoRepositorio.findById(idFoto).get().getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(foto,headers,HttpStatus.OK);
    }
    
    @GetMapping("/idMascota")
    public String buscarMascotaporid(@RequestParam(required = false) String q, ModelMap modelo){
        modelo.put("titulo", "Bienvenido nuevamente");
        Mascota mascota = mascotaRepositorio.getOne(q);
        System.out.println("--------///////-----------////////-7777777--------///////-----------////////-7777777");
        modelo.put("mascotas", mascota);
        return "exitoregistro.html";
    }
    
    
    }