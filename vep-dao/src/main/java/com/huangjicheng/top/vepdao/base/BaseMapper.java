package com.huangjicheng.top.vepdao.base;



import java.util.List;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/27 0:19
 */

public interface BaseMapper<T> {

    /**
     * 新增数据
     * @param bean
     * @return
     */
    int save(T bean);

    /**
     * 删除数据
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    T get(Integer id);

    /**
     * 查询全部数据
     * @return
     */
    List<T> getList(T t);

    /**
     * 更新数据
     * @param bean
     * @return
     */
    int update(T bean);
}
