package com.juane.peapyoung.service.impl;

import com.juane.peapyoung.dao.AdministrateurDao;
import com.juane.peapyoung.entity.Administrateur;
import com.juane.peapyoung.service.AdministrateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministrateurServiceImpl implements AdministrateurService {
    @Autowired
    private AdministrateurDao aDao;

    //获取管理员数据
    public List<Administrateur> getData(String id) {
        Administrateur administrateur = new Administrateur();
        administrateur.setId(id);
        List<Administrateur> list = aDao.selectByConditions(administrateur);
        if (list.get(0).getStatus() != 3){
            return null;
        }else
            return aDao.getTrenteData(id);
    }

    //新增管理员信息,因为是用户自己输入的id，所以要判定数据库中是否已存在，确保唯一性
    @Override
    public int saveData(String id,Administrateur savedData) {
        Administrateur administrateur = new Administrateur();
        administrateur.setId(id);
        List<Administrateur> list = aDao.selectByConditions(administrateur);
        if (list.get(0).getStatus() == 3){
            int a = aDao.isIdExist(administrateur.getId());
            if (a != 1){
                aDao.savaData(administrateur);
                return 2;
            }else
                return 1;
        }else
            return 0;

    }

    //判断该id是否存在（测试类删除后把此函数再删除）
    @Override
    public int isIdExist(String id) {
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
    public String deleteByOwner(String currentId,String targetId) {
        Administrateur administrateur = new Administrateur();
        administrateur.setId(currentId);
        List<Administrateur> lists = aDao.selectByConditions(administrateur);
        if (lists.get(0).getStatus() == 3){
            aDao.deleteDataByOwner(targetId);
            return "更新管理员信息成功！";
        } else
            return "当前管理员权限不足，无法完成修改！";
    }

    @Override
    public List<Administrateur> selectByConditions(Administrateur administrateur) {
        return aDao.selectByConditions(administrateur);
    }
}
