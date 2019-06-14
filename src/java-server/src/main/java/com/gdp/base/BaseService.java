package com.gdp.base;

import java.util.List;

import tk.mybatis.mapper.common.Mapper;

public interface BaseService<M extends Mapper<T>, T> {
	/**
	 * 根据主键查找
	 * 
	 * @param id
	 * @return
	 */
	public T selectByPrimaryKey(Object id);
	
	/**
	 * 查找该表所有记录
	 * 
	 * @return
	 */
	public List<T> selectAll();
	
	/**
	 * 插入非空的对象值, 若为空, 则使用数据库默认值; 插入记录后自增id的会保存在 t 对象中
	 * 
	 * @param t
	 * @return
	 */
	public Integer insertSelective(T t);
	
	/**
	 * 根据主键更新非空字段
	 * 
	 * @param t
	 * @return
	 */
	public Integer updateByPrimaryKeySelective(T t);
}
