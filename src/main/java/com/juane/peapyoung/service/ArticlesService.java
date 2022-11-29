package com.juane.peapyoung.service;

import com.juane.peapyoung.entity.Articles;

import java.util.List;

public interface ArticlesService {
    //新增失物（捡到人员角度）
    int saveArticlesByTaker(Articles articles, String takerId);

    //新增失物（失主角度）
    int saveArticlesByOwner(Articles articles,String owner);

    //根据条件查找失物(两方用途，一是根据用户提供的先所从数据库查找，而是根据用户id查找)
    List<Articles> selectArticlesByConditions(Articles articles);

    //根据物品状态给物品分类，给普通用户使用，通过“待认领2”和“丢失中1”两种状态来查询
    List<Articles> selectArticlesByStatus(int status);

    //显示所有记录在案的失物,给管理员使用
    List<Articles> selectAllArticles();

    //修改失物信息
    int updateArticles(Articles articles);

    //通过id获取物品所有信息
    Articles getArticlesById(String id);
}
