package com.juane.controller;

import com.juane.common.BaseContext;
import com.juane.common.R;
import com.juane.entity.Usager;
import com.juane.service.UsagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/usager")
public class UsagerController {
    @Autowired
    private UsagerService uService;
    /*
    登录验证
     */
    @PostMapping("/login")
    public R<String> login(HttpServletRequest request,@RequestBody Map<String,String> map){
        String id = map.get("id");
        String password = map.get("password");
        Usager usager = uService.selectUsagerById(id);
        if (usager == null){
            return R.error("账号不存在，请核实后重试");
        }else if (!usager.getPassword().equals(password)){
            return R.error("密码错误，请重试！");
        } else if (usager.getStatus() == 3){
            return R.error("该账号已经停用，可联系管理员进行申诉");
        }else if (usager.getStatus() == 4){
            return R.error("改账号已经注销，请重新注册使用");
        }
        BaseContext.setCurrentId(id);
        request.getSession().setAttribute("usager",id);
        String name = "欢迎您："+usager.getName() + "同学";
        return new R(name,"登录成功！",1);
    }

    //注册账号
    @PostMapping("/enregister")
    public R<String> enregister(@RequestBody Usager usager){
        usager.setGmt_create(LocalDate.now());
        Usager usager1 = uService.selectUsagerById(usager.getId());
        if (usager1 == null){
            uService.savaUsager(usager);
            return R.success("恭喜您账号注册成功!");
        }
        //4代表注销态，是用户自己操作的，如果用户想要恢复账号正常状态，也是可以的
        if (usager1.getStatus() == 4 || usager1.getStatus() == 5){
            uService.modifyStatus(usager.getId(), null,2);
            return R.success("账号重新注册成功，等待管理员审核");
        }else
            return R.error("注册失败，当前账号已被注册!");
    }

    //修改用户信息
    @PostMapping("/updateUsager")
    public R<String> updateUsager(HttpServletRequest request,@RequestBody Usager usager){
        usager.setId((String) request.getSession().getAttribute("usager"));
        int result = uService.updateUsager(usager);
        if (result == 1)
            return R.success("用户信息修改成功！");
        else return R.error("保存错误！");
    }

    //根据id查看个人信息
    @GetMapping("/selectById")
    public R<Usager> selectUsagerById(HttpServletRequest request){
        String id = (String) request.getSession().getAttribute("usager");
        Usager usager = uService.selectUsagerById(id);
        usager.setPassword(null);
        return R.success(usager);
    }

    //注销账号
    @GetMapping("/deleteAccount")
    public R<String> deleteAccount(HttpServletRequest request){
        String id = (String) request.getSession().getAttribute("usager");
        int code = uService.modifyStatus(id, id, 4);
        if (code == 1)
            return R.success("账号注销成功！");
        else return R.error("发生不可知错误，请重试！");
    }

    //登出账号
    @GetMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("usager");
        return R.success("退出登录成功！");
    }

    //修改密码
    @PostMapping("/changePassword")
    public R<String> changePassword(HttpServletRequest request,
                                    @RequestBody Map<String,String> map){
        String id  = (String) request.getSession().getAttribute("usager");
        int code = uService.changePassword(id,map.get("pastCode"),map.get("newCode"));
        if (code == 1)
            return R.success("修改密码成功");
        else
            return R.error("原密码不正确，操作失败");
    }
}
