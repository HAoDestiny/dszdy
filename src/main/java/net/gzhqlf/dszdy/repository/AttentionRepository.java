package net.gzhqlf.dszdy.repository;


import net.gzhqlf.dszdy.entity.AttentionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface AttentionRepository extends JpaRepository<AttentionEntity, Integer> {

    AttentionEntity findByFormAttentionIdAndToAttentionIdAndAttentionStatus(int formAttentionId, int toAttentionId, int attentionStatus);

    List<AttentionEntity> findByToAttentionIdAndAttentionStatus(int toAttentionId, int attentionStatus);

    List<AttentionEntity> findByFormAttentionIdAndAttentionStatus(int formAttentionId, int attentionStatus);

    Page<AttentionEntity> findByFormAttentionIdAndAttentionStatus(int formAttentionId, int attentionStatus, Pageable pageable);

    Page<AttentionEntity> findByToAttentionIdAndAttentionStatus(int toAttentionId, int attentionStatus, Pageable pageable);
}
