package com.lening.controller;

import com.lening.entity.MeunBean;
import com.lening.entity.UserBean;
import com.lening.service.UserService;
import com.lening.utils.Page;
import com.lening.utils.ResultInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/getUserLists")
    public List<UserBean> getUserLists(){
        return userService.getUserList();
    }


    @RequestMapping("/getUserConn")
    public Page<UserBean> getUserConn(@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "3")Integer pageSize){
        UserBean userBean = new UserBean();
        userBean.setUname("张");
        userBean.setAge(12);
        userBean.setEage(20);
        return userService.getUsetConn(userBean, pageNum, pageSize);
    }

    @RequestMapping("/getLogin")
    public ResultInfo getLogin(@RequestBody UserBean ub, HttpServletRequest request){
        UserBean userBean = userService.getLogin(ub);

        if(userBean==null){
            return new ResultInfo(false,"登录失败，账户或者密码错误");
        }else{
            request.getSession().setAttribute("ub", userBean);
            return new ResultInfo(true,"登录成功");
        }
    }

    @RequestMapping("/getMeunList")
    public List<MeunBean> getMeunList(HttpServletRequest request){
        UserBean ub = (UserBean) request.getSession().getAttribute("ub");
        return userService.getMeunList(ub);
    }


    @RequestMapping("/getUserById")
    public UserBean getUserById(Long id){
        return userService.getUserListById(id);
    }

    @RequestMapping("/saveUserDapt")
    public ResultInfo saveUserDapt(Long id,@RequestBody Long[] deptids){

        try {
            userService.saveUserDapt(id,deptids);
            return new ResultInfo(true,"编辑成功");
        }catch (Exception e){
            return new ResultInfo(false,"编辑失败");
        }

    }

    @RequestMapping("/getUserInfo")
    public UserBean getUserInfo(Long id){
        return userService.getUserInfo(id);
    }

    @RequestMapping("/saveUserPost")
    public ResultInfo saveUserPost(@RequestBody UserBean userBean){
        try {
            userService.saveUserPost(userBean);
            return new ResultInfo(true,"编辑成功");
        }catch (Exception e){
            return new ResultInfo(false,"编辑成功");
        }
    }

}
