package top.it138.face.service;

import java.util.List;

import com.github.pagehelper.Page;

public interface BaseService<T> {

	int save(T entity);

	int delete(T entity);

	Page<T> selectPage(int pageNum, int pageSize);

	T selectById(Long id);

	void deleteById(Long id);

	Integer selectCount();
	
	/**
	 * 根据主键更新实体全部字段，null值会被更新
	 * @param entity
	 * @return
	 */
	int update(T entity);
	
	List<T> select(T entity);
}
