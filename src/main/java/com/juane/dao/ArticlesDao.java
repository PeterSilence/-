package com.juane.dao;

import com.juane.entity.Articles;

import org.apache.ibatis.annotations.Delete;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface ArticlesDao {
    //新增失物
    int saveArticles(Articles articles);
    //根据条件查找失物
    List<Articles> selectByConditions(Articles articles, int startIndex);
    //根据条件查找失物，返回数量
    int theSumOfSelectArticlesByConditions( Articles articles);

    //根据物品状态给物品分类，给普通用户使用，通过“待认领2”和“丢失中1”两种状态来查询
    @Select("select id,name,position,lost_time,avator,status" +
            " from articles where status = #{status} limit #{startIndex},30")
    List<Articles> selectArticlesByStatus(int status,int startIndex);//1找物品(失主角度)2找失主(拾者角度)

    //根据物品状态进行查找，返回数量
    @Select("select count(*) from articles where status = #{status}")
    int theSumOfSelectArticlesByStatus(int status);

    //修改失物信息
    int updateArticles(Articles articles);

    //根据id获取失物信息
    @Select("select id,name,position,lost_time,takerId,avator,status,owner from articles where id  = #{id}")
    Articles  getArticlesById(Long id);

    //我的丢失
    @Select("select id,name,position,lost_time,avator,status" +
            " from articles where owner = #{current} and takerId is null")
    List<Articles> myLost(String usagerId);

    //我的上传
    @Select("select id,name,position,lost_time,avator,status" +
            " from articles where takerId = #{current} and owner is null")
    List<Articles> myUpload(String usagerId);


    //我的认领
    @Select("select id,name,position,lost_time,avator,status" +
            " from articles where owner = #{usagerId} and takerId is not null")
    List<Articles> myClaim(String usagerId);

    //更新物品状态
    @Update("update articles set status = #{status},owner = ''," +
            "gmt_modified = #{gmt_modified} where id = #{id}")
    int cancelClaim(Articles articles);

    //删除我的丢失或者上传信息
    @Delete("delete from articles where id = #{id}")
    int deleteArticles(Long id);
}
