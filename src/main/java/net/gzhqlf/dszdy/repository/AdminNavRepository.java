package net.gzhqlf.dszdy.repository;


import net.gzhqlf.dszdy.entity.AdminNavEntity;
import net.gzhqlf.dszdy.entity.AreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface AdminNavRepository extends JpaRepository<AdminNavEntity, Integer> {

}
