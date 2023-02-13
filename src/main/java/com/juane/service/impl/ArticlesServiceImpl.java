package com.juane.service.impl;

import com.juane.dao.ArticlesDao;
import com.juane.entity.Articles;
import com.juane.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;
@Service
public class ArticlesServiceImpl implements ArticlesService {
    @Autowired
    private ArticlesDao articlesDao;
    @Override
    public int saveArticles(Articles articles) {
        articles.setGmt_create(LocalDate.now());
        return articlesDao.saveArticles(articles);
    }

    @Override
    public List<Articles> selectByConditions(Articles articles,int startIndex) {
            return articlesDao.selectByConditions(articles,startIndex);
    }

    @Override
    public int theSumOfSelectArticlesByConditions(Articles articles) {
        int sum = articlesDao.theSumOfSelectArticlesByConditions(articles);
        int remainder = sum % 30;
        int pageNum = sum / 30;
        if (remainder == 0) return pageNum;
        else return (pageNum) + 1;
    }

    @Override
    public List<Articles> selectArticlesByStatus(int status,int startIndex) {
        return articlesDao.selectArticlesByStatus(status,startIndex);
    }

    //如果一页能装30条数据，那么所有数据可以存放多少页？
    @Override
    public int theSumOfSelectArticlesByStatus(int status) {
        int sum = articlesDao.theSumOfSelectArticlesByStatus(status);
        int remainder = sum % 30;
        int pageNum = sum / 30;
        if (remainder == 0) return pageNum;
        else return (pageNum) + 1;
    }

    @Override
    public int updateArticles(Articles articles) {
        articles.setGmt_modified(LocalDate.now());
        return articlesDao.updateArticles(articles);
    }

    @Override
    public Articles getArticlesById(Long id) {
        System.out.println(articlesDao.getArticlesById(id));
        return articlesDao.getArticlesById(id);
    }

    @Override
    public List<Articles> selectByMe(String usagerId, int status) {
        if (status == 1)
            return articlesDao.myLost(usagerId);
        else if (status == 2)
            return articlesDao.myUpload(usagerId);
        else if (status == 3)
            return articlesDao.myClaim(usagerId);
        else return null;
    }

    @Override
    public int changeStatus(Articles articles) {
        articles.setGmt_modified(LocalDate.now());
        return articlesDao.cancelClaim(articles);
    }

    @Override
    public int deleteArticles(Long id) {
        return articlesDao.deleteArticles(id);
    }
}
