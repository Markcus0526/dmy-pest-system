<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header">
	<div>
        <div class="col-xs-2 no-padding-left">
            <h5>表格管理</h5>
        </div>
		<div class="pull-right">
            <p>
                <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("AddReportTable"))
               {%>
                <a class="btn btn-sm btn-white  btn-default btn-bold" href="<%= ViewData["rootUri"] %>Settings/AddTable">
	                <i class="ace-icon fa fa-plus blue"></i>新建
                </a>
                <%} %>
                <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("BatchDeleteReportTable"))
               {%>
                <a class="btn btn-sm btn-white  btn-default btn-bold" style="" id="A1" onclick="MultiprocessDel();">
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
                            <!-- 第一行-->
                            <div class="form-group" style="margin-bottom:0px">
                                <label class="col-sm-1 control-label no-padding-right" for="">表格名称：</label>
				                <div class="col-sm-2">
                                    <div class="clearfix">
						                <input  value="" id="tablename" name="tablename" placeholder="请输入关键字" style="height:28px;margin-top:1px"/>
                                    </div>
				                </div> 
                                <div class="col-sm-1" style="">
						            <a class="btn btn-sm btn-info" id="searchdata" onclick="SearchTable()"><i class="fa fa-search"></i> 搜索</a>
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
<div id="tabledlg" class="hide">
    <form class="form-horizontal" role="form" id="form1">
        <div class="col-xs-12">
            <div class="form-group " id="" >
                <table border="1" cellspacing="0" cellpadding="0" width="100%" id="tablediv">
                	
                </table>
            </div>
        </div>    
    </form>
</div>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
    <link href="<%= ViewData["rootUri"] %>Content/css/ui.jqgrid.css" rel="Stylesheet"/> 
    <link rel="stylesheet" type="text/css" href="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.min.css" /> 
    <link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/jquery-ui.min.css" /> 
    <link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/select2.css" /> 

</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/i18n/grid.locale-cn.js" type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery.ui.touch-punch.min.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>

    <script type="text/javascript">
        jQuery(function ($) {
            $(".select2").css('width', '100px').select2({ allowClear: true });
        
            $("#tbldata").jqGrid({
                 multiselect: true, //复选
                url: '<%= ViewData["rootUri"] %>' + 'Settings/RetrieveTableList',
                datatype: "json",
                height: 321,
                width: 1000,
                rowNum: 10,
                rowList: [10,20, 30],
                pager: '#pager10',

                sortname: 'id',
                viewrecords: true,
                sortable: false,
                pagerpos: "center",
                pgbuttons: true,
                rownumbers: true,
                scrollOffset: 1,
                postData:{
                    tablename:$("#tablename").val() 
                },
                viewsortcols: [false, 'vertical', false],
                colNames: ['id','名称', '操作'],
                colModel: [
                            { name: 'id', index: 'id', width: 1, align: "center" },
                            { name: 'name', index: 'name', align: "center", title: false },
                            { name: 'uid', index: 'uid', align: "center", title: false,
                                formatter: function (cellvalue, options, rowObject) {
                                    var html = "";
                                    <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("ViewReportTable")) {%>
                                     html += '<a  href="#" onclick="showtable(' + cellvalue + ')" style="font-size:12px">查看</a>&nbsp;&nbsp;';
                                    <%} %>
                                    <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("DeleteReportTable")) {%>
                                    html +='<a  href="#" onclick="processDel(' + cellvalue + ')" style="font-size:12px">删除</a>&nbsp;&nbsp;';
                                    <%} %>
                                    <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("DownloadReportTable")) {%>
                                    html +='<a  href="#" onclick="DownLoad(' + cellvalue + ')" style="font-size:12px">下载</a>';
                                    <%} %>
                                    return html;
                                }
                            }
                ],
                gridComplete: function () {
                }
            });
            $("#tbldata").jqGrid('hideCol', "id");
            $("#tbldata").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });
        });
        
        var tabledlg;

        function showtable(tableid) {
            
            if (tableid != null) {
                $.ajax({
                    type: "GET",
                    url: '<%= ViewData["rootUri"] %>'+ "Settings/GetTableHtml",
                    data: { "tableid": tableid},
                    method: 'post',
                    dataType: "json",
                    success: function (data) {
                        if (data.length > 0) {
                            $("#tablediv").html(data);

                        }
                        else{
                        }
                    },
                });
            }

            tabledlg = $("#tabledlg").removeClass('hide').dialog({
                modal: true,
                width: 800,
            });
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
                var con = confirm("是否要删除相关表格?");
                if (con == true) {
                    $.ajax({
                        url: '<%= ViewData["rootUri"] %>' + 'Settings/DeleteTable',
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
        function SearchTable() {
            var PostData = { 
                            tablename:$("#tablename").val()
                            };
            jQuery("#tbldata").setGridParam({ url: '<%= ViewData["rootUri"] %>' + 'Settings/RetrieveTableList',
                                                  postData: PostData 
                                                }).trigger('reloadGrid');
        }
        function DownLoad(formid) {

         location.href="<%= ViewData["rootUri"] %>UpdatedInfo/DownloadTable?formid="+formid;
       
        }

        function MultiprocessDel(){
            var con=confirm("是否确定要删除相关表格");
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
                        url: "<%= ViewData["rootUri"] %>Settings/DeleteTable",
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
                    alert('请选择要删除的表格');
                }
              }
        }
    </script>
</asp:Content>
