package com.juane.peapyoung.dao;

import com.juane.peapyoung.entity.Articles;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.DeleteMapping;


import java.util.List;

@Mapper
public interface ArticlesDao {
    //新增失物
    int saveArticles(Articles articles);
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
