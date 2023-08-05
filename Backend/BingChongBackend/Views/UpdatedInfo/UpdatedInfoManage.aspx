
<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
    <!--<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> -->
    <div class="page-header">
        <div>
            <div class="col-xs-2 no-padding-left">
                <h5>
                    上报数据管理</h5>
            </div>
            <div class="pull-right">
                <p>
                    <% if (ViewData["roles"].ToString().Contains("BatchExportReportData"))
                       { %>
                    <a class="btn btn-sm btn-white  btn-default btn-bold" onclick="ExportData()"><i class="ace-icon fa fa-share blue">
                    </i>批量导出数据 </a>
                    <%}
                       else
                       { %>
                    <a class="btn btn-sm btn-white  btn-default btn-bold" onclick="ExportData()" style="display: none">
                        <i class="ace-icon fa fa-share blue"></i>批量导出数据 </a>
                    <%} %>
                    <% if (ViewData["roles"].ToString().Contains("ImportReportData") && ViewData["ul"] != "4")
                       { %>
                    <a class="btn btn-sm btn-white  btn-default btn-bold" onclick="ImportShow()" href="#">
                        <i class="ace-icon fa fa-plus blue"></i>导入数据 </a>
                    <%}
                       else
                       { %>
                    <a class="btn btn-sm btn-white  btn-default btn-bold" onclick="ImportShow()" href="#"
                        style="display: none"><i class="ace-icon fa fa-plus blue"></i>导入数据 </a>
                    <%} %>
                    <% if (ViewData["roles"].ToString().Contains("DeleteReportData") && ViewData["ul"] != "4")
                       { %>
                    <a class="btn btn-sm btn-white  btn-default btn-bold" href="#" onclick="Prodelete()"
                        id="del"><i class="ace-icon fa fa-trash-o blue"></i>批量删除 </a>
                    <%}
                       else
                       { %>
                    <a class="btn btn-sm btn-white  btn-default btn-bold" href="#" onclick="Prodelete()"
                        id="del" style="display: none"><i class="ace-icon fa fa-trash-o blue"></i>批量删除
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
                        <div class="searchbar" id="search">
                            <form class="form-horizontal" role="form" id="validation-form">
                            <!-- 第一行-->
                            <div class="form-group">
                                <label class="col-sm-1 control-label no-padding-right" for="">
                                    省/自治区:</label>
                                <div class="col-sm-1">
                                    <div class="clearfix" style="width:120px">
                                        <% if (ViewData["ul"] == "4" || ViewData["ul"] == "0")
                                           { %>
                                        <select class="select2" id="sheng" name="sheng" data-placeholder="请选择" onchange="shengselect(this.value)">
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
                                            <select class="select2" id="sheng" name="sheng" data-placeholder="请选择" onchange="shengselect(this.value)"
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
                                <label class="col-sm-1 control-label no-padding-right" for="">
                                    盟/市：</label>
                                <div class="col-sm-1">
                                    <div class="clearfix" style="width:120px">
                                        <% if (ViewData["ul"] == "4" || ViewData["ul"] == "3" || ViewData["ul"] == "0")
                                           { %>
                                        <select class="select2" id="shi" name="" data-placeholder="请选择" onchange="shiselect(this.value)"
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
                                            <select class="select2" id="shi" name="" data-placeholder="请选择" onchange="shiselect(this.value)"
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
                                <label class="col-sm-1 control-label no-padding-right" for="">
                                    区/县：</label>
                                <div class="col-sm-1">
                                    <div class="clearfix" style="width:120px">
                                        <% if (ViewData["ul"] == "2" || ViewData["ul"] == "3" || ViewData["ul"] == "4" || ViewData["ul"] == "0")
                                           { %>
                                        <select class="select2"  style="width:180px" id="xian" name="xian" data-placeholder="请选择" onchange="xianselect(this.value)"
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
                                            <select class="select2" id="xian"  style="width:180px" name="xian" data-placeholder="请选择" onchange="xianselect(this.value)"
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
                                <label class="col-sm-1 control-label no-padding-right" for="" style="margin-left:20px">
                                    测报点级别：</label>
                                <div class="col-sm-1">
                                    <div class="clearfix" style="width:120px">
                                        <select class="select2" id="testlevel" name="" data-placeholder="请选择" onchange="levelselect(this.value)">
                                            <option value="0" selected="selected">国家级</option>
                                            <% if (ViewData["ul"] == "1")
                                               { %>
                                            <option value="3">县级</option>
                                            <%} %>
                                            <% if (ViewData["ul"] == "2")
                                               { %>
                                            <option value="2">市级</option>
                                            <option value="3">县级</option>
                                            <%} %>
                                            <% if (ViewData["ul"] == "3")
                                               { %>
                                            <option value="1">省级</option>
                                            <option value="2">市级</option>
                                            <option value="3">县级</option>
                                            <%} %>
                                            <% if (ViewData["ul"] == "4")
                                               { %>
                                            <%} %>
                                            <% if (ViewData["ul"] == "0")
                                               { %>
                                            <option value="1">省级</option>
                                            <option value="2">市级</option>
                                            <option value="3">县级</option>
                                            <%} %>
                                        </select>
                                    </div>
                                </div>
                                <label class="col-sm-1 control-label no-padding-right" for="" style="margin-left:20px">
                                    测报点类型：</label>
                                <div class="col-sm-1">
                                    <div class="clearfix" style="width:120px">
                                        <select   class="select2"  id="testtype" name="" data-placeholder="请选择" onchange="typeselect(this.value)">
                                            <option value="0" selected="selected">固定测报点</option>
                                            <option value="1">非固定测报点</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <!-- 存储测报点<input id="l"/>-->
                            <!-- 第er行 <input id="t"/>-->
                            <!-- 第er行-->
                            <div class="form-group">
                                <label class="col-sm-1 control-label no-padding-right" for="" >
                                    测报点名称:</label>
                                <div class="col-sm-1">
                                    <div class="clearfix" style="width:120px">
                                        <select class="select2" id="testpoint" name="" data-placeholder="请选择" disabled="disabled">
                                            <option value="0" selected="selected">全部</option>
                                            <% if (ViewData["ul"] == "1" && ViewBag.pointlist != null)
                                               { %>
                                            <%
                                                              for (int i = 0; i < ViewBag.pointlist.Count; i++)
                                                              {   
                                            %>
                                            <option value="<%=ViewBag.pointlist[i].uid %>">
                                                <%=ViewBag.pointlist[i].name %></option>
                                            <% }%>
                                            <%} %>
                                        </select>
                                    </div>
                                </div>
                               <label class="col-sm-1 control-label no-padding-right" for="">病虫类型:</label>
                                <div class="col-sm-1">
                                    <div class="clearfix" style="width:120px">
						                <select class="select2" disabled="disabled" id="bkind" name="" data-placeholder="请选择" onchange="bkindselect(this.value)">
                                            <option value="2" selected="selected">全部</option>
                                            <option value="0" >病害</option>     
                                            <option value="1" >虫害</option>                       
				                        </select>
                                    </div>
                                </div>
              	                <label class="col-sm-1 control-label no-padding-right" for="" style="margin-left:20px">病虫名称：</label>
				                <div class="col-sm-1">
                                    <div class="clearfix" style="width:120px">
						                <select class="select2" id="bname" name="" disabled="disabled"   data-placeholder="请选择" onchange="bnameselect(this.value)">
                                            <option value="0" selected="selected">全部</option>
                                                           
				                        </select>
                                    </div>
				                </div>                  
                                <label class="col-sm-1 control-label no-padding-right" for="" >表格：</label>
                                <div class="col-sm-1">
                                    <div class="clearfix" style="width:120px">
						                <select class="select2 " id="tablename" name=""  disabled="disabled">
                           
                                           
                                            <option value="0" selected="selected">全部</option>
				                        </select>
                                    </div>
                                </div>
                                <label class="col-sm-1 control-label no-padding-right" for="" style="margin-left:20px">审核状态：</label>
                                <div class="col-sm-1">
                                    <div class="clearfix" style="width:120px">
                                        <select class="select2" id="checkstatus" name="" data-placeholder="请选择">
                                            <% if (ViewData["ul"] == "2")
                                               { %>
                                            <option value="0" selected="selected">全部</option>
                                            <option value="1">未审核</option>
                                            <option value="2">已审核</option>
                                            <option value="3">未通过</option>
                                            <option value="4">通过</option>
                                            <%} %>
                                            <% if (ViewData["ul"] == "1")
                                               { %>
                                            <option value="0" selected="selected">全部</option>
                                            <option value="1">待审核</option>
                                            <option value="3">未通过</option>
                                            <option value="4">通过</option>
                                            <%} %>
                                            <% if (ViewData["ul"] == "3")
                                               { %>
                                            <option value="0" selected="selected">全部</option>
                                            <option value="2">未审核</option>
                                            <option value="4">通过</option>
                                            <option value="5">未通过</option>
                                            <%} %>
                                            <% if (ViewData["ul"] == "4" || ViewData["ul"] == "0")
                                               { %>
                                            <option value="0" selected="selected">全部</option>
                                            <option value="1">待市级审核</option>
                                            <option value="2">待省级审核</option>
                                            <option value="3">市级未通过</option>
                                            <option value="4">省级审核通过</option>
                                            <option value="5">省级未通过</option>
                                            <%} %>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group" style="margin-bottom: 0px">
                             <label class="col-sm-1 control-label no-padding-right" >开始时间：</label>
                                <div class="col-sm-1">
                                    <div class="clearfix" >
                                        <div class="input-group">
                                            <input class="input-large date-picker" id="starttime" name="starttime" type="text"
                                                data-date-format="yyyy-mm-dd" value="<%= ViewData["cumonth"] %>" style="width: 160px;" />
                                            <span class="input-group-addon"><i class="fa fa-calendar bigger-110"> </i></span>
                                        </div>
                                    </div>
                                </div>
                                <label class="col-sm-2 control-label no-padding-right" >结束时间：</label>
                                <div class="col-sm-1">
                                    <div class="clearfix" >
                                        <div class="input-group">
                                            <input class="input-large date-picker" id="endtime" name="endtime" type="text" data-date-format="yyyy-mm-dd"
                                                value="<%= ViewData["today"] %>" style="width: 160px;" />
                                            <span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="col-sm-1" style="float: right;">
                                    <a id="searchdata" class="btn btn-sm btn-info" onclick="searchby()"><i class="fa fa-search">
                                    </i>搜索</a>
                                </div>
                            </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        <div style="margin-top:10px;" id="h">
			    <table id="myDataTable" class="">
				
			    </table>
                <div id="pager10" style="height:50px" id="p"></div>
        </div>
       
    </div>
    <input id="idinput" style="  display:none">
</div>

<!-- #dialog-message 修改-->
<div id="modify" class="hide">
    <form class="form-horizontal" role="form" id="form_crew" method="post" action="biaodan">
        <div class="col-xs-12">
            <div class="form-group" >
               <span id="mt"></span>
               <hr style=" border:1px solid red; "/>
            </div>
            <div class="form-group" >
                    <table border="1" style=" font-weight: bold; text-align:center"  cellspacing="0" cellpadding="0" width="100%" id="modifytable">
                	
                </table>
            </div>
            <div class="form-group" >
            <label>审核日志:</label></br>
               <textarea id="modifyloghtml"  cols="60" rows="2"  style="border:0px solid orange;font-weight:bolder;background-color:Gray; width:100% ;color:White"; readonly="readonly"  ></textarea>
        </div>
        <div>
       <a href="#" class="btn btn-primary btn-xs" onclick="modifydata()">确认修改</a>
        </div>
    </form>
</div>
<!-- #dialog-message 审核-->
<div id="dialog2" class="hide">
    <form class="form-horizontal" role="form" id="form1">
        <div class="col-xs-12">
            <div class="form-group" >
                  <span id="title"></span>
                  <hr style=" border:1px solid red; "/>
            </div>
            <div class="form-group" >
               <table border="1" style=" font-weight: bold; text-align:center"  cellspacing="0" cellpadding="0" width="100%" id="checktable">
                	
                </table>
            </div>
            <div class="form-group" id="r" >
              <label >审核日志:</label>
                <textarea id="log"  cols="60" rows="2"  style="border:0px solid orange;font-weight:bolder;background-color:Gray; width:100%;  color:White"; readonly="readonly" ></textarea></br>
                <label>审核理由:</label></br>
                <textarea rows="2" cols="60" id="passreason"  style=" width:100%;font-weight:bolder;"></textarea>
              
            </div>
        </div>    
    </form>
</div>
<!-- #dialog-message 查看-->
<div id="showtables" class="hide">
    <form class="form-horizontal" role="form" id="form2">
        <div class="col-xs-12">
            <div class="form-group" >
              
              <span id="ct"></span>
              <hr style=" border:1px solid red; "/>
            </div>
            <div class="form-group" id="tablehtmls" >
   
               <table border="1" style=" font-weight: bold; text-align:center" cellspacing="0" cellpadding="0" width="100%" id="tablehtml">
                	
                </table>
            </div>
            <div class="form-group" >
                     <label >审核日志:</label></br>
            <textarea id="loghtml"  cols="60" rows="2"  style="border:0px solid orange;font-weight:bolder;background-color:#999999; width:100%; color:White"; readonly="readonly"  ></textarea>
               
            </div>
        </div>    
    </form>
</div><!-- #dialog-message -->
<div id="dialogimport"  class="hide">
    <form class="form-horizontal" role="form" id="form3">
        <div class="col-xs-12" id="dialogdetail">
        <div style="margin-left:30px;margin-top:0px">
          <a class="btn btn-sm btn-info" href="javascript:$('#uploadify').uploadify('upload','*')">
	                <i class="ace-icon fa fa-arrow-up"></i>上&nbsp;&nbsp;传</a>  
        </div>
        <div style="margin-top:-33px;margin-left:180px">
        <span style=" color:red">**温馨提示:在上传之前请您最好将您的文件格式与范本进行对比**</span></br>
           <a class="btn btn-sm btn-white btn-default btn-bold" onclick="ShowTable()" href="<%= ViewData["rootUri"] %>Settings/TableManage">
	                <i class="ace-icon fa fa-arrow-down blue"></i>下载范本</a>  
        </div>
        <div style="margin-left:180px;margin-top:15px">
           <input  id="uploadify" type="file" class="upload" name="upload" />
        </div>       
       </div>    
    </form>
</div>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
    <link rel="Stylesheet" href="<%= ViewData["rootUri"] %>Content/css/jquery-ui.min.css" />
    <link rel="Stylesheet" href="<%= ViewData["rootUri"] %>Content/js/uploadify/uploadify.css" />
    <link rel="Stylesheet" href="<%= ViewData["rootUri"] %>Content/css/select2.css" />
    <link rel="Stylesheet" href="<%= ViewData["rootUri"] %>Content/css/datepicker.css" />
    <link href="<%= ViewData["rootUri"] %>Content/css/ui.jqgrid.css" rel="Stylesheet" />
    <link rel="Stylesheet" type="text/css" href="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.min.css" />
    <style type="text/css">
        #sheng
        {
            width: 140px;
        }
        #shi
        {
            width: 140px;
        }
        #xian
        {
            width: 140px;
        }
        #testtype
        {
            width: 140px;
        }
        #h
        {
            min-width:1000px
        }
        #p
        {
            min-width:1200px
        }
        
        
    </style>
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
    <%--<script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/jquery-1.11.0.min.js" type="text/javascript"></script>--%>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/i18n/grid.locale-cn.js"
        type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery.ui.touch-punch.min.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js" type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/date-time/bootstrap-datepicker.min.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/date-time/locales/bootstrap-datepicker.zh-CN.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/bootbox.min.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/uploadify/jquery.uploadify.min.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/uploadify/jquery.uploadify.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>
    <script type="text/javascript">
    
    jQuery(function ($) {
            $('.date-picker').datepicker({ autoclose: true, todayHighlight: true, language: "zh-CN" })
			.on('change', function () { });
        });
    var dialog = $("#dialogimport");
   function afterClosed(){　　　　
       // 在弹出对话框内找到Uploadify插件，并隐藏之。　　
       //dialog.find('#uploadify').hide();　　
       // 将弹出对话框还原成弹出前的状态，并清空里面的内容。　
       dialog.dialog('destroy');
       $("#dialogdetail").hide();
       
   }
   //function beforeClosed(){　　　　
       // 在弹出对话框内找到Uploadify插件，并隐藏之。　　
      // dialog.find('#uploadify').show();　
      　
   //}
    function ImportShow(){
         $("#dialogdetail").show();
         dialog.removeClass('hide').dialog({
                title: '',
                modal: true,
                width: 500,
                //open:beforeClosed,
                beforeClose: afterClosed
            });
        }
    function modifydata(){
    var res= "";
    if (confirm("确认修改？")) {
     var name=document.getElementsByName("look");
   //  alert(name);
      var  rep = $('#idinput').val();
            for(var i=0;i<name.length;i++)
                {
           
                var k =name[i].id;
                var n = $("#"+k).val();
                    $.ajax({
                    type: "GET",
                         async:false,
                    url: '<%= ViewData["rootUri"] %>'+ "UpdatedInfo/Modifys",
                    data: { "report": rep,"filedid":k,"val":n},
                    method: 'post',
                    dataType: "json",
                    success: function (data) {
                       res+=data;                 
                    },
                });

                }
                if (res.indexOf("nk")>-1) {
                          toastr["error"]("修改失败！");
                }else{
                      toastr["success"]("修改成功！");
                }
                searchby();
                    $('#modify').dialog("close");
    }
     



    }
    function ExportData(){

     
                        var endtime = $('#endtime').val();
                        var starttime = $('#starttime').val();
                        var shengid = $('#sheng').val();
                        var shiid = $('#shi').val();
                        var type = $('#testtype').val();
                        var kindid =  $('#bkind').val();
                        var pointid = $('#testpoint').val();
                        var xianid = $('#xian').val();
                        var level=$('#testlevel').val();
                        var formid =$('#tablename').val();
                        var nameid=$('#bname').val();
                         var sta=$('#checkstatus').val();
                        
                      location.href="<%= ViewData["rootUri"] %>UpdatedInfo/ExData?shengid="+shengid+"&shiid="+shiid+"&xianid="+xianid+"&type="+type+"&kindid="+kindid+"&pointid="+pointid+ "&level="+level+"&formid="+formid+"&nameid="+nameid+"&sat="+sta+"&starttime="+starttime+"&endtime="+endtime;



    }
    //最终的搜索
    function searchby(){
  
                        var endtime = $('#endtime').val();
                        var starttime = $('#starttime').val();
                        var shengid = $('#sheng').val();
                        var shiid = $('#shi').val();
                        var type = $('#testtype').val();
                        var kindid =  $('#bkind').val();
                        var pointid = $('#testpoint').val();
                        var xianid = $('#xian').val();
                        var level=$('#testlevel').val();
                        var formid =$('#tablename').val();
                        var nameid=$('#bname').val();
                         var sta=$('#checkstatus').val();
                        //alert("开始时间："+starttime+"结束时间："+endtime+"省id："+shengid+"市id："+shiid+"县id："+xianid+"测报点等级："+level+"测报点类型："+type+"病虫种类："+kindid+"表格"+formid);           
                        var PostData = { shengid:shengid ,shiid:shiid,xianid:xianid,level:level,type:type,pointid:pointid,kindid:kindid,nameid:nameid,formid:formid ,sta:sta,starttime:starttime,endtime:endtime};
                        var tabl = $("#myDataTable");
                        tabl.setGridParam({ page:1, url: ' <%= ViewData["rootUri"] %>UpdatedInfo/SearchBy', postData: PostData }).trigger('reloadGrid');

    
    
                       }
    //选择一个县
 function xianselect(xianid){ {
 }var type = $('#testtype').val();typeselect(type);
 
 
 
 
 {
 }}
    //病虫种类
    function bkindselect(kindid){
    document.getElementById("bname").options.length = 1;

    var nameid=$('#tablename').val();
      var u = "<%= ViewData["rootUri"] %>"+"UpdatedInfo/GetBnamelist";
             var shengid = $('#sheng').val();
                  var shiid = $('#shi').val();
                   var type = $('#testtype').val();
     var xianid = $('#xian').val();
     var level=$('#testlevel').val();
     if (kindid!=2) {
       document.getElementById("bname").disabled=false;
          // alert("type+"+type+"xian"+xianid+"level"+level+"shengid"+shengid+"shhiid"+shiid);
       $.ajax({
			url :u,
				data:{"shengid":shengid ,"shiid":shiid,"xianid":xianid,"level":level,"type":type,"kind":kindid},
				dataType : "json",
				method:'post',
				success: function (jsonObject) {
				    for (var s in jsonObject) {
                      
                     
				        $("#bname").append("<option value=" + jsonObject[s].uid + ">" + jsonObject[s].name + "</option>");
				    }
                     $("#bname").select2({ allowClear: true,minimumResultsForSearch: -1 });
                      //  $(".select2").css('width', '100px').select2({ minimumResultsForSearch: -1 });
                     //bnameselect(nameid)
                       $("#tablename").val(0);
                     $("#tablename").select2({ allowClear: true,minimumResultsForSearch: -1 });
                     document.getElementById("tablename").disabled=true;
                   
				    }
                   
							
			
			});
     }else if(kindid==2){
      $("#bname").select2({ allowClear: true,minimumResultsForSearch: -1 });
      document.getElementById("bname").disabled=true;
      $("#tablename").val(0);
      $("#tablename").select2({ allowClear: true,minimumResultsForSearch: -1 });
      document.getElementById("tablename").disabled=true;

}
     
       
             
 
    
    
    
    
    }
    //删除数据
    function deltedreport(id){
    if (confirm("确定删除？")) {
    //alert(id);
      var u = "<%= ViewData["rootUri"] %>"+"UpdatedInfo/DeleteReport";
           $.ajax({
			                        url :u,
				                    data:{"id":id,
                                            



                                    },
				                    dataType : "json",
				                    method:'post',
				                    success: function (jsonObject) {
                                    if (jsonObject=="ok") {
                                     toastr["success"]("数据删除成功！");
                                    }else{

                                     toastr["error"]("操作失败，请检查或联系管理员！");
                                    }
                                    
				                    searchby();
                      // $('#dialog2').dialog("close");
                     //  document.getElementById('r').innerHTML="  <textarea rows='3' cols='50' id='passreason' ></textarea>";

				                     }
                   
							
			
			                    });}else{

                                searchby();

                                }
                                
    

    }
    //查看
     var tabledlg;

        function showtable(tableid,repid) {
            
            if (tableid != null) {
                $.ajax({
                    
                    url: '<%= ViewData["rootUri"] %>'+ "UpdatedInfo/UpGetTableHtml",
                    data: { "tableid": tableid,"repid":repid,"type":0},
                    method: 'post',
                    dataType: "json",
                    success: function (data) {
                        if (data.length > 0) {
                            $("#tablehtml").html(data);

                        }
                        else{
                        }
                    },
                });
                   $.ajax({
                 
                    url: '<%= ViewData["rootUri"] %>'+ "UpdatedInfo/GetTitleNames",
                    data: { "reportid":repid},
                    method: 'post',
                    dataType: "json",
                    success: function (data) {
                        if (data.length > 0) {
                            $("#ct").html(data);

                        }
                        else{
                        }
                    },
                });
                  $.ajax({
                   
                    url: '<%= ViewData["rootUri"] %>'+ "UpdatedInfo/GetLog",
                    data: { "tableid": tableid,"repid":repid},
                    method: 'post',
                    dataType: "json",
                    success: function (data) {
                        if (data!="n") {
             
                            $("#loghtml").val(data);

                        }
                        else{
                     
                        $("#loghtml").val("");

                        }
                    },
                });
            }
  
           
            //alert(l.id);
            tabledlg = $("#showtables").removeClass('hide').dialog({
                modal: true,
                width: 500,

            });
                 // var name=document.getElementsByName("look");
// for(var i=0;i<name.length;i++)
 //{
  //alert(name[i].id);
 //}
        }





        //修改
     var tabledlg1;

        function mshowtable(tableid,repid) {
            $("#idinput").val(repid);
            if (tableid != null) {
                $.ajax({
              
                    url: '<%= ViewData["rootUri"] %>'+ "UpdatedInfo/UpGetTableHtml",
                    data: { "tableid": tableid,"repid":repid,"type":1},
                    method: 'post',
                    dataType: "json",
                    success: function (data) {
                        if (data.length > 0) {
                            $("#modifytable").html(data);

                        }
                        else{
                        }
                    },
                });
                   $.ajax({
               
                    url: '<%= ViewData["rootUri"] %>'+ "UpdatedInfo/GetTitleNames",
                    data: { "reportid":repid},
                    method: 'post',
                    dataType: "json",
                    success: function (data) {
                        if (data.length > 0) {
                            $("#mt").html(data);

                        }
                        else{
                        }
                    },
                });
                  $.ajax({
                   
                    url: '<%= ViewData["rootUri"] %>'+ "UpdatedInfo/GetLog",
                    data: { "tableid": tableid,"repid":repid},
                    method: 'post',
                    dataType: "json",
                    success: function (data) {
                        if (data!="n") {
                            $("#modifyloghtml").val(data);

                        }
                        else{
                         $("#modifyloghtml").val("");
                        }
                    },
                });
            }
  
           
            //alert(l.id);
            tabledlg1 = $("#modify").removeClass('hide').dialog({
                modal: true,
                width: 600,
              
    

            });
                
        }
         $(document).ready(function() {
             var myfile = new Array();
             var uid = <%= ViewData["uid"] %>;
               var fid=$("#tablename").val();
                            var bid=$("#bname").val();
                            var pid=$("#testpoint").val();
                            var myfile = new Array();
                            myfile[0] = fid;
                            myfile[1] = bid;
                            myfile[2]=pid;
                            myfile[3]=uid;
            $("#uploadify").uploadify({
               'formData':
                    {
                        'myfile': myfile    
                    },
                    'uploadLimit': 0,
                    'hieght':30,
                    'width':90,
                    'auto': false, //当文件被添加到队列时，
                    'buttonText': '<i class="ace-icon fa fa-book blue"></i>选择文件',
                    'multi': true, //设置为true将允许多文件上传
                    'cancelImg': '<%= ViewData["rootUri"] %>Content/js/uploadify/uploadify-cancel.png',
                    //'fileTypeDesc': '文本文件',
                    'fileTypeExts': '*.xls;*.xlsx',
                    'fileSizeLimit': '20MB',  //上传文件的大小限制，单位为字节 100k
                    'swf': '<%= ViewData["rootUri"] %>Content/js/uploadify/uploadify.swf',
                    'uploader':"<%= ViewData["rootUri"] %>UpdatedInfo/ImportData", 

                    'onUploadStart': function (file) {
                            var fid=$("#tablename").val();
                            var bid=$("#bname").val();
                            var pid=$("#testpoint").val();
                              var uid = <%= ViewData["uid"] %>;
                            var myfile = new Array();
                            myfile[0] = fid;
                            myfile[1] = bid;
                            myfile[2]=pid;
                             myfile[3]=uid;
                            if(fid!="0"&&pid!="0"&&bid!="0"){
                                  $("#uploadify").uploadify('settings', 'formData',{ 'myfile': myfile });
                            }
                            else{
                                toastr["error"]("为保证您的数据准确，测报点名称，病虫名称，表格名称必须全部选择！");
                                $('#uploadify').uploadify('cancel', '*');
                            }
                        },
                    'onUploadSuccess': function (file, data, response) {
                       if(data=="1"){
                          	        toastr["success"]("操作成功！");
                                setTimeout(function(){
                                window.location.reload();
                            }, 2500);
                            
                            
                        }
                           if(data=="4"){
                          	        toastr["error"]("您传入的文件样式与选择的表格不相符，请检查！");
                                setTimeout(function(){
                                window.location.reload();
                            }, 2500);
                            
                            
                        }
                        if(data=="2")
                        {
                         toastr.options = {
				            "closeButton": false,
				            "debug": true,
				            "positionClass": "toast-center-center",
				            "onclick": null,
				            "showDuration": "3",
				            "hideDuration": "3",
				            "timeOut": "4000",
				            "extendedTimeOut": "4000",
				            "showEasing": "swing",
				            "hideEasing": "linear",
				            "showMethod": "fadeIn",
				            "hideMethod": "fadeOut"
				        };

				        toastr["error"]("写入数据异常,可能是您输入了不合法的值或者有没填写的字段,请核对Excel文件的样式及数据！");
                                setTimeout(function(){
                                window.location.reload();
                            }, 2500);
                            
                        }
                         if(data=="5")
                        {
                         toastr.options = {
				            "closeButton": false,
				            "debug": true,
				            "positionClass": "toast-center-center",
				            "onclick": null,
				            "showDuration": "3",
				            "hideDuration": "3",
				            "timeOut": "4000",
				            "extendedTimeOut": "4000",
				            "showEasing": "swing",
				            "hideEasing": "linear",
				            "showMethod": "fadeIn",
				            "hideMethod": "fadeOut"
				        };

				        toastr["error"]("您的表格样式可能不是最新版本，请与范本格式进行严格对比或者联系管理员！");
                                setTimeout(function(){
                                window.location.reload();
                            }, 2500);
                            
                        }
                        if(data=="3")
                        {
                         toastr.options = {
				            "closeButton": false,
				            "debug": true,
				            "positionClass": "toast-bottom-right",
				            "onclick": null,
				            "showDuration": "3",
				            "hideDuration": "3",
				            "timeOut": "4000",
				            "extendedTimeOut": "4000",
				            "showEasing": "swing",
				            "hideEasing": "linear",
				            "showMethod": "fadeIn",
				            "hideMethod": "fadeOut"
				        };

				        toastr["error"]("上传出现错误,请选择范本格式添加!", "温馨敬告");
                                setTimeout(function(){
                                window.location.reload();
                            }, 2500);
                            
                        }
                    }, 

              });
    });

    //批量删除
    function Prodelete(){

  
          var u = "<%= ViewData["rootUri"] %>"+"UpdatedInfo/BatchDelete";
    var rowIds = $("#myDataTable").jqGrid('getGridParam', 'selarrrow');
    //alert(rowIds);
            var tids = "";
            var id = [];
            $.each(rowIds, function (index, rowIds) {
                id.push(rowIds);
                id.join(",");
            });
            tids += id + ",";
            if (tids != ",") {
                if (confirm("确定删除？")) {

                    $.ajax({
                        url: u,
                        data: {
                            "ids": tids
                        },
                        type: "post",
                        success: function (message) {

                       
                            if (message=="ok") {
                              toastr["success"]("操作成功！");
                            }else{
                                 toastr["success"]("操作失败，请检查！");
                            }
                           searchby();
                        }



                    });


                }else{

                searchby();
                }
            } else {
        toastr["error"]("您可能没有选择要操作的数据请检查！");
             
           }


    }
    //病虫名称
    function bnameselect(nameid){
  
     document.getElementById("tablename").options.length = 1;
      var u = "<%= ViewData["rootUri"] %>"+"UpdatedInfo/GetFormNamelist";
             var shengid = $('#sheng').val();
                  var shiid = $('#shi').val();
                   var type = $('#testtype').val();
                   var kindid =  $('#bkind').val();
     var xianid = $('#xian').val();
     var level=$('#testlevel').val();
     if (nameid>0) {
      document.getElementById("tablename").disabled=false;
             
    // alert("type+"+type+"xian"+xianid+"level"+level+"shengid"+shengid+"shhiid"+shiid);
       $.ajax({
			url :u,
				data:{"shengid":shengid ,"shiid":shiid,"xianid":xianid,"level":level,"type":type,"kind":kindid,"bid":nameid},
				dataType : "json",
				method:'post',
				success: function (jsonObject) {
				    for (var s in jsonObject) {
                      
                     
				        $("#tablename").append("<option value=" + jsonObject[s].uid + ">" + jsonObject[s].name + "</option>");
                        
				    }
                      $("#tablename").select2({ allowClear: true,minimumResultsForSearch: -1 });
				    }
                   
							
			
			});
    
     }else{
      $("#tablename").val(0)
     $("#tablename").select2({ allowClear: true,minimumResultsForSearch: -1 });
     $("#bkind").select2({ allowClear: true,minimumResultsForSearch: -1 });
     document.getElementById("tablename").disabled=true;

     }
     
    
    
    }
    //$(function(){　　 

//$(window).resize(function(){　　

//$("#myDataTable").setGridWidth($(window).width()*0.85);})

//});
   $(function () {
  var l = <%= ViewData["ul"] %>;
  if (l=="1") {
  document.getElementById("testpoint").disabled=false;
               document.getElementById("testlevel").disabled=false;
               document.getElementById("testtype").disabled=false;
           document.getElementById("bkind").disabled=false;
  }
    if (l=="2") {
      document.getElementById("xian").disabled=false;
 // document.getElementById("testpoint").disabled=false;
             //   document.getElementById("testlevel").disabled=false;
             //   document.getElementById("testtype").disabled=false;
               document.getElementById("bkind").disabled=false;
  }
     if (l=="3") {
       document.getElementById("shi").disabled=false;  
       // document.getElementById("testpoint").disabled=false;
//                 document.getElementById("testlevel").disabled=false;
//               document.getElementById("testtype").disabled=false;
               document.getElementById("bkind").disabled=false;
  }
      if (l=="4") {
       document.getElementById("sheng").disabled=false;

   //document.getElementById("testpoint").disabled=false;
               // document.getElementById("testlevel").disabled=false;
               //document.getElementById("testtype").disabled=false;
               document.getElementById("bkind").disabled=false;
  }
        if (l=="0") {
       document.getElementById("sheng").disabled=false;

   //document.getElementById("testpoint").disabled=false;
//                document.getElementById("testlevel").disabled=false;
//                              document.getElementById("testtype").disabled=false;
               document.getElementById("bkind").disabled=false;
  }
  
                        var endtime = $('#endtime').val();
                        var starttime = $('#starttime').val();
                        var shengid = $('#sheng').val();
                        var shiid = $('#shi').val();
                        var type = $('#testtype').val();
                        var kindid =  $('#bkind').val();
                        var pointid = $('#testpoint').val();
                        var xianid = $('#xian').val();
                        var level=$('#testlevel').val();
                        var formid =$('#tablename').val();
                        var nameid=$('#bname').val();
                         var sta=$('#checkstatus').val();
                        //alert("开始时间："+starttime+"结束时间："+endtime+"省id："+shengid+"市id："+shiid+"县id："+xianid+"测报点等级："+level+"测报点类型："+type+"病虫种类："+kindid+"表格"+formid);           
                        var PostData = { shengid:shengid ,shiid:shiid,xianid:xianid,level:level,type:type,pointid:pointid,kindid:kindid,nameid:nameid,formid:formid ,sta:sta,starttime:starttime,endtime:endtime};
                       
   var rol = "<%= ViewData["roles"] %>";
 var w = $(window).width()*0.94;
 //alert(w);
            $("#myDataTable").jqGrid({
               multiselect: true, //复选
              
            // url: ' <%= ViewData["rootUri"] %>UpdatedInfo/SearchBy',
                datatype: "json",
                height: 333,
                postData: PostData,
             //width:w,
                scrollOffset: 0,
                viewsortcols: [false, 'vertical', false],
                multiboxonly: true,
                autowidth: true,
           // caption: "DemoGrid",标题
                shrinkToFit: true,
                   rowNum: 10,
                       viewrecords: true,
                rowList: [10, 20, 30],
                 pager: '#pager10',
                rownumbers: true,
                colNames: ['ied','le','省/自治区', ' 盟/市', '区/县', '测报点名称','病虫类别','病虫名称','上报时间','测报员','表格','fffid','审核状态','操作'],
                colModel: [{ name: 'id', index: 'id', align: "center", width: 0.1},
                          { name: 'level', index: 'level', align: "center", width: 0.1 },
                            { name: 'sheng', index: 'sheng', align: "center", width:150},
                            { name: 'shi', index: 'shi', align: "center" , width: 150},
                            { name: 'xian', index: 'xian', align: "center" , width: 150},
                            { name: 'pointname', index: 'pointname', align: "center" , width: 150},
                            { name: 'blight_kind', index: 'blight_kind', align: "center",  width:120,
                                         formatter: function (cellvalue, options, rowObject) {
                                                                                                    if (cellvalue==0) {return "病害"; }
                                                                                                    if (cellvalue==1) {return "虫害"; }
                                                                                              }
                            },
                            { name: 'blight_name', index: 'blight_name', align: "center" , width: 120},
                            { name: 'report_time', index: 'report_time', align: "center",formatter: 'date', formatoptions: { srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d'} },
                            { name: 'user_name', index: 'user_name', align: "center" , width: 100},

                            { name: 'form_name', index: 'form_name', align: "center"},
                            
                            { name: 'form_id', index: 'form_id', align: "center" , width:0.1},
                            { name: 'review_state', index: 'review_state', align: "center" , width: 120, formatter: function (cellvalue, options, rowObject) {
                            if (l=="4"||l=="0") {
                             if (rowObject.level==0) {
                                  if (cellvalue==1) {
                                    return "待市级审核";
                                  }
                                   if (cellvalue==2) {
                                     return "待省级审核";                                    

                                  }
                                   if (cellvalue==3) {
                                     return "市级未通过";
                                  }
                                   if (cellvalue==4) {
                                     return "省级通过";
                                  }
                                   if (cellvalue==5) {
                                     return "省级未通过";
                                  }
                                
                                  }else{

                                  return ("/");

                                  }
                            }
                                 if (l=="1") {
                             if (rowObject.level==0) {
                                  if (cellvalue==1) {
                                    return "待审核";
                                  }
                                   if (cellvalue==2) {
                                     return "待审核";                                    

                                  }
                                   if (cellvalue==3) {
                                     return "未通过";
                                  }
                                   if (cellvalue==4) {
                                     return "通过";
                                  }
                                   if (cellvalue==5) {
                                     return "未通过";
                                  }
                                
                                  }else{

                                  return ("/");

                                  }
                            }

                             if (l=="2") {
                             if (rowObject.level==0) {
                                  if (cellvalue==1) {
                                    return "未审核";
                                  }
                                   if (cellvalue==2) {
                                     return "已审核";                                    

                                  }
                                   if (cellvalue==3) {
                                     return "未通过";
                                  }
                                   if (cellvalue==4) {
                                     return "通过";
                                  }
                                   if (cellvalue==5) {
                                     return "未通过";
                                  }
                                
                                  }else{

                                  return ("/");

                                  }
                            }
                                 if (l=="3") {
                             if (rowObject.level==0) {
                                  if (cellvalue==1) {
                                    return "未审核";
                                  }
                                   if (cellvalue==2) {
                                     return "未审核";                                    

                                  }
                                   if (cellvalue==3) {
                                     return "未通过";
                                  }
                                   if (cellvalue==4) {
                                     return "通过";
                                  }
                                   if (cellvalue==5) {
                                     return "未通过";
                                  }
                                
                                  }else{

                                  return ("/");

                                  }
                            }
                                 
                               }},
                             { name: 'r', index: 'r', width:220, align: "center", formatter: function (cellvalue, options, rowObject) {
                                   if (rowObject.level==0) {
                                   if (l=="1") {
                                     var str ="";
                                    if (rol.indexOf('ViewReportData')>-1) {
                                    str+='<a href="#"  onclick="showtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">查看</a>&nbsp;&nbsp;';
                                    }
                                      if (rol.indexOf('UpdateReportData')>-1) {
                                    str+='<a href="#" onclick="mshowtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">修改</a>&nbsp;&nbsp;'
                                    }
                                      if (rol.indexOf('DeleteReportData')>-1) {
                                    str+='<a href="#" onclick="deltedreport(' + rowObject.id+')"; role="button" data-toggle="modal" style="display:inline">删除</a>'
                                    }
                                    if (rowObject.review_state==4) {
                                   if (rol.indexOf('ViewReportData')>-1) {
                                    str='<a href="#"  onclick="showtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">查看</a>&nbsp;&nbsp;';
                                    }
                                    }
                                 return str;

                                    
                                   }else{
                                   if (l=="2") {
                                    if (rowObject.review_state==1) {
                                      var str ="";
                                    if (rol.indexOf('ViewReportData')>-1) {
                                    str+='<a href="#"  onclick="showtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">查看</a>&nbsp;&nbsp;';
                                    }
                                      if (rol.indexOf('ReviewReportData')>-1) {
                                    str+='<a href="#" onclick="checkpass(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">审核</a>'
                                    }
                                 return str;

                                   }else{
                                    if (rol.indexOf('ViewReportData')>-1) {
                                    str+='<a href="#"  onclick="showtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">查看</a>&nbsp;&nbsp;';
                            
                                return '<a href="#" onclick="showtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">查看</a>';
                                }else{
                                return "";
                                }
                                   }
                                   }
                                   if (l=="3") {
                                    if (rowObject.review_state==2) {
                                         var str ="";
                                    if (rol.indexOf('ViewReportData')>-1) {
                                    str+='<a href="#"  onclick="showtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">查看</a>&nbsp;&nbsp;';
                                    }
                                      if (rol.indexOf('ReviewReportData')>-1) {
                                    str+='<a href="#" onclick="checkpass(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">审核</a>'
                                    }
                                 return str;
                                   }else{

                                      if (rol.indexOf('ViewReportData')>-1) {
                                    str+='<a href="#"  onclick="showtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">查看</a>&nbsp;&nbsp;';
                            
                                return '<a href="#" onclick="showtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">查看</a>';
                                }else{
                                return "";
                                }
                                   }
                                   }
                                  if (l=="4"||l=="0") {
                                

                                                                         if (rol.indexOf('ViewReportData')>-1) {
                                    str+='<a href="#"  onclick="showtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">查看</a>&nbsp;&nbsp;';
                            
                                return '<a href="#" onclick="showtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">查看</a>';
                                }else{
                                return "";
                                }

                                   }


                                   }
                                 

                                   }else{
                                    if (l=="1") {
                                    if (rowObject.level==3) {
                                                                        var str ="";
                                    if (rol.indexOf('ViewReportData')>-1) {
                                    str+='<a href="#"  onclick="showtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">查看</a>&nbsp;&nbsp;';
                                    }
                                      if (rol.indexOf('UpdateReportData')>-1) {
                                    str+='<a href="#" onclick="mshowtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">修改</a>&nbsp;&nbsp;'
                                    }
                                      if (rol.indexOf('DeleteReportData')>-1) {
                                    str+='<a href="#" onclick="deltedreport(' + rowObject.id+')"; role="button" data-toggle="modal" style="display:inline">删除</a>'
                                    }
                                 return str;

                                    }else{
                                  if (rol.indexOf('ViewReportData')>-1) {
                                    str+='<a href="#"  onclick="showtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">查看</a>&nbsp;&nbsp;';
                            
                                return '<a href="#" onclick="showtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">查看</a>';
                                }else{
                                return "";
                                }

                                    }
                                 
                                    }
                                      if (l=="2") {
                                    if (rowObject.level==2) {

                                                                                var str ="";
                                    if (rol.indexOf('ViewReportData')>-1) {
                                    str+='<a href="#"  onclick="showtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">查看</a>&nbsp;&nbsp;';
                                    }
                                      if (rol.indexOf('UpdateReportData')>-1) {
                                    str+='<a href="#" onclick="mshowtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">修改</a>&nbsp;&nbsp;'
                                    }
                                      if (rol.indexOf('DeleteReportData')>-1) {
                                    str+='<a href="#" onclick="deltedreport(' + rowObject.id+')"; role="button" data-toggle="modal" style="display:inline">删除</a>'
                                    }
                                 return str;

                                    }else{
                                  
                                     if (rol.indexOf('ViewReportData')>-1) {
                                    str+='<a href="#"  onclick="showtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">查看</a>&nbsp;&nbsp;';
                            
                                return '<a href="#" onclick="showtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">查看</a>';
                                }else{
                                return "";
                                }
            
                                    }
                                 
                                    }
                                      if (l=="3") {
                                    if (rowObject.level==1) {
                                                                                var str ="";
                                    if (rol.indexOf('ViewReportData')>-1) {
                                    str+='<a href="#"  onclick="showtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">查看</a>&nbsp;&nbsp;';
                                    }
                                      if (rol.indexOf('UpdateReportData')>-1) {
                                    str+='<a href="#" onclick="mshowtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">修改</a>&nbsp;&nbsp;'
                                    }
                                      if (rol.indexOf('DeleteReportData')>-1) {
                                    str+='<a href="#" onclick="deltedreport(' + rowObject.id+')"; role="button" data-toggle="modal" style="display:inline">删除</a>'
                                    }
                                 return str;

                                    }else{
                                  if (rol.indexOf('ViewReportData')>-1) {
                                    str+='<a href="#"  onclick="showtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">查看</a>&nbsp;&nbsp;';
                            
                                return '<a href="#" onclick="showtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">查看</a>';
                                }else{
                                return "";
                                }

                                    }
                                 
                                    }
                                    
                                   }
                                        if (l=="0") {   if (rol.indexOf('ViewReportData')>-1) {
                                    str+='<a href="#"  onclick="showtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">查看</a>&nbsp;&nbsp;';
                            
                                return '<a href="#" onclick="showtable(' + rowObject.form_id +","+ rowObject.id +')"; role="button" data-toggle="modal" style="display:inline">查看</a>';
                                }else{
                                return "";
                                }}
                                }},
//                            { name: 'userid', index: 'userid', width: 100, align: "right" },
//                            { name: 'taskname', index: 'taskname', width: 250, align: "center",
//                                formatter: function (cellvalue, options, rowObject) {
//                                    return '<a class="editcls" href="<%= ViewData["rootUri"] %>OATask/TaskContent?taskid=' + escape(rowObject.taskid) + '">' + cellvalue + '</a>';
//                                }
//                            },
//                            { name: 'peoplename', index: 'peoplename', width: 200, align: "center" },

//                            { name: 'endtime', index: 'endtime', width: 200, align: "center", formatter: 'date', formatoptions: { srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d'} },
//                             { name: 'status', index: 'status', width: 200, align: "center",
//                                 formatter: function (cellvalue, options, rowObject) {
//                                     if (cellvalue == 0) {
//                                         return "发布";
//                                     }
//                                     if (cellvalue == 1) {
//                                         return "收到";
//                                     }
//                                     if (cellvalue == 4) {
//                                         return "已完成";
//                                     }
//                                 }
//                             },
//                            { name: 'operate', index: 'operate', width: 200, align: "center",
//                                formatter: function (cellvalue, options, rowObject) {
//                                    if (rowObject.nowid == rowObject.ppnid) {
//                                        if (rowObject.usere == 0) {
//                                            return "<input value='收到' type='button' onclick='gettask(" + rowObject.taskid + ")'/>&nbsp;<input value='修改' type='button' onclick='Updatetask(" + rowObject.taskid + ")'/>";
//                                        }
//                                        if (rowObject.process == 1) {
//                                            return "<input value='修改' type='button' onclick='Updatetask(" + rowObject.taskid + ")'/>";
//                                        }
//                                    }
//                                    if (rowObject.nowid == rowObject.userid) {
//                                        if (rowObject.usere == 0) {
//                                            return "<input value='取消' type='button' onclick='deLetetask(" + rowObject.taskid + ")'/>&nbsp;<input value='修改' type='button' onclick='Updatetask(" + rowObject.taskid + ")'/>";
//                                        }
//                                        else {
//                                            return "<input value='修改' type='button' onclick='Updatetask(" + rowObject.taskid + ")'/>";
//                                        }
//                                    }
//                                }
//                            }
                ],
           
            });
        $("#myDataTable").jqGrid('hideCol', "id");
          $("#myDataTable").jqGrid('hideCol', "form_id");
            $("#myDataTable").jqGrid('hideCol', "level");
//            $("#myDataTable").jqGrid('hideCol', "userid");
//            $("#myDataTable").jqGrid('hideCol', "usere");
        $("#myDataTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });
        });
        jQuery(function ($) {
            $(".select2").css('width', '120px').select2({ minimumResultsForSearch: -1 });
  

        });
        var dlg1;
        var dlg2;
        var dlg3;

        function checkpass(tableid,rid) {
         var u = "<%= ViewData["rootUri"] %>"+"UpdatedInfo/PassCheck";
           var unl = "<%= ViewData["rootUri"] %>"+"UpdatedInfo/NotPassCheck";
            
              if (tableid != null) {
                $.ajax({
                   
                    url: '<%= ViewData["rootUri"] %>'+ "UpdatedInfo/UpGetTableHtml",
                    data: { "tableid": tableid,"repid":rid,"type":0},
                    method: 'post',
                    dataType: "json",
                    success: function (data) {
                        if (data.length > 0) {
                            $("#checktable").html(data);

                        }
                        else{
                        }
                    },
                });
                $.ajax({
                
                    url: '<%= ViewData["rootUri"] %>'+ "UpdatedInfo/GetTitleNames",
                    data: { "reportid":rid},
                    method: 'post',
                    dataType: "json",
                    success: function (data) {
                        if (data.length > 0) {
                            $("#title").html(data);

                        }
                        else{
                        }
                    },
                });
                  $.ajax({
                  
                    url: '<%= ViewData["rootUri"] %>'+ "UpdatedInfo/GetLog",
                    data: { "tableid": tableid,"repid":rid},
                    method: 'post',
                    dataType: "json",
                    success: function (data) {
                        if (data!="n") {
                            $("#log").val(data);

                        }
                        else{
                        $("#log").val("");
                        }
                    },
                });
            }
  
            dlg1 = $("#dialog2").removeClass('hide').dialog({
                modal: false,
                width: 500,
               
                buttons: [
					{
					    text: "通过",
					    "class": "btn btn-primary btn-xs",
					    click: function () {
                         var passreason = $('#passreason').val();
                         //  alert(rid+"---passreason:"+passreason);
                            if (confirm("确定审核通过？")) {
                                $.ajax({
			                        url :u,
				                    data:{"rid":rid,
                                            "reason":passreason



                                    },
				                    dataType : "json",
				                    method:'post',
				                    success: function (jsonObject) {
                                    if (jsonObject=="ok") {
                                     toastr["success"]("通过审核操作成功！");
                                    }else{
                                     toastr["success"]("操作失败，请检查！");
                                    }
                                     
                                    
				                    searchby();
                       $('#dialog2').dialog("close");
                     //  document.getElementById('r').innerHTML="  <textarea rows='3' cols='50' id='passreason' ></textarea>";

				                     }
                   
							
			
			                    });
                            
                            }else{

                                   $('#dialog2').dialog("close");

                            }





                       
					    }
					},
					{
					    text: "不通过",
					    "class": "btn btn-xs",
					    click: function () {
                        var passreason = $('#passreason').val();
					      //  $(this).dialog("close");
                            if (confirm("确定审核不通过？")) {
                                        $.ajax({
			                        url :unl,
				                    data:{"rid":rid,
                                            "reason":passreason



                                    },
				                    dataType : "json",
				                    method:'post',
				                    success: function (jsonObject) {
                                    if (jsonObject=="ok") {
                                     toastr["success"]("不通过审核操作成功！");
                                    }else{
                                     toastr["success"]("操作失败，请检查！");
                                    }
                                    
				                    searchby();
                       $('#dialog2').dialog("close");
                     //  document.getElementById('r').innerHTML="  <textarea rows='3' cols='50' id='passreason' ></textarea>";

				                     }
                   
							
			
			                    });
                      
                            }else{

                                   $('#dialog2').dialog("close");

                            }
                       
					    }
					}
				]
            });

        }
        function shengselect(shengid){
        var u = "<%= ViewData["rootUri"] %>"+"UpdatedInfo/AddShilist";
       
                 var type = $('#testtype').val();
           if (shengid>0) {
            document.getElementById("shi").options.length = 1;
             $.ajax({
			url :u,
				data:"shengid="+shengid,
				dataType : "json",
				method:'post',
				success: function (jsonObject) {
				    for (var s in jsonObject) {
                      
                     
				        $("#shi").append("<option value=" + jsonObject[s].uid + ">" + jsonObject[s].name + "</option>");
				    } 
                             $("#shi").select2({ allowClear: true,minimumResultsForSearch: -1 });
                             
                             document.getElementById("shi").disabled=false;
                       $("#xian").val(0);
                     $("#xian").select2({ allowClear: true,minimumResultsForSearch: -1 });
                      document.getElementById("xian").disabled=true;
                              typeselect(type);
                     
                       

				    }
                   
							
			
			});
           } else{
              $("#xian").val(0);
              $("#xian").select2({ allowClear: true,minimumResultsForSearch: -1 });
              $("#shi").val(0);
              $("#shi").select2({ allowClear: true,minimumResultsForSearch: -1 });
              $("#testpoint").select2({ allowClear: true,minimumResultsForSearch: -1 });
              $("#testpoint").val(0);
    
         document.getElementById("xian").disabled=true;
           document.getElementById("shi").disabled=true;
       //  document.getElementById("testtype").disabled=true;
        // document.getElementById("testlevel").disabled=true;
         document.getElementById("testpoint").disabled=true;
            typeselect(type);
           
           }

  


                 
     
        

     }
     function levelselect(level){
     if (level>0) {
     document.getElementById("checkstatus").disabled=true;

     }else{

          document.getElementById("checkstatus").disabled=false;

     }
     var type = $('#testtype').val();
         typeselect(type);
        
        
     }
     //改变测报点类型
     function typeselect(type){
       var kindid =  $('#bkind').val();
     document.getElementById("testpoint").options.length = 1;
        var u = "<%= ViewData["rootUri"] %>"+"UpdatedInfo/GetPointlist";
             var shengid = $('#sheng').val();
                  var shiid = $('#shi').val();
     var xianid = $('#xian').val();
        var level=$('#testlevel').val();
    var ul = <%= ViewData["ul"] %>;
               
             if (xianid>0) {document.getElementById("testpoint").disabled=false;  
              document.getElementById("testtype").disabled=false;
               //document.getElementById("bkind").disabled=false;
               
               if (ul=="4") {
                document.getElementById("testlevel").disabled=true;
               }else{  document.getElementById("testlevel").disabled=false;}
             }else
            {
            document.getElementById("testpoint").disabled=true;   
            //document.getElementById("testtype").disabled=true;
              // document.getElementById("bkind").disabled=true;

             //  document.getElementById("bname").disabled=true;
               // document.getElementById("tablename").disabled=true;
              //  document.getElementById("testlevel").disabled=true;
              // document.getElementById("testlevel").disabled=true;
             }

            
              
     //alert("type+"+type+"xian"+xianid+"level"+level+"shengid"+shengid+"shhiid"+shiid);
       $.ajax({
			url :u,
				data:{"shengid":shengid ,"shiid":shiid,"xianid":xianid,"level":level,"type":type},
				dataType : "json",
				method:'post',
				success: function (jsonObject) {
				    for (var s in jsonObject) {
                      
                     
				        $("#testpoint").append("<option value=" + jsonObject[s].uid + ">" + jsonObject[s].name + "</option>");
				    }
                                //  document.getElementById("bkind").value=2;
              document.getElementById("checkstatus").value=0;
                 //document.getElementById("bname").value=0;
        
               //  document.getElementById("tablename").value=0;
     //$("#bkind").select2({ allowClear: true,minimumResultsForSearch: -1 });
      $("#checkstatus").select2({ allowClear: true,minimumResultsForSearch: -1 });
           // $("#bname").select2({ allowClear: true,minimumResultsForSearch: -1 });
    $("#testtype").select2({ allowClear: true,minimumResultsForSearch: -1 });
       $("#testlevel").select2({ allowClear: true,minimumResultsForSearch: -1 });
       $("#testpoint").select2({ allowClear: true,minimumResultsForSearch: -1 });
      //  $("#tablename").select2({ allowClear: true,minimumResultsForSearch: -1 });
				    }
                   
							
			
			});


  
     
     }
     
    function shiselect(shiid){
      var kindid =  $('#bkind').val();
        var u = "<%= ViewData["rootUri"] %>"+"UpdatedInfo/AddXianlist";
        document.getElementById("xian").options.length = 1;
         var type = $('#testtype').val();
         if (shiid>0) {
          document.getElementById("xian").disabled=false; 
            $.ajax({
			url :u,
				data:"shiid="+shiid,
				dataType : "json",
				method:'post',
				success: function (jsonObject) {
				    for (var s in jsonObject) {
                      
                     
				        $("#xian").append("<option value=" + jsonObject[s].uid + ">" + jsonObject[s].name + "</option>");
				    }
                             $("#xian").select2({ allowClear: true,minimumResultsForSearch: -1 });
                            typeselect(type);
             

				    }
                   
							
			
			});
         }else{  
              $("#xian").select2({ allowClear: true,minimumResultsForSearch: -1 });
              
         $("#xian").val(0);
         $("#testpoint").select2({ allowClear: true,minimumResultsForSearch: -1 });
         $("#testpoint").val(0);
    
         document.getElementById("xian").disabled=true;
       //  document.getElementById("testtype").disabled=true;
        // document.getElementById("testlevel").disabled=true;
         document.getElementById("testpoint").disabled=true;}
     typeselect(type);
        
    

     }
        function ShowDialog2() {
            dlg2= $("#dialog2").removeClass('hide').dialog({
                modal: true,
                width: 800,
                buttons: [
					   {
					    text: "通过",
					    "class": "btn btn-primary btn-xs",
					    click: function () {
					        var fiedname = $("#fiedname").val();
					        var type = $("#type").val();
					        var parentornot = $("#parentornot").val();
					        var parentid = $("#parentid").val();

					        if (fiedname != null && fiedname != "") {
					            $.ajax({
					                type: "POST",
					                url: "<%= ViewData["rootUri"] %>" + "",
					                //data: { },
					                dataType: "json",
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
					                        toastr["success"]("审核成功！", "恭喜您");
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
					                        toastr["error"]("操作失败", "温馨敬告");
					                    }
					                }
					            });
					        }
					        clumdlg.dialog("close");
					    }
					},
					    {
					        text: "未通过",
					        "class": "btn btn-xs",
					        click: function () {
					            $(this).dialog("close");
					        }
					    }
				    ]
            });
        }
        function ShowDialog3() {
            dlg3 = $("#dialog3").removeClass('hide').dialog({
                modal: true,
                width: 800,
                buttons: [
					        {
					            text: "反审核",
					            "class": "btn btn-primary btn-xs",
					            click: function () {
					                $(this).dialog("close");
					            }
					        }
				         ]
            });
        }
    </script>
</asp:Content>
