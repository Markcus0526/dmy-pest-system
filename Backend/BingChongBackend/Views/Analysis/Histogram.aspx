<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header">
	<div >
        <div class="col-xs-2 no-padding-left">
            <h5>柱状图</h5>
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
                                <label class="col-sm-1 control-label no-padding-right" for="starttime" style="width:100px;">上报日期：</label>
				                <div class="col-sm-2" style="width:200px;">
                                    <div class="clearfix">
						                <div class="input-group col-xs-10 col-sm-5">
							                <input class="input-large date-picker" id="starttime" name="starttime" type="text" data-date-format="yyyy-mm-dd" value="<%= ViewData["today"] %>" style="width:150px;"/>
							                <span class="input-group-addon">
								                <i class="fa fa-calendar bigger-110" for="starttime"></i>
							                </span>
						                </div>
                                    </div>
                                    <input value=""  id="" name="starttime" style="display:none"/ >
				                </div>
                                <div class="col-sm-1" style="float:right;margin-right: 10px;">
					                <a class="btn btn-sm btn-info" id="searchdata" onclick="searchdata()"><i class="fa fa-search"></i> 统计</a>
                                </div>               
                            </div>
                            
                            <!-- 第2行-->
                               <div class="form-group" >
                                <label class="col-sm-1 control-label no-padding-right" for="" style="width:100px;">测报点级别：</label>
				                <div class="col-sm-1" style="width:150px;">
                                    <div class="clearfix">
						                <select class="select2" id="Select4" name="" data-placeholder="请选择"  style="width:150px">
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
                                <label class="col-sm-1 control-label no-padding-right" for=""style="width:100px;" >省/自治区:</label>
                                <div class="col-sm-1" style="width:150px;">
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
				                <div class="col-sm-1" style="width:150px;">
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
                                <div class="col-sm-1" style="width:150px;">
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

                            <div class="form-group" style="margin-bottom:0px" >
                                <label class="col-sm-1 control-label no-padding-right" for="" style="width:100px;" >病虫类型:</label>
                                <div class="col-sm-1" style="width:150px;">
                                    <div class="clearfix">
						                <select class="select2" id="Select7" name="" data-placeholder="请选择"  style="width:130px" onchange="changeselect()">
                                            <option value="1" selected="selected">虫害</option>
                                            <option value="0" >病害</option>                         
				                        </select>
                                    </div>
                                </div>
              	                <label class="col-sm-1 control-label no-padding-right" for="" style="width:100px;">病虫名称：</label>
				                <div class="col-sm-1" style="width:150px;">
                                    <div class="clearfix">
						                <select class="select2" id="Select8" name="" data-placeholder="请选择"  style="width:130px" onchange="changetable()">
                                            <option value="" selected=""></option>
                                            <option value="" ></option>                         
				                        </select>
                                    </div>
				                </div>                  
                                <label class="col-sm-1 control-label no-padding-right" for="" style="width:100px;">表格：</label>
                                <div class="col-sm-1" style="width:150px;">
                                    <div class="clearfix">
						                <select class="select2 " id="Select9" name="" data-placeholder="请选择部门"  style="width:150px" onchange="changefield()">
                                     
				                        </select>
                                    </div>
                                </div>
                                  <label class="col-sm-1 control-label no-padding-right" for="" style="width:100px;">字段：</label>
                                <div class="col-sm-1" style="width:120px;">
                                    <div class="clearfix">
						                <select class="select2 " id="Select10" name="" data-placeholder="请选择部门" onchange=""  style="width:120px">
                                     
				                        </select>
                                    </div>
                                </div>

                            </div>			
                        </form>
                    </div>
				</div>
			</div>
		</div>
    </div>
     <div  class="col-xs-12" style="margin-top:10px">
          <div id="chart" style="width: 92%;height: 500px; margin-left: 55px">
          </div>
        </div>

</div>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/select2.css" />
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/datepicker.css" />
    <link  href="<%= ViewData["rootUri"] %>Content/css/jquery-ui.custom.min.css" rel="Stylesheet"/>
    <link href="<%= ViewData["rootUri"] %>Content/css/ui.jqgrid.css" rel="Stylesheet" />
    <link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/js/jqplot/jquery.jqplot.css"

</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jqplot1250/jquery.min.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jqplot1250/jquery.jqplot.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jqplot1250/plugins/jqplot.pointLabels.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jqplot1250/plugins/jqplot.canvasAxisTickRenderer.min.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jqplot1250/plugins/jqplot.canvasTextRenderer.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jqplot1250/plugins/jqplot.canvasAxisLabelRenderer.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jqplot1250/plugins/jqplot.categoryAxisRenderer.min.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jqplot1250/plugins/jqplot.barRenderer.min.js"></script>

    <script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js" type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/date-time/bootstrap-datepicker.min.js"></script>
	<script src="<%= ViewData["rootUri"] %>Content/js/date-time/locales/bootstrap-datepicker.zh-CN.js"></script>

    
    <script type="text/javascript">
        jQuery(function ($) {
           $(".select2").css('width', '150px').select2({ minimumResultsForSearch: -1 });
           $('.date-picker').datepicker({ autoclose: true, todayHighlight: true, language: "zh-CN" }).on('change', function () { });
             changeselect();
       //  changetypeLevel();
        });
     $(function () {
          var l = <%= ViewData["ul"] %>;
          if (l=="1") {
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
                        changefield();
                    }
            });                                               
           }
             function changefield(){
           document.getElementById("Select10").options.length = 0;
             $.ajax({
                url: "<%= ViewData["rootUri"] %>Analysis/FindDiseaseField",
                dataType: 'json',
                data: {"uid":$("#Select9").val()},
                success: function (ret) {
                     $("#Select10").append("<option value=" + "0" + " selected='selected'>" + "全部" + "</option>");
                        for (var s in ret) {
                        
                            if (ret[s].type=="i"||ret[s].type=="r") {
                                if (s == 0) {
                                    $("#Select10").append("<option value=" + ret[s].uid + ">" + ret[s].name + "</option>");
                                } else {
                                    $("#Select10").append("<option value=" + ret[s].uid + ">" + ret[s].name + "</option>");
                                }
                             }
                             $("#Select10").select2({ allowClear: true,minimumResultsForSearch: -1 });
                        }
                    }
            });
           }
            function dutyset(id) {
            document.getElementById("Select3").options.length = 0;
                $.ajax({
                    url: "<%= ViewData["rootUri"] %>Analysis/Findshi",
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
                    data: { "shiid": id },
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

        function searchdata(){
            $.ajax({
            url: "<%= ViewData["rootUri"] %>Analysis/SearchHistoGram",
            post:'post',
            data:{
            "starttime":$("#starttime").val(),
            "shengid":$("#Select1").val(),
            "shiid":$("#Select3").val(),
            "xianid":$("#Select0").val(),
            "point_level":$("#Select4").val(),
             "disease_kind":$("#Select7").val(),
             "disease_id":$("#Select8").val(),
             "disease_table":$("#Select9").val(),
             "field_id":$("#Select10").val()
             },
            success: function(ret){
               //console.log(ret); 
                 var ts=[]; 
                 var line3=[ ];
                  var x=[ ];
                 for(var i=0;i<ret.length;i++){
                  if (i!=ret.length-1) {
                    var line2= [ ];
                       for(var j=0;j<ret[i].length;j++){
                            if(j!=ret[i].length-1){
                            line2.push(parseInt(ret[i][j].value));
          
                            }
                             if(j==ret[i].length-1){
                            x.push(ret[i][j].value);
          
                            }
                          
                         }
                         line3.push(line2);
                       }
                       for(var j=0;j<ret[i].length;j++){
                        if (i==ret.length-1){
                            if(j!=ret[i].length-1){
                            ts.push(ret[i][j].value);
                                }
                            }
                        }
                       
                }
                if (ts.length<=0) {
                alert("查询不到该条件的测报点信息,柱状图生成失败");
                return ;
                }
                       //console.log(line3); 
                       // console.log(ts);
                     plot = $.jqplot('chart', line3, {
                     title: {
                    text:'统计分析柱状图',  //设置当前图的标题
                        show: true,//设置当前图的标题是否显示
                    },
                    seriesDefaults: {
                        seriesColors: [ "#4bb2c5", "#c5b47f", "#EAA228", "#579575", "#839557", "#958c12", 
                                            "#953579", "#4b5de4", "#d8b83f", "#ff5800", "#0085cc"], 
                        renderer: $.jqplot.BarRenderer, //使用柱状图表示
                        rendererOptions: {
                            barMargin: 5,
                            barDirection:"vertical",
                            barPadding: 8,
                            //barWidth: 3,
                            shadow: false
                        },
                         
                        pointLabels: {  //数据点文本设置,需要页面引用jqplot.pointLabels.js  
                            show: true,  //是否固定显示数值  
                            formatString: "%.2f",  //格式  
                            location: "s"   //位置  
                        }
                    },
                    axesDefaults: {
                           // useSeriesColor: true ,
                        tickRenderer: $.jqplot.CanvasAxisTickRenderer,
                        tickOptions: {
                             fontFamily: 'Tahoma',
                             angle:-50,
                             fontSize: '15px'
                        }

                     },
                     axes: {
                            xaxis: {
                               //label: "测报点", //指定X轴的说明文字
                                 ticks:ts, renderer: $.jqplot.CategoryAxisRenderer 
                                //x轴绘制方式
                            },
                            yaxis: {
                                min:0,
                               autoscale: true,
                                label: "病虫害数据" ,//指定Y轴的说明文字
                               tickOptions: {
                               formatString: '%#d',
                                showGridline: false,
                                 show: true
                                }
                            },
                             x2axis: {
                             renderer:$.jqplot.CategoryAxisRenderer, //指定X轴显示分类
                        }
                    }
                    }).replot();
                }
                });
            }
           
    </script>
</asp:Content>
