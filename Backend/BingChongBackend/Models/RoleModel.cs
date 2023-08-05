using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace BingChongBackend.Models
{
    public class RoleModel
    {
        BingChongDBDataContext db = new BingChongDBDataContext();

        public List<tbl_right> GetRoleList()
        {
            return db.tbl_rights.Where(m => m.deleted == 0&&m.uid!=1).OrderByDescending(m=>m.uid).ToList();
        }

        public string InsertRole(string rolename, string role)
        {
            tbl_right newitem = new tbl_right();

            newitem.name = rolename;
            newitem.role = role;
            newitem.enabled = 0;
            newitem.regtime = DateTime.Now;
            newitem.deleted= 0;
            newitem.description = "";
            db.tbl_rights.InsertOnSubmit(newitem);

            db.SubmitChanges();

            return "";
        }

        public string UpdateRole(long uid, string rolename, string role)
        {
            string rst = "";
            tbl_right edititem = GetRoleById(uid);

            if (edititem != null)
            {
                edititem.name = rolename;
                edititem.role = role;
                db.SubmitChanges();
                rst = "";
            }
            else
            {
                rst = "数据不存在";
            }

            return rst;
        }

        public bool DeleteRole(long[] items)
        {
            bool rst = false;
            try
            {
                string delSql = "UPDATE tbl_right SET deleted = 1 WHERE ";
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

        public tbl_right GetRoleById(long uid)
        {
            return db.tbl_rights
                .Where(m => m.deleted == 0 && m.uid == uid)
                .FirstOrDefault();
        }

        public bool CheckDuplicateRole(string rolename,long uid)
        {
            bool rst = true;
            rst = ((from m in db.tbl_rights
                    where m.deleted == 0 && m.name == rolename&& m.uid != uid
                    select m).FirstOrDefault() == null);

            return rst;
        }

    }
}