package com.simulation.service.knowledge;

import com.simulation.common.page.Pagination;
import com.simulation.model.basedata.ResponseParam;
import com.simulation.model.knowledge.NewsComment;

public interface NewsCommentService {
    ResponseParam addComment(NewsComment comment);
    ResponseParam findCommentByNewsId(int newsId, Pagination page);
}
