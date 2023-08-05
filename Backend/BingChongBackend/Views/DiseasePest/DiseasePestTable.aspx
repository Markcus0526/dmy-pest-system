<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header">
	<div>
        <div class="col-xs-2 no-padding-left">
            <h5>测报表格</h5>
        </div>
		<div class="pull-right">
            <p>

                <a class="btn btn-white btn-warning btn-bold" style="display:none;" id="A1" onclick="processDel();">
                    <i class="ace-icon fa fa-trash-o bigger-120 orange"></i>批量删除
                </a>
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
                        <form class="form-horizontal" role="form" id="Form1">
                             <!-- 第一行-->
                            <div class="form-group" style="margin-bottom:0px">
                                <label class="col-sm-1 control-label no-padding-right" for="">表格名称：</label>
				                <div class="col-sm-2">
                                    <div class="clearfix">
						               <input id="name" style="width:150px;height:30px;"/> 
                                    </div>
				                </div>  
                                <div class="col-sm-1" style="">
						            <a class="btn btn-sm btn-info" id="searchdata" onclick="searchtable()"><i class="fa fa-search"></i> 搜索</a>
                                </div>
                            </div>
                        </form>
                    </div>
				</div>
			</div>
		</div>
        <div style="margin-top:10px">
		    <table id="myDataTable" class="">
			</table>
            <div id="pager10" style="height:50px"></div>
        </div>
    </div>
    
</div>
<div id="tabledlg" class="hide">
    <form class="form-horizontal" role="form" id="form2">
        <div class="col-xs-12">
            <div class="form-group " id="Div1" >
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
    $(function () {
         // $(".select2").css('width', '150px').select2({ minimumResultsForSearch: -1 }); 
            $("#myDataTable").jqGrid({
                viewsortcols: [false, 'vertical', false],
                url: ' <%= ViewData["rootUri"] %>DiseasePest/FindAllTable',
                datatype: "json",
                //  rowNum: 20,
                height: 481,
                width: 1000,
                mtype: 'post',
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
                //multiboxonly:true,
               // multiselect:true,
                postData: {
                    name: $("#name").val(),
                },
                colNames: ['序号', '表名称',  '操作'],
                colModel: [
                            { name: 'uid', index: 'operat_people', align: "center" },
                            { name: 'name', index: 'operat_time', align: "center" },
                             { name: 'operation', index: 'operation', width: 150, align: "center",
                                 formatter: function (cellvalue, options, rowObject) {
                                    var html = "";
                                    <%if (ViewData["role"].ToString().Contains("ViewReportTable")) {%>
                                    html += '<a  href="#" onclick="showtable(' + rowObject.uid + ')" style="font-size:12px">查看</a>&nbsp;&nbsp;';
                                    <%}%> 
                                    <%if (ViewData["role"] != null && ViewData["role"].ToString().Contains("DownloadReportTable")) {%>
                                    html +='<a  href="#" onclick="DownLoad(' + rowObject.uid + ')" style="font-size:12px">下载</a>';
                                    <%} %>
                                    return html;
                                 }
                             },
                     ]
            });
             $("#myDataTable").jqGrid('hideCol', "uid");
             $("#myDataTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });

        });
        function searchtable(){
         var PostData = { name: $("#name").val() };
            var tabl = $("#myDataTable");
            tabl.setGridParam({  url: ' <%= ViewData["rootUri"] %>DiseasePest/FindAllTable', postData: PostData }).trigger('reloadGrid');

        }

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
        function DownLoad(formid) {
            location.href="<%= ViewData["rootUri"] %>UpdatedInfo/DownloadTable?formid="+formid;
        }
    </script>
</asp:Content>
