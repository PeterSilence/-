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
    public List<Articles> selectArticlesByConditions(Articles articles,int startIndex,int pageSize) {
        return articlesDao.selectArticlesByConditions(articles,startIndex,pageSize);
    }

    @Override
    public int theSumOfSelectArticlesByConditions(Articles articles) {
        int sum = articlesDao.theSumOfSelectArticlesByConditions(articles);
        int pageNum = sum % 30;
        if (pageNum == 0) return pageNum / 30;
        else return (pageNum / 30) + 1;
    }

    @Override
    public List<Articles> selectArticlesByStatus(int status,int startIndex,int pageSize) {
        return articlesDao.selectArticlesByStatus(status,startIndex,pageSize);
    }

    @Override
    public int theSumOfSelectArticlesByStatus(int status) {
        int sum = articlesDao.theSumOfSelectArticlesByStatus(status);
        int pageNum = sum % 30;
        if (pageNum == 0) return pageNum / 30;
        else return (pageNum / 30) + 1;
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
            return articlesDao.deleteMyUpload(id,usager);
        else return 2;
    }
}
