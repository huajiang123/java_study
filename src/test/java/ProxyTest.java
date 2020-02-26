import com.huajiang.example.proxy.ProxyHandler;
import com.huajiang.example.proxy.RealSubject;
import com.huajiang.example.proxy.Subject;
import org.junit.Test;

/**
 * @author jianghua
 * @version v1.0
 * @package PACKAGE_NAME
 * @date 2020/2/26 下午3:49
 * @Copyright
 */
public class ProxyTest {
    @Test
    public void test_01(){
        ProxyHandler proxyHandler = new ProxyHandler();
        Subject subject =(Subject) proxyHandler.bind(new RealSubject());
        subject.sayHello();
    }
}
