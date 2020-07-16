//package com.huangjicheng.top.vepjob.jobhandler;
//
//import com.huangjicheng.top.vepapi.service.FilesService;
//import com.huangjicheng.top.vepdao.entity.Files;
//import com.huangjicheng.top.vepjob.util.DealFiles;
//import com.xxl.job.core.biz.model.ReturnT;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import org.apache.dubbo.config.annotation.Reference;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//
//@Component
//public class ReadFile {
//
//    private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);
//
//    private static Logger logger = LoggerFactory.getLogger(ReadFile.class);
//
//    @Reference
//    private FilesService filesService;
//
//    @Autowired
//    private RestHighLevelClient restHighLevelClient;
//
//    @XxlJob("readFile")
//    public ReturnT<String> execute(String param) {
//        try {
//            if (atomicBoolean.compareAndSet(false, true)) {
//                Files files = new Files();
//                files.setState(0);
//                List<Files> filesList = filesService.getList(files);
//
//                for (Files file : filesList) {
//                    DealFiles.dealFile(file, filesService, restHighLevelClient);
//                }
//            } else {
//                logger.info("readFile任务正在执行中......");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            logger.info("readFile更新锁");
//            atomicBoolean.set(false);
//        }
//        return ReturnT.SUCCESS;
//
//    }
//
////    public ReturnT<String> execute(FilesService filesService,RestHighLevelClient restHighLevelClient) {
////        try {
////            if (atomicBoolean.compareAndSet(false, true)) {
////                Files files = new Files();
////                files.setState(0);
////                List<Files> filesList = filesService.getList(files);
////
////                for (Files file : filesList) {
////                    DealFiles.dealFile(file, filesService, restHighLevelClient);
////                }
////            } else {
////                logger.info("readFile任务正在执行中......");
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        } finally {
////            logger.info("readFile更新锁");
////            atomicBoolean.set(false);
////        }
////        return ReturnT.SUCCESS;
////
////    }
//}
