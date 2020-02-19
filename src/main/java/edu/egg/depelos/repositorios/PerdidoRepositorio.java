
package edu.egg.depelos.repositorios;

import edu.egg.depelos.entidades.Perdido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerdidoRepositorio extends JpaRepository<Perdido, String>{
    
}
