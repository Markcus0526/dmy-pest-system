<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>
<%@ Import Namespace="BingChongBackend.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<% var fieldinfo = (FieldInfo)ViewData["fieldinfo"]; %>
<div class="page-header">
	<div>
        <div class="col-xs-2 no-padding-left">
            <h5>字段设置</h5>
        </div>
		<div class="pull-right">
            <p>
            </p>
		</div>
    </div>
</div>
<div class="row">
    <div class="col-xs-12">
        <div class="widget-box">
			<div class="widget-body">
				<div class="widget-main">
                    <div class="searchbar">
                        <form class="form-horizontal" role="form" id="validation-form">
                            <div class="form-group" style="margin-bottom:0px">
                                <label class="col-sm-1 control-label no-padding-right" for="">所属字段：</label>
				                <div class="col-sm-2">
						            <input value="" placeholder="请输入关键字" id="searchParentFieldWord" style="height:28px;margin-top:1px"/>
                                </div>   
                                <label class="col-sm-1 control-label no-padding-right" for="">字段名称：</label>
				                <div class="col-sm-2">
						            <input value="" placeholder="请输入关键字" id="searchFieldWord" style="height:28px;margin-top:1px"/>
                                </div>   
                                <div class="col-sm-1" style="">
						            <a class="btn btn-sm btn-info" id="searchdata" onclick="SerchTable()"><i class="fa fa-search"></i> 搜索</a>
                                </div>   
                                <div  style="text-align:right;margin-right:10px">
						            <a class="btn btn-sm btn-info" id="A1" onclick="AddClum()">
                                        </i> 添加字段
                                    </a>
                                </div>   
                            </div>
	
                        </form>
                    </div>
				</div>
			</div>
		</div>
        <div style="margin-top:10px;margin-right:17px">
			<table id="myDataTable" class="table-hover">
			</table>
            <div id="pager10" style="height:50px"></div>
        </div>
    </div>
</div>

<div id="dialog-message" class="hide">
    <form class="form-horizontal" role="form" id="form_crew">
        <div class="col-xs-12">
            <div class="form-group">
                <label class="col-sm-4 control-label no-padding-right">
				    <input id="parentornot" type="checkbox" class="ace indbtn"  value="1" />
				    <span class="lbl"> 所属字段：</span>
                </label>
                <div class="col-sm-8" style="" >
                    <div class="clearfix">
                    <select class="select2" id="parentid" >
                        <% 
                            if (ViewData["fieldlist"] != null)
                                {
                                    foreach (var item in (List<FieldInfo>)ViewData["fieldlist"])
                                    { %>
                            <option value="<%= item.uid %>"  <%if(fieldinfo!=null &&fieldinfo.parentid== item.uid){%>selected<%} %> ><%= item.name%></option>
                            <% }
                                } %>
                    </select>
			        </div>
                </div>
            </div>
            <div class="form-group">
               <label class="col-sm-4 control-label no-padding-right" style="margin:6px 0px;" for="sgroupid"> 字段名称：</label>
			   <div class="col-sm-8" style="margin:6px 0px;" >
                    <input id="fiedname" />
			    </div>
		    </div>
		    <div class="form-group">
			    <label class="col-sm-4 control-label no-padding-right" style="margin:6px 0px;" for="sgroupid">字段属性：</label>
			    <div class="col-sm-8" style="margin:6px 0px;" >
                    <div class="clearfix">
                        <select class="select2" id="type" onchange="ChangeInputHide()" >
                            <option value="d">日期</option>
                            <option value="r">实数</option>
                            <option value="i">整数</option>
                            <option value="c">选项</option>
                            <option value="t">文字</option>
                            <option value="s">合计字段</option>
                            <option value="p">母字段</option>
                        </select>
                    </div>
			    </div>
               
		    </div>
            <div class="form-group unit hide">
                <label class="col-sm-4 control-label no-padding-right" style="margin:6px 0px;" for="unit">单位：</label>
			    <div class="col-sm-8" style="margin:6px 0px;" >
                    <input id="unit" />                   
			    </div>
            </div>
            <div class="form-group option hide">
                <label class="col-sm-4 control-label no-padding-right" style="margin:6px 0px;" for="option">选项：</label>
			    <div class="col-sm-8" style="margin:6px 0px;" >
                    <input id="option" name="option"/><br />
                    <span class="time grey">选项之间用","隔开。 例如:  升高,降低</span>                 
			    </div>
            </div>
        </div>
    </form>
</div><!-- #dialog-message -->
<div id="dialog-message2" class="hide">
    <form class="form-horizontal" role="form" id="form2">
        <div class="col-xs-12">
            <div class="form-group">
               <label class="col-sm-4 control-label no-padding-right" style="margin:6px 0px;" for="sgroupid"> 字段名称：</label>
			   <div class="col-sm-8" style="margin:6px 0px;" >
                    <input id="editname" />
			    </div>
		    </div>
            <input class="hide" name="edituid" id="edituid" />

<%--		    <div class="form-group">
			    <label class="col-sm-4 control-label no-padding-right" style="margin:6px 0px;" for="sgroupid">字段属性：</label>
			    <div class="col-sm-8" style="margin:6px 0px;" >
                    <div class="clearfix">
                        <select class="select2" id="Select2" onchange="ChangeInputHide()" >
                            <option value="d">日期</option>
                            <option value="r">实数</option>
                            <option value="i">整数</option>
                            <option value="c">选项</option>
                            <option value="t">文字</option>
                            <option value="s">合计字段</option>
                            <option value="p">母字段</option>
                        </select>
                    </div>
			    </div>
--%>               
		    </div>
<%--            <div class="form-group unit hide">
                <label class="col-sm-4 control-label no-padding-right" style="margin:6px 0px;" for="unit">单位：</label>
			    <div class="col-sm-8" style="margin:6px 0px;" >
                    <input id="Text2" />                   
			    </div>
            </div>
            <div class="form-group option hide">
                <label class="col-sm-4 control-label no-padding-right" style="margin:6px 0px;" for="option">选项：</label>
			    <div class="col-sm-8" style="margin:6px 0px;" >
                    <input id="Text3" /><br />
                    <span class="time grey">选项之间用","隔开。 例如:  升高,降低</span>                 
			    </div>
            </div>
        </div>--%>
    </form>
</div><!-- #dialog-message -->

<!-- #dialog-message 确认-->
<div id="dialog2" class="hide">
    <form class="form-horizontal" role="form" id="form1">
        <div class="col-xs-12">
            <div class="form-group" >
                <h3 class="red">是否确定“保存”，保存成功后不能删除，请谨慎操作。</h3>
            </div>
        </div>    
    </form>
</div>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/select2.css" />
    <link href="<%= ViewData["rootUri"] %>Content/css/ui.jqgrid.css" rel="Stylesheet"/>  
    <link rel="stylesheet" type="text/css" href="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.min.css" />
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/jquery-ui.min.css" />

</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery.ui.touch-punch.min.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>
	<script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/i18n/grid.locale-cn.js" type="text/javascript"></script>

    <script type="text/javascript">
        jQuery(function ($) {
            $(".select2").css('width', '150px').select2({ minimumResultsForSearch: -1 });
        });

        $(function () {
            $("#myDataTable").jqGrid({
                // multiselect: true, //复选
                url: '<%= ViewData["rootUri"] %>' + 'Settings/RetrieveFieldList',
                datatype: "json",
                autowidth: true,  
                height: 468,                
                rowNum: 15,
                rowList: [5,15, 20, 30],
                pager: '#pager10',

                sortname: 'id',
                viewrecords: true,
                sortable: false,
                pagerpos: "center",
                pgbuttons: true,
                rownumbers: true,
                scrollOffset: 1,

                viewsortcols: [false, 'vertical', false],
                colNames: ['所属字段', '字段名称', '字段属性', '操作'],
                colModel: [
                            { name: 'parentname', index: 'parentname', align: "center" },
                            { name: 'name', index: 'name',  align: "center" },
                            { name: 'type', index: 'type',  align: "center", width:"100",
                                 formatter: function (cellvalue, options, rowObject) {
                                    if (cellvalue=="d") {
                                    return "日期";
                                    }
                                    if (cellvalue=="r") {
                                    return "实数";
                                    }
                                    if (cellvalue=="i") {
                                    return "整数";
                                    }
                                    if (cellvalue=="c") {
                                    return "选项";
                                    }
                                    if (cellvalue=="t") {
                                    return "文字";
                                    }
                                    if (cellvalue=="s") {
                                    return "合计字段";
                                    }
                                    if (cellvalue=="p") {
                                    return "母字段";
                                    }
                               }
                            },
                            { name: 'uid', index: 'uid',  align: "center",
                                formatter: function (cellvalue, options, rowObject) {
                                    return '<a  href="#" onclick="processEdit('+ cellvalue +')"  style="font-size:12px">修改名称</a>&nbsp;&nbsp;';
                                    }
                            
                             }
                ],
                gridComplete: function () {
                 }
            });
            $("#myDataTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });
        });

        var clumdlg;
        var dialog2;
        function AddClum() {
            document.getElementById("form_crew").reset();
            clumdlg = $("#dialog-message").removeClass('hide').dialog({
                modal: false,
                width: 500,
                title: "添加字段",
                title_html: true,
                buttons: [
					{
					    text: "确定",
					    "class": "btn btn-primary btn-xs",
					    click: function () {
                           Confirm();
					    }
					},
					{
					    text: "取消",
					    "class": "btn btn-xs",
					    click: function () {
					        $(this).dialog("close");
					    }
					}
				]
            });

        }


         function Confirm() {
            dialog2 = $("#dialog2").removeClass('hide').dialog({
                modal: true,
                width: 500,
                title_html: true,
                buttons: [
					{
					    text: "确定",
					    "class": "btn btn-primary btn-xs",
					    click: function () {
                            
					        var fiedname = $("#fiedname").val();
					        var type = $("#type").val();
					        var parentornot = $("#parentornot").prop("checked");
					        var parentid = $("#parentid").val();

					        if (fiedname != null && fiedname != "") {
					            $.ajax({
					                type: "POST",
					                url: "<%= ViewData["rootUri"] %>" + "Settings/SubmitAddField",
					                data: {
					                    name: fiedname,
					                    type: type,
                                        parentornot:parentornot,
                                        parentid:parentid,
                                        unit: $("#unit").val(),
                                        option: $("#option").val()
					                },
					                dataType: "json",
					                success: function (message) {
					                    if (message == "") {
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
					                            toastr["success"]("添加成功！", "恭喜您");
                                                $("#myDataTable").trigger("reloadGrid");   
					                       	}
					                    else {
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
					                        toastr["error"](message, "温馨敬告");
					                    }
                                        $("#myDataTable").trigger("reloadGrid");
                                        var rhtml="";
                                        $.ajax({
                                            type: "GET",
                                            url: '<%= ViewData["rootUri"] %>'+ "Settings/RefreshParentFieldList",
                                            method: 'post',
                                            dataType: "json",
                                            success: function (data) {
                                                if (data.length > 0) {
                                                    for (var i = 0; i < data.length; i++) {
                                                        rhtml += "<option value='" + data[i].uid + "'>" + data[i].name + "</option>";
                                                    }
                                                    $("#parentid").html(rhtml);
                                                    $("#parentid").select2({ allowClear: true, minimumResultsForSearch: -1});

                                                }
                                                else{
                                                }
                                            }
                                    });
					                }
					            });
					        }
					        dialog2.dialog("close");
					    }
					},
					{
					    text: "取消",
					    "class": "btn btn-xs",
					    click: function () {
					    $(this).dialog("close");
					    }
					}
				]
            });
        }
        function ChangeInputHide() {
            var fieldtype = $("#type").val();
            if (fieldtype == "c") {
                $(".option").removeClass("hide");
                $(".unit").addClass("hide");
            }
            else if (fieldtype == "r"||fieldtype == "i") {
                $(".option").addClass("hide");
                $(".unit").removeClass("hide");
            }
            else{
                $(".option").addClass("hide");
                $(".unit").addClass("hide");
            }
        }

        function SerchTable() {
            var PostData = { 
                            searchParentFieldWord:$("#searchParentFieldWord").val(),
                            searchFieldWord:$("#searchFieldWord").val()
                            };
            jQuery("#myDataTable").setGridParam({ page:1, 
                                                  url: '<%= ViewData["rootUri"] %>' + 'Settings/SearchFieldList',
                                                  postData: PostData 
                                                }).trigger('reloadGrid');
        }


        var editdlg;
        function processEdit(sel_id) {
             $.ajax({
                    url: '<%= ViewData["rootUri"] %>' + 'Settings/GetFieldInfo',
                    data: {
                        "uid": sel_id
                    },
                    type: "post",
                    success: function (date) {
                        if (date != null) {
                           $("#editname").val(date.name);
                           $("#edituid").val(sel_id);
                        }
                        else {
                        }
                    }
                });
            editdlg = $("#dialog-message2").removeClass('hide').dialog({
                modal: true,
                width: 550,
                title: "修改字段",
                title_html: true,
                buttons: [
					{
					    text: "确定",
					    "class": "btn btn-primary btn-xs",
					    click: function () {
					        $.ajax({
					            type: "POST",
					            url: '<%= ViewData["rootUri"] %>' + 'Settings/SubmitEditField',
					            dataType: "json",
					            data: {
                                        uid:sel_id,
                                        name:$("#editname").val()
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
                                    $("#tbldata").trigger("reloadGrid");
					            },
					            error: function (data) {
					                alert("Error: " + data.status);
					                //$('.loading-btn').button('reset');
					            }
					        });
					        editdlg.dialog("close");
                            $("#myDataTable").trigger("reloadGrid");
					    }
					},
					{
					    text: "取消",
					    "class": "btn btn-xs",
					    click: function () {
					        $(this).dialog("close");
					    }
					}
				]
            });
        }
    </script>
</asp:Content>
