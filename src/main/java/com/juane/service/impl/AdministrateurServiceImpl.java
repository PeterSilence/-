package com.juane.service.impl;

import com.juane.dao.AdministrateurDao;
import com.juane.entity.Administrateur;
import com.juane.service.AdministrateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministrateurServiceImpl implements AdministrateurService {
    @Autowired
    private AdministrateurDao aDao;

    //获取管理员数据
    public List<Administrateur> getData(String id) {
        return aDao.getTrenteData(id);
    }

    //新增管理员信息,让高级管理员进行操作
    @Override
    public int saveData(String id,Administrateur savedData) {
        Administrateur administrateur = new Administrateur();
        administrateur.setId(id);
        List<Administrateur> list = aDao.selectByConditions(administrateur);
        //验证执行添加操作的管理员权限
        if (list.get(0).getStatus() == 6){
            //验证所要添加的管理员id是否已经存在
            Administrateur a = aDao.isIdExist(savedData.getId());
            if (a == null){
                aDao.savaData(savedData);
                return 2;
            } else
                //当前id已经存在
                return 1;
        }else
            //管理员权限不足
            return 0;

    }

    //判断该id是否存在（测试类删除后把此函数再删除）
    @Override
    public Administrateur isIdExist(String id) {
        return aDao.isIdExist(id);
    }

    @Override
    public Administrateur isAdministrateurExist(String id, String password) {
        Administrateur administrateur = aDao.isAdministrateurExist(id,password);
        return administrateur;
    }

    //通过管理员id更新其个人信息
    @Override
    public int updateData(Administrateur administrateur) {
        return aDao.updateData(administrateur);
    }

    @Override
    public int changeAdministrateurStatus(int status, String targetId) {
        return aDao.changeAdministrateurStatus(status,targetId);
    }

    @Override
    public List<Administrateur> selectByConditions(Administrateur administrateur) {
        return aDao.selectByConditions(administrateur);
    }
}
