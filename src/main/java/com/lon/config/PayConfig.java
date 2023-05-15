package com.lon.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ctl
 */
@Slf4j
@Configuration
public class PayConfig {

    public static final String APP_ID = "2021000122619290";

    public static final String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCEixOHFuueekQWqlOM6ZGI8IeFayybpONQJJl9+wIfXrMisOJTjGP3g3EjE0GJv1g+wu4EdlozTpC2V8rmJViBnVWEYCygamLBIZm+nI+mC0pYZYXhiaNG/4YpEjopqr+kP9VnI4+69fhcMv+GKpXR+Y9dIzGL00+Dl9oMqtUXIb0mbv1qgvZqcwn2zqxTTt1ofR1TOh3ZnY0a4SwpIjyuNlsUM7jSY1AtxQQmtIyNEjxTT/VWe4/HxWjPbtmnPHCdc/cHQP+SQhmk5n7L4zLiWKSyCacezyG8yjgTuLUmuFfQ8AUFPYwouXoUEykHL9BvuvytBDsVLdUJaexArp4VAgMBAAECggEAPcfl7k4Mve4X1sjF8STeJYqYYOQ4AsS1ACJPOZ3Ku3yU21K/7FnALxshnUvYfLCoHXkXIRVZrhis85Gwjd5ShvgjaHS72Ho+Ve+njmXDv437tYB9C0fwx6NLkdFhMxLYCWOHwW+eC3OjtLZAzPUEqL8+yBcjfR2eLqSVitoGQaVYmFoDZZo8nrzEF3YZPyZFPURfiqkvsSLKhiSkP2lbCd1SlZVdSAsl5sW54ZNdw8UQZAyQ8r5av1PGUk582fq6v6yFe4fPz04HOgXcFSjJf7Io/bAJ/3dw7NRfSOudJ6BDCzpnQAeYLN7Ek6C3qiubnz6DmBJF985CAO6TAznW8QKBgQC7tiO+93tLWP5gswZWL19siQAUYEWzPul+PblotA/+92y9rdTia6ZRQ9bas+LWkOn0RpzAltIHu6QN0vq0S6aeL/eo37DeXRBtAqCUkSJ23rPlckXuFQgwbS6LR7oLplZlwY1dLs3/xOvMDcntzqV6TB8OCnpaDrh66pAn8QGOdwKBgQC0wwkTWDMPL/K+WPumtuU/TJ6MmBtQif00KjolgX5+UMezTFvL7lPByfRTBTqAy1xOAcw0UZXGaniHnmMOqP0grdoe9N5OZsose4wXH1NMr9gHSXodAINk/V7ELwZr7gSZxR/LP7wkZ12SyRDa23VngzoG6n4Yle0srDzJ073e0wKBgQCmSv6x8DcnEikQ66iic4da9KfAZWV6HTLnZrE5w8MkAngwNa6SBAIsnifaSmCb63eHs4KszzA0L5RvtsfpL9fk7eO+lKTw6Ytit2AhHc5ldnfyveCbwcAmfqMsOpfkODD0JpsgwRU4EHFArbqFVXuds5ATdU7dN4tRQToCWG0jkwKBgH1F6YVBi+lLlpR6gLTybgAnbn4LcGqppxQ+65tQmkSaYTwOgFPdsKhN+QiMSBY4WZkqP8ZGgGFJKoz6pLXlQcAjHFZ155c4P4cCfH0N1aIA/9tU/c8ZqvYTbljSKkYkZ4XWConGevJTvJLcy9SRYVVCqbVfT9JkHUHngB1zpFPHAoGAVQJFnWpLf0SbZwXhETJMnaYDNiVCByDFCBIbstRnUdi34N6KLXcCaqx8murjHfCC/pmoIkPwE2SRLPGHLon6ldvzGUe1sBTH+2V1WpXasqsnOG3HZ82lRfW+WdcEOl296wD69L9GvufUfK7ATZHvZXVRygNS6eK8JZrJdpviJds=";

    public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh6bn5WR7w6JW0ipghEhyPfkc+xNMHfn1CNa7JQiPR0KjeAVLEGgbzxcrNUGiPo56eNzRNW12+eyjwNFKAcbY4+Q/6C/dV1Hb9JOUA6ui7Kj0L1bxqBSqjAy9TSpvPi8Wp4lvlDb3Yvidfa5vYgHfXSmjYFjrrsFnC3667ZsMCOQcO4QST/1HNedSnFGPRpyGYEWO5XM34DX5CA+cQp58NkYOTqPdj+f85GDFp2/AEBHBeaLIsMAvHqw2VdQKOFkZ2jCkKiPHwsJWzqPjkZpONmrhcc0EjXsDhG7XfZNxfx7gd/w8Ky7+zx/LyUe7kG2EJywbd3+0MgiXpdykmcbegQIDAQAB";

    public static final String CHARSET = "utf-8";

    public static final String SIGN_TYPE = "RSA2";

    @Bean
    public AlipayClient alipayClient(){
        return new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", APP_ID, PRIVATE_KEY, "json", CHARSET, PUBLIC_KEY, SIGN_TYPE);
    }

    /**
     * 验签，是否正确
     */
    public static boolean checkSign(HttpServletRequest request){
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, String> paramsMap = new HashMap<>();
        requestMap.forEach((key, values) -> {
            StringBuilder str = new StringBuilder();
            for(String value : values) {
                str.append(value);
            }
            log.info("ZFB验签:" + key + "===>" + str);
            paramsMap.put(key, str.toString());
        });
        //调用SDK验证签名
        try {
            return AlipaySignature.rsaCheckV1(paramsMap, PayConfig.PUBLIC_KEY, PayConfig.CHARSET, PayConfig.SIGN_TYPE);
        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.info("*********************验签失败********************");
            return false;
        }
    }

}
