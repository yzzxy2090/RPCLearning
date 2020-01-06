import com.zxyrpc.handler.ServerHandler;
import com.zxyrpc.service.implement.UserServiceImpl;

public class Server {

    public static void main(String[] args) {

        //服务端开启处理过程
        ServerHandler serverHandler = new ServerHandler();

        //这里服务端使用的服务过程是写死的，可以使用注册中心进行扩展
        UserServiceImpl targetUserService = new UserServiceImpl();
        serverHandler.handle(targetUserService, 1234);
    }
}