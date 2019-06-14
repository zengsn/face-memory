package com.gdp.mapper;

/**
 *
 * @author Jashon
 * @Version 1.0
 * @since 2019-01-30
 */
public interface FeedbackDao {

    /**
     * 批量更新反馈信息为 逻辑删除状态
     * @param ids
     * @return
     */
    int deleteFeedback(int [] ids);

}
