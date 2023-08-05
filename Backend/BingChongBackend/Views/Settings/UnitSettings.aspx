<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>
<%@ Import Namespace="BingChongBackend.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<% var role = CommonModel.GetUserRoleInfo(); %>

<div class="page-header">
	<div>
        <div class="col-md-2 no-padding-left">
            <h5>直属单位设置</h5>
        </div>
		<div class="pull-right">
            <p>
                <% if (role != null && role.Contains("AddAreaUnit"))
                { %>
					<a class="btn btn-sm btn-white  btn-default" id="A1" href="<%= ViewData["rootUri"] %>Settings/AddUnit" >
                        <i class="ace-icon fa fa-plus blue"></i> 添加直属单位</a>
                <%} %>
            </p>
		</div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="widget-box ">
			<div class="widget-body">
				<div class="widget-main">
                    <div class="searchbar">
                        <form class="form-horizontal" role="form" id="validation-form">
                            <div class="form-group" style="margin-bottom:0px">
                                <label class="col-md-2 control-label no-padding-right" for="starttime">单位/部门级别：</label>
				                <div class="col-md-2">
                                    <div class="clearfix">
						               <select class="select2" id="searchlevel" name="searchlevel" onchange="ChangeRegionList()">
				                            <% if (ViewData["RegionOptions"] != null)
                                            {
                                                    foreach (var item in (List<SearchList>)ViewData["RegionOptions"])
                                                    {%>
                                                        <option value="<%= item.uid %>" ><%= item.name%></option>
                                                    <%}
                                          
                                                }%>
                                        </select>                 
                                    </div>
				                </div>  
                                <label class="col-md-1  control-label no-padding-right" for="starttime">单位名称：</label>
				                <div class="col-md-2">
						            <input value="" placeholder="请输入关键字" id="searchname" name="searchname" style="height:28px;margin-top:1px"/>
                                </div>   
                                <div class="col-md-1" style="">
						            <a class="btn btn-sm btn-info" id="searchdata" onclick=""><i class="fa fa-search"></i> 搜索</a>
                                </div>   
                            </div>
                        </form>
                    </div>
				</div>
			</div>
		</div>
        <div style="margin-top:10px">
			<table id="tbldata" class="">
			</table>
            <div id="pager10" style="height:50px"></div>            
        </div>
    </div>
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
            $(".select2").css('width', '100px').select2({ minimumResultsForSearch: -1 });
          
        })

        $(function () {
            $("#tbldata").jqGrid({
                // multiselect: true, //复选
                url: '<%= ViewData["rootUri"] %>' + 'Settings/RetrieveUnitList',
                datatype: "json",
                height: 481,
                autowidth:  true,              
                rowNum: 15,
                rowList: [15, 20, 30],
                pager: '#pager10',

                sortname: 'id',
                viewrecords: true,
                sortable: false,
                pagerpos: "center",
                pgbuttons: true,
                rownumbers: true,
                scrollOffset: 1,
                postData:{
                    searchlevel:$("#searchlevel").val()
                },
                viewsortcols: [false, 'vertical', false],
                colNames: ['单位编号', '单位名称', '单位/部门级别', '负责人', '移动电话','操作'],
                colModel: [
                            { name: 'serial', index: 'serial', align: "center" ,title: false},
                            { name: 'name', index: 'name', align: "center" ,title: false},
                            { name: 'level', index: 'type', align: "center",title: false,
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
                                } 
                            },
                            { name: 'chief', index: 'chief', align: "center" ,title: false},
                            { name: 'mobile', index: 'mobile', align: "center" ,title: false},
                            { name: 'uid', index: 'uid', align: "center", title: false,
                                formatter: function (cellvalue, options, rowObject) {
                                    var html = "";
                                    <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("ViewAreaUnit")) {%>
                                    html+='<a  href=" <%= ViewData["rootUri"] %>Settings/ViewUnit/' + cellvalue + '"  style="font-size:12px">查看</a>&nbsp;&nbsp;';
                                    <%} %>
                                    <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("UpdateAreaUnit")) {%>
                                    html+='<a  href=" <%= ViewData["rootUri"] %>Settings/EditUnit/' + cellvalue + '"   style="font-size:12px">修改</a>&nbsp;&nbsp;';
                                    <%} %>
                                    <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("DeleteAreaUnit")) {%>
                                        html+='<a  href="#" onclick="processDel(' + cellvalue + ')" style="font-size:12px">删除</a>';
                                    <% } %>
                                    return html;
                                }
                           }
                ],
                gridComplete: function () {
                }
            });
            $("#tbldata").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });

            $("#searchdata").click(function () {
                var PostData = { searchlevel: $("#searchlevel").val(), searchname: $("#searchname").val() };
                jQuery("#tbldata").setGridParam({ url: '<%= ViewData["rootUri"] %>Settings/SearchUnitList', postData: PostData }).trigger('reloadGrid');
            });
        });

        function processDel(sel_id) {

            if (sel_id != "") {
                var con = confirm("是否要删除直属单位?");
                if (con == true) {
                    $.ajax({
                        url: '<%= ViewData["rootUri"] %>' + 'Settings/DeleteUnit',
                        data: {
                            "delids": sel_id
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
