/**
 * js 工具方法
 * 扩展JQuery类，可以用$调用
 * create by dengwm
 */
jQuery.extend({
			onloading : function(msg, dom) // 加载等待条,扩展easyUI没有的功能,调用datagrid的加载效果
			{
				var showmsg = '', appendDom = '';
				if (msg == null || msg == '' || msg == 'undefined') {
					showmsg = '正在处理，请稍候。。。';
				} else {
					showmsg = msg;
				}
				if (dom == null || msg == '' || msg == 'undefined') {
					appendDom = "body";
				} else {
					appendDom = dom;
				}
				$("<div class=\"datagrid-mask\"></div>").css({
					display : "block",
					width : "100%",
					height : $(window).height()
				}).appendTo(appendDom);
				$("<div class=\"datagrid-mask-msg\"></div>")
						.html(showmsg)
						.appendTo(appendDom)
						.css(
								{
									display : "block",
									left : ($(document.body).outerWidth(true) - 190) / 2,
									top : ($(window).height() - 45) / 2
								});
			},
			removeload : function() // 取出等待条,扩展easyUI没有的功能
			{
				$(".datagrid-mask").remove();
				$(".datagrid-mask-msg").remove();
			},
			checkAll : function(a, b) {
				$("input[name='" + a + "']").attr("checked", b);
			},
			getChkValue : function(a) {
				var b = "";
				$('input[type="checkbox"][name=' + a + "]").each(function() {
					if ($(this).attr("checked")) {
						b += $(this).val() + ",";
					}
				});
				if (b != "") {
					b = b.substring(0, b.length - 1);
				}
				return b;
			},
			getSelectValue : function(a) {
				var b = "";
				$("select[name=" + a + "] option").each(function() {
					b += $(this).val() + ",";
				});
				if (b != "") {
					b = b.substring(0, b.length - 1);
				}
				return b;
			},
			copyToClipboard : function(a) {
				if (window.clipboardData) {
					window.clipboardData.clearData();
					window.clipboardData.setData("Text", a);
					return true;
				} else {
					if (navigator.userAgent.indexOf("Opera") != -1) {
						window.location = a;
						return false;
					} else {
						if (window.netscape) {
							try {
								netscape.security.PrivilegeManager
										.enablePrivilege("UniversalXPConnect");
							} catch (f) {
								alert($lang.tip.msg,
										$lang_js.util.copyToClipboard.netscape);
								return false;
							}
							var c = Components.classes["@mozilla.org/widget/clipboard;1"]
									.createInstance(Components.interfaces.nsIClipboard);
							if (!c) {
								return false;
							}
							var b = Components.classes["@mozilla.org/widget/transferable;1"]
									.createInstance(Components.interfaces.nsITransferable);
							if (!b) {
								return false;
							}
							b.addDataFlavor("text/unicode");
							var g = Components.classes["@mozilla.org/supports-string;1"]
									.createInstance(Components.interfaces.nsISupportsString);
							var h = a;
							g.data = h;
							b.setTransferData("text/unicode", g, h.length * 2);
							var d = Components.interfaces.nsIClipboard;
							if (!c) {
								return false;
							}
							c.setData(b, null, d.kGlobalClipboard);
							return true;
						} else {
							alert($lang.tip.msg,
									$lang_js.util.copyToClipboard.notCopy);
							return false;
						}
					}
				}
			},
			copy : function(a) {
				var c = $("#" + a).val();
				var b = jQuery.copyToClipboard(c);
				if (b) {
					alert($lang_js.util.copy.success);
				}
			},
			isIE : function() {
				var b = navigator.appName;
				var a = b.indexOf("Microsoft");
				return a == 0;
			},
			isIE6 : function() {
				if (($.browser.msie && $.browser.version == "6.0")
						&& !$.support.style) {
					return true;
				}
				return false;
			},
			getChildXml : function(c, j) {
				var b = c.childNodes;
				var f = b.length;
				for ( var e = 0; e < f; e++) {
					var a = b[e];
					if (a.nodeType != 1) {
						continue;
					}
					var h = a.nodeName;
					j.append("<" + h + " ");
					var l = a.attributes;
					for ( var d = 0; d < l.length; d++) {
						var g = l[d];
						j.append(" " + g.name + '="' + g.value + '" ');
					}
					j.append(">");
					$.getChildXml(a, j);
					j.append("</" + h + ">");
				}
			},
			getChildXmlByNode : function(a) {
				var b = new StringBuffer();
				jQuery.getChildXml(a, b);
				return b.toString();
			},
			getAttrXml : function(d, c) {
				var b = d.childNodes;
				var h = b.length;
				for ( var g = 0; g < h; g++) {
					var a = b[g];
					if (a.nodeType != 1) {
						continue;
					}
					var l = a.attributes;
					var f = new Object();
					for ( var e = 0; e < l.length; e++) {
						var j = l[e];
						f[j.name] = j.value;
					}
					c.push(f);
					$.getAttrXml(a, c);
				}
			},
			fixPNG : function(f) {
				var h = navigator.appVersion.split("MSIE");
				var c = parseFloat(h[1]);
				if ((c >= 5.5) && (c < 7) && (document.body.filters)) {
					var b = (f.id) ? "id='" + f.id + "' " : "";
					var e = (f.className) ? "class='" + f.className + "' " : "";
					var g = (f.title) ? "title='" + f.title + "' " : "title='"
							+ f.alt + "' ";
					var d = "display:inline-block;" + f.style.cssText;
					var a = "<span "
							+ b
							+ e
							+ g
							+ ' style="'
							+ "width:"
							+ f.width
							+ "px; height:"
							+ f.height
							+ "px;"
							+ d
							+ ";"
							+ "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader"
							+ "(src='" + f.src
							+ "', sizingMethod='scale');\"></span>";
					f.outerHTML = a;
				}
			},
			getParameter : function(b) {
				var d = unescape(window.location.search.substr(1)).split("&");
				for ( var a = 0; a < d.length; a++) {
					var c = d[a].split("=");
					if (c.length == 2 && c[0].toUpperCase() == b.toUpperCase()) {
						return c[1];
					}
				}
				return new String();
			},
			getMonthDays : function(a, c) {
				if (c < 0 || c > 11) {
					return 30;
				}
				var b = new Array(12);
				b[0] = 31;
				if (a % 4 == 0) {
					b[1] = 29;
				} else {
					b[1] = 28;
				}
				b[2] = 31;
				b[3] = 30;
				b[4] = 31;
				b[5] = 30;
				b[6] = 31;
				b[7] = 31;
				b[8] = 30;
				b[9] = 31;
				b[10] = 30;
				b[11] = 31;
				return b[c];
			},
			weekOfYear : function(e, h, b) {
				var g = new Date(e, 0, 1);
				var f = new Date(e, h - 1, b, 1);
				var d = 24 * 60 * 60 * 1000;
				var a = (7 - g.getDay()) * d;
				var c = 7 * d;
				g = g.getTime();
				f = f.getTime();
				return Math.ceil((f - g - a) / c) + 1;
			},
			addBookmark : function(b, a) {
				if (window.sidebar) {
					window.sidebar.addPanel(b, a, "");
				} else {
					if (document.all) {
						window.external.AddFavorite(a, b);
					} else {
						if (window.opera && window.print) {
							return true;
						}
					}
				}
			},
			setCookie : function(b, h) {
				var c = new Date();
				var g = arguments;
				var e = arguments.length;
				var d = (e > 2) ? g[2] : null;
				var i = (e > 3) ? g[3] : null;
				var f = (e > 4) ? g[4] : null;
				var a = (e > 5) ? g[5] : false;
				if (d != null) {
					c.setTime(c.getTime() + (d * 1000));
				}
				document.cookie = b
						+ "="
						+ escape(h)
						+ ((d == null) ? "" : (";  expires=" + c.toGMTString()))
						+ ((i == null) ? "" : (";  path=" + i))
						+ ((f == null) ? "" : (";  domain=" + f))
						+ ((a == true) ? ";  secure" : "");
			},
			delCookie : function(a) {
				var c = new Date();
				c.setTime(c.getTime() - 1);
				var b = GetCookie(a);
				document.cookie = a + "=" + b + ";  expires=" + c.toGMTString();
			},
			getCookie : function(d) {
				var b = d + "=";
				var f = b.length;
				var a = document.cookie.length;
				var e = 0;
				while (e < a) {
					var c = e + f;
					if (document.cookie.substring(e, c) == b) {
						return $.getCookieVal(c);
					}
					e = document.cookie.indexOf("  ", e) + 1;
					if (e == 0) {
						break;
					}
				}
				return null;
			},
			getCookieVal : function(b) {
				var a = document.cookie.indexOf(";", b);
				if (a == -1) {
					a = document.cookie.length;
				}
				return unescape(document.cookie.substring(b, a));
			},
			setFormByJson : function(d) {
				var b = d;
				if (typeof (d) == "string") {
					b = jQuery.parseJSON(d);
				}
				for ( var e in b) {
					var c = b[e];
					var a = $("input[name='" + e + "'],textarea[name='" + e
							+ "']");
					if (a[0]) {
						a.val(c);
					}
				}
			},
			highlightTableRows : function() {
				$("tr.odd,tr.even").hover(function() {
					$(this).addClass("over");
				}, function() {
					$(this).removeClass("over");
				});
			},
			insert : function(a, e, c) {
				if (isNaN(c) || c < 0 || c > a.length) {
					a.push(e);
				} else {
					var b = a.slice(c);
					a[c] = e;
					for ( var d = 0; d < b.length; d++) {
						a[c + 1 + d] = b[d];
					}
				}
			},
			getFirstLower : function(a) {
				var e = "";
				if (a.indexOf("_") != -1) {
					var d = a.split("_");
					for ( var c = 0; c < d.length; c++) {
						var b = d[c];
						if (c == 0) {
							e += b.toLowerCase();
						} else {
							e += b.substring(0, 1).toUpperCase()
									+ b.substring(1, b.length + 1)
											.toLowerCase();
						}
					}
				} else {
					e = a.toLowerCase();
				}
				return e;
			},
			getFirstUpper : function(a) {
				var e = "";
				if (a.indexOf("_") != -1) {
					var d = a.split("_");
					for ( var c = 0; c < d.length; c++) {
						var b = d[c];
						e += b.substring(0, 1).toUpperCase()
								+ b.substring(1, b.length + 1).toLowerCase();
					}
				} else {
					e = a.substring(0, 1).toUpperCase()
							+ a.substring(1, a.length + 1).toLowerCase();
				}
				return e;
			},
			openFullWindow : function(b) {
				var c = screen.availHeight - 35;
				var a = screen.availWidth - 5;
				var e = "top=0,left=0,height="
						+ c
						+ ",width="
						+ a
						+ ",status=no,toolbar=no,menubar=no,location=no,resizable=1,scrollbars=1";
				var d = window.open(b, "", e, true);
				return d;
			},
			isEmpty : function(b, a) {
				return b === null || b === undefined || (!a ? b === "" : false);
			},
			convertCurrency : function(x) {
				var c = 99999999999.99;
				var B = "零";
				var F = "壹";
				var j = "贰";
				var k = "叁";
				var m = "肆";
				var H = "伍";
				var E = "陆";
				var A = "柒";
				var J = "捌";
				var C = "玖";
				var g = "拾";
				var q = "佰";
				var t = "仟";
				var f = "万";
				var h = "亿";
				var z = "";
				var w = "元";
				var e = "角";
				var u = "分";
				var y = "整";
				var b;
				var M;
				var v;
				var I;
				var K, o, s, r;
				var a;
				var G, D, L;
				var N, l;
				x = x.toString();
				if (x == "") {
					return "";
				}
				if (x.match(/[^,.\d]/) != null) {
					return "";
				}
				if ((x)
						.match(/^((\d{1,3}(,\d{3})*(.((\d{3},)*\d{1,3}))?)|(\d+(.\d+)?))$/) == null) {
					return "";
				}
				x = x.replace(/,/g, "");
				x = x.replace(/^0+/, "");
				if (Number(x) > c) {
					return "";
				}
				I = x.split(".");
				if (I.length > 1) {
					b = I[0];
					M = I[1];
					M = M.substr(0, 2);
				} else {
					b = I[0];
					M = "";
				}
				K = new Array(B, F, j, k, m, H, E, A, J, C);
				o = new Array("", g, q, t);
				s = new Array("", f, h);
				r = new Array(e, u);
				v = "";
				if (Number(b) > 0) {
					a = 0;
					for (G = 0; G < b.length; G++) {
						D = b.length - G - 1;
						L = b.substr(G, 1);
						N = D / 4;
						l = D % 4;
						if (L == "0") {
							a++;
						} else {
							if (a > 0) {
								v += K[0];
							}
							a = 0;
							v += K[Number(L)] + o[l];
						}
						if (l == 0 && a < 4) {
							v += s[N];
						}
					}
					v += w;
				}
				if (M != "") {
					for (G = 0; G < M.length; G++) {
						L = M.substr(G, 1);
						if (L != "0") {
							v += K[Number(L)] + r[G];
						}
					}
				}
				if (v == "") {
					v = B + w;
				}
				if (M == "") {
					v += y;
				}
				v = z + v;
				return v;
			},
			tagName : function(b, a) {
				var d = b.attributes, f = document.createElement(a);
				for ( var e = 0, g; g = d[e++];) {
					if (!g.value || g.value == "null") {
						continue;
					}
					$(f).attr(g.name, g.value);
				}
				$(b).before($(f));
				$(b).remove();
				return $(f);
			},
			insertText : function(g, a) {
				if (document.selection) {
					var e = document.selection.createRange().text;
					if (!e) {
						e = a;
					}
					g.focus();
					if (e.charAt(e.length - 1) == " ") {
						e = e.substring(0, e.length - 1);
						document.selection.createRange().text = e + " ";
					} else {
						document.selection.createRange().text = e;
					}
				} else {
					if (g.selectionStart || g.selectionStart == "0") {
						var c = g.selectionStart;
						var b = g.selectionEnd;
						var f = (g.value).substring(c, b);
						if (!f) {
							f = a;
						}
						if (f.charAt(f.length - 1) == " ") {
							subst = f.substring(0, (f.length - 1)) + " ";
						} else {
							subst = f;
						}
						g.value = g.value.substring(0, c) + subst
								+ g.value.substring(b, g.value.length);
						g.focus();
						var d = c + (f.length);
						g.selectionStart = d;
						g.selectionEnd = d;
					} else {
						g.value += a;
						g.focus();
					}
				}
				if (g.createTextRange) {
					g.caretPos = document.selection.createRange().duplicate();
				}
			},
			confirm : function(a, b, c) {
				$(a).click(function() {
					if ($(this).hasClass("disabled")) {
						return false;
					}
					var d = this;
					$.ligerDialog.confirm(b, $lang.tip.msg, function(e) {
						if (e) {
							if ($.browser.msie) {
								$.gotoDialogPage(d.href);
							} else {
								location.href = d.href;
							}
						}
					});
					return false;
				});
			},
			gotoDialogPage : function(c) {
				if ($.browser.msie) {
					var b = document.createElement("a");
					b.href = c;
					document.body.appendChild(b);
					b.click();
				} else {
					location.href = c;
				}
			},
			cloneObject : function(b) {
				var c = b.constructor === Array ? [] : {};
				for ( var a in b) {
					if (b.hasOwnProperty(a)) {
						c[a] = typeof b[a] === "object" ? cloneObject(b[a])
								: b[a];
					}
				}
				return c;
			},
			clearQueryForm : function() {
				$("input[name^='Q_'],select[name^='Q_']").each(function() {
					$(this).val("");
				});
			},
			getFileExtName : function(b) {
				var a = b.lastIndexOf(".");
				if (a == -1) {
					return "";
				}
				return b.substring(a + 1);
			},
			comdify : function(a) {
				if (a && a != "") {
					n = a + "";
					var c = /\d{1,3}(?=(\d{3})+$)/g;
					var b = n.trim().replace(/^(\d+)((\.\d+)?)$/,
							function(f, e, d) {
								return e.replace(c, "$&,") + d;
							});
					return b;
				}
				return a;
			},
			toNumber : function(a) {
				if (a && a != "") {
					if (a.indexOf(",") == -1) {
						return a;
					}
					var b = a.split(",");
					var c = b.join("");
				}
				return 0;
			},
			moveTr : function(e, d) {
				var c = $(e).parents("tr");
				if (d) {
					var b = $(c).prev();
					if (b) {
						c.insertBefore(b);
					}
				} else {
					var a = $(c).next();
					if (a) {
						c.insertAfter(a);
					}
				}
			}
		});

//https://shop106571874.taobao.com/?spm=2013.1.1000126.d21.ysGLAa
//qq:983150316
function getRootPath(){
    //获取当前网址，如： http://localhost:8080/yhact/xx/xx.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： yhact/*****
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8080
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}