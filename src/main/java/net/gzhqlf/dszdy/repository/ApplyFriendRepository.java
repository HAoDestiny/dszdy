package net.gzhqlf.dszdy.repository;


import net.gzhqlf.dszdy.entity.ApplyFriendEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface ApplyFriendRepository extends JpaRepository<ApplyFriendEntity, Integer> {

    Page<ApplyFriendEntity> findByApplyId(Integer applyId, Pageable pageable);

    Page<ApplyFriendEntity> findByApplyIdOrApplyObjectId(Integer applyId, Integer applyObjectId, Pageable pageable);

    ApplyFriendEntity findByApplyIdAndApplyObjectIdAndDeleteTag(Integer applyId, Integer applyObjectId, Integer deleteTag);
}
