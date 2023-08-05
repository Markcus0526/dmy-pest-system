<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>
<%@ Import Namespace="BingChongBackend.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header">
	<div>
        <div class="col-xs-2 no-padding-left">
            <h5>用户管理</h5>
        </div>
		<div class="pull-right">
            <p>
            	<%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("AddUser"))
               {%>
                <a class="btn btn-sm btn-white  btn-default btn-bold" href="<%= ViewData["rootUri"] %>Settings/AddUser">
	                <i class="ace-icon fa fa-plus blue"></i>添加用户
                </a>
                <%} %>
                <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("BatchDeleteUser"))
               {%>
                <a class="btn btn-sm btn-white  btn-default btn-bold" onclick="MultiprocessDel()">
                    <i class="ace-icon fa fa-trash-o bigger-120 blue"></i>批量删除
                </a>
                <%} %>
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
                            <div class="form-group" style="">
                                <label class="col-sm-1 control-label no-padding-right">级别：</label>
				                <div class="col-sm-1">
                                    <div class="clearfix">
						                <select class="select2 unsearch" name="userlevel" id="userlevel" data-placeholder="请选择">
                                              <% if (ViewData["SearchRegion"] != null) {
                                                   foreach (var item in (List<SearchList>)ViewData["SearchRegion"])
                                                    {%>
                                                        <option value="<%= item.uid %>" ><%= item.name%></option>
                                                    <%}
                                          
                                               }%>
				                        </select>                       
				                        </select>
                                    </div>
				                </div>  
                                <label class="col-sm-1 control-label no-padding-right">省/自治区：</label>
				                <div class="col-sm-2">
                                    <div class="clearfix">
						                <select class="select2 search" name="sheng" id="sheng" data-placeholder="请选择" onchange="ChangeShiList()">
                                            <option value="0" selected="selected">全部</option>
                                             <% if (ViewData["shenglist"] != null)
                                                {
                                                    foreach (var item in (List<RegionList>)ViewData["shenglist"])
                                                    {%>
                                                        <option value="<%= item.uid %>" ><%= item.name%></option>
                                                    <%}
                                          
                                               }%>
				                        </select>
                                    </div>
				                </div>  
                                <label class="col-sm-1 control-label no-padding-right" >盟/市：</label>
				                <div class="col-sm-1">
                                    <div class="clearfix">
						                <select class="select2 unsearch" id="shi" name="shi" data-placeholder="请选择" onchange="ChangeXianList(this.value)">
                                            <option value="0" >全部</option>
                                          
				                        </select>
                                    </div>
				                </div> 
                               
                            </div>
                            <div class="form-group" style="margin-bottom:0px">
	                            <label class="col-sm-1 control-label no-padding-right"onchange="">旗/县：</label>
				                <div class="col-sm-1">
                                    <div class="clearfix">
						                <select class="select2 unsearch" id="xian" name="xian" data-placeholder="请选择">
                                            <option value="0" >全部</option>
                                            
				                        </select>
                                    </div>
				                </div> 
                                <label class="col-sm-1 control-label no-padding-right">角色：</label>
				                <div class="col-sm-3">
                                    <div class="clearfix">
						                <select class="select2 unsearch" id="roleid" name="roleid" data-placeholder="请选择">
                                         <option value="-1">全部</option>
                                         <option value="0">测报员</option>
                                             <% if (ViewData["rolelist"] != null)
                                                {
                                                    foreach (var item in (List<tbl_right>)ViewData["rolelist"])
                                                    { %>
                                                        <option value="<%= item.uid %>" ><%= item.name %></option>
                                                <% 
                                                    }
                                                %>
                                            <% } %>
				                        </select>
                                    </div>
				                </div> 
                                <div class="col-sm-2">
						            <input value="" placeholder="输入用户名" name="username" id="username" style="height:28px;margin-top:1px"/>
                                </div>   
                                <div class="col-sm-1" style="">
						            <a class="btn btn-sm btn-info" id="searchdata" onclick=""><i class="fa fa-search"></i> 搜索</a>
                                </div>
                            </div> 
                        </form>
                    </div>
				</div>
			</div>
		</div>
        <div style="margin-top:10px">
			<table id="tbldata" class="table-hover">
			</table>
            <div id="pager10" style="height:50px"></div>
        </div>
    </div>
</div>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/select2.css" />
    <link href="<%= ViewData["rootUri"] %>Content/css/ui.jqgrid.css" rel="Stylesheet"/>  
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/jquery-ui.min.css" />
	<link rel="stylesheet" type="text/css" href="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.min.css" />
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/i18n/grid.locale-cn.js" type="text/javascript"></script>
	<script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>
	<script src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>      
    <script type="text/javascript">
        jQuery(function ($) {
            $(".search").css('width', '150px').select2({ allowClear: true, minimumResultsForSearch : -1});

            $(".unsearch").css('width', '100px').select2({allowClear: true, minimumResultsForSearch: -1 });

            $("#tbldata").jqGrid({
                multiselect: true, //复选
                url: '<%= ViewData["rootUri"] %>' + 'Settings/RetrieveUserList',
                datatype: "json",
                height: 313,
                autowidth:  true,
                rowNum: 10,
                rowList: [10, 20, 30],
                pager: '#pager10',

                sortname: 'id',
                viewrecords: true,
                sortable: false,
                pagerpos: "center",
                pgbuttons: true,
                rownumbers: true,
                scrollOffset: 0,
                postData:{
                    userlevel:$("#userlevel").val()
                },
                viewsortcols: [false, 'vertical', false],
                colNames: ['id','级别', '用户名','用户代码','姓名','角色','操作'],
                colModel: [
                            { name: 'id', index: 'id', width: 1, align: "center" },
                            { name: 'level', index: 'level', align: "center",
                                formatter: function (cellvalue, options, rowObject) {
                                    if (cellvalue == 1) {
                                        return "县级";
                                    }
                                    if (cellvalue == 2) {
                                        return "市级";
                                    }
                                    if (cellvalue == 3) {
                                        return "省级";
                                    }
                                    if (cellvalue == 4) {
                                        return "国家级";
                                    }
                                    if (cellvalue == 0) {
                                        return "超管";
                                    }
                                }
                             },
                            { name: 'username', index: 'name', align: "center",title: false },
                            { name: 'serial', index: 'serial', align: "center",title: false },
                            { name: 'realname', index: 'realname', align: "center",title: false },
                            { name: 'role', index: 'role', align: "center",title: false },
                            { name: 'uid', index: 'uid', align: "center", width:"300",title: false,
                                formatter: function (cellvalue, options, rowObject) {
                                    var html = "";
                                    <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("ViewUser")) {%>
                                    html += '<a  href=" <%= ViewData["rootUri"] %>Settings/ViewUser/' + cellvalue + '"  style="font-size:12px">查看</a>&nbsp;&nbsp;';
                                    <%} %>
                                    
                                    <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("UpdateUser")) {%>
                                    html += '<a  href=" <%= ViewData["rootUri"] %>Settings/EditUser/' + cellvalue + '"   style="font-size:12px">修改</a>&nbsp;&nbsp;';
                                    <%} %>
                                    if (cellvalue!=1) {
                                        <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("DeleteUser")) {%>
                                        html += '<a  href="#" onclick="processDel(' + cellvalue + ')" style="font-size:12px">删除</a>';
                                        <%} %>
                                    }
                                    return html;
                                }
                            }
                ],
                gridComplete: function () {
                    $("#jqg_tbldata_1").attr("disabled",true);
                    $("#jqg_tbldata_1").addClass("hide");
                }
            });
            $("#tbldata").jqGrid('hideCol', "id");
            $("#tbldata").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });
            $("#searchdata").click(function () {
                var PostData = { level: $("#userlevel").val(),
                    sheng: $("#sheng").val(),
                    shi: $("#shi").val(),
                    xian: $("#xian").val(),
                    roleid: $("#roleid").val(),
                    username: $("#username").val()
                };
                $("#tbldata").setGridParam({page:1, url: '<%= ViewData["rootUri"] %>Settings/SearchUserList', postData: PostData }).trigger('reloadGrid');
            });
            $( "#tbldata").setSelection('6',true);
        });

        function ChangeShiList(){
            var shengid=$("#sheng").val();
            var rhtml=" <option value='0' >全部</option>";
            $.ajax({
                type: "GET",
                url: '<%= ViewData["rootUri"] %>'+ "Settings/ChangUserManageShiList",
                data: { "shengid": shengid },
                method: 'post',
                dataType: "json",
                success: function (data) {
                    if (data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            rhtml += "<option value='" + data[i].uid + "'>" + data[i].name + "</option>";
                        }
                        $("#shi").html(rhtml);
                        $("#shi").select2({ allowClear: true, minimumResultsForSearch: -1});

                    }
                    else{
                    $("#shi").html(rhtml);
                    $("#shi").select2({ allowClear: true, minimumResultsForSearch: -1 });

                    }
                }
            });
            ChangeXianList(0);
        }

         function ChangeXianList(shiid){
            //var shiid=$("#shi").val();
            var rhtml=" <option value='0' >全部</option>";
            $.ajax({
                type: "GET",
                url:  '<%= ViewData["rootUri"] %>'+ 'Settings/ChangUserManageXianList',
                data: { "shiid": shiid },
                method: 'post',
                dataType: "json",
                success: function (data) {
                    if (data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            rhtml += "<option value='" + data[i].uid + "'>" + data[i].name + "</option>";
                        }
                        $("#xian").html(rhtml);
                        $("#xian").select2({ allowClear: true, minimumResultsForSearch: -1});

                    }
                    else{
                    $("#xian").html(rhtml);
                    $("#xian").select2({ allowClear: true, minimumResultsForSearch: -1 });

                    }
                },
            });
        }
        function MultiprocessDel(){
            var con=confirm("是否确定要删除相关用户");
              if(con==true){
                var ids = $("#tbldata").jqGrid("getGridParam", "selarrrow");
                var uids = new Array();
                var ss = "";
                for (var i = 0; i < ids.length; i++) {
                    var id = ids[i];
                    uids[i] = $("#tbldata").jqGrid("getCell", id, "id");
                    ss+= uids[i] + ",";
                }
                if (ss!=null&&ss!="") {
                    $.ajax({
                        type: "post",
                        data: { delids: ss
                        },
                        url: "<%= ViewData["rootUri"] %>Settings/DeleteUser",
                        dataType: "json",
                        success: function (success) {
                            if (success == true) {
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
                                toastr["success"]("删除成功！", "恭喜您");
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
                                toastr["error"]("操作失败！", "温馨敬告");
                            }
                            setTimeout(function () {
                                $("#tbldata").trigger("reloadGrid");
                            }, 2500);
                        }

                    });
                }
                else {
                    alert('请选择要删除的用户');
                }
              }
        }

          function processDel(sel_id) {
            var selected_id = "";

            if (sel_id != null && sel_id.length != "") {
                selected_id = sel_id;
            } else {
                $(':checkbox:checked').each(function () {
                    if ($(this).attr('name') == 'selcheckbox')
                        selected_id += $(this).attr('value') + ",";
                });
            }

            if (selected_id != "") {
                var con = confirm("是否要删除相关角色?");
                if (con == true) {
                    $.ajax({
                        url: '<%= ViewData["rootUri"] %>' + 'Settings/DeleteUser',
                        data: {
                            "delids": selected_id
                        },
                        type: "post",
                        success: function (message) {
                            if (message == true) {
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
                                toastr["success"]("删除成功！", "恭喜您");

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
                                toastr["error"]("操作失败！", "温馨敬告");
                            }
                            setTimeout(function () {
                            $("#tbldata").trigger("reloadGrid");
                            }, 2500);
                        }
                    });
                }
                
            }
            else {
                //
            }
            return false;
        }
    </script>
</asp:Content>
