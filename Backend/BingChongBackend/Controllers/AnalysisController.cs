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
    public class AnalysisController : Controller
    {
        //
        // GET: /Analysis/
        [Authorize]
        public ActionResult SummaryStatistics()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            List<sheng> shenglist = new UpDataInfoModel().GetSheng();
            List<shi> shilist = new UpDataInfoModel().GetShi();
            List<xian> xianlist = new UpDataInfoModel().GetXian();
           long userid = CommonModel.GetSessionUserID();//获得用户id 根据id查询出用户级别
            user users = new BingChongDBDataContext().users.Single(a => a.uid == userid);
            string userlevel = users.level.ToString();//获得级别
            List<point> points = new UpDataInfoModel().GetPoint();
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
                points = points.Where(a => a.level == 0 && a.type == 0 && a.shi_id == shiid && a.sheng_id == shengid).ToList();
            }
            //如果是省级
            if (userlevel == "3")
            {
                ViewData["ul"] = "3";
                shenglist = shenglist.Where(a => a.uid == shengid).ToList();
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
            int m2 = DateTime.Now.Month;
            DateTime m1 = DateTime.Now.AddDays(-DateTime.Now.Day + 1).AddHours(-DateTime.Now.Hour).AddMinutes(-DateTime.Now.Minute).AddSeconds(-DateTime.Now.Second);
            DateTime m3 = DateTime.Now.AddHours(-DateTime.Now.Hour).AddMinutes(-DateTime.Now.Minute).AddSeconds(-DateTime.Now.Second);
            string s = m1.ToString("yyyy-MM-dd");
            string s1 = m3.ToString("yyyy-MM-dd");
            ViewData["cumonth"] = s;
            ViewData["today"] = s1;
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Analysis";
            ViewData["level2nav"] = "SummaryStatistics";
            ViewData["role"] = CommonModel.GetUserRoleInfo();
            ViewBag.pointlist = points;
            ViewBag.shenglist = shenglist;
            ViewBag.shilist = shilist;
            ViewBag.xianlist = xianlist;
            return View();
        }
        [Authorize]
        //折线图
        public ActionResult LineChart()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            List<sheng> shenglist = new UpDataInfoModel().GetSheng();
            List<shi> shilist = new UpDataInfoModel().GetShi();
            List<xian> xianlist = new UpDataInfoModel().GetXian();
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
                points = points.Where(a => a.level == 0 && a.type == 0 && a.shi_id == shiid && a.sheng_id == shengid).ToList();
            }
            //如果是省级
            if (userlevel == "3")
            {
                ViewData["ul"] = "3";
                shenglist = shenglist.Where(a => a.uid == shengid).ToList();
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
            int m2 = DateTime.Now.Month;
            DateTime m1 = DateTime.Now.AddDays(-DateTime.Now.Day + 1).AddHours(-DateTime.Now.Hour).AddMinutes(-DateTime.Now.Minute).AddSeconds(-DateTime.Now.Second);
            DateTime m3 = DateTime.Now.AddHours(-DateTime.Now.Hour).AddMinutes(-DateTime.Now.Minute).AddSeconds(-DateTime.Now.Second);
            string s = m1.ToString("yyyy-MM-dd");
            string s1 = m3.ToString("yyyy-MM-dd");
            ViewData["cumonth"] = s;
            ViewData["today"] = s1;
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Analysis";
            ViewData["level2nav"] = "LineChart";
            ViewData["role"] = CommonModel.GetUserRoleInfo();
           // ViewBag.shenglist = shenglist;
            ViewBag.pointlist = points;
            ViewBag.shenglist = shenglist;
            ViewBag.shilist = shilist;
            ViewBag.xianlist = xianlist;
            return View();
        }
        [Authorize]
        //柱状图
        public ActionResult Histogram()
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
                points = points.Where(a => a.level == 0 && a.type == 0 && a.shi_id == shiid && a.sheng_id == shengid).ToList();
            }
            //如果是省级
            if (userlevel == "3")
            {
                ViewData["ul"] = "3";
                shenglist = shenglist.Where(a => a.uid == shengid).ToList();
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
            ViewData["level1nav"] = "Analysis";
            ViewData["level2nav"] = "Histogram";
            ViewData["role"] = roles;
            ViewBag.pointlist = points;
            ViewBag.shenglist = shenglist;
            ViewBag.shilist = shilist;
            ViewBag.xianlist = xianlist;
            return View();
        }

        /*
         * 根据测报点类型和测报点级别查询测报点信息
         */
        public JsonResult FindPoint(String type,String level,String shengid,String shiid,String xianid) 
        {
            List<point> list = new List<point>();
            list = new AnalysisModel().FindPoint(type, level, list,shengid,shiid,xianid);
            return Json(list, JsonRequestBehavior.AllowGet);
        }
        /*
         * 根据表id 查询表里的字段信息
         */
        public JsonResult FindDiseaseField(String uid)
        {
            List<field> list = new List<field>();
            list = new AnalysisModel().FindDiseaseField(uid,list);
            return Json(list, JsonRequestBehavior.AllowGet);
        }

        /*
         * 统计查询功能
         *   
         */
        public JsonResult SearchSummaryStatistics(String starttime, String endtime, String shengid,
            String shiid,String xianid,String point_level,String point_kind,String point_id,
            String disease_kind,String disease_id,String disease_table)
        {
            List<report> list = new List<report>();
            string rst = "";
            rst = new AnalysisModel().GetTableHtml(shengid, shiid, xianid,point_level,point_kind, starttime, endtime, point_id, disease_id, disease_table);
            return Json(rst.Split('|')[0], JsonRequestBehavior.AllowGet);
        }
        /*
       * 折线图统计查询功能
       *   
       */
        public JsonResult SearchLineChart(String starttime, String endtime, String shengid,
            String shiid, String xianid, String point_level, String point_kind, String point_id,
            String disease_kind, String disease_id, String disease_table,String field_id)
        {
            DateTime time1 = DateTime.Parse(starttime);
            DateTime time2 = DateTime.Parse(endtime);
            int day = (time2 - time1).Days+1;
            List<field> list = new List<field>();
            if (field_id == "0")
            {
                list = new AnalysisModel().FindDiseaseIntField(disease_table, list);
            }
            else
            {
                list = new AnalysisModel().FindDiseaseIntFieldById(field_id);
            }
            long shuju1 = 0;
            double shuju2 = 0;
         
            List<List<LineChartInfo>> chart2 = new List<List<LineChartInfo>>();
            for (int i = 0; i <= list.Count; i++)
            {
                List<LineChartInfo> chart1 = new List<LineChartInfo>();
                for (int j = 0; j <= day; j++)
                {
                    shuju1 = 0;
                    shuju2 = 0;
                    LineChartInfo lineinfo = new LineChartInfo();
                    if (j != day)
                {
                    if (i != list.Count)
                {
               
                 if (list.Count>0)
                 {
                     List<report_detail> listdetails = new List<report_detail>();
                     //listdetails = new AnalysisModel().findReportDetails(point_id, shengid, shiid, xianid, point_level, disease_id, disease_table, list[i].uid.ToString(), listdetails, time1.AddDays(j), time1.AddDays(j + 1));
                     listdetails = new AnalysisModel().findReportDetails(point_id, shengid, shiid, xianid, point_level, point_kind, disease_id, disease_table, list[i].uid.ToString(), listdetails, time1.AddDays(j), time1.AddDays(j + 1));
                     for (int k = 0; k < listdetails.Count;k++ )
                     {
                        if (list[i].type == "i")
                         {
                             shuju1+=listdetails[k].value_integer.Value ;
                         }
                         if (list[i].type == "r")
                         {
                             shuju2+=listdetails[k].value_real.Value ;
                         }

                     }
                        if (list[i].type == "i")
                         {
                             lineinfo.value=shuju1.ToString();
                             chart1.Add(lineinfo);
                         }
                         if (list[i].type == "r")
                         {
                           lineinfo.value=shuju2.ToString();
                             chart1.Add(lineinfo);
                         }
                 }
                }
                else
                {

                    lineinfo.value = time1.AddDays(j).ToString("MM-dd");
                    chart1.Add(lineinfo);
                }
                }
                if (j==day)
                {
                    if (i != list.Count)
                    {
                        lineinfo.value = list[i].name;
                        chart1.Add(lineinfo);
                    } if (i == list.Count)
                    {
                        lineinfo.value = "";
                        chart1.Add(lineinfo);
                    }
                        
                }
                }
                chart2.Add(chart1);

            }
            
           
            //rst = new AnalysisModel().SearchLineChart(shengid, shiid, xianid, starttime, endtime, point_id, disease_id, disease_table, field_id);
            return Json(chart2, JsonRequestBehavior.AllowGet);
        }
        /*
      * 柱状图统计查询功能
      *   
      */
        public JsonResult SearchHistoGram(String starttime,  String shengid, String shiid, String xianid, String point_level, 
            String disease_kind, String disease_id, String disease_table, String field_id)
        {
            List<List<LineChartInfo>> chart2 = new List<List<LineChartInfo>>();
           
            if (shengid=="0")
            {
                List<sheng> shenglist = new AnalysisModel().FindSheng();
              //  List<point> pointlist = new AnalysisModel().findDiseasePoint(shengid, shiid, xianid, point_level);
                   
                long shuju1 = 0;
                double shuju2 = 0;
                List<field> list = new List<field>();
                if (field_id == "0")
                {
                    list = new AnalysisModel().FindDiseaseIntField(disease_table, list);
                }
                else
                {
                    list = new AnalysisModel().FindDiseaseIntFieldById(field_id);
                }
           //     List<List<LineChartInfo>> chart2 = new List<List<LineChartInfo>>();
                for (int i = 0; i <= list.Count; i++)
                {
                    List<LineChartInfo> chart1 = new List<LineChartInfo>();
                    for (int j = 0; j <= shenglist.Count; j++)
                    {
                        shuju1 = 0;
                        shuju2 = 0;
                        LineChartInfo lineinfo = new LineChartInfo();
                        if (j != shenglist.Count)
                        {
                            if (i != list.Count)
                            {

                                if (list.Count > 0)
                                {
                                    List<report_detail> listdetails = new List<report_detail>();
                                    listdetails = new AnalysisModel().findShengReportDetails1(shenglist[j].uid,point_level,  disease_id, disease_table, list[i].uid.ToString(), listdetails, DateTime.Parse(starttime), DateTime.Parse(starttime).AddDays(1));
                                    for (int k = 0; k < listdetails.Count; k++)
                                    {
                                        if (list[i].type == "i")
                                        {
                                            shuju1 += listdetails[k].value_integer.Value;
                                        }
                                        if (list[i].type == "r")
                                        {
                                            shuju2 += listdetails[k].value_real.Value;
                                        }

                                    }
                                    if (list[i].type == "i")
                                    {
                                        lineinfo.value = shuju1.ToString();
                                        chart1.Add(lineinfo);
                                    }
                                    if (list[i].type == "r")
                                    {
                                        lineinfo.value = shuju2.ToString();
                                        chart1.Add(lineinfo);
                                    }
                                }
                            }
                            else
                            {

                                lineinfo.value = shenglist[j].name;
                                chart1.Add(lineinfo);
                            }
                        }
                        if (j == shenglist.Count)
                        {
                            if (i != list.Count)
                            {
                                lineinfo.value = list[i].name;
                                chart1.Add(lineinfo);
                            } if (i == list.Count)
                            {
                                lineinfo.value = "";
                                chart1.Add(lineinfo);
                            }

                        }
                    }
                    chart2.Add(chart1);

                }
            }else{
                if (shiid == "0")
                {
                    List<shi> shilist = new AnalysisModel().FindShi(shengid);
                    long shuju1 = 0;
                    double shuju2 = 0;
                    List<field> list = new List<field>();
                    if (field_id == "0")
                    {
                        list = new AnalysisModel().FindDiseaseIntField(disease_table, list);
                    }
                    else
                    {
                        list = new AnalysisModel().FindDiseaseIntFieldById(field_id);
                    }
                  //  List<List<LineChartInfo>> chart2 = new List<List<LineChartInfo>>();
                    for (int i = 0; i <= list.Count; i++)
                    {
                        List<LineChartInfo> chart1 = new List<LineChartInfo>();
                        for (int j = 0; j <= shilist.Count; j++)
                        {
                            shuju1 = 0;
                            shuju2 = 0;
                            LineChartInfo lineinfo = new LineChartInfo();
                            if (j != shilist.Count)
                            {
                                if (i != list.Count)
                                {

                                    if (list.Count > 0)
                                    {
                                        List<report_detail> listdetails = new List<report_detail>();
                                        listdetails = new AnalysisModel().findShiReportDetails1(shilist[j].uid, point_level,disease_id, disease_table, list[i].uid.ToString(), listdetails, DateTime.Parse(starttime), DateTime.Parse(starttime).AddDays(1));
                                        for (int k = 0; k < listdetails.Count; k++)
                                        {
                                            if (list[i].type == "i")
                                            {
                                                shuju1 += listdetails[k].value_integer.Value;
                                            }
                                            if (list[i].type == "r")
                                            {
                                                shuju2 += listdetails[k].value_real.Value;
                                            }

                                        }
                                        if (list[i].type == "i")
                                        {
                                            lineinfo.value = shuju1.ToString();
                                            chart1.Add(lineinfo);
                                        }
                                        if (list[i].type == "r")
                                        {
                                            lineinfo.value = shuju2.ToString();
                                            chart1.Add(lineinfo);
                                        }
                                    }
                                }
                                else
                                {

                                    lineinfo.value = shilist[j].name;
                                    chart1.Add(lineinfo);
                                }
                            }
                            if (j == shilist.Count)
                            {
                                if (i != list.Count)
                                {
                                    lineinfo.value = list[i].name;
                                    chart1.Add(lineinfo);
                                } if (i == list.Count)
                                {
                                    lineinfo.value = "";
                                    chart1.Add(lineinfo);
                                }

                            }
                        }
                        chart2.Add(chart1);

                    }
                }
                else
                {
                    if (xianid == "0")
                    {
                        List<xian> xianlist = new AnalysisModel().FindXian(shiid);
                        long shuju1 = 0;
                        double shuju2 = 0;
                        List<field> list = new List<field>();
                        if (field_id == "0")
                        {
                            list = new AnalysisModel().FindDiseaseIntField(disease_table, list);
                        }
                        else
                        {
                            list = new AnalysisModel().FindDiseaseIntFieldById(field_id);
                        }
                   //     List<List<LineChartInfo>> chart2 = new List<List<LineChartInfo>>();
                        for (int i = 0; i <= list.Count; i++)
                        {
                            List<LineChartInfo> chart1 = new List<LineChartInfo>();
                            for (int j = 0; j <= xianlist.Count; j++)
                            {
                                shuju1 = 0;
                                shuju2 = 0;
                                LineChartInfo lineinfo = new LineChartInfo();
                                if (j != xianlist.Count)
                                {
                                    if (i != list.Count)
                                    {

                                        if (list.Count > 0)
                                        {
                                            List<report_detail> listdetails = new List<report_detail>();
                                            listdetails = new AnalysisModel().findXianReportDetails1(xianlist[j].uid,point_level, disease_id, disease_table, list[i].uid.ToString(), listdetails, DateTime.Parse(starttime), DateTime.Parse(starttime).AddDays(1));
                                            for (int k = 0; k < listdetails.Count; k++)
                                            {
                                                if (list[i].type == "i")
                                                {
                                                    shuju1 += listdetails[k].value_integer.Value;
                                                }
                                                if (list[i].type == "r")
                                                {
                                                    shuju2 += listdetails[k].value_real.Value;
                                                }

                                            }
                                            if (list[i].type == "i")
                                            {
                                                lineinfo.value = shuju1.ToString();
                                                chart1.Add(lineinfo);
                                            }
                                            if (list[i].type == "r")
                                            {
                                                lineinfo.value = shuju2.ToString();
                                                chart1.Add(lineinfo);
                                            }
                                        }
                                    }
                                    else
                                    {

                                        lineinfo.value = xianlist[j].name;
                                        chart1.Add(lineinfo);
                                    }
                                }
                                if (j == xianlist.Count)
                                {
                                    if (i != list.Count)
                                    {
                                        lineinfo.value = list[i].name;
                                        chart1.Add(lineinfo);
                                    } if (i == list.Count)
                                    {
                                        lineinfo.value = "";
                                        chart1.Add(lineinfo);
                                    }

                                }
                            }
                            chart2.Add(chart1);

                        }
                    }
                    else
                    {
                        //// List<point> shenglist = new AnalysisModel().FindPointAll();
                        List<point> pointlist = new AnalysisModel().findDiseasePointAll(shengid, shiid, xianid, point_level);
                        long shuju1 = 0;
                        double shuju2 = 0;

                        List<field> list = new List<field>();
                        if (field_id == "0")
                        {
                            list = new AnalysisModel().FindDiseaseIntField(disease_table, list);
                        }
                        else
                        {
                            list = new AnalysisModel().FindDiseaseIntFieldById(field_id);
                        }
                       
                        for (int i = 0; i <= list.Count; i++)
                        {
                            List<LineChartInfo> chart1 = new List<LineChartInfo>();
                            for (int j = 0; j <= pointlist.Count; j++)
                            {
                                shuju1 = 0;
                                shuju2 = 0;
                                LineChartInfo lineinfo = new LineChartInfo();
                                if (j != pointlist.Count)
                                {
                                    if (i != list.Count)
                                    {

                                        if (list.Count > 0)
                                        {
                                            List<report_detail> listdetails = new List<report_detail>();
                                           // listdetails = new AnalysisModel().findReportDetails1(shenglist[j].uid, point_level, disease_id, disease_table, list[i].uid.ToString(), listdetails, DateTime.Parse(starttime), DateTime.Parse(starttime).AddDays(1));
                                            listdetails = new AnalysisModel().findPointReportDetails1(pointlist[j].uid, point_level, disease_id, disease_table, list[i].uid.ToString(), listdetails, DateTime.Parse(starttime), DateTime.Parse(starttime).AddDays(1));
                                            for (int k = 0; k < listdetails.Count; k++)
                                            {
                                                if (list[i].type == "i")
                                                {
                                                    shuju1 += listdetails[k].value_integer.Value;
                                                }
                                                if (list[i].type == "r")
                                                {
                                                    shuju2 += listdetails[k].value_real.Value;
                                                }

                                            }
                                            if (list[i].type == "i")
                                            {
                                                lineinfo.value = shuju1.ToString();
                                                chart1.Add(lineinfo);
                                            }
                                            if (list[i].type == "r")
                                            {
                                                lineinfo.value = shuju2.ToString();
                                                chart1.Add(lineinfo);
                                            }
                                        }
                                    }
                                    else
                                    {

                                        lineinfo.value = pointlist[j].name;
                                        chart1.Add(lineinfo);
                                    }
                                }
                                if (j == pointlist.Count)
                                {
                                    if (i != list.Count)
                                    {
                                        lineinfo.value = list[i].name;
                                        chart1.Add(lineinfo);
                                    } if (i == list.Count)
                                    {
                                        lineinfo.value = "";
                                        chart1.Add(lineinfo);
                                    }

                                }
                            }
                            chart2.Add(chart1);

                        }
                    }
                }
            }
       
     


            //rst = new AnalysisModel().SearchLineChart(shengid, shiid, xianid, starttime, endtime, point_id, disease_id, disease_table, field_id);
            return Json(chart2, JsonRequestBehavior.AllowGet);
        }

        public JsonResult Findshi(String shengid)
        {
            List<shi> list = new List<shi>();
            list = new PointModel().Findshi(long.Parse(shengid));
            return Json(list, JsonRequestBehavior.AllowGet);
        }

        public JsonResult Findxian(String shengid,String shiid)
        {
            List<xian> list = new List<xian>();
            list = new AnalysisModel().Findxian(shengid, long.Parse(shiid));
            return Json(list, JsonRequestBehavior.AllowGet);
        }

        /*
        * 导出统计信息
        */
        public ActionResult GetPgWord(String starttime, String endtime, String shengid,
            String shiid,String xianid,String point_level,String point_kind,String point_id,
            String disease_kind,String disease_id,String disease_table, String Dfilename)
        {
            String name = new AnalysisModel().FindTableByid(disease_table);
            List<report> list = new List<report>();
            string rst = "";
            rst = new AnalysisModel().GetTableHtml(shengid, shiid, xianid,point_level,point_kind, starttime, endtime, point_id, disease_id, disease_table);
            int num = int.Parse(rst.Split('|')[1]);
            rst = rst.Split('|')[0];
            //根据meetingid 查到会议表详情
            //    long meeting_id = long.Parse(meetingid);
            String  meeting = "<html>"
                    + "<head>"
                    + "<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
                    + "</head>"
                    + "<body>"
                    + "<table style='border:1px solid #DDD;font-size:18px'><tr><td align='center' valign=middle' colspan='" + num + "'style='height:45px;'>" + name + "统计分析</td></tr>";
            rst = meeting + rst;

            form frms = new AnalysisModel().FindForms(disease_table);
           
            //System.Data.DataTable dtData = da.gettable();

            System.Web.UI.WebControls.DataGrid dgExport = null;
            // 当前对话 
            System.Web.HttpContext curContext = System.Web.HttpContext.Current;
            // IO用于导出并返回excel文件 
            System.IO.StringWriter strWriter = null;
            System.Web.UI.HtmlTextWriter htmlWriter = null;
            string filename = frms.name + "统计表" + DateTime.Now.Month + "月" + DateTime.Now.Day + "日"
                + DateTime.Now.Hour + "时" + DateTime.Now.Minute + "分";
            byte[] str = null;
            curContext.Response.ContentType = "application/vnd.ms-excel";
            Response.AppendHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
            // 导出excel文件 
            //设置输出的HTTP MIME类型
            // Response.ContentType = FileType;


            rst += "</table ></body></html>";
            System.Text.StringBuilder sb = new System.Text.StringBuilder();

            sb.Append(rst);

            //把字符数组写入HTTP响应输出流
            Response.Write(sb.ToString());
            //发送完，关闭
            Response.End();

            /*    strWriter = new System.IO.StringWriter();
                htmlWriter = new System.Web.UI.HtmlTextWriter(strWriter);
               // System.Web.UI.WebControls.TextBox dgExport1 = new System.Web.UI.WebControls.TextBox();
               // dgExport1.Text = meeting;
               // dgExport1.ReadOnly = false;
               // dgExport1.Width = 100;
               // dgExport1.RenderControl(htmlWriter);
                // 为了解决dgData中可能进行了分页的情况，需要重新定义一个无分页的DataGrid 
                dgExport = new System.Web.UI.WebControls.DataGrid();
              //  dgExport.DataSource = dtData;
                dgExport.AllowPaging = false;
                dgExport.DataBind();
                dgExport.Font.Size = 14;
                dgExport.HeaderStyle.Font.Bold = true;
                dgExport.RenderControl(htmlWriter);
                // 返回客户端 
                str = System.Text.Encoding.Unicode.GetBytes(strWriter.ToString());
          */

            return File(str, "attachment;filename=" + filename + ".xls");

        }
            /*
        * 导出统计信息
        */
        public ActionResult GetFieldWord(String starttime, String endtime, String shengid,
            String shiid, String xianid, String point_level, String point_kind, String point_id,
            String disease_kind, String disease_id, String disease_table, String Dfilename)
        {
            String name = new AnalysisModel().FindTableByid(disease_table);
            List<report> list = new List<report>();
            string rst = "";
            rst = new AnalysisModel().GetAllHtml(shengid, shiid, xianid, point_level, point_kind, starttime, endtime, point_id, disease_id, disease_table);
           // int num = int.Parse(rst.Split('|')[1]);
           // rst = rst.Split('|')[0];
            String meeting = "<html>"
                    + "<head>"
                    + "<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
                    + "</head>"
                    + "<body>"
                    + "<table style='border:1px solid #DDD;font-size:18px'>";
            rst = meeting + rst;

            form frms = new AnalysisModel().FindForms(disease_table);

            //System.Data.DataTable dtData = da.gettable();

            System.Web.UI.WebControls.DataGrid dgExport = null;
            // 当前对话 
            System.Web.HttpContext curContext = System.Web.HttpContext.Current;
            // IO用于导出并返回excel文件 
            System.IO.StringWriter strWriter = null;
            System.Web.UI.HtmlTextWriter htmlWriter = null;
            string filename = frms.name +"基数调差统计表"+ DateTime.Now.Month + "月" + DateTime.Now.Day + "日"
                + DateTime.Now.Hour + "时" + DateTime.Now.Minute + "分";
            byte[] str = null;
            curContext.Response.ContentType = "application/vnd.ms-excel";
            Response.AppendHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
            // 导出excel文件 
            //设置输出的HTTP MIME类型
            // Response.ContentType = FileType;


            rst += "</table ></body></html>";
            System.Text.StringBuilder sb = new System.Text.StringBuilder();

            sb.Append(rst);

            //把字符数组写入HTTP响应输出流
            Response.Write(sb.ToString());
            //发送完，关闭
            Response.End();

            return File(str, "attachment;filename=" + filename + ".xls");

        }
    }
    public class LineChartInfo
    {
        public string value { get; set; }
    }
}
