package com.wzliulan.demo.spca.content;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class SpcaContentApplicationTests {

    void contextLoads() {
    }

    // 批量发送请求至 /actuator/sentinel
//    public static void main(String[] args) throws InterruptedException {
//        RestTemplate restTemplate = new RestTemplate();
//        for (int i=0;i<10000;i++) {
//            String result = restTemplate.getForObject("http://localhost:8101/actuator/sentinel", String.class);
//            Thread.sleep(200); // 为了让10000个请求没那么快被消耗掉，这里每隔200毫秒发送一次请求过去
//        }
//    }

    // 批量发送请求至 /test/t-a，测试【排队等待】流控效果
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        for (int i=0;i<10000;i++) {
            String result = restTemplate.getForObject("http://localhost:8101/test/t-a", String.class);
            System.err.println("---"+result);
        }
    }

}
