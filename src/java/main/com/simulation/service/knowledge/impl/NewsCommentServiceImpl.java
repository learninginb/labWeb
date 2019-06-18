package com.simulation.service.knowledge.impl;

import com.simulation.common.page.Pagination;
import com.simulation.dao.mybatis.knowledge.NewsCommentDao;
import com.simulation.model.basedata.ResponseParam;
import com.simulation.model.knowledge.NewsComment;
import com.simulation.service.knowledge.NewsCommentService;
import org.apache.activemq.command.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsCommentServiceImpl implements NewsCommentService {
    @Autowired
    private NewsCommentDao newsCommentDao ;
    @Override
    public ResponseParam addComment(NewsComment comment) {
       ResponseParam res = new ResponseParam();

       try{
           newsCommentDao.insertComment(comment);
       }catch (Exception e){
           e.printStackTrace();
           res.setCode(500);
           res.setMsg("发表评论失败");
           return res;
       }
       res.setCode(200);
       res.setMsg("发表评论成功");
       return res;
    }

    @Override
    public ResponseParam findCommentByNewsId(int newsId, Pagination page) {
        ResponseParam res = new ResponseParam();
        try{
            List<NewsComment> commentList = newsCommentDao.findCommentByNewsId(newsId,page);
            res.setCode(200);
            res.setMsg("获取列表成功");
            Map<String,Object> data = new HashMap<>();
            data.put("list",commentList);
            res.setData(data);
        }catch (Exception e){
            e.printStackTrace();
            res.setCode(500);
            res.setMsg("获取列表失败");
            return res;
        }
        return res;
    }
}
