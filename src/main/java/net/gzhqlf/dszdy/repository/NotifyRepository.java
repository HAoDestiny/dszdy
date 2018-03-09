package net.gzhqlf.dszdy.repository;


import net.gzhqlf.dszdy.entity.NotifyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface NotifyRepository extends JpaRepository<NotifyEntity, Integer> {

    @Transactional
    @Query(value = "select * from notify where notify_to_user_id = ?1 and notify_status != ?2 order by create_time desc", nativeQuery = true)
    List<NotifyEntity> findByNotifyToUserIdAndNotifyStatusNot(Integer notifyToUserId, Integer notifyStatus);

    @Transactional
    @Query(value = "select * from notify where notify_to_user_id = ?1 and notify_status != ?2 and notify_type = ?3 order by create_time desc", nativeQuery = true)
    List<NotifyEntity> findByNotifyToUserIdAndNotifyStatusNotAndNotifyType(Integer notifyToUserId, Integer notifyStatus, Integer notifyType);

    @Modifying
    @Transactional
    @Query(value = "update notify set notify_status = ?1 where id = ?2", nativeQuery = true)
    void updateNotifyStatus(Integer notifyStatus ,Integer notifyId);

    @Modifying
    @Transactional
    @Query(value = "update notify set notify_status = ?1 where notify_to_user_id =?2 and notify_type = ?3", nativeQuery = true)
    void updateNotifyStatus(Integer notifyStatus ,Integer userId, Integer notifyType);

    @Modifying
    @Transactional
    @Query(value = "update notify set notify_status = 1 where notify_to_user_id = ?1 and notify_status = 0", nativeQuery = true)
    void clearUserNotifies(Integer userId);

}
