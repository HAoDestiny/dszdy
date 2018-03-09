package net.gzhqlf.dszdy.repository;



import net.gzhqlf.dszdy.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Integer> {

    List<CityEntity> findByFather(Integer provinceId);

    CityEntity findByCode(Integer code);

    CityEntity findByNameLike(String name);
}
