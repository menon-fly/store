package com.fly.store.controller;

import com.fly.store.commons.utils.JsonResult;
import com.fly.store.controller.ex.*;
import com.fly.store.service.ex.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

public class BaseController {
    public static final int SUCCESS = 200;//成功
    public static final int FAIL = 400; //失败

    /**
     * 统一处理ServiceException异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicatedException){
            result.setState(100); //用户名被占用
        }else if(e instanceof InsertException){
            result.setState(102); //注册时发生异常
        }else if(e instanceof UserNotFoundException){
            result.setState(101);  //用户不存在
        }else if (e instanceof PasswordNotMatchException){
            result.setState(103); //密码错误
        }else if (e instanceof UpdateException){
            result.setState(201); //更新异常
        }else if (e instanceof FileEmptyException){
            result.setState(300); //文件为空异常
        }else if (e instanceof FileSizeException){
            result.setState(301); //文件大小异常
        }else if (e instanceof FileStateException){
            result.setState(302); //文件状态异常
        }else if (e instanceof FileTypeException){
            result.setState(303); //文件类型异常
        }else if (e instanceof FileUploadIOException){
            result.setState(304); //文件输入输出异常
        }else if (e instanceof AddressCountLimitException){
            result.setState(400);//地址超出限制异常
        }else if (e instanceof AddressNotFoundException){
            result.setState(401);
        }else if (e instanceof DeleteException){
            result.setState(500);
        }else if (e instanceof ProductNotFoundException){
            result.setState(600);
        }else if (e instanceof UpdateException){
            result.setState(700);
        }
        return result;
    }

    protected final Integer getUidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
