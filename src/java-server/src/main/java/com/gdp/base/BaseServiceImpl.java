package com.gdp.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import tk.mybatis.mapper.common.Mapper;

public class BaseServiceImpl<M extends Mapper<T>, T> implements BaseService<Mapper<T>, T> {
	
	@Autowired
	private M mapper;
	
	/**
	 * 根据主键查找
	 * 
	 * @param id
	 * @return
	 */
	public T selectByPrimaryKey(Object id){
		T t = (T) this.mapper.selectByPrimaryKey(id);
		return t;
	}
	
	/**
	 * 查找该表所有记录
	 * 
	 * @return
	 */
	public List<T> selectAll() {
		return this.mapper.selectAll();
	}
	
	/**
	 * 插入非空的对象值, 若为空, 则使用数据库默认值; 插入记录后自增id的会保存在 t 对象中
	 * 
	 * @param t
	 * @return
	 */
	public Integer insertSelective(T t) {
		int i = this.mapper.insertSelective(t);
		return i;
	}
	
	/**
	 * 根据主键更新非空字段
	 * 
	 * @param t
	 * @return
	 */
	public Integer updateByPrimaryKeySelective(T t) {
		return this.mapper.updateByPrimaryKeySelective(t);
	}

}
