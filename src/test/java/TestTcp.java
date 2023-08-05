import com.yesterz.netty.client.ClientRequest;
import com.yesterz.netty.client.Response;
import com.yesterz.netty.client.TcpClient;
import org.junit.Test;

public class TestTcp {

    @Test
    public void testGetResponse() {
        ClientRequest request = new ClientRequest();
        request.setContent("测试TCP长连接请求");
        Response response = TcpClient.send(request);
        System.out.println(response.getResult());
    }
}
