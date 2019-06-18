<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2019/5/31
  Time: 15:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/common/global.jsp"%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>新闻-审核列表</title>
    <script type="text/javascript" src="${path }/js/ajax.js"></script>
    <script type="text/javascript" >
        function postQueryParams(params) {
            var queryParams = $("#searchForm").serializeObject();
            queryParams.limit = params.limit;
            queryParams.offset = params.offset;
            return queryParams;
        }
        //查询列表
        function queryList() {
            $('#NewsReviewList').bootstrapTable('refresh');
        }

        function newsInfo(id) {
            window.location="${path}/jsp/knowledge/news/news_info.jsp?id="+id;
        }

        function review(id){
            var dialog = art.dialog({
                title: '新闻审核',
                content: '请选择审核结果！',
                lock: true,
                okVal : "通过",
                ok:function(){
                    getAjax('/shiyanshi/NewsController/'+id+'/review/true.do', {}, (data)=>{
                        showArtDiaglog('提示','审核通过成功',function(){
                        },function(){
                            closeDialog();
                            window.location = '${path}/NewsController/toReviewList.do'
                        });
                    });
                },
                button:[{
                    name:'不通过',
                    callback:function () {
                        getAjax('/shiyanshi/NewsController/'+id+'/review/false.do',{},(data)=>{
                            showArtDiaglog('提示','审核不通过成功',function(){
                            },function(){
                                closeDialog();
                                window.location = '${path}/NewsController/toReviewList.do'
                            });
                        });
                    }
                }]
            })
        }
        function importanceFormatter(value,row,index){
            if (value == '1') {
                return '★'
            } else if (value == '2') {
                return '★★';
            } else if(value == '3'){
                return "★★★";
            }else{
                return "-"
            }
        }

        function operatorFormatter(value, row, index) {
            var operator = "";
            <shiro:hasPermission name="News:review">
                operator += '<button class="btn grey-cascade btn-xs" onclick="review(\''+row.id+'\')"><i class="fa fa-link"></i>审核</button>&nbsp;&nbsp;'
            </shiro:hasPermission>
            <shiro:hasPermission name="News">
            operator += '<button class="btn btn-success btn-round btn-xs" onclick="newsInfo(\''
                + row.id
                + '\')"><i class="glyphicon glyphicon-list-alt"></i>详情</button>';
            </shiro:hasPermission>
            return operator;
        }

        function statusFormatter(value, row, index) {
            return '<span class="label label-warning label-lg">待审核</span>'
        }
    </script>
</head>
<body>
<div>
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">首页</a></li>
            <li><a href="#">信息管理</a></li>
            <li><a href="#">新闻管理</a></li>
            <li><a href="#">新闻审核</a></li>
        </ul>
    </div>
    <div class="rightinfo">
        <div>
            <input type="hidden" name="resource_path" id="resource_path" value="${resource_path }">
            <form id="searchForm" name="searchForm" method="post">
                <label>标题：</label><input type="text" name="newsName" class="txtSearch">&nbsp;
                <input type="button" class="btn btn-info btn-round" value="查询"
                       onclick="queryList()">&nbsp;&nbsp; <input type="button"
                                                                 class="btn btn-warning btn-round" value="重置"
                                                                 onclick="$('#searchForm')[0].reset();">
            </form>
        </div>
        <table id="NewsReviewList" data-toggle="table"
               data-url="${path}/NewsController/reviewList.do" data-pagination="true"
               data-side-pagination="server" data-cache="false"
               data-query-params="postQueryParams"
               data-page-list="[10, 15, 20, 30, 50,100]" data-method="post"
               data-show-refresh="true" data-show-toggle="true"
               data-show-columns="true" data-toolbar="#toolbar"
               data-click-to-select="true" data-single-select="false"
               data-striped="true"
               data-content-type="application/x-www-form-urlencoded">
            <thead>
            <tr>
                <th data-field="" data-checkbox="true"></th>
                <th data-field="newsName">标题</th>
                <th data-field="author">作者</th>
                <th data-field="location">所属实验室</th>
                <th data-field="importance" data-formatter=importanceFormatter>重要程度</th>
                <th data-field="keyWords">关键词</th>
                <th data-field="createTime" data-formatter="dateFormatter">发布时间</th>
                <th data-field="statusId" data-formatter="statusFormatter">状态</th>
                <th data-field="operator" data-formatter="operatorFormatter">操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>
