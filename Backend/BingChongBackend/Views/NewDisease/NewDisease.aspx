<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header">
	<div>
        <div class="col-xs-2 no-padding-left">
            <h5>病虫预警</h5>
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
                            <div class="form-group" >
                                <label class="col-sm-1 control-label no-padding-right" style=" width:100px" for="">年度：</label>
				                <div class="col-sm-1" style=" width:155px">
                                    <div class="clearfix">
						                <select class="select2" id="Select7" name="" data-placeholder="请选择" style="width:150px">
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
                                <label class="col-sm-1 control-label no-padding-right" style=" width:100px" for="">省/自治区：</label>
				                <div class="col-sm-1" style=" width:155px">
                                    <div class="clearfix">
						                
                                        <% if (ViewData["ul"] == "4" || ViewData["ul"] == "0")
                                           { %>
                                        <select class="select2" id="Select5" name="Select1" data-placeholder="请选择" onchange="dutyset(this.value)">
                                            <option value="0" selected>全部</option>
                                            <%
                                  for (int i = 0; i < ViewBag.shenglist.Count; i++)
                                  {   
                                            %>
                                            <option value="<%=ViewBag.shenglist[i].uid %>">
                                                <%=ViewBag.shenglist[i].name %></option>
                                            <% }%>
                                            <% }
                                           else
                                           { %>
                                            <select class="select2" id="Select5" name="Select1" data-placeholder="请选择" onchange="dutyset(this.value)"
                                                disabled="disabled">
                                                <%
                                  for (int i = 0; i < ViewBag.shenglist.Count; i++)
                                  {   
                                                %>
                                                <option value="<%=ViewBag.shenglist[i].uid %>">
                                                    <%=ViewBag.shenglist[i].name %></option>
                                                <% }%>
                                                <%} %>
                                            </select>
                                    </div>
				                </div>  
                                <label class="col-sm-1 control-label no-padding-right" style=" width:100px" for="">盟/市：</label>
				                <div class="col-sm-1" style=" width:155px">
                                    <div class="clearfix">
						              
                                           <% if (ViewData["ul"] == "4" || ViewData["ul"] == "3" || ViewData["ul"] == "0")
                                           { %>
                                        <select class="select2" id="Select1" name="" data-placeholder="请选择" onchange="dutyset1(this.value)"
                                            disabled="disabled">
                                            <option value="0" selected>全部</option>
                                            <%
                                                
                                  for (int i = 0; i < ViewBag.shilist.Count; i++)
                                  {   
                                            %>
                                            <option value="<%=ViewBag.shilist[i].uid %>">
                                                <%=ViewBag.shilist[i].name %></option>
                                            <% }%>
                                            <% }
                                           else
                                           { %>
                                            <select class="select2" id="Select1" name="" data-placeholder="请选择" onchange="dutyset1(this.value)"
                                                disabled="disabled">
                                                <%
                                             
                                  for (int i = 0; i < ViewBag.shilist.Count; i++)
                                  {   
                                                %>
                                                <option value="<%=ViewBag.shilist[i].uid %>">
                                                    <%=ViewBag.shilist[i].name %></option>
                                                <% }%>
                                                <%} %>
                                            </select>
                                    </div>
				                </div> 
                                <label class="col-sm-1 control-label no-padding-right" style=" width:100px" for="">区/县：</label>
				                <div class="col-sm-1" style=" width:155px">
                                    <div class="clearfix">
						               
                                        <% if (ViewData["ul"] == "2" || ViewData["ul"] == "3" || ViewData["ul"] == "4" || ViewData["ul"] == "0")
                                           { %>
                                        <select class="select2"  style="width:180px" id="Select0" name="Select0" data-placeholder="请选择" 
                                            disabled="disabled">
                                            <option value="0" selected>全部</option>
                                            <%
                                  for (int i = 0; i < ViewBag.xianlist.Count; i++)
                                  {   
                                            %>
                                            <option value="<%=ViewBag.xianlist[i].uid %>">
                                                <%=ViewBag.xianlist[i].name %></option>
                                            <% }%>
                                            <% }
                                           else
                                           { %>
                                            <select class="select2" id="Select0"  style="width:180px" name="Select0" data-placeholder="请选择" 
                                                disabled="disabled">
                                                <%
                                  for (int i = 0; i < ViewBag.xianlist.Count; i++)
                                  {   
                                                %>
                                                <option value="<%=ViewBag.xianlist[i].uid %>">
                                                    <%=ViewBag.xianlist[i].name %></option>
                                                <% }%>
                                                <%} %>
                                            </select>
                                    </div>
				                </div>  
                            </div>
	                        <div class="form-group" >
                                <label class="col-sm-1 control-label no-padding-right" style=" width:100px" for="">病虫害类别：</label>
				                <div class="col-sm-1" style=" width:155px">
                                    <div class="clearfix">
						                <select class="select2" id="Select3" name="" data-placeholder="请选择" style="width:150px" >
                                            <option value="2" selected="selected">全部</option>
                                            <option value="0" >病害</option>   
                                            <option value="1" >虫害</option>                           
				                        </select>
                                    </div>
				                </div>  
                                <label class="col-sm-1 control-label no-padding-right" style=" width:100px" for="">审核状态：</label>
				                <div class="col-sm-2" style=" width:155px">
                                    <div class="clearfix">
						                <select class="select2" id="Select6" name="" data-placeholder="请选择"  style="width:150px">
                                            <option value="3" selected="selected">全部</option>     
                                            <option value="0" >待审核</option>    
                                            <option value="1">未通过</option>    
                                            <option value="2">已通过</option>                       
				                        </select>
                                    </div>
				                </div>  
                                <div class="col-sm-1" style="120px; margin-left:20px;">
						            <a class="btn btn-sm btn-info  btn-default btn-bold" id="searchdata" onclick="SearchData()"><i class="fa fa-search"></i> 搜索</a>
                                </div>
                            </div>         
                             
   
                        </form>
                    </div>
				</div>
			</div>
		</div>
      <div style="margin-top:10px;" >
			<table id="myDataTable" class=" table-hover">
			</table>
             <div id="pager10" style="height:50px"></div>
        </div>
    </div>
</div>
<!--a href="#" onclick="ShowTable()" style="float:right">查看</a-->
<!-- #dialog-message -->
<div id="dialog-message" class="hide">
    <form class="form-horizontal" role="form" id="form_crew">
        <div class="col-xs-12">
        </div>    
    </form>
</div><!-- #dialog-message -->

   <script type="text/javascript">
       $(function () {
           $(".select2").css('width', '150px').select2({ minimumResultsForSearch: -1 });

       });
   </script>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
	   <link  href="<%= ViewData["rootUri"] %>Content/css/select2.css"rel="stylesheet" />
       <link rel="stylesheet" type="text/css" href="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.min.css" /> 
       <link href="<%= ViewData["rootUri"] %>Content/css/ui.jqgrid.css" rel="Stylesheet"/>
    <link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/jquery-ui.min.css" /> 
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
	<script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/jquery-1.11.0.min.js" type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/i18n/grid.locale-cn.js"type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script type="text/javascript"  src="<%= ViewData["rootUri"] %>Content/js/jquery-ui.min.js"></script>
    <script type="text/javascript"  src="<%= ViewData["rootUri"] %>Content/js/bootbox.min.js"></script>
    <script type="text/javascript"  src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>
       	<script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js" type="text/javascript"></script>
    <script type="text/javascript">
       
      /*  var tabledlg;
        function ShowTable() {
            tabledlg = $("#dialog-message").removeClass('hide').dialog({
                modal: true,
                width: 800
            });
        }*/

             $(function () {
          var l = <%= ViewData["ul"] %>;
          if (l=="1") {
                  }
                    if (l=="2") {
                      document.getElementById("Select0").disabled=false;
                  }
                     if (l=="3") {
                       document.getElementById("Select1").disabled=false;  
                  }
                      if (l=="4") {
                       document.getElementById("Select5").disabled=false;
                  }
                        if (l=="0") {
                       document.getElementById("Select5").disabled=false;
                  }
        });

                    function dutyset(id) {
            document.getElementById("Select1").options.length = 0;
                $.ajax({
                    url: "<%= ViewData["rootUri"] %>Station/Findshi",
                    data: { "shengid": id },
                    method: 'post',
                    success: function (data) {
                       
                         if (id>0) {
                              document.getElementById("Select1").disabled=false; 
                           $("#Select1").append("<option value=" + "0" + " selected='selected'>" + "全部" + "</option>");
                        for (var s in data) {
                            if (s == 0) {
                                $("#Select1").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                            } else {
                                $("#Select1").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                            }
                        }
                         $("#Select1").select2({ allowClear: true,minimumResultsForSearch: -1 });
                    }
                       
                        else{
                         document.getElementById("Select1").disabled=true;
                           $("#Select1").append("<option value=" + "0" + " selected='selected'>" + "全部" + "</option>");
                        for (var s in data) {
                            if (s == 0) {
                                $("#Select1").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                            } else {
                                $("#Select1").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                            }
                        }
                         $("#Select1").select2({ allowClear: true,minimumResultsForSearch: -1 });
                        }
                     
                        dutyset1($("#Select1").val());
                    }
                });
        }
        function dutyset1(id) {
            document.getElementById("Select0").options.length = 0;
                $.ajax({
                    url: "<%= ViewData["rootUri"] %>Analysis/Findxian",
                    data: { "shengid":$("#Select5").val(),"shiid": id },
                    method: 'post',
                    success: function (data) {
                        
                        if (id>0) {
                              document.getElementById("Select0").disabled=false; 
                               $("#Select0").append("<option value=" + "0" + " selected='selected'>" + "请选择" + "</option>");
                        for (var s in data) {
                            if (s == 0) {
                                $("#Select0").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                            } else {
                                $("#Select0").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                            }
                        }
                        $("#Select0").select2({ allowClear: true,minimumResultsForSearch: -1 });
                    }
                       
                        else{
                        document.getElementById("Select0").disabled=true;
                         $("#Select0").append("<option value=" + "0" + " selected='selected'>" + "全部" + "</option>");
                        for (var s in data) {
                            if (s == 0) {
                                $("#Select0").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                            } else {
                                $("#Select0").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                            }
                        }
                        $("#Select0").select2({ allowClear: true,minimumResultsForSearch: -1 });
                        }
                    }
                });
        }

            function SearchData(){
             var PostData = {
                    type: $("#Select3").val(),
                    year: $("#Select7").val(),
                    status: $("#Select6").val(),
                    shengid:$("#Select5").val(),
                    shiid:$("#Select1").val(),
                    xianid:$("#Select0").val(),
             };
            var tabl = $("#myDataTable");
            tabl.setGridParam({  url:'<%= ViewData["rootUri"] %>NewDisease/FindNewDiseasePest', postData: PostData }).trigger('reloadGrid');

            }

        $(function () {
            $("#myDataTable").jqGrid({
                viewsortcols: [false, 'vertical', false],
                url: ' <%= ViewData["rootUri"] %>NewDisease/FindNewDiseasePest',
                datatype: "json",
                //  rowNum: 20,
                height: 540,
                //width:1030,
                autowidth:true,
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
                scrollOffset:1,
                rownumbers: true,
                postData: {
                    type: $("#Select3").val(),
                    year: $("#Select7").val(),
                    status: $("#Select6").val(),
                    shengid:$("#Select5").val(),
                    shiid:$("#Select1").val(),
                    xianid:$("#Select0").val(),
                },
                
                colNames: ['序号','发布时间','审核时间','省/自治区', '市', '区/县', '类别', '名称', '发布者', '审核者',  '状态', '操作'],
                colModel: [
                            { name: 'uid', index: 'uid', width: 1, align: "center" },
                            { name: 'report_date', index: 'report_date', width: 300, align: "center",formatter: 'date', formatter: 'date', formatoptions: { srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'} },
                            { name: 'review_date', index: 'review_date', width: 300, align: "center",formatter: 'date', formatter: 'date', formatoptions: { srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'} },
                            { name: 'sheng_name', index: 'sheng_name', align: "center" },
                            { name: 'shi_name', index: 'shi_name', align: "center" },
                            { name: 'xian_name', index: 'xian_name', align: "center" },
                            
                            { name: 'kind', index: 'kind', align: "center",
                               formatter: function (cellvalue, options, rowObject) {
                                if (cellvalue==0) {
                                return "病害";
                                }
                                if (cellvalue==1) {
                                return "虫害";
                                }
                               }
                            },
                            { name: 'name', index: 'name', width: 150, align: "center" },
                            { name: 'watcher_name', index: 'watcher_name', width: 150, align: "center" },
                            { name: 'admin_name', index: 'admin_name', width: 150, align: "center" },
                            { name: 'status', index: 'status', align: "center",
                             formatter: function (cellvalue, options, rowObject) {
                                if (cellvalue==0) {
                                return "待审核";
                                }
                                if (cellvalue==1) {
                                return "未通过";
                                }
                                 if (cellvalue==2) {
                                return "已通过";
                                }
                               }
                             },
                             { name: 'operation', index: 'operation', align: "center",
                                 formatter: function (cellvalue, options, rowObject) {    
                                     return '<%if (ViewData["role"].ToString().Contains("ViewBingchongWarning")) {%><a class=""role="button" href="<%= ViewData["rootUri"] %>NewDisease/ViewNewDiseasePest?uid='+rowObject.uid+'" data-toggle="modal" style="display:inline" >查看</a><%} %>';
                                 }
                             }
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
    </script>
</asp:Content>
