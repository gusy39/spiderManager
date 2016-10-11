import com.ecmoho.cache.RedisCacheProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 许巧生 on 2016/9/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-redis.xml"})
public class RedisTest {

    @Autowired
    private RedisCacheProvider rediscacheProvider;

    @Test
    public void test() {
        rediscacheProvider.put("aaa", "123");
    }

    @Test
    public void test2() {
       String o =  rediscacheProvider.get("aaa");
        System.out.print("======================================================" + o);
    }

    public RedisCacheProvider getRediscacheProvider() {
        return rediscacheProvider;
    }

    public void setRediscacheProvider(RedisCacheProvider rediscacheProvider) {
        this.rediscacheProvider = rediscacheProvider;
    }
}
