package net.gzhqlf.dszdy.repository;


import net.gzhqlf.dszdy.entity.FileEntity;
import net.gzhqlf.dszdy.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    @Transactional
    @Query(value = "select count(*) from file", nativeQuery = true)
    int getCounts();

}
