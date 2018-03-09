package net.gzhqlf.dszdy.repository;


import net.gzhqlf.dszdy.entity.FriendsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DESTINY on 2017/9/15.
 */

@Repository
public interface FriendsRepository extends JpaRepository<FriendsEntity, Integer>, JpaSpecificationExecutor<FriendsEntity> {

    FriendsEntity findByUserIdAndFriendIdAndDeleteTag(Integer userId, Integer friendId, Integer deleteTag);

    FriendsEntity findByUserIdAndFriendId(Integer userId, Integer friendId);

    FriendsEntity findByUserIdOrFriendId(Integer userId, Integer friendId);

    List<FriendsEntity> findByUserIdOrFriendIdAndDeleteTag(Integer userId, Integer friendId, Integer deleteTag);

}
