import com.yesterz.netty.client.ClientRequest;
import com.yesterz.netty.client.Response;
import com.yesterz.netty.client.TcpClient;
import org.junit.Test;

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
}
