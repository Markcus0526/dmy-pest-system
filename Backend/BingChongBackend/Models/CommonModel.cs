using System;
using System.Collections.Generic;
using System.Linq;
using System.Collections;

using System.Configuration;
using System.Web.Security;
using System.Web.Mvc;

namespace BingChongBackend.Models
{
    public class CommonModel : Controller
    {
        public static MasterDBDataContext _masterdb;

        public enum UserLevel
        {
            ADMIN = 0,//超管
            XIANJI ,  //县级
            SHIJI,    //市级
            SHENGJI,   //省级
            GUOJIAJI
        }

        public ActionResult Index()
        {
            return View();
        }


        public static long GetSessionUserID()
        {
            FormsIdentity fId = (FormsIdentity)System.Web.HttpContext.Current.User.Identity;
            FormsAuthenticationTicket authTicket = fId.Ticket;
            string[] udata = authTicket.UserData.Split(new Char[] { '|' });
            string uid = udata[1];

            return long.Parse(uid);

        }

        public static user GetSessionUser(){
            var userid = CommonModel.GetSessionUserID();

            BingChongDBDataContext db = new BingChongDBDataContext();
            var user = db.users.Where(m => m.uid == userid).FirstOrDefault();
            return user;
        }
        public static UserLevel GetSessionUserLevel()
        {
            FormsIdentity fId = (FormsIdentity)System.Web.HttpContext.Current.User.Identity;
            FormsAuthenticationTicket authTicket = fId.Ticket;
            string[] udata = authTicket.UserData.Split(new Char[] { '|' });
         //   long userlevel = int.Parse(udata[2]);

            UserLevel level = (UserLevel)Enum.Parse(typeof(UserLevel), udata[2]);
            return level;
        }


        public static string GetUserRoleInfo()
        {
            FormsIdentity fId = (FormsIdentity)System.Web.HttpContext.Current.User.Identity;
            FormsAuthenticationTicket authTicket = fId.Ticket;
            string[] udata = authTicket.UserData.Split(new Char[] { '|' });
            
            long roleid = Convert.ToInt64(udata[0]);
            BingChongDBDataContext db = new BingChongDBDataContext();

            return db.tbl_rights.Where(m=>m.uid==roleid).Select(m=>m.role).FirstOrDefault();
        }

        public static string[] GetSessionUserRoles()
        {
            string roleinfo = GetUserRoleInfo();

            return roleinfo.Split(',');
        }
        public static MasterDBDataContext GetMasterDBContext()
        {
            _masterdb = new MasterDBDataContext(ConfigurationManager.ConnectionStrings["masterConnectionString"].ConnectionString);
            return _masterdb;
        }
    }
}
