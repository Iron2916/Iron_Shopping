package com.iron.mapper;

import com.iron.model.dto.system.SysRoleDto;
import com.iron.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    public abstract List<SysRole> findByPage(SysRoleDto sysRoleDto);

    void deleteById(Integer id);

    void updateSystemRole(SysRole sysRole);

    void addSystemRole(SysRole sysRole);

    List<SysRole> getAllRoles();

    List<SysRole> getUserRoles(int userId);
}