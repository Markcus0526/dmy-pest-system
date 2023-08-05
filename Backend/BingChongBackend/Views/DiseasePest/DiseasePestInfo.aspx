<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header">
	<div class="">
        <div class="col-xs-2 no-padding-left">
            <h5>病虫害信息</h5>
        </div>
		<div class="pull-right">
            <p>
            <%if (ViewData["role"].ToString().Contains("AddBingchong"))
              {%>
                   <a class="btn btn-sm btn-white  btn-default btn-bold" href="<%= ViewData["rootUri"] %>DiseasePest/AddDiseasePest">
	                <i class="ace-icon fa fa-plus blue"></i>新增
                </a>
            <% } %>
                <%if (ViewData["role"].ToString().Contains("BatchDeleteBingchong"))
              {%>
                   <a class="btn btn-sm btn-white  btn-default btn-bold" href="javascript:BatchDeleteBingchong();">
	                <i class="ace-icon fa fa-plus blue"></i>批量删除
                </a>
            <% } %> 
                <!--a class="btn btn-white btn-warning btn-bold" style="display:none;" id="A1" onclick="processDel();">
                    <i class="ace-icon fa fa-trash-o bigger-120 orange"></i>
                </a--> 
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
                                <label class="col-sm-1 control-label no-padding-center" for="" style=" width:120px">病虫害类别：</label>
				                <div class="col-sm-2">
                                    <div class="clearfix">
						                <select class="select2" id="Select4" name="" data-placeholder="请选择" <!--onchange="changeselect()"--> >
                                            <option value="2" selected="selected">全部</option>
                                            <option value="1">虫害</option>
                                            <option value="0">病害</option>                      
				                        </select>
                                    </div>
				                </div>  
                                <label class="col-sm-1 control-label no-padding-center" for=""  style=" width:120px">病虫害名称：</label>
				                <div class="col-sm-2">
                                    <div class="clearfix">
						                  <input id="name" style="height:28px;margin-top:1px"/> 
                                    </div>
				                </div>  
                                <div class="col-sm-1" style="">
                                    <a class="btn btn-sm btn-info" id="searchdata" onclick="SearchData()"><i class="fa fa-search"></i> 搜索</a>
                                </div>   
                            </div>
	
                        </form>
                    </div>
				</div>
			</div>
		</div>
        <div style="margin-top:10px;margin-right:18px">
            <div style="width:100%;">
			    <table id="myDataTable" class="">
			    </table>
                <div id="pager10" style="height:50px"></div>
            </div>
        </div>
    </div>
</div>
<!-- #dialog-message -->
<div id="dialog-message" class="hide">
    <form class="form-horizontal" role="form" id="form_crew">
        <div class="col-xs-12">
        </div>    
    </form>
</div><!-- #dialog-message -->
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/jquery-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.min.css" /> 
    <link   href="<%= ViewData["rootUri"] %>Content/css/jquery-ui.custom.min.css"rel="Stylesheet"/>
	<link  href="<%= ViewData["rootUri"] %>Content/css/select2.css"rel="stylesheet" />
    <link href="<%= ViewData["rootUri"] %>Content/css/ui.jqgrid.css" rel="Stylesheet"/>
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
	<script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js" type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/i18n/grid.locale-cn.js"type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script type="text/javascript"  src="<%= ViewData["rootUri"] %>Content/js/jquery-ui.min.js"></script>
    <script type="text/javascript"  src="<%= ViewData["rootUri"] %>Content/js/bootbox.min.js"></script>
    <script type="text/javascript"  src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>
   

    <script type="text/javascript">
        $(function () {
             // changeselect();
            });

            function SearchData(){
             var PostData = { type:$("#Select4").val() , name: $("#name").val() };
            var tabl = $("#myDataTable");
            tabl.setGridParam({  url: ' <%= ViewData["rootUri"] %>DiseasePest/FindDiseasePestInfo', postData: PostData }).trigger('reloadGrid');

            }
     /*  function changeselect(){
       document.getElementById("Select5").options.length = 0;
         $("#Select5").append("<option value='0' selected='selected' >全部</option>");
         $.ajax({
            url: "<%= ViewData["rootUri"] %>DiseasePest/FindDiseasePestName",
            dataType: 'json',
            data: {"type":$("#Select4").val()},
            success: function (ret) {
                    
                    for (var s in ret) {
                        if (s == 0) {
                            $("#Select5").append("<option value=" + ret[s].uid + ">" + ret[s].name + "</option>");
                        } else {
                            $("#Select5").append("<option value=" + ret[s].uid + ">" + ret[s].name + "</option>");
                        }
                    }
                }
        });
       }*/
        $(function () {
         //   alert(<%= ViewData["rootUri"] %>);
            $(".select2").css('width', '150px').select2({ minimumResultsForSearch: -1 });
            $("#myDataTable").jqGrid({
                multiselect: true, //复选
                viewsortcols: [false, 'vertical', false],
                url: ' <%= ViewData["rootUri"] %>DiseasePest/FindDiseasePestInfo',
                datatype: "json",
                //  rowNum: 20,
                height: 481,
                autowidth: true,              

                //scrollOffset: 0,
                // shrinkToFit: true,

                //  scroll:false,
                mtype: 'post',
                rowNum: 15,
                rowList: [15, 20, 30],
                pager: '#pager10',
                sortname: 'id',
                viewrecords: true,
                //sortorder: "desc",
                sortable: false,
                pagerpos: "center",
                pgbuttons: true,
                scrollOffset: 1,
                rownumbers: true,
                postData: {
                    type: $("#Select4").val(),
                    name: $("#name").val()
                },
                colNames: ['序号', '类别', '病虫害名称', '病虫害代码', '操作'],
                colModel: [
                            { name: 'uid', index: 'operat_people', align: "center",title: false },
                            { name: 'kind', index: 'platform', align: "center",title: false,
                               formatter: function (cellvalue, options, rowObject) {
                                if (cellvalue==0) {
                                return "病害";
                                }
                                if (cellvalue==1) {
                                return "虫害";
                                }
                               },
                            },
                            { name: 'name', index: 'operat_time', align: "center",title: false },
                            { name: 'serial', index: 'remark', align: "center",title: false },
                             { name: 'operation', index: 'operation', align: "center",title: false,
                                 formatter: function (cellvalue, options, rowObject) {
                                     return '<%if (ViewData["role"].ToString().Contains("ViewBingchong")) {%><a class=""role="button" href=" <%= ViewData["rootUri"] %>DiseasePest/ViewDiseasePest?id='+rowObject.uid+'" data-toggle="modal" style="display:inline" >查看</a> <% } %><%if (ViewData["role"].ToString().Contains("UpdateBingchong")) {%>&nbsp;&nbsp;<a class=" " role="button"  href=" <%= ViewData["rootUri"] %>DiseasePest/ModifyDiseasePest?id='+rowObject.uid+'"  data-toggle="modal" style="display:inline" >修改</a><% } %>  <%if (ViewData["role"].ToString().Contains("DeleteBingchong")) {%> &nbsp;&nbsp;<a class=" "role="button" href="#" onclick="deleteddis('+rowObject.uid+')"  data-toggle="modal" style="display:inline" >删除</a><%} %>';
                                 }
                             },
                     ]
            });
            $("#myDataTable").jqGrid('hideCol', "uid");

             $("#myDataTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });

        });
 function deleteddis(uid){
    var con=confirm("是否确定要删除此病虫害信息");
           if(con==true){
                $.ajax({
                        type: "post",
                        data: { uid: uid  
                        },
                        url: "<%= ViewData["rootUri"] %>DiseasePest/DeleteDiseasePestInfo",
                        dataType: "json",
                        success: function (success) {
                            if (success == true) {
                            toas1();
                                setTimeout(function(){
                                jQuery("#myDataTable").trigger("reloadGrid");
                            }, 4000);
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
				            "positionClass": "toast-top-right",
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
				            "positionClass": "toast-top-right",
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

        function BatchDeleteBingchong(){
          var con=confirm("是否确定要批量删除病虫信息");
              if(con==true){
                var ids = $("#myDataTable").jqGrid("getGridParam", "selarrrow");
                var uids = new Array();
                var ss = "";
                for (var i = 0; i < ids.length; i++) {
                    var id = ids[i];
                    //ss+= id + ",";
                    uids[i] = $("#myDataTable").jqGrid("getCell", id, "uid");
                    ss+= uids[i] + ",";
                }
                if (ss!=null&&ss!="") {
                    $.ajax({
                        type: "post",
                        data: { "uids": ss    
                        },
                        url: "<%= ViewData["rootUri"] %>DiseasePest/BatchDeleteBingchong",
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
                else {
                    alert('请选择要删除的病虫信息测报点');
                }
              }
        }

  
    </script>
</asp:Content>
