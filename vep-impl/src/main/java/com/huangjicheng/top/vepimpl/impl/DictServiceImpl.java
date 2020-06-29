package com.huangjicheng.top.vepimpl.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangjicheng.top.vepapi.service.DictService;
import com.huangjicheng.top.vepdao.entity.Dict;
import com.huangjicheng.top.vepdao.mapper.DictMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 21:36
 */

@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictMapper dictMapper;

    @Override
    public Dict get(Integer id) {
        return dictMapper.get(id);
    }

    @Override
    public int save(Dict dict) {
        return dictMapper.save(dict);
    }

    @Override
    public int update(Dict dict) {
        return dictMapper.update(dict);
    }

    @Override
    public int delete(Integer id) {
        return dictMapper.delete(id);
    }

    @Override
    public List<Dict> getList(Dict dict) {
        return dictMapper.getList(dict);
    }


    @Override
    public PageInfo<Dict> findAllDictByPage(Dict dict, int pageNum, int pageSize) {
        // TODO Auto-generated method stub
        PageHelper.startPage(pageNum, pageSize);
        List<Dict> lists = dictMapper.getList(dict);
        PageInfo<Dict> pageInfo = new PageInfo<Dict>(formatDict(lists));
        return pageInfo;

    }

    @Override
    public Dict getByName(String name) {
        return dictMapper.getByName(name);
    }

    public List<Dict> formatDict(List<Dict> lists){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lists.forEach(list -> {
            if (list.getCreateTime() != null) list.setCreateTimeStr(simpleDateFormat.format(list.getCreateTime()));
            if (list.getUpdateTime() != null) list.setUpdateTimeStr(simpleDateFormat.format(list.getUpdateTime()));
        });
        return lists;
    }

}
