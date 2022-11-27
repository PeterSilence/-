package com.juane.peapyoung.dao;

import com.juane.peapyoung.entity.Articles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import java.util.List;

@Mapper
public interface ArticlesDao {
    //新增失物
    int savaAllArticles(Articles articles);
    //根据条件查找失物(两方用途，一是根据用户提供的先所从数据库查找，而是根据用户id查找)
    List<Articles> selectArticlesByConditions(Articles articles);
    //根据物品状态给物品分类
    @Select("select * from articles where status = #{status}")
    List<Articles> selectArticlesByStatus(int status);//1丢失2待认领
    //显示所有失物
    @Select("select * from articles where status != 3")
    List<Articles> selectAllArticles();
    //找到失物主人,或者失物被捡到
    @Update("update articles set status = 3 where id = #{id}")
    int findOwner(String id);
    //修改失物信息
    int updateArticles(Articles articles);
}
