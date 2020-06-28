package com.huangjicheng.top.vepweb.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepapi.service.FilesService;
import com.huangjicheng.top.vepcommont.util.ResponseCode;
import com.huangjicheng.top.vepcommont.util.ResultJson;
import com.huangjicheng.top.vepdao.entity.Files;
import com.huangjicheng.top.vepweb.config.ConfigBeanProp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 20:32
 */

@RestController
@RequestMapping("/file")
@Api(description = "文件管理")
public class FilesController {

    @Reference
    private FilesService filesService;


    @Autowired
    private ConfigBeanProp configBeanProp;


    /**
     * 获取全部文件
     *
     * @return
     */

    @ApiOperation(value = "获取全部文件", notes = "获取全部文件")
    @RequestMapping(value = "/getList/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public ResultJson getList(@RequestBody Files file, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        PageInfo<Files> files = filesService.findAllFileByPage(file, pageNum, pageSize);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("files", files.getList());
        jsonObject.put("total", files.getTotal());
        jsonObject.put("dictName", "FILE_NAME");

        return new ResultJson(jsonObject);

    }

    /**
     * 文件上传
     *
     * @return
     */
    @ApiOperation(value = "文件上传", notes = "文件上传")
    @RequestMapping(value = "/upload/{fileType}", method = RequestMethod.POST)
    public ResultJson importData(@RequestParam(value = "uploadFile") MultipartFile[] uploadFile
            , @PathVariable String fileType) {
        if (uploadFile != null && uploadFile.length > 0) {
            if (dealUploadFile(uploadFile, configBeanProp.getPath(), fileType)) {
                return new ResultJson().ok();
            } else {
                return new ResultJson(ResponseCode.UPLOAD_ERROR, null, "上传失败");
            }
        } else {
            return new ResultJson(ResponseCode.UPLOAD_ERROR, null, "文件为空，上传失败");
        }

    }


    /**
     * 文件上传到本地服务器以及hdfs
     *
     * @param uploadFile
     * @param uploadPath
     * @param fileType
     * @return
     */
    public boolean dealUploadFile(MultipartFile[] uploadFile, String uploadPath, String fileType) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        for (MultipartFile multipartFile : uploadFile) {
            String fileName = multipartFile.getOriginalFilename();
            String type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            if (type != null && "csv".equals(type.toLowerCase())) {
                String uuid = UUID.randomUUID().toString();
                String date = simpleDateFormat.format(new Date());
                String filePath = uploadPath + "/" + date + "/" + uuid + ".csv";
                File dest = new File(filePath);
                //判断文件父目录是否存在
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdir();
                }
                try {
                    // 文件上传到本地服务器
                    multipartFile.transferTo(dest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 文件名入库
                Files files = new Files();
                files.setFileName(fileName);
                files.setFileType(fileType);
                files.setPath(filePath);
                files.setCreateTime(new Date());
                files.setState(0);
                files.setUploadStatus(0);
                filesService.save(files);
            } else {
                return false;
            }
        }
        return true;
    }


    /**
     * 删除文件
     *
     * @return
     */
    @ApiOperation(value = "删除文件", notes = "删除文件")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResultJson delete(
            @ApiParam(value = "文件ID", readOnly = true)
            @PathVariable Integer id) {
        if (filesService.get(id) != null) {
            if (filesService.delete(id) > 0) {
                return new ResultJson().ok();
            } else {
                return new ResultJson(ResponseCode.DELETE_ERROR, null, "删除失败");
            }
        } else {
            return new ResultJson(ResponseCode.GET_ERROR, null, "查询失败");

        }
    }

}
