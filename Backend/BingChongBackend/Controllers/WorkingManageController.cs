using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using BingChongBackend.Models;
using BingChongBackend.Models.Library;
using System.IO;
using System.Collections;
using System.Text.RegularExpressions;

namespace BingChongBackend.Controllers
{
    public class WorkingManageController : Controller
    {
        //
        // GET: /WorkManage/
        WorkingManageModel workingmodel = new WorkingManageModel();

        [Authorize]
        public ActionResult WorkingPlan()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "WorkingManage";
            ViewData["level2nav"] = "WorkingPlan";
            ViewBag.sheng = new PointModel().GetAllSheng();
            user list1 = new PointModel().GetUserRoleInfo();
            ViewData["level"] = list1.level;
            ViewData["sheng1"] = list1.sheng_id;
            ViewData["shi1"] = list1.shi_id;
            ViewData["xian1"] = list1.xian_id;
            return View();
        }

        [Authorize]
        public ActionResult TemporaryWork()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "WorkingManage";
            ViewData["level2nav"] = "TemporaryWork";
            var currentlevel = CommonModel.GetSessionUserLevel();
            var user= CommonModel.GetSessionUser();
            ViewData["level"] = (int)currentlevel;

            var allshenglist = new RegionModel().GetShengList();
            switch (currentlevel)
            {
                case CommonModel.UserLevel.ADMIN:
                    ViewData["shenglist"] = allshenglist;
                    break;
                case CommonModel.UserLevel.GUOJIAJI:
                    ViewData["shenglist"] = allshenglist;
                    break;
                case CommonModel.UserLevel.SHENGJI:
                    ViewData["shenglist"] = allshenglist.Where(m=>m.uid==user.sheng_id).ToList();
                    break;
                case CommonModel.UserLevel.SHIJI:
                    ViewData["shenglist"] = allshenglist.Where(m => m.uid == user.sheng_id).ToList();

                    break;
                case CommonModel.UserLevel.XIANJI:
                    ViewData["shenglist"] = allshenglist.Where(m => m.uid == user.sheng_id).ToList();

                    break;
            }



            return View();
        }
        [HttpGet]
        public JsonResult RetrieveTeampWork(int year, long sheng, int rows, int page)
        {
            var currentlevel = CommonModel.GetSessionUserLevel();
            var user = CommonModel.GetSessionUser();
            var rst = new List<TempleWork>();
            
            switch (currentlevel)
            {
                case CommonModel.UserLevel.ADMIN:
                    rst = workingmodel.SearchTempWork(year, sheng, 0, 0);
                    break;
                case CommonModel.UserLevel.GUOJIAJI:
                    rst = workingmodel.SearchTempWork(year, sheng, 0, 0);
                    break;
                case CommonModel.UserLevel.SHENGJI:
                    rst = workingmodel.SearchTempWork(year, sheng, 0, 0);
                    break;
                case CommonModel.UserLevel.SHIJI:
                    rst = workingmodel.SearchTempWork(year, sheng, user.shi_id, 0);
                    break;
                case CommonModel.UserLevel.XIANJI:
                    rst = workingmodel.SearchTempWork(year, sheng, user.shi_id, user.xian_id);
                    break;
            }
            int record = rst.Count;

            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }
            return Json(new { page = page, total = total, records = record, rows = rst.Skip((page - 1) * rows).Take(page * rows).ToList() }, JsonRequestBehavior.AllowGet);
        }

        
        [HttpGet]
        public JsonResult SearchTeampWork(int year, long shengid, long shiid, long xianid, int rows, int page)
        {
            var rst = workingmodel.SearchTempWork(year, shengid, shiid, xianid);

            int record = rst.Count;

            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }
            return Json(new { page = page, total = total, records = record, rows = rst.Skip((page - 1) * rows).Take(page * rows).ToList() }, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        public ActionResult ManagingDocuments()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "WorkingManage";
            ViewData["level2nav"] = "ManagingDocuments";

            ViewData["role"] = CommonModel.GetUserRoleInfo();
            return View();
        }


        [HttpGet]
        public JsonResult RetrieveDocumentsList(int rows, int page)
        {
            var rst = workingmodel.GetDocumentsList();
            var currentlevel = CommonModel.GetSessionUserLevel();
            var user = CommonModel.GetSessionUser();
            
            if (currentlevel!=CommonModel.UserLevel.ADMIN)
            {
                rst = rst.Where(m => m.createrid == user.uid).ToList();
            } 

            int record = rst.Count;

            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }
            return Json(new { page = page, total = total, records = record, rows = rst.Skip((page - 1) * rows).Take(page * rows).ToList() }, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        public ActionResult AddDocuments()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "WorkingManage";
            ViewData["level2nav"] = "ManagingDocuments";

            return View();
        }

        [HttpPost]
        [AjaxOnly]
        public JsonResult GetUnreadNotice()
        {
            var rst = 0;
            if (CommonModel.GetUserRoleInfo().Contains("MessageUpperLevelSelection"))
            {
                rst = workingmodel.GetUnreadNotice();
            }

            return Json(rst, JsonRequestBehavior.AllowGet);
        }
        [HttpPost]
        [AjaxOnly]
        public JsonResult SubmitDocument(long uid, string name, string contents)
        {
            string rst = "";

            string role = "";

            if (uid == 0)
            {
                rst = workingmodel.InsertDocument(name, contents);
            }
            else
            {
                rst = workingmodel.UpdateDocument(uid, name, contents);
            }

            return Json(rst, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        public ActionResult EditDocument(long id)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "WorkingManage";
            ViewData["level2nav"] = "ManagingDocuments";

            ViewData["documentinfo"] = workingmodel.GetDocumentById(id);
            ViewData["contents"] = (String)workingmodel.GetDocumentById(id).contents;
            ViewData["uid"] = id;

            return View("AddDocuments");
        }
        [Authorize]
        public ActionResult ViewDocument(long id)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "WorkingManage";
            ViewData["level2nav"] = "ManagingDocuments";

            ViewData["documentinfo"] = workingmodel.GetDocumentById(id);
            ViewData["documentcontents"] = (String)workingmodel.GetDocumentById(id).contents;
            ViewData["uid"] = id;

            return View();
        }
        [HttpPost]
        public JsonResult DeleteDocument(string delids)
        {
            string[] ids = delids.Split(',');
            long[] selcheckbox = ids.Where(m => !String.IsNullOrWhiteSpace(m)).Select(m => long.Parse(m)).ToArray();
            bool rst = workingmodel.DeleteDocument(selcheckbox);

            return Json(rst, JsonRequestBehavior.AllowGet);
        }

        [Authorize]
        public ActionResult Notice()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "WorkingManage";
            ViewData["level2nav"] = "Notice";
            ViewData["role"] = CommonModel.GetUserRoleInfo();

            return View();
        }
        
        [HttpGet]
        public JsonResult SearchNoticeList(string type, string year, int rows, int page)
        {
            var rst = workingmodel.SearchNoticeList(year);
            var currentuserlevel = CommonModel.GetSessionUserLevel();
            var currentuser = CommonModel.GetSessionUser();

            if (type == "upper")
            {
                switch (currentuserlevel)
                {
                    case CommonModel.UserLevel.XIANJI:
                        rst = rst.Where(m => m.level == CommonModel.UserLevel.SHIJI && m.shi_id == currentuser.shi_id).ToList();
                        break;
                    case CommonModel.UserLevel.SHIJI:
                        rst = rst.Where(m => m.level == CommonModel.UserLevel.SHENGJI && m.sheng_id == currentuser.sheng_id).ToList();

                        break;
                    case CommonModel.UserLevel.SHENGJI:
                        rst = rst.Where(m => m.level == CommonModel.UserLevel.GUOJIAJI || m.level == CommonModel.UserLevel.ADMIN).ToList();
                        break;
                    case CommonModel.UserLevel.GUOJIAJI:
                        rst = new List<Noitice>();
                        break;
                    case CommonModel.UserLevel.ADMIN:
                        rst = new List<Noitice>();
                        break;
                }
            }
            else
            {
                //switch (currentuserlevel)
                //{
                //    case CommonModel.UserLevel.XIANJI:
                //        rst = rst.Where(m => m.level == CommonModel.UserLevel.XIANJI).ToList();
                //        break;
                //    case CommonModel.UserLevel.SHIJI:
                //        rst = rst.Where(m => m.level == CommonModel.UserLevel.SHIJI).ToList();
                //        break;
                //    case CommonModel.UserLevel.SHENGJI:
                //        rst = rst.Where(m => m.level == CommonModel.UserLevel.SHENGJI).ToList();
                //        break;
                //    case CommonModel.UserLevel.GUOJIAJI:
                //        rst = rst.Where(m => m.level == CommonModel.UserLevel.ADMIN || m.level == CommonModel.UserLevel.GUOJIAJI).ToList();
                //        break;
                //    case CommonModel.UserLevel.ADMIN:
                //        rst = rst.Where(m => m.level == CommonModel.UserLevel.ADMIN || m.level == CommonModel.UserLevel.GUOJIAJI).ToList();
                //        break;
                //}
                rst = rst.Where(m => m.userid == currentuser.uid).ToList();

            }
            int record = rst.Count;

            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }
            return Json(new { page = page, total = total, records = record, rows = rst.Skip((page - 1) * rows).Take(page * rows).ToList() }, JsonRequestBehavior.AllowGet);
        }
        [HttpGet ]
        public JsonResult RetrieveNoticeList(string type,int rows, int page)
        {
            var rst = workingmodel.GetNoiceList();
            var currentuserlevel = CommonModel.GetSessionUserLevel();
            var currentuser = CommonModel.GetSessionUser();
            if (type=="upper")
            {
                switch (currentuserlevel)
                {
                    case CommonModel.UserLevel.XIANJI:
                        rst = rst.Where(m => m.level == CommonModel.UserLevel.SHIJI && m.shi_id == currentuser.shi_id).ToList();
                    break;
                    case CommonModel.UserLevel.SHIJI:
                    rst = rst.Where(m => m.level == CommonModel.UserLevel.SHENGJI&&m.sheng_id==currentuser.sheng_id).ToList();

                    break;
                    case CommonModel.UserLevel.SHENGJI:
                        rst = rst.Where(m=>m.level == CommonModel.UserLevel.GUOJIAJI || m.level == CommonModel.UserLevel.ADMIN).ToList();
                        break;
                    case CommonModel.UserLevel.GUOJIAJI:
                        rst = new List<Noitice>(); 
                        break;
                    case CommonModel.UserLevel.ADMIN:
                        rst = new List<Noitice>(); 
                        break;
                }
            } 
            else
            {
                //switch (currentuserlevel)
                //{
                //    case CommonModel.UserLevel.XIANJI:
                //        rst = rst.Where(m => m.level == CommonModel.UserLevel.XIANJI).ToList();
                //        break;
                //    case CommonModel.UserLevel.SHIJI:
                //        rst = rst.Where(m => m.level == CommonModel.UserLevel.SHIJI).ToList();
                //        break;
                //    case CommonModel.UserLevel.SHENGJI:
                //        rst = rst.Where(m => m.level == CommonModel.UserLevel.SHENGJI).ToList();
                //        break;
                //    case CommonModel.UserLevel.GUOJIAJI:
                //        rst = rst.Where(m => m.level == CommonModel.UserLevel.ADMIN || m.level == CommonModel.UserLevel.GUOJIAJI).ToList();
                //        break;
                //    case CommonModel.UserLevel.ADMIN:
                //        rst = rst.Where(m => m.level == CommonModel.UserLevel.ADMIN || m.level == CommonModel.UserLevel.GUOJIAJI).ToList();
                //        break;
                //}
                rst = rst.Where(m => m.userid == currentuser.uid).ToList();
            }
            int record = rst.Count;

            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }
            return Json(new { page = page, total = total, records = record, rows = rst.Skip((page - 1) * rows).Take(page * rows).ToList() }, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        public ActionResult AddNotice()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "WorkingManage";
            ViewData["level2nav"] = "Notice";
            var currentid=CommonModel.GetSessionUserID();
            ViewData["username"] = new BingChongDBDataContext().users.Where(m => m.uid == currentid).Select(m => m.name).FirstOrDefault();
            ViewData["userid"] = CommonModel.GetSessionUserID();
            return View();
        }

        [HttpPost]
        [AjaxOnly]
        public JsonResult SubmitNotice(long uid, string title, long userid, string serial, string contents, string creattime)
        {
            var rst = "";
            String xianid = Request.Form["xian"];
            String shiid = Request.Form["shi"];
            String shengid = Request.Form["sheng"];

            if (uid == 0)
            {
                rst = workingmodel.InserteNotice(title, userid, creattime, serial, contents);
            }
            else
            {
                rst = workingmodel.UpdateNotice(uid, title, userid, creattime, serial, contents);
            }

            return Json(rst, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        public ActionResult ViewNotice(long id)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "WorkingManage";
            ViewData["level2nav"] = "Notice";
            var currentid = CommonModel.GetSessionUserID();
            workingmodel.ReadNotice(id, currentid);

            ViewData["username"] = new BingChongDBDataContext().users.Where(m => m.uid == currentid).Select(m => m.name).FirstOrDefault();
            ViewData["userid"] = CommonModel.GetSessionUserID();

            ViewData["noticeinfo"] = workingmodel.GetNoticeById(id);
            ViewData["uid"] = id;

            return View();
        }
        [Authorize]

        public ActionResult EditNotice(long id)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "WorkingManage";
            ViewData["level2nav"] = "Notice";

            var currentid = CommonModel.GetSessionUserID();
            ViewData["username"] = new BingChongDBDataContext().users.Where(m => m.uid == currentid).Select(m => m.name).FirstOrDefault();
            ViewData["userid"] = CommonModel.GetSessionUserID();

            ViewData["noticeinfo"] = workingmodel.GetNoticeById(id);
            ViewData["uid"] = id;

            return View("AddNotice");
        }

        [HttpPost]
        public JsonResult DeleteNotice(string delids)
        {
            string[] ids = delids.Split(',');
            long[] selcheckbox = ids.Where(m => !String.IsNullOrWhiteSpace(m)).Select(m => long.Parse(m)).ToArray();
            bool rst = workingmodel.DeleteNotice(selcheckbox);

            return Json(rst, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        public ActionResult YearWorkingPlan(long planid)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "WorkingManage";
            ViewData["level2nav"] = "WorkingPlan";
            List<Pointplan> list = new WorkingManageModel().GetOnePlan(planid);
            ViewBag.blight = new WorkingManageModel().GetAllBlight();
;           ViewBag.task = list[0];
            ViewData["planid"] = planid;
            return View();
        }
        [Authorize]
        public ActionResult EditWorking(long planid,long uid)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "WorkingManage";
            ViewData["level2nav"] = "WorkingPlan";
            ViewBag.shengc = new PointModel().GetAllSheng();
            user list2 = new PointModel().GetUserRoleInfo();
            ViewData["level"] = list2.level;
            ViewData["sheng"] = list2.sheng_id;
            ViewData["shi"] = list2.shi_id;
            ViewData["xian"] = list2.xian_id;
            List<Pointplan> list = new WorkingManageModel().GetOnePlan(planid);
            task_detail list1 = new WorkingManageModel().GetTask_detail(uid);
            blight bl = new WorkingManageModel().GetOneBlight(list1.blight_id);
            ViewData["blkind"] = bl.kind;
            ViewData["blname"] = list1.blight_id;
            ViewData["formss"] = list1.form_id;
            ViewData["starttime"] = list1.startdate.Year + "年" + list1.startdate.Month + "月" + list1.startdate.Day +"日";
            ViewData["endtime"] = list1.enddate.Year + "年" + list1.enddate.Month + "月" + list1.enddate.Day + "日";
            string startime=list1.startdate.ToString();
            string endtime=list1.enddate.ToString();
            ViewData["dates"] = GetDates2(endtime, startime);
            ViewData["days"] = list1.period;
            ViewData["fs"] = list1.term;
            ViewData["stshi"] = list1.starthour;
            ViewData["stfen"] = list1.startmin;
            ViewData["enshi"] = list1.endhour;
            ViewData["enfen"] = list1.endmin;
            ViewData["point_list"] = list1.point_list;
            ViewBag.forms = new WorkingManageModel().GetAllForm();
            ViewData["yearid"] = list[0].year_id;
            ViewData["shengid"] = list[0].sheng;
            ViewData["shiid"] = list[0].shi;
            ViewData["yearidd"] = list[0].year_id - 1;
            ViewData["taskid"] = list[0].id;
            ViewData["uid"] = uid;
            return View();
        }
        [Authorize]
        public ActionResult AddWorking(long planid)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "WorkingManage";
            ViewData["level2nav"] = "WorkingPlan";
            ViewBag.shengc = new PointModel().GetAllSheng();
            user list2 = new PointModel().GetUserRoleInfo();
            ViewData["level"] = list2.level;
            ViewData["sheng"] = list2.sheng_id;
            ViewData["shi"] = list2.shi_id;
            ViewData["xian"] = list2.xian_id;
             List<Pointplan> list = new WorkingManageModel().GetOnePlan(planid);
             ViewBag.forms = new WorkingManageModel().GetAllForm();
            ViewData["yearid"]=list[0].year_id;
            ViewData["shengid"]=list[0].sheng;
            ViewData["shiid"] = list[0].shi;
            ViewData["yearidd"] = list[0].year_id-1;
            ViewData["taskid"] = list[0].id;
            return View();
        }

        public JsonResult InsertPlan(string planname, long sheng, long year, long shi)
        {
            bool success = false;
            success = new WorkingManageModel().InsertPlan(planname, sheng, year, shi);
            return Json(success, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        public JsonResult FintPointPlan(long year, long sheng, long shi, int rows, int page)
        {
            List<Pointplan> list = new List<Pointplan>();
            //list = new WorkingManageModel().FintPointPlan(year, sheng, shi,rows,page);
            List<Pointplan> list1 = new WorkingManageModel().FintPointPlan1(year, sheng, shi);
            //int record = list1.Count;

            //int total = 0;
            //if ((record % rows) == 0)
            //{
            //    total = record / rows;
            //}
            //else if ((record % rows) != 0)
            //{
            //    total = record / rows + 1;
            //}
            //new { page = page, total = total, records = record, rows = list }
            return Json(list1, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        [HttpPost]
        [AjaxOnly]
        public JsonResult Deletepoint(string uids)
        {
            long[] s = new long[1];
            s[0] = Convert.ToInt64(uids);
            bool success = new WorkingManageModel().Deletepoint(Convert.ToInt64(uids));
            return Json(success, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        [HttpPost]
        [AjaxOnly]
        public JsonResult DeleteTaskDetail(string uids)
        {
            long[] s = new long[1];
            s[0] = Convert.ToInt64(uids);
            bool success = new WorkingManageModel().DeleteTaskDetail(Convert.ToInt64(uids));
            return Json(success, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        public JsonResult FintPoint(Byte sort, Byte style1, long sheng, long shi, long xian, int rows, int page)
        {
            List<StationPoint> list = new List<StationPoint>();
            list = new WorkingManageModel().FintPoint(sort, style1, sheng, shi, xian,rows,page);
            List<StationPoint> list1 = new WorkingManageModel().FintPoint1(sort, style1, sheng, shi, xian);
            int record = list1.Count;

            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }
            return Json(new { page = page, total = total, records = record, rows = list }, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        [HttpPost]
        [AjaxOnly]
        public JsonResult Findblname(long blkind)
        {
            List<blight> list = new List<blight>();
            list = new WorkingManageModel().Findblname(blkind);
            return Json(list, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        public JsonResult FintPlanDetail(Byte blkind, long blname,long planid, int rows, int page)
        {
            List<TaskDetail> list = new List<TaskDetail>();
            list = new WorkingManageModel().FintPlanDetail(blkind, blname,planid,rows,page);
            List<TaskDetail> list1 = new WorkingManageModel().FintPlanDetail1(blkind, blname,planid);

            foreach (TaskDetail a in list)
            {
                a.stime = a.starthour + "时" + a.startmin + "分" + "-" + a.endhour + "时" + a.endmin + "分";
                a.starttime = a.startdate.Month+"月"+a.startdate.Day+"日";
                a.endtime = a.enddate.Month + "月" + a.enddate.Day + "日";

            }
            int record = list1.Count;

            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }
            return Json(new { page = page, total = total, records = record, rows = list }, JsonRequestBehavior.AllowGet);
        }
        public JsonResult GetDates(string endtime, string startime)
        {
            DateTime st = Convert.ToDateTime(startime);
            DateTime et = Convert.ToDateTime(endtime);
            string dateDiff = "";
            if (st > et)
            {
                dateDiff = "true";
            }
            else
            {
                TimeSpan ts_now = new TimeSpan(st.Ticks);     //当前时间的 TimeSpan 结构对象；
                TimeSpan ts_end = new TimeSpan(et.Ticks);    //当前时间的 TimeSpan 结构对象；
                TimeSpan ts_diff = ts_now.Subtract(ts_end).Duration();   // 两时间相差的TimeSpan
                dateDiff = (ts_diff.Days + 1).ToString();
            }
            
            return Json(dateDiff, JsonRequestBehavior.AllowGet);
        }
        public string GetDates2(string endtime, string startime)
        {
            DateTime st = Convert.ToDateTime(startime);
            DateTime et = Convert.ToDateTime(endtime);
            string dateDiff = "";
            if (st > et)
            {
                dateDiff = "true";
            }
            else
            {
                TimeSpan ts_now = new TimeSpan(st.Ticks);     //当前时间的 TimeSpan 结构对象；
                TimeSpan ts_end = new TimeSpan(et.Ticks);    //当前时间的 TimeSpan 结构对象；
                TimeSpan ts_diff = ts_now.Subtract(ts_end).Duration();   // 两时间相差的TimeSpan
                dateDiff = (ts_diff.Days + 1).ToString();
            }

            return dateDiff;
        }
        public JsonResult Getdays(string daysid,string dates)
        {
            int d = 0;
            string c = "1";
            int a = Convert.ToInt32(dates) / Convert.ToInt32(daysid);
            int m = Convert.ToInt32(dates) % Convert.ToInt32(daysid);
            if (m > 0)
            {
                d = a + 1;
                c = "2";
             }
            if (m == 0)
            {
                d = a;
            }
            List<string> list = new List<string>();
            list.Add(c);
            list.Add(d+"");
           
            return Json(list, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        public JsonResult Getform(long blname)
        {
            List<form> list = new List<form>();
            list = new WorkingManageModel().Getform(blname);
            return Json(list, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        public JsonResult InserPoint(Byte blkind, long blname, long formss, DateTime starttime, DateTime endtime,long dates,long fs,long days,
            long stshi, long stfen, long enshi, long enfen, string pointids,long taskid)
        {
            bool success = false;
            success = new WorkingManageModel().InsertPoint(blname, formss, starttime, endtime, dates, fs, days, stshi, stfen, enshi, enfen, pointids,taskid);
            return Json(success, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        public JsonResult EditPoint(long uid,Byte blkind, long blname, long formss, DateTime starttime, DateTime endtime, long dates, long fs, long days,
            long stshi, long stfen, long enshi, long enfen, string pointids, long taskid)
        {
            bool success = false;
            success = new WorkingManageModel().EditPoint(uid,blname, formss, starttime, endtime, dates, fs, days, stshi, stfen, enshi, enfen, pointids, taskid);
            return Json(success, JsonRequestBehavior.AllowGet);
        }

        public ActionResult ProcessKindEditorRequestT()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            //String aspxUrl = context.Request.Path.Substring(0, context.Request.Path.LastIndexOf("/") + 1);

            //根目录路径，相对路径
            String rootPath = "/Content/EditZoneInformation/";
            //根目录URL，可以指定绝对路径，
            String rootUrl = "/Content/EditZoneInformation/";

            long merchantUid = 11;

            //try
            //{
            //    merchantUid = long.Parse(rootUri.Split('.')[0]);
            //}
            //catch (System.Exception ex)
            //{

            //}

            string datelbl = String.Format("{0:yyyyMMdd}", DateTime.Now);
            string tempPath = Server.MapPath(rootPath) + merchantUid.ToString() + "\\" + datelbl;

            if (!System.IO.Directory.Exists(tempPath))
            {
                System.IO.Directory.CreateDirectory(tempPath);
            }

            rootPath = rootPath + merchantUid.ToString() + "/";
            rootUrl = rootPath;

            //图片扩展名
            String fileTypes = "gif,jpg,jpeg,png,bmp";

            String currentPath = "";
            String currentUrl = "";
            String currentDirPath = "";
            String moveupDirPath = "";

            //根据path参数，设置各路径和URL
            String path = Request.QueryString["path"];
            path = String.IsNullOrEmpty(path) ? "" : path;
            if (path == "")
            {
                currentPath = Server.MapPath(rootPath);
                currentUrl = rootUrl;
                currentDirPath = "";
                moveupDirPath = "";
            }
            else
            {
                currentPath = Server.MapPath(rootPath) + path;
                currentUrl = rootUrl + path;
                currentDirPath = path;
                moveupDirPath = Regex.Replace(currentDirPath, @"(.*?)[^\/]+\/$", "$1");
            }

            //排序形式，name or size or type
            String order = Request.QueryString["order"];
            order = String.IsNullOrEmpty(order) ? "" : order.ToLower();

            //不允许使用..移动到上一级目录
            if (Regex.IsMatch(path, @"\.\."))
            {
                Response.Write("Access is not allowed.");
                Response.End();
            }
            //最后一个字符不是/
            if (path != "" && !path.EndsWith("/"))
            {
                Response.Write("Parameter is not valid.");
                Response.End();
            }
            //目录不存在或不是目录
            if (!Directory.Exists(currentPath))
            {
                Response.Write("Directory does not exist.");
                Response.End();
            }

            //遍历目录取得文件信息
            string[] dirList = Directory.GetDirectories(currentPath);
            string[] fileList = Directory.GetFiles(currentPath);

            switch (order)
            {
                case "size":
                    Array.Sort(dirList, new NameSorter());
                    Array.Sort(fileList, new SizeSorter());
                    break;
                case "type":
                    Array.Sort(dirList, new NameSorter());
                    Array.Sort(fileList, new TypeSorter());
                    break;
                case "name":
                default:
                    Array.Sort(dirList, new NameSorter());
                    Array.Sort(fileList, new NameSorter());
                    break;
            }

            Hashtable result = new Hashtable();
            result["moveup_dir_path"] = moveupDirPath;
            result["current_dir_path"] = currentDirPath;
            result["current_url"] = currentUrl;
            result["total_count"] = dirList.Length + fileList.Length;
            List<Hashtable> dirFileList = new List<Hashtable>();
            result["file_list"] = dirFileList;
            for (int i = 0; i < dirList.Length; i++)
            {
                DirectoryInfo dir = new DirectoryInfo(dirList[i]);
                Hashtable hash = new Hashtable();
                hash["is_dir"] = true;
                hash["has_file"] = (dir.GetFileSystemInfos().Length > 0);
                hash["filesize"] = 0;
                hash["is_photo"] = false;
                hash["filetype"] = "";
                hash["filename"] = dir.Name;
                hash["datetime"] = dir.LastWriteTime.ToString("yyyy-MM-dd HH:mm:ss");
                dirFileList.Add(hash);
            }
            for (int i = 0; i < fileList.Length; i++)
            {
                FileInfo file = new FileInfo(fileList[i]);
                Hashtable hash = new Hashtable();
                hash["is_dir"] = false;
                hash["has_file"] = false;
                hash["filesize"] = file.Length;
                hash["is_photo"] = (Array.IndexOf(fileTypes.Split(','), file.Extension.Substring(1).ToLower()) >= 0);
                hash["filetype"] = file.Extension.Substring(1);
                hash["filename"] = file.Name;
                hash["datetime"] = file.LastWriteTime.ToString("yyyy-MM-dd HH:mm:ss");
                dirFileList.Add(hash);
            }
            //Response.AddHeader("Content-Type", "application/json; charset=UTF-8");
            //context.Response.Write(JsonMapper.ToJson(result));
            //context.Response.End();
            return Json(result, "text/html;charset=UTF-8", JsonRequestBehavior.AllowGet);
        }
         public class NameSorter : IComparer
    {
        public int Compare(object x, object y)
        {
            if (x == null && y == null)
            {
                return 0;
            }
            if (x == null)
            {
                return -1;
            }
            if (y == null)
            {
                return 1;
            }
            FileInfo xInfo = new FileInfo(x.ToString());
            FileInfo yInfo = new FileInfo(y.ToString());

            return xInfo.FullName.CompareTo(yInfo.FullName);
        }
    }

    public class SizeSorter : IComparer
    {
        public int Compare(object x, object y)
        {
            if (x == null && y == null)
            {
                return 0;
            }
            if (x == null)
            {
                return -1;
            }
            if (y == null)
            {
                return 1;
            }
            FileInfo xInfo = new FileInfo(x.ToString());
            FileInfo yInfo = new FileInfo(y.ToString());

            return xInfo.Length.CompareTo(yInfo.Length);
        }
    }

    public class TypeSorter : IComparer
    {
        public int Compare(object x, object y)
        {
            if (x == null && y == null)
            {
                return 0;
            }
            if (x == null)
            {
                return -1;
            }
            if (y == null)
            {
                return 1;
            }
            FileInfo xInfo = new FileInfo(x.ToString());
            FileInfo yInfo = new FileInfo(y.ToString());

            return xInfo.Extension.CompareTo(yInfo.Extension);
        }
    }
    }
    public class NameSorter : IComparer
    {
        public int Compare(object x, object y)
        {
            if (x == null && y == null)
            {
                return 0;
            }
            if (x == null)
            {
                return -1;
            }
            if (y == null)
            {
                return 1;
            }
            FileInfo xInfo = new FileInfo(x.ToString());
            FileInfo yInfo = new FileInfo(y.ToString());

            return xInfo.FullName.CompareTo(yInfo.FullName);
        }
    }

    public class SizeSorter : IComparer
    {
        public int Compare(object x, object y)
        {
            if (x == null && y == null)
            {
                return 0;
            }
            if (x == null)
            {
                return -1;
            }
            if (y == null)
            {
                return 1;
            }
            FileInfo xInfo = new FileInfo(x.ToString());
            FileInfo yInfo = new FileInfo(y.ToString());

            return xInfo.Length.CompareTo(yInfo.Length);
        }
    }

    public class TypeSorter : IComparer
    {
        public int Compare(object x, object y)
        {
            if (x == null && y == null)
            {
                return 0;
            }
            if (x == null)
            {
                return -1;
            }
            if (y == null)
            {
                return 1;
            }
            FileInfo xInfo = new FileInfo(x.ToString());
            FileInfo yInfo = new FileInfo(y.ToString());

            return xInfo.Extension.CompareTo(yInfo.Extension);
        }
    }
}
