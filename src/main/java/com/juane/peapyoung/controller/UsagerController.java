package com.juane.peapyoung.controller;

import com.juane.peapyoung.common.BaseContext;
import com.juane.peapyoung.common.R;
import com.juane.peapyoung.entity.Usager;
import com.juane.peapyoung.service.UsagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/usager")
public class UsagerController {
    @Autowired
    private UsagerService uService;
    @PostMapping("/login")
    public R<String> login(HttpServletRequest request,@RequestBody Map<String,String> map){
        String id = map.get("id");
        String password = map.get("password");
        Usager usager = uService.login(id,password);
        if (usager == null){
            return R.error("账号或密码错误，请核实后重试");
        }else if (usager.getStatus() == 3){
            return R.error("该账号已经停用，可联系管理员进行申诉");
        }
        BaseContext.setCurrentId(id);
        request.getSession().setAttribute("usager",map.get("id"));
        return R.success("登录成功！");
    }

    //注册账号
    @PostMapping("/enregister")
    public R<String> enregister(@RequestBody Usager usager){
        Usager usager1 = uService.selectUsagerById(usager.getId());
        if (usager.getStatus() == 4){
            uService.modifyStatus(usager.getId(), null,2);
            return R.success("注册成功，等待管理员审核");
        }else if (usager1 != null){
            return R.error("注册失败，当前账号已被注册!");
        }
        uService.savaUsager(usager);
        return R.success("恭喜您账号注册成功!");

    }

    //修改用户信息
    @PostMapping("/updateUsager")
    public R<String> updateUsager(HttpServletRequest request,@RequestBody Usager usager){
        usager.setId((String) request.getSession().getAttribute("usager"));
        uService.updateUsager(usager);
        return R.success("用户信息修改成功！");
    }

    //根据id查看个人信息
    @GetMapping("/selectById")
    public R<Usager> selectUsagerById(String id){
        return R.success(uService.selectUsagerById(id));
    }

    //修改用户状态:正常态、审核态、停用态
    @PostMapping("/modifyStatus")
    public R<String> modifyStatus(String id,String cId,int status){
        uService.modifyStatus(id, cId, status);
        return R.success("操作成功！");
    }

    @GetMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("usager");
        return R.success("退出登录成功！");
    }

}
