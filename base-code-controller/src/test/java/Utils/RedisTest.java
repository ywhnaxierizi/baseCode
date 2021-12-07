
package Utils;

import com.ywh.base.BaseApplication;
import com.ywh.base.common.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;


/**
 * @author ywh
 * @description
 * @Date 2021/11/19 13:13
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BaseApplication.class)
@WebAppConfiguration
public class RedisTest {

    @Autowired
    private RedisUtils redisUtils;


    @Test
    public void setValueTest() {
        String key = "common:test";
        String value = "testvalue";
        redisUtils.setValue(key, value);
        System.out.println(redisUtils.getValue(key));
    }
}

