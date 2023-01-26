package com.juane.peapyoung.controller;
import com.juane.peapyoung.common.Code;
import com.juane.peapyoung.common.R;

import com.juane.peapyoung.entity.Administrateur;

import com.juane.peapyoung.entity.Usager;
import com.juane.peapyoung.service.AdministrateurService;
import com.juane.peapyoung.service.UsagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/administrateur")
public class AdministrateurController {
    @Autowired
    private AdministrateurService aService;
    @Autowired
    private UsagerService usagerService;

    @Autowired
    private RedisTemplate redisTemplate;

    //获取所有数据（只能给等级更高的管理员使用）
    @GetMapping("/getData")
    public R getData(HttpServletRequest request){
        String id = (String) request.getSession().getAttribute("administrateur");
        //对访问者进行权限检查
        Administrateur status = aService.isIdExist(id);
        if (status.getStatus() != 6){
            return R.error("当前权限不足");
        }

        List<Administrateur> administrateur = null;
        String key = "administrateur";
        //先从redis中获取缓存数据
        administrateur = (List<Administrateur>) redisTemplate.opsForValue().get(key);
        if (administrateur != null){
            //如果存在，无需查询数据库
            return R.success(administrateur);
        }

        //如果缓存中不存在数据，就从数据库中查询
        List<Administrateur> list = aService.getData(id);
        redisTemplate.opsForValue().set("administrateur",list);
        return R.success(list);
    }

    //保存新增数据（这个也应该交给权限更高的管理员进行操作）
    @PutMapping("/saveData")
    public R saveData(HttpServletRequest request,
                      @RequestBody Administrateur administrateur){
        String id = (String) request.getSession().getAttribute("administrateur");

        administrateur.setGmt_create(LocalDate.now());
        int a = aService.saveData(id, administrateur);
        String msg = "";
        Integer code = -1;
        switch (a){
            case 0:
                msg = "您的权限不足，无法执行添加管理员信息操作";
                code = 0;
                break;
            case 1:
                msg = "当前管理员id已经存在，请勿重复添加";
                code = 0;
                break;
            case 2:
                msg = "添加管理员成功！";
                redisTemplate.delete("administrateur");
                code = 1;
        }
        return new R(null,msg,code);
    }

    //更新数据
    @PostMapping("/updateData")
    public R<String> updateData(HttpServletRequest request,@RequestBody Administrateur administrateur){
        String id = (String) request.getSession().getAttribute("administrateur");
        administrateur.setId(id);
        administrateur.setGmt_modified(LocalDate.now());
        int code = aService.updateData(administrateur);
        if (code == 1){
            //清理缓存中所有管理员信息
            redisTemplate.delete("administrateur");
            return R.success("修改管理员信息成功！");
        }
        return R.success("发现未知错误，请重试！");
    }

    //删除管理员信息（逻辑删除而非物理删除，需要有更高级管理员操作）
    @DeleteMapping("/deleteByHa")
    public R<String> deleteByHa(HttpServletRequest request, String id){
        String currentId = (String) request.getSession().getAttribute("administrateur");
        Administrateur administrateur = aService.isIdExist(currentId);
        if (administrateur == null){
            return R.error("权限不足！");
        }
        if (administrateur.getStatus() == 6){
            int code = aService.changeAdministrateurStatus(3,id);
            if (code == 1){
                redisTemplate.delete("administrateur");
                return R.success("删除管理员信息成功！");
            }
            return R.error("发生未知错误，请重试！");
        }
        return R.error("当前管理员权限不足，无法完成操作");
    }

    //恢复管理员账号状态，给高级管理员使用
    @PutMapping("/restore")
    public R<String> restore(HttpServletRequest request,String id){
        String currentId = (String) request.getSession().getAttribute("administrateur");
        Administrateur administrateur = aService.isIdExist(currentId);
        if (administrateur == null){
            return R.error("权限不足！");
        }
        if (administrateur.getStatus() == 6){
            int code = aService.changeAdministrateurStatus(2,id);
            if (code == 1){
                redisTemplate.delete("administrateur");
                return R.success("数据恢复成功！");
            }
        }
        return R.error("当前管理员权限不足，无法完成操作");
    }

    //根据条件查找数据，可用于查看个人信息
    @PostMapping("/selectByConditions")
    public R<List<Administrateur>> selectByConditions(HttpServletRequest request,@RequestBody Administrateur administrateur){
        String id = (String) request.getSession().getAttribute("administrateur");
        Administrateur code = aService.isIdExist(id);
        if (code != null)
            return R.success(aService.selectByConditions(administrateur));
        return R.error("权限不足！");
    }

    //登录
    @PostMapping("/login")
    public R login(HttpServletRequest request, @RequestBody Map<String,String> map){
        Administrateur administrateur = aService.isAdministrateurExist
                (map.get("id"),map.get("password"));
        if (administrateur == null){
            return R.error("账号或密码错误，请核实后重试");
        }else if (administrateur.getStatus() == 3){
            return R.error("当前账号处于封禁状态中，可联系高级管理员申诉");
        }
        request.getSession().setAttribute("administrateur",administrateur.getId());
        return new R(administrateur.getName(),"登录成功",1);
    }
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("administrateur");
        return R.success("注销登录成功！");
    }

    //查询所有用户信息
    @GetMapping("/allUsagers")
    public R<List<Usager>> allUsagers(HttpServletRequest request,int startIndex){
        String key = "usager_" + startIndex;
        List<Usager> usagers = (List<Usager>) redisTemplate.opsForValue().get(key);
        if (usagers != null){
            return R.success(usagers);
        }

        String id = (String) request.getSession().getAttribute("administrateur");
        Administrateur code = aService.isIdExist(id);
        if (code != null){
            redisTemplate.opsForValue().set(key,code,60,TimeUnit.MINUTES);
            return R.success(usagerService.selectAllUsagers(startIndex));
        }

        return R.error("权限不足！");
    }

    //查询所有用户信息，返回页数
    @GetMapping("/theSumOfAllUsagers")
    public R<Integer> theSumOfAllUsagers(){
        return R.success(usagerService.theSumOfSelectAllUsagers());
    }

    //审核通过用户信息
    @GetMapping("/passUsagerInfo")
    public R<String> passUsagerInfo(HttpServletRequest request,String id){
        String administrateurId = (String) request.getSession().getAttribute("administrateur");
        Administrateur administrateur = aService.isIdExist(administrateurId);
        if (administrateur == null){
            return R.error("权限不足");
        }

        String currentId = (String) request.getSession().getAttribute("administrateur");
        int code = usagerService.modifyStatus(id,currentId,1);
        if (code == 1){
            Set keys = redisTemplate.keys("usager_*");
            redisTemplate.delete(keys);
            return R.success("用户信息验证成功！");
        }
        return R.error("数据库不存在本数据，请重试！");
    }
    //驳回用户信息
    @GetMapping("backUsagerInfo")
    public R<String> backUsagerInfo(HttpServletRequest request,String id){
        String currentId = (String) request.getSession().getAttribute("administrateur");
        Administrateur administrateur = aService.isIdExist(currentId);
        if (administrateur == null){
            return R.error("权限不足！");
        }

        int code = usagerService.modifyStatus(id,currentId,5);
        if (code == 1){
            Set keys = redisTemplate.keys("usager_*");
            redisTemplate.delete(keys);
            return R.success("用户信息驳回成功！");
        }
        return R.error("数据库不存在该数据，请重试！");
    }
    //停用用户账号
    @GetMapping("/stopUsagerAccount")
    public R<String> stopUsagerAccount(HttpServletRequest request,String id){
        String currentId = (String) request.getSession().getAttribute("administrateur");
        Administrateur administrateur = aService.isIdExist(currentId);
        if (administrateur == null){
            return R.error("权限不足！");
        }

        int code = usagerService.modifyStatus(id,currentId,3);
        if (code == 1){
            Set keys = redisTemplate.keys("usager_*");
            redisTemplate.delete(keys);
            return R.success("用户账号停用成功!");
        }

        return R.error("数据库不存在该数据，请重试！");
    }

    //筛选出所有待审核账号
    @GetMapping("/allUsagerForChecking")
    public R<List<Usager>> allUsagerForChecking(HttpServletRequest request,int startIndex){
        String id = (String) request.getSession().getAttribute("administrateur");
        Administrateur administrateur = aService.isIdExist(id);
        if (administrateur == null){
            return R.error("权限不足！");
        }

        return R.success(usagerService.selectUsagerByStatus(2,startIndex));
    }

    //筛选出所有待审核账号，返回页数
    @GetMapping("/theSumOfAllUsagerForChecking")
    public R<Integer> theSumOfAllUsagerForChecking(){
        return R.success(usagerService.theSumOfSelectUsagerByStatus(2));
    }
}
