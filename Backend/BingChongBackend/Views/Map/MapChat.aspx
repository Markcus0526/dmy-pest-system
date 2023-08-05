<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<dynamic>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<div class="page-header" id="phead">
	<div>
        <div class="col-xs-2 no-padding-left">
            <h5>视频通话</h5>
        </div>
		<div class="pull-right">
            <p>
            </p>
		</div>
    </div>
</div>
<div id="body">
<!--系统日志信息层-->
<div id="LOG_DIV_BODY" style="display: none;">
    <div id="LOG_DIV_TITLE" style="display: none;">系统日志</div>
    <div id="LOG_DIV_CONTENT" style="display: none;"></div>
</div>
<div id="list-top"></div>
<!--操作等待层 -->
<div id="LOADING_DIV">
    <img src="<%= ViewData["rootUri"] %>content/img/LoadImg.gif" alt="请等待..." style="height: 50px;" /></div>

<!--灰色背景透明层 -->
<div id="LOADING_GREY_DIV"></div>

<!--所有界面与浏览器顶部距离层 -->
<div id="margintop"></div>
<div id="show_shipin">

    <div id="shipin-l">
        <div id="nongmin"></div>
        <div id="videoshow_1" class="videoshow"></div>
    </div>
    <div id="shipin-r">
        <div id="zhuanjiajieshao" style="height:122px;width:350px;margin-top:15px">
        <img style="height:120px;width:348px;margin-left:-15px;margin-top:-15px" src="<%= ViewData["rootUri"] %>content/img/shipining.png" />
        </div>
        <div id="myname">zhaoruifan</div>
        <div id="videoshow_2" class="videoshow"></div>
           <div id="caozuo">
                <div class="l set-button">
                    <img onclick="shezhi()" src="<%= ViewData["rootUri"] %>content/img/setbutton.png" />
                </div>
                <div class="l close-button">
                    <img onclick="gua()" id="guaduan" src="<%= ViewData["rootUri"] %>content/img/button__03.gif" />
                </div>
            </div>
    </div>
</div>

<div id="Shade_Div"></div>
<div id="MAIN_DIV">
    <!--安装插件提示层 -->
    <div id="prompt_div">
        <div class="close_div">
            <div id="prompt_div_headline1">插件安装提示</div>
            <div id="prompt_div_headline2" onclick="window.location.reload();">刷新</div>
        </div>
        <div>
            <div id="prompt_div_line1"></div>
            <div id="prompt_div_line2">控件安装完成后，请重启浏览器</div>
            <div id="prompt_div_btn_load" onclick="window.open('<%= ViewData["rootUri"] %>content/js/AnyChatWebSetup.exe')">下载安装</div>
        </div>
    </div>
    <div id="login_div">
        <div id="setting_div" style="display: none">
            <div id="setting_div_input">
                服务器:&nbsp<input type="text" id="ServerAddr" style="width: 120px;" />&nbsp&nbsp&nbsp
                 端口:&nbsp<input type="text" id="ServerPort" style="width: 40px;" />
            </div>
        </div>
    </div>
    <!--大厅层 -->
    <div id="hall_div">
        <div style="width: auto; height: 30px;">
            <div id="ExitSystemBtn" style="display: block;">
            <span id="text1" readonly="readonly" style=" border-style:none;margin-left:220px;font-size:20px;color: Red" >视频用户在线列表</span>
            </div>
        </div>
        <div id="hall_div_userinfo" style="">
            <!--显示在线好友列表-->
            <div id="UserListContent" style="width:70%;margin-left:220px; height: 400px;display: block;border:1px solid #CCC;">
            </div>
            <div></div>
        </div>
        <div id="SessionPrompt_Div"></div>
    </div>
    <!-- 主动呼叫等待框 -->
    <div id="Initiative_Call_Div">
        <div class="Div_HeadPart" style="margin-top:-8px;margin-left:-30px;">
            <img alt="取消呼叫" src="<%= ViewData["rootUri"] %>content/img/close.png" onclick="CancelCall()" />
        </div>
        <div id="Initiative_Call_Div_Content" class="Call_Div_Content"></div>
       <!-- <div class="Call_Div_Button">
            <div id="Initiative_Cancel_Button" onclick="CancelCall()" style="">取消呼叫</div>
        </div>-->
    </div>
    <!-- 被呼叫询问框 -->
    <div id="BeCalls_Div">
        <div class="Div_HeadPart"></div>
        <div id="BeCalls_Div_Content" class="Call_Div_Content"></div>
        <div class="Call_Div_Button">
            <div class="ButtonImg tongyi" onclick="AcceptRequestBtnClick()"></div>
            <div class="ButtonImg jujue" onclick="RejectRequestBtnClick()"></div>
        </div>
    </div>
    <!--高级设置界面 -->
    <div id="advanceset_div">
        <div id="advanceset_div_close">×关闭</div>
        <div id="advanceset_div_mainpark">
            <div id="advanceset_div_Div_Btn">
                <div id="Btn_Div_Device" clickstate="true" onclick="selectSettingMenu('Device_Interface','Btn_Div_Device')">设备选择</div>
                <div id="Btn_Div_Video" class="Btn_Div" clickstate="false" onclick="selectSettingMenu('Video_Parameter_Interface','Btn_Div_Video')">视频参数</div>
                <div id="Btn_Div_Sound" class="Btn_Div" clickstate="false" onclick="selectSettingMenu('Sound_Parameter_Interface','Btn_Div_Sound')">音频参数</div>
                <div id="Btn_Div_Other" class="Btn_Div" clickstate="false" onclick="selectSettingMenu('Other_Parameter_Interface','Btn_Div_Other')">其他设置</div>
            </div>
            <div id="advanceset_div_mainpark_parameter">
                <div style="height: 10px;"></div>
                <div id="Device_Interface">
                    视频设备：<select id="DeviceType_VideoCapture" onchange="GetTheValue('DeviceType_VideoCapture')" style="width: 250px;"></select><br />
                    录音设备：<select id="DeviceType_AudioCapture" onchange="GetTheValue('DeviceType_AudioCapture')" style="width: 250px;"></select><br />
                    放音设备：<select id="DeviceType_AudioPlayBack" onchange="GetTheValue('DeviceType_AudioPlayBack')" style="width: 250px;"></select>
                </div>
                <div id="Video_Parameter_Interface">
                    <table id="advanceset_div_Tab">
                        <tr>
                            <td class="td_input">
                                <input type="checkbox" id="ServerSetting" onclick="ChangeTheResult('ServerSetting')" checked="checked" /></td>
                            <td>服务器配置参数优先</td>
                            <td></td>
                            <td class="td_input"><a>质量：</a></td>
                            <td>
                                <select id="quality" onchange="GetTheValue('quality')" disabled="disabled" style="width: 80px;"></select></td>
                        </tr>
                        <tr>
                            <td class="td_input"><a>码率：</a></td>
                            <td>
                                <select id="code_rate" onchange="GetTheValue('code_rate')" disabled="disabled" style="width: 80px;"></select></td>
                            <td></td>
                            <td class="td_input"><a>分辨率：</a></td>
                            <td>
                                <select id="distinguishability" onchange="GetTheValue('distinguishability')" disabled="disabled" style="width: 80px;"></select></td>
                        </tr>
                        <tr>
                            <td class="td_input"><a>帧率：</a></td>
                            <td>
                                <select id="frame_rate" onchange="GetTheValue('frame_rate')" disabled="disabled" style="width: 80px;"></select></td>
                            <td></td>
                            <td class="td_input">(当前分辨率：</td>
                            <td id="current_resolution"></td>
                        </tr>
                        <tr>
                            <td class="td_input"><a>预设：</a></td>
                            <td>
                                <select id="preinstall" onchange="GetTheValue('preinstall')" disabled="disabled" style="width: 80px;"></select></td>
                            <td></td>
                            <td>
                                <div id="regulate" onclick="BtnAdjust()" onmouseout="Mouseout('regulate')" onmousemove="Mouseover('regulate')">画面调节</div>
                            </td>
                            <td>
                                <div id="apply_changes" onclick="BtnApply()" onmouseout="Mouseout('apply_changes')" onmousemove="Mouseover('apply_changes')">应用更改</div>
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="Sound_Parameter_Interface">
                    <div style="height: 30px; margin: 10px 0px 0px 5px;">
                        <select id="Speak_Mode" style="width: 140px;" onchange="GetTheValue('Speak_Mode')"></select></div>
                    <div class="audio_tabcontrol">
                        <input id="audio_vadctrl" onchange="ChangeTheResult('audio_vadctrl')" type="checkbox" />静音检测</div>
                    <div class="audio_tabcontrol">
                        <input id="audio_echoctrl" onchange="ChangeTheResult('audio_echoctrl')" type="checkbox" />回音消除</div>
                    <div class="audio_tabcontrol">
                        <input id="audio_nsctrl" onchange="ChangeTheResult('audio_nsctrl')" type="checkbox" />噪音抑制</div>
                    <div class="audio_tabcontrol">
                        <input id="audio_agcctrl" onchange="ChangeTheResult('audio_agcctrl')" type="checkbox" />自动增益</div>
                </div>
                <div id="Other_Parameter_Interface">
                    <p style="float: left; margin: 10px;">
                        <input id="Checkbox_P2P" onchange="ChangeTheResult('Checkbox_P2P')" type="checkbox" />P2P优先</p>
                    <p style="float: left; margin: 10px;">视频显示裁剪模式:<select id="videoshow_clipmode" onchange="GetTheValue('videoshow_clipmode')" style="width: 80px;"></select></p>
                </div>
            </div>
        </div>
    </div>

</div>
<div class="clear"></div>
<div id="shipin-top"></div>
</div>

</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="PageStyle" runat="server">
    <link href="<%= ViewData["rootUri"] %>content/css/index.css" rel="stylesheet" type="text/css" />
    <link href="<%= ViewData["rootUri"] %>content/css/VideoFace.css" rel="Stylesheet" />
    <link href="<%= ViewData["rootUri"] %>content/css/hall.css" rel="Stylesheet" />
    <link href="<%= ViewData["rootUri"] %>content/css/VideoCall.css" rel="Stylesheet" />
    <link href="<%= ViewData["rootUri"] %>content/css/advanceset.css" rel="Stylesheet" />
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="PageScripts" runat="server">
    <script language="javascript" type="text/javascript" src="<%= ViewData["rootUri"] %>content/js/jquery-1.8.3.min.js" charset="GB2312"></script>
    <!-- 加载绿云信息 for Web SDK库  -->
    <script language="javascript" type="text/javascript" src="<%= ViewData["rootUri"] %>content/js/anychatsdk.js" charset="GB2312"></script>
    <script language="javascript" type="text/javascript" src="<%= ViewData["rootUri"] %>content/js/anychatevent.js" charset="GB2312"></script>

    <!-- 加载业务逻辑控制脚本  -->
    <script language="javascript" type="text/javascript" src="<%= ViewData["rootUri"] %>content/js/logicfunc.js" charset="GB2312"></script>
    <script language="javascript" type="text/javascript" src="<%= ViewData["rootUri"] %>content/js/videocall.js" charset="GB2312"></script>
    <script language="javascript" type="text/javascript" src="<%= ViewData["rootUri"] %>content/js/advanceset.js" charset="GB2312"></script>
   <script>
   var idlist=new Array();
   var  rootUri="<%= ViewData["rootUri"] %>";
   var msg="<%=ViewData["msg"] %>";
          if (msg != "") {
              alert(msg);
          }
       $(function () {
       $.ajax({
						url: rootUri+"Map/GetUserList",
						method: 'post',
						success: function (data) {
							  if(data.length>0)
							  {
								 idlist=data; 
                                 LogicInit("<%=ViewData["username"] %>");
							  }          
					}
				});
          

       })
       function gua() {
           BRAC_VideoCallControl(BRAC_VIDEOCALL_EVENT_FINISH, mTargetUserId, 0, 0, 0, "");
           //  location.reload();
       }
       function shezhi() {
           if (Getdmo("advanceset_div").style.display == "block")
               Getdmo("advanceset_div").style.display = "none";
           else {
               Getdmo("advanceset_div").style.display = "block"; // 显示高级设置界面
               // 初始化高级设置界面
               InitAdvanced();
           }
       }
   </script>
</asp:Content>
