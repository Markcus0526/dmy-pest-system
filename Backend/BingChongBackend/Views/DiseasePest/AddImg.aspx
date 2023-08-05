<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header">
	<div class="">
        <div class="col-xs-2 no-padding-left">
            <h5>上传图片</h5>
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
                <label class="col-sm-3 " style="text-align:right;"> 病虫类别:</label>
                <div class="col-sm-2">
                    <div class="clearfix">
						<select class="select2" id="Select4" name="" data-placeholder="请选择">
                            <option value="" selected="selected">病害</option>
                            <option value="" >虫害</option>                         
				        </select>
                    </div>
				</div> 
		        <label class="col-sm-1 " style="text-align:right;"> 名称:</label>
                <div class="col-sm-2">
                    <div class="clearfix">
						<select class="select2" id="Select1" name="" data-placeholder="请选择">
                            <option value="" selected="selected"></option>
                            <option value="" ></option>                         
				        </select>
                    </div>
				</div> 
            </div>
            </br>
            <div class="form-group profile" style="margin-bottom:0px">
                <label class="col-sm-3 control-label no-padding-right" >图片：</label>
				<div class="col-md-3">
                    <ul class="list-unstyled profile-nav">
                        <li>
                            <img src="<% if (false) { %><%= ViewData["rootUri"] %><% %><% } else { %><%= ViewData["rootUri"] %>Content/img/profile-img.png<% } %>"
                                class="img-responsive" alt="" id="previewimg" width="300px" height="300px" />
                            <input type="hidden" id="image" name="image" value="" />
                            <a href="#" class="profile-edit" id="upload_btn">修改</a> </li>
                    </ul>
                </div>
            </div>
            </br>
            <div class="form-group" style="margin-bottom:0px">
                <span class="col-sm-5"></span>
                <div class="col-sm-1" style="">
		            <a class="btn btn-sm btn-info" id="A1" onclick=""><i class=""></i> 保存</a>
                </div>   
                <div class="col-sm-1" style="">
		            <a class="btn btn-sm btn-info" id="A2" href="<%= ViewData["rootUri"] %>DiseasePest/ImgManage"><i class=""></i> 取消</a>
                </div>   
            </div>
        </form>  

    </div>


</div>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/select2.css" />
    <link href="<% =ViewData["rootUri"]%>Content/css/pages/profile.css" rel="stylesheet" type="text/css" />
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
	<script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>
    <script type="text/javascript">
        jQuery(function ($) {
            $(".select2").css('width', '100px').select2({ allowClear: true });

        });

    </script>
</asp:Content>
