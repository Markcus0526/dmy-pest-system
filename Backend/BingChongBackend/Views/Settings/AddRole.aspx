<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header">
	<div>
        <div class="col-xs-2 no-padding-left">
            <h5>角色管理</h5>
        </div>
		<div class="pull-right">
            <p>
            </p>
		</div>
    </div>
</div>
<div class="row">
	<div class="col-xs-12">
		<form class="form-horizontal" id="validation-form">
			<div class="form-group">
				<label class="col-sm-3 control-label no-padding-right" for="rolename">角色名称：</label>
				<div class="col-sm-9">
                    <div class="clearfix">
					<input type="text" id="rolename" name="rolename" placeholder="请输入角色名称" class="input-large form-control"  <% if (ViewData["rolename"] != null) { %>value="<%= ViewData["rolename"] %>"<% } %> />
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label no-padding-right" >角色：</label>
				<div class="col-sm-8">
                    <div class="clearfix">
                    <table id="listTable" class="table table-bordered table-hover dataTable " style="clear:right; width:100%; margin:0px; padding:0px;">
                        <tbody>
                            <tr>
                                <td rowspan="3" style="width:200px; text-align:center;">
									<label>
										<input type="checkbox" class="ace setbtn ul-1" data-id="1" 
                                        <%--<% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("Judge") && 
                                        ViewData["role"].ToString().Split(',').Contains("Credit") && 
                                        ViewData["role"].ToString().Split(',').Contains("Document") && 
                                        ViewData["role"].ToString().Split(',').Contains("Mission") && 
                                        ViewData["role"].ToString().Split(',').Contains("Rule") && 
                                        ViewData["role"].ToString().Split(',').Contains("OnlineTest")
                                        ) { %> checked <% } %>--%> />
										<span class="lbl"> 病虫管理</span>
									</label>
						        </td>
				            </tr>
							<tr>
							    <td>
                                    <label>病虫害信息：</label>
									<input name="configuration" id="meta1-1" type="checkbox" class="ace indbtn li-1" data-id="1" value="AddBingchong" 
                                        <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("AddBingchong")) { %> checked <% } %> />
									<label class="lbl" for="meta1-1"> 新增</label>
                                    <input name="configuration" id="meta1-2" type="checkbox" class="ace indbtn li-1" data-id="1" value="ViewBingchong" 
                                        <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("ViewBingchong")) { %> checked <% } %> />
									<label class="lbl" for="meta1-2"> 查看</label>
                                    <input name="configuration" id="meta1-3" type="checkbox" class="ace indbtn li-1" data-id="1" value="UpdateBingchong" 
                                        <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("UpdateBingchong")) { %> checked <% } %> />
									<label class="lbl" for="meta1-3"> 修改</label>
                                    <input name="configuration" id="meta1-4" type="checkbox" class="ace indbtn li-1" data-id="1" value="DeleteBingchong" 
                                        <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("DeleteBingchong")) { %> checked <% } %> />
									<label class="lbl" for="meta1-4"> 删除</label>
                                    <input name="configuration" id="meta1-5" type="checkbox" class="ace indbtn li-1" data-id="1" value="BatchDeleteBingchong" 
                                        <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("BatchDeleteBingchong")) { %> checked <% } %> />
									<label class="lbl" for="meta1-5"> 批量删除</label>
							    </td>
				            </tr>
                            <tr>
							     <td>
                                    <label style="width:84px">测报表格：</label>
                                    <input name="configuration" id="meta1-6" type="checkbox" class="ace indbtn li-1" data-id="1" value="ViewReportTable" 
                                        <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("ViewReportTable")) { %> checked <% } %> />
									<label class="lbl" for="meta1-6"> 查看</label>
                                    <input name="configuration" id="meta1-7" type="checkbox" class="ace indbtn li-1" data-id="1" value="AddReportTable" 
                                        <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("AddReportTable")) { %> checked <% } %> />
									<label class="lbl" for="meta1-7"> 新建</label>
                                    <input name="configuration" id="meta1-9" type="checkbox" class="ace indbtn li-1" data-id="1" value="DeleteReportTable" 
                                        <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("DeleteReportTable")) { %> checked <% } %> />
									<label class="lbl" for="meta1-9"> 删除</label>
                                    <input name="configuration" id="Checkbox1" type="checkbox" class="ace indbtn li-1" data-id="1" value="DownloadReportTable" 
                                        <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("DownloadReportTable")) { %> checked <% } %> />
									<label class="lbl" for="meta1-10"> 下载</label>
                                    <input name="configuration" id="meta1-8" type="checkbox" class="ace indbtn li-1" data-id="1" value="BatchDeleteReportTable" 
                                        <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("BatchDeleteReportTable")) { %> checked <% } %> />
									<label class="lbl" for="meta1-8"> 批量删除</label>
							    </td>
				            </tr>
                            <tr>
                                <td rowspan="2" style="width:200px; text-align:center;">
                                    <label>
									<input type="checkbox" class="ace setbtn ul-2" data-id="2" />
									<span class="lbl"> 测报点管理</span>
                                    </label>
						        </td>
				            </tr>
							<tr>
							    <td>
									<label style="width:91px;float:left">测报点：</label>
                                    <div style="float:left">
                                        <input name="configuration" id="meta2-1" type="checkbox" class="ace indbtn li-2" data-id="2" value="BatchImportPoint" 
                                              <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("BatchImportPoint")) { %> checked <% } %>/>
										<label class="lbl" for="meta2-1"> 批量导入</label>
                                        <input name="configuration" id="meta2-2" type="checkbox" class="ace indbtn li-2" data-id="2" value="BatchExportPoint" 
                                              <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("BatchExportPoint")) { %> checked <% } %>/>
										<label class="lbl" for="meta2-2"> 批量导出</label>
                                        <input name="configuration" id="meta2-3" type="checkbox" class="ace indbtn li-2" data-id="2" value="BatchDeletePoint" 
                                              <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("BatchDeletePoint")) { %> checked <% } %>/>
										<label class="lbl" for="meta2-3"> 批量删除</label>
                                        </br>
                                        </br>
                                        <input name="configuration" id="meta2-4" type="checkbox" class="ace indbtn li-2" data-id="2" value="AddPoint" 
                                              <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("AddPoint")) { %> checked <% } %>/>
										<label class="lbl" for="meta2-4"> 新增</label>
                                        <input name="configuration" id="meta2-5" type="checkbox" class="ace indbtn li-2" data-id="2" value="ViewPoint" 
                                              <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("ViewPoint")) { %> checked <% } %>/>
										<label class="lbl" for="meta2-5"> 查看</label>
                                        <input name="configuration" id="meta2-6" type="checkbox" class="ace indbtn li-2" data-id="2" value="UpdatePoint" 
                                              <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("UpdatePoint")) { %> checked <% } %>/>
										<label class="lbl" for="meta2-6"> 修改</label>
                                        <input name="configuration" id="meta2-7" type="checkbox" class="ace indbtn li-2" data-id="2" value="DeletePoint" 
                                              <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("DeletePoint")) { %> checked <% } %>/>
										<label class="lbl" for="meta2-7"> 删除</label>
                                    </div>
							    </td>
				            </tr>
                            <tr>
                                <td rowspan="4" style="width:200px; text-align:center;">
									<label>
										<input type="checkbox" class="ace setbtn ul-3" data-id="3" />
										<span class="lbl"> 上报数据</span>
									</label>
						        </td>
				            </tr>
							<tr>
							    <td>
                                    <div style="margin-left:91px">
									<input name="configuration" id="meta3-1" type="checkbox" class="ace indbtn li-3" data-id="3" value="ImportReportData" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("ImportReportData")) { %> checked <% } %>/>
									<label class="lbl" for="meta3-1"> 导入数据</label>
                                    <input name="configuration" id="meta3-2" type="checkbox" class="ace indbtn li-3" data-id="3" value="BatchDeleteReportData" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("BatchDeleteReportData")) { %> checked <% } %>/>
									<label class="lbl" for="meta3-2"> 批量删除</label>
                                    <input name="configuration" id="meta3-3" type="checkbox" class="ace indbtn li-3" data-id="3" value="BatchExportReportData" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("BatchExportReportData")) { %> checked <% } %>/>
									<label class="lbl" for="meta3-3"> 批量导出</label>
                                    </div>
							    </td>
				            </tr>
                            <tr>
							    <td>
                                    <div style="margin-left:91px">
									<input name="configuration" id="meta3-4" type="checkbox" class="ace indbtn li-3" data-id="3" value="ViewReportData" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("ViewReportData")) { %> checked <% } %>/>
									<label class="lbl" for="meta3-4"> 查看</label>
                                    <input name="configuration" id="meta3-5" type="checkbox" class="ace indbtn li-3" data-id="3" value="UpdateReportData" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("UpdateReportData")) { %> checked <% } %>/>
									<label class="lbl" for="meta3-5"> 修改</label>
                                    <input name="configuration" id="meta3-6" type="checkbox" class="ace indbtn li-3" data-id="3" value="DeleteReportData" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("DeleteReportData")) { %> checked <% } %>/>
									<label class="lbl" for="meta3-6"> 删除</label>
                                    </div>
							    </td>
				            </tr>
                             <tr>
							    <td>
                                    <div style="margin-left:91px">
									<input name="configuration" id="meta3-7" type="checkbox" class="ace indbtn li-3" data-id="3" value="ReviewReportData" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("ReviewReportData")) { %> checked <% } %>/>
									<label class="lbl" for="meta3-7"> 审核</label>
                                    </div>
							    </td>
				            </tr>
						    <tr>
                                <td rowspan="4" style="width:200px; text-align:center;">
									<label>
										<input type="checkbox" class="ace setbtn ul-4" data-id="4" />
										<span class="lbl"> 统计分析</span>
									</label>
						        </td>
				            </tr>
							<tr>
							    <td>
                                    <label style="width:91px;float:left">汇总统计：</label>
                                    <div style="float:left">
                                    <input name="configuration" id="meta4-1" type="checkbox" class="ace indbtn li-4" data-id="4" value="ViewStatistics" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("ViewStatistics")) { %> checked <% } %>/>
									<label class="lbl" for="meta4-1"> 查看</label>
                                    <input name="configuration" id="meta4-2" type="checkbox" class="ace indbtn li-4" data-id="4" value="ExportStatistics" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("ExportStatistics")) { %> checked <% } %>/>
									<label class="lbl" for="meta4-2"> 导出数据</label>
                                    </div>
							    </td>
				            </tr>
                            <tr>
							     <td>
									<label>
                                        <label>自由汇总：</label>
									</label>
							    </td>
				            </tr>
							<tr>
							    <td>
									<label>
                                        <label>图形分析：</label>
                                        <input name="configuration" id="meta4-3" type="checkbox" class="ace indbtn li-4" data-id="4" value="CreateGraph" 
                                              <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("CreateGraph")) { %> checked <% } %>/>
										<span class="lbl" for="meta4-3"> 生成图形</span>
									</label>
								</td>
				            </tr>
                             <tr>
                                <td rowspan="5" style="width:200px; text-align:center;">
									<label>
										<input type="checkbox" class="ace setbtn ul-5" data-id="5" />
										<span class="lbl"> 工作管理</span>
									</label>
						        </td>
				            </tr>
							<tr>
							    <td>
                                    <label>测报计划：</label>
                                    <input name="configuration" id="meta5-1" type="checkbox" class="ace indbtn li-5" data-id="5" value="ViewPlan" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("ViewPlan")) { %> checked <% } %>/>
									<label class="lbl" for="meta5-1"> 查询</label>
                                    <input name="configuration" id="meta5-2" type="checkbox" class="ace indbtn li-5" data-id="5" value="AddPlan" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("AddPlan")) { %> checked <% } %>/>
									<label class="lbl" for="meta5-2"> 新增</label>
                                    <input name="configuration" id="meta5-3" type="checkbox" class="ace indbtn li-5" data-id="5" value="UpdatePlan" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("UpdatePlan")) { %> checked <% } %>/>
									<label class="lbl" for="meta5-3"> 编辑</label>
                                    <input name="configuration" id="meta5-4" type="checkbox" class="ace indbtn li-5" data-id="5" value="DeletePlan" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("DeletePlan")) { %> checked <% } %>/>
									<label class="lbl" for="meta5-4"> 删除</label>
							    </td>
				            </tr>
                            <tr>
							     <td>
									<label>
                                        <label>临时工作：</label>
									</label>
							    </td>
				            </tr>
							<tr>
							    <td>
                                    <label>管理办法：</label>
                                    <input name="configuration" id="meta5-5" type="checkbox" class="ace indbtn li-5" data-id="5" value="ViewManagementMethod" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("ViewManagementMethod")) { %> checked <% } %>/>
									<label class="lbl" for="meta5-5"> 查看</label>
                                    <input name="configuration" id="meta5-6" type="checkbox" class="ace indbtn li-5" data-id="5" value="AddManagementMethod" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("AddManagementMethod")) { %> checked <% } %>/>
									<label class="lbl" for="meta5-6"> 新增</label>
                                    <input name="configuration" id="meta5-7" type="checkbox" class="ace indbtn li-5" data-id="5" value="UpdateManagementMethod" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("UpdateManagementMethod")) { %> checked <% } %>/>
									<label class="lbl" for="meta5-7"> 修改</label>
                                    <input name="configuration" id="meta5-8" type="checkbox" class="ace indbtn li-5" data-id="5" value="DeleteManagementMethod" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("DeleteManagementMethod")) { %> checked <% } %>/>
									<label class="lbl" for="meta5-8"> 删除</label>
								</td>
				            </tr>
                            <tr>
							    <td>
									<label style="width:77px;float:left">通知：</label>
                                    <div style="float:left">
                                    <input name="configuration" id="meta5-9" type="checkbox" class="ace indbtn li-5" data-id="5" value="MessageUpperLevelSelection" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("MessageUpperLevelSelection")) { %> checked <% } %>/>
									<label class="lbl" for="meta5-9"> 上级单选</label>
                                    <input name="configuration" id="meta5-10" type="checkbox" class="ace indbtn li-5" data-id="5" value="ViewUpperMessage" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("ViewUpperMessage")) { %> checked <% } %>/>
									<label class="lbl" for="meta5-10"> 查看</label>
                                    </br>
                                    </br>
                                    <input name="configuration" id="meta5-11" type="checkbox" class="ace indbtn li-5" data-id="5" value="MessageLowerLevelSelection" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("MessageLowerLevelSelection")) { %> checked <% } %>/>
									<label class="lbl" for="meta5-11"> 下级单选</label>
                                    <input name="configuration" id="meta5-12" type="checkbox" class="ace indbtn li-5" data-id="5" value="ViewLowerMessage" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("ViewLowerMessage")) { %> checked <% } %>/>
									<label class="lbl" for="meta5-12"> 查看</label>
                                    <input name="configuration" id="meta5-13" type="checkbox" class="ace indbtn li-5" data-id="5" value="UpdateMessage" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("UpdateMessage")) { %> checked <% } %>/>
									<label class="lbl" for="meta5-13"> 修改</label>
                                    <input name="configuration" id="meta5-14" type="checkbox" class="ace indbtn li-5" data-id="5" value="DeleteMessage" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("DeleteMessage")) { %> checked <% } %>/>
									<label class="lbl" for="meta5-14"> 删除</label>
                                    <input name="configuration" id="meta5-15" type="checkbox" class="ace indbtn li-5" data-id="5" value="PublishMessage" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("PublishMessage")) { %> checked <% } %>/>
									<label class="lbl" for="meta5-15"> 发布通知</label>
                                    </div>
							    </td>
				            </tr>
                            <tr>
                                <td rowspan="2" style="width:200px; text-align:center;">
									<label>
										<input type="checkbox" class="ace setbtn ul-6" data-id="6" />
										<span class="lbl"> 动态数据</span>
									</label>
						        </td>
				            </tr>
							<tr>
							    <td>
                                    <div style="margin-left:77px">
									<input name="configuration" id="meta6-1" type="checkbox" class="ace indbtn li-6" data-id="6" value="ViewMapPoint" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("ViewMapPoint")) { %> checked <% } %>/>
									<label class="lbl" for="meta6-1"> 查看测报点</label>
                                    <input name="configuration" id="meta6-2" type="checkbox" class="ace indbtn li-6" data-id="6" value="ViewMapWatcher" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("ViewMapWatcher")) { %> checked <% } %>/>
									<label class="lbl" for="meta6-2"> 查看测报员</label>
                                    <input name="configuration" id="meta6-3" type="checkbox" class="ace indbtn li-6" data-id="6" value="ViewMapWatcherTrack" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("ViewMapWatcherTrack")) { %> checked <% } %>/>
									<label class="lbl" for="meta6-3"> 测报员轨迹显示</label>
                                    </div>
							    </td>
				            </tr>
                                 <tr>
                                <td rowspan="2" style="width:200px; text-align:center;">
									<label>
										<input type="checkbox" class="ace setbtn ul-7" data-id="7" />
										<span class="lbl"> 病虫预警</span>
									</label>
						        </td>
				            </tr>
							<tr>
							    <td>
									<div style="margin-left:77px">
										<input name="configuration" id="meta7-1" type="checkbox" class="ace indbtn li-7" data-id="7" value="ViewBingchongWarning" 
                                              <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("ViewBingchongWarning")) { %> checked <% } %>/>
										<span class="lbl" for="meta7-1"> 查看</span>
									</div>
							    </td>
				            </tr>
                             <tr>
                                <td rowspan="5" style="width:200px; text-align:center;">
									<label>
										<input type="checkbox" class="ace setbtn ul-8" data-id="8" />
										<span class="lbl"> 系统设置</span>
									</label>
						        </td>
				            </tr>
							<tr>
							    <td>
                                    <label>角色管理：</label>
                                    <input name="configuration" id="meta8-1" type="checkbox" class="ace indbtn li-8" data-id="8" value="ViewRight" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("ViewRight")) { %> checked <% } %>/>
									<label class="lbl" for="meta8-1"> 查看</label>
                                    <input name="configuration" id="meta8-2" type="checkbox" class="ace indbtn li-8" data-id="8" value="UpdateRight" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("UpdateRight")) { %> checked <% } %>/>
									<label class="lbl" for="meta8-2"> 修改</label>
                                    <input name="configuration" id="meta8-3" type="checkbox" class="ace indbtn li-8" data-id="8" value="DeleteRight" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("DeleteRight")) { %> checked <% } %>/>
									<label class="lbl" for="meta8-3"> 删除</label>
                                    <input name="configuration" id="meta8-4" type="checkbox" class="ace indbtn li-8" data-id="8" value="AddRight" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("AddRight")) { %> checked <% } %>/>
									<label class="lbl" for="meta8-4"> 添加</label>
							    </td>
				            </tr>
                            <tr>
							     <td>
                                    <label>地区管理：</label>
                                    <input name="configuration" id="meta8-5" type="checkbox" class="ace indbtn li-8" data-id="8" value="UpdateArea" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("UpdateArea")) { %> checked <% } %>/>
									<label class="lbl" for="meta8-5"> 修改</label>
                                    <input name="configuration" id="meta8-6" type="checkbox" class="ace indbtn li-8" data-id="8" value="DeleteArea" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("DeleteArea")) { %> checked <% } %>/>
									<label class="lbl" for="meta8-6"> 删除</label>
                                    <input name="configuration" id="meta8-7" type="checkbox" class="ace indbtn li-8" data-id="8" value="AddArea" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("AddArea")) { %> checked <% } %>/>
									<label class="lbl" for="meta8-7"> 添加</label>
							    </td>
				            </tr>
							<tr>
							    <td>
                                    <label>直属单位设置：</label>
                                    <input name="configuration" id="meta8-8" type="checkbox" class="ace indbtn li-8" data-id="8" value="ViewAreaUnit" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("ViewAreaUnit")) { %> checked <% } %>/>
									<label class="lbl" for="meta8-8"> 查看</label>
                                    <input name="configuration" id="meta8-9" type="checkbox" class="ace indbtn li-8" data-id="8" value="UpdateAreaUnit" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("UpdateAreaUnit")) { %> checked <% } %>/>
									<label class="lbl" for="meta8-9"> 修改</label>
                                    <input name="configuration" id="meta8-10" type="checkbox" class="ace indbtn li-8" data-id="8" value="DeleteAreaUnit" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("DeleteAreaUnit")) { %> checked <% } %>/>
									<label class="lbl" for="meta8-10"> 删除</label>
                                    <input name="configuration" id="meta8-11" type="checkbox" class="ace indbtn li-8" data-id="8" value="AddAreaUnit" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("AddAreaUnit")) { %> checked <% } %>/>
									<label class="lbl" for="meta8-11"> 添加</label>
								</td>
				            </tr>
                            <tr>
							    <td>
									<label class="" style="float:left;width:104px">用户管理：</label>
                                    <div style="float:left">
                                    <input name="configuration" id="meta8-12" type="checkbox" class="ace indbtn li-8" data-id="8" value="AddUser" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("AddUser")) { %> checked <% } %>/>
									<label class="lbl" for="meta8-12"> 添加</label>
                                    <input name="configuration" id="meta8-13" type="checkbox" class="ace indbtn li-8" data-id="8" value="BatchDeleteUser" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("BatchDeleteUser")) { %> checked <% } %>/>
									<label class="lbl" for="meta8-13"> 批量删除</label>
                                    </br>
                                    </br>
                                    <input name="configuration" id="meta8-14" type="checkbox" class="ace indbtn li-8" data-id="8" value="ViewUser" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("ViewUser")) { %> checked <% } %>/>
									<label class="lbl" for="meta8-14"> 查看</label>
                                    <input name="configuration" id="meta8-15" type="checkbox" class="ace indbtn li-8" data-id="8" value="UpdateUser" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("UpdateUser")) { %> checked <% } %>/>
									<label class="lbl" for="meta8-15"> 修改</label>
                                    <input name="configuration" id="meta8-16" type="checkbox" class="ace indbtn li-8" data-id="8" value="DeleteUser" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("DeleteUser")) { %> checked <% } %>/>
									<label class="lbl" for="meta8-16"> 删除</label>
                                    <input name="configuration" id="meta8-17" type="checkbox" class="ace indbtn li-8" data-id="8" value="ManageUserRight" 
                                          <% if (ViewData["role"] != null && ViewData["role"].ToString().Split(',').Contains("ManageUserRight")) { %> checked <% } %>/>
									<label class="lbl" for="meta8-17"> 权限</label>
                                    </div>
							    </td>
				            </tr>
                        </tbody>
                    </table>					
                    </div>
				</div>
			</div>

            <input type="hidden" id="uid" name="uid"  value="<% if (ViewData["uid"] != null) { %><%= ViewData["uid"] %><% } else { %>0<% } %>" />

			<div class="clearfix form-actions">
				<div class="col-md-offset-3 col-md-9">
					<button class="btn btn-info" type="submit">
						<i class="ace-icon fa fa-check bigger-110"></i>
						确认
					</button>

					&nbsp; &nbsp; &nbsp;
					<a class="btn" type="reset" href="<%= ViewData["rootUri"] %>Settings/RoleManage">
						<i class="ace-icon fa fa-undo bigger-110"></i>
						取消
					</a>
				</div>
			</div>
        </form>
	</div>
</div>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
	<link rel="stylesheet" type="text/css" href="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.min.css" />
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
	<script src="<%= ViewData["rootUri"] %>Content/plugins/bootstrap-toastr/toastr.js"></script>  
	<script src="<%= ViewData["rootUri"] %>Content/js/jquery.validate.min.js"></script>

    <script type="text/javascript">
        function redirectToListPage(status) {
            if (status.indexOf("error") != -1) {
                //$('.loading-btn').button('reset');
            } else {
                window.location = '<%=ViewData["rootUri"]%>' + "Settings/RoleManage";
            }
        }
        
        
        jQuery(function ($) {

            $.validator.messages.required = "必须要填写";
            $.validator.messages.minlength = jQuery.validator.format("必须由至少{0}个字符组成.");
            $.validator.messages.maxlength = jQuery.validator.format("必须由最多{0}个字符组成");
            $.validator.messages.equalTo = jQuery.validator.format("密码不一致.");
            $('#validation-form').validate({
                errorElement: 'span',
                errorClass: 'help-block',
                //focusInvalid: false,
                rules: {
                    rolename: {
                        required: true,
                        uniquerole: true
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
            $.validator.addMethod("uniquerole", function (value, element) {
                return checkRoleName();
            }, "权限名已存在");

            $(".setbtn").change(function (obj) {
                var group_name = $(this).data("id");
                var that = this;
                $('.li-' + group_name)
					.each(function () {
					    this.checked = that.checked;
					});
            });

	        $(".indbtn").change(function (obj) {
	            var group_name = $(this).data("id");
	            var check_all = true;

	            $('.li-' + group_name).each(function () {
	                if (this.checked == false) {
	                    check_all = false;
	                }
	            });

	            $('.ul-' + group_name)
	                    .each(function () {
	                        this.checked = check_all;
	                    });

	        });

         });

        function submitform() {
           // alert($('#validation-form').serialize());
           $.ajax({
	            type: "POST",
	            url: '<%= ViewData["rootUri"] %>' + 'Settings/SubmitRole',
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

	    function checkRoleName() {
	        var rolename = $("#rolename").val();
	        var uid = $("#uid").val();
	        var retval = false;

	        $.ajax({
	            async: false,
	            type: "GET",
	            url: '<%= ViewData["rootUri"] %>' + 'Settings/CheckUniqueRolename',
	            dataType: "json",
	            data: {
	                rolename: rolename,
	                uid: uid
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

	    function checkUserName() {
	        var username = $("#username").val();
	        var uid = $("#uid").val();
	        var retval = false;

	        $.ajax({
	            async: false,
	            type: "GET",
	            url: rootUri + "Settings/CheckUniqueUsername",
	            dataType: "json",
	            data: {
	                username: username,
	                uid: uid
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
    </script>
</asp:Content>
