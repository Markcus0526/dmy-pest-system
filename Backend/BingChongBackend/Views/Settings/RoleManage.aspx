<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>
<%@ Import Namespace="BingChongBackend.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<% var role = CommonModel.GetUserRoleInfo(); %>

<div class="page-header">
	<div>
        <div class="col-xs-2 no-padding-left">
            <h5>角色管理</h5>
        </div>
		<div class="pull-right">
            <p>
            <% if (role != null && ((string)role).Contains("AddRight"))
            { %> 
                <a class="btn btn-sm btn-white  btn-default btn-bold" href="<%= ViewData["rootUri"] %>Settings/AddRole">
	                <i class="ace-icon fa fa-plus blue"></i>添加角色
                </a>
            <%} %>
            </p>
		</div>
    </div>
</div>
<div class="row">
    <div class="col-xs-12">
        <span class="col-sm-2"></span>
        <div class="col-sm-10" style="margin-top:10px;" >
			<table id="tbldata" class="table-hover">
			</table>
            <div id="pager10" style="height:50px"></div>
        </div>
    </div>
</div>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/select2.css" />
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/jquery-ui.min.css" />
    <link href="<%= ViewData["rootUri"] %>Content/css/ui.jqgrid.css" rel="Stylesheet"/>  
	<link rel="stylesheet" type="text/css" href="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.min.css" />
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
	<script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/i18n/grid.locale-cn.js" type="text/javascript"></script>
	<script src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>  

    <script type="text/javascript">
        jQuery(function ($) {
            $(".select2").css('width', '100px').select({ allowClear: true });

            $("#tbldata").jqGrid({
                // multiselect: true, //复选
                url: '<%= ViewData["rootUri"] %>' + 'Settings/RetrieveRoleList',
                datatype: "json",
                height: 321,
                width: 600,
                rowNum: 10,
                rowList: [10, 20, 30],
                pager: '#pager10',

                sortname: 'id',
                viewrecords: true,
                sortable: false,
                pagerpos: "center",
                pgbuttons: true,
                rownumbers: true,
                scrollOffset: 1,

                viewsortcols: [false, 'vertical', false],
                colNames: ['角色名称', '操作'],
                colModel: [
                            { name: 'name', index: 'name', align: "center", title: false },
                            { name: 'uid', index: 'uid', align: "center", title: false,
                                formatter: function (cellvalue, options, rowObject) {
                                    var html = "";
                                    <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("ViewRight")) {%>
                                    html+='<a  href=" <%= ViewData["rootUri"] %>Settings/ViewRole/' + cellvalue + '"  style="font-size:12px">查看</a>&nbsp;&nbsp;';
                                    <%} %>
                                    <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("UpdateRight")) {%>
                                    html+='<a  href=" <%= ViewData["rootUri"] %>Settings/EditRole/' + cellvalue + '"   style="font-size:12px">修改</a>&nbsp;&nbsp;';
                                    <%} %>
                                    <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("DeleteRight")) {%>
                                    html+='<a  href="#" onclick="processDel(' + cellvalue + ')" style="font-size:12px">删除</a>';
                                    <%} %>
                                    return html;
                                }
                            }
                ],
                gridComplete: function () {
                }
            });
            $("#tbldata").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });
        });

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
                        url: '<%= ViewData["rootUri"] %>' + 'Settings/DeleteRole',
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
