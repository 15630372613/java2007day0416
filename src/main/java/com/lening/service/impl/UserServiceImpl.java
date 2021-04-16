package com.lening.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lening.entity.*;
import com.lening.mapper.DeptMapper;
import com.lening.mapper.MeunMapper;
import com.lening.mapper.UserMapper;
import com.lening.service.UserService;
import com.lening.utils.MD5key;
import com.lening.utils.Page;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private MeunMapper meunMapper;

    @Resource
    private DeptMapper deptMapper;

    public List<UserBean> getUserList() {
        return userMapper.selectByExample(null);
    }

    public List<UserBean> selectAll() {
        return userMapper.selectAll();
    }

    public Page<UserBean> getUsetConn(UserBean ub, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        UserBeanExample example = new UserBeanExample();
        UserBeanExample.Criteria criteria = example.createCriteria();
        if(ub!=null){
            if(ub.getUname()!=null&&ub.getUname().length()>=1){
                criteria.andUnameLike("%"+ub.getUname()+"%");
            }
            if(ub.getAge()!=null&&ub.getAge()>=1){
                criteria.andAgeGreaterThanOrEqualTo(ub.getAge());
            }
            if(ub.getEage()!=null&&ub.getEage()>=1){
                criteria.andAgeLessThanOrEqualTo(ub.getEage());
            }
        }

        List<UserBean> list = userMapper.selectByExample(example);
        PageInfo<UserBean> pageInfo = new PageInfo<UserBean>(list);
        Long total = pageInfo.getTotal();
        Page page = new Page(pageInfo.getPageNum()+"", total.intValue(),pageInfo.getPageSize()+"");
        page.setList(list);
        return page;
    }

    public List<MeunBean> getMeunList(UserBean ub) {
        if(ub!=null){
            MeunBeanExample example = new MeunBeanExample();
            MeunBeanExample.Criteria criteria = example.createCriteria();
            criteria.andIsbuttonEqualTo(0);
            List<MeunBean> list = meunMapper.selectByExample(example);
            return list;
        }
        return null;
    }

    @Override
    public UserBean getUserListById(Long id) {
        UserBean userBean = userMapper.selectByPrimaryKey(id);
        Long[] deptids = userMapper.selectUserDeptById(id);
        userBean.setDeptids(deptids);
        List<DeptBean> deptBeanList = deptMapper.selectByExample(null);
        userBean.setDlist(deptBeanList);
        return userBean;
    }

    @Override
    public void saveUserDapt(Long id, Long[] deptids) {

        userMapper.deleteUserDeptById(id);
        userMapper.deleteUserPostById(id);
        if(deptids!=null&&deptids.length>=1){
            for(Long deptid : deptids){
                userMapper.insertUserDept(id, deptid);
            }
        }

    }

    @Override
    public UserBean getUserInfo(Long id) {

        UserBean userBean = userMapper.selectByPrimaryKey(id);
        List<DeptBean> dlist = userMapper.getUserDeptById(id);
        if(dlist!=null&&dlist.size()>=1){
            for(DeptBean deptBean : dlist){
                List<PostBean> plist = deptMapper.getDeptPostList(deptBean.getId());
                Long[] postids = deptMapper.getUserPostByIdAndDeptid(id, deptBean.getId());
                deptBean.setPlist(plist);
                deptBean.setPostids(postids);
            }
        }
        userBean.setDlist(dlist);
        return userBean;
    }

    @Override
    public void saveUserPost(UserBean userBean) {
        if(userBean!=null){
            userMapper.deleteUserPostById(userBean.getId());
            if(userBean.getDlist()!=null&&userBean.getDlist().size()>=1){
                for(DeptBean deptBean : userBean.getDlist()){
                    if(deptBean.getPostids()!=null&&deptBean.getPostids().length>=1){
                        for(Long postid : deptBean.getPostids()){
                            userMapper.inserUserPost(userBean.getId(),postid);
                        }
                    }
                }
            }
        }
    }

    @Override
    public UserBean getLogin(UserBean ub) {

        if(ub!=null){
            List<UserBean> list = userMapper.getLogin(ub);
            if(list!=null&&list.size()==1){
                UserBean userBean = list.get(0);
                String pwd = ub.getPwd();
                pwd = userBean.getPwdsalt()+pwd+userBean.getPwdsalt();
                MD5key md5key = new MD5key();
                String newpwd = md5key.getkeyBeanofStr(pwd);
                if(newpwd.equals(userBean.getPwd())){
                    return userBean;
                }
            }
        }
        return null;
    }

    @Test
    public void test(){
        String pwd = "123456";
        pwd = "1234"+pwd+"1234";
        MD5key md5key = new MD5key();
        String newpwd = md5key.getkeyBeanofStr(pwd);
        System.out.println(newpwd);
    }

}
