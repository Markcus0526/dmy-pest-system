using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using BingchongService.SvcManager;

namespace BingchongService
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IService" in both code and config file together.
    [ServiceContract]
    public interface IService
    {

        [OperationContract]
        string GetData(int value);

        [WebGet, OperationContract]
        SVCResult loginUser(String name,
            String password);

        [WebGet, OperationContract]
        SVCResult getShengs();

        [WebGet, OperationContract]
        SVCResult getShis(long sheng_id);

        [WebGet, OperationContract]
        SVCResult getXians(long shi_id);

        [WebGet, OperationContract]
        SVCResult getPoints(long userid,
            long sheng_id,
            long shi_id,
            long xian_id,
            String search,
            int type,
            int level,
            String time,
            String token);

        [WebGet, OperationContract]
        SVCResult getPointInfo(long userid,
            long point_id,
            String token);

        [WebGet, OperationContract]
        SVCResult getBlights(long userid,
            int kind,
            String search,
            String token);

        [WebGet, OperationContract]
        SVCResult getBlightInfo(long userid,
            long blight_id,
            String token);

        [WebGet(UriTemplate = "/getUsers?admin_id={admin_id}&level={level}&sheng_id={sheng_id}&shi_id={shi_id}&xian_id={xian_id}&search={search}"), 
        OperationContract]
        SVCResult getUsers(long admin_id,
            int level,
            long sheng_id,
            long shi_id,
            long xian_id,
            String search);

        [WebGet, OperationContract]
        SVCResult getUserInfo(long userid);

        [WebInvoke(Method = "POST",
            BodyStyle = WebMessageBodyStyle.WrappedRequest,
            RequestFormat = WebMessageFormat.Json,
            ResponseFormat = WebMessageFormat.Json)]
        SVCResult updateUserInfo(long userid,
            String phone,
            String place,
            String job,
            String photo,
            String point_list,
            String token);

        [WebGet, OperationContract]
        SVCResult getReports(long userid,
            long point_id,
            String start_time,
            String end_time,
            String token);

        [WebGet, OperationContract]
        SVCResult getReportInfo(long userid,
            long report_id,
            String token);

        [WebGet, OperationContract]
        SVCResult getForms(long blight_id);

        [WebGet, OperationContract]
        SVCResult getFields(long form_id);

        [WebInvoke(Method = "POST",
            BodyStyle = WebMessageBodyStyle.WrappedRequest,
            RequestFormat = WebMessageFormat.Json,
            ResponseFormat = WebMessageFormat.Json)]
        SVCResult setReports(long userid,
            long form_id,
            long point_id,
            long blight_id,
            String photo,
            String watch_time,
            String fields,
            String token);

        [WebInvoke(Method = "POST",
            BodyStyle = WebMessageBodyStyle.WrappedRequest,
            RequestFormat = WebMessageFormat.Json,
            ResponseFormat = WebMessageFormat.Json)]
        SVCResult uploadWatcherTrack(long userid,
            String longitude,
            String latitude,
            String token);

        [WebGet(UriTemplate = "/getWatcherTracks?sheng_id={sheng_id}&shi_id={shi_id}&xian_id={xian_id}&date={date}"),
        OperationContract]
        SVCResult getWatcherTracks(long sheng_id,
            long shi_id,
            long xian_id,
            String date);

        /*
        [WebInvoke(Method = "POST",
            BodyStyle = WebMessageBodyStyle.WrappedRequest,
            RequestFormat = WebMessageFormat.Json,
            ResponseFormat = WebMessageFormat.Json)]
         * */
        [WebGet, OperationContract]
        SVCResult uploadExtraTask(long admin_id,
            String name,
            long watcher_id,
            String notice_date,
            String report_date,
            String note);

        [WebInvoke(Method = "POST",
            BodyStyle = WebMessageBodyStyle.WrappedRequest,
            RequestFormat = WebMessageFormat.Json,
            ResponseFormat = WebMessageFormat.Json)]
        SVCResult updateExtraTask(long admin_id,
            long watcher_id,
            long task_id,
            int status);

        [WebGet(UriTemplate = "/getExtraTasks?admin_id={admin_id}&watcher_id={watcher_id}"),
        OperationContract]
        SVCResult getExtraTasks(long admin_id,
            long watcher_id);

        [WebGet, OperationContract]
        SVCResult getTempBlights(long admin_id,
            long watcher_id,
            String search);

        [WebGet, OperationContract]
        SVCResult getTempBlightInfo(long blight_id);

        [WebInvoke(Method = "POST",
            BodyStyle = WebMessageBodyStyle.WrappedRequest,
            RequestFormat = WebMessageFormat.Json,
            ResponseFormat = WebMessageFormat.Json)]
        SVCResult uploadTempBlight(long watcher_id,            
            String name,
            int kind,
            Decimal longitude,
            Decimal latitude,
            String info1,
            String info2,
            String info3,
            String note,
            String photo);

        [WebInvoke(Method = "POST",
            BodyStyle = WebMessageBodyStyle.WrappedRequest,
            RequestFormat = WebMessageFormat.Json,
            ResponseFormat = WebMessageFormat.Json)]
        SVCResult updateTempBlight(long admin_id,
            long blight_id,
            int status);

        [WebGet, OperationContract]
        SVCResult getHelps(long userid, 
            int type);

        [WebGet, OperationContract]
        SVCResult getHelpInfo(int type,
            long help_id);

        [WebGet, OperationContract]
        SVCResult getNotices(long userid,
            int year,
            int type,
            String token);

        [WebGet, OperationContract]
        SVCResult getNoticeInfo(long notice_id);

        //[WebGet, OperationContract]
        [WebInvoke(Method = "POST",
            BodyStyle = WebMessageBodyStyle.WrappedRequest,
            RequestFormat = WebMessageFormat.Json,
            ResponseFormat = WebMessageFormat.Json)]
        SVCResult publishNotice(String title,
            String serial,
            long user_id,
            String content);

        [WebGet(UriTemplate = "/getReportLine?userid={userid}&sheng_id={sheng_id}&shi_id={shi_id}&xian_id={xian_id}&blight_id={blight_id}&form_id={form_id}&point_id={point_id}&start_time={start_time}&end_time={end_time}&field_id={field_id}&token={token}"), 
        OperationContract]
        SVCResult getReportLine(long userid,
            long sheng_id,
            long shi_id,
            long xian_id,
            long blight_id,
            long form_id,
            long point_id,
            String start_time,
            String end_time,
            long field_id,
            String token);

        [WebGet(UriTemplate = "/getReportBar?userid={userid}&sheng_id={sheng_id}&shi_id={shi_id}&xian_id={xian_id}&blight_id={blight_id}&form_id={form_id}&time={time}&field_id={field_id}&token={token}"),
        OperationContract]
        SVCResult getReportBar(long userid,
            long sheng_id,
            long shi_id,
            long xian_id,
            long blight_id,
            long form_id,
            String time,
            long field_id,
            String token);

        [WebGet, OperationContract]
        SVCResult getReportHistory(long userid,
            long point_id,
            String start_time,
            String end_time,
            String token);

        [WebGet, OperationContract]
        SVCResult getTaskCount(long userid,
            int point_type,
            String start_time,
            String end_time,
            String token);

        [WebGet, OperationContract]
        SVCResult getTaskList(long userid,
            int type,
            int point_type,
            String start_time,
            String end_time,
            String token);

        [WebGet(UriTemplate = "/getTaskInfo?task_detail_id={task_detail_id}&point_id={point_id}&stime={stime}&check_reports={check_reports}&report_id={report_id}"), 
        OperationContract]
        SVCResult getTaskInfo(long task_detail_id,
            long point_id,
            String stime,
            bool check_reports,
            long report_id);

        [WebGet, OperationContract]
        SVCResult getVersion();

        [WebGet, OperationContract]
        SVCResult getOpinionKind();

        //[WebGet, OperationContract]
        [WebInvoke(Method = "POST",
            BodyStyle = WebMessageBodyStyle.WrappedRequest,
            RequestFormat = WebMessageFormat.Json,
            ResponseFormat = WebMessageFormat.Json)]         
        SVCResult uploadOpinion(long kind_id,
            long user_id,
            String title,
            String content);

        [WebGet, OperationContract]
        SVCResult getUserPhotos();

        [WebGet, OperationContract]
        SVCResult getReportData(long userid,
            long point_id,
            String start_time,
            String end_time,
            String token);
    }

}
