package com.huangjicheng.top.vepjob.test;

import com.huangjicheng.top.vepapi.service.FilesService;
import com.huangjicheng.top.vepjob.VepJobApplication;
import com.huangjicheng.top.vepjob.jobhandler.ReadFile;
import org.apache.dubbo.config.annotation.Reference;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VepJobApplication.class)
public class VepjobtApplicationTests {

    @Reference
    private FilesService filesService;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void test() {
        ReadFile readFile = new ReadFile();
        readFile.execute(null);
    }

}