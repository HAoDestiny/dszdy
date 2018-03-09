package net.gzhqlf.dszdy.repository;


import net.gzhqlf.dszdy.entity.UserInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by DESTINY on 2017/9/22.
 */

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Integer> {

    Page<UserInfoEntity> findByNickname(String nickname, Pageable pageable);

    List<UserInfoEntity> findByNickname(String nickname);
}
