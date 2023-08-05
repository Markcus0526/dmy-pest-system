using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Security;
using NPOI.SS.UserModel;
using System.IO;

namespace BingChongBackend.Models
{
    public class UDIM
    {
        public long id { get; set; }//上报信息id
        public long form_id { get; set; }//上报表格id
        public long user_id { get; set; }//上报员id
        public long point_id { get; set; }//侧报点id
        public int level { get; set; }//测报点等级
        public int type { get; set; }//测报点类型
        public string pointname { get; set; }//测报点名称
        public byte blight_kind { get; set; }//病虫害类型
        public string blight_name { get; set; }//病虫害名称
        public string photo { get; set; }//照片
        public DateTime watch_time { get; set; }//测报日期
        public DateTime report_time { get; set; }//上报日期
        public byte review_state { get; set; }//审核状态
        public string review_log { get; set; }//审核日志
        public string sheng { get; set; }//省
        public string shi { get; set; }//市
        public string xian { get; set; }//县
        public string user_name { get; set; }//测报员姓名
        public string form_name { get; set; }//表格名称
        public long shengid { get; set; }//省id
        public long shiid { get; set; }//市id
        public long xianid { get; set; }//县id
        public long blight_id { get; set; }//病虫害id
        public decimal longitude { get; set; }//经度
        public decimal latitude { get; set; }//纬度





    }
    public class UpDataInfoModel
    {
        BingChongDBDataContext db = new BingChongDBDataContext();
        //获得省的信息
        public List<sheng> GetSheng()
        {
            List<sheng> shengs = db.shengs.Where(a => a.deleted == 0&&a.status==1).ToList();


            return shengs;
        }
        //获得市的信息
        public List<shi> GetShi()
        {

            List<shi> shis = db.shis.Where(a => a.deleted == 0&&a.status==1).ToList();
            return shis;

        }
        //获得区县
        public List<xian> GetXian()
        {

            List<xian> xians = db.xians.Where(a => a.deleted == 0&&a.status==1).ToList();
            return xians;



        }
        //获得全部的测报点信息

        public List<point> GetPoint()
        {

            List<point> points = db.points.Where(a => a.deleted == 0).ToList();
            return points;


        }
        //获得全部的测报数据
        public List<UDIM> GetUpDataInfo()
        {
            List<UDIM> upinfo = new List<UDIM>();
            ///FormsIdentity fId = (FormsIdentity)HttpContext.Current.User.Identity;
            //FormsAuthenticationTicket authTicket = fId.Ticket;
            var list = db.reports.Where(r => r.deleted == 0).Join(db.points, r => r.point_id, p => p.uid, (r, p) => new { r1 = r, p1 = p }).Join(db.users, r2 => r2.r1.user_id, u => u.uid, (r2, u) => new { r3 = r2, u1 = u }).Join(db.forms, r4 => r4.r3.r1.form_id, f => f.uid, (r4, f) => new { r5 = r4, f1 = f }).Join(db.shengs, r6 => r6.r5.r3.p1.sheng_id, s => s.uid, (r6, s) => new { r7 = r6, s1 = s }).Join(db.shis, r8 => r8.r7.r5.r3.p1.shi_id, ss => ss.uid, (r8, ss) => new { r9 = r8, ss1 = ss }).Join(db.xians, r10 => r10.r9.r7.r5.r3.p1.xian_id, x => x.uid, (r10, x) => new { r11 = r10, x1 = x }).Join(db.blights, r12 => r12.r11.r9.r7.r5.r3.r1.blight_id, bl => bl.uid, (r12, bl) => new { r13 = r12, b1 = bl }).Select(rlist => new UDIM
            {
                id = rlist.r13.r11.r9.r7.r5.r3.r1.uid,
                form_id = rlist.r13.r11.r9.r7.r5.r3.r1.form_id,
                user_id = rlist.r13.r11.r9.r7.r5.r3.r1.user_id,
                point_id = rlist.r13.r11.r9.r7.r5.r3.r1.point_id,
                level = rlist.r13.r11.r9.r7.r5.r3.p1.level,
                type = rlist.r13.r11.r9.r7.r5.r3.p1.type,
                pointname = rlist.r13.r11.r9.r7.r5.r3.p1.name,
                blight_kind = rlist.b1.kind,
                blight_id = rlist.r13.r11.r9.r7.r5.r3.r1.blight_id,
                blight_name = rlist.b1.name,
                photo = rlist.r13.r11.r9.r7.r5.r3.r1.photo,
                watch_time = rlist.r13.r11.r9.r7.r5.r3.r1.watch_time,
                report_time = rlist.r13.r11.r9.r7.r5.r3.r1.report_time,
                review_state = rlist.r13.r11.r9.r7.r5.r3.r1.review_state,
                sheng = rlist.r13.r11.r9.s1.name,
                shengid = rlist.r13.r11.r9.s1.uid,
                shi = rlist.r13.r11.ss1.name,
                shiid = rlist.r13.r11.ss1.uid,
                xian = rlist.r13.x1.name,
                xianid = rlist.r13.x1.uid,
                user_name = rlist.r13.r11.r9.r7.r5.u1.realname,
                form_name = rlist.r13.r11.r9.r7.f1.name,
                longitude = rlist.r13.r11.r9.r7.r5.r3.p1.longitude,
                latitude = rlist.r13.r11.r9.r7.r5.r3.p1.latitude



            }).ToList();
            upinfo = list;
            return upinfo;


        }
        //查询数据的获得
        public List<UDIM> GetSearchList( long shengid, long shiid, long xianid, byte level, byte type, long pointid, byte kindid, long nameid, long formid, int stat,DateTime starttime,DateTime endtime)
        {
            List<UDIM> baselist = new List<UDIM>();
            long userid = CommonModel.GetSessionUserID();//获得用户id 根据id查询出用户级别
            user users = new BingChongDBDataContext().users.Single(a => a.uid == userid);
            long rid = users.right_id;
            string userlevel = users.level.ToString();//获得级别
            if (stat == 0)
            {
                baselist = GetUpDataInfo().Where(a => a.level == level && a.type == type&&a.report_time<=endtime&&starttime<=a.report_time).ToList();
            }
            else
            {
                if (userlevel == "2")
                {
                    if (stat == 3)
                    {
                        baselist = GetUpDataInfo().Where(a => a.level == level && a.type == type && a.report_time <= endtime && starttime <= a.report_time && (a.review_state == 3||a.review_state==5)).ToList();
                    }
                    else
                    {

                        baselist = GetUpDataInfo().Where(a => a.level == level && a.type == type && a.report_time <= endtime && starttime <= a.report_time && a.review_state == stat).ToList();
                    }
                }

                else {
                    baselist = GetUpDataInfo().Where(a => a.level == level && a.type == type && a.report_time <= endtime && starttime <= a.report_time && a.review_state == stat).ToList();
                }
                if (userlevel == "1")
                {
                    if (stat == 1)
                    {
                        baselist = GetUpDataInfo().Where(a => a.level == level && a.type == type && a.report_time <= endtime && starttime <= a.report_time && (a.review_state == 1 || a.review_state == 2)).ToList();
                    }
                    else if (stat==3)
                        {
                            baselist = GetUpDataInfo().Where(a => a.level == level && a.type == type && a.report_time <= endtime && starttime <= a.report_time && (a.review_state == 3 || a.review_state == 5)).ToList();
                        }else if(stat==4){
                            baselist = GetUpDataInfo().Where(a => a.level == level && a.type == type && a.report_time <= endtime && starttime <= a.report_time && (a.review_state == 4)).ToList();

                        }
                 
                }

                else
                {
                    baselist = GetUpDataInfo().Where(a => a.level == level && a.type == type && a.report_time <= endtime && starttime <= a.report_time && a.review_state == stat).ToList();
                }


                
            }

            List<UDIM> finallist = new List<UDIM>();
            //先区分测报点选了么
            if (pointid == 0)
            {
                //全部
                //1.就要用到省，市，县，测报点级别，测报点类型来获得测报点信息的满足这些条件的信息
                if (shengid == 0)
                {
                    finallist = baselist;
                }
                if (shengid > 0)
                {
                    baselist = baselist.Where(a => a.shengid == shengid).ToList();
                }
                if (shiid > 0)
                {
                    baselist = baselist.Where(a => a.shiid == shiid).ToList();
                }
                if (xianid > 0)
                {
                    baselist = baselist.Where(a => a.xianid == xianid).ToList();
                }

                //2.区分病虫种类选了么 没选就不用确定病虫id跟表格id了选了 要看病虫id是不是选了 没选不判断表格 选了 看表格的选了么 选了就判断 没选就不判断
                if (kindid != 2)
                {
                    baselist = baselist.Where(a => a.blight_kind == kindid).ToList();
                }
                if (nameid > 0)
                {
                    baselist = baselist.Where(a => a.blight_id == nameid).ToList();

                }
                if (formid > 0)
                {
                    baselist = baselist.Where(a => a.form_id == formid).ToList();
                }

                //if (stat > 0)
                //{
                //    baselist = baselist.Where(a => a.review_state == stat).ToList();
                //}

            }
            else
            {
                //选择了一个测报点 就用
                //2.区分病虫种类选了么 没选就不用确定病虫id跟表格id了选了 要看病虫id是不是选了 没选不判断表格 选了 看表格的选了么 选了就判断 没选就不判断
                baselist = baselist.Where(a => a.point_id == pointid).ToList();
                if (kindid != 2)
                {
                    baselist = baselist.Where(a => a.blight_kind == kindid).ToList();
                }
                if (nameid > 0)
                {
                    baselist = baselist.Where(a => a.blight_id == nameid).ToList();

                }
                if (formid > 0)
                {
                    baselist = baselist.Where(a => a.form_id == formid).ToList();
                }

                //if (stat > 0)
                //{
                //    baselist = baselist.Where(a => a.review_state == stat).ToList();
                //}

            }
            finallist = baselist;
            return finallist;




        }
        //删除一条数据
        public string DeletedOne(long id)
        {
            string res = "ok";
            try
            {
                report reports = db.reports.Single(a => a.uid == id);
                reports.deleted = 1;
                db.SubmitChanges();
            }
            catch (System.Exception ex)
            {
                res = ex.ToString();
            }

            return res;


        }
        //审核通过
        public string PassVerify(long rid, string reason)
        {
            string res = "ok";
            string oldlog = db.reports.Single(a => a.uid == rid).review_log==null?"":db.reports.Single(a => a.uid == rid).review_log;
            try
            {
                long userid = CommonModel.GetSessionUserID();//获得用户id
                user users = new BingChongDBDataContext().users.Single(a => a.uid == userid);
                string log = "";
                if (oldlog != "")
                {
                    log = oldlog + ";" + "管理员：" + userid + "-"+users.name+","+ DateTime.Now.ToString("yyyy/MM/dd HH:mm") + "," + "通过" + "," + "通过理由：" + reason;
                }
                else
                {

                    log = "管理员：" + userid + "-"+users.name+"," + DateTime.Now.ToString("yyyy/MM/dd HH:mm") + "," + "通过" + "," + "通过理由：" + reason;

                }
                report reports = db.reports.Single(a => a.uid == rid);
                reports.review_log = log;
                if (reports.review_state == 1)
                {
                    reports.review_state = 2;
                }
                if (reports.review_state == 2)
                {
                    if (users.level == 2)
                    {
                        reports.review_state = 2;
                    } if (users.level == 3)
                    {
                        reports.review_state = 4;
                    }

                }
                db.SubmitChanges();
            }
            catch (System.Exception ex)
            {
                res = "操作失败，请检查！";
            }

            return res;

        }
        //审核不通过
        public string NotPassVerify(long rid, string reason)
        {
            string oldlog = db.reports.Single(a => a.uid == rid).review_log == null ? "" : db.reports.Single(a => a.uid == rid).review_log;
            string res = "ok";
            try
            {
                long userid = CommonModel.GetSessionUserID();//获得用户id
                user users = new BingChongDBDataContext().users.Single(a => a.uid == userid);
                string log = "";
                if (oldlog != "")
                {
                    log = oldlog + ";" + "管理员：" + userid + "-" +users.name+","+ DateTime.Now.ToString("yyyy/MM/dd HH:mm") + "," + "不通过" + "," + "不通过理由：" + reason;
                }
                else
                {

                    log =  "管理员：" + userid + "-"+users.name+"," + DateTime.Now.ToString("yyyy/MM/dd HH:mm") + "," + "不通过" + "," + "不通过理由：" + reason;

                }

                
                report reports = db.reports.Single(a => a.uid == rid);
                reports.review_log = log;
                if (reports.review_state == 1)
                {
                    reports.review_state = 3;
                }
                if (reports.review_state == 2)
                {
                    reports.review_state = 5;
                }
                db.SubmitChanges();
            }
            catch (System.Exception ex)
            {
                res = "操作失败，请检查！";
            }

            return res;

        }
        //
       

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
        public string uGetTableHtml(long tableid, long repid,int type)
        {
            string html = "";
            List<FieldInfo> allFiellist = new List<FieldInfo>();
            List<FieldInfo> mastFieldlist = new List<FieldInfo>();
            List<FieldInfo> restFieldlist = new List<FieldInfo>();
            List<FieldInfo> allist1 = new List<FieldInfo>();
     
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
            int y = utabledeep(mastFieldlist, restFieldlist, allFiellist);
            int deep = 1;
            foreach (var n in restFieldlist)

            {
                if (deep < n.floor)
                    deep = n.floor;
            }
            var outprintlist = uHandleField(deep, mastFieldlist, restFieldlist, allFiellist);
            Handlecolspan(mastFieldlist);
            html = uGenerateHtml(outprintlist, deep, repid, allFiellist, type);
            allist1 = Getalllsit(allFiellist);
            html += getData(html, allist1, deep, repid, type);
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
        public int utabledeep(List<FieldInfo> master, List<FieldInfo> rest, List<FieldInfo> alllist)
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
                        nc.floor = n.floor+1;
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
                        y = utabledeep(childfield, restFieldlist, alllist);
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
        public List<FieldInfo> uHandleField(int deep, List<FieldInfo> master, List<FieldInfo> rest, List<FieldInfo> alllist)
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
                        uHandleField(deep, n.childrenfieldlist, restFieldlist, alllist);
                    }
                }
                else
                {
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
        public string uGenerateHtml(List<FieldInfo> list, int deep, long repid, List<FieldInfo> alllist,int type)
        {
            var html = "";
            List<FieldInfo> allist1 = new List<FieldInfo>();
            allist1 = Getalllsit(alllist);
            List<FieldInfo> childfield = new List<FieldInfo>();
            html += "<tr>";

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
                html += uGenerateHtml(childfield, deep, repid, alllist,type);
               // html += getData(html, allist1, deep, repid, type);
            }
       
            return html;
        }
   
        public List<FieldInfo> Getalllsit(List<FieldInfo> plist)
        {
            List<FieldInfo> clist = new List<FieldInfo>();
            List<FieldInfo> lc = new List<FieldInfo>();
            for (int i = 0; i < plist.Count;i++ )
            {
              


                if (plist[i].type == "p" && plist[i].parentid == null)
                {
                    clist.Add(plist[i]);
                    for (int j = 0; j < plist.Count; j++)
                    {
                        if (plist[j].parentid == plist[i].uid)
                        {
                            if (plist[j].type!="p")
                            {
                                clist.Add(plist[j]);
                            }else{

                               lc =GetalllsitHelp(plist, plist[j]);
                             
                               clist.AddRange(lc);


                            }
                           
                        }
                    }
                }
                else
                {
                    if (plist[i].type!="p"&&plist[i].parentid==null)
                    {
                        clist.Add(plist[i]);
                    }
                }
            }

            return clist;
        }
        public List<FieldInfo> GetalllsitHelp(List<FieldInfo> plist,FieldInfo p)
        {
            List<FieldInfo> clist = new List<FieldInfo>();

            for (int i = 0; i < plist.Count;i++ )
            {
                if (p.uid == plist[i].parentid)
                {
                    if (plist[i].type!="p")
                    {
                        clist.Add(plist[i]);
                    }
                    else
                    {
                        GetalllsitHelp(plist, plist[i]);
                    }
                }
            }

            return clist;
        }

        public string getData(string htmls, List<FieldInfo> alllist, int deep, long repid,int type)
        {
            List<FieldInfo> mastFieldlist = alllist.Where(a => a.parentid == null).ToList();
            List<FieldInfo> restFieldlist = new List<FieldInfo>();
            foreach (var n in alllist)
            {
                if (!mastFieldlist.Contains(n))
                {
                    restFieldlist.Add(n);
                }
            }
           // var list2 = new ExcelModel().setxyofList(0, 0, deep, mastFieldlist, restFieldlist);

            var html = "";
            List<FieldInfo> childfield = new List<FieldInfo>();
            html += "<tr>";
            //alllist = alllist.OrderBy(a => a.uid).ToList();
            List<report_detail> rdlist = db.report_details.Where(a => a.report_id == repid).ToList();
            foreach (var print in alllist)
            {
                if (print.type != "p")
                {
              foreach(var k in rdlist){
                  if (k.field_id==print.uid)
                  {
                      if (print.type == "i")
                      {
                          if (type == 0)
                          {
                              html += "<td ><input  style=width:100%; readonly=readonly name=look  id=" + print.uid + " value=" + k.value_integer + "></input></td>";
                          }
                          else
                          {
                              html += "<td ><input name=look  style=width:100% id=" + print.uid + " value=" + k.value_integer + "></input></td>";
                          }

                      } if (print.type == "r")
                      {
                          if (type == 0)
                          {
                              html += "<td ><input style=width:100%; readonly=readonly name=look  id=" + print.uid + " value=" + k.value_real + " ></input></td>";
                          }
                          else
                          {
                              html += "<td ><input style=width:100% name=look  id=" + print.uid + " value=" + k.value_real + " ></input></td>";
                          }
                      }
                      if (print.type != "i" && print.type != "r")
                      {
                          if (type == 0)
                          {
                              html += "<td ><input  style=width:100%; readonly=readonly name=look  id=" + print.uid + " value=" + k.value_text + "></input></td>";
                          }
                          else
                          {
                              html += "<td ><input  style=width:100% name=look  id=" + print.uid + " value=" + k.value_text + "></input></td>";
                          }
                      }
                  }
                    
                    }
                }

            }
            html += "</tr>";


            return html;

        }
        //获得审核日志
        public string GetLogBy(long repid)
        {
            report rp = db.reports.Single(a => a.uid == repid);
            return rp.review_log;


        }
        //修改
        public string ModifyData(long rid, long fid, string val)
        {
            string str = "ok";
            try
            {
                report_detail rep = db.report_details.Single(a => a.report_id == rid && a.field_id == fid);
                report re = db.reports.Single(a => a.uid == rid);
                
                if (rep.value_integer != null)
                {
                    rep.value_integer = long.Parse(val);
                }
                if (rep.value_real != null)
                {
                    rep.value_real = double.Parse(val);
                }
                if (rep.value_text != null)
                {
                    rep.value_text = val;
                }
                re.review_state = 1;//修改后状态应该是待市级真核
                db.SubmitChanges();
                
            }
            catch (System.Exception ex)
            {
                str = "nk";
            }

            return str;

        }
        //先获得深度 决定合并单元格操作
        public int GetFloor(long tableid, long repid)
        {

            string html = "";
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
            int y = utabledeep(mastFieldlist, restFieldlist, allFiellist);
            int deep = 1;
            foreach (var n in restFieldlist)
            {
                if (deep < n.floor)
                    deep = n.floor;
            }
            return deep;
        }
        //获得表头
        public string GetTitle(long repid)
        {
            string namestr="";
            UDIM name = GetUpDataInfo().Single(a => a.id == repid);
            namestr = name.form_name + "(" + name.sheng + "-" + name.shi + "-" + name.xian + "-" + name.pointname + ")";

            return namestr;

        }
    }

}
