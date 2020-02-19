
package edu.egg.depelos.entidades;

import edu.egg.depelos.enumeraciones.ColorPrimario;
import edu.egg.depelos.enumeraciones.ColorSecundario;
import edu.egg.depelos.enumeraciones.Raza;
import edu.egg.depelos.enumeraciones.Sexo;
import edu.egg.depelos.enumeraciones.Tamanio;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Mascota {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private Integer edad;
    
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date alta;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date baja;
    
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    
    @Enumerated(EnumType.STRING)
    private Tamanio tamanio;
    
    @Enumerated(EnumType.STRING)
    private ColorPrimario colorPrimario;
    
    @Enumerated(EnumType.STRING)
    private ColorSecundario colorSecundario;
    
    @Enumerated(EnumType.STRING)
    private Raza raza;
    
    @OneToOne
    private Foto foto;
    
    @ManyToOne
    private Usuario usuario;
    
    
    

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the alta
     */
    public Date getAlta() {
        return alta;
    }

    /**
     * @param alta the alta to set
     */
    public void setAlta(Date alta) {
        this.alta = alta;
    }

    /**
     * @return the baja
     */
    public Date getBaja() {
        return baja;
    }

    /**
     * @param baja the baja to set
     */
    public void setBaja(Date baja) {
        this.baja = baja;
    }

    /**
     * @return the edad
     */
    public Integer getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    /**
     * @return the sexo
     */
    public Sexo getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the tamanio
     */
    public Tamanio getTamanio() {
        return tamanio;
    }

    /**
     * @param tamanio the tamanio to set
     */
    public void setTamanio(Tamanio tamanio) {
        this.tamanio = tamanio;
    }

    /**
     * @return the colorPrimario
     */
    public ColorPrimario getColorPrimario() {
        return colorPrimario;
    }

    /**
     * @param colorPrimario the colorPrimario to set
     */
    public void setColorPrimario(ColorPrimario colorPrimario) {
        this.colorPrimario = colorPrimario;
    }

    /**
     * @return the colorSecundario
     */
    public ColorSecundario getColorSecundario() {
        return colorSecundario;
    }

    /**
     * @param colorSecundario the colorSecundario to set
     */
    public void setColorSecundario(ColorSecundario colorSecundario) {
        this.colorSecundario = colorSecundario;
    }

    /**
     * @return the raza
     */
    public Raza getRaza() {
        return raza;
    }

    /**
     * @param raza the raza to set
     */
    public void setRaza(Raza raza) {
        this.raza = raza;
    }

    /**
     * @return the foto
     */
    public Foto getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
