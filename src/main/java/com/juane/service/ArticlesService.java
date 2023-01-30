package com.juane.service;

import com.juane.entity.Articles;

import java.util.List;

public interface ArticlesService {
    //新增失物
    int saveArticles(Articles articles);

    //根据条件查找失物(两方用途，一是根据用户提供的先所从数据库查找，而是根据用户id查找)
    List<Articles> selectByConditions(Articles articles,int startIndex);

    //根据条件查找失物，返回页数
    int theSumOfSelectArticlesByConditions(Articles articles);
    //根据物品状态给物品分类，给普通用户使用，通过“待认领2”和“丢失中1”两种状态来查询
    List<Articles> selectArticlesByStatus(int status,int startIndex);

    //根据物品状态分类，返回页数
    int theSumOfSelectArticlesByStatus(int status);
    //修改失物信息
    int updateArticles(Articles articles);

    //通过id获取物品所有信息
    Articles getArticlesById(Long id);

    //根据物品与用户的关系查找物品
    List<Articles> selectByMe(String usagerId,int status);

    //改变物品状态
    int changeStatus(Long id,int status);

    //删除物品信息
    int deleteArticles(Long id);

}
