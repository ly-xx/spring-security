package com.daqsoft.entity;

import javax.persistence.*;
import java.util.List;

/**
 * @author lxx
 */

@Entity
@Table(name = "t_user")
public class SysUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "T_USER_ROLE",
        joinColumns = {@JoinColumn(name = "userid")},
        inverseJoinColumns = {@JoinColumn(name = "roleid")})
    private List<SysRole> roleList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }
}
