<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header">
	<div >
        <div class="col-xs-2 no-padding-left">
            <h5>汇总统计</h5>
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
                            
                            <!-- 第1行-->
                            <div class="form-group" >  
                                <label class="col-sm-1 control-label no-padding-right" for="starttime" style="width:100px;">开始时间：</label>
				                <div class="col-sm-2" style="width:200px;">
                                    <div class="clearfix">
						                <div class="input-group col-xs-10 col-sm-5">
							                <input class="input-large date-picker" id="starttime" name="starttime" type="text"  value="<%= ViewData["cumonth"] %>" data-date-format="yyyy-mm-dd"  style="width:150px;"/>
							                <span class="input-group-addon">
								                <i class="fa fa-calendar bigger-110" for="starttime"></i>
							                </span>
						                </div>
                                    </div>
                                    <input value=""  id="" name="starttime" style="display:none"/ >
				                </div>
              	                <label class="col-sm-1 control-label no-padding-right" for="endtime" style="width:60px;">至：</label>
				                 <div class="col-sm-2" style="width:180px;">
                                    <div class="clearfix">
						                <div class="input-group col-xs-10 col-sm-5">
							                <input class="input-large date-picker" id="endtime" name="endtime" type="text" value="<%= ViewData["today"] %>" data-date-format="yyyy-mm-dd" style="width:150px;"/>
							                <span class="input-group-addon">
								                <i class="fa fa-calendar bigger-110"  for="endtime"></i>
							                </span>
						                </div>
                                    </div>
                                    <input value=""  id="" name="endtime" style="display:none"/ >
				                </div>                
                            </div>
                            
                            <!-- 第2行-->
                            <div class="form-group" >
                                <label class="col-sm-1 control-label no-padding-right" for=""style="width:100px;" >省/自治区:</label>
                                <div class="col-sm-2" style="width:150px;">
                                    <div class="clearfix">
						                
                                          <% if (ViewData["ul"] == "4" || ViewData["ul"] == "0")
                                           { %>
                                        <select class="select2" id="Select1" name="Select1" data-placeholder="请选择" onchange="dutyset(this.value)">
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
                                            <select class="select2" id="Select1" name="Select1" data-placeholder="请选择" onchange="dutyset(this.value)"
                                                >
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
              	                <label class="col-sm-1 control-label no-padding-right" for="" style="width:100px;">市：</label>
				                <div class="col-sm-2" style="width:150px;">
                                    <div class="clearfix">
                                              <% if (ViewData["ul"] == "4" || ViewData["ul"] == "3" || ViewData["ul"] == "0")
                                           { %>
                                        <select class="select2" id="Select3" name="" data-placeholder="请选择" onchange="dutyset1(this.value)"
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
                                            <select class="select2" id="Select3" name="" data-placeholder="请选择" onchange="dutyset1(this.value)"
                                                >
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
                                <label class="col-sm-1 control-label no-padding-right" for="" style="width:100px;">区/县：</label>
                                <div class="col-sm-2" style="width:150px;">
                                    <div class="clearfix">
                                          <% if (ViewData["ul"] == "2" || ViewData["ul"] == "3" || ViewData["ul"] == "4" || ViewData["ul"] == "0")
                                           { %>
                                        <select class="select2"  style="width:180px" id="Select0" name="Select0" data-placeholder="请选择" onchange="changetypeLevel(this.value)"
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
                                            <select class="select2" id="Select0"  style="width:180px" name="Select0" data-placeholder="请选择" onchange="changetypeLevel(this.value)"
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
                                <label class="col-sm-1 control-label no-padding-right" for="" style="width:100px;">测报点级别：</label>
				                <div class="col-sm-2" style="width:150px;">
                                    <div class="clearfix">
						                <select class="select2" id="Select4" name="" data-placeholder="请选择" onchange="changetypeLevel(this.value)" >
                                           <%if (Convert.ToInt16(ViewData["level"]) == 0)
                          {%>
                            <option value="0" <%if (Convert.ToInt16(ViewData["sort"]) == 0){%>selected="selected" <%} %>>国家级</option>
                            <option value="1" <%if (Convert.ToInt16(ViewData["sort"]) == 1){%>selected="selected" <%} %>>省级</option>
                            <option value="2" <%if (Convert.ToInt16(ViewData["sort"]) == 2){%>selected="selected" <%} %>>市级</option>
                            <option value="3" <%if (Convert.ToInt16(ViewData["sort"]) == 3){%>selected="selected" <%} %>>县级</option> 
                            <%} %>
                            <%if (Convert.ToInt16(ViewData["level"]) == 1)
                          {%>
                            <option value="0" <%if (Convert.ToInt16(ViewData["sort"]) == 0){%>selected="selected" <%} %>>国家级</option>
                            <option value="3" <%if (Convert.ToInt16(ViewData["sort"]) == 3){%>selected="selected" <%} %>>县级</option>
                            <%} %>  
                            <%if (Convert.ToInt16(ViewData["level"]) == 2)
                          {%>
                            <option value="0" <%if (Convert.ToInt16(ViewData["sort"]) == 0){%>selected="selected" <%} %>>国家级</option>
                             <option value="2" <%if (Convert.ToInt16(ViewData["sort"]) == 2){%>selected="selected" <%} %>>市级</option>
                            <%} %>  
                            <%if (Convert.ToInt16(ViewData["level"]) == 3)
                          {%>
                            <option value="0" <%if (Convert.ToInt16(ViewData["sort"]) == 0){%>selected="selected" <%} %>>国家级</option>
                            <option value="1" <%if (Convert.ToInt16(ViewData["sort"]) == 1){%>selected="selected" <%} %>>省级</option>
                            <%} %>   
                            <%if (Convert.ToInt16(ViewData["level"]) == 4)
                          {%>
                            <option value="0" <%if (Convert.ToInt16(ViewData["sort"]) == 0){%>selected="selected" <%} %>>国家级</option>
                            <%} %>             
				                        </select>
                                    </div>
				                </div>  
                                <label class="col-sm-1 control-label no-padding-right" for="" style="width:100px;">类型：</label>
				                <div class="col-sm-2" style="width:150px;">
                                    <div class="clearfix">
						                <select class="select2" id="Select5" name="" data-placeholder="请选择"  onchange="changetypeLevel(this.value)">
                                            <option value="0" selected="">固定测报点</option>
                                            <option value="1" >非固定测报点</option>                         
				                        </select>
                                    </div>
				                </div>  
                                <label class="col-sm-1 control-label no-padding-right" for="" style="width:100px;">测报点名称：</label>
				                <div class="col-sm-2" style="width:150px;">
                                    <div class="clearfix">
						                <select class="select2" id="Select6" name=""  disabled="disabled" data-placeholder="请选择"  >
                                            <option value="0" selected="">全部</option>                       
				                        </select>
                                    </div>
				                </div>  
                            </div>

                            <div class="form-group" style="margin-bottom:0px" >
                                <label class="col-sm-1 control-label no-padding-right" for="" style="width:100px;" >病虫类型:</label>
                                <div class="col-sm-2" style="width:150px;">
                                    <div class="clearfix">
						                <select class="select2" id="Select7" name="" data-placeholder="请选择"   onchange="changeselect()">
                                            <option value="1" selected="selected">虫害</option>
                                            <option value="0" >病害</option>                         
				                        </select>
                                    </div>
                                </div>
              	                <label class="col-sm-1 control-label no-padding-right" for="" disabled="disabled" style="width:100px;">病虫名称：</label>
				                <div class="col-sm-2" style="width:150px;">
                                    <div class="clearfix">
						                <select class="select2" id="Select8" name="" data-placeholder="请选择"   onchange="changetable()">
                                            <option value="" selected="" ></option>
                                            <option value="" ></option>                         
				                        </select>
                                    </div>
				                </div>                  
                                <label class="col-sm-1 control-label no-padding-right" for="" style="width:100px;">表格：</label>
                                <div class="col-sm-2" style="width:150px;">
                                    <div class="clearfix">
						                <select class="select2 " id="Select9" name="" data-placeholder="请选择部门" onchange=""  >
                                     
				                        </select>
                                    </div>
                                </div>
                                <div class="col-sm-1" style="float:right;">
  				                    <a class=" btn btn-sm btn-info" id="searchdata" href="javascript:void(0)" onclick="searchdata()"><i class="fa fa-search"></i> 统计</a>
                                </div> 
                            </div>			
                        </form>
                    </div>
				</div>
			</div>
		</div>
        <div  style="margin-top:10px;" >
          <%if (ViewData["role"].ToString().Contains("ExportStatistics"))
            {%>
            <div>
               <a type="button" id="output"  class="btn btn-sm btn-info"  style=" display:none; height:32px;width:95px;margin-top:10px; float:right;"><i class="ace-icon fa fa-share-square-o"></i>导出EXCEL</a>
               <a type="button" id="outputfield"  class="btn btn-sm btn-info"  style=" display:none; height:32px;width:125px;margin-top:10px; float:right;margin-right:20px"><i class="ace-icon fa fa-share-square-o"></i>导出字段EXCEL</a>
           </br>
           </br>
            </div>
          <%} %>
			<table id="tbldata1" style="display:none;margin-top:20px"  class="table table-striped table-bordered table-hover">
            
			</table>
        </div>
    </div>
</div>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/select2.css" />
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/datepicker.css" />

</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
	<script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>
     <script src="<%= ViewData["rootUri"] %>Content/js/date-time/bootstrap-datepicker.min.js"></script>
	<script src="<%= ViewData["rootUri"] %>Content/js/date-time/locales/bootstrap-datepicker.zh-CN.js"></script>
    <script type="text/javascript">
        jQuery(function ($) {
       
             changeselect();
            $('.date-picker').datepicker({ autoclose: true, todayHighlight: true, language: "zh-CN" })
			.on('change', function () { });
                 $(".select2").css('width', '150px').select2({ minimumResultsForSearch: -1 });
                 changetypeLevel();
        });

         $(function () {
  var l = <%= ViewData["ul"] %>;
  if (l=="1") {
  document.getElementById("Select6").disabled=false;
  }
    if (l=="2") {
      dutyset1($("#Select3").val());
      document.getElementById("Select0").disabled=false;
  }
     if (l=="3") {
        dutyset($("#Select1").val());
       document.getElementById("Select3").disabled=false;  
  }
      if (l=="4") {
       document.getElementById("Select1").disabled=false;
  }
        if (l=="0") {
       document.getElementById("Select1").disabled=false;

  }
        });
          function changeselect(){
           document.getElementById("Select8").options.length = 0;
             $.ajax({
                url: "<%= ViewData["rootUri"] %>DiseasePest/FindDiseasePestName",
                dataType: 'json',
                data: {"type":$("#Select7").val()},
                success: function (ret) {
                       
                        for (var s in ret) {
                            if (s == 0) {
                                $("#Select8").append("<option value=" + ret[s].uid + " selected='selected'>" + ret[s].name + "</option>");
                            } else {
                                $("#Select8").append("<option value=" + ret[s].uid + ">" + ret[s].name + "</option>");
                            }
                        }
                        $("#Select8").select2({ allowClear: true,minimumResultsForSearch: -1 });
                         changetable();
                    }
            });
           }
          function changetable(){
           document.getElementById("Select9").options.length = 0;
             $.ajax({
                url: "<%= ViewData["rootUri"] %>DiseasePest/FindDiseasePestTable",
                dataType: 'json',
                data: {"uid":$("#Select8").val()},
                success: function (ret) {
                    
                        for (var s in ret) {
                            if (s == 0) {
                                $("#Select9").append("<option value=" + ret[s].uid + " selected='selected'>" + ret[s].name + "</option>");
                            } else {
                                $("#Select9").append("<option value=" + ret[s].uid + ">" + ret[s].name + "</option>");
                            }
                        }
                        $("#Select9").select2({ allowClear: true,minimumResultsForSearch: -1 });
                    }
            });
           }
            function dutyset(id) {
            document.getElementById("Select3").options.length = 0;
                $.ajax({
                    url: "<%= ViewData["rootUri"] %>Station/Findshi",
                    data: { "shengid": id },
                    method: 'post',
                    success: function (data) {
                          if (id>0) {
                              document.getElementById("Select3").disabled=false; 
                           $("#Select3").append("<option value=" + "0" + " selected='selected'>" + "全部" + "</option>");
                        for (var s in data) {
                            if (s == 0) {
                                $("#Select3").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                            } else {
                                $("#Select3").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                            }
                        }
                         $("#Select3").select2({ allowClear: true,minimumResultsForSearch: -1 });
                    }
                       
                        else{
                          document.getElementById("Select3").disabled=true;
                           $("#Select3").append("<option value=" + "0" + " selected='selected'>" + "全部" + "</option>");
                        for (var s in data) {
                            if (s == 0) {
                                $("#Select3").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                            } else {
                                $("#Select3").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                            }
                        }
                         $("#Select3").select2({ allowClear: true,minimumResultsForSearch: -1 });
                        }
                        dutyset1(0);
                    }
                });
        }
        function dutyset1(id) {
            document.getElementById("Select0").options.length = 0;
                $.ajax({
                    url: "<%= ViewData["rootUri"] %>Analysis/Findxian",
                    data: { "shengid":$("#Select1").val(),"shiid": id },
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
                        changetypeLevel();
                    }
                });
        }
        function  changetypeLevel(){
                document.getElementById("Select6").options.length = 0;
                $.ajax({
                    url: "<%= ViewData["rootUri"] %>Analysis/FindPoint",
                    data: { "type": $("#Select5").val(),'level':$("#Select4").val(),"shengid": $("#Select1").val(),'shiid':$("#Select3").val(),"xianid": $("#Select0").val() },
                    method: 'post',
                    success: function (data) {
                    if ($("#Select0").val()>0) {
                    document.getElementById("Select6").disabled=false; 
                     $("#Select6").append("<option value=" + "0" + " selected='selected'>" + "请选择" + "</option>");
                        for (var s in data) {
                            if (s == 0) {
                                $("#Select6").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                            } else {
                                $("#Select6").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                            }
                        }
                        $("#Select6").select2({ allowClear: true,minimumResultsForSearch: -1 });
                    }else{
                    document.getElementById("Select6").disabled=true;
                    $("#Select6").append("<option value=" + "0" + " selected='selected'>" + "全部" + "</option>");
                        for (var s in data) {
                            if (s == 0) {
                                $("#Select6").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                            } else {
                                $("#Select6").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                            }
                        }
                        $("#Select6").select2({ allowClear: true,minimumResultsForSearch: -1 });
                    }
                       
                    }
                });
        }

           var starttime="";
           var endtime="";
           var shengid="";
           var shiid="";
           var xianid="";
           var point_level="";
           var point_kind="";
           var point_id="";
           var disease_kind="";
           var disease_id="";
           var disease_table="";
        function searchdata(){
            starttime=$("#starttime").val(),
            endtime=$("#endtime").val(),
            shengid=$("#Select1").val(),
            shiid=$("#Select3").val(),
            xianid=$("#Select0").val(),
            point_level=$("#Select4").val(),
            point_kind=$("#Select5").val(),
            point_id=$("#Select6").val(),
            disease_kind=$("#Select7").val(),
            disease_id=$("#Select8").val(),
            disease_table=$("#Select9").val()
            document.getElementById("tbldata1").style.display="block";
            $.ajax({
            url: "<%= ViewData["rootUri"] %>Analysis/SearchSummaryStatistics",
            post:'post',
            data:{
            "starttime":starttime,
            "endtime":endtime,
            "shengid":shengid,
            "shiid":shiid,
            "xianid":xianid,
            "point_level":point_level,
             "point_kind":point_kind,
             "point_id":point_id,
             "disease_kind":disease_kind,
             "disease_id":disease_id,
             "disease_table":disease_table
             },
            success: function(ret){
                $("#tbldata1").html("");
               $("#tbldata1").html(ret);
            document.getElementById("output").style.display="block";
              document.getElementById("outputfield").style.display="block";
            }
            });
        }
        $(document).ready(function () {
         $("#Select1").select2({ allowClear: true,minimumResultsForSearch: -1 });
            $("#output").click(function () {
            var Efilename="统计分析";
             location.href= "<%= ViewData["rootUri"] %>Analysis/GetPgWord?Dfilename="+Efilename+"&starttime="+starttime
             +"&endtime="+endtime+"&shengid="+shengid+"&shiid="+shiid+"&xianid="+xianid+"&point_level="+point_level+
             "&point_kind="+point_kind+"&point_id="+point_id+"&disease_kind="+disease_kind+"&disease_id="+disease_id+"&disease_table="+disease_table;
        });
             $("#outputfield").click(function () {
            var Efilename="统计分析";
             location.href= "<%= ViewData["rootUri"] %>Analysis/GetFieldWord?Dfilename="+Efilename+"&starttime="+starttime
             +"&endtime="+endtime+"&shengid="+shengid+"&shiid="+shiid+"&xianid="+xianid+"&point_level="+point_level+
             "&point_kind="+point_kind+"&point_id="+point_id+"&disease_kind="+disease_kind+"&disease_id="+disease_id+"&disease_table="+disease_table;
        });
    });
    </script>
</asp:Content>
