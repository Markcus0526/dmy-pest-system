using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
//using WeifenxiaoBackend.Models;


namespace BingChongBackend.Controllers
{
    public class HomeController : Controller
    {
        [Authorize]    
        public ActionResult Index()
        {

            return RedirectToAction("UpdatedInfoManage", "UpdatedInfo");
        }
        [Authorize]
        public ActionResult About()
        {
            return View();
        }
    }
}
