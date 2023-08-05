<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>
<%@ Import Namespace="BingChongBackend.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header">
	<div>
        <div class="col-xs-2 no-padding-left">
            <h5>系统恢复</h5>
        </div>
		<div class="pull-right">
            <p>
                <a class="btn btn-sm btn-white  btn-default btn-bold" href="#" onclick="recover('base')">
	                恢复到基础数据
                </a>
                <a class="btn btn-sm btn-white  btn-default btn-bold" href="#" onclick="recover('factory')">
	                恢复到初始状态
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
                        <form class="form-horizontal" role="form" id="validation-form">
                            <!-- 第一行-->
                            <div class="form-group" style="margin-bottom:0px" >
                                <label class="col-sm-1 control-label no-padding-right" for="">备份周期：</label>
				                <div class="col-sm-3">
                                    <div class="clearfix">
						                <input  type="number" min="1"   <%if (ViewData["info"]!=null) {%> value="<%=((dbbackup_info)ViewData["info"]).backupperiod %>" <% } else{%> value="5" <%}%> id="peroid" name="peroid"style="width:80px;" />天
                                        <select class="select2" id="time" name="time" style="width:60px"/>
                                            <option value="0">0:00</option>                                        
                                            <%for (int i = 1; i <24;i++ ) {
                                            %> 
                                                <option value="<%=i%>" <%if (ViewData["time"]!=null&&(int)ViewData["time"]==i) {%>selected<% } %>><%=i%>:00</option>                                                  
                                            
                                            <% } %>
                                        </select>
                                    </div>
				                </div> 
                                <label class="col-sm-1 control-label no-padding-right" for="">备份保留：</label>
				                <div class="col-sm-2">
                                    <div class="clearfix">
						                <input  type="number" <%if (ViewData["info"]!=null) {%> value="<%=((dbbackup_info)ViewData["info"]).cleanupperiod %>" <% } else{%> value="70" <%}%>min="1" id="cleanypperiod" name="cleanypperiod"style="width:80px" />天
                                    </div>
				                </div> 
                                <div class="col-sm-3" style="">
						            <a class="btn btn-sm btn-info" id="searchdata" onclick="SaveBackup()"> 保存设置</a>
                                </div>  
                                <div class="col-sm-1" style="">
						            <a class="btn btn-sm btn-info" id="A1" onclick="OnBackup_dbbackup()"> 立即备份</a>
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
        </div>
    </div>
</div>
<div id="tabledlg" class="hide">
    <form class="form-horizontal" role="form" id="form1">
        <div class="col-xs-12">
            <div class="form-group " id="" >
                <table border="1" cellspacing="0" cellpadding="0" width="100%" id="tablediv">
                	
                </table>
                <div id="pager10" style="height:50px"></div>
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
            $(".select2").css('width', '100px').select2({ minimumResultsForSearch: -1 });
        
            $("#tbldata").jqGrid({
                 //multiselect: true, //复选
                url: '<%= ViewData["rootUri"] %>' + 'Settings/RetrieveBackupTable',
                datatype: "json",
                height: 313,
                width: 1000,
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
                postData:{
                    tablename:$("#tablename").val() 
                },
                viewsortcols: [false, 'vertical', false],
                colNames: ['文件名称', '备份时间', '文件大小(kb)','操作'],
                colModel: [
                            { name: 'filename', index: 'filename', align: "center", title: false },
                            { name: 'createtime', index: 'createtime', align: "center", title: false },
                            { name: 'filesize', index: 'filesize', align: "center", title: false},
                            { name: 'uid', index: 'uid', align: "center", title: false,
                                 formatter: function (cellvalue, options, rowObject) {
                                    return  '<a  href="#" onclick="recover('+ cellvalue +')" style="font-size:12px">恢复备份</a>&nbsp;&nbsp;';
                                            }                                    
                            }
                ],
                gridComplete: function () {
                }
            });
            $("#tbldata").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });
        });

        function SaveBackup() {
            $.ajax({
					type: "POST",
					url: "<%= ViewData["rootUri"] %>" + "Settings/SubmitBackupSetting",
					data: {
					    peroid: $("#peroid").val(),
					    time: $("#time").val(),
                        cleanypperiod:$("#cleanypperiod").val()
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
					            toastr["success"]("备份成功！", "恭喜您");
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
                    $("#tbldata").trigger("reloadGrid");   
					}
				});
        }

        function OnBackup_dbbackup() {   
            $.ajax({
                type: "POST",
                url: "<%= ViewData["rootUri"] %>" + "Settings/Backup",
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
                                toastr["success"]("备份成功！", "恭喜您");

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
            return false;
        }
        function recover(flag){
            var con = confirm("请谨慎操作！确认恢复？");
            if (con == true) {
                    $.ajax({
                        url: '<%= ViewData["rootUri"] %>' + 'Settings/RestoreTo',
                        data: {
                            "flag": flag
                        },
                        type: "post",
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
                                toastr["success"]("恢复成功！", "恭喜您");

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
    </script>
</asp:Content>
