package com.iron.controller;

import com.github.pagehelper.PageInfo;
import com.iron.model.Result.Result;
import com.iron.model.Result.ResultCodeEnum;
import com.iron.model.dto.system.SysRoleDto;
import com.iron.model.entity.system.SysRole;
import com.iron.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService ;

    /**
     * 根据PageHelper实现分页查询和模糊查询
     * @param sysRoleDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/findByPage/{pageNum}/{pageSize}")
    public Result<PageInfo<SysRole>> findByPage(SysRoleDto sysRoleDto ,
                                                @PathVariable(value = "pageNum") Integer pageNum ,
                                                @PathVariable(value = "pageSize") Integer pageSize) {
        PageInfo<SysRole> pageInfo = sysRoleService.findByPage(sysRoleDto , pageNum , pageSize) ;
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }


    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable(value = "id") Integer id) {

        sysRoleService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/updateSystemRdeole")
    public Result updateSystemRole(@RequestBody SysRole sysRole) {

        sysRoleService.updateSystemRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/addSystemRole")
    public Result addSystenRole(@RequestBody SysRole sysRole) {

        sysRoleService.addSystemRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/getAllRoles")
    public Result getAllRoles() {

        Map<String, List<SysRole>> data = sysRoleService.getAllRoles();
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/getUserRoles/{userId}")
    public Result getUserRoles(@PathVariable("userId") int userId) {

        List<SysRole> roles = sysRoleService.getUserRoles(userId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}