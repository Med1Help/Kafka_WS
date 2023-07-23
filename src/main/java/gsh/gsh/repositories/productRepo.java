package gsh.gsh.repositories;

import gsh.gsh.models.product;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Transactional
public interface productRepo extends CrudRepository<product,String> {
  Optional<product> findById(String id);
}
