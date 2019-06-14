//闭包限定命名空间
(function($) {
	$.fn.extend({
		"loadData" : function(options) {
			// 检测用户传进来的参数是否合法
			if (!isValid(options))
				return this;
			var opts = $.extend({}, defaluts, options); // 使用jQuery.extend
														// 覆盖插件默认参数
			return this.each(function() { // 这里的this 就是 jQuery对象。这里return
											// 为了支持链式调用
				// 遍历所有的要高亮的dom,当调用 highLight()插件的是一个集合的时候。
				var $this = $(this); // 获取当前dom 的 jQuery对象，这里的this是当前循环的dom
				loadFormData($this, opts);
			});
		}
	});
	$.fn.loadData.format = {
		date : function(datestr, fmt) {
			var date = new Date(datestr);
			var o = {
					'M+': date.getMonth() + 1, //月份
					'd+': date.getDate(), //日
					'h+': date.getHours(), //小时
					'm+': date.getMinutes(), //分
					's+': date.getSeconds(), //秒
					'q+': Math.floor((date.getMonth() + 3) / 3), //季度
					'S': date.getMilliseconds() //毫秒
				};
				if(!isNotEmpty(fmt)){
					fmt = 'yyyy-MM-dd hh:mm:ss';
				}
				if (/(y+)/.test(fmt)) {
					fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
				}
				for (var k in o) {
					if (new RegExp('(' + k + ')').test(fmt)) {
						fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)));
					}
				}
				return fmt;
		}
	}
	// 默认参数
	var defaluts = {
		data:{},
		rules : {},
		format : {},
		loadAll:true
	};
	// 私有方法，检测参数是否合法
	function isValid(options) {
		return !options || (options && typeof options === "object") ? true
				: false;
	}
	//私有方法
	function isNotEmpty(str) {
		if (str != '' && str != null && typeof str != 'undefined') {
			return true;
		}
		return false;
	}
	//私有方法，赋值到form
	function loadFormData(form, options){
		var obj = options.data;
		var rules = options.rules;
		var format = options.format;
		var key,value,tagName,type,arr;
		for(x in obj){
			key = x;
			value = obj[x];
			var rule = rules[key];
			if (rule === undefined) {
				rule = options.loadAll;
			} else if (rule === true || rule === false) {
				//值正确
			} else {
				//值错误改为false
				rule = false;
			}
			if (!rule) {
				continue;
			}
			
			form.find("[name='"+key+"'],[name='"+key+"[]']").each(function(){
				tagName = $(this)[0].tagName;
				type = $(this).attr('type');
				if(tagName=='INPUT'){
					if(type=='radio'){
						$(this).attr('checked',$(this).val()==value);
					}else if(type=='checkbox'){
						arr = value.split(',');
						for(var i =0;i<arr.length;i++){
							if($(this).val()==arr[i]){
								$(this).attr('checked',true);
								break;
							}
						}
					}else{
						var f = format[key];
						if (isNotEmpty(f)) {
							if (isNotEmpty($.fn.loadData.format[f.type])) {
								value = $.fn.loadData.format[f.type](value, f.fmt);
							} else {
								console.err("找不到"+f.type+"的格式化函数")
							}
						}
						$(this).val(value);
					}
				}else if(tagName=='SELECT' || tagName=='TEXTAREA'){
					$(this).val(value);
				}
				
			});
		}
	}
})(window.jQuery);