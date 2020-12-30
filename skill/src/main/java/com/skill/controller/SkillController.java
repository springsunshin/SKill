package com.skill.controller;

import cn.hutool.core.util.ObjectUtil;
import com.skill.domain.Item;
import com.skill.domain.User;
import com.skill.dto.ResponseResult;
import com.skill.dto.SkillUrl;
import com.skill.exception.ItemException;
import com.skill.exception.UserException;
import com.skill.service.impl.ItemServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/skill")
public class SkillController {

    @Autowired
    ItemServiceImp itemServiceImp;

    @RequestMapping(path = "/item")
    public ModelAndView item(){
        List<Item> list=itemServiceImp.getAllItems();
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("list",list);
        modelAndView.setViewName("skill_list");
        return modelAndView;
    }

    @RequestMapping(path = "/detail/{skillID}" ,method = RequestMethod.GET)
    public String skillDetail(@PathVariable("skillID") Integer skillID, Model model){
        Item item=itemServiceImp.getOneItem(skillID);
        if (ObjectUtil.isEmpty(item)){
            throw new ItemException("商品不存在");
        }
        model.addAttribute("item",item);
        return "detail";
    }


    /**
     * 获取服务器时间
     * @return
     */
    @RequestMapping(path = "/now",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Long> now(){
        Date date=new Date();
        return new ResponseResult(true,date.getTime(),"now time");
    }

    /**
     * 获取商品的秒杀地址
     * @param skillID
     * @param session
     * @return
     */
    @RequestMapping(path = "/getUrl/{skillID}",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<SkillUrl> getSkillUrl(@PathVariable("skillID") Integer skillID, HttpSession session) {

        //判断用户是否登录，未登录，直接访问该接口不给予响应
        User user = (User) session.getAttribute("user");
        if (ObjectUtil.isEmpty(user)) {
            throw new UserException("未登录");
        }

        ResponseResult<SkillUrl> responseResult = new ResponseResult<>();
        try {
            SkillUrl skillUrl = itemServiceImp.getSkillUrl(skillID);
            System.out.println(skillUrl);//===================================输出语句================

            responseResult.setData(skillUrl);
            responseResult.setSuccess(true);
            responseResult.setMessage("ok");
            return responseResult;
        } catch (Exception e) {
            responseResult.setSuccess(false);
            responseResult.setMessage(e.getMessage());
            return responseResult;
        }
    }

    /**
     * 下单
     * @param skillId
     * @param md5
     * @param session
     * @return
     */
    @RequestMapping(path = "/excSkill/{skillId}/{md5}",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<SkillUrl> excSkill(@PathVariable("skillId") Integer skillId,@PathVariable("md5") String md5,HttpSession session){
        ResponseResult<SkillUrl> responseResult=new ResponseResult<>();
        User user= (User) session.getAttribute("user");
        if (ObjectUtil.isEmpty(user)){
            responseResult.setSuccess(false);
            responseResult.setMessage("user not login");
            return responseResult;
        }
        boolean md5VerifyResult=itemServiceImp.verifySkillMd5(skillId, md5);
        if (!md5VerifyResult){
            responseResult.setSuccess(false);
            responseResult.setMessage("md5 is fail");
            return responseResult;
        }
        boolean result=itemServiceImp.startSkill(user,skillId);
        if (!result){
            responseResult.setSuccess(false);
            responseResult.setMessage("order fail");
            return responseResult;
        }
        String orderCode=itemServiceImp.downOrder(user,skillId);
        responseResult.setMessage("order success");
        responseResult.setData(orderCode);
        responseResult.setSuccess(true);
        return responseResult;
    }

    /**
     * 转发支付页面
     * @param skillId
     * @param model
     * @return
     */
    @RequestMapping(path = "/pay/{skillId}/{orderCode}",method = RequestMethod.GET)
    public String pay(@PathVariable("skillId") Integer skillId,@PathVariable("orderCode") String orderCode,Model model){
        Item item=itemServiceImp.getOneItem(skillId);
        model.addAttribute("item",item);
        model.addAttribute("orderCode",orderCode);
        model.addAttribute("orderTime",new Date().getTime());
        return "order";
    }
}
