package net.gzhqlf.dszdy.repository;


import net.gzhqlf.dszdy.entity.GoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface GoodsRepository extends JpaRepository<GoodsEntity, Integer> {

    List<GoodsEntity> findByDeleteTag(Integer deleteTag);

    List<GoodsEntity> findByGoodsTypeAndDeleteTag(Integer goodsType, Integer deleteTag);

}
