using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace BingchongService.SvcManager
{
    public class ConstMgr
    {
        #region String Constants
        public const String STR_DIAN = "点";
        public const String STR_JIYONGHU = "级用户";
        public const String STR_CHENGGONG = "成功";
        public const String STR_CHENGJIAOLIANG = "成交量";
        public const String STR_WEIDING = "未定";
        public const String STR_YONGHUMING = "用户名 : ";
        public const String STR_SHOUJIHAO = "手机号 : ";
        #endregion

        public enum REVIEWSTATE
        {
            WAIT_CITY_VERIFY = 1,
            PASSED_CITY,
            NOPASSED_CITY,
            PASSED_PROVINCE,
            NOPASSED_PROVINCE
        }

        public enum EXTRATASK_STATE
        {
            WAITING = 0,
            RECEIVED,
            COMPLETED
        }

        public enum POINT_LEVEL
        {
            GUOJIA = 0,
            SHENG,
            SHI,
            XIAN
        }
    }
}