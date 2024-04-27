package com.iron.mapper;

import com.iron.model.dto.system.SysUserDto;
import com.iron.model.entity.system.SysUser;
import com.iron.model.vo.system.LoginVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {

    SysUser selectByUserName(String userName);

    List<SysUser> findeByPage(SysUserDto sysUserDto);

    void delteById(int userId);

    void saveUser(SysUser sysUSer);

    void updateSysUSer(SysUser sysUser);
}
