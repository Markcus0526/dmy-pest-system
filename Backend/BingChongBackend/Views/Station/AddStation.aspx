<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header">
	<div class="">
        <div class="col-xs-2 no-padding-left">
            <h5>添加测报点</h5>
        </div>
		<div class="pull-right">
            <p>
            </p>
		</div>
    </div>
</div>
<div class="row">
    <div class="col-xs-12" style="margin-top:30px">
        <form class="form-horizontal" id="validation-form">
            <div class="form-group" style="">
                <label class="col-sm-3 " style="text-align:right;margin-left:60px"> 测报点级别:</label>
                <div class="col-sm-2">
                    <div class="clearfix" >
                    <div class="form-group">
						<select class="select2" id="sort" name="sort"  data-placeholder="请选择" onchange="GetPcode()">
                        <%if (Convert.ToInt16(ViewData["level"]) == 0)
                          {%>
                            <option value="0" selected="selected">国家级</option>
                            <option value="1" >省级</option>
                            <option value="2">市级</option>
                            <option value="3">县级</option>  
                            <%} %>
                            <%if (Convert.ToInt16(ViewData["level"]) == 1)
                          {%>
                            <option value="0">国家级</option>
                            <option value="3" selected="selected">县级</option>  
                            <%} %>  
                            <%if (Convert.ToInt16(ViewData["level"]) == 2)
                          {%>
                            <option value="0">国家级</option>
                            <option value="2" selected="selected">市级</option>
                            <%} %>  
                            <%if (Convert.ToInt16(ViewData["level"]) == 3)
                          {%>
                            <option value="0">国家级</option>
                            <option value="1" selected="selected">省级</option>
                            <%} %>   
                            <%if (Convert.ToInt16(ViewData["level"]) == 4)
                          {%>
                            <option value="0"  selected="selected">国家级</option>
                            <%} %>                         
				        </select>
                        </div>
                    </div>
				</div> 
		        <label class="col-sm-1 " style="text-align:right;"> 类型:</label>
                <div class="col-sm-2">
                    <div class="clearfix">
                      <div class="form-group">
						<select class="select2" id="style1" name="style1" data-placeholder="请选择" onchange="GetPcode()">
                            <option value="0" selected="selected">固定测报点</option>
                            <option value="1" >非固定测报点</option>                         
				        </select>
                        </div>
                    </div>
				</div> 
            </div>
            </br>
            <div class="form-group" style="margin-bottom:0px" style="text-align:center;">
               <table border="1" cellspacing="0" cellpadding="0" width="60%"  style="text-align:center;margin:auto;" >
               	<tr>
               		<td>省/自治区：</td>
                    <td>
                    <div class="form-group" style="margin-top:7px">
                    <div class="clearfix">
                    
                    <%if (Convert.ToInt16(ViewData["level"]) == 0 || Convert.ToInt16(ViewData["level"]) == 4)
                      {%>
                      <select style="width:40%" id="sheng"  name="sheng" onchange="dutyset(this.value)">
                             <option value="0" >请选择</option>
                           <%
                          for (int i = 0; i < ViewBag.shengc.Count; i++)
                          {   
           %>
       <option value="<%=ViewBag.shengc[i].uid %>" ><%=ViewBag.shengc[i].name%></option>
       <% }%>
                    </select>
                     <% }%>  
                      <%if (Convert.ToInt16(ViewData["level"]) != 0 && Convert.ToInt16(ViewData["level"]) != 4)
                      {%>
                      <select style="width:40%" id="sheng" disabled="disabled"  name="sheng" onchange="dutyset(this.value)">
                           <%
                          for (int i = 0; i < ViewBag.shengc.Count; i++)
                          {   
           %>
       <option value="<%=ViewBag.shengc[i].uid %>" <%if (Convert.ToInt64(ViewData["sheng"]) == ViewBag.shengc[i].uid){%>selected="selected" <%} %> ><%=ViewBag.shengc[i].name%></option>
       <% }%>
                   </select>
                     <% }%>    
                        
                        </div>
                     </div>
                    </td>
               	</tr>
                <tr>
               		<td>盟/市：</td>
                    <td>
                    <div class="form-group" style="margin-top:7px">
                    <div class="clearfix">
                    <%if (Convert.ToInt16(ViewData["level"]) == 0 || Convert.ToInt16(ViewData["level"]) == 4 || Convert.ToInt16(ViewData["level"]) == 3)
                      {%>
                    <select style="width:40%" id="shi" name="shi" onchange="dutyset1(this.value)" >
                                                       
                        </select>
                        <%} %>
                        <%if (Convert.ToInt16(ViewData["level"]) == 1 || Convert.ToInt16(ViewData["level"]) == 2)
                      {%>
                    <select style="width:40%" id="shi" disabled="disabled" name="shi" onchange="dutyset1(this.value)" >
                                                       
                        </select>
                        <%} %>
                    </div>
                    </div>
                    </td>
               	</tr>
                 <tr>
               		<td>旗/县：</td>
                    <td>
                    <div class="form-group" style="margin-top:7px">
                    <%if (Convert.ToInt16(ViewData["level"]) == 0 || Convert.ToInt16(ViewData["level"]) == 4 || Convert.ToInt16(ViewData["level"]) == 3 || Convert.ToInt16(ViewData["level"]) == 2)
                      {%>
                    <select style="width:40%" id="xian" name="xian" onchange="GetPcode()">
                                                      
                        </select>
                        <%} %>
                        <%if (Convert.ToInt16(ViewData["level"]) ==1 )
                      {%>
                    <select style="width:40%" id="xian" disabled="disabled"  name="xian" onchange="GetPcode()">
                                                      
                        </select>
                        <%} %>
                    </div>
                    </td>
               	</tr>
                 <tr>
               		<td>测报点名称：</td>
                    <td>
                    <div class="form-group" style="margin-top:7px">
                    <div class="clearfix">
                        <input type="text" style="width:40%" id="pointname" name="pointname"/>
                    </div>
                    </div>
                    </td>
               	</tr>
                 <tr>
               		<td>测报点代码：</td>
                    <td>
                    <div class="form-group" style="margin-top:7px">
                        <input type="text" style="width:40%" id="pointcode" name="pointcode" readonly value="02800100101"/>
                    </div>
                    </td>
               	</tr>
                <tr>
               		<td>东经：</td>
                    <td>
                    <div class="form-group" style="margin-top:7px">
                    <div class="clearfix">
                        <input  style="width:40%" id="dongjing" name="dongjing" type="text" placeholder="请输入标准经度 如：121.12321"/>
                    </div>
                    </div>
                    </td>
               	</tr>
                <tr>
               		<td>北纬：</td>
                    <td>
                    <div class="form-group" style="margin-top:7px">
                    <div class="clearfix">
                        <input style="width:40%" type="text" id="beiwei" name="beiwei" placeholder="请输入标准纬度 如：41.12321"/>
                    </div>
                    </div>
                    </td>
               	</tr>
                <tr>
               		<td>田块面积：</td>
                    <td>
                    <div class="form-group" style="margin-top:7px">
                    <div class="clearfix">
                        <input style="width:40%" type="text" id="fieldarea" name="fieldarea" placeholder="请输入标准面积 如：41"/>
                    
                    </div>
                    </div>
                    </td>
               	</tr>
                <tr>
               		<td>代表面积：</td>
                    <td>
                    <div class="form-group" style="margin-top:7px" >
                    <div class="clearfix">
                        <input style="width:40%" type="text" id="daibiaoarea" name="daibiaoarea" placeholder="请输入标准面积 如：41"/>
                    </div>
                    </div>
                    </td>
               	</tr>
                <tr>
               		<td>种植作物：</td>
                    <td>
                    <div class="form-group" style="margin-top:7px">
                    <div class="clearfix">
                        <input style="width:40%" type="text" id="crop" name="crop"/>
                    </div>
                    </div>
                    </td>
               	</tr>
                <tr>
               		<td>乡镇：</td>
                    <td>
                    <div class="form-group" style="margin-top:7px">
                    <div class="clearfix">
                        <input style="width:40%" type="text" id="xiang" name="xiang"/>
                    </div>
                    </div>
                    </td>
               	</tr>
                <tr>
               		<td>村组：</td>
                    <td>
                    <div class="form-group" style="margin-top:7px">
                    <div class="clearfix">
                        <input style="width:40%" type="text" id="cun" name="cun"/>
                    </div>
                    
                    </div></td>
               	</tr>
                <tr>
               		<td>测报对象：</td>
                    <td>
                    <div class="form-group" style="margin-top:7px">
                    <div class="clearfix">
                        <textarea style="width:40%" rols="3" cols="50" id="pobject" name="pobject"></textarea>
                    </div>
                    </div>
                    </td>
               	</tr>
                <tr>
               		<td>备注信息：</td>
                    <td>
                    <div class="form-group" style="margin-top:7px">
                    <div class="clearfix">
                        <textarea style="width:40%" rols="3" cols="50" id="detail" name="detail"></textarea>
                    </div>
                    </div>
                    </td>
               	</tr>
               </table>
            </div>
            </br>
            <div class="form-group" style="margin-bottom:0px">
                <span class="col-sm-5"></span>
                <div style="margin-left:150px">
                
                <input id="text1" readonly="readonly" style="margin-top:10px;text-align:center;width:50%;height:20px; border-style:none;display:none;margin-left:150px;color: Red" />
                </div>
              </div>
            <div class="form-group" style="margin-bottom:0px">
                <span class="col-sm-5"></span>
                <div style="margin-left:100px">
                
                <button class="btn btn-info loading-btn" type="submit" >
								<i class="ace-icon fa fa-check bigger-110"></i>
								保存
							</button>

							&nbsp; &nbsp;
							<button id="sett" class="btn" type="reset" onclick="resett()">
								<i class="ace-icon fa fa-undo bigger-110"></i>
								重置
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
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
	<script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>  
   	<script src="<%= ViewData["rootUri"] %>Content/js/jquery.validate.min.js"></script>
    <script type="text/javascript">
    var level="<%= ViewData["level"] %> ";
    var shenga="<%= ViewData["sheng"] %>";
    var shia="<%= ViewData["shi"] %>";
    var xiana="<%= ViewData["xian"] %>";
    function resett() {
     
         if(level==3)
        {
        dutyset(shenga);
        $("#sort option[value='1']").attr("selected", "selected"); 
         $("#style1 option[value='0']").attr("selected", "selected"); 
        }
        if(level==2)
        {
         dutyset(shenga);
          $("#sort option[value='2']").attr("selected", "selected"); 
         $("#style1 option[value='0']").attr("selected", "selected"); 
        //dutyset1(shia);
        }
        if(level==1)
        {
         dutyset1(shia);
         $("#sort option[value='3']").attr("selected", "selected"); 
         $("#style1 option[value='0']").attr("selected", "selected"); 
         GetPcode();
        }
        if(level==0||level==4)
        {
         $("#sort option[value='0']").attr("selected", "selected"); 
         $("#style1 option[value='0']").attr("selected", "selected"); 
        dutyset(0);
        }
     $("#sort").css('width', '150px').select2({ minimumResultsForSearch: -1 });
     $("#style1").css('width', '150px').select2({ minimumResultsForSearch: -1 });
    }
    if(level==3)
    {
    dutyset(shenga);
    }
    if(level==2||level==1)
    {
     dutyset(shenga);
    // dutyset1(shia);
    }
    if(level==1)
    {
     dutyset1(shia);
     GetPcode();

    }
        jQuery(function ($) {
            $(".select2").css('width', '150px').select2({ minimumResultsForSearch: -1 });

        });
     function GetPcode() {
           var pid=$("#sheng").val();
           var sid=$("#shi").val();
           var xid=$("#xian").val();
         if(level==1)
         {  xid=xiana;
            pid=shenga;
            sid=shia;
         }
           var type1=$("#sort").val();
           var style1=$("#style1").val();
            if (xid != 0&&xid!=""){
                $.ajax({
                    url: "<%= ViewData["rootUri"] %>Station/GetPcode",
                    data: 
                        { xianid: xid,
                          pid:pid,
                          sid:sid,
                          type1:type1,
                          style1:style1
                        },
                    method: 'post',
                    success: function (data) {
                       if(data!=null&&data!=""&&data!="1")
                       {
                          $("#pointcode").val(data);
                       }
                        if(data=="1")
                       {
                        
                        setTimeout(function(){
                               $("#pointcode").val("无法生成测报点代码!");
                            }, 2500);
                          
                       }
                    }
                });
            } 
            else
            {
               $("#pointcode").val("无法生成测报点代码!");
            }
        }
        function dutyset(id) {
            document.getElementById("shi").options.length = 0;
            if (id != 0) {
                $.ajax({
                    url: "<%= ViewData["rootUri"] %>Station/Findshi",
                    data: { "shengid": id },
                    method: 'post',
                    async:false,
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
                        dutyset1($("#shi").val());
                    }
                });
            } else {
                $("#shi").append("<option value=" + "0" + ">" + "请选择" + "</option>");
                  dutyset1($("#shi").val());
            }
        }
        function dutyset1(id) {
            document.getElementById("xian").options.length = 0;
            if (id != 0) {
                $.ajax({
                    url: "<%= ViewData["rootUri"] %>Station/Findxian",
                    data: { "shiid": id },
                    method: 'post',
                    success: function (data) {
                          if(level==1)
                            {
                                     $("#xian").append("<option value=" + "0" + ">" + "请选择" + "</option>");
                                for (var s in data) {
                                    if (data[s].uid==xiana) {
                                        $("#xian").append("<option value=" + data[s].uid + " selected='selected'>" + data[s].name + "</option>");
                                    } else {
                                        $("#xian").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                                    }
                                }
                            }else
                            {
                                         $("#xian").append("<option value=" + "0" + ">" + "请选择" + "</option>");
                                   for (var s in data) {
                                    if (s == 0) {
                                        $("#xian").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                                    } else {
                                        $("#xian").append("<option value=" + data[s].uid + ">" + data[s].name + "</option>");
                                    }
                                }
                            }
                    }
                });
            } else {
                $("#xian").append("<option value=" + "0" + ">" + "请选择" + "</option>");
            }
        }
 var rootUri = "<%= ViewData["rootUri"] %>";
  function removeclass() {
      $("#sheng").attr("disabled", false);
      $("#shi").attr("disabled", false);
      $("#xian").attr("disabled", false); 
    }
    function addclass() {
        if(level==0)
        {
          $("#sheng").attr("disabled", false);
          $("#shi").attr("disabled", false);
          $("#xian").attr("disabled", false); 
        }
        if(level==1)
        {
          $("#sheng").attr("disabled", "disabled");
          $("#shi").attr("disabled", "disabled");
          $("#xian").attr("disabled", "disabled"); 
        }
        if(level==2)
        {
          $("#sheng").attr("disabled", "disabled");
          $("#shi").attr("disabled", "disabled");
          $("#xian").attr("disabled", false); 
        }
        if(level==3)
        {
          $("#sheng").attr("disabled", "disabled");
          $("#shi").attr("disabled", false);
          $("#xian").attr("disabled", false); 
        }
        if(level==4)
        {
          $("#sheng").attr("disabled", false);
          $("#shi").attr("disabled", false);
          $("#xian").attr("disabled", false); 
        }
      
    }
       function check1(){
            var reg=/^-?\d+(\.\d*)?$/; //正则表达式
            dongjing=$("#dongjing").val(); 
           if(reg.test(dongjing)&&dongjing<=180){
               document.getElementById("text1").style.display="none";
               return true;
              }
              else{
               document.getElementById("text1").style.display="block";
                $('#text1').val("请输入标准的经度");
                return false;
              }
      }
      function check2(){
            var reg=/^-?\d+(\.\d*)?$/; //正则表达式
            beiwei=$("#beiwei").val(); 
           if(reg.test(beiwei)&&beiwei<=180){
               document.getElementById("text1").style.display="none";
               return true;
              }
              else{
               document.getElementById("text1").style.display="block";
                $('#text1').val("请输入标准的纬度");
                return false;
              }
      }
      function check3(){
            var reg=/^-?\d+(\.\d*)?$/; //正则表达式
            fieldarea=$("#fieldarea").val(); 
           if(reg.test(fieldarea)){
               document.getElementById("text1").style.display="none";
               return true;
              }
              else{
               document.getElementById("text1").style.display="block";
                $('#text1').val("请输入标准的面积");
                return false;
              }
      }
      function check4(){
            var reg=/^-?\d+(\.\d*)?$/; //正则表达式
            daibiaoarea=$("#daibiaoarea").val(); 
           if(reg.test(daibiaoarea)){
               document.getElementById("text1").style.display="none";
               return true;
              }
              else{
               document.getElementById("text1").style.display="block";
                $('#text1').val("请输入标准的面积");
                return false;
              }
      }
      function check5(){
            removeclass(); 
            var xi=$("#xian").val();
           if(xi!=0){
               document.getElementById("text1").style.display="none";
               return true;
              }
           else{
               document.getElementById("text1").style.display="block";
                $('#text1').val("请选择县");
                addclass
                return false;
              }
      }
      function check6(){
            
            var pc=$("#pointcode").val();
           if(pc!="无法生成测报点代码!"){
               document.getElementById("text1").style.display="none";
               return true;
              }
           else{
               document.getElementById("text1").style.display="block";
                $('#text1').val("测报点代码没有生成，无法添加！");
                addclass
                return false;
              }
      }
jQuery(function ($) {
            $.validator.messages.remote = "这个测报点可能已经存在或输入的格式有误！";
	        $.validator.messages.required = "必须要填写";
            $('#validation-form').validate({
	            errorElement: 'span',
	            errorClass: 'help-block',
	            //focusInvalid: false,
	            rules: {
	                sort: {
	                    required: true
	                },
                    style1: {
	                    required: true
	                },
                    sheng: {
	                    required: true
	                },
                    shi: {
	                    required: true
	                },
                    xian: {
	                    required: true
	                },
                    pointname: { 
                        required: true
	                },
                    pointcode: {
	                    required: true
	                },
	                dongjing: {
                        required: true
	                },
                    beiwei: {
                        required: true,
                        remote:{
                                url: rootUri+"Station/GetSamePoint",    //后台处理程序 
                               type: "post",              //数据发送方式
                           dataType: "json",          //接受数据格式    
                               data: {                    //要传递的数据
                                     dongjing: function() {
                                                return $("#dongjing").val();
                                      },
                                     beiwei: function() {
                                                return $("#beiwei").val();
                                      }
                               }
                        }

                    },                  
                    fieldarea: {
                        required: true 
                    },
                    daibiaoarea: {
                        required: true
                        
                    },
                     crop: {
                        required: true
                        
                    },
                     xiang: {
                        required: true
                        
                    },
                     cun: {
                        required: true
                        
                    },
                    pobject: {
                        required: true
                        
                    },
                    detail: {
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
	        
    });
        function submitform() {
        if(check1()==true&&check2()==true&&check3()==true&&check4()==true&&check5()==true&&check6()==true)
        {    removeclass();
			$.ajax({
				type: "POST",
				url: "<%= ViewData["rootUri"] %>Station/InserPoint",
				dataType: "json",
				data: $('#validation-form').serialize(),
				success: function (data) {
				    if (data == true) {
                            toas1();
                            setTimeout(function(){
                                window.location.href="<%= ViewData["rootUri"] %>Station/StationManage";
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
				        toastr["success"]("新增成功!", "恭喜您");
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

				        toastr["error"]("新增失败!", "温馨敬告");
        }
    </script>
</asp:Content>
