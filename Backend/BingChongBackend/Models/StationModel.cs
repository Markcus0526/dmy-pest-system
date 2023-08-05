using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using BingChongBackend.Models.Library;
using System.Web.Security;

namespace BingChongBackend.Models
{
    public class StationPoint
    {
            public long uid { get; set; }
        	public  long id{get;set;}	        //标识符	bigint		autoincrement
	        public string nickname{get;set;}	//代码	nvarchar	50	
	        public string name{get;set;}	    //名称	nvarchar	50	
	        public decimal longitude{get;set;}	//经度	decimal	10,7	
            public decimal latitude { get; set; }	//纬度	decimal	10,7	
	        public string ptype{get;set;}	    //测报点类型	tinyint		"0：固定测报点1：非固定测报点"
	        public string plevel{get;set;}	    //测报点级别	tinyint		"0：国家级1：省级2：市级3：县级"
	        public string sheng{get;set;}	//省号码	bigint		
	        public string shi{get;set;}	//市号码	bigint		
	        public string xian{get;set;}	//县号码	bigint		
	        public string info1{get;set;}	//田块面积	nvarchar	50	
	        public string info2{get;set;}	//代表面积	nvarchar	50	
	        public string info3{get;set;}	//种植作物	nvarchar	50	
	        public string info4{get;set;}	//乡镇	nvarchar	50	
	        public string info5{get;set;}	//村组	nvarchar	50	
            public string info6 { get; set; }	//测报对象	nvarchar	200	
            public string note { get; set; }	//备注信息	ntext		
            public long shengid { get; set; }
            public long shiid { get; set; }
            public long xianid { get; set; }
            public byte type { get; set; }	    //测报点类型	tinyint		"0：固定测报点1：非固定测报点"
            public byte level { get; set; }	    //测报点级别	tinyint		"0：国家级1：省级2：市级3：县级"
    }

    public class PointModel
    {
        BingChongDBDataContext db = new BingChongDBDataContext();

        public user GetUserRoleInfo()
        {
            FormsIdentity fId = (FormsIdentity)HttpContext.Current.User.Identity;
            FormsAuthenticationTicket authTicket = fId.Ticket;
            string[] udata = authTicket.UserData.Split(new Char[] { '|' });
            string userid = udata[1];
            user getp = db.users.Single(a => a.uid == Convert.ToInt64(userid));
            return getp;
        }
        public bool InsertPoint(Byte sort, Byte style1, long sheng, long shi, long xian, string pointname, string pointcode, float dongjing, float beiwei,
            string fieldarea, string daibiaoarea, string crop, string xiang, string cun, string pobject, string detail)
        {
            bool success = false;
            try
            {
                point p = new point();
                p.nickname = pointcode;
                p.type = style1;
                p.latitude = Convert.ToDecimal(beiwei);
                p.longitude = Convert.ToDecimal(dongjing);
                p.name = pointname;
                p.level = sort;
                p.note = detail;
                p.sheng_id = sheng;
                p.shi_id = shi;
                p.xian_id = xian;
                p.info1 = fieldarea;
                p.info2 = daibiaoarea;
                p.info3 = crop;
                p.info4 = xiang;
                p.info5 = cun;
                p.info6 = pobject;
                p.deleted = 0;
                db.points.InsertOnSubmit(p);
                db.SubmitChanges();
            }
            catch (System.Exception ex)
            {
                string a = ex.Message;
            }
           
            success = true;
            return success;
        }
        public bool UpdatePoint(long uid,Byte sort, Byte style1, long sheng, long shi, long xian, string pointname, string pointcode, float dongjing, float beiwei,
           string fieldarea, string daibiaoarea, string crop, string xiang, string cun, string pobject, string detail)
        {
            bool success = false;
            try
            {
                point p = db.points.Single(m=>m.uid==uid);
                p.nickname = pointcode;
                p.type = style1;
                p.latitude = Convert.ToDecimal(beiwei);
                p.longitude = Convert.ToDecimal(dongjing);
                p.name = pointname;
                p.level = sort;
                p.note = detail;
                p.sheng_id = sheng;
                p.shi_id = shi;
                p.xian_id = xian;
                p.info1 = fieldarea;
                p.info2 = daibiaoarea;
                p.info3 = crop;
                p.info4 = xiang;
                p.info5 = cun;
                p.info6 = pobject;
                p.deleted = 0;
                db.SubmitChanges();
            }
            catch (System.Exception ex)
            {
                string a = ex.Message;
            }

            success = true;
            return success;
        }
        public bool GetSamePoint(String dongjing, string beiwei)
        {
            bool success = false;
            try
            {
                List<point> p = db.points.Where(m => m.deleted == 0 && m.longitude == Convert.ToDecimal(dongjing) && m.latitude == Convert.ToDecimal(beiwei)).ToList();
                if (p.Count == 0)
                {
                    success = true;
                }
            }
            catch (System.Exception ex)
            {
                success = false;
            }
           
            return success;
        }
        public List<sheng> GetAllSheng()
        {
            var tr = (from m in db.shengs
                      where m.deleted==0&&m.status==1
                      select m).ToList();

            return tr;//返回list
        }
        public long Getmponit()
        {
            long mponit = db.points.Max(m => m.uid);

            return mponit;//返回list
        }
        public List<xian> GetPcode( long xianid)
        {

            List<xian> list = db.xians.Where(m => m.uid == xianid&&m.deleted==0).ToList();
            return list;//返回list
        }
        public List<shi> Findshi(long shengid)
        {
            List<shi> list = new List<shi>();
            try
            {

                list = db.shis.Where(m => m.sheng_id == shengid && m.deleted == 0&&m.status==1).ToList();
            }
            catch (System.Exception e)
            {
                e.ToString();
            }
            return list;
        }
        public List<xian> Findxian(long shiid)
        {
            List<xian> list = new List<xian>();
            try
            {

                list = db.xians.Where(m => m.shi_id == shiid&&m.deleted==0&&m.status==1).ToList();
            }
            catch (System.Exception e)
            {
                e.ToString();
            }
            return list;
        }
        public List<StationPoint> FintPoint1(Byte sort, Byte style1, long sheng, long shi, long xian, string pointname)
        {
            List<StationPoint> list = new List<StationPoint>();
            try
            {

                List<Byte> so = new List<Byte>();
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

                    list = db.points.Where(m => m.deleted == 0 && so.Contains(m.level) && st.Contains(m.type) && m.name.Contains(pointname))
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
                    list = db.points.Where(m => m.deleted == 0 && so.Contains(m.level) && st.Contains(m.type) && m.name.Contains(pointname) && m.sheng_id == sheng)
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
                    list = db.points.Where(m => m.deleted == 0 && so.Contains(m.level) && st.Contains(m.type) && m.name.Contains(pointname) && m.sheng_id == sheng && m.shi_id == shi)
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
                    list = db.points.Where(m => m.deleted == 0 && so.Contains(m.level) && st.Contains(m.type) && m.name.Contains(pointname) && m.sheng_id == sheng && m.shi_id == shi && m.xian_id == xian)
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
        public List<StationPoint> FintPoint(Byte sort, Byte style1, long sheng, long shi, long xian, string pointname, int rows, int page)
        {
            List<StationPoint> list = new List<StationPoint>();
            try
            {

                List<Byte> so = new List<Byte>();
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

                    list = db.points.Where(m => m.deleted == 0 && so.Contains(m.level) && st.Contains(m.type) && m.name.Contains(pointname))
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.xians, m1 => m1.m2.m2.xian_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new StationPoint
                       {
                            id= al1.m2.m2.m2.uid,
                           name = al1.m2.m2.m2.name,
                           nickname = al1.m2.m2.m2.nickname,
                           type = al1.m2.m2.m2.type,
                           level = al1.m2.m2.m2.level,
                           sheng = al1.m2.m2.empt.name,
                           shi = al1.m2.empt.name,
                           xian = al1.empt.name,
                           
                       }
                       ).OrderByDescending(m => m.id).ToList();

                }
                if (sheng != 0 && shi == 0)
                {
                    list = db.points.Where(m => m.deleted == 0 && so.Contains(m.level) && st.Contains(m.type) && m.name.Contains(pointname)&&m.sheng_id==sheng)
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
                       ).OrderByDescending(m => m.id).ToList();

                }
                if (shi != 0 && xian==0)
                {
                    list = db.points.Where(m => m.deleted == 0 && so.Contains(m.level) && st.Contains(m.type) && m.name.Contains(pointname)&&m.sheng_id==sheng&&m.shi_id==shi)
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
                       ).OrderByDescending(m => m.id).ToList();

                }
                if (xian != 0)
                {
                    list = db.points.Where(m => m.deleted == 0 && so.Contains(m.level) && st.Contains(m.type) && m.name.Contains(pointname) && m.sheng_id == sheng && m.shi_id == shi&&m.xian_id==xian)
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.xians, m1 => m1.m2.m2.xian_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new StationPoint
                       {
                            id= al1.m2.m2.m2.uid,
                           name = al1.m2.m2.m2.name,
                           nickname = al1.m2.m2.m2.nickname,
                           type = al1.m2.m2.m2.type,
                           level = al1.m2.m2.m2.level,
                           sheng = al1.m2.m2.empt.name,
                           shi = al1.m2.empt.name,
                           xian = al1.empt.name,
                           
                       }
                       ).OrderByDescending(m => m.id).ToList();

                }
                    
                
            }
            catch (Exception ex)
            {
                String a = ex.Message;
            }
            list=list.Skip((page - 1) * rows).Take(page * rows).ToList();

            return list;
        }
        public List<StationPoint> GetImportP(long[] uid)
        {
            List<StationPoint> list = new List<StationPoint>();
            list = db.points.Where(m => m.deleted == 0 && uid.Contains(m.uid))
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
                           longitude = al1.m2.m2.m2.longitude,
                           note = al1.m2.m2.m2.note,
                           latitude = al1.m2.m2.m2.latitude,
                           info1 = al1.m2.m2.m2.info1,
                           info2 = al1.m2.m2.m2.info2,
                           info3 = al1.m2.m2.m2.info3,
                           info4 = al1.m2.m2.m2.info4,
                           info5 = al1.m2.m2.m2.info5,
                           info6 = al1.m2.m2.m2.info6,
                           shengid = al1.m2.m2.m2.sheng_id,
                           shiid = al1.m2.m2.m2.shi_id,
                           xianid = al1.m2.m2.m2.xian_id,
                       }
                       ).ToList();
            return list;

        }
        public List<StationPoint> ShowPoint(long uid)
        {
            List<StationPoint> list = new List<StationPoint>();
            list = db.points.Where(m => m.deleted == 0 && m.uid==uid)
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
                           longitude = al1.m2.m2.m2.longitude,
                           note = al1.m2.m2.m2.note,
                           latitude = al1.m2.m2.m2.latitude,
                           info1 = al1.m2.m2.m2.info1,
                           info2 = al1.m2.m2.m2.info2,
                           info3 = al1.m2.m2.m2.info3,
                           info4 = al1.m2.m2.m2.info4,
                           info5 = al1.m2.m2.m2.info5,
                           info6 = al1.m2.m2.m2.info6,
                           shengid=al1.m2.m2.m2.sheng_id,
                           shiid = al1.m2.m2.m2.shi_id,
                           xianid = al1.m2.m2.m2.xian_id,
                       }
                       ).ToList();
            return list;

        }
        public bool Deletepoints(long[] s)
        {
            bool success = false;
            try
            {

                foreach (long a in s)
                {
                    point cm = db.points.Where(m => m.uid == a).Single();
                    cm.deleted = 1;
                    db.SubmitChanges();
                }
                success = true;
            }
            catch (System.Exception ex)
            {

            }
            return success;
        }
        public bool Deletepoint(long s)
        {
            bool success = false;
            try
            {
                point cm = db.points.Where(m => m.uid == s).Single();
                cm.deleted = 1;
                db.SubmitChanges();

                success = true;
            }
            catch (System.Exception ex)
            {

            }
            return success;
        }

    }
}