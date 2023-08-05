<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>
<%@ Import Namespace="BingChongBackend.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<%var documentinfo = (help)ViewData["documentinfo"]; %>
<div class="page-header">
	<div class="">
        <div class="col-xs-2 no-padding-left">
            <% if (documentinfo != null)
               { %>
            <h5>编辑帮助信息</h5>
               <%}
               else
               { %>
            <h5>新建帮助信息</h5>
               <%} %>
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
                        <input type=""  name="name" id="name" <% if (documentinfo!= null) { %>value="<%=documentinfo.title %>"<% }%> style="width:350px"/>
                    </div>
				</div> 
            </div>
            <div class="form-group profile" style="margin-bottom:0px">
                <label class="col-sm-1 control-label no-padding-right" >内容：</label>
				<div class="col-md-6">
                        <textarea cols="50" name="contents" id="contents" style="height:360px;width:100%" ><% if (documentinfo != null) {%><%= documentinfo.contents %><%}%></textarea>
                </div>
            </div>
            </br>
            <div class="form-group" style="margin-bottom:0px">
                <span class="col-sm-5"></span>
                <div class="col-sm-1" style="">
		            <button class="btn btn-sm btn-info" id="A1" onclick=""><i class=""></i> 保存</button>
                </div>   
                <div class="col-sm-1" style="">
		            <a class="btn btn-sm" id="A2" href="<%= ViewData["rootUri"] %>WorkingManage/ManagingDocuments"><i class=""></i> 取消</a>
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
      function redirectToListPage(status) {
            if (status.indexOf("error") != -1) {
                //$('.loading-btn').button('reset');
            } else {
                window.location = '<%=ViewData["rootUri"]%>' + "Settings/HelpInfo";
            }
        }
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
                    name: {
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
                    //  $('.loading-btn').button('reset');
                }
            });



        });

        function submitform() {
         //   alert($('#validation-form').serialize());
           $.ajax({
	            type: "POST",
	            url: '<%= ViewData["rootUri"] %>' + 'Settings/SubmitHelp',
	            dataType: "json",
	            data: {
                        uid: $("#uid").val(),
                        name: $("#name").val(),
                        contents: $("#contents").val()
                },
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
