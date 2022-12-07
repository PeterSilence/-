package com.juane.peapyoung.service.impl;

import com.juane.peapyoung.dao.UsagerDao;
import com.juane.peapyoung.entity.Usager;
import com.juane.peapyoung.service.UsagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsagerServiceImpl implements UsagerService {
    @Autowired
    private UsagerDao usagerDao;
    @Override
    public List<Usager> selectAllUsagers() {
        return usagerDao.selectAllUsagers();
    }

    @Override
    public Usager selectUsagerById(String id) {
        return usagerDao.selectUsagerById(id);
    }

    @Override
    public int updateUsager(Usager usager) {
        return usagerDao.updateUsager(usager);
    }

    @Override
    public int modifyStatus(String id, String cId, int status) {
        return usagerDao.modifyStatus(id, cId, status);
    }

    @Override
    public int savaUsager(Usager usager) {
        return usagerDao.saveUsager(usager);
    }


    @Override
    public String getPhone(String id) {
        return usagerDao.getPhone(id);
    }

    @Override
    public int changePassword(String id, String pastCode, String newCode) {
        return usagerDao.changePassword(id, pastCode, newCode);
    }

    @Override
    public List<Usager> selectUsagerByStatus(int status) {
        return usagerDao.selectUsagerByStatus(status);
    }
}
