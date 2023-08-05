<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>
<%@ Import Namespace="BingChongBackend.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<%var unitinfo = (department)ViewData["UnitInfo"]; %>

<div class="page-header">
	<div class="">
        <div class="col-xs-2 no-padding-left">
        <% if (unitinfo != null)
           {%>
            <h5>修改直属单位</h5>
        <%}
           else
           {%>
            <h5>添加直属单位</h5>
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
            <div class="form-group" style="margin-bottom:0px" style="text-align:center;">
               <table border="1" cellspacing="0" cellpadding="0" width="60%"  style="text-align:center;margin:auto;" >
               	<tr>
               		<td>单位/部门名称：</td>
                    <td>
                        <div class="form-group" style="margin-top:7px">
                            <div class="clearfix">
                                <input type="text" style="width:40%" id="unitname" name="unitname" <% if (unitinfo!= null) { %>value="<%=unitinfo.name%>"<% }%> />
                            </div>
                        </div>
                    </td>
               	</tr>
                <tr>
               		<td>单位/部门级别：</td>
                    <td>
                        <div class="form-group" style="margin-top:7px">
                                <select class="select2" id="regionlevel" name="regionlevel" onchange="ChangeRegionList()" <% if (unitinfo!= null){%>disabled<%}%>>
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
                 <tr class="region">
               		<td>所属地区：</td>
                    <td>
                        <div class="form-group" style="margin-top:7px">
                                <div <% if (unitinfo!= null){%>class="hide"<%}%> >
                                    <select class="select2" id="regionid" name="regionid" <% if (unitinfo!= null){%>disabled<%}%> onchange="changepostcode()">
				                   <%if(ViewData["regionname"]!=null) {%>
                                   <option value="<%=ViewData["UnitRedionid"] %>" selected><%=ViewData["regionname"] %></option>
                                     <%} %>
                                    </select>
                                </div>
                                <%if(ViewData["regionname"]!=null) {%>
                                <input type="text" value="<%=ViewData["regionname"] %>" readonly style="width:40%"/>
                               
                                <%} %>
                        </div>
                    </td>
               	</tr>
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
                                <input type="text" style="width:40%" id="chief" name="chief"  <% if (unitinfo!= null) { %>value="<%=unitinfo.chief%>"<% }%>/>
                            </div>
                        </div>
                    </td>
               	</tr>
                <tr>
               		<td>移动电话：</td>
                    <td>
                        <div class="form-group" style="margin-top:7px">
                            <div class="clearfix">
                                <input type="text" style="width:40%" id="Text3" name="cellphone" <% if (unitinfo!= null) { %>value="<%=unitinfo.mobile%>"<% }%>/>
                            </div>
                        </div>
                    </td>
               	</tr>
                <tr>
               		<td>固定电话：</td>
                    <td>
                        <div class="form-group" style="margin-top:7px">
                            <div class="clearfix">
                                <input type="text" style="width:40%" id="telephone" name="telephone" <% if (unitinfo!= null) { %>value="<%=unitinfo.phone%>"<% }%>/>
                            </div>
                        </div>
                    </td>
               	</tr>
                <tr>
               		<td>地址：</td>
                    <td>
                        <div class="form-group" style="margin-top:7px">
                            <div class="clearfix">
                                <input type="text" style="width:40%" id="cellphone" name="addr" <% if (unitinfo!= null) { %>value="<%=unitinfo.address%>"<% }%>/>
                            </div>
                        </div>
                    </td>
               	</tr>
                
                <tr>
               		<td>备注信息：</td>
                    <td>
                        <div class="form-group" style="margin-top:7px">
                            <div class="clearfix">
                                <textarea rols="3" cols="50" id="note" name="note"><% if (unitinfo!= null) { %><%=unitinfo.note%><% }%></textarea>
                            </div>
                        </div>
                    </td>
               	</tr>
               </table>
            </div>
            </br>
            <input type="hidden" id="uid" name="uid"  value="<% if (ViewData["uid"] != null) { %><%= ViewData["uid"] %><% } else { %>0<% } %>" />
            <div class="form-group" style="margin-bottom:0px">
                <span class="col-sm-5"></span>
                <div class="col-sm-1" style="">
		            <button class="btn btn-sm btn-info" type="submit" data-loading-text="提交中...">
						<i class="ace-icon fa fa-check bigger-110"></i>
						提交
					</button>
                </div>   
                <div class="col-sm-1" style="">
		            <a class="btn btn-sm" id="A2" href="<%= ViewData["rootUri"] %>Settings/UnitSettings"><i class="ace-icon fa fa-times bigger-110"></i> 取消</a>
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
    	function redirectToListPage(status) {
	        if (status.indexOf("error") != -1) {
	            $('.loading-btn').button('reset');
	        } else {
	            window.location = '<%=ViewData["rootUri"]%>' + 'Settings/UnitSettings';
	        }
	    }

        jQuery(function ($) {
            $(".select2").css('width', '200px').select2({ minimumResultsForSearch: -1 });            
            
            $.validator.messages.required = "必须要填写";
            $.validator.messages.minlength = jQuery.validator.format("必须由至少{0}个字符组成.");
            $.validator.messages.maxlength = jQuery.validator.format("必须由最多{0}个字符组成");
            $.validator.messages.equalTo = jQuery.validator.format("密码不一致.");
            $('#validation-form').validate({
                errorElement: 'span',
                errorClass: 'help-block',
                //focusInvalid: false,
                rules: {
                    unitname: {
                        required: true
                    },
                    regionid:
                    {
                        required: true
                    },
                    postcode:
                    {
                        number:true,
                        required: true
                    }
                },
                        
                messages:{
                    postcode:
                    {
                        number:"没有邮编无法添加"
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
            $.validator.addMethod("uniquename", function (value, element) {
                return checkTableName();
            }, "表格已存在");
          
            ChangeRegionList();
        });
         function ChangeRegionList() {
                var regionlevel = $("#regionlevel").val();
                if (regionlevel ==4) {
                    $(".region").addClass("hide");
                     var rhtml="<option value='0'></option>";
                    $("#regionid").html(rhtml);
                    $("#regionid").select2({ allowClear: true, minimumResultsForSearch: -1 });
                    changepostcode();
                }
                else{
                    $(".region").removeClass("hide");
                    var rhtml= "";
                    $.ajax({
                        type: "GET",
                        url: '<%= ViewData["rootUri"] %>'+ "Settings/GetRegionListByLevelForUnit/?regionlevel=" + regionlevel,
                        dataType: "json",
                        async:false,
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
                            changepostcode();
                        }
                    });
                }
        }

         function submitform() {
           // alert($('#validation-form').serialize());
           $("#regionlevel").removeAttr('disabled');
           $("#regionid").removeAttr('disabled');
            $.ajax({
                type: "POST",
                url: '<%= ViewData["rootUri"] %>' + 'Settings/SubmitUnit',
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
        function changepostcode()
        {
            var level=$("#regionlevel").val();
            if (level==4) {
                $("#postcode").val("86");
            }
            else{
                var regionid=$("#regionid").val();
                if (regionid!=null) {
                    $.ajax({
                    type: "POST",
                    url: '<%= ViewData["rootUri"] %>' + 'Settings/GetRegionPostCode',
                    dataType: "json",
                    data: {
                        level: level,
                        regionid: $("#regionid").val()
                    },
                    success: function (data) {
                        if (data != "") {
                            $("#postcode").val(data);
                        } else {
                            $("#postcode").val("该地区未添加邮编");
                        }
                    },
                    error: function (data) {
                        alert("Error: " + data.status);
                        //$('.loading-btn').button('reset');
                    }
                });
                }
                
            }
        }
    </script>
</asp:Content>
