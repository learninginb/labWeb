/**
 * 对easyui dagagrid 的扩展
 *
 * 1，对工具栏的操作
 */
window.jCore.EasyuiGridPermissionToolSelectedSite = {
	/**
	 * 或的toolbar 容器的 对象
	 *
	 * @param {}
	 *            jq
	 * @return {}
	 */
	getToolbar : function(jq) {
		return $.data(jq[0], "datagrid").panel.find("div.datagrid-toolbar");
	},
	/**
	 * 获得grid的panel
	 *
	 * @param {Object}
	 *            jq
	 */
	getPanel : function(jq) {
		var panel = $.data(jq[0], "datagrid").panel;
		return panel;
	},
	/**
	 * 获得toolbar 更多下拉按钮
	 *
	 * 返回更多工具栏 或者 null
	 */
	getToolbarMoreButton : function(jq) {
		var toolbar = jq.datagrid('getToolbar');

		var toolbarMore = toolbar.find(".datagrid-toolbar-more");

		if(toolbarMore.size() == 0) {
			return null;
		}
		return toolbarMore;
	},
	/**
	 * 获得更多操作的菜单
	 *
	 */
	getToolbarMoreMenu : function(jq) {

		var datagridPanel = jq.datagrid('getPanel');

		var toolbarMoreMenu = datagridPanel.find(".datagrid-toolbar-more-menu");

		if(toolbarMoreMenu.size() == 0) {
			$('<div class="datagrid-toolbar-more-menu" style="width:150px;"></div>').appendTo(datagridPanel);
			toolbarMoreMenu = datagridPanel.find(".datagrid-toolbar-more-menu");

			if(toolbarMoreMenu.size() == 0) {
				alert('err');
			}
		}

		return toolbarMoreMenu;

	},
	/**
	 * 添加更多操作栏
	 *
	 * 当toolbar 工具栏操作按钮太多放不下的时候 ，
	 *
	 * 需要添加一个更多的效果 以menubutton的方式显示
	 *
	 * param{dataType:'json|html',menus:[{},{}],url:'',content:'<div>menuItem</div>'}
	 */
	appendToolbarMore : function(jq, param) {
		//判断传入的参数 ，如果没有参数 ，检查 options.toolbarMore 属性，如果都没有直接忽略掉
		//如果有一个存在 则创建  更多 按钮操作
		//2011-12-24 wangjunming

		var toolbarMore = jq.datagrid('options').toolbarMore;
		if(!param) {
			if(!toolbarMore) {
				//如果没有toolbarMore属性则忽略 改查操作
				//alert('请设置  appendToolbarMore（param）或 构建 datagrid(“options”).toolbarMore 属性 ');
				return;
			}
		}

		//查找toolbarMoreButton对象
		var toolbarMoreButton = jq.datagrid('getToolbarMoreButton');

		//如果不存在则创建，
		if(!toolbarMoreButton) {
			var toolbar = jq.datagrid('getToolbar');
			var toolMore = $('<a href="javascript:void(0)" class="datagrid-toolbar-more">更多</a>');

			toolMore.css('float', 'right');

			var toolbarLastChild = toolbar.children('a:last-child');

			toolbarLastChild.after(toolMore);

			var datagridId = jq.attr('id');
			var datagridPanel = jq.datagrid('getPanel');

			var toolbarMoreMenu = null;

			toolMore.menubutton({
				menu : '#' + datagridId + '_moreMenu'
			});
			toolbarMoreButton = jq.datagrid('getToolbarMoreButton');
		} else {
			alert('moreMenu id exists');
		}

		if(param) {
			//如果url存在则读取 ajax url内容
			if(param.url) {
				var content = $.ajax({
					url : param.url,
					async : false
				}).responseText;
				toolbarMore = eval("(" + content + ")");
			}
		} else {
			var menus = toolbarMore;

			var toolbarMoreButton = jq.datagrid('getToolbarMoreButton');
			var toolbarMoreMenu = jq.datagrid('getToolbarMoreMenu');

			if(!menus.length) {
				alert('please set menus[]');
				return;
			}

			var grid_content_more_menu_id = jq.attr('id') + "_more_menu_id";
			jCore.contentMenu.create({
				id : grid_content_more_menu_id,
				items : menus
			});
			toolbarMoreButton.menubutton({
				menu : '#' + grid_content_more_menu_id
			});
		}
		jq.datagrid('setSize');
	},
	/**
	 * 放在 工具栏上边的区域，内容自定义 也可以ajax获取
	 *
	 * @param {}
	 *            jq
	 * @param {content:'html',height:50,url:'search.html',speed:1000}
	 *            param
	 */
	appendAreaAboveToolbar : function(jq, param) {
		//alert('appendAreaAboveToolbar');
		if(!param) {
			if(jCore.devMode) {
				alert('please input param');
			}
			return;
		}
		/**
		 * 设置默认 添加查询栏后是 80px
		 */
		param = $.extend({
			speed : 0
		}, param);
		var toolbar = jq.datagrid('getToolbar');
		var toolbarSearch = toolbar.find("div.datagrid-toolbar-above");

		if(toolbarSearch.size() == 0)
		// toolbar存在，
		{
			if(param.height) {
				toolbar.height(toolbar.height() + param.height);
			} else {
				// 一般是一个行的高度 30px
				toolbar.height(toolbar.height() + 30);
			}
			var searchHtml = "";
			if(param.content) {
				searchHtml = param.content;
			} else {
				if(param.url) {
					searchHtml = $.ajax({
						url : param.url,
						async : false
					}).responseText;
				}
			}
			if(searchHtml) {
				// 清除浮动 使用包装器
				searchHtml = '<div class="datagrid-toolbar-above">' + searchHtml + '</div><div style="clear: both;"></div>';
				toolbar.prepend(searchHtml);

				if($.parser) {
					$.parser.parse(toolbar);
				}
			} else {
				if(jCore.devMode) {
					alert('please set url or content');
				}
			}
		} else {
			// toolbarSearch 存在
			if(toolbarSearch.is(':hidden')) {
				if(param.height) {
					toolbar.height(toolbar.height() + param.height);
				} else {
					// 一般是一个行的高度 30px
					toolbar.height(toolbar.height() + 30);
				}
				// toolbar.height(toolbar.height()+30);
				toolbarSearch.show();
				// toolbar.

			} else {
				if(param.height) {
					toolbar.height(toolbar.height() - param.height);
				} else {
					// 一般是一个行的高度 30px
					toolbar.height(toolbar.height() - 30);
				}
				// toolbar.height(toolbar.height()-30);
				toolbarSearch.hide();

			}
		}
		jq.datagrid('setSize');
	},
	/**
	 * 工具栏 手工添加内容，添加到最前面
	 *
	 * @param {}
	 *            jq
	 * @param {content:'html',height:50,url:'search.html',speed:1000}
	 *            param
	 */
	appendToolbar : function(jq, param) {
		// alert('appendToolbar');
		if(!param) {
			if(jCore.devMode) {
				alert('please input param');
			}
			return;
		}

		var toolbar = jq.datagrid('getToolbar');
		var exist = toolbar.find(".datagrid-toolbar-combobox");
		if(exist.size() == 0) {
			var toolbarFirstChild = toolbar.children('a:first-child');
			var searchHtml = "";
			if(param.content) {
				searchHtml = param.content;
			} else {
				if(param.url) {
					searchHtml = $.ajax({
						url : param.url,
						async : false
					}).responseText;
				}
			}
			if(searchHtml) {
				searchHtml = '<a class="datagrid-toolbar-combobox">' + searchHtml + '</a>';
				toolbarFirstChild.before(searchHtml);

				if($.parser) {
					$.parser.parse(toolbar);
				}
			} else {
				if(jCore.devMode) {
					alert('please set url or content');
				}
			}
			jq.datagrid('setSize');
		}
	},
	/**
	 * 显示/关闭 查询框
	 *
	 * @param {}
	 *            jq
	 * @param {content:'html',height:50,url:'search.html',speed:1000}
	 *            param
	 */
	toggleSearchbar : function(jq, param) {
		if(!param) {
			if(jCore.devMode) {
				alert('please input param');
			}
			return;
		}
		/**
		 * 设置默认 添加查询栏后是 80px
		 */
		param = $.extend({
			speed : 0
		}, param);
		var toolbar = jq.datagrid('getToolbar');
		var toolbarSearch = toolbar.find("div.datagrid-toolbar-search");

		if(toolbarSearch.size() == 0)
		// toolbar存在，
		{
			if(param.height) {
				toolbar.height(toolbar.height() + param.height);
			} else {
				// 一般是一个行的高度 30px
				toolbar.height(toolbar.height() + 30);
			}
			var searchHtml = "";
			if(param.content) {
				searchHtml = param.content;
			} else {
				if(param.url) {
					searchHtml = $.ajax({
						url : param.url,
						async : false
					}).responseText;
				}
			}
			if(searchHtml) {
				// 清除浮动 使用包装器
				searchHtml = '<div style="clear: both;"></div><div class="datagrid-toolbar-search">' + searchHtml + '</div>';
				toolbar.append(searchHtml);
				if($.parser) {
					$.parser.parse(toolbar);
				}
			} else {
				if(jCore.devMode) {
					alert('please set url or content');
				}
			}
		} else {
			// toolbarSearch 存在
			if(toolbarSearch.is(':hidden')) {
				if(param.height) {
					toolbar.height(toolbar.height() + param.height);
				} else {
					// 一般是一个行的高度 30px
					toolbar.height(toolbar.height() + 30);
				}
				// toolbar.height(toolbar.height()+30);
				toolbarSearch.show();
				// toolbar.

			} else {
				if(param.height) {
					toolbar.height(toolbar.height() - param.height);
				} else {
					// 一般是一个行的高度 30px
					toolbar.height(toolbar.height() - 30);
				}
				// toolbar.height(toolbar.height()-30);
				toolbarSearch.hide();

			}
		}
		jq.datagrid('setSize');
	},
	/**
	 * 关闭查询窗体
	 *
	 * @param {Object}
	 *            jq
	 * @param {Object}
	 *            param
	 */
	closeSearchbar : function(jq, param) {
		param = param || {};
		var toolbar = jq.datagrid('getToolbar');
		var toolbarSearch = toolbar.find("div.datagrid-toolbar-search");
		if(param.height) {
			toolbar.height(toolbar.height() - param.height);
		} else {
			// 一般是一个行的高度 30px
			toolbar.height(toolbar.height() - 30);
		}
		// toolbar.height(toolbar.height()-30);
		toolbarSearch.hide();
		jq.datagrid('setSize');
	},
	/**
	 * 打开查询工具条
	 *
	 * @param {Object}
	 *            jq
	 * @param {Object}
	 *            param
	 */
	openSearchbar : function(jq, param) {
		param = param || {};
		var toolbar = jq.datagrid('getToolbar');
		var toolbarSearch = toolbar.find("div.datagrid-toolbar-search");
		if(param.height) {
			toolbar.height(toolbar.height() + param.height);
		} else {
			// 一般是一个行的高度 30px
			toolbar.height(toolbar.height() + 30);
		}
		// toolbar.height(toolbar.height()+30);
		toolbarSearch.show();
		jq.datagrid('setSize');
	},
	/**
	 * 设置高度宽度 grid 根据周围环境自适应 高度宽度
	 *
	 * @param {}
	 *            jq
	 */
	setSize : function(jq) {
		var _9 = jq[0];
		var _a = $.data(_9, "datagrid").options;
		var _b = $.data(_9, "datagrid").panel;
		var _c = _b.width();
		var _d = _b.height();
		var _e = _b.children("div.datagrid-view");
		var _f = _e.children("div.datagrid-view1");
		var _10 = _e.children("div.datagrid-view2");
		_e.width(_c);
		_f.width(_f.find("table").width());
		_10.width(_c - _f.outerWidth());
		_f.children("div.datagrid-header,div.datagrid-body,div.datagrid-footer").width(_f.width());
		_10.children("div.datagrid-header,div.datagrid-body,div.datagrid-footer").width(_10.width());
		var hh;
		var _11 = _f.children("div.datagrid-header");
		var _12 = _10.children("div.datagrid-header");
		var _13 = _11.find("table");
		var _14 = _12.find("table");
		_11.css("height", "");
		_12.css("height", "");
		_13.css("height", "");
		_14.css("height", "");
		hh = Math.max(_13.height(), _14.height());
		_13.height(hh);
		_14.height(hh);
		if($.boxModel == true) {
			_11.height(hh - (_11.outerHeight() - _11.height()));
			_12.height(hh - (_12.outerHeight() - _12.height()));
		} else {
			_11.height(hh);
			_12.height(hh);
		}
		if(_a.height != "auto") {
			var _15 = _d - _10.children("div.datagrid-header").outerHeight(true) - _10.children("div.datagrid-footer").outerHeight(true) - _b.children("div.datagrid-toolbar").outerHeight(true) - _b.children("div.datagrid-pager").outerHeight(true);
			_f.children("div.datagrid-body").height(_15);
			_10.children("div.datagrid-body").height(_15);
		}
		_e.height(_10.height());
		_10.css("left", _f.outerWidth());
	}
}

$.extend($.fn.datagrid.methods, window.jCore.EasyuiGridPermissionToolSelectedSite);
