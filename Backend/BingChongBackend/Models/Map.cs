using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using BingChongBackend.Models.Library;
using System.Web.Security;

namespace BingChongBackend.Models
{
    public class WatchPoint
    {
        public decimal longitude { get; set; }	//经度	decimal	10,7	
        public decimal latitude { get; set; }	//纬度	decimal	10,7	
        public string name { get; set; }	    //名称	nvarchar	50	
        public string nickname { get; set; }	//代码	nvarchar	50	
        public string sheng { get; set; }	//省号码	bigint		
        public string shi { get; set; }	//市号码	bigint		
        public string xian { get; set; }	//县号码	bigint		
        public string info1 { get; set; }	//田块面积	nvarchar	50	
        public string info2 { get; set; }	//代表面积	nvarchar	50	
        public string info3 { get; set; }	//种植作物	nvarchar	50	
        public string info4 { get; set; }	//乡镇	nvarchar	50	
        public string info5 { get; set; }	//村组	nvarchar	50	
        public string info6 { get; set; }	//测报对象	nvarchar	200	
        public string note { get; set; }	//备注信息	ntext		
        public long shengid { get; set; }
        public long shiid { get; set; }
        public long xianid { get; set; }
        public byte type { get; set; }	    //测报点类型	tinyint		"0：固定测报点1：非固定测报点"
        public byte level { get; set; }	    //测报点级别	tinyint		"0：国家级1：省级2：市级3：县级"
        public long uid { get; set; }
        public long id { get; set; }	        //标识符	bigint		autoincrement
        public string ptype { get; set; }	    //测报点类型	tinyint		"0：固定测报点1：非固定测报点"
        public string plevel { get; set; }	    //测报点级别	tinyint		"0：国家级1：省级2：市级3：县级"
        public int num { get; set; }
    }
    public class WatchUser
    {
        public decimal longitude { get; set; }	//经度	decimal	10,7	
        public decimal latitude { get; set; }	//纬度	decimal	10,7	
        public long uid { get; set; }
        public string name { get; set; }	    //名称	nvarchar	50	
        public string role { get; set; }	   
        public string level { get; set; }	    //人员级别
        public string dept { get; set; }	//所属单位	
        public string job { get; set; }	//职务	
        public string phone { get; set; }	
        public string imgurl { get; set; }	//图片地址
        public int le { get; set; }		
        public DateTime time { get; set; }
    }

    public class Map
    {
        BingChongDBDataContext db = new BingChongDBDataContext();
        public List<task_detail> GetPointTask(long pointid)
        {
            List<task_detail> list = db.task_details.Where(m => m.deleted == 0 && m.point_list.Contains(pointid.ToString())).ToList();
            return list;
        }
        public int GetEmpty(List<task_detail> list, DateTime stime)
        {
            int num =0;
            int num1 = 0;
            if (list.Count == 0)
            {
                num = 1;
            }
            else
            {
                foreach (task_detail a in list)
                {
                    DateTime b = a.startdate;
                    List<DateTime> time = new List<DateTime>();
                    for (int i = 1; i <= (a.term); i++)
                    {
                        if (i == 1)
                        {
                            b = b.AddDays((a.period - 1));
                        }
                        else
                        {
                            b = b.AddDays((a.period));
                        }
                        if (b > a.enddate)
                        {
                            time.Add(a.enddate);
                        }
                        else
                        {
                            time.Add(b);
                        }
                    }
                    if (time.Contains(stime))
                    {
                        num1 += 1;
                    }
                    else { num1 = num1; }


                }
                if (num1 == 0)
                {
                    num = 1;
                }
            }
            

            return num;
        }
        public int GetFinish(List<task_detail> list, DateTime stime,long uid)
        {
            int num = 0;
            int num1 = 0;
            int num2 = 0;
            foreach (task_detail a in list)
            {
                DateTime b = a.startdate;
                List<DateTime> time = new List<DateTime>();
                for (int i = 1; i <= (a.term); i++)
                {
                    if (i == 1)
                    {
                        b = b.AddDays((a.period-1));
                    }
                    else
                    {
                        b = b.AddDays((a.period));
                    }
                   
                    if (b > a.enddate)
                    {
                        time.Add(a.enddate);
                    }
                    else
                    {
                        time.Add(b);
                    }
                }
                if (time.Contains(stime))
                {
                    num1 += 1;
                    List<report> l = db.reports.Where(m => m.deleted == 0 && m.form_id == a.form_id && m.blight_id == a.blight_id && m.point_id == uid && m.report_time >= stime && m.report_time < stime.AddDays(1)).ToList();
                    if (l.Count > 0)
                    {
                        num += 1;
                    }
                }


            }
            if (num1 > 0 && num1 == num)
            {
                num2 = 1;
            }
            return num2;
        }
        public int Getufinish(List<task_detail> list, DateTime stime, long uid)
        {
            int num=0;
            foreach(task_detail a in list)
            {
                DateTime b=a.startdate;
                List<DateTime> time = new List<DateTime>();
                for (int i = 1; i <= (a.term); i++)
                {
                    if (i == 1)
                    {
                        b = b.AddDays((a.period - 1));
                    }
                    else
                    {
                        b = b.AddDays((a.period));
                    }
                   if (b > a.enddate)
                   {
                       time.Add(a.enddate);
                   }
                   else
                   {
                       time.Add(b);
                  } 
                }
                if (time.Contains(stime))
                {
                    List<report> l = db.reports.Where(m => m.deleted == 0&&m.form_id==a.form_id && m.blight_id == a.blight_id && m.point_id == uid && m.report_time >= stime && m.report_time < stime.AddDays(1)).ToList();
                    if (l.Count == 0)
                    {
                        num += 1;
                    }
                }

               
            }
            
            return num;
        }
        public List<StationPoint> FintPoint1(Byte sort, Byte style1, long sheng, long shi, long xian,int level)
        {
            List<StationPoint> list = new List<StationPoint>();
            try
            {

                List<Byte> so = new List<Byte>();
                if (level == 0)
                {
                    if (sort == 4)
                    {
                        so.Add(0);
                        so.Add(1);
                        so.Add(2);
                        so.Add(3);

                    }
                    else
                    {
                        so.Add(sort);
                    }
                }
                if (level == 1)
                {
                    if (sort == 4)
                    {
                        so.Add(0);
                        so.Add(3);

                    }
                    else
                    {
                        so.Add(sort);
                    }
                }
                if (level == 2)
                {
                    if (sort == 4)
                    {
                        so.Add(0);
                        so.Add(2);

                    }
                    else
                    {
                        so.Add(sort);
                    }
                }
                if (level == 3)
                {
                    if (sort == 4)
                    {
                        so.Add(0);
                        so.Add(1);

                    }
                    else
                    {
                        so.Add(sort);
                    }
                }
                if (level == 4)
                {
                    if (sort == 4)
                    {
                        so.Add(0);

                    }
                    else
                    {
                        so.Add(sort);
                    }
                }
                List<Byte> st = new List<Byte>();
                if (style1 == 2)
                {
                    st.Add(0);
                    st.Add(1);

                }
                else
                {
                    st.Add(style1);
                }
                if (sheng == 0)
                {

                    list = db.points.Where(m => m.deleted == 0 && so.Contains(m.level) && st.Contains(m.type))
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.xians, m1 => m1.m2.m2.xian_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new StationPoint
                       {
                           id = al1.m2.m2.m2.uid,
                           name = al1.m2.m2.m2.name,
                           nickname = al1.m2.m2.m2.nickname,
                           type = al1.m2.m2.m2.type,
                           level = al1.m2.m2.m2.level,
                           sheng = al1.m2.m2.empt.name,
                           shi = al1.m2.empt.name,
                           xian = al1.empt.name,

                       }
                       ).ToList();

                }
                if (sheng != 0 && shi == 0)
                {
                    list = db.points.Where(m => m.deleted == 0 && so.Contains(m.level) && st.Contains(m.type) && m.sheng_id == sheng)
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.xians, m1 => m1.m2.m2.xian_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new StationPoint
                       {
                           id = al1.m2.m2.m2.uid,
                           name = al1.m2.m2.m2.name,
                           nickname = al1.m2.m2.m2.nickname,
                           type = al1.m2.m2.m2.type,
                           level = al1.m2.m2.m2.level,
                           sheng = al1.m2.m2.empt.name,
                           shi = al1.m2.empt.name,
                           xian = al1.empt.name,

                       }
                       ).ToList();

                }
                if (shi != 0 && xian == 0)
                {
                    list = db.points.Where(m => m.deleted == 0 && so.Contains(m.level) && st.Contains(m.type) && m.sheng_id == sheng && m.shi_id == shi)
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.xians, m1 => m1.m2.m2.xian_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new StationPoint
                       {
                           id = al1.m2.m2.m2.uid,
                           name = al1.m2.m2.m2.name,
                           nickname = al1.m2.m2.m2.nickname,
                           type = al1.m2.m2.m2.type,
                           level = al1.m2.m2.m2.level,
                           sheng = al1.m2.m2.empt.name,
                           shi = al1.m2.empt.name,
                           xian = al1.empt.name,

                       }
                       ).ToList();

                }
                if (xian != 0)
                {
                    list = db.points.Where(m => m.deleted == 0 && so.Contains(m.level) && st.Contains(m.type) && m.sheng_id == sheng && m.shi_id == shi && m.xian_id == xian)
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.xians, m1 => m1.m2.m2.xian_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new StationPoint
                       {
                           id = al1.m2.m2.m2.uid,
                           name = al1.m2.m2.m2.name,
                           nickname = al1.m2.m2.m2.nickname,
                           type = al1.m2.m2.m2.type,
                           level = al1.m2.m2.m2.level,
                           sheng = al1.m2.m2.empt.name,
                           shi = al1.m2.empt.name,
                           xian = al1.empt.name,

                       }
                       ).ToList();

                }


            }
            catch (Exception ex)
            {
                String a = ex.Message;
            }
            return list;
        }
        public List<WatchPoint> FintPoint(Byte sort, Byte style1, long sheng, long shi, long xian,long pointid,int level)
        {
            List<WatchPoint> list = new List<WatchPoint>();
            try
            {

                List<Byte> so = new List<Byte>();
                if (level == 0)
                {
                    if (sort == 4)
                    {
                        so.Add(0);
                        so.Add(1);
                        so.Add(2);
                        so.Add(3);

                    }
                    else
                    {
                        so.Add(sort);
                    }
                }
                if (level == 1)
                {
                    if (sort == 4)
                    {
                        so.Add(0);
                        so.Add(3);

                    }
                    else
                    {
                        so.Add(sort);
                    }
                }
                if (level == 2)
                {
                    if (sort == 4)
                    {
                        so.Add(0);
                        so.Add(2);

                    }
                    else
                    {
                        so.Add(sort);
                    }
                }
                if (level == 3)
                {
                    if (sort == 4)
                    {
                        so.Add(0);
                        so.Add(1);

                    }
                    else
                    {
                        so.Add(sort);
                    }
                }
                if (level == 4)
                {
                    if (sort == 4)
                    {
                        so.Add(0);

                    }
                    else
                    {
                        so.Add(sort);
                    }
                }
                List<Byte> st = new List<Byte>();
                if (style1 == 2)
                {
                    st.Add(0);
                    st.Add(1);

                }
                else
                {
                    st.Add(style1);
                }
                if (pointid != 0)
                {
                    list = db.points.Where(m => m.deleted == 0 &&m.uid==pointid)
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.xians, m1 => m1.m2.m2.xian_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new WatchPoint
                       {
                           id = al1.m2.m2.m2.uid,
                           name = al1.m2.m2.m2.name,
                           nickname = al1.m2.m2.m2.nickname,
                           sheng = al1.m2.m2.empt.name,
                           shi = al1.m2.empt.name,
                           xian = al1.empt.name,
                           type=al1.m2.m2.m2.type,
                           longitude=al1.m2.m2.m2.longitude,
                           latitude=al1.m2.m2.m2.latitude,
                           info1 = al1.m2.m2.m2.info1,
                           info2 = al1.m2.m2.m2.info2,
                           info3 = al1.m2.m2.m2.info3,
                           info4 = al1.m2.m2.m2.info4,
                           info5 = al1.m2.m2.m2.info5,
                           info6 = al1.m2.m2.m2.info6,

                       }
                       ).ToList();
                }
                if (pointid == 0&&sheng == 0)
                {

                    list = db.points.Where(m => m.deleted == 0 && so.Contains(m.level) && st.Contains(m.type))
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.xians, m1 => m1.m2.m2.xian_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new WatchPoint
                       {
                           id = al1.m2.m2.m2.uid,
                           name = al1.m2.m2.m2.name,
                           nickname = al1.m2.m2.m2.nickname,
                           sheng = al1.m2.m2.empt.name,
                           shi = al1.m2.empt.name,
                           xian = al1.empt.name,
                           type = al1.m2.m2.m2.type,
                           longitude = al1.m2.m2.m2.longitude,
                           latitude = al1.m2.m2.m2.latitude,
                           info1 = al1.m2.m2.m2.info1,
                           info2 = al1.m2.m2.m2.info2,
                           info3 = al1.m2.m2.m2.info3,
                           info4 = al1.m2.m2.m2.info4,
                           info5 = al1.m2.m2.m2.info5,
                           info6 = al1.m2.m2.m2.info6,

                       }
                       ).ToList();

                }
                if (pointid == 0 && sheng != 0 && shi == 0)
                {
                    list = db.points.Where(m => m.deleted == 0 && so.Contains(m.level) && st.Contains(m.type) && m.sheng_id == sheng)
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.xians, m1 => m1.m2.m2.xian_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new WatchPoint
                       {
                           id = al1.m2.m2.m2.uid,
                           name = al1.m2.m2.m2.name,
                           nickname = al1.m2.m2.m2.nickname,
                           sheng = al1.m2.m2.empt.name,
                           shi = al1.m2.empt.name,
                           xian = al1.empt.name,
                           type = al1.m2.m2.m2.type,
                           longitude = al1.m2.m2.m2.longitude,
                           latitude = al1.m2.m2.m2.latitude,
                           info1 = al1.m2.m2.m2.info1,
                           info2 = al1.m2.m2.m2.info2,
                           info3 = al1.m2.m2.m2.info3,
                           info4 = al1.m2.m2.m2.info4,
                           info5 = al1.m2.m2.m2.info5,
                           info6 = al1.m2.m2.m2.info6,

                       }
                       ).ToList();

                }
                if (pointid == 0 && shi != 0 && xian == 0)
                {
                    list = db.points.Where(m => m.deleted == 0 && so.Contains(m.level) && st.Contains(m.type) && m.sheng_id == sheng && m.shi_id == shi)
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.xians, m1 => m1.m2.m2.xian_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new WatchPoint
                       {
                           id = al1.m2.m2.m2.uid,
                           name = al1.m2.m2.m2.name,
                           nickname = al1.m2.m2.m2.nickname,
                           sheng = al1.m2.m2.empt.name,
                           shi = al1.m2.empt.name,
                           xian = al1.empt.name,
                           type = al1.m2.m2.m2.type,
                           longitude = al1.m2.m2.m2.longitude,
                           latitude = al1.m2.m2.m2.latitude,
                           info1 = al1.m2.m2.m2.info1,
                           info2 = al1.m2.m2.m2.info2,
                           info3 = al1.m2.m2.m2.info3,
                           info4 = al1.m2.m2.m2.info4,
                           info5 = al1.m2.m2.m2.info5,
                           info6 = al1.m2.m2.m2.info6,

                       }
                       ).ToList();

                }
                if (pointid == 0 && xian != 0)
                {
                    list = db.points.Where(m => m.deleted == 0 && so.Contains(m.level) && st.Contains(m.type) && m.sheng_id == sheng && m.shi_id == shi && m.xian_id == xian)
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.xians, m1 => m1.m2.m2.xian_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new WatchPoint
                       {
                           id = al1.m2.m2.m2.uid,
                           name = al1.m2.m2.m2.name,
                           nickname = al1.m2.m2.m2.nickname,
                           sheng = al1.m2.m2.empt.name,
                           shi = al1.m2.empt.name,
                           xian = al1.empt.name,
                           type = al1.m2.m2.m2.type,
                           longitude = al1.m2.m2.m2.longitude,
                           latitude = al1.m2.m2.m2.latitude,
                           info1 = al1.m2.m2.m2.info1,
                           info2 = al1.m2.m2.m2.info2,
                           info3 = al1.m2.m2.m2.info3,
                           info4 = al1.m2.m2.m2.info4,
                           info5 = al1.m2.m2.m2.info5,
                           info6 = al1.m2.m2.m2.info6,
                       }
                       ).ToList();

                }


            }
            catch (Exception ex)
            {
                String a = ex.Message;
            }
            return list;
        }
        public List<List<WatchUser>> GetWatcher(long sheng, long shi, long xian,DateTime startime)
        {
            List<user> list = new List<user>();
            List<List<WatchUser>> list2 = new List<List<WatchUser>>();
            try
            {

               
                if (sheng == 0)
                {

                    list = db.users.Where(m => m.deleted == 0 ).ToList();
                      

                }
                if (sheng != 0 && shi == 0)
                {
                    list = db.users.Where(m => m.deleted == 0&&m.sheng_id==sheng).ToList();
                      

                }
                if (shi != 0 && xian == 0)
                {
                    list = db.users.Where(m => m.deleted == 0&&m.shi_id==shi&&m.sheng_id==sheng).ToList();
                      
                }
                if (xian != 0)
                {
                    list = db.users.Where(m => m.deleted == 0&&m.shi_id==shi&&m.sheng_id==sheng&&m.xian_id==xian).ToList();
                      
                }
                foreach (user a in list)
               {
                   List<WatchUser> list3 = new List<WatchUser>();
                   if(a.right_id==0)
                   {
                       list3 = db.users.Where(m => m.uid == a.uid)
                        .Join(db.watcher_tracks, m1 => m1.uid, emp => emp.user_id, (m1, emp) => new { m2 = m1, empt = emp })
                        .Where(m => m.empt.time < startime.AddHours(24) && m.empt.time >= startime)
                        .Select(al1 => new WatchUser
                        {
                            uid = al1.m2.uid,
                            name = al1.m2.name,
                            longitude = al1.empt.longitude,
                            latitude = al1.empt.latitude,
                            imgurl = al1.m2.imgurl,
                            job = al1.m2.job,
                            le = al1.m2.level,
                            phone = al1.m2.phone,
                            role = "测报员",
                            dept = al1.m2.place,
                            time = al1.empt.time

                        }
                        ).OrderByDescending(m => m.time).ToList();
                   }
                   else
                   {
                       list3 = db.users.Where(m => m.uid == a.uid)
                         .Join(db.tbl_rights, m1 => m1.right_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                        .Join(db.watcher_tracks, m1 => m1.m2.uid, emp => emp.user_id, (m1, emp) => new { m2 = m1, empt = emp })
                        .Where(m => m.empt.time < startime.AddHours(24) && m.empt.time >= startime)
                        .Select(al1 => new WatchUser
                        {
                            uid = al1.m2.m2.uid,
                            name = al1.m2.m2.name,
                            longitude = al1.empt.longitude,
                            latitude = al1.empt.latitude,
                            imgurl = al1.m2.m2.imgurl,
                            job = al1.m2.m2.job,
                            le = al1.m2.m2.level,
                            phone = al1.m2.m2.phone,
                            role = al1.m2.empt.name,
                            dept = al1.m2.m2.place,
                            time = al1.empt.time

                        }
                        ).OrderByDescending(m => m.time).ToList();
                   }
                   
                   foreach (WatchUser b in list3)
                   {
                       if (b.le == 0)
                       {
                           b.level = "超级管理员";
                       }
                       if (b.le == 1)
                       {
                           b.level = "县级";
                       }
                       if (b.le == 2)
                       {
                           b.level = "市级";
                       }
                       if (b.le == 3)
                       {
                           b.level = "省级";
                       }
                       if (b.le == 4)
                       {
                           b.level = "国家级";
                       }
                   }
                   list2.Add(list3);
               }


            }
            catch (Exception ex)
            {
                String a = ex.Message;
            }
            return list2;
        }

    }
}