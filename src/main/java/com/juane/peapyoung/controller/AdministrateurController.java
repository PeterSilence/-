package com.juane.peapyoung.controller;
import com.juane.peapyoung.common.Code;
import com.juane.peapyoung.common.R;

import com.juane.peapyoung.entity.Administrateur;

import com.juane.peapyoung.entity.Usager;
import com.juane.peapyoung.service.AdministrateurService;
import com.juane.peapyoung.service.UsagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/administrateur")
public class AdministrateurController {
    @Autowired
    private AdministrateurService aService;
    @Autowired
    private UsagerService usagerService;

    //获取所有数据（只能给等级更高的管理员使用）
    @GetMapping("/getData")
    public R getData(HttpServletRequest request){
        String id = (String) request.getSession().getAttribute("administrateur");
        List<Administrateur> list = aService.getData(id);
        Integer code = list == null ? Code.GET_ERR : Code.GET_OK;
        String msg = list == null ? "您的权限不足,无法查看其他管理员信息" : "";
        return new R(list,msg,code);
    }

    //保存新增数据（这个也应该交给权限更高的管理员进行操作）
    @PostMapping("/saveData")
    public R saveData(HttpServletRequest request,
                      @RequestBody Administrateur administrateur){
        int a = aService.saveData((String) request.getSession()
                .getAttribute("administrateur"), administrateur);
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
                code = 1;
        }
        return new R(new Administrateur(),msg,code);
    }

    //更新数据
    @PostMapping("/updateData")
    public R<String> updateData(@RequestBody Administrateur administrateur){
        int code = aService.updateData(administrateur);
        if (code == 1)
            return R.success("修改管理员信息成功！");
        else return R.success("发现未知错误，请重试！");
    }

    //删除管理员信息（逻辑删除而非物理删除，需要有更高级管理员操作）
    @DeleteMapping("/deleteByHa")
    public R<String> deleteByHa(HttpServletRequest request, String id){
        String currentId = (String) request.getSession().getAttribute("administrateur");
        System.out.println("administrateur" + currentId);
        Administrateur administrateur = aService.isIdExist(currentId);
        if (administrateur != null && administrateur.getStatus() == 6){
            int code = aService.changeAdministrateurStatus(3,id);
            if (code == 1)
                return R.success("增加删除成功！");
            else return R.error("发生未知错误，请重试！");
        }
        return R.error("当前管理员权限不足，无法完成操作");
    }

    //恢复账号状态，给高级管理员使用
    @PutMapping("/restore")
    public R<String> restore(HttpServletRequest request,String id){
        String currentId = (String) request.getSession().getAttribute("administrateur");
        System.out.println("administrateur" + currentId);
        Administrateur administrateur = aService.isIdExist(currentId);
        if (administrateur != null && administrateur.getStatus() == 6){
            int code = aService.changeAdministrateurStatus(2,id);
            if (code == 1)
                return R.success("数据恢复成功！");
            else return R.error("发生未知错误，请重试！");
        }
        return R.error("当前管理员权限不足，无法完成操作");
    }

    //根据条件查找数据，可用于查看个人信息
    @PostMapping("/selectByConditions")
    public R<List<Administrateur>> selectByConditions(@RequestBody Administrateur administrateur){
        return R.success(aService.selectByConditions(administrateur));
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
    public R<List<Usager>> allUsagers(){
        return R.success(usagerService.selectAllUsagers());
    }


    //审核通过用户信息
    @GetMapping("/passUsagerInfo")
    public R<String> passUsagerInfo(HttpServletRequest request,String id){
        String currentId = (String) request.getSession().getAttribute("administrateur");
        int code = usagerService.modifyStatus(id,currentId,1);
        if (code == 1){
            return R.success("操作成功！");
        }
        return R.error("发生未知错误，请重试！");
    }
    //驳回用户信息
    @GetMapping("backUsagerInfo")
    public R<String> backUsagerInfo(HttpServletRequest request,String id){
        String currentId = (String) request.getSession().getAttribute("administrateur");
        int code = usagerService.modifyStatus(id,currentId,5);
        if (code == 1){
            return R.success("操作成功！");
        }
        return R.error("发生未知错误，请重试！");
    }
    //停用用户账号
    @GetMapping("/stopUsagerAccount")
    public R<String> stopUsagerAccount(HttpServletRequest request,String id){
        String currentId = (String) request.getSession().getAttribute("administrateur");
        int code = usagerService.modifyStatus(id,currentId,3);
        if (code == 1)
            return R.success("操作成功!");
        return R.error("发生错误，请重试！");
    }

    //筛选出所有待审核账号
    @GetMapping("/allUsagerForChecking")
    public R<List<Usager>> allUsagerForChecking(){
        return R.success(usagerService.selectUsagerByStatus(2));
    }
}
