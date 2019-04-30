package com.suntak.eightdisciplines.service.impl;

import com.suntak.eightdisciplines.dao.CustomerComplaintDao;
import com.suntak.eightdisciplines.entity.CustomerComplaint;
import com.suntak.eightdisciplines.service.CustomerComplaintService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 经过思考，决定将繁琐的业务也在业务层，而不是在控制层
 */
@Service
public class CustomerComplaintServiceImpl implements CustomerComplaintService {

    @Resource
    CustomerComplaintDao customerComplaintDao;

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
    public String getComplaintChangeContent(CustomerComplaint complaint) {

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
            if (oldComplaint.getRevokeresult() == "" || oldComplaint.getRevokeresult() == null)
                content += "将不计入客诉理由： 由 空 改为 " + Revokeresult + "  ";
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
        return content;
    }
}
