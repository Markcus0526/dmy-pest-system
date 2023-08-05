using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using BingChongBackend.Models.Library;

namespace BingChongBackend.Models
{
    public class FieldInfo
    {
       public long uid { get; set; }
       public long? parentid { get; set; }
       public String parentname { get; set; }
       public String name { get; set; }
       public String type { get; set; }
       public String note { get; set; }
       public int haschildren { get; set; }

       public int rowspan{ get; set; }
       public int colspan { get; set; }
       public int floor { get; set; }

       public int xset { get; set; }
       public int yset { get; set; }
       public List<FieldInfo> childrenfieldlist { get; set; }
    }

    public class FieldModel
    {
        BingChongDBDataContext db = new BingChongDBDataContext();

        public FieldInfo GetFieldInfo(long uid)
        {
            return db.fields.Where(m => m.deleted == 0 && m.uid == uid)
                               .Select(m => new FieldInfo
                               {
                                   uid = m.uid,
                                   parentid = m.parent_fieldid,
                                   type = m.type,
                                   name = m.name
                               }
                               ).FirstOrDefault();
        }
        public List<FieldInfo> GetFieldList()
        {
            var rst = db.fields.Where(m => m.deleted == 0)
                               .Select(m=>new FieldInfo
                                        {
                                            uid=m.uid,
                                            parentid=m.parent_fieldid,
                                            type=m.type,
                                            name=m.name
                                        } 
                               ).ToList();
            foreach(var n in rst){
                if (n.parentid!=null)
                {
                    n.parentname = db.fields.Where(m => m.deleted == 0 && m.uid == n.parentid).Select(m => m.name).FirstOrDefault();
                }
            }
            return rst ;
        }

        public List<FieldInfo> SearchFieldList(string searchParentFieldWord, string searchFieldWord)
        {
            var rstlist = GetFieldList();
            if (!String.IsNullOrWhiteSpace(searchFieldWord))
            {
                rstlist = rstlist.Where(m => m.name.Contains(searchFieldWord)).ToList();

            }

            if (!String.IsNullOrWhiteSpace(searchParentFieldWord))
            {
                rstlist = rstlist.Where(m => m.parentname != null && m.parentname.Contains(searchParentFieldWord)).ToList();
            }

            return rstlist;
        }
        public List<FieldInfo> GetParentFieldList()
        {
            var rst = db.fields.Where(m => m.deleted == 0&&m.type=="p")
                               .Select(m => new FieldInfo
                               {
                                   uid = m.uid,
                                   parentid = m.parent_fieldid,
                                   type = m.type,
                                   name = m.name
                               }
                               ).ToList();
            foreach (var n in rst)
            {
                if (n.parentid != null)
                {
                    n.parentname = db.fields.Where(m => m.deleted == 0 && m.uid == n.parentid).Select(m => m.name).FirstOrDefault();
                }
            }
            return rst;
        }
        
        public string AddField(string name, string type, string parentornot,string parentid,string unit,string option)
        {
            string rst = "";
            if (parentornot=="true")
            {
                try
                {
                    field field = new field();
                    field.name = name;
                    field.type = type;
                    field.note = "";
                    try
                    {
                        field.parent_fieldid = int.Parse(parentid);
                    }
                    catch (System.Exception ex)
                    {
                        rst = "请选择上级字段";
                    }

                    switch (type)
                    {
                        case "c":
                            field.note = option;                            
                            break;
                        case "r":
                            field.note = unit;
                            break;
                        case "i":
                            field.note = unit;
                            break;
                        default:
                            field.note = "";
                            break;
                    }
                    field.deleted = 0;
                    db.fields.InsertOnSubmit(field);

                    db.SubmitChanges();
                }
                catch
                {
                    rst = "操作失败!";
                }
            }
            else
                try
                {
                    field field = new field();
                    field.name = name;
                    field.type = type;
                    switch (type)
                    {
                        case "c":
                            field.note = option;
                            break;
                        case "r":
                            field.note = unit;
                            break;
                        case "i":
                            field.note = unit;
                            break;
                        default :
                            field.note = "";
                            break;
                    }
                    field.deleted = 0;
                    db.fields.InsertOnSubmit(field);

                    db.SubmitChanges();
                }
                catch
                {
                    rst = "操作失败!";
                }
            return rst;
        }

        public string EditField(long uid, string name)
        {
            string rst = "";
            try
            {
                field editfield = db.fields.Where(m => m.deleted == 0 && m.uid == uid).FirstOrDefault();
                editfield.name = name;
                db.SubmitChanges();
                
            }
            catch (System.Exception ex)
            {
                rst = "没有此数据";
            }
            return rst;
        }

        public JqDataTableInfo GetFieldDataTable(JQueryDataTableParamModel param, String rootUri)
        {
            JqDataTableInfo rst = new JqDataTableInfo();

            List<FieldInfo> alllist = GetFieldList();

            var result = from c in alllist
                         select new[] { 
                c.ToString(),
                c.parentname,
                c.name,
                c.type,
                Convert.ToString(c.uid)
            };

            rst.sEcho = param.sEcho;
            rst.iTotalRecords = alllist.Count();
            rst.iTotalDisplayRecords = alllist.Count();
            rst.aaData = result;

            return rst;
        }

    }
}