package com.juane.peapyoung.dao;

import com.juane.peapyoung.entity.Usager;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsagerDao {
    //显示所有用户信息（提供管理员查看）
    @Select("select id,aId,name,sex,phone,status,schoolPosition,avator from usager limit #{startIndex},30")
    List<Usager> selectAllUsagers(int startIndex);

    //显示所有用户信息，返回数量
    @Select("select count(*) from usager")
    int theSumOfSelectAllUsagers();
    //根据id查看个人信息
    @Select("select * from usager where id = #{id}")
    Usager selectUsagerById(String id);

    //根据id修改用户信息（给用户使用）
    int updateUsager(Usager usager);

    //修改用户状态（给用户和管理员使用）用户可以自己注销账号，管理员可以验证和停用
    int modifyStatus(String id,String aId,int status);

    //添加用户信息
    int saveUsager(Usager usager);

    //配合失物认领使用，返回捡到者id
    @Select("select phone from usager where id = #{id}")
    String getPhone(String id);

    //修改用户密码
    @Update("update usager set password = #{newCode} where id = #{id} and password = #{pastCode}")
    int changePassword(String id,String pastCode,String newCode);

    //根据状态筛选数据
    @Select("select * from usager where status = #{status} limit #{startIndex},30")
    List<Usager> selectUsagerByStatus(int status,int startIndex);
    //根据状态筛选数据，返回数量
    @Select("select count(*) from usager where status = #{status}")
    int theSumOfSelectUsagerByStatus(int status);
}
