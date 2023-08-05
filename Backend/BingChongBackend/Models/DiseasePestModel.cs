using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace BingChongBackend.Models
{
   
    public class BlightInfo
    {
        public long uid { get; set; }
        public string name { get; set; }
        public byte kind { get; set; }
        public string info1 { get; set; }
        public string info2 { get; set; }
        public string info3 { get; set; }
        public string info4 { get; set; }
        public string info5 { get; set; }
        public string photo_list { get; set; }
        public string serial { get; set; }
        public string form_ids { get; set; }
        public byte deleted { get; set; }
    }
    public class DiseasePestModel
    {

        BingChongDBDataContext db = new BingChongDBDataContext();
        public List<blight> FindDiseasePestInfo(int type, string name, int rows, int page, List<blight> list)
        { if (type!=2&&name!="")
        {
            list = db.blights.Where(m => m.deleted == 0&&m.kind==type&&m.name.Contains(name)).Skip((page - 1) * rows).Take(page * rows).ToList();
        }if (type==2&&name!="")
        {
            list = db.blights.Where(m => m.deleted == 0 && m.name.Contains(name)).Skip((page - 1) * rows).Take(page * rows).ToList();
        }
        if (type != 2 && name == "")
        {
            list = db.blights.Where(m => m.deleted == 0 && m.kind == type).Skip((page - 1) * rows).Take(page * rows).ToList();
        }
        if (type == 2 && name == "")
        {
            list = db.blights.Where(m => m.deleted == 0 ).Skip((page - 1) * rows).Take(page * rows).ToList();
        }  
            return list;
        }

        internal String FindDiseasePestTotal(int type, string name, int rows, int page, List<blight> list)
        {
             if (type!=2&&name!="")
            {
                list = db.blights.Where(m => m.deleted == 0 && m.kind == type && m.name.Contains(name)).ToList();
            }if (type==2&&name!="")
            {
                list = db.blights.Where(m => m.deleted == 0 && m.name.Contains(name)).ToList();
            }
            if (type != 2 && name == "")
            {
                list = db.blights.Where(m => m.deleted == 0 && m.kind == type).ToList();
            }
            if (type == 2 && name == "")
            {
                list = db.blights.Where(m => m.deleted == 0 ).ToList();
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

        public List<blight> FindDiseasePestName(int type,List<blight> list)
        {
            if (type==1)
            {
                list = db.blights.Where(m => m.deleted == 0 && m.kind == 1).ToList();
            } else if (type == 0)
            {
                list = db.blights.Where(m => m.deleted == 0 && m.kind == 0).ToList();
            } else
            {
                list = db.blights.Where(m => m.deleted == 0).ToList();
            }
            return list;
        }

        public List<form> FindTables(List<form> list)
        {
            
           list = db.forms.Where(m => m.deleted == 0).ToList();
           
            return list;
        }

        public string AddDiseasePestInfo(string type, string name, string info1, string info2, string info3, string info4, string info5, string info6, string img, string table1)
        {
            try
            {
              blight infos = new blight();
              infos = db.blights.Where(m => m.name == name && m.deleted == 0).FirstOrDefault();
              if (infos != null)
                {
                    return "error";
                }
                else
                {
                    blight info = new blight();
                    info.name = name;
                    var photo = img.Split(',');
                    String listphoto = "";
                    for (int i = 0; i < photo.Length;i++ )
                    {
                        if (photo[i]!=""&&photo[i]!=null)
                        {
                            listphoto += photo[i] + ",";
                        }
                    }
                    info.photo_list = listphoto;
                    info.form_ids = table1;
                    info.info1 = info1;
                    info.info2 = info2;
                    info.info3 = info3;
                    info.info4 = info4;
                    info.info5 = info5;
                    info.info6 = info6;
                    info.kind = byte.Parse(type);
                    long uid = db.blights.Where(m => m.kind == byte.Parse(type)).Max(m => m.uid);
                    blight disinfo = db.blights.Single(m => m.uid == uid);
                    String serial1 = disinfo.serial;
                    String head = serial1.Substring(0, 2);
                    int after1 = int.Parse(serial1.Substring(serial1.IndexOf('H') + 1)) + 1;
                    if (after1 / 10 <= 0)
                    {
                        String serial2 = head + "00" + after1;
                        info.serial = serial2;
                    }
                    else if (after1 / 10 > 0 && after1 / 100 <= 0)
                    {
                        String serial2 = head + "0" + after1;
                        info.serial = serial2;
                    }
                    else if (after1 / 100 > 0)
                    {
                        String serial2 = head + "0" + after1;
                        info.serial = serial2;
                    }


                    db.blights.InsertOnSubmit(info);
                    db.SubmitChanges();
                    return "success";
                }
                }
            catch (System.Exception ex)
            {
            	return ex.ToString();
            }

        }

        public blight ViewDiseasePestInfo(long uid)
        {
            blight info = new blight();
            try
            {
                info = db.blights.Single(m=>m.uid==uid);
            }
            catch (System.Exception ex)
            {
            }
            return info;
        }


        public List<form> ViewTableInfo(string form_ids)
        {
            List<form> list = new List<form>();
            try
            {
            
            var uids = form_ids.Split(',');
            for (int i = 0; i < uids.Length;i++ )
            {
                 form form1=new form();
                if (uids[i]!="")
                {
                   form1 = db.forms.Where(m => m.uid == long.Parse(uids[i])&&m.deleted==0).FirstOrDefault();
                    if (form1!=null)
                    {
                        list.Add(form1); 
                    }
                    
                }
              
            }
            }
            catch (System.Exception ex)
            {

            }
            return list;
        }

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
        public bool DeleteDiseasePestInfo(string uid, bool status)
        {
            try
            {
                blight info = db.blights.Single(m=>m.uid==long.Parse(uid));
                info.deleted = 1;
                db.SubmitChanges();
                return true;
            }
            catch (System.Exception ex)
            {
            	return false;
            }
        }

        public string ModifyDiseasePestInfo(string name, string info1, string info2, string info3, string info4, string info5, string info6, string img, string table1, string uid)
        {
            try
            {

                blight info = db.blights.Single(m=>m.uid==long.Parse(uid)) ;
                info.name = name;
                var photo = img.Split(',');
                String listphoto = "";
                for (int i = 0; i < photo.Length; i++)
                {
                    if (photo[i] != "" && photo[i] != null)
                    {
                        listphoto += photo[i] + ",";
                    }
                }
                info.photo_list = listphoto;
                info.form_ids = table1;
                info.info1 = info1;
                info.info2 = info2;
                info.info3 = info3;
                info.info4 = info4;
                info.info5 = info5;
                info.info6 = info6;
                db.SubmitChanges();
                return "success";
            }
            catch (System.Exception ex)
            {
                return ex.ToString();
            }

        }

        internal List<form> FindDiseasePestTable(int uid, List<form> list)
        {
            try
            {
                    blight info = db.blights.Single(m => m.uid == uid);
                    var uids = info.form_ids.Split(',');
                    for (int i = 0; i < uids.Length; i++)
                    {
                        form form1 = new form();
                        if (uids[i] != "")
                        {
                            form1 = db.forms.Single(m => m.uid == long.Parse(uids[i]));
                            list.Add(form1);
                        }

                    }
            }
            catch (System.Exception ex)
            {

            }
            return list;
        }

        public bool BatchDeleteBingchong(string uids)
        {
            bool flag = false;
            try
            {
                var ids = uids.Split(',');
                for (int i = 0; i < ids.Length;i++ )
                {
                    if (ids[i]!=null&&ids[i]!="")
                    {
                        blight info = db.blights.Single(m=>m.deleted==0&&m.uid==long.Parse(ids[i]));
                        db.blights.DeleteOnSubmit(info);
                    }
                }
                db.SubmitChanges();
                flag = true;
            }
            catch (System.Exception ex)
            {

            }
            return flag;
        }
    }
}
