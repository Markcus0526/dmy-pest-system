<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header" id="phead">
	<div>
        <div class="col-xs-2 no-padding-left">
            <h5>动态数据</h5>
        </div>
		<div class="pull-right">
            <p>
            </p>
		</div>
    </div>
</div>
<div class="row">
    <div class="col-xs-12">
        <div class="widget-box" id="cform">
			<div class="widget-body">
				<div class="widget-main">
                    <div class="searchbar">
                        <form class="form-horizontal" role="form" id="validation-form" >
                            <!-- 第一行-->
                            <div class="form-group" >
                                <label class="col-sm-1 control-label no-padding-right" for="starttime">日期选择：</label>
				                <div class="col-sm-1">
                                    <div class="clearfix">
						                <div class="input-group col-xs-10 col-sm-5">
                                        <% string times = DateTime.Now.Year + "-" + DateTime.Now.Month + "-" + DateTime.Now.Day; %>
							                <input class="input-large date-picker" readonly id="starttime" name="starttime" type="text" data-date-format="yyyy-mm-dd" value="<%=times %>" style="width:150px;"/>
							                <span class="input-group-addon">
								                <i class="fa fa-calendar bigger-110"></i>
							                </span>
						                </div>
                                    </div>
                                    <input value=""  id="" name="starttime" style="display:none"/ >
				                </div>
                                <label class="col-sm-1 control-label no-padding-right" style="margin-left:90px" for="">省/自治区:</label>
				                <div class="col-sm-1">
                                    <div class="clearfix">
                     <select style="width:40%" class="select2" id="sheng"  name="sheng" onchange="dutyset(this.value)">
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
                                <label class="col-sm-1 control-label no-padding-right" style="margin-left:50px" for="">盟/市:</label>
				                <div class="col-sm-1">
                                    <div class="clearfix">
						                 
                                        <select style="width:40%" class="select2" id="shi" name="shi" onchange="dutyset1(this.value)" >
                                                <option value="0" >请选择</option>                             
                                            </select>
                                            
                                    </div>
				                </div> 
                                  <label class="col-sm-1 control-label no-padding-right" style="margin-left:50px" for="">旗/县:</label>
				                <div class="col-sm-1">
                                    <div class="clearfix">
						                                 
                                    <select style="width:40%" id="xian" class="select2" name="xian" onchange="dutyset2()">
                                              <option value="0" >请选择</option>                          
                                        </select>
                                    </div>
				                </div>  
                            </div> 
                            <div class="form-group" style="margin-bottom:0px">
                                <label class="col-sm-1 control-label no-padding-right" for="">测报点级别:</label>
				                <div class="col-sm-1">
                                    <div class="clearfix">
						                <select class="select2" id="sort" name="sort" data-placeholder="请选择" onchange="dutyset2()">
                                           <%if (Convert.ToInt16(ViewData["level"]) == 0)
                          {%>
                            <option value="4" selected="selected" >全部</option> 
                            <option value="0">国家级</option>
                            <option value="1" >省级</option>
                            <option value="2">市级</option>
                            <option value="3">县级</option>  
                            <%} %>
                            <%if (Convert.ToInt16(ViewData["level"]) == 1)
                          {%>
                            <option value="4" selected="selected" >全部</option>
                            <option value="0">国家级</option>
                            <option value="3" selected="selected">县级</option>  
                            <%} %>  
                            <%if (Convert.ToInt16(ViewData["level"]) == 2)
                          {%>
                             <option value="4" selected="selected" >全部</option>
                            <option value="0">国家级</option>
                            <option value="2" selected="selected">市级</option>
                            <%} %>  
                            <%if (Convert.ToInt16(ViewData["level"]) == 3)
                          {%>
                            <option value="4" selected="selected" >全部</option>
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
                                <label class="col-sm-1 control-label no-padding-right" style="margin-left:90px" for="">测报点类型:</label>
				                <div class="col-sm-1">
                                    <div class="clearfix">
						                <select style="width:150px" class="select2" onchange="dutyset2()" id="style1" name="style1" >
                                            <option value="2" >全部</option>  
                                            <option value="0">固定测报点</option>
                                            <option value="1" >非固定测报点</option>                         
				                        </select>
                                    </div>
				                </div>  
                                <label class="col-sm-1 control-label no-padding-right" style="margin-left:50px" for="">测报点:</label>
				                <div class="col-sm-1">
                                    <div class="clearfix">
						                <select class="select2" id="point" name="point"  data-placeholder="请选择">
                                            <option value="0" selected="selected">全部</option>
                                                               
				                        </select>
                                    </div>
				                </div>  
                                
                                <div class="col-sm-1" style="margin-left:110px">
                                    <input id="bfull" name="bfull" type="hidden" value="0"/>
						            <a class="btn btn-sm btn-info" id="searchdata" onclick="return GetAll1() "><i class="fa fa-search"></i>&nbsp;搜索</a>
                                </div>
                                <div class="col-sm-1" style="">
                                    <a class="btn btn-sm btn-info" id="A3" onclick="formquan()"><i class="fa fa-expand">&nbsp; 全屏</i></a>
                                </div>   
                            </div>
	                       
                        </form>
                    </div>
                     
				</div>
			</div>
            
		</div>
          
            <div id="allmap" style="height:600px;border:1px solid #CCC;"></div>
            <div   id="tcqj" style="display:none;margin-left:-200px">
                                    <a class="btn btn-sm btn-info" id="A1" onclick="tcquan()"><i class="fa fa-compress"> 退出全屏</i></a>
                                </div> 
    </div>
</div>
 


</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/select2.css" />
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/datepicker.css" />

</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery.ui.touch-punch.min.js"></script> 
    <script src="<%= ViewData["rootUri"] %>Content/js/date-time/bootstrap-datepicker.min.js"></script>
	<script src="<%= ViewData["rootUri"] %>Content/js/date-time/locales/bootstrap-datepicker.zh-CN.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=B9c5b113ec6dcbccb4540870728af498"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/getscript?v=2.0&amp;ak=B9c5b113ec6dcbccb4540870728af498&amp;services=&amp;t=20150305140607"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>
    <script type="text/javascript">
    pagebuju();
    function pagebuju() {
            $(".select2").css('width', '150px').select2({ minimumResultsForSearch: -1 });
            $("#point").css('width', '150px').select2({ minimumResultsForSearch: -1 });
            $('.date-picker').datepicker({ autoclose: true, todayHighlight: true, language: "zh-CN" })
			.on('change', function () { });
        };
    var level="<%= ViewData["level"] %> ";
    var shenga="<%= ViewData["sheng"] %>";
    var shia="<%= ViewData["shi"] %>";
    var xiana="<%= ViewData["xian"] %>";

    var datapointx="";
     var datapointy="";
      var seepoint="<%=ViewData["seepoint"] %>";
      var seewatcher="<%=ViewData["seewatcher"] %>";
      var seewatchertrack="<%=ViewData["seewatchertrack"] %>";
    function formquan() {
      $("#foott").hide();
      $("#nb2").hide();
      $("#cebian").hide();
      $("#breadcrumbs").hide();
      $("#cform").hide();
      $("#tcqj").show();
      $("#phead").hide();
      var heii=document.documentElement.clientHeight;
      var widths=document.documentElement.clientWidth;
      document.getElementById("allmap").style.height=heii+200+"px";
      document.getElementById("allmap").style.marginTop="-40px";
      document.getElementById("allmap").style.marginLeft="-210px";
      document.getElementById("allmap").style.width=widths+100+"px";
      document.getElementById("tcqj").style.marginTop="-"+(heii+200-40)+"px";
    }
    function tcquan() {
      $("#phead").show();
      $("#foott").show();
      $("#nb2").show();
      $("#cebian").show();
      $("#breadcrumbs").show();
      $("#cform").show();
      $("#tcqj").hide();
       document.getElementById("allmap").style.height="600px";
       document.getElementById("allmap").style.marginTop="0px";
       document.getElementById("allmap").style.marginLeft="0px";
       document.getElementById("allmap").style.width="auto";
    }
    
     
    var cbdlist = new Array();
    var empty_cbdlist = new Array();
    var completed_cbdlist = new Array();
    var incompleted_cbdlist = new Array();
    var cbloglist = new Array();
    var pointlist1 = new Array();
    var colornum=new Array();
    var data;
    var  map = new BMap.Map("allmap");							// 创建Map实例
    map.enableScrollWheelZoom();									// 启用滚轮放大缩小
    var myLabel = new BMap.Label("66"); 
    //var cbdicon = new BMap.Icon("<%= ViewData["rootUri"] %>iconset01/web2/gif/64604.gif", new BMap.Size(32,32));
    var cbdicon = new BMap.Icon("<%= ViewData["rootUri"] %>Content/img/place_mark.png", new BMap.Size(50,50));
    var completed_cbdicon = new BMap.Icon("<%= ViewData["rootUri"] %>Content/img/place_completed_mark.png", new BMap.Size(50,50));
    var incompleted_cbdicon = new BMap.Icon("<%= ViewData["rootUri"] %>Content/img/place_un_mark.png", new BMap.Size(50,50));
    //var cbyicon = new BMap.Icon("<%= ViewData["rootUri"] %>down/png/1309/gur-project/gur-project-26.png", new BMap.Size(32,32));
    var cbyicon = new BMap.Icon("<%= ViewData["rootUri"] %>Content/img/map_watcher_mark.png", new BMap.Size(50,50));
    var ffinish = new BMap.Icon("<%= ViewData["rootUri"] %>Content/img/ffinish.png", new BMap.Size(50,50));
    var fkong = new BMap.Icon("<%= ViewData["rootUri"] %>Content/img/fkong.png", new BMap.Size(50,50));
    var funfinish = new BMap.Icon("<%= ViewData["rootUri"] %>Content/img/funfinish.png", new BMap.Size(50,50));
	var imgiconurl = "";
      GetAll();
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
function showMap()
{
    map.clearOverlays();
    $.ajax({

        url: "http://api.map.baidu.com/location/ip?ak=B9c5b113ec6dcbccb4540870728af498&coor=bd09ll",
        type: 'GET',
        crossDomain: true,
        dataType: 'jsonp',
        success: function(data) { 
	    var point = new BMap.Point(data.content.point.x, data.content.point.y);
        			// 创建点坐标
        datapointx=data.content.point.x;
        datapointy=data.content.point.y;
        //map.centerAndZoom(12);									// 初始化地图,设置中心点坐标和地图级别。
        },
        error: function() { alert('Failed!'); }
    });
     if(seewatcher==1)
    { 
	     if(cbloglist.length!=0)
         {
            showCBYLine(cbloglist);
         }  
    }
    if(seepoint==1)
    {
        if (empty_cbdlist.length!=0) {
            for(var i=0; i< empty_cbdlist.length; i++)
	        {
		        showMapPoint(empty_cbdlist[i]);
	        }
        }
    }
    if(seepoint==1)
    {
	    if(completed_cbdlist.length!=0)  
        {
         for(var j=0; j< completed_cbdlist.length; j++)
	        {
		        showCompletedPoint(completed_cbdlist[j]);
	        }
        }
    }
    if(seepoint==1)
    {
	    if(incompleted_cbdlist.length!=0)
        {
             for(var k=0; k< incompleted_cbdlist.length; k++)
	        {
		        showIncompletedPoint(incompleted_cbdlist[k]);
	        }
        } 
    } 
   
    if(pointlist1.length>0)
    {
	    map.setViewport(pointlist1);
        map.setZoom(9); 
    }else{
       var myCity = new BMap.LocalCity();  
        var point = myCity.get(myFun);  
        function myFun(result) {  
            var cityName = result.name; 
            map.centerAndZoom(cityName,12); 
          
        }  
       
    }

}
function showCBYLine(cblist)
{  
    for(var i=0; i<cblist.length; i++)
	{
	        var pointlist = new Array();
	        for(var j=0; j<cblist[i].length; j++)
	        {
                if(j==0)
                { 
                    showCBYPoint(cblist[i][j]);
                }
		        
	        if (pointlist[cblist[i][j].name] == undefined)
		        {
			        pointlist[cblist[i][j].name] = new Array();
		        }
	        pointlist[cblist[i][j].name].push(new BMap.Point(cblist[i][j].longitude,cblist[i][j].latitude));
	        }
            if(seewatchertrack==1)
            {
               
                for(var key in pointlist)
                {
		                var polyline = new BMap.Polyline(pointlist[key], {strokeColor:colornum[i], strokeWeight:6, strokeOpacity:0.5});
		                map.addOverlay(polyline);
	            }
           }
    }
}
//function getRandomColor(num) {
//  var cnum=new Array();
//    for(var j = 0; j < num; j++ )
//    {
//        var letters = '0123456789ABCDEF'.split('');
//        var color = '#';
//        for (var i = 0; i < 6; i++ ) {
//           
//            color += letters[Math.floor(Math.random() * 16)];
//        }
//        cnum[j]=color;
//    }
//   
//    return color;
//}
function showIncompletedPoint(cbdinfo) {
     if(cbdinfo.type==0){
        var point = new BMap.Point(cbdinfo.longitude, cbdinfo.latitude);
        pointlist1.push(point);
        var a=15;
        if(cbdinfo.num<10)
        {
            a=19;
        }
        var myLabel = new BMap.Label(cbdinfo.num,{offset:new BMap.Size(a ,14)});
        myLabel.setStyle({ color:"red",fontSize:"15px",border:"0",backgroundColor : null,textAlign:"center",lineHeight:"0px",lineWhith:"0px",fontWeight:"bold",Whith:"30px"});
       // myLabel.BackColor = System.Drawing.Color.Transparent; 
        //map.addOverlay(myLabel);
        var marker = new BMap.Marker(point,{icon:incompleted_cbdicon});
        marker.setLabel(myLabel);
        map.addOverlay(marker);
    }
    if(cbdinfo.type==1){
        var point = new BMap.Point(cbdinfo.longitude, cbdinfo.latitude);
        pointlist1.push(point);
        var a=15;
        if(cbdinfo.num<10)
        {
            a=19;
        }
        var myLabel = new BMap.Label(cbdinfo.num,{offset:new BMap.Size(a,12)});
        myLabel.setStyle({ color:"red",fontSize:"15px",border:"0",backgroundColor :null,textAlign:"center",lineHeight:"0px",lineWhith:"0px",fontWeight:"bold",Whith:"30px"});
        var marker = new BMap.Marker(point,{icon:funfinish});
        marker.setLabel(myLabel);
        map.addOverlay(marker);
    }
    


	var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>测报点名称: " + cbdinfo.name + "</p>" + 
		"<p style='font-size:14px;'>测报点代码：" + cbdinfo.nickname + "</p>" + 
		"<p style='font-size:14px;'>省 / 自治区：" + cbdinfo.sheng + "</p>" + 
		"<p style='font-size:14px;'>盟 / 市：" + cbdinfo.shi + "</p>" + 
		"<p style='font-size:14px;'>旗 / 县：" + cbdinfo.xian + "</p>" + 
		"<p style='font-size:14px;'>乡 镇：" + cbdinfo.info4 + "</p>" + 
		"<p style='font-size:14px;'>村 组：" + cbdinfo.info5 + "</p>" + 
		"<p style='font-size:14px;'>田块面积：" + cbdinfo.info1 + "</p>" + 
		"<p style='font-size:14px;'>代表面积：" + cbdinfo.info2 + "</p>" + 
		"<p style='font-size:14px;'>种植作物：" + cbdinfo.info3 + "</p>" + 
		"<p style='font-size:14px;'>测报对象：" + cbdinfo.info6 + "</p>"
		//"<p style='font-size:14px;'>备 注：" + cbdinfo[13] + "</p>" 
	);
	marker.addEventListener("click", function(){this.openInfoWindow(infoWindow);});

}
function showMapPoint(cbdinfo) {

    
    if(cbdinfo.type==0){
        var point = new BMap.Point(cbdinfo.longitude, cbdinfo.latitude);
        pointlist1.push(point);
        var marker = new BMap.Marker(point,{icon:cbdicon});
        map.addOverlay(marker);
    }
    if(cbdinfo.type==1){
        var point = new BMap.Point(cbdinfo.longitude, cbdinfo.latitude);
         pointlist1.push(point);
        var marker = new BMap.Marker(point,{icon:fkong});
        map.addOverlay(marker);
    }

	var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>测报点名称: " + cbdinfo.name + "</p>" + 
		"<p style='font-size:14px;'>测报点代码：" + cbdinfo.nickname + "</p>" + 
		"<p style='font-size:14px;'>省 / 自治区：" + cbdinfo.sheng + "</p>" + 
		"<p style='font-size:14px;'>盟 / 市：" + cbdinfo.shi + "</p>" + 
		"<p style='font-size:14px;'>旗 / 县：" + cbdinfo.xian + "</p>" + 
		"<p style='font-size:14px;'>乡 镇：" + cbdinfo.info4 + "</p>" + 
		"<p style='font-size:14px;'>村 组：" + cbdinfo.info5 + "</p>" + 
		"<p style='font-size:14px;'>田块面积：" + cbdinfo.info1 + "</p>" + 
		"<p style='font-size:14px;'>代表面积：" + cbdinfo.info2 + "</p>" + 
		"<p style='font-size:14px;'>种植作物：" + cbdinfo.info3 + "</p>" + 
		"<p style='font-size:14px;'>测报对象：" + cbdinfo.info6 + "</p>"
		//"<p style='font-size:14px;'>备 注：" + cbdinfo[13] + "</p>" 
	);
	marker.addEventListener("click", function(){this.openInfoWindow(infoWindow);});

}

function showCompletedPoint(cbdinfo) {
   if(cbdinfo.type==0){
        var point = new BMap.Point(cbdinfo.longitude, cbdinfo.latitude);
         pointlist1.push(point);
        var marker = new BMap.Marker(point,{icon:completed_cbdicon});
        map.addOverlay(marker);
    }
    if(cbdinfo.type==1){
        var point = new BMap.Point(cbdinfo.longitude, cbdinfo.latitude);
         pointlist1.push(point);
        var marker = new BMap.Marker(point,{icon:ffinish});
        map.addOverlay(marker);
    }
	var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>测报点名称: " + cbdinfo.name + "</p>" + 
		"<p style='font-size:14px;'>测报点代码：" + cbdinfo.nickname + "</p>" + 
		"<p style='font-size:14px;'>省 / 自治区：" + cbdinfo.sheng + "</p>" + 
		"<p style='font-size:14px;'>盟 / 市：" + cbdinfo.shi + "</p>" + 
		"<p style='font-size:14px;'>旗 / 县：" + cbdinfo.xian + "</p>" + 
		"<p style='font-size:14px;'>乡 镇：" + cbdinfo.info4 + "</p>" + 
		"<p style='font-size:14px;'>村 组：" + cbdinfo.info5+ "</p>" + 
		"<p style='font-size:14px;'>田块面积：" + cbdinfo.info1 + "</p>" + 
		"<p style='font-size:14px;'>代表面积：" + cbdinfo.info2 + "</p>" + 
		"<p style='font-size:14px;'>种植作物：" + cbdinfo.info3 + "</p>" + 
		"<p style='font-size:14px;'>测报对象：" + cbdinfo.info6 + "</p>"
		//"<p style='font-size:14px;'>备 注：" + cbdinfo[13] + "</p>" 
	);
	marker.addEventListener("click", function(){this.openInfoWindow(infoWindow);});

}
function showCBYPoint(cbyinfo) {
   
   
        var point = new BMap.Point(cbyinfo.longitude,cbyinfo.latitude);
        pointlist1.push(point);
        var marker = new BMap.Marker(point,{icon:cbyicon});
        map.addOverlay(marker);
        var b=350;
    if(cbyinfo.dept.length>11)
    {
      b=500;
    }
    if(cbyinfo.dept.length>5&&cbyinfo.dept.length<=11)
    {
      b=400;
    }
    var infoWindow;
    if(cbyinfo.imgurl==null||cbyinfo.imgurl=="")
    {
      infoWindow = new BMap.InfoWindow(
        "<div><img src='<%= ViewData["rootUri"] %>Content/img/map_watcher_mark.png' height='200px' width='160px' style='border:1px solid #CCC;margin-top:5px;'/></div>"+
		"<div style='float: right;margin-left:30px;margin-top: -195px;'><p style='font-size:14px;'>用户名：" + cbyinfo.name + "</p>" + 
		"<p style='font-size:14px;'>角色：" + cbyinfo.role + "</p>" + 
		"<p style='font-size:14px;'>所属级别：" + cbyinfo.level + "</p>" + 
		"<p style='font-size:14px;'>所属单位：" + cbyinfo.dept + "</p>" + 
		"<p style='font-size:14px;'>职务/职称：" + cbyinfo.job + "</p>" + 
		"<p style='font-size:14px;'>手机号：" + cbyinfo.phone + "</p>" + 
        "<a class='btn btn-sm btn-info' href='<%= ViewData["rootUri"] %>Map/MapChat'><i class='fa fa-desktop'></i>视频通话 </a></div>",{
            width : b,     // 信息窗口宽度
            height: 240   }
	  );
    }
    else{
        infoWindow = new BMap.InfoWindow(
        "<div><img src='<%=ViewData["rootUri"] %>" + cbyinfo.imgurl + "' height='200px' width='160px' style='border:1px solid #CCC;margin-top:5px;'/></div>"+
		"<div style='float: right;margin-left:30px;margin-top: -195px;'><p style='font-size:14px;'>用户名：" + cbyinfo.name + "</p>" + 
		"<p style='font-size:14px;'>角色：" + cbyinfo.role + "</p>" + 
		"<p style='font-size:14px;'>所属级别：" + cbyinfo.level + "</p>" + 
		"<p style='font-size:14px;'>所属单位：" + cbyinfo.dept + "</p>" + 
		"<p style='font-size:14px;'>职务/职称：" + cbyinfo.job + "</p>" + 
		"<p style='font-size:14px;'>手机号：" + cbyinfo.phone + "</p>" + 
        "<a class='btn btn-sm btn-info' href='<%= ViewData["rootUri"] %>Map/MapChat'><i class='fa fa-desktop'></i>视频通话 </a></div>",{
            width : b,     // 信息窗口宽度
            height: 240   }
	  );
    }
	  
	marker.addEventListener("click", function(){this.openInfoWindow(infoWindow);});

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
            dutyset2();
        }
        function dutyset2() {
                removeclass();
               var sort=$("#sort").val();
                var style1=$("#style1").val();
                var sheng=$("#sheng").val();
                var shi=$("#shi").val(); 
                var xian=$("#xian").val(); 
                if(shi==null)
                {
                    shi="0";
                    xian="0";
                }
                if(xian==null)
                {
                    xian="0";
                }
            if(level==1)
                {
                  shi=shia;
                  xian=xiana;
                }         
            document.getElementById("point").options.length = 0;
            if(sheng!=0)
            {
                $.ajax({
                    url: "<%= ViewData["rootUri"] %>Map/FintPoint",
                    data: {
                            sort:sort,
                            style1:style1,
                            sheng:sheng,         
                            shi:shi, 
                            xian:xian,
                            level:level
                    },
                    method: 'post',
                    success: function (data) {
                                     $("#point").append("<option value=" + "0" + " selected='selected'>" + "全部" + "</option>");
                                for (var s in data) {
                                    if (s==0) {
                                        $("#point").append("<option value=" + data[s].id + ">" + data[s].name + "</option>");
                                       

                                    } else {
                                        $("#point").append("<option value=" + data[s].id + ">" + data[s].name + "</option>");
                                        
                                    }
                                }
                                 $("#point").css('width', '150px').select2({allowClear:true, minimumResultsForSearch: -1 });
                    }
                });
               }
               else{
                $("#point").append("<option value=" + "0" + " selected='selected'>" + "全部" + "</option>");
                 $("#point").css('width', '150px').select2({allowClear:true, minimumResultsForSearch: -1 });
               }
               addclass();
            } 
   
  function GetWatcher() {
  cbloglist.length=0;
   var sheng=$("#sheng").val();
   var shi=$("#shi").val();
    var xian=$("#xian").val();
    if(shi==null||xian==null)
    {
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
    }
      $.ajax({
                    url: "<%= ViewData["rootUri"] %>Map/GetWatcher",
                    data: {
                            sheng:sheng,         
                            shi:shi, 
                            xian:xian,
                            starttime:$("#starttime").val(),
                            level:level
                    },
                    method: 'post',
                    success: function (data) {
                          if(data.length>0)
                          {
                             cbloglist=data; 
                          }          
                           GetFinish() ;  
                    }
                });
  }
  function GetFinish() {
  completed_cbdlist.length=0;
   var sheng=$("#sheng").val();
   var shi=$("#shi").val();
    var xian=$("#xian").val();
    if(shi==null||xian==null)
    {
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
    }
      $.ajax({
                    url: "<%= ViewData["rootUri"] %>Map/GetFinish",
                    data: {
                            sort:$("#sort").val(),
                            style1:$("#style1").val(),
                            point1:$("#point").val(),
                            sheng:sheng,         
                            shi:shi, 
                            xian:xian,
                            starttime:$("#starttime").val(),
                            level:level
                    },
                    method: 'post',
                    success: function (data) {
                           if(data.length>0)
                          {
                             completed_cbdlist=data; 
                          }               
                          GetUnFinish();
                          GetColor();
                    }
                });
  }
  function GetColor() {
  colornum.length=0;
   
      $.ajax({
                    url: "<%= ViewData["rootUri"] %>Map/Getcolor",
                    data: {
                            num:cbloglist.length
                    },
                    method: 'post',
                    success: function (data) {
                           if(data.length>0)
                          {
                             colornum=data; 
                          }                  
                    }
                });
  }
  function GetUnFinish() {
  incompleted_cbdlist.length=0;
   var sheng=$("#sheng").val();
   var shi=$("#shi").val();
    var xian=$("#xian").val();
    if(shi==null||xian==null)
    {
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
    }
      $.ajax({
                    url: "<%= ViewData["rootUri"] %>Map/GetUnFinish",
                    data: {
                            sort:$("#sort").val(),
                            style1:$("#style1").val(),
                            point1:$("#point").val(),
                            sheng:sheng,         
                            shi:shi, 
                            xian:xian,
                            starttime:$("#starttime").val(),
                            level:level
                    },
                    method: 'post',
                    success: function (data) {
                            if(data.length>0)
                          {
                             incompleted_cbdlist=data; 
                          }            
                          GetEmpty();    
                    }
                });
  }
  function GetEmpty() {
  empty_cbdlist.length=0;
   var sheng=$("#sheng").val();
   var shi=$("#shi").val();
    var xian=$("#xian").val();
    if(shi==null||xian==null)
    {
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
    }
      $.ajax({
                    url: "<%= ViewData["rootUri"] %>Map/GetEmpty",
                    data: {
                            sort:$("#sort").val(),
                            style1:$("#style1").val(),
                            point1:$("#point").val(),
                            sheng:sheng,         
                            shi:shi, 
                            xian:xian,
                            starttime:$("#starttime").val(),
                            level:level
                    },
                    method: 'post',
                    success: function (data) {
                                    
                           if(data.length>0)
                          {
                             empty_cbdlist=data; 
                          }  
                          
                           showMap();    
                    }
                });
  }
  function GetAll() {
 
             
    GetUserInfo ();
   removeclass();
     pointlist1.length=0;
      GetWatcher();
    addclass();    
      
  }
  function GetAll1() {
   removeclass();
   var sheng=$("#sheng").val();
   var shi=$("#shi").val();
    var xian=$("#xian").val();
     pointlist1.length=0;
      GetWatcher();
    addclass();    
      
  }
    </script>
</asp:Content>
