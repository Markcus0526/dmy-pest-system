<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>
<%@ Import Namespace="BingChongBackend.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<%var documentinfo = (manual)ViewData["documentinfo"]; %>
<div class="page-header">
	<div class="">
        <div class="col-xs-2 no-padding-left">
            <h5>查看管理办法</h5>
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
            <input type="hidden" id="uid" name="uid"  value="<% if (ViewData["uid"] != null) { %><%= ViewData["uid"] %><% } else { %>0<% } %>" />
            <div class="form-group" style="">
                <label class="col-sm-1 " style="text-align:right;"> 标题:</label>
                <div class="col-sm-2">
                    <div class="clearfix">
                        <input type=""  name="name" readonly <% if (documentinfo!= null) { %>value="<%=documentinfo.title %>"<% }%> style="width:350px"/>
                    </div>
				</div> 
            </div>
            <div class="form-group profile" style="margin-bottom:0px">
                <label class="col-sm-1 control-label no-padding-right" ></label>
				<div class="col-md-10" id="contents">
                        <%-- <% if (documentinfo != null) { %><%= documentinfo.contents %> <%} %> --%>
                </div>
            </div>
            </br>
            </br>
            </br>
            <div class="form-group" style="margin-bottom:0px">
                <span class="col-sm-6"></span>
                <div class="col-sm-1" style="">
		            <a class="btn btn-sm" id="A2" href="<%= ViewData["rootUri"] %>WorkingManage/ManagingDocuments"><i class=""></i> 返回</a>
                </div>   
            </div>
        </form>  

    </div>


</div>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/select2.css" />
	<link rel="stylesheet" type="text/css" href="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.min.css" />
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
	<script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jquery.validate.min.js"></script>
	<script src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>  
    <script type="text/javascript">
      var documentinfo = "<%= ViewData["documentcontents"] %>";
      $("#contents").append(unescape(documentinfo));
    </script>
</asp:Content>
