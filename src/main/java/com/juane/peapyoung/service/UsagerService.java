package com.juane.peapyoung.service;

import com.juane.peapyoung.entity.Usager;
import org.graalvm.compiler.lir.alloc.lsra.LinearScan;

import java.util.List;

public interface UsagerService {
    //显示所有用户信息，供管理员使用
    List<Usager> selectAllUsagers();

    //根据id查询个人信息
    Usager selectUsagerById(String id);

    //根据id修改用户信息
    int updateUsager(Usager usager);

    //修改用户状态，用户可以注销账号，管理员可以审核和停用账号
    int modifyStatus(String id,String cId,int status);

    //添加用户信息
    int savaUsager(Usager usager);

    //登录验证
    Usager login(String id,String password);

   //获取手机号
   String getPhone(String id);
}
