package com.daqsoft.service;

import com.daqsoft.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author lxx
 */
@Service
public interface UserService extends JpaRepository<SysUser, Long> , JpaSpecificationExecutor<SysUser> {

    SysUser findByUsernameAndPassword(String username, String password);

    SysUser findByUsername(String username);
}
