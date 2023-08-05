<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>
<%@ Import Namespace="BingChongBackend.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<% var userinfo = (UserInfo)ViewData["userinfo"]; %>

<div class="page-header">
	<div class="">
        <div class="col-xs-2 no-padding-left">
            <h5>修改密码</h5>
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
            <div class="col-sm-6 ">
                <div class="form-group" style="">
                    <input type="hidden" id="uid" name="uid" value="<% if (userinfo!= null) { %><%= userinfo.uid %><% } else { %>0<% } %>" />
                    <label class="col-sm-6 control-label no-padding-right" for="username" >用户名：</label>
                    <label class="col-sm-1 control-label "><% if (userinfo!= null) { %><%=userinfo.username %><% }%> </label>
                </div>
                <div class="form-group" style="">
                    <label class="col-sm-6 control-label no-padding-right" for="userpwd">密码：</label>
				    <div class="col-md-6" >
                       <input name="userpwd" id="userpwd"  />
                    </div>
                </div>
                <div class="form-group" style="">
                    <label class="col-sm-6 control-label no-padding-right" >确认密码：</label>
				    <div class="col-md-6">
                       <input name="confirmpwd" id="confirmpwd" />
                    </div>
                </div>
             <div class="form-group" style="">
                <span class="col-sm-5"></span>
                <div class="col-sm-3" style="">
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
            </div>
           

        </form>
    </div>


</div>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/jquery-ui.min.css" />
	<link rel="stylesheet" type="text/css" href="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.min.css" />

</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
	<script src="<%= ViewData["rootUri"] %>Content/js/jquery.validate.min.js"></script>
	<script src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>  

<script type="text/javascript">

    jQuery(function ($) {
        $.validator.messages.required = "必须要填写";
	        $.validator.messages.minlength = jQuery.validator.format("必须由至少{0}个字符组成.");
	        $.validator.messages.maxlength = jQuery.validator.format("必须由最多{0}个字符组成");
	        $.validator.messages.equalTo = jQuery.validator.format("密码不一致.");
	        $('#validation-form').validate({
	            errorElement: 'span',
	            errorClass: 'help-block',
	            //focusInvalid: false,
	            rules: {
	                userpwd: {
	                    minlength: 6,
                        required: true
	                },
	                confirmpwd: {
	                    equalTo: "#userpwd"
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
	        // alert($('#validation-form').serialize());
	        $.ajax({
	            type: "POST",
	            url: '<%= ViewData["rootUri"] %>' + 'Settings/SubmitPass',
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
</script>
</asp:Content>
