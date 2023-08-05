<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>
<%@ Import Namespace="BingChongBackend.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<% var fieldInfo = (List<FieldInfo>)ViewData["fieldinfo"]; %>

<div class="page-header">
	<div>
        <div class="col-xs-2 no-padding-left">
            <h5>添加表格</h5>
        </div>
		<div class="pull-right">
            <p>
            </p>
		</div>
    </div>
</div>
<div class="row ">
	<div class="col-xs-12">
		<form class="form-horizontal" role="form" id="validation-form">
			<div class="form-group">
				<label class="col-sm-2 control-label no-padding-right" for="tablename">表格名称：</label>
				<div class="col-sm-9">
                    <div class="clearfix">
					<input type="text" id="tablename" name="tablename" placeholder="" class="input-large form-control"  />
                    </div>
				</div>
			</div>
            <div class="form-group">
                <label class="col-sm-2 control-label no-padding-right">选择字段：</label>
                <input class="" id="fieldidstr" name="fieldidstr" style="display:none" />
            </div>
            <span class ="col-sm-2">
            </span>

            <div class="col-sm-10 has-error">
                <% =ViewData["html"]%>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label no-padding-right">表格预览：</label>
                
            </div>
            <div class="form-group" align="center" >
                <table border="1" cellspacing="0" cellpadding="0" width="80%" id="tablediv">
                	
                </table>
            </div>
			<div class="clearfix">
				<div class="col-md-offset-3 col-md-9">
					<button class="btn btn-info loading-btn" type="submit" data-loading-text="提交中...">
						<i class="ace-icon fa fa-check bigger-110"></i>
						提交
					</button>

					&nbsp; &nbsp; &nbsp;
					<button class="btn" type="reset">
						<i class="ace-icon fa fa-undo bigger-110"></i>
						取消
					</button>
				</div>
			</div>
        </form>
	</div>
</div>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/select2.css" />
	<link rel="stylesheet" type="text/css" href="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.min.css" />
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
	<script src="<%= ViewData["rootUri"] %>Content/js/jquery.validate.min.js"></script>
	<script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>
	<script src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>  
    <script type="text/javascript">
    
	    function redirectToListPage(status) {
	        if (status.indexOf("error") != -1) {
	            $('.loading-btn').button('reset');
	        } else {
	            window.location = '<%=ViewData["rootUri"]%>' + 'Settings/TableManage';
	        }
	    }

        jQuery(function ($) {
            $(".select2").css('width', '100px').select({ allowClear: true });

            $.validator.messages.required = "必须要填写";
            $.validator.messages.minlength = jQuery.validator.format("必须由至少{0}个字符组成.");
            $.validator.messages.maxlength = jQuery.validator.format("必须由最多{0}个字符组成");
            $.validator.messages.equalTo = jQuery.validator.format("密码不一致.");
            $('#validation-form').validate({
                errorElement: 'span',
                errorClass: 'help-block',
                //focusInvalid: false,
                rules: {
                    tablename: {
                        required: true,
                        uniquename: true
                    },
                    fieldid:
                    {
                        required: true
                    }
                },
                
                highlight: function (e) {
                    $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
                },

                success: function (e) {
                    $(e).closest('.form-group').removeClass('has-error'); //.addClass('has-info');
                    $(e).remove();
                },

                errorPlacement: function (error, element) {
                    if (element.is(':checkbox') || element.is(':radio')) {
                        var controls = element.closest('div[class*="col-"]');
                        if (controls.find(':checkbox,:radio').length > 1) controls.append(error);
                        else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
                    }
                    else if (element.is('.select2')) {
                        error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
                    }
                    else if (element.is('.chosen-select')) {
                        error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
                    }
                    else error.insertAfter(element.parent());
                },

                submitHandler: function (form) {
                    submitform();
                    return false;
                },
                invalidHandler: function (form) {
                    //  $('.loading-btn').button('reset');
                }
            });
            $.validator.addMethod("uniquename", function (value, element) {
                return checkTableName();
            }, "表格已存在");

            
                var tds = document.getElementsByName("fieldid");
                for (var i = 0; i < tds.length; i++) {
                    var td = tds[i];
                    td.setAttribute("onclick", "viewtable()");
                }
            
        });

        function checkTableName() {
            var tablename = $("#tablename").val();
            var retval = false;
            $.ajax({
                async: false,
                type: "GET",
                url: '<%= ViewData["rootUri"] %>' + 'Settings/CheckUniqueTablename',
                dataType: "json",
                data: {
                    tablename: tablename,
                },
                success: function (data) {
                    if (data == true) {
                        retval = true;
                    } else {
                        retval = false;
                    }
                }
            });

            return retval;
        }

        function submitform() {
            var fieldid = GetInfoFromTable();
            $.ajax({
                type: "POST",
                url: '<%= ViewData["rootUri"] %>' + 'Settings/SubmitTable',
                dataType: "json",
                data: {
                        tablename: $("#tablename").val(),
                        fieldid: fieldid 
                },
                success: function (data) {
                    if (data == "") {
                        toastr.options = {
                            "closeButton": false,
                            "debug": true,
                            "positionClass": "toast-bottom-right",
                            "onclick": null,
                            "showDuration": "3",
                            "hideDuration": "3",
                            "timeOut": "1500",
                            "extendedTimeOut": "1000",
                            "showEasing": "swing",
                            "hideEasing": "linear",
                            "showMethod": "fadeIn",
                            "hideMethod": "fadeOut"
                        };

                        toastr["success"]("操作成功!", "恭喜您");
                    } else {
                        toastr.options = {
                            "closeButton": false,
                            "debug": true,
                            "positionClass": "toast-bottom-right",
                            "onclick": null,
                            "showDuration": "3",
                            "hideDuration": "3",
                            "timeOut": "1500",
                            "extendedTimeOut": "1000",
                            "showEasing": "swing",
                            "hideEasing": "linear",
                            "showMethod": "fadeIn",
                            "hideMethod": "fadeOut"
                        };

                        toastr["error"](data, "温馨敬告");

                    }
                },
                error: function (data) {
                    alert("Error: " + data.status);
                    //$('.loading-btn').button('reset');
                }
            });
        }

        function viewtable() {
            var fieldid = $("input:checkbox[name='fieldid']:checked");
            if (fieldid.length == 0) {
                $("#tablediv").html("");
            }
            else {
                $.ajax({
                    type: "POST",
                    url: '<%= ViewData["rootUri"] %>' + 'Settings/ViewTable',
                    dataType: "json",
                    data: $('#validation-form').serialize(),
                    success: function (data) {
                        $("#tablediv").html(data);
                    },
                    error: function (data) {
                        alert("Error: " + data.status);
                        //$('.loading-btn').button('reset');
                    }
                });
            }
        }

        function moveleft(o) {
            if ($(o).parent().prev().length== 0) {
                return;
            }
            else 
            {
           
                if ($(o).parent().prev().attr("parentid")==$(o).parent().attr("parentid")) {
                //移动子元素开始
                //思路1：判断是否有子元素
                //    2: 判断子元素是否需要移动
                //    3: 获得移动格数，移动所有子元素

                //移动子元素
                var subelement = new Array();
                var presubelement = new Array();
                var elementid=$(o).parent().attr("value");
                var preelementid=$(o).parent().prev().attr("value");

                //储存子元素
                var subfloorements=$(o).parent().parent().next();
                if (subfloorements.children().length!=0) {
                    for(var i=0;i<subfloorements.children().length;i++)
                            {
                                if (subfloorements.children().eq(i).attr("parentid")==elementid) {
                                    subelement.push(subfloorements.children().eq(i));
                                }
                                if (subfloorements.children().eq(i).attr("parentid")==preelementid) {
                                    presubelement.push(subfloorements.children().eq(i));
                                }
                            }
                }
                //判断是需要移动
                if(subelement.length !=0&&presubelement.length!=0){
                    for(var i=0;i< subelement.length;i++){
                        var value=subelement[i].attr("value");                      
                        for(var j=0;j< presubelement.length;j++){
                            $("td[value="+value+"]").prev().before($("td[value="+value+"]").eq(0)[0].outerHTML);
                            $("td[value="+value+"]").eq(1).remove();
                      
                        }                    
                    }
                }
                //移动子元素结束



                
                    $(o).parent().prev().before($(o).parent()[0].outerHTML);
                    $(o).parent().remove();

                }
            }
           


        }

        function moveright(o) {
            if ($(o).parent().next().length== 0) {
                return;
            }
            else {
                if ($(o).parent().next().attr("parentid")==$(o).parent().attr("parentid")) {
                //移动子元素开始
                //思路1：判断是否有子元素
                //    2: 判断子元素是否需要移动
                //    3: 获得移动格数，移动所有子元素

                //移动子元素
                var subelement = new Array();
                var nextsubelement = new Array();
                var elementid=$(o).parent().attr("value");
                var nextelementid=$(o).parent().next().attr("value");

                //储存子元素
                var subfloorements=$(o).parent().parent().next();
                if (subfloorements.children().length!=0) {
                    for(var i=0;i<subfloorements.children().length;i++)
                            {
                                if (subfloorements.children().eq(i).attr("parentid")==elementid) {
                                    subelement.push(subfloorements.children().eq(i));
                                }
                                if (subfloorements.children().eq(i).attr("parentid")==nextelementid) {
                                    nextsubelement.push(subfloorements.children().eq(i));
                                }
                            }
                }
                //判断是需要移动
                if(subelement.length !=0&&nextsubelement.length!=0){
                    for(var i= subelement.length-1;i>=0;i--){
                        var value=subelement[i].attr("value");                      
                        for(var j=0;j< nextsubelement.length;j++){
                            $("td[value="+value+"]").next().after($("td[value="+value+"]").eq(0)[0].outerHTML);
                            $("td[value="+value+"]").eq(0).remove();
                      
                        }                    
                    }
                }
                //移动子元素结束
                    $(o).parent().next().after($(o).parent()[0].outerHTML);
                    $(o).parent().remove();
                }
            }
            
        }

        function GetInfoFromTable() {
            var tableInfo = "";
            var tableObj = document.getElementById("tablediv");
            for (var i = 0; i < tableObj.rows.length; i++) {    //遍历Table的所有Row
                for (var j = 0; j < tableObj.rows[i].cells.length; j++) {   //遍历Row中的每一列
                    if (tableInfo != "") {
                        tableInfo += ",";
                        tableInfo += tableObj.rows[i].cells[j].getAttribute("value");   //获取Table中单元格的内容
                    }
                    else {
                        tableInfo += tableObj.rows[i].cells[j].getAttribute("value");   //获取Table中单元格的内容                    
                    }                    
                }
               // tableInfo += "\n";
            }
            //alert(tableInfo);
            return tableInfo;
        }
    </script>
</asp:Content>
