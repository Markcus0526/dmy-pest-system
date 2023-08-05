using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using BingChongBackend.Models;
using BingChongBackend.Models.Library;
//using FSSite.Models;
using System.Data;
using System.IO;
using System.Data.OleDb;

namespace BingChongBackend.Controllers
{
    public class StationController : Controller
    {
        //
        // GET: /Station/
        [Authorize]
        public ActionResult StationManage()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "StationManage";
            ViewData["level2nav"] = "StationManage";
            ViewBag.shengc = new PointModel().GetAllSheng();
            user list1 = new PointModel().GetUserRoleInfo();
            ViewData["level"] = list1.level;
            ViewData["sheng1"] = list1.sheng_id;
            ViewData["shi1"] = list1.shi_id;
            ViewData["xian1"] = list1.xian_id;
            return View();
        }
        [Authorize]
        public ActionResult SeeStation(int makeid,long pointid)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "StationManage";
            ViewData["level2nav"] = "StationManage";
            ViewBag.shengc = new PointModel().GetAllSheng();
            user list1 = new PointModel().GetUserRoleInfo();
            ViewData["level"] = list1.level;
            ViewData["sheng1"] = list1.sheng_id;
            ViewData["shi1"] = list1.shi_id;
            ViewData["xian1"] = list1.xian_id;
            List<StationPoint> list = new PointModel().ShowPoint(pointid);
            ViewData["makeid"]=makeid;
            ViewData["uid"]=list[0].id;
            ViewData["sort"]=list[0].level;
            ViewData["style1"]=list[0].type;
            ViewData["sheng"]=list[0].shengid;
            ViewData["shi"]=list[0].shiid;
            ViewData["xian"]=list[0].xianid;
            ViewData["pointname"]=list[0].name;
            ViewData["pointcode"]=list[0].nickname;
            ViewData["dongjing"]=list[0].longitude;
            ViewData["beiwei"]=list[0].latitude;
            ViewData["fieldarea"]=list[0].info1;
            ViewData["daibiaoarea"]=list[0].info2;
            ViewData["crop"]=list[0].info3;
            ViewData["xiang"]=list[0].info4;
            ViewData["cun"]=list[0].info5;
            ViewData["pobject"]=list[0].info6;
            ViewData["detail"] = list[0].note;
            return View();
        }

        [Authorize]
        public ActionResult AddStation()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "StationManage";
            ViewData["level2nav"] = "StationManage";
            ViewBag.shengc = new PointModel().GetAllSheng();
            user list = new PointModel().GetUserRoleInfo();
            ViewData["level"] = list.level;
            ViewData["sheng"] = list.sheng_id;
            ViewData["shi"] = list.shi_id;
            ViewData["xian"] = list.xian_id;
            return View();
        }
        [Authorize]
        [HttpPost]
        [AjaxOnly]
        public JsonResult Findshi(long shengid)
        {
            List<shi> list = new List<shi>();
            list = new PointModel().Findshi(shengid);
            return Json(list, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        [HttpPost]
        [AjaxOnly]
        public JsonResult Findxian(long shiid)
        {
            List<xian> list = new List<xian>();
            list = new PointModel().Findxian(shiid);
            return Json(list, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        [HttpPost]
        public JsonResult InserPoint(Byte sort, Byte style1, long sheng, long shi, long xian, string pointname, string pointcode, float dongjing, float beiwei,
            string fieldarea,string daibiaoarea,string crop,string xiang,string cun,string pobject,string detail)
        {
            bool success = false;
            success = new PointModel().InsertPoint(sort,style1,sheng,shi,xian,pointname,pointcode,dongjing,beiwei,fieldarea,daibiaoarea,crop,xiang,cun,pobject,detail);
            return Json(success, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        [HttpPost]
        public JsonResult UpdatePoint(long uid, Byte sort, Byte style1, long sheng, long shi, long xian, string pointname, string pointcode, float dongjing, float beiwei,
            string fieldarea, string daibiaoarea, string crop, string xiang, string cun, string pobject, string detail)
        {
            bool success = false;
            success = new PointModel().UpdatePoint(uid, sort, style1, sheng, shi, xian, pointname, pointcode, dongjing, beiwei, fieldarea, daibiaoarea, crop, xiang, cun, pobject, detail);
            return Json(success, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        [HttpPost]
        [AjaxOnly]
        public JsonResult Deletepoints(string uids)
        {
            String[] ids = uids.Split(',');
            long[] s = new long[ids.Length - 1];
            for (int i = 0; i < ids.Length - 1; i++)
            {
                s[i] = Convert.ToInt64(ids[i]);
            }
            bool success = new PointModel().Deletepoints(s);
            return Json(success, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        [HttpPost]
        [AjaxOnly]
        public JsonResult Deletepoint(string uids)
        {
            long[] s = new long[1];
            s[0] = Convert.ToInt64(uids);
            bool success = new PointModel().Deletepoint(Convert.ToInt64(uids));
            return Json(success, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        public JsonResult FintPoint(Byte sort, Byte style1, long sheng, long shi, long xian, string pointname,int rows,int page)
        {
            List<StationPoint> list = new List<StationPoint>();
            list = new PointModel().FintPoint(sort, style1, sheng, shi, xian, pointname,rows,page);
            List<StationPoint> list1 = new PointModel().FintPoint1(sort, style1, sheng, shi, xian, pointname);
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
        public JsonResult GetSamePoint(String dongjing,string beiwei)
        {
            bool success = false;
            success = new PointModel().GetSamePoint(dongjing, beiwei);
            return Json(success, JsonRequestBehavior.AllowGet);
        }
        [Authorize]
        public JsonResult GetPcode(Byte type1, Byte style1, long xianid)
        {
            List<xian> list = new List<xian>();
            long mpoint = new PointModel().Getmponit();
            list = new PointModel().GetPcode(xianid);
            string ty = "";
            if (type1 == 0)
            {
                ty ="G";
            }
            if (type1 == 1)
            {
                ty = "P";
            }
            if (type1 == 2)
            {
                ty = "S";
            }
            if (type1 == 3)
            {
                ty = "X";
            }
            string st = "";
            if (style1 == 0)
            {
                st = "G";
            }
            if (style1 == 1)
            {
                st = "F";
            }
            string pcode = "";
            if (list.Count>0)
            {
               pcode = st + ty + list[0].postcode + mpoint;
            }
            if (list.Count==0)
            {
                pcode = "1";
            }
            return Json(pcode, JsonRequestBehavior.AllowGet);
        }
        
        public void GetPgExcel(int d, String pfilename, String uids)
        {
            String[] ids = uids.Split(',');
            long[] s = new long[ids.Length - 1];
            for (int i = 0; i < ids.Length - 1; i++)
            {
                s[i] = Convert.ToInt64(ids[i]);
            }

            List<StationPoint> list = new List<StationPoint>();
            list =new PointModel().GetImportP(s);
            ListToDatatable ltd = new ListToDatatable();
            DataTable dtData = ltd.listToDatatable(list, d);
           
            System.Web.UI.WebControls.DataGrid dgExport = null;
            // 当前对话 
            System.Web.HttpContext curContext = System.Web.HttpContext.Current;
            // IO用于导出并返回excel文件 
            System.IO.StringWriter strWriter = null;
            System.Web.UI.HtmlTextWriter htmlWriter = null;
            string filename = pfilename + DateTime.Now.Month + "月" + DateTime.Now.Day + "日"
                + DateTime.Now.Hour + "时" + DateTime.Now.Minute + "分";
            byte[] str = null;
            for(int i=0;i<dtData.Rows.Count;i++)
            {
                dtData.Rows[i]["uid"] = i+1;
                if (Convert.ToInt32(dtData.Rows[i]["type"]) == 0)
                {
                    dtData.Rows[i]["ptype"] = "固定测报点";
                }
                if (Convert.ToInt32(dtData.Rows[i]["type"]) == 1)
                {
                    dtData.Rows[i]["ptype"] = "非固定测报点";
                }
                if (Convert.ToInt32(dtData.Rows[i]["level"]) == 0)
                {
                    dtData.Rows[i]["plevel"] = "国家级";
                }
                if (Convert.ToInt32(dtData.Rows[i]["level"]) == 1)
                {
                    dtData.Rows[i]["plevel"] = "省级";
                }
                if (Convert.ToInt32(dtData.Rows[i]["level"]) == 2)
                {
                    dtData.Rows[i]["plevel"] = "市级";
                }
                if (Convert.ToInt32(dtData.Rows[i]["level"]) == 3)
                {
                    dtData.Rows[i]["plevel"] = "县级";
                }
            }
            if (dtData != null)
            {
                // 设置编码和附件格式 
                dtData.Columns["uid"].ColumnName = "编号";
                dtData.Columns["name"].ColumnName = "测报点名称";
                dtData.Columns["nickname"].ColumnName = "测报点代码";
                dtData.Columns["ptype"].ColumnName = "测报点类型";
                dtData.Columns["plevel"].ColumnName = "测报点级别";
                dtData.Columns["sheng"].ColumnName = "省/自治区";
                dtData.Columns["shi"].ColumnName = "盟/市";
                dtData.Columns["xian"].ColumnName = "旗/县";
                dtData.Columns["longitude"].ColumnName = "经度";
                dtData.Columns["latitude"].ColumnName = "纬度";
                dtData.Columns["info1"].ColumnName = "田块面积";
                dtData.Columns["info2"].ColumnName = "代表面积";
                dtData.Columns["info3"].ColumnName = "种植作物";
                dtData.Columns["info4"].ColumnName = "乡镇";
                dtData.Columns["info5"].ColumnName = "村组";
                dtData.Columns["info6"].ColumnName = "测报对象";
                dtData.Columns["note"].ColumnName = "备注信息";
                dtData.Columns.Remove("type");
                dtData.Columns.Remove("level");
                dtData.Columns.Remove("shengid");
                dtData.Columns.Remove("shiid");
                dtData.Columns.Remove("xianid");
                dtData.Columns.Remove("id");
                curContext.Response.ContentType = "application/vnd.ms-excel";
                curContext.Response.ContentEncoding = System.Text.Encoding.UTF8;
                curContext.Response.Charset = "gb2312";

                Response.AppendHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
                // 导出excel文件 
                strWriter = new System.IO.StringWriter();
                htmlWriter = new System.Web.UI.HtmlTextWriter(strWriter);

                // 为了解决dgData中可能进行了分页的情况，需要重新定义一个无分页的DataGrid 
                dgExport = new System.Web.UI.WebControls.DataGrid();
                dgExport.DataSource = dtData;
                dgExport.AllowPaging = false;
                dgExport.DataBind();
                dgExport.Font.Size = 14;
                dgExport.HeaderStyle.Font.Bold = true;
                dgExport.RenderControl(htmlWriter);
                // 返回客户端 
                str = System.Text.Encoding.UTF8.GetBytes(strWriter.ToString());
                //string meeting = "<Workbook>"
                 // + "<Worksheet>";
                string meeting = "<html>"
                   + "<head>"
                   + "<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
                   + "</head>"
                   + "<body>";
                //string meeting1 = "</Worksheet><Worksheet></Worksheet></Workbook>";
                string meeting1 = "</body></html>";
                String a = meeting + strWriter.ToString() + meeting1;
                Response.Write(a);
                //发送完，关闭
                Response.End();
            }

        }
        public FileResult EditFile()
        {
            String file_path = "Content/file/point.xls";
            String file_name = "point.xls";
            string filePath = Server.MapPath("~/" + file_path);
            string fileExtension = Path.GetExtension(file_name) + "/plain"; // 文件扩展名
            return File(filePath, fileExtension, file_name);
        }
        [Authorize]
        [AcceptVerbs(HttpVerbs.Post)]
        public JsonResult UploadifyComplain(HttpPostedFileBase fileData)
        {
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
                            return Json("");
                        }
                    }
                    string fileName = Path.GetFileName(fileData.FileName);// 原始文件名称
                    string fileExtension = Path.GetExtension(fileName); // 文件扩展名
                    string saveName = Guid.NewGuid().ToString() + fileExtension; // 保存文件名称
                    string filePath = Server.MapPath("~/Content/pointfile/");
                    if (!Directory.Exists(filePath))
                    {
                        Directory.CreateDirectory(filePath);
                    }
                    fileData.SaveAs(filePath + saveName);
                    DataSet ds = ExcelSqlConnection(filePath + saveName, saveName,IsXls); //调用自定义方法
                    // ExcelSql(filePath + saveName, saveName, IsXls);
                    DataRow[] dr = ds.Tables[0].Select(); //定义一个DataRow数组
                    int rowsnum = ds.Tables[0].Rows.Count;
                    if (rowsnum >1)
                    {
                        BingChongDBDataContext db = new BingChongDBDataContext();
                        for (int i = 1; i < rowsnum; i++)
                        {
                            point p = new point();
                            p.nickname = dr[i][1].ToString();
                            if (dr[i][5].ToString() == "固定测报点")
                            {
                                p.type = 0;
                            }
                            if (dr[i][5].ToString() == "非固定测报点")
                            {
                                p.type = 1;
                            }
                            p.latitude = Convert.ToDecimal(dr[i][4].ToString());
                            p.longitude = Convert.ToDecimal(dr[i][3].ToString());
                            p.name = dr[i][2].ToString();
                            if (dr[i][6].ToString() == "国家级")
                            {
                                p.level = 0;
                            }
                            if (dr[i][6].ToString() == "省级")
                            {
                                p.level = 1;
                            }
                            if (dr[i][6].ToString() == "市级")
                            {
                                p.level = 2;
                            }
                            if (dr[i][6].ToString() == "县级")
                            {
                                p.level = 3;
                            }
                            sheng sh = db.shengs.Single(m => m.name == dr[i][7].ToString()&&m.deleted==0);
                            p.sheng_id = sh.uid;
                            shi shi = db.shis.Single(m => m.name == dr[i][8].ToString() && m.sheng_id == sh.uid && m.deleted == 0);
                            p.shi_id = shi.uid ;
                            xian xi = db.xians.Single(m => m.name == dr[i][9].ToString() && m.shi_id == shi.uid && m.deleted == 0);
                            p.xian_id = xi.uid;
                            p.info1 = dr[i][10].ToString();
                            p.info2 = dr[i][11].ToString();
                            p.info3 = dr[i][12].ToString();
                            p.info4 = dr[i][13].ToString();
                            p.info5 = dr[i][14].ToString();
                            p.info6 = dr[i][15].ToString();
                            p.note = dr[i][16].ToString();
                            p.deleted = 0;
                            db.points.InsertOnSubmit(p);
                            db.SubmitChanges();
                        }
                        
                        int success = 1;
                        return Json(success, JsonRequestBehavior.AllowGet);
                       
                    }
                    else
                    {
                        int success = 2;
                        return Json(success, JsonRequestBehavior.AllowGet);
                    }

                }
                catch (Exception ex)
                {
                    int success = 3;
                    return Json(success, JsonRequestBehavior.AllowGet);
                }


            }
            else
            {
                bool success = false;
                return Json(success, JsonRequestBehavior.AllowGet);
            }
        }
        //导入工单excel方法
        [Authorize]
        public DataSet ExcelSqlConnection(string filepath, string tableName, string IsXls)
        {
            string strCon = "";
            if (IsXls == ".xls")
            {
                strCon = "Provider=Microsoft.Jet.OLEDB.4.0;Data Source=" + filepath + ";Extended Properties='Excel 8.0;HDR=NO;IMEX=1'";
            }
            else
            {
                strCon = "Provider=Microsoft.ACE.OLEDB.12.0;Data Source=" + filepath + ";Extended Properties='Excel 12.0;HDR=NO;IMEX=1'";
            }
            OleDbConnection ExcelConn = new OleDbConnection(strCon);
            try
            {
                ExcelConn.Open();
                var dtSchema = ExcelConn.GetOleDbSchemaTable(OleDbSchemaGuid.Tables, new object[] { null, null, null, "TABLE" });
                string Sheet1 = dtSchema.Rows[0].Field<string>("TABLE_NAME");
                string strCom = string.Format("SELECT * FROM [" + Sheet1 + "]");
                OleDbDataAdapter myCommand = new OleDbDataAdapter(strCom, ExcelConn);
                DataSet ds = new DataSet();
                myCommand.Fill(ds, "[" + tableName + "$]");
                ExcelConn.Close();
                return ds;
            }
            catch
            {
                ExcelConn.Close();
                return null;
            }
        }
    }
}
