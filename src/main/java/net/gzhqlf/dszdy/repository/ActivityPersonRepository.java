package net.gzhqlf.dszdy.repository;


import net.gzhqlf.dszdy.entity.ActivityPersonEntity;
import net.gzhqlf.dszdy.entity.AreaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface ActivityPersonRepository extends JpaRepository<ActivityPersonEntity, Integer> {

    ActivityPersonEntity findByUserIdAndActivityId(Integer userId, Integer activityId);

    List<ActivityPersonEntity> findByUserId(Integer userId);

    Page<ActivityPersonEntity> findByActivityId(Integer activityId, Pageable pageable);

    Page<ActivityPersonEntity> findByActivityIdAndUserSex(Integer activityId, Integer userSex, Pageable pageable);

    List<ActivityPersonEntity> findByActivityId(Integer activityId);
}
