package net.gzhqlf.dszdy.service;

import net.gzhqlf.dszdy.entity.NotifyEntity;
import net.gzhqlf.dszdy.po.NotifyPo;
import net.gzhqlf.dszdy.po.ResultPo;
import net.gzhqlf.dszdy.repository.NotifyRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NotifyService {

    @Resource
    private NotifyRepository notifyRepository;

    public ResultPo getNotify(int userId) {

        List<NotifyEntity> notifyEntityList = getNotifies(userId, 2);

        if (notifyEntityList.size() != 0)
            notifyRepository.clearUserNotifies(userId);

        NotifyPo notifyPo = new NotifyPo();
        notifyPo.setNotifyCounts(notifyEntityList.size());
        notifyPo.setNotifyUserId(userId);
        notifyPo.setNotify(notifyEntityList);

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setData(notifyPo);

        if (notifyEntityList.size() != 0 && notifyEntityList.get(0).getNotifyStatus() == 0) {
            if (notifyEntityList.get(0).getNotifyType() == 1 || notifyEntityList.get(0).getNotifyType() == 3) {
                resultPo.setMessage(notifyEntityList.get(0).getNotifyContent());
            }
            if (notifyEntityList.get(0).getNotifyType() == 2) {
                resultPo.setMessage("您有一条新的私信消息");
            }
        }

        return resultPo;

    }

    public List<NotifyEntity> getNotifies(int userId, int notStatus) {
         return notifyRepository.findByNotifyToUserIdAndNotifyStatusNot(userId, notStatus);
    }

    public List<NotifyEntity> getNotifies(int userId, int notStatus, int notifyType) {
        return notifyRepository.findByNotifyToUserIdAndNotifyStatusNotAndNotifyType(userId, notStatus, notifyType);
    }

    public void setNotify(NotifyEntity notifyEntity) {
        notifyRepository.saveAndFlush(notifyEntity);
    }

    public void updateNotifyStatus(int notifyType, int userId, int notifyNewStatus) {
        notifyRepository.updateNotifyStatus(notifyNewStatus, userId, notifyType);

    }

    public void updateNotifyStatus(int notifyId, int notifyNewStatus) {
        notifyRepository.updateNotifyStatus(notifyNewStatus, notifyId);
    }
}
