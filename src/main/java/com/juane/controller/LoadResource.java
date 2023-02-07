package com.juane.controller;
import com.juane.common.R;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class LoadResource {
    @Value("${peapyoung.path}")
    private String studentCardImagePath;

    //文件上传:功能在于更改用户上传的图片名并存储用户上传的图片文件，最后返回修改后的文件名
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        //原始文件名
        String originName = file.getOriginalFilename();
        //保存后缀名
        String suffix = originName.substring(originName.lastIndexOf("."));
        //使用UUID重新生成文件名，防止文件名重复而被覆盖
        String fileName = UUID.randomUUID() + suffix;

        //创建一个目录对象
        File dir = new File(studentCardImagePath);
        if (!dir.exists()){
            //如果文件名不存在，就创建之
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(studentCardImagePath + fileName));
        } catch (IOException e) {
            log.info(e.getMessage());
            e.printStackTrace();
            return R.error("发生存储错误");
        }
        //把改好的文件名传递回去
        return R.success(fileName);
    }

    //下载功能：作用在于根据用户提供的文件名找到源文件并返回之
    @GetMapping ("/download")
    public void download(String name, HttpServletResponse response){
        //name是文件名
        try {
            //通过输入流获取文件内容
            FileInputStream fileInputStream = new FileInputStream(studentCardImagePath+name);
            //通过输出流写回浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int length;
            byte[] bytes = new byte[1024];
            while ((length = fileInputStream.read(bytes)) != -1){
                //写bytes数组中的数据，起点为off，读取length长度的数据
                outputStream.write(bytes,0,length);
                outputStream.flush();
            }

            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
