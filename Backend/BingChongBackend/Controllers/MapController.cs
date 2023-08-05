using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using BingChongBackend.Models;

namespace BingChongBackend.Controllers
{
    public class MapController : Controller
    {
        //
        // GET: /Map/
        [Authorize]
        public ActionResult Map()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Map";
            ViewData["level2nav"] = "Map";
            var role = CommonModel.GetUserRoleInfo();
            ViewData["seepoint"] = 0;
            ViewData["seewatcher"] = 0;
            ViewData["seewatchertrack"] = 0;
            if (role != null && ((string)role).Contains("ViewMapPoint"))//查看测报点
            {
                ViewData["seepoint"] =1;
            }
            if (role != null && ((string)role).Contains("ViewMapWatcher"))//查看测报员
            {
                ViewData["seewatcher"] = 1;
            }
            if (role != null && ((string)role).Contains("ViewMapWatcherTrack"))//查看测报员轨迹
            {
                ViewData["seewatchertrack"] = 1;
            }
            ViewBag.shengc = new PointModel().GetAllSheng();
            user list2 = new PointModel().GetUserRoleInfo();
            ViewData["level"] = list2.level;
            ViewData["sheng"] = list2.sheng_id;
            ViewData["shi"] = list2.shi_id;
            ViewData["xian"] = list2.xian_id;
            return View();
        }
        [Authorize]
        public ActionResult MapChat()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Map";
            ViewData["level2nav"] = "Map";
            user list = new PointModel().GetUserRoleInfo();
            ViewData["username"] = list.name;
            ViewData["msg"]="";
            String str = Request.QueryString["msg"];
            if (str == null || str == "") { ViewData["msg"] = ""; }
            else { ViewData["msg"] =str; }
            return View();
        }
        [Authorize]
        public JsonResult FintPoint(Byte sort, Byte style1, long sheng, long shi, long xian,int level)
        {
            List<StationPoint> list = new Map().FintPoint1(sort, style1, sheng, shi, xian, level);
           
            return Json(list , JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        public JsonResult GetWatcher(long sheng, long shi, long xian, string starttime, int level)
        {
            List<List<WatchUser>> list = new Map().GetWatcher(sheng, shi, xian, Convert.ToDateTime(starttime));
           

            return Json(list, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        public JsonResult GetUnFinish(Byte sort, Byte style1, long sheng, long shi, long xian, long point1, string starttime, int level)
        {
            List<WatchPoint> list = new Map().FintPoint(sort, style1, sheng, shi, xian, point1, level);
            List<WatchPoint> list1 = new List<WatchPoint>();
            if (list.Count > 0)
            {
                foreach (WatchPoint a in list)
                {
                    List<task_detail> ll = new Map().GetPointTask(a.id);
                    int num = new Map().Getufinish(ll, Convert.ToDateTime(starttime),a.id);
                    if (num != 0)
                    {
                        a.num = num;
                        list1.Add(a);
                    }
                }
            }
            return Json(list1, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        public JsonResult GetFinish(Byte sort, Byte style1, long sheng, long shi, long xian, long point1, string starttime, int level)
        {
            List<WatchPoint> list = new Map().FintPoint(sort, style1, sheng, shi, xian, point1, level);

            List<WatchPoint> list1 = new List<WatchPoint>();
            if (list.Count > 0)
            {
                foreach (WatchPoint a in list)
                {
                    List<task_detail> ll = new Map().GetPointTask(a.id);
                    int num = new Map().GetFinish(ll, Convert.ToDateTime(starttime),a.id);
                    if (num == 1)
                    {
                        a.num = num;
                        list1.Add(a);
                    }
                }
            }
            
            return Json(list1, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        public JsonResult GetEmpty(Byte sort, Byte style1, long sheng, long shi, long xian, long point1, string starttime, int level)
        {
            List<WatchPoint> list = new Map().FintPoint(sort, style1, sheng, shi, xian, point1, level);
            List<WatchPoint> list1 = new List<WatchPoint>();
            if (list.Count > 0)
            {
                foreach (WatchPoint a in list)
                {
                    List<task_detail> ll = new Map().GetPointTask(a.id);
                    int num = new Map().GetEmpty(ll, Convert.ToDateTime(starttime));
                    if (num == 1)
                    {
                        a.num = num;
                        list1.Add(a);
                    }
                }
            }
            return Json(list1, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        public JsonResult Getcolor(int num)
        {
           List<string> list=new List<string>();
           int k = 0;
           GetoneColor(list, num,k);


            return Json(list, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        public void GetoneColor(List<string> list,int num,int k)
        {
            string le = "0 1 2 3 4 5 6 7 8 9 A B C D E F";
            string[] letters = le.Split(' ');
            Random rnd = new Random();
            for (int j = k; j < num; j++)
            {
                string color = "#";
                for (var i = 0; i < 6; i++)
                {
                    int b = rnd.Next(16);
                    color += letters[b];
                    
                }
                if (!list.Contains(color))
                {
                    list.Add(color);
                }
                else
                {
                    k = j;
                    GetoneColor(list, num, k);
                }
            
            }
           
            
        }
        [Authorize]
        public JsonResult GetUserList()
        {
            BingChongBackend.shipin.shipinSoapClient c = new BingChongBackend.shipin.shipinSoapClient();
            var returnrst = c.BCUserlist("lvyunxinxi2014");
            List<long> idlist = new List<long>();
            for (int i = 0; i < returnrst.Length;i++ )
            {
                idlist.Add(returnrst[i].id);
            }
            return Json(idlist, JsonRequestBehavior.AllowGet);
        }
    }
}
