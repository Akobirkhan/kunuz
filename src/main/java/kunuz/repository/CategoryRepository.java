package kunuz.repository;

import kunuz.entity.CategoryEntity;
import kunuz.mapper.CategoryMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    List<CategoryEntity> findAllByVisibleTrueOrderByOrderNumberDesc();

    @Query("from CategoryEntity where visible = true order by orderNumber desc")
    List<CategoryEntity> findAllVisible();

    @Query(value = " select id, " +
            " CASE :lang " +
            "   WHEN 'UZ' THEN name_uz " +
            "   WHEN 'EN' THEN name_en " +
            "   WHEN 'RU' THEN name_ru " +
            "  END as name " +
            "from regions order by order_number desc ; ", nativeQuery = true)
    List<CategoryMapper> findAll(@Param("lang") String lang);
}
