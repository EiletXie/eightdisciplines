package com.suntak.eightdisciplines.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.suntak.eightdisciplines.dbErp.service.CommonUtilsService;
import com.suntak.eightdisciplines.entity.*;
import com.suntak.eightdisciplines.db8d.service.CustomerComplaintService;
import com.suntak.eightdisciplines.db8d.service.RecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    private static final Logger logger = LoggerFactory.getLogger(CustomerComplaintController.class);

    @PostMapping("/complaintInfo")
    public String getComplaintsByCar(@RequestParam("leasts") String leasts, ModelMap map) {
        List<CustomerComplaint> list = customerComplaintService.getComplaintsByCar(leasts);

        logger.info("通过Car号" + leasts + "获取客诉信息");
        for (CustomerComplaint c : list) {

            logger.info("查询的客诉 ->" + c);
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

            switch (c.getStatus()){
               case "E" : c.setStatus("已完成");break;
               case "T" : c.setStatus("已删除");break;
               case "R" : c.setStatus("已回退");break;
               default:c.setStatus("在流程中");break;
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
    @PostMapping("/editComplaint") //@RequestBody JSONObject jsonObject
    @Transactional
    public Msg editComplaint(HttpSession session, HttpServletRequest request, @RequestParam(value = "complaint_file", required = true) List<MultipartFile> files) throws Exception {
        User user = (User) session.getAttribute("user");
        JSONObject jsonObject = JSONObject.parseObject(request.getParameter("complaint"));
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
            String content = customerComplaintService.getComplaintChangeContent(nowComplaint, files);
            record.setContent(content);
            record.setCreate_date(new Date());
            System.out.println(complaint);
            logger.info("修改客诉 ：原内容 " + complaint);
            int rid = recordService.addRecord(record);
            customerComplaintService.updateComplaint(nowComplaint);

            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    logger.info("未上传文件");
                } else {
                    AttachFile attachFile = new AttachFile();
                    String fileName = file.getOriginalFilename();
                    logger.info("上传的文件 ： " + fileName);
                    attachFile.setFilename(fileName);
                    attachFile.setFilesize(file.getSize() + "");
                    attachFile.setCreate_date(new Date());
                    attachFile.setRid(record.getRid());

                    String suffixName = fileName.substring(fileName.lastIndexOf("."));
                    logger.info("上传的后缀名为：" + suffixName);

                    // 文件上传后的路径 E://test// window测试目录,下面是实际linux附件存放目录
                    String filePath = "//usr//local//attachfiles-8d//";
                    // 上传Linux服务器，要求不能出现中文路径
                    fileName = UUID.randomUUID() + suffixName;
                    attachFile.setFilepath(filePath + fileName);
                    File dest = new File(filePath + fileName);
                    // 检测是否存在目录
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdirs();
                    }

                    try {
                        file.transferTo(dest);
                        System.out.println(attachFile);
                        recordService.addAttachFile(attachFile);

                        Msg.success();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            return Msg.fail();
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
        List<BlameProcess> blameSelectOptions = commonUtilsService.getBlameSelectOptions(c.getMfg_org_id(), c.getInventory_item_id());


        HashMap<String, Object> map = customerComplaintService.generateSelectList();

        return Msg.success().add("complaint", c).add("blameSelectOptions", blameSelectOptions)
                .add("type_list", map.get("type_list")).add("result_list", map.get("result_list"));
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
                record.setModify_username(user.getAlternateName());
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

    @GetMapping("/userSelectPanel")
    public String toSelectPanel() {
        return "selectPanel";
    }
}
