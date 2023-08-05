using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using BingchongService.DBManager;

namespace BingchongService.SvcManager
{
    public class SvcMgr
    {
        public static bool checkUserTokenIsValid(long userid, String token)
        {
            BingchongDBDataContext dbConn = null;
            dbConn = new BingchongDBDataContext();

            try
            {
                user userItem = dbConn.users
                .Where(m => m.uid == userid && m.deleted == 0)
                .FirstOrDefault();
            if (userItem == null)
                return false;

            if (token.Equals(Global.MD5Hash(userItem.name + "," + userItem.password)))
                return true;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);
            }
            
            return false;
        }

        public static SVCResult loginUser(String name,
            String password)
        {
            SVCResult result = new SVCResult();            
            BingchongDBDataContext dbConn = null;

            try
            {
                if (name.Equals("") || password.Equals(""))
                {
                    result.retcode = ErrMgr.ERRCODE_PARAM;
                    result.retmsg = ErrMgr.ERRMSG_PARAM;
                }
                else
                {
                    dbConn = new BingchongDBDataContext();

                    user userinfo = dbConn.users
                        .Where(m => m.name.Equals(name)
                            && m.deleted == 0)
                        .FirstOrDefault();

                    if (userinfo == null)
                    {
                        result.retcode = ErrMgr.ERRCODE_NORMAL;
                        result.retmsg = ErrMgr.ERRMSG_NOTREGUSER;
                    }
                    else if (!userinfo.password.Equals(Global.MD5Hash(password)))
                    {
                        result.retcode = ErrMgr.ERRCODE_NORMAL;
                        result.retmsg = ErrMgr.ERRMSG_PWDWRONG;
                    }
                    else
                    {
                        result.retcode = ErrMgr.ERRCODE_NONE;
                        result.retmsg = ErrMgr.ERRMSG_NONE;

                        sheng shengItem = dbConn.shengs
                            .Where(m => m.uid == userinfo.sheng_id &&
                            m.deleted == 0)
                            .FirstOrDefault();

                        shi shiItem = dbConn.shis
                            .Where(m => m.uid == userinfo.shi_id &&
                            m.deleted == 0)
                            .FirstOrDefault();

                        xian xianItem = dbConn.xians
                            .Where(m => m.uid == userinfo.xian_id &&
                            m.deleted == 0)
                            .FirstOrDefault();

                        Dictionary<String, object> retdata = new Dictionary<String, object>();

                        retdata.Add("uid", userinfo.uid);
                        retdata.Add("name", userinfo.name);
                        retdata.Add("real_name", userinfo.realname);
                        retdata.Add("phone", Global.ignoreNullStr(userinfo.phone));
                        retdata.Add("place", Global.ignoreNullStr(userinfo.place));
                        retdata.Add("job", Global.ignoreNullStr(userinfo.job));
                        retdata.Add("right_id", userinfo.right_id);
                        retdata.Add("imgurl", Global.getAbsImgUrl(userinfo.imgurl));
                        retdata.Add("sheng_id", userinfo.sheng_id);
                        retdata.Add("shi_id", userinfo.shi_id);
                        retdata.Add("xian_id", userinfo.xian_id);
                        retdata.Add("sheng_name", shengItem != null ? shengItem.name : "");
                        retdata.Add("shi_name", shiItem != null ? shiItem.name : "");
                        retdata.Add("xian_name", xianItem != null ? xianItem.name : "");
                        retdata.Add("point_list", userinfo.point_list);
                        retdata.Add("level", userinfo.level);
                        retdata.Add("token", Global.MD5Hash(userinfo.name + "," + userinfo.password));

                        result.retdata = retdata;
                    }
                }
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getShengs()
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();

                List<DataClasses.STSheng> arrShengs = dbConn.shengs
                    .Where(m => m.status == 1)                    
                    .Select(m => new DataClasses.STSheng
                    {
                        uid = m.uid,
                        name = m.name,
                        status = m.status,
                    })
                    .ToList();

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = arrShengs;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getShis(long sheng_id)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();

                List<DataClasses.STSheng> arrShengs = dbConn.shis
                    .Where(m => m.sheng_id == sheng_id && m.status == 1)
                    .Select(m => new DataClasses.STSheng
                    {
                        uid = m.uid,
                        name = m.name,
                        status = m.status,
                    })
                    .ToList();

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = arrShengs;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getXians(long shi_id)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();

                List<DataClasses.STSheng> arrShengs = dbConn.xians
                    .Where(m => m.shi_id == shi_id && m.status == 1)
                    .Select(m => new DataClasses.STSheng
                    {
                        uid = m.uid,
                        name = m.name,
                        status = m.status,
                    })
                    .ToList();

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = arrShengs;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getPoints(long userid,
            long sheng_id,
            long shi_id,
            long xian_id,
            String search,
            int type,
            int level,
            String time,
            String token)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            if (!checkUserTokenIsValid(userid, token))
            {
                result.retcode = ErrMgr.ERRCODE_TOKEN;
                result.retmsg = ErrMgr.ERRMSG_TOKEN;
                return result;
            }

            try
            {
                dbConn = new BingchongDBDataContext();

                List<DataClasses.STPoint> arrPoints = new List<DataClasses.STPoint>();

                user watcherItem = dbConn.users
                    .Where(m => m.uid == userid &&
                    m.right_id == 0 &&
                    m.deleted == 0)
                    .FirstOrDefault();

                if (watcherItem != null)
                {
                    arrPoints = dbConn.points
                        .Where(m => m.deleted == 0)
                        .Select(m => new DataClasses.STPoint
                        {
                            uid = m.uid,
                            name = m.name,
                            nickname = m.nickname,
                            longitude = m.longitude,
                            latitude = m.latitude,
                            type = m.type,
                            level = m.level,
                            sheng_id = m.sheng_id,
                            shi_id = m.shi_id,
                            xian_id = m.xian_id,
                            info1 = m.info1,
                            info2 = m.info2,
                            info3 = m.info3,
                            info4 = m.info4,
                            info5 = m.info5,
                            info6 = m.info6,
                            note = m.note,
                            task_count = -1
                        })
                        .ToList();

                    if (watcherItem.sheng_id > 0)
                    {
                        arrPoints = arrPoints.Where(m => m.sheng_id == watcherItem.sheng_id).ToList();
                    }
                    if (watcherItem.shi_id > 0)
                    {
                        arrPoints = arrPoints.Where(m => m.shi_id == watcherItem.shi_id).ToList();
                    }
                    if (watcherItem.xian_id > 0)
                    {
                        arrPoints = arrPoints.Where(m => m.xian_id == watcherItem.xian_id).ToList();
                    }

                    arrPoints = arrPoints.Where(m => m.level == 0 || m.level == (4 - watcherItem.level)).ToList();

                    result.retcode = ErrMgr.ERRCODE_NONE;
                    result.retmsg = ErrMgr.ERRMSG_NONE;
                    result.retdata = arrPoints;
                    return result;
                }

                user adminItem = dbConn.users
                    .Where(m => m.right_id > 0 &&
                    m.uid == userid &&
                    m.deleted == 0)
                    .FirstOrDefault();

                if (adminItem == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NONE;
                    return result;
                }

                arrPoints = dbConn.points
                    .Where(m => m.sheng_id == sheng_id &&
                        m.shi_id == shi_id &&
                        m.xian_id == xian_id &&
                        m.name.Contains(search) &&
                        m.deleted == 0)
                    .OrderBy(m => m.nickname)
                    .Select(m => new DataClasses.STPoint
                    {
                        uid = m.uid,
                        name = m.name,
                        nickname = m.nickname,
                        longitude = m.longitude,
                        latitude = m.latitude,
                        type = m.type,
                        level = m.level,
                        sheng_id = m.sheng_id,
                        shi_id = m.shi_id,
                        xian_id = m.xian_id,
                        info1 = m.info1,
                        info2 = m.info2,
                        info3 = m.info3,
                        info4 = m.info4,
                        info5 = m.info5,
                        info6 = m.info6,
                        note = m.note,
                        task_count = -1
                    })
                    .ToList();

                if (adminItem.level == 1) // 县级管理员
                {
                    arrPoints = arrPoints.Where(m => m.level == 0 || m.level == 3).ToList();
                }
                else if (adminItem.level == 2) // 市级管理员
                {
                    arrPoints = arrPoints.Where(m => m.level == 0 || m.level == 2).ToList();
                }
                else if (adminItem.level == 3) // 省级管理员
                {
                    arrPoints = arrPoints.Where(m => m.level == 0 || m.level == 1).ToList();
                }

                if (type >= 0)
                {
                    arrPoints = arrPoints.Where(m => m.type == type).ToList();
                }

                if (level >= 0)
                {
                    arrPoints = arrPoints.Where(m => m.level == level).ToList();
                }

                if (time != null && time.Length > 0)
                {
                    arrPoints = arrPoints
                        .Select(m => new DataClasses.STPoint
                        {
                            uid = m.uid,
                            name = m.name,
                            nickname = m.nickname,
                            longitude = m.longitude,
                            latitude = m.latitude,
                            type = m.type,
                            level = m.level,
                            sheng_id = m.sheng_id,
                            shi_id = m.shi_id,
                            xian_id = m.xian_id,
                            info1 = m.info1,
                            info2 = m.info2,
                            info3 = m.info3,
                            info4 = m.info4,
                            info5 = m.info5,
                            info6 = m.info6,
                            note = m.note,
                            task_count = getTaskCountForPoint(m.uid, time)
                        })
                    .ToList();
                }
                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = arrPoints;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static int getTaskCountForPoint(long point_id, String time)
        {
            int taskCount = -1;
            BingchongDBDataContext dbConn = null;
            dbConn = new BingchongDBDataContext();

            DateTime startTime = Convert.ToDateTime(time);
            DateTime endTime = Convert.ToDateTime(time);

            List<DataClasses.STTaskDetail> arrTasks = dbConn.task_details
                    .Where(m => m.deleted == 0)
                    .AsEnumerable()
                    .Select(row => new DataClasses.STTaskDetail
                    {
                        uid = row.uid,
                        arr_points = row.point_list.Split(',').ToList(),
                        startdate = row.startdate,
                        enddate = row.enddate,
                        period = row.period,
                        blight_id = row.blight_id,
                        form_id = row.form_id                        
                    })
                    .Where(m => m.arr_points.Contains(point_id + ""))
                    .ToList();

            if (arrTasks == null || arrTasks.Count() <= 0)
            {
                return taskCount;
            }

            foreach (DataClasses.STTaskDetail task_item in arrTasks)
            {
                DateTime sD = task_item.startdate;
                DateTime eD = task_item.enddate;
                long period = task_item.period;
                DateTime d = sD.AddDays(task_item.period - 1);
                bool isArrivedToEndDate = false;
                while (d <= endTime)
                {
                    if (d >= eD)
                    {
                        if (isArrivedToEndDate)
                            break;
                        d = eD;
                        isArrivedToEndDate = true;
                    }

                    if (d < startTime)
                    {
                        if (!isArrivedToEndDate)
                        {
                            d = d.AddDays(period);
                            if (d > eD)
                            {
                                d = eD;
                            }
                            continue;
                        }
                        else
                            break;
                    }

                    if (taskCount < 0)
                        taskCount = 0;

                    report reportItem = dbConn.reports
                        .Where(m => m.point_id == point_id &&
                            m.blight_id == task_item.blight_id &&
                            m.form_id == task_item.form_id &&
                            m.report_time >= d && m.report_time < d.AddDays(1) &&
                            m.deleted == 0)
                        .FirstOrDefault();

                    if(reportItem == null)
                        taskCount++;

                    d = d.AddDays(period);
                }
            }

            return taskCount;
        }
        public static SVCResult getPointInfo(long userid,
            long point_id,
            String token)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            if (!checkUserTokenIsValid(userid, token))
            {
                result.retcode = ErrMgr.ERRCODE_TOKEN;
                result.retmsg = ErrMgr.ERRMSG_TOKEN;
                return result;
            }

            try
            {                
                dbConn = new BingchongDBDataContext();

                point pointItem = dbConn.points
                    .Where(m => m.uid == point_id &&
                    m.deleted == 0)
                    .FirstOrDefault();

                if (pointItem == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;
                }
                else
                {
                    sheng shengItem = dbConn.shengs
                        .Where(m => m.uid == pointItem.sheng_id &&
                        m.deleted == 0)
                        .FirstOrDefault();

                    shi shiItem = dbConn.shis
                        .Where(m => m.uid == pointItem.shi_id &&
                        m.deleted == 0)
                        .FirstOrDefault();

                    xian xianItem = dbConn.xians
                        .Where(m => m.uid == pointItem.xian_id &&
                        m.deleted == 0)
                        .FirstOrDefault();

                    String strType = "固定测报点";
                    if (pointItem.type == 1)
                        strType = "非固定测报点";
                    String strLevel = "国家级";
                    switch (pointItem.level)
                    {
                        case 0:
                            strLevel = "国家级";
                            break;
                        case 1:
                            strLevel = "省级";
                            break;
                        case 2:
                            strLevel = "市级";
                            break;
                        case 3:
                            strLevel = "县级";
                            break;
                    }
                    Dictionary<String, Object> pointInfo = new Dictionary<String, Object>();
                    String htmlContent = "<!doctype html>" + 
                                "<html>" + 
                                "<head>" + 
                                "    <meta charset=\"utf-8\" />" + 
                                "</head>" + 
                                "<body>" + 
                                "<table align=\"center\" style=\"color: #7AB038;\" bordercolor=\"#7AB038\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">" + 
                                "    <tbody>" +
                                "    <tr>" +
                                "        <td width=\"100\" height=\"40\" align=center>" +
                                "            <p>测报点类型</p>" +
                                "        </td>" +
                                "        <td width=\"200\" height=\"40\" align=center>" +
                                "            <p>" + strType + "</p>" +
                                "        </td>" +
                                "    </tr>" +
                                "    <tr>" +
                                "        <td width=\"100\" height=\"40\" align=center>" +
                                "            <p>测报点级别</p>" +
                                "        </td>" +
                                "        <td width=\"200\" height=\"40\" align=center>" +
                                "            <p>" + strLevel + "</p>" +
                                "        </td>" +
                                "    </tr>" +
                                "    <tr>" + 
                                "        <td width=\"100\" height=\"40\" align=center>" + 
                                "            <p>测报点名称</p>" + 
                                "        </td>" + 
                                "        <td width=\"200\" height=\"40\" align=center>" + 
                                "            <p>"+ pointItem.name + "</p>" + 
                                "        </td>" + 
                                "    </tr>" +
                                "    <tr>" +
                                "        <td width=\"100\" height=\"40\" align=center>" +
                                "            <p>测报点代码</p>" +
                                "        </td>" +
                                "        <td width=\"200\" height=\"40\" align=center>" +
                                "            <p>" + pointItem.nickname + "</p>" +
                                "        </td>" +
                                "    </tr>" + 
                                "    <tr>" + 
                                "        <td width=\"100\" height=\"40\" align=center>" + 
                                "            <p>东经</p>" + 
                                "        </td>" + 
                                "        <td width=\"200\" height=\"40\" align=center>" + 
                                "            <p>" + pointItem.longitude + "</p>" + 
                                "        </td>" + 
                                "    </tr>" + 
                                "    <tr>" + 
                                "        <td width=\"100\" height=\"40\" align=center>" + 
                                "            <p>北纬</p>" + 
                                "        </td>" + 
                                "        <td width=\"200\" height=\"40\" align=center>" + 
                                "            <p>" + pointItem.latitude + "</p>" + 
                                "        </td>" + 
                                "    </tr>" + 
                                "    <tr>" + 
                                "        <td width=\"100\" height=\"40\" align=center>" + 
                                "            <p>省\\自治区</p>" + 
                                "        </td>" + 
                                "        <td width=\"200\" height=\"40\" align=center>" + 
                                "            <p>" + shengItem.name + "</p>" + 
                                "        </td>" + 
                                "    </tr>" + 
                                "    <tr>" + 
                                "        <td width=\"100\" height=\"40\" align=center>" + 
                                "            <p>盟\\市</p>" + 
                                "        </td>" + 
                                "        <td width=\"200\" height=\"40\" align=center>" + 
                                "            <p>" + shiItem.name + "</p>" + 
                                "        </td>" + 
                                "    </tr>" + 
                                "    <tr>" + 
                                "        <td width=\"100\" height=\"40\" align=center>" + 
                                "            <p>旗县</p>" + 
                                "        </td>" + 
                                "        <td width=\"200\" height=\"40\" align=center>" + 
                                "            <p>" + xianItem.name + "</p>" + 
                                "        </td>" + 
                                "    </tr>" + 
                                "    <tr>" + 
                                "        <td width=\"100\" height=\"40\" align=center>" + 
                                "            <p>乡镇</p>" + 
                                "        </td>" + 
                                "        <td width=\"200\" height=\"40\" align=center>" + 
                                "            <p>" + pointItem.info1 + "</p>" + 
                                "        </td>" + 
                                "    </tr>" + 
                                "    <tr>" + 
                                "        <td width=\"100\" height=\"40\" align=center>" + 
                                "            <p>村组</p>" + 
                                "        </td>" + 
                                "        <td width=\"200\" height=\"40\" align=center>" + 
                                "            <p>" + pointItem.info2 + "</p>" + 
                                "        </td>" + 
                                "    </tr>" + 
                                "    <tr>" + 
                                "        <td width=\"100\" height=\"40\" align=center>" + 
                                "            <p>田块面积</p>" + 
                                "        </td>" + 
                                "        <td width=\"200\" height=\"40\" align=center>" + 
                                "            <p>" + pointItem.info3 + "</p>" + 
                                "        </td>" + 
                                "    </tr>" + 
                                "    <tr>" + 
                                "        <td width=\"100\" height=\"40\" align=center>" + 
                                "            <p>代表面积</p>" + 
                                "        </td>" + 
                                "        <td width=\"200\" height=\"40\" align=center>" + 
                                "            <p>" + pointItem.info4 + "</p>" + 
                                "        </td>" + 
                                "    </tr>" + 
                                "    <tr>" + 
                                "        <td width=\"100\" height=\"40\" align=center>" + 
                                "            <p>种植作物</p>" + 
                                "        </td>" + 
                                "        <td width=\"200\" height=\"40\" align=center>" + 
                                "            <p>" + pointItem.info5 + "</p>" + 
                                "        </td>" + 
                                "    </tr>" + 
                                "    <tr>" + 
                                "        <td width=\"100\" height=\"40\" align=center>" + 
                                "            <p>测报对象</p>" + 
                                "        </td>" + 
                                "        <td width=\"200\" height=\"40\" align=center>" + 
                                "            <p>" + pointItem.info6 + "</p>" + 
                                "        </td>" + 
                                "    </tr>" + 
                                "    </tbody>" + 
                                "</table>" + 
                                "<p></p>" + 
                                "<table align=\"center\" style=\"color: #7AB038;\" bordercolor=\"#7AB038\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">" + 
                                "    <tbody>" + 
                                "    <tr>" + 
                                "        <td width=\"300\" height=\"40\">" + 
                                "            <p>备注信息:</p>" + 
                                "            <p></p>" + 
                                "            <p>" + pointItem.note + "</p>" + 
                                "        </td>" + 
                                "    </tr>" + 
                                "    </tbody>" + 
                                "</table>" + 
                                "</body>" + 
                                "</html>";

                    pointInfo.Add("content", htmlContent);
                    result.retcode = ErrMgr.ERRCODE_NONE;
                    result.retmsg = ErrMgr.ERRMSG_NONE;
                    result.retdata = pointInfo;
                }                
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getBlights(long userid,
            int kind,
            String search,
            String token)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            if (!checkUserTokenIsValid(userid, token))
            {
                result.retcode = ErrMgr.ERRCODE_TOKEN;
                result.retmsg = ErrMgr.ERRMSG_TOKEN;
                return result;
            }

            try
            {
                dbConn = new BingchongDBDataContext();

                List<DataClasses.STBlight> arrBlights = new List<DataClasses.STBlight>();

                arrBlights = dbConn.blights
                    .Where(m => m.deleted == 0 &&
                        m.name.Contains(search))
                    .OrderBy(m => m.serial)
                    .Select(m => new DataClasses.STBlight
                    {
                        uid = m.uid,
                        name = m.name,
                        kind = m.kind,
                        info1 = m.info1,
                        info2 = m.info2,
                        info3 = m.info3,
                        info4 = m.info4,
                        info5 = m.info5,
                        info6 = m.info6,
                        photo_list = m.photo_list,
                        serial = m.serial,
                        form_ids = m.form_ids
                    })
                    .OrderBy(m => m.kind)
                    .ToList();

                if (kind >= 0)
                {
                    arrBlights = arrBlights.Where(m => m.kind == kind).ToList();
                }
                
                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = arrBlights;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getBlightInfo(long userid,
            long blight_id,
            String token)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            if (!checkUserTokenIsValid(userid, token))
            {
                result.retcode = ErrMgr.ERRCODE_TOKEN;
                result.retmsg = ErrMgr.ERRMSG_TOKEN;
                return result;
            }

            try
            {
                dbConn = new BingchongDBDataContext();

                blight blightItem = dbConn.blights
                    .Where(m => m.uid == blight_id &&
                    m.deleted == 0)
                    .FirstOrDefault();

                if (blightItem == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;
                }
                else
                {   
                    Dictionary<String, Object> blightInfo = new Dictionary<String, Object>();
                    String htmlContent = "";
                    if (blightItem.kind == 1)
                    {
                        htmlContent = "<!doctype html>" +
                                "<html>" +
                                "<head>" +
                                "    <meta charset=\"utf-8\" />" +
                                "</head>" +
                                "<body>" +
                                "<h1 align=\"center\"> <font size=6 color=\"#7AB038\">" + blightItem.name + "</font></h1>" +
                                "<p> </p>" +
                                "<table width=\"300\" align=\"center\" style=\"color: #7AB038;\" bordercolor=\"#7AB038\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">" +
                                "<tbody>";

                        List<String> photoList = blightItem.photo_list.Split(',').ToList();
                        
                        foreach (String path in photoList)
                        {
                            if(String.IsNullOrEmpty(path.Replace(" ", "")))
                                continue;

                            htmlContent += "<tr>" +
                                            " <td align=\"center\">" +
                                            "     <p></p>" +
                                            "     <img width = \"300\" src=\"" + Global.getAbsImgUrl(path) + "\" />" +
                                            "     <p>" + "图片" + "</p>" +
                                            " </td>" +
                                            " <?php }?>" +
                                            "</tr>";
                        }

                        htmlContent += "</tbody>" +
                                    "</table>";
                        htmlContent +=
                                "<table width=\"300\" align=\"center\" style=\"color: #7AB038;\" bordercolor=\"#7AB038\" border=\"1\" cellspacing=\"0\" cellpadding=\"5\">" +
                                "    <tbody>" +                                
                                "    <tr>" +
                                "        <td width=\"80\" height=\"40\" valign=top>" +
                                "            <p>形态特征:</p>" +
                                "        </td>" +
                                "        <td width=\"220\" height=\"40\" align=center>" +
                                "            <p>" + blightItem.info1 + "</p>" +
                                "        </td>" +
                                "    </tr>" +
                                "    <tr>" +
                                "        <td width=\"80\" height=\"40\" valign=top>" +
                                "            <p>危害特点:</p>" +
                                "        </td>" +
                                "        <td width=\"200\" height=\"40\" align=center>" +
                                "            <p>" + blightItem.info2 + "</p>" +
                                "        </td>" +
                                "    </tr>" +
                                "    <tr>" +
                                "        <td width=\"80\" height=\"40\" valign=top>" +
                                "            <p>生活习性:</p>" +
                                "        </td>" +
                                "        <td width=\"200\" height=\"40\" align=center>" +
                                "            <p>" + blightItem.info3 + "</p>" +
                                "        </td>" +
                                "    </tr>" +
                                "    <tr>" +
                                "        <td width=\"80\" height=\"40\" valign=top>" +
                                "            <p>发生规律:</p>" +
                                "        </td>" +
                                "        <td width=\"200\" height=\"40\" align=center>" +
                                "            <p>" + blightItem.info4 + "</p>" +
                                "        </td>" +
                                "    </tr>" +
                                "    <tr>" +
                                "        <td width=\"80\" height=\"40\" valign=top>" +
                                "            <p>防治方法:</p>" +
                                "        </td>" +
                                "        <td width=\"200\" height=\"40\" align=center>" +
                                "            <p>" + blightItem.info5 + "</p>" +
                                "        </td>" +
                                "    </tr>" +
                                "    </tbody>" +
                                "</table>" +
                                "</body>" +
                                "</html>";
                    }
                    else
                    {
                        htmlContent = "<!doctype html>" +
                                "<html>" +
                                "<head>" +
                                "    <meta charset=\"utf-8\" />" +
                                "</head>" +
                                "<body>" +
                                "<h1 align=\"center\"> <font size=6 color=\"#7AB038\">" + blightItem.name + "</font></h1>" +
                                "<p> </p>" +
                                "<table width=\"300\" align=\"center\" style=\"color: #7AB038;\" bordercolor=\"#7AB038\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">" +
                                "<tbody>";

                        List<String> photoList = blightItem.photo_list.Split(',').ToList();

                        foreach (String path in photoList)
                        {
                            if (path.Replace(" ", "").Length <= 0)
                                continue;

                            htmlContent += "<tr>" +
                                            " <td align=\"center\">" +
                                            "     <p></p>" +
                                            "     <img width = \"300\" src=\"" + Global.getAbsImgUrl(path) + "\" />" +
                                            "     <p>" + "图片" + "</p>" +
                                            " </td>" +
                                            " <?php }?>" +
                                            "</tr>";
                        }

                        htmlContent += "</tbody>" +
                                    "</table>";
                        htmlContent +=
                                "<table width=\"300\" align=\"center\" style=\"color: #7AB038;\" bordercolor=\"#7AB038\" border=\"1\" cellspacing=\"0\" cellpadding=\"5\">" +
                                "    <tbody>" +
                                "    <tr>" +
                                "        <td width=\"80\" height=\"40\" valign=top>" +
                                "            <p>病 原:</p>" +
                                "        </td>" +
                                "        <td width=\"220\" height=\"40\" align=center>" +
                                "            <p>" + blightItem.info1 + "</p>" +
                                "        </td>" +
                                "    </tr>" +
                                "    <tr>" +
                                "        <td width=\"80\" height=\"40\" valign=top>" +
                                "            <p>症 状:</p>" +
                                "        </td>" +
                                "        <td width=\"200\" height=\"40\" align=center>" +
                                "            <p>" + blightItem.info2 + "</p>" +
                                "        </td>" +
                                "    </tr>" +
                                "    <tr>" +
                                "        <td width=\"80\" height=\"40\" valign=top>" +
                                "            <p>传播途径:</p>" +
                                "        </td>" +
                                "        <td width=\"200\" height=\"40\" align=center>" +
                                "            <p>" + blightItem.info3 + "</p>" +
                                "        </td>" +
                                "    </tr>" +
                                "    <tr>" +
                                "        <td width=\"80\" height=\"40\" valign=top>" +
                                "            <p>发病条件:</p>" +
                                "        </td>" +
                                "        <td width=\"200\" height=\"40\" align=center>" +
                                "            <p>" + blightItem.info4 + "</p>" +
                                "        </td>" +
                                "    </tr>" +
                                "    <tr>" +
                                "        <td width=\"80\" height=\"40\" valign=top>" +
                                "            <p>防治方法:</p>" +
                                "        </td>" +
                                "        <td width=\"200\" height=\"40\" align=center>" +
                                "            <p>" + blightItem.info5 + "</p>" +
                                "        </td>" +
                                "    </tr>" +
                                "    </tbody>" +
                                "</table>" +
                                "</body>" +
                                "</html>";
                    }
                    

                    blightInfo.Add("content", htmlContent);
                    result.retcode = ErrMgr.ERRCODE_NONE;
                    result.retmsg = ErrMgr.ERRMSG_NONE;
                    result.retdata = blightInfo;
                }
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getUsers(long admin_id,
            int level,
            long sheng_id,
            long shi_id,
            long xian_id,
            String search)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();

                List<DataClasses.STItem> arrUsers = new List<DataClasses.STItem>();
                if (search == null)
                    search = "";

                if (admin_id > 0)
                {
                    user adminItem = dbConn.users
                        .Where(m => m.deleted == 0 &&
                        m.uid == admin_id)
                        .FirstOrDefault();
                    if (adminItem == null)
                    {
                        result.retcode = ErrMgr.ERRCODE_NORMAL;
                        result.retmsg = ErrMgr.ERRMSG_NODATA;
                        return result;
                    }
                    arrUsers = dbConn.users
                        .Where(m => m.right_id == 0 &&           
                            m.level == adminItem.level &&
                            m.deleted == 0 &&
                            m.name.Contains(search))
                        .Where(m => (adminItem.level == 0) ||
                            (adminItem.level == 1 && adminItem.xian_id == m.xian_id) ||
                            (adminItem.level == 2 && adminItem.shi_id == m.shi_id) ||
                            (adminItem.level == 3 && adminItem.sheng_id == m.sheng_id))
                        .Select(m => new DataClasses.STItem
                        {
                            uid = m.uid,
                            name = m.name
                        })
                        .ToList();
                }
                else
                {
                    List<user> dbUsers = dbConn.users
                        .Where(m => m.right_id == 0 &&                            
                            m.deleted == 0 &&
                            m.name.Contains(search))                        
                        .ToList();

                    if (level == 4)
                    {
                        dbUsers = dbUsers.Where(m => m.level == level).ToList();
                    }
                    else if (level == 3)
                    {
                        dbUsers = dbUsers.Where(m => m.level == level && m.sheng_id == sheng_id).ToList();
                    }
                    else if (level == 2)
                    {
                        dbUsers = dbUsers.Where(m => m.level == level && m.shi_id == shi_id).ToList();
                    }
                    else if (level == 1)
                    {
                        dbUsers = dbUsers.Where(m => m.level == level && m.xian_id == xian_id).ToList();
                    }
                    else
                    {
                        if (sheng_id > 0)
                            dbUsers = dbUsers.Where(m => m.sheng_id == sheng_id).ToList();
                        if(shi_id > 0)
                            dbUsers = dbUsers.Where(m => m.shi_id == shi_id).ToList();
                        if(xian_id > 0)
                            dbUsers = dbUsers.Where(m => m.xian_id == xian_id).ToList();
                    }

                    arrUsers = dbUsers
                        .Select(m => new DataClasses.STItem
                        {
                            uid = m.uid,
                            name = m.name
                        })
                        .ToList();
                }

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = arrUsers;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getUserInfo(long userid)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();

                user userItem = dbConn.users
                    .Where(m => m.uid == userid &&
                    m.deleted == 0)
                    .FirstOrDefault();

                if (userItem == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;
                }
                else
                {
                    sheng shengItem = dbConn.shengs
                        .Where(m => m.uid == userItem.sheng_id &&
                        m.deleted == 0)
                        .FirstOrDefault();

                    shi shiItem = dbConn.shis
                        .Where(m => m.uid == userItem.shi_id &&
                        m.deleted == 0)
                        .FirstOrDefault();

                    xian xianItem = dbConn.xians
                        .Where(m => m.uid == userItem.xian_id &&
                        m.deleted == 0)
                        .FirstOrDefault();


                    Dictionary<String, Object> userInfo = new Dictionary<String, Object>();

                    userInfo.Add("uid", userItem.uid);
                    userInfo.Add("name", userItem.name);
                    userInfo.Add("place", userItem.place);
                    userInfo.Add("job", userItem.job);
                    userInfo.Add("phone", userItem.phone);
                    userInfo.Add("point_list", Global.ignoreNullStr(userItem.point_list));

                    String photo_path = "";
                    String user_type = "测报员";
                    if (userItem.right_id != 0)
                    {
                        user_type = "管理员";
                    }

                    String strUserPoints = "";
                    List<String> arrPoints = new List<String>();
                    try
                    {
                        if (!String.IsNullOrEmpty(userItem.point_list))
                            arrPoints = userItem.point_list.Split(',').ToList();
                    }
                    catch (System.Exception ex)
                    {
                        Global.logError(ex.Message);
                    }

                    foreach(String item in arrPoints)
                    {
                        if(String.IsNullOrEmpty(item))
                            continue;
                        point pointItem = dbConn.points
                            .Where(m => m.uid == Convert.ToInt32(item)).FirstOrDefault();

                        if (pointItem == null)
                            continue;

                        if (!String.IsNullOrEmpty(strUserPoints))
                            strUserPoints += ",";

                        strUserPoints += pointItem.name;
                    }

                    String htmlContent = "";
                    htmlContent = "<!doctype html>" +
                            "<html>" +
                            "<head>" +
                            "    <meta charset=\"utf-8\" />" +
                            "</head>" +
                            "<body>" +
                            "<div class=\"right_cont\">" +
                            "   <div align=\"center\" class=\"photo_logo\">" +
                            "      <img width=\"120\" src=" + photo_path + "/>" +
                            "   </div>" +
                            "</div>" +
                            "<p> </p>" +
                            "<table align=\"center\" style=\"color: #7AB038;\" bordercolor=\"#7AB038\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">" +
                            "    <tbody>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>用户名</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + userItem.name + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>姓名</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + userItem.realname + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>用户类别</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + user_type + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>省\\自治区</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + (shengItem == null ? "" : shengItem.name) + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>盟\\市</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + (shiItem == null ? "" : shiItem.name) + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>旗县</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + (xianItem == null ? "" : xianItem.name) + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>所在单位</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + userItem.place + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>职务/职称</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + userItem.job + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>测报点</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + strUserPoints + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>手机号码</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + userItem.phone + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    </tbody>" +
                            "</table>" +
                            "</body>" +
                            "</html>";
                    

                    userInfo.Add("content", htmlContent);
                    result.retcode = ErrMgr.ERRCODE_NONE;
                    result.retmsg = ErrMgr.ERRMSG_NONE;
                    result.retdata = userInfo;
                }
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult updateUserInfo(long userid,
            String phone,
            String place,
            String job,
            String photo,
            String point_list,
            String token)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            if (!checkUserTokenIsValid(userid, token))
            {
                result.retcode = ErrMgr.ERRCODE_TOKEN;
                result.retmsg = ErrMgr.ERRMSG_TOKEN;
                return result;
            }

            try
            {
                dbConn = new BingchongDBDataContext();

                user userItem = dbConn.users
                    .Where(m => m.uid == userid &&
                        m.deleted == 0)
                    .FirstOrDefault();

                if (userItem == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NOTREGUSER;
                }
                else
                {
                    userItem.phone = phone;
                    userItem.place = place;
                    userItem.job = job;                    
                    userItem.point_list = point_list;
                    if(photo.Length > 0)
                        userItem.imgurl = Global.saveImage(photo, userItem.imgurl);

                    dbConn.SubmitChanges();

                    Dictionary<String, object> retdata = new Dictionary<String, object>();
                    retdata.Add("imgurl", Global.getAbsImgUrl(userItem.imgurl));

                    result.retcode = ErrMgr.ERRCODE_NONE;
                    result.retmsg = ErrMgr.ERRMSG_NONE;
                    result.retdata = retdata;
                }
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getReports(long userid,
            long point_id,
            String start_time,
            String end_time,
            String token)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            if (!checkUserTokenIsValid(userid, token))
            {
                result.retcode = ErrMgr.ERRCODE_TOKEN;
                result.retmsg = ErrMgr.ERRMSG_TOKEN;
                return result;
            }

            try
            {
                dbConn = new BingchongDBDataContext();
                DateTime startTime = Convert.ToDateTime(start_time);
                DateTime endTime = Convert.ToDateTime(end_time);
                List<DataClasses.STReport> arrReports = dbConn.reports
                    .Where(m => m.point_id == point_id &&
                        m.report_time >= startTime &&
                        m.report_time <= endTime)
                    .Join(dbConn.users, m => m.user_id, l => l.uid, (m, l) => new { _report = m, _user = l})
                    .Join(dbConn.blights, m => m._report.blight_id, l => l.uid, (m, l) => new { _report = m, _blight = l })
                    .Join(dbConn.forms, m => m._report._report.form_id, l => l.uid, (m, l) => new { _report = m, _form = l })
                    .OrderBy(m => m._report._report._report.report_time)
                    .Select(m => new DataClasses.STReport
                    {
                        uid = m._report._report._report.uid,
                        form_id = m._report._report._report.form_id,
                        form_name = m._form.name,
                        user_name = m._report._report._user.name,
                        blight_kind = m._report._blight.kind,
                        blight_name = m._report._blight.name,
                        report_time = m._report._report._report.report_time.ToString(),
                        review_status = m._report._report._report.review_state
                    })
                    .ToList();

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = arrReports;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getReportInfo(long userid,
            long report_id,
            String token)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            if (!checkUserTokenIsValid(userid, token))
            {
                result.retcode = ErrMgr.ERRCODE_TOKEN;
                result.retmsg = ErrMgr.ERRMSG_TOKEN;
                return result;
            }

            try
            {
                dbConn = new BingchongDBDataContext();

                report reportItem = dbConn.reports
                    .Where(m => m.uid == report_id &&
                    m.deleted == 0)
                    .FirstOrDefault();

                if (reportItem == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;
                }
                else
                {                    
                    Dictionary<String, Object> reportDetailInfo = new Dictionary<String, Object>();

                    String strKind = "病害";
                    List<DataClasses.STReportDetail> arrReportDetails = dbConn.report_details
                        .Where(m => m.report_id == report_id &&
                        m.deleted == 0)
                        .Join(dbConn.fields, m => m.field_id, l => l.uid, (m, l) => new { _detail = m, _field = l })
                        .Select(row => new DataClasses.STReportDetail{
                            uid = row._detail.uid,
                            field_name = row._field.name,
                            value_integer = row._detail.value_integer,
                            value_real = row._detail.value_real,
                            value_text = row._detail.value_text,
                            field_type = row._field.type,
                            field_unit = row._field.note
                        })
                        .ToList();

                    blight blightItem = dbConn.blights.Where(m => m.uid == reportItem.blight_id).FirstOrDefault();
                    form formItem = dbConn.forms.Where(m => m.uid == reportItem.form_id).FirstOrDefault();

                    if (reportItem != null && blightItem.kind == 1)
                        strKind = "虫害";

                    String htmlContent = "";
                    htmlContent = "<!doctype html>" +
                            "<html>" +
                            "<head>" +
                            "    <meta charset=\"utf-8\" />" +
                            "</head>" +
                            "<body>" +
                            "<p> </p>" +
                            "<table align=\"center\" style=\"color: #7AB038;\" bordercolor=\"#7AB038\"  border=\"1\" cellspacing=\"0\" cellpadding=\"0\">" +
                            "    <tbody>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>病虫害类别</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + strKind + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>名称</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + blightItem.name + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>测报时间</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + reportItem.watch_time.ToString() + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>上报时间</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + reportItem.report_time.ToString() + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>报表名称</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + formItem.name + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    </tbody>" +
                            "</table>" +
                            "<p> </p>" +
                            "<h2 align=\"center\"> <font size=5 color=\"#7AB038\">" + formItem.name + "</font></h2>" +
                            "<table align=\"center\" style=\"color: #7AB038;\" bordercolor=\"#7AB038\" border=\"1\" cellspacing=\"0\" cellpadding=\"5\">" +
                            "    <tbody>";

                    foreach(DataClasses.STReportDetail detailItem in arrReportDetails)
                    {
                        String field_value = "";
                        if (detailItem.field_type.Contains("r"))
                        {
                            field_value = detailItem.value_real + "";
                        }
                        else if (detailItem.field_type.Contains("i"))
                        {
                            field_value = detailItem.value_integer + "";
                        }
                        else
                        {
                            field_value = detailItem.value_text;
                        }
                        htmlContent += "<tr>" +
                                       "     <td width=\"130\" height=\"40\" align=left>" +
                                       "         <p>" + detailItem.field_name + "</p>" +
                                       "     </td>" +
                                       "     <td width=\"150\" height=\"40\" align=left>" +
                                       "         <p>" + field_value + "</p>" +
                                       "     </td>" +
                                       " </tr>";
                    }

                    htmlContent = htmlContent +
                            "</body>" +
                            "</html>";


                    reportDetailInfo.Add("content", htmlContent);
                    result.retcode = ErrMgr.ERRCODE_NONE;
                    result.retmsg = ErrMgr.ERRMSG_NONE;
                    result.retdata = reportDetailInfo;
                }
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getForms(long blight_id)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();
                blight blightItem = dbConn.blights
                    .Where(m => m.uid == blight_id &&
                    m.deleted == 0)
                    .FirstOrDefault();

                List<DataClasses.STForm> arrForms = new List<DataClasses.STForm>();

                if (blightItem != null)
                {
                    arrForms = dbConn.forms
                        .Where(m => m.deleted == 0 &&
                            blightItem.form_ids.Split(',').Contains(m.uid.ToString()))
                        .AsEnumerable()
                        .Select(m => new DataClasses.STForm
                        {
                            uid = m.uid,
                            name = m.name,
                            fields = sortArrFields(m.field_ids.Split(',').ToList()),
                            //arrFieldIds = m.field_ids.Split(',').ToList(),
                        })                        
                        .ToList();
                }
                else
                {
                    arrForms = dbConn.forms
                        .Where(m => m.deleted == 0)
                        .AsEnumerable()
                        .Select(m => new DataClasses.STForm
                        {
                            uid = m.uid,
                            name = m.name,
                            fields = sortArrFields(m.field_ids.Split(',').ToList()),
                            //arrFieldIds = m.field_ids.Split(',').ToList(),
                        })                        
                        .ToList();
                }

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = arrForms;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static List<DataClasses.STField> sortArrFields(List<String> arrFields)
        {
            BingchongDBDataContext dbConn = new BingchongDBDataContext();

            List<DataClasses.STField> result = new List<DataClasses.STField>();
            List<field> resultArray = new List<field>();
            field fieldItem = null;

            while (arrFields.Count() > 0)
            {
                String strItem = arrFields.FirstOrDefault();
                if (strItem == null)
                    break;

                long fieldId = 0;
                try
                {
                    fieldId = Convert.ToInt32(strItem);
                }
                catch (Exception ex) { Global.logError(ex.Message); }                
                if(fieldId == 0) continue;

                fieldItem = dbConn.fields
                    .Where(m => m.uid == fieldId)
                    .FirstOrDefault();

                if (fieldItem == null) continue;

                resultArray.Add(fieldItem);
                arrFields.Remove(strItem);

                List<field> l1Fields = dbConn.fields
                    .Where(m => m.parent_fieldid == fieldId && 
                        arrFields.Contains(m.uid.ToString()) && m.deleted == 0)                    
                    .ToList()
                    .OrderBy(m => arrFields.IndexOf(m.uid.ToString()))
                    .ToList();
                foreach (field l1Member in l1Fields)
                {
                    resultArray.Add(l1Member);
                    arrFields.Remove(l1Member.uid.ToString());

                    List<field> l2Fields = dbConn.fields
                        .Where(m => m.parent_fieldid == l1Member.uid &&
                            arrFields.Contains(m.uid.ToString()) && m.deleted == 0)
                        .ToList()
                        .OrderBy(m => arrFields.IndexOf(m.uid.ToString()))
                        .ToList();
                    foreach (field l2Member in l2Fields)
                    {
                        resultArray.Add(l2Member);
                        arrFields.Remove(l2Member.uid.ToString());

                        List<field> l3Fields = dbConn.fields
                            .Where(m => m.parent_fieldid == l2Member.uid &&
                                arrFields.Contains(m.uid.ToString()) && m.deleted == 0)
                            .ToList()
                            .OrderBy(m => arrFields.IndexOf(m.uid.ToString()))
                            .ToList();
                        foreach (field l3Member in l3Fields)
                        {
                            resultArray.Add(l3Member);
                            arrFields.Remove(l3Member.uid.ToString());
                        }
                    }
                }
            }

            result = resultArray.Select(l => new DataClasses.STField
                        {
                            uid = l.uid,
                            name = getAbsFieldName(l.uid),
                            type = l.type,
                            parent_fieldid = l.parent_fieldid,
                            note = l.note
                        })
                        .ToList();

            return result;
        }

        public static String getAbsFieldName(long filed_id)
        {
            BingchongDBDataContext dbConn = new BingchongDBDataContext();

            String name = "";
            field fileItem = dbConn.fields
                .Where(m => m.uid == filed_id)
                .FirstOrDefault();

            if (fileItem == null)
                return name;

            name = fileItem.name;
            while (fileItem != null && fileItem.parent_fieldid != null)
            {
                name = "__" + name;
                fileItem = dbConn.fields
                    .Where(m => m.uid == fileItem.parent_fieldid)
                    .FirstOrDefault();
            }
            return name;
        }

        public static SVCResult getFields(long form_id)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();
                form formItem = dbConn.forms
                    .Where(m => m.uid == form_id &&
                    m.deleted == 0)
                    .FirstOrDefault();

                List<DataClasses.STField> arrFields = dbConn.fields
                    .Where(m => m.deleted == 0 &&
                        formItem.field_ids.Split(',').Contains(m.uid.ToString()))
                    .Select(m => new DataClasses.STField
                    {
                        uid = m.uid,
                        name = m.name,
                        type = m.type,
                        parent_fieldid = m.parent_fieldid,
                        note = m.note
                    })
                    .ToList();

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = arrFields;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult setReports(long userid,
            long form_id,
            long point_id,
            long blight_id,
            String photo,
            String watch_time,
            String fields,
            String token)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            if (!checkUserTokenIsValid(userid, token))
            {
                result.retcode = ErrMgr.ERRCODE_TOKEN;
                result.retmsg = ErrMgr.ERRMSG_TOKEN;
                return result;
            }

            try
            {
                dbConn = new BingchongDBDataContext();

                user userItem = dbConn.users
                    .Where(m => m.uid == userid &&
                    m.deleted == 0)
                    .FirstOrDefault();
                
                form formItem = dbConn.forms
                    .Where(m => m.uid == form_id &&
                    m.deleted == 0)
                    .FirstOrDefault();

                point pointItem = dbConn.points
                    .Where(m => m.uid == point_id &&
                    m.deleted == 0)
                    .FirstOrDefault();
                
                blight blightItem = dbConn.blights
                    .Where(m => m.uid == blight_id &&
                    m.deleted == 0)
                    .FirstOrDefault();

                String[] arrFields = fields.Split(',');

                if (userItem == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NOTREGUSER;
                    return result;
                }

                if (formItem == null ||
                    pointItem == null ||
                    blightItem == null ||
                    Convert.ToDateTime(watch_time) == null ||
                    arrFields == null ||
                    arrFields.Length < 1)
                {
                    result.retcode = ErrMgr.ERRCODE_PARAM;
                    result.retmsg = ErrMgr.ERRMSG_PARAM;
                    return result;
                }

                report reportItem = dbConn.reports
                    .Where(m => m.user_id == userid &&
                    m.report_time.Year == DateTime.Now.Year &&
                    m.report_time.Month == DateTime.Now.Month &&
                    m.report_time.Day == DateTime.Now.Day &&
                    m.blight_id == blight_id &&
                    m.point_id == point_id &&
                    m.form_id == form_id &&
                    m.deleted == 0)
                    .FirstOrDefault();

                if (reportItem != null)
                {
                    if (reportItem.review_state != (byte)ConstMgr.REVIEWSTATE.WAIT_CITY_VERIFY)
                    {
                        result.retcode = ErrMgr.ERRCODE_STATE;
                        result.retmsg = ErrMgr.ERRMSG_STATE;
                        return result;
                    }

                    reportItem.photo = Global.saveImage(photo, "");
                    reportItem.watch_time = Convert.ToDateTime(watch_time);
                    reportItem.report_time = DateTime.Now;
                    reportItem.review_state = (byte)ConstMgr.REVIEWSTATE.WAIT_CITY_VERIFY;
                    reportItem.review_log = "";
                    dbConn.SubmitChanges();

                    List<report_detail> detailItems = dbConn.report_details
                        .Where(m => m.report_id == reportItem.uid &&
                        m.deleted == 0)
                        .ToList();

                    Dictionary<long, String> dicVals = new Dictionary<long, String>();
                    foreach (String strField in arrFields)
                    {
                        String[] arrVals = strField.Split(':');
                        if (arrVals.Length < 1)
                            continue;
                        field fieldItem = dbConn.fields
                            .Where(m => m.uid == Convert.ToInt32(arrVals[0]) &&
                            m.deleted == 0)
                            .FirstOrDefault();
                        if (fieldItem == null || fieldItem.type.Equals("p"))
                            continue;

                        String fieldValue = "";
                        if (arrVals.Length >= 2)
                        {
                            fieldValue = arrVals[1];
                        }
                        else
                            continue;

                        dicVals.Add(fieldItem.uid, fieldValue);
                    }

                    foreach (report_detail detailItem in detailItems)
                    {
                        if (dicVals.ContainsKey(detailItem.field_id))
                        {
                            field fieldItem = dbConn.fields
                            .Where(m => m.uid == detailItem.field_id &&
                            m.deleted == 0)
                            .FirstOrDefault();
                            if (fieldItem != null)
                            {
                                if (fieldItem.type.Contains("i"))
                                {
                                    try
                                    {
                                        detailItem.value_integer = Convert.ToInt32(dicVals[fieldItem.uid]);
                                    }
                                    catch {}
                                }
                                else if (fieldItem.type.Contains("r"))
                                {       
                                    try
                                    {
                                        detailItem.value_real = Convert.ToDouble(dicVals[fieldItem.uid]);
                                    }
                                    catch {}
                                }
                                else
                                {
                                    try
                                    {
                                    detailItem.value_text = dicVals[fieldItem.uid];
                                    }
                                    catch { }
                                }

                                dbConn.SubmitChanges();
                                continue;
                            }
                        }

                        detailItem.value_integer = 0;
                        detailItem.value_real = 0;
                        detailItem.value_text = "";
                        dbConn.SubmitChanges();
                    }

                    result.retcode = ErrMgr.ERRCODE_NONE;
                    result.retmsg = ErrMgr.ERRMSG_NONE;
                    return result;
                }

                report report_info = new report();

                report_info.form_id = formItem.uid;
                report_info.user_id = userItem.uid;
                report_info.point_id = pointItem.uid;
                report_info.blight_id = blightItem.uid;
                report_info.photo = Global.saveImage(photo, "");
                report_info.watch_time = Convert.ToDateTime(watch_time);
                report_info.report_time = DateTime.Now;
                report_info.review_state = (byte)ConstMgr.REVIEWSTATE.WAIT_CITY_VERIFY;
                report_info.review_log = "";
                report_info.deleted = 0;

                dbConn.reports.InsertOnSubmit(report_info);
                dbConn.SubmitChanges();

                foreach (String strField in arrFields)
                {
                    String[] arrVals = strField.Split(':');
                    if (arrVals.Length < 1)
                        continue;
                    field fieldItem = dbConn.fields
                        .Where(m => m.uid == Convert.ToInt32(arrVals[0]) &&
                        m.deleted == 0)
                        .FirstOrDefault();
                    if (fieldItem == null || fieldItem.type.Equals("p"))
                        continue;

                    String fieldValue = "";
                    if (arrVals.Length >= 2)
                        fieldValue = arrVals[1];

                    report_detail report_detail_info = new report_detail();
                    report_detail_info.report_id = report_info.uid;
                    report_detail_info.field_id = fieldItem.uid;
                    if (fieldItem.type.Contains("i"))
                    {
                        report_detail_info.value_integer = 0;
                        try
                        {
                            report_detail_info.value_integer = Convert.ToInt32(fieldValue);
                        }
                        catch { }
                    }
                    else if (fieldItem.type.Contains("r"))
                    {
                        report_detail_info.value_real = 0.0;
                        try
                        {
                            report_detail_info.value_real = Convert.ToDouble(fieldValue);
                        }
                        catch { }
                    }
                    else
                    {
                        report_detail_info.value_text = fieldValue;
                    }

                    dbConn.report_details.InsertOnSubmit(report_detail_info);
                    dbConn.SubmitChanges();
                }

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult uploadWatcherTrack(long userid,
            String longitude,
            String latitude,
            String token)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            if (!checkUserTokenIsValid(userid, token))
            {
                result.retcode = ErrMgr.ERRCODE_TOKEN;
                result.retmsg = ErrMgr.ERRMSG_TOKEN;
                return result;
            }

            try
            {
                dbConn = new BingchongDBDataContext();

                user userItem = dbConn.users
                    .Where(m => m.uid == userid &&
                        m.right_id == 0 &&                // check if is watcher
                        m.deleted == 0)
                    .FirstOrDefault();

                if (userItem == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NOTREGUSER;
                }                
                else
                {
                    watcher_track track_info = new watcher_track();

                    track_info.user_id = userItem.uid;
                    track_info.time = DateTime.Now;
                    track_info.longitude = Convert.ToDecimal(longitude);
                    track_info.latitude = Convert.ToDecimal(latitude);
                    track_info.deleted = 0;

                    dbConn.watcher_tracks.InsertOnSubmit(track_info);
                    dbConn.SubmitChanges();

                    result.retcode = ErrMgr.ERRCODE_NONE;
                    result.retmsg = ErrMgr.ERRMSG_NONE;
                }
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getWatcherTracks(long sheng_id,
            long shi_id,
            long xian_id,            
            String date)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            DateTime startTime = Convert.ToDateTime(date);
            DateTime endTime = startTime.AddDays(1);

            try
            {
                dbConn = new BingchongDBDataContext();
                Dictionary<String, object> retdata = new Dictionary<String, object>();

                List<user> arrWatchers = new List<user>();

                arrWatchers = dbConn.users
                    .Where(m => m.right_id == 0 &&
                    m.deleted == 0)
                    .ToList();

                if (sheng_id > 0)
                {
                    arrWatchers = arrWatchers
                        .Where(m => m.sheng_id == sheng_id)
                        .ToList();
                            
                }
                if (shi_id > 0)
                {
                    arrWatchers = arrWatchers
                        .Where(m => m.shi_id == shi_id)
                        .ToList();
                }
                if (xian_id > 0)
                {
                    arrWatchers = arrWatchers
                        .Where(m => m.xian_id == xian_id)
                        .ToList();
                }

                if (arrWatchers == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NONE;
                    return result;
                }

                List<DataClasses.STUserTrack> arrUserTracks = new List<DataClasses.STUserTrack>();

                foreach (user watcherItem in arrWatchers)
                {
                    List<DataClasses.STTrackPoint> arrPoints = dbConn.watcher_tracks
                        .Where(m => m.user_id == watcherItem.uid &&
                            m.time >= startTime && m.time < endTime &&
                            m.deleted == 0)
                        .OrderBy(m => m.time)
                        .Select(m => new DataClasses.STTrackPoint
                        {
                            longitude = m.longitude,
                            latitude = m.latitude,
                            date = m.time
                        })
                        .ToList();
                    arrUserTracks.Add(new DataClasses.STUserTrack
                    {
                        userid = watcherItem.uid,
                        username = watcherItem.name,
                        tracks = arrPoints
                    });
                }
                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = arrUserTracks;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult uploadExtraTask(long admin_id,
            String name,
            long watcher_id,
            String notice_date,
            String report_date,
            String note)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();

                user adminItem = dbConn.users
                    .Where(m => m.uid == admin_id &&
                        m.right_id != 0 &&                // check if is admin
                        m.deleted == 0)
                    .FirstOrDefault();

                user watcherItem = dbConn.users
                    .Where(m => m.uid == watcher_id &&
                        m.right_id == 0 &&                // check if is watcher
                        m.deleted == 0)
                    .FirstOrDefault();

                if (adminItem == null || watcherItem == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NOTREGUSER;
                }
                else
                {
                    extra_task task_info = new extra_task();

                    task_info.name = name;
                    task_info.admin_id = admin_id;
                    task_info.watcher_id = watcher_id;
                    task_info.notice_date = Convert.ToDateTime(notice_date);
                    task_info.report_date = Convert.ToDateTime(report_date);
                    task_info.note = note;
                    task_info.status = (byte)ConstMgr.EXTRATASK_STATE.WAITING;
                    task_info.deleted = 0;

                    dbConn.extra_tasks.InsertOnSubmit(task_info);
                    dbConn.SubmitChanges();

                    result.retcode = ErrMgr.ERRCODE_NONE;
                    result.retmsg = ErrMgr.ERRMSG_NONE;
                }
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult updateExtraTask(long admin_id,
            long watcher_id,
            long task_id,
            int status)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();

                user adminItem = dbConn.users
                    .Where(m => m.uid == admin_id &&
                        m.right_id != 0 &&                // check if is admin
                        m.deleted == 0)
                    .FirstOrDefault();

                user watcherItem = dbConn.users
                    .Where(m => m.uid == watcher_id &&
                        m.right_id == 0 &&                // check if is watcher
                        m.deleted == 0)
                    .FirstOrDefault();

                extra_task task_info = dbConn.extra_tasks
                    .Where(m => m.uid == task_id &&
                    m.status != 2 &&
                    m.deleted == 0)
                    .FirstOrDefault();

                if (task_info == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NOTREGUSER;
                    return result;
                }

                if (watcherItem != null && task_info.watcher_id != watcher_id)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NOTREGUSER;
                    return result;
                }

                if (adminItem != null && task_info.admin_id != admin_id)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NOTREGUSER;
                    return result;
                }

                if (watcherItem != null && task_info.status != 0)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NOTREGUSER;
                    return result;
                }

                if (adminItem != null && task_info.status != 1)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NOTREGUSER;
                    return result;
                }

                task_info.status = (byte)status;
                
                dbConn.SubmitChanges();

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getExtraTasks(long admin_id,
            long watcher_id)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();

                List<DataClasses.STExtraTask> arrTasks = new List<DataClasses.STExtraTask>();

                if (admin_id > 0)
                {
                    arrTasks = dbConn.extra_tasks
                    .Where(m => m.deleted == 0 &&
                        m.admin_id == admin_id)
                    .Join(dbConn.users, m => m.watcher_id, l => l.uid, (m, l) => new { _report = m, _user = l })
                    .OrderByDescending(m => m._report.report_date)
                    .Select(row => new DataClasses.STExtraTask
                    {
                        uid = row._report.uid,
                        name = row._report.name,
                        admin_id = row._report.admin_id,
                        watcher_id = row._report.watcher_id,
                        notice_date = Convert.ToDateTime(row._report.notice_date),
                        report_date = Convert.ToDateTime(row._report.report_date),
                        user_name = row._user.name,
                        note = row._report.note,
                        status = row._report.status
                    })
                    .ToList();
                }
                else if (watcher_id > 0)
                {
                    arrTasks = dbConn.extra_tasks
                    .Where(m => m.deleted == 0 &&
                        m.watcher_id == watcher_id)
                    .Join(dbConn.users, m => m.watcher_id, l => l.uid, (m, l) => new { _report = m, _user = l })
                    .OrderByDescending(m => m._report.report_date)
                    .Select(row => new DataClasses.STExtraTask
                    {
                        uid = row._report.uid,
                        name = row._report.name,
                        admin_id = row._report.admin_id,
                        watcher_id = row._report.watcher_id,
                        notice_date = Convert.ToDateTime(row._report.notice_date),
                        report_date = Convert.ToDateTime(row._report.report_date),
                        user_name = row._user.name,
                        note = row._report.note,
                        status = row._report.status
                    })
                    .ToList();
                }
                else
                {
                    arrTasks = dbConn.extra_tasks
                    .Where(m => m.deleted == 0)
                    .Join(dbConn.users, m => m.watcher_id, l => l.uid, (m, l) => new { _report = m, _user = l })
                    .OrderByDescending(m => m._report.report_date)
                    .Select(row => new DataClasses.STExtraTask
                    {
                        uid = row._report.uid,
                        name = row._report.name,
                        admin_id = row._report.admin_id,
                        watcher_id = row._report.watcher_id,
                        notice_date = Convert.ToDateTime(row._report.notice_date),
                        report_date = Convert.ToDateTime(row._report.report_date),
                        user_name = row._user.name,
                        note = row._report.note,
                        status = row._report.status
                    })
                    .ToList();
                }

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = arrTasks;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getTempBlights(long admin_id,
            long watcher_id,
            String search)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();

                user adminItem = null;
                List<DataClasses.STTempBlight> arrBlghts = new List<DataClasses.STTempBlight>();

                if (admin_id > 0)
                {
                    adminItem = dbConn.users
                     .Where(m => m.uid == admin_id &&
                     m.deleted == 0)
                     .FirstOrDefault();
                }                          

                if (adminItem == null && watcher_id == 0)
                {
                    result.retcode = ErrMgr.ERRCODE_PARAM;
                    result.retmsg = ErrMgr.ERRMSG_PARAM;
                }
                else if(adminItem != null)
                {
                    arrBlghts = dbConn.temp_blights
                    .Where(m => m.deleted == 0 &&
                        m.name.Contains(search))
                    .Join(dbConn.users, m => m.watcher_id, l => l.uid, (m, l) => new { _report = m, _user = l })
                    .Where(m => (m._user.level <= adminItem.level && adminItem.level == 0)
                    || (m._user.level <= adminItem.level && adminItem.level == 1 && adminItem.xian_id == m._user.xian_id)
                    || (m._user.level <= adminItem.level && adminItem.level == 2 && adminItem.shi_id == m._user.shi_id)
                    || (m._user.level <= adminItem.level && adminItem.level == 3 && adminItem.sheng_id == m._user.sheng_id))
                    .OrderByDescending(m => m._report.report_date)
                    .Select(row => new DataClasses.STTempBlight
                    {
                        uid = row._report.uid,
                        name = row._report.name,
                        kind = row._report.kind,
                        longitude = row._report.longitude.ToString(),
                        latitude = row._report.latitude.ToString(),
                        info1 = row._report.info1,
                        info2 = row._report.info2,
                        info3 = row._report.info3,
                        photo = Global.getAbsImgUrl(row._report.photo),
                        note = row._report.note,
                        status = row._report.status,
                        watcher_id = row._report.watcher_id,
                        admin_id = (long)row._report.admin_id,
                        report_date = row._report.report_date
                    })
                    .ToList();

                    result.retcode = ErrMgr.ERRCODE_NONE;
                    result.retmsg = ErrMgr.ERRMSG_NONE;
                    result.retdata = arrBlghts;
                }
                else if (watcher_id > 0)
                {
                    arrBlghts = dbConn.temp_blights
                    .Where(m => m.watcher_id == watcher_id &&
                        m.deleted == 0 &&
                        m.name.Contains(search))
                    .Join(dbConn.users, m => m.watcher_id, l => l.uid, (m, l) => new { _report = m, _user = l })
                    .OrderByDescending(m => m._report.report_date)
                    .Select(row => new DataClasses.STTempBlight
                    {
                        uid = row._report.uid,
                        name = row._report.name,
                        kind = row._report.kind,
                        longitude = row._report.longitude.ToString(),
                        latitude = row._report.latitude.ToString(),
                        info1 = row._report.info1,
                        info2 = row._report.info2,
                        info3 = row._report.info3,
                        photo = row._report.photo,
                        note = row._report.note,
                        status = row._report.status,
                        watcher_id = row._report.watcher_id,
                        admin_id = row._report.admin_id,
                        report_date = row._report.report_date
                    })
                    .ToList();

                    result.retcode = ErrMgr.ERRCODE_NONE;
                    result.retmsg = ErrMgr.ERRMSG_NONE;
                    result.retdata = arrBlghts;
                }
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getTempBlightInfo(long blight_id)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();

                DataClasses.STTempBlight blightItem = dbConn.temp_blights
                    .Where(m => m.uid == blight_id &&
                    m.deleted == 0)
                    .Join(dbConn.shengs, m => m.sheng_id, l => l.uid, (m, l) => new { _blight = m, _sheng = l})
                    .Join(dbConn.shis, m => m._blight.shi_id, l => l.uid, (m, l) => new { _blight = m, _shi = l })
                    .Join(dbConn.xians, m => m._blight._blight.xian_id, l => l.uid, (m, l) => new { _blight = m, _xian = l })
                    .Select(row => new DataClasses.STTempBlight
                    {
                        uid = row._blight._blight._blight.uid,
                        name = row._blight._blight._blight.name,
                        kind = row._blight._blight._blight.kind,
                        longitude = row._blight._blight._blight.longitude.ToString(),
                        latitude = row._blight._blight._blight.latitude.ToString(),
                        sheng_name = row._blight._blight._sheng.name,
                        shi_name = row._blight._shi.name,
                        xian_name = row._xian.name,
                        info1 = row._blight._blight._blight.info1,
                        info2 = row._blight._blight._blight.info2,
                        info3 = row._blight._blight._blight.info3,
                        note = row._blight._blight._blight.note,
                        status = row._blight._blight._blight.status,
                        watcher_id = row._blight._blight._blight.watcher_id,
                        admin_id = (long)row._blight._blight._blight.admin_id,
                        report_date = row._blight._blight._blight.report_date,
                        photo = row._blight._blight._blight.photo
                    })
                    .FirstOrDefault();
                
                if (blightItem == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;
                }
                else
                {
                    Dictionary<String, Object> blightInfo = new Dictionary<String, Object>();
                    String htmlContent = "";

                    user watcherItem = dbConn.users
                        .Where(m => m.uid == blightItem.watcher_id &&
                        m.deleted == 0)
                        .FirstOrDefault();

                    user adminItem = dbConn.users
                        .Where(m => m.uid == blightItem.admin_id &&
                        m.deleted == 0)
                        .FirstOrDefault();

                    htmlContent = "<!doctype html>" +
                            "<html>" +
                            "<head>" +
                            "    <meta charset=\"utf-8\" />" +
                            "</head>" +
                            "<body>" +                                                        
                            "<table align=\"center\" style=\"color: #7AB038;\" bordercolor=\"#7AB038\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">" +
                            "    <tbody>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>名称</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + blightItem.name + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>名称</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + blightItem.name + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>省</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + Global.ignoreNullStr(blightItem.sheng_name) + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>市</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + Global.ignoreNullStr(blightItem.shi_name) + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>县</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + Global.ignoreNullStr(blightItem.xian_name) + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>乡镇</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + Global.ignoreNullStr(blightItem.info1) + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>村组</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + Global.ignoreNullStr(blightItem.info2) + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>东经</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + Global.ignoreNullStr(blightItem.longitude) + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>北纬</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + Global.ignoreNullStr(blightItem.latitude) + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>病虫害类别</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + (blightItem.kind == 0 ? "病害" : "虫害") + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>发生作物</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + Global.ignoreNullStr(blightItem.info3) + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>发布者</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + (watcherItem != null ? watcherItem.name : "") + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>发布时间</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + blightItem.report_date.ToString() + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>审核者</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + (adminItem != null ? adminItem.name : "") + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>审核时间</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + (blightItem.review_date != null ? blightItem.review_date.ToString() : "") + "</p>" +
                            "        </td>" +
                            "    </tr>" +
                            "    </tbody>" +
                            "</table>" +
                            "<p></p>" +
	                        "<table align=\"center\" style=\"color: #7AB038;\" bordercolor=\"#7AB038\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">" +
		                    "    <tbody>" +
			                "        <tr>" +
				            "            <td width=\"300\" height=\"40\">" +
					        "                <p>备注信息</p>" +
					        "                <p></p>" +
					        "                <p>" + blightItem.note +"</p>" +
				            "            </td>" +
			                "        </tr>" +
		                    "    </tbody>" +
	                        "</table>"+
	                        "<p></p>" +
	                        "<table align=\"center\" style=\"color: #7AB038;\" bordercolor=\"#7AB038\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">" +
		                    "    <tbody>" +
			                "        <tr>" +
				            "            <td width=\"300\" height=\"40\" border=\"0\">" +
					        "                <p>侵入病虫害照片:</p>" +
				            "            </td>" +
			                "        </tr>" +
				            "            <?php if(!empty($photo)) {?>" +
			                "        <tr>" +
				            "            <td width=\"300\" height=\"40\" border=\"0\" align=center >" +
					        "                <img width=\"300\" src=\"" + Global.getAbsImgUrl(blightItem.photo) + "\" />" +
				            "            </td>" +
			                "        </tr>" +
				            "            <?php } ?>" +
		                    "    </tbody>" +
	                        "</table>" +
                            "</body>" +
                            "</html>";
                    
                    blightInfo.Add("content", htmlContent);
                    result.retcode = ErrMgr.ERRCODE_NONE;
                    result.retmsg = ErrMgr.ERRMSG_NONE;
                    result.retdata = blightInfo;
                }
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult uploadTempBlight(long watcher_id,            
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
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();

                user watcherItem = dbConn.users
                    .Where(m => m.uid == watcher_id &&
                        m.right_id == 0 &&                // check if is watcher
                        m.deleted == 0)
                    .FirstOrDefault();

                if (watcherItem == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NOTREGUSER;
                }
                else
                {
                    temp_blight blight_info = new temp_blight();

                    blight_info.name = name;
                    blight_info.kind = (byte)kind;
                    blight_info.longitude = longitude;
                    blight_info.latitude = latitude;
                    blight_info.sheng_id = watcherItem.sheng_id;
                    blight_info.shi_id = watcherItem.shi_id;
                    blight_info.xian_id = watcherItem.xian_id;
                    blight_info.info1 = info1;
                    blight_info.info2 = info2;
                    blight_info.info3 = info3;
                    blight_info.photo = Global.saveImage(photo, "");
                    blight_info.note = note;
                    blight_info.status = 0;
                    blight_info.watcher_id = watcher_id;
                    blight_info.report_date = DateTime.Now;
                    blight_info.deleted = 0;

                    dbConn.temp_blights.InsertOnSubmit(blight_info);
                    dbConn.SubmitChanges();

                    result.retcode = ErrMgr.ERRCODE_NONE;
                    result.retmsg = ErrMgr.ERRMSG_NONE;
                }
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult updateTempBlight(long admin_id,
            long blight_id,
            int status)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();

                user adminItem = dbConn.users
                    .Where(m => m.uid == admin_id &&
                        m.right_id != 0 &&                // check if is admin
                        m.deleted == 0)
                    .FirstOrDefault();

                temp_blight blight_info = dbConn.temp_blights
                    .Where(m => m.uid == blight_id &&
                    m.status ==0 &&
                    m.deleted == 0)
                    .FirstOrDefault();

                if (blight_info == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NOTREGUSER;
                    return result;
                }

                if (adminItem == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NOTREGUSER;
                    return result;
                }

                blight_info.admin_id = adminItem.uid;
                blight_info.status = (byte)status;
                blight_info.review_date = DateTime.Now;
                dbConn.SubmitChanges();

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getHelps(long userid,
            int type)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();

                Dictionary<String, object> retdata = new Dictionary<String, object>();
                List<DataClasses.STItem> arrHelps = new List<DataClasses.STItem>();

                user userItem = dbConn.users
                    .Where(m => m.uid == userid && m.deleted == 0)
                    .FirstOrDefault();

                if (userItem == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;
                    return result;
                }

                if (type == 0)      // 管理办法
                {
                    IEnumerable<long> arrAdminIds = null;

                    arrAdminIds = dbConn.users
                    .Where(m => (m.right_id > 0 && m.level == userItem.level && m.deleted == 0 &&
                        ((userItem.level == 1 && userItem.xian_id == m.xian_id) ||
                        (userItem.level == 2 && userItem.shi_id == m.shi_id) ||
                        (userItem.level == 3 && userItem.sheng_id == m.sheng_id) ||
                        (userItem.level == 0) ||
                        (userItem.level == 4))))
                        .Select(m => m.uid)
                        .ToList();

                    if (arrAdminIds == null)
                    {
                        result.retcode = ErrMgr.ERRCODE_NORMAL;
                        result.retmsg = ErrMgr.ERRMSG_NOTREGUSER;
                        return result;
                    }

                    arrHelps = dbConn.manuals
                        .Where(m => arrAdminIds.Contains(m.creatuser) &&
                            m.deleted == 0)
                        .OrderByDescending(m => m.creattime)
                        .Select(m => new DataClasses.STItem
                        {
                            uid = m.uid,
                            name = m.title
                        })
                        .ToList();
                }
                else   // 帮助信息
                {
                    arrHelps = dbConn.helps
                        .Where(m => m.deleted == 0)
                        .Select(m => new DataClasses.STItem
                        {
                            uid = m.uid,
                            name = m.title
                        })
                        .ToList();
                }
                
                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = arrHelps;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getHelpInfo(int type,
            long help_id)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();

                Dictionary<String, object> retdata = new Dictionary<String, object>();
                String htmlContent = "";

                if (type == 0)      // 管理办法
                {
                    manual manualItem = dbConn.manuals
                        .Where(m => m.uid == help_id)
                        .FirstOrDefault();

                    user createUser = dbConn.users
                        .Where(m => m.uid == manualItem.creatuser)
                        .FirstOrDefault();

                    /*
                    htmlContent = "<!doctype html>" +
                            "<html>" +
                            "<head>" +
                            "    <meta charset=\"utf-8\" />" +
                            "</head>" +
                            "<body>" +
                            "    <h4 align=\"center\"> <font size=6 color=\"#7AB038\"> 标题：" + manualItem.title + "</font></h4>" +
                            "    <h4 align=\"center\"> <font size=4 color=\"#7AB038\"> 发布者：" + createUser.name + "</font></h4>" +
                            "    <h4 align=\"center\"> <font size=4 color=\"#7AB038\"> 内容详情 </font></h4>" +
                            "    <p> </p>" + manualItem.contents +                            
                            "</body>" +
                            "</html>";
                     */
                    htmlContent = Global.GetHtmlContent(manualItem.contents);
                }
                else   // 帮助信息
                {
                    help helpItem = dbConn.helps
                        .Where(m => m.uid == help_id)
                        .FirstOrDefault();

                    htmlContent = "<!doctype html>" +
                            "<html>" +
                            "<head>" +
                            "    <meta charset=\"utf-8\" />" +
                            "</head>" +
                            "<body>" +
                            "    <h4 align=\"center\"> <font size=4 color=\"#7AB038\"> 标题：" + helpItem.title + "</font></h4>" +
                            "    <p> </p>" + helpItem.contents.Replace("\n", "<br>") +
                            "</body>" +
                            "</html>";
                }
                retdata.Add("content", htmlContent);

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = retdata;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getNotices(long userid,
            int year,
            int type,
            String token)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;
            List<DataClasses.STNotice> arrNotices = new List<DataClasses.STNotice>();
            IEnumerable<long> arrAdminIds = null;

            if (!checkUserTokenIsValid(userid, token))
            {
                result.retcode = ErrMgr.ERRCODE_TOKEN;
                result.retmsg = ErrMgr.ERRMSG_TOKEN;
                return result;
            }

            try
            {
                dbConn = new BingchongDBDataContext();

                user userItem = dbConn.users
                    .Where(m => m.uid == userid &&                        
                        m.deleted == 0)
                    .FirstOrDefault();

                if (userItem == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NOTREGUSER;
                    return result;
                }

                if (userItem.right_id == 0)     // watcher
                {
                    arrAdminIds = dbConn.users
                    .Where(m => (m.right_id > 0 && m.level == userItem.level && m.deleted == 0 &&
                        ((userItem.level == 1 && userItem.xian_id == m.xian_id) ||
                        (userItem.level == 2 && userItem.shi_id == m.shi_id) ||
                        (userItem.level == 3 && userItem.sheng_id == m.sheng_id) ||
                        (userItem.level == 4))))
                        .Select(m => m.uid)
                        .ToList();

                    if (arrAdminIds == null)
                    {
                        result.retcode = ErrMgr.ERRCODE_NORMAL;
                        result.retmsg = ErrMgr.ERRMSG_NOTREGUSER;
                        return result;
                    }

                    arrNotices = dbConn.notices
                        .Where(m => arrAdminIds.Contains(m.user_id) &&
                            m.year == year &&
                            m.deleted == 0)
                        .Join(dbConn.users, m => m.user_id, l => l.uid, (m, l) => new { _notice = m, _user = l })
                        .OrderByDescending(m => m._notice.pub_date)
                        .Select(m => new DataClasses.STNotice
                        {
                            uid = m._notice.uid,
                            title = m._notice.title,
                            pubuser_name = m._user.name,
                            year = m._notice.year,
                            serial = m._notice.serial,
                            pub_date = m._notice.pub_date.ToString(),
                            content = m._notice.contents
                        })
                        .ToList();
                }
                else   // manager
                {
                    if (type == 0)  // 上级发布的通知
                    {
                        arrAdminIds = dbConn.users
                        .Where(m => (m.right_id > 0 && m.level == ((userItem.level + 1) % 4) && m.deleted == 0 &&
                            ((userItem.level == 1 && userItem.shi_id == m.shi_id) ||
                            (userItem.level == 2 && userItem.sheng_id == m.sheng_id) ||
                            (userItem.level == 3))))
                        .Select(m => m.uid)
                        .ToList();

                        if (arrAdminIds == null)
                        {
                            result.retcode = ErrMgr.ERRCODE_NORMAL;
                            result.retmsg = ErrMgr.ERRMSG_NOTREGUSER;
                            return result;
                        }

                        arrNotices = dbConn.notices
                            .Where(m => arrAdminIds.Contains(m.user_id) &&
                                m.year == year &&
                                m.deleted == 0)
                            .Join(dbConn.users, m => m.user_id, l => l.uid, (m, l) => new { _notice = m, _user = l })
                            .OrderByDescending(m => m._notice.pub_date)
                            .Select(m => new DataClasses.STNotice
                            {
                                uid = m._notice.uid,
                                title = m._notice.title,
                                pubuser_name = m._user.name,
                                year = m._notice.year,
                                serial = m._notice.serial,
                                pub_date = m._notice.pub_date.ToString(),
                                content = m._notice.contents
                            })
                            .ToList();
                    }
                    else   // 自己发布的通知
                    {
                        arrNotices = dbConn.notices
                            .Where(m => m.user_id == userItem.uid &&
                                m.year == year &&
                                m.deleted == 0)
                            .Join(dbConn.users, m => m.user_id, l => l.uid, (m, l) => new { _notice = m, _user = l })
                            .OrderByDescending(m => m._notice.pub_date)
                            .Select(m => new DataClasses.STNotice
                            {
                                uid = m._notice.uid,
                                title = m._notice.title,
                                pubuser_name = m._user.name,
                                year = m._notice.year,
                                serial = m._notice.serial,
                                pub_date = m._notice.pub_date.ToString(),
                                content = m._notice.contents
                            })
                            .ToList();
                    }
                    
                }

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = arrNotices;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getNoticeInfo(long notice_id)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();

                notice noticeItem = dbConn.notices
                    .Where(m => m.uid == notice_id &&
                    m.deleted == 0)
                    .FirstOrDefault();

                if (noticeItem == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;
                    return result;
                }

                user publisher = dbConn.users
                    .Where(m => m.uid == noticeItem.user_id &&
                    m.deleted == 0)
                    .FirstOrDefault();

                Dictionary<String, Object> noticeInfo = new Dictionary<String, Object>();
                String htmlContent = "<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
                                    "<head>" +
                                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\"/>" +
                                    "<style type=\"text/css\">" +
                                    "</style>" +
                                    "</head>" +
                                    "<body>" +
                                    "<div id=\"content\">" +
                                    "    <h4>标 题：" + noticeItem.title + "</h4>" +
                                    "</div>" +
                                    "<div id=\"content\">" +
                                    "    <h4>文号：" + noticeItem.serial + "</h4>" +
                                    "</div>" +
                                    "<div id=\"content\">" +
                                    "    <h4>发布者：" + publisher.name + "</h4>" +
                                    "</div>" +
                                    "<div id=\"content\">" +
                                    "    <h4>发布日期：" + noticeItem.pub_date.ToString("yyyy-MM-dd HH:mm:ss") + "</h4>" +
                                    "</div>" +
                                    "<div id=\"content\">" +
                                    "    <div class=\"content-container\"><fieldset>" +
	                                "    <div align=center>" +
	                                "    <h3>通知内容</h3>" +
	                                "    <p></p>" +
	                                "    </div>" +
                                    "    <h4>" + noticeItem.contents + "</h4>" +
                                    "    </fieldset></div>" +
                                    "</div>" +
                                    "</body>" +
                                    "</html>";

                noticeInfo.Add("content", htmlContent);
                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = noticeInfo;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult publishNotice(String title,
            String serial,
            long user_id,
            String content)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();

                user userItem = dbConn.users
                    .Where(m => m.uid == user_id &&
                        m.right_id > 0 &&
                        m.deleted == 0)
                    .FirstOrDefault();

                if (userItem == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NOTREGUSER;
                    return result;
                }

                notice noticeItem = new notice();
                noticeItem.title = title;
                noticeItem.serial = serial;
                noticeItem.user_id = user_id;
                noticeItem.contents = content;
                noticeItem.year = DateTime.Now.Year;
                noticeItem.pub_date = DateTime.Now;

                dbConn.notices.InsertOnSubmit(noticeItem);
                dbConn.SubmitChanges();

                List<user> receiveUsers = dbConn.users
                    .Where(m => m.level == userItem.level && m.deleted == 0 &&
                    ((userItem.level == 1 && m.xian_id == userItem.xian_id) ||
                    (userItem.level == 2 && m.shi_id == userItem.shi_id) ||
                    (userItem.level == 3 && m.sheng_id == userItem.sheng_id) ||
                    (userItem.level == 4)))
                    .ToList();

                if (receiveUsers != null && receiveUsers.Count > 0)
                {
                    foreach (user tmpUser in receiveUsers)
                    {
                        noticetrack trackItem = new noticetrack();
                        trackItem.createtime = DateTime.Now;
                        trackItem.userid = tmpUser.uid;
                        trackItem.noticeid = noticeItem.uid;
                        trackItem.status = 0;
                        trackItem.deleted = 0;
                        dbConn.noticetracks.InsertOnSubmit(trackItem);
                        dbConn.SubmitChanges();
                    }
                }
                
                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getReportLine(long userid,
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
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            if (!checkUserTokenIsValid(userid, token))
            {
                result.retcode = ErrMgr.ERRCODE_TOKEN;
                result.retmsg = ErrMgr.ERRMSG_TOKEN;
                return result;
            }

            try
            {
                dbConn = new BingchongDBDataContext();

                List<DataClasses.STReport> arrReports = dbConn.reports
                    .Where(m => m.blight_id == blight_id &&
                        m.form_id == form_id &&
                        m.deleted == 0 &&
                        m.report_time >= Convert.ToDateTime(start_time) &&
                        m.report_time <= Convert.ToDateTime(end_time))
                    .Join(dbConn.points, m => m.point_id, l => l.uid, (m, l) => new { _report = m, _point = l})
                    .Where(m => (m._point.level == (byte)ConstMgr.POINT_LEVEL.GUOJIA && m._report.review_state == (byte)ConstMgr.REVIEWSTATE.PASSED_PROVINCE) ||
                        m._point.level != (byte)ConstMgr.POINT_LEVEL.GUOJIA)
                    .Select(m => new DataClasses.STReport
                    {
                        uid = m._report.uid,
                        sheng_id = m._point.sheng_id,
                        shi_id = m._point.shi_id,
                        xian_id = m._point.xian_id,
                        point_id = m._report.point_id
                    })
                    .ToList();

                List<long> arrReportIds = new List<long>();
                if (sheng_id > 0)
                {
                    arrReportIds = arrReports
                        .Where(m => m.sheng_id == sheng_id)
                        .Select(s => s.uid)
                        .ToList();
                }

                if (shi_id > 0)
                {
                    arrReportIds = arrReports
                        .Where(m => m.shi_id == shi_id)
                        .Select(s => s.uid)
                        .ToList();
                }

                if (xian_id > 0)
                {
                    arrReportIds = arrReports
                        .Where(m => m.xian_id == xian_id)
                        .Select(s => s.uid)
                        .ToList();
                }

                if (point_id > 0)
                {
                    arrReportIds = arrReports
                        .Where(m => m.point_id == point_id)
                        .Select(s => s.uid)
                        .ToList();
                }

                if (arrReportIds == null || arrReportIds.Count() <= 0)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;

                    return result;
                }

                List<DataClasses.STChartValueItem> arrValueItems = dbConn.report_details
                    .Where(m => arrReportIds.Contains(m.report_id) &&
                        m.field_id == field_id &&
                        m.deleted == 0)
                    .Join(dbConn.reports, m => m.report_id, l => l.uid, (m, l) => new { _detail = m, _report = l })
                    .Join(dbConn.fields, m => m._detail.field_id, l => l.uid, (m, l) => new { _detail = m, _field = l })
                    .Select(row => new DataClasses.STReportDetail
                    {
                        uid = row._detail._detail.uid,
                        field_id = row._detail._detail.field_id,
                        field_type = row._field.type,
                        value_integer = row._detail._detail.value_integer,
                        value_real = row._detail._detail.value_real,
                        value_text = row._detail._detail.value_text,
                        report_time = row._detail._report.report_time,
                        report_date = String.Format("{0}/{1}", row._detail._report.report_time.Month, row._detail._report.report_time.Day)
                    })
                    .Where(m => m.field_type.Contains("r") ||
                        m.field_type.Contains("i"))
                    .ToList()
                    .GroupBy(m => m.report_date)
                    .Select
                    (g => new DataClasses.STReportLineItem
                        {
                            name = g.Key,
                            int_value = g.Where(i => i.value_integer != null).Select(i => (long)i.value_integer).ToList().Sum(),
                            float_value = g.Where(i => i.value_real != null).Select(i => (Double)i.value_real).ToList().Sum()
                        })
                    .ToList()
                    .Select
                    (m => new DataClasses.STChartValueItem
                    {
                        name = m.name,
                        value = m.int_value > 0 ? m.int_value : m.float_value
                    }
                    )
                    .ToList();

                if (arrValueItems == null || arrValueItems.Count() <= 0)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;

                    return result;
                }

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = arrValueItems;                
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getReportBar(long userid,
            long sheng_id,
            long shi_id,
            long xian_id,
            long blight_id,
            long form_id,
            String time,
            long field_id,
            String token)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            if (!checkUserTokenIsValid(userid, token))
            {
                result.retcode = ErrMgr.ERRCODE_TOKEN;
                result.retmsg = ErrMgr.ERRMSG_TOKEN;
                return result;
            }

            try
            {
                dbConn = new BingchongDBDataContext();

                DateTime targetDate = Convert.ToDateTime(time);
                DateTime startTime = new DateTime(targetDate.Year, targetDate.Month, targetDate.Day);
                DateTime endTime = startTime.AddDays(1);

                List<DataClasses.STReport> arrReports = dbConn.reports
                    .Where(m => m.blight_id == blight_id &&
                        m.form_id == form_id &&
                        m.deleted == 0 &&
                        m.report_time >= Convert.ToDateTime(startTime) &&
                        m.report_time < Convert.ToDateTime(endTime))
                    .Join(dbConn.points, m => m.point_id, l => l.uid, (m, l) => new { _report = m, _point = l })
                    .Where(m => (m._point.level == (byte)ConstMgr.POINT_LEVEL.GUOJIA && m._report.review_state == (byte)ConstMgr.REVIEWSTATE.PASSED_PROVINCE) ||
                        m._point.level != (byte)ConstMgr.POINT_LEVEL.GUOJIA)
                    .Select(m => new DataClasses.STReport
                    {
                        uid = m._report.uid,
                        sheng_id = m._point.sheng_id,
                        shi_id = m._point.shi_id,
                        xian_id = m._point.xian_id,
                        point_id = m._report.point_id
                    })
                    .ToList();

                List<long> arrReportIds = new List<long>();
                if (sheng_id > 0)
                {
                    arrReportIds = arrReports
                        .Where(m => m.sheng_id == sheng_id)
                        .Select(s => s.uid)
                        .ToList();
                }

                if (shi_id > 0)
                {
                    arrReportIds = arrReports
                        .Where(m => m.shi_id == shi_id)
                        .Select(s => s.uid)
                        .ToList();
                }

                if (xian_id > 0)
                {
                    arrReportIds = arrReports
                        .Where(m => m.xian_id == xian_id)
                        .Select(s => s.uid)
                        .ToList();
                }

                if (arrReportIds == null || arrReportIds.Count() <= 0)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;

                    return result;
                }

                List<DataClasses.STChartValueItem> arrValueItems = null;
                List<DataClasses.STReportDetail> tmpReportDetails 
                    = dbConn.report_details
                    .Where(m => arrReportIds.Contains(m.report_id) &&
                        m.field_id == field_id &&
                        m.deleted == 0)
                    .Join(dbConn.reports, m => m.report_id, l => l.uid, (m, l) => new { _detail = m, _report = l })
                    .Join(dbConn.fields, m => m._detail.field_id, l => l.uid, (m, l) => new { _detail = m, _field = l })
                    .Join(dbConn.points, m => m._detail._report.point_id, l => l.uid, (m, l) => new { _detail = m, _point = l })
                    .Join(dbConn.shengs, m => m._point.sheng_id, l => l.uid, (m, l) => new { _detail = m, _sheng = l })
                    .Join(dbConn.shis, m => m._detail._point.shi_id, l => l.uid, (m, l) => new { _detail = m, _shi = l })
                    .Join(dbConn.xians, m => m._detail._detail._point.xian_id, l => l.uid, (m, l) => new { _detail = m, _xian = l })
                    .Select(row => new DataClasses.STReportDetail
                    {
                        uid = row._detail._detail._detail._detail._detail._detail.uid,
                        field_id = row._detail._detail._detail._detail._detail._detail.field_id,
                        field_type = row._detail._detail._detail._detail._field.type,
                        value_integer = row._detail._detail._detail._detail._detail._detail.value_integer,
                        value_real = row._detail._detail._detail._detail._detail._detail.value_real,
                        value_text = row._detail._detail._detail._detail._detail._detail.value_text,
                        report_time = row._detail._detail._detail._detail._detail._report.report_time,
                        report_date = String.Format("{0}/{1}", row._detail._detail._detail._detail._detail._report.report_time.Month,
                            row._detail._detail._detail._detail._detail._report.report_time.Day),
                        point_name = row._detail._detail._detail._point.name,                    
                        sheng_name = row._detail._detail._sheng.name,
                        shi_name = row._detail._shi.name,
                        xian_name = row._xian.name,
                    })
                    .Where(m => m.field_type.Contains("r") ||
                        m.field_type.Contains("i"))
                    .ToList();

                List<DataClasses.STReportLineItem> tmpLineItems = null;

                if(sheng_id <= 0)
                {
                    tmpLineItems = tmpReportDetails
                        .GroupBy(m => m.sheng_name)
                        .Select
                        (g => new DataClasses.STReportLineItem
                        {
                            name = g.Key,
                            int_value = g.Where(i => i.value_integer != null).Select(i => (long)i.value_integer).ToList().Sum(),
                            float_value = g.Where(i => i.value_real != null).Select(i => (Double)i.value_real).ToList().Sum()
                        })
                        .ToList();
                }
                else if (shi_id <= 0)
                {
                    tmpLineItems = tmpReportDetails
                        .GroupBy(m => m.shi_name)
                        .Select
                        (g => new DataClasses.STReportLineItem
                        {
                            name = g.Key,
                            int_value = g.Where(i => i.value_integer != null).Select(i => (long)i.value_integer).ToList().Sum(),
                            float_value = g.Where(i => i.value_real != null).Select(i => (Double)i.value_real).ToList().Sum()
                        })
                        .ToList();
                }
                else if (xian_id <= 0)
                {
                    tmpLineItems = tmpReportDetails
                        .GroupBy(m => m.xian_name)
                        .Select
                        (g => new DataClasses.STReportLineItem
                        {
                            name = g.Key,
                            int_value = g.Where(i => i.value_integer != null).Select(i => (long)i.value_integer).ToList().Sum(),
                            float_value = g.Where(i => i.value_real != null).Select(i => (Double)i.value_real).ToList().Sum()
                        })
                        .ToList();
                }
                else
                {
                    tmpLineItems = tmpReportDetails
                        .GroupBy(m => m.point_name)
                        .Select
                        (g => new DataClasses.STReportLineItem
                        {
                            name = g.Key,
                            int_value = g.Where(i => i.value_integer != null).Select(i => (long)i.value_integer).ToList().Sum(),
                            float_value = g.Where(i => i.value_real != null).Select(i => (Double)i.value_real).ToList().Sum()
                        })
                        .ToList();
                }
                    
                arrValueItems = tmpLineItems
                    .Select
                    (m => new DataClasses.STChartValueItem
                    {
                        name = m.name,
                        value = m.int_value > 0 ? m.int_value : m.float_value
                    })
                    .ToList();

                if (arrValueItems == null || arrValueItems.Count() <= 0)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;

                    return result;
                }

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = arrValueItems;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getReportHistory(long userid,            
            long point_id,
            String start_time,
            String end_time,
            String token)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            if (!checkUserTokenIsValid(userid, token))
            {
                result.retcode = ErrMgr.ERRCODE_TOKEN;
                result.retmsg = ErrMgr.ERRMSG_TOKEN;
                return result;
            }

            try
            {
                dbConn = new BingchongDBDataContext();
                DateTime startTime = Convert.ToDateTime(start_time);
                DateTime endTime = Convert.ToDateTime(end_time);
                endTime = endTime.AddDays(1);
                List<DataClasses.STReport> arrReports = dbConn.reports
                    .Where(m => m.point_id == point_id &&
                        m.report_time >= startTime &&
                        m.report_time <= endTime &&
                        m.deleted == 0)
                    .OrderByDescending(m => m.report_time)
                    .Join(dbConn.users, m => m.user_id, l => l.uid, (m, l) => new { _report = m, _user = l })
                    .Join(dbConn.blights, m => m._report.blight_id, l => l.uid, (m, l) => new { _report = m, _blight = l })
                    .Join(dbConn.forms, m => m._report._report.form_id, l => l.uid, (m, l) => new { _report = m, _form = l })
                    .Select(m => new DataClasses.STReport
                    {
                        uid = m._report._report._report.uid,
                        form_id = m._report._report._report.form_id,
                        form_name = m._form.name,
                        user_name = m._report._report._user.name,
                        blight_kind = m._report._blight.kind,
                        blight_name = m._report._blight.name,
                        report_time = String.Format("{0:yyyy-MM-dd HH:mm}", m._report._report._report.report_time),
                        review_status = m._report._report._report.review_state
                    })                    
                    .ToList();

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = arrReports;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getTaskCount(long userid,
            int point_type,
            String start_time,
            String end_time,
            String token)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;
            List<long> arrTaskCounts = new List<long>();
            DateTime startTime = Convert.ToDateTime(start_time);
            DateTime endTime = Convert.ToDateTime(end_time);

            int dayCount = (endTime - startTime).Days + 1;
            for (int i = 0; i < dayCount; i++)
            {
                arrTaskCounts.Add(0);
            }

            if (!checkUserTokenIsValid(userid, token))
            {
                result.retcode = ErrMgr.ERRCODE_TOKEN;
                result.retmsg = ErrMgr.ERRMSG_TOKEN;
                return result;
            }

            try
            {
                dbConn = new BingchongDBDataContext();

                user watcherItem = dbConn.users
                    .Where(m => m.uid == userid &&
                    m.right_id == 0 &&
                    m.deleted == 0)
                    .FirstOrDefault();

                if (watcherItem == null || String.IsNullOrEmpty(watcherItem.point_list))
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;
                    return result;
                }

                String[] arrWatcherPoints = watcherItem.point_list.Split(',');

                if (arrWatcherPoints == null || arrWatcherPoints.Count() <= 0)
                {
                    result.retcode = ErrMgr.ERRCODE_NONE;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;
                    result.retdata = arrTaskCounts;
                    return result;
                }

                List<String> watcherPointList = new List<String>();
                foreach (String strPointUid in arrWatcherPoints)
                {
                    if (String.IsNullOrEmpty(strPointUid))
                        continue;

                    point pointItem = dbConn.points
                        .Where(m => m.uid == Convert.ToInt32(strPointUid))
                        .FirstOrDefault();
                    if (pointItem != null && pointItem.type == point_type)
                        watcherPointList.Add(strPointUid);
                }

                if (watcherPointList.Count() <= 0)
                {
                    result.retcode = ErrMgr.ERRCODE_NONE;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;
                    result.retdata = arrTaskCounts;
                    return result;
                }

                List<DataClasses.STTaskDetail> arrTasks = dbConn.task_details
                    .Where(m => m.deleted == 0)
                    .AsEnumerable()
                    .Select(row => new DataClasses.STTaskDetail
                    {
                        uid = row.uid,
                        arr_points = row.point_list.Split(',').ToList(),
                        startdate = row.startdate,
                        enddate = row.enddate,
                        period = row.period
                    })
                    .Where(m => m.arr_points.Any(op => watcherPointList.Contains(op)))
                    .ToList();

                if (arrTasks == null || arrTasks.Count() <= 0)
                {
                    result.retcode = ErrMgr.ERRCODE_NONE;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;
                    result.retdata = arrTaskCounts;
                    return result;
                }
                
                foreach (DataClasses.STTaskDetail task_item in arrTasks)
                {
                    DateTime sD = task_item.startdate;
                    DateTime eD = task_item.enddate;
                    long period = task_item.period;
                    DateTime d = sD.AddDays(task_item.period - 1);
                    List<String> arrSubPoints = task_item.arr_points.Intersect(watcherPointList).ToList();
                    bool isArrivedToEndDate = false;
                    while(d <= endTime)
                    {
                        if (d >= eD)
                        {
                            if (isArrivedToEndDate)
                                break;
                            d = eD;
                            isArrivedToEndDate = true;
                        }

                        if (d < startTime)
                        {
                            if (!isArrivedToEndDate)
                            {
                                d = d.AddDays(period);
                                if (d > eD)
                                {
                                    d = eD;
                                }
                                continue;
                            }
                            else
                                break;
                        }

                        foreach (String stPoint in arrSubPoints)
                        {
                            report reportItem = dbConn.reports
                                .Where(m => m.point_id == Convert.ToInt32(stPoint) &&
                                    m.blight_id == task_item.blight_id &&
                                    m.form_id == task_item.form_id &&
                                    m.report_time >= d && m.report_time < d.AddDays(1) &&
                                    m.deleted == 0)
                                .FirstOrDefault();

                            if (reportItem == null)
                            {
                                int dayOffset = (d - startTime).Days;
                                arrTaskCounts[dayOffset] += 1;
                            }
                        }

                        d = d.AddDays(period);
                    }
                }
                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = arrTaskCounts;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getTaskList(long userid,
            int type,
            int point_type,
            String start_time,
            String end_time,
            String token)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            if (!checkUserTokenIsValid(userid, token))
            {
                result.retcode = ErrMgr.ERRCODE_TOKEN;
                result.retmsg = ErrMgr.ERRMSG_TOKEN;
                return result;
            }

            try
            {
                dbConn = new BingchongDBDataContext();

                user watcherItem = dbConn.users
                    .Where(m => m.uid == userid &&
                    m.right_id == 0 &&
                    m.deleted == 0)
                    .FirstOrDefault();

                if (watcherItem == null || String.IsNullOrEmpty(watcherItem.point_list))
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;
                    return result;
                }

                String[] arrWatcherPoints = watcherItem.point_list.Split(',');

                if (arrWatcherPoints == null || arrWatcherPoints.Count() <= 0)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;
                    return result;
                }

                List<String> watcherPointList = new List<String>();
                foreach (String strPointUid in arrWatcherPoints)
                {
                    if(String.IsNullOrEmpty(strPointUid))
                        continue;

                    point pointItem = dbConn.points
                        .Where(m => m.uid == Convert.ToInt32(strPointUid))
                        .FirstOrDefault();

                    if(point_type < 0)
                        watcherPointList.Add(strPointUid);
                    else
                    {
                        if (pointItem != null && pointItem.type == point_type)
                            watcherPointList.Add(strPointUid);
                    }
                }

                if (watcherPointList.Count() <= 0)
                {
                    result.retcode = ErrMgr.ERRCODE_NONE;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;                    
                    return result;
                }

                List<DataClasses.STTaskDetail> arrTasks = dbConn.task_details
                    .Where(m => m.deleted == 0)
                    .Join(dbConn.tasks, m => m.task_id, l => l.uid, (m, l) => new { _detail = m, _task = l })
                    .AsEnumerable()
                    .Select(row => new DataClasses.STTaskDetail
                    {
                        uid = row._detail.uid,
                        arr_points = row._detail.point_list.Split(',').ToList(),
                        startdate = row._detail.startdate,
                        enddate = row._detail.enddate,
                        period = row._detail.period,
                        name = row._task.name,
                        form_id = row._detail.form_id,
                        blight_id = row._detail.blight_id
                    })
                    .Where(m => m.arr_points.Any(op => watcherPointList.Contains(op)))
                    .ToList();

                if (arrTasks == null || arrTasks.Count() <= 0)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;
                    return result;
                }

                DateTime startTime = Convert.ToDateTime(start_time);
                DateTime endTime = Convert.ToDateTime(end_time);
                List<DataClasses.STTaskItem> arrTaskItems = new List<DataClasses.STTaskItem>();                

                foreach (DataClasses.STTaskDetail task_item in arrTasks)
                {
                    DateTime sD = task_item.startdate;
                    DateTime eD = task_item.enddate;
                    long period = task_item.period;
                    DateTime d = sD.AddDays(task_item.period - 1);
                    List<String> arrSubPoints = task_item.arr_points.Intersect(watcherPointList).ToList();
                    bool isArrivedToEndDate = false;
                    while (d <= endTime)
                    {
                        if (d >= eD)
                        {
                            if (isArrivedToEndDate)
                                break;
                            d = eD;
                            isArrivedToEndDate = true;
                        }

                        if (d < startTime)
                        {
                            if (!isArrivedToEndDate)
                            {
                                d = d.AddDays(period);
                                if (d > eD)
                                {                                    
                                    d = eD;
                                }
                                continue;
                            }
                            else
                                break;
                        }

                        foreach (String stPoint in arrSubPoints)
                        {
                            long reportId = 0;
                            report reportItem = dbConn.reports
                                .Where(m => m.point_id == Convert.ToInt32(stPoint) &&
                                    m.blight_id == task_item.blight_id &&
                                    m.form_id == task_item.form_id &&
                                    m.report_time >= d && m.report_time < d.AddDays(1) &&
                                    m.deleted == 0)
                                .FirstOrDefault();

                            if (reportItem != null)
                                reportId = reportItem.uid;

                            blight blightItem = dbConn.blights.Where(m => m.uid == task_item.blight_id).FirstOrDefault();

                            if (type == 0)      // 未完成工作
                            {
                                if (reportItem == null)
                                {
                                    
                                    arrTaskItems.Add(new DataClasses.STTaskItem
                                    {
                                        uid = task_item.uid,
                                        name = task_item.name,
                                        form_id = task_item.form_id,
                                        point_id = Convert.ToInt32(stPoint),
                                        point_name = dbConn.points.Where(m => m.uid == Convert.ToInt32(stPoint) && m.deleted == 0).FirstOrDefault().name,
                                        dt_task_date = d,
                                        task_date = d.ToShortDateString(),
                                        blight_id = task_item.blight_id,
                                        blight_name = blightItem != null ? blightItem.name : "",
                                        report_id = reportId
                                    });
                                }
                            }
                            else if (type == 1)  // 已完成工作
                            {
                                if (reportItem != null && reportItem.review_state == 4)
                                {
                                    arrTaskItems.Add(new DataClasses.STTaskItem
                                    {
                                        uid = task_item.uid,
                                        name = task_item.name,
                                        form_id = task_item.form_id,
                                        point_id = Convert.ToInt32(stPoint),
                                        point_name = dbConn.points.Where(m => m.uid == Convert.ToInt32(stPoint) && m.deleted == 0).FirstOrDefault().name,
                                        dt_task_date = d,
                                        task_date = d.ToShortDateString(),
                                        blight_id = task_item.blight_id,
                                        blight_name = blightItem != null ? blightItem.name : "",
                                        report_id = reportId
                                    });
                                }
                            }
                            else if (type == 2)  // 待审核工作
                            {
                                if (reportItem != null && reportItem.review_state != 4)
                                {
                                    arrTaskItems.Add(new DataClasses.STTaskItem
                                    {
                                        uid = task_item.uid,
                                        name = task_item.name,
                                        form_id = task_item.form_id,
                                        point_id = Convert.ToInt32(stPoint),
                                        point_name = dbConn.points.Where(m => m.uid == Convert.ToInt32(stPoint) && m.deleted == 0).FirstOrDefault().name,
                                        dt_task_date = d,
                                        task_date = d.ToShortDateString(),
                                        blight_id = task_item.blight_id,
                                        blight_name = blightItem != null ? blightItem.name : "",
                                        report_id = reportId
                                    });
                                }
                            }                            
                        }
                        
                        d = d.AddDays(period);
                    }
                }

                arrTaskItems = arrTaskItems.OrderByDescending(m => m.dt_task_date).ToList();

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = arrTaskItems;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getTaskInfo(long task_detail_id,
            long point_id,
            String stime,
            bool check_reports,
            long report_id)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            report lastReportItem = null;

            try
            {
                dbConn = new BingchongDBDataContext();

                task_detail taskDetailItem = dbConn.task_details
                    .Where(m => m.uid == task_detail_id &&
                    m.deleted == 0)
                    .FirstOrDefault();
                if (taskDetailItem == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;
                    return result;
                }

                task taskItem = dbConn.tasks
                    .Where(m => m.uid == taskDetailItem.task_id &&
                    m.deleted == 0)
                    .FirstOrDefault();
                if (taskItem == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;
                    return result;
                }

                DateTime startTime = taskDetailItem.startdate.AddHours(taskDetailItem.starthour).AddMinutes(taskDetailItem.startmin);
                DateTime endTime = taskDetailItem.enddate.AddHours(taskDetailItem.endhour).AddMinutes(taskDetailItem.endmin);

                String stState = "已过期";
                if (DateTime.Now < endTime)
                {
                    stState = "待执行";
                }

                String eDateTitle = "结束时间";
                String lastReportTime = "";
                if (check_reports)
                {
                    lastReportItem = dbConn.reports
                            .Where(m => m.uid == report_id &&
                                m.deleted == 0)
                            .FirstOrDefault();

                    if (lastReportItem != null)
                    {
                        eDateTitle = "上报时间";
                        lastReportTime = lastReportItem.report_time.ToString();
                        endTime = lastReportItem.report_time;
                        stState = "已执行";
                    }
                }

                Dictionary<String, Object> taskInfo = new Dictionary<String, Object>();
                String htmlContent = "<!doctype html>" +
                            "<html>" +
                            "<head>" +
                            "    <meta charset=\"utf-8\" />" +
                            "</head>" +
                            "<body>" +
                            "<table align=\"center\" style=\"color: #7AB038;\" bordercolor=\"#7AB038\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">" +
                            "    <tbody>";

                if (check_reports)
                {
                    htmlContent += "    <tr>" +
                            "        <td width=\"100\" height=\"40\" align=center>" +
                            "            <p>上报状态</p>" +
                            "        </td>" +
                            "        <td width=\"200\" height=\"40\" align=center>" +
                            "            <p>" + stState + "</p>" +
                            "        </td>" +
                            "    </tr>";
                }

                String stAuthor = "";
                    stAuthor += dbConn.shengs.Where(m => m.uid == taskItem.sheng_id).FirstOrDefault().name;
                    stAuthor += dbConn.shis.Where(m => m.uid == taskItem.shi_id).FirstOrDefault().name;

                    point pointItem = dbConn.points
                        .Where(m => m.uid == point_id)
                        .FirstOrDefault();
                    String stPointType = "固定测报点";
                    if (pointItem != null && pointItem.type == 1)
                        stPointType = "非固定测报点";                            


                    htmlContent += 
                                "    <tr>" +
                                "        <td width=\"100\" height=\"40\" align=center>" +
                                "            <p>测报点名称</p>" +
                                "        </td>" +
                                "        <td width=\"200\" height=\"40\" align=center>" +
                                "            <p>" + dbConn.points.Where(m => m.uid == point_id).FirstOrDefault().name + "</p>" +
                                "        </td>" +
                                "    </tr>" +
                                "    <tr>" +
                                "        <td width=\"100\" height=\"40\" align=center>" +
                                "            <p>测报点类型</p>" +
                                "        </td>" +
                                "        <td width=\"200\" height=\"40\" align=center>" +
                                "            <p>" + stPointType + "</p>" +
                                "        </td>" +
                                "    </tr>" +
                                "    <tr>" +
                                "        <td width=\"100\" height=\"40\" align=center>" +
                                "            <p>病虫害类别</p>" +
                                "        </td>" +
                                "        <td width=\"200\" height=\"40\" align=center>" +
                                "            <p>" + (dbConn.blights.Where(m => m.uid == taskDetailItem.blight_id).FirstOrDefault().kind == 0 ? "病害" : "虫害") + "</p>" +
                                "        </td>" +
                                "    </tr>" +
                                "    <tr>" +
                                "        <td width=\"100\" height=\"40\" align=center>" +
                                "            <p>名称</p>" +
                                "        </td>" +
                                "        <td width=\"200\" height=\"40\" align=center>" +
                                "            <p>" + dbConn.blights.Where(m => m.uid == taskDetailItem.blight_id).FirstOrDefault().name + "</p>" +
                                "        </td>" +
                                "    </tr>" +
                                "    <tr>" +
                                "        <td width=\"100\" height=\"40\" align=center>" +
                                "            <p>开始时间</p>" +
                                "        </td>" +
                                "        <td width=\"200\" height=\"40\" align=center>" +
                                "            <p>" + startTime.ToString() + "</p>" +
                                "        </td>" +
                                "    </tr>" +
                                "    <tr>" +
                                "        <td width=\"100\" height=\"40\" align=center>" +
                                "            <p>" + eDateTitle + "</p>" +
                                "        </td>" +
                                "        <td width=\"200\" height=\"40\" align=center>" +
                                "            <p>" + endTime.ToString() + "</p>" +
                                "        </td>" +
                                "    </tr>" +
                                "    <tr>" +
                                "        <td width=\"100\" height=\"40\" align=center>" +
                                "            <p>测报表格</p>" +
                                "        </td>" +
                                "        <td width=\"200\" height=\"40\" align=center>" +
                                "            <p>" + dbConn.forms.Where(m => m.uid == taskDetailItem.form_id).FirstOrDefault().name + "</p>" +
                                "        </td>" +
                                "    </tr>" +
                                "    <tr>" +
                                "        <td width=\"100\" height=\"40\" align=center>" +
                                "            <p>发布单位</p>" +
                                "        </td>" +
                                "        <td width=\"200\" height=\"40\" align=center>" +
                                "            <p>" + stAuthor + "</p>" +
                                "        </td>" +
                                "    </tr>" +
                                "    </tbody>" +
                                "</table>" +
                                "<p></p>" +
                                "<table align=\"center\" style=\"color: #7AB038;\" bordercolor=\"#7AB038\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">" +
                                "    <tbody>" +
                                "    <tr>" +
                                "        <td width=\"300\" height=\"40\">" +
                                "            <p>备注:</p>" +
                                "            <p></p>" +
                                "            <p>" + taskDetailItem.note + "</p>" +
                                "        </td>" +
                                "    </tr>" +
                                "    </tbody>" +
                                "</table>";

                    if (lastReportItem != null)
                    {
                        htmlContent += "<h2 align=\"center\"> <font size=5 color=\"#7AB038\">" + dbConn.forms.Where(m => m.uid == taskDetailItem.form_id).FirstOrDefault().name + "</font></h2>" +
                                        "<table align=\"center\" style=\"color: #7AB038;\" bordercolor=\"#7AB038\" border=\"1\" cellspacing=\"0\" cellpadding=\"5\">" +
                                        "    <tbody>";

                        List<DataClasses.STReportDetail> arrReportDetails = dbConn.report_details
                        .Where(m => m.report_id == lastReportItem.uid &&
                        m.deleted == 0)
                        .Join(dbConn.fields, m => m.field_id, l => l.uid, (m, l) => new { _detail = m, _field = l })
                        .Select(row => new DataClasses.STReportDetail
                        {
                            uid = row._detail.uid,
                            field_name = row._field.name,
                            value_integer = row._detail.value_integer,
                            value_real = row._detail.value_real,
                            value_text = row._detail.value_text,
                            field_type = row._field.type,
                            field_unit = row._field.note
                        })
                        .ToList();

                        foreach (DataClasses.STReportDetail detailItem in arrReportDetails)
                        {
                            String field_value = "";
                            if (detailItem.field_type.Contains("r"))
                            {
                                field_value = detailItem.value_real + "";
                            }
                            else if (detailItem.field_type.Contains("i"))
                            {
                                field_value = detailItem.value_integer + "";
                            }
                            else
                            {
                                field_value = detailItem.value_text;
                            }
                            htmlContent += "<tr>" +
                                           "     <td width=\"130\" height=\"40\" align=left>" +
                                           "         <p>" + detailItem.field_name + "</p>" +
                                           "     </td>" +
                                           "     <td width=\"150\" height=\"40\" align=left>" +
                                           "         <p>" + field_value + "</p>" +
                                           "     </td>" +
                                           " </tr>";
                        }

                        htmlContent += "    </tbody>" +
                                        "</table>";
                    }

                    htmlContent += "</body>" +
                    "</html>";

                taskInfo.Add("content", htmlContent);
                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = taskInfo;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getTaskStatus(long point_id,
            String start_time,
            String end_time)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;
            long taskCount = -1;

            try
            {
                dbConn = new BingchongDBDataContext();
                Dictionary<String, object> retdata = new Dictionary<String, object>();

                List<DataClasses.STTaskDetail> arrTasks = dbConn.task_details
                    .Where(m => m.deleted == 0)
                    .AsEnumerable()
                    .Select(row => new DataClasses.STTaskDetail
                    {
                        uid = row.uid,
                        arr_points = row.point_list.Split(',').ToList(),
                        startdate = row.startdate,
                        enddate = row.enddate,
                        period = row.period
                    })
                    .Where(m => m.arr_points.Contains(point_id.ToString()))
                    .ToList();

                if (arrTasks == null || arrTasks.Count() <= 0)
                {
                    retdata.Add("count", taskCount);
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;
                    result.retdata = retdata;
                    return result;
                }

                DateTime startTime = Convert.ToDateTime(start_time);
                DateTime endTime = Convert.ToDateTime(end_time);
                taskCount = 0;

                foreach (DataClasses.STTaskDetail task_item in arrTasks)
                {
                    DateTime sD = task_item.startdate;
                    DateTime eD = task_item.enddate;
                    long period = task_item.period;
                    DateTime d = sD;
                    while (d < endTime)
                    {
                        if (d < sD)
                        {
                            d = d.AddDays(period);
                            continue;
                        }

                        report reportItem = dbConn.reports
                                .Where(m => m.point_id == point_id &&
                                    m.form_id == task_item.form_id &&
                                    m.report_time >= d && m.report_time < d.AddDays(1) &&
                                    m.deleted == 0)
                                .FirstOrDefault();

                        if (reportItem == null)
                            taskCount += 1;

                        d = d.AddDays(period);
                    }
                }

                retdata.Add("count", taskCount);

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = retdata;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getVersion()
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();
                Dictionary<String, object> retdata = new Dictionary<String, object>();

                version versionItem = dbConn.versions
                    .OrderByDescending(m => m.number)
                    .FirstOrDefault();

                if (versionItem == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NODATA;
                    return result;
                }

                retdata.Add("uid", versionItem.uid);
                retdata.Add("number", versionItem.number);
                retdata.Add("name", versionItem.name);
                retdata.Add("path", versionItem.path);

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = retdata;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getOpinionKind()
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();

                List<DataClasses.STItem> arrKinds = dbConn.opinion_kinds
                    .Where(m => m.deleted == 0)
                    .Select(m => new DataClasses.STItem
                    {
                        uid = m.uid,
                        name = m.name
                    })
                    .ToList();

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = arrKinds;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult uploadOpinion(long kind_id,
            long user_id,
            String title,
            String content)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();

                user userItem = dbConn.users
                    .Where(m => m.uid == user_id &&
                    m.deleted == 0)
                    .FirstOrDefault();

                if (userItem == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NOTREGUSER;                
                    return result;
                }

                opinion opinionItem = new opinion();
                opinionItem.kind_id = kind_id;
                opinionItem.user_id = user_id;
                opinionItem.title = title;
                opinionItem.contents = content;
                opinionItem.report_date = DateTime.Now;

                dbConn.opinions.InsertOnSubmit(opinionItem);
                dbConn.SubmitChanges();

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;                
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getUserPhotos()
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            try
            {
                dbConn = new BingchongDBDataContext();

                List<DataClasses.STUserInfo> arrUsers = dbConn.users
                    .Where(m => m.deleted == 0)
                    .Select(m => new DataClasses.STUserInfo
                    {
                        uid = m.uid,
                        name = m.name,
                        phone = m.phone,
                        imgurl = Global.getAbsImgUrl(m.imgurl),
                        right_id = m.right_id,
                        level = m.level,
                        sheng_id = m.sheng_id,
                        shi_id = m.shi_id,
                        xian_id = m.xian_id
                    })
                    .ToList();

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = arrUsers;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static SVCResult getReportData(long userid,
            long point_id,
            String start_time,
            String end_time,
            String token)
        {
            SVCResult result = new SVCResult();
            BingchongDBDataContext dbConn = null;

            if (!checkUserTokenIsValid(userid, token))
            {
                result.retcode = ErrMgr.ERRCODE_TOKEN;
                result.retmsg = ErrMgr.ERRMSG_TOKEN;
                return result;
            }

            try
            {
                dbConn = new BingchongDBDataContext();

                point pointItem = dbConn.points
                    .Where(m => m.uid == point_id && m.deleted == 0).FirstOrDefault();
                if (pointItem == null)
                {
                    result.retcode = ErrMgr.ERRCODE_NORMAL;
                    result.retmsg = ErrMgr.ERRMSG_NONE;
                    return result;
                }

                sheng shengItem = dbConn.shengs
                    .Where(m => m.uid == pointItem.sheng_id && m.deleted == 0).FirstOrDefault();
                shi shiItem = dbConn.shis
                    .Where(m => m.uid == pointItem.shi_id && m.deleted == 0).FirstOrDefault();
                xian xianItem = dbConn.xians
                    .Where(m => m.uid == pointItem.xian_id && m.deleted == 0).FirstOrDefault();

                DateTime startTime = Convert.ToDateTime(start_time);
                DateTime endTime = Convert.ToDateTime(end_time);
                endTime = endTime.AddDays(1);
                List<DataClasses.STReport> arrReports = dbConn.reports
                    .Where(m => m.point_id == point_id &&
                        m.report_time >= startTime &&
                        m.report_time <= endTime &&
                        m.deleted == 0)
                    .Join(dbConn.users, m => m.user_id, l => l.uid, (m, l) => new { _report = m, _user = l })
                    .Join(dbConn.blights, m => m._report.blight_id, l => l.uid, (m, l) => new { _report = m, _blight = l })
                    .Join(dbConn.forms, m => m._report._report.form_id, l => l.uid, (m, l) => new { _report = m, _form = l })
                    .Select(m => new DataClasses.STReport
                    {
                        uid = m._report._report._report.uid,
                        sheng_id = (shengItem != null) ? shengItem.uid : 0,
                        sheng_name = (shengItem != null) ? shengItem.name : "",
                        shi_id = (shiItem != null) ? shiItem.uid : 0,
                        shi_name = (shiItem != null) ? shiItem.name : "",
                        xian_id = (xianItem != null) ? xianItem.uid : 0,
                        xian_name = (xianItem != null) ? xianItem.name : "",
                        point_id = pointItem.uid,
                        point_name = pointItem.name,
                        form_id = m._report._report._report.form_id,
                        form_name = m._form.name,
                        user_name = m._report._report._user.name,
                        blight_kind = m._report._blight.kind,
                        blight_name = m._report._blight.name,
                        report_time = String.Format("{0:yyyy-MM-dd HH:mm}", m._report._report._report.report_time),
                        review_status = m._report._report._report.review_state,
                        form_data = dbConn.report_details
                            .Where(k => k.report_id == m._report._report._report.uid)
                            .Join(dbConn.fields, k => k.field_id, n => n.uid, (k, n) => new { _detail = k, _field = n})
                            .Select(k => new DataClasses.STFieldVal
                            {
                                field_id = k._detail.field_id,
                                field_name = k._field.name,
                                field_val = getFieldValidValue(k._detail, k._field.type)
                            })
                            .ToList()
                    })
                    .ToList();

                result.retcode = ErrMgr.ERRCODE_NONE;
                result.retmsg = ErrMgr.ERRMSG_NONE;
                result.retdata = arrReports;
            }
            catch (Exception ex)
            {
                Global.logError(ex.Message);

                result.retcode = ErrMgr.ERRCODE_EXCEPTION;
                result.retmsg = ex.Message;
            }

            return result;
        }

        public static String getFieldValidValue(report_detail reportDetail, String field_type)
        {
            if (field_type.Contains("i"))
                return Convert.ToString(reportDetail.value_integer);
            if(field_type.Contains("r"))
                return Convert.ToString(reportDetail.value_real);
            if(field_type.Contains("t"))
                return Convert.ToString(reportDetail.value_text);
            return "";                
        }
    }
}