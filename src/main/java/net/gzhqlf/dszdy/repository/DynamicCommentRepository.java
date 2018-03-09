package net.gzhqlf.dszdy.repository;

import net.gzhqlf.dszdy.entity.DynamicCommentEntity;
import net.gzhqlf.dszdy.entity.DynamicPraiseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface DynamicCommentRepository extends JpaRepository<DynamicCommentEntity, Integer> {

    List<DynamicCommentEntity> findByDynamicId(Integer dynamicId);
}
