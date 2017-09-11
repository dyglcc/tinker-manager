package com.dx168.patchserver.core.mapper;

import com.dx168.patchserver.core.domain.ClientsFix;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by tong on 16/10/26.
 */
@Mapper
public interface ClientFixMapper {

    Integer insert(ClientsFix clientsFix);

    @Deprecated
    List<ClientsFix> findClients(Integer patchId, Integer curPage, Integer pageSize);

    List<Object> findClientsAndCount(Integer curPage, Integer pageSize, Integer patchId);


    List<ClientsFix> findClient(Integer patchId, String clientId);

    void deleteById(Integer id);

    void saveBatchClients(List<ClientsFix> list);

    void updateClientPublishSuccessFlag(String deviceId, Integer id);
}
