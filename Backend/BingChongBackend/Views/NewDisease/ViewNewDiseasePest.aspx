<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header">
	<div class="">
        <div class="col-xs-2 no-padding-left">
            <h5>新发病虫害基本信息</h5>
        </div>
		<div class="pull-right">
            <p>
            </p>
		</div>
    </div>
</div>
<div class="row">
    <div class="col-xs-12" style="margin-top:30px">
        <form class="form-horizontal" role="form" id="validation-form">
        <table style=" border:0.5px solid; width:760px; margin-left:160px; font-size:medium">
        <tr style=" border:0.5px solid #858585;height:35px;">
        <td colspan="1" align="center"  style=" border:0.5px solid #858585;font-size:medium">基本信息&nbsp;</td> 
        <td colspan="2" align="center"  style=" border:0.5px solid #858585; width:400px;font-size:medium"> 描述图片&nbsp;</td>
        </tr>
        <tr style=" border:0.5px solid #858585;height:35px;">
        <td align="right"  style=" border:0.5px solid #858585;width:160px">
         病虫害类别:&nbsp;
        </td>
        <td align="left"  style=" border:0.5px solid #858585; width:400px;">
        &nbsp;&nbsp; <%
            for (int i = 0; i < 1; i++)
            {   
        %>
        <%if (ViewBag.info.kind == 1)
          {%>   虫害
        <%} %>
         <%if (ViewBag.info.kind == 0)
          {%>   病害
        <%} %>
        <% }%> 
        </td>
          <td rowspan="8"  style=" border:0.5px solid #858585; width:400px;">
           &nbsp;&nbsp;  <%
            for (int i = 0; i < 1; i++)
            {   
        %>
          <img src="<%= ViewData["rootUri"]%><%= ViewBag.info.photo%>" id ="picture" height="200px" width="186px" style=" margin-left:10px;margin-top:5"/>
          <% }%> 
        </td>
        </tr>
         <tr style=" border:0.5px solid #858585;height:35px;">
        <td align="right"  style=" border:0.5px solid #858585;width:160px;">
         病虫害名称:&nbsp; 
        </td>
        <td align="left"  style=" border:0.5px solid #858585; width:400px;">
          &nbsp;&nbsp;<%
            for (int i = 0; i < 1; i++)
            {   
         %>
            <%=ViewBag.info.name %> 
         <% }%> 
        </td>
        </tr>
            <tr style=" border:0.5px solid #858585;height:35px;">
        <td align="right"  style=" border:0.5px solid #858585;width:160px;">
        &nbsp;&nbsp;发生作物:&nbsp;  
        </td>
        <td align="left"  style=" border:0.5px solid #858585; width:400px;">
          &nbsp;&nbsp;  <%
            for (int i = 0; i < 1; i++)
            {   
         %>
            <%=ViewBag.info.info3 %> 
         <% }%> 
        </td>
        </tr>
            <tr style=" border:0.5px solid #858585;height:35px;">
        <td align="right"  style=" border:0.5px solid #858585;width:160px;">
        &nbsp; &nbsp;东经度:&nbsp;      
        </td>
        <td align="left"  style=" border:0.5px solid #858585; width:400px;">
          &nbsp;&nbsp;  <%
            for (int i = 0; i < 1; i++)
            {   
         %>
            <%=ViewBag.info.longitude %> 
         <% }%> 
        </td>
        </tr>
         <tr style=" border:0.5px solid #858585;height:35px;">
        <td align="right"  style=" border:0.5px solid #858585">
        &nbsp;&nbsp; 北纬度:&nbsp;
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
          &nbsp;&nbsp;  <%
            for (int i = 0; i < 1; i++)
            {   
         %>
            <%=ViewBag.info.latitude %> 
         <% }%>
        </td>
        </tr>
          <tr style=" border:0.5px solid #858585;height:35px;">
        <td align="right"  style=" border:0.5px solid #858585">
       &nbsp; 省/自治区:&nbsp;
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
          &nbsp;&nbsp;  <%
            for (int i = 0; i < 1; i++)
            {   
         %>
            <%=ViewBag.info.sheng_name %> 
         <% }%>
        </td>
        </tr>
         <tr style=" border:0.5px solid #858585;height:35px;">
        <td align="right"  style=" border:0.5px solid #858585">
      &nbsp;&nbsp;&nbsp; 市:&nbsp;&nbsp;
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
          &nbsp;&nbsp;  <%
            for (int i = 0; i < 1; i++)
            {   
         %>
            <%=ViewBag.info.shi_name %> 
         <% }%>
        </td>
        </tr>
          <tr style=" border:0.5px solid #858585;height:35px;">
        <td align="right"  style=" border:0.5px solid #858585">
      &nbsp;&nbsp;  区/县:&nbsp;
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
        &nbsp;&nbsp;    <%
            for (int i = 0; i < 1; i++)
            {   
         %>
            <%Response.Write(ViewBag.info.xian_name);%> 
         <% }%>
        </td>
        </tr>
          <tr style=" border:0.5px solid #858585;height:35px;">
        <td align="right"  style=" border:0.5px solid #858585">
        &nbsp;&nbsp; 乡镇:&nbsp;
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
           &nbsp;&nbsp; <%
            for (int i = 0; i < 1; i++)
            {   
         %>
            <%=ViewBag.info.info1 %> 
         <% }%>
        </td>
                <td align="center"  style=" border:0.5px solid #858585">
         备注信息
        </td>
        </tr>
        <tr style=" border:0.5px solid #858585;height:35px;">
        <td align="right"  style=" border:0.5px solid #858585">
       &nbsp;&nbsp;  村组:&nbsp;
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
         &nbsp;&nbsp;   <%
            for (int i = 0; i < 1; i++)
            {   
         %>
            <% =ViewBag.info.info2 %> 
         <% }%>
        </td>
         <td rowspan="5" align="left"  style=" border:0.5px solid #858585;height:200px">
          <div  style="height:200px;OVERFLOW:scroll" class="divScroll">
           &nbsp;&nbsp;   <%
            for (int i = 0; i < 1; i++)
            {   
         %>
            <%=ViewBag.info.note %> 
         <% }%>
         </div>
        </td>
        </tr>
        <tr style=" border:0.5px solid #858585;height:35px;">
        <td align="right"  style=" border:0.5px solid #858585">
        &nbsp;&nbsp; 发布者:&nbsp;
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
          &nbsp;&nbsp;  <%
            for (int i = 0; i < 1; i++)
            {   
         %>
            <% Response.Write(ViewBag.info.watcher_name); %> 
         <% }%>
        </td>
        </tr>
        <tr style=" border:0.5px solid #858585;height:35px;">
        <td align="right"  style=" border:0.5px solid #858585">
     &nbsp;&nbsp; 审核者:&nbsp;
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
         &nbsp;&nbsp;   <%
            for (int i = 0; i < 1; i++)
            {   
         %>
            <% Response.Write(ViewBag.info.admin_name);%> 
         <% }%>
        </td>
        </tr>
       <tr style=" border:0.5px solid #858585;height:35px;">
        <td align="right"  style=" border:0.5px solid #858585">
       &nbsp; &nbsp;发布时间:&nbsp;
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
         &nbsp;&nbsp;   <%
            for (int i = 0; i < 1; i++)
            {   
         %>
            <%=ViewBag.info.report_date %> 
         <% }%>
        </td>
        </tr>
        <tr style=" border:0.5px solid #858585;height:35px;" >
        <td   style=" border:0.5px solid #858585">
        <div align="right">
        &nbsp; 审核时间:&nbsp;<br />
         </div>
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
           &nbsp;&nbsp;    <%
            for (int i = 0; i < 1; i++)
            {   
         %>
            <%=ViewBag.info.review_date %> 
         <% }%>
        </td>
        </tr>
        </table>
            <%--<div  style=" margin-left:140px; margin-top:10px;">
                <div style=" margin-left:60px;">
		            <a class="btn btn-sm btn-white  btn-default btn-bold" id="sub" onclick="comeback();"style="width:130px; font-size: medium"><i class="fa fa-undo"></i> 返回</a>
                
		       </div>  
            </div> --%>
            </br>
            <div class="form-group" style="margin-bottom:0px">
                <span class="col-sm-9"></span>
                <div class="col-sm-1" style="">
		            <a class="btn btn-sm" id="sub" href="#" onclick="comeback();"><i class=""></i> 返回</a>
                </div>   
            </div>
        </form>
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
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/select2.css" />
     <link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/js/uploadify/uploadify.css" />   
     <style type="text/css">
                td 
            {       font-family:Microsoft YaHei;
                    font-size: 0.9em;  
                }
     </style>
     
    </asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
	<script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>
       <script charset="utf-8" src="<%= ViewData["rootUri"] %>Content/kindeditor/kindeditor.js"></script>
    <script charset="utf-8" src="<%= ViewData["rootUri"] %>Content/kindeditor/lang/zh_CN.js"></script>
    <script charset="utf-8" src="<%= ViewData["rootUri"] %>Content/kindeditor/plugins/code/prettify.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/date-time/bootstrap-datepicker.min.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/jquery-1.11.0.min.js"  type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/jquery.jqGrid.min.js"  type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/i18n/grid.locale-cn.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/bootbox.min.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>       
    <script src="<%= ViewData["rootUri"] %>Content/js/uploadify/jquery.uploadify.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/uploadify/jquery.uploadify.min.js"></script>
    
    <script type="text/javascript">


        function comeback(){
        window.location="<%= ViewData["rootUri"] %>NewDisease/NewDisease"
        }
    </script>
</asp:Content>
