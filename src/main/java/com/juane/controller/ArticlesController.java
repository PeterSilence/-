package com.juane.controller;

import com.juane.common.R;
import com.juane.entity.Articles;
import com.juane.entity.ReceiveBody;
import com.juane.service.ArticlesService;
import com.juane.service.UsagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
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

    @Autowired
    private RedisTemplate redisTemplate;
    //新增失物信息（从捡到人员角度）
    @PostMapping("/saveArticlesByTaker")
    public R<String> saveArticlesByTaker(HttpServletRequest request,
                                         @RequestBody Articles articles){
        articles.setTakerId((String) request.getSession().getAttribute("usager"));
        articles.setStatus(2);
        int code = articlesService.saveArticles(articles);

        if (code == 1){
            redisTemplate.delete("articles*");
            return R.success("感谢您让牧院变得更加美好，希望物品早日回到失主身边！");
        }

        else return R.error("发生未知错误，请联系管理员并重试！");
    }

    @PostMapping("/saveArticlesByOwner")
    public R<String> saveArticlesByOwner(HttpServletRequest request,
                                         @RequestBody Articles articles){
        articles.setOwner((String) request.getSession().getAttribute("usager"));
        articles.setStatus(1);
        int code = articlesService.saveArticles(articles);

        if (code == 1){
            redisTemplate.delete("articles*");
            return R.success("请您耐心等待，系统会尽快帮您找回物品");
        }

        else return R.error("发生未知错误，请联系管理员重试!");
    }

    //根据条件查找失物
    @PostMapping("/selectArticlesByConditions")
    public R selectArticlesByConditions(@RequestBody ReceiveBody receiveBody) throws ParseException {
        /**
         * 在查找数据时，需要对特定参数进行相关的判定，比如字符串的空''和无null判断，
         * 但是这样的判定模式不能应用于Date类型以及Integer类型，这两种类型只能进行非空判定
         */
        //先在缓存中查找，有的话直接返回，没有的话再从数据库中进行查找
        Articles articles = receiveBody.getArticles();
        int startIndex = receiveBody.getStartIndex();
        startIndex = 30 * ( startIndex - 1 );
        String key = articles.buildKey() + startIndex;
        List<Articles> articlesList = (List<Articles>) redisTemplate.opsForValue().get(key);
        if (articlesList != null){
            return R.success(articlesList);
        }

        if (articles.getName() != null && articles.getName() != ""){
            articles.setName("%"+articles.getName()+"%");
        }
        if (articles.getPosition() != null && articles.getPosition() != ""){
            articles.setPosition(articles.getPosition()+"%");
        }
        List<Articles> list = articlesService.selectByConditions(articles,startIndex);

        if (!list.isEmpty()){
            redisTemplate.opsForValue().set(key,list);
            return R.success(list);
        }

        else return R.error("当前条件搜索为空!");
    }

    @PostMapping("/theSumOfSelectArticlesByConditions")
    public R<Integer> selectArticlesByConditions(@RequestBody Articles articles){
        if (articles.getName() != null && articles.getName() != ""){
            articles.setName("%"+articles.getName()+"%");
        }
        if (articles.getPosition() != null && articles.getPosition() != ""){
            articles.setPosition(articles.getPosition()+"%");
        }
        int sum = aService.theSumOfSelectArticlesByConditions(articles);
        return  R.success(sum);
    }

    /**
     * 以下四个方法可以删除不用，我先去理发，回来继续做
     * @param
     * @return
     */
    //根据物品状态给物品分类
    //查看所有物品信息(已经找到)
//    @GetMapping("allArticlesByFind")
//    public R<List<Articles>> allArticlesByFind(int startIndex){
//        return R.success(articlesService.selectArticlesByStatus(3,startIndex));
//    }
//    //查看所有物品信息（无人认领）
//    @GetMapping("allArticlesByTake")
//    public R<List<Articles>> allArticlesByTake(int startIndex){
//        return R.success(articlesService.selectArticlesByStatus(2,startIndex));
//    }
//    //查看所有物品信息（丢失中）
//    @GetMapping("allArticlesByLost")
//    public R<List<Articles>> allArticlesByLost(int startIndex){
//        return R.success(articlesService.selectArticlesByStatus(1,startIndex));
//    }
//
//    @GetMapping("theSumOfSelectArticlesByStatus")
//    public R<Integer> theSumOfSelectArticlesByStatus(int status){
//        return R.success(articlesService.theSumOfSelectArticlesByStatus(status));
//    }

    //失物认领,捡到者和主人都可以调用此等方法。前端标签可示为：我捡到了，我是失主
    //前端传来物品id即可
    @GetMapping("/findOwner")
    public R<String> findOwner(HttpServletRequest request,
                                     Long articlesId){
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
        redisTemplate.delete("articles*");
        return R.success(phone);
    }

    //我的丢失(面向主人)
    @GetMapping("/myLost")
    public R myLost(HttpServletRequest request){
        String id = (String) request.getSession().getAttribute("usager");
        List<Articles> articles = articlesService.selectByMe(id,1);
        if (articles.isEmpty()) return R.error("未获得您的物品丢失信息");
        return R.success(articles);
    }
    //我的上传（面向拾者）
    @GetMapping("/myUpload")
    public R myUpload(HttpServletRequest request){
        String id = (String) request.getSession().getAttribute("usager");
        List<Articles> articles = articlesService.selectByMe(id,2);
        if (articles == null) return R.error("未获得您上传的物品信息");
        return R.success(articles);
    }

    //我的认领
    @GetMapping("/myClaim")
    public R myClaim(HttpServletRequest request){
        String id = (String) request.getSession().getAttribute("usager");
        List<Articles> articles = articlesService.selectByMe(id,3);
        if (articles.isEmpty()) return R.error("您当前暂未认领任何物品");
        return R.success(articles);
    }

    //取消认领.需要先确定物品确实被操作者认领了
    @GetMapping("/cancelClaim")
    public R<String> cancelClaim(HttpServletRequest request,Long articlesId){
        Articles articles = articlesService.getArticlesById(articlesId);
        String id = (String) request.getSession().getAttribute("usager");
        if (articles == null || articles.getOwner() == null || articles.getOwner().equals(id))
            return R.error("黑客请勿攻击！");

        if (articles.getOwner().equals(id)){
            articles.setStatus(2);
            int code = articlesService.changeStatus(articles);
            if (code == 1){
                redisTemplate.delete("articles*");
                return R.success("操作成功！");
            }

        }
        return R.error("请不要给我的项目搞破坏！我要生气了！");
    }

    //删除"我丢失的"物品信息
    @DeleteMapping("/deleteMyLost")
    public R<String> deleteArticles(HttpServletRequest request,Long id){
        String usagerId = (String) request.getSession().getAttribute("usager");
        Articles articles = articlesService.getArticlesById(id);
        if (articles == null || articles.getOwner() == null
                ||!articles.getOwner().equals(usagerId))
            return R.error("Please don't disturb my project!！");

        articlesService.deleteArticles(id);
        redisTemplate.delete("articles*");
        return R.success("删除数据成功！");
    }
    //删除“我上传的”物品信息
    @DeleteMapping("/deleteMyUpload")
    public R<String> deleteMyUpload(HttpServletRequest request,Long id){
        String usagerId = (String) request.getSession().getAttribute("usager");
        Articles articles = articlesService.getArticlesById(id);
        if (articles == null || articles.getTakerId() == null
                || !articles.getTakerId().equals(usagerId))
            return R.success("Please don't disturb my project!！");

        articlesService.deleteArticles(id);
        redisTemplate.delete("articles*");
        return R.success("删除数据成功！");
    }

    @GetMapping
    public String egg(){
        redisTemplate.delete("articles*");
        return "Made by 张培阳";
    }
}
