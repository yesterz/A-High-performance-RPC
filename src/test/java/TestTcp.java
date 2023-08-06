import com.yesterz.netty.client.ClientRequest;
import com.yesterz.netty.util.Response;
import com.yesterz.netty.client.TcpClient;
import com.yesterz.user.bean.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestTcp {

    @Test
    public void testGetResponse() {
        // 构造一个客户端请求
        ClientRequest request = new ClientRequest();
        // 设置客户端请求的内容
        request.setContent("测试TCP长连接请求");
        // 构造服务器返回的响应结果来接收响应结果
        Response response = TcpClient.send(request);
        // 打印出来响应结果
        System.out.println(response.getResult());
    }

    @Test
    public void testSaveUser() {
        ClientRequest request = new ClientRequest();
        User user = new User();
        user.setId(1);
        user.setName("1个老6");
        request.setCommand("com.yesterz.user.controller.UserController.saveUser");
        request.setContent(user);
        Response response = TcpClient.send(request);
        System.out.println(response.getResult());
    }

    @Test
    public void testSaveUsers() {
        ClientRequest request = new ClientRequest();
        List<User> users = new ArrayList<User>();
        User old6 = new User();
        old6.setId(1);
        old6.setName("老6们");
        users.add(old6);
        request.setCommand("com.yesterz.user.controller.UserController.saveUsers");
        request.setContent(users);
        Response response = TcpClient.send(request);
        System.out.println(response.getResult());
    }

} // end of TestTcp Class
