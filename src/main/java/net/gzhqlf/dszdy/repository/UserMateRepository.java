package net.gzhqlf.dszdy.repository;


import net.gzhqlf.dszdy.entity.UserMateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface UserMateRepository extends JpaRepository<UserMateEntity, Integer> {

}
