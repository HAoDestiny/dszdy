package net.gzhqlf.dszdy.repository;


import net.gzhqlf.dszdy.entity.WeChatAccessTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface WeChatAccessTokenRepository extends JpaRepository<WeChatAccessTokenEntity, Integer> {

}
