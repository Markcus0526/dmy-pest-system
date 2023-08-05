<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header">
	<div class="">
        <div class="col-xs-2 no-padding-left">
            <h5>导入测报表格</h5>
        </div>
		<div class="pull-right">
            <p>
            </p>
		</div>
    </div>
</div>
<div class="row">
    <div class="col-xs-12">
		<form class="form-horizontal" role="form" id="validation-form">
			<div class="form-group">
				<label class="col-sm-3 control-label no-padding-right" for="company">导入文件<span class="red">*</span>：</label>
				<div class="col-sm-9">
                    <div class="clearfix">
                        <input type="button" class="btn btn-sm" id="btndetimg" value="选择文件" />
                        <img src="<%= ViewData["rootUri"] %>Content/img/loading.gif" style="display:none;" id="loadingimg">
                        <input type="hidden" name="fileurl" id="fileurl" />
                    </div>
                    <div style="margin:10px 0px;" id="divimglist"></div>
				</div>
			</div>
			<div class="clearfix form-actions">
				<div class="col-md-offset-3 col-md-9">
					<button class="btn btn-info loading-btn" type="submit" data-loading-text="提交中...">
						<i class="ace-icon fa fa-check bigger-110"></i>
						提交
					</button>

					&nbsp; &nbsp; &nbsp;
					<button class="btn" type="reset" >
						<i class="ace-icon fa fa-undo bigger-110"></i>
						重置
					</button>
				</div>
			</div>
        </form>	
        <span class="col-sm-1"></span>
        <div  class="col-sm-10" style="margin-top:10px">
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
