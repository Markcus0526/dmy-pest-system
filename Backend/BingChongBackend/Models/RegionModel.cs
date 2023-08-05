using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace BingChongBackend.Models
{
    public class SearchList
    {
        public long uid { set; get; }
        public string name { set; get; }
    }

    public class RegionList
    {
        public long uid { set; get; }
        public long parentid { set; get; }
        public string name { set; get; }
        public string postcode { set; get; }
        public CommonModel.UserLevel level { set; get; }
        public int status{ set; get; }
        public long sheng_id { set; get; }
        public long shi_id { set; get; }
        public long xian_id { set; get; }

    }

    public class RegionModel
    {
        BingChongDBDataContext db = new BingChongDBDataContext();

        public List<SearchList> GetRegionOptionsWithoutGou(CommonModel.UserLevel level)
        {
            List<SearchList> rst=new List<SearchList>();

            switch(level){
                case CommonModel.UserLevel.ADMIN:
                   //rst.Add(new SearchList { name = "国家", uid = 4 });
                    rst.Add(new SearchList { name = "省级", uid = 3 });
                    rst.Add(new SearchList { name = "市级", uid = 2 });
                    rst.Add(new SearchList { name = "县级", uid = 1 });
                    break;

                case CommonModel.UserLevel.XIANJI:
                    
                    break;
                case CommonModel.UserLevel.SHIJI:
                    rst.Add(new SearchList { name = "县级", uid = 1 });                    
                    break;
                case CommonModel.UserLevel.SHENGJI:
                    rst.Add(new SearchList { name = "市级", uid = 2 });
                    break;
                case CommonModel.UserLevel.GUOJIAJI:
                    //rst.Add(new SearchList { name = "国家", uid = 4 });
                    rst.Add(new SearchList { name = "省级", uid = 3 });
                    rst.Add(new SearchList { name = "市级", uid = 2 });
                    rst.Add(new SearchList { name = "县级", uid = 1 });
                    break;
            }


            return rst;
        }

        public List<SearchList> GetRegionOptions(CommonModel.UserLevel level)
        {
            List<SearchList> rst = new List<SearchList>();

            switch (level)
            {
                case CommonModel.UserLevel.ADMIN:
                    rst.Add(new SearchList { name = "国家", uid = 4 });
                    rst.Add(new SearchList { name = "省级", uid = 3 });
                    rst.Add(new SearchList { name = "市级", uid = 2 });
                    rst.Add(new SearchList { name = "县级", uid = 1 });
                    break;

                case CommonModel.UserLevel.XIANJI:

                    break;
                case CommonModel.UserLevel.SHIJI:
                    rst.Add(new SearchList { name = "县级", uid = 1 });
                    break;
                case CommonModel.UserLevel.SHENGJI:
                    rst.Add(new SearchList { name = "市级", uid = 2 });
                    break;
                case CommonModel.UserLevel.GUOJIAJI:
                    rst.Add(new SearchList { name = "省级", uid = 3 });
                    break;
            }


            return rst;
        }

        public List<RegionList> GetRegionList()
        {
            
            var shilist = db.shis.Where(m => m.deleted == 0).Select(m => new RegionList
            {
                name = m.name,
                uid = m.uid,
                level = CommonModel.UserLevel.SHIJI,
                postcode = m.postcode
            }).ToList();
            var shenglist = db.shengs.Where(m => m.deleted == 0).Select(m => new RegionList
            {
                name = m.name,
                uid = m.uid,
                level = CommonModel.UserLevel.SHENGJI,
                postcode = m.postcode
            }).ToList();
            var xianlist = db.xians.Where(m => m.deleted == 0).Select(m => new RegionList
            {
                name = m.name,
                uid = m.uid,
                level = CommonModel.UserLevel.XIANJI,
                postcode = m.postcode
            }).ToList();
            return  shenglist.Concat(shilist).Concat(xianlist).ToList();
        }
        public List<RegionList> SearchRegionList(string name,int level)
        {
            var alllisr = GetRegionList();
            var rstlist=new List<RegionList>();
            if (!String.IsNullOrWhiteSpace(name))
            {
                rstlist=alllisr.Where(m => m.name.Contains(name) && (int)m.level == level).ToList();

            }
            else
            {
                rstlist = alllisr.Where(m => (int)m.level == level).ToList();
            
            }
            return rstlist;
        }

        public RegionList GetRegionInfo(long uid, int level)
        {
            RegionList rst = new RegionList();
            switch (level)
            {
                case 1:
                   rst= db.xians.Where(m => m.deleted == 0 && m.uid == uid).Select(m => new RegionList
                    {
                        parentid = m.shi_id,
                        name = m.name,
                        uid = m.uid,
                        level = CommonModel.UserLevel.XIANJI,
                        postcode = m.postcode
                    }
                    ).FirstOrDefault();
                    break;
                case 2:
                   rst= db.shis.Where(m => m.deleted == 0 && m.uid == uid).Select(m => new RegionList
                    {
                        parentid=m.sheng_id,
                        name = m.name,
                        uid = m.uid,
                        level = CommonModel.UserLevel.XIANJI,
                        postcode = m.postcode
                    }
                    ).FirstOrDefault();
                   break;

                case 3:
                   rst= db.shengs.Where(m => m.deleted == 0 && m.uid == uid).Select(m => new RegionList
                    {
                        name = m.name,
                        uid = m.uid,
                        level = CommonModel.UserLevel.XIANJI,
                        postcode = m.postcode
                    }
                    ).FirstOrDefault();
                   break;
            }
            return rst;
        }
        ////sratus==0
        //public List<RegionList> GetShiListForUnit()
        //{
        //    return db.shis.Where(m => m.deleted == 0 && m.status == 0).Select(m => new RegionList
        //    {
        //        name=m.name,
        //        uid=m.uid,
        //        postcode=m.postcode,
        //        level=CommonModel.UserLevel.SHIJI
        //    }).ToList();
        //}

        ////sratus==0
        //public List<RegionList> GetXianListForUnit()
        //{
        //    return db.xians.Where(m => m.deleted == 0&&m.status==0).Select(m => new RegionList
        //    {
        //        name = m.name,
        //        uid = m.uid,
        //        postcode = m.postcode,
        //        level = CommonModel.UserLevel.XIANJI
        //    }).ToList();
        //}
        ////sratus==0
        //public List<RegionList> GetShengListForUnit()
        //{
        //    return db.shengs.Where(m => m.deleted == 0 && m.status == 0).Select(m => new RegionList
        //    {
        //        name = m.name,
        //        uid = m.uid,
        //        postcode = m.postcode,
        //        level = CommonModel.UserLevel.SHENGJI
        //    }).ToList();
        //}

        //sratus==1
        public List<RegionList> GetShiList()
        {
            return db.shis.Where(m => m.deleted == 0).Select(m => new RegionList
            {
                name = m.name,
                uid = m.uid,
                postcode = m.postcode,
                level = CommonModel.UserLevel.SHIJI,
                status = m.status,
                sheng_id=m.sheng_id
            }).ToList();
        }

        //sratus==1
        public List<RegionList> GetXianList()
        {
            return db.xians.Where(m => m.deleted == 0 ).Select(m => new RegionList
            {
                name = m.name,
                uid = m.uid,
                postcode = m.postcode,
                level = CommonModel.UserLevel.XIANJI,
                status=m.status,
                shi_id=m.shi_id
            }).ToList();
        }
        //sratus==1
        public List<RegionList> GetShengList()
        {
            return db.shengs.Where(m => m.deleted == 0).Select(m => new RegionList
            {
                name = m.name,
                uid = m.uid,
                postcode = m.postcode,
                level = CommonModel.UserLevel.SHENGJI,
                status = m.status

            }).ToList();
        }



        public string InsertRegion(int regionlevel, string regionname,string postcode,string upperregionid)
        {
            var rst = "";
            try
            {
                switch (regionlevel)
                {
                    case 1:
                        xian newxian = new xian();
                        newxian.name = regionname;
                        newxian.shi_id = long.Parse(upperregionid);
                        newxian.postcode = postcode;
                        newxian.status = 0;
                        db.xians.InsertOnSubmit(newxian);

                        db.SubmitChanges();
                        break;
                    case 2:
                        shi newshi = new shi();
                        newshi.name = regionname;
                        newshi.sheng_id = long.Parse(upperregionid);
                        newshi.status = 0;
                        newshi.postcode = postcode;
                        db.shis.InsertOnSubmit(newshi);

                        db.SubmitChanges();                        
                        break;
                    case 3:
                        sheng newsheng = new sheng();
                        newsheng.name = regionname;
                        newsheng.status = 0;
                        newsheng.postcode = postcode;
                        db.shengs.InsertOnSubmit(newsheng);
                        
                        db.SubmitChanges();            
                        break;

                }
            }
            catch (System.Exception ex)
            {
                rst = "操作失败！";
            }

            return rst;
        }


        public string UpdateRegion(long uid, int regionlevel,string regionname, string upperregion)
        {
            string rst = "";

            switch (regionlevel)
            {
                case 1:
                    xian editxian = db.xians.Where(m=>m.uid==uid).FirstOrDefault();
                    if (editxian!=null)
                    {
                        editxian.name = regionname;
                        editxian.shi_id = long.Parse(upperregion);
                        db.SubmitChanges();
                    } 
                    else
                    {
                        rst = "数据不存在";
                    }
                    break;

                case 2:
                    shi editshi = db.shis.Where(m=>m.uid==uid).FirstOrDefault();
                    if (editshi!=null)
                    {
                        editshi.name = regionname;
                        editshi.sheng_id = long.Parse(upperregion);
                        db.SubmitChanges();
                    } 
                    else
                    {
                        rst = "数据不存在";
                    }
                    break;
                case 3:
                    sheng editsheng = db.shengs.Where(m=>m.uid==uid).FirstOrDefault();
                    if (editsheng!=null)
                    {
                        editsheng.name = regionname;
                        db.SubmitChanges();
                    } 
                    else
                    {
                        rst = "数据不存在";
                    }
                    break;
            }
            return rst;
        }
        public bool CheckDuplicateRegion(long uid, string regionname,int level)
        {
            bool rst = true;
            if (uid == 0)
            {
                switch (level)
                {
                    case 1:
                        rst = (db.xians.Where(m => m.deleted == 0 && m.name == regionname).FirstOrDefault() == null);
                        break;
                    case 2:
                        rst = (db.shis.Where(m => m.deleted == 0 && m.name == regionname).FirstOrDefault() == null);
                        break;
                    case 3:
                        rst = (db.shengs.Where(m => m.deleted == 0 && m.name == regionname).FirstOrDefault() == null);
                        break;
                    //case 4:
                    //    rst = (db.guojias.Where(m => m.deleted == 0 && m.name == regionname).FirstOrDefault() == null);
                    //    break;
                }
            }
            else
            {
                switch (level)
                {
                    case 1:
                        rst = (db.xians.Where(m => m.deleted == 0 && m.uid != uid && m.name == regionname).FirstOrDefault() == null);
                        break;
                    case 2:
                        rst = (db.shis.Where(m => m.deleted == 0 && m.uid != uid && m.name == regionname).FirstOrDefault() == null);
                        break;
                    case 3:
                        rst = (db.shengs.Where(m => m.deleted == 0 && m.uid != uid && m.name == regionname).FirstOrDefault() == null);
                        break;
                    //case 4:
                    //    rst = (db.guojias.Where(m => m.deleted == 0 && m.name == regionname).FirstOrDefault() == null);
                    //    break;
                }
            }
            
            return rst;
        }

        public List<SearchList> GetUpperListByLowerlevel(int level)
        {
            var rst=new List<SearchList>();
            switch(level)
            {
                case 1:
                    rst=db.shis.Where(m=>m.deleted==0).Select(m=>new SearchList{
                                name=m.name,
                                uid=m.uid
                    }).ToList();
                    break;
                case 2:
                rst=db.shengs.Where(m=>m.deleted==0).Select(m=>new SearchList{
                    name = m.name,
                    uid = m.uid
                }).ToList();
                break;
            }
            return rst;
        }

        public List<RegionList> GetRegionListBylevel(int level)
        {
            var rst = new List<RegionList>();
            switch (level)
            {
                case 1:
                    rst = GetXianList();
                    break;
                case 2:
                    rst = GetShiList();
                    break;
                case 3:
                    rst = GetShengList();
                    break;
            }
            return rst;
        }


        public bool DeleteRegion(int level, long delids)
        {
            bool rst = false;

            string delSql = "";

            switch (level)
            {
                case 1:
                    delSql = "UPDATE xian SET deleted = 1 WHERE ";
                    break;
                case 2:
                    delSql = "UPDATE shi SET deleted = 1 WHERE ";
                    break;
                case 3:
                    delSql = "UPDATE sheng SET deleted = 1 WHERE ";
                    break;
            }
            try
            {
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
    }
}