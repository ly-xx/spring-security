package com.daqsoft.service;

import com.daqsoft.entity.SysOperate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author lxx
 */

@Service
public interface SysOperateService extends JpaRepository<SysOperate, Long>, JpaSpecificationExecutor<SysOperate> {

    @Query("select role.rolename as rolename, op.url as url from SysOperate as op, SysRoleOperate as ro, SysRole as role " +
            " where ro.operateid = op.id and role.id = ro.roleid ")
    List<Map<String, Object>> queryForAuthorization();

}
