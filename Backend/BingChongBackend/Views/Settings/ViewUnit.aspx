<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>
<%@ Import Namespace="BingChongBackend.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<%var unitinfo = (department)ViewData["UnitInfo"]; %>

<div class="page-header">
	<div class="">
        <div class="col-xs-2 no-padding-left">
            <h5>查看直属单位</h5>
        </div>
		<div class="pull-right">
            <p>
            </p>
		</div>
    </div>
</div>
<div class="row">
    <div class="col-md-12" style="margin-top:30px">
        <form class="form-horizontal" role="form" id="validation-form">
            <div class="form-group" style="margin-bottom:0px" style="text-align:center;">
               <table border="1" cellspacing="0" cellpadding="0" width="60%"  style="text-align:center;margin:auto;" >
               	<tr>
               		<td>单位/部门名称：</td>
                    <td>
                        <div class="form-group" style="margin-top:7px">
                            <div class="clearfix">
                                <input type="text" style="width:40%" id="unitname" name="unitname" readonly <% if (unitinfo!= null) { %>value="<%=unitinfo.name%>"<% }%> />
                            </div>
                        </div>
                    </td>
               	</tr>
                <tr>
               		<td>单位/部门级别：</td>
                    <td>
                        <div class="form-group" style="margin-top:7px">
                                <select class="select2" id="regionlevel" name="regionlevel" disabled >
				                    <% if (ViewData["RegionOptions"] != null)
                                    {
                                            foreach (var item in (List<SearchList>)ViewData["RegionOptions"])
                                            {%>
                                                <option value="<%= item.uid %>" <% if (unitinfo!= null&&unitinfo.level==item.uid) { %>selected<% }%> ><%= item.name%></option>
                                            <%}
                                          
                                        }%>
                                </select>
                        </div>
                    </td>
               	</tr>
                <%if(ViewData["regionname"]!=null) {%>

                 <tr class="region">
               		<td>所属地区：</td>
                    <td>
                        <div class="form-group" style="margin-top:7px">
                                <input type="text" value="<%=ViewData["regionname"] %>" readonly style="width:40%"/>
                        </div>
                    </td>
               	</tr>
                                <%} %>

                 <tr>
               		<td>邮政编码：</td>
                    <td>
                        <div class="form-group" style="margin-top:7px">
                            <div class="clearfix">
                                <input type="text" style="width:40%" id="postcode" name="postcode" readonly <% if (unitinfo!= null) { %> value="<%=unitinfo.post_number%>"<% }%>/>
                            </div>
                        </div>
                    </td>
               	</tr>
                 <tr>
               		<td>负责人：</td>
                    <td>
                        <div class="form-group" style="margin-top:7px">
                            <div class="clearfix">
                                <input type="text" style="width:40%" id="chief" name="chief"  readonly <% if (unitinfo!= null) { %>value="<%=unitinfo.chief%>"<% }%>/>
                            </div>
                        </div>
                    </td>
               	</tr>
                <tr>
               		<td>移动电话：</td>
                    <td>
                        <div class="form-group" style="margin-top:7px">
                            <div class="clearfix">
                                <input type="text" style="width:40%" id="Text3" name="cellphone" readonly <% if (unitinfo!= null) { %>value="<%=unitinfo.mobile%>"<% }%>/>
                            </div>
                        </div>
                    </td>
               	</tr>
                <tr>
               		<td>固定电话：</td>
                    <td>
                        <div class="form-group" style="margin-top:7px">
                            <div class="clearfix">
                                <input type="text" style="width:40%" id="telephone" name="telephone" readonly <% if (unitinfo!= null) { %>value="<%=unitinfo.phone%>"<% }%>/>
                            </div>
                        </div>
                    </td>
               	</tr>
                <tr>
               		<td>地址：</td>
                    <td>
                        <div class="form-group" style="margin-top:7px">
                            <div class="clearfix">
                                <input type="text" style="width:40%" id="cellphone" name="addr" readonly <% if (unitinfo!= null) { %>value="<%=unitinfo.address%>"<% }%>/>
                            </div>
                        </div>
                    </td>
               	</tr>
                
                <tr>
               		<td>备注信息：</td>
                    <td>
                        <div class="form-group" style="margin-top:7px">
                            <div class="clearfix">
                                <textarea rols="3" cols="50" id="note" name="note" readonly ><% if (unitinfo!= null) { %><%=unitinfo.note%><% }%></textarea>
                            </div>
                        </div>
                    </td>
               	</tr>
               </table>
            </div>
            </br>
            <input type="hidden" id="uid" name="uid"  value="<% if (ViewData["uid"] != null) { %><%= ViewData["uid"] %><% } else { %>0<% } %>" />
            <div class="form-group" style="margin-bottom:0px">
                <span class="col-md-6"></span> 
                <div class="col-md-1" style="">
		            <a class="btn btn-sm btn-info" id="A2" href="<%= ViewData["rootUri"] %>Settings/UnitSettings"> 返回</a>
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
    <script src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>  
	<script src="<%= ViewData["rootUri"] %>Content/js/jquery.validate.min.js"></script>
    <script type="text/javascript">
	    jQuery(function ($) {
	        $(".select2").css('width', '200px').select2({ minimumResultsForSearch: -1 });
	    });

        function ChangeRegionList() {
                var regionlevel = $("#regionlevel").val();
                if (regionlevel ==4) {
                    $(".region").addClass("hide");
                     var rhtml="<option value='0'></option>";
                    $("#regionid").html(rhtml);
                    $("#regionid").select2({ allowClear: true, minimumResultsForSearch: -1 });
                }
                else{
                    $(".region").removeClass("hide");
                    var rhtml= "";
                    $.ajax({
                        type: "GET",
                        url: '<%= ViewData["rootUri"] %>'+ "Settings/GetRegionListByLevel/?regionlevel=" + regionlevel,
                        dataType: "json",
                        success: function (data) {
                            if (data.length > 0) {
                                for (var i = 0; i < data.length; i++) {
                                    <%if(ViewData["UnitRedionid"]!=null ) 
                                    {%>
                                        var regionid=<%=(Int64)ViewData["UnitRedionid"] %>
                                    <%}%>
                                    if (regionid != null&&regionid==data[i].uid) {
                                        rhtml += "<option value='" + data[i].uid + "' selected>" + data[i].name + "</option>";
                                    }
                                    else{
                                    rhtml += "<option value='" + data[i].uid + "'>" + data[i].name + "</option>";
                                    }
                                }
                                $("#regionid").html(rhtml);
                                $("#regionid").select2({ allowClear: true, minimumResultsForSearch: -1});

                            }
                            else{
                            $("#regionid").html(rhtml);
                            $("#regionid").select2({ allowClear: true, minimumResultsForSearch: -1 });

                            }
                        },
                    });
                }
                
        }
    </script>
</asp:Content>
