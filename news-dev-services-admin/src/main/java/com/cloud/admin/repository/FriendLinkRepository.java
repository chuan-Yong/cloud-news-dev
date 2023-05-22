package com.cloud.admin.repository;

import com.cloud.mo.FriendLinkMO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 12:17 2023/5/22
 * @Modified by:ycy
 */
public interface FriendLinkRepository extends MongoRepository<FriendLinkMO,String> {

    List<FriendLinkMO> getAllByIsDelete(Integer isDelete);
}
