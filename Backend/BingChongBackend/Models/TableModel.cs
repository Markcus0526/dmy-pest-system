using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Collections;

namespace BingChongBackend.Models
{
    public class table
    {
        public string name { set; get; }
        public string field_ids{ set; get; }
        public long id { set; get; }
        public long uid{ set; get; }

    }
    public class TableModel
    {
        BingChongDBDataContext db = new BingChongDBDataContext();

        public List<FieldInfo> GetFirstFieldList()
        {
            var rst=db.fields.Where(m => m.deleted == 0 && m.parent_fieldid == null).Select(m => new FieldInfo
                    {
                        name = m.name,
                        uid = m.uid,
                        type = m.type,
                        note = m.note
                    }).ToList();

            return rst;
        }


        public List<FieldInfo> GetChildField(List<FieldInfo> masterList) 
        {
           // var flag=new string[]{};
          //  var searcharea = masterList.Where(m => m.type == "p").ToList();
            foreach(var n in masterList)
            {
                if (n.type=="p")
                {
                    n.childrenfieldlist = db.fields.Where(m => m.deleted == 0 && m.parent_fieldid == n.uid).Select(m => new FieldInfo
                    {
                        name = m.name,
                        uid = m.uid,
                        type = m.type,
                        note = m.note
                    }).ToList();
                    if (n.childrenfieldlist.Count == 0)
                    {
                        n.haschildren = 0;
                    }
                    else
                    {
                        n.haschildren = 1;
                        n.childrenfieldlist = GetChildField(n.childrenfieldlist);
                    }
                }
            }

            return masterList;
        }

        public List<table> GetTableList()
        {
            return db.forms.Where(m => m.deleted == 0).Select(m => new table
            {
                name = m.name,
                uid = m.uid,
                id = m.uid,
                field_ids = m.field_ids
            }).ToList();
        }

        public List<table> SearchTableList(string tablename)
        {
            var rstlist = GetTableList();
            if (!String.IsNullOrWhiteSpace(tablename))
            {
                rstlist = rstlist.Where(m => m.name.Contains(tablename)).ToList();

            }

            return rstlist;
        }
        public string GetHtml(List<FieldInfo> fieldlist,int span)
        {
            var html = "";

            foreach (var n in fieldlist)
            {
                html += "<div class='form-group'>";
                html += "  <span class='col-sm-"+span+"'></span>";
                html += "  <input type='checkbox' name='fieldid' class='ace indbtn' id='field_" + n.uid + "'  value='" + n.uid + "' />";
                html += "  <label class='lbl' for='field_"+ n.uid +"'>" + n.name + "</label>";
                html += "</div>";
                if (n.haschildren==1)
                {
                    html += GetHtml(n.childrenfieldlist,span+1);
                }
            }

            return html;
        }
        public bool CheckDuplicateTable(string tablename)
        {
            bool rst = true;
            rst = ((from m in db.forms
                    where m.deleted == 0 && m.name == tablename 
                    select m).FirstOrDefault() == null);

            return rst;
        }


        public string InsertTable(string tablename, string field)
        {
            form newitem = new form();

            newitem.name = tablename;
            newitem.field_ids = field;
            newitem.deleted = 0;
            db.forms.InsertOnSubmit(newitem);

            db.SubmitChanges();

            return "";
        }


        public bool DeleteTable(long[] items)
        {
            bool rst = false;
            try
            {
                string delSql = "UPDATE form SET deleted = 1 WHERE ";
                string whereSql = "";
                foreach (long uid in items)
                {
                    if (whereSql != "") whereSql += " OR";
                    whereSql += " uid = " + uid;
                }

                delSql += whereSql;

                db.ExecuteCommand(delSql);
                rst = true;
            }
            catch (System.Exception ex)
            {

            }

            return rst;
        }

        public string GetTableView(string[] fieldid)
        {
            string html = "";
            List<FieldInfo> allFiellist = new List<FieldInfo>();
            List<FieldInfo> mastFieldlist = new List<FieldInfo>();
            List<FieldInfo> restFieldlist = new List<FieldInfo>();

            int[] intArray = Array.ConvertAll<string, int>(fieldid, s => int.Parse(s));
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

            mastFieldlist = allFiellist.Where(m => m.parentid == null).ToList();

            foreach (var n in allFiellist)
            {
                if (!mastFieldlist.Contains(n))
                {
                    restFieldlist.Add(n);
                }
            }
            int y = tabledeep(mastFieldlist, restFieldlist);
            int deep = 1;
            foreach (var n in restFieldlist)
            {
                if (deep < n.floor)
                    deep = n.floor;
            }

            var outprintlist = HandleField(deep, mastFieldlist, restFieldlist);
            Handlecolspan(mastFieldlist);
            html = GenerateViewHtml(outprintlist, deep);

            return html;
        }

        public string GenerateViewHtml(List<FieldInfo> list, int deep)
        {
            var html = "";
            List<FieldInfo> childfield = new List<FieldInfo>();
            html += "<tr>";
            foreach (var print in list)
            {
                html += "<td rowspan=" + print.rowspan + " colspan='" + print.colspan + "' parentid = '"+print.parentid +"' value='" + print.uid + "'>" + print.name + " <i onclick='moveleft(this)' class='ace-icon fa fa-chevron-left' style='margin-left:5px'></i><i onclick='moveright(this)' class='ace-icon fa fa-chevron-right' style='margin-left:10px'></i></td>";
            }
            html += "</tr>";
            deep = deep - 1;
            if (deep != 0)
            {
                foreach (var n in list)
                {

                    if (n.haschildren == 1)
                    {
                        childfield.AddRange(n.childrenfieldlist);
                    }
                }
                html += GenerateViewHtml(childfield, deep);
            }

            return html;
        }



        /*
         * 生成表格Html方法说明
         * 参数说明：
         *            allFiellist     表格全部字段集合，类型 List<FieldInfo>；
         *            mastFieldlist   表格第一层字段集合（parentid为空的字段），类型 List<FieldInfo>；
         *            restFieldlist   allFiellist内除去mastFieldlist的剩余字段集合（parentid不为空的字段）；
         *            y               表格深度，即表格行数   类型int；
         *            outprintlist    封装好的字段集合，封装了rolspan，culspan，floor等属性
         *            colspan         html中显示的colspn属性值，默认为1
         *            rowspan         html中显示的rowspan属性值，默认为1
         */
        public string GetTableHtml(long tableid) 
        {
            string html = "";
            List<FieldInfo> allFiellist = new List<FieldInfo>();
            List<FieldInfo> mastFieldlist = new List<FieldInfo>();
            List<FieldInfo> restFieldlist = new List<FieldInfo>();

            //1.获得数据库的表格信息：tableinfo
            var tableinfo = db.forms.Where(m => m.deleted == 0 && m.uid == tableid).FirstOrDefault();

            //2.获得顶级字段列表:mastFieldlist
            string[] strArray= tableinfo.field_ids.Split(',');
            int[] intArray = Array.ConvertAll<string, int>(strArray, s => int.Parse(s));
            foreach (var n in intArray)
            {
                var field = db.fields.Where(m => m.deleted == 0 &&m.uid==n).Select(m => new FieldInfo
                                            {
                                                parentid=m.parent_fieldid,
                                                name = m.name,
                                                uid = m.uid,
                                                type = m.type,
                                                rowspan=1,
                                                colspan=1,
                                                floor=1,
                                                note = m.note
                                            }).FirstOrDefault();
                allFiellist.Add(field);
            }

            mastFieldlist = allFiellist.Where(m => m.parentid == null).ToList();

            foreach (var n in allFiellist)
            {
                if (!mastFieldlist.Contains(n))
                {
                    restFieldlist.Add(n);
                }
            }
            int y = tabledeep(mastFieldlist, restFieldlist);
            int deep = 1;
            foreach (var n in restFieldlist)
            {
                if (deep < n.floor)
                    deep = n.floor;
            }
            var outprintlist = HandleField(deep, mastFieldlist, restFieldlist);
            Handlecolspan(mastFieldlist);
            html = GenerateHtml(outprintlist, deep);

            return html;
        }

        /*
         * 获取表格深度方法说明
         * 参数说明：
         *            y           深度，默认为1，           类型int
         *            floor       层数，当前所分析的层数    类型int
         *            master      本层字段，每次需要分析的该层的字段集合
         *            childfield  下层字段，当前层数+1层的字段集合
         *            
         *            rest            剩余元素，每次分析之前的剩余元素
         *            restFieldlist   剩余元素，每次分析之后的剩余元素（即rest-childfield）
         *            
         */
        public int tabledeep(List<FieldInfo> master,List<FieldInfo> rest )
        {
            //表格深度 
            int y = 1;

            foreach (var n in master)
            {
                var childfield = rest.Where(m => m.parentid == n.uid).ToList();
                if (childfield.Count != 0)
                {
                //    n.clumspan += childfield.Count - 1;
                 //   n.haschildren = 1;
                    foreach (var nc in childfield)
                    {
                        nc.floor = n.floor + 1;
                        y = nc.floor;
                    }
                    var restFieldlist = new List<FieldInfo>();
                    foreach (var nr in rest)
                    {
                        if (!childfield.Contains(nr))
                        {
                           // nr.floor += 1;
                            restFieldlist.Add(nr);
                        }
                    }
                    if (restFieldlist.Count != 0)
                    {
                        y=tabledeep(childfield, restFieldlist);
                    }
                    else
                    {
                        return y;
                    }
                }
            }

            return y;
        }


        /*
         * 处理表格字段方法说明
         * 参数说明：
         * 
         *            deep        表格总深度, 即之前的y值
         *            master      本层字段，每次需要分析的该层的字段集合
         *            childfield  下层字段，当前层数+1层的字段集合
         *            rest            剩余元素，每次分析之前的剩余元素
         *            restFieldlist   剩余元素，每次分析之后的剩余元素（即rest-childfield            
         * 
         *            colspan         html中显示的colspn属性值，默认为1
         *            rowspan         html中显示的rowspan属性值，默认为1
         *            
         */
        public List<FieldInfo> HandleField(int deep,List<FieldInfo> master, List<FieldInfo> rest)
        {

            foreach (var n in master)
            {
                var childfield = rest.Where(m => m.parentid == n.uid).ToList();
                if (childfield.Count != 0)
                {
                    //n.clumspan += childfield.Count - 1;
                    n.haschildren = 1;
                    n.childrenfieldlist = childfield;
                    n.colspan = deep + childfield.Count - n.floor - 1;
                    //foreach (var nc in n.childrenfieldlist)
                    //{
                    //    nc.floor += 1;
                    //}
                    var restFieldlist = new List<FieldInfo>();
                    foreach (var nr in rest)
                    {
                        if (!childfield.Contains(nr))
                        {
                            restFieldlist.Add(nr);
                        }
                    }
                    if (restFieldlist.Count != 0)
                    {
                        HandleField(deep, n.childrenfieldlist, restFieldlist);
                    }
                }
                else{
                    n.rowspan = deep + 1 - n.floor;
                }
            }
            return master;
        }

        public void Handlecolspan(List<FieldInfo> master)
        {
            foreach (var n in master)
            {
                var childfield = n.childrenfieldlist;
                if (n.haschildren == 1)
                {
                    Handlecolspan(childfield);
                    var colspan=0;
                    foreach(var child in childfield){
                        colspan+=child.colspan;
                    }
                    n.colspan = colspan; 
                }
                else
                {
                    n.colspan = 1;
                }
            }       
        }
        /*
         *  生成table html字符串
         *  
         *   list    处理好的全部字段，包含rowspan,colspan属性
         *   deep    需要生成的表格深度

         */
        public string GenerateHtml(List<FieldInfo>  list,int deep)
        {
            var html = "";
            List<FieldInfo> childfield=new List<FieldInfo>();
            html += "<tr style='text-align:center;'>";
            foreach (var print in list)
            {
                html += "<td rowspan=" + print.rowspan + " colspan=" + print.colspan + " >" + print.name + "</td>";
            }
            html += "</tr>";
            deep = deep - 1;
            if (deep!=0)
            {
                foreach(var n in list){
                    
                    if (n.haschildren==1)
                    {
                        childfield.AddRange(n.childrenfieldlist);
                    }
                }
                html += GenerateHtml(childfield, deep);
            }

            return html;
        }
    }
}