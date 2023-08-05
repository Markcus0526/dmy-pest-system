<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header">
	<div class="">
        <div class="col-xs-2 no-padding-left">
            <h5>修改病虫害信息</h5>
        </div>
		<div class="pull-right">
            <p>
            </p>
		</div>
    </div>
</div>
<div id="dialog-message" class="hide">
        <div class="col-xs-12">
           <div class="widget-box">
			<div class="widget-body">
				<div class="widget-main">
                    <div class="searchbar">
                        <form class="form-horizontal" role="form" id="Form1">
                             <!-- 第一行-->
                            <div class="form-group" style="margin-bottom:0px;width:500px;">
                                表格名称：
						         <input id="name1" style="width:150px;height:30px;"/> 
						        <a class="btn btn-sm btn-white  btn-default btn-bold " id="A1" onclick="searchtable()"><i class="fa fa-search"></i> 搜索</a>
                                
                            </div>
	
                        </form>
                    </div>
				</div>
			</div>
		</div>
        <div style="margin-top:10px">
		<table id="myDataTable" class="table table-striped table-bordered table-hover">
			</table>
             <div id="pager10" style="height:50px"></div>
        </div>

        </div>
</div>
<div class="row">
    <div class="col-xs-12" style="margin-top:30px">
        <form class="form-horizontal" role="form" id="validation-form">
        <table style=" border:0.5px solid; width:560px; margin-left:160px;" >

        <tr style=" border:0.5px solid #858585;height:40px;">
        <td align="center"  style=" border:0.5px solid #858585; width:160px;">
         <label  style="text-align:right;">&nbsp;&nbsp;病虫害名称:&nbsp;</label>
        </td>
        <td align="left"  style=" border:0.5px solid #858585; width:400px;">
        <input id="name" style=" width:300px;height:32px;"/>
        </td>
         <td style=" border:0.5px solid #FFF;height:100px;">
        
        </td>
        </tr>
         <tr style=" border:0.5px solid #858585;height:100px;">
        <td align="center"  style=" border:0.5px solid #858585">
         <label class=" control-label no-padding-right"  >形态特征：</label>
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
           <textarea id="info1" style="width:395px;margin-left:2px; margin-top:2px;height:100px;"></textarea>
        </td>
         <td style=" border:0.5px solid #FFF;height:100px;">
        
        </td>
        </tr>
        <tr style=" border:0.5px solid #858585;height:100px;">
        <td align="center"  style=" border:0.5px solid #858585">
         <label class="control-label no-padding-right" >危害特点：</label>
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
           <textarea id="info2" style="width:395px;margin-left:2px; margin-top:2px;height:100px;"></textarea>
        </td>
         <td style=" border:0.5px solid #FFF;height:100px;">
        
        </td>
        </tr>
        <tr style=" border:0.5px solid #858585;height:100px;">
        <td align="center"  style=" border:0.5px solid #858585">
         <label class="control-label no-padding-right" >生活习性：</label>
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
           <textarea id="info3" style="width:395px;margin-left:2px; margin-top:2px;height:100px;"></textarea>
        </td>
         <td style=" border:0.5px solid #FFF;height:100px;">
        
        </td>
        </tr>
        <tr style=" border:0.5px solid #858585;height:100px;">
        <td align="center"  style=" border:0.5px solid #858585">
        <label class="control-label no-padding-right" >发生规律：</label>
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
           <textarea id="info4" style="width:395px;margin-left:2px; margin-top:2px;height:100px;"></textarea>
        </td>
         <td style=" border:0.5px solid #FFF;height:100px;">
        
        </td>
        </tr>
       <tr style=" border:0.5px solid #858585;height:100px;">
        <td align="center"  style=" border:0.5px solid #858585">
         <label class="control-label no-padding-right" >防治方法：</label>
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
           <textarea id="info5" style="width:395px;margin-left:2px; margin-top:2px;height:100px;"></textarea>
        </td>
        <td style=" border:0.5px solid #FFF;height:100px;">
        
        </td>
        </tr>
              <tr style=" border:0.5px solid #858585;height:100px;">
        <td align="center"  style=" border:0.5px solid #858585">
        <label class="control-label no-padding-right" >发病规律：</label>
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
           <textarea id="info6" style="width:395px;margin-left:2px; margin-top:2px;height:100px;"></textarea>
        </td>
        <td style=" border:0.5px solid #FFF;height:100px;">
        
        </td>
        </tr>
        <tr style=" border:0.5px solid #858585;height:100px;">
        <td   style=" border:0.5px solid #858585">
        <div align="center">
         <label class=" control-label no-padding-right" >典型图片：</label><br />
         </div>
        <div  style="float:left;margin-left:13px;"><input type="file" class="upfiles" id="uploadify1" style="width:80px;" name="carImg2" /></div> 
         <div style="float:left;margin-left:13px;"><a class="btn btn-sm btn-white  btn-default btn-bold " id="deletedimg" onclick="deletedimg()"style="width:120px; font-size: small"> 删除图片</a></div>
                
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
        <div id="tupian" style="height:150px;width:400px;OVERFLOW:scroll" class="divScroll">
          <input type="hidden" id="img" class="img"  name="img" value="" />
            <input type="hidden" id="number" class="img"  name="img" value="0" />
               <input type="hidden" id="number1" class="img"  name="img" value="0" />
                <input type="hidden" id="number2" class="img"  name="img" value="0" />
        </div>
         
        </td>
        <td style=" border:0.5px solid #FFF;height:100px;">
          <div style="float:left;margin-left:13px;"><a class="btn btn-sm btn-white  btn-default btn-bold " id="A3" onclick="selecttupians()"style="width:120px; font-size: small"> 选择全部</a></div>
              
        </td>
        </tr>
        <tr style=" border:0.5px solid #858585;height:160px;text-align:center ">
        <td align="center"  style=" border:0.5px solid #858585;">
         <label class="" >测报表格：</label><br />
         <!--input type="button" class=" " id="Button1" value="" --/-->
         <div style="float:left;margin-left:10px;"><a class=" btn btn-sm btn-white  btn-default btn-bold" id="deletedtable" onclick="deletedtable()"style="width:60px; font-size: small">删除 </a></div>&nbsp;
          <div style="float:left; margin-left:10px;"><a class="btn btn-sm btn-white  btn-default btn-bold " id="addtable" onclick="addtable()"style="width:60px; font-size: small">添加 </a></div>
        </td>
        <td align="left"  style=" border:0.5px solid #858585">
         <div style="height:160px;width:400px;OVERFLOW:scroll">
           <input id="table1" type="hidden" value="" />
              <input id="table2" type="hidden" value="" />
         </div>
        </td>
         <td style=" border:0.5px solid #FFF;height:100px;">
          <div style="float:left;margin-left:13px;"><a class="btn btn-sm btn-white  btn-default btn-bold " id="A4" onclick="selecttables()"style="width:120px; font-size: small"> 选择全部</a></div>
              
        </td>
        </tr>
        </table>
         
      <div  style=" margin-left:500px; margin-top:10px;">
                <input id="text1" readonly="readonly"  value=""  style="width:300px; display:none; color:Red; margin-left:40px; border:0px; font-size:larger; "   />
                <div style=" margin-left:60px;">
                
		            <a class="btn btn-sm btn-info" id="sub" onclick=""style=" "><i class="fa fa-save"></i> 修改</a>
                
		            <a class="btn btn-sm " id="A2" onclick="comeback();" style=" margin-left:50px;"><i class="fa fa-times"></i> 取消</a>
                </div>  
            </div> 
            </br>
<%--             <div class="form-group" style="margin-bottom:0px">
                <span class="col-sm-5"></span>
                <input id="text1" readonly="readonly"  value=""  style="width:300px; display:none; color:Red; margin-left:40px; border:0px; font-size:larger; "   />
                <div class="col-sm-1" style="">
		            <button class="btn btn-sm btn-info" id="sub" onclick="" ><i class=""></i> 修改</button>
                </div>   
                <div class="col-sm-1" style="">
		            <a class="btn btn-sm" id="A2" onclick="comeback();" href="#"><i class=""></i> 取消</a>
                </div>   
            </div>--%>
        </form>
    </div>
</div>

<div id="tabledlg" class="hide">
    <form class="form-horizontal" role="form" id="form2">
        <div class="col-xs-12">
            <div class="form-group " id="" >
                <table border="1" cellspacing="0" cellpadding="0" width="100%" id="tablediv">
                	
                </table>
                <div id="Div1" style="height:50px"></div>
            </div>
        </div>    
    </form>
</div>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">

     <link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/js/uploadify/uploadify.css" /> 
     
    <link rel="stylesheet" type="text/css" href="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.min.css" />  
    <link href="<%= ViewData["rootUri"] %>Content/css/ui.jqgrid.css" rel="Stylesheet"/> 
    <link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/jquery-ui.min.css" /> 
    <link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/select2.css" /> 

    </asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
    <script type="text/javascript"  src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/jqGrid/i18n/grid.locale-cn.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="<%= ViewData["rootUri"] %>Content/js/jquery.ui.touch-punch.min.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>  
    <script src="<%= ViewData["rootUri"] %>Content/js/uploadify/jquery.uploadify.js"></script>
    <script src="<%= ViewData["rootUri"] %>Content/js/uploadify/jquery.uploadify.min.js"></script>
    
    <script type="text/javascript">
          $(document).ready(function () {
            $("#number").val(0);
            $("#img").val("");
            $("#table1").val("");
             $("#number1").val(0);
             $("#table2").val("");
             $("#number2").val("0");
            var name = "";
             var number=$("#number").val();
                 number=parseInt(number);
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
                 
                 var img=ret.photo_list.toString().split(',');
                 for (var s in img) {
                    if (img[s] != "") {
                    number=number+1;
                     $("#img").before('<input type="checkbox"  onchange="imagechange(this.value)" id="img'+number+'" value="'+number+'" style=" margin-left:5px;margin-top:5"/><img src="<%= ViewData["rootUri"]%>Content/Images/default_img.jpg" id ="picture'+number+'" height="130px" width="106px" value="'+img[s]+'" />');
                    $("#picture"+number+"").attr("src", "<%= ViewData["rootUri"]%>"+img[s]);
                    $("#picture"+number+"").val(img[s]);
                          var numer2=$("#number2").val();
                    $("#number2").val(numer2+","+number);
                     }
                    
                 }
                 if (ret.form_ids!="") {
                     $.ajax({
                     url: '<%= ViewData["rootUri"] %>DiseasePest/ViewTableInfo?form_ids='+ret.form_ids,
                     dataType: 'json',
                     success: function (ret) {
                     var tables="";
                        for (var s in ret) {
                                $("#table1").before("<div id='t"+ret[s].uid+"' style='width:375px;height:30px;border:0.5px solid #858585'><input type='checkbox' onchange='tablechange(this.value)' id='check"+ret[s].uid+"' value='"+ret[s].uid+"'/><span style='font:20px; ' onclick='showtable("+ret[s].uid+")'>&nbsp;" + ret[s].name + "</span></div>");
                                tables=tables+","+ret[s].uid;
                            }
                            $("#table1").val(tables);
                      }
                     });
                   }
                
                }
            });

              $('#uploadify1').uploadify({
                'buttonText': "添加图片",
                //'queueSizeLimit': 1,  //设置上传队列中同时允许的上传文件数量，默认为999
                //'uploadLimit': 1,   //设置允许上传的文件数量，默认为999
                'swf':'<%= ViewData["rootUri"] %>Content/plugins/uploadify/uploadify.swf',

	            'fileTypeExts': '*.jpg;*.png;*.jpeg',
	            'fileTypeDesc': 'Image Files (.jpg,.png,*.jpeg)',
	            'fileSizeLimit': '4MB',

                'uploader': '<%= ViewData["rootUri"] %>DiseasePest/UploadifyImageS',
                'onUploadComplete': function (file) {   //单个文件上传完成时触发事件
                	name=file.name;
                },
                'onQueueComplete': function (queueData) {   //队列中全部文件上传完成时触发事件
                },
                'onUploadSuccess': function (file, data, response) {    //单个文件上传成功后触发事件                                     
                    //alert('文件 ' + file.name + ' 已经上传成功，并返回 ' + response + ' 保存文件名称为 ' + data.SaveName);
                
                  number=number+1;
                   data = data.replace(/\"/,"");
                    data = data.replace(/\"/,"");
                    var imgdata=$("#img").val();
                    imgdata=data+","+imgdata;
                     $("#img").before('<input type="checkbox" onchange="imagechange(this.value)" id="img'+number+'" value="'+number+'"  style=" margin-left:5px;margin-top:5"/><img src="<%= ViewData["rootUri"]%>Content/Images/default_img.jpg" id ="picture'+number+'" height="130px" width="106px" value="'+data+'" />');
                  
                    $("#img").val(imgdata);
                    $("#picture"+number+"").attr("src", "<%= ViewData["rootUri"]%>"+data);
                     $("#picture"+number+"").val(data);
                    $("#number").val(number);
                    var numer2=$("#number2").val();
                    $("#number2").val(numer2+","+number);
                }
            });

             $("#myDataTable").jqGrid({
                viewsortcols: [false, 'vertical', false],
                url: ' <%= ViewData["rootUri"] %>DiseasePest/FindAllTable',
                datatype: "json",
                //  rowNum: 20,
                height: 340,
                width: 600,
                mtype: 'post',
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
                multiselect:true,
                postData: {
                    name: $("#name1").val(),
                },
                colNames: ['序号', '表名称'],
                colModel: [
                            { name: 'uid', index: 'operat_people', width: 250, align: "center" },
                            { name: 'name', index: 'operat_time', width: 400, align: "center" }
                        ]
            });
             $("#myDataTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });
     
           $("#sub").click(function () {
         //   type1=$("#type1").val();
           // type2=$("#type2").val();
            type=$("input[name=type]:checked").val();
            name=$("#name").val();
             info1=$("#info1").val();
             info2=$("#info2").val();
             info3=$("#info3").val();
             info4=$("#info4").val();
             info5=$("#info5").val();
             info6=$("#info6").val();
             img=$("#img").val();
            table1=$("#table1").val();
             if((name==null||name=="")||(info1==null||info1=="")||(info2==null||info2=="")||(info3==null||info3=="")||(info4==null||info4=="")||(info5==null||info5=="")||(info6==null||info6=="")||(img==null||img=="")||(table1==null||table1==""))
            {
               document.getElementById("text1").style.display="block";
               $("#text1").val("****基本信息不能为空****");
            }
            else
            {
                document.getElementById("text1").style.display="none";
                submitform(type,name,info1,info2,info3,info4,info5,info6,img,table1);
             }

        });
        });

        function comeback(){
        window.location="<%= ViewData["rootUri"] %>DiseasePest/DiseasePestInfo"
        }
        function tablechange(value){
          
          var table=$("#table2").val();
          if(document.getElementById("check"+value+"").checked==true){
          table=value+","+table;
          $("#table2").val(table);
          }
          if(document.getElementById("check"+value+"").checked==false){
          var number=table.split(",");
          var values="";
               for(var s in number){
                  if (number[s]==value) {
   
                  }else{
                  values=number[s]+","+values;
                  }
               }
               $("#table2").val(values);
          }
       }
        function imagechange(value){
          var table1=$("#number1").val();
          if(document.getElementById("img"+value+"").checked==true){
                  table1=value +","+table1;
                  $("#number1").val(table1);
               }
          
          if(document.getElementById("img"+value+"").checked==false){
          var number2=table1.split(",");
          var values="0";
               for(var s in number2){
                   if (number2[s]!=0&&number2[s]!=value) {
                    values=number2[s]+","+values;
                   }else{
                   values=values;
                   }
               }
               $("#number1").val(values);
         }
        }
        function deletedimg(){
        var number1=$("#number1").val();
        
         var number2=number1.split(",");
       
        
          for(var s in number2){
          if (number2[s]!=0) {
           var img1=$("#picture"+number2[s]+"").val();
           $("#picture"+number2[s]+"").remove();
            var imgs2="";
           $("#img"+number2[s]+"").remove();
           $("#number1").val(0);
            var img=$("#img").val();
         var imgs=img.split(",");
          for(var k in imgs){
          if (img1==imgs[k]) {
          
          }else{
          imgs2=imgs2+","+imgs[k];
          }
          }
            $("#img").val(imgs2); 
          }
          }
         

        }
           function addtable() {
            plandlg = $("#dialog-message").removeClass('hide').dialog({
                modal: true,
                width: 670,
                //                title: "<div class='widget-header widget-header-small'><h4 class='smaller'>年度测报计划</h4></div>",
                title: "添加表格信息",
                title_html: true,
                title:false,
                buttons: [
					{
					    text: "  确 定  ",
					    "class": "btn btn-primary btn-xs",
					    click: function () {
                        AddTablesForm();
					        plandlg.dialog("close");
					    }
					},
					{
					    text: "  取 消  ",
					    "class": "btn btn-xs",
					    click: function () {
					        $(this).dialog("close");
					    }
					}
				]
            });

        }
           function searchtable(){
         var PostData = { name: $("#name1").val() };
            var tabl = $("#myDataTable");
            tabl.setGridParam({  url: ' <%= ViewData["rootUri"] %>DiseasePest/FindAllTable', postData: PostData }).trigger('reloadGrid');

        }
        function AddTablesForm(){
         var rowIds = jQuery("#myDataTable").jqGrid('getGridParam', 'selarrrow');
            var tids = "";
            var id = [];
            $.each(rowIds, function (index, rowIds) {
            var rowData = $("#myDataTable").jqGrid('getRowData',rowIds);
                id.push(rowData.uid);
                id.join(",");
            });
            tids += id + ",";
            if (tids != ",") {
                
                    $.ajax({
                        url: '<%= ViewData["rootUri"] %>DiseasePest/ViewTableInfo',
                        data: { "form_ids": tids},
                        type: "post",
                        success: function (ret) {
                         for (var s in ret) {
                         var form_ids= $("#table1").val();
                          var ids=form_ids.split(",");
                          var num=0;
                          for(var ki in ids){
                          if (ids[ki]==ret[s].uid) {
                          num=1;
                          }
                          }
                          if (num==0) {
                           var form4=$("#table1").val();
                          var  values =form4+","+ret[s].uid;
                            $("#table1").before("<div id='t"+ret[s].uid+"'style='width:375px;height:30px;border:0.5px solid #858585'><input type='checkbox' onchange='tablechange(this.value)' id='check"+ret[s].uid+"' value='"+ret[s].uid+"'/><span style='font:20px;'>&nbsp;" + ret[s].name + "</span></div>");
                         $("#table1").val(values)
                         }
                         }
                        }
                    });
            } else {
                alert("你还没有选择！");
            }
        }
        function submitform(type,name,info1,info2,info3,info4,info5,info6,img,table1){
          $.ajax({
          url:'<%= ViewData["rootUri"] %>DiseasePest/ModifyDiseasePestInfo',
            dataType: 'json',
            method:'post',
            data:{"name":name,"info1":info1,"info2":info2,"info3":info3,"info4":info4,"info5":info5,"info6":info6,"img":img,"table1":table1,"uid":<%= ViewData["uid"] %>},
            success: function (ret) {
                    if (ret==true) {
                       toas1();
                                setTimeout(function(){
                                window.location='<%= ViewData["rootUri"] %>DiseasePest/DiseasePestInfo'
                            }, 4000);
                   
                    }else{
                    toas2();
                    }
                }
          });
        }
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

        function deletedtable(){
        
        var tables2=$("#table2").val();
        var table3=tables2.split(",");
        for(var s in table3){
        var tables1=$("#table1").val();
           table4=tables1.split(",");
           var number="";
           for(var k in table4){
             if (table4[k]==table3[s]) {
             }else{
             number=number+","+table4[k];
             }
           }
           if (table3[s]!="") {
           $("#t"+table3[s]+"").remove();
           }
            

            $("#table1").val(number);
          //  var obj = document.getElementById(""+table3[s]+"");
         //   document.body.removeChild(obj);
        }
        $("#table2").val("");
        }
              function toas1() {
           toastr.options = {
				            "closeButton": false,
				            "debug": true,
				            "positionClass": "toast-top-right",
				            "onclick": null,
				            "showDuration": "3",
				            "hideDuration": "3",
				            "timeOut": "4000",
				            "extendedTimeOut": "4000",
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
				            "positionClass": "toast-top-right",
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
        function selecttupians(){
         $("#number1").val($("#number2").val());
            var tables= $("#number2").val().split(",");
        for(var s in tables){
        var numb="img"+tables[s]+"";
        if (tables[s]!=0&&tables[s]!=""&&tables[s]!=null) {
        document.getElementById(""+numb+"") .checked = true;
        }
         
        }

        }
        function selecttables(){
         var tables= $("#table1").val().split(",");
         $("#table2").val(tables);
        for(var s in tables){
        var numb="check"+tables[s]+"";
        if (tables[s]!=null&&tables[s]!="") {
        document.getElementById(""+numb+"") .checked = true;
        }
         
        }
        
        }
    </script>
</asp:Content>
