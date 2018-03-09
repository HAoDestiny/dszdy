package net.gzhqlf.dszdy.repository;

import net.gzhqlf.dszdy.entity.WeChatTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by DESTINY on 2017/9/22.
 */

@Repository
public interface WeChatTokenRepository extends JpaRepository<WeChatTokenEntity, Integer> {

    WeChatTokenEntity findByOpenId(String openId);

    WeChatTokenEntity findByUserId(Integer userId);

}
