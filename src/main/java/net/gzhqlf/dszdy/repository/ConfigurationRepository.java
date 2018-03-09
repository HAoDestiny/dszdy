package net.gzhqlf.dszdy.repository;


import net.gzhqlf.dszdy.entity.ConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface ConfigurationRepository extends JpaRepository<ConfigurationEntity, Integer> {

    List<ConfigurationEntity> findByType(Integer type);

    ConfigurationEntity findByCodeAndType(Integer code, Integer Type);
}
