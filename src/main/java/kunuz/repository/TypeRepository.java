package kunuz.repository;

import kunuz.entity.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<TypeEntity, Integer> {
//    @Query("delete from TypeEntity where id = ?1")
//    Optional<Boolean> delete(Integer id);
}
