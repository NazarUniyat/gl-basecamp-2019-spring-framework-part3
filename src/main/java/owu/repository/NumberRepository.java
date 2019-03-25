package owu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import owu.domains.NumberModel;



@Repository
public interface NumberRepository extends JpaRepository<NumberModel, Integer> {

}
