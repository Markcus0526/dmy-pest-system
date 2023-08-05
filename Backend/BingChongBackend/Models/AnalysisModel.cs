using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Newtonsoft.Json;

namespace BingChongBackend.Models
{

    public class AnalysisModel
    {
        BingChongDBDataContext db = new BingChongDBDataContext();



        public List<point> FindPoint(string type, string level, List<point> list, String shengid, String shiid, String xianid)
        {
            try
            {
                list = db.points.Where(m => m.deleted==0).ToList();
                if (shengid!="0")
                {
                    list = list.Where(m => m.sheng_id==long.Parse(shengid)).ToList();
                }
                if (shiid != "0")
                {
                    list = list.Where(m => m.shi_id == long.Parse(shiid)).ToList();
                }
                if (xianid != "0")
                {
                    list = list.Where(m => m.xian_id == long.Parse(xianid)).ToList();
                }
                list = list.Where(m => m.type == byte.Parse(type) && m.level == byte.Parse(level)).ToList() ;
            }
            catch (System.Exception ex)
            {
            	
            }
            return list;
        }

        internal List<field> FindDiseaseField(string uid, List<field> list)
        {
            try
            {
                form forminfo = db.forms.Where(m => m.uid==long.Parse(uid)).FirstOrDefault();
                String fieldlist = forminfo.field_ids;
                if (fieldlist!=null&&fieldlist!="")
                {
                    var fieldid = fieldlist.Split(',');
                    for (int i = 0; i < fieldid.Length; i++)
                    {
                        if (fieldid[i]!="")
                        {
                            field info = new field();
                            info = db.fields.Single(m=>m.deleted==0&&m.uid==long.Parse(fieldid[i]));
                            list.Add(info);
                        }
                    }
                }
            }
            catch (System.Exception ex)
            {

            }
            return list;
        }

        internal List<report> SearchSummaryStatistics(String starttime, String endtime, string point_id, string disease_id, string disease_table, List<report> list)
        {
            try
            {
                list = db.reports.Where(m => m.deleted ==0&& m.point_id == long.Parse(point_id) && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table)).ToList();
                if (starttime!=""&&starttime!=null)
                {
                    list = list.Where(m => m.report_time > DateTime.Parse(starttime)).ToList();
                }
                if (endtime != "" && endtime != null)
                {
                    list = list.Where(m => m.report_time < DateTime.Parse(endtime)).ToList();
                }
            }
            catch (System.Exception ex)
            {
            	
            }
            return list;
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
        public string GetTableHtml(String shengid, String shiid, String xianid, String point_level, String point_kind, String starttime, String endtime, String point_id, String disease_id, String disease_table)
        {
            string html = "";
            int num = 0;
            try
            {
               
                List<report> list = new List<report>();
                if (point_id == "0")
                {
                    List<point> pointlist = findDiseasePoint(shengid, shiid, xianid, point_level, point_kind);
                    for (int i = 0; i < pointlist.Count; i++)
                    {
                        if (point_level == "0")
                        {
                            list.AddRange(db.reports.Where(m => m.deleted == 0 && m.point_id == pointlist[i].uid && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 4).ToList());
                        }
                        else if (point_level != "0")
                        {
                            list.AddRange(db.reports.Where(m => m.deleted == 0 && m.point_id == pointlist[i].uid && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 1).ToList());

                        }
                      
                    }
                }
                else
                {
                    if (point_level == "0")
                    {
                        list = db.reports.Where(m => m.deleted == 0 && m.point_id == long.Parse(point_id) && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 4).ToList();
                    }
                    else if (point_level != "0")
                    {
                        list = db.reports.Where(m => m.deleted == 0 && m.point_id == long.Parse(point_id) && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 1).ToList();
                    }
                   
                }
            if (starttime != "" && starttime != null)
            {
                list = list.Where(m => m.report_time >= DateTime.Parse(starttime)).ToList();
            }
            if (endtime != "" && endtime != null)
            {

                list = list.Where(m => m.report_time <= DateTime.Parse(endtime).AddDays(1)).ToList();
            }
            long tableid = long.Parse(disease_table);
          
            List<FieldInfo> allFiellist = new List<FieldInfo>();
            List<FieldInfo> mastFieldlist = new List<FieldInfo>();
            List<FieldInfo> restFieldlist = new List<FieldInfo>();

            //1.获得数据库的表格信息：tableinfo
            var tableinfo = db.forms.Where(m => m.deleted == 0 && m.uid == tableid).FirstOrDefault();

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
           
            List<FieldInfo> childrenist = new List<FieldInfo>();
            childrenist = GenerateChild(outprintlist, allFiellist, y);
            List<long> number1 = new List<long>();
            List<double> number2 = new List<double>();
            List<double> number3= new List<double>();
             for (int i = 0; i <= list.Count;i++ )
            {
                      List<report_detail> detailslist =new List<report_detail>();
           
                        html += "<tr>";
                 if (i!=list.Count)
                 {
                     point pointinfo = db.points.Single(m => m.deleted == 0 && m.uid == list[i].point_id);
                     sheng shenginfo = db.shengs.Single(m => m.deleted == 0 && m.uid == pointinfo.sheng_id);
                     shi shiinfo = db.shis.Single(m => m.deleted == 0 && m.uid == pointinfo.shi_id);
                     xian xianinfo = db.xians.Single(m => m.deleted == 0 && m.uid == pointinfo.xian_id);
                     html += "<td id='report_time'style='border: 1px solid #DDD;text-align:center;vertical-align:middle'>" + list[i].watch_time + "</td>";
                     html += "<td id='report_time'style='border: 1px solid #DDD;text-align:center;vertical-align:middle'>" + list[i].report_time + "</td>";
                     html += "<td id='region' style='border: 1px solid #DDD;text-align:center;vertical-align:middle'>" + shenginfo.name + "" + shiinfo.name + "" + xianinfo.name + "</td>";
                     html += "<td id='point_name' style='border: 1px solid #DDD;text-align:center;vertical-align:middle' >" + pointinfo.name + "</td>";
                     detailslist = db.report_details.Where(m => m.deleted == 0 && m.report_id == list[i].uid).ToList();
                 }
                 if (i==list.Count&&list.Count>0)
                 {
                     html += "<td id=' ' colspan='4' style='border: 1px solid #DDD;text-align:center;vertical-align:middle' >总计</td>";
                     detailslist = db.report_details.Where(m => m.deleted == 0 && m.report_id == list[i-1].uid).ToList();
                 }

                 num = childrenist.Count + 4;
                int number = 50 / childrenist.Count;
                if (i == 0)
                {
                    for (int j = 0; j < childrenist.Count; j++)
                    {
                        number1.Add(0);
                        number2.Add(0);
                        number3.Add(0);
                    }
                }
                for (int j = 0; j < childrenist.Count; j++)
                {
                       
                    field fieldsin = db.fields.Single(m => m.deleted == 0 && m.uid == childrenist[j].uid);
                 report_detail detailsinfo = db.report_details.Single(m => m.deleted == 0 && m.field_id == fieldsin.uid && m.report_id==detailslist[j].report_id);
             
                    if (i!=list.Count)
                 {
                     if (fieldsin.type == "d" || fieldsin.type == "c" || fieldsin.type == "t")
                    {
                        html += "<td id='" + childrenist[j].name + "'  style='width:" + number + "%;' style='border: 1px solid #DDD;text-align:center;vertical-align:middle'>" + detailsinfo.value_text + "</td>";
                    } 
                    if (fieldsin.type=="i")
                    {
                        number1[j] =number1[j] + detailsinfo.value_integer.Value;
                        html += "<td id='" + childrenist[j].name + "'  style='width:" + number + "%;' style='border: 1px solid #DDD;text-align:center;vertical-align:middle'>" + detailsinfo.value_integer + "</td>";
                    }
                     if (fieldsin.type=="r")
                    {
                        number2[j] = number2[j] + detailsinfo.value_real.Value;
                        html += "<td id='" + childrenist[j].name + "'  style='width:" + number + "%;' style='border: 1px solid #DDD;text-align:center;vertical-align:middle'>" + detailsinfo.value_real + "</td>";
                    }
                     if (fieldsin.type == "s")
                     {
                         number3[j] = number3[j] + Math.Round(Convert.ToDouble(detailsinfo.value_text), 2);
                         html += "<td id='" + childrenist[j].name + "'  style='width:" + number + "%;' style='border: 1px solid #DDD;text-align:center;vertical-align:middle'>" + detailsinfo.value_text + "</td>";
                     }
                  }
                 if (i == list.Count && list.Count > 0)
                 {
                     if (fieldsin.type == "d" || fieldsin.type == "c" || fieldsin.type == "t")
                     {
                         html += "<td id='" + childrenist[j].name + "'  style='width:" + number + "%;' style='border: 1px solid #DDD;text-align:center;vertical-align:middle'>/</td>";
                     }
                     if (fieldsin.type == "i")
                     {
                         html += "<td id='" + childrenist[j].name + "'  style='width:" + number + "%;' style='border: 1px solid #DDD;text-align:center;vertical-align:middle'>" + number1[j] + "</td>";
                     }
                     if (fieldsin.type == "r" )
                     {
                         html += "<td id='" + childrenist[j].name + "'  style='width:" + number + "%;' style='border: 1px solid #DDD;text-align:center;vertical-align:middle'>" + number2[j] + "</td>";
                     }
                     if (fieldsin.type == "s")
                     {
                         html += "<td id='" + childrenist[j].name + "'  style='width:" + number + "%;' style='border: 1px solid #DDD;text-align:center;vertical-align:middle'>" + number3[j] + "</td>";
                     }
                 }
                }
             html += "</tr>";
                
            }
            }
            catch (System.Exception ex)
            {

            }
            return html + "|" + num;
        }


        public void Handlecolspan(List<FieldInfo> master)
        {
            foreach (var n in master)
            {
                var childfield = n.childrenfieldlist;
                if (n.haschildren == 1)
                {
                    Handlecolspan(childfield);
                    var colspan = 0;
                    foreach (var child in childfield)
                    {
                        colspan += child.colspan;
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
       *   
       */
        public string uGenerateHtml(List<FieldInfo> list, int deep, List<FieldInfo> alllist)
        {
            var html = "";
            List<FieldInfo> allist1 = new List<FieldInfo>();
            allist1 = Getalllsit(alllist);
            List<FieldInfo> childfield = new List<FieldInfo>();
            html += "<tr>";
            html += "<td rowspan='" + deep + "' colspan=' ' id='report_time' style='width:12%;border: 1px solid #DDD;text-align:center;vertical-align:middle'>调查日期 </td>";
            html += "<td rowspan='" + deep + "' colspan=' ' id='report_time' style='width:12%;border: 1px solid #DDD;text-align:center;vertical-align:middle'>上报日期 </td>";
            html += "<td rowspan='" + deep + "' colspan=' '  id='region' style='width:16%;border: 1px solid #DDD;text-align:center;vertical-align:middle'>地区 </td>";
            html += "<td rowspan='" + deep + "' colspan=' ' id='point_name' style='width:10%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> 测报点名称</td>";
           
            foreach (var print in list)
            {

                html += "<td rowspan=" + print.rowspan + " colspan=" + print.colspan + ">" + print.name + "</td>";
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
                html += uGenerateHtml(childfield, deep, alllist);
                // html += getData(html, allist1, deep, repid, type);
            }

            return html;
        }
        public List<FieldInfo> Getalllsit(List<FieldInfo> plist)
        {
            List<FieldInfo> clist = new List<FieldInfo>();

            for (int i = 0; i < plist.Count; i++)
            {
                if (plist[i].type == "p")
                {
                    clist.Add(plist[i]);
                    for (int j = 0; j < plist.Count; j++)
                    {
                        if (plist[j].parentid == plist[i].uid)
                        {
                            clist.Add(plist[j]);
                        }
                    }
                }
                else
                {
                    if (plist[i].type != "p" && plist[i].parentid == null)
                    {
                        clist.Add(plist[i]);
                    }
                }
            }

            return clist;
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
        public int tabledeep(List<FieldInfo> master, List<FieldInfo> rest)
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
                        y = tabledeep(childfield, restFieldlist);
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
        public List<FieldInfo> HandleField(int deep, List<FieldInfo> master, List<FieldInfo> rest)
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
                else
                {
                    n.rowspan = deep + 1 - n.floor;
                }
            }
            return master;
        }


        /*
         *  生成table html字符串
         *  
         *   list    处理好的全部字段，包含rowspan,colspan属性
         *   deep    需要生成的表格深度
         *   
         */
        public string GenerateHtml(List<FieldInfo> list, int deep)
        {
            var html = "";
            List<FieldInfo> childfield = new List<FieldInfo>();
            html += "<tr>";
            html += "<td rowspan='" + deep + "' colspan=' ' id='report_time' style='width:12%;border: 1px solid #DDD;text-align:center;vertical-align:middle'>调查日期 </td>";
            html += "<td rowspan='" + deep + "' colspan=' ' id='report_time' style='width:12%;border: 1px solid #DDD;text-align:center;vertical-align:middle'>上报日期 </td>";
            html += "<td rowspan='" + deep + "' colspan=' '  id='region' style='width:16%;border: 1px solid #DDD;text-align:center;vertical-align:middle'>地区 </td>";
            html += "<td rowspan='" + deep + "' colspan=' ' id='point_name' style='width:10%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> 测报点名称</td>";
            foreach (var print in list)
            {

                html += "<td rowspan='" + print.rowspan + "' colspan='" + print.colspan + "' id='" + print.name + "' style='width:200px;border: 1px solid #DDD;text-align:center;vertical-align:middle'>" + print.name + "</td>";
               
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
                html += GenerateHtml1(childfield, deep);
            }

            return html ;
        }

        /*
        *  生成table html字符串
        *  
        *   list    处理好的全部字段，包含rowspan,colspan属性
        *   deep    需要生成的表格深度
        *   
        */
        public string GenerateHtml1(List<FieldInfo> list, int deep)
        {
            var html = "";
            List<FieldInfo> childfield = new List<FieldInfo>();
            html += "<tr>";
            foreach (var print in list)
            {

                html += "<td rowspan='" + print.rowspan + "' colspan='" + print.colspan + "' id='" + print.name + "' style='width:200px;text-align:center;vertical-align:middle;border: 1px solid #DDD'>" + print.name + "</td>";
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
                html += GenerateHtml1(childfield, deep);
            }

            return html;
        }
        /*
         *  获取字段子字段信息
         *  
         *   list    处理好的全部字段，包含rowspan,colspan属性
         *   deep    需要生成的表格深度
         *   
         */
        public List<FieldInfo> GenerateChild(List<FieldInfo> list, List<FieldInfo> rest, int deep)
        {
            List<FieldInfo> listinfo = new List<FieldInfo>();
            foreach (var n in list)
            {
                var childfield = rest.Where(m => m.parentid == n.uid).ToList();
                if (childfield.Count != 0)
                {
                    foreach (var nr in childfield)
                    {
                        if (nr.childrenfieldlist == null)
                        {
                            listinfo.Add(nr);
                        }
                        else
                        {
                            List<FieldInfo> listinfo1 = new List<FieldInfo>();
                            listinfo1.Add(nr);
                            listinfo.AddRange(GenerateChild(listinfo1, nr.childrenfieldlist, 0));
                        }
                    }
                }
                else
                {
                    listinfo.Add(n);
                }
            }
            return listinfo;

        }
        internal List<field> FindDiseaseIntField(string uid, List<field> list)
        {
            try
            {
                form forminfo = db.forms.Where(m => m.uid == long.Parse(uid)).FirstOrDefault();
                String fieldlist = forminfo.field_ids;
                if (fieldlist != null && fieldlist != "")
                {
                    var fieldid = fieldlist.Split(',');
                    for (int i = 0; i < fieldid.Length; i++)
                    {
                        if (fieldid[i] != "")
                        {
                            field info = new field();
                            info = db.fields.Single(m => m.deleted == 0 && m.uid == long.Parse(fieldid[i]));
                            if (info.type=="i"||info.type=="r")
                            {
                                list.Add(info);
                            }
                           
                        }
                    }
                }
            }
            catch (System.Exception ex)
            {

            }
            return list;
        }

        public List<report_detail> findReportDetails(string point_id, string shengid, string shiid, string xianid, string point_level, String point_kind, string disease_id, string disease_table, String listfield, List<report_detail> field, DateTime starttime, DateTime endtime)
        {
            try
            {
                List<report> list = new List<report>();
                if (point_id=="0")
                {
               // List<point> pointlist= findDiseasePoint( shengid,  shiid,  xianid,  point_level);
                List<point> pointlist = findDiseasePoint(shengid, shiid, xianid, point_level, point_kind);
                for (int i = 0; i < pointlist.Count;i++ )
                {
                    if (point_level == "0")
                    {
                        list.AddRange(db.reports.Where(m => m.deleted == 0 && m.point_id == pointlist[i].uid && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 4).ToList());
                    }
                    else if (point_level != "0")
                    {
                        list.AddRange(db.reports.Where(m => m.deleted == 0 && m.point_id == pointlist[i].uid && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 1).ToList());

                    }
                   
                }
                }
                else
                {
                    if (point_level == "0")
                    {
                        list = db.reports.Where(m => m.deleted == 0 && m.point_id == long.Parse(point_id) && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 4).ToList();
                    }
                    else if (point_level != "0")
                    {
                        list = db.reports.Where(m => m.deleted == 0 && m.point_id == long.Parse(point_id) && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 1).ToList();
                    }
                   
                }
               
                
                    list = list.Where(m => m.report_time > starttime).ToList();
             
                    list = list.Where(m => m.report_time < endtime).ToList();
             
                for (int i = 0; i < list.Count;i++ )
                {
                    report_detail info=new report_detail( );
                    info = db.report_details.Single(m => m.report_id == list[i].uid && m.field_id == long.Parse(listfield));
                    field.Add(info);
                }
            }
            catch (System.Exception ex)
            {
            	
            }
            return field;
        }


        public List<field> FindDiseaseIntFieldById(string field_id)
        {
            List<field> list = new List<field>();
            field info = new field();
            try
            {
                field info1 = db.fields.Single(m => m.deleted == 0 && m.uid == long.Parse(field_id));
                if (info1.type == "i" || info1.type == "r")
                {
                    info = info1;
                }
            }
            catch (System.Exception ex)
            {

            }
            list.Add(info);
            return list;   
        }

        public List<point> findDiseasePoint(string shengid, string shiid, string xianid, string point_level, String point_kind)
        { //m => m.sheng_id == long.Parse(shengid)
            List<point> list = new List<point>();
            try
            {

                list = db.points.Where(m => m.deleted == 0 && m.level == int.Parse(point_level) && m.type == int.Parse(point_kind)).ToList();
                if (shengid != "0")
                {
                    list = list.Where(m => m.sheng_id == long.Parse(shengid)).ToList();
                }
                if (shiid != "0")
                {
                    list = list.Where(m => m.shi_id == long.Parse(shiid)).ToList();
                }
                if (xianid != "0")
                {
                    list = list.Where(m => m.xian_id == long.Parse(xianid)).ToList();
                }
            }
            catch (System.Exception ex)
            {

            }
            return list;
        }

        public List<report_detail> findReportDetails1(long p,int level, string disease_id, string disease_table, string listfield, List<report_detail> listdetails, DateTime dateTime, DateTime dateTime_2)
        {
            try
            {
                List<report> list = new List<report>();
                if (level == 0)
                {

                    list = db.reports.Where(m => m.deleted == 0 && m.point_id == p && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 4).ToList();
                }
                else if (level != 0)
                {
                    list = db.reports.Where(m => m.deleted == 0 && m.point_id == p && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 1).ToList();

                }
               
                list = list.Where(m => m.report_time > dateTime).ToList();

                list = list.Where(m => m.report_time < dateTime_2).ToList();

                for (int i = 0; i < list.Count; i++)
                {
                    report_detail info = new report_detail();
                    info = db.report_details.Single(m => m.report_id == list[i].uid && m.field_id == long.Parse(listfield));
                    listdetails.Add(info);
                }
            }
            catch (System.Exception ex)
            {

            }
            return listdetails;
        }


        public string FindTableByid(string disease_table)
        {
            string name="";
            try
            {
                name = db.forms.Where(m => m.uid == long.Parse(disease_table)).FirstOrDefault().name;
            }
            catch (System.Exception ex)
            {
            	
            }
            return name;
        }

        internal List<xian> Findxian(string shengid, long p)
        {
            List<xian> list = new List<xian>();
            try
            {
                if (p != 0)
                {
                    list = db.xians.Where(m => m.shi_id == p && m.deleted == 0).ToList();
                }
                else
                {   List<shi> lists=db.shis.Where(m=>m.sheng_id==long.Parse(shengid)).ToList();
                    for ( int i=0;i<lists.Count;i++)
                    {
                    list.AddRange(db.xians.Where(m => m.shi_id == lists[i].uid && m.deleted == 0).ToList());
                    }
                  
                }
               
            }
            catch (System.Exception e)
            {
                e.ToString();
            }
            return list;
        }

        public form FindForms(string disease_table)
        { 
            form frms=db.forms.Where(m=>m.uid==long.Parse(disease_table)).FirstOrDefault();

            return frms;
        }

        public string GetAllHtml(string shengid, string shiid, string xianid, string point_level, string point_kind, string starttime, string endtime, string point_id, string disease_id, string disease_table)
        {
               string html = "";
            int num = 0;
            try
            {
                List<report> listreport = new List<report>();
                List<report> list = new List<report>();
                if (point_id == "0")
                {
                    List<point> pointlist = findDiseasePoint(shengid, shiid, xianid, point_level,point_kind);
                    for (int i = 0; i < pointlist.Count; i++)
                    {
                        if (point_level=="0")
                        {

                            list.AddRange(db.reports.Where(m => m.deleted == 0 && m.point_id == pointlist[i].uid && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 4).ToList());

                        }
                        else if (point_level != "0")
                        {
                            list.AddRange(db.reports.Where(m => m.deleted == 0 && m.point_id == pointlist[i].uid && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 1).ToList());

                        }

                    }
                }
                else
                {
                      if (point_level=="0")
                        {

                    list = db.reports.Where(m => m.deleted == 0 && m.point_id == long.Parse(point_id) && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 4).ToList();
                    } else if (point_level != "0")
                        {
                            list = db.reports.Where(m => m.deleted == 0 && m.point_id == long.Parse(point_id) && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 1).ToList();
                   
                        }
                }
                if (starttime != "" && starttime != null)
                {
                    list = list.Where(m => m.report_time > DateTime.Parse(starttime)).ToList();
                }
                if (endtime != "" && endtime != null)
                {
                    list = list.Where(m => m.report_time < DateTime.Parse(endtime)).ToList();
                }
                long tableid = long.Parse(disease_table);

                List<FieldInfo> allFiellist = new List<FieldInfo>();
                List<FieldInfo> mastFieldlist = new List<FieldInfo>();
                List<FieldInfo> restFieldlist = new List<FieldInfo>();

                //1.获得数据库的表格信息：tableinfo
                var tableinfo = db.forms.Where(m => m.deleted == 0 && m.uid == tableid).FirstOrDefault();

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

                mastFieldlist = allFiellist.Where(m => m.parentid == null).ToList();

                foreach (var n in allFiellist)
                {
                    if (!mastFieldlist.Contains(n))
                    {
                        restFieldlist.Add(n);
                    }
                }
                int y = tabledeep(mastFieldlist, restFieldlist);
                var outprintlist = HandleField(y, mastFieldlist, restFieldlist);
               // html = GenerateHtml(outprintlist, y);

                List<FieldInfo> childrenist = new List<FieldInfo>();
                childrenist = GenerateChild(outprintlist, allFiellist, y);
               
                   html += "<tr>";
                   html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'>"+tableinfo.name+" </td>";
                   html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                   html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                   html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                   html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                   html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                   html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                   html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                   html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                   html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                   html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                   html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                  html += "</tr>"; 
               
                for (int i = 0; i < childrenist.Count;i++ )
                {
                    //												
    
                    List<long> pois = new List<long>();
                    for (int j = 0; j < list.Count; j++)
                    {
                        if (j == 0)
                        {
                            pois.Add(list[j].point_id);
                        }
                        else
                        {
                            int number = 0;
                            for (int k = 0; k < pois.Count; k++)
                            {
                                if (pois[k] == list[j].point_id)
                                {
                                    number = 1;
                                }
                            }
                            if (number!=1)
                            {
                                pois.Add(list[j].point_id);
                            }
                        }
                    }
                    if (childrenist[i].type == "i")
                    {
                        html += "<tr>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "</tr>"; 
                        html += "<tr>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'>" + childrenist[i].name + "</td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "</tr>";
                        html += "<tr>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>省市 </td>";
                        html += "<td colspan=' '  id='region' style='width:20%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>旗县 </td>";
                        html += "<td colspan=' '  id='region' style='width:20%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>测报点 </td>";
                        html += "<td  colspan=' ' id='point_name' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> 最小值</td>";
                        html += "<td  colspan=' ' id='point_name' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> 最大值</td>";
                        html += "<td  colspan=' ' id='point_name' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> 平均值</td>";
                        html += "<td  colspan=' ' id='point_name' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> 合计</td>";
                        html += "<td  colspan=' ' id='point_name' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> 数据量</td>";
                        html += "<td  colspan=' ' id='point_name' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> 总最小值</td>";
                        html += "<td  colspan=' ' id='point_name' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> 总最大值</td>";
                        html += "<td  colspan=' ' id='point_name' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> 总平均值</td>";
                        html += "<td  colspan=' ' id='point_name' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> 总合计</td>";
                        html += "<td  colspan=' ' id='point_name' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> 数据总量</td>";
                        html += "</tr>";

                        List<report_detail> detail1 = new List<report_detail>();
                        for (int j = 0; j < pois.Count; j++)
                        {
                            listreport = list.Where(m => m.point_id == pois[j]).ToList();

                            for (int k = 0; k < listreport.Count; k++)
                            {
                                detail1.AddRange(db.report_details.Where(m => m.deleted == 0 && m.report_id == listreport[k].uid && m.field_id == childrenist[i].uid));
                            }
                        }
                        for (int j = 0; j < pois.Count; j++)
                        {
                            listreport = list.Where(m => m.point_id == pois[j]).ToList();
                            List<report_detail> detail = new List<report_detail>();
                            for (int k = 0; k < listreport.Count; k++)
                            {
                                detail.AddRange(db.report_details.Where(m => m.deleted == 0 && m.report_id == listreport[k].uid && m.field_id == childrenist[i].uid));
                            }
                            if (j == 0)
                            {
                                long? min = 0;
                                long? max = 0;
                                long? total = 0;
                                for (int k = 0; k < detail.Count; k++)
                                {


                                    if (k == 0)
                                    {
                                        min = detail[0].value_integer;
                                        max = detail[0].value_integer;
                                    }
                                    else
                                    {
                                        if (detail[k].value_integer < min)
                                        {
                                            min = detail[k].value_integer;
                                        }
                                        if (detail[k].value_integer > max)
                                        {
                                            max = detail[k].value_integer;
                                        }
                                    }
                                    total += detail[k].value_integer;
                                }
                                long? avg = total / detail.Count;
                                long? number = detail.Count;
                                long? totalmin = 0;
                                long? totalmax = 0;

                                long? totaln = 0;
                                for (int k = 0; k < detail1.Count; k++)
                                {


                                    if (k == 0)
                                    {
                                        totalmin = detail1[0].value_integer;
                                        totalmax = detail1[0].value_integer;
                                    }
                                    else
                                    {
                                        if (detail1[k].value_integer < totalmin)
                                        {
                                            totalmin = detail1[k].value_integer;
                                        }
                                        if (detail1[k].value_integer > totalmax)
                                        {
                                            totalmax = detail1[k].value_integer;
                                        }
                                    }
                                    totaln += detail1[k].value_integer;
                                }
                                long? totalavg = totaln / detail1.Count;
                                int totalnumber = detail1.Count;
                                html += "<tr>";
                                point pointinfo = db.points.Where(m => m.deleted == 0 && m.uid == pois[j]).FirstOrDefault();
                                sheng shenginfo = db.shengs.Where(m => m.deleted == 0 && m.uid == pointinfo.sheng_id).FirstOrDefault();
                                shi shiinfo = db.shis.Where(m => m.deleted == 0 && m.uid == pointinfo.shi_id).FirstOrDefault();
                                xian xianinfo = db.xians.Where(m => m.deleted == 0 && m.uid == pointinfo.xian_id).FirstOrDefault();
                                point point = db.points.Where(m => m.deleted == 0 && m.uid == pois[j]).FirstOrDefault();
                                html += "<td id='point_name' style='border: 1px solid #8D8B8B;text-align:center;vertical-align:middle' >" + shenginfo.name + "" + shiinfo.name + "</td>";
                                html += "<td id='region' style='border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>" + xianinfo.name + "</td>";
                                html += "<td id='point_name' style='border: 1px solid #8D8B8B;text-align:center;vertical-align:middle' >" + point.name + "</td>";
                                html += "<td  rowspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>" + min + "</td>";
                                html += "<td  rowspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> " + max + "</td>";
                                html += "<td  rowspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>" + avg + " </td>";
                                html += "<td  rowspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>" + total + " </td>";
                                html += "<td  rowspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> " + number + "</td>";
                                html += "<td  rowspan='" + pois.Count + " ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> " + totalmin + "</td>";
                                html += "<td  rowspan='" + pois.Count + " ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> " + totalmax + "</td>";
                                html += "<td  rowspan='" + pois.Count + "' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>" + totalavg + " </td>";
                                html += "<td  rowspan='" + pois.Count + " ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> " + totaln + "</td>";
                                html += "<td  rowspan='" + pois.Count + " ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>" + totalnumber + " </td>";
                                html += "</tr>";
                            }
                            else
                            {

                                long? min = 0;
                                long? max = 0;
                                long? total = 0;
                                for (int k = 0; k < detail.Count; k++)
                                {


                                    if (k == 0)
                                    {
                                        min = detail[0].value_integer;
                                        max = detail[0].value_integer;
                                    }
                                    else
                                    {
                                        if (detail[k].value_integer < min)
                                        {
                                            min = detail[k].value_integer;
                                        }
                                        if (detail[k].value_integer > max)
                                        {
                                            max = detail[k].value_integer;
                                        }
                                    }
                                    total += detail[k].value_integer;
                                }
                                long? avg = total / detail.Count;
                                long? number = detail.Count;
                                long? totalmin = 0;
                                long? totalmax = 0;

                                long? totaln = 0;
                                for (int k = 0; k < detail1.Count; k++)
                                {


                                    if (k == 0)
                                    {
                                        totalmin = detail1[0].value_integer;
                                        totalmax = detail1[0].value_integer;
                                    }
                                    else
                                    {
                                        if (detail1[k].value_integer < totalmin)
                                        {
                                            totalmin = detail1[k].value_integer;
                                        }
                                        if (detail1[k].value_integer > totalmax)
                                        {
                                            totalmax = detail1[k].value_integer;
                                        }
                                    }
                                    totaln += detail1[k].value_integer;
                                }
                                long? totalavg = totaln / detail1.Count;
                                int totalnumber = detail1.Count;
                                html += "<tr>";
                                point pointinfo = db.points.Where(m => m.deleted == 0 && m.uid == pois[j]).FirstOrDefault();
                                sheng shenginfo = db.shengs.Where(m => m.deleted == 0 && m.uid == pointinfo.sheng_id).FirstOrDefault();
                                shi shiinfo = db.shis.Where(m => m.deleted == 0 && m.uid == pointinfo.shi_id).FirstOrDefault();
                                xian xianinfo = db.xians.Where(m => m.deleted == 0 && m.uid == pointinfo.xian_id).FirstOrDefault();
                                point point = db.points.Where(m => m.deleted == 0 && m.uid == pois[j]).FirstOrDefault();
                                html += "<td id='point_name' style='border: 1px solid #8D8B8B;text-align:center;vertical-align:middle' >" + shenginfo.name + "" + shiinfo.name + "</td>";
                                html += "<td id='region' style='border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>" + xianinfo.name + "</td>";
                                html += "<td id='point_name' style='border: 1px solid #8D8B8B;text-align:center;vertical-align:middle' >" + point.name + "</td>";
                                html += "<td  rowspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>" + min + "</td>";
                                html += "<td  rowspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> " + max + "</td>";
                                html += "<td  rowspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>" + avg + " </td>";
                                html += "<td  rowspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>" + total + " </td>";
                                html += "<td  rowspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> " + number + "</td>";
                                html += "</tr>";
                            }

                        }
                    }
                    if (childrenist[i].type=="r")
                    {
                        html += "<tr>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'>" + childrenist[i].name + "</td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                        html += "</tr>";
                        html += "<tr>";
                        html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>省市 </td>";
                        html += "<td colspan=' '  id='region' style='width:20%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>旗县 </td>";

                        html += "<td colspan=' '  id='region' style='width:20%;border: 1px solid #DDD;text-align:center;vertical-align:middle'>测报点 </td>";
                        html += "<td  colspan=' ' id='point_name' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> 最小值</td>";
                        html += "<td  colspan=' ' id='point_name' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> 最大值</td>";
                        html += "<td  colspan=' ' id='point_name' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> 平均值</td>";
                        html += "<td  colspan=' ' id='point_name' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> 合计</td>";
                        html += "<td  colspan=' ' id='point_name' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> 数据量</td>";
                        html += "<td  colspan=' ' id='point_name' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> 总最小值</td>";
                        html += "<td  colspan=' ' id='point_name' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> 总最大值</td>";
                        html += "<td  colspan=' ' id='point_name' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> 总平均值</td>";
                        html += "<td  colspan=' ' id='point_name' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> 总合计</td>";
                        html += "<td  colspan=' ' id='point_name' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> 数据总量</td>";
                        html += "</tr>";
                        List<report_detail> detail1 = new List<report_detail>();
                        for (int j = 0; j < pois.Count; j++)
                        {
                            listreport = list.Where(m => m.point_id == pois[j]).ToList();

                            for (int k = 0; k < listreport.Count; k++)
                            {
                                detail1.AddRange(db.report_details.Where(m => m.deleted == 0 && m.report_id == listreport[k].uid && m.field_id == childrenist[i].uid));
                            }
                        }
                        for (int j = 0; j < pois.Count; j++)
                        {
                            listreport = list.Where(m => m.point_id == pois[j]).ToList();
                            List<report_detail> detail = new List<report_detail>();
                            for (int k = 0; k < listreport.Count; k++)
                            {
                                detail.AddRange(db.report_details.Where(m => m.deleted == 0 && m.report_id == listreport[k].uid && m.field_id == childrenist[i].uid));
                            }
                            if (j == 0)
                            {
                                double? min = 0;
                                double? max = 0;
                                double? total = 0;
                                for (int k = 0; k < detail.Count; k++)
                                {


                                    if (k == 0)
                                    {
                                        min = detail[0].value_real;
                                        max = detail[0].value_real;
                                    }
                                    else
                                    {
                                        if (detail[k].value_real < min)
                                        {
                                            min = detail[k].value_real;
                                        }
                                        if (detail[k].value_real > max)
                                        {
                                            max = detail[k].value_real;
                                        }
                                    }
                                    total += detail[k].value_real;
                                }
                                double? avg = total / detail.Count;
                                double? number = detail.Count;
                                double? totalmin = 0;
                                double? totalmax = 0;

                                double? totaln = 0;
                                for (int k = 0; k < detail1.Count; k++)
                                {


                                    if (k == 0)
                                    {
                                        totalmin = detail1[0].value_real;
                                        totalmax = detail1[0].value_real;
                                    }
                                    else
                                    {
                                        if (detail1[k].value_real < totalmin)
                                        {
                                            totalmin = detail1[k].value_real;
                                        }
                                        if (detail1[k].value_real > totalmax)
                                        {
                                            totalmax = detail1[k].value_real;
                                        }
                                    }
                                    totaln += detail1[k].value_real;
                                }
                                double? totalavg = totaln / detail1.Count;
                                int totalnumber = detail1.Count;
                                html += "<tr>";
                                point pointinfo = db.points.Where(m => m.deleted == 0 && m.uid == pois[j]).FirstOrDefault();
                                sheng shenginfo = db.shengs.Where(m => m.deleted == 0 && m.uid == pointinfo.sheng_id).FirstOrDefault();
                                shi shiinfo = db.shis.Where(m => m.deleted == 0 && m.uid == pointinfo.shi_id).FirstOrDefault();
                                xian xianinfo = db.xians.Where(m => m.deleted == 0 && m.uid == pointinfo.xian_id).FirstOrDefault();
                                point point = db.points.Where(m => m.deleted == 0 && m.uid == pois[j]).FirstOrDefault();
                                html += "<td id='point_name' style='border: 1px solid #8D8B8B;text-align:center;vertical-align:middle' >" + shenginfo.name + "" + shiinfo.name + "</td>";
                                html += "<td id='region' style='border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>" + xianinfo.name + "</td>";
                                html += "<td id='point_name' style='border: 1px solid #8D8B8B;text-align:center;vertical-align:middle' >" + point.name + "</td>";
                                html += "<td  rowspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>" + min + "</td>";
                                html += "<td  rowspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> " + max + "</td>";
                                html += "<td  rowspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>" + avg + " </td>";
                                html += "<td  rowspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>" + total + " </td>";
                                html += "<td  rowspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> " + number + "</td>";
                                html += "<td  rowspan='" + pois.Count + " ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> " + totalmin + "</td>";
                                html += "<td  rowspan='" + pois.Count + " ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> " + totalmax + "</td>";
                                html += "<td  rowspan='" + pois.Count + "' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>" + totalavg + " </td>";
                                html += "<td  rowspan='" + pois.Count + " ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> " + totaln + "</td>";
                                html += "<td  rowspan='" + pois.Count + " ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>" + totalnumber + " </td>";
                                html += "</tr>";
                            }
                            else
                            {

                                double? min = 0;
                                double? max = 0;
                                double? total = 0;
                                for (int k = 0; k < detail.Count; k++)
                                {


                                    if (k == 0)
                                    {
                                        min = detail[0].value_real;
                                        max = detail[0].value_real;
                                    }
                                    else
                                    {
                                        if (detail[k].value_real < min)
                                        {
                                            min = detail[k].value_real;
                                        }
                                        if (detail[k].value_real > max)
                                        {
                                            max = detail[k].value_real;
                                        }
                                    }
                                    total += detail[k].value_real;
                                }
                                double? avg = total / detail.Count;
                                double? number = detail.Count;
                                double? totalmin = 0;
                                double? totalmax = 0;

                                double? totaln = 0;
                                for (int k = 0; k < detail1.Count; k++)
                                {


                                    if (k == 0)
                                    {
                                        totalmin = detail1[0].value_real;
                                        totalmax = detail1[0].value_real;
                                    }
                                    else
                                    {
                                        if (detail1[k].value_real < totalmin)
                                        {
                                            totalmin = detail1[k].value_real;
                                        }
                                        if (detail1[k].value_real > totalmax)
                                        {
                                            totalmax = detail1[k].value_real;
                                        }
                                    }
                                    totaln += detail1[k].value_real;
                                }
                                double? totalavg = totaln / detail1.Count;
                                int totalnumber = detail1.Count;
                                html += "<tr>";
                                point pointinfo = db.points.Where(m => m.deleted == 0 && m.uid == pois[j]).FirstOrDefault();
                                sheng shenginfo = db.shengs.Where(m => m.deleted == 0 && m.uid == pointinfo.sheng_id).FirstOrDefault();
                                shi shiinfo = db.shis.Where(m => m.deleted == 0 && m.uid == pointinfo.shi_id).FirstOrDefault();
                                xian xianinfo = db.xians.Where(m => m.deleted == 0 && m.uid == pointinfo.xian_id).FirstOrDefault();
                                point point = db.points.Where(m => m.deleted == 0 && m.uid == pois[j]).FirstOrDefault();
                                html += "<td id='point_name' style='border: 1px solid #8D8B8B;text-align:center;vertical-align:middle' >" + shenginfo.name + "" + shiinfo.name + "</td>";
                                html += "<td id='region' style='border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>" + xianinfo.name + "</td>";
                                html += "<td id='point_name' style='border: 1px solid #8D8B8B;text-align:center;vertical-align:middle' >" + point.name + "</td>";
                                html += "<td  rowspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>" + min + "</td>";
                                html += "<td  rowspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> " + max + "</td>";
                                html += "<td  rowspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>" + avg + " </td>";
                                html += "<td  rowspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'>" + total + " </td>";
                                html += "<td  rowspan=' ' id='report_time' style='width:15%;border: 1px solid #8D8B8B;text-align:center;vertical-align:middle'> " + number + "</td>";
                                html += "</tr>";
                            }

                        }
                    }
                    html += "<tr>";
                    html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                    html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                    html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                    html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                    html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                    html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                    html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                    html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                    html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                    html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                    html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                    html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                    html += "<td  colspan=' ' id='report_time' style='width:15%;border: 1px solid #DDD;text-align:center;vertical-align:middle'> </td>";
                    html += "</tr>"; 
                }

            }catch(Exception  e){

            }
            return html;
        }

        internal List<shi> FindShi(string shengid)
        {
            List<shi> list = db.shis.Where(m => m.sheng_id == long.Parse(shengid)&&m.deleted==0).ToList();
            return list;
        }

        internal List<xian> FindXian(string shiid)
        {
            List<xian> list = db.xians.Where(m => m.shi_id == long.Parse(shiid) && m.deleted == 0).ToList();
            return list;
        }

        internal List<sheng> FindSheng()
        {
            List<sheng> list = db.shengs.Where(m =>  m.deleted == 0).ToList();
            return list;
        }

        internal List<report_detail> findShengReportDetails1(long shengid,string point_level, string disease_id, string disease_table, string listfield, List<report_detail> listdetails, DateTime dateTime, DateTime dateTime_2)
        {
            try
            {
                List<report> list = new List<report>();
                List<point> point = db.points.Where(m => m.sheng_id == shengid && m.deleted == 0 && m.level == byte.Parse(point_level)).ToList();
                for (int i = 0; i < point.Count;i++ )
                {
                    if (point_level == "0")
                    {

                        list .AddRange(db.reports.Where(m => m.deleted == 0 && m.point_id == point[i].uid && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 4).ToList());
                    }
                    else if (point_level != "0")
                    {
                        list .AddRange(db.reports.Where(m => m.deleted == 0 && m.point_id == point[i].uid && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 1).ToList());

                    }
                }
          

                list = list.Where(m => m.report_time > dateTime).ToList();

                list = list.Where(m => m.report_time < dateTime_2).ToList();

                for (int i = 0; i < list.Count; i++)
                {
                    report_detail info = new report_detail();
                    info = db.report_details.Single(m => m.report_id == list[i].uid && m.field_id == long.Parse(listfield));
                    listdetails.Add(info);
                }
            }
            catch (System.Exception ex)
            {

            }
            return listdetails;
        }
        internal List<report_detail> findPointReportDetails1(long pointid, string point_level, string disease_id, string disease_table, string listfield, List<report_detail> listdetails, DateTime dateTime, DateTime dateTime_2)
        {
            try
            {
                List<report> list = new List<report>();
                List<point> point = db.points.Where(m => m.uid==pointid && m.deleted == 0 && m.level == byte.Parse(point_level)).ToList();
                for (int i = 0; i < point.Count; i++)
                {
                    if (point_level == "0")
                    {

                        list.AddRange(db.reports.Where(m => m.deleted == 0 && m.point_id == point[i].uid && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 4).ToList());
                    }
                    else if (point_level != "0")
                    {
                        list.AddRange(db.reports.Where(m => m.deleted == 0 && m.point_id == point[i].uid && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 1).ToList());

                    }
                }


                list = list.Where(m => m.report_time > dateTime).ToList();

                list = list.Where(m => m.report_time < dateTime_2).ToList();

                for (int i = 0; i < list.Count; i++)
                {
                    report_detail info = new report_detail();
                    info = db.report_details.Single(m => m.report_id == list[i].uid && m.field_id == long.Parse(listfield));
                    listdetails.Add(info);
                }
            }
            catch (System.Exception ex)
            {

            }
            return listdetails;
        }
        internal List<report_detail> findShiReportDetails1(long shiid, string point_level, string disease_id, string disease_table, string listfield, List<report_detail> listdetails, DateTime dateTime, DateTime dateTime_2)
        {
            try
            {
                List<report> list = new List<report>();
                List<point> point = db.points.Where(m => m.shi_id == shiid && m.deleted == 0&&m.level==byte.Parse(point_level)).ToList();
                for (int i = 0; i < point.Count; i++)
                {
                    if (point_level == "0")
                    {

                        list.AddRange(db.reports.Where(m => m.deleted == 0 && m.point_id == point[i].uid && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 4).ToList());
                    }
                    else if (point_level != "0")
                    {
                        list.AddRange(db.reports.Where(m => m.deleted == 0 && m.point_id == point[i].uid && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 1).ToList());

                    }
                }


                list = list.Where(m => m.report_time > dateTime).ToList();

                list = list.Where(m => m.report_time < dateTime_2).ToList();

                for (int i = 0; i < list.Count; i++)
                {
                    report_detail info = new report_detail();
                    info = db.report_details.Single(m => m.report_id == list[i].uid && m.field_id == long.Parse(listfield));
                    listdetails.Add(info);
                }
            }
            catch (System.Exception ex)
            {

            }
            return listdetails;
        }

        internal List<report_detail> findXianReportDetails1(long xianid, string point_level, string disease_id, string disease_table, string listfield, List<report_detail> listdetails, DateTime dateTime, DateTime dateTime_2)
        {
            try
            {
                List<report> list = new List<report>();
                List<point> point = db.points.Where(m => m.xian_id == xianid && m.deleted == 0 && m.level == byte.Parse(point_level)).ToList();
                for (int i = 0; i < point.Count; i++)
                {
                    if (point_level == "0")
                    {

                        list.AddRange(db.reports.Where(m => m.deleted == 0 && m.point_id == point[i].uid && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 4).ToList());
                    }
                    else if (point_level != "0")
                    {
                        list.AddRange(db.reports.Where(m => m.deleted == 0 && m.point_id == point[i].uid && m.blight_id == long.Parse(disease_id) && m.form_id == long.Parse(disease_table) && m.review_state == 1).ToList());

                    }
                }


                list = list.Where(m => m.report_time > dateTime).ToList();

                list = list.Where(m => m.report_time < dateTime_2).ToList();

                for (int i = 0; i < list.Count; i++)
                {
                    report_detail info = new report_detail();
                    info = db.report_details.Single(m => m.report_id == list[i].uid && m.field_id == long.Parse(listfield));
                    listdetails.Add(info);
                }
            }
            catch (System.Exception ex)
            {

            }
            return listdetails;
        }

        public List<point> findDiseasePointAll(string shengid, string shiid, string xianid, string point_level)
        {
            List<point> list = new List<point>();
            try
            {

                list = db.points.Where(m => m.deleted == 0 && m.level == int.Parse(point_level)).ToList();
                if (shengid != "0")
                {
                    list = list.Where(m => m.sheng_id == long.Parse(shengid)).ToList();
                }
                if (shiid != "0")
                {
                    list = list.Where(m => m.shi_id == long.Parse(shiid)).ToList();
                }
                if (xianid != "0")
                {
                    list = list.Where(m => m.xian_id == long.Parse(xianid)).ToList();
                }
            }
            catch (System.Exception ex)
            {

            }
            return list;
        }
    }
}
