using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using BingChongBackend.Models;
using System.IO;
using System.Collections;
using System.Text.RegularExpressions;
using System.Globalization;
using Newtonsoft.Json;
using System.Net;
using System.Web.UI.WebControls;
namespace BingChongBackend.Controllers
{
    public class DiseasePestController : Controller
    {
        RoleModel rolemodel = new RoleModel();
        //
        // GET: /DiseasePest/
        /*
         * 病虫信息显示的页面
         */
        [Authorize]
        public ActionResult DiseasePestInfo()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "DiseasePestManage";
            ViewData["level2nav"] = "DiseasePestInfo";
            ViewData["role"] = CommonModel.GetUserRoleInfo();
     
            return View();
        }
        /*
         * 测报表格显示的页面
         */
        [Authorize]
        public ActionResult DiseasePestTable()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "DiseasePestManage";
            ViewData["level2nav"] = "DiseasePestTable";
         //   ViewData["role"] = rolemodel.GetRoleById(CommonModel.GetSessionUserID()).role;
            ViewData["role"] = CommonModel.GetUserRoleInfo();
            return View();
        }
        /*
         * 图片管理 (此功能已取消)
         */
        [Authorize]
        public ActionResult ImgManage()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "DiseasePestManage";
            ViewData["level2nav"] = "ImgManage";
            return View();
        }
        /*
         * 添加病虫信息页面
         * 
         */
        [Authorize]
        public ActionResult AddDiseasePest()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "DiseasePestManage";
            ViewData["level2nav"] = "AddDiseasePest";
            return View();
        }
        /*
         * 查看病虫害信息的页面 
         */
        [Authorize]
        public ActionResult ViewDiseasePest(String id)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "DiseasePestManage";
            ViewData["level2nav"] = "ViewDiseasePest";
            ViewData["uid"] = id;
            return View();
        }
        /*
       * 修改病虫害信息的页面 
       */
        [Authorize]
        public ActionResult ModifyDiseasePest(String id)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "DiseasePestManage";
            ViewData["level2nav"] = "ModifyDiseasePest";
            ViewData["uid"] = id;
            return View();
        }
        /*
       * 删除病虫害信息的页面 
       *
       [Authorize]
        public ActionResult DeletedDiseasePest()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "DiseasePestManage";
            ViewData["level2nav"] = "ViewDiseasePest";

            return View();
        }
          */

        /*
         * 添加图片 (此功能为图片管理的功能 已取消)
         */
        public ActionResult AddImg()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "DiseasePestManage";
            ViewData["level2nav"] = "ImgManage";

            return View();
        }
       /*
        * 根据条件查询病虫害信息
        */
        public JsonResult FindDiseasePestInfo(int type,string name,int rows,int page)
        {
            List<blight> list=new List<blight>();
            list = new DiseasePestModel().FindDiseasePestInfo(type, name, rows, page, list);
            String totals = new DiseasePestModel().FindDiseasePestTotal(type, name, rows, page, list);
            int total = int.Parse(totals.Split('|')[0]);
            int records = int.Parse(totals.Split('|')[1]);
            return Json(new { page = page, total = total, records = records, rows = list }, JsonRequestBehavior.AllowGet);
        }

        /*
         * 根据病虫种类查询病虫名称
         */
        public JsonResult FindDiseasePestName(int type)
        {
            List<blight> list = new List<blight>();
            list = new DiseasePestModel().FindDiseasePestName(type,list);

            return Json(list,JsonRequestBehavior.AllowGet);
        } 

         /*
         * 根据病虫种类查询病虫名称
         */
        public JsonResult FindDiseasePestTable(int uid)
        {
            List<form> list = new List<form>();
            list = new DiseasePestModel().FindDiseasePestTable(uid, list);

            return Json(list,JsonRequestBehavior.AllowGet);
        }

        /*
         * 上传图片的方法
         * 
         */
        public JsonResult UploadifyImageS(HttpPostedFileBase Filedata)
        {
            if (Filedata != null)
            {
                try
                {
                    // 文件上传后的保存路径
                    string filePath = Server.MapPath("~/Content/DiseasePest/");
                    if (!Directory.Exists(filePath))
                    {
                        Directory.CreateDirectory(filePath);
                    }
                    string fileName = Path.GetFileName(Filedata.FileName);// 原始文件名称
                    string fileExtension = Path.GetExtension(fileName); // 文件扩展名
                    //string saveName = Guid.NewGuid().ToString() + fileExtension; // 保存文件名称
                    DateTime time = DateTime.Now;
                    String names = time.Year + "" + (time.Month < 10 ? "0" + time.Month : "" + time.Month) + (time.Day < 10 ? "0" + time.Day : "" + time.Day) + (time.Hour < 10 ? "0" + time.Hour : "" + time.Hour) + (time.Minute < 10 ? "0" + time.Minute : "" + time.Minute) + (time.Second < 10 ? "0" + time.Second : "" + time.Second) + (time.Millisecond < 10 ? "0" + time.Millisecond : "" + time.Millisecond);
          
                    string saveName = names +"."+ fileExtension;
                    Filedata.SaveAs(filePath + saveName);

                    //return Json(new { Success = true, FileName = fileName, SaveName = "Content/uploads/video/" + saveName }, JsonRequestBehavior.AllowGet);
                    return Json("Content/DiseasePest/" + saveName, JsonRequestBehavior.AllowGet);
                }
                catch (Exception ex)
                {
                    return Json(new { Success = false, Message = ex.Message }, JsonRequestBehavior.AllowGet);
                }
            }
            else
            {

                return Json(new { Success = false, Message = "请选择要上传的文件！" }, JsonRequestBehavior.AllowGet);
            }
        }

        public JsonResult FindTables()
        {
            try
            {
                List<form> list = new List<form>();
                list = new DiseasePestModel().FindTables(list);
                return Json(list, JsonRequestBehavior.AllowGet);
            }
            catch (System.Exception ex)
            {
                return Json(new { Success = false, Message = ex.Message }, JsonRequestBehavior.AllowGet);
            }
            
        }
        /*
         * 添加病虫害信息的添加功能
         */
        public JsonResult AddDiseasePestInfo(String type, String name, String info1, String info2, String info3, String info4, String info5, String info6, String img, String table1)
        {
                String status = "";
                status = new DiseasePestModel().AddDiseasePestInfo(type, name, info1, info2, info3, info4, info5,info6, img, table1);
    
                   return Json(status, JsonRequestBehavior.AllowGet);
              
        }
        
        /*
         * 修改病虫害信息的添加功能
         */
        public JsonResult ModifyDiseasePestInfo(String name, String info1, String info2, String info3, String info4, String info5, String info6, String img, String table1, string uid)
        {
                String status = "";
                status = new DiseasePestModel().ModifyDiseasePestInfo(name, info1, info2, info3, info4, info5, info6, img, table1, uid);
               if (status=="success")
               {
                   return Json(true, JsonRequestBehavior.AllowGet);
               }
               else
               {
                   return Json(false, JsonRequestBehavior.AllowGet);
               }
              
        }

        /*
         * 查看病虫害的信息查询
         */
        public JsonResult ViewDiseasePestInfo(long uid)
        {
            blight info = new DiseasePestModel().ViewDiseasePestInfo(uid);
            return Json(info, JsonRequestBehavior.AllowGet);
        }
        /*
         * 根据表的ids查询表信息
         */
        public JsonResult ViewTableInfo(String form_ids)
        {
            try
            {
                List<form> list = new List<form>();
                list = new DiseasePestModel().ViewTableInfo(form_ids);
                return Json(list, JsonRequestBehavior.AllowGet);
            }
            catch (System.Exception ex)
            {
                return Json(new { Success = false, Message = ex.Message }, JsonRequestBehavior.AllowGet);
            }
        }
        /*
         * 查询测报表格的信息记录
         */
        public JsonResult FindAllTable(String name, int rows, int page)
        {
            List<form> list = new List<form>();
            list = new DiseasePestModel().FindAllTable(name,list,rows,page);
            //return Json(list, JsonRequestBehavior.AllowGet);
         //   list = new DiseasePestModel().FindDiseasePestInfo(type, name, rows, page, list);
            String totals = new DiseasePestModel().FindAllTableTotal(name, rows, page, list);
            int total = int.Parse(totals.Split('|')[0]);
            int records = int.Parse(totals.Split('|')[1]);
            return Json(new { page = page, total = total, records = records, rows = list }, JsonRequestBehavior.AllowGet);
        
        }
        /*
         * 病虫害的删除信息
         */
        public JsonResult DeleteDiseasePestInfo(String uid)
        {
          bool status=false;
          status= new DiseasePestModel().DeleteDiseasePestInfo(uid,status);
          return Json(status, JsonRequestBehavior.AllowGet);
        }
       
        /*
         * 调用接口获取Json数据
         */
        public JsonResult GetJsonMassage1(String name,String type)
        {
            try
            {

                System.Net.WebRequest webRequest = System.Net.WebRequest.Create("http://120.193.233.26:8006/b/index.php?act=get_bingchongxinxi&diseasename=" + name + "&&kind=" + type + "");
            System.Net.HttpWebRequest httpRequest = webRequest as System.Net.HttpWebRequest;
            httpRequest.Method = "GET";
            //httpRequest.ContentType = "application/x-www-form-urlencoded";
            httpRequest.ContentType = "application/json";
            System.Text.Encoding encoding = System.Text.Encoding.ASCII;
             byte[] bytesToPost = encoding.GetBytes("");
             httpRequest.ContentLength = bytesToPost.Length;
//             System.IO.Stream requestStream = httpRequest.GetRequestStream();
//             requestStream.Write(bytesToPost, 0, bytesToPost.Length);
//             requestStream.Close();
            System.Net.WebResponse wr = httpRequest.GetResponse();
            System.IO.Stream receiveStream = wr.GetResponseStream();
            List<ApkResponseData1> ret1 = new List<ApkResponseData1>();
            ApkResponseData2 ret2 = new ApkResponseData2();
            using (System.IO.StreamReader reader = new System.IO.StreamReader(receiveStream, System.Text.Encoding.UTF8))
            {
                string responseContent = "";
                responseContent = reader.ReadToEnd();

                ret1 = JsonConvert.DeserializeObject<List<ApkResponseData1>>(responseContent);
                String img = ret1[0].imgphoto_list;
                var img1 = img.Split(',');
                String img2="";
                for (int i = 0; i < img1.Length;i++ )
                {
                    String nameFile= Page_Load(img1[i]);
                    if (nameFile != "success")
                    {
                        img2 += "Content/DiseasePest/" + nameFile + ",";
                    }
                }
                ret1[0].imgphoto_list = img2;
            } 
             return Json(ret1, JsonRequestBehavior.AllowGet);
            
            }
            catch (System.Exception ex)
            {
                return Json(false, JsonRequestBehavior.AllowGet);
            }
            
        }

        /*
         * 调用接口获取Json数据
         */
        public JsonResult GetJsonMassage2(String name, String type)
        {
            try
            {

                System.Net.WebRequest webRequest = System.Net.WebRequest.Create("http://120.193.233.26:8006/b/index.php?act=get_bingchongxinxi&diseasename=" + name + "&&kind=" + type + "");
                System.Net.HttpWebRequest httpRequest = webRequest as System.Net.HttpWebRequest;
                httpRequest.Method = "GET";
                //httpRequest.ContentType = "application/x-www-form-urlencoded";
                httpRequest.ContentType = "application/json";
                System.Text.Encoding encoding = System.Text.Encoding.ASCII;
                byte[] bytesToPost = encoding.GetBytes("");
                httpRequest.ContentLength = bytesToPost.Length;
                //             System.IO.Stream requestStream = httpRequest.GetRequestStream();
                //             requestStream.Write(bytesToPost, 0, bytesToPost.Length);
                //             requestStream.Close();
                System.Net.WebResponse wr = httpRequest.GetResponse();
                System.IO.Stream receiveStream = wr.GetResponseStream();
                ApkResponseData1 ret1 = new ApkResponseData1();
                List<ApkResponseData2> ret2 = new List<ApkResponseData2>();

                using (System.IO.StreamReader reader = new System.IO.StreamReader(receiveStream, System.Text.Encoding.UTF8))
                {
                    string responseContent = "";
                    responseContent = reader.ReadToEnd();

                    ret2 = JsonConvert.DeserializeObject<List<ApkResponseData2>>(responseContent);
                    String img = ret2[0].imgphoto_list;
                    var img1 = img.Split(',');
                    String img2 = "";
                    for (int i = 0; i < img1.Length; i++)
                    {
                        String nameFile = Page_Load(img1[i]);
                        if (nameFile != "success")
                        {
                            img2 += "Content/DiseasePest/" + nameFile+",";
                        }
                    }
                    ret2[0].imgphoto_list = img2;
                }
                    return Json(ret2, JsonRequestBehavior.AllowGet);
             }
             catch (System.Exception ex)
             {
                 return Json(false, JsonRequestBehavior.AllowGet);
             }

        }

        protected String Page_Load(String url)
        {
          
            String name = url.Substring(url.LastIndexOf("/"));
            DateTime time = DateTime.Now;

            String names = time.Year + "" + (time.Month < 10 ? "0" + time.Month : "" + time.Month) + (time.Day < 10 ? "0" + time.Day : "" + time.Day) + (time.Hour < 10 ? "0" + time.Hour : "" + time.Hour) + (time.Minute < 10 ? "0" + time.Minute : "" + time.Minute) + (time.Second < 10 ? "0" + time.Second : "" + time.Second) + (time.Millisecond < 10 ? "0" + time.Millisecond : "" + time.Millisecond);
            String fileExtension = name.Split('.')[1];
            string saveName = names +"."+ fileExtension;
            string file_name = Server.MapPath("~/Content/DiseasePest/") + saveName;

            bool flag=save_file_from_url(file_name, url);
             if (flag==true)
             {
                 return saveName;
             }
             else
             {
                 return "success";
             }
          
        }

        public bool save_file_from_url(string file_name, string url)
        {
            byte[] content;
            try
            {
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
                WebResponse response = request.GetResponse();

                Stream stream = response.GetResponseStream();

                using (BinaryReader br = new BinaryReader(stream))
                {
                    content = br.ReadBytes(500000);
                    br.Close();
                }
                response.Close();
                FileStream fs = new FileStream(file_name, FileMode.Create);
                BinaryWriter bw = new BinaryWriter(fs);

                bw.Write(content);
                fs.Close();
                bw.Close();
                return true;
            }
            catch (WebException e)
            {
                return false;
            }
            finally
            {
                
            }
        }
        public JsonResult FindAllDesInfo(String kind)
        {
            try
            {
                System.Net.WebRequest webRequest = System.Net.WebRequest.Create("http://120.193.233.26:8006/b/index.php?act=get_bingchongxinxi&diseasename=&&kind=2");
                System.Net.HttpWebRequest httpRequest = webRequest as System.Net.HttpWebRequest;
                httpRequest.Method = "GET";
                //httpRequest.ContentType = "application/x-www-form-urlencoded";
                httpRequest.ContentType = "application/json";
                System.Text.Encoding encoding = System.Text.Encoding.ASCII;
                byte[] bytesToPost = encoding.GetBytes("");
                httpRequest.ContentLength = bytesToPost.Length;
                //             System.IO.Stream requestStream = httpRequest.GetRequestStream();
                //             requestStream.Write(bytesToPost, 0, bytesToPost.Length);
                //             requestStream.Close();
                System.Net.WebResponse wr = httpRequest.GetResponse();
                System.IO.Stream receiveStream = wr.GetResponseStream();

                List<ApkResponseData3> ret1 = new List<ApkResponseData3>();
                using (System.IO.StreamReader reader = new System.IO.StreamReader(receiveStream, System.Text.Encoding.UTF8))
                {
                    string responseContent = "";
                    responseContent = reader.ReadToEnd();
                    ret1 = JsonConvert.DeserializeObject<List<ApkResponseData3>>(responseContent);
                    if (kind=="1")
                    {
                      ret1=ret1.Where(m=>m.type==1).ToList();  
                    }
                    if (kind=="0")
                    {
                    ret1=ret1.Where(m=>m.type==0).ToList(); 
                    }
                }
                return Json(ret1, JsonRequestBehavior.AllowGet);
            }
            catch (System.Exception ex)
            {
                return Json(false, JsonRequestBehavior.AllowGet);
            }
        }

        /*
         * 批量删除
         */
        public JsonResult BatchDeleteBingchong(String uids)
        {
            bool flag = new DiseasePestModel().BatchDeleteBingchong(uids);
            return Json(flag, JsonRequestBehavior.AllowGet);
        }

    }

    
    public class ApkResponseData3
    {
        [JsonProperty("name")]
        public String name { get; set; }

        [JsonProperty("type")]
        public byte type { get; set; }
    }
    public class ApkResponseData1
    {
        [JsonProperty("name")]
        public String name { get; set; }

        [JsonProperty("img")]
        public String imgphoto_list { get; set; }

        [JsonProperty("1")]
        public string info5 { get; set; }

        [JsonProperty("2")]
        public string info1 { get; set; }

        [JsonProperty("3")]
        public String info3 { get; set; }

        [JsonProperty("5")]
        public String info4 { get; set; }




  //      [JsonProperty("7")]
   //     public object info2 { get; set; }

        public ApkResponseData1()
        {
        }
    }
    public class ApkResponseData2
    {
        [JsonProperty("name")]
        public String name { get; set; }

        [JsonProperty("img")]
        public String imgphoto_list { get; set; }

        [JsonProperty("1")]
        public string info5 { get; set; }

        [JsonProperty("6")]
        public string info6 { get; set; }

        [JsonProperty("7")]
        public String info2 { get; set; }

        [JsonProperty("5")]
        public String info4 { get; set; }

        public ApkResponseData2()
        {
        }
    }
}
