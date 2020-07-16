//package com.huangjicheng.top.vepjob.jobhandler;
//
//import com.huangjicheng.top.vepapi.service.FilesService;
//import com.huangjicheng.top.vepdao.entity.Files;
//import com.xxl.job.core.biz.model.ReturnT;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import org.apache.dubbo.config.annotation.Reference;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.net.URI;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//
//@Component
//public class UplaodFile {
//    private static Logger logger = LoggerFactory.getLogger(UplaodFile.class);
//
//    private static String task = "vep项目文件上传hdfs......";
//
//    private static final String hdfsPath = "/vep/web/file/";
//
//    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//
//    private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);
//
//    @Reference
//    private FilesService filesService;
//
//    @XxlJob("uploadHdfs")
//    public ReturnT<String> execute(String param) {
//        try {
//            if (atomicBoolean.compareAndSet(false, true)) {
//                logger.info(task + "开始");
//                Files files = new Files();
//                files.setUploadStatus(0);
//                List<Files> filesList = filesService.getList(files);
//
//                for (Files file : filesList) {
//                    // 文件上传到hdfs
//                    String fileName = UUID.randomUUID().toString() + ".csv";
//                    String path = hdfsPath + simpleDateFormat.format(new Date()) + "/" + fileName;
//                    if (uploadHdfs(file.getPath(), path)) {
//                        logger.info(task + "上传hdfs成功，开始更新状态");
//                        file.setUploadStatus(1);
//                        file.setHdfsPath(path);
//                        filesService.update(file);
//                        logger.info(task + "上传hdfs成功，结束更新状态");
//                    }
//                }
//            } else {
//                logger.info(task + "任务正在执行中......");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.info(task + "上传失败");
//        } finally {
//            logger.info(task + "更新锁");
//            atomicBoolean.set(false);
//        }
//        logger.info(task + "结束");
//        return ReturnT.SUCCESS;
//    }
//
//    /**
//     * 文件上传到hdfs
//     *
//     * @param filePath
//     * @return
//     */
//    public boolean uploadHdfs(String filePath, String path) throws Exception {
//        FileSystem fs = init();
//        fs.copyFromLocalFile(new Path(filePath.trim()), new Path(path));
//        fs.close();
//        return true;
//    }
//
//    public FileSystem init() throws Exception {
//        FileSystem fs = null;
//        Configuration conf = new Configuration();
//        conf.set("dfs.replication", "2");
//        conf.set("dfs.blocksize", "64m");
//        fs = FileSystem.get(new URI("hdfs://centos3:9000"), conf, "root");
//        return fs;
//    }
//
//
//}
