package top.hhduan.market.service.impl;

import top.hhduan.market.exception.BusinessException;
import top.hhduan.market.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 基类的实现
 *
 * @author duanhaohao
 * @version 1.0
 * @date 2020/8/6 10:21
 */
public class BaseServiceImpl<T, D> implements BaseService<T, D> {

    @Autowired
    public Mapper<T> mapper;

    @Override
    public int save(T entity) throws BusinessException {
        return mapper.insertSelective(entity);
    }

    @Override
    public int update(T entity) throws BusinessException {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int delete(D id) throws BusinessException {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public T findById(D id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> findList(Example example) {
        return mapper.selectByExample(example);
    }

    public Mapper<T> getMapper() {
        return mapper;
    }

    public void setMapper(Mapper<T> mapper) {
        this.mapper = mapper;
    }
}
