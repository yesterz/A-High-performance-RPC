import com.yesterz.client.annotation.RemoteInvoke;
import com.yesterz.user.bean.User;
import com.yesterz.user.remote.UserRemote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestRemoteInvoke.class)
@ComponentScan("com.yesterz")
public class TestRemoteInvoke {

    @RemoteInvoke
    private UserRemote userRemote;

    @Test
    public void testSaveUser() {
        User old6 = new User();
        old6.setId(6);
        old6.setName("1个老6");
        userRemote.saveUser(old6);
    }

    @Test
    public void testSaveUsers() {
        List<User> users = new ArrayList<User>();
        User old6 = new User();
        old6.setId(1);
        old6.setName("老6们");
        users.add(old6);

        userRemote.saveUsers(users);
    }

}
