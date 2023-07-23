package gsh.gsh.repositories;

import gsh.gsh.models.orderResp;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepo extends CrudRepository<orderResp,String> {
}
