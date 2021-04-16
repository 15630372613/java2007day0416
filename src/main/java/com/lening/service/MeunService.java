package com.lening.service;

import com.lening.entity.MeunBean;

import java.util.List;

public interface MeunService {


    List<MeunBean> getMeunListByPid(Long pid);

    void saveMeun(MeunBean meunBean);

    void deleteMeunById(Long id);
}
