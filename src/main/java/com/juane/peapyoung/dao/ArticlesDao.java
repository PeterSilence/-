package com.juane.peapyoung.dao;

import com.juane.peapyoung.entity.Articles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


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
    //找到失物主人,或者失物被捡到.物品的属性中应该存在“主人”
    @Update("update articles set status = 3,owner = #{ownerId} where id = #{id}")
    int findOwner(String id,String ownerId);
    //修改失物信息
    int updateArticles(Articles articles);
}
