using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace BingChongBackend.Models
{
    public class UnitModel
    {
        BingChongDBDataContext db = new BingChongDBDataContext();


        public List<department> GetUnitList()
        {
            var rst = db.departments.Where(m => m.deleted == 0 ).OrderByDescending(m=>m.uid).ToList();

            return rst;
        }

        public department GetUnitByID(long uid)
        {
            var rst = db.departments.Where(m => m.deleted == 0&&m.uid==uid).FirstOrDefault();

            return rst;
        }

        public List<department> SearchUnitList(string name, int level)
        {
            var alllisr = GetUnitList();
            var rstlist = new List<department>();
            if (!String.IsNullOrWhiteSpace(name))
            {
                rstlist = alllisr.Where(m => m.name.Contains(name) && (int)m.level == level).ToList();

            }
            else
            {
                rstlist = alllisr.Where(m => (int)m.level == level).ToList();

            }
            return rstlist;
        }


        public string InserteUnit(string name, int level, long regionid,
                                  string postcode, string chief, string telephone, string celphone, string addr, string note)
        {
            var rst = "";
            try
            {
                long maxserial = 0;
                var unitlist=db.departments.Where(m => m.deleted == 0).ToList();
                
                if (unitlist.Count!=0)
                {
                    maxserial = unitlist.Max(x => x.serial);
                
                }
                try
                {
                    department newitem = new department();

                    newitem.name = name;

                    newitem.level = level;
                    switch (level)
                    {
                        case (int)CommonModel.UserLevel.SHIJI:
                            newitem.sheng_id = db.shis.Where(m => m.uid == regionid).FirstOrDefault().sheng_id;
                            newitem.shi_id = regionid;
                            break;
                        case (int)CommonModel.UserLevel.XIANJI:
                            newitem.shi_id = db.xians.Where(m => m.uid == regionid).FirstOrDefault().shi_id;

                            newitem.xian_id = regionid;
                            break;
                        case (int)CommonModel.UserLevel.SHENGJI:
                            newitem.sheng_id = regionid;
                            break;
                    }
                    newitem.post_number = postcode;
                    newitem.chief = chief;
                    newitem.phone = telephone;
                    newitem.mobile = celphone;
                    newitem.address = addr;
                    newitem.serial = maxserial+1;
                    newitem.note = note;

                    newitem.deleted = 0;

                    db.departments.InsertOnSubmit(newitem);


                    switch (level)
                    {
                        case (int)CommonModel.UserLevel.SHIJI:
                            var editshi = db.shis.Where(m => m.uid == regionid).FirstOrDefault();
                            editshi.status = 1;
                            break;
                        case (int)CommonModel.UserLevel.XIANJI:
                            var editxian = db.xians.Where(m => m.uid == regionid).FirstOrDefault();
                            editxian.status = 1;
                            break;
                        case (int)CommonModel.UserLevel.SHENGJI:
                            var editsheng = db.shengs.Where(m => m.uid == regionid).FirstOrDefault();
                            editsheng.status = 1;
                            break;
                    }

                    db.SubmitChanges();
                }
                catch (System.Exception ex)
                {
                    rst = "操作失败！";
                }
            }
            catch (System.Exception ex)
            {
                rst = "生成序列码出错";
            }
          


            return rst;
        }

        public string UpdateUnit(long uid, string unitname,string chief, string telephone, string cellphone, string addr, string note)
        {
            string rst = "";

            department edititem = db.departments.Where(m => m.uid == uid).FirstOrDefault();

            if (edititem != null)
            {

                edititem.name = unitname;
                edititem.chief= chief;
                edititem.phone = telephone;
                edititem.mobile = cellphone;
                edititem.address = addr;
                edititem.note= note;

                //edititem.imgurl = "";
                db.SubmitChanges();
                rst = "";
            }
            else
            {
                rst = "用户不存在";
            }

            return rst;
        }

        public bool DeleteUnit(long delids)
        {
            bool rst = false;
            try
            {
                string delSql = "UPDATE department SET deleted = 1 WHERE ";
                string whereSql = "uid=" + Convert.ToString(delids);

                delSql += whereSql;

                db.ExecuteCommand(delSql);
                rst = true;
            }
            catch (System.Exception ex)
            {

            }

            return rst;
        }

        public string GetRegionPostcode(int level, long regionid)
        {
            var rst = "";
            switch (level)
            {
                case 3:
                    rst = db.shengs.Where(m => m.deleted == 0 && m.uid == regionid).FirstOrDefault().postcode == null ? "" : db.shengs.Where(m => m.deleted == 0 && m.uid == regionid).FirstOrDefault().postcode;
                    break;
                case 2:
                    rst = db.shis.Where(m => m.deleted == 0 && m.uid == regionid).FirstOrDefault().postcode == null ? "" : db.shis.Where(m => m.deleted == 0 && m.uid == regionid).FirstOrDefault().postcode;
                    break;               
                case 1:
                    rst = db.xians.Where(m => m.deleted == 0 && m.uid == regionid).FirstOrDefault().postcode == null ? "" : db.xians.Where(m => m.deleted == 0 && m.uid == regionid).FirstOrDefault().postcode;
                    break;
            }
            return rst;

        }
    }
}