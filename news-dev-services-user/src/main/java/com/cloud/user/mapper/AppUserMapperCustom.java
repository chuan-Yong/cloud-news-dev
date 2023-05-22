package com.cloud.user.mapper;

import com.cloud.vo.PublisherVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 16:10 2023/5/22
 * @Modified by:ycy
 */
@Repository
public interface AppUserMapperCustom {

    List<PublisherVO> getUserList(Map<String, Object> map);

}
