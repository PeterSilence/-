package com.juane.peapyoung;
import com.juane.peapyoung.service.AdministrateurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@SpringBootTest
class PeapyoungApplicationTests {
    @Autowired
    private AdministrateurService aService;
    //使用Jedis操作Redis
    @Autowired
    private RedisTemplate redisTemplate;


//    @Test
//    void redis() {
//        //获取连接
//        Jedis jedis = new Jedis("localhost",6379);
//        //执行具体操作
//        Set<String> keys = jedis.keys("*");
//        for (String key : keys) {
//            System.out.print(key+" ");
//        }
////        jedis.hset("myhash","addr","HeNan");
////        System.out.println(jedis.hget("myhash", "addr"));
//        //关闭连接
//        jedis.close();
//    }
    @Test
    void springDataRedis(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name","Juane");
        System.out.println(valueOperations.get("name"));
    }

    //操作字符串类型数据
    @Test
    void testString(){
        //存放字符串
        redisTemplate.opsForValue().set("city","Paris");
        //给字符串设置存活周期
        redisTemplate.opsForValue().set("name","Peter",10l, TimeUnit.SECONDS);
        //如果key已经存在值就不做任何改变，返回布尔类型的值
        redisTemplate.opsForValue().setIfAbsent("city","New York");
    }
    //操作哈希数据类型
    @Test
    void testHash(){
        //存数据到哈希表
        redisTemplate.opsForHash().put("student","name","BOHEME");
        redisTemplate.opsForHash().put("student","tel","17637463945");
        //取数据
        redisTemplate.opsForHash().get("student","name");

        //获取哈希结构所有字段
        Set keys = redisTemplate.opsForHash().keys("student");
        for (Object key : keys) {
            System.out.print(key + " ");
        }

        //获取哈希结构中所有值
        List lists = redisTemplate.opsForHash().values("student");
        for (Object list : lists) {
            System.out.print(list + " ");
        }
    }

    //操作列表
    @Test
    void testList(){

        //存值
        redisTemplate.opsForList().leftPush("myList","a");
        redisTemplate.opsForList().leftPushAll("myList","b","c");

        //取值
        List<String> myList = redisTemplate.opsForList().range("myList", 0, -1);
        for (String s : myList) {
            System.out.print(s + " ");
        }

        //获取列表长度
        Long length = redisTemplate.opsForList().size("myList");
        System.out.println(length);

        int size = length.intValue();
        for (int i = 0; i < size; i++) {
            //出队列
            System.out.print(redisTemplate.opsForList().rightPop("myList") + " ");
        }
    }

    //操作set类型数据,去重，无序
    @Test
    void testSet(){
        //存值
        redisTemplate.opsForSet().add("mySet","a","b","c","a");
        //取值
        Set<String> strings = redisTemplate.opsForSet().members("mySet");
        for (String string : strings) {
            System.out.println(string);
        }
        //删除成员
        redisTemplate.opsForSet().remove("mySet","a","b");
    }

    //操作ZSet,有序，去重
    @Test
    void testZSet(){
        //存值
        redisTemplate.opsForZSet().add("MyZSet","A",10.0);
        redisTemplate.opsForZSet().add("MyZSet","B",11.0);
        redisTemplate.opsForZSet().add("MyZSet","C",12.0);
        //取值
        Set<String> strings = redisTemplate.opsForZSet().range("MyZSet",0,-1);
        for (String string : strings) {
            System.out.println(string);
        }
        //修改分数
        redisTemplate.opsForZSet().incrementScore("MyZSet","b",25.2);
        //删除成员
        redisTemplate.opsForZSet().remove("MyZSet","a","b");
    }

    //通用操作
    @Test
    void general(){
        //获取Redis中所有key
        Set<String> keys = redisTemplate.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
        //判断某个key是否存在
        Boolean isKeyExist = redisTemplate.hasKey("it");
        System.out.println(isKeyExist);
        //删除指定key
        redisTemplate.delete("MyZSet");
        //获取指定key对应的value数据类型
        DataType mySet = redisTemplate.type("MySet");
        System.out.println(mySet.name());
    }
}
