<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header">
	<div class="">
        <div class="col-xs-2 no-padding-left">
            <h5>添加病虫害信息</h5>
        </div>
		<div class="pull-right">
            <p>
            </p>
		</div>
    </div>
</div>

<div class="row" >
<div class="col-xs-12">
        <div class="widget-box">
			<div class="widget-body">
				<div class="widget-main">
                    <div class="searchbar">
                        <form class="form-horizontal" role="form" id="Form1">
                            <!-- 第一行-->
                            <div class="form-group" style="margin-bottom:0px;margin-left:160px ">
                                <label class="col-sm-1 control-label no-padding-center" for="" style=" width:120px">病虫害类别：</label>
				                <div class="col-sm-2" style="width:140px;">
                                    <div class="clearfix">
						                <select class="select2" id="Select4" name="" data-placeholder="请选择" onchange="changeDisease(this.value)" >
                                            <option value="1">虫害</option>
                                            <option value="0">病害</option>                      
				                        </select>
                                    </div>
				                </div>  
                                <label class="col-sm-1 control-label no-padding-center" for=""  style=" width:120px">病虫害名称：</label>
				                <div class="col-sm-2" style="width:140px;">
                                    <div class="clearfix">
						               <select class="" id="Select1" name="" data-placeholder="请选择" onchange="changeselect(this.value)" >                  
				                       </select>
                                    </div>
				                </div>  
                                <div class="col-sm-1" style="width:120px;" id="d1">
						        <a class="btn btn-sm btn-info " id="searchdata" onclick="SearchData()"><i class="ace-icon fa fa-search blue"></i> 添加</a>
                                </div> 
                                  <div class="col-sm-1" style="width:120px; display:none" id="d2">
						        <a class="btn btn-sm btn-info " id="A3" onclick="SearchData1()"><i class="fa fa-edit"></i> 自定义</a>
                                </div>    
                            </div>
	
                        </form>
                    </div>
				</div>
			</div>
		</div>
    </div>
    <div class="col-xs-12" style="margin-top:30px;display:none" id="hiden">
        <form class="form-horizontal" role="form" id="validation-form">
        <table style=" width:560px; margin-left:160px ">
       <!-- <tr style=" border:0.5px solid #858585;height:40px;">
        <td align="center"  style=" border:0.5px solid #858585; ">
           <label >&nbsp;&nbsp;病虫害类别:&nbsp;</label>
        </td>
        <td align="left"  style=" border:0.5px solid #858585; width:400px;">
         <div class="col-sm-1" style="display:inline-block; width:90px">
			        <label>
				        <input id="type1" name="type" type="radio" value="0" checked="checked" />
				        <span class="lbl">病害</span>
			        </label>
		        </div>
		        <div class="col-sm-2" style="display:inline-block;width:90px">
			        <label>
				        <input id="type2" name="type" type="radio" value="1" />
				        <span class="lbl">虫害</span>
			        </label>
                    
		        </div>
        </td>
        </tr-->
        <tr style=" height:40px;">
        <td align="center"  style="width:160px;">
         <span style=" font-family:Microsoft YaHei;  font-size:1.1em">病虫名称:</span>
        </td>
        <td align="left"  style=" width:400px;">
        <input id="name" style=" width:100%;height:32px;"/>
          <input id="type" style=" width:300px;height:32px; display:none"/>
        </td>
        </tr>
         <tr style="height:100px;">
        <td align="center"  style="">
          <span style=" font-family:Microsoft YaHei;  font-size:1.1em">形态特征:</span>
        </td>
        <td align="left"  style="">
           <textarea id="info1" style="width:100%;margin-left:2px; margin-top:2px;height:100px;"></textarea>
        </td>
        </tr>
        <tr style="height:100px;">
        <td align="center"  style="">
       <span style=" font-family:Microsoft YaHei;  font-size:1.1em">危害特点:</span>
        </td>
        <td align="left"  style="">
           <textarea id="info2" style="width:100%;margin-left:2px; margin-top:2px;height:100px;"></textarea>
        </td>
        </tr>
        <tr style="height:100px;">
        <td align="center"  style=" ">
          <span style=" font-family:Microsoft YaHei;  font-size:1.1em">生活习性:</span>
        </td>
        <td align="left"  style="">
           <textarea id="info3" style="width:100%;margin-left:2px; margin-top:2px;height:100px;"></textarea>
        </td>
        </tr>
        <tr style="height:100px;">
        <td align="center"  style="">
         <span style=" font-family:Microsoft YaHei;  font-size:1.1em">发生规律:</span>
        </td>
        <td align="left"  style="">
           <textarea id="info4" style="width:100%;margin-left:2px; margin-top:2px;height:100px;"></textarea>
        </td>
        </tr>
       <tr style="height:100px;">
        <td align="center"  style="">
          <span style=" font-family:Microsoft YaHei;  font-size:1.1em">防治方法:</span>
        </td>
        <td align="left"  style="">
           <textarea id="info5" style="width:100%;margin-left:2px; margin-top:2px;height:100px;"></textarea>
        </td>
        </tr>
           <tr style=" height:100px;">
        <td align="center"  style=" ">
          <span style=" font-family:Microsoft YaHei;  font-size:1.1em">发病条件:</span>
        </td>
        <td align="left"  style="">
           <textarea id="info6" style="width:100%;margin-left:2px; margin-top:2px;height:100px;"></textarea>
        </td>
        </tr>
        <tr style="height:100px;">
        <td   style="">
        <div align="center">
          <span style=" font-family:Microsoft YaHei;  font-size:1.1em">典型图片:</span><br />
         </div>
        <div  style=" margin-left:13px;"><input type="file" class="upfiles" id="uploadify1" name="carImg2" /></div> 
        </td>
        <td align="left"  style="">
        <div id="tupian" style="height:150px;width:400px;OVERFLOW:auto" class="divScroll">
            <!--img src="<%= ViewData["rootUri"] %>Content/Images/default_img.jpg" id ="picture" height="130px" width="106px" style=" margin-left:10px; margin-top:5"/-->
            <input type="hidden" id="img" class="img"  name="img" value="" />
            <input type="hidden" id="number" class="img"  name="img" value="0" />
        </div>
        
        </td>
        </tr>
        <tr style="height:160px;">
        <td align="center"  style="">
          <span style=" font-family:Microsoft YaHei;  font-size:1.1em">测报表格:</span><br />
         <!--input type="button" class=" " id="Button1" value="" --/-->
         <div style="float:left;margin-left:10px;"><a class=" btn btn-sm btn-white  btn-default btn-bold" id="deletedtable" onclick="deletedtable()"style="width:60px; font-size: small">删除 </a></div>&nbsp;
          <div style="float:left; margin-left:10px;"><a class="btn btn-sm btn-white  btn-default btn-bold " id="addtable" onclick="addtable()"style="width:60px; font-size: small">添加 </a></div>
        </td>
        <td align="left"  style="">
         <div style="height:160px;width:400px;OVERFLOW:auto">
           <input id="table1" type="hidden" value="" />
              <input id="table2" type="hidden" value="" />
         </div>
        </td>
        </tr>
        <!--tr style=" border:0.5px solid #858585;height:160px;">
        <td align="center"  style=" border:0.5px solid #858585">
         <label class=" control-label no-padding-right" >测报表格：</label><br />
         <!--input type="button" class=" " id="Button1" value="" --/-->
        <!--/td>
        <td align="left"  style=" border:0.5px solid #858585">
         <div style="height:160px;width:400px;OVERFLOW:scroll">
           <input id="table1" type="hidden" value="" />
         </div>
        </td>
        </tr-->
        </table>

          <input id="text1" readonly="readonly" style="margin-top:10px;text-align:center;width:50%;height:20px; border-style:none;display:none;margin-left:150px;color: Red" />

     
            <div  style=" margin-left:140px; margin-top:10px;">
               <input id="text1" readonly="readonly"  value=""  style="width:300px; display:none; color:Red; margin-left:40px; border:0px; font-size:larger; "   />
            
                <div style=" margin-left:30%">
		            <a class="btn btn-sm btn-info" id="sub" onclick=""style=""><i class="fa fa-save"></i> 保存</a>
                
		            <a class=" btn btn-sm " id="A2" onclick="comeback();" style="margin-left:50px;"><i class="fa fa-times"></i> 取消</a>
                </div>  
            </div> 
           <%-- </br>
             <div class="form-group" style="margin-bottom:0px">
                <span class="col-sm-5"></span>
                <div class="col-sm-1" style="">
		            <button class="btn btn-sm btn-info" id="sub" onclick="" ><i class=""></i> 保存</button>
                </div>   
                <div class="col-sm-1" style="">
		            <a class="btn btn-sm" id="A2" onclick="comeback();" href="#"><i class=""></i> 取消</a>
                </div>   
            </div>--%>
        </form>
    </div>
</div>
<div id="dialog-message" class="hide">
        <div class="col-xs-12">
           <div class="widget-box">
			<div class="widget-body">
				<div class="widget-main">
                    <div class="searchbar">
                        <form class="form-horizontal" role="form" id="Form2">
                             <!-- 第一行-->
                            <div class="form-group" style="margin-bottom:0px;width:600px;">
                                表格名称：
						               <input id="name1" style="width:150px;height:30px;"/> 
						        <a class="btn btn-sm btn-white  btn-default btn-bold " id="A1" onclick="searchtable()"><i class="fa fa-search"></i> 搜索</a>
                                
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
     $("#number").val("0");
       var number=$("#number").val();
       number=parseInt(number);
        jQuery(function ($) {  
              $(".select2").css('width', '120px').select2({ minimumResultsForSearch: -1 });
              $("#Select1").css('width', '120px').select2({ allowClear:true});
              
              

            
        });

       $(document).ready(function() {
         document.getElementById("Select1").length=0;
            $.ajax({
            url:'<%= ViewData["rootUri"] %>DiseasePest/FindAllDesInfo',
            dataType: 'json',
            data:{"kind":2},
            success: function (ret) {

                    for(var s in ret) {
                           if (s == 0) {
                                $("#Select1").append("<option value=" + ret[s].name+":"+ret[s].type+ " selected='selected'>" + ret[s].name + "</option>");  
                                $("#Select4 option[value='"+ret[s].type+"']").attr("selected", "selected");  
                                $("#Select1").css('width', '120px').select2({ allowClear:true});
                                $("#Select4").css('width', '120px').select2({ allowClear:true, minimumResultsForSearch: -1  });
                            } else {
                                $("#Select1").append("<option value=" + ret[s].name +":"+ret[s].type+ ">" + ret[s].name + "</option>");
                            }
                    }
                          $("#Select1").append("<option value='新建'>新建</option>");
                          
                          
                }
            });

       $("#number").val(0);
       $("#img").val("");
       $("#table1").val("");
    
       var name="";
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
                 
               //  if (number==0) {
             //     data = data.replace(/\"/,"");
               //   data = data.replace(/\"/,"");
              //      var imgdata=$("#img").val();
              //      imgdata=data+","+imgdata;
              //     $("#img").val(imgdata);
              //      $("#picture").attr("src", "<%= ViewData["rootUri"] %>"+data);
               //     number=number+1;
                //    $("#number").val(number);
               //  }else if (number!=0){
                  $("#img").before('<img src="<%= ViewData["rootUri"]%>Content/Images/default_img.jpg" id ="picture'+number+'" height="130px" width="106px" style=" margin-left:10px;margin-top:5"/>');
                    data = data.replace(/\"/,"");
                    data = data.replace(/\"/,"");
                    var imgdata=$("#img").val();
                    imgdata=data+","+imgdata;
                    $("#img").val(imgdata);
                    $("#picture"+number+"").attr("src", "<%= ViewData["rootUri"]%>"+data);
                    number=number+1;
                    $("#number").val(number);
                  //  }
                }
            });

            //$.ajax({
           // url:'<%= ViewData["rootUri"] %>DiseasePest/FindTables',
           // dataType: 'json',
          //  success: function (ret) {
           //         for(var s in ret) {
            //         $("#table1").before("<div style='width:375px;height:30px;border:0.5px solid #858585'><input type='checkbox' onchange='tablechange(this.value)' id='check"+ret[s].uid+"' value='"+ret[s].uid+"'/><span style='font:20px;'>&nbsp;"+ret[s].name+"</span></div>");
           //         }
          //      }
        //    });
            $("#sub").click(function () {
        
            type=   $("#type").val();
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
     

  });

 /* function tablechange(value){
 
  var table=$("#table1").val();
  if(document.getElementById("check"+value+"").checked==true){
  table=value+","+table;
  $("#table1").val(table);
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
       $("#table1").val(values);
  }
  

  }*/
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
      function submitform(type,name,info1,info2,info3,info4,info5,info6,img,table1){
      $.ajax({
      url:'<%= ViewData["rootUri"] %>DiseasePest/AddDiseasePestInfo',
        dataType: 'json',
        method:'post',
        data:{"type":type,"name":name,"info1":info1,"info2":info2,"info3":info3,"info4":info4,"info5":info5,"info6":info6,"img":img,"table1":table1},
        success: function (ret) {
                if (ret=="success") {
                window.location='<%= ViewData["rootUri"] %>DiseasePest/DiseasePestInfo'
                }else if (ret=="error") {
                alert("添加失败,不能重复添加病虫害信息");
                }else{
                alert("添加失败");
                }
            }
      });
      }
       function comeback(){
            window.location="<%= ViewData["rootUri"] %>DiseasePest/DiseasePestInfo"
            }

        function changeselect(value){
if (value=="新建") {
document.getElementById('d1').style.display="none";
document.getElementById('d2').style.display="block";
document.getElementById("hiden").style.display="block";
}else{document.getElementById('d1').style.display="block";
document.getElementById('d2').style.display="none";}
        var type=value.split(":")[1];
            if (type==0){
               $("#Select4 option[value='1']").removeAttr("selected");
         }
           if (type==1) {
             $("#Select4 option[value='0']").removeAttr("selected");    
         }
          $("#Select4 option[value='"+type+"']").attr("selected", "selected"); 
           $("#Select4").css('width', '120px').select2({ allowClear:true, minimumResultsForSearch: -1  });
        }
       function SearchData1(){
          var imagew= $("#img").val();
        $("#img").val("");
          $("#number").val("0");
          if (imagew=="") {
          }else{
           document.getElementById('tupian').innerHTML="" ; 
            document.getElementById('tupian').innerHTML='     <!--img src="<%= ViewData["rootUri"] %>Content/Images/default_img.jpg" id ="picture" height="130px" width="106px" style=" margin-left:10px; margin-top:5"/--> <input type="hidden" id="img" class="img"  name="img" value="" />  <input type="hidden" id="number" class="img"  name="img" value="0" />' ; 
          }
            $("#name").val("");
                   $("#info6").val("");
                   $("#info2").val("");
                   $("#info3").val("");
                   $("#info4").val("");
                   $("#info5").val("");
                   $("#info1").val("");
       
        }



        function SearchData(){
        var imagew= $("#img").val();
        $("#img").val("");
          $("#number").val("0");
          if (imagew=="") {
          }else{
           document.getElementById('tupian').innerHTML="" ; 
            document.getElementById('tupian').innerHTML='     <!--img src="<%= ViewData["rootUri"] %>Content/Images/default_img.jpg" id ="picture" height="130px" width="106px" style=" margin-left:10px; margin-top:5"/--> <input type="hidden" id="img" class="img"  name="img" value="" />  <input type="hidden" id="number" class="img"  name="img" value="0" />' ; 
          }
         
        document.getElementById("hiden").style.display="block";
          var type=$("#Select1").val().split(":");
          if (type[1]==0) {
            $.ajax({
            url:'<%= ViewData["rootUri"] %>DiseasePest/GetJsonMassage2',
            dataType: 'json',
            data:{"name":type[0],"type":0},
            success: function (ret) {
              $("#name").val(type[0]);
                 $("#type").val(type[1]);
            for(var s in ret){
                   $("#name").val(ret[s].name);
                   $("#info6").val(ret[s].info6);
                   $("#info2").val(ret[s].info2);
                   $("#info3").val("无");
                   $("#info4").val(ret[s].info4);
                   $("#info5").val(ret[s].info5);
                   $("#info1").val("无");
                   var img=ret[s].imgphoto_list;
                   var img1=img.split(",");
                    for(var v in img1){
                        if (img1[v]!="") {
                             //  if (number==0) {
                             //        data = img1[v];
                             //       var imgdata=$("#img").val();
                             //       imgdata=data+","+imgdata;
                             //       $("#img").val(imgdata);
                             //       $("#picture").attr("src", "<%= ViewData["rootUri"] %>"+data);
                             //       number=number+1;
                             //       $("#number").val(number);
                            //    }else if (number!=0){
                                  $("#img").before('<img src="<%= ViewData["rootUri"]%>Content/Images/default_img.jpg" id ="picture'+number+'" height="130px" width="106px" style=" margin-left:10px;margin-top:5"/>');
                                    data = img1[v];
                                    var imgdata=$("#img").val();
                                    imgdata=data+","+imgdata;
                                    $("#img").val(imgdata);
                                    $("#picture"+number+"").attr("src", "<%= ViewData["rootUri"]%>"+data);
                                    number=number+1;
                                    $("#number").val(number);
                             //       }
                        }
                    }
                   }
                  }   
            });
            }
            if (type[1]==1) {
             $.ajax({
            url:'<%= ViewData["rootUri"] %>DiseasePest/GetJsonMassage1',
            dataType: 'json',
            data:{"name":type[0],"type":1},
            success: function (ret) {
              //   $("#name").val(ret[0].name);
                 $("#name").val(type[0]);
                 $("#type").val(type[1]);
                  $("#info1").val(ret[0].info1);
                  $("#info2").val("无");
                  $("#info3").val(ret[0].info3);
                  $("#info4").val(ret[0].info4);
                  $("#info5").val(ret[0].info5);
                  $("#info6").val("无");
                   var img=ret[0].imgphoto_list;
                   var img1=img.split(",");
                        for(var v in img1){
                            if (img1[v]!="") {
                                 //  if (number==0) {
                                 //        data = img1[v];
                                 //       var imgdata=$("#img").val();
                                  //      imgdata=data+","+imgdata;
                                  //      $("#img").val(imgdata);
                                 //       $("#picture").attr("src", "<%= ViewData["rootUri"] %>"+data);
                                  //      number=number+1;
                                   //     $("#number").val(number);
                                   // }else if (number!=0){
                                      $("#img").before('<img src="<%= ViewData["rootUri"]%>Content/Images/default_img.jpg" id ="picture'+number+'" height="130px" width="106px" style=" margin-left:10px;margin-top:5"/>');
                                        data = img1[v];
                                        var imgdata=$("#img").val();
                                        imgdata=data+","+imgdata;
                                        $("#img").val(imgdata);
                                        $("#picture"+number+"").attr("src", "<%= ViewData["rootUri"]%>"+data);
                                        number=number+1;
                                        $("#number").val(number);
                               //         }
                            }
                        }
                    }    
            });
                 
            }
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

                   function addtable() {
            plandlg = $("#dialog-message").removeClass('hide').dialog({
                modal: true,
                width: 650,
                //                title: "<div class='widget-header widget-header-small'><h4 class='smaller'>年度测报计划</h4></div>",
                title: "添加表格",
                title_html: true,
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

               function searchtable(){
         var PostData = { name: $("#name1").val() };
            var tabl = $("#myDataTable");
            tabl.setGridParam({  url: ' <%= ViewData["rootUri"] %>DiseasePest/FindAllTable', postData: PostData }).trigger('reloadGrid');

        }

        function changeDisease(value){
        document.getElementById("Select1").length=0;
             $.ajax({
            url:'<%= ViewData["rootUri"] %>DiseasePest/FindAllDesInfo',
            dataType: 'json',
            data:{"kind":value},
            success: function (ret) {

                    for(var s in ret) {
                           if (s == 0) {
                                $("#Select1").append("<option value=" + ret[s].name+":"+ret[s].type+ " selected='selected'>" + ret[s].name + "</option>");  
                                $("#Select4 option[value='"+ret[s].type+"']").attr("selected", "selected");  
                                $("#Select1").css('width', '120px').select2({ allowClear:true });
                                $("#Select4").css('width', '120px').select2({ allowClear:true, minimumResultsForSearch: -1  });
                            } else {
                                $("#Select1").append("<option value=" + ret[s].name +":"+ret[s].type+ ">" + ret[s].name + "</option>");
                            }
                    }
                        $("#Select1").append("<option value='新建'>新建</option>");
                          
                }
            });
        }
    </script>
</asp:Content>
