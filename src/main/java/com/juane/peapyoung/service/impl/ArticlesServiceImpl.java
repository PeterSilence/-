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
    public int saveArticlesByTaker(Articles articles, String takerId) {
        return articlesDao.saveArticlesByTaker(articles, takerId);
    }

    @Override
    public int saveArticlesByOwner(Articles articles, String owner) {
        return articlesDao.saveArticlesByOwner(articles, owner);
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
}
