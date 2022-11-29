package com.juane.peapyoung.dao;

import com.juane.peapyoung.entity.Articles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


import java.util.List;

@Mapper
public interface ArticlesDao {
    //新增失物（捡到人员角度）
    int saveArticlesByTaker(Articles articles, String takerId);
    //新增失物（失主角度）
    int saveArticlesByOwner(Articles articles,String owner);
    //根据条件查找失物(两方用途，一是根据用户提供的先所从数据库查找，而是根据用户id查找)
    List<Articles> selectArticlesByConditions(Articles articles);
    //根据物品状态给物品分类，给普通用户使用，通过“待认领2”和“丢失中1”两种状态来查询
    @Select("select * from articles where status = #{status}")
    List<Articles> selectArticlesByStatus(int status);//1找物品2找失主
    //显示所有记录在案的失物,给管理员使用
    @Select("select * from articles")
    List<Articles> selectAllArticles();

    //修改失物信息
    int updateArticles(Articles articles);

    //根据id获取失物信息
    @Select("select * from articles where id  = #{id}")
    Articles  getArticlesById(String id);
}
