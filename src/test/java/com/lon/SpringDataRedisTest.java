package com.lon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringDataRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 操作string类型数据
     */
    @Test
    public void testString() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String city1 = (String) valueOperations.get("city1");
        System.out.println(city1);
    }

    /**
     * 操作Hash类型数据
     */
    @Test
    public void testHash() {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("007", "name", "xiaoming");
        hashOperations.put("007", "age", "18");
        hashOperations.put("007", "addr", "guangzhou");

        Set keys = hashOperations.keys("007");
        for (Object key : keys) {
            System.out.println(key);
        }

        List values = hashOperations.values("007");
        for (Object value : values) {
            System.out.println(value);
        }
    }

    /**
     * 操作List类型数据
     */
    @Test
    public void testList() {
        ListOperations listOperations = redisTemplate.opsForList();

        //存值
        listOperations.leftPush("list1", "a");
        listOperations.leftPushAll("list1", "1", "2");

        //取值
        List<String> list1 = listOperations.range("list1", 0, -1);

        for (String value : list1) {
            System.out.println(value);
        }

        //获取列表长度
        Long size = listOperations.size("list1");
        int lSize = size.intValue();

        for (int i = 0; i < lSize; i++) {
            //出队列
            String element = (String) listOperations.rightPop("list1");
            System.out.println(element);
        }
    }

    /**
     * 操作set类型数据
     */

    @Test
    public void testSet() {
        SetOperations setOperations = redisTemplate.opsForSet();
        setOperations.add("myset", "1", "2", "3", "3", "5");

        Set<String> myset = setOperations.members("myset");

        for (String s : myset) {
            System.out.println(s);
        }
    }

    /**
     * 操作ZSet类型数据
     */
    @Test
    public void testZSet() {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        //存
        zSetOperations.add("Zset", "a", 10.1);
        zSetOperations.add("Zset", "a", 10.2);
        zSetOperations.add("Zset", "b", 10.0);
        zSetOperations.add("Zset", "c", 10.9);

        //取
        Set<String> zset = zSetOperations.range("Zset", 0, -1);
        for (String s : zset) {
            System.out.println(s);
        }

        //改
        zSetOperations.incrementScore("Zset", "c", 10);

        //删
        zSetOperations.remove("Zset", "a");
    }

    /**
     * 通用操作,针对不同的数据类型都可以操作
     */
    @Test
    public void testCommon() {
        //获取Redis中所以的key
        Set<String> keys = redisTemplate.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }

        //判断某个key是否存在
        Boolean aBoolean = redisTemplate.hasKey("long");
        System.out.println(aBoolean);

        //删除指定key
        Boolean zset = redisTemplate.delete("Zset");
        System.out.println(zset);

        //获取指定key对应的value数据类型
        DataType dataType = redisTemplate.type("myset ");
        System.out.println(dataType.name());
    }
}
