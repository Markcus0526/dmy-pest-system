using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace BingChongBackend.Models
{
    public class HelpModel
    {
        BingChongDBDataContext db = new BingChongDBDataContext();


        public List<Document> GetHelpList()
        {
            var rst = db.helps.Where(m => m.deleted == 0).OrderByDescending(m=>m.uid)
                .Select(m => new Document
                {
                    uid = m.uid,
                    title = m.title,
                }).ToList();
            return rst;
        }

        public string InsertHelp(string name, string contents)
        {
            string rst = "";
            help newitem = new help();
            try
            {
                newitem.title = name;
                newitem.contents = contents;
                newitem.deleted = 0;
                db.helps.InsertOnSubmit(newitem);
                db.SubmitChanges();
            }
            catch (System.Exception ex)
            {
                rst = "操作失败";
            }
            return rst;
        }

        public string UpdateHelp(long uid, string name, string contents)
        {
            string rst = "";
            help edititem = GetHelpById(uid);

            if (edititem != null)
            {
                edititem.title = name;
                edititem.contents = contents;
                db.SubmitChanges();
                rst = "";
            }
            else
            {
                rst = "数据不存在";
            }

            return rst;
        }

        public help GetHelpById(long uid)
        {
            return db.helps.Where(m => m.deleted == 0 && m.uid == uid).FirstOrDefault();
        }

        public bool DeleteHelp(long[] items)
        {
            bool rst = false;
            try
            {
                string delSql = "UPDATE help SET deleted = 1 WHERE ";
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
    }
}