package com.iron.controller;

import com.github.pagehelper.PageInfo;
import com.iron.model.Result.Result;
import com.iron.model.Result.ResultCodeEnum;
import com.iron.model.dto.system.SysUserDto;
import com.iron.model.entity.system.SysUser;
import com.iron.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户接口")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Autowired
    SysUserService sysUserService;
    @Operation(summary = "分页条件查询")
    @GetMapping("/findePage/{pageNum}/{PageSize}")
    public Result findeByPage(
            @PathVariable("pageNum") int pageNum,
            @PathVariable("pageSize") int pageSize,
            @RequestBody SysUserDto sysUserDto) {

        final PageInfo<SysUser> pageInfo = sysUserService.findeByPage(pageNum, pageSize, sysUserDto);

        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除数据")
    @DeleteMapping("/deleteById/{userId}")
    public Result deleteById(@PathVariable("userId") int userId) {

        sysUserService.deleteById(userId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "新增用户")
    @PostMapping("/saveSysUser")
    public Result saveUser(@RequestBody SysUser sysUSer) {

        sysUserService.saveUser(sysUSer);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "更新用户")
    @PutMapping("/updateSysUser")
    public Result updateSysUSer(@RequestBody SysUser sysUser) {

        sysUserService.updateSysUSer(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
