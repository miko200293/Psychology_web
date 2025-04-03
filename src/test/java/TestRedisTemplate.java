import com.oliver.PsychologyWebApplication;
import com.oliver.entity.Users;
import com.oliver.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = PsychologyWebApplication.class)
public class TestRedisTemplate {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void testRedisUtils(){
        boolean redsis = redisUtils.hasKey("Users");
        System.out.println(redsis);
    }


    @Test
    public void testStringRedisTemplate() {
        stringRedisTemplate.opsForValue().set("name","James");
        Long expire =stringRedisTemplate.getExpire("name");//检测key的超时时间 ，-1代表永不超时，-2表示key不存在 》=0过期时间
        System.out.println(expire);
        stringRedisTemplate.randomKey();//随机获取key
       // stringRedisTemplate.rename("name","name1");//
        stringRedisTemplate.opsForValue().set("code","2732",120, TimeUnit.SECONDS);//设置超时时间
    }

    @Test
    public void testString(){
        Users users =new Users();
        users.setUsername("James");
        users.setPhone("2324232323");
        users.setPassword("23214211231");
        users.setWeChatID("2321e12312e12312");
       // redisTemplate.opsForValue().set("Users",users);
        //redisTemplate.opsForZSet().add("zset",users,99);
        //对哈希表的hashkey进行反序列化，key的序列化我已经配置了在redisConfig里面
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.opsForHash().put("map","dawe",users);
    }

    @Test
    public void  testList(){
        stringRedisTemplate.opsForList().leftPush("name2","lily");
        List<String> names =new ArrayList<>();
        names.add("theresa");
        names.add("cindy");
        stringRedisTemplate.opsForList().leftPushAll("names",names);

        List<String> stringList =stringRedisTemplate.opsForList().range("names",0,1);
        stringList.forEach(value -> System.out.println("value " +value));
    }

    @Test
    public void testSet(){
        stringRedisTemplate.opsForSet().add("sets","theresa","taylor","katy","gaga","olivia");
        Set<String> set1= stringRedisTemplate.opsForSet().members("sets");
        set1.forEach(value -> System.out.println("value " +value));
    }




    //BoundAPI
    @Test
    public void testBoundAPI(){
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        //
        stringRedisTemplate.opsForValue().set("kpop star","taylor swift");
        BoundValueOperations<String, String> name = stringRedisTemplate.boundValueOps("name1");
        name.set("taylor");
        name.append(" swift");
        String s1=name.get();
        System.out.println(s1);

    }

    @Test
    public  void testMood(){
        redisTemplate.setStringSerializer((new StringRedisSerializer()));
        String username="jiao";
        String userword="jie";
        stringRedisTemplate.opsForValue().set(username,userword);
    }

    @Test
    public void testLogin(){
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        String users ="美美姐";
        String users2="公交";

        stringRedisTemplate.opsForValue().set(users,users2);
    }
}
