package com.lon;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

public class JedisTest {

    @Test
    public void testJedis() {
        Jedis jedis = new Jedis("localhost", 6379);

        jedis.set("username", "xiaoming");

        String username = jedis.get("username");

        System.out.println(username);

        jedis.close();
    }
}
