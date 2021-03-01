package top.hhduan.market.service;

import top.hhduan.market.exception.BusinessException;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 基础service
 * @author duanhaohao
 * @version 1.0
 * @date 2020/8/6 10:20
 */
public interface BaseService<T, D> {
    /**
     * 保存
     * @param entity
     * @return
     * @throws BusinessException
     */
    int save(T entity) throws BusinessException;

    /**
     * 修改
     * @param entity
     * @return
     * @throws BusinessException
     */
    int update(T entity) throws BusinessException;

    /**
     *  　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　
     * @param id
     * @return
     * @throws BusinessException
     */
    int delete(D id) throws BusinessException;

    /**
     * 根据ID查找
     * @param id
     * @return
     */
    T findById(D id);

    /**
     * 列表查询
     * @param example
     * @return
     */
    List<T> findList(Example example);
}
