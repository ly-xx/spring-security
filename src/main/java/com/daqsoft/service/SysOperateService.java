//package com.daqsoft.service;
//
//import com.daqsoft.entity.SysOperate;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * @author lxx
// */
//
//@Service
//public interface SysOperateService extends JpaRepository<SysOperate, Long>, JpaSpecificationExecutor<SysOperate> {
//
//    @Query("select op.url, role.rolename from t_role as role left join t_role_operate as ro " +
//            " on role.id = ro.roleid left join t_operate as op on ro.operateid = op.id ")
//    List<Map<String, Object>> queryForAuthorization();
//
//}
