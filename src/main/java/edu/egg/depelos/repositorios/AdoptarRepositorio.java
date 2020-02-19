
package edu.egg.depelos.repositorios;

import edu.egg.depelos.entidades.Adoptar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptarRepositorio extends JpaRepository<Adoptar, String>{
    
}
