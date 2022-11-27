package com.juane.peapyoung.controller;
import com.juane.peapyoung.common.Code;
import com.juane.peapyoung.common.R;
import com.juane.peapyoung.entity.Administrateur;
import com.juane.peapyoung.entity.ReceiveBody;
import com.juane.peapyoung.service.AdministrateurService;
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

    //获取所有数据（只能给等级更高的管理员使用）
    @GetMapping("/getData/{id}")
    public R getData(@PathVariable String id){
        List<Administrateur> list = aService.getData(id);
        Integer code = list == null ? Code.GET_ERR : Code.GET_OK;
        String msg = list == null ? "您的权限不足,无法查看其他管理员信息" : "";
        return new R(list,msg,code);
    }

    //保存新增数据（这个也应该交给权限更高的管理员进行操作）
    @PostMapping("/savaData")
    public R savaData(@RequestBody ReceiveBody receiveBody){
        int a = aService.saveData(receiveBody.getId(), receiveBody.getAdministrateur());
        String msg = "";
        Integer code = -1;
        switch (a){
            case 0:
                msg = "您的权限不足，无法执行添加管理员信息操作";
                code = Code.SAVE_ERR;
                break;
            case 1:
                msg = "当前管理员id已经存在，请勿重复添加";
                code = Code.SAVE_ERR;
                break;
            case 2:
                msg = "添加管理员成功！";
                code = Code.SAVE_OK;
        }
        return new R(new Administrateur(),msg,code);
    }

    //更新数据
    @PostMapping("/updateData")
    public String updateData(@RequestBody Administrateur administrateur){
        aService.updateData(administrateur);
        return "修改管理员信息成功！";
    }

    //删除管理员信息（逻辑删除而非物理删除，需要有更高级管理员操作）
    @DeleteMapping("/deleteByOwner")
    public String deleteByOwner(@RequestBody Map<String,String> map){
        return aService.deleteByOwner(map.get("currentId"), map.get("targetId"));
    }

    //根据条件查找数据，可用于查看个人信息
    @PostMapping("/selectByConditions")
    public List<Administrateur> selectByConditions(@RequestBody Administrateur administrateur){
        return aService.selectByConditions(administrateur);
    }

    //登录验证
    @PostMapping("/login")
    public R<String> login(HttpServletRequest request, @RequestBody Map<String,String> map){
        Administrateur administrateur = aService.isAdministrateurExist
                (map.get("id"),map.get("password"));
        if (administrateur == null){
            return R.error("账号或密码错误，请核实后重试");
        }else if (administrateur.getStatus() == 0){
            return R.error("当前账号处于封禁状态中，可联系高级管理员申诉");
        }
        request.getSession().setAttribute("administrateur",administrateur.getName());
        return R.success(administrateur.getName());
    }
    @PostMapping("/logout")
    public R<String> login(HttpServletRequest request){
        request.getSession().removeAttribute("administrateur");
        return R.success("注销登录成功！");
    }
}
