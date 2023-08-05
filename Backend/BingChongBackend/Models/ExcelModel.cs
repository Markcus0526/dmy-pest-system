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
using BingChongBackend.Models;
using NPOI.HSSF.Util;



using NPOI.HSSF;
using NPOI.HSSF.UserModel;
using NPOI.HSSF.UserModel;
using NPOI.SS.UserModel;
namespace BingChongBackend.Models
{
    public class ColInfo
    {
        public int colnum { get; set; }//列号
        public string titlename { get; set; }//标题名
        public long fileid { get; set; }//字段id
        public string idtype { get; set; }

}
    public class ValueInfo
    {
        public long fid { get; set; }
        public double rval { get; set; }
        public int ival { get; set; }
        public string tval { get; set; }
        public DateTime wtime { get; set; }

    }
    public class ExcelModel
    {

        public void GetExcel(){
            BingChongDBDataContext db = new BingChongDBDataContext();
            long userid = CommonModel.GetSessionUserID();//获得用户id 根据id查询出用户级别
            user users = new BingChongDBDataContext().users.Single(a => a.uid == userid);
            string userlevel = users.level.ToString();//获得级别
            List<UDIM> list = new List<UDIM>();
            //先获得层数
            int c = new UpDataInfoModel().GetFloor(4, 2);//以4,2为例子
            List<FieldInfo> allFiellist = new List<FieldInfo>();
            List<FieldInfo> mastFieldlist = new List<FieldInfo>();
            List<FieldInfo> restFieldlist = new List<FieldInfo>();
            //1.获得数据库的表格信息：tableinfo
            var tableinfo = db.forms.Where(m => m.deleted == 0 && m.uid == 4).FirstOrDefault();
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


            int kuan = allFiellist.Where(a=>a.type!="p").ToList().Count;
            string tablename = "测试表名";
            System.Web.HttpContext curContext = System.Web.HttpContext.Current;
            curContext.Response.ContentType = "application/vnd.ms-excel";
            curContext.Response.ContentEncoding = System.Text.Encoding.UTF8;
            curContext.Response.Charset = "gb2312";
            NPOI.HSSF.UserModel.HSSFWorkbook book = new NPOI.HSSF.UserModel.HSSFWorkbook();
            NPOI.SS.UserModel.ISheet sheet = book.CreateSheet("Sheet1");
            IRow titlerow = sheet.CreateRow(0);//第0行用于存表名
          
            titlerow.CreateCell(0).SetCellValue(tablename);
            sheet.AddMergedRegion(new NPOI.SS.Util.CellRangeAddress(0,0,0,kuan-1));//合并这个单元格
            NPOI.SS.UserModel.IRow headerrow = sheet.CreateRow(2);
            ICellStyle style = book.CreateCellStyle();
           // style.Alignment = HorizontalAlignment.CENTER;
          //  style.VerticalAlignment = VerticalAlignment.CENTER;

           // ICell cell = headerrow.CreateCell(0);
            //ICell cell = headerrow.CreateCell(1);
            //3得到顶级列表之后如果不是父字段就用合并单元格
            for (int i = 0; i < mastFieldlist.Count; i++)
            {
                if (mastFieldlist[i].type != "p")
                {
                    headerrow.CreateCell(i).SetCellValue(mastFieldlist[i].name);
                    sheet.AddMergedRegion(new NPOI.SS.Util.CellRangeAddress(2, 2+c-1, i, i ));
                
                }
                else
                {
                    int cun = 0;
                    int p=i;
                    int h = 3;
                    //找到他的 子字段 在第四行
                   
                    for (int j = 0; j < restFieldlist.Count;j++ )
                    {
              
                
                         if (restFieldlist[i].parentid == mastFieldlist[i].uid)
                         {
                             cun++;
                             if (cun > 0)
                             {
                                 headerrow.CreateCell(i).SetCellValue(mastFieldlist[i].name);
                                 sheet.CreateRow(2+1).CreateCell(p).SetCellValue(restFieldlist[j].name);
                                 p++;
                                 sheet.AddMergedRegion(new NPOI.SS.Util.CellRangeAddress(2, 2, i, i + cun - 1));
                             }


                         }
                         else
                         {

                             for (int m = 0; m < restFieldlist.Count; m++)
                             {
                                 if (restFieldlist[i].parentid == restFieldlist[m].uid)
                                 {
                             
                             
                             
                                 }


                             }

                         }
                    
                        //根据cun可以算出合并几个单元格
                    
                    }
                }

                    
            }
            //sheet1.Cells.AddValueCell(1, 3, "这是标题行");
            //sheet.Cells.AddValueCell(i + 2, i + 1, "sss");
            // Response.AppendHeader("Content-Disposition", "attachment;filename=" + "qqqq随意5"+".xls");
           


            MemoryStream ms = new MemoryStream();
            book.Write(ms);
            book = null;
            ms.Close();
            ms.Dispose();
         


        }

        public void WriteSingelLine(NPOI.SS.UserModel.ISheet sheet, List<FieldInfo> masterlist, List<FieldInfo> rest, int deep)
        {/**
          * 1.进入先判断有没有这个行数被创建了
          * 2.如果没有创建一个 
          *3.如果有就获得这行 
          *4.把现在的行数传给oldnum
          *
          * 
          * 
          * 
          * 
          * **/
            int oldnum=-1;
            foreach (var n in masterlist)
            {
                NPOI.SS.UserModel.IRow headerrow1 = sheet.GetRow(n.yset + 3);

                if (oldnum != n.yset + 3&&headerrow1==null)
                {
                    NPOI.SS.UserModel.IRow headerrow = sheet.CreateRow(n.yset + 3);
                 
                    
                    headerrow.CreateCell(4 + n.xset).SetCellValue(n.name);

                }
                else {
                    NPOI.SS.UserModel.IRow headerrow = sheet.GetRow(n.yset + 3);
   
                
                    headerrow.CreateCell(4 + n.xset).SetCellValue(n.name);
                }


                oldnum = n.yset + 3;
              
                sheet.AddMergedRegion(new NPOI.SS.Util.CellRangeAddress(n.yset + 3, n.yset + 3 + n.rowspan-1, n.xset+4, n.xset+4+n.colspan-1));
             
                var childfield = rest.Where(m => m.parentid == n.uid).ToList();
                if (childfield.Count != 0)
                {
                    var restFieldlist = new List<FieldInfo>();
                    foreach (var nr in rest)
                    {
                        if (!childfield.Contains(nr))
                        {
                            restFieldlist.Add(nr);
                        }
                    }
                    WriteSingelLine(sheet, n.childrenfieldlist, restFieldlist,deep);
                    if (restFieldlist.Count == 0)
                    {
                        // setxyofList(y,x,deep, childfield,  restFieldlist);
                        continue ;
                    }
                }
            }
        }
        public void DWriteSingelLine(NPOI.SS.UserModel.ISheet sheet, List<FieldInfo> masterlist, List<FieldInfo> rest, int deep)
        {/**
          * 1.进入先判断有没有这个行数被创建了
          * 2.如果没有创建一个 
          *3.如果有就获得这行 
          *4.把现在的行数传给oldnum
          *
          * 
          * 
          * 
          * 
          * **/
            int oldnum = -1;
            foreach (var n in masterlist)
            {
                NPOI.SS.UserModel.IRow headerrow1 = sheet.GetRow(n.yset + 3);

                if (oldnum != n.yset + 3&&headerrow1==null)
                {
                   
                    NPOI.SS.UserModel.IRow headerrow = sheet.CreateRow(n.yset + 3);


                    headerrow.CreateCell(n.xset+1).SetCellValue(n.name);

                }
                else
                {
                    NPOI.SS.UserModel.IRow headerrow = sheet.GetRow(n.yset + 3);
                  

                    headerrow.CreateCell(n.xset+1).SetCellValue(n.name);
                }


                oldnum = n.yset + 3;

                sheet.AddMergedRegion(new NPOI.SS.Util.CellRangeAddress(n.yset + 3, n.yset + 3 + n.rowspan - 1, n.xset+1, n.xset+1+ n.colspan - 1));

                var childfield = rest.Where(m => m.parentid == n.uid).ToList();
                if (childfield.Count != 0)
                {
                    var restFieldlist = new List<FieldInfo>();
                    foreach (var nr in rest)
                    {
                        if (!childfield.Contains(nr))
                        {
                            restFieldlist.Add(nr);//
                        }
                    }
                   DWriteSingelLine(sheet, n.childrenfieldlist, restFieldlist, deep);
                    if (restFieldlist.Count == 0)
                    {
                        // setxyofList(y,x,deep, childfield,  restFieldlist);
                        continue;
                    }
                }
            }
        }
        //public ICellStyle Getstyle(NPOI.HSSF.UserModel.HSSFWorkbook book)
        //{
        //    //ICellStyle style = book.CreateCellStyle();
        //    ////设置单元格的样式：水平对齐居中
        //    //style.Alignment = HorizontalAlignment.CENTER;
        //    //style.BorderBottom = NPOI.HSSF.UserModel.;
        //    //style.BorderLeft = CellBorderType.THIN;
        //    //style.BorderRight = CellBorderType.THIN;
        //    //style.BorderTop = CellBorderType.THIN;
        //    //style.BorderLeft=
        //    //style.VerticalAlignment = VerticalAlignment.CENTER;
        //    ////新建一个字体样式对象
        //    //IFont font = book.CreateFont();
        //    ////设置字体加粗样式
        //    //font.Boldweight = short.MaxValue;

        //    ////使用SetFont方法将字体样式添加到单元格样式中 
        //    //style.SetFont(font);
        //    ////将新的样式赋给单元格

        //}
      
     
        public List<FieldInfo> setxyofList(int y,int x,int deep, List<FieldInfo> master, List<FieldInfo> rest)
        {
            foreach (var n in master)
            {
                n.xset = x;
                n.yset = y;

                var childfield = rest.Where(m => m.parentid == n.uid).ToList();
                if (childfield.Count != 0)
                {
                    var restFieldlist = new List<FieldInfo>();
                    foreach (var nr in rest)
                    {
                        if (!childfield.Contains(nr))
                        {
                            restFieldlist.Add(nr);
                        }
                    }
                    setxyofList(y + 1, x, deep, n.childrenfieldlist, restFieldlist);
                    if (restFieldlist.Count == 0)
                    {
                    // setxyofList(y,x,deep, childfield,  restFieldlist);
                        x = x + n.colspan;
                        continue;
                    }
                }
                x = x + n.colspan;
            }
            return master;
        }


    }
}