package net.gzhqlf.dszdy.repository;

import net.gzhqlf.dszdy.entity.DynamicEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface DynamicRepository extends JpaRepository<DynamicEntity, Integer> {

    Page<DynamicEntity> findByUserId(Integer userId, Pageable pageable);

    Page<DynamicEntity> findAllByCityCode(Integer cityCode, Pageable pageable);

    Page<DynamicEntity> findAllBySchoolCode(String schoolCode, Pageable pageable);

    Page<DynamicEntity> findByIdIn(List<Integer> id, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update dynamic set comment_total = comment_total + 1 where id = ?1", nativeQuery = true)
    void updateCommentTotal(Integer dynamicId);

}
