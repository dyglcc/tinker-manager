package com.dx168.patchserver.core.mapper;

import com.dx168.patchserver.core.domain.BasicUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by tong on 16/10/26.
 */
@Mapper
public interface UserMapper {
    BasicUser findByUsername(String username);

    BasicUser findById(Integer id);

    Integer insert(BasicUser basicUser);

    List<BasicUser> findAllChildUser(Integer parentId);

    void deleteById(Integer id);
}
