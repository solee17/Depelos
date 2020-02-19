
package edu.egg.depelos.repositorios;

import edu.egg.depelos.entidades.Encontrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface EncontradoRepositorio extends JpaRepository<Encontrado, String>{
    
}
