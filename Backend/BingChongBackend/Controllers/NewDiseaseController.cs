using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using BingChongBackend.Models;

namespace BingChongBackend.Controllers
{
    public class NewDiseaseController : Controller
    {
        //
        // GET: /NewDisease/S
        [Authorize]
        public ActionResult NewDisease()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            List<sheng> shenglist = new UpDataInfoModel().GetSheng();
            List<shi> shilist = new UpDataInfoModel().GetShi();
            List<xian> xianlist = new UpDataInfoModel().GetXian();
            int m2 = DateTime.Now.Month;
            DateTime m1 = DateTime.Now.AddDays(-DateTime.Now.Day + 1).AddHours(-DateTime.Now.Hour).AddMinutes(-DateTime.Now.Minute).AddSeconds(-DateTime.Now.Second);
            DateTime m3 = DateTime.Now.AddHours(-DateTime.Now.Hour).AddMinutes(-DateTime.Now.Minute).AddSeconds(-DateTime.Now.Second);
            long userid = CommonModel.GetSessionUserID();//获得用户id 根据id查询出用户级别
            user users = new BingChongDBDataContext().users.Single(a => a.uid == userid);
            List<point> points = new UpDataInfoModel().GetPoint();
            string userlevel = users.level.ToString();//获得级别
            long? shengid = users.sheng_id;
            long? shiid = users.shi_id;
            long? xianid = users.xian_id;
            long rid = users.right_id;
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
                xianlist = xianlist.Where(a => a.deleted == 0 && a.shi_id == shiid).ToList();
                points = points.Where(a => a.level == 0 && a.type == 0 && a.shi_id == shiid && a.sheng_id == shengid).ToList();
            }
            //如果是省级
            if (userlevel == "3")
            {
                ViewData["ul"] = "3";
                shenglist = shenglist.Where(a => a.uid == shengid).ToList();
                shilist = shilist.Where(a => a.deleted == 0 && a.sheng_id == shengid).ToList();
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
            ViewData["level"] = users.level;
            string s = m1.ToString("yyyy-MM-dd");
            string s1 = m3.ToString("yyyy-MM-dd");
            ViewData["cumonth"] = s;
            ViewData["today"] = s1;
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "NewDisease";
            ViewData["level2nav"] = "NewDisease";
            ViewData["role"] = roles;
            ViewBag.pointlist = points;
            ViewBag.shenglist = shenglist;
            ViewBag.shilist = shilist;
            ViewBag.xianlist = xianlist;
           
            return View();
        }

        public ActionResult ViewNewDiseasePest(String uid)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "NewDisease";
            ViewData["level2nav"] = "ViewNewDiseasePest";
            NewDiseaseInfo1 info = new NewDiseaseModel().FindNewById(uid);
             ViewBag.info = info;
            return View();
        }
        /*
         * 查询新病虫害的信息
         */
        public JsonResult FindNewDiseasePest(String type, string year,String status, String shengid, String shiid, String xianid ,int page,int rows)
        {
            List<NewDiseaseInfo> list = new List<NewDiseaseInfo>();
            list = new NewDiseaseModel().FindNewDiseasePest( type,  year, status,  shengid,  shiid,  xianid , page, rows);
            List<NewDiseaseInfo1> list1 = new List<NewDiseaseInfo1>();
            if (list.Count>0)
            {
                list1 = new NewDiseaseModel().GetNewDiseaseinfo(list, list1);
            }
           
            int record = new NewDiseaseModel().FindNewDiseaseRecord(type, year, status, shengid, shiid, xianid);
            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }
            return Json(new { page = page, total = total, records = record, rows = list1 }, JsonRequestBehavior.AllowGet);
        }

    }
}
