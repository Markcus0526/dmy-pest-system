using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using BingChongBackend.Models.Library;
using System.Globalization;
using System.Text;

namespace BingChongBackend.Models
{
    public class Noitice
    {
        public long uid { get; set; }

        public string title{ get; set; }
        public long userid { get; set; }
        public string username{ get; set; }
        public string serial{ get; set; }
        public string contents{ get; set; }
        public CommonModel.UserLevel level { get; set; }
        public string creattime { get; set; }
        public long sheng_id { get; set; }
        public long shi_id{ get; set; }
        
    }

    public class Document
    {
        public long uid { get; set; }

        public string content{ get; set; }
        public string title { get; set; }
        public long createrid { get; set; }
        public string creatername { get; set; }
        public string creattime { get; set; }

        public long regionid { get; set; }
        public string regionname { get; set; }
        
    }
    public class TaskDetail
    {
        public long id{get;set;}	//标识符	bigint		autoincrement		
        public string sheng{get;set;}	
		public string shi {get;set;}
        public string point_list{get;set;}	//测报点目录	ntext		以“,”区分测报点
        public long form_id	{get;set;}//上报表格ID	bigint	
	    public byte blkind{get;set;}//虫害种类
        public string blname{get;set;}//虫害名称
        public DateTime startdate{get;set;}	//开始日期	datetime		
        public DateTime enddate{get;set;}	//结束日期	datetime		
        public long period{get;set;}	//测报周期	bigint		
        public long starthour{get;set;}	//测报开始时间(时)	bigint		
        public long startmin{get;set;}	//测报开始时间(分)	bigint		
        public long endhour{get;set;}	//测报结束时间(时)	bigint		
        public long endmin{get;set;}	//测报结束时间(分)	bigint		
        public string note { get; set; }	//备注	ntext		
        public string starttime { get; set; }
        public string endtime { get; set; }
        public long term { get; set; }
        public string stime { get; set; }
    }

    public class Pointplan
    {
        public long id{get;set;}	//标识符	bigint		autoincrement
        public string name{get;set;}	//任务名称	nvarchar	100	
        public string sheng { get; set; }
        public string shi { get; set; }
        public long year_id{get;set;}	//计划年度	bigint		
        public long sheng_id{get;set;}	//省号码	bigint		
        public long shi_id { get; set; }	//市号码	bigint	
    }

        public class TempleWork
    {
        public long id {get;set;}	//标识符	bigint		autoincrement
        public string name {get;set;}	//任务名称	nvarchar	100	
        public string sheng { get; set; }
        public string shi { get; set; }
        public string xian {get;set;}	
        
        public long sheng_id{get;set;}	//省号码	bigint		
        public long shi_id { get; set; }	//市号码	bigint	
        public long xian_id { get; set; }
        public long adminid{ get; set; }
        public long recieverid { get; set; }	


        public string notice_date { get; set; }
        public string report_date { get; set; }

        public string note { get; set; }
        public string adminname { get; set; }
        public string reciever { get; set; }
        public int status { get; set; }
    }
    public class WorkingManageModel
    {
        BingChongDBDataContext db = new BingChongDBDataContext();
        public bool InsertPlan( string planname,long sheng,long year,long shi)
        {
            bool success = false;
            task ta = new task();
            ta.sheng_id = sheng;
            ta.shi_id = shi;
            ta.name = planname;
            ta.year = year;
            ta.deleted=0;
            db.tasks.InsertOnSubmit(ta);
            db.SubmitChanges();
            success = true;
            return success;
        }
        public List<blight> Findblname(long blkind)
        {
            List<blight> list = new List<blight>();
            try
            {
                if (blkind == 2)
                {
                    list = db.blights.Where(m => m.deleted == 0).ToList();
                }
                else
                {
                    list = db.blights.Where(m => m.kind == blkind && m.deleted == 0).ToList();
                }
                
            }
            catch (System.Exception e)
            {
                e.ToString();
            }
            return list;
        }
        public List<form> Getform(long blname)
        {
            List<form> list = new List<form>();
            blight bl = db.blights.Where(m => m.uid == blname).FirstOrDefault();
            string[] formids = bl.form_ids.Split(',');

            list = db.forms.Where(m => formids.Contains(m.uid.ToString())).ToList();
            return list;

        }
        public List<Pointplan> FintPointPlan(long year, long sheng, long shi, int rows, int page)
        {
            List<Pointplan> list = new List<Pointplan>();
            if (year == 0 && sheng == 0)
            {
                list = db.tasks.Where(m => m.deleted == 0)
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new Pointplan
                       {
                           id = al1.m2.m2.uid,
                           name = al1.m2.m2.name,
                           year_id = al1.m2.m2.year,
                           sheng = al1.m2.empt.name,
                           shi = al1.empt.name,
                       }
                       ).OrderByDescending(m => m.id).ToList();
            }
            if (year == 0 && sheng != 0&&shi==0)
            {
                list = db.tasks.Where(m => m.deleted == 0&&m.sheng_id==sheng)
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new Pointplan
                       {
                           id = al1.m2.m2.uid,
                           name = al1.m2.m2.name,
                           year_id = al1.m2.m2.year,
                           sheng = al1.m2.empt.name,
                           shi = al1.empt.name,
                       }
                       ).OrderByDescending(m => m.id).ToList();
            }
            if (year == 0 && shi != 0)
            {
                list = db.tasks.Where(m => m.deleted == 0 && m.sheng_id == sheng&&m.shi_id==shi)
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new Pointplan
                       {
                           id = al1.m2.m2.uid,
                           name = al1.m2.m2.name,
                           year_id = al1.m2.m2.year,
                           sheng = al1.m2.empt.name,
                           shi = al1.empt.name,
                       }
                       ).OrderByDescending(m => m.id).ToList();
            }
            if (year != 0 && sheng == 0)
            {
                list = db.tasks.Where(m => m.deleted == 0 && m.year == year)
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new Pointplan
                       {
                           id = al1.m2.m2.uid,
                           name = al1.m2.m2.name,
                           year_id = al1.m2.m2.year,
                           sheng = al1.m2.empt.name,
                           shi = al1.empt.name,
                       }
                       ).OrderByDescending(m => m.id).ToList();
            }
            if (year != 0 &&sheng != 0&& shi == 0)
            {
                list = db.tasks.Where(m => m.deleted == 0&&m.year==year&&m.sheng_id==sheng)
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new Pointplan
                       {
                           id = al1.m2.m2.uid,
                           name = al1.m2.m2.name,
                           year_id = al1.m2.m2.year,
                           sheng = al1.m2.empt.name,
                           shi = al1.empt.name,
                       }
                       ).OrderByDescending(m => m.id).ToList();
            }
            if (year != 0 && shi != 0)
            {
                list = db.tasks.Where(m => m.deleted == 0 && m.year == year && m.sheng_id == sheng&&m.shi_id==shi)
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new Pointplan
                       {
                           id = al1.m2.m2.uid,
                           name = al1.m2.m2.name,
                           year_id = al1.m2.m2.year,
                           sheng = al1.m2.empt.name,
                           shi = al1.empt.name,
                       }
                       ).OrderByDescending(m => m.id).ToList();
            }
            list = list.Skip((page - 1) * rows).Take(page * rows).ToList();
            return list;
        }
        public List<Pointplan> FintPointPlan1(long year, long sheng, long shi)
        {
            List<Pointplan> list = new List<Pointplan>();
            if (year == 0 && sheng == 0)
            {
                list = db.tasks.Where(m => m.deleted == 0)
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new Pointplan
                       {
                           id = al1.m2.m2.uid,
                           name = al1.m2.m2.name,
                           year_id = al1.m2.m2.year,
                           sheng = al1.m2.empt.name,
                           shi = al1.empt.name,
                       }
                       ).ToList();
            }
            if (year == 0 && sheng != 0 && shi == 0)
            {
                list = db.tasks.Where(m => m.deleted == 0 && m.sheng_id == sheng)
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new Pointplan
                       {
                           id = al1.m2.m2.uid,
                           name = al1.m2.m2.name,
                           year_id = al1.m2.m2.year,
                           sheng = al1.m2.empt.name,
                           shi = al1.empt.name,
                       }
                       ).ToList();
            }
            if (year == 0 && shi != 0)
            {
                list = db.tasks.Where(m => m.deleted == 0 && m.sheng_id == sheng && m.shi_id == shi)
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new Pointplan
                       {
                           id = al1.m2.m2.uid,
                           name = al1.m2.m2.name,
                           year_id = al1.m2.m2.year,
                           sheng = al1.m2.empt.name,
                           shi = al1.empt.name,
                       }
                       ).ToList();
            }
            if (year != 0 && sheng == 0)
            {
                list = db.tasks.Where(m => m.deleted == 0 && m.year == year)
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new Pointplan
                       {
                           id = al1.m2.m2.uid,
                           name = al1.m2.m2.name,
                           year_id = al1.m2.m2.year,
                           sheng = al1.m2.empt.name,
                           shi = al1.empt.name,
                       }
                       ).ToList();
            }
            if (year != 0 && sheng != 0 && shi == 0)
            {
                list = db.tasks.Where(m => m.deleted == 0 && m.year == year && m.sheng_id == sheng)
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new Pointplan
                       {
                           id = al1.m2.m2.uid,
                           name = al1.m2.m2.name,
                           year_id = al1.m2.m2.year,
                           sheng = al1.m2.empt.name,
                           shi = al1.empt.name,
                       }
                       ).ToList();
            }
            if (year != 0 && shi != 0)
            {
                list = db.tasks.Where(m => m.deleted == 0 && m.year == year && m.sheng_id == sheng && m.shi_id == shi)
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new Pointplan
                       {
                           id = al1.m2.m2.uid,
                           name = al1.m2.m2.name,
                           year_id = al1.m2.m2.year,
                           sheng = al1.m2.empt.name,
                           shi = al1.empt.name,
                       }
                       ).ToList();
            }
            return list;
        }
        public bool Deletepoint(long s)
        {
            bool success = false;
            try
            {
                task cm = db.tasks.Where(m => m.uid == s).Single();
                cm.deleted = 1;
                db.SubmitChanges();
                List<task_detail> list = db.task_details.Where(m => m.task_id == m.uid && m.deleted == 0).ToList();
                foreach(task_detail a in list)
                {
                    a.deleted = 1;
                    db.SubmitChanges();
                }
                success = true;
            }
            catch (System.Exception ex)
            {

            }
            return success;
        }
        public bool DeleteTaskDetail(long s)
        {
            bool success = false;
            try
            {
                task_detail cm = db.task_details.Where(m => m.uid == s).Single();
                cm.deleted = 1;
                db.SubmitChanges();

                success = true;
            }
            catch (System.Exception ex)
            {

            }
            return success;
        }
        public bool EditPoint(long uid,long blname, long formss, DateTime starttime, DateTime endtime, long dates, long fs, long days,
            long stshi, long stfen, long enshi, long enfen, string pointids, long taskid)
        {
            bool success = false;
            try
            {
                task_detail p = db.task_details.Single(m=>m.uid==uid);
                p.blight_id = blname;
                p.form_id = formss;
                p.enddate = endtime;
                p.startdate = starttime;
                p.point_list = pointids;
                p.note = "";
                p.period = days;
                p.endhour = enshi;
                p.endmin = enfen;
                p.starthour = stshi;
                p.startmin = stfen;
                p.term = fs;
                p.task_id = taskid;
                p.deleted = 0;
                db.SubmitChanges();
                success = true;
            }
            catch (System.Exception ex)
            {
                string a = ex.Message;
            }

           
            return success;
        }
        public bool InsertPoint( long blname, long formss, DateTime starttime, DateTime endtime, long dates, long fs, long days,
            long stshi, long stfen, long enshi, long enfen, string pointids,long taskid)
        {
            bool success = false;
            try
            {
                task_detail p = new task_detail();
                p.blight_id = blname;
                p.form_id = formss;
                p.enddate = endtime;
                p.startdate = starttime;
                p.point_list = pointids;
                p.note="";
                p.period= days;
                p.endhour= enshi;
                p.endmin = enfen;
                p.starthour = stshi;
                p.startmin = stfen;
                p.term = fs;
                p.task_id =taskid;
                p.deleted = 0;
                db.task_details.InsertOnSubmit(p);
                db.SubmitChanges();
                success = true;
            }
            catch (System.Exception ex)
            {
                string a = ex.Message;
            }

            
            return success;
        }
        public List<Pointplan> GetOnePlan(long planid)
        {
            List<Pointplan> list = new List<Pointplan>();
            list = db.tasks.Where(m => m.deleted == 0&&m.uid==planid)
                       .Join(db.shengs, m1 => m1.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new Pointplan
                       {
                           id = al1.m2.m2.uid,
                           name = al1.m2.m2.name,
                           year_id = al1.m2.m2.year,
                           sheng = al1.m2.empt.name,
                           shi = al1.empt.name,
                           sheng_id=al1.m2.m2.sheng_id,
                           shi_id=al1.m2.m2.shi_id
                       }
                       ).ToList();
            return list;
        }
        public blight GetOneBlight(long uid)
        {
            blight list = db.blights.Single(m => m.uid == uid);
            return list;
        }
        public task_detail GetTask_detail(long uid)
        {
            task_detail list = db.task_details.Single(m=>m.uid==uid);
            return list;
        }
        public List<TaskDetail> FintPlanDetail(Byte blkind, long blname,long planid, int rows, int page)
        {
            List<TaskDetail> list = new List<TaskDetail>();
            if (blkind == 2 && blname == 0)
            {
                list = db.task_details.Where(m => m.deleted == 0 && m.task_id == planid)
                       .Join(db.blights, m1 => m1.blight_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.tasks, m1 => m1.m2.task_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shengs, m1 => m1.empt.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.empt.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.forms, m1 => m1.m2.m2.m2.m2.form_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new TaskDetail
                       {
                           id = al1.m2.m2.m2.m2.m2.uid,
                           blkind=al1.m2.m2.m2.m2.empt.kind,
                           blname=al1.m2.m2.m2.m2.empt.name,
                           enddate=al1.m2.m2.m2.m2.m2.enddate,
                           form_id=al1.empt.uid,
                           period=al1.m2.m2.m2.m2.m2.period,
                           point_list=al1.m2.m2.m2.m2.m2.point_list,
                           sheng=al1.m2.m2.empt.name,
                           shi = al1.m2.empt.name,
                           startdate = al1.m2.m2.m2.m2.m2.startdate,
                           starthour = al1.m2.m2.m2.m2.m2.starthour,
                           startmin=al1.m2.m2.m2.m2.m2.startmin,
                           endhour=al1.m2.m2.m2.m2.m2.endhour,
                           endmin=al1.m2.m2.m2.m2.m2.endmin,
                           term=al1.m2.m2.m2.m2.m2.term
                       }
                       ).OrderByDescending(m => m.id).ToList();
            }
            if (blkind == 2 && blname != 0)
            {
                list = db.task_details.Where(m => m.deleted == 0 && m.task_id == planid)
                       .Join(db.blights, m1 => m1.blight_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Where(m => m.empt.uid == blname)
                       .Join(db.tasks, m1 => m1.m2.task_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shengs, m1 => m1.empt.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.empt.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.forms, m1 => m1.m2.m2.m2.m2.form_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new TaskDetail
                       {
                           id = al1.m2.m2.m2.m2.m2.uid,
                           blkind = al1.m2.m2.m2.m2.empt.kind,
                           blname = al1.m2.m2.m2.m2.empt.name,
                           enddate = al1.m2.m2.m2.m2.m2.enddate,
                           form_id = al1.empt.uid,
                           period = al1.m2.m2.m2.m2.m2.period,
                           point_list = al1.m2.m2.m2.m2.m2.point_list,
                           sheng = al1.m2.m2.empt.name,
                           shi = al1.m2.empt.name,
                           startdate = al1.m2.m2.m2.m2.m2.startdate,
                           starthour = al1.m2.m2.m2.m2.m2.starthour,
                           startmin = al1.m2.m2.m2.m2.m2.startmin,
                           endhour = al1.m2.m2.m2.m2.m2.endhour,
                           endmin = al1.m2.m2.m2.m2.m2.endmin,
                           term = al1.m2.m2.m2.m2.m2.term
                       }
                       ).OrderByDescending(m => m.id).ToList();
            }
            if (blkind != 2 && blname == 0)
            {
                list = db.task_details.Where(m => m.deleted == 0 && m.task_id == planid)
                       .Join(db.blights, m1 => m1.blight_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Where(m=>m.empt.kind==blkind)
                       .Join(db.tasks, m1 => m1.m2.task_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shengs, m1 => m1.empt.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.empt.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.forms, m1 => m1.m2.m2.m2.m2.form_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new TaskDetail
                       {
                           id = al1.m2.m2.m2.m2.m2.uid,
                           blkind = al1.m2.m2.m2.m2.empt.kind,
                           blname = al1.m2.m2.m2.m2.empt.name,
                           enddate = al1.m2.m2.m2.m2.m2.enddate,
                           form_id = al1.empt.uid,
                           period = al1.m2.m2.m2.m2.m2.period,
                           point_list = al1.m2.m2.m2.m2.m2.point_list,
                           sheng = al1.m2.m2.empt.name,
                           shi = al1.m2.empt.name,
                           startdate = al1.m2.m2.m2.m2.m2.startdate,
                           starthour = al1.m2.m2.m2.m2.m2.starthour,
                           startmin = al1.m2.m2.m2.m2.m2.startmin,
                           endhour = al1.m2.m2.m2.m2.m2.endhour,
                           endmin = al1.m2.m2.m2.m2.m2.endmin,
                           term = al1.m2.m2.m2.m2.m2.term
                       }
                       ).OrderByDescending(m => m.id).ToList();
            }
            if (blkind != 2 && blname != 0)
            {
                list = db.task_details.Where(m => m.deleted == 0 && m.task_id == planid)
                       .Join(db.blights, m1 => m1.blight_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Where(m => m.empt.uid == blname)
                       .Join(db.tasks, m1 => m1.m2.task_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shengs, m1 => m1.empt.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.empt.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.forms, m1 => m1.m2.m2.m2.m2.form_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new TaskDetail
                       {
                           id = al1.m2.m2.m2.m2.m2.uid,
                           blkind = al1.m2.m2.m2.m2.empt.kind,
                           blname = al1.m2.m2.m2.m2.empt.name,
                           enddate = al1.m2.m2.m2.m2.m2.enddate,
                           form_id = al1.empt.uid,
                           period = al1.m2.m2.m2.m2.m2.period,
                           point_list = al1.m2.m2.m2.m2.m2.point_list,
                           sheng = al1.m2.m2.empt.name,
                           shi = al1.m2.empt.name,
                           startdate = al1.m2.m2.m2.m2.m2.startdate,
                           starthour = al1.m2.m2.m2.m2.m2.starthour,
                           startmin = al1.m2.m2.m2.m2.m2.startmin,
                           endhour = al1.m2.m2.m2.m2.m2.endhour,
                           endmin = al1.m2.m2.m2.m2.m2.endmin,
                           term = al1.m2.m2.m2.m2.m2.term
                       }
                       ).OrderByDescending(m => m.id).ToList();
            }
            list = list.Skip((page - 1) * rows).Take(page * rows).ToList();
            return list;
        }
        public List<TaskDetail> FintPlanDetail1(Byte blkind, long blname,long planid)
        {
            List<TaskDetail> list = new List<TaskDetail>();
            if (blkind == 2 && blname == 0)
            {
                list = db.task_details.Where(m => m.deleted == 0 && m.task_id == planid)
                       .Join(db.blights, m1 => m1.blight_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.tasks, m1 => m1.m2.task_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shengs, m1 => m1.empt.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.empt.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.forms, m1 => m1.m2.m2.m2.m2.form_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new TaskDetail
                       {
                           id = al1.m2.m2.m2.m2.m2.uid,
                           blkind = al1.m2.m2.m2.m2.empt.kind,
                           blname = al1.m2.m2.m2.m2.empt.name,
                           enddate = al1.m2.m2.m2.m2.m2.enddate,
                           form_id = al1.empt.uid,
                           period = al1.m2.m2.m2.m2.m2.period,
                           point_list = al1.m2.m2.m2.m2.m2.point_list,
                           sheng = al1.m2.m2.empt.name,
                           shi = al1.m2.empt.name,
                           startdate = al1.m2.m2.m2.m2.m2.startdate,
                           starthour = al1.m2.m2.m2.m2.m2.starthour,
                           startmin = al1.m2.m2.m2.m2.m2.startmin,
                           endhour = al1.m2.m2.m2.m2.m2.endhour,
                           endmin = al1.m2.m2.m2.m2.m2.endmin,
                           term = al1.m2.m2.m2.m2.m2.term
                       }
                       ).OrderByDescending(m => m.id).ToList();
            }
            if (blkind == 2 && blname != 0)
            {
                list = db.task_details.Where(m => m.deleted == 0 && m.task_id == planid)
                       .Join(db.blights, m1 => m1.blight_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Where(m => m.empt.uid == blname)
                       .Join(db.tasks, m1 => m1.m2.task_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shengs, m1 => m1.empt.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.empt.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.forms, m1 => m1.m2.m2.m2.m2.form_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new TaskDetail
                       {
                           id = al1.m2.m2.m2.m2.m2.uid,
                           blkind = al1.m2.m2.m2.m2.empt.kind,
                           blname = al1.m2.m2.m2.m2.empt.name,
                           enddate = al1.m2.m2.m2.m2.m2.enddate,
                           form_id = al1.empt.uid,
                           period = al1.m2.m2.m2.m2.m2.period,
                           point_list = al1.m2.m2.m2.m2.m2.point_list,
                           sheng = al1.m2.m2.empt.name,
                           shi = al1.m2.empt.name,
                           startdate = al1.m2.m2.m2.m2.m2.startdate,
                           starthour = al1.m2.m2.m2.m2.m2.starthour,
                           startmin = al1.m2.m2.m2.m2.m2.startmin,
                           endhour = al1.m2.m2.m2.m2.m2.endhour,
                           endmin = al1.m2.m2.m2.m2.m2.endmin,
                           term = al1.m2.m2.m2.m2.m2.term
                       }
                       ).OrderByDescending(m => m.id).ToList();
            }
            if (blkind != 2 && blname == 0)
            {
                list = db.task_details.Where(m => m.deleted == 0 && m.task_id == planid)
                       .Join(db.blights, m1 => m1.blight_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Where(m => m.empt.kind == blkind)
                       .Join(db.tasks, m1 => m1.m2.task_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shengs, m1 => m1.empt.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.empt.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.forms, m1 => m1.m2.m2.m2.m2.form_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new TaskDetail
                       {
                           id = al1.m2.m2.m2.m2.m2.uid,
                           blkind = al1.m2.m2.m2.m2.empt.kind,
                           blname = al1.m2.m2.m2.m2.empt.name,
                           enddate = al1.m2.m2.m2.m2.m2.enddate,
                           form_id = al1.empt.uid,
                           period = al1.m2.m2.m2.m2.m2.period,
                           point_list = al1.m2.m2.m2.m2.m2.point_list,
                           sheng = al1.m2.m2.empt.name,
                           shi = al1.m2.empt.name,
                           startdate = al1.m2.m2.m2.m2.m2.startdate,
                           starthour = al1.m2.m2.m2.m2.m2.starthour,
                           startmin = al1.m2.m2.m2.m2.m2.startmin,
                           endhour = al1.m2.m2.m2.m2.m2.endhour,
                           endmin = al1.m2.m2.m2.m2.m2.endmin,
                           term = al1.m2.m2.m2.m2.m2.term
                       }
                       ).OrderByDescending(m => m.id).ToList();
            }
            if (blkind != 2 && blname != 0)
            {
                list = db.task_details.Where(m => m.deleted == 0 && m.task_id == planid)
                       .Join(db.blights, m1 => m1.blight_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Where(m => m.empt.uid == blname)
                       .Join(db.tasks, m1 => m1.m2.task_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shengs, m1 => m1.empt.sheng_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.shis, m1 => m1.m2.empt.shi_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Join(db.forms, m1 => m1.m2.m2.m2.m2.form_id, emp => emp.uid, (m1, emp) => new { m2 = m1, empt = emp })
                       .Select(al1 => new TaskDetail
                       {
                           id = al1.m2.m2.m2.m2.m2.uid,
                           blkind = al1.m2.m2.m2.m2.empt.kind,
                           blname = al1.m2.m2.m2.m2.empt.name,
                           enddate = al1.m2.m2.m2.m2.m2.enddate,
                           form_id = al1.empt.uid,
                           period = al1.m2.m2.m2.m2.m2.period,
                           point_list = al1.m2.m2.m2.m2.m2.point_list,
                           sheng = al1.m2.m2.empt.name,
                           shi = al1.m2.empt.name,
                           startdate = al1.m2.m2.m2.m2.m2.startdate,
                           starthour = al1.m2.m2.m2.m2.m2.starthour,
                           startmin = al1.m2.m2.m2.m2.m2.startmin,
                           endhour = al1.m2.m2.m2.m2.m2.endhour,
                           endmin = al1.m2.m2.m2.m2.m2.endmin,
                           term = al1.m2.m2.m2.m2.m2.term
                       }
                       ).OrderByDescending(m => m.id).ToList();
            }
            return list;
        }
        public List<blight> GetAllBlight()
        {
            List<blight> list = db.blights.Where(m => m.deleted == 0).ToList();
            return list;
        }
        public List<form> GetAllForm()
        {
            List<form> list = db.forms.Where(m => m.deleted == 0).ToList();
            return list;
        }
        public List<StationPoint> FintPoint(Byte sort, Byte style1, long sheng, long shi, long xian, int rows, int page)
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
            list = list.Skip((page - 1) * rows).Take(page * rows).ToList();
            return list;
        }
        public List<StationPoint> FintPoint1(Byte sort, Byte style1, long sheng, long shi, long xian)
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
#region TempWork
        public List<TempleWork> GetTempWorkList()
        {
            var rst= db.extra_tasks.Where(m => m.deleted == 0).Select(m => new TempleWork
            {
                name=m.name,
                adminid=m.admin_id,
                recieverid=m.watcher_id,
                notice_date = String.Format("{0:yyyy-MM-dd}", m.notice_date),
                report_date = String.Format("{0:yyyy-MM-dd}", m.report_date),
                note=m.note,
                status=m.status
            }).ToList();
            
            foreach(var n in rst){
                n.adminname=db.users.Where(m=>m.uid==n.adminid).FirstOrDefault().name;
                var reciever=db.users.Where(m=>m.uid==n.recieverid).FirstOrDefault();
                n.reciever=reciever.name;
                n.shi_id=reciever.shi_id;
                n.sheng_id=reciever.sheng_id;
                n.xian_id=reciever.xian_id;
                if (reciever.sheng_id!=0)
                {
                    n.sheng_id=reciever.sheng_id;
                    n.sheng=db.shengs.Where(m=>m.uid==reciever.sheng_id).FirstOrDefault().name;
                }
                if (reciever.shi_id!=0)
                {
                    n.shi_id=reciever.shi_id;
                    n.shi=db.shis.Where(m=>m.uid==reciever.shi_id).FirstOrDefault().name;
                }
                if (reciever.xian_id!=0)
                {
                    n.xian_id=reciever.xian_id;
                    n.xian=db.xians.Where(m=>m.uid==reciever.xian_id).FirstOrDefault().name;
                }
            }
            return rst;
        }

        public List<TempleWork> SearchTempWork(int year, long shengid, long shiid, long xianid)
        {
            var rstlist = GetTempWorkList();
            rstlist = rstlist.Where(m => m.notice_date.Contains(Convert.ToString(year))).ToList();
            
            if (shengid!=0)
            {
                rstlist = rstlist.Where(m => m.sheng_id == shengid).ToList();
            }
            if (shiid!= 0)
            {
                rstlist = rstlist.Where(m => m.shi_id== shiid).ToList();
            }
            if (xianid!= 0)
            {
                rstlist = rstlist.Where(m => m.xian_id== xianid).ToList();
            }
            return rstlist;
        }
#endregion

        public List<Document> GetDocumentsList()
        {
            var rst = db.manuals.Where(m => m.deleted == 0).OrderByDescending(m=>m.creattime)
                .Select(m => new Document
            {
                uid=m.uid,
                title = m.title,
                content = m.contents,
                createrid = m.creatuser,
                creattime = String.Format("{0:yyyy-MM-dd hh-mm-ss}",m.creattime)
            }).ToList();

            foreach (var n in rst)
            {
                var creater = db.users.Where(m => m.uid == n.createrid).FirstOrDefault();
                var comboregion = "";
                var regionsheng = "";
                var regionshi = "";
                var regionxian = "";
                n.creatername = creater.realname;
                if (creater.sheng_id != 0)
                {
                    regionsheng = db.shengs.Where(m => m.uid == creater.sheng_id).FirstOrDefault().name;
                    comboregion = regionsheng;
                }
                if (creater.shi_id != 0)
                {
                    regionshi = db.shis.Where(m => m.uid == creater.shi_id).FirstOrDefault().name;
                    if (comboregion != "")
                    {
                        comboregion += "-" + regionshi;
                    }
                    else
                    {
                        comboregion = regionshi;
                    }
                }
                if (creater.xian_id != 0)
                {
                    regionxian = db.xians.Where(m =>  m.uid == creater.xian_id).FirstOrDefault().name;
                    if (comboregion != "")
                    {
                        comboregion += "-" + regionxian;
                    }
                    else
                    {
                        comboregion = regionxian;
                    }
                }

                n.regionname = comboregion;
            }
            return rst;
        }

        public bool DeleteDocument(long[] items)
        {
            bool rst = false;
            try
            {
                string delSql = "UPDATE manual SET deleted = 1 WHERE ";
                string whereSql = "";
                foreach (long uid in items)
                {
                    if (whereSql != "") whereSql += " OR";
                    whereSql += " uid = " + uid;
                }

                delSql += whereSql;

                db.ExecuteCommand(delSql);
                rst = true;
            }
            catch (System.Exception ex)
            {

            }

            return rst;
        }
        public string InsertDocument(string name, string contents)
        {
            string rst = "";
            manual newitem = new manual();
            try
            {
                newitem.title = name;
                newitem.contents = contents;
                newitem.creatuser = CommonModel.GetSessionUserID();
                newitem.creattime = DateTime.Now;
                newitem.deleted = 0;
                db.manuals.InsertOnSubmit(newitem);
                db.SubmitChanges();
            }
            catch (System.Exception ex)
            {
                rst = "操作失败";            	
            }
            return rst;
        }

        public string UpdateDocument(long uid, string name, string contents)
        {
            string rst = "";
            manual edititem = GetDocumentById(uid);

            if (edititem != null)
            {
                edititem.title = name;
                edititem.contents = contents;
                db.SubmitChanges();
                rst = "";
            }
            else
            {
                rst = "数据不存在";
            }

            return rst;
        }

        public manual GetDocumentById(long uid)
        {
            return db.manuals
                .Where(m => m.deleted == 0 && m.uid == uid)
                .FirstOrDefault();
        }

        public List<Noitice> GetNoiceList()
        {
            return db.notices.Where(m => m.deleted == 0)
                      .Join(db.users, m => m.user_id, l => l.uid, (m, l) => new { notice = m, user = l })
                      .Select(m => new Noitice
                      {
                          title = m.notice.title,
                          uid = m.notice.uid,
                          userid=m.notice.user_id,
                          serial = m.notice.serial,
                          contents = m.notice.contents,
                          creattime = string.Format("{0:yyyy-MM-dd}", m.notice.pub_date),
                          level = (CommonModel.UserLevel)m.user.level,
                          sheng_id = m.user.sheng_id,
                          shi_id = m.user.shi_id
                      }).ToList();
        }

        public Noitice GetNoticeById(long uid)
        {
            return db.notices.Where(m => m.deleted == 0 && m.uid == uid)
                      .Join(db.users, m => m.user_id, l => l.uid, (m, l) => new { notice = m, user = l })
                      .Select(m => new Noitice
                      {
                          title = m.notice.title,
                          uid = m.notice.uid,
                          serial = m.notice.serial,
                          contents = m.notice.contents,
                          level = (CommonModel.UserLevel)m.user.level,
                          creattime= string.Format("{0:yyyy-MM-dd}",m.notice.pub_date),
                          username=m.user.name
                      }).FirstOrDefault();
        }

        public int GetUnreadNotice()
        {
            var userid = CommonModel.GetSessionUserID();
            var unreadlist = db.noticetracks.Where(m => m.deleted == 0 && m.userid == userid&&m.status==0).ToList();
            return unreadlist.Count;
        }

        public void ReadNotice(long noticeid, long userid)
        {
            var noticetrack = db.noticetracks.Where(m => m.noticeid == noticeid && m.userid == userid).FirstOrDefault();
            if (noticetrack!=null)
            {
                if (noticetrack.readtime == null)
                {
                    noticetrack.readtime = DateTime.Now;
                    noticetrack.status = 1;

                    db.SubmitChanges();
                }
            }
        }
        public string InserteNotice(string title, long userid, string creattime, string serial,string contents)
        {
            var rst = "";
            var noticelevel = db.users.Where(m => m.uid == userid).FirstOrDefault().level;
            var currentuser=CommonModel.GetSessionUser();
            try
            {
                notice newitem = new notice();
                var receiverlist=new List<user>();

                newitem.title = title;
                newitem.user_id = userid;

                newitem.serial = serial;
                newitem.contents = contents;

                DateTimeFormatInfo dtFormat = new System.Globalization.DateTimeFormatInfo();

                dtFormat.ShortDatePattern = "yyyy-MM-dd";
                var dt = Convert.ToDateTime(creattime, dtFormat);
                newitem.year = dt.Year;
                newitem.pub_date = dt;
                db.notices.InsertOnSubmit(newitem);
                db.SubmitChanges();
                switch (noticelevel)
                {
                    case 1:
                        receiverlist = db.users.Where(m => m.deleted == 0 && m.right_id == 0 && m.xian_id == currentuser.xian_id).ToList();
                        
                        break;
                    case 2:
                        receiverlist = db.users.Where(m => m.deleted == 0 && m.level == 1&&m.shi_id==currentuser.shi_id).ToList();
                        break;
                    case 3:
                        receiverlist = db.users.Where(m => m.deleted == 0 && m.level == 2&&m.sheng_id==currentuser.sheng_id).ToList();
                        break;
                    case 4:
                        receiverlist = db.users.Where(m => m.deleted == 0 && m.level == 3).ToList();                        
                        break;
                    case 0:
                        receiverlist = db.users.Where(m => m.deleted == 0 && m.level == 3).ToList();
                        break;
                }
                foreach(var n in receiverlist){
                    noticetrack newtrack = new noticetrack();

                    newtrack.userid = n.uid;

                    newtrack.noticeid = newitem.uid;
                    newtrack.status = 0;
                    newtrack.deleted = 0;
                    newtrack.createtime = DateTime.Now;

                    db.noticetracks.InsertOnSubmit(newtrack);
                }
                db.SubmitChanges();
            }
            catch (System.Exception ex)
            {
                rst = "操作失败！";
            }


            return rst;
        }
        public string UpdateNotice(long uid, string title, long userid, string creattime, string serial, string contents)
        {
            string rst = "";

            notice edititem = db.notices.Where(m => m.uid == uid).FirstOrDefault();

            if (edititem != null)
            {

                edititem.title = title;

                edititem.user_id = userid;

                DateTimeFormatInfo dtFormat = new System.Globalization.DateTimeFormatInfo();
                dtFormat.ShortDatePattern = "yyyy-MM-dd";
                var dt = Convert.ToDateTime(creattime, dtFormat);
                edititem.year = dt.Year;

                edititem.pub_date = dt;
                edititem.serial = serial;
                edititem.contents = contents;

                //edititem.imgurl = "";
                db.SubmitChanges();
                rst = "";
            }
            else
            {
                rst = "数据不存在";
            }

            return rst;
        }
        public bool DeleteNotice(long[] items)
        {
            bool rst = false;
            try
            {
                string delSql = "UPDATE notice SET deleted = 1 WHERE ";
                string delTractSql = "UPDATE noticetrack SET deleted = 1 WHERE ";

                string whereSql = "";
                string whereTrackSql = "";
                foreach (long uid in items)
                {
                    if (whereSql != "") whereSql += " OR";
                    whereSql += " uid = " + uid;

                    if (whereTrackSql != "") whereTrackSql += " OR";
                    whereTrackSql += " noticeid = " + uid;
                }

                delSql += whereSql;
                delTractSql += whereTrackSql;

                db.ExecuteCommand(delSql);
                db.ExecuteCommand(delTractSql);
                rst = true;
            }
            catch (System.Exception ex)
            {

            }

            return rst;
        }

        public List<Noitice> SearchNoticeList(string year)
        {
            var rst= GetNoiceList();
            return rst = rst.Where(m=>m.creattime.Contains(year)).ToList();
           
        }

        public string unescape(string s)
        {
            string str = s.Remove(0, 2);//删除最前面两个＂%u＂  
            string[] strArr = str.Split(new string[] { "%u" }, StringSplitOptions.None);//以子字符串＂%u＂分隔  
            byte[] byteArr = new byte[strArr.Length * 2];
            for (int i = 0, j = 0; i < strArr.Length; i++, j += 2)
            {
                byteArr[j + 1] = Convert.ToByte(strArr[i].Substring(0, 2), 16);  //把十六进制形式的字串符串转换为二进制字节  
                byteArr[j] = Convert.ToByte(strArr[i].Substring(2, 2), 16);
            }
            str = System.Text.Encoding.Unicode.GetString(byteArr);　//把字节转为unicode编码  
            return str;

        }
        public static string UnEscape(string str)
        {
            StringBuilder sb = new StringBuilder();
            int len = str.Length;
            int i = 0;
            while (i != len)
            {
                if (Uri.IsHexEncoding(str, i))
                    sb.Append(Uri.HexUnescape(str, ref i));
                else
                    sb.Append(str[i++]);
            }
            return sb.ToString();
        } 
    }
}