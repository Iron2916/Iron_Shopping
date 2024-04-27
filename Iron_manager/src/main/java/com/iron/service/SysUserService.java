package com.iron.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iron.model.dto.system.LoginDto;
import com.iron.model.dto.system.SysUserDto;
import com.iron.model.entity.system.SysUser;
import com.iron.model.vo.system.LoginVo;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SysUserService {

    LoginVo selectByUserName(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String token);

   PageInfo<SysUser> findeByPage(int pageNum, int pageSize, SysUserDto sysUserDto);

    void deleteById(int userId);

    void saveUser(SysUser sysUSer);

    void updateSysUSer(SysUser sysUser);
}
