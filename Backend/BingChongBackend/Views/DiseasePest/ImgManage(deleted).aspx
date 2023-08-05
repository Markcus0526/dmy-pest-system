<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header">
	<div>
        <div class="col-xs-2 no-padding-left">
            <h5>图片管理</h5>
        </div>
		<div class="pull-right">
            <p>
                <a class="btn btn-sm btn-white  btn-default btn-bold" href="<%= ViewData["rootUri"] %>DiseasePest/AddImg">
	                <i class="ace-icon fa fa-plus blue"></i>添加
                </a>
                <a class="btn btn-white btn-warning btn-bold" style="display:none;" id="A1" onclick="processDel();">
                    <i class="ace-icon fa fa-trash-o bigger-120 orange"></i>批量删除
                </a>
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
                            <!-- 第一行-->
                            <div class="form-group" style="margin-bottom:0px">
                                <label class="col-sm-1 control-label no-padding-right" for="">病虫害类别：</label>
				                <div class="col-sm-2">
                                    <div class="clearfix">
						                <select class="select2" id="Select4" name="" data-placeholder="请选择">
                                            <option value="" selected="selected">全部</option>
                                            <option value="" ></option>                         
				                        </select>
                                    </div>
				                </div> 
                                <div class="col-sm-2"> 
                                  <input value="请输入名称"/>
				                </div>
                                <div class="col-sm-1" style="">
						         <a class="btn btn-sm btn-info" id="searchdata" onclick=""><i class="fa fa-search"></i> 搜索</a>
                                </div>   
                            </div>
	
                        </form>
                    </div>
				</div>
			</div>
		</div>
        <div style="margin-top:10px">
			<table id="tbldata" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
                        <th class="center">
							<label class="position-relative">
								<input type="checkbox" class="ace" />
								<span class="lbl"></span>
							</label>
						</th>
						<th class="center" style="width:70px">省/自治区</th>
						<th class="center" style="width:80px">盟/市</th>
						<th class="center" >区/县</th>
						<th class="center">测报点名称</th>
						<th class="center">病虫类别</th>
						<th class="center">病虫名称</th>
						<th class="center">上报时间</th>
                        <th class="center">测报员</th>
                        <th class="center"  style="width:270px">表格</th>
                        <th class="center">审核状态</th>
                        <th class="center" style="width:200px">操作</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
        </div>
    </div>
</div>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
	<link rel="stylesheet" href="<%= ViewData["rootUri"] %>Content/css/select2.css" />

</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
	<script src="<%= ViewData["rootUri"] %>Content/js/select2.min.js"></script>
    <script type="text/javascript">
        jQuery(function ($) {
            $(".select2").css('width', '100px').select2({ allowClear: true });

        });

    </script>
</asp:Content>
