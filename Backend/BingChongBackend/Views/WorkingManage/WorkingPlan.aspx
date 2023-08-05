<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>
<%@ Import Namespace="BingChongBackend.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<% var role = CommonModel.GetUserRoleInfo(); %>
<div class="page-header">
	<div class="">
        <div class="col-xs-2 no-padding-left">
            <h5>测报计划</h5>
        </div>
		<div class="pull-right">
            <p>
            <% if (role != null && ((string)role).Contains("AddPlan"))
               { %> 
                <a class="btn btn-sm btn-white  btn-default btn-bold" onclick="AddPlan()">
	                <i class="ace-icon fa fa-plus blue"></i>新增
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
                        <form class="form-horizontal" role="form" id="validation-form1">
                            <!-- 第一行-->
                            <div class="form-group" style="margin-bottom:0px">
                                <label class="col-sm-1 control-label no-padding-right" for="">年度：</label>
				                <div class="col-sm-2">
                                    <div class="clearfix">
						                <select class="select2" id="yearid" name="">
                                             <option value="0">全部</option>
                                            <% DateTime now = DateTime.Now;
                                              int year = now.Year;
                                              int years = year +10;
                                              for (int i = year; i <= years; i++)
                                              {%>
                                                 <option value="<%=i %>"><%=i %></option>
                                             <% }%>                        
				                        </select>
                                    </div>
				                </div>  
                                <label class="col-sm-1 control-label no-padding-right" style="margin-left:-30px" for="">省/自治区：</label>
				                <div class="col-sm-2">
                                    <div class="clearfix">
						                <select class="select2" id="shengid" name="shengid" onchange="dutyset1(this.value)">
                                             <option value="0" >请选择</option> 
                                           <%
                                               for (int i = 0; i < ViewBag.sheng.Count; i++)
                                           {   
                                           %>
                                             <option value="<%=ViewBag.sheng[i].uid %>" ><%=ViewBag.sheng[i].name%></option>
                                           <% }%>    
                                        </select>                        
                                    </div>
				                </div>  
                                <label class="col-sm-1 control-label no-padding-right" for="" style="margin-left:-30px">盟/市：</label>
				                <div class="col-sm-2">
                                    <div class="clearfix">
						                <select class="select2" id="shiid" name="shiid">
                                              <option value="0" >请选择</option> 
				                        </select>
                                    </div>
				                </div> 
                                  <div class="col-sm-1" style="">
                                  <% if (role != null && ((string)role).Contains("ViewPlan"))
                                     { %> 
						            <a class="btn btn-sm btn-info" id="find"><i class="fa fa-search"></i> 搜索</a>
                                    <%} %>
                                </div>   
                            </div>
	
                        </form>
                    </div>
				</div>
			</div>
		</div>
        <div style="margin-top:10px;margin-right:17px">
			
              <p id="p" style="display:none;font-size:16px">没有找到要搜所的测报点</p>
              <table id="myDataTable" style="text-align:center">
              </table>
              <div id="pager10" style="height:50px"></div>
          
        </div>
    </div>
</div>

<div id="dialog-message" class="hide">
   <div class="col-xs-12" style="margin-top:10px">
    <form class="form-horizontal" role="form" id="validation-form">
			    <label class="col-sm-4 control-label no-padding-right" style="margin:6px 0px;" for="" >测报计划名称：</label>
                <div class="form-group" >
                <div class="col-sm-4">
                  <div class="clearfix">
                    <input id="planname" name="planname" style="width:150px;margin-top:10px" />
                    </div>
                    </div>
			    </div>
			    <label class="col-sm-4 control-label no-padding-right" style="margin:6px 0px;" for="">年度选择：</label>
			    <div class="form-group">
                <div class="col-sm-4">
                     <select class="select2" id="year" name="year">
                                            <% DateTime now1 = DateTime.Now;
                                              int year1 = now.Year;
                                              int years1 = year +10;
                                              for (int i = year1; i <= years1; i++)
                                              {%>
                                                 <option value="<%=i %>"><%=i %></option>
                                             <% }%>                        
				                        </select>
               </div>
               </div>
               <label class="col-sm-4 control-label no-padding-right" style="margin:6px 0px;" for=""> 省/自治区：</label>
			    <div class="form-group">
                <div class="col-sm-4">
                   <select class="select2" style="width:150px" id="sheng" name="sheng" onchange="dutyset(this.value)">
                        <%
                            for (int i = 0; i < ViewBag.sheng.Count; i++)
                        {   
                        %>
                            <option value="<%=ViewBag.sheng[i].uid %>" ><%=ViewBag.sheng[i].name%></option>
                        <% }%>    
                    </select>
		         </div>
		    </div>
			    <label class="col-sm-4 control-label no-padding-right" style="margin:6px 0px;" for="">盟/市：</label>
                    <div class="form-group">
                    <div class="col-sm-4">
						<select class="select2" style="width:150px" id="shi" name="shi">  
                               <option value="0" >请选择</option> 

                         </select>
                         </div>
            <input id="text1" readonly="readonly" style="margin-top:10px;text-align:center;width:50%;height:20px; border-style:none;display:none;margin-left:15px;color: Red" />

                   </div>
               
		    
            <%--<div style="margin-left:70px">
            <input id="text1" readonly="readonly" style="margin-top:10px;text-align:center;width:50%;height:20px; border-style:none;display:none;margin-left:15px;color: Red" />
            <button class="btn btn-info loading-btn" type="submit" >
								<i class="ace-icon fa fa-check bigger-110"></i>
								确定
			</button>

			&nbsp; &nbsp;
			<button id="sett" class="btn" type="reset" onclick="addclass()">
				<i class="ace-icon fa fa-undo bigger-110"></i>
				取消
			</button> 
            </br>
        </div>--%>
            <div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix" style="background-color:#ffffff">
                <div class="ui-dialog-buttonset">
                    <button  class="btn btn-primary btn-xs ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" aria-disabled="false"  type="submit" >
                        <span class="ui-button-text">确定</span>
                    </button>
                    <button type="button" class="btn btn-xs ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" onclick="addclass()">
                        <span class="ui-button-text">取消</span>
                    </button>
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
    <link href="<%= ViewData["rootUri"] %>Content/css/ui.jqgrid.css" rel="Stylesheet"/> 

</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
   
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/i18n/grid.locale-cn.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery.ui.touch-punch.min.js"></script>
   	<script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>  
   	<script src="<%= ViewData["rootUri"] %>Content/js/jquery.validate.min.js"></script>
    <script type="text/javascript">
     var level="<%= ViewData["level"] %> ";
     var shenga="<%= ViewData["sheng1"] %>";
     var shia="<%= ViewData["shi1"] %>";
     var xiana="<%= ViewData["xian1"] %>";
      pagebuju();
      function pagebuju() {
            $(".select2").css('width', '150px').select2({ minimumResultsForSearch: -1 });
             GetUserInfo();
        };
 function deleteshop(s){
    var con=confirm("是否确定要删除相关测报计划");
           if(con==true){
                $.ajax({
                        type: "post",
                        data: { uids: s    
                        },
                        url: "<%= ViewData["rootUri"] %>WorkingManage/Deletepoint",
                        dataType: "json",
                        success: function (success) {
                            if (success == true) {
                               toas1();
                                setTimeout(function(){
                                jQuery("#myDataTable").trigger("reloadGrid");
                            }, 2500);
                            }
                            else {
                                toas2();
                            }
                        }

                    });
        }

    }
     function removeclass() {
      $("#sheng").attr("disabled", false);
      $("#shi").attr("disabled", false);
      $("#shengid").attr("disabled", false);
      $("#shiid").attr("disabled", false); 
    }
    function addcl() {
        if(level==0)
        {
          $("#sheng").attr("disabled", false);
          $("#shi").attr("disabled", false);
           $("#shengid").attr("disabled", false);
          $("#shiid").attr("disabled", false);
        }
        if(level==1)
        {
          $("#sheng").attr("disabled", "disabled");
          $("#shi").attr("disabled", "disabled");
           $("#shengid").attr("disabled", "disabled");
          $("#shiid").attr("disabled", "disabled"); 
        }
        if(level==2)
        {
          $("#sheng").attr("disabled", "disabled");
          $("#shi").attr("disabled", "disabled");
           $("#shengid").attr("disabled", "disabled");
          $("#shiid").attr("disabled", "disabled");
        }
        if(level==3)
        {
          $("#sheng").attr("disabled", "disabled");
          $("#shi").attr("disabled", false);
           $("#shengid").attr("disabled", "disabled");
          $("#shiid").attr("disabled", false);
        }
        if(level==4)
        {
          $("#sheng").attr("disabled", false);
          $("#shi").attr("disabled", false);
           $("#shengid").attr("disabled", false);
          $("#shiid").attr("disabled", false);
        }
      
    }
        function dutyset(id) {
            document.getElementById("shi").options.length = 0;
            if (id != 0) {
                $.ajax({
                    url: "<%= ViewData["rootUri"] %>Station/Findshi",
                    data: { "shengid": id },
                    method: 'post',
                    success: function (data) {
                       if(level==2||level==1)
                            {
                                     $("#shi").append("<option value=" + "0" + ">" + "请选择" + "</option>");
                                for (var s in data) {
                                    if (data[s].uid==shia) {
                                        $("#shi").append("<option value=" + data[s].uid + " selected='selected'>" + data[s].name + "</option>");
                                    } else {
                                        $("#shi").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                                    }
                                }
                            }else
                            {
                                    $("#shi").append("<option value=" + "0" + ">" + "请选择" + "</option>");
                                   for (var s in data) {
                                    if (s == 0) {
                                        $("#shi").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                                    } else {
                                        $("#shi").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                                    }
                                }
                            }
                        $("#shi").css('width', '150px').select2({allowClear:true, minimumResultsForSearch: -1 });
                    }
                });
            } else {
                $("#shi").append("<option value=" + "0" + ">" + "请选择" + "</option>");
                $("#shi").css('width', '150px').select2({allowClear:true, minimumResultsForSearch: -1 });
            }
        }
        function dutyset1(id) {
            document.getElementById("shiid").options.length = 0;
            if (id != 0) {
                $.ajax({
                    url: "<%= ViewData["rootUri"] %>Station/Findshi",
                    data: { "shengid": id },
                    method: 'post',
                    success: function (data) {
                        if(level==2||level==1)
                            {
                                     $("#shiid").append("<option value=" + "0" + ">" + "请选择" + "</option>");
                                for (var s in data) {
                                    if (data[s].uid==shia) {
                                        $("#shiid").append("<option value=" + data[s].uid + " selected='selected'>" + data[s].name + "</option>");
                                    } else {
                                        $("#shiid").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                                    }
                                }
                            }else
                            {
                                    $("#shiid").append("<option value=" + "0" + ">" + "请选择" + "</option>");
                                   for (var s in data) {
                                    if (s == 0) {
                                        $("#shiid").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                                    } else {
                                        $("#shiid").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                                    }
                                }
                            }
                       $("#shiid").css('width', '150px').select2({allowClear:true, minimumResultsForSearch: -1 }); 
                    }
                });
            } else {
                $("#shiid").append("<option value=" + "0" + ">" + "请选择" + "</option>");
                $("#shiid").css('width', '150px').select2({allowClear:true, minimumResultsForSearch: -1 }); 
            }
        }
        var plandlg;
        function AddPlan() {
            plandlg = $("#dialog-message").removeClass('hide').dialog({
                modal: true,
                width: 500,
                title: "年度测报计划"
                //title_html: true,
         
            });

        }
        function addclass() {
            $("#dialog-message").dialog("close");
        }
        $(document).ready(function () {
            $("#find").click(function () {
                removeclass();
                var PostData = { year:$("#yearid").val(),sheng:$("#shengid").val(),shi:$("#shiid").val() };
                jQuery("#myDataTable").setGridParam({page:1, url:'<%= ViewData["rootUri"] %>WorkingManage/FintPointPlan', postData: PostData }).trigger('reloadGrid');
                addcl();
            });
       });
       function GetUserInfo () {
            if(level==3)
                {
                 $("#sheng option[value='"+shenga+"']").attr("selected", "selected");
                 $("#shengid option[value='"+shenga+"']").attr("selected", "selected");  
                 dutyset(shenga);
                 dutyset1(shenga);
                  $("#sheng").css('width', '150px').select2({ minimumResultsForSearch: -1 });
                  $("#shengid").css('width', '150px').select2({ minimumResultsForSearch: -1 });
                }
            if(level==2)
                {
                $("#sheng option[value='"+shenga+"']").attr("selected", "selected"); 
                $("#shengid option[value='"+shenga+"']").attr("selected", "selected"); 
                 dutyset(shenga);
                 dutyset1(shenga);
                  $("#sheng").css('width', '150px').select2({ minimumResultsForSearch: -1 });
                   $("#shengid").css('width', '150px').select2({ minimumResultsForSearch: -1 });
                }
            if(level==1)
                {
                $("#sheng option[value='"+shenga+"']").attr("selected", "selected");
                $("#shengid option[value='"+shenga+"']").attr("selected", "selected");  
                 dutyset(shenga);
                 dutyset1(shenga);
                  $("#sheng").css('width', '150px').select2({ minimumResultsForSearch: -1 });
                   $("#shengid").css('width', '150px').select2({ minimumResultsForSearch: -1 });
                }
                 
          
        }
        $(function () {
          var sheng=$("#shengid").val();
                var shi=$("#shiid").val(); 
                
            if(level==3)
                {
                 shi="0";
                }
            if(level==2)
                {
                  shi=shia;
                  
                }
            if(level==1)
                {
                  shi=shia;
                }   
            $("#myDataTable").jqGrid({
                url: '<%= ViewData["rootUri"] %>WorkingManage/FintPointPlan',
                datatype: "json",
                height: 300,
                autowidth: true, 
                rowNum: 15,
                rowList: [5,15, 20, 30],
                pager: '#pager10',
                sortname: 'id',
                viewrecords: true,
                sortable: false,
                pagerpos: "center",
                pgbuttons: true,
                rownumbers: true,
                scrollOffset: 1,
                postData:{
                    year:$("#yearid").val(),
                    sheng:sheng,
                    shi:shi       
                           
                },
                viewsortcols: [false, 'vertical', false],
                colNames: [ 'id', '年度', "省/自治区", '盟/市', '测报计划名称', '操作'],
                colModel: [
                            { name: 'id', index: 'id', width: 1, align: "center" },
                            { name: 'year_id', index: 'year_id', width: 100, align: "center" },
                            { name: 'sheng', index: 'nickname', width: 100, align: "center" }, 
                            { name: 'shi', index: 'shi', width: 100, align: "center"},
                             { name: 'name', index: 'xian', width: 200, align: "center"},
                            { name: 'operate', index: 'operate', width: 100, align: "center",
                                formatter: function (cellvalue, options, rowObject) {
                                    return "<% var role = CommonModel.GetUserRoleInfo(); %>"
                                     +"<% if (role != null && ((string)role).Contains("UpdatePlan")){ %>"
                                     +"<a  href='<%= ViewData["rootUri"] %>WorkingManage/YearWorkingPlan?planid="+rowObject.id+"' style='font-size:12px'  title='编辑';>编辑</a>&nbsp;"
                                     +" <%} %>"
                                     +"<% if (role != null && ((string)role).Contains("DeletePlan")){ %>"
                                     + "<a  href='#' style='font-size:12px'onclick='return deleteshop(" + rowObject.id + ")'  title='删除';>删除</a>";
                                     +" <%} %>"
                                }
                            }
                ],
                gridComplete: function () {
                  addcl();
                    var ids = $("#myDataTable").jqGrid('getDataIDs');
                    if (ids.length == 0) {
                        $("#p").slideDown();
                    }
                    else { $("#p").hide(); }
                }
            });
            $("#myDataTable").jqGrid('hideCol', "id");
            $("#myDataTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });
        });
        function check2(){
           removeclass();  
           var shiii=$("#shi").val(); 
           if(shiii!=0){
               document.getElementById("text1").style.display="none";
               return true;
              }
           else{
               document.getElementById("text1").style.display="block";
                $('#text1').val("请选择相应的城市");
                addcl();
                return false;
              }
      }
        jQuery(function ($) {
	        $.validator.messages.required = "必须要填写";
            $('#validation-form').validate({
	            errorElement: 'span',
	            errorClass: 'help-block',
	            //focusInvalid: false,
	            rules: {
	                planname: {
	                    required: true
	                },
                    year: {
	                    required: true
	                },
                    sheng: {
	                    required: true
	                },
                    shi: {
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
	                //$('.loading-btn').button('reset');
	            }
          });

          });
     function submitform() {
     if(check2()==true)
     {     
            removeclass();   
			$.ajax({
				type: "POST",
				url: "<%= ViewData["rootUri"] %>WorkingManage/InsertPlan",
				dataType: "json",
				data: $('#validation-form').serialize(),
				success: function (data) {
				    if (data == true) {
                            toas1();
                            setTimeout(function(){
                                window.location.reload();
                            }, 2500);

				    } else {
				        toas2();
				    }
				},
				error: function (data) {
				    alert("Error: " + data.status);
				    $('.loading-btn').button('reset');
				}
			});
         } 
        }
        function toas1() {
           toastr.options = {
				            "closeButton": false,
				            "debug": true,
				            "positionClass": "toast-bottom-right",
				            "onclick": null,
				            "showDuration": "3",
				            "hideDuration": "3",
				            "timeOut": "2000",
				            "extendedTimeOut": "2000",
				            "showEasing": "swing",
				            "hideEasing": "linear",
				            "showMethod": "fadeIn",
				            "hideMethod": "fadeOut"
				        };
				        toastr["success"]("操作成功!", "恭喜您");
        }
         function toas2() {
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

				        toastr["error"]("操作失败!", "温馨敬告");
        }
    </script>
</asp:Content>
