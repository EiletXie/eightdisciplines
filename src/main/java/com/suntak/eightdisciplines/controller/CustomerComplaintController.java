package com.suntak.eightdisciplines.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.suntak.eightdisciplines.dbErp.service.CommonUtilsService;
import com.suntak.eightdisciplines.entity.*;
import com.suntak.eightdisciplines.db8d.service.CustomerComplaintService;
import com.suntak.eightdisciplines.db8d.service.RecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

//注入的时候一定要是Controller 不要是RestController 因为它是rest接口（json格式） 是解析不到html
@Controller
@RequestMapping("/complaint")
public class CustomerComplaintController {

    @Resource
    private CustomerComplaintService customerComplaintService;

    @Resource
    private RecordService recordService;

    @Resource
    private CommonUtilsService commonUtilsService;


    @PostMapping("/complaintInfo")
    public String getComplaintsByCar(@RequestParam("leasts") String leasts, ModelMap map) {
        List<CustomerComplaint> list = customerComplaintService.getComplaintsByCar(leasts);
        for (CustomerComplaint c : list) {
            if (!"".equals(c.getCustomprocess())) {
                String customprocess = customerComplaintService.getMeaningByCode(c.getCustomprocess(), "CLAIM_ID");
                c.setCustomprocess(customprocess);
            }

            if (!"".equals(c.getRevokeresult())) {
                String revokeresult = customerComplaintService.getMeaningByCode(c.getRevokeresult(), "REVOKERESULT_ID");
                c.setRevokeresult(revokeresult);
            }

            if (!"".equals(c.getClaimtypes())) {
                String claimtypes = customerComplaintService.getMeaningByCode(c.getClaimtypes(), "TYPES_ID");
                c.setClaimtypes(claimtypes);
            }


        }

        map.addAttribute("complaintList", list);
        map.addAttribute("customerComplaint", new CustomerComplaint());

        return "complaintChange";
    }

    /**
     * 更新方法，并且生成修改历史记录
     *
     * @param session
     * @return
     */
    @ResponseBody
    @PostMapping("/editComplaint")
    public Msg editComplaint(@RequestBody JSONObject jsonObject, HttpSession session) throws Exception{
         User user = (User) session.getAttribute("user");
        String reason = jsonObject.getString("reason");
        JSONArray complaint = jsonObject.getJSONArray("complaint");

        System.out.println(complaint);
        List<BlameProcess> blameProcessList = jsonObject.getJSONArray("blameList").toJavaList(BlameProcess.class);
        List<BlameProcess> outBlameProcessList = jsonObject.getJSONArray("outBlameList").toJavaList(BlameProcess.class);

        // 新建一个现在的complaint
        CustomerComplaint nowComplaint = new CustomerComplaint();
        nowComplaint.setBase_uid(jsonObject.getString("base_uid"));
        nowComplaint.setModel(complaint.getJSONObject(0).get("value").toString());
        nowComplaint.setCustomcode(complaint.getJSONObject(1).get("value").toString());
        nowComplaint.setClaimtypes(complaint.getJSONObject(2).get("value").toString());
        nowComplaint.setCustomprocess(complaint.getJSONObject(3).get("value").toString());
        nowComplaint.setRevokeresult(complaint.getJSONObject(4).get("value").toString());
        nowComplaint.setDefedtdesc(complaint.getJSONObject(5).get("value").toString());
        nowComplaint.setOutBlameProcesses(outBlameProcessList);
        nowComplaint.setBlameProcesses(blameProcessList);
        System.out.println("现在的客诉对象" + nowComplaint);

        if (!"".equals(user.getUsername()) && user.getUsername() != null) {
            Record record = new Record();
            record.setModify_username(user.getAlternateName()); // 这里改成 使用姓名
            record.setEmp_id(user.getEmpId());
            record.setReason(reason);
            CustomerComplaint oldComplaint = customerComplaintService.getComplaintByBaseUid(jsonObject.getString("base_uid"));
            record.setCustomercode(oldComplaint.getCustomcode());
            record.setLeasts(oldComplaint.getLeasts());
            // 将变更内容进行封装
            String content = customerComplaintService.getComplaintChangeContent(nowComplaint);
            record.setContent(content);
            record.setCreate_date(new Date());
            System.out.println(complaint);
            System.out.println(record);
            recordService.addRecord(record);
            customerComplaintService.updateComplaint(nowComplaint);
            return Msg.success();
        } else
            return Msg.fail().add("message", "登录超时，用户信息失效，请重新登录！");
    }


    @ResponseBody
    @PostMapping("/editById")
    public Msg editComplaintById(@RequestParam("base_uid") String base_uid) {
        CustomerComplaint c = new CustomerComplaint();
        System.out.println("base_uid : " + base_uid);
        if (base_uid != null) {
            c = customerComplaintService.getComplaintByBaseUid(base_uid);
        }
        List<BlameProcess> blameSelectOptions = commonUtilsService.getBlameSelectOptions(c.getMfg_org_id(),c.getInventory_item_id());


        HashMap<String,Object> map = customerComplaintService.generateSelectList();

        return Msg.success().add("complaint", c).add("blameSelectOptions",blameSelectOptions)
                .add("type_list",map.get("type_list")).add("result_list",map.get("result_list"));
    }




    @ResponseBody
    @DeleteMapping("/delComplaint")
    public Msg delComplaint(@RequestParam("base_uid") String base_uid, @RequestParam("reason") String reason, HttpSession session) {
        CustomerComplaint complaint = customerComplaintService.getComplaintByBaseUid(base_uid);
        if ("P".equals(complaint.getStatus()))
            return Msg.fail().add("message", "删除失败，该客诉需让客服人员先回退！");
        else if ("T".equals(complaint.getStatus()))
            return Msg.fail().add("message", "该客诉已经删除了，请勿重复操作！");
        else {
            User user = (User) session.getAttribute("user");
            if (!"".equals(user.getUsername()) && user.getUsername() != null) {
                Record record = new Record();
                record.setModify_username(user.getUsername());
                record.setEmp_id(user.getEmpId());
                record.setLeasts(complaint.getLeasts());
                record.setReason(reason);
                record.setContent("删除客诉");
                record.setCustomercode(complaint.getCustomcode());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String create_date = sdf.format(new Date());
                record.setCreate_date(new Date());
                recordService.addRecord(record);
                customerComplaintService.deleteComplaint(base_uid);
            }
        }
        return Msg.success();
    }


    @GetMapping("/complaintChange")
    public String toComplaint(ModelMap map) {
        List<CustomerComplaint> list = new ArrayList<>();
        map.addAttribute("complaintList", list);
        map.addAttribute("customerComplaint", new CustomerComplaint());
        return "complaintChange";
    }



}
