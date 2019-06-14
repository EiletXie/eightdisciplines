package com.suntak.eightdisciplines.db8d.service.impl;

import com.suntak.eightdisciplines.db8d.dao.BlameProcessDao;
import com.suntak.eightdisciplines.db8d.dao.CustomerComplaintDao;
import com.suntak.eightdisciplines.db8d.service.CustomerComplaintService;
import com.suntak.eightdisciplines.entity.BlameProcess;
import com.suntak.eightdisciplines.entity.CustomerComplaint;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 经过思考，决定将繁琐的业务也在业务层，而不是在控制层
 */
@Service
public class CustomerComplaintServiceImpl implements CustomerComplaintService {

    @Resource
    CustomerComplaintDao customerComplaintDao;

    @Resource
    BlameProcessDao blameProcessDao;

    @Override
    public List<CustomerComplaint> getComplaintsByCar(String leasts){
        return customerComplaintDao.getComplaintsByCar(leasts);
    }

    @Override
    public void deleteComplaint(String base_uid) {
         customerComplaintDao.deleteComplaint(base_uid);
    }

    @Override
    public void updateComplaint(CustomerComplaint customerComplaint) {
         customerComplaintDao.updateComplaint(customerComplaint);
         // 先删除 责任工序的所有数据
         blameProcessDao.deleteBlamesByBaseUid(customerComplaint.getBase_uid());
         // 插入 责任产生工序和流出工序
        for ( BlameProcess blame : customerComplaint.getBlameProcesses()) {
            blame.setBase_uid(customerComplaint.getBase_uid());
            blameProcessDao.addBlameProcess(blame);
        }
        for ( BlameProcess blame : customerComplaint.getOutBlameProcesses()) {
            blame.setBase_uid(customerComplaint.getBase_uid());
            blameProcessDao.addBlameProcess(blame);
        }
    }

    @Override
    public String getMeaningByCode(String code,String code_type) {
        return customerComplaintDao.getMeaningByCode(code,code_type);
    }

    @Override
    public CustomerComplaint getComplaintByBaseUid(String base_uid) {
        return customerComplaintDao.getComplaintByBaseUid(base_uid);
    }

    @Override
    public  HashMap<String,Object> generateSelectList() {
        List<Map<Object, Object>> type_list =  customerComplaintDao.generateClaimtypeList();
        List<Map<Object, Object>> result_list =  customerComplaintDao.generateRevokeresultList();
        HashMap<String,Object> map = new HashMap<>();
        map.put("type_list",type_list);
        map.put("result_list",result_list);
        return map;
    }

    @Override
    public String getComplaintChangeContent(CustomerComplaint complaint, List<MultipartFile> files) {

        String base_uid = complaint.getBase_uid();
        //1、 获取未修改前的客诉记录
        CustomerComplaint oldComplaint = customerComplaintDao.getComplaintByBaseUid(base_uid);
        //2、 比对修改内容，生成修改的事项字符串
        String content = "";
        if (!complaint.getCustomprocess().equals(oldComplaint.getCustomprocess())) {
            String oldCustomprocess = customerComplaintDao.getMeaningByCode(oldComplaint.getCustomprocess(), "CLAIM_ID");
            String Customprocess = customerComplaintDao.getMeaningByCode(complaint.getCustomprocess(), "CLAIM_ID");
            content += "将" + oldCustomprocess + "  改为 " + Customprocess + "  ";
        }

        String oldRevokeresult = "";
        if (complaint.getRevokeresult() == null) {
            if (!"".equals(oldComplaint.getRevokeresult()) && oldComplaint.getRevokeresult() != null) {
                oldRevokeresult = customerComplaintDao.getMeaningByCode(oldComplaint.getRevokeresult(), "REVOKERESULT_ID");
                content += "将不计入客诉理由： 由 " + oldRevokeresult + "  改为空 ";
            }
        } else if (complaint.getRevokeresult() != null && !complaint.getRevokeresult().equals(oldComplaint.getRevokeresult())) {
            oldRevokeresult = customerComplaintDao.getMeaningByCode(oldComplaint.getRevokeresult(), "REVOKERESULT_ID");
            String Revokeresult = customerComplaintDao.getMeaningByCode(complaint.getRevokeresult(), "REVOKERESULT_ID");
            if (oldComplaint.getRevokeresult() == "" || oldComplaint.getRevokeresult() == null) {
                if(Revokeresult != null && !Revokeresult.equals(""))
                content += "将不计入客诉理由： 由 空 改为 " + Revokeresult + "  ";
            }
            else
                content += "将不计入客诉理由： 由 " + oldRevokeresult + "  改为 " + Revokeresult + "  ";
        }

        if (!complaint.getClaimtypes().equals(oldComplaint.getClaimtypes())) {
            String oldClaimtypes = customerComplaintDao.getMeaningByCode(oldComplaint.getClaimtypes(), "TYPES_ID");
            String Claimtypes = customerComplaintDao.getMeaningByCode(complaint.getClaimtypes(), "TYPES_ID");
            content += "将投诉类别： 由 " + oldClaimtypes + "  改为 " + Claimtypes + "  ";
        }


        if (!complaint.getDefedtdesc().equals(oldComplaint.getDefedtdesc())) {
            content += "将缺陷描述： 由 " + oldComplaint.getDefedtdesc() + "  改为 " + complaint.getDefedtdesc() + "  ";
        }

        String nowBlameList = "";
        for(BlameProcess blame : complaint.getBlameProcesses()){
            nowBlameList += blame.getOperation_description() + " ";
        }
        String oldBlameList = "";
        for(BlameProcess blame : oldComplaint.getBlameProcesses()){
            oldBlameList += blame.getOperation_description() + " ";
        }
        if(!nowBlameList.equals(oldBlameList)){
            content += "将责任产出工序： 由 " + oldBlameList + " 改为 " + nowBlameList + " ";
        }

        String nowOutBlameList = "";
        for(BlameProcess blame : complaint.getOutBlameProcesses()){
            nowOutBlameList += blame.getOperation_description() + " ";
        }
        String oldOutBlameList = "";
        for(BlameProcess blame : oldComplaint.getOutBlameProcesses()){
            oldOutBlameList += blame.getOperation_description() + " ";
        }
        if(!nowOutBlameList.equals(oldOutBlameList)){
            content += "将责任流出工序： 由 " + oldOutBlameList + " 改为 " + nowOutBlameList + " ";
        }


        if(!files.isEmpty()){
            content += "添加附件 : ";
            for(MultipartFile file : files){
                content += file.getOriginalFilename() + " ";
            }
        }
        return content;
    }
}
