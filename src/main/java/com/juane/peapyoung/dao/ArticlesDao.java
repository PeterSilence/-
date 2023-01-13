package com.juane.peapyoung.dao;

import com.juane.peapyoung.entity.Articles;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ArticlesDao {
    //新增失物
    int saveArticles(Articles articles);
    //根据条件查找失物
    List<Articles> selectArticlesByConditions(Articles articles,int startIndex,int pageSize);

    //根据条件查找失物，返回数量
    int theSumOfSelectArticlesByConditions(Articles articles);

    //根据物品状态给物品分类，给普通用户使用，通过“待认领2”和“丢失中1”两种状态来查询
    @Select("select * from articles where status = #{status} limit #{startIndex},#{pageSize}")
    List<Articles> selectArticlesByStatus(int status,int startIndex,int pageSize);//1找物品(失主角度)2找失主(拾者角度)

    //根据物品状态进行查找，返回数量
    @Select("select count(*) from articles where status = #{status}")
    int theSumOfSelectArticlesByStatus(int status);

    //修改失物信息
    int updateArticles(Articles articles);

    //根据id获取失物信息
    @Select("select * from articles where id  = #{id}")
    Articles  getArticlesById(String id);

    //我的丢失
    @Select("select * from articles where owner = #{current} and takerId is null")
    List<Articles> myLost(String usagerId);

    //我的上传
    @Select("select * from articles wherre takerId = #{current} and owner is null")
    List<Articles> myUpload(String usagerId);

    //我的认领
    @Select("select * from articles where owner = #{usagerId} and takerId is not null")
    List<Articles> myClaim(String usagerId);

    @Update("update articles set status = #{status} =  where id = #{id}")
    int updateStatus(Long id,int status);

    //删除我的丢失
    @Delete("delete from articles where id = #{id} and owner = #{owner}")
    int deleteMyLost(Long id,String owner);

    //删除我的上传
    @Delete("delete from articles where id = #{id} and takerId = #{takerId}")
    int deleteMyUpload(Long id,String takerId);
}
