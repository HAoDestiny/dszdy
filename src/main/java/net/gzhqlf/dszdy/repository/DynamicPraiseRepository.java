package net.gzhqlf.dszdy.repository;

import net.gzhqlf.dszdy.entity.DynamicPraiseEntity;
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
public interface DynamicPraiseRepository extends JpaRepository<DynamicPraiseEntity, Integer> {

    List<DynamicPraiseEntity> findByDynamicId(Integer dynamicId);

    DynamicPraiseEntity findByDynamicIdAndPraisePersonId(Integer dynamicId, Integer praisePersonId);

    @Modifying
    @Transactional
    @Query(value = "update dynamic set praise_total = praise_total + 1 where id = ?1", nativeQuery = true)
    void updatePraiseTotal(Integer dynamicId);
}
