using System;
using System.Configuration;
using System.Data;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.Mvc;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.IO;
using System.Data.OleDb;
using BingChongBackend.Models.Library;
using NPOI.Util.Collections;
using NPOI.DDF;
using BingChongBackend.Models;
using NPOI.HSSF;
using NPOI.HSSF.UserModel;
using NPOI.SS.UserModel;
using NPOI.HSSF.Util;
using NPOI;
using NPOI.SS.Util;



namespace BingChongBackend.Controllers
{
    public class UpdatedInfoController : Controller
    {
        //
        // GET: /UpdatedInfo/
        [Authorize]
        public ActionResult UpdatedInfoManage()
        {

            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            List<sheng> shenglist = new UpDataInfoModel().GetSheng();
            //ExData();
            List<shi> shilist = new UpDataInfoModel().GetShi();
            List<xian> xianlist = new UpDataInfoModel().GetXian();
            int m2 = DateTime.Now.Month;
            DateTime m1 = DateTime.Now.AddDays(-DateTime.Now.Day + 1).AddHours(-DateTime.Now.Hour).AddMinutes(-DateTime.Now.Minute).AddSeconds(-DateTime.Now.Second);
            DateTime m3 = DateTime.Now.AddHours(-DateTime.Now.Hour).AddMinutes(-DateTime.Now.Minute).AddSeconds(-DateTime.Now.Second);
            long userid = CommonModel.GetSessionUserID();//获得用户id 根据id查询出用户级别
            user users = new BingChongDBDataContext().users.Single(a => a.uid == userid);
            long rid = users.right_id;
            string userlevel = users.level.ToString();//获得级别
            long? shengid = users.sheng_id;
            long? shiid = users.shi_id;
            long? xianid = users.xian_id;
            List<point> points = new UpDataInfoModel().GetPoint();

            //如果是县 获得省市县的id
            if (userlevel == "1")
            {

                shenglist = shenglist.Where(a => a.uid == shengid).ToList();
                shilist = shilist.Where(a => a.uid == shiid).ToList();
                xianlist = xianlist.Where(a => a.uid == xianid).ToList();
                ViewData["ul"] = "1";
                points = points.Where(a => a.xian_id == xianid && a.level == 0 && a.type == 0 && a.shi_id == shiid && a.sheng_id == shengid).ToList();
            }
            //如果是市级账号登陆
            if (userlevel == "2")
            {
                ViewData["ul"] = "2";
                shenglist = shenglist.Where(a => a.uid == shengid).ToList();
                shilist = shilist.Where(a => a.uid == shiid).ToList();
                xianlist = xianlist.Where(a => a.shi_id==shiid).ToList();
                points = points.Where(a => a.level == 0 && a.type == 0 && a.shi_id == shiid && a.sheng_id == shengid).ToList();
            }
            //如果是省级
            if (userlevel == "3")
            {
                ViewData["ul"] = "3";
                shenglist = shenglist.Where(a => a.uid == shengid).ToList();
                shilist = shilist.Where(a => a.sheng_id == shengid).ToList();
                points = points.Where(a => a.level == 0 && a.type == 0 && a.sheng_id == shengid).ToList();

            }
            //如果是国家级
            if (userlevel == "4")
            {
                ViewData["ul"] = "4";

                shenglist = shenglist.Where(a => a.deleted == 0).ToList();
                shilist = shilist.Where(a => a.deleted == 0).ToList();
                xianlist = xianlist.Where(a => a.deleted == 0).ToList();

            }
            //如果是国家级
            if (userlevel == "0")
            {
                ViewData["ul"] = "0";

                shenglist = shenglist.Where(a => a.deleted == 0).ToList();
                shilist = shilist.Where(a => a.deleted == 0).ToList();
                xianlist = xianlist.Where(a => a.deleted == 0).ToList();

            }
            string roles = new BingChongDBDataContext().tbl_rights.Single(a => a.uid == rid).role;
            string s = m1.ToString("yyyy-MM-dd");
            string s1 = m3.ToString("yyyy-MM-dd");

            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "UpdateManage";
            ViewData["level2nav"] = "UpdateManage";
            ViewData["roles"] = roles;
            ViewData["cumonth"] = s;
            ViewData["today"] = s1;
            ViewData["uid"] = userid.ToString();
            ViewBag.pointlist = points;
            ViewBag.shenglist = shenglist;
            ViewBag.shilist = shilist;
            ViewBag.xianlist = xianlist;
            return View();
        }
        //根据所选的省获得市
        public JsonResult AddShilist(long shengid)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            List<shi> shilist = new UpDataInfoModel().GetShi().Where(a => a.sheng_id == shengid).ToList();

            return Json(shilist, JsonRequestBehavior.AllowGet);

        }

        //根据所选的市获得县
        public JsonResult AddXianlist(long shiid)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            List<xian> xianlist = new UpDataInfoModel().GetXian().Where(a => a.shi_id == shiid).ToList();

            return Json(xianlist, JsonRequestBehavior.AllowGet);

        }

        //获得页面加载时默认的表格的数据
        public JsonResult UpInfoList()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            List<UDIM> list = new UpDataInfoModel().GetUpDataInfo();
            long userid = CommonModel.GetSessionUserID();//获得用户id 根据id查询出用户级别
            user users = new BingChongDBDataContext().users.Single(a => a.uid == userid);
            string userlevel = users.level.ToString();//获得级别
            long? shengid = users.sheng_id;
            long? shiid = users.shi_id;
            long? xianid = users.xian_id;
            //如果是县 获得省市县的id
            if (userlevel == "1")
            {
                list = list.Where(a => a.shengid == shengid && a.shiid == shiid && a.xianid == xianid && a.level == 0 && a.type == 0).ToList();
            }
            //如果是市级账号登陆
            if (userlevel == "2")
            {
                list = list.Where(a => a.shengid == shengid && a.shiid == shiid && a.level == 0 && a.type == 0 && (a.review_state == 1 || a.review_state == 2 || a.review_state == 3 || a.review_state == 5)).ToList();
            }
            //如果是省级
            if (userlevel == "3")
            {
                list = list.Where(a => a.shengid == shengid && a.level == 0 && a.type == 0 && (a.review_state == 2 || a.review_state == 4 || a.review_state == 5)).ToList();
            }
            //如果是国家级
            if (userlevel == "4")
            {
                list = list.Where(a => a.level == 0 && a.type == 0).ToList();
            }
            //如果是国家级
            if (userlevel == "0")
            {
                list = list;
            }



            return Json(list, JsonRequestBehavior.AllowGet);



        }

        //联动获得测报点信息
        public JsonResult GetPointlist(long shengid, long shiid, long xianid, byte level, byte type)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));

            List<point> list = new List<point>();
            if (shengid != 0 && shiid != 0 && xianid != 0)
            {
                list = new UpDataInfoModel().GetPoint().Where(a => a.xian_id == xianid && a.level == level && a.type == type && a.shi_id == shiid && a.sheng_id == shengid).ToList();
            }
            //如果省市县全都是0
            if (shiid == 0 && shengid == 0 && xianid == 0) { list = new UpDataInfoModel().GetPoint().Where(a => a.level == level && a.type == type).ToList(); }
            //只有省 省不是0 市0 县0
            if (shengid > 0 && shiid == 0 && xianid == 0)
            {
                list = new UpDataInfoModel().GetPoint().Where(a => a.level == level && a.type == type && a.sheng_id == shengid).ToList();


            }
            //市级 省不是0 市不是 0 县0
            if (shengid > 0 && shiid > 0 && xianid == 0)
            {
                list = new UpDataInfoModel().GetPoint().Where(a => a.level == level && a.type == type && a.shi_id == shiid && a.sheng_id == shengid).ToList();


            }
            //市级 省不是0 市不是 0 县0
            if (shengid > 0 && shiid > 0 && xianid > 0)
            {
                list = new UpDataInfoModel().GetPoint().Where(a => a.level == level && a.type == type && a.shi_id == shiid && a.sheng_id == shengid && a.xian_id == xianid).ToList();


            }

            return Json(list, JsonRequestBehavior.AllowGet);






        }

        //将id相同的数据整合一起
        public class Comparint : IEqualityComparer<UDIM>
        {

            public bool Equals(UDIM x, UDIM y)
            {
                if (x == null && y == null)
                    return false;
                return x.blight_id == y.blight_id;
            }

            public int GetHashCode(UDIM obj)
            {
                return obj.ToString().GetHashCode();
            }
        }

        //将id相同的数据整合一起
        public class Comparint1 : IEqualityComparer<UDIM>
        {

            public bool Equals(UDIM x, UDIM y)
            {
                if (x == null && y == null)
                    return false;
                return x.form_id == y.form_id;
            }

            public int GetHashCode(UDIM obj)
            {
                return obj.ToString().GetHashCode();
            }
        }

        //获得病虫害名称
        public JsonResult GetBnamelist(long shengid, long shiid, long xianid, byte level, byte type, byte kind)
        {
            BingChongDBDataContext db = new BingChongDBDataContext();
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            List<blight> blist = new List<blight>();
            blist = db.blights.Where(a => a.kind == kind).ToList();
            //List<UDIM> list = new List<UDIM>();


            //if (kind == 2)
            //{
            //    if (shengid != 0 && shiid != 0 && xianid != 0)
            //    {
            //        list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.shiid == shiid && a.xianid == xianid && a.level == level && a.type == type).ToList();
            //    }
            //    //如果省市县全都是0
            //    if (shiid == 0 && shengid == 0 && xianid == 0) { list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.level == level && a.type == type).ToList(); }
            //    //只有省 省不是0 市0 县0
            //    if (shengid > 0 && shiid == 0 && xianid == 0)
            //    {
            //        list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.level == level && a.type == type).ToList();


            //    }
            //    //市级 省不是0 市不是 0 县0
            //    if (shengid > 0 && shiid > 0 && xianid == 0)
            //    {
            //        list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.shiid == shiid && a.level == level && a.type == type).ToList();


            //    }
            //    if (shengid > 0 && shiid > 0 && xianid > 0)
            //    {
            //        list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.shiid == shiid && a.xianid == xianid && a.level == level && a.type == type).ToList();


            //    }

            //}
            //else
            //{

            //    if (shengid != 0 && shiid != 0 && xianid != 0)
            //    {
            //        list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.shiid == shiid && a.xianid == xianid && a.level == level && a.type == type && a.blight_kind == kind).ToList();
            //    }
            //    //如果省市县全都是0
            //    if (shiid == 0 && shengid == 0 && xianid == 0) { list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.level == level && a.type == type && a.blight_kind == kind).ToList(); }
            //    //只有省 省不是0 市0 县0
            //    if (shengid > 0 && shiid == 0 && xianid == 0)
            //    {
            //        list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.level == level && a.type == type && a.blight_kind == kind).ToList();


            //    }
            //    //市级 省不是0 市不是 0 县0
            //    if (shengid > 0 && shiid > 0 && xianid == 0)
            //    {
            //        list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.shiid == shiid && a.level == level && a.type == type && a.blight_kind == kind).ToList();


            //    }
            //    if (shengid > 0 && shiid > 0 && xianid > 0)
            //    {
            //        list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.shiid == shiid && a.xianid == xianid && a.level == level && a.type == type && a.blight_kind == kind).ToList();


            //    }
            // }
            return Json(blist, JsonRequestBehavior.AllowGet);


        }

        //获得表格名称
        public JsonResult GetFormNamelist(long shengid, long shiid, long xianid, byte level, byte type, byte kind, long bid)
        {
            BingChongDBDataContext db = new BingChongDBDataContext();
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            blight bl = db.blights.Single(a => a.uid == bid);
            string fids = bl.form_ids;
            List<form> formall = new List<form>();
            if (fids == null)
            {
            }
            else
            {
                string[] fidsarr = fids.Split(',');

                for (int i = 0; i < fidsarr.Length; i++)
                {
                    if (fidsarr[i] != "")
                    {
                        long fid = long.Parse(fidsarr[i]);
                        List<form> listone = db.forms.Where(a => a.uid == fid && a.deleted == 0).ToList();
                        formall.AddRange(listone);
                    }


                }
            }

            //List<UDIM> list = new List<UDIM>();
            //if (bid > 0)
            //{
            //    if (kind == 2)
            //    {
            //        if (shengid != 0 && shiid != 0 && xianid != 0)
            //        {
            //            list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.shiid == shiid && a.xianid == xianid && a.level == level && a.type == type && a.blight_id
            //                == bid).ToList();
            //        }
            //        //如果省市县全都是0
            //        if (shiid == 0 && shengid == 0 && xianid == 0) { list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.level == level && a.type == type && a.blight_id == bid).ToList(); }
            //        //只有省 省不是0 市0 县0
            //        if (shengid > 0 && shiid == 0 && xianid == 0)
            //        {
            //            list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.level == level && a.type == type && a.blight_id == bid).ToList();


            //        }
            //        //市级 省不是0 市不是 0 县0
            //        if (shengid > 0 && shiid > 0 && xianid == 0)
            //        {
            //            list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.shiid == shiid && a.level == level && a.type == type && a.blight_id == bid).ToList();


            //        }
            //        if (shengid > 0 && shiid > 0 && xianid > 0)
            //        {
            //            list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.shiid == shiid && a.xianid == xianid && a.level == level && a.type == type && a.blight_id == bid).ToList();


            //        }

            //    }
            //    else
            //    {

            //        if (shengid != 0 && shiid != 0 && xianid != 0)
            //        {
            //            list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.shiid == shiid && a.xianid == xianid && a.level == level && a.type == type && a.blight_kind == kind).ToList();
            //        }
            //        //如果省市县全都是0
            //        if (shiid == 0 && shengid == 0 && xianid == 0) { list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.level == level && a.type == type && a.blight_kind == kind).ToList(); }
            //        //只有省 省不是0 市0 县0
            //        if (shengid > 0 && shiid == 0 && xianid == 0)
            //        {
            //            list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.level == level && a.type == type && a.blight_kind == kind).ToList();


            //        }
            //        //市级 省不是0 市不是 0 县0
            //        if (shengid > 0 && shiid > 0 && xianid == 0)
            //        {
            //            list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.shiid == shiid && a.level == level && a.type == type && a.blight_kind == kind).ToList();


            //        }
            //        if (shengid > 0 && shiid > 0 && xianid > 0)
            //        {
            //            list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.shiid == shiid && a.xianid == xianid && a.level == level && a.type == type && a.blight_kind == kind).ToList();


            //        }
            //    }
            //}
            //else
            //{
            //    if (kind == 2)
            //    {
            //        if (shengid != 0 && shiid != 0 && xianid != 0)
            //        {
            //            list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.shiid == shiid && a.xianid == xianid && a.level == level && a.type == type).ToList();
            //        }
            //        //如果省市县全都是0
            //        if (shiid == 0 && shengid == 0 && xianid == 0) { list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.level == level && a.type == type).ToList(); }
            //        //只有省 省不是0 市0 县0
            //        if (shengid > 0 && shiid == 0 && xianid == 0)
            //        {
            //            list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.level == level && a.type == type).ToList();


            //        }
            //        //市级 省不是0 市不是 0 县0
            //        if (shengid > 0 && shiid > 0 && xianid == 0)
            //        {
            //            list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.shiid == shiid && a.level == level && a.type == type).ToList();


            //        }
            //        if (shengid > 0 && shiid > 0 && xianid > 0)
            //        {
            //            list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.shiid == shiid && a.xianid == xianid && a.level == level && a.type == type).ToList();


            //        }

            //    }
            //    else
            //    {

            //        if (shengid != 0 && shiid != 0 && xianid != 0)
            //        {
            //            list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.shiid == shiid && a.xianid == xianid && a.level == level && a.type == type && a.blight_kind == kind).ToList();
            //        }
            //        //如果省市县全都是0
            //        if (shiid == 0 && shengid == 0 && xianid == 0) { list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.level == level && a.type == type && a.blight_kind == kind).ToList(); }
            //        //只有省 省不是0 市0 县0
            //        if (shengid > 0 && shiid == 0 && xianid == 0)
            //        {
            //            list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.level == level && a.type == type && a.blight_kind == kind).ToList();


            //        }
            //        //市级 省不是0 市不是 0 县0
            //        if (shengid > 0 && shiid > 0 && xianid == 0)
            //        {
            //            list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.shiid == shiid && a.level == level && a.type == type && a.blight_kind == kind).ToList();


            //        }
            //        if (shengid > 0 && shiid > 0 && xianid > 0)
            //        {
            //            list = new UpDataInfoModel().GetUpDataInfo().Where(a => a.shengid == shengid && a.shiid == shiid && a.xianid == xianid && a.level == level && a.type == type && a.blight_kind == kind).ToList();


            //        }
            //    }
            //}

            return Json(formall, JsonRequestBehavior.AllowGet);


        }

        //查询功能 分页也有
        public JsonResult SearchBy(int rows, int page, long shengid, long shiid, long xianid, byte level, byte type, long pointid, byte kindid, long nameid, long formid, int sta, DateTime starttime, DateTime endtime)
        {
            long userid = CommonModel.GetSessionUserID();//获得用户id 根据id查询出用户级别
            user users = new BingChongDBDataContext().users.Single(a => a.uid == userid);
            string userlevel = users.level.ToString();//获得级别
            List<UDIM> list = new List<UDIM>();

            list = new UpDataInfoModel().GetSearchList(shengid, shiid, xianid, level, type, pointid, kindid, nameid, formid, sta, starttime, endtime.AddDays(1)).OrderByDescending(a => a.report_time).ToList();
            //如果是县 获得省市县的id
            if (userlevel == "1")
            {
                if (level == 0)
                {
                    list = list.Where(a => a.level == level && a.type == type).ToList();
                }
                else
                {
                    list = list.Where(a => a.level == level && a.type == type).ToList();
                }

            }
            //如果是市级账号登陆
            if (userlevel == "2")
            {
                if (level == 0)
                {
                    list = list.Where(a => a.level == level && a.type == type && (a.review_state == 1 || a.review_state == 2 || a.review_state == 3 || a.review_state == 4 || a.review_state == 5)).ToList();
                }
                else
                {
                    list = list.Where(a => a.level == level && a.type == type).ToList();

                }

            }
            //如果是省级
            if (userlevel == "3")
            {
                if (level == 0)
                {
                    list = list.Where(a => a.level == level && a.type == type && (a.review_state == 2 || a.review_state == 4 || a.review_state == 5)).ToList();
                }
                else
                {
                    list = list.Where(a => a.level == level && a.type == type).ToList();

                }

            }
            //如果是国家级
            if (userlevel == "4")
            {
                list = list.Where(a => a.level == level && a.type == type && a.review_state == 4).ToList();
            }
            //如果是国家级
            if (userlevel == "0")
            {
                list = list;
            }







            List<UDIM> list1 = list.Skip((page - 1) * rows).Take(page * rows).ToList();

            int record = list.Count;

            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }

            string t = total + "|" + record;
            total = int.Parse(t.Split('|')[0]);
            int records = int.Parse(t.Split('|')[1]);
            return Json(new { page = page, total = total, records = records, rows = list1 }, JsonRequestBehavior.AllowGet);
        }

        //审核通过
        public JsonResult PassCheck(long rid, string reason)
        {

            string resultstr = new UpDataInfoModel().PassVerify(rid, reason);
            return Json(resultstr, JsonRequestBehavior.AllowGet);

        }

        //审核不通过
        public JsonResult NotPassCheck(long rid, string reason)
        {

            string resultstr = new UpDataInfoModel().NotPassVerify(rid, reason);
            return Json(resultstr, JsonRequestBehavior.AllowGet);

        }

        //删除一条数据
        public JsonResult DeleteReport(long id)
        {
            string resstr = new UpDataInfoModel().DeletedOne(id);
            return Json(resstr, JsonRequestBehavior.AllowGet);


        }

        //批量删除
        public JsonResult BatchDelete(string ids)
        {
            string resstr = "";
            string[] idarr = ids.Split(',');
            long[] select = idarr.Where(m => !String.IsNullOrWhiteSpace(m)).Select(m => long.Parse(m)).ToArray();
            for (int i = 0; i < select.LongCount(); i++)
            {

                resstr = new UpDataInfoModel().DeletedOne(select[i]);


            }
            return Json(resstr, JsonRequestBehavior.AllowGet);
        }

        //表格查看
        [HttpPost]
        public JsonResult UpGetTableHtml(long tableid, long repid, int type)
        {
            string rst = "";
            rst = new UpDataInfoModel().uGetTableHtml(tableid, repid, type);
            return Json(rst, JsonRequestBehavior.AllowGet);
        }

        //审核日志的显示
        public JsonResult GetLog(long tableid, long repid)
        {

            string rst = "";
            rst = new UpDataInfoModel().GetLogBy(repid);
            if (rst == null)
            {
                rst = "n";
            }
            else
            {
                rst = rst;
            }

            return Json(rst, JsonRequestBehavior.AllowGet);

        }

        //修改 
        public JsonResult Modifys(long report, long filedid, string val)
        {

            string rst = "";
            rst = new UpDataInfoModel().ModifyData(report, filedid, val);
            return Json(rst, JsonRequestBehavior.AllowGet);
        }

        //导出
        public void ExData(long shengid, long shiid, long xianid, byte type, byte kindid, long pointid, byte level, long formid, long nameid, int sat, DateTime starttime, DateTime endtime)
        {
            BingChongDBDataContext db = new BingChongDBDataContext();
            long userid = CommonModel.GetSessionUserID();//获得用户id 根据id查询出用户级别
            user users = new BingChongDBDataContext().users.Single(a => a.uid == userid);
            string userlevel = users.level.ToString();//获得级别
            List<UDIM> list = new List<UDIM>();

            list = new UpDataInfoModel().GetSearchList(shengid, shiid, xianid, level, type, pointid, kindid, nameid, formid, sat, starttime, endtime.AddDays(1));
            //如果是县 获得省市县的id
            if (userlevel == "1")
            {
                if (level == 0)
                {
                    list = list.Where(a => a.level == level && a.type == type).ToList();
                }
                else
                {
                    list = list.Where(a => a.level == level && a.type == type).ToList();
                }

            }
            //如果是市级账号登陆
            if (userlevel == "2")
            {
                if (level == 0)
                {
                    list = list.Where(a => a.level == level && a.type == type && (a.review_state == 1 || a.review_state == 2 || a.review_state == 3 || a.review_state == 4 || a.review_state == 5)).ToList();
                }
                else
                {
                    list = list.Where(a => a.level == level && a.type == type).ToList();

                }

            }
            //如果是省级
            if (userlevel == "3")
            {
                if (level == 0)
                {
                    list = list.Where(a => a.level == level && a.type == type && (a.review_state == 2 || a.review_state == 4 || a.review_state == 5)).ToList();
                }
                else
                {
                    list = list.Where(a => a.level == level && a.type == type).ToList();

                }

            }
            //如果是国家级
            if (userlevel == "4")
            {
                list = list.Where(a => a.level == level && a.type == type && a.review_state == 4).ToList();
            }
            //如果是国家级
            if (userlevel == "0")
            {
                list = list;
            }
            //根据表格进行分组

            List<UDIM> datalist = list.Distinct(new Comparint1()).ToList();
            if (datalist.Count > 0)
            {
                NPOI.HSSF.UserModel.HSSFWorkbook book = new NPOI.HSSF.UserModel.HSSFWorkbook();
                HSSFFont font = (HSSFFont)book.CreateFont();
                HSSFFont headfont = (HSSFFont)book.CreateFont();
                font.FontHeightInPoints = 10;
                font.Boldweight = (short)FontBoldWeight.Bold;
                headfont.FontHeightInPoints = 14;
                headfont.Boldweight = (short)FontBoldWeight.Bold;
                ICellStyle style = (ICellStyle)book.CreateCellStyle();
                ICellStyle headstyle = (ICellStyle)book.CreateCellStyle();
                style.Alignment = HorizontalAlignment.Center;
                headstyle.Alignment = HorizontalAlignment.Center;
                //style.ShrinkToFit = true;
                style.SetFont(font);
                headstyle.SetFont(headfont);
                style.BorderBottom = NPOI.SS.UserModel.BorderStyle.Thin;
                style.BorderTop = NPOI.SS.UserModel.BorderStyle.Thin;
                style.BorderLeft = NPOI.SS.UserModel.BorderStyle.Thin;
                style.BorderRight = NPOI.SS.UserModel.BorderStyle.Thin;
                headstyle.BorderBottom = NPOI.SS.UserModel.BorderStyle.Thin;
                headstyle.BorderTop = NPOI.SS.UserModel.BorderStyle.Thin;
                headstyle.BorderLeft = NPOI.SS.UserModel.BorderStyle.Thin;
                headstyle.BorderRight = NPOI.SS.UserModel.BorderStyle.Thin;


                string tablename = "";

                if (datalist.Count == 1)
                {
                    tablename += datalist[0].form_name;
                }
                else
                {
                    tablename += "上报数据导出多个表";
                }


                //循环list
                for (int l = 0; l < datalist.Count; l++)
                {      //先获得层数

                    int c = new UpDataInfoModel().GetFloor(datalist[l].form_id, datalist[l].id);
                    List<FieldInfo> allFiellist = new List<FieldInfo>();
                    List<FieldInfo> mastFieldlist = new List<FieldInfo>();
                    List<FieldInfo> restFieldlist = new List<FieldInfo>();
                    //1.获得数据库的表格信息：tableinfo
                    var tableinfo = db.forms.Where(m => m.deleted == 0 && m.uid == datalist[l].form_id).FirstOrDefault();
                    //2.获得顶级字段列表:mastFieldlist
                    string[] strArray = tableinfo.field_ids.Split(',');
                    int[] intArray = Array.ConvertAll<string, int>(strArray, s => int.Parse(s));
                    foreach (var n in intArray)
                    {
                        var field = db.fields.Where(m => m.deleted == 0 && m.uid == n).Select(m => new FieldInfo
                        {
                            parentid = m.parent_fieldid,
                            name = m.name,
                            uid = m.uid,
                            type = m.type,
                            rowspan = 1,
                            colspan = 1,
                            floor = 1,
                            note = m.note
                        }).FirstOrDefault();
                        allFiellist.Add(field);
                    }
                    int kuan = allFiellist.Where(a => a.type != "p").ToList().Count;

                    mastFieldlist = allFiellist.Where(m => m.parentid == null).ToList();

                    foreach (var n in allFiellist)
                    {
                        if (!mastFieldlist.Contains(n))
                        {
                            restFieldlist.Add(n);
                        }
                    }
                    int y = new TableModel().tabledeep(mastFieldlist, restFieldlist);
                    int deep = 1;
                    foreach (var n in restFieldlist)
                    {
                        if (deep < n.floor)
                            deep = n.floor;
                    }
                    var outprintlist = new TableModel().HandleField(deep, mastFieldlist, restFieldlist);
                    new TableModel().Handlecolspan(mastFieldlist);
                    var list2 = new ExcelModel().setxyofList(0, 0, deep, mastFieldlist, restFieldlist);
                    string sheetname = datalist[l].form_name.ToString();
                    if (sheetname.IndexOf(':') > -1)
                    {
                        sheetname = sheetname.Remove(sheetname.IndexOf(':'), 1);
                    }
                    else
                    {
                        sheetname = sheetname;

                    }
                    System.Web.HttpContext curContext = System.Web.HttpContext.Current;
                    curContext.Response.ContentType = "application/vnd.ms-excel";
                    curContext.Response.ContentEncoding = System.Text.Encoding.UTF8;
                    curContext.Response.Charset = "gb2312";
                    //获得要输出的数据
                    List<UDIM> datalist1 = list.Where(a => a.form_id == datalist[l].form_id).ToList();
                    book.CreateSheet(sheetname + "").DefaultColumnWidth = 18;
                    book.GetSheet(sheetname + "").DefaultRowHeight = 18;
                    book.GetSheet(sheetname).CreateRow(0).CreateCell(0).SetCellValue(sheetname);
                    book.GetSheet(sheetname).AddMergedRegion(new NPOI.SS.Util.CellRangeAddress(0, 1, 0, kuan - 1 + 4));


                    //NPOI.SS.UserModel.ISheet sheet1 = book.CreateSheet(sheetname+"1");
                    new ExcelModel().WriteSingelLine(book.GetSheet(sheetname + ""), list2, restFieldlist, deep);

                    book.GetSheet(sheetname).GetRow(3).CreateCell(0).SetCellValue("调查日期");


                    book.GetSheet(sheetname).GetRow(3).CreateCell(1).SetCellValue("经度");


                    book.GetSheet(sheetname).GetRow(3).CreateCell(2).SetCellValue("纬度");


                    book.GetSheet(sheetname).GetRow(3).CreateCell(3).SetCellValue("单位");
                    book.GetSheet(sheetname).GetRow(3).GetCell(3).CellStyle = style;

                    book.GetSheet(sheetname).AddMergedRegion(new NPOI.SS.Util.CellRangeAddress(3, 3 + c - 1, 0, 0));
                    book.GetSheet(sheetname).AddMergedRegion(new NPOI.SS.Util.CellRangeAddress(3, 3 + c - 1, 1, 1));
                    book.GetSheet(sheetname).AddMergedRegion(new NPOI.SS.Util.CellRangeAddress(3, 3 + c - 1, 2, 2));
                    book.GetSheet(sheetname).AddMergedRegion(new NPOI.SS.Util.CellRangeAddress(3, 3 + c - 1, 3, 3));


                    //new ExcelModel().WriteSingelLine(sheet1, list2, restFieldlist, y);
                    //sheet1.GetRow(3).CreateCell(0).SetCellValue("调查日期");
                    //sheet1.GetRow(3).CreateCell(1).SetCellValue("经度");
                    //sheet1.GetRow(3).CreateCell(2).SetCellValue("纬度");
                    //sheet1.GetRow(3).CreateCell(3).SetCellValue("单位");
                    //sheet1.AddMergedRegion(new NPOI.SS.Util.CellRangeAddress(3, 3 + c - 1, 0, 0));
                    //sheet1.AddMergedRegion(new NPOI.SS.Util.CellRangeAddress(3, 3 + c - 1, 1, 1));
                    //sheet1.AddMergedRegion(new NPOI.SS.Util.CellRangeAddress(3, 3 + c - 1, 2, 2));
                    //sheet1.AddMergedRegion(new NPOI.SS.Util.CellRangeAddress(3, 3 + c - 1, 3, 3));
                    for (int m = 0; m < datalist1.Count; m++)
                    {
                        IRow datarow = book.GetSheet(sheetname + "").CreateRow(3 + c + m);
                        datarow.HeightInPoints = 20;

                        datarow.CreateCell(0).SetCellValue(datalist1[m].watch_time.ToString());
                        datarow.CreateCell(1).SetCellValue(datalist1[m].longitude.ToString());
                        datarow.CreateCell(2).SetCellValue(datalist1[m].latitude.ToString());
                        datarow.CreateCell(3).SetCellValue(datalist1[m].sheng + " " + datalist1[m].shi + " " + datalist1[m].xian + " " + datalist1[m].pointname);
                        List<report_detail> repd = db.report_details.Where(a => a.report_id == datalist1[m].id).ToList();
                        int oldnum = -1;
                        for (int q = 0; q < repd.Count; q++)
                        {
                            for (int w = 0; w < allFiellist.Count; w++)
                            {
                                //找到与字段相等的值得cellnum
                                if (repd[q].field_id == allFiellist[w].uid)
                                {
                                    string strva = "";
                                    if (repd[q].value_integer != null)
                                    {
                                        strva = repd[q].value_integer.ToString();

                                    }
                                    if (repd[q].value_real != null)
                                    {
                                        strva = repd[q].value_real.ToString();

                                    }
                                    if (repd[q].value_text != null)
                                    {
                                        strva = repd[q].value_text.ToString();

                                    }

                                    try
                                    {
                                        datarow.GetCell(4 + allFiellist[w].xset).SetCellValue(strva);
                                    }
                                    catch (System.Exception ex)
                                    {
                                        datarow.CreateCell(4 + allFiellist[w].xset).SetCellValue(strva);
                                    }

                                }
                            }



                        }





                    }

                    CellRangeAddress reg = new CellRangeAddress(3, book.GetSheet(sheetname).LastRowNum, 0, kuan - 1 + 4);
                    for (int i = reg.FirstRow; i <= reg.LastRow; i++)
                    {
                        IRow row = HSSFCellUtil.GetRow(i, (HSSFSheet)book.GetSheet(sheetname));
                        for (int j = reg.FirstColumn; j <= reg.LastColumn; j++)
                        {
                            ICell singleCell = HSSFCellUtil.GetCell(row, (short)j);
                            singleCell.CellStyle = style;
                        }
                    }
                    CellRangeAddress reg1 = new CellRangeAddress(0, 1, 0, kuan - 1 + 4);
                    for (int i = reg1.FirstRow; i <= reg1.LastRow; i++)
                    {
                        IRow row = HSSFCellUtil.GetRow(i, (HSSFSheet)book.GetSheet(sheetname));
                        for (int j = reg1.FirstColumn; j <= reg1.LastColumn; j++)
                        {
                            ICell singleCell = HSSFCellUtil.GetCell(row, (short)j);
                            singleCell.CellStyle = headstyle;
                        }
                    }

                }
                //以4,2为例子












                ////2.获得顶级字段列表:mastFieldlist
                //string[] strArray = tableinfo.field_ids.Split(',');
                //int[] intArray = Array.ConvertAll<string, int>(strArray, s => int.Parse(s));
                //foreach (var n in intArray)
                //{
                //    var field = db.fields.Where(m => m.deleted == 0 && m.uid == n).Select(m => new FieldInfo
                //                                {
                //                                    parentid = m.parent_fieldid,
                //                                    name = m.name,
                //                                    uid = m.uid,
                //                                    type = m.type,
                //                                    rowspan = 1,
                //                                    colspan = 1,
                //                                    floor = 1,
                //                                    note = m.note
                //                                }).FirstOrDefault();
                //    allFiellist.Add(field);
                //}

                //mastFieldlist = allFiellist.Where(m => m.parentid == null).ToList();
                //foreach (var n in allFiellist)
                //{
                //    if (!mastFieldlist.Contains(n))
                //    {
                //        restFieldlist.Add(n);
                //    }
                //}


                //int kuan = allFiellist.Where(a=>a.type!="p").ToList().Count;


                // IRow titlerow = sheet.CreateRow(0);//第0行用于存表名

                // titlerow.CreateCell(0).SetCellValue(tablename);
                // sheet.AddMergedRegion(new NPOI.SS.Util.CellRangeAddress(0,0,0,kuan-1));//合并这个单元格
                // NPOI.SS.UserModel.IRow headerrow = sheet.CreateRow(2);
                // ICellStyle style = book.CreateCellStyle();
                // style.Alignment = HorizontalAlignment.CENTER;
                // style.VerticalAlignment = VerticalAlignment.CENTER;

                //// ICell cell = headerrow.CreateCell(0);
                // //ICell cell = headerrow.CreateCell(1);
                // //3得到顶级列表之后如果不是父字段就用合并单元格
                // for (int i = 0; i < mastFieldlist.Count; i++)
                // {
                //     if (mastFieldlist[i].type != "p")
                //     {
                //         headerrow.CreateCell(i).SetCellValue(mastFieldlist[i].name);
                //         sheet.AddMergedRegion(new NPOI.SS.Util.CellRangeAddress(2, 2+c-1, i, i ));

                //     }
                //     else
                //     {
                //         int cun = 0;
                //         int p=i;
                //         int h = 3;
                //         //找到他的 子字段 在第四行

                //         for (int j = 0; j < restFieldlist.Count;j++ )
                //         {


                //              if (restFieldlist[i].parentid == mastFieldlist[i].uid)
                //              {
                //                  cun++;
                //                  if (cun > 0)
                //                  {
                //                      headerrow.CreateCell(i).SetCellValue(mastFieldlist[i].name);
                //                      sheet.CreateRow(2+1).CreateCell(p).SetCellValue(restFieldlist[j].name);
                //                      p++;
                //                      sheet.AddMergedRegion(new NPOI.SS.Util.CellRangeAddress(2, 2, i, i + cun - 1));
                //                  }


                //              }
                //              else
                //              {

                //                  for (int m = 0; m < restFieldlist.Count; m++)
                //                  {
                //                      if (restFieldlist[i].parentid == restFieldlist[m].uid)
                //                      {



                //                      }


                //                  }

                //              }

                //             //根据cun可以算出合并几个单元格

                //         }
                //     }


                // }Response.AddHeader("Content-Disposition", string.Format("attachment; filename={0}.xls", tablename+ "_" + DateTime.Now.ToString("yyyy-MM-dd")));
                //sheet1.Cells.AddValueCell(1, 3, "这是标题行");
                //sheet.Cells.AddValueCell(i + 2, i + 1, "sss");
                // Response.AppendHeader("Content-Disposition", "attachment;filename=" + "qqqq随意5"+".xls");
                //遍历所有出来标题行的单元格设置边框




                MemoryStream ms = new MemoryStream();
                book.Write(ms);
                Response.ContentEncoding = System.Text.Encoding.UTF8;
                Response.AddHeader("Content-Disposition", string.Format("attachment; filename={0}.xls", HttpUtility.UrlEncode(tablename + "-" + DateTime.Now.ToString("yyyy-MM-dd"), System.Text.Encoding.UTF8)));
                Response.BinaryWrite(ms.ToArray());
                Response.End();
                book = null;
                ms.Close();
                ms.Dispose();

            }
            else
            {
                Response.Write("<script>alert('你选择的条件没有数据可导出，请检查！')</script>");


            }



        }

        public void DownloadTable(long formid)
        {
            BingChongDBDataContext db = new BingChongDBDataContext();
            NPOI.HSSF.UserModel.HSSFWorkbook book = new NPOI.HSSF.UserModel.HSSFWorkbook();
            List<FieldInfo> allFiellist = new List<FieldInfo>();
            List<FieldInfo> mastFieldlist = new List<FieldInfo>();
            List<FieldInfo> restFieldlist = new List<FieldInfo>();
            //1.获得数据库的表格信息：tableinfo
            var tableinfo = db.forms.Where(m => m.deleted == 0 && m.uid == formid).FirstOrDefault();
            //2.获得顶级字段列表:mastFieldlist
            string[] strArray = tableinfo.field_ids.Split(',');
            int[] intArray = Array.ConvertAll<string, int>(strArray, s => int.Parse(s));
            foreach (var n in intArray)
            {
                var field = db.fields.Where(m => m.deleted == 0 && m.uid == n).Select(m => new FieldInfo
                {
                    parentid = m.parent_fieldid,
                    name = m.name,
                    uid = m.uid,
                    type = m.type,
                    rowspan = 1,
                    colspan = 1,
                    floor = 1,
                    note = m.note
                }).FirstOrDefault();
                allFiellist.Add(field);
            }
            int kuan = allFiellist.Where(a => a.type != "p").ToList().Count;

            mastFieldlist = allFiellist.Where(m => m.parentid == null).ToList();
            foreach (var n in allFiellist)
            {
                if (!mastFieldlist.Contains(n))
                {
                    restFieldlist.Add(n);
                }
            }

            int y = new TableModel().tabledeep(mastFieldlist, restFieldlist);
            int deep = 1;
            foreach (var n in restFieldlist)
            {
                if (deep < n.floor)
                    deep = n.floor;
            }
            var outprintlist = new TableModel().HandleField(deep, mastFieldlist, restFieldlist);
            new TableModel().Handlecolspan(mastFieldlist);
            var list2 = new ExcelModel().setxyofList(0, 0, deep, mastFieldlist, restFieldlist);
            string sheetname = tableinfo.name;
            if (sheetname.IndexOf(':') > -1)
            {
                sheetname = sheetname.Remove(sheetname.IndexOf(':'), 1);
            }
            else
            {
                sheetname = sheetname;

            }
            System.Web.HttpContext curContext = System.Web.HttpContext.Current;
            curContext.Response.ContentType = "application/vnd.ms-excel";
            curContext.Response.ContentEncoding = System.Text.Encoding.UTF8;
            curContext.Response.Charset = "gb2312";
            HSSFFont font = (HSSFFont)book.CreateFont();
            HSSFFont headfont = (HSSFFont)book.CreateFont();
            font.FontHeightInPoints = 10;
            font.Boldweight = (short)FontBoldWeight.Bold;
            headfont.FontHeightInPoints = 12;
            headfont.Boldweight = (short)FontBoldWeight.Bold;
            ICellStyle style = (ICellStyle)book.CreateCellStyle();
            ICellStyle headstyle = (ICellStyle)book.CreateCellStyle();
            style.Alignment = HorizontalAlignment.Center;
            style.IsLocked = true;
            headstyle.Alignment = HorizontalAlignment.Center;
            //style.ShrinkToFit = true;
            style.SetFont(font);
            headstyle.SetFont(headfont);
            //style.BorderBottom = NPOI.SS.UserModel.BorderStyle.Thin;
            //style.BorderTop = NPOI.SS.UserModel.BorderStyle.Thin;
            //style.BorderLeft = NPOI.SS.UserModel.BorderStyle.Thin;
            //style.BorderRight = NPOI.SS.UserModel.BorderStyle.Thin;
            //headstyle.BorderBottom = NPOI.SS.UserModel.BorderStyle.Thin;
            //headstyle.BorderTop = NPOI.SS.UserModel.BorderStyle.Thin;
            //headstyle.BorderLeft = NPOI.SS.UserModel.BorderStyle.Thin;
            //headstyle.BorderRight = NPOI.SS.UserModel.BorderStyle.Thin;
            //NPOI.SS.UserModel.ISheet sheet1 = book.CreateSheet(sheetname+"1");
            new ExcelModel().DWriteSingelLine(book.CreateSheet(sheetname), list2, restFieldlist, deep);
            book.GetSheet(sheetname).GetRow(3).CreateCell(0).SetCellValue("调查日期");
            book.GetSheet(sheetname).AddMergedRegion(new NPOI.SS.Util.CellRangeAddress(3, 3 + deep - 1, 0, 0));
            book.GetSheet(sheetname).CreateRow(0).CreateCell(0).SetCellValue(sheetname);
            book.GetSheet(sheetname).AddMergedRegion(new NPOI.SS.Util.CellRangeAddress(0, 1, 0, kuan));
            CellRangeAddress reg = new CellRangeAddress(3, book.GetSheet(sheetname).LastRowNum, 0, kuan);
            for (int i = reg.FirstRow; i <= reg.LastRow; i++)
            {
                IRow row = HSSFCellUtil.GetRow(i, (HSSFSheet)book.GetSheet(sheetname));
                for (int j = reg.FirstColumn; j <= reg.LastColumn; j++)
                {
                    ICell singleCell = HSSFCellUtil.GetCell(row, (short)j);
                    singleCell.CellStyle = style;
                }
            }
            CellRangeAddress reg1 = new CellRangeAddress(0, 1, 0, kuan);
            for (int i = reg1.FirstRow; i <= reg1.LastRow; i++)
            {
                IRow row = HSSFCellUtil.GetRow(i, (HSSFSheet)book.GetSheet(sheetname));
                for (int j = reg1.FirstColumn; j <= reg1.LastColumn; j++)
                {
                    ICell singleCell = HSSFCellUtil.GetCell(row, (short)j);
                    singleCell.CellStyle = headstyle;
                }
            }
            MemoryStream ms = new MemoryStream();
            book.Write(ms);
            Response.AddHeader("Content-Disposition", string.Format("attachment; filename={0}.xls", HttpUtility.UrlEncode(sheetname.Trim() + "-" + DateTime.Now.ToString("yyyy-MM-dd"), System.Text.Encoding.UTF8)));
            Response.BinaryWrite(ms.ToArray());
            Response.End();
            book = null;
            ms.Close();
            ms.Dispose();

        }
        //检测文件是否可以提交
        public int CanImportData(HttpPostedFileBase fileData, String[] myfile)
        {
            BingChongDBDataContext db = new BingChongDBDataContext();

            if (fileData != null)
            {

                try
                {


                    string IsXls = System.IO.Path.GetExtension(fileData.FileName).ToString().ToLower();//System.IO.Path.GetExtension获得文件的扩展名 
                    if (IsXls != ".xls")
                    {
                        if (IsXls != ".xlsx")
                        {
                            Response.Write("<script>alert('只可以选择Excel文件')</script>");
                            //return Json("");
                        }
                    }
                    //先获得层数
                    int c = new UpDataInfoModel().GetFloor(long.Parse(myfile[0]), 2);
                    string fileName = Path.GetFileName(fileData.FileName);// 原始文件名称
                    string fileExtension = Path.GetExtension(fileName); // 文件扩展名
                    string saveName = Guid.NewGuid().ToString() + fileExtension; // 保存文件名称
                    string filePath = Server.MapPath("~/Content/testfile/");
                    fileData.SaveAs(filePath + saveName);
                    Stream stream = new FileStream(filePath + saveName, FileMode.Open, FileAccess.Read);//找到文件

                    HSSFWorkbook workbook = new HSSFWorkbook(stream);//根据文件创建一个表
                    HSSFSheet sheet = (NPOI.HSSF.UserModel.HSSFSheet)workbook.GetSheetAt(0);//读取第一个工作表

                    string tablesheetname = sheet.SheetName;
                    if (!Directory.Exists(filePath))
                    {
                        Directory.CreateDirectory(filePath);
                    }
                    //首先获得这个表格中用于存储值的字段 就是没有子字段 的都是直接存值的
                    List<FieldInfo> allFiellist = new List<FieldInfo>();
                    List<FieldInfo> mastFieldlist = new List<FieldInfo>();
                    List<FieldInfo> restFieldlist = new List<FieldInfo>();
                    //1.获得数据库的表格信息：tableinfo
                    var tableinfo = db.forms.Where(m => m.deleted == 0 && m.uid == long.Parse(myfile[0])).FirstOrDefault();

                    //2.获得顶级字段列表:mastFieldlist
                    string[] strArray = tableinfo.field_ids.Split(',');
                    int[] intArray = Array.ConvertAll<string, int>(strArray, s => int.Parse(s));
                    foreach (var n in intArray)
                    {
                        var field = db.fields.Where(m => m.deleted == 0 && m.uid == n).Select(m => new FieldInfo
                        {
                            parentid = m.parent_fieldid,
                            name = m.name,
                            uid = m.uid,
                            type = m.type,
                            rowspan = 1,
                            colspan = 1,
                            floor = 1,
                            note = m.note
                        }).FirstOrDefault();
                        allFiellist.Add(field);
                    }
                    restFieldlist = allFiellist.Where(a => a.type != "p").ToList();
                    List<ColInfo> colnames = new List<ColInfo>();
                    //对有值的字段进行循环 找到表格中与字段名子相同的标题的列 然后读取这的列号存到list里面
                    for (int i = 0; i < restFieldlist.Count; i++)
                    {    //找到标题们 首先从第四行开始是标题行 那么应该获得3+深度-1
                        for (int j = 3; j <= 3 + c - 1; j++)
                        {
                            HSSFRow row = (NPOI.HSSF.UserModel.HSSFRow)sheet.GetRow(j);

                            for (int m = 0; m < row.LastCellNum; m++)
                            {
                                string cel = row.GetCell(m).StringCellValue;
                                if (cel == restFieldlist[i].name)
                                {
                                    ColInfo childcol = new ColInfo();
                                    childcol.colnum = m;
                                    childcol.titlename = restFieldlist[i].name;
                                    childcol.fileid = restFieldlist[i].uid;
                                    childcol.idtype = restFieldlist[i].type;

                                    colnames.Add(childcol);
                                }
                            }



                        }



                    }

                    string tname = tableinfo.name;
                    if (tname.IndexOf(':') > -1)
                    {
                        tname = tname.Remove(tableinfo.name.IndexOf(':'), 1);
                    }
                    else
                    {
                        tname = tname;

                    }
                    if (restFieldlist.Count == colnames.Count)
                    {
                        if (tname == tablesheetname)//如果导入的sheet名 与数据库里的文件名一样就可以进行插入操作
                        {
                            long uid = long.Parse(myfile[3]);
                            //循环数据行
                            //   DateTime wtime = DateTime.Parse(watchdate);//调查日期
                            //上报时间
                            long blightid = long.Parse(myfile[1]);//病虫害
                            long fid = long.Parse(myfile[0]);//表格
                            long pid = long.Parse(myfile[2]);//测报点

                            for (int i = 3 + c; i <= sheet.LastRowNum; i++)
                            {  //获得列号 以及filedid
                                DateTime repdate = DateTime.Now;
                                ValueInfo valinfo = new ValueInfo();


                                //开始插入数据了
                                //获得用户id 根据id查询出用户级别
                                string insertval;
                                valinfo.wtime = sheet.GetRow(i).GetCell(0).DateCellValue;//调查日期
                                DateTime testdate = valinfo.wtime.AddDays(1);
                                report insertreport = new report();
                                insertreport.blight_id = blightid;
                                insertreport.form_id = fid;
                                insertreport.point_id = pid;
                                insertreport.watch_time = valinfo.wtime;
                                insertreport.report_time = repdate;
                                insertreport.deleted = 0;
                                insertreport.review_state = 1;
                                insertreport.user_id = uid;
                                //db.reports.InsertOnSubmit(insertreport);
                                //db.SubmitChanges();//在上报report表里面插入一个数据
                                for (int j = 0; j < colnames.Count; j++)
                                {//判断值的类型

                                    valinfo.fid = colnames[j].fileid;

                                    if (colnames[j].idtype == "i" || colnames[j].idtype == "r" || colnames[j].idtype == "s")
                                    {

                                        insertval = sheet.GetRow(i).GetCell(colnames[j].colnum).NumericCellValue.ToString();

                                        if (colnames[j].idtype == "i")
                                        {
                                            valinfo.ival = int.Parse(insertval);
                                            //  valinfo.wtime = sheet.GetRow(i).GetCell(0).DateCellValue;//调查日期
                                        }
                                        if (colnames[j].idtype == "r" || colnames[j].idtype == "s")
                                        {
                                            valinfo.rval = double.Parse(insertval);

                                        }
                                    }
                                    if (colnames[j].idtype == "d")
                                    {
                                        insertval = sheet.GetRow(i).GetCell(colnames[j].colnum).DateCellValue.ToString();
                                        DateTime t = DateTime.Parse(insertval).AddDays(1);
                                        valinfo.tval = insertval;
                                    }
                                    if (colnames[j].idtype == "text" || colnames[j].idtype == "c")
                                    {
                                        insertval = sheet.GetRow(i).GetCell(colnames[j].colnum).StringCellValue;

                                        valinfo.tval = insertval;
                                    }
                                    // report backreport = db.reports.Single(a => a.form_id == fid && a.point_id == pid && a.blight_id == blightid && a.deleted == 0 && a.watch_time == valinfo.wtime && a.report_time == repdate);
                                    long backreportid = 2;//找到刚刚上传的数据的id
                                    report_detail reportdetail = new report_detail();
                                    reportdetail.report_id = backreportid;
                                    reportdetail.field_id = valinfo.fid;
                                    reportdetail.deleted = 0;
                                    if (colnames[j].idtype == "i")
                                    {
                                        reportdetail.value_integer = valinfo.ival;
                                    } if (colnames[j].idtype == "r" || colnames[j].idtype == "s")
                                    {
                                        reportdetail.value_real = valinfo.rval;
                                    }
                                    if (colnames[j].idtype == "t" || colnames[j].idtype == "t" || colnames[j].idtype == "d")
                                    {
                                        reportdetail.value_text = valinfo.tval;
                                    }



                                    // db.report_details.InsertOnSubmit(reportdetail);
                                    //  db.SubmitChanges();

                                }


                            }
                            int success = 1;//正常
                            //正常要删除这个文件

                            System.IO.File.Delete(filePath + saveName);
                            return success;
                        }
                        else
                        {
                            int success = 4;//穿错文件了
                            return success;

                        }
                    }
                    else
                    {
                        int success = 5;//名字不相符
                        return success;
                    }






                }
                catch (Exception ex)
                {
                    int success = 2;//异常
                    return success;
                }


            }
            else
            {
                int success = 3;//没有文件
                return success;
            }

        }
        //导入
        public JsonResult ImportData(HttpPostedFileBase fileData, String[] myfile)
        {
            BingChongDBDataContext db = new BingChongDBDataContext();
            if (fileData != null)
            {

                int can = CanImportData(fileData, myfile);
                if (can == 1)
                {
                    try
                    {


                        string IsXls = System.IO.Path.GetExtension(fileData.FileName).ToString().ToLower();//System.IO.Path.GetExtension获得文件的扩展名 
                        if (IsXls != ".xls")
                        {
                            if (IsXls != ".xlsx")
                            {
                                Response.Write("<script>alert('只可以选择Excel文件')</script>");
                                return Json("");
                            }
                        }
                        //先获得层数
                        int c = new UpDataInfoModel().GetFloor(long.Parse(myfile[0]), 2);
                        string fileName = Path.GetFileName(fileData.FileName);// 原始文件名称
                        string fileExtension = Path.GetExtension(fileName); // 文件扩展名
                        string saveName = Guid.NewGuid().ToString() + fileExtension; // 保存文件名称
                        string filePath = Server.MapPath("~/Content/testfile/");
                        fileData.SaveAs(filePath + saveName);
                        Stream stream = new FileStream(filePath + saveName, FileMode.Open, FileAccess.Read);//找到文件

                        HSSFWorkbook workbook = new HSSFWorkbook(stream);//根据文件创建一个表
                        HSSFSheet sheet = (NPOI.HSSF.UserModel.HSSFSheet)workbook.GetSheetAt(0);//读取第一个工作表
                        string tablesheetname = sheet.SheetName;
                        if (!Directory.Exists(filePath))
                        {
                            Directory.CreateDirectory(filePath);
                        }
                        //首先获得这个表格中用于存储值的字段 就是没有子字段 的都是直接存值的
                        List<FieldInfo> allFiellist = new List<FieldInfo>();
                        List<FieldInfo> mastFieldlist = new List<FieldInfo>();
                        List<FieldInfo> restFieldlist = new List<FieldInfo>();
                        //1.获得数据库的表格信息：tableinfo
                        var tableinfo = db.forms.Where(m => m.deleted == 0 && m.uid == long.Parse(myfile[0])).FirstOrDefault();

                        //2.获得顶级字段列表:mastFieldlist
                        string[] strArray = tableinfo.field_ids.Split(',');
                        int[] intArray = Array.ConvertAll<string, int>(strArray, s => int.Parse(s));
                        foreach (var n in intArray)
                        {
                            var field = db.fields.Where(m => m.deleted == 0 && m.uid == n).Select(m => new FieldInfo
                            {
                                parentid = m.parent_fieldid,
                                name = m.name,
                                uid = m.uid,
                                type = m.type,
                                rowspan = 1,
                                colspan = 1,
                                floor = 1,
                                note = m.note
                            }).FirstOrDefault();
                            allFiellist.Add(field);
                        }
                        restFieldlist = allFiellist.Where(a => a.type != "p").ToList();
                        List<ColInfo> colnames = new List<ColInfo>();
                        //对有值的字段进行循环 找到表格中与字段名子相同的标题的列 然后读取这的列号存到list里面
                        for (int i = 0; i < restFieldlist.Count; i++)
                        {    //找到标题们 首先从第四行开始是标题行 那么应该获得3+深度-1
                            for (int j = 3; j <= 3 + c - 1; j++)
                            {
                                HSSFRow row = (NPOI.HSSF.UserModel.HSSFRow)sheet.GetRow(j);

                                for (int m = 0; m < row.LastCellNum; m++)
                                {
                                    string cel = row.GetCell(m).StringCellValue;
                                    if (cel == restFieldlist[i].name)
                                    {
                                        ColInfo childcol = new ColInfo();
                                        childcol.colnum = m;
                                        childcol.titlename = restFieldlist[i].name;
                                        childcol.fileid = restFieldlist[i].uid;
                                        childcol.idtype = restFieldlist[i].type;

                                        colnames.Add(childcol);
                                    }
                                }



                            }



                        }
                        string tname = tableinfo.name;
                        if (tname.IndexOf(':') > -1)
                        {
                            tname = tname.Remove(tableinfo.name.IndexOf(':'), 1);
                        }
                        else
                        {
                            tname = tname;

                        }
                        if (tname == tablesheetname)//如果导入的sheet名 与数据库里的文件名一样就可以进行插入操作
                        {
                            long uid = long.Parse(myfile[3]);
                            //循环数据行
                            //   DateTime wtime = DateTime.Parse(watchdate);//调查日期
                            //上报时间
                            long blightid = long.Parse(myfile[1]);//病虫害
                            long fid = long.Parse(myfile[0]);//表格
                            long pid = long.Parse(myfile[2]);//测报点

                            for (int i = 3 + c; i <= sheet.LastRowNum; i++)
                            {  //获得列号 以及filedid
                                DateTime repdate = DateTime.Now;
                                ValueInfo valinfo = new ValueInfo();


                                //开始插入数据了
                                //获得用户id 根据id查询出用户级别
                                string insertval;
                                valinfo.wtime = sheet.GetRow(i).GetCell(0).DateCellValue;//调查日期
                                report insertreport = new report();
                                insertreport.blight_id = blightid;
                                insertreport.form_id = fid;
                                insertreport.point_id = pid;
                                insertreport.watch_time = valinfo.wtime;
                                insertreport.report_time = repdate;
                                insertreport.deleted = 0;
                                insertreport.review_state = 1;
                                insertreport.user_id = uid;
                                db.reports.InsertOnSubmit(insertreport);
                                db.SubmitChanges();//在上报report表里面插入一个数据
                                for (int j = 0; j < colnames.Count; j++)
                                {//判断值的类型

                                    valinfo.fid = colnames[j].fileid;

                                    if (colnames[j].idtype == "i" || colnames[j].idtype == "r" || colnames[j].idtype == "s")
                                    {

                                        insertval = sheet.GetRow(i).GetCell(colnames[j].colnum).NumericCellValue.ToString();

                                        if (colnames[j].idtype == "i")
                                        {
                                            valinfo.ival = int.Parse(insertval);
                                            //  valinfo.wtime = sheet.GetRow(i).GetCell(0).DateCellValue;//调查日期
                                        }
                                        if (colnames[j].idtype == "r" || colnames[j].idtype == "s")
                                        {
                                            valinfo.rval = double.Parse(insertval);

                                        }
                                    }
                                    if (colnames[j].idtype == "d")
                                    {
                                        insertval = sheet.GetRow(i).GetCell(colnames[j].colnum).DateCellValue.ToString();

                                        valinfo.tval = insertval;
                                    }
                                    if (colnames[j].idtype == "t" || colnames[j].idtype == "c")
                                    {
                                        insertval = sheet.GetRow(i).GetCell(colnames[j].colnum).StringCellValue;

                                        valinfo.tval = insertval;
                                    }
                                    report backreport = db.reports.Single(a => a.form_id == fid && a.point_id == pid && a.blight_id == blightid && a.deleted == 0 && a.watch_time == valinfo.wtime && a.report_time == repdate);
                                    long backreportid = backreport.uid;//找到刚刚上传的数据的id
                                    report_detail reportdetail = new report_detail();
                                    reportdetail.report_id = backreportid;
                                    reportdetail.field_id = valinfo.fid;
                                    reportdetail.deleted = 0;
                                    if (colnames[j].idtype == "i")
                                    {
                                        reportdetail.value_integer = valinfo.ival;
                                    } if (colnames[j].idtype == "r" || colnames[j].idtype == "s")
                                    {
                                        reportdetail.value_real = valinfo.rval;
                                    }
                                    if (colnames[j].idtype == "t" || colnames[j].idtype == "t" || colnames[j].idtype == "d")
                                    {
                                        reportdetail.value_text = valinfo.tval;
                                    }



                                    db.report_details.InsertOnSubmit(reportdetail);
                                    db.SubmitChanges();

                                }


                            }
                            int success = 1;//正常
                            //正常要删除这个文件

                            System.IO.File.Delete(filePath + saveName);
                            return Json(success, JsonRequestBehavior.AllowGet);
                        }
                        else
                        {
                            int success = 4;//穿错文件了
                            return Json(success, JsonRequestBehavior.AllowGet);

                        }





                    }
                    catch (Exception ex)
                    {
                        int success = 2;//异常
                        return Json(success, JsonRequestBehavior.AllowGet);
                    }
                }
                else
                {
                    int success = can;//异常
                    return Json(success, JsonRequestBehavior.AllowGet);
                }



            }
            else
            {
                int success = 3;//没有文件
                return Json(success, JsonRequestBehavior.AllowGet);
            }

        }

        //[Authorize]
        //public DataSet ExcelSqlConnection(string filepath, string tableName, string IsXls)
        //{
        //    string strCon = "";
        //    if (IsXls == ".xls")
        //    {
        //        strCon = "Provider=Microsoft.Jet.OLEDB.4.0;Data Source=" + filepath + ";Extended Properties='Excel 8.0;HDR=YES;IMEX=1'";
        //    }
        //    else
        //    {
        //        strCon = "Provider=Microsoft.ACE.OLEDB.12.0;Data Source=" + filepath + ";Extended Properties='Excel 12.0;HDR=YES;IMEX=1'";
        //    }
        //    OleDbConnection ExcelConn = new OleDbConnection(strCon);
        //    try
        //    {
        //        ExcelConn.Open();
        //        var dtSchema = ExcelConn.GetOleDbSchemaTable(OleDbSchemaGuid.Tables, new object[] { null, null, null, "TABLE" });
        //        string Sheet1 = dtSchema.Rows[0].Field<string>("TABLE_NAME");
        //        string strCom = string.Format("SELECT * FROM [" + Sheet1 + "]");
        //        OleDbDataAdapter myCommand = new OleDbDataAdapter(strCom, ExcelConn);
        //        DataSet ds = new DataSet();
        //        myCommand.Fill(ds, "[" + tableName + "$]");
        //        ExcelConn.Close();
        //        return ds;
        //    }
        //    catch
        //    {
        //        ExcelConn.Close();
        //        return null;
        //    }
        //}
        public JsonResult GetTitleNames(long reportid)
        {
            string name = new UpDataInfoModel().GetTitle(reportid);
            return Json(name, JsonRequestBehavior.AllowGet);


        }

    }
}
