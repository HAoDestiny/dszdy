package net.gzhqlf.dszdy.repository;


import net.gzhqlf.dszdy.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByMobile(String mobile);

    UserEntity findByMobileAndPassword(String mobile, String password);

    UserEntity findByUserProveId(int userProveId);

    UserEntity findByUserInfoId(int userInfoId);

    Page<UserEntity> findByUserType(int userType, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update user set vip_exp = ?2,vip_type = ?3,balance = ?4 where id = ?1", nativeQuery = true)
    void updateUserVip(int userId, Integer vipExp, String vipType, Integer balance);

    @Modifying
    @Transactional
    @Query(value = "update user set balance = balance - 1 where id = ?1", nativeQuery = true)
    void updateUserBalance(int userId);

    @Modifying
    @Transactional
    @Query(value = "update user set flower_total = flower_total - 1 where id = ?1", nativeQuery = true)
    void recallFlowerTotal(Integer userId);

    @Modifying
    @Transactional
    @Query(value = "update user set flower_total = flower_total + 1 where id = ?1", nativeQuery = true)
    void addFlowerTotal(Integer userId);

    @Modifying
    @Transactional
    @Query(value = "update user set flower_total = flower_total + 10 where id = ?1", nativeQuery = true)
    void updateFlowerTotal(Integer userId);

    @Modifying
    @Transactional
    @Query(value = "update user set flower_total = flower_total + ?2 where id = ?1", nativeQuery = true)
    void addFlowerTotal(Integer userId, int flowerTotal);

    @Modifying
    @Transactional
    @Query(value = "update user set flower_total = flower_total + ?2 where id in ?1", nativeQuery = true)
    void addFlowerTotal(List<Integer> userId, int flowerTotal);

    @Transactional
    @Query(value = "select count(*) from user", nativeQuery = true)
    int getCounts();

    @Transactional
    @Query(value = "SELECT count(*) FROM user WHERE register_time > ?1", nativeQuery = true)
    int getNewUserCounts(int timeAfter);
}
