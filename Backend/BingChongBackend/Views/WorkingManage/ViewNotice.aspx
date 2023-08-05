<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>
<%@ Import Namespace="BingChongBackend.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<%var noticeinfo = (Noitice)ViewData["noticeinfo"]; %>

<div class="page-header">
	<div class="">
        <div class="col-xs-2 no-padding-left">
            <h5>发布通知</h5>
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
            <div class="form-group" style="">
                <input type="hidden" id="Hidden1" name="uid"  value="<% if (ViewData["uid"] != null) { %><%= ViewData["uid"] %><% } else { %>0<% } %>" />
                <label class="col-sm-3 " style="text-align:right;"> <span class="red">*</span>标题:</label>
                <div class="col-sm-2">
                    <div class="clearfix">
                        <input type="" name="title" id="title" readonly <% if (noticeinfo!= null) { %>value="<%=noticeinfo.title %>"<% }%> />
                    </div>
				</div> 
            </div>
            <div class="form-group" style="">
                <label class="col-sm-3 " style="text-align:right;"> <span class="red">*</span>文号:</label>
                <div class="col-sm-2">
                    <div class="clearfix">
                        <input type="" name="serial" id="serial" readonly <% if (noticeinfo!= null) { %>value="<%=noticeinfo.serial %>"<% }%>/>
                    </div>
				</div> 
            </div>
            <div class="form-group" style="">
                <label class="col-sm-3 " style="text-align:right;"> 签发人:</label>
                <div class="col-sm-2">
                    <div class="clearfix">
                        <input type="" id="username" name="username" readonly <% if (noticeinfo!= null) { %>value="<%=noticeinfo.username %>"<% }%>/>
                        
                    </div>
				</div> 
            </div>
            <div class="form-group" style="">
                <label class="col-sm-3 " style="text-align:right;"> 发布日期:</label>
                <div class="col-sm-2">
                    <div class="clearfix">
                        <input type="" id="creattime" name="creattime" readonly <% if (noticeinfo!= null) { %>value="<%=noticeinfo.creattime %>"<% }%> />
                    </div>
				</div> 
            </div>
            <div class="form-group profile" style="margin-bottom:0px">
                <label class="col-sm-3 control-label no-padding-right" >内容：</label>
				<div class="col-md-3">
                        <textarea style="height:260px; width:450px;" readonly name="contents" id="contents"><% if (noticeinfo!= null) { %><%=noticeinfo.contents %><% }%></textarea>
                </div>
            </div>
            </br>
            <div class="form-group" style="margin-bottom:0px">
                <span class="col-sm-6"></span>
               
                <div class="col-sm-1" style="">
		            <a class="btn btn-sm btn-info" id="A2" href="<%= ViewData["rootUri"] %>WorkingManage/Notice"><i class=""></i> 返回</a>
                </div>   
            </div>
        </form>  

    </div>


</div>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/select2.css" />
    <link href="<% =ViewData["rootUri"]%>Content/css/pages/profile.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.min.css" />

</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
	<script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>
	<script src="<%= ViewData["rootUri"] %>Content/js/jquery.validate.min.js"></script>
	<script src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>  


    <script type="text/javascript">
        jQuery(function ($) {
        });
        function getNowFormatDate() {
            var date = new Date();
            var seperator1 = "-";
            var seperator2 = ":";
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
                strDate = "0" + strDate;
            }
            var currentdate = year + seperator1 + month + seperator1 + strDate;
            return currentdate;
        }

    </script>
</asp:Content>
