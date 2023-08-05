 <%@ Control Language="C#" Inherits="System.Web.Mvc.ViewUserControl" %>
<%@ Import Namespace="BingChongBackend.Models" %>

<% var userrole = CommonModel.GetUserRoleInfo(); %>
<% var userlevel = CommonModel.GetSessionUserLevel(); %>

<ul class="nav nav-list">
	<li class="<% if (ViewData["level1nav"] != null && ViewData["level1nav"] == "DiseasePestManage") { %>active open hsub <% } %>">
		<a href="#" class="dropdown-toggle">
			<i class="menu-icon fa fa-list" style="font-size:15px;"></i>
			<span class="menu-text" style="font-family:Microsoft YaHei;font-size:1.1em"> 病虫管理 </span>

            <b class="arrow fa fa-angle-down"></b>
		</a>

		<b class="arrow"></b>
        <ul class="submenu">
         
			<li class="<% if (ViewData["level2nav"] != null && ViewData["level2nav"] == "DiseasePestInfo"){ %>active <% } %>">
				<a href="<%= ViewData["rootUri"] %>DiseasePest/DiseasePestInfo">
					<i class="menu-icon fa fa-caret-right"></i>
					病虫害信息
				</a>

				<b class="arrow"></b>
			</li>

			<li class="<% if (ViewData["level2nav"] != null && ViewData["level2nav"] == "DiseasePestTable") { %>active <% } %>">
				<a href="<%= ViewData["rootUri"] %>DiseasePest/DiseasePestTable">
					<i class="menu-icon fa fa-caret-right"></i>
					测报表格
				</a>

				<b class="arrow"></b>
			</li>
          <%--  <li class="<% if (ViewData["level2nav"] != null && ViewData["level2nav"] == "ImgManage") { %>active <% } %>">
				<a href="<%= ViewData["rootUri"] %>DiseasePest/ImgManage">
					<i class="menu-icon fa fa-caret-right"></i>
					图片管理
				</a>

				<b class="arrow"></b>
			</li>--%>
		</ul>

        </li>
	<li class="<% if (ViewData["level1nav"] != null && ViewData["level1nav"] == "StationManage") { %>active <% } %>">
		<a href="<%= ViewData["rootUri"] %>Station/StationManage">
			<i class="menu-icon fa fa-list"  style="font-size:15px;"></i>
			<span class="menu-text" style="font-family:Microsoft YaHei;"> 测报点管理 </span>
		</a>
		<b class="arrow"></b>
	</li>

	<li class="<% if (ViewData["level1nav"] != null && ViewData["level1nav"] == "UpdateManage") { %>active <% } %>">
		<a href="<%= ViewData["rootUri"] %>UpdatedInfo/UpdatedInfoManage">
			<i class="menu-icon fa fa-list" style="font-size:15px;"></i>
			<span class="menu-text" style="font-family:Microsoft YaHei;"> 上报数据管理 </span>
		</a>
		<b class="arrow"></b>
	</li>

      <%if (userrole != null && ((userrole.Contains("ViewStatistics")) || (userrole.Contains("CreateGraph"))))
        {%>
    <li class="<% if (ViewData["level1nav"] != null && ViewData["level1nav"] == "Analysis") { %>active open hsub <% } %>">
		<a href="#" class="dropdown-toggle">
			<i class="menu-icon fa fa-list" style="font-size:15px;"></i>
			<span class="menu-text" style="font-family:Microsoft YaHei;"> 统计分析 </span>
            <b class="arrow fa fa-angle-down"></b>

		</a>

		<b class="arrow"></b>

        <ul class="submenu">
          <%if (userrole != null && (userrole.Contains("ViewStatistics")))
            {%>
			<li class="<% if (ViewData["level2nav"] != null && ViewData["level2nav"] == "SummaryStatistics") { %>active <% } %>">
				<a href="<%= ViewData["rootUri"] %>Analysis/SummaryStatistics">
					<i class="menu-icon fa fa-caret-right"></i>
					汇总统计
				</a>
				<b class="arrow"></b>
			</li>
            <%} %>
            <%if (userrole != null && (userrole.Contains("CreateGraph")))
              {%>
            <li class="<% if (ViewData["level2nav"] != null && ViewData["level2nav"] == "LineChart") { %>active <% } %>">
				<a href="<%= ViewData["rootUri"] %>Analysis/LineChart">
					<i class="menu-icon fa fa-caret-right"></i>
					折线图
				</a>
				<b class="arrow"></b>
			</li>

            <li class="<% if (ViewData["level2nav"] != null && ViewData["level2nav"] == "Histogram") { %>active <% } %>">
				<a href="<%= ViewData["rootUri"] %>Analysis/Histogram">
					<i class="menu-icon fa fa-caret-right"></i>
					柱状图
				</a>
				<b class="arrow"></b>
			</li>
            <%} %>
		</ul>
	</li>
    <%} %>
    <li class="<% if (ViewData["level1nav"] != null && ViewData["level1nav"] == "WorkingManage") { %>active  open hsub<% } %>">
		<a href="#" class="dropdown-toggle">
			<i class="menu-icon fa fa-list" style="font-size:15px;"></i>
			<span class="menu-text" style="font-family:Microsoft YaHei;font-size:1.1em"> 工作管理 </span>
			<b class="arrow fa fa-angle-down"></b>
		</a>

		<b class="arrow"></b>
        <ul class="submenu">
			<li class="<% if (ViewData["level2nav"] != null && ViewData["level2nav"] == "WorkingPlan") { %>active <% } %>">
				<a href="<%= ViewData["rootUri"] %>WorkingManage/WorkingPlan">
					<i class="menu-icon fa fa-caret-right"></i>
					测报计划
				</a>
				<b class="arrow"></b>
			</li>

            <li class="<% if (ViewData["level2nav"] != null && ViewData["level2nav"] == "TemporaryWork") { %>active <% } %>">
				<a href="<%= ViewData["rootUri"] %>WorkingManage/TemporaryWork">
					<i class="menu-icon fa fa-caret-right"></i>
					临时工作
				</a>
				<b class="arrow"></b>
			</li>

            <li class="<% if (ViewData["level2nav"] != null && ViewData["level2nav"] == "ManagingDocuments") { %>active <% } %>">
				<a href="<%= ViewData["rootUri"] %>WorkingManage/ManagingDocuments">
					<i class="menu-icon fa fa-caret-right"></i>
					管理办法
				</a>
				<b class="arrow"></b>
			</li>
            <%if (userrole != null && (userrole.Contains("MessageUpperLevelSelection") || userrole.Contains("MessageLowerLevelSelection"))){%>
            <li class="<% if (ViewData["level2nav"] != null && ViewData["level2nav"] == "Notice") { %>active <% } %>">
				<a href="<%= ViewData["rootUri"] %>WorkingManage/Notice">
					<i class="menu-icon fa fa-caret-right"></i>
					通知
				</a>
				<b class="arrow"></b>
			</li>
            <%}%>
		</ul>
	</li>

    <li class="<% if (ViewData["level1nav"] != null && ViewData["level1nav"] == "Map") { %>active <% } %>">
		<a  href="<%= ViewData["rootUri"] %>Map/Map">
			<i class="menu-icon fa fa-list" style="font-size:15px;"></i>
			<span class="menu-text" style="font-family:Microsoft YaHei;font-size:1.1em"> 动态数据 </span>
		</a>
		<b class="arrow"></b>
	</li>
  
	<li class="<% if (ViewData["level1nav"] != null && ViewData["level1nav"] == "NewDisease") { %>active <% } %>">
		<a href="<%= ViewData["rootUri"] %>NewDisease/NewDisease">
			<i class="menu-icon fa fa-list" style="font-size:15px;"></i>
			<span class="menu-text" style="font-family:Microsoft YaHei;font-size:1.1em"> 病虫预警 </span>

		</a>
		<b class="arrow"></b>
	</li>


	<li class="<% if (ViewData["level1nav"] != null && ViewData["level1nav"] == "Settings") { %>active open hsub <% } %>">
		<a href="#" class="dropdown-toggle">
			<i class="menu-icon fa fa-list" style="font-size:15px;"></i>
			<span class="menu-text" style="font-family:Microsoft YaHei;font-size:1.1em"> 系统设置 </span>
			<b class="arrow fa fa-angle-down"></b>
		</a>

		<b class="arrow"></b>

		<ul class="submenu">
            <%if (userrole != null && ((userrole.Contains("ViewRight")) || (userrole.Contains("UpdateRight")) || (userrole.Contains("DeleteRight")) || (userrole.Contains("AddRight"))))
              {%>
			<li class="<% if (ViewData["level2nav"] != null && ViewData["level2nav"] == "RoleManage") { %>active <% } %>">
				<a href="<%= ViewData["rootUri"] %>Settings/RoleManage">
					<i class="menu-icon fa fa-caret-right"></i>
					角色设置
				</a>

				<b class="arrow"></b>
			</li>
            <%} %>
            <%if (userlevel != CommonModel.UserLevel.XIANJI)
              {%>
            <li class="<% if (ViewData["level2nav"] != null && ViewData["level2nav"] == "RegionManage") { %>active <% } %>">
				<a href="<%= ViewData["rootUri"] %>Settings/RegionManage">
					<i class="menu-icon fa fa-caret-right"></i>
					地区管理
				</a>

				<b class="arrow"></b>
			</li>
            <%} %>
            <%if (userlevel != CommonModel.UserLevel.XIANJI)
              {%>
            <li class="<% if (ViewData["level2nav"] != null && ViewData["level2nav"] == "UnitSettings") { %>active <% } %>">
				<a href="<%= ViewData["rootUri"] %>Settings/UnitSettings">
					<i class="menu-icon fa fa-caret-right"></i>
					直属单位设置
				</a>

				<b class="arrow"></b>
			</li>
            <%} %>

            <li class="<% if (ViewData["level2nav"] != null && ViewData["level2nav"] == "UserManage") { %>active <% } %>">
				<a href="<%= ViewData["rootUri"] %>Settings/UserManage">
					<i class="menu-icon fa fa-caret-right"></i>
					用户管理
				</a>

				<b class="arrow"></b>
			</li>
            <%if (userlevel == CommonModel.UserLevel.ADMIN)
              {%>
            <li class="<% if (ViewData["level2nav"] != null && ViewData["level2nav"] == "FieldManage") { %>active <% } %>">
				<a href="<%= ViewData["rootUri"] %>Settings/FieldManage">
					<i class="menu-icon fa fa-caret-right"></i>
					字段设置
				</a>

				<b class="arrow"></b>
			</li>
            <%} %>
            <li class="<% if (ViewData["level2nav"] != null && ViewData["level2nav"] == "TableManage") { %>active <% } %>">
				<a href="<%= ViewData["rootUri"] %>Settings/TableManage">
					<i class="menu-icon fa fa-caret-right"></i>
					表格管理
				</a>

				<b class="arrow"></b>
			</li>
            <%if (userlevel == CommonModel.UserLevel.ADMIN)
              {%>
            <li class="<% if (ViewData["level2nav"] != null && ViewData["level2nav"] == "SystemRecover") { %>active <% } %>">
				<a href="<%= ViewData["rootUri"] %>Settings/SystemRecover">
					<i class="menu-icon fa fa-caret-right"></i>
					系统恢复
				</a>

				<b class="arrow"></b>
			</li>
            <%} %>
            <%if (userlevel == CommonModel.UserLevel.ADMIN)
            {%>
            <li class="<% if (ViewData["level2nav"] != null && ViewData["level2nav"] == "HelpInfo") { %>active <% } %>">
				<a href="<%= ViewData["rootUri"] %>Settings/HelpInfo">
					<i class="menu-icon fa fa-caret-right"></i>
					帮助信息
				</a>

				<b class="arrow"></b>
			</li>
            <%} %>
		</ul>
	</li>
 
</ul>
