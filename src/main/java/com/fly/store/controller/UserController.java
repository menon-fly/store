package com.fly.store.controller;

import com.fly.store.commons.utils.JsonResult;
import com.fly.store.commons.utils.UUIDUtils;
import com.fly.store.controller.ex.*;
import com.fly.store.pojo.User;
import com.fly.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public JsonResult<Void> addUser(User user){
        Integer ret = userService.addUser(user);
        JsonResult<Void> result = new JsonResult<>();
        if (ret == 1){
            result.setState(SUCCESS);
            result.setMessage("注册成功");
        }else {
            result.setState(FAIL);
            result.setMessage("注册失败");
        }
        return result;
    }

    @RequestMapping("/login")
    public JsonResult<User> queryByUsername(String username, String password,
                                            HttpSession session){
        User user = userService.queryByUsername(username,password);
        session.setAttribute("uid",user.getUid());
        session.setAttribute("username",user.getUsername());
        return new JsonResult<User>(SUCCESS,user);
    }

    @RequestMapping("/logout")
    public JsonResult<Void> logout(HttpSession session){
        session.removeAttribute("uid");
        session.removeAttribute("username");
        session.invalidate();
        return new JsonResult<>(SUCCESS);
    }

    @RequestMapping("/editPassword")
    public JsonResult<Void> editPasswordByUid(String oldPassword,String newPassword,HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.editPasswordByUid(uid,username,oldPassword,newPassword);
        return new JsonResult<>(SUCCESS);
    }

    @RequestMapping("/queryByUid")
    public JsonResult<User> queryByUid(HttpSession session){
        User user = userService.queryByUid(getUidFromSession(session));
        return new JsonResult<>(SUCCESS,user);
    }

    @RequestMapping("/editInfo")
    public JsonResult<Void> editInfoBy(User user,HttpSession session){
        user.setUid(getUidFromSession(session));
        userService.editInfo(user);
        return new JsonResult<>(SUCCESS);
    }

    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    public static final List<String> AVATAR_TYPE = new ArrayList<>();
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
        AVATAR_TYPE.add("image/jpg");
    }

    @RequestMapping("/editAvatar")  //当前端发送的参数名称不一致可以使用@RequestParam映射
    public JsonResult<String> editAvatar(@RequestParam("file")  //@PathVariable也行
                                       MultipartFile file,//任何文件都可以接收
                                       HttpSession session){
        if (file.isEmpty()){
            throw new FileEmptyException("文件为空");
        }
        if (file.getSize() > AVATAR_MAX_SIZE){
            throw new FileSizeException("文件超出限制");
        }
        String contentType = file.getContentType();
        if (!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("文件类型不支持");
        }
        //String path = session.getServletContext().getRealPath("/upload");
        File dir = new File("D:/java/projects/store/src/main/resources/static/images/avatar");
        if (!dir.exists()){
            dir.mkdir();
        }
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String filename = UUIDUtils.getUUID().toUpperCase() + suffix;
        File dest = new File(dir,filename); //在dir目录创建创建filename文件
        try{
            file.transferTo(dest); //将file文件写入dest文件
        }catch (IOException e){
            throw new FileUploadIOException("文件读写异常");
        }catch (FileStateException e){
            throw new FileStateException("文件状态异常");
        }
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        String avatar = "/static/images/avatar/" + filename;
        System.out.println(avatar);
        userService.editAvatar(uid,avatar,username);
        return new JsonResult<>(SUCCESS,avatar);
    }
}
