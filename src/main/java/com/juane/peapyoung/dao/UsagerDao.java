package com.juane.peapyoung.dao;

import com.juane.peapyoung.entity.Usager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UsagerDao {
    //显示所有用户信息（提供管理员查看）
    @Select("select * from usager")
    List<Usager> selectAllUsagers();

    //根据id查看个人信息
    @Select("select * from usager where id = #{id}")
    Usager selectUsagerById(String id);

    //根据id修改用户信息（给用户使用）
    int updateUsager(Usager usager);

    //修改用户状态（给用户和管理员使用）用户可以自己注销账号，管理员可以验证和停用
    int modifyStatus(String id,String cId,int status);

    //添加用户信息
    int saveUsager(Usager usager);

    //登录验证
    @Select("select * from usager where id = #{id} and password = #{password}")
    Usager login(String id,String password);

    //配合失物认领使用，返回捡到者id
    @Select("select phone from usager where id = #{id}")
    String getPhone(String id);
}
