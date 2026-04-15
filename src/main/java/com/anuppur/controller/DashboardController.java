package com.anuppur.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anuppur.bean.WorkBean;
import com.anuppur.service.CommonService;

@RestController
@RequestMapping(value = {"/api"})
public class DashboardController {

    @Autowired
    private CommonService commonService;

    @GetMapping("/getWorkDetailsWithGeo")
    public List<WorkBean> getWorkDetailsWithGeo() {
System.err.println("call power");
        return commonService.getWorkDetailsWithGeo();
    }
}