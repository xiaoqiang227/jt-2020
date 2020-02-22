<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="css/table.css" />
<form id="itemParamEditForm" class="itemForm" method="post" >
	<input type="hidden" id="name" name="id"/>
<table cellpadding="5" style="margin-left: 30px" id="itemParamEditTable" class="itemParam" >
	<tr>
		<td>商品类目:</td>
		<td><a href="javascript:void(0)" class="easyui-linkbutton selectItemCat" >选择类目</a>
			<span style='margin-left:10px;' id="itemParamCatName"></span>
			<input type="hidden" name="itemCatName" id="itemParamCatNames"/>
			<input <%--type="hidden"--%> name="cid" style="width: 280px;" />
		</td>
	</tr>
	<tr >
		<td>规格参数:</td>
		<td style="white-space:nowrap;width: 220px">
			<ul>
				<li><a href="javascript:void(0)" class="easyui-linkbutton addGroup">添加分组</a></li>
				<input  type="hidden" name="paramData"/>
			</ul>
		</td>
	</tr>
	<tr>
		<td></td>
		<td>
			<a href="javascript:void(0)" class="easyui-linkbutton submit">提交</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton close">关闭</a>
		</td>
	</tr>
</table>
<div  class="itemParamAddTemplate" style="display: none;">
	<li class="param">
		<ul>
			<li >

				<input class="easyui-textbox" style="width: 150px;" name="group" />&nbsp;<a href="javascript:void(0)"  class="easyui-linkbutton addParam"  title="添加参数" data-options="plain:true,iconCls:'icon-add'"></a>
				<a  href="javascript:void(0)" class="easyui-linkbutton delParam" title="删除" data-options="plain:true,iconCls:'icon-cancel'"></a>
			</li>
			<li>
				<span>|----------</span><input  style="width: 150px;" class="easyui-textbox" name="param"/>&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton delParam" title="删除" data-options="plain:true,iconCls:'icon-cancel'"></a>
			</li>
		</ul>
	</li>
</div>
</form>
<script type="text/javascript">

	$(function(){
		KindEditorUtil.initItemCat({
			fun:function(node){
				$(".addGroupTr").find(".param").remove();
				//  判断选择的目录是否已经添加过规格
				$.getJSON("/item/param/query/itemcatid/" + node.id,function(data){
					if(data.status == 200 && data.data){
						$.messager.alert("提示", "该类目已经添加，请选择其他类目。", undefined, function(){
							$("#itemParamAddTable .selectItemCat").click();
						});
						return ;
					}
					$(".addGroupTr").show();
				});
			}
		});
	});
			//定义添加分组事件
			$(".addGroup").click(function(){
				var temple = $(".itemParamAddTemplate li").eq(0).clone();
				$(this).parent().parent().append(temple);
				temple.find(".addParam").click(function(){
					var li = $(".itemParamAddTemplate li").eq(2).clone();
					li.find(".delParam").click(function(){
						$(this).parent().remove();
					});
					li.appendTo($(this).parentsUntil("ul").parent());
				});

				//删除group同时删除下面所有的param
				temple.find(".delParam").click(function(){
					$(this).parent().parent().parent().remove();
				});

				temple.find(".delParam").click(function(){
					$(this).parent().remove();
				});
			});

			//自动触发点击事件
			//$('.addGroup').click()

		//异步函数,获取itemCatName
		setTimeout(function () {


			var ParamCatName = $("#itemParamCatNames").val();
			console.log("ParamCatName: "+ParamCatName)
			$("#itemParamCatName").html(ParamCatName)
			itemParamDatas();

		});

		//回显规格参数分组
		function itemParamDatas() {
			var d,itemCatName;
			var t = $("#itemParamEditForm").serializeArray();
			//t的值为[{name: "a1", value: "xx"},{name: "a2", value: "xx"}...]
			console.log(t)
			$.each(t, function() {
				if (this.name == "paramData"){
					d = this.value;
				}
			});
			console.log("paramData: "+d)

			var arr=eval(d);
			//group
			for(var i=0;i<arr.length;i++) {
				//alert(arr[i].group);
				var temple = $(".itemParamAddTemplate li").eq(0).clone();
				temple.find("input").val(arr[i].group);
				temple.find("li:eq(1)").remove();
				$(".addGroup").parent().parent().append(temple);
				temple.find(".addParam").click(function(){
					var li = $(".itemParamAddTemplate li").eq(2).clone();
					li.find(".delParam").click(function(){
						$(this).parent().remove();
					});
					li.appendTo($(this).parentsUntil("ul").parent());
				});

				//删除group同时删除下面所有的param
				temple.find(".delParam").click(function(){
					$(this).parent().parent().parent().remove();
				});

				var params = arr[i].params;
				//param
				for (var j = 0; j<params.length; j++){
					//alert(params[j]);
					var temple1 = $(".itemParamAddTemplate li").eq(2).clone();
					temple1.find("input").val(params[j]);
					temple.find("ul").append(temple1);
					temple1.find(".delParam").click(function(){
						$(this).parent().remove();
					});
				};
			};
		}



		$("#itemParamEditTable .close").click(function(){
			$(".panel-tool-close").click();
		});

		$("#itemParamEditTable .submit").click(function(){
			var params = [];
			var groups = $("#itemParamEditTable [name=group]");
			groups.each(function(i,e){
				var p = $(e).parentsUntil("ul").parent().find("[name=param]");
				var _ps = [];
				p.each(function(_i,_e){
					var _val = $(_e).siblings("input").val();
					if($.trim(_val).length>0){
						_ps.push(_val);
					}
				});
				var _val = $(e).siblings("input").val();
				if($.trim(_val).length>0 && _ps.length > 0){
					params.push({
						"group":_val,
						"params":_ps
					});
				}
			});
			var url = "/item/param/update";
			$.post(url,{
				"paramData":JSON.stringify(params),
				"itemCatId":$("#itemParamEditTable [name=cid]").val(),
				"id":$("#name").val()
			},function(data){
				if(data.status == 200){
					$.messager.alert('提示','修改成功!',undefined,function(){
						$(".panel-tool-close").click();
    					$("#itemParamList").datagrid("reload");
    				});
				}
			});
		});

</script>