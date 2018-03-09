package net.gzhqlf.dszdy.repository;


import net.gzhqlf.dszdy.entity.MateLikeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface MateLikeRepository extends JpaRepository<MateLikeEntity, Integer> {

    MateLikeEntity findByUserIdAndLikeId(Integer userId, Integer likeId);

    Page<MateLikeEntity> findByUserIdAndStatus(Integer userId, Integer status, Pageable pageable);
}
