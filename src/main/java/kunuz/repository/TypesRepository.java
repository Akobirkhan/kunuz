package kunuz.repository;

import kunuz.entity.TypesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypesRepository extends JpaRepository<TypesEntity, Integer> {
//    @Query("delete from TypeEntity where id = ?1")
//    Optional<Boolean> delete(Integer id);
}
