package com.juane.service;
import com.juane.entity.Administrateur;
import java.util.List;


public interface AdministrateurService {
    List<Administrateur> getData(String id);

    int saveData(String currentId,Administrateur administrateur);

    Administrateur isIdExist(String id);

    Administrateur isAdministrateurExist(String id,String password);

    int updateData(Administrateur administrateur);

    int changeAdministrateurStatus(int status,String targetId);

    List<Administrateur> selectByConditions(Administrateur administrateur);
}
