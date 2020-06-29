package com.huangjicheng.top.vepweb.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepapi.service.DictService;
import com.huangjicheng.top.vepcommont.util.ExcelUtils;
import com.huangjicheng.top.vepcommont.util.ResponseCode;
import com.huangjicheng.top.vepcommont.util.ResultJson;
import com.huangjicheng.top.vepdao.entity.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 20:32
 */

@CrossOrigin
@RestController
@RequestMapping("/dict")
@Api(description = "数据字典管理")
public class DictController {

    @Reference
    private DictService dictService;


    /**
     * 获取数据字典
     *
     * @return
     */
    @ApiOperation(value = "获取数据字典", notes = "获取数据字典")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResultJson get(
            @ApiParam(value = "权限ID", readOnly = true)
            @PathVariable Integer id) {
        Dict dict = dictService.get(id);
        return new ResultJson(dict);
    }

    /**
     * 根据字典名获取字典数据
     *
     * @return
     */
    @ApiOperation(value = "根据字典名获取字典数据", notes = "根据字典名获取字典数据")
    @RequestMapping(value = "/getByName/{dictName}", method = RequestMethod.GET)
    public ResultJson getByName(
            @ApiParam(value = "字典名", readOnly = true)
            @PathVariable String dictName) {

        Dict dict = dictService.getByName(dictName);

        List<String> dictValues = Arrays.asList(dict.getDictValue().split(","));

        List<Object> list = new ArrayList<>();

        dictValues.forEach(dictValue -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("value", dictValue);
            list.add(jsonObject);
        });

        return new ResultJson(list);
    }

    /**
     * 获取全部数据字典
     *
     * @return
     */

    @ApiOperation(value = "获取全部角色", notes = "获取全部角色")
    @RequestMapping(value = "/getList/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public ResultJson getList(@RequestBody Dict dict, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        PageInfo<Dict> dicts = dictService.findAllDictByPage(dict, pageNum, pageSize);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dicts", dicts.getList());
        jsonObject.put("total", dicts.getTotal());

        return new ResultJson(jsonObject);

    }


    /**
     * 新建数据字典
     *
     * @return
     */
    @ApiOperation(value = "新建数据字典", notes = "新建数据字典")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultJson save(@RequestBody Dict dict) {
        dict.setCreateTime(new Date());
        if (dictService.save(dict) > 0) {
            return new ResultJson(dict);
        } else {
            return new ResultJson(ResponseCode.CREATE_ERROR, null, "创建失败");
        }
    }

    /**
     * 更新数据字典
     *
     * @return
     */
    @ApiOperation(value = "更新数据字典", notes = "更新数据字典")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultJson update(@RequestBody Dict dict) {
        dict.setUpdateTime(new Date());
        if (dictService.update(dict) > 0) {
            return new ResultJson(dict);
        } else {
            return new ResultJson(ResponseCode.UPDATE_ERROR, null, "更新失败");
        }
    }

    /**
     * 更新数据字典
     *
     * @return
     */
    @ApiOperation(value = "下载数据字典", notes = "下载数据字典")
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void export(HttpServletRequest request, HttpServletResponse response) {
        Dict dict = new Dict();
        List<Dict> dicts = dictService.getList(dict);
        // 列名
        String columnNames[] = {"字典名称", "字典值","字典描述"};
        // map中的key
        String keys[] = {"dictName", "dictValue", "dictDesc"};
        try {
            ExcelUtils.exportExcel(request, response, "数据字典列表", dicts, columnNames, keys);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除数据字典
     *
     * @return
     */
    @ApiOperation(value = "删除数据字典", notes = "删除数据字典")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResultJson delete(
            @ApiParam(value = "权限ID", readOnly = true)
            @PathVariable Integer id) {
        if (dictService.get(id) != null) {
            if (dictService.delete(id) > 0) {
                return new ResultJson().ok();
            } else {
                return new ResultJson(ResponseCode.DELETE_ERROR, null, "删除失败");
            }
        } else {
            return new ResultJson(ResponseCode.GET_ERROR, null, "查询失败");

        }
    }

}
