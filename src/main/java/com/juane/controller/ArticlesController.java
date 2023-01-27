package com.juane.controller;

import com.juane.common.R;
import com.juane.entity.Articles;
import com.juane.entity.ReceiveBody;
import com.juane.service.ArticlesService;
import com.juane.service.UsagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticlesController {
    @Autowired
    private ArticlesService aService;
    @Autowired
    private UsagerService uService;
    @Autowired
    private ArticlesService articlesService;
    //新增失物信息（从捡到人员角度）
    @PostMapping("/saveArticlesByTaker")
    public R<String> saveArticlesByTaker(HttpServletRequest request,
                                         @RequestBody Articles articles){
        articles.setTakerId((String) request.getSession().getAttribute("usager"));
        articles.setStatus(2);
        int code = articlesService.saveArticles(articles);
        if (code == 1)
            return R.success("感谢您让牧院变得更加美好，希望物品早日回到失主身边！");
        else return R.error("发生未知错误，请联系管理员并重试！");
    }

    @PostMapping("/saveArticlesByOwner")
    public R<String> saveArticlesByOwner(HttpServletRequest request,
                                         @RequestBody Articles articles){
        articles.setOwner((String) request.getSession().getAttribute("usager"));
        articles.setStatus(1);
        int code = articlesService.saveArticles(articles);
        if (code == 1)
            return R.success("请您耐心等待，系统会尽快帮您找回物品");
        else return R.error("发生未知错误，请联系管理员重试!");
    }

    //根据条件查找失物
    @PostMapping("/selectArticlesByConditions")
    public R<List<Articles>> selectArticlesByConditions(@RequestBody ReceiveBody receiveBody){
        Articles articles = receiveBody.getArticles();
        int startIndex = receiveBody.getStartIndex();
        int pageSize = receiveBody.getPageSize();
        articles.setName("%"+articles.getName()+"%");
        articles.setPosition(articles.getPosition()+"%");
        List<Articles> list = aService.selectArticlesByConditions(articles,startIndex,pageSize);
        if (!list.isEmpty()) return R.success(list);
        else return R.error("当前条件搜索为空!");
    }
    @PostMapping("/theSumOfSelectArticlesByConditions")
    public R<Integer> selectArticlesByConditions(@RequestBody Articles articles){
        articles.setName("%"+articles.getName()+"%");
        articles.setPosition(articles.getPosition()+"%");
        int sum = aService.theSumOfSelectArticlesByConditions(articles);
        return  R.success(sum);
    }

    //根据物品状态给物品分类
    //查看所有物品信息(已经找到)
    @GetMapping("allArticlesByFind")
    public R<List<Articles>> allArticlesByFind(int startIndex,int pageSize){
        return R.success(articlesService.selectArticlesByStatus(3,startIndex,pageSize));
    }
    //查看所有物品信息（无人认领）
    @GetMapping("allArticlesByTake")
    public R<List<Articles>> allArticlesByTake(int startIndex,int pageSize){
        return R.success(articlesService.selectArticlesByStatus(2,startIndex,pageSize));
    }
    //查看所有物品信息（丢失中）
    @GetMapping("allArticlesByLost")
    public R<List<Articles>> allArticlesByLost(int startIndex,int pageSize){
        return R.success(articlesService.selectArticlesByStatus(1,startIndex,pageSize));
    }

    @GetMapping("theSumOfSelectArticlesByStatus")
    public R<Integer> theSumOfSelectArticlesByStatus(int status){
        return R.success(articlesService.theSumOfSelectArticlesByStatus(status));
    }
    //失物认领,捡到者和主人都可以调用此等方法。前端标签可示为：我捡到了，我是失主
    //前端传来物品id即可
    @GetMapping("/findOwner")
    public R<String> findOwner(HttpServletRequest request,
                                 String articlesId){
        //根据id获取对应的物品信息
        Articles articles = articlesService.getArticlesById(articlesId);
        int status = articles.getStatus();
        //将物品状态调整到“物归原主”态
        articles.setStatus(3);
        String phone;
        //如果验证码是1，获取捡到者id
        if (status == 1){
            //如果是捡到者上传的失物，设置物品的takerId信息，并获取捡到者电话
            articles.setTakerId((String) request.getSession().getAttribute("usager"));
            phone = uService.getPhone(articles.getTakerId());
        }
        else if (status == 2){
            //如果是失主上传的失物，设置物品的owner信息，并获取主人电话
            articles.setOwner((String) request.getSession().getAttribute("usager"));
            phone = uService.getPhone(articles.getOwner());
        }else return R.error("当前物品已经找到主人啦，不要再找一个爸爸或妈妈啦");
        //更新物品信息
        articlesService.updateArticles(articles);
        //返回失主或捡到者的电话号码
        return R.success(phone);
    }

    //我的丢失
    @GetMapping("/myLost")
    public R<List<Articles>> myLost(HttpServletRequest request){
        String id = (String) request.getSession().getAttribute("usager");
        return R.success(articlesService.selectByMe(id,0));
    }
    //我的上传
    @GetMapping("/myUpload")
    public R<List<Articles>> myUpload(HttpServletRequest request){
        String id = (String) request.getSession().getAttribute("usager");
        return R.success(articlesService.selectByMe(id,1));
    }

    //我的认领
    @GetMapping("/myClaim")
    public R<List<Articles>> myClaim(HttpServletRequest request){
        String id = (String) request.getSession().getAttribute("usager");
        return R.success(articlesService.selectByMe(id,2));
    }

    //取消认领
    @GetMapping("/cancelClaim")
    public R<String> cancelClaim(HttpServletRequest request,Long articlesId){
        int code = articlesService.changeStatus(articlesId,2);
        if (code == 1)
            return R.success("操作成功！");
        else return R.error("发生未知错误，请重试！");
    }

    //删除"我丢失的"物品信息
    @DeleteMapping("/deleteMyLost")
    public R<String> deleteArticles(HttpServletRequest request,Long id){
        String usagerId = (String) request.getSession().getAttribute("usager");
        int code = articlesService.deleteArticles(id,usagerId,0);
        if (code == 1)
            return R.success("删除数据成功！");
        else return R.error("发生未知错误，请重试！");
    }
    //删除“我上传的”物品信息
    @DeleteMapping("/deleteMyUpload")
    public R<String> deleteMyUpload(HttpServletRequest request,Long id){
        String usagerId = (String) request.getSession().getAttribute("usager");
        int code = articlesService.deleteArticles(id,usagerId,1);
        if (code == 1)
            return R.success("删除数据成功！");
        else return R.error("发生未知错误，请重试！");
    }
}
