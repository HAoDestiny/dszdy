package net.gzhqlf.dszdy.repository;


import net.gzhqlf.dszdy.entity.UserInfoEntity;
import net.gzhqlf.dszdy.entity.UserInfoOtherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by DESTINY on 2017/9/22.
 */

@Repository
public interface UserInfoOtherRepository extends JpaRepository<UserInfoOtherEntity, Integer> {

}
