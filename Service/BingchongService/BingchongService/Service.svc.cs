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
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Service" in code, svc and config file together.
    public class Service : IService
    {
        public string GetData(int value)
        {
            return string.Format("You entered: {0}", value);
        }

        public SVCResult loginUser(String name,
            String password)
        {
            return SvcMgr.loginUser(name,
                password);
        }

        public SVCResult getShengs()
        {
            return SvcMgr.getShengs();
        }

        public SVCResult getShis(long sheng_id)
        {
            return SvcMgr.getShis(sheng_id);
        }

        public SVCResult getXians(long shi_id)
        {
            return SvcMgr.getXians(shi_id);
        }

        public SVCResult getPoints(long userid,
            long sheng_id,
            long shi_id,
            long xian_id,
            String search,
            int type,
            int level,
            String time,
            String token)
        {
            return SvcMgr.getPoints(userid, sheng_id, shi_id, xian_id, search, type,level, time, token);
        }

        public SVCResult getPointInfo(long userid,
            long point_id,
            String token)
        {
            return SvcMgr.getPointInfo(userid, point_id, token);
        }

        public SVCResult getBlights(long userid,
            int kind,
            String search,
            String token)
        {
            return SvcMgr.getBlights(userid, kind, search, token);
        }

        public SVCResult getBlightInfo(long userid,
            long blight_id,
            String token)
        {
            return SvcMgr.getBlightInfo(userid, blight_id, token);
        }

        public SVCResult getUsers(long admin_id,
            int level,
            long sheng_id,
            long shi_id,
            long xian_id,
            String search)
        {
            return SvcMgr.getUsers(admin_id, level, sheng_id, shi_id, xian_id, search);
        }

        public SVCResult getUserInfo(long userid)
        {
            return SvcMgr.getUserInfo(userid);
        }

        public SVCResult updateUserInfo(long userid,
            String phone,
            String place,
            String job,
            String photo,
            String point_list,
            String token)
        {
            return SvcMgr.updateUserInfo(userid, phone, place, job, photo, point_list, token);
        }

        public SVCResult getReports(long userid,
            long point_id,
            String start_time,
            String end_time,
            String token)
        {
            return SvcMgr.getReports(userid, point_id, start_time, end_time, token);
        }

        public SVCResult getReportInfo(long userid,
            long report_id,
            String token)
        {
            return SvcMgr.getReportInfo(userid, report_id, token);
        }

        public SVCResult getForms(long blight_id)
        {
            return SvcMgr.getForms(blight_id);
        }

        public SVCResult getFields(long form_id)
        {
            return SvcMgr.getFields(form_id);
        }

        public SVCResult setReports(long userid,
            long form_id,
            long point_id,
            long blight_id,
            String photo,
            String watch_time,
            String fields,
            String token)
        {
            return SvcMgr.setReports(userid, form_id, point_id, blight_id, photo, watch_time, fields, token);
        }

        public SVCResult uploadWatcherTrack(long userid,
            String longitude,
            String latitude,
            String token)
        {
            return SvcMgr.uploadWatcherTrack(userid, longitude, latitude, token);
        }

        public SVCResult getWatcherTracks(long sheng_id,
            long shi_id,
            long xian_id,
            String date)
        {
            return SvcMgr.getWatcherTracks(sheng_id, shi_id, xian_id, date);
        }

        public SVCResult uploadExtraTask(long admin_id,
            String name,
            long watcher_id,
            String notice_date,
            String report_date,
            String note)
        {
            return SvcMgr.uploadExtraTask(admin_id, name, watcher_id, notice_date, report_date, note);
        }

        public SVCResult updateExtraTask(long admin_id,
            long watcher_id,
            long task_id,
            int status)
        {
            return SvcMgr.updateExtraTask(admin_id, watcher_id, task_id, status);
        }

        public SVCResult getExtraTasks(long admin_id,
            long watcher_id)
        {
            return SvcMgr.getExtraTasks(admin_id, watcher_id);
        }

        public SVCResult getTempBlights(long admin_id,
            long watcher_id,
            String search)
        {
            return SvcMgr.getTempBlights(admin_id, watcher_id, search);
        }

        public SVCResult getTempBlightInfo(long blight_id)
        {
            return SvcMgr.getTempBlightInfo(blight_id);
        }

        public SVCResult uploadTempBlight(long watcher_id,
            String name,
            int kind,
            Decimal longitude,
            Decimal latitude,
            String info1,
            String info2,
            String info3,
            String note,
            String photo)
        {
            return SvcMgr.uploadTempBlight(watcher_id, name, kind, longitude, latitude, info1, info2, info3, note, photo);
        }

        public SVCResult updateTempBlight(long admin_id,
            long blight_id,
            int status)
        {
            return SvcMgr.updateTempBlight(admin_id, blight_id, status);
        }

        public SVCResult getHelps(long userid,
            int type)
        {
            return SvcMgr.getHelps(userid, type);
        }

        public SVCResult getHelpInfo(int type,
            long help_id)
        {
            return SvcMgr.getHelpInfo(type, help_id);
        }

        public SVCResult getNotices(long userid,
            int year,
            int type,
            String token)
        {
            return SvcMgr.getNotices(userid, year, type, token);
        }

        public SVCResult getNoticeInfo(long notice_id)
        {
            return SvcMgr.getNoticeInfo(notice_id);
        }

        public SVCResult publishNotice(String title,
            String serial,
            long user_id,
            String content)
        {
            return SvcMgr.publishNotice(title, serial, user_id, content);
        }

        public SVCResult getReportLine(long userid,
            long sheng_id,
            long shi_id,
            long xian_id,
            long blight_id,
            long form_id,
            long point_id,
            String start_time,
            String end_time,
            long field_id,
            String token)
        {
            return SvcMgr.getReportLine(userid, sheng_id, shi_id, xian_id, blight_id, form_id, point_id, start_time, end_time, field_id, token);
        }

        public SVCResult getReportBar(long userid,
            long sheng_id,
            long shi_id,
            long xian_id,
            long blight_id,
            long form_id,
            String time,
            long field_id,
            String token)
        {
            return SvcMgr.getReportBar(userid, sheng_id, shi_id, xian_id, blight_id, form_id, time, field_id, token);
        }

        public SVCResult getReportHistory(long userid,
            long point_id,
            String start_time,
            String end_time,
            String token)
        {
            return SvcMgr.getReportHistory(userid, point_id, start_time, end_time, token);
        }

        public SVCResult getTaskCount(long userid,
            int point_type,
            String start_time,
            String end_time,
            String token)
        {
            return SvcMgr.getTaskCount(userid, point_type, start_time, end_time, token);
        }

        public SVCResult getTaskList(long userid,
            int type,
            int point_type,
            String start_time,
            String end_time,
            String token)
        {
            return SvcMgr.getTaskList(userid, type, point_type, start_time, end_time, token);
        }

        public SVCResult getTaskInfo(long task_detail_id,
            long point_id,
            String stime,
            bool check_reports,
            long report_id)
        {
            return SvcMgr.getTaskInfo(task_detail_id, point_id, stime, check_reports, report_id);
        }

        public SVCResult getTaskStatus(long point_id,            
            String start_time,
            String end_time)
        {
            return SvcMgr.getTaskStatus(point_id, start_time, end_time);
        }

        public SVCResult getVersion()
        {
            return SvcMgr.getVersion();
        }

        public SVCResult getOpinionKind()
        {
            return SvcMgr.getOpinionKind();
        }

        public SVCResult uploadOpinion(long kind_id,
            long user_id,
            String title,
            String content)
        {
            return SvcMgr.uploadOpinion(kind_id, user_id, title, content);
        }

        public SVCResult getUserPhotos()
        {
            return SvcMgr.getUserPhotos();
        }

        public SVCResult getReportData(long userid,
            long point_id,
            String start_time,
            String end_time,
            String token)
        {
            return SvcMgr.getReportData(userid, point_id, start_time, end_time, token);
        }
    }
}
