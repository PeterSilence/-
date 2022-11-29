package com.juane.peapyoung.controller;

import com.juane.peapyoung.common.R;
import com.juane.peapyoung.entity.Articles;
import com.juane.peapyoung.entity.ReceiveBody;
import com.juane.peapyoung.service.ArticlesService;
import com.juane.peapyoung.service.UsagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticlesController {
    @Autowired
    private ArticlesService aService;
    @Autowired
    private UsagerService uService;
    @Autowired
    private ArticlesService articlesService;
    //新增失物信息（从捡到人员角度）
    @PostMapping("/saveArticlesByTaker")
    public R<String> saveArticlesByTaker(HttpServletRequest request,
                                         @RequestBody ReceiveBody receiveBody){
        receiveBody.setId((String) request.getSession().getAttribute("usager"));
        aService.saveArticlesByTaker(receiveBody.getArticles(), receiveBody.getId());
        return R.success("物品信息添加成功，系统会帮您尽快找到失主");
    }

    @PostMapping("/saveArticlesByOwner")
    public R<String> saveArticlesByOwner(HttpServletRequest request,
                                         @RequestBody ReceiveBody receiveBody){
        receiveBody.setId((String) request.getSession().getAttribute("usager"));
        aService.saveArticlesByOwner(receiveBody.getArticles(), receiveBody.getId());
        return R.success("失物信息添加成功，希望失物尽快回到您的身边");
    }

    //根据条件查找失物
    @PostMapping("/selectArticlesByConditions")
    public R<List<Articles>> selectArticlesByConditions(@RequestBody Articles articles){
        List<Articles> list = aService.selectArticlesByConditions(articles);
        return R.success(list);
    }

    //根据物品状态给物品分类
    @GetMapping("/selectArticlesByStatus")
    public R<List<Articles>> selectArticlesByStatus(@PathVariable int status){
        return R.success(aService.selectArticlesByStatus(status));
    }

    //显示所有记录在案的失物信息，给管理员使用
    @GetMapping("/selectAllArticles")
    public R<List<Articles>> selectAllArticles(){
        return R.success(aService.selectAllArticles());
    }

    //失物认领,物品主人角度
    @GetMapping("/findOwner")
    public R<String> findOwner(HttpServletRequest request,
                                @PathVariable String articlesId,
                                @PathVariable int code){
        //根据id获取对应的物品信息
        Articles articles = articlesService.getArticlesById(articlesId);
        //将物品状态调整到“物归原主”态
        articles.setStatus(3);
        String phone;
        //如果验证码是1，获取捡到者id
        if (code == 1){
            //如果是捡到者上传失物信息，设置物品的takerId信息，并获取捡到者电话
            articles.setTakerId((String) request.getSession().getAttribute("usager"));
            phone = uService.getPhone(articles.getTakerId());
        }
        else {
            //如果是失主上传失物信息，设置物品的owner信息，并获取主人电话
            articles.setOwner((String) request.getSession().getAttribute("usager"));
            phone = uService.getPhone(articles.getOwner());
        }
        //更新物品信息
        articlesService.updateArticles(articles);
        //返回失主或捡到者的电话号码
        return R.success(phone);
    }

}
