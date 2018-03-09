package net.gzhqlf.dszdy.repository;


import net.gzhqlf.dszdy.entity.CityActivityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface CityActivityRepository extends JpaRepository<CityActivityEntity, Integer> {

    Page<CityActivityEntity> findByActivityStatusAndDeleteTag(int activityStatus, int deleteTag, Pageable pageable);

    @Transactional
    @Query(value = "select count(*) from city_activity", nativeQuery = true)
    int getCounts();
}
