package com.ecmoho.token;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RedisSessionCache {
    public static final String CURRENT_SESSION = "_current_session";
    public static final String CURRENT_SESSION_ID = "_current_session_id";
    public static final String SESSION_SHOP_MERCHANT_KEY = "shop:merchant";//商家对象
    public static final String LOGIN_SHOP_OPEN_AUTH = "login:shop:auth";//商家购买的服务授权信息
    public static final String LOGIN_SHOP = "login:shop";//登陆的店铺信息
    public static final String LOGIN_SHOP_CAT_ID = "login:shop:cat:id";//店铺行业ID

    public static final String SESSION_REGION="region";

    public static final String SESSION_USERS_KEY = "user:token";//2c用户标示
    public static final String SESSION_USERS_ANONY_KEY = "anony:user:token";//2c回话token标示
    public static final int sessionTimeout = 24*60*365*10;//回话时间


    private RedisTemplate redisTemplate;

    //延迟缓存所有的bean序列号模板
    private Map<String, RedisTemplate> beanRedisTemplate = new HashMap<>();


    private RedisTemplate getBeanRedisTemplate(Class clazz) {
        String clazzName = clazz.getName();
        if (beanRedisTemplate.get(clazzName)!=null) {
            return beanRedisTemplate.get(clazzName);
        }
        RedisTemplate jsonRedisTemp = new Jackson2JsonRedisTemplate(clazz);
        jsonRedisTemp.setConnectionFactory(redisTemplate.getConnectionFactory());
        jsonRedisTemp.afterPropertiesSet();

        beanRedisTemplate.put(clazzName, jsonRedisTemp);
        return jsonRedisTemp;
    }

    private RedisTemplate getBeanRedisTemplate(TypeReference typeReference) {
        String clazzName = typeReference.getClass().getGenericSuperclass().toString();
        RedisTemplate jsonRedisTemp = new Jackson2JsonRedisTemplate(typeReference);
        jsonRedisTemp.setConnectionFactory(redisTemplate.getConnectionFactory());
        jsonRedisTemp.afterPropertiesSet();
        beanRedisTemplate.put(clazzName, jsonRedisTemp);
        return jsonRedisTemp;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Map<String, Object> getSession(String root) {
        Map<String, Object> mapValue = redisTemplate.opsForHash().entries(root);
        return mapValue;
    }

    public void setSession(String root, Map<String, Object> session, int exp) {
        redisTemplate.opsForHash().putAll(root, session);
        redisTemplate.expire(root, exp, TimeUnit.MINUTES);
    }

    /**
     * 返回一个map结构的对象，json序列号可以看成是个map 反序列不封装给对象
     *
     * @param root
     * @param name
     * @return
     */
    public <T> T getAttribute(String root, String name) {
        Object o = redisTemplate.opsForHash().get(root, name);
        return (T)o;
    }

    /**
     * json反序列 成JavaBean对象
     *
     * @param root
     * @param name
     * @param <T>
     * @return
     */
    public <T> T getAttribute(String root, String name,Class tClass) {
        RedisTemplate jsonRedisTemp = getBeanRedisTemplate(tClass);
        T object = (T) jsonRedisTemp.opsForHash().get(root, name);
        return object;
    }

    /**
     * json反序列 成JavaBean对象
     *
     * @param root
     * @param name
     * @param <T>
     * @return
     */
    public <T> T getAttribute(String root, String name,TypeReference typeReference) {
        RedisTemplate jsonRedisTemp = getBeanRedisTemplate(typeReference);
        T object = (T) jsonRedisTemp.opsForHash().get(root, name);
        return object;
    }


    public void setAttribute(String root, String name, Object value) {
        redisTemplate.opsForHash().put(root, name, value);
    }


    public void remove(String root, String... name) {
        redisTemplate.opsForHash().delete(root, name);
    }

    public void clear(String root) {
        redisTemplate.delete(root);
    }

    public boolean exist(String root) {
        return redisTemplate.hasKey(root);
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(redisTemplate);
    }

}
