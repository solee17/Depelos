package edu.egg.depelos.repositorios;

import edu.egg.depelos.entidades.Mascota;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaRepositorio extends JpaRepository<Mascota, String>{
    
    @Query("SELECT c FROM Mascota c WHERE c.usuario.id = :id")
    public List<Mascota> buscarMascotasPorUsuario(@Param("id") String id);
    
    @Query("SELECT c FROM Mascota c WHERE c.zona.id = :q ORDER BY c.alta DESC ")
    public List<Mascota> buscarMascotasPorZona(@Param("q") String q);
    
    @Query("SELECT c FROM Mascota c WHERE c.estado='ADOPTAR' ")
    public List<Mascota> buscarMascotasPorAdoptar();
    
    @Query("SELECT c FROM Mascota c WHERE c.zona.id = :q AND c.estado ='ADOPTAR' ORDER BY c.alta DESC ")
    public List<Mascota> buscarMascotasPorZonayEstado(@Param("q") String q);
    
    @Query("SELECT c FROM Mascota c WHERE c.id = :q")
    public List<Mascota> buscarMascotasPorID(@Param("q") String q);
   
   @Query("SELECT c FROM Mascota c ORDER BY c.alta DESC")
   public List<Mascota> buscarMascotas();
   
   
}