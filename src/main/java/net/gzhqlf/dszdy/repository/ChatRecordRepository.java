package net.gzhqlf.dszdy.repository;


import net.gzhqlf.dszdy.entity.ChatRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface ChatRecordRepository extends JpaRepository<ChatRecordEntity, Integer> {

    List<ChatRecordEntity> findByFormUidAndToUidAndStatus(Integer formUid, Integer toUid, Integer status);

    List<ChatRecordEntity> findByFormUidAndToUid(Integer formUid, Integer toUid);

    @Transactional
    @Query(value = "select * from chat_record t where " +
            "not exists (select 1 from chat_record where" +
            " ((form_uid=t.form_uid and to_uid=t.to_uid) " +
            "or(form_uid=t.to_uid and to_uid=t.form_uid)) " +
            "and create_time>t.create_time) " +
            "and (form_uid = ?1 or to_uid = ?1) order by create_time desc",
            nativeQuery = true)
    List<ChatRecordEntity> getChatListByUserId(Integer userId);

}
