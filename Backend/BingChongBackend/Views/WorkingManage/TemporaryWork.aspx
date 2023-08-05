<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>
<%@ Import Namespace="BingChongBackend.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header">
	<div class="">
        <div class="col-xs-2 no-padding-left">
            <h5>临时工作</h5>
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
                            <!-- 第一行-->
                            <div class="form-group" style="margin-bottom:0px">
                                <label class="col-sm-1 control-label no-padding-right" for="">年度：</label>
				                <div class="col-sm-1">
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
                                <label class="col-sm-1 control-label no-padding-right" for="">省/自治区：</label>
				                <div class="col-sm-1">
                                    <div class="clearfix">
						                <select class="select2" id="sheng" name="sheng" data-placeholder="请选择" onchange="ChangeShiList()">
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
                                <label class="col-sm-1 control-label no-padding-right" for="">盟/市：</label>
				                <div class="col-sm-1">
                                    <div class="clearfix">
						                <select class="select2" id="shi" name="shi" data-placeholder="请选择" onchange="ChangeXianList();">
                                            <option value="0" selected="selected">全部</option>
                                            <option value="" ></option>                         
				                        </select>
                                    </div>
				                </div>  
                              
                                <label class="col-sm-1 control-label no-padding-right" for="">旗/县：</label>
				                <div class="col-sm-2">
                                    <div class="clearfix">
						                <select class="select2" id="xian" name="xian" data-placeholder="请选择">
                                            <option value="" selected="selected">全部</option>
                                            <option value="" ></option>                         
				                        </select>
                                    </div>
				                </div>  
                                <div class="col-sm-1" style="">
						        <a class="btn btn-sm btn-info" id="searchdata" onclick="SerchTable()"><i class="fa fa-search"></i> 搜索</a>
                                </div>   
                            </div>
                        </form>
                    </div>
				</div>
			</div>
		</div>
        <div style="margin-top:10px; margin-right:17px" >
			<table id="myDataTable" class="table-hover">
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
	<script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>
        <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery.ui.touch-punch.min.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>
	<script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/i18n/grid.locale-cn.js" type="text/javascript"></script>
    <script type="text/javascript">
        jQuery(function ($) {
            $(".select2").css('width', '100px').select2({ minimumResultsForSearch: -1 });

            $(function () {
                $("#myDataTable").jqGrid({
                    // multiselect: true, //复选
                    url: '<%= ViewData["rootUri"] %>' + 'WorkingManage/RetrieveTeampWork',
                    datatype: "json",
                    height: 400,
                   // width: 1100,
                    autowidth:true,
                    rowNum: 15,
                    rowList: [5, 15, 20, 30],
                    pager: '#pager10',

                    sortname: 'id',
                    viewrecords: true,
                    sortable: false,
                    pagerpos: "center",
                    pgbuttons: true,
                    rownumbers: true,
                    scrollOffset: 1,
                    postData:{
                        year:$("#year").val(),
                        sheng:$("#sheng").val()
                    },
                    viewsortcols: [false, 'vertical', false],
                    colNames: ['发布日期', '完成日期', '省/自治区', '盟/市', '旗/县', '工作名称', '发布者', '接受者', '审核状态'],
                    colModel: [
                            { name: 'notice_date', index: 'notice_date', align: "center" },
                            { name: 'report_date', index: 'report_date', align: "center" },
                            { name: 'sheng', index: 'sheng', align: "center" },
                            { name: 'shi', index: 'shi', align: "center" },
                            { name: 'xian', index: 'xian', align: "center" },
                            { name: 'name', index: 'name', align: "center" },
                            { name: 'adminname', index: 'adminname', align: "center" },
                            { name: 'reciever', index: 'recievername', align: "center" },
                            { name: 'status', index: 'status', align: "center",
                                formatter: function (cellvalue, options, rowObject) {
                                    if (cellvalue == "0") {
                                        return "等待";
                                    }
                                    if (cellvalue == "1") {
                                        return "已接受";
                                    }
                                    if (cellvalue == "2") {
                                        return "已完成";
                                    }
                                } 
                            }
                    ],
                    gridComplete: function () {
                    }
                });
                $("#myDataTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });
            });
            ChangeShiList();
        });
        function ChangeShiList(){
            var shengid=$("#sheng").val();
            var level=<%= ViewData["level"]  %>;
            var rhtml;
            if (level==1||level==2) {
                rhtml="";            
            }
            else{
                rhtml="<option value='0'>全部</option>";
            }
            $.ajax({
                type: "GET",
                url: '<%= ViewData["rootUri"] %>'+ "Settings/ChangUserManageShiList",
                data: { "shengid": shengid },
                 async: false,
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
                },
            });
            ChangeXianList();
        }
         function ChangeXianList(){
            var shiid=$("#shi").val();
            var level=<%= ViewData["level"]  %>;
            var rhtml;
            if (level==1) {
                rhtml="";            
            }
            else{
                rhtml="<option value='0'>全部</option>";
            }
            $.ajax({
                type: "GET",
                url:  '<%= ViewData["rootUri"] %>'+ 'Settings/ChangUserManageXianList',
                data: { "shiid": shiid },
                method: 'post',
                async: false,
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

        function SerchTable() {
            var PostData = { 
                            year:$("#year").val(),
                            shengid:$("#sheng").val(),
                            shiid:$("#shi").val(),
                            xianid:$("#xian").val()
                            };
            jQuery("#myDataTable").setGridParam({ url: '<%= ViewData["rootUri"] %>' + 'WorkingManage/SearchTeampWork',
                                                  postData: PostData 
                                                }).trigger('reloadGrid');
        }
    </script>
</asp:Content>
