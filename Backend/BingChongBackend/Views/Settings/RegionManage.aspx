<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>
<%@ Import Namespace="BingChongBackend.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<% var role = CommonModel.GetUserRoleInfo(); %>

<div class="page-header">
	<div>
        <div class="col-xs-2 no-padding-left">
            <h5>地区管理</h5>
        </div>
		<div class="pull-right">
            <p>
            <% if ((int)ViewData["userlevel"] != 4 && role != null && role.Contains("AddArea"))
               { %>
                 <a class="btn btn-sm btn-white  btn-default btn-bold" onclick="AddRegion()">
	                <i class="ace-icon fa fa-plus blue"></i>添加地区
                </a>
            <%} %>
               
            </p>
		</div>
    </div>
</div>
<div class="row">
    <div class="col-xs-12">
        <div class="widget-box">
			<div class="widget-body">
				<div class="widget-main">
                    <div class="searchbar">
                        <form class="form-horizontal" role="form" id="validation-form">
                            <div class="form-group" style="margin-bottom:0px">
                                <label class="col-sm-2 control-label no-padding-right" for="searchlevel">单位/部门级别：</label>
				                <div class="col-sm-2">
                                    <div class="clearfix">
						                <select class="select2" id="searchlevel" name="searchlevel" data-placeholder="请选择">
                                            <% if (ViewData["SearchRegion"] != null) {
                                                   foreach (var item in (List<SearchList>)ViewData["SearchRegion"])
                                                    {%>
                                                        <option value="<%= item.uid %>" ><%= item.name%></option>
                                                    <%}
                                          
                                               }%>
				                        </select>
                                    </div>
				                </div>  
                                <label class="col-sm-1 control-label no-padding-right" for="searchname">地区名称：</label>
				                <div class="col-sm-2">
						            <input name="searchname" id="searchname" style="height:28px;margin-top:1px" placeholder="请输入关键字"/>
                                </div>   
                                <div class="col-sm-1" style="">
						            <a class="btn btn-sm btn-info" id="searchdata" ><i class="fa fa-search"></i> 搜索</a>
                                </div>   
                            </div>
	
                        </form>
                    </div>
				</div>
			</div>
		</div>
        <div style="margin-top:10px;">
			    <table id="tbldata" class="" >
			    </table>
                <div id="pager10" style="height:50px"></div>
        </div>
    </div>
</div>

<div id="dialog-message" class="hide">
    <form class="form-horizontal" role="form" id="form_insertregion">
        <div class="col-xs-12">
            <div class="form-group">
			    <label class="col-sm-3 control-label no-padding-right" style="margin:6px 0px;" for="regionlevel">单位/部门级别：</label>
			    <div class="col-sm-3" style="margin:6px 0px;" >
                    <select class="select2" id="regionlevel" name="regionlevel" onchange="ChangeUpperRegion()">
				        <% if (ViewData["SearchRegion"] != null) {
                                foreach (var item in (List<SearchList>)ViewData["SearchRegion"])
                                {%>
                                    <option value="<%= item.uid %>" ><%= item.name%></option>
                                <%}
                                          
                            }%>
                    </select>
			    </div>
                <label class="upperregion col-sm-3 control-label no-padding-right" style="margin:6px 0px;" for="upperregion">所属地区：</label>
			    <div class="col-sm-3 upperregion" style="margin:6px 0px;" >
                    <select class="select2" id="upperregion" name="upperregion" data-placeholder="" >
				       
                    </select>
			    </div>
            </div>
            <div class="form-group">
               <label class="col-sm-3 control-label no-padding-right" style="margin:6px 0px;" for="regionname"> 地区名称：</label>
			   <div class="col-sm-3" style="margin:6px 0px;" >
                    <input name="regionname" id="regionname" />
			    </div>
		    </div>
		    <div class="form-group">
			    <label class="col-sm-3 control-label no-padding-right" style="margin:6px 0px;" for="postcode">邮编：</label>
			    <div class="col-sm-3" style="margin:6px 0px;" >
                    <div class="clearfix">
						<input name="postcode" id="postcode" />
                    </div>
			    </div>
		    </div>
            <div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix" style="background-color:#ffffff">
                <div class="ui-dialog-buttonset">
                    <button  class="btn btn-primary btn-xs ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" aria-disabled="false">
                        <span class="ui-button-text">确定</span>
                    </button>
                    <button type="button" class="btn btn-xs ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" onclick='javascript:regindlg.dialog("close");'>
                        <span class="ui-button-text">取消</span>
                    </button>
                </div>
            </div>
        </div>
    </form>
</div><!-- #dialog-message -->

<div id="dialog-message2" class="hide">
    <form class="form-horizontal" role="form" id="form2">
        <div class="col-xs-12">
            <div class="form-group">
                <label class="edit_upperregion col-sm-3 control-label no-padding-right" style="margin:6px 0px;" >所属地区：</label>
			    <div class="col-sm-3 edit_upperregion" style="margin:6px 0px;" >
                    <select class="select2" id="edit_upperregion" name="edit_upperregion" data-placeholder="" >
				       
                    </select>
			    </div>
            </div>
            <input class="hide" name="edit_level" id="edit_level" />
            <input class="hide" name="edit_uid" id="edit_uid" />
            <div class="form-group">
               <label class="col-sm-3 control-label no-padding-right" style="margin:6px 0px;" for="regionname"> 地区名称：</label>
			   <div class="col-sm-3" style="margin:6px 0px;" >
                    <input name="edit_regionname" id="edit_regionname" style="height:28px;margin-top:1px"/>
			    </div>
		    </div>
            <div class="form-group">
			    <label class="col-sm-3 control-label no-padding-right" style="margin:6px 0px;" for="postcode">邮编：</label>
			    <div class="col-sm-3" style="margin:6px 0px;" >
                    <div class="clearfix">
						<input name="postcode" id="Text1" />
                    </div>
			    </div>
		    </div>
            <div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix" style="background-color:#ffffff">
                <div class="ui-dialog-buttonset">
                    <button  class="btn btn-primary btn-xs ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" aria-disabled="false">
                        <span class="ui-button-text">确定</span>
                    </button>
                    <button type="button" class="btn btn-xs ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" onclick='javascript:editdlg.dialog("close");'>
                        <span class="ui-button-text">取消</span>
                    </button>
                </div>
            </div>
        </div>

    </form>
</div><!-- #dialog-message -->
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
    <link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/jquery-ui.min.css" /> 
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/select2.css" />
    <link rel="stylesheet" type="text/css" href="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.min.css" />
    <link href="<%= ViewData["rootUri"] %>Content/css/ui.jqgrid.css" rel="Stylesheet"/>  

</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery.ui.touch-punch.min.js"></script>
	<script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/i18n/grid.locale-cn.js" type="text/javascript"></script>
	<script src="<%= ViewData["rootUri"] %>Content/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>


    <script type="text/javascript">
        jQuery(function ($) {
            $(".select2").css('width', '100px').select2({minimumResultsForSearch: -1 });

            $("#tbldata").jqGrid({
                // multiselect: true, //复选
                url: '<%= ViewData["rootUri"] %>' + 'Settings/RetrieveRegionList',
                datatype: "json",
                height: 481,
                autowidth: true,              
                rowNum: 15,
                rowList: [15, 20, 30],
                pager: '#pager10',
                sortname: 'id',
                viewrecords: true,
                sortable: false,
                pagerpos: "center",
                pgbuttons: true,
                rownumbers: true,
                scrollOffset: 1,
                postData:{
                    searchlevel:$("#searchlevel").val()
                },
                viewsortcols: [false, 'vertical', false],
                colNames: ['单位/部门级别', '地区名称', '邮编', '操作'],
                colModel: [
                            { name: 'level', index: 'level', align: "center",title: false,
                                 formatter: function (cellvalue, options, rowObject) {
                                    if (cellvalue==1) {
                                    return "县级";
                                    }
                                    if (cellvalue==2) {
                                    return "市级";
                                    }
                                    if (cellvalue==3) {
                                    return "省级";
                                    }
                                    if (cellvalue==4) {
                                    return "国家级";
                                    }
                               }
                             },
                            { name: 'name', index: 'name',  align: "center",title: false },
                            { name: 'postcode', index: 'postcode',  align: "center" ,title: false},
                            { name: 'uid', index: 'uid',  align: "center" ,title: false,
                                    formatter: function (cellvalue, options, rowObject) {
                                    var html = "";
                                     
                                    <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("UpdateArea")) {%>
                                    html+='<a  href="#" onclick="processEdit('+rowObject.level+ ','+ cellvalue + ')"  style="font-size:12px">修改</a>&nbsp;&nbsp;';
                                    <%} %>
                                    <%if (ViewData["role"] != null && ((String)ViewData["role"]).Contains("DeleteArea")) {%>
                                    html+='<a  href="#" onclick="processDel('+rowObject.level+ ','+ cellvalue + ')" style="font-size:12px">删除</a>';
                                    <%} %>

                                    return html;
                                    }
                            }
                ],
                gridComplete: function () {
                 }
            });
            $("#tbldata").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });
            <% if((int)ViewData["userlevel"]==4){ %>
                $("#tbldata").jqGrid('hideCol', "uid");
            <%} %>





            $.validator.messages.required = "必须要填写";
            $.validator.messages.minlength = jQuery.validator.format("必须由至少{0}个字符组成.");
            $.validator.messages.maxlength = jQuery.validator.format("必须由最多{0}个字符组成");
            $.validator.messages.equalTo = jQuery.validator.format("密码不一致.");
            $('#form_insertregion').validate({
                errorElement: 'span',
                errorClass: 'help-block',
                //focusInvalid: false,
                rules: {
                    regionname: {
                        required: true,
                        uniqueregion: true
                    },
                    postcode:{
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
                    else error.insertAfter(element);
                },

                submitHandler: function (form) {
                    submitform();
                    return false;
                },
                invalidHandler: function (form) {
                    //  $('.loading-btn').button('reset');
                }
            });


            $('#form2').validate({
                errorElement: 'span',
                errorClass: 'help-block',
                //focusInvalid: false,
                rules: {
                    edit_regionname: {
                        required: true,
                        uniqueeditregion: true
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
                    else error.insertAfter(element);
                },

                submitHandler: function (form) {
                    submitedit();
                    return false;
                },
                invalidHandler: function (form) {
                    //  $('.loading-btn').button('reset');
                }
            });
            $.validator.addMethod("uniqueregion", function (value, element) {
                return checkRegionName(value);
            }, "地区已存在");
            $.validator.addMethod("uniqueeditregion", function (value, element) {
                return checkEditRegionName(value);
            }, "地区已存在");

            $("#searchdata").click(function () {
                var PostData = { searchlevel:$("#searchlevel").val(),searchname:$("#searchname").val()};
                jQuery("#tbldata").setGridParam({page:1, url:'<%= ViewData["rootUri"] %>Settings/SearchRegionList', postData: PostData }).trigger('reloadGrid');
            });
            ChangeUpperRegion();
        });

        //对话框，添加region操作
        var regindlg;
        function AddRegion() {
            $("#regionname").val("");
            $("#postcode").val("");

            regindlg = $("#dialog-message").removeClass('hide').dialog({
                modal: true,
                width: 550,
                title: "添加地区",
                title_html: true
            });

}

        function submitform() {
            // alert($('#validation-form').serialize());
            $.ajax({
                type: "POST",
                url: '<%= ViewData["rootUri"] %>' + 'Settings/SubmitRegion',
                dataType: "json",
                data: $('#form_insertregion').serialize(),
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
                    $("#tbldata").trigger("reloadGrid");

                },
                error: function (data) {
                    alert("Error: " + data.status);
                    //$('.loading-btn').button('reset');
                }
            });
			regindlg.dialog("close");
        }

        function checkRegionName(name) {
            var regionlevel=$("#regionlevel").val();
            var retval = false;

            $.ajax({
                async: false,
                type: "GET",
                url: '<%= ViewData["rootUri"] %>' + 'Settings/CheckUniqueRegionname',
                dataType: "json",
                data: {
                    uid:0,
                    regionname: name,
                    regionlevel: regionlevel
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
        function checkEditRegionName(name) {
            var regionlevel=$("#edit_level").val();
            var retval = false;

            $.ajax({
                async: false,
                type: "GET",
                url: '<%= ViewData["rootUri"] %>' + 'Settings/CheckUniqueRegionname',
                dataType: "json",
                data: {
                    uid:$("#edit_uid").val(),
                    regionname: name,
                    regionlevel: regionlevel
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

        function ChangeUpperRegion() {
                var regionlevel = $("#regionlevel").val();
                if (regionlevel==1|| regionlevel==2) {
                $(".upperregion").removeClass("hide");
                }
                else{
                $(".upperregion").addClass("hide");
                }
                var rhtml="";
                $.ajax({
                    type: "GET",
                    url: '<%= ViewData["rootUri"] %>'+ "Settings/GetUpperList/?regionlevel=" + regionlevel,
                    dataType: "json",
                    success: function (data) {
                        if (data.length > 0) {
                            for (var i = 0; i < data.length; i++) {
                                rhtml += "<option value='" + data[i].uid + "'>" + data[i].name + "</option>";
                            }
                            $("#upperregion").html(rhtml);
                            $("#upperregion").select2({ allowClear: true, minimumResultsForSearch: -1});

                        }
                        else{
                        $("#upperregion").html(rhtml);
                        $("#upperregion").select2({ allowClear: true, minimumResultsForSearch: -1 });

                        }
                    },
                });
        }
        function processDel(level,sel_id) {

            if (sel_id != "") {
                var con = confirm("是否要删除地区?");
                if (con == true) {
                    $.ajax({
                        url: '<%= ViewData["rootUri"] %>' + 'Settings/DeleteRegion',
                        data: {
                            "level":level,
                            "delids": sel_id
                        },
                        type: "post",
                        success: function (message) {
                            if (message == true) {
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
                                toastr["success"]("删除成功！", "恭喜您");

                            }
                            else {
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
                                toastr["error"]("操作失败！", "温馨敬告");
                            }
                            setTimeout(function () {
                            $("#tbldata").trigger("reloadGrid");
                            }, 2500);
                        }
                    });
                }
                
            }
            else {
                //
            }
            return false;
        }
       
        function ChangeEditUpperRegion(level,regionid,parentid) {
                var rhtml="";
                $.ajax({
                    type: "GET",
                    url: '<%= ViewData["rootUri"] %>'+ "Settings/GetUpperList/?regionlevel=" + level,
                    dataType: "json",
                    success: function (data) {
                        if (data.length > 0) {
                            for (var i = 0; i < data.length; i++) {
                                if (data[i].uid==parentid) {
                                rhtml += "<option value='" + data[i].uid + "' selected>" + data[i].name + "</option>";
                                }
                                else{
                                rhtml += "<option value='" + data[i].uid + "'>" + data[i].name + "</option>";
                                }
                            }
                            $("#edit_upperregion").html(rhtml);
                            $("#edit_upperregion").select2({ allowClear: true, minimumResultsForSearch: -1});

                        }
                        else{
                        $("#edit_upperregion").html(rhtml);
                        $("#edit_upperregion").select2({ allowClear: true, minimumResultsForSearch: -1 });

                        }
                    },
                });
        }

        var editdlg;
        function processEdit(level,sel_id) {
             
            if (level==1|| level==2) {
            $(".edit_upperregion").removeClass("hide");
            }
            else{
            $(".edit_upperregion").addClass("hide");
            }

             $.ajax({
                    url: '<%= ViewData["rootUri"] %>' + 'Settings/GetRegionInfo',
                    data: {
                        "level":level,
                        "uid": sel_id
                    },
                    type: "post",
                    success: function (date) {
                        if (date != null) {
                           $("#edit_regionname").val(date.name);
                           $("#edit_uid").val(date.uid);
                           $("#edit_level").val(level);
                           ChangeEditUpperRegion(level,date.uid,date.parentid);
                        }
                        else {
                        }
                    }
                });
            editdlg = $("#dialog-message2").removeClass('hide').dialog({
                modal: true,
                width: 550,
                title: "修改地区",
                title_html: true,
//                buttons: [
//					{
//					    text: "确定",
//					    "class": "btn btn-primary btn-xs",
//					    click: function () {
//					        $.ajax({
//					            type: "POST",
//					            url: '<%= ViewData["rootUri"] %>' + 'Settings/SubmitRegion',
//					            dataType: "json",
//					            data: {
//                                        regionlevel:level,
//                                        uid:sel_id,
//                                        regionname:$("#edit_regionname").val(),
//                                        upperregion:$("#edit_upperregion").val()
//                                        },
//					            success: function (data) {
//					                if (data == "") {
//					                    toastr.options = {
//					                        "closeButton": false,
//					                        "debug": true,
//					                        "positionClass": "toast-bottom-right",
//					                        "onclick": null,
//					                        "showDuration": "3",
//					                        "hideDuration": "3",
//					                        "timeOut": "1500",
//					                        "extendedTimeOut": "1000",
//					                        "showEasing": "swing",
//					                        "hideEasing": "linear",
//					                        "showMethod": "fadeIn",
//					                        "hideMethod": "fadeOut"
//					                    };

//					                    toastr["success"]("操作成功!", "恭喜您");
//					                } else {
//					                    toastr.options = {
//					                        "closeButton": false,
//					                        "debug": true,
//					                        "positionClass": "toast-bottom-right",
//					                        "onclick": null,
//					                        "showDuration": "3",
//					                        "hideDuration": "3",
//					                        "timeOut": "1500",
//					                        "extendedTimeOut": "1000",
//					                        "showEasing": "swing",
//					                        "hideEasing": "linear",
//					                        "showMethod": "fadeIn",
//					                        "hideMethod": "fadeOut"
//					                    };

//					                    toastr["error"](data, "温馨敬告");

//					                }
//                                    $("#tbldata").trigger("reloadGrid");
//					            },
//					            error: function (data) {
//					                alert("Error: " + data.status);
//					                //$('.loading-btn').button('reset');
//					            }
//					        });
//					        editdlg.dialog("close");
//					    }
//					},
//					{
//					    text: "取消",
//					    "class": "btn btn-xs",
//					    click: function () {
//					        $(this).dialog("close");
//					    }
//					}
//				]
            });
        }

        function submitedit(){
        $.ajax({
				type: "POST",
				url: '<%= ViewData["rootUri"] %>' + 'Settings/SubmitRegion',
				dataType: "json",
				data: {
                        regionlevel:$("#edit_level").val(),
                        uid:$("#edit_uid").val(),
                        regionname:$("#edit_regionname").val(),
                        upperregion:$("#edit_upperregion").val()
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
                    $("#tbldata").trigger("reloadGrid");
				},
				error: function (data) {
					alert("Error: " + data.status);
					//$('.loading-btn').button('reset');
				}
			});
			editdlg.dialog("close");
        }
    </script>
</asp:Content>
