package com.iron.service;

import com.github.pagehelper.PageInfo;
import com.iron.model.dto.system.SysRoleDto;
import com.iron.model.entity.system.SysRole;

import java.util.List;
import java.util.Map;

public interface SysRoleService {
    public abstract PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize);

    void deleteById(Integer id);

    void updateSystemRole(SysRole sysRole);

    void addSystemRole(SysRole sysRole);

    Map<String, List<SysRole>> getAllRoles();

    List<SysRole> getUserRoles(int userId);
}