using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace BingchongService.SvcManager
{
    public class ErrMgr
    {
        #region Error codes
        public const int ERRCODE_NONE = 0;
        public const int ERRCODE_NORMAL = -1;
        public const int ERRCODE_PARAM = -2;
        public const int ERRCODE_EXCEPTION = -3;        
        public const int ERRCODE_BLOCKEDUSER = -4;
        public const int ERRCODE_STATE = -5;
        public const int ERRCODE_TOKEN = -6;
        #endregion


        #region Error Messages
        public const String ERRMSG_NONE = "操作成功";
        public const String ERRMSG_NORMAL = "操作失败";
        public const String ERRMSG_PARAM = "参数格式不正确";
        public const String ERRMSG_EXCEPTION = "例外错误";
        public const String ERRMSG_NOTREGUSER = "未注册的用户";
        public const String ERRMSG_PWDWRONG = "密码错误";
        public const String ERRMSG_NODATA = "没有数据";
        public const String ERRMSG_STATE = "状态不合适，已进入审核状态";
        public const String ERRMSG_TOKEN = "用户令牌错误";
        #endregion
    }
}