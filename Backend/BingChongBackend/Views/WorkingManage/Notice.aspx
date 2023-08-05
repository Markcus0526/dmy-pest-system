<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>
<%@ Import Namespace="BingChongBackend.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<% var role = CommonModel.GetUserRoleInfo(); %>

<div class="page-header">
	<div class="">
        <div class="col-xs-2 no-padding-left">
            <h5>通知</h5>
        </div>
		<div class="pull-right">
            <p>
                <% if (role != null && ((string)role).Contains("PublishMessage"))
                { %> 
                <a class="btn btn-sm btn-white  btn-default btn-bold" href="<%= ViewData["rootUri"] %>WorkingManage/AddNotice">
	                <i class="ace-icon fa fa-plus blue"></i>发布
                </a>
                <%} %>
            </p>
		</div>
    </div>
</div>
<div class="row">
    <div class="col-xs-12" style="margin-left:12px;" >
            <label class="lbl" style="margin-left:47px"> 通知:</label>
             <% if (role != null && ((string)role).Contains("MessageUpperLevelSelection"))
                { %> 
            <div class="radio" style="display:inline-block;margin-left:20px">
				<label>
					<input name="type" type="radio" checked="true"  value="upper" onclick="refreshtable(this.value)"/>
					<span class="lbl"> 上级</span>
				</label>
			</div>
            <%} %>
            <% if (role != null && ((string)role).Contains("MessageLowerLevelSelection"))
                { %> 
			<div class="radio" style="display:inline-block">
				<label>
					<input name="type" type="radio" value="lower" checked="true" onclick="refreshtable(this.value)"/>
					<span class="lbl"> 下级</span>
				</label>
			</div>
            <%} %>

    </div>
    <div class="col-xs-12">
        <div class="widget-box">
			<div class="widget-body">
				<div class="widget-main">
                    <div class="searchbar">
                        <form class="form-horizontal" role="form" id="validation-form">
                            <!-- 第一行-->
                            <div class="form-group" style="margin-bottom:0px">
                                <label class="col-sm-1 control-label no-padding-right" for="">年度：</label>
				                <div class="col-sm-2">
                                    <div class="clearfix">
						                <select class="select2" id="year" name="year" data-placeholder="请选择">
                                           <% DateTime now = DateTime.Now;
                                              int year = now.Year;
                                              int years = year -10;
                                              for (int i = year; i >= years; i--)
                                              {%>
                                                 <option value="<%=i %>"><%=i %></option>
                                             <% }%>                                
				                        </select>
                                    </div>
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
            <div id="pager" style="height:50px"></div>

            <table id="tbldata2" class="table-hover">
			</table>
            <div id="pager2" class="hide" style="height:50px"></div>
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
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery.ui.touch-punch.min.js"></script> 
    <script src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/i18n/grid.locale-cn.js" type="text/javascript"></script>
	<script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>
    <script type="text/javascript">
        jQuery(function ($) {
            $(".select2").css('width', '100px').select2({ minimumResultsForSearch: -1 });

            $("#tbldata").jqGrid({
                // multiselect: true, //复选
                url: '<%= ViewData["rootUri"] %>' + 'WorkingManage/RetrieveNoticeList',
                datatype: "json",
                height: 313,
                width: 1000,
                rowNum: 15,
                rowList: [10, 20, 30],
                pager: '#pager',

                sortname: 'id',
                viewrecords: true,
                sortable: false,
                pagerpos: "center",
                pgbuttons: true,
                rownumbers: true,
                scrollOffset: 1,
                postData: {
                    type: "upper"
                },
                viewsortcols: [false, 'vertical', false],
                colNames: ['文号', '标题', '操作'],
                colModel: [
                            { name: 'serial', index: 'title', align: "center" },
                            { name: 'title', index: 'title', align: "center" },
                            { name: 'uid', index: 'uid', align: "center",
                                formatter: function (cellvalue, options, rowObject) {
                                     var html = "";
                                    <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("ViewUpperMessage")) {%>
                                     html = '<a  class="" href=" <%= ViewData["rootUri"] %>WorkingManage/ViewNotice/' + cellvalue + '"  style="font-size:12px">查看</a>&nbsp;&nbsp;';
                                    <%} %>
                                    return html;
                                }
                            }
                ]
            });
            $("#tbldata").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });

            $("#tbldata2").jqGrid({
                // multiselect: true, //复选
                url: '<%= ViewData["rootUri"] %>' + 'WorkingManage/RetrieveNoticeList',
                datatype: "json",
                height: 313,
                width: 1000,
                rowNum: 15,
                rowList: [10, 20, 30],
                pager: '#pager2',

                sortname: 'id',
                viewrecords: true,
                sortable: false,
                pagerpos: "center",
                pgbuttons: true,
                rownumbers: true,
                scrollOffset: 1,
                postData: {
                    type: "lower"
                },
                viewsortcols: [false, 'vertical', false],
                colNames: ['文号', '标题', '操作'],
                colModel: [
                            { name: 'serial', index: 'title', align: "center" },
                            { name: 'title', index: 'title', align: "center" },
                            { name: 'uid', index: 'uid', align: "center",
                                formatter: function (cellvalue, options, rowObject) {
                                var html = "";
                                    <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("ViewLowerMessage")) {%>
                                    html += '<a  class="" href=" <%= ViewData["rootUri"] %>WorkingManage/ViewNotice/' + cellvalue + '"  style="font-size:12px">查看</a>&nbsp;&nbsp;';
                                    <%} %>
                                    <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("UpdateMessage")) {%>
                                    html += '<a  class="" name="edit" href=" <%= ViewData["rootUri"] %>WorkingManage/EditNotice/' + cellvalue + '"   style="font-size:12px">修改</a>&nbsp;&nbsp;';
                                    <%} %>
                                    <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("DeleteMessage")) {%>
                                    html +='<a  class="" name="delete" href="#" onclick="processDel(' + cellvalue + ')" style="font-size:12px">删除</a>';
                                    <%} %>                                    
                                    return html;
                                }
                            }
                ]
            });
            $("#tbldata2").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });
            $("#searchdata").click(function () {
                var type=$('input:radio[name="type"]:checked').val();

                if (type=="upper") {
                    var PostData = { year:$("#year").val(), type:type};
                    $("#tbldata").setGridParam({ url:'<%= ViewData["rootUri"] %>WorkingManage/SearchNoticeList', postData: PostData }).trigger('reloadGrid');
                }
                else{
                    var PostData = { year:$("#year").val(), type:type};
                    $("#tbldata2").setGridParam({ url:'<%= ViewData["rootUri"] %>WorkingManage/SearchNoticeList', postData: PostData }).trigger('reloadGrid');
                }
            });
            hideHtml();
        });

        function refreshtable (){
            var type=$('input:radio[name="type"]:checked').val();
            if (type=="upper") {
                $("#gview_tbldata").removeClass("hide");
                $("#gview_tbldata2").addClass("hide");
                $("#pager").removeClass("hide");
                $("#pager2").addClass("hide");
            }
            else {
                $("#gview_tbldata2").removeClass("hide");
                $("#gview_tbldata").addClass("hide");
                $("#pager2").removeClass("hide");
                $("#pager").addClass("hide");
            }
        }
        function hideHtml() {
            <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("MessageLowerLevelSelection")) {%>
                
                  $("#gview_tbldata2").removeClass("hide");
                $("#gview_tbldata").addClass("hide");
                $("#pager2").removeClass("hide");
                $("#pager").addClass("hide");
            <%} %>
            <%else if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("MessageUpperLevelSelection")) {%>
                $("#gview_tbldata").removeClass("hide");
                $("#gview_tbldata2").addClass("hide");
                $("#pager").removeClass("hide");
                $("#pager2").addClass("hide");
            <%} %>
            <%else{ %>
                $("#gview_tbldata2").addClass("hide");
                $("#gview_tbldata").addClass("hide");
                $("#pager2").addClass("hide");
                $("#pager").addClass("hide");
            <%} %>
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
                var con = confirm("是否要删除相关信息?");
                if (con == true) {
                    $.ajax({
                        url: '<%= ViewData["rootUri"] %>' + 'WorkingManage/DeleteNotice',
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
                            var type=$('input:radio[name="type"]:checked').val();
                            setTimeout(function () {
                                if (type=="upper") {
                                $("#tbldata").trigger("reloadGrid");
                                }
                                else{
                                $("#tbldata2").trigger("reloadGrid");                                
                                }
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
