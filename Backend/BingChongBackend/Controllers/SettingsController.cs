using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using BingChongBackend.Models.Library;
using BingChongBackend.Models;
using BingChongBackend.shipin;

 
namespace BingChongBackend.Controllers
{
    public class SettingsController : Controller
    {
        FieldModel fieldmodel = new FieldModel();
        RoleModel rolemodel = new RoleModel();
        UserModel usermodel = new UserModel();
        RegionModel regionmodel = new RegionModel();
        TableModel tablemodel = new TableModel();
        UnitModel unitmodel = new UnitModel();
        BackupModel backupmodel = new BackupModel();
#region Role
        [Authorize]
        public ActionResult RoleManage()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Settings";
            ViewData["level2nav"] = "RoleManage";
            ViewData["role"] = CommonModel.GetUserRoleInfo();
            return View();
        }
        [HttpGet]
        public JsonResult RetrieveRoleList(int rows, int page)
        {
            var rst = rolemodel.GetRoleList();
            int record = rst.Count;

            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }
            return Json(new { page = page, total = total, records = record, rows = rst.Skip((page - 1) * rows).Take(page * rows).ToList() }, JsonRequestBehavior.AllowGet);
        }

        [Authorize]
        public ActionResult AddRole()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Settings";
            ViewData["level2nav"] = "RoleManage";

            return View();
        }

        [Authorize]
        public ActionResult ViewRole(long id)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Settings";
            ViewData["level2nav"] = "RoleManage";
            ViewData["rolename"] = rolemodel.GetRoleById(id).name;
            ViewData["role"] = rolemodel.GetRoleById(id).role;
            ViewData["uid"] = id;
            return View();
        }
        [AjaxOnly]
        public JsonResult CheckUniqueRoleName(string rolename, long uid)
        {
            bool rst = rolemodel.CheckDuplicateRole(rolename,uid);

            return Json(rst, JsonRequestBehavior.AllowGet);
        }

        [Authorize]
        public ActionResult EditRole(long id)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Settings";
            ViewData["level2nav"] = "RoleManage";

            ViewData["rolename"] = rolemodel.GetRoleById(id).name;
            ViewData["role"] = rolemodel.GetRoleById(id).role;
            ViewData["uid"] = id;

            return View("AddRole");
        }
         
        [HttpPost]
        public JsonResult DeleteRole(string delids)
        {
            string[] ids = delids.Split(',');
            long[] selcheckbox = ids.Where(m => !String.IsNullOrWhiteSpace(m)).Select(m => long.Parse(m)).ToArray();
            bool rst = rolemodel.DeleteRole(selcheckbox);

            return Json(rst, JsonRequestBehavior.AllowGet);
        }
        [HttpPost]
        [AjaxOnly]
        public JsonResult SubmitRole(long uid, string rolename, string[] configuration)
        {
            string rst = "";

            string role = "";

            if (configuration != null)
            {
                role = String.Join(",", configuration);
            }

            if (uid == 0)
            {
                rst = rolemodel.InsertRole(rolename, role);
            }
            else
            {
                rst = rolemodel.UpdateRole(uid, rolename, role);
            }

            return Json(rst, JsonRequestBehavior.AllowGet);
        }
#endregion

#region Region
        [Authorize]
        public ActionResult RegionManage()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Settings";
            ViewData["level2nav"] = "RegionManage";

            var level = CommonModel.GetSessionUserLevel();
            ViewData["SearchRegion"] = regionmodel.GetRegionOptionsWithoutGou(level);

            ViewData["shenglist"] = regionmodel.GetShengList();
            ViewData["shilist"] = regionmodel.GetShiList();
            ViewData["userlevel"] = (int)CommonModel.GetSessionUserLevel();
            ViewData["role"] = CommonModel.GetUserRoleInfo();

            return View();
        }

        [HttpGet]
        public JsonResult RetrieveRegionList(int searchlevel, int rows, int page)
        {
            var rst = regionmodel.GetRegionList().Where(m=>(int)m.level==searchlevel).ToList();
            var user=CommonModel.GetSessionUser();
            var userlvel = CommonModel.GetSessionUserLevel();
            if (userlvel == CommonModel.UserLevel.GUOJIAJI)
            {
                rst = regionmodel.GetShengList();
            }
            if (userlvel==CommonModel.UserLevel.SHIJI)
            {
                rst = regionmodel.GetXianList().Where(m => m.shi_id == user.shi_id).ToList();
            }
            if (userlvel==CommonModel.UserLevel.SHENGJI)
            {
                rst=regionmodel.GetShiList().Where(m=>m.sheng_id==user.sheng_id).ToList();
            }
            if (userlvel == CommonModel.UserLevel.XIANJI)
            {
                rst = new List<RegionList>();
            }
            int record = rst.Count;

            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }
            return Json(new { page = page, total = total, records = record, rows = rst.Skip((page - 1) * rows).Take(page * rows).ToList() }, JsonRequestBehavior.AllowGet);
        }
        [HttpGet]
        public JsonResult SearchRegionList(int searchlevel, string searchname, int rows, int page)
        {
            var rst = regionmodel.SearchRegionList(searchname, searchlevel);
            var user = CommonModel.GetSessionUser();
            var userlvel = CommonModel.GetSessionUserLevel();
            if (userlvel == CommonModel.UserLevel.GUOJIAJI)
            {
                rst = rst.Where(m=>m.level==CommonModel.UserLevel.SHENGJI).ToList();
            }
            if (userlvel == CommonModel.UserLevel.SHIJI)
            {
                rst = regionmodel.GetXianList().Where(m => m.shi_id == user.shi_id).ToList();
            }
            if (userlvel == CommonModel.UserLevel.SHENGJI)
            {
                rst = regionmodel.GetShiList().Where(m => m.sheng_id == user.sheng_id).ToList();
            }
            if (userlvel == CommonModel.UserLevel.XIANJI)
            {
                rst = new List<RegionList>();
            }
            
            int record = rst.Count;

            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }
            return Json(new { page = page, total = total, records = record, rows = rst.Skip((page - 1) * rows).Take(page * rows).ToList() }, JsonRequestBehavior.AllowGet);
        }

        [AjaxOnly]
        public JsonResult GetRegionInfo(int level, long uid)
        {
            var rst = regionmodel.GetRegionInfo(uid, level);
            return Json(rst, JsonRequestBehavior.AllowGet);
        }
        [AjaxOnly]
        public JsonResult GetUpperList(int regionlevel)
        {

           var rst = regionmodel.GetUpperListByLowerlevel(regionlevel);
           var currentlevel = CommonModel.GetSessionUserLevel();
           var user = CommonModel.GetSessionUser();
           switch (currentlevel)
           {
               case CommonModel.UserLevel.SHENGJI:
                   rst = rst.Where(m => m.uid == user.sheng_id).ToList();
                   break;
               case CommonModel.UserLevel.SHIJI:
                   rst = rst.Where(m => m.uid == user.shi_id).ToList();
                   break;
           }

           return Json(rst, JsonRequestBehavior.AllowGet);

        }

        [AjaxOnly]
        public JsonResult GetRegionListByLevel(int regionlevel)
        {

            var rst = regionmodel.GetRegionListBylevel(regionlevel);

            return Json(rst, JsonRequestBehavior.AllowGet);
        }

        [AjaxOnly]
        public JsonResult GetRegionListByLevelForUnit(int regionlevel)
        {

            var rst = regionmodel.GetRegionListBylevel(regionlevel).Where(m => m.status == 0).ToList(); ;

            var currentlevel=CommonModel.GetSessionUserLevel();
            var user=CommonModel.GetSessionUser();

            switch (currentlevel)
            {
                case CommonModel.UserLevel.ADMIN:

                    break;
                case CommonModel.UserLevel.SHENGJI:
                    rst = rst.Where(m => m.sheng_id == user.sheng_id).ToList();
                    break;
                case CommonModel.UserLevel.SHIJI:
                    rst = rst.Where(m => m.shi_id == user.shi_id).ToList();
                    break;
                case CommonModel.UserLevel.XIANJI:

                    break;

            }
            return Json(rst, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        [AjaxOnly]
        public JsonResult SubmitRegion(int regionlevel, string regionname, string postcode)
        {
            var rst = "";
            String uid = Request.Form["uid"];
            String upperregion = Request.Form["upperregion"];

            if (uid == null)
            {
                rst = regionmodel.InsertRegion(regionlevel, regionname, postcode, upperregion);
            }
            else
            {

                rst = regionmodel.UpdateRegion(long.Parse(uid), regionlevel, regionname, upperregion);
            }

            return Json(rst, JsonRequestBehavior.AllowGet);
        }
        [HttpPost]
        public JsonResult DeleteRegion(int level,long delids)
        {
           // long[] selcheckbox = ids.Where(m => !String.IsNullOrWhiteSpace(m)).Select(m => long.Parse(m)).ToArray();
            bool rst = regionmodel.DeleteRegion(level, delids);

            return Json(rst, JsonRequestBehavior.AllowGet);
        }
        [AjaxOnly]
        public JsonResult CheckUniqueRegionName(long uid, string regionname,int regionlevel)
        {
            bool rst = regionmodel.CheckDuplicateRegion(uid,regionname, regionlevel);

            return Json(rst, JsonRequestBehavior.AllowGet);
        }
#endregion
        [Authorize]
        public ActionResult UnitSettings()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Settings";
            ViewData["level2nav"] = "UnitSettings";
            var level = CommonModel.GetSessionUserLevel();
            ViewData["role"] = CommonModel.GetUserRoleInfo();
            ViewData["RegionOptions"] = regionmodel.GetRegionOptions(level);

            return View();
        }

        [HttpGet]
        public JsonResult RetrieveUnitList(int searchlevel,int rows, int page)
        {
            var rst = unitmodel.GetUnitList().Where(m=>m.level==searchlevel).ToList();
            var userlvel = CommonModel.GetSessionUserLevel();
            var user = CommonModel.GetSessionUser();


            if (userlvel == CommonModel.UserLevel.GUOJIAJI)
            {
                rst = rst.Where(m => m.level == 3).ToList();
            }
            if (userlvel == CommonModel.UserLevel.SHIJI)
            {
                rst = rst.Where(m => m.level == 1 && m.shi_id == user.shi_id).ToList();
            }
            if (userlvel == CommonModel.UserLevel.SHENGJI)
            {
                rst = rst.Where(m => m.level == 2 && m.sheng_id == user.sheng_id).ToList();
            }
            if (userlvel == CommonModel.UserLevel.XIANJI)
            {
                rst = new List<department>();
            }
            
            
            int record = rst.Count;

            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }
            return Json(new { page = page, total = total, records = record, rows = rst.Skip((page - 1) * rows).Take(page * rows).ToList() }, JsonRequestBehavior.AllowGet);
        
        }

        public JsonResult GetRegionPostCode(int level,long regionid)
        {
            var rst="";
            rst = unitmodel.GetRegionPostcode(level,regionid);
            
            return Json(rst, JsonRequestBehavior.AllowGet);

        }
        [HttpGet]
        public JsonResult SearchUnitList(int searchlevel, string searchname, int rows, int page)
        {
            var rst = unitmodel.SearchUnitList(searchname, searchlevel);
            int record = rst.Count;
            var userlvel = CommonModel.GetSessionUserLevel();

            var user = CommonModel.GetSessionUser();

            if (userlvel == CommonModel.UserLevel.GUOJIAJI)
            {
                rst = rst.Where(m => m.level == 3).ToList();
            }
            if (userlvel == CommonModel.UserLevel.SHIJI)
            {
                rst = rst.Where(m => m.level == 1 && m.shi_id == user.shi_id).ToList();
            }
            if (userlvel == CommonModel.UserLevel.SHENGJI)
            {
                rst = rst.Where(m => m.level == 2 && m.sheng_id == user.sheng_id).ToList();
            }
            if (userlvel == CommonModel.UserLevel.XIANJI)
            {
                rst = new List<department>();
            }

            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }
            return Json(new { page = page, total = total, records = record, rows = rst.Skip((page - 1) * rows).Take(page * rows).ToList() }, JsonRequestBehavior.AllowGet);
        }

        [Authorize]
        public ActionResult AddUnit()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Settings";
            ViewData["level2nav"] = "UnitSettings";

            var level = CommonModel.GetSessionUserLevel();
            ViewData["RegionOptions"] = regionmodel.GetRegionOptions(level);

            return View();
        }

        [Authorize]
        public ActionResult ViewUnit(long id)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Settings";
            ViewData["level2nav"] = "UnitSettings";

            var level = CommonModel.GetSessionUserLevel();
            ViewData["RegionOptions"] = regionmodel.GetRegionOptions(level);
            ViewData["uid"] = id;

            var unit = unitmodel.GetUnitByID(id);
            ViewData["UnitInfo"] = unit;
            switch (unit.level)
            {
                case 1:
                    ViewData["regionname"] = regionmodel.GetRegionInfo(unit.xian_id, 1).name;
                    break;
                case 2:
                    ViewData["regionname"] = regionmodel.GetRegionInfo(unit.shi_id, 2).name;
                    break;
                case 3:
                    ViewData["regionname"] = regionmodel.GetRegionInfo(unit.sheng_id, 3).name;
                    break;

            }
            var unitlevel = unit.level;
            switch (unitlevel)
            {
                case 1:
                    ViewData["UnitRedionid"] = unitmodel.GetUnitByID(id).xian_id;
                    break;
                case 2:
                    ViewData["UnitRedionid"] = unitmodel.GetUnitByID(id).shi_id;
                    break;
                case 3:
                    ViewData["UnitRedionid"] = unitmodel.GetUnitByID(id).sheng_id;
                    break;
            }
            return View();
        }
        [Authorize]
        public ActionResult EditUnit(long id)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Settings";
            ViewData["level2nav"] = "UnitSettings";

            var level = CommonModel.GetSessionUserLevel();
            ViewData["RegionOptions"] = regionmodel.GetRegionOptions(level);
            ViewData["uid"] = id;
            
            var unit=unitmodel.GetUnitByID(id);
            ViewData["UnitInfo"] = unit;
            switch (unit.level)
            {
                case 1:
                    ViewData["regionname"] = regionmodel.GetRegionInfo(unit.xian_id, 1).name;
          
            	    break;
                case 2:
                    ViewData["regionname"] = regionmodel.GetRegionInfo(unit.shi_id, 2).name;
                   
                    break;
                case 3:
                    ViewData["regionname"] = regionmodel.GetRegionInfo(unit.sheng_id, 3).name;
               
                    break;

            }

            var unitlevel = unit.level;
            switch (unitlevel)
            {
            case 1:
                ViewData["UnitRedionid"] = unitmodel.GetUnitByID(id).xian_id;
            	break;
            case 2:
                ViewData["UnitRedionid"] = unitmodel.GetUnitByID(id).shi_id;
                break;
            case 3:
                ViewData["UnitRedionid"] = unitmodel.GetUnitByID(id).sheng_id;
                break;
            }

            return View("AddUnit");
        }
        [HttpPost]
        [AjaxOnly]
        public JsonResult SubmitUnit(long uid, string unitname, int regionlevel, long regionid,
                                     string postcode, string chief, string telephone, string cellphone, string addr, string note)
        {
            var rst = "";
            //long regionid = long.Parse(regionids);
            if (uid == 0)
            {
                rst = unitmodel.InserteUnit(unitname, regionlevel, regionid, postcode, chief, telephone, cellphone, addr, note);
            }
            else
            {
                rst = unitmodel.UpdateUnit(uid, unitname, chief, telephone, cellphone, addr, note);
            }

            return Json(rst, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult DeleteUnit(long delids)
        {
            // long[] selcheckbox = ids.Where(m => !String.IsNullOrWhiteSpace(m)).Select(m => long.Parse(m)).ToArray();
            bool rst = unitmodel.DeleteUnit(delids);

            return Json(rst, JsonRequestBehavior.AllowGet);
        }
#region User
        [Authorize]
        public ActionResult UserManage()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Settings";
            ViewData["level2nav"] = "UserManage";
            ViewData["userid"] = CommonModel.GetSessionUserID();
            ViewData["userlevel"] = CommonModel.GetSessionUserLevel();
            ViewData["role"] = CommonModel.GetUserRoleInfo();

            //搜索条件
            var level = CommonModel.GetSessionUserLevel();
            ViewData["SearchRegion"] = usermodel.GetRegionOptionsForUserView(level);
            ViewData["rolelist"] = rolemodel.GetRoleList();
            var currentlevel = CommonModel.GetSessionUserLevel();
            var user= CommonModel.GetSessionUser();

            switch (currentlevel)
            {
                case CommonModel.UserLevel.ADMIN:
                    ViewData["shenglist"] = regionmodel.GetShengList();
                    ViewData["shilist"] = regionmodel.GetShiList();
                    ViewData["xianlist"] = regionmodel.GetXianList();
                    break;
                case CommonModel.UserLevel.GUOJIAJI:
                    ViewData["shenglist"] = regionmodel.GetShengList();
                    ViewData["shilist"] = regionmodel.GetShiList();
                    ViewData["xianlist"] = regionmodel.GetXianList();
                    break;
                case CommonModel.UserLevel.SHENGJI:
                    ViewData["shenglist"] = regionmodel.GetShengList().Where(m=>m.uid==user.sheng_id).ToList();
                    break;
                case CommonModel.UserLevel.SHIJI:
                    ViewData["shenglist"] = regionmodel.GetShengList().Where(m => m.uid == user.sheng_id).ToList();
                    break;
                case CommonModel.UserLevel.XIANJI:
                    ViewData["shenglist"] = regionmodel.GetShengList().Where(m => m.uid == user.sheng_id).ToList();
                    break;

            }

            return View();
        }

        
        [AjaxOnly]
        public JsonResult ChangUserManageShiList(long shengid)
        {
            var currentlevel = CommonModel.GetSessionUserLevel();
            var user = CommonModel.GetSessionUser();

            List<shi> list = new List<shi>();
            list = new PointModel().Findshi(shengid);

            switch (currentlevel)
            {
                case CommonModel.UserLevel.SHIJI:
                    list = list.Where(m => m.uid == user.shi_id).ToList();
                    break;
                case CommonModel.UserLevel.XIANJI:
                    list = list.Where(m => m.uid == user.shi_id).ToList();
                    break;

            }
            return Json(list, JsonRequestBehavior.AllowGet);
        }

        [AjaxOnly]
        public JsonResult ChangUserManageXianList(long shiid)
        {
            List<xian> list = new List<xian>();
            list = new PointModel().Findxian(shiid);
            var currentlevel = CommonModel.GetSessionUserLevel();
            var user = CommonModel.GetSessionUser();

            switch (currentlevel)
            {
                case CommonModel.UserLevel.XIANJI:
                    list = list.Where(m => m.uid == user.xian_id).ToList();
                    break;

            }
            return Json(list, JsonRequestBehavior.AllowGet);
        }

        [AjaxOnly]
        public JsonResult ChangAddUserShiList(long shengid)
        {

            List<shi> list = new List<shi>();
            list = new PointModel().Findshi(shengid).Where(m=>m.status==1).ToList();
            var currentlevel = CommonModel.GetSessionUserLevel();
            var user=CommonModel.GetSessionUser();
            if (currentlevel==CommonModel.UserLevel.SHIJI)
            {
                list = list.Where(m => m.uid == user.shi_id).ToList();
            }


            return Json(list, JsonRequestBehavior.AllowGet);
        }

        [AjaxOnly]
        public JsonResult ChangAddUserXianList(long shiid)
        {
            List<xian> list = new List<xian>();
            list = new PointModel().Findxian(shiid).Where(m => m.status == 1).ToList(); ;
            var currentlevel = CommonModel.GetSessionUserLevel();
            var user = CommonModel.GetSessionUser();
            if (currentlevel == CommonModel.UserLevel.XIANJI)
            {
                list = list.Where(m => m.uid == user.xian_id).ToList();
            }
            return Json(list, JsonRequestBehavior.AllowGet);
        }

        [HttpGet]
        public JsonResult RetrieveUserList(int userlevel, int rows, int page)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            var rst = usermodel.GetUserList().Where(m=>(int)m.level==userlevel).ToList();
            var userid = CommonModel.GetSessionUserID();

            BingChongDBDataContext db = new BingChongDBDataContext();
            //var level = (int)CommonModel.GetSessionUserLevel();
            var user = db.users.Where(m => m.uid == userid).FirstOrDefault();

            switch (CommonModel.GetSessionUserLevel())
            {
                case CommonModel.UserLevel.ADMIN:
                    break;
                case CommonModel.UserLevel.GUOJIAJI:
                    if (userlevel == 4)
                    {
                        rst = rst.Where(m => m.roleid == 0).ToList();
                    }
                    else
                    {
                        rst = rst.Where(m => m.roleid != 0).ToList();
                    }
                    break;
                case CommonModel.UserLevel.SHENGJI:
                    if (userlevel == 3)
                    {
                        rst = rst.Where(m => m.sheng_id == user.sheng_id && m.roleid == 0).ToList();
                    }
                    else
                    {
                        rst = rst.Where(m => m.sheng_id == user.sheng_id && m.roleid != 0).ToList();
                    }
                    //  rst = rst.Where(m => m.sheng_id == user.sheng_id ).ToList();

                    break;
                case CommonModel.UserLevel.SHIJI:
                    if (userlevel == 2)
                    {
                        rst = rst.Where(m => m.shi_id == user.shi_id && m.roleid == 0).ToList();

                    }
                    else
                    {
                        rst = rst.Where(m => m.shi_id == user.shi_id && m.roleid != 0).ToList();

                    }
                    //  rst = rst.Where(m => m.shi_id == user.shi_id).ToList();
                    break;
                case CommonModel.UserLevel.XIANJI:
                    rst = rst.Where(m => m.xian_id == user.xian_id && m.roleid == 0).ToList();
                    break;
            }

            int record = rst.Count;


            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }
            return Json(new { page = page, total = total, records = record, rows = rst.Skip((page - 1) * rows).Take(page * rows).ToList() }, JsonRequestBehavior.AllowGet);
        }
        [AjaxOnly]
        public JsonResult GetRoleList()
        {
            var rst = ViewData["rolelist"] = rolemodel.GetRoleList();
            return Json(rst, JsonRequestBehavior.AllowGet);
        }

        [Authorize]
        public ActionResult AddUser()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Settings";
            ViewData["level2nav"] = "UserManage";

            var currentlevel = CommonModel.GetSessionUserLevel();
            ViewData["level"] = usermodel.GetRegionOptionsForUserView(currentlevel);
            ViewData["curentlevel"] = (int)CommonModel.GetSessionUserLevel();

            ViewData["rolelist"] = rolemodel.GetRoleList();


            BingChongDBDataContext db = new BingChongDBDataContext();
            var userid = CommonModel.GetSessionUserID();
            var user = db.users.Where(m => m.uid == userid).FirstOrDefault();
            switch (currentlevel)
            {
                case CommonModel.UserLevel.ADMIN:
                    ViewData["shenglist"] = regionmodel.GetShengList().Where(m=>m.status==1).ToList();
                    ViewData["shilist"] = regionmodel.GetShiList().Where(m => m.status == 1).ToList();
                    ViewData["xianlist"] = regionmodel.GetXianList().Where(m => m.status == 1).ToList();
                    break;

                case CommonModel.UserLevel.GUOJIAJI:
                    ViewData["shenglist"] = regionmodel.GetShengList().Where(m => m.status == 1).ToList();
                    ViewData["shilist"] = regionmodel.GetShiList().Where(m => m.status == 1).ToList();
                    ViewData["xianlist"] = regionmodel.GetXianList().Where(m => m.status == 1).ToList();
                    break;

                case CommonModel.UserLevel.SHENGJI:
                    ViewData["shenglist"] = db.shengs.Where(m=>m.uid==user.sheng_id).Select(m => new RegionList
                                                {
                                                    name = m.name,
                                                    uid = m.uid,
                                                    postcode = m.postcode,
                                                }).ToList();
                    ViewData["shilist"] = db.shis.Where(m => m.deleted == 0 && m.sheng_id == user.sheng_id && m.status == 1).Select(m => new RegionList
                                                {
                                                    name = m.name,
                                                    uid = m.uid,
                                                    postcode = m.postcode,
                                                }).ToList();
                    ViewData["xianlist"] = regionmodel.GetXianList();
                    break;

                case CommonModel.UserLevel.SHIJI:
                    ViewData["shenglist"] = db.shengs.Where(m=>m.uid==user.sheng_id).Select(m => new RegionList
                                                {
                                                    name = m.name,
                                                    uid = m.uid,
                                                    postcode = m.postcode,
                                                }).ToList();

                    ViewData["shilist"] = db.shis.Where(m => m.uid == user.shi_id).Select(m => new RegionList
                                                {
                                                    name = m.name,
                                                    uid = m.uid,
                                                    postcode = m.postcode,
                                                }).ToList();

                    ViewData["xianlist"] = db.xians.Where(m => m.deleted == 0 && m.shi_id == user.shi_id && m.status == 1).Select(m => new RegionList
                                        {
                                            name = m.name,
                                            uid = m.uid,
                                            postcode = m.postcode,
                                        }).ToList();
                    break;

                case CommonModel.UserLevel.XIANJI:
                     ViewData["shenglist"] = db.shengs.Where(m=>m.uid==user.sheng_id).Select(m => new RegionList
                                                {
                                                    name = m.name,
                                                    uid = m.uid,
                                                    postcode = m.postcode,
                                                }).ToList();

                    ViewData["shilist"] = db.shis.Where(m => m.uid == user.shi_id).Select(m => new RegionList
                                                {
                                                    name = m.name,
                                                    uid = m.uid,
                                                    postcode = m.postcode,
                                                }).ToList();

                    ViewData["xianlist"] = db.xians.Where(m => m.uid == user.xian_id).Select(m => new RegionList
                                                {
                                                    name = m.name,
                                                    uid = m.uid,
                                                    postcode = m.postcode,
                                                }).ToList();
                    break;

            }



            return View();
        }

        [Authorize]
        public ActionResult ViewUser(long id)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Settings";
            ViewData["level2nav"] = "UserManage";

            ViewData["userinfo"] = usermodel.GetUserInfoById(id);
            ViewData["rolelist"] = rolemodel.GetRoleList();
            ViewData["shenglist"] = regionmodel.GetShengList();
            ViewData["shilist"] = regionmodel.GetShiList();
            ViewData["xianlist"] = regionmodel.GetXianList();
            return View();
        }

        [Authorize]
        public ActionResult EditUser(long id)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Settings";
            ViewData["level2nav"] = "UserManage";
            ViewData["role"] = CommonModel.GetUserRoleInfo();
            ViewData["level"] = usermodel.GetRegionOptionsForUserView(CommonModel.GetSessionUserLevel());
            ViewData["curentlevel"] = (int)CommonModel.GetSessionUserLevel();

            ViewData["userinfo"] = usermodel.GetUserInfoById(id);
            ViewData["roleid"] = usermodel.GetUserInfoById(id).roleid;

            ViewData["rolelist"] = rolemodel.GetRoleList();


            ViewData["shenglist"] = regionmodel.GetShengList();
            ViewData["shilist"] = regionmodel.GetShiList();
            ViewData["xianlist"] = regionmodel.GetXianList();
            return View("AddUser");
        }
        [HttpPost]
        public JsonResult DeleteUser(string delids)
        {
            string[] ids = delids.Split(',');
            long[] selcheckbox = ids.Where(m => !String.IsNullOrWhiteSpace(m)).Select(m => long.Parse(m)).ToArray();
            bool rst = usermodel.DeleteUser(selcheckbox);

            return Json(rst, JsonRequestBehavior.AllowGet);
        }
        [HttpPost]
        [AjaxOnly]
        public JsonResult SubmitUser(long uid, long role, string username, string userpwd, string realname,
                                     string company, string phone, string duty, string imgurl)
        {
            var rst = "";
            String level = Request.Form["level"];
            String xianid = Request.Form["xian"];
            String shiid = Request.Form["shi"];
            String shengid = Request.Form["sheng"];

            if (uid == 0)
            {
                rst = usermodel.InserteUser(level, role, username, userpwd, realname, company, phone, duty, imgurl, xianid, shiid, shengid);
            }
            else
            {
                rst = usermodel.UpdateUserInfo(uid, role, userpwd, realname, company, phone, duty, imgurl);
            }

            return Json(rst, JsonRequestBehavior.AllowGet);
        }


        [AjaxOnly]
        public JsonResult CheckUniqueUserName(string username, long uid)
        {
            bool rst = usermodel.CheckDuplicateName(username, uid);

            return Json(rst, JsonRequestBehavior.AllowGet);
        }
        [HttpGet]
        public JsonResult SearchUserList(int level, int sheng, int shi, int xian, long roleid, string username, int rows, int page)
        {
            var rst = usermodel.SearchUserList(level, sheng, shi, xian, roleid, username);
            var user = CommonModel.GetSessionUser();

            switch (CommonModel.GetSessionUserLevel())
            {
                case CommonModel.UserLevel.ADMIN:
                    break;
                case CommonModel.UserLevel.GUOJIAJI:
                    if (level == 4)
                    {
                        rst = rst.Where(m=> m.roleid == 0).ToList();
                    }
                    else
                    {
                        rst = rst.Where(m => m.roleid != 0).ToList();
                    }
                    break;
                case CommonModel.UserLevel.SHENGJI:
                    if (level == 3)
                    {
                        rst = rst.Where(m => m.sheng_id == user.sheng_id && m.roleid == 0).ToList();
                    }
                    else
                    {
                        rst = rst.Where(m => m.sheng_id == user.sheng_id && m.roleid != 0).ToList();
                    }
                  //  rst = rst.Where(m => m.sheng_id == user.sheng_id ).ToList();

                    break;
                case CommonModel.UserLevel.SHIJI:
                    if (level == 2)
                    {
                        rst = rst.Where(m => m.shi_id == user.shi_id && m.roleid == 0).ToList();

                    }
                    else
                    {
                        rst = rst.Where(m => m.shi_id == user.shi_id && m.roleid != 0).ToList();

                    }
                  //  rst = rst.Where(m => m.shi_id == user.shi_id).ToList();
                    break;
                case CommonModel.UserLevel.XIANJI:
                    rst = rst.Where(m => m.xian_id == user.xian_id && m.roleid == 0).ToList();
                    break;
            }

            
            int record = rst.Count;

            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }
            return Json(new { page = page, total = total, records = record, rows = rst.Skip((page - 1) * rows).Take(page * rows).ToList() }, JsonRequestBehavior.AllowGet);
        }
#endregion
#region field
        [Authorize]
        public ActionResult FieldManage()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Settings";
            ViewData["level2nav"] = "FieldManage";
            ViewData["fieldlist"] = fieldmodel.GetParentFieldList();

            return View();
        }

        [HttpGet]
        public JsonResult RetrieveFieldList(int rows, int page)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));

            var rst = fieldmodel.GetFieldList();

            int record = rst.Count;

            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }
            return Json(new { page = page, total = total, records = record, rows = rst.Skip((page - 1) * rows).Take(page * rows).ToList() }, JsonRequestBehavior.AllowGet);
        }
        [HttpGet]
        public JsonResult SearchFieldList(string searchParentFieldWord, string searchFieldWord, int rows, int page)
        {
            var rst = fieldmodel.SearchFieldList(searchParentFieldWord, searchFieldWord);

            int record = rst.Count;

            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }
            return Json(new { page = page, total = total, records = record, rows = rst.Skip((page - 1) * rows).Take(page * rows).ToList() }, JsonRequestBehavior.AllowGet);
        
        }
        [Authorize]
        public JsonResult SubmitAddField(string name, string type, string parentornot, string parentid,string unit,string option)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));

            String rst = fieldmodel.AddField(name, type, parentornot, parentid, unit, option);
            return Json(rst, JsonRequestBehavior.AllowGet);
        }

        [Authorize]
        public JsonResult SubmitEditField(long uid,string name)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));

            String rst = fieldmodel.EditField(uid,name);
            return Json(rst, JsonRequestBehavior.AllowGet);
        }

        
        [AjaxOnly]
        public JsonResult RefreshParentFieldList()
        {
            var rst = fieldmodel.GetParentFieldList();
            return Json(rst, JsonRequestBehavior.AllowGet);
        }

        [AjaxOnly]
        public JsonResult GetFieldInfo(long uid)
        {
            var rst = fieldmodel.GetFieldInfo(uid);
            return Json(rst, JsonRequestBehavior.AllowGet);
        }
#endregion
#region table
        [Authorize]
        public ActionResult TableManage()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Settings";
            ViewData["level2nav"] = "TableManage";
            ViewData["role"] = CommonModel.GetUserRoleInfo();
            return View();
        }
        [HttpGet]
        public JsonResult RetrieveTableList(string tablename,int rows, int page)
        {
            var rst = tablemodel.SearchTableList(tablename);

            int record = rst.Count;

            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }
            return Json(new { page = page, total = total, records = record, rows = rst.Skip((page - 1) * rows).Take(page * rows).ToList() }, JsonRequestBehavior.AllowGet);
        }

        [HttpGet]
        public JsonResult SearchTableList(string tablename, int rows, int page)
        {
            var rst = tablemodel.SearchTableList(tablename);

            int record = rst.Count;

            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }
            return Json(new { page = page, total = total, records = record, rows = rst.Skip((page - 1) * rows).Take(page * rows).ToList() }, JsonRequestBehavior.AllowGet);

        }
        [Authorize]
        public ActionResult AddTable()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Settings";
            ViewData["level2nav"] = "TableManage";

            var fieldInfo = tablemodel.GetChildField(tablemodel.GetFirstFieldList());
            ViewData["fieldInfo"] = fieldInfo;
            ViewData["html"] = tablemodel.GetHtml(fieldInfo,0);
            return View();
        }


        [AjaxOnly]
        public JsonResult ViewTable(string[] fieldid)
        {
            string rst = "";

            rst = tablemodel.GetTableView(fieldid);

            return Json(rst, JsonRequestBehavior.AllowGet);
        }

        [AjaxOnly]
        public JsonResult CheckUniqueTableName(string tablename)
        {
            bool rst = tablemodel.CheckDuplicateTable(tablename);

            return Json(rst, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        [AjaxOnly]
        public JsonResult SubmitTable(string tablename, string fieldid)
        {
            string rst = "";

            rst = tablemodel.InsertTable(tablename, fieldid);
           

            return Json(rst, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult DeleteTable(string delids)
        {
            string[] ids = delids.Split(',');
            long[] selcheckbox = ids.Where(m => !String.IsNullOrWhiteSpace(m)).Select(m => long.Parse(m)).ToArray();
            bool rst = tablemodel.DeleteTable(selcheckbox);

            return Json(rst, JsonRequestBehavior.AllowGet);
        }
        [HttpPost]
        [AjaxOnly]
        public JsonResult GetTableHtml(long tableid)
        {
            string rst = "";
            rst = tablemodel.GetTableHtml(tableid);
            return Json(rst, JsonRequestBehavior.AllowGet);
        }
#endregion
        [Authorize]
        public ActionResult SystemRecover()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Settings";
            ViewData["level2nav"] = "SystemRecover";
            ViewData["info"] = backupmodel.GetBackupInfo();
            ViewData["time"] = backupmodel.GetBackupInfo().backuptime.Hour;
            return View();
        }
        [HttpGet]
        public JsonResult RetrieveBackupTable(int rows, int page)
        {
            var rst = backupmodel.GetBackupList();

            int record = rst.Count;

            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }
            return Json(new { page = page, total = total, records = record, rows = rst.Skip((page - 1) * rows).Take(page * rows).ToList() }, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        [AjaxOnly]
        public JsonResult SubmitBackupSetting(int peroid, string time, int cleanypperiod)
        {
            var rst = "";


            rst = backupmodel.UpdateBackupinfo(peroid, time, cleanypperiod);


            return Json(rst, JsonRequestBehavior.AllowGet);
        }
        //[Authorize]
        //[HttpPost]
        //public JsonResult RestoreDB(string uid)
        //{
        //    Dictionary<string, string> results = new Dictionary<string, string>();

        //    string succ = BackupModel.RestoreByid(long.Parse(uid));
        //    results.Add("success", succ);

        //    return Json(results);
        //}

        [Authorize]
        [HttpPost]
        public JsonResult RestoreTo(string flag)
        {
            string results = "";
            if (flag == "base" || flag == "factory")
            {
                results = BackupModel.RestoreTo(flag);
            }
            else
            {
                results = BackupModel.RestoreByid(long.Parse(flag));
            }
            return Json(results, JsonRequestBehavior.AllowGet);
        }

        [Authorize]
        [HttpPost]
        public JsonResult Backup()
        {
            string results = "";

            results= BackupModel.backup();
            return Json(results, JsonRequestBehavior.AllowGet);
        }

        [Authorize]
        public ActionResult PassWord()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            var id = CommonModel.GetSessionUserID();
            ViewData["userinfo"] = usermodel.GetUserInfoById(id);

            return View();
        }

        [HttpPost]
        [AjaxOnly]
        public JsonResult SubmitPass(long uid, string userpwd)
        {
            var rst = usermodel.SubmitPass(uid, userpwd);
            return Json(rst, JsonRequestBehavior.AllowGet);
        }

        public ActionResult Test()
        {
            BingChongBackend.shipin.shipinSoapClient c = new BingChongBackend.shipin.shipinSoapClient();
            var rst = c.BCUserlist("lvyunxinxi2014");
           
            return View();
        }

        public ActionResult HelpInfo()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Settings";
            ViewData["level2nav"] = "HelpInfo";

            ViewData["role"] = CommonModel.GetUserRoleInfo();
            return View();
        }


        [HttpGet]
        public JsonResult RetrieveHelpList(int rows, int page)
        {
            var rst = new HelpModel().GetHelpList();
            var user = CommonModel.GetSessionUser();

            int record = rst.Count;

            int total = 0;
            if ((record % rows) == 0)
            {
                total = record / rows;
            }
            else if ((record % rows) != 0)
            {
                total = record / rows + 1;
            }
            return Json(new { page = page, total = total, records = record, rows = rst.Skip((page - 1) * rows).Take(page * rows).ToList() }, JsonRequestBehavior.AllowGet);
        }

        public ActionResult AddHelp()
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Settings";
            ViewData["level2nav"] = "HelpInfo";

            return View();
        }
        [HttpPost]
        [AjaxOnly]
        public JsonResult SubmitHelp(long uid, string name, string contents)
        {
            string rst = "";


            if (uid == 0)
            {
                rst = new HelpModel().InsertHelp(name, contents);
            }
            else
            {
                rst = new HelpModel().UpdateHelp(uid, name, contents);
            }

            return Json(rst, JsonRequestBehavior.AllowGet);
        }

        public ActionResult EditHelp(long id)
        {
            string rootUri = string.Format("{0}://{1}{2}", Request.Url.Scheme, Request.Url.Authority, Url.Content("~"));
            ViewData["rootUri"] = rootUri;
            ViewData["level1nav"] = "Settings";
            ViewData["level2nav"] = "HelpInfo";

            ViewData["documentinfo"] = new HelpModel().GetHelpById(id);
            ViewData["uid"] = id;
            return View("AddHelp");
        }
        [HttpPost]
        public JsonResult DeleteHelp(string delids)
        {
            string[] ids = delids.Split(',');
            long[] selcheckbox = ids.Where(m => !String.IsNullOrWhiteSpace(m)).Select(m => long.Parse(m)).ToArray();
            bool rst = new HelpModel().DeleteHelp(selcheckbox);

            return Json(rst, JsonRequestBehavior.AllowGet);
        }

    }
}
