package com.huangjicheng.top.vepjob.test;

import com.huangjicheng.top.vepjob.VepJobApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VepJobApplication.class)
public class VepjobtApplicationTests {

//    @Reference
//    private FilesService filesService;
//
//    @Autowired
//    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void test() {
//        ReadFile readFile = new ReadFile();
//        readFile.execute(filesService,restHighLevelClient);

//        ReadFile readFile = new ReadFile();
//        readFile.execute(null);
    }

    // 第一种模式
    @Test
    public void testMQ1() {
        rabbitTemplate.convertAndSend("hello", "hello word");
    }


    // 第二种模式
    @Test
    public void testMQ2() {
        for (int i = 0; i < 100; i++) {
            rabbitTemplate.convertAndSend("work", "------------------------work");
        }
    }

    // 第3种模式
    @Test
    public void testMQ3() {
        rabbitTemplate.convertAndSend("logs", "", "------------------------logs");
    }

    // 第4种模式 direct
    @Test
    public void testMQ4() {
        rabbitTemplate.convertAndSend("directs", "info", "info------------------------logs");
    }

    // 第4种模式 topic
    @Test
    public void testMQ5() {
        rabbitTemplate.convertAndSend("topics", "info", "info------------------------logs");
    }




}