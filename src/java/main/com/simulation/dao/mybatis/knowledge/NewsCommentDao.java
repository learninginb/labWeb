package com.simulation.dao.mybatis.knowledge;

import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.NewsComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NewsCommentDao {
    void insertComment(@Param("comment") NewsComment comment);
    List<NewsComment> findCommentByNewsId(@Param("newsId")int newsId, @Param("page") Pagination page);
}
