package com.juane.dao;
import com.juane.entity.Administrateur;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AdministrateurDao {
    //查询所有数据
    @Select("select id,name,phone,password,status from administrateur where id != #{id}")
    List<Administrateur> getTrenteData(String id);

    //新增管理员数据
    int savaData(Administrateur administrateur);

    //根据id查找数据
    @Select("select status from administrateur where id = #{id}")
    Administrateur isIdExist(String id);

    //登录验证功能
    @Select("select id,name,password,status from administrateur where id = #{id} and password = #{password}")
    Administrateur isAdministrateurExist(String id,String password);

    //更新管理员信息
    int updateData(Administrateur administrateur);

    //删除管理员信息
    int changeAdministrateurStatus(int status,String id);

    //根据条件查询管理员数据
    List<Administrateur> selectByConditions(@Param("administrateur")
                                            Administrateur administrateur);

}
