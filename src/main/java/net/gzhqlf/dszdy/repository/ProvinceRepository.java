package net.gzhqlf.dszdy.repository;

import net.gzhqlf.dszdy.entity.ProvinceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface ProvinceRepository extends JpaRepository<ProvinceEntity, Integer> {

    ProvinceEntity findByCode(Integer code);

    ProvinceEntity findByNameLike(String name);
}
