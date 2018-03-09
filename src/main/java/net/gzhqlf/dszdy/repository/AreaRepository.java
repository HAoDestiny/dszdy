package net.gzhqlf.dszdy.repository;


import net.gzhqlf.dszdy.entity.AreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface AreaRepository extends JpaRepository<AreaEntity, Integer> {

    List<AreaEntity> findByFather(Integer cityId);

    AreaEntity findByCode(Integer code);

    AreaEntity findByNameLike(String name);
}
