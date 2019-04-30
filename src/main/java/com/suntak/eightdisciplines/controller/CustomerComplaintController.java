package com.suntak.eightdisciplines.controller;

import com.suntak.eightdisciplines.entity.CustomerComplaint;
import com.suntak.eightdisciplines.entity.Msg;
import com.suntak.eightdisciplines.entity.Record;
import com.suntak.eightdisciplines.entity.User;
import com.suntak.eightdisciplines.service.CustomerComplaintService;
import com.suntak.eightdisciplines.service.RecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//注入的时候一定要是Controller 不要是RestController 因为它是rest接口（json格式） 是解析不到html
@Controller
@RequestMapping("/complaint")
public class CustomerComplaintController {

    @Resource
    private CustomerComplaintService customerComplaintService;

    @Resource
    private RecordService recordService;


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
     * @param complaint
     * @param reason
     * @param session
     * @return
     */
    @ResponseBody
    @PostMapping("/editComplaint")
    public Msg editComplaint(CustomerComplaint complaint, @RequestParam("reason") String reason, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String base_uid = complaint.getBase_uid();
        if (!"".equals(user.getUsername()) && user.getUsername() != null) {
            Record record = new Record();
            record.setModify_username(user.getAlternateName()); // 这里改成 使用姓名
            record.setEmp_id(user.getEmpId());
            record.setReason(reason);
            record.setCustomercode(complaint.getCustomcode());
            CustomerComplaint oldComplaint = customerComplaintService.getComplaintByBaseUid(complaint.getBase_uid());
            record.setLeasts(oldComplaint.getLeasts());
            // 将变更内容进行封装
            String content = customerComplaintService.getComplaintChangeContent(complaint);
            record.setContent(content);
            record.setCreate_date(new Date());
            System.out.println(complaint);
            System.out.println(record);
            recordService.addRecord(record);
            customerComplaintService.updateComplaint(complaint);
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
        System.out.println("---------------------------------->");
        System.out.println(c);
        return Msg.success().add("complaint", c);
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


    @GetMapping("/generateOption")
    public Msg generateOptionsSelect(@RequestParam("org_id") String org_id,@RequestParam("item_id") String item_id) {

        return Msg.success();
    }
}
