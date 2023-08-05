<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>
<%@ Import Namespace="BingChongBackend.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<% var role = CommonModel.GetUserRoleInfo(); %>
<div class="page-header">
	<div>
        <div class="col-xs-2 no-padding-left">
            <h5>测报点管理</h5>
        </div>
		<div class="pull-right">
            <p> <% if (role != null && ((string)role).Contains("AddPoint"))
            { %> 
                <a class="btn btn-sm btn-white  btn-default btn-bold" href="<%= ViewData["rootUri"] %>Station/AddStation">
	                <i class="ace-icon fa fa-plus blue"></i>新增
                </a>
                <%} %>
                <% if (role != null && ((string)role).Contains("BatchImportPoint"))
                 { %> 
                <a class="btn btn-sm btn-white  btn-default btn-bold" onclick="ShowTable()" href="#">
	                <i class="ace-icon fa fa-arrow-up blue"></i>批量导入
                </a>
                <%} %>
                <% if (role != null && ((string)role).Contains("BatchExportPoint"))
                 { %> 
                <a class="btn btn-sm btn-white  btn-default btn-bold" id="import" href="#">
	                <i class="ace-icon fa fa-arrow-down blue"></i>批量导出
                </a>
                <%} %>
                <% if (role != null && ((string)role).Contains("BatchDeletePoint"))
                 { %> 
                <a class="btn btn-sm btn-white  btn-default btn-bold" id="yes">
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
                            <div class="form-group" >
                                <label class="col-sm-1 control-label no-padding-right" for="">测报点级别</label>
				                <div class="col-sm-1">
                                    <div class="clearfix" style="width:150px">
						                <select class="select2" id="sort" name="sort" data-placeholder="请选择">
                                           <%if (Convert.ToInt16(ViewData["level"]) == 0)
                          {%>
                            <option value="4" selected="selected">全部</option>
                            <option value="0">国家级</option>
                            <option value="1" >省级</option>
                            <option value="2">市级</option>
                            <option value="3">县级</option>  
                            <%} %>
                            <%if (Convert.ToInt16(ViewData["level"]) == 1)
                          {%>
                            <option value="0">国家级</option>
                            <option value="3" selected="selected">县级</option>  
                            <%} %>  
                            <%if (Convert.ToInt16(ViewData["level"]) == 2)
                          {%>
                            <option value="0">国家级</option>
                            <option value="2" selected="selected">市级</option>
                            <%} %>  
                            <%if (Convert.ToInt16(ViewData["level"]) == 3)
                          {%>
                            <option value="0">国家级</option>
                            <option value="1" selected="selected">省级</option>
                            <%} %>   
                            <%if (Convert.ToInt16(ViewData["level"]) == 4)
                          {%>
                            <option value="0"  selected="selected">国家级</option>
                            <%} %>                                               
				                        </select>
                                    </div>
				                </div>  
                                <label class="col-sm-1 control-label no-padding-right" style="margin-left:50px" for="">省/自治区</label>
				                <div class="col-sm-1">
                                    <div class="clearfix" style="width:150px">
                                    
                      
                          <select style="width:40%" id="sheng"  name="sheng" onchange="dutyset(this.value)">
                               <option value="0" >请选择</option>
                               <%
                              for (int i = 0; i < ViewBag.shengc.Count; i++)
                              {   
                                   %>
                               <option value="<%=ViewBag.shengc[i].uid %>" ><%=ViewBag.shengc[i].name%></option>
                               <% }%>
                               </select>
                    
						                
                                    </div>
				                </div>  
                                <label class="col-sm-1 control-label no-padding-right" style="margin-left:50px" for="">盟/市</label>
				                <div class="col-sm-1">
                                    <div class="clearfix" style="width:150px">

                        <select class="select2" style="width:40%" id="shi"  name="shi" onchange="dutyset1(this.value)" >
                                            <option value="0" >请选择</option>            
                                            </select>
                      
                                    </div>
				                </div> 
                            </div> 
                            <div class="form-group" style="margin-bottom:0px">
                                <label class="col-sm-1 control-label no-padding-right" for="">旗/县</label>
				                <div class="col-sm-1">
                                    <div class="clearfix" style="width:150px">
                                      
                                <select class="select2" style="width:40%" id="xian"  name="xian" ">
                                      <option value="0" >请选择</option>             
                                    </select>
                                    </div>
				                </div>  
                                <label class="col-sm-1 control-label no-padding-right" style="margin-left:50px" for="">测报点类型</label>
				                <div class="col-sm-2">
                                    <div  >
						                <select  style="width:150px" class="select2" id="style1" name="style1">
                                            <option value="2" >全部</option>  
                                            <option value="0">固定测报点</option>
                                            <option value="1" >非固定测报点</option>                         
				                        </select>
                                    </div>
				                </div>  
                                <div class="col-sm-2">
						            <input  id="pointname" value="" style="height:28px;margin-top:1px" placeholder="请输入关键字"/>
                                </div>  
                                <div class="col-sm-1" style="">
						            <a class="btn btn-sm btn-info" id="find" onclick=""><i class="fa fa-search"></i> 搜索</a>
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
<div id="dialog-message"  class="hide">
    
        <div class="col-xs-12" id="dialogdetail">
            <div style="margin-left:30px;margin-top:0px">
              <a class="btn btn-sm btn-info" href="javascript:$('#uploadify').uploadify('upload','*')">
	                    <i class="ace-icon fa fa-arrow-up"></i>上&nbsp;&nbsp;传</a> 
            </div>
            <div style="margin-top:-33px;margin-left:180px">
            <span style="color:red">**温馨提示:请使用范本格式上传,请认真填写，注意格式**</span></br>
               <a class="btn btn-sm btn-white btn-default btn-bold" href="<%= ViewData["rootUri"] %>Station/EditFile">
	                    <i class="ace-icon fa fa-arrow-down blue"></i>下载范本</a>  
            </div>
            <div style="margin-left:180px;margin-top:15px">
               <input  id="uploadify" type="file" class="upload" name="upload" />
            </div>
        
        
          
        </div>    
</div>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
	
    <link href="<%= ViewData["rootUri"] %>Content/css/ui.jqgrid.css" rel="Stylesheet"/> 
    <link rel="stylesheet" type="text/css" href="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.min.css" /> 
    <link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/js/uploadify/uploadify.css" />
    <link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/jquery-ui.min.css" />
    <link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/select2.css" /> 
    <style type="text/css">
        #sort
        {
            width: 150px;
        }
    </style>
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
	<script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/uploadify/jquery.uploadify.js"></script> 
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jqGrid/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jqGrid/i18n/grid.locale-cn.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery.ui.touch-punch.min.js"></script> 
    <script type="text/javascript">
            var level="<%= ViewData["level"] %> ";
            var shenga="<%= ViewData["sheng1"] %>";
            var shia="<%= ViewData["shi1"] %>";
            var xiana="<%= ViewData["xian1"] %>";

    jQuery(function ($) {
            $(".select2").css('width', '150px').select2({ minimumResultsForSearch: -1 });
            $("#sheng").css('width', '150px').select2({ minimumResultsForSearch: -1 });
            GetUserInfo();
            RetrieveTable();
        });

             removeclass();          
  
    $(document).ready(function() { 
            $("#uploadify").uploadify({
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
                    'uploader':"<%= ViewData["rootUri"] %>Station/UploadifyComplain", 
                    'onUploadSuccess': function (file, data, response) {
                       if(data=="1"){
                            toas1();
                                setTimeout(function(){
                                window.location.reload();
                            }, 2500);
                            
                        }
                        if(data=="2")
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

				        toastr["error"]("没有找到要上传的数据!", "温馨敬告");
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

				        toastr["error"]("上传出现错误,请选择范本格式添加,并注意填写格式!", "温馨敬告");
                                setTimeout(function(){
                                window.location.reload();
                            }, 2500);
                            
                        }
                    }, 

              });
    });
    function removeclass() {
      $("#sheng").attr("disabled", false);
      $("#shi").attr("disabled", false);
      $("#xian").attr("disabled", false); 
    }
    function addclass() {
        if(level==0)
        {
          $("#sheng").attr("disabled", false);
          $("#shi").attr("disabled", false);
          $("#xian").attr("disabled", false); 
        }
        if(level==1)
        {
          $("#sheng").attr("disabled", "disabled");
          $("#shi").attr("disabled", "disabled");
          $("#xian").attr("disabled", "disabled"); 
        }
        if(level==2)
        {
          $("#sheng").attr("disabled", "disabled");
          $("#shi").attr("disabled", "disabled");
          $("#xian").attr("disabled", false); 
        }
        if(level==3)
        {
          $("#sheng").attr("disabled", "disabled");
          $("#shi").attr("disabled", false);
          $("#xian").attr("disabled", false); 
        }
        if(level==4)
        {
          $("#sheng").attr("disabled", false);
          $("#shi").attr("disabled", false);
          $("#xian").attr("disabled", false); 
        }
      
    }
     function dutyset(id) {
            
            document.getElementById("shi").options.length = 0;
            if (id != 0) {
                $.ajax({
                    url: "<%= ViewData["rootUri"] %>Station/Findshi",
                    data: { "shengid": id },
                    method: 'post',
                    success: function (data) {
                       if(level==2||level==1)
                            {
                                     $("#shi").append("<option value=" + "0" + ">" + "请选择" + "</option>");
                                for (var s in data) {
                                    if (data[s].uid==shia) {
                                        $("#shi").append("<option value=" + data[s].uid + " selected='selected'>" + data[s].name + "</option>");
                                    } else {
                                        $("#shi").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                                    }
                                }
                            }else
                            {
                                    $("#shi").append("<option value=" + "0" + ">" + "请选择" + "</option>");
                                   for (var s in data) {
                                    if (s == 0) {
                                        $("#shi").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                                    } else {
                                        $("#shi").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                                    }
                                }
                            }
                       
                        document.getElementById("xian").value=0;
                        $("#shi").css('width', '150px').select2({allowClear:true, minimumResultsForSearch: -1 });
                        $("#xian").css('width', '150px').select2({allowClear:true, minimumResultsForSearch: -1 });
                    }
                });
            } else {
                $("#shi").append("<option value=" + "0" + ">" + "请选择" + "</option>");
                 $("#shi").css('width', '150px').select2({allowClear:true, minimumResultsForSearch: -1 });
            }
        dutyset1(0);
        }
        function dutyset1(id) {
            document.getElementById("xian").options.length = 0;
            if (id != 0) {
                $.ajax({
                    url: "<%= ViewData["rootUri"] %>Station/Findxian",
                    data: { "shiid": id },
                    method: 'post',
                    success: function (data) {
                        if(level==1)
                            {
                                     $("#xian").append("<option value=" + "0" + ">" + "请选择" + "</option>");
                                for (var s in data) {
                                    if (data[s].uid==xiana) {
                                        $("#xian").append("<option value=" + data[s].uid + " selected='selected'>" + data[s].name + "</option>");
                                    } else {
                                        $("#xian").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                                    }
                                }
                            }else
                            {
                                         $("#xian").append("<option value=" + "0" + " selected='selected'>" + "请选择" + "</option>");
                                   for (var s in data) {
                                    if (s == 0) {
                                        $("#xian").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                                    } else {
                                        $("#xian").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                                    }
                                }
                            }
                        $("#xian").css('width', '150px').select2({allowClear:true, minimumResultsForSearch: -1 });
                    }
                });
            } else {
                $("#xian").append("<option value=" + "0" + ">" + "请选择" + "</option>");
                 $("#xian").css('width', '150px').select2({allowClear:true, minimumResultsForSearch: -1 });
            }
        }
     $(document).ready(function () {
        $("#find").click(function () {
            removeclass();
            var PostData = { sort:$("#sort").val(),style1:$("#style1").val(),sheng:$("#sheng").val(),shi:$("#shi").val(), xian:$("#xian").val(),pointname:$("#pointname").val()};
           
            jQuery("#myDataTable").setGridParam({page:1, url:'<%= ViewData["rootUri"] %>Station/FintPoint', postData: PostData }).trigger('reloadGrid');
            addclass();
        });
    });
        function GetUserInfo () {
            if(level==3)
                {
                 $("#sheng option[value='"+shenga+"']").attr("selected", "selected"); 
                 dutyset(shenga);
                  $("#sheng").css('width', '150px').select2({ minimumResultsForSearch: -1 });
                }
            if(level==2)
                {
                $("#sheng option[value='"+shenga+"']").attr("selected", "selected"); 
                 dutyset(shenga);
                 dutyset1(shia);
                  $("#sheng").css('width', '150px').select2({ minimumResultsForSearch: -1 });
                }
            if(level==1)
                {
                $("#sheng option[value='"+shenga+"']").attr("selected", "selected"); 
                 dutyset(shenga);
                 dutyset1(shia);
                  $("#sheng").css('width', '150px').select2({ minimumResultsForSearch: -1 });
                }
                 
          
        }

        function RetrieveTable(){
                var sort=$("#sort").val();
                var style1=$("#style1").val();
                var sheng=$("#sheng").val();
                var shi=$("#shi").val(); 
                var xian=$("#xian").val(); 
                
            if(level==3)
                {
                 shi="0";
                 xian="0";
                }
            if(level==2)
                {
                  shi=shia;
                  xian="0";
                }
            if(level==1)
                {
                  shi=shia;
                  xian=xiana;
                }         
                var pointname=$("#pointname").val(); 
              $("#myDataTable").jqGrid({
                multiselect: true, //复选
                url: '<%= ViewData["rootUri"] %>Station/FintPoint',
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
                    sort:sort,
                    style1:style1,
                    sheng:sheng,         
                    shi:shi, 
                    xian:xian,         
                    pointname:pointname   
                },
                viewsortcols: [false, 'vertical', false],
                colNames: [ 'id', '测报点名称', '测报点代码', '测报点类型', '测报点级别', "省/自治区", '盟/市', '旗/县', '操作'],
                colModel: [
                            { name: 'id', index: 'id', width: 1, align: "center" },
                            { name: 'name', index: 'name', width: 100, align: "center" },
                            { name: 'nickname', index: 'nickname', width: 100, align: "center" },
                            { name: 'type', index: 'type', width: 100, align: "center",
                                formatter: function (cellvalue, options, rowObject) {
                                if(cellvalue==0)
                                {
                                    return "固定测报点";
                                }
                                if(cellvalue==1)
                                {
                                    return "非固定测报点";
                                }
                               }
                             },
                            { name: 'level', index: 'level', width: 100, align: "center",
                                formatter: function (cellvalue, options, rowObject) {
                                if(cellvalue==0)
                                {
                                    return "国家级";
                                }
                                if(cellvalue==1)
                                {
                                    return "省级";
                                }
                                if(cellvalue==2)
                                {
                                    return "市级";
                                }
                                if(cellvalue==3)
                                {
                                    return "县级";
                                }
                               }
                            },
                            { name: 'sheng', index: 'sheng', width: 100, align: "center" },
                            { name: 'shi', index: 'shi', width: 100, align: "center"},
                             { name: 'xian', index: 'xian', width: 100, align: "center"},
                            { name: 'operate', index: 'operate', width: 100, align: "center",
                                formatter: function (cellvalue, options, rowObject) {
                                    return "<% var role = CommonModel.GetUserRoleInfo(); %>"
                                     +"<% if (role != null && ((string)role).Contains("ViewPoint")){ %>"
                                     +"<a  href='<%= ViewData["rootUri"] %>Station/SeeStation?makeid=1&pointid="+rowObject.id+"' style='font-size:12px'  title='查看';>查看</a>&nbsp;"
                                     +" <%} %>"
                                     +"<% if (role != null && ((string)role).Contains("UpdatePoint")){ %>"
                                     + "<a  href='<%= ViewData["rootUri"] %>Station/SeeStation?makeid=2&pointid="+rowObject.id+"' style='font-size:12px'  title='修改';>修改</a>&nbsp;"
                                     +" <%} %>"
                                     +"<% if (role != null && ((string)role).Contains("DeletePoint")){ %>"
                                     + "<a  href='#' style='font-size:12px'onclick='return deleteshop(" + rowObject.id + ")'  title='删除';>删除</a>&nbsp;";
                                     +" <%} %>"
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
              addclass(); 
            $("#myDataTable").jqGrid('hideCol', "id");
            $("#myDataTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });
            
        }
     $(document).ready(function () {
        $("#yes").click(function () {
            var con=confirm("是否确定要删除相关测报点");
              if(con==true){
                var ids = $("#myDataTable").jqGrid("getGridParam", "selarrrow");
                var uids = new Array();
                var ss = "";
                for (var i = 0; i < ids.length; i++) {
                    var id = ids[i];
                    uids[i] = $("#myDataTable").jqGrid("getCell", id, "id");
                    ss+= uids[i] + ",";
                }
                if (ss!=null&&ss!="") {
                    $.ajax({
                        type: "post",
                        data: { uids: ss    
                        },
                        url: "<%= ViewData["rootUri"] %>Station/Deletepoints",
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
                    alert('请选择要删除的测报点');
                }
              }
            });
        });
 function deleteshop(s){
    var con=confirm("是否确定要删除相关测报点");
           if(con==true){
                $.ajax({
                        type: "post",
                        data: { uids: s    
                        },
                        url: "<%= ViewData["rootUri"] %>Station/Deletepoint",
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
    $(document).ready(function () {
        $("#import").click(function () {
            var ids = $("#myDataTable").jqGrid('getDataIDs');
            var ids = $("#myDataTable").jqGrid("getGridParam", "selarrrow");
                var uids = new Array();
                var ss = "";
                for (var i = 0; i < ids.length; i++) {
                    var id = ids[i];
                    uids[i] = $("#myDataTable").jqGrid("getCell", id, "id");
                    ss+= uids[i] + ",";
                }
                if (ss!=null&&ss!="") {
                   var d=23;
                    var filename="测报点统计表";
                    location.href ="<%= ViewData["rootUri"] %>Station/GetPgExcel?d="+escape(d)+"&pfilename="+escape(filename)+"&uids="+escape(ss);
                }
                else {
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
				        toastr["error"]("请选择要导出的测报点!", "恭喜您");
                }
               
        });
    });
   var dialog = $("#dialog-message");
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
  function ShowTable() {
         $("#dialogdetail").show();
         dialog.removeClass('hide').dialog({
                //title: '',
                modal: true,
                width: 500,
                //open:beforeClosed,
                beforeClose: afterClosed
            });
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
