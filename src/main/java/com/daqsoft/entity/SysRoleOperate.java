package com.daqsoft.entity;

import javax.persistence.*;

/**
 * @author lxx
 */

@Entity
@Table(name = "t_role_operate")
public class SysRoleOperate {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "roleid")
    private String roleid;

    @Column(name = "operateid")
    private String operateid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getOperateid() {
        return operateid;
    }

    public void setOperateid(String operateid) {
        this.operateid = operateid;
    }
}
