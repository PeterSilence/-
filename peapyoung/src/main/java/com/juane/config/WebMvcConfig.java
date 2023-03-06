package com.juane.config;

import com.juane.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurationSupport {
    //设置静态资源映射,浏览器输入地址映射到项目文件路径
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //项目内静态资源
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        //把对front的访问映射到存储图片的本地目录。项目外静态资源
        registry.addResourceHandler("/front/**").addResourceLocations("file:/privateProjects/studentCardImg/");
        System.out.println("地址转制成功！");
    }
    //扩展mvc框架的消息转换器
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器，底层使用Jackson将Java转换为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将上面的消息转换器对象追加到mvc框架的消息转换器集合中
        converters.add(0,messageConverter);
    }
}
