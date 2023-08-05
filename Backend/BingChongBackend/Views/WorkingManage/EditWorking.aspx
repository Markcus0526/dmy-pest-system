<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header">
	<div class="">
        <div class="col-xs-2 no-padding-left">
            <h5>编辑工作计划</h5>
        </div>
		<div class="pull-right">
        <div class="col-sm-1" style="">
						        
                                <a class="btn btn-sm btn-info" id="A3" href="javascript:history.go(-1);" ><i class="ace-icon fa fa-undo bigger-110"></i> 返回</a>
                                </div> 
            <p>
            </p>
		</div>
    </div>
</div>
<h3>
<%=ViewData["yearid"]%>年度<%=ViewData["shengid"]%><%=ViewData["shiid"]%>测报计划
</h3>
<div class="row">
    <div class="col-xs-12" style="margin-top:30px">
        <form class="form-horizontal" role="form" id="validation-form1">
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
                                            <select style="width:40%" id="sheng" class="select2"  name="sheng" onchange="dutyset(this.value)">
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
						                <select style="width:150px" class="select2" id="style1" name="style1">
                                            <option value="2" >全部</option>  
                                            <option value="0">固定测报点</option>
                                            <option value="1" >非固定测报点</option>                         
				                        </select>
                                    </div>
				                </div>  
                                <div class="col-sm-1" style="">
						            <a class="btn btn-sm btn-info" id="find" onclick="Find()"><i class="fa fa-search"></i> 搜索</a>
                                </div> 
                                 
                            </div>
                        </form>

                <div class="form-group" style="margin-bottom:0px">
                   <div style="margin-top:10px;margin-right:17px;border:1px solid #CCC;">
            
                      <p id="p" style="display:none;font-size:16px">没有找到要搜所的测报点</p>
                      <table id="myDataTable" style="text-align:center">
                      </table>
                   </div>
                </div>
            </br>
            <form class="form-horizontal" role="form" id="validation-form">
            <div class="form-group" style="">
                <label class="col-sm-2 control-label no-padding-right" for="">病虫害类别：</label>
			    <div class="col-sm-9">
                    <div class="clearfix">
					    <select class="select2" id="blkind" name="blkind" onchange="dutyset2(this.value)">
                                            
                                            <option value="0" <%if (Convert.ToInt16(ViewData["blkind"]) == 0){%>selected="selected" <%} %> >病害</option>
                                            <option value="1" <%if (Convert.ToInt16(ViewData["blkind"]) == 1){%>selected="selected" <%} %> >虫害</option>                          
				                        </select>
                    </div>
			    </div>  
            </div>
            <div class="form-group" style="">
                <label class="col-sm-2 control-label no-padding-right" for="">病虫害名称：</label>
			    <div class="col-sm-1">
                    <div class="clearfix">
					   <select class="select2" id="blname" name="blname" onchange="getform(this.value)">                   
				      
                       </select>
                    </div>
			    </div>  
            </div>
            <div class="form-group" style="">
                <label class="col-sm-2 control-label no-padding-right" for="">选择表格：</label>
			    <div class="col-sm-9">
                    <div class="clearfix">
					    <select class="select2" id="formss" name="formss" data-placeholder="请选择">
                                                  
				        </select>
                    </div>
			    </div>  
            </div>
            <div class="form-group" style="">
                <label class="col-sm-2 control-label no-padding-right" for="">测报日期区间：</label>
			    <div class="col-sm-1">
                    <div class="clearfix">
                        <div class="input-group">
						    <input class="input-medium date-picker" name = "starttime" id="starttime" value="<%=ViewData["starttime"] %>" type="text" readonly  placeholder="选择开始时间" />
						    <span class="input-group-addon">
						    <i class="ace-icon fa fa-calendar"></i>
						    </span>
				        </div>
                        <br />
                        <div class="input-group" >
						    <input class="input-medium date-picker" name = "endtime" id="endtime" value="<%=ViewData["endtime"] %>" type="text" readonly  placeholder="选择结束时间" />
						    <span class="input-group-addon">
						    <i class="ace-icon fa fa-calendar"></i>
						    </span>
				        </div>
                        
                    </div>
			    </div>  
            </div>
            <div class="form-group" style="">
                <label class="col-sm-2 control-label no-padding-right" for="">延续天数：</label>
			    <div class="col-sm-1">
                    <div class="clearfix">
                        <input type="text" id="dates" name="dates" readonly  value="<%= ViewData["dates"] %>"/>
                    </div>
			    </div>  
            </div>
            <div class="form-group" style="">
                <label class="col-sm-2 control-label no-padding-right" for="">调查与报表时间：</label>
			    <div class="col-sm-2">
                    <span>每</span>
                    <select id="days" name="days"  onchange="dutyset3(this.value)">
                        <%for (int i = 1; i < 10; i++){   
                                           %>
                           <option value="<%=i%>"><%=i%></option>
                         <% }%>  
                    </select>
                    <span>天</span>
			    </div>  
            </div>
            <div class="form-group" style="">
                <label class="col-sm-2 control-label no-padding-right" for="">应报表期数：</label>
			    <div class="col-sm-1">
                    <div class="clearfix">
                        <input type="text" id="fs" name="fs" readonly value="<%=ViewData["fs"] %>"/>
                    </div>
			    </div>  
            </div>
            <div class="form-group" style="">
                <label class="col-sm-2 control-label no-padding-right" for="">每次测报时间：</label>
			    <div class="col-sm-9">
                    <div class="clearfix">
                    <select id="stshi" name="stshi">
                        <%for (int i = 0; i < 24; i++){   
                                           %>
                           <option value="<%=i%>"><%=i%></option>
                         <% }%>  
                    </select>
                    <span>时</span>
                    <select id="stfen" name="stfen">
                        <%for (int i = 0; i < 60; i++){   
                                           %>
                           <option value="<%=i%>"><%=i%></option>
                         <% }%>  
                    </select>
                    <span>分&nbsp;至</span>
                    <select id="enshi" name="enshi">
                        <%for (int i = 0; i < 24; i++){   
                                           %>
                           <option value="<%=i%>"><%=i%></option>
                         <% }%>  
                    </select>
                    <span>时</span>
                    <select id="enfen" name="enfen">
                        <%for (int i = 0; i < 60; i++){   
                                           %>
                           <option value="<%=i%>"><%=i%></option>
                         <% }%>  
                    </select>
                    <span>分</span>
                    </div>
			    </div>  
            </div>
            </br>
            <div class="form-group" style="margin-bottom:0px">
                <span class="col-sm-5"></span>
                <div style="margin-left:100px">
                  <input id="text1" readonly="readonly" style="margin-top:10px;text-align:center;width:50%;height:20px; border-style:none;display:none;margin-left:150px;color: Red" />
                </div> 
                <div style="margin-left:400px">
                <button class="btn btn-info loading-btn" type="submit" >
								<i class="ace-icon fa fa-check bigger-110"></i>
								保存
							</button>

							&nbsp; &nbsp;
							<button id="sett" class="btn" type="reset" onclick="resett()">
								<i class="ace-icon fa fa-undo bigger-110"></i>
								重置
							</button> 
              </div> 
            </div>
        </form>  

    </div>


</div>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
	<link href="<%= ViewData["rootUri"] %>Content/css/ui.jqgrid.css" rel="Stylesheet"/> 
    <link rel="stylesheet" type="text/css" href="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.min.css" /> 
    <link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/jquery-ui.min.css" /> 
    <link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/js/uploadify/uploadify.css" />
    <link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/select2.css" />
    <link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/datepicker.css" />
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
	<script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/i18n/grid.locale-cn.js" type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery.ui.touch-punch.min.js"></script> 
    <script src="<%= ViewData["rootUri"] %>Content/js/uploadify/jquery.uploadify.min.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/uploadify/jquery.uploadify.js"></script> 
    <script src="<%= ViewData["rootUri"] %>Content/js/date-time/bootstrap-datepicker.min.js"></script>
	<script src="<%= ViewData["rootUri"] %>Content/js/date-time/locales/bootstrap-datepicker.zh-CN.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jquery.validate.min.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>
    <script type="text/javascript">
    
    var level="<%= ViewData["level"] %> ";
    var shenga="<%= ViewData["sheng"] %>";
    var shia="<%= ViewData["shi"] %>";
    var xiana="<%= ViewData["xian"] %>";
      removeclass();     
  var blk="<%=ViewData["blkind"] %>";
  var formid="<%=ViewData["formss"] %>";
  ds="<%=ViewData["days"] %>"
  sts="<%=ViewData["stshi"] %>"
  stf="<%=ViewData["stfen"] %>"
  ens="<%=ViewData["enshi"] %>"
  enf="<%=ViewData["enfen"] %>"
  $("#days option[value='"+ds+"']").attr("selected", "selected"); 
  $("#stshi option[value='"+sts+"']").attr("selected", "selected"); 
  $("#stfen option[value='"+stf+"']").attr("selected", "selected"); 
  $("#enshi option[value='"+ens+"']").attr("selected", "selected"); 
  $("#enfen option[value='"+enf+"']").attr("selected", "selected"); 
    dutyset2(blk);
    function resett()
    {
        window.location.reload();
//       var formsss="<%=ViewData["formss"] %>"
//        var lbk="<%=ViewData["blkind"] %>";
//         $("#blkind option[value='"+lbk+"']").attr("selected", "selected"); 
//         $("#formss option[value='"+formsss+"']").attr("selected", "selected");
//          $("#formss").css('width', '150px').select2({allowClear:true, minimumResultsForSearch: -1 });  
//        $("#blkind").css('width', '150px').select2({allowClear:true, minimumResultsForSearch: -1 });
//        dutyset2(lbk);
//     if(level==0)
//    {
//      $("#sort option[value='1']").attr("selected", "selected"); 
//      $("#shi option[value='0']").attr("selected", "selected"); 
//      $("#xian option[value='0']").attr("selected", "selected"); 
//      dutyset(shenga);
//      dutyset1(0);
//      $("#shi").css('width', '150px').select2({allowClear:true, minimumResultsForSearch: -1 });  
//      $("#xian").css('width', '150px').select2({allowClear:true, minimumResultsForSearch: -1 });  
//    }
//    if(level==3)
//    {
//    dutyset(shenga);
//    $("#sort option[value='1']").attr("selected", "selected"); 
//    }
//    if(level==2||level==1)
//    {
//       if(level==2)
//       {
//         $("#sort option[value='2']").attr("selected", "selected"); 
//       }
//     dutyset(shenga);
//     dutyset1(shia);
//    }
//    if(level==1)
//    {
//     $("#sort option[value='3']").attr("selected", "selected"); 
//     dutyset1(shia);
//    }
//     if(level==4)
//    {
//     $("#sort option[value='0']").attr("selected", "selected"); 
//    }
//    $("#sheng option[value='"+shenga+"']").attr("selected", "selected"); 
//     $("#sort").css('width', '150px').select2({allowClear:true, minimumResultsForSearch: -1 }); 
//      $("#sheng").css('width', '150px').select2({allowClear:true, minimumResultsForSearch: -1 });  
//      var blk="<%=ViewData["blkind"] %>";
//      ds="<%=ViewData["days"] %>"
//      sts="<%=ViewData["stshi"] %>"
//      stf="<%=ViewData["stfen"] %>"
//      ens="<%=ViewData["enshi"] %>"
//      enf="<%=ViewData["enfen"] %>"
//      $("#days option[value='"+ds+"']").attr("selected", "selected"); 
//      $("#stshi option[value='"+sts+"']").attr("selected", "selected"); 
//      $("#stfen option[value='"+stf+"']").attr("selected", "selected"); 
//      $("#enshi option[value='"+ens+"']").attr("selected", "selected"); 
//      $("#enfen option[value='"+enf+"']").attr("selected", "selected"); 
//      $("#style1 option[value='2']").attr("selected", "selected"); 
//      $("#style1").css('width', '150px').select2({allowClear:true, minimumResultsForSearch: -1 }); 
    }
        jQuery(function ($) {
            $(".select2").css('width', '150px').select2({ minimumResultsForSearch: -1 });
            $("#Select1").css('width', '150px').select2({ allowClear: true });
            GetUserInfo();
            RetrieveTable();
            $('#starttime').datepicker({autoclose: true, todayHighlight: true, language: "zh-CN",startDate:new Date("<%=ViewData["yearidd"]%>",12,01),endDate:new Date("<%=ViewData["yearid"]%>",11,31)}).on('changeDate', function (ev) {});
            $('#endtime').datepicker({autoclose: true, todayHighlight: true, language: "zh-CN",startDate:new Date("<%=ViewData["yearidd"]%>",12,01),endDate:new Date("<%=ViewData["yearid"]%>",11,31)  }).on('changeDate', function (ev) {
            var startime=$("#starttime").val();
            if (startime!=""&&startime!=null) {
            
                $.ajax({
                    url: "<%= ViewData["rootUri"] %>WorkingManage/GetDates",
                    data: { 
                            endtime: $("#endtime").val(),
                            startime:startime 
                    },
                    method: 'post',
                    success: function (data) {
                    if(data=="true")
                    {
                        toastr.options = {
				            "closeButton": false,
				            "debug": true,
				            "positionClass": "toast-bottom-right",
				            "onclick": null,
				            "showDuration": "1",
				            "hideDuration": "1",
				            "timeOut": "3000",
				            "extendedTimeOut": "3000",
				            "showEasing": "swing",
				            "hideEasing": "linear",
				            "showMethod": "fadeIn",
				            "hideMethod": "fadeOut"
				        };
				        toastr["error"]("开始时间不能大于结束时间!", "温馨敬告");
                    }
                    else{
                    $("#dates").val(data);
                    }
                    }
                  });
            }else{
               toastr.options = {
				            "closeButton": false,
				            "debug": true,
				            "positionClass": "toast-bottom-right",
				            "onclick": null,
				            "showDuration": "1",
				            "hideDuration": "1",
				            "timeOut": "3000",
				            "extendedTimeOut": "3000",
				            "showEasing": "swing",
				            "hideEasing": "linear",
				            "showMethod": "fadeIn",
				            "hideMethod": "fadeOut"
				        };
				        toastr["error"]("请选择开始时间!", "温馨敬告");
            }});
        });
        function dutyset2(id) {
            document.getElementById("blname").options.length = 0;
              var bln="<%=ViewData["blname"] %>";
                $.ajax({
                    url: "<%= ViewData["rootUri"] %>WorkingManage/Findblname",
                    data: { "blkind": id },
                    method: 'post',
                    success: function (data) {
                        $("#blname").append("<option value=" + "0" + ">" + "请选择" + "</option>");
                        for (var s in data) {
                            if (data[s].uid == bln) {
                                $("#blname").append("<option value=" + data[s].uid + " selected='selected' >" + data[s].name + "</option>");

                            } else {
                                $("#blname").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                                
                            }
                             
                        }
                        $("#blname").css('width', '150px').select2({allowClear:true, minimumResultsForSearch: -1 });
                        getform($("#blname").val());
                    }
                });
            
        }
        function getform(id) {
            document.getElementById("formss").options.length = 0;
            if(id!=0)
            {
                $.ajax({
                    url: "<%= ViewData["rootUri"] %>WorkingManage/Getform",
                    data: { "blname": id },
                    method: 'post',
                    success: function (data) {
                        $("#formss").append("<option value=" + "0" + ">" + "请选择" + "</option>");
                        for (var s in data) {
                             if (data[s].uid == formid) { 
                                $("#formss").append("<option value=" + data[s].uid + " selected='selected' >" + data[s].name + "</option>");
                                 
                            } else {
                                $("#formss").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                                
                            }
                        }
                        $("#formss").css('width', '150px').select2({allowClear:true, minimumResultsForSearch: -1 });
                    }
                });
            }
            else{
                $("#formss").append("<option value=" + "0" + ">" + "请选择" + "</option>");
                $("#formss").css('width', '150px').select2({allowClear:true, minimumResultsForSearch: -1 });
            }
          
        }
        function dutyset3(daysid) {
            var dates=$("#dates").val();
            if(dates!=""&&dates!=null)
            {
                $.ajax({
                    url: "<%= ViewData["rootUri"] %>WorkingManage/Getdays",
                    data: { 
                            daysid: daysid,
                            dates:dates 
                    },
                    method: 'post',
                    success: function (data) {
                    
                     if(data[0]==1)
                     {
                        document.getElementById("fs").style.color="black";
                         $("#fs").val(data[1]);
                      }
                      if(data[0]==2)
                     {
                       document.getElementById("fs").style.color="red";
                         $("#fs").val(data[1]);
                      }
                    }
                });
             }
             else{
             toastr.options = {
				            "closeButton": false,
				            "debug": true,
				            "positionClass": "toast-bottom-right",
				            "onclick": null,
				            "showDuration": "1",
				            "hideDuration": "1",
				            "timeOut": "3000",
				            "extendedTimeOut": "3000",
				            "showEasing": "swing",
				            "hideEasing": "linear",
				            "showMethod": "fadeIn",
				            "hideMethod": "fadeOut"
				        };
				        toastr["error"]("请先确定延续天数!", "温馨敬告");
             }
            
        }
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
 function  Find(){
          removeclass();
            var PostData = { sort:$("#sort").val(),style1:$("#style1").val(),sheng:$("#sheng").val(),shi:$("#shi").val(), xian:$("#xian").val()};
            jQuery("#myDataTable").setGridParam({url:'<%= ViewData["rootUri"] %>WorkingManage/FintPoint', postData: PostData }).trigger('reloadGrid');
          addclass();
     };
    function RetrieveTable(){
                removeclass();
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
                $("#myDataTable").jqGrid({
                multiselect: true, //复选
                url: '<%= ViewData["rootUri"] %>WorkingManage/FintPoint',
                datatype: "json",
                height: 200,
                autowidth: true, 
                rowNum:99999999,
//                rowList: [5,15, 20, 30],
//                pager: '#pager10',
//                sortname: 'id',
//                viewrecords: true,
//                sortable: false,
//                pagerpos: "center",
//                pgbuttons: true,
                rownumbers: true,
                scrollOffset: 1,
                postData:{
                    sort:sort,
                    style1:style1,
                    sheng:sheng,         
                    shi:shi, 
                    xian:xian         
                },
                viewsortcols: [false, 'vertical', false],
                colNames: [ 'id', '测报点级别', '省/自治区', '盟/市', "旗/县", '测报点类型', '测报点名称'],
                colModel: [
                            { name: 'id', index: 'id', width: 0.1, align: "right" },
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
                            { name: 'name', index: 'name', width: 100, align: "center" }
                ],
                gridComplete: function () {
                  
                    var ids = $("#myDataTable").jqGrid('getDataIDs');
                    if (ids.length == 0) {
                        $("#p").slideDown();
                    }
                    else { $("#p").hide(); }
                    Setselect(ids);
                }
            });
              addclass(); 
            $("#myDataTable").jqGrid('hideCol', "id");
            $("#myDataTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });
            
        }
        
        function  Setselect(ids) {
         for(var j=0;j<ids.length;j++)
            {
                var points="<%=ViewData["point_list"] %>";
                var strs= new Array(); //定义一数组 
                strs=points.split(","); //字符分割 
                for (var i=0;i<strs.length-1 ;i++ ) 
                { 
                    if(ids[j]==strs[i])
                    {
                      $("#myDataTable").setSelection(ids[j]); 
                    }
                } 
            }
        }
         function check1(){
            var ids = $("#myDataTable").jqGrid("getGridParam", "selarrrow");
                var uids = new Array();
                var ss = "";
                for (var i = 0; i < ids.length; i++) {
                    var id = ids[i];
                    uids[i] = $("#myDataTable").jqGrid("getCell", id, "id");
                    ss+= uids[i] + ",";
                }
                if (ss!=null&&ss!="") {
               document.getElementById("text1").style.display="none";
               return true;
              }
              else{
               document.getElementById("text1").style.display="block";
                $('#text1').val("请选择测报点！");
                return false;
              }
      }
      function check2(){
           var blname=$("#blname").val();
                if (blname!=0) {
               document.getElementById("text1").style.display="none";
               return true;
              }
              else{
               document.getElementById("text1").style.display="block";
                $('#text1').val("请选择病虫害名称！");
                return false;
              }
      }
      function check3(){
           var formid=$("#formss").val();
                if (formid!=0) {
               document.getElementById("text1").style.display="none";
               return true;
              }
              else{
               document.getElementById("text1").style.display="block";
                $('#text1').val("请选择病虫害表格！");
                return false;
              }
      }
        jQuery(function ($) {
	        $.validator.messages.required = "必须要填写";
            $('#validation-form').validate({
	            errorElement: 'span',
	            errorClass: 'help-block',
	            //focusInvalid: false,
	            rules: {
	                blkind: {
	                    required: true
	                },
                    blname: {
	                    required: true
	                },
                    formss: {
	                    required: true
	                },
                    starttime: {
	                    required: true
	                },
                    endtime: {
	                    required: true
	                },
                    dates: { 
                        required: true
	                },
                    days: {
	                    required: true
	                },
	                fs: {
                        required: true
	                },
                    stshi: {
                        required: true
                    },                  
                    stfen: {
                        required: true 
                    },
                    enshi: {
                        required: true
                        
                    },
                    enfen: {
                        required: true
                        
                    }
	            },
	            highlight: function (e) {
	                $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
	            },

	            success: function (e) {
	                $(e).closest('.form-group').removeClass('has-error'); //.addClass('has-info');
	                $(e).remove();
	            },

	            errorPlacement: function (error, element) {
	                if (element.is(':checkbox') || element.is(':radio')) {
	                    var controls = element.closest('div[class*="col-"]');
	                    if (controls.find(':checkbox,:radio').length > 1) controls.append(error);
	                    else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
	                }
	                else if (element.is('.select2')) {
	                    error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
	                }
	                else if (element.is('.chosen-select')) {
	                    error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
	                }
	                else error.insertAfter(element.parent());
	            },

	            submitHandler: function (form) {
	                submitform();
	                return false;
	            },
	            invalidHandler: function (form) {
	               
	            }
          });
	      
    });
        function submitform() {
        if(check1()==true&&check2()==true&&check3()==true)
        {
        var ids = $("#myDataTable").jqGrid("getGridParam", "selarrrow");
                var uids = new Array();
                var ss = "";
                for (var i = 0; i < ids.length; i++) {
                    var id = ids[i];
                    uids[i] = $("#myDataTable").jqGrid("getCell", id, "id");
                    ss+= uids[i] + ",";
                }
                var taskid="<%=ViewData["taskid"] %>"
			$.ajax({
				type: "POST",
				url: "<%= ViewData["rootUri"] %>WorkingManage/EditPoint?pointids="+ss+"&taskid="+taskid+"&uid=<%=ViewData["uid"] %>",
				dataType: "json",
				data: $('#validation-form').serialize(),
				success: function (data) {
				    if (data == true) {
                            toas1();
                            setTimeout(function(){
                                window.location.href="<%= ViewData["rootUri"] %>WorkingManage/YearWorkingPlan?planid="+taskid;
                            }, 2500);

				    } else {
				        toas2();
				    }
				},
				error: function (data) {
				    alert("Error: " + data.status);
				    $('.loading-btn').button('reset');
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
