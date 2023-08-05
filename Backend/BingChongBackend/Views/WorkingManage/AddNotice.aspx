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
                <input type="hidden" id="uid" name="uid"  value="<% if (ViewData["uid"] != null) { %><%= ViewData["uid"] %><% } else { %>0<% } %>" />
                <label class="col-sm-3 " style="text-align:right;"> <span class="red">*</span>标题:</label>
                <div class="col-sm-2">
                    <div class="clearfix">
                        <input type="" name="title" id="title" <% if (noticeinfo!= null) { %>value="<%=noticeinfo.title %>"<% }%> style="width:350px"/>
                    </div>
				</div> 
            </div>
            <div class="form-group" style="">
                <label class="col-sm-3 " style="text-align:right;"> <span class="red">*</span>文号:</label>
                <div class="col-sm-2">
                    <div class="clearfix">
                        <input type="" name="serial" id="serial" <% if (noticeinfo!= null) { %>value="<%=noticeinfo.serial %>"<% }%> style="width:350px"/>
                    </div>
				</div> 
            </div>
            <div class="form-group" style="">
                <label class="col-sm-3 " style="text-align:right;"> 签发人:</label>
                <div class="col-sm-2">
                    <div class="clearfix">
                        <input type="" id="username" name="username"<%if(ViewData["username"]!=null){%> value="<% =ViewData["username"]  %> "<%} %> readonly style="width:350px"/>
                        <input type="" class="hide" id="userid" name="userid"<%if(ViewData["userid"]!=null){%> value="<% =ViewData["userid"]  %> "<%} %> readonly />
                        
                    </div>
				</div> 
            </div>
            <div class="form-group" style="">
                <label class="col-sm-3 " style="text-align:right;"> 发布日期:</label>
                <div class="col-sm-2">
                    <div class="clearfix">
                        <input type="" value="" id="creattime" name="creattime" readonly style="width:350px" />
                    </div>
				</div> 
            </div>
            <div class="form-group profile" style="margin-bottom:0px">
                <label class="col-sm-3 control-label no-padding-right" >内容：</label>
				<div class="col-md-3">
                        <textarea style="height:260px; width:350px;" name="contents" id="contents"><% if (noticeinfo!= null) { %><%=noticeinfo.contents %><% }%></textarea>
                </div>
            </div>
            </br>
            <div class="form-group" style="margin-bottom:0px">
                <span class="col-sm-5"></span>
                <div class="col-sm-1" style="">
		            <button class="btn btn-sm btn-info" id="A1" onclick=""><i class=""></i> 发布</button>
                </div>   
                <div class="col-sm-1" style="">
		            <a class="btn btn-sm btn-info" id="A2" href="<%= ViewData["rootUri"] %>WorkingManage/Notice"><i class=""></i> 取消</a>
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
        function redirectToListPage(status) {
	        if (status.indexOf("error") != -1) {
	            $('.loading-btn').button('reset');
	        } else {
	            window.location = '<%=ViewData["rootUri"]%>' + 'WorkingManage/Notice';
	        }
	    }

        jQuery(function ($) {
            $(".select2").css('width', '100px').select2({ allowClear: true });
            $("#creattime").val(getNowFormatDate());

            $.validator.messages.required = "必须要填写";
            $.validator.messages.minlength = jQuery.validator.format("必须由至少{0}个字符组成.");
            $.validator.messages.maxlength = jQuery.validator.format("必须由最多{0}个字符组成");
            $.validator.messages.equalTo = jQuery.validator.format("密码不一致.");
            $('#validation-form').validate({
                errorElement: 'span',
                errorClass: 'help-block',
                //focusInvalid: false,
                rules: {
                    title: {
                        required: true,
                    },
                    serial: {
                        required: true,
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
           // alert($('#validation-form').serialize());
            $.ajax({
                type: "POST",
                url: '<%= ViewData["rootUri"] %>' + 'WorkingManage/SubmitNotice',
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
