package com.juane.service;

import com.juane.entity.Usager;

import java.util.List;

public interface UsagerService {
    //显示所有用户信息，供管理员使用
    List<Usager> selectAllUsagers(int startIndex);

    //显示所有用户信息，返回数量
    int theSumOfSelectAllUsagers();

    //根据id查询个人信息
    Usager selectUsagerById(String id);

    //根据id修改用户信息
    int updateUsager(Usager usager);

    //修改用户状态，用户可以注销账号，管理员可以审核和停用账号
    int modifyStatus(String id,String cId,int status);

    //添加用户信息
    int savaUsager(Usager usager);

   //获取手机号
   String getPhone(String id);

   //修改密码
    int changePassword(String id,String pastCode,String newCode);

    //根据状态筛选用户
    List<Usager> selectUsagerByStatus(int status,int startIndex);

    //根据状态筛选用户
    int theSumOfSelectUsagerByStatus(int status);
}
