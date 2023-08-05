<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header">
	<div class="">
        <div class="col-xs-2 no-padding-left">
            <h5>查看病虫害信息</h5>
        </div>
		<div class="pull-right">
            <p>
            </p>
		</div>
    </div>
</div>
<div id="zoom" style="display:none;">
    <a class="close"></a>
    <a class="previous" id="previous" value="0" onclick="onclick1(this.value)"></a>
    <a class="next"  id="next" value="0" onclick="onclick1(this.value)"></a>
    <div class="content" style="width: 407px; height: 272px; margin-top: -136px; margin-left: -203.5px;">
   
    </div>


</div>
<div class="row">
    <div class="col-xs-12" style="margin-top:30px">
        <form class="form-horizontal" role="form" id="validation-form">
        <table style=" border:0.5px solid; width:560px; margin-left:160px ">
        
        <tr style=" border:0.5px solid #858585;height:40px;">
        <td align="center"  style=" border:0.5px solid #858585;width:160px;">
         <label  style="text-align:right;">&nbsp;&nbsp;病虫害名称:&nbsp;</label>
        </td>
        <td align="left"  style=" border:0.5px solid #858585; width:400px;">
        <input id="name" disabled="disabled" style=" width:300px;height:32px; border-style:none"/>
        </td>
        </tr>
         <tr style=" border:0.5px solid #858585;height:100px;">
        <td align="center"  style=" border:0.5px solid #858585">
         <label class=" control-label no-padding-right"  >形态特征：</label>
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
           <textarea id="info1" disabled="disabled" style="width:395px;margin-left:2px; margin-top:2px;height:100px;"></textarea>
        </td>
        </tr>
        <tr style=" border:0.5px solid #858585;height:100px;">
        <td align="center"  style=" border:0.5px solid #858585">
         <label class="control-label no-padding-right" >危害特点：</label>
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
           <textarea id="info2" disabled="disabled" style="width:395px;margin-left:2px; margin-top:2px;height:100px;"></textarea>
        </td>
        </tr>
        <tr style=" border:0.5px solid #858585;height:100px;">
        <td align="center"  style=" border:0.5px solid #858585">
         <label class="control-label no-padding-right" >生活习性：</label>
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
           <textarea id="info3" disabled="disabled" style="width:395px;margin-left:2px; margin-top:2px;height:100px;"></textarea>
        </td>
        </tr>
        <tr style=" border:0.5px solid #858585;height:100px;">
        <td align="center"  style=" border:0.5px solid #858585">
        <label class="control-label no-padding-right" >发生规律：</label>
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
           <textarea id="info4" disabled="disabled" style="width:395px;margin-left:2px; margin-top:2px;height:100px;"></textarea>
        </td>
        </tr>
       <tr style=" border:0.5px solid #858585;height:100px;">
        <td align="center"  style=" border:0.5px solid #858585">
         <label class="control-label no-padding-right" >防治方法：</label>
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
           <textarea id="info5" disabled="disabled" style="width:395px;margin-left:2px; margin-top:2px;height:100px;"></textarea>
        </td>
        </tr>
          <tr style=" border:0.5px solid #858585;height:100px;">
        <td align="center"  style=" border:0.5px solid #858585">
        <label class="control-label no-padding-right" >发病条件：</label>
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
           <textarea id="info6" style="width:395px;margin-left:2px; margin-top:2px;height:100px;"></textarea>
        </td>
        <tr style=" border:0.5px solid #858585;height:100px;">
        <td   style=" border:0.5px solid #858585">
        <div align="center">
         <label class=" control-label no-padding-right" >典型图片：</label><br />
         </div>
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
        <div id="tupian" style="height:150px;width:400px;OVERFLOW:scroll" class="divScroll">
            <input type="hidden" id="img" class="img"  name="img" value="" />
            <input type="hidden" id="number" class="img"  name="img" value="0" />
        </div>
         
        <!--div class="container">

	        <ul class="gallery">
		      
                 <input type="hidden" id="img" class="img"  name="img" value="" />
                    <input type="hidden" id="number" class="img"  name="img" value="0" />
	        </ul>
	        <div class="clear"></div-->

        </div>
        </td>
        </tr>
        <tr style=" border:0.5px solid #858585;height:160px;">
        <td align="center"  style=" border:0.5px solid #858585">
         <label class=" control-label no-padding-right" >测报表格：</label><br />
         <!--input type="button" class=" " id="Button1" value="" --/-->
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
         <div style="height:160px;width:400px;OVERFLOW:scroll">
           <input id="table1" type="hidden" value="" />
         </div>
        </td>
        </tr>
        </table>
           <%-- <div  style=" margin-left:140px; margin-top:10px;">
                <div style=" margin-left:60px;">
		            <a class="btn btn-sm btn-white  btn-default btn-bold " id="sub" onclick="comeback();"style="width:130px; font-size: medium"><i class="fa fa-undo"></i> 返回</a>
		            <a class="btn btn-sm" id="A2" href="<%= ViewData["rootUri"] %>WorkingManage/ManagingDocuments"><i class=""></i> 返回</a>
                
		       </div>  

            </div> --%>
            </br>
             <div class="form-group" style="margin-bottom:0px">
                <span class="col-sm-7"></span>
                <div class="col-sm-1" style="">
		            <a class="btn btn-sm" id="sub" href="#" onclick="comeback();"><i class=""></i> 返回</a>
                </div>   
            </div>
        </form>
    </div>
</div>

<div id="tabledlg" class="hide">
    <form class="form-horizontal" role="form" id="form1">
        <div class="col-xs-12">
            <div class="form-group " id="" >
                <table border="1" cellspacing="0" cellpadding="0" width="100%" id="tablediv">
                	
                </table>
                <div id="pager10" style="height:50px"></div>
            </div>
        </div>    
    </form>
</div>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
    <!--图片弹出层样式 必要样式-->
    <link rel="stylesheet"  href="<%= ViewData["rootUri"] %>Content/css/zoom.css" media="all" />

	    <link href="<%= ViewData["rootUri"] %>Content/css/ui.jqgrid.css" rel="Stylesheet"/> 
    <link rel="stylesheet" type="text/css" href="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.min.css" /> 
    <link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/jquery-ui.min.css" /> 
    <link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/select2.css" /> 
    <style type="text/css">
    pre{overflow-x:scroll;background:#ffffff;border:1px solid #cecece;padding:10px;}
    .clear{clear:both;}
    .zoomed > .container{-webkit-filter:blur(3px);filter:blur(3px);}
    .container{width:505px;margin:0 auto;}
    .gallery{list-style-type:none;float:left;background:#ffffff;padding:20px 20px 10px 20px;margin:0;-webkit-box-shadow:0 1px 3px rgba(0,0,0,0.25);-moz-box-shadow:0 1px 3px rgba(0,0,0,0.25);box-shadow:0 1px 3px rgba(0,0,0,0.25);-webkit-border-radius:2px;-moz-border-radius:2px;border-radius:2px;}
    .gallery li{float:left;padding:0 10px 10px 0;}
    .gallery li:nth-child(6n){padding-right:0;}
    .gallery li a,.gallery li {float:left;}
    </style>




    </asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
     <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/i18n/grid.locale-cn.js" type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery.ui.touch-punch.min.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/zoom.min.js"></script>
    
    <script type="text/javascript">
     var img="";
        jQuery(function ($) {
          //  $(".select2").css('width', '400px').select2({ allowClear: true });
        });

        $(document).ready(function () {
            $("#number").val(0);
            $("#img").val("");
            $("#table1").val("");
            var name = "";

            $.ajax({
                url: '<%= ViewData["rootUri"] %>DiseasePest/ViewDiseasePestInfo?uid=<%= ViewData["uid"] %>',
                dataType: 'json',
                success: function (ret) {
                 $("input[name=type]:checked").val(ret.kind);
                 $("#name").val(ret.name);
                 $("#info1").val(ret.info1);
                 $("#info2").val(ret.info2);
                 $("#info3").val(ret.info3);
                 $("#info4").val(ret.info4);
                 $("#info5").val(ret.info5);
                 $("#info6").val(ret.info6);
                 $("#img").val(ret.photo_list);
                 $("#table1").val(ret.form_ids);
                img=ret.photo_list.toString().split(',');
                 for (var s in img) {
                    if (img[s] != "") {
                    //  <li><a href="img/gallery/DSC_0008-660x441.jpg"><img src="img/gallery/DSC_0008-69x69.jpg" /></a></li>
                     //$("#img").before('<img onlick="onclick1(this.value)"  value="'+s+'" src="<%= ViewData["rootUri"]%>Content/Images/default_img.jpg" id ="picture'+s+'" height="130px" width="106px" style=" margin-left:10px;margin-top:5"/>');
                     $("#img").before('<a onclick="onclick12('+s+')"  value="'+s+'" style="height:130px;width:106px"><img src="<%= ViewData["rootUri"]%>Content/Images/default_img.jpg" id ="picture'+s+'" height="130px" width="106px" style=" margin-left:10px;margin-top:5"/></a>');
                     $("#picture"+s+"").attr("src", "<%= ViewData["rootUri"]%>"+img[s]);
                     }
                    
                 }
                 if (ret.form_ids!="") {
                     $.ajax({
                     url: '<%= ViewData["rootUri"] %>DiseasePest/ViewTableInfo?form_ids='+ret.form_ids,
                     dataType: 'json',
                     success: function (ret) {
                        for (var s in ret) {
                                $("#table1").before("<div  style='width:375px;height:30px;border:0.5px solid #858585'><span  onclick='showtable("+ret[s].uid+")'  style='font:20px;'>&nbsp;" + ret[s].name + "</span></div>");
                            }
                      
                      }
                     });
                   }
                
                }
            });

        });

        function comeback(){
        window.location="<%= ViewData["rootUri"] %>DiseasePest/DiseasePestInfo"
        }
               
      function showtable(tableid) {
            
        if (tableid != null) {
            $.ajax({
                type: "GET",
                url: '<%= ViewData["rootUri"] %>'+ "Settings/GetTableHtml",
                data: { "tableid": tableid},
                method: 'post',
                dataType: "json",
                success: function (data) {
                    if (data.length > 0) {
                        $("#tablediv").html(data);

                    }
                    else{
                    }
                },
            });
        }

            tabledlg = $("#tabledlg").removeClass('hide').dialog({
                modal: true,
                width: 800,
            });
        }
  function onclick1( value ){
    if (parseInt(value)==0||img[value-1]=="") {
         $("#previous").val(parseInt(value));
    }else{
         $("#previous").val(parseInt(value)-1);
    }

      if (parseInt(value)==img.length-1||img[value+1]=="") {
        $("#next").val(parseInt(value));
      }else if (img[parseInt(value)+1]!=""){
         $("#next").val(parseInt(value)+1);
        }
    document.getElementById("zoom").style.display="block";
    $("#mana").attr("src", "<%= ViewData["rootUri"] %>"+img[value]);
    
    
  }
    function onclick12( value ){
    if (parseInt(value)==0||img[value-1]=="") {
         $("#previous").val(parseInt(value));
    }else{
         $("#previous").val(parseInt(value)-1);
    }

      if (parseInt(value)==img.length-1||img[value+1]=="") {
        $("#next").val(parseInt(value));
      }else if (img[parseInt(value)+1]!=""){
         $("#next").val(parseInt(value)+1);
        }
    document.getElementById("zoom").style.display="block";
    $(".content").html('<img id="mana" style="width: 407.075px; height: 272px;" src=""></img>');
    $("#mana").attr("src", "<%= ViewData["rootUri"] %>"+img[value]);
    
    
  }
    </script>
</asp:Content>
