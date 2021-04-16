package com.lening.controller;

import com.lening.entity.MeunBean;
import com.lening.service.MeunService;
import com.lening.utils.ResultInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/meun")
public class MeunController {

    @Resource
    private MeunService meunService;

    @RequestMapping("/getMeunListByPid")
    public List<MeunBean> getMeunListByPid(@RequestParam(defaultValue = "1") Long pid){
        return meunService.getMeunListByPid(pid);
    }

    @RequestMapping("/saveMeun")
    public ResultInfo saveMeun(@RequestBody MeunBean meunBean){

        try {
            meunService.saveMeun(meunBean);
            return new ResultInfo(true,"编辑成功");
        }catch (Exception e){
            return new ResultInfo(false,"编辑失败");
        }

    }

    @RequestMapping("/delMeunById")
    public ResultInfo delMeunById(Long id){
        try {
            meunService.deleteMeunById(id);
            return new ResultInfo(true ,"编辑成功");
        }catch (Exception e){
            return new ResultInfo(false,"编辑失败");
        }
    }

    


}
