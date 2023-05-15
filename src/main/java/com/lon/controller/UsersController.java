package com.lon.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lon.entity.Users;
import com.lon.common.ResultMsg;
import com.lon.service.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author ctl
 * @date 2022/09/09
 */

@Slf4j
@RestController
    @RequestMapping("/user")
@Api(tags = "用户接口")
public class UsersController {

    @Autowired
    private UsersService userService;

    /**
     * 登录
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public ResultMsg<Users> login(HttpServletRequest request, @RequestBody Users users) {

        String password = users.getPassword();
        // 1.将密码进行md5转换
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2.根据用户查数据库
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getUsername, users.getUsername());
        Users user = userService.getOne(queryWrapper);

        // 3.没有用户则返回失败结果
        if (user == null) {
            return ResultMsg.error("没有该用户,请先注册用户");
        }

        // 4.密码比对
        if (!user.getPassword().equals(password)) {
            return ResultMsg.error("密码错误,请从新输入");
        }

        // 5.查看员工状态
        if (user.getStatus() == 0) {
            return ResultMsg.error("账号已禁用");
        }

        // 6.登录成功
        request.getSession().setAttribute("user", user.getId());
        return ResultMsg.success(user);
    }

    /**
     * 退出
     *
     * @param request 请求
     * @return {@link ResultMsg}<{@link String}>
     */
    @PostMapping("/logout")
    @ApiOperation(value = "退出")
    public ResultMsg<String> logout(HttpServletRequest request) {
        // 清理session中保存的用户id
        request.getSession().removeAttribute("user");
        return ResultMsg.success("退出成功");
    }

    /**
     * 注册
     *
     * @param users
     * @return {@link ResultMsg}<{@link String}>
     */
    @PostMapping("/register")
    @ApiOperation(value = "注册")
    public ResultMsg<String> register(@RequestBody Users users) {
        log.info("新增员工 == > {}", users.toString());
        //进行md5加密
        String password = users.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        users.setPassword(password);
        userService.save(users);
        return ResultMsg.success("新增成功");
    }

    /**
     * 查询用户是否存在
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询用户是否存在")
    public ResultMsg<Users> list(String username) {
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getUsername, username);
        Users emp = userService.getOne(queryWrapper);
        return ResultMsg.success(emp);
    }

    /**
     * 统计住户数量
     */
    @GetMapping("/count")
    @ApiOperation(value = "统计住户个人数量")
    public ResultMsg<Integer> count() {
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getLevel, 2);
        int count = userService.count(queryWrapper);
        if (count != 0) {
            return ResultMsg.success(count);
        } else {
            return ResultMsg.error("没有统计到住户信息");
        }
    }

    /**
     * 新增用户
     *
     * @return {@link ResultMsg}<{@link String}>
     */
    @ApiOperation(value = "新增用户")
    @PostMapping
    public ResultMsg<String> save(HttpServletRequest request, @RequestBody Users users) {
        log.info("新增员工 == > {}", users.toString());
        //设置初始密码,进行md5加密
        users.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        userService.save(users);
        return ResultMsg.success("新增用户成功");
    }

    /**
     * 住户分页查询
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @param name     名字
     * @return {@link ResultMsg}<{@link Page}>
     */
    @GetMapping("/page")
    @ApiOperation(value = "根据分页查找用户信息")
    public ResultMsg<Page> page(int page, int pageSize, String name) {
        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper();
        //添加过滤条件
        wrapper.like(StringUtils.isNotEmpty(name), Users::getName, name);
        //过滤出用户信息
        wrapper.eq(Users::getLevel, 2);
        //添加排序条件
        wrapper.orderByDesc(Users::getUpdateTime);
        //执行查询
        userService.page(pageInfo, wrapper);

        return ResultMsg.success(pageInfo);
    }


    @GetMapping("/pageAdmin")
    @ApiOperation(value = "根据分页查找用户信息")
    public ResultMsg<Page> pageAdmin(int page, int pageSize, String name) {
        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper();
        //添加过滤条件
        wrapper.like(StringUtils.isNotEmpty(name), Users::getName, name);
        //过滤出用户信息
        wrapper.eq(Users::getLevel, 1);
        //添加排序条件
        wrapper.orderByDesc(Users::getUpdateTime);
        //执行查询
        userService.page(pageInfo, wrapper);

        return ResultMsg.success(pageInfo);
    }


    /**
     * 删除用户信息
     */
    @DeleteMapping
    @ApiOperation(value = "删除用户信息")
    public ResultMsg<String> delete(Long id) {
        userService.removeById(id);
        return ResultMsg.success("用户信息删除成功");
    }


    /**
     * 根据id修改用户信息
     *
     * @param users 员工
     * @return {@link ResultMsg}<{@link String}>
     */

    @PutMapping
    @ApiOperation(value = "修改用户信息")
    public ResultMsg<String> update(@RequestBody Users users) {
        users.setUpdateTime(LocalDateTime.now());
        boolean fol = userService.updateById(users);
        if (fol) {
            return ResultMsg.success("用户信息修改成功");
        } else {
            return ResultMsg.error("修改失败");
        }
    }

    @GetMapping("/userInfo")
    @ApiOperation(value = "查询用户数据")
    public ResultMsg<Users> userInfo(Long id){
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper();
        wrapper.eq(Users::getId, id);
        Users userInfo = userService.getOne(wrapper);
        return ResultMsg.success(userInfo);
    }

}
