<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>
<%@ Import Namespace="BingChongBackend.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<% var userinfo = (UserInfo)ViewData["userinfo"]; %>

<div class="page-header">
	<div class="">
        <div class="col-xs-2 no-padding-left">
            <h5>添加用户</h5>
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
            <h5 style="margin-left:150px;">基本信息</h5>
            <div class="col-sm-6 ">
                <div class="form-group" style="">
                    <input type="hidden" id="uid" name="uid" value="<% if (userinfo!= null) { %><%= userinfo.uid %><% } else { %>0<% } %>" />


                    <label class="col-sm-6 control-label no-padding-right" for="username" >用户名：</label>
				    <div class="col-md-6">
                       <input name="username" id="usernmae" <% if (userinfo!= null) { %>value="<%=userinfo.username %>" readonly<% }%> />
                    </div>
                </div>
                <div class="form-group" style="">
                    <label class="col-sm-6 control-label no-padding-right" >姓名：</label>
				    <div class="col-md-6">
                       <input name="realname" id="realname"  <% if (userinfo!= null) { %>readonly value="<%=userinfo.realname %>"<% }%> />
                    </div>
                </div>
                <div class="form-group" style="">
                    <label class="col-sm-6 " style="text-align:right;" for="role"> 角色:</label>
                    <div class="col-sm-6 ">
                        <div class="clearfix">
                           <input name="rolename" id="rolename"  <% if (userinfo!= null) { %>readonly value="<%=userinfo.role%>"<% }%> />
                        </div>

                    </div>
                </div>
                 <div class="form-group" style="">
                <label class="col-sm-6 " style="text-align:right;" for="level"> 级别:</label>
                <div class="col-sm-6">
                    <div class="clearfix">
						<select class="select2" id="level" name="level" onchange="changeregion()" <% if (userinfo!= null) { %>disabled<% }%>>
                            <option value="1" <%if (userinfo != null&&(int)userinfo.level==1){%>selected<%} %>>县级</option>
                            <option value="2" <%if (userinfo != null&&(int)userinfo.level==2){%>selected<%} %>>市级</option>
                            <option value="3" <%if (userinfo != null&&(int)userinfo.level==3){%>selected<%} %>>省级</option>
                            <option value="4" <%if (userinfo != null&&(int)userinfo.level==4){%>selected<%} %>>国家级</option>
				        </select>
                    </div>
				</div> 
		       
            </div>
                <div class="form-group" style="">
                    <label class="col-sm-6 control-label no-padding-right" >所属单位：</label>
				    <div class="col-md-6">
                       <input name="company" id="company"  <% if (userinfo!= null) { %>readonly value="<%=userinfo.place %>"<% }%> />
                    </div>
                </div>
                <div class="form-group" style="">
                    <label class="col-sm-6 control-label no-padding-right" >手机号码：</label>
				    <div class="col-md-6">
                       <input name="phone" id="phone"  <% if (userinfo!= null) { %>readonly value="<%=userinfo.phone %>"<% }%> />
                    </div>
                </div>
                <div class="form-group" style="">
                    <label class="col-sm-6 control-label no-padding-right" >职务/职称：</label>
				    <div class="col-md-6">
                       <input name="duty" id="duty"  <% if (userinfo!= null) { %>readonly value="<%=userinfo.job %>"<% }%> />
                    </div>
                </div>
                <br />
                    
                    <h5 class="region" style="margin-left:150px;">所辖区域</h5>
                    <div class="form-group region sheng" style="">
                        <label class="col-sm-6 control-label no-padding-right" for="sheng" >省/自治区：</label>
				        <div class="col-md-6">
                           <select class="select2" name="sheng" id="sheng" onchange="ChangeShiList()" <% if (userinfo!= null) { %>disabled<% }%>>
                             <% if (ViewData["shenglist"] != null)
                                {
                                    foreach (var item in (List<RegionList>)ViewData["shenglist"])
                                {%>
                                    <option value="<%= item.uid %>"  <% if (userinfo!= null && userinfo.sheng_id==item.uid) { %>selected <% }%> ><%= item.name%></option>
                                <%}
                                          
                            }%>
                           </select>
                        </div>
                    </div>
                    <div class="form-group region shi" style="">
                        <label class="col-sm-6 control-label no-padding-right" label="shi">市/盟：</label>
				        <div class="col-md-6">
                            <select class="select2" name="shi" id="shi" onchange="ChangeXianList()" <% if (userinfo!= null) { %>disabled<% }%>>
                             <% if (ViewData["shilist"] != null)
                                {
                                    foreach (var item in (List<RegionList>)ViewData["shilist"])
                                {%>
                                    <option value="<%= item.uid %>" <% if (userinfo!= null && userinfo.shi_id==item.uid) { %>selected <% }%> ><%= item.name%></option>
                                <%}
                                          
                            }%>
                           </select>
                        </div>
                    </div>
                    <div class="form-group region xian" style="">
                        <label class="col-sm-6 control-label no-padding-right" for="xian">旗/县：</label>
				        <div class="col-md-6">
                            <select class="select2" name="xian" id="xian" <% if (userinfo!= null) { %>disabled<% }%>>
                             <% if (ViewData["xianlist"] != null)
                                {
                                    foreach (var item in (List<RegionList>)ViewData["xianlist"])
                                {%>
                                    <option value="<%= item.uid %>" <% if (userinfo!= null && userinfo.xian_id==item.uid) { %>selected <% }%>><%= item.name%></option>
                                <%}
                                          
                            }%>
                           </select>
                        </div>
                    </div>
                </br>
            </div>
            <div class="form-group profile col-sm-6" style="margin-bottom:0px">
                <label class="col-sm-2 control-label no-padding-right" >头像：</label>
			    <div class="col-sm-4">
                    <ul class="list-unstyled profile-nav">
                              <img src="<% if (userinfo!= null&&userinfo.imgurl!=null) { %><%= ViewData["rootUri"] %><%= userinfo.imgurl %><% } else { %><%= ViewData["rootUri"] %>Content/img/profile-img.jpg<% } %>"
                                class="img-responsive" alt="" id="previewimg" width="200px" height="200px" />
                    </ul>
                </div>
            </div>
            <div class="form-group col-sm-12" style="margin-bottom:0px">
                <span class="col-sm-6"></span> 
                <div class="col-sm-1" style="">
                    <a class="btn btn-sm btn-info" type="reset" href="<%= ViewData["rootUri"] %>Settings/UserManage">
						<i class="ace-icon fa fa-undo "></i>
						返回
					</a>
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
        jQuery(function ($) {
            $(".select2").css('width', '150px').select2({ minimumResultsForSearch: -1 });
            changeregion();
        });


        function changeregion() {
            var userlevel=$("#level").val();
            switch(userlevel){
                case "1":
                    $(".region").removeClass("hide");
                    $(".shi").removeClass("hide");
                    $(".xian").removeClass("hide");
                    break;
                case "2":
                    $(".region").removeClass("hide");
                    $(".shi").removeClass("hide");
                    $(".xian").addClass("hide");
                    break;
                case "3":
                    $(".region").removeClass("hide");
                    $(".shi").addClass("hide");
                    $(".xian").addClass("hide");
                    break;
                case "4":
                    $(".region").addClass("hide");
                    break;
            }
        }

        function ChangeShiList(){
            var shengid=$("#sheng").val();
            var rhtml="";
            $.ajax({
                type: "GET",
                url: '<%= ViewData["rootUri"] %>'+ "Station/Findshi",
                data: { "shengid": shengid },
                method: 'post',
                dataType: "json",
                success: function (data) {
                    if (data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            rhtml += "<option value='" + data[i].uid + "'>" + data[i].name + "</option>";
                        }
                        $("#shi").html(rhtml);
                        $("#shi").select2({ allowClear: true, minimumResultsForSearch: -1});

                    }
                    else{
                    $("#shi").html(rhtml);
                    $("#shi").select2({ allowClear: true, minimumResultsForSearch: -1 });

                    }
                },
            });
            ChangeXianList();
        }

        function ChangeXianList(){
            var shiid=$("#shi").val();
            var rhtml="";
            $.ajax({
                type: "GET",
                url:  '<%= ViewData["rootUri"] %>'+ 'Station/Findxian',
                data: { "shiid": shiid },
                method: 'post',
                dataType: "json",
                success: function (data) {
                    if (data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            rhtml += "<option value='" + data[i].uid + "'>" + data[i].name + "</option>";
                        }
                        $("#xian").html(rhtml);
                        $("#xian").select2({ allowClear: true, minimumResultsForSearch: -1});

                    }
                    else{
                    $("#xian").html(rhtml);
                    $("#xian").select2({ allowClear: true, minimumResultsForSearch: -1 });

                    }
                },
            });
        }

          function checkUserName() {
	        var username = $("#username").val();
	        var uid = $("#uid").val();
	        var retval = false;

	        $.ajax({
	            async: false,
	            type: "GET",
	            url: '<%= ViewData["rootUri"] %>' + "Settings/CheckUniqueUsername",
	            dataType: "json",
	            data: {
	                username: username,
	                uid: uid
	            },
	            success: function (data) {
	                if (data == true) {
	                    retval = true;
	                } else {
	                    retval = false;
	                }
	            }
	        });

	        return retval;
	    }
    </script>
</asp:Content>
