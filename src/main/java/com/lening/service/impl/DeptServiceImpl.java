package com.lening.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lening.entity.DeptBean;
import com.lening.entity.DeptBeanExample;
import com.lening.entity.PostBean;
import com.lening.mapper.DeptMapper;
import com.lening.mapper.PostMapper;
import com.lening.service.DeptService;
import com.lening.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private PostMapper postMapper;


    public Page<DeptBean> getDeptListConn(DeptBean deptBean, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        DeptBeanExample example = new DeptBeanExample();
        DeptBeanExample.Criteria criteria = example.createCriteria();
        if(deptBean!=null){
            if(deptBean.getDeptname()!=null&&deptBean.getDeptname().length()>=1){
                criteria.andDeptnameLike("%"+deptBean.getDeptname()+"%");
            }
        }
        List<DeptBean> list = deptMapper.selectByExample(example);
        PageInfo<DeptBean> pageInfo = new PageInfo(list);
        Long total = pageInfo.getTotal();
        Page page = new Page(pageInfo.getPageNum()+"", total.intValue(), pageInfo.getPageSize()+"");
        page.setList(list);
        return page;
    }

    @Override
    public DeptBean getDeptByDeptid(Long deptid) {
        DeptBean deptBean = deptMapper.selectByPrimaryKey(deptid);
        List<PostBean> plist = postMapper.selectByExample(null);
        deptBean.setPlist(plist);
        Long[] postids = deptMapper.getDeptPostIds(deptid);
        deptBean.setPostids(postids);
        return deptBean;
    }

    @Override
    public void insertDeptPost(Long deptid,Long[] postids) {
        deptMapper.deleteDeptPost(deptid);
        if(postids!=null&&postids.length>=1){
            for(Long postid : postids){
                deptMapper.insertDeptPost(deptid,postid);
            }
        }
    }
}
