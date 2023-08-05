<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header">
	<div class="">
        <div class="col-xs-2 no-padding-left">
            <h5>年报计划</h5>
        </div>
		<div class="pull-right">
            <p>
                <a class="btn btn-sm btn-white  btn-default btn-bold" href="<%= ViewData["rootUri"] %>WorkingManage/AddWorking?planid=<%=ViewData["planid"] %>">
	                <i class="ace-icon fa fa-plus blue"></i>新增工作
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
                            <div class="form-group" style="margin-bottom:0px">
                                <label class="col-sm-2 control-label no-padding-right" style="margin-left:-70px" for="">病虫害类别：</label>
				                <div class="col-sm-2">
                                    <div class="clearfix">
						                <select class="select2" id="blkind" name="blkind" onchange="dutyset2(this.value)">
                                            <option value="2" selected="selected">全部</option>
                                            <option value="0" >病害</option>
                                            <option value="1" >虫害</option>                          
				                        </select>
                                    </div>
				                </div>  
                                <label class="col-sm-2 control-label no-padding-right" style="margin-left:-100px" for="">病虫害名称：</label>
				                <div class="col-sm-2">
                                    <div class="clearfix">
						                <select class="select2" id="blname" name="blname">    
                                            <option value="0" >全部</option>            
				                        </select>
                                    </div>
				                </div>  
                                <div class="col-sm-1" style="">
						        <a class="btn btn-sm btn-info" id="find" ><i class="fa fa-search"></i> 查询</a>
                                </div>
                                <div class="col-sm-1" style="">
						        
                                <a class="btn btn-sm btn-info" id="A3" href="javascript:history.go(-1);" ><i class="ace-icon fa fa-undo bigger-110"></i> 返回</a>
                                </div>    
                            </div>
	
                        </form>
                    </div>
				</div>
			</div>
		</div>
        <div style="margin-top:10px;margin-right:17px">
            
              <p id="p" style="display:none;font-size:16px">没有找到要搜所的测报点</p>
              <table id="myDataTable" style="text-align:center">
              </table>
              <div id="pager10" style="height:50px"></div>
          
        </div>
    </div>
</div>

<div id="dialog-message" class="hide">
    <form class="form-horizontal" role="form" id="">
        <div class="col-xs-12">
            <div class="form-group">

			    <label class="col-sm-4 control-label no-padding-right" style="margin:6px 0px;" for="">年度选择：</label>
			    <div class="col-sm-3" style="margin:6px 0px;" >
                    <select class="select2" id="Select3" name="" data-placeholder="">
				    </select>
			    </div>
            </div>
            <div class="form-group">
               <label class="col-sm-4 control-label no-padding-right" style="margin:6px 0px;" for=""> 省/自治区：</label>
			   <div class="col-sm-3" style="margin:6px 0px;" >
                   <select class="select2" id="" name="newgroup" data-placeholder="">
				   </select>
			    </div>
		    </div>
		    <div class="form-group">
			    <label class="col-sm-4 control-label no-padding-right" style="margin:6px 0px;" for="">盟/市：</label>
			    <div class="col-sm-3" style="margin:6px 0px;" >
                    <div class="clearfix">
						<select class="select2" id="newgroup" name="newgroup" data-placeholder="">
				        </select>
                    </div>
			    </div>
               
		    </div>

        </div>
    </form>
</div><!-- #dialog-message -->
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
	<link href="<%= ViewData["rootUri"] %>Content/css/ui.jqgrid.css" rel="Stylesheet"/> 
    <link rel="stylesheet" type="text/css" href="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.min.css" /> 
    <link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/jquery-ui.min.css" /> 
    <link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/js/uploadify/uploadify.css" />
    <link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/select2.css" />
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
	<script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/i18n/grid.locale-cn.js" type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery.ui.touch-punch.min.js"></script> 
    <script src="<%= ViewData["rootUri"] %>Content/js/uploadify/jquery.uploadify.min.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/uploadify/jquery.uploadify.js"></script> 
    <script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>

    <script type="text/javascript">
        jQuery(function ($) {
            $(".select2").css('width', '100px').select2({ minimumResultsForSearch: -1 });

        });
         function dutyset2(id) {
            document.getElementById("blname").options.length = 0;
            
                $.ajax({
                    url: "<%= ViewData["rootUri"] %>WorkingManage/Findblname",
                    data: { "blkind": id },
                    method: 'post',
                    success: function (data) {
                        $("#blname").append("<option value=" + "0" + ">" + "全部" + "</option>");
                        for (var s in data) {
                            if (s == 0) {
                                $("#blname").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                            } else {
                                $("#blname").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                            }
                        }
                        $("#blname").css('width', '100px').select2({allowClear:true, minimumResultsForSearch: -1 }); 
                    }
                });
            } 
        
        $(document).ready(function () {
        $("#find").click(function () {
            var PostData = { blkind:$("#blkind").val(),
                    blname:$("#blname").val()};
            jQuery("#myDataTable").setGridParam({page:1, url:'<%= ViewData["rootUri"] %>WorkingManage/FintPlanDetail', postData: PostData }).trigger('reloadGrid');
        });
    });
        $(function () {
            $("#myDataTable").jqGrid({
               // multiselect: true, //复选
                url: '<%= ViewData["rootUri"] %>WorkingManage/FintPlanDetail',
                datatype: "json",
                height: 300,
                autowidth: true, 
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
                postData:{
                    blkind:$("#blkind").val(),
                    blname:$("#blname").val(),
                    planid:"<%= ViewData["planid"] %>" 
                },
                viewsortcols: [false, 'vertical', false],
                colNames: [ 'id', '省/自治区', '盟/市', '类别', '名称', "开始日期", '终止日期', '时间','调查与报表时间', '操作'],
                colModel: [
                            { name: 'id', index: 'id', width: 1, align: "center" },
                            { name: 'sheng', index: 'sheng', width: 100, align: "center" },
                            { name: 'shi', index: 'shi', width: 100, align: "center" },
                            { name: 'blkind', index: 'blkind', width: 100, align: "center",
                                formatter: function (cellvalue, options, rowObject) {
                                if(cellvalue==0)
                                {
                                    return "病害";
                                }
                                if(cellvalue==1)
                                {
                                    return "虫害";
                                }
                               }
                             },
                            { name: 'blname', index: 'blname', width: 100, align: "center"},
                            { name: 'starttime', index: 'starttime', width: 100, align: "center"} ,
                            { name: 'endtime', index: 'endtime', width: 100, align: "center"},
                            { name: 'stime', index: 'stime', width: 100, align: "center"},
                            { name: 'period', index: 'period', width: 100, align: "center",
                                formatter: function (cellvalue, options, rowObject) {
                                return cellvalue+"天";
                               }
                            
                            },
                            { name: 'operate', index: 'operate', width: 100, align: "center",
                                formatter: function (cellvalue, options, rowObject) {
                                    return "<a  href='<%= ViewData["rootUri"] %>WorkingManage/EditWorking?uid="+rowObject.id+"&planid=<%=ViewData["planid"] %>' style='font-size:12px'  title='编辑';>编辑</a>&nbsp;"
                                     + "<a  href='#' style='font-size:12px'onclick='return deleteshop(" + rowObject.id + ")'  title='删除';>删除</a>";
                                }
                            }
                ],
                gridComplete: function () {
                    var ids = $("#myDataTable").jqGrid('getDataIDs');
                    if (ids.length == 0) {
                        $("#p").slideDown();
                    }
                    else { $("#p").hide(); }
                }
            });
            $("#myDataTable").jqGrid('hideCol', "id");
            $("#myDataTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });
        });
        function deleteshop(s){
    var con=confirm("是否确定要删除相关测报计划");
           if(con==true){
                $.ajax({
                        type: "post",
                        data: { uids: s    
                        },
                        url: "<%= ViewData["rootUri"] %>WorkingManage/DeleteTaskDetail",
                        dataType: "json",
                        success: function (success) {
                            if (success == true) {
                               toas1();
                                setTimeout(function(){
                                jQuery("#myDataTable").trigger("reloadGrid");
                            }, 2500);
                            }
                            else {
                                toas2();
                            }
                        }

                    });
        }

    }
    function toas1() {
           toastr.options = {
				            "closeButton": false,
				            "debug": true,
				            "positionClass": "toast-bottom-right",
				            "onclick": null,
				            "showDuration": "3",
				            "hideDuration": "3",
				            "timeOut": "2000",
				            "extendedTimeOut": "2000",
				            "showEasing": "swing",
				            "hideEasing": "linear",
				            "showMethod": "fadeIn",
				            "hideMethod": "fadeOut"
				        };
				        toastr["success"]("操作成功!", "恭喜您");
        }
     function toas2() {
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

				        toastr["error"]("操作失败!", "温馨敬告");
        }
    </script>
</asp:Content>
