using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace BingChongBackend.Models
{
    public class NewDiseaseInfo
    {
     public long uid {get;set;}
        public string name {get;set;}
        public byte kind {get;set;}
        public double longitude { get; set; }
        public double latitude {get;set;}
        public long? sheng_id {get;set;}
        public long? shi_id {get;set;}
        public long? xian_id {get;set;}
        public string info1 {get;set;}
        public string info2 {get;set;}
        public string info3 {get;set;}
        public String photo {get;set;}
        public String note {get;set;}
        public byte status {get;set;}
        public long? watcher_id {get;set;}
        public long? admin_id {get;set;}
        public String report_date { get; set; }
        public String review_date {get;set;}
    }
    public class   NewDiseaseInfo1
    {
        public long uid {get;set;}
        public string name {get;set;}
        public byte kind {get;set;}
        public double longitude { get; set; }
        public double latitude {get;set;}
        public String sheng_name {get;set;}
        public String shi_name { get; set; }
        public String xian_name { get; set; }
        public string info1 {get;set;}
        public string info2 {get;set;}
        public string info3 {get;set;}
        public String photo {get;set;}
        public String note {get;set;}
        public byte status {get;set;}
        public String watcher_name {get;set;}
        public String admin_name {get;set;}
        public String report_date { get; set; }
        public String review_date {get;set;}
    }
    public class NewDiseaseModel
    {
        BingChongDBDataContext db = new BingChongDBDataContext();
        /*
        public List<form> FindAllTable(string name, List<form> list, int rows, int page)
        {
            try
            {

                if (name != "" && name != null)
                {
                    list = db.forms.Where(m => m.name.Contains(name) && m.deleted == 0).Skip((page - 1) * rows).Take(page * rows).ToList();
                }
                else
                {
                    list = db.forms.Where(m => m.deleted == 0).Skip((page - 1) * rows).Take(page * rows).ToList();
                }
            }
            catch (System.Exception ex)
            {

            }
            return list;
        }
        internal String FindAllTableTotal(string name, int rows, int page, List<form> list)
        {
             try
            {
                if (name != "" && name != null)
                {
                    list = db.forms.Where(m => m.name.Contains(name)).ToList();
                }
                else
                {
                    list = db.forms.Where(m => m.deleted == 0).ToList();
                }
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
                return total + "|" + record;
            }
             catch (System.Exception ex)
             {
                 return ex.ToString();
             }
          
        }
 
        */

        internal List<NewDiseaseInfo> FindNewDiseasePest(string type, string year, string status, string shengid, string shiid, string xianid, int page, int rows)
        {
            List<NewDiseaseInfo> list = new List<NewDiseaseInfo>();
            try
            {
                list = db.temp_blights.Where(m => m.deleted == 0).Select(s => new NewDiseaseInfo
                {
                    uid=s.uid,
                    name=s.name,
                    kind =s.kind,
                    longitude =decimal.ToDouble(s.longitude),
                    latitude =decimal.ToDouble(s.latitude),
                    sheng_id=s.sheng_id,
                    shi_id =s.shi_id,
                    xian_id =s.xian_id ,
                    info1=s.info1,
                    info2=s.info2,
                    info3=s.info3,
                    photo=s.photo,
                    note=s.note,
                    status=s.status,
                    watcher_id =s.watcher_id,
                    admin_id =s.admin_id,
                    report_date = (s.report_date == null) ? "" : s.report_date.ToString(),
                    review_date = (s.review_date == null)?"" :s.review_date.ToString()

                }).ToList();

                list = list.Where(m => m.report_date.Contains(year)).ToList();
                if (type != "2")
                {
                    list = list.Where(m =>m.kind == byte.Parse(type)).ToList();
                }
                if (status=="0")
                {
                    list = list.Where(m => m.status == byte.Parse(status)).ToList();
                }
                if (status == "2")
                {
                    list = list.Where(m => m.status == 2).ToList();
                }
                else if (status == "1")
                {
                    list = list.Where(m => m.status == 1).ToList();
                }
                if (shengid!="0")
                {
                    list = list.Where(m => m.sheng_id == byte.Parse(shengid)).ToList();
                }
                if (shiid != "0")
                {
                    list = list.Where(m => m.shi_id == byte.Parse(shiid)).ToList();
                }
                if (xianid != "0")
                {
                    list = list.Where(m => m.xian_id == byte.Parse(xianid)).ToList();
                }
                list = list.Skip((page - 1) * rows).Take(page * rows).ToList();
            }
            catch (System.Exception ex)
            {
            	
            }
            return list;
          
        }

        internal int FindNewDiseaseRecord(string type, string year, string status, string shengid, string shiid, string xianid)
        {
            List<NewDiseaseInfo> list = new List<NewDiseaseInfo>();
            try
            {
                list = db.temp_blights.Where(m => m.deleted == 0).Select(s => new NewDiseaseInfo
                {
                    uid = s.uid,
                    name = s.name,
                    kind = s.kind,
                    longitude = decimal.ToDouble(s.longitude),
                    latitude = decimal.ToDouble(s.latitude),
                    sheng_id = s.sheng_id,
                    shi_id = s.shi_id,
                    xian_id = s.xian_id,
                    info1 = s.info1,
                    info2 = s.info2,
                    info3 = s.info3,
                    photo = s.photo,
                    note = s.note,
                    status = s.status,
                    watcher_id = s.watcher_id,
                    admin_id = s.admin_id,
                    report_date = (s.report_date == null) ? "" : s.report_date.ToString(),
                    review_date = (s.review_date == null) ? "" : s.review_date.ToString()

                }).ToList();
                if (type != "2")
                {
                    list = list.Where(m => m.kind == byte.Parse(type)).ToList();
                }
                if (status != "3")
                {
                    list = list.Where(m => m.status == byte.Parse(status)).ToList();
                }
                if (shengid != "0")
                {
                    list = list.Where(m => m.sheng_id == byte.Parse(shengid)).ToList();
                }
                if (shiid != "0")
                {
                    list = list.Where(m => m.shi_id == byte.Parse(shiid)).ToList();
                }
                if (xianid != "0")
                {
                    list = list.Where(m => m.xian_id == byte.Parse(xianid)).ToList();
                }
            }
            catch (System.Exception ex)
            {

            }
            return list.Count;
        }

        public List<NewDiseaseInfo1> GetNewDiseaseinfo(List<NewDiseaseInfo> list,List<NewDiseaseInfo1> list1)
        {
            for (int i = 0; i < list.Count;i++ )
            {
                NewDiseaseInfo1 info = new NewDiseaseInfo1();
                info.uid = list[i].uid;
                info.kind = list[i].kind;
                info.longitude = list[i].longitude;
                info.latitude = list[i].latitude;
                info.name = list[i].name;
                info.note = list[i].note;
                info.photo = list[i].photo;
                info.info1 = list[i].info1;
                info.info2 = list[i].info2;
                info.info3 = list[i].info3;
                info.report_date = DateTime.Parse(list[i].report_date).ToString("yyyy-MM-dd HH:mm:ss");
                if (list[i].review_date == ""||list[i].review_date ==null)
                {
                    info.review_date = list[i].review_date;
                }
                else
                {
                    info.review_date = DateTime.Parse(list[i].review_date).ToString("yyyy-MM-dd HH:mm:ss");
                }
                
                sheng shenginfo = db.shengs.Single(m=> m.uid==list[i].sheng_id);
                info.sheng_name = shenginfo.name;
                shi shiinfo = db.shis.Single(m => m.uid == list[i].shi_id);
                info.shi_name = shiinfo.name;
                xian xianinfo = db.xians.Single(m => m.uid == list[i].xian_id );
                info.xian_name = xianinfo.name;
                info.status = list[i].status;
                user userinfo1 = db.users.Single(m=> m.uid==list[i].watcher_id);
                info.watcher_name = userinfo1.name;
                if (list[i].admin_id == 0 || list[i].admin_id == null)
                {
                    info.admin_name = "暂无";
                }
                else
                {
                    user userinfo2 = db.users.Single(m => m.deleted == 0 && m.uid == list[i].admin_id);
                    info.admin_name = userinfo2.name;
                }
              
                list1.Add(info);
            }
            return list1;
        }

        internal NewDiseaseInfo1 FindNewById(string uid)
        {

            NewDiseaseInfo1 info = new NewDiseaseInfo1();
          
            try
            {
                NewDiseaseInfo info1 = db.temp_blights.Where(m => m.uid == long.Parse(uid)).Select(s => new NewDiseaseInfo
                {
                    uid = s.uid,
                    name = s.name,
                    kind = s.kind,
                    longitude = decimal.ToDouble(s.longitude),
                    latitude = decimal.ToDouble(s.latitude),
                    sheng_id = s.sheng_id,
                    shi_id = s.shi_id,
                    xian_id = s.xian_id,
                    info1 = s.info1,
                    info2 = s.info2,
                    info3 = s.info3,
                    photo = s.photo,
                    note = s.note,
                    status = s.status,
                    watcher_id = s.watcher_id,
                    admin_id = s.admin_id,
                    report_date = (s.report_date == null) ? "" : s.report_date.ToString(),
                    review_date = (s.review_date == null) ? "" : s.review_date.ToString()

                }).FirstOrDefault();
               
                info.uid = info1.uid;
                info.kind = info1.kind;
                info.longitude = info1.longitude;
                info.latitude = info1.latitude;
                info.name = info1.name;
                info.note = info1.note;
                info.photo = info1.photo;
                info.info1 = info1.info1;
                info.info2 = info1.info2;
                info.info3 = info1.info3;
                info.report_date = DateTime.Parse(info1.report_date).ToString("yyyy-MM-dd HH:mm:ss");
                if (info1.review_date == "" || info1.review_date == null)
                {
                    info.review_date = "暂无";
                }
                else
                {
                    info.review_date = DateTime.Parse(info1.review_date).ToString("yyyy-MM-dd HH:mm:ss");
                }

                sheng shenginfo = db.shengs.Single(m =>m.uid == info1.sheng_id);
                info.sheng_name = shenginfo.name;
                shi shiinfo = db.shis.Single(m => m.uid == info1.shi_id);
                info.shi_name = shiinfo.name;
                xian xianinfo = db.xians.Single(m => m.uid == info1.xian_id);
                info.xian_name = xianinfo.name;
                info.status = info1.status;
                user userinfo1 = db.users.Single(m =>  m.uid == info1.watcher_id);
                info.watcher_name = userinfo1.name;
                if (info1.admin_id == 0 || info1.admin_id == null)
                {
                    info.admin_name = "暂无";
                }
                else
                {
                    user userinfo2 = db.users.Single(m => m.deleted == 0 && m.uid == info1.admin_id);
                    info.admin_name = userinfo2.name;
                }
            }
            catch (System.Exception ex)
            {

            }
            return info;
        }
    }
}
