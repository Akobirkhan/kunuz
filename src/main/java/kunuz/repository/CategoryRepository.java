package kunuz.repository;

import kunuz.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    @Query("from CategoryEntity order by orderNumber")
    List<CategoryEntity> findAllByOrderNumber();

}
