package utils;

import com.ywh.base.auth.AuthServerApplication;
import com.ywh.base.common.utils.AesUtils;
import com.ywh.base.common.utils.Md5Utils;
import com.ywh.base.common.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.concurrent.TimeUnit;


/**
 * @author ywh
 * @description
 * @Date 2021/11/19 13:13
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServerApplication.class)
@WebAppConfiguration
public class RedisTest {

    @Autowired
    private RedisUtils redisUtils;


    @Test
    public void setValueTest() {
        String key = "verify:test";
        String value = "testvalue";
        redisUtils.setValue(key, value, 3, TimeUnit.MINUTES);
        System.out.println(redisUtils.getValue("test"));
        redisUtils.deleteKey("test");
        System.out.println(redisUtils.getValue(key));
    }

    @Test
    public void passwordTest() throws Exception {
        String password = "123456";
        System.out.println(AesUtils.encrypt(password));
        System.out.println(Md5Utils.mdsEncode(password));

    }
}

