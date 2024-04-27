package com.iron.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iron.mapper.SysRoleMapper;
import com.iron.model.dto.system.SysRoleDto;
import com.iron.model.entity.system.SysRole;
import com.iron.service.SysRoleService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 接口实现类
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper ;

    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum , pageSize) ;
        List<SysRole> sysRoleList = sysRoleMapper.findByPage(sysRoleDto) ;
        PageInfo<SysRole> pageInfo = new PageInfo(sysRoleList) ;
        return pageInfo;
    }

    @Override
    public void deleteById(Integer id) {

        sysRoleMapper.deleteById(id);
    }

    @Override
    public void updateSystemRole(SysRole sysRole) {

        sysRoleMapper.updateSystemRole(sysRole);
    }

    @Override
    public void addSystemRole(SysRole sysRole) {

        sysRoleMapper.addSystemRole(sysRole);
    }

    @Override
    public Map<String, List<SysRole>> getAllRoles() {

        Map<String, List<SysRole>> map = new HashMap<>();
        List<SysRole> roles = sysRoleMapper.getAllRoles();
        map.put("allRolesList", roles);
        return map;
    }

    @Override
    public List<SysRole> getUserRoles(int userId) {

        List<SysRole> roles = sysRoleMapper.getUserRoles(userId);
        return roles;
    }
}