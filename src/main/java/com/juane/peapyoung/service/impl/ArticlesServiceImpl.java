package com.juane.peapyoung.service.impl;

import com.juane.peapyoung.dao.ArticlesDao;
import com.juane.peapyoung.entity.Articles;
import com.juane.peapyoung.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ArticlesServiceImpl implements ArticlesService {
    @Autowired
    private ArticlesDao articlesDao;
    @Override
    public int saveArticles(Articles articles) {
        return articlesDao.saveArticles(articles);
    }

    @Override
    public List<Articles> selectArticlesByConditions(Articles articles) {
        return articlesDao.selectArticlesByConditions(articles);
    }

    @Override
    public List<Articles> selectArticlesByStatus(int status) {
        return articlesDao.selectArticlesByStatus(status);
    }

    @Override
    public List<Articles> selectAllArticles() {
        return articlesDao.selectAllArticles();
    }

    @Override
    public int updateArticles(Articles articles) {
        return articlesDao.updateArticles(articles);
    }

    @Override
    public Articles getArticlesById(String id) {
        return articlesDao.getArticlesById(id);
    }

    @Override
    public List<Articles> selectByMe(String usagerId, int status) {
        if (status == 0)
            return articlesDao.myLost(usagerId);
        else if (status == 1)
            return articlesDao.myUpload(usagerId);
        else return articlesDao.myClaim(usagerId);
    }

    @Override
    public int changeStatus(Long id, int status) {
        return articlesDao.updateStatus(id, status);
    }

    @Override
    public int deleteArticles(Long id, String usager,int code) {
        if (code == 0)
            return articlesDao.deleteMyLost(id,usager);
        else if (code == 1)
            return articlesDao.deleteMyLost(id,usager);
        else return 2;
    }
}
