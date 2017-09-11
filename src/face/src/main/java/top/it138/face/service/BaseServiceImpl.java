package top.it138.face.service;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.common.Mapper;

/**
 * 数据库表服务基类，可以根据需要拓展
 * 
 * @author Lenovo
 *
 * @param <T>
 */
//@Service
public abstract class BaseServiceImpl<T> implements BaseService<T> {
	/** Logger available to subclasses */
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected Mapper<T> mapper;

	@Override
	public int save(T entity) {
		Class<?> clazz = entity.getClass();
		try {
			Method setGmtCreateMethod = clazz.getMethod("setGmtCreate", Date.class);
			setGmtCreateMethod.invoke(entity, new Date());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapper.insert(entity);
	}

	@Override
	public int delete(T entity) {
		return mapper.delete(entity);
	}

	/**
	 * 单表分页查询
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public Page<T> selectPage(int pageNum, int pageSize) {
		Page<T> pageBeen = PageHelper.startPage(pageNum, pageSize);
		// Spring4支持泛型注入 
		mapper.select(null);
		
		return pageBeen;
	}

	@Override
	public T selectById(Long id) {
		return mapper.selectByPrimaryKey(id);
	}
	
	@Override
	public void deleteById(Long id) {
		mapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public Integer selectCount() {
		return mapper.selectCountByExample(null);
	}
	
	@Override
	public int update(T entity) {
		try {
			Class<?> clazz = entity.getClass();
			Method setGmtModifiedMethod = clazz .getMethod("setGmtModified", Date.class);
			setGmtModifiedMethod.invoke(entity, new Date());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapper.updateByPrimaryKey(entity);
	}
	
	@Override
	public List<T> select(T entity) {
		return mapper.select(entity);
	}
}