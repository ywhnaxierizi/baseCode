package common;

import com.ywh.base.BaseApplication;
import com.ywh.base.common.dao.UserMapper;
import com.ywh.base.common.domain.User;
import com.ywh.base.service.auth.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BaseApplication.class)
@WebAppConfiguration
public class CommonTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @Test
    public void dataSourceTest() throws SQLException {
       /* String sql = "select * from user";
        List<User> count =  jdbcTemplate.query(sql, (Object[]) null, new BeanPropertyRowMapper(User.class));
        System.out.println(count);*/
        /*int count = userMapper.count();
        System.out.println(count);*/
        Condition condition = new Condition(User.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("userName","zhangsan");
        List<User> userList = userMapper.selectByExample(condition);
        System.out.println(userList);
    }

    @Test
    public void UserServiceTest() {
        List<User> users = userService.selectAll();

        users.stream().forEach(s -> System.out.println(s));
        users = users.stream().filter(s -> s.getId().equals(1)).collect(Collectors.toList());
        User user = new User();
        if (!CollectionUtils.isEmpty(users)) {
            user = users.get(0);
        }
        userService.deleteByPrimaryKey(1);
        userService.insert(user);
    }
}
