package com.iron.service.impl;


import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iron.RedisKeyHeader;
import com.iron.exception.IronException;
import com.iron.mapper.SysUserMapper;
import com.iron.model.Result.Result;
import com.iron.model.Result.ResultCodeEnum;
import com.iron.model.dto.system.LoginDto;
import com.iron.model.dto.system.SysUserDto;
import com.iron.model.entity.system.SysUser;
import com.iron.model.vo.system.LoginVo;
import com.iron.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public LoginVo selectByUserName(LoginDto loginDto) {

        final SysUser sysUser = sysUserMapper.selectByUserName(loginDto.getUserName());

        if (sysUser == null) {

            throw new IronException(ResultCodeEnum.LOGIN_ERROR);
        }

        String md5InputPassword = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        if (!sysUser.getPassword().equals(md5InputPassword)) {

            throw new IronException(ResultCodeEnum.LOGIN_ERROR);
        }

        // 生成token并将用户信息存入到redis中
        final String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(RedisKeyHeader.userLogin+token, JSON.toJSONString(sysUser), 30, TimeUnit.MINUTES);

        // 构建返回对象
        final LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setRefresh_token("");

        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {

        final SysUser sysUser = JSON.parseObject(redisTemplate.opsForValue().get(RedisKeyHeader.userLogin + token).toString(), SysUser.class);
        return sysUser;
    }

    @Override
    public void logout(String token) {

        redisTemplate.delete(RedisKeyHeader.userLogin+token);
    }

    @Override
    public PageInfo<SysUser> findeByPage(int pageNum, int pageSize, SysUserDto sysUserDto) {

        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> sysuserList = sysUserMapper.findeByPage(sysUserDto);
        final PageInfo<SysUser> pageInfo = new PageInfo<>(sysuserList);

        return pageInfo;
    }

    @Override
    public void deleteById(int userId) {

        sysUserMapper.delteById(userId);
    }

    @Override
    public void saveUser(SysUser sysUSer) {

        sysUserMapper.saveUser(sysUSer);
    }

    @Override
    public void updateSysUSer(SysUser sysUser) {

        sysUserMapper.updateSysUSer(sysUser);
    }

}
