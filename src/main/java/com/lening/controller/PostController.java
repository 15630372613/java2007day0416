package com.lening.controller;

import com.lening.entity.MeunBean;
import com.lening.entity.PostBean;
import com.lening.service.PostService;
import com.lening.utils.Page;
import com.lening.utils.ResultInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/post")
@RestController
public class PostController {

    @Resource
    private PostService postService;

    @RequestMapping("/getPostListConn")
    public Page<PostBean> getPostListConn(@RequestBody PostBean postBean, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "3") Integer pageSize){
        return postService.getPostListConn(postBean,pageNum,pageSize);
    }

    @RequestMapping("/getMeunListById")
    public List<MeunBean> getMeunListById(Long id){
        return postService.getMeunListById(id);
    }

    @RequestMapping("/savePostMeun")
    public ResultInfo savePostMeun(Long postid,@RequestBody Long[] ids){

        try {
            postService.savePostMeun(postid,ids);
            return new ResultInfo(true,"编辑成功");
        }catch (Exception e){
            return new ResultInfo(false,"编辑失败");
        }

    }

}
