<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>
<%@ Import Namespace="BingChongBackend.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
    <% var userinfo = (UserInfo)ViewData["userinfo"]; %>

<div class="page-header">
	<div class="">
        <div class="col-xs-2 no-padding-left">
            
            <% if (userinfo!= null) { %><h5>修改用户</h5><% }
                                                   else{
                   %> <h5>添加用户</h5><%
               }
                                                   %>
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
                <label class="col-sm-3 " style="text-align:right;" for="level"> 级别:</label>
                <div class="col-sm-2">
                    <div class="clearfix">
						<select class="select2" id="level" name="level" onchange="changeregion();changerolelist();" <% if (userinfo!= null) { %>disabled<% }%>>
                              <% if (ViewData["level"] != null)
                             {
                                 foreach (var item in (List<SearchList>)ViewData["level"])
                                    {%>
                                        <option value="<%= item.uid %>" <%if (userinfo != null &&(int)userinfo.level== item.uid){%>selected<%} %> ><%= item.name%></option>
                                    <%}
                                          
                                }%>
				        </select>
                    </div>
				</div> 

                <div <%if ( ViewData["role"] != null && !((String)ViewData["role"]).Contains("ManageUserRight"))
                  {%> class="hide"<%} %> >
		            <label class="col-sm-1 " style="text-align:right;" for="role"> 角色:</label>
                    <div class="col-sm-2">
                        <div class="clearfix">
						    <select class="select2" id="role" name="role" data-placeholder="请选择">
				            </select>
                        </div>
				    </div> 
                </div>

                
            </div>
            </br>
            <h5 style="margin-left:150px;">基本信息</h5>
            <div class="col-sm-6 ">
                <div class="form-group" style="">
                    <input type="hidden" id="uid" name="uid" value="<% if (userinfo!= null) { %><%= userinfo.uid %><% } else { %>0<% } %>" />


                    <label class="col-sm-6 control-label no-padding-right" for="username" >用户名：</label>
				    <div class="col-md-6">
                       <input name="username" id="username" <% if (userinfo!= null) { %>value="<%=userinfo.username %>" readonly<% }%> style="margin-top:2px"/>
                    </div>
                </div>
                <div class="form-group" style="">
                    <label class="col-sm-6 control-label no-padding-right" for="userpwd">密码：</label>
				    <div class="col-md-6" >
                       <input name="userpwd" type="password" id="userpwd"  style="padding:0px 0px 0px; width:135px; border: 2px inset;margin-top:2px"/>
                    </div>
                </div>
                <div class="form-group" style="">
                    <label class="col-sm-6 control-label no-padding-right" >确认密码：</label>
				    <div class="col-md-6">
                       <input name="confirmpwd" type="password" id="confirmpwd" style="padding:0px 0px 0px; width:135px; border: 2px inset;margin-top:2px ;"/>
                    </div>
                </div>
                <div class="form-group" style="">
                    <label class="col-sm-6 control-label no-padding-right" >姓名：</label>
				    <div class="col-md-6">
                       <input name="realname" id="realname"  <% if (userinfo!= null) { %>value="<%=userinfo.realname %>"<% }%>  style="margin-top:2px"/>
                    </div>
                </div>
                <div class="form-group" style="">
                    <label class="col-sm-6 control-label no-padding-right" >所属单位：</label>
				    <div class="col-md-6">
                       <input name="company" id="company"  <% if (userinfo!= null) { %>value="<%=userinfo.place %>"<% }%> style="margin-top:2px"/>
                    </div>
                </div>
                <div class="form-group" style="">
                    <label class="col-sm-6 control-label no-padding-right" >手机号码：</label>
				    <div class="col-md-6">
                       <input name="phone" id="phone"  <% if (userinfo!= null) { %>value="<%=userinfo.phone %>"<% }%> style="margin-top:2px"/>
                    </div>
                </div>
                <div class="form-group" style="">
                    <label class="col-sm-6 control-label no-padding-right" >职务/职称：</label>
				    <div class="col-md-6">
                       <input name="duty" id="duty"  <% if (userinfo!= null) { %>value="<%=userinfo.job %>"<% }%> style="margin-top:2px"/>
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
                        <li>
                            <img src="<% if (userinfo!= null&& userinfo.imgurl!=null) { %><%= ViewData["rootUri"] %><%= userinfo.imgurl %><% } else { %><%= ViewData["rootUri"] %>Content/img/profile-img.jpg<% } %>"
                                class="img-responsive" alt="" id="previewimg" width="200px" height="200px" />
                            <input type="hidden" id="imgurl" name="imgurl" value="" />
                            <a href="#" class="profile-edit" id="upload_btn">修改</a>
                            <img src="<%= ViewData["rootUri"] %>Content/img/load.gif" style="float:right;display:none" id="loadingimg" />
                            
                             </li>
                    </ul>

                </div>
            </div>
            <div class="form-group col-sm-12" style="margin-bottom:0px">
                <span class="col-sm-5"></span>
                <div class="col-sm-1" style="">
                    <button class="btn btn-sm btn-info" type="submit">
						<i class="ace-icon fa fa-check "></i>
						确认
					</button>
                </div>   
                <div class="col-sm-1" style="">
                    <a class="btn btn-sm " type="reset" href="<%= ViewData["rootUri"] %>Settings/UserManage">
						<i class="ace-icon fa fa-undo "></i>
						取消
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
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/jquery-ui.min.css" />
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/pages/profile.css" />
    

</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
    <script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>
	<script src="<%= ViewData["rootUri"] %>Content/js/jquery.validate.min.js"></script>
	<script src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>  
    <script src="<%= ViewData["rootUri"] %>Content/js/ajaxupload.js"></script>


    <script type="text/javascript">
        function redirectToListPage(status) {
            if (status.indexOf("error") != -1) {
                //$('.loading-btn').button('reset');
            } else {
                window.location = '<%=ViewData["rootUri"]%>' + "Settings/UserManage";
            }
        }
        jQuery(function ($) {
            $(".select2").css('width', '150px').select2({ minimumResultsForSearch: -1 });


            new AjaxUpload('#upload_btn', {
		        action: '<%=ViewData["rootUri"] %>' + 'Upload/UploadImage',
                onSubmit : function(file , ext){
                    $('#loadingimg').show();
                    if (! (ext && /^(JPG|PNG|JPEG|GIF)$/.test( ext.toUpperCase() ))){
                        // extensiones permitidas
		                alert('错误: 只能上传图片', '');
                        $('#loadingimg').hide();
                        return false;
                    } 
                },
                onComplete: function(file, response){
                    $('#loadingimg').hide();
//                    var pic_data = "<div style='float:left; padding:5px;'>";
//                    pic_data += "<img src='" + rootUri + "Content/uploads/temp/" + response + "' height='180px' onmouseover='over_img(this)' onmouseout='out_img(this)' >";
//                    pic_data +=  "<a href='javascript:(0);'><img src='" + rootUri + "content/img/imgdel.png' class='close_btn' onclick='removeMe(this, \""+response+"\")' onmouseover='over_close(this)' style='visibility:hidden; margin-top:-100px; margin-left:-10px; width:20px; height:20px;' onmouseout='out_close(this)'></a>";
//                    pic_data += "</div>";
                    var pic_data='<%=ViewData["rootUri"] %>'+"Content/uploads/temp/"+response;
                    $('#previewimg').attr( "src",pic_data );
                    $('#imgurl').attr("value", response );
                }
            });


            $.validator.messages.required = "必须要填写";
	        $.validator.messages.minlength = jQuery.validator.format("必须由至少{0}个字符组成.");
	        $.validator.messages.maxlength = jQuery.validator.format("必须由最多{0}个字符组成");
	        $.validator.messages.equalTo = jQuery.validator.format("密码不一致.");
	        $('#validation-form').validate({
	            errorElement: 'span',
	            errorClass: 'help-block',
	            //focusInvalid: false,
	            rules: {
	                username: {
	                    required: true,
	                    uniquename: true
	                },
	                userpwd: {
	                    minlength: 6
                        <% if (ViewData["userinfo"] == null) { %>
	                    ,required: true
                        <% } %>
	                },
	                confirmpwd: {
	                    equalTo: "#userpwd"
	                },
	                realname: {
	                    required: true
	                },
	                company: {
	                    required: true
	                },
	                phone: {
	                    required: true,
	                    minlength: 11,
	                    maxlength: 11
	                },
	                duty: {
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
            $.validator.addMethod("uniquename", function (value, element) {
                return checkUserName();
            }, "用户名已存在");
            //ChangeXianList();
            changeregion();
            changerolelist();
        });

         function submitform() {
            //alert($('#validation-form').serialize());
           var level=$("#level").val();
           if (level==1) {
                var xian=$("#xian").val();
                if (xian==null) {
                    alert("请选择县！")
                    return;
                }
           }
           if (level==2) {
                var shi=$("#shi").val();
                if (shi==null) {
                    alert("请选择市！")
                    return;
                }
           }
            $.ajax({
                type: "POST",
                url: '<%= ViewData["rootUri"] %>' + 'Settings/SubmitUser',
                dataType: "json",
                data: $('#validation-form').serialize(),
                success: function (data) {
                    if (data == "") {
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

                        toastr["success"]("操作成功!", "恭喜您");
                    } else {
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

                        toastr["error"](data, "温馨敬告");

                    }
                },
                error: function (data) {
                    alert("Error: " + data.status);
                    //$('.loading-btn').button('reset');
                }
            });
        }

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

        function changerolelist(){
            var roleid;
            var level=$("#level").val();
            var currentlevel= <%=ViewData["curentlevel"] %>;
            //超管
            if(currentlevel==0){
                var rhtml="<option value='0'>测报员</option>";
                $.ajax({
                type: "GET",
                url: '<%= ViewData["rootUri"] %>'+ "Settings/GetRoleList",
                async:false,
                dataType: "json",
                success: function (data) {
                    if (data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            <%if(ViewData["roleid"]!=null ) 
                            {%>
                                roleid=<%=(Int64)ViewData["roleid"] %>
                            <%}%>
                            if (roleid!=undefined&&data[i].uid==roleid) {
                                rhtml += "<option value='" + data[i].uid + "' selected>" + data[i].name + "</option>";
                            }
                            else{
                                rhtml += "<option value='" + data[i].uid + "' >" + data[i].name + "</option>";
                            }
                        }
                        $("#role").html(rhtml);
                        $("#role").select2({ allowClear: true, minimumResultsForSearch: -1});

                    }
                    else{
                    $("#role").html(rhtml);
                    $("#role").select2({ allowClear: true, minimumResultsForSearch: -1 });

                    }
                },
            });
            }
            
            else{
            //非超管添加本级人员（只能为测报员）
                if (level==currentlevel) {
                    $("#role").html("<option value='0'>测报员</option>");    
                            $("#role").select2({ allowClear: true, minimumResultsForSearch: -1});                            
                }
            //非超管添加下级人员（不能为测报员）
                    
                else{
                    var rhtml="";
                    $.ajax({
                        type: "GET",
                        url: '<%= ViewData["rootUri"] %>'+ "Settings/GetRoleList",
                        async:false,
                        dataType: "json",
                        success: function (data) {
                            if (data.length > 0) {
                                for (var i = 0; i < data.length; i++) {
                                    <%if(ViewData["roleid"]!=null ) 
                                        {%>
                                            roleid=<%=(Int64)ViewData["roleid"] %>
                                        <%}%>
                                        if (roleid!=undefined&&data[i].uid==roleid) {
                                            rhtml += "<option value='" + data[i].uid + "' selected>" + data[i].name + "</option>";
                                        }
                                        else{
                                            rhtml += "<option value='" + data[i].uid + "' >" + data[i].name + "</option>";
                                        }
                                   // rhtml += "<option value='" + data[i].uid + "' >" + data[i].name + "</option>";
                                }
                                $("#role").html(rhtml);
                                $("#role").select2({ allowClear: true, minimumResultsForSearch: -1});
                            }
                            else{
                                $("#role").html(rhtml);
                                $("#role").select2({ allowClear: true, minimumResultsForSearch: -1 });

                            }
                        }
                    });
                }
            }
        }

        function ChangeShiList(){
            var shengid=$("#sheng").val();
            var rhtml="";
            $.ajax({
                type: "GET",
                url: '<%= ViewData["rootUri"] %>'+ "Settings/ChangAddUserShiList",
                data: { "shengid": shengid },
                method: 'post',
                async:false,
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
            if(shiid!=null){
            var rhtml="";
                $.ajax({
                    type: "GET",
                    url:  '<%= ViewData["rootUri"] %>'+ 'Settings/ChangAddUserXianList',
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
            else{
                        $("#xian").html("");
                        $("#xian").select2({ allowClear: true, minimumResultsForSearch: -1 });

            }
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
