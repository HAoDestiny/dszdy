package net.gzhqlf.dszdy.repository;


import net.gzhqlf.dszdy.entity.UserPopularEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface UserPopularRepository extends JpaRepository<UserPopularEntity, Integer> {

    @Modifying
    @Transactional
    @Query(value = "update user_popular set to_attention_total = to_attention_total + 1 where id = ?1", nativeQuery = true)
    void addToAttentionTotal(Integer userPopular);

    @Modifying
    @Transactional
    @Query(value = "update user_popular set to_attention_total = to_attention_total - 1 where id = ?1", nativeQuery = true)
    void recallToAttentionTotal(Integer userPopular);

    @Modifying
    @Transactional
    @Query(value = "update user_popular set form_attention_total = form_attention_total + 1 where id = ?1", nativeQuery = true)
    void addFormAttentionTotal(Integer userPopular);

    @Modifying
    @Transactional
    @Query(value = "update user_popular set form_attention_total = form_attention_total - 1 where id = ?1", nativeQuery = true)
    void recallFormAttentionTotal(Integer userPopular);
}
