package net.gzhqlf.dszdy.repository;


import net.gzhqlf.dszdy.entity.UserProveEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface UserProveRepository extends JpaRepository<UserProveEntity, Integer> {

    Page<UserProveEntity> findByIdentityStatus(Integer identityStatus, Pageable pageable);

    Page<UserProveEntity> findByEducationStatus(Integer educationStatus, Pageable pageable);

    Page<UserProveEntity> findByCarStatus(Integer carStatus, Pageable pageable);

    Page<UserProveEntity> findByHouseStatus(Integer houseStatus, Pageable pageable);

    @Transactional
    @Query(value = "SELECT count(*) FROM user_prove WHERE " +
            "identity_status = 2 OR education_status = 2 OR car_status = 2 OR house_status = 2", nativeQuery = true)
    int getProveCounts();
}
