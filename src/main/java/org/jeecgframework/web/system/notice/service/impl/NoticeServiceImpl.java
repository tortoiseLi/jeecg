package org.jeecgframework.web.system.notice.service.impl;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.system.notice.entity.NoticeEntity;
import org.jeecgframework.web.system.notice.service.NoticeService;
import org.jeecgframework.web.system.pojo.base.NoticeAuthorityRoleEntity;
import org.jeecgframework.web.system.pojo.base.NoticeAuthorityUserEntity;
import org.jeecgframework.web.system.pojo.base.NoticeReadUserEntity;
import org.jeecgframework.web.system.user.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 通知公告Service
 * @author DELL
 * @date 2019-04-30
 * @version V1.0
 */
@Service("noticeService")
public class NoticeServiceImpl extends CommonServiceImpl implements NoticeService {

    @Override
    public String addNotice(String noticeTitle, String noticeContent, String noticeType, String noticeLevel, Date noticeTerm, String createUser) {
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setNoticeTitle(noticeTitle);
        noticeEntity.setNoticeContent(noticeContent);
        noticeEntity.setNoticeType(noticeType);
        noticeEntity.setNoticeLevel(noticeLevel);
        noticeEntity.setNoticeTerm(noticeTerm);
        noticeEntity.setCreateUser(createUser);
        noticeEntity.setCreateTime(new Date());
        this.add(noticeEntity);
        return noticeEntity.getId();
    }

    @Override
    public void addNoticeAuthorityUser(String noticeId, String userId) {
        if (StringUtils.isNotBlank(noticeId) && StringUtils.isNotBlank(userId)) {
            NoticeAuthorityUserEntity entity = new NoticeAuthorityUserEntity();
            entity.setNoticeId(noticeId);
            UserEntity userEntity = new UserEntity();
            userEntity.setId(userId);
            entity.setUser(userEntity);
            this.saveOrUpdate(entity);
        }
    }

    @Override
    public void deleteNotice(NoticeEntity noticeEntity) {
        super.deleteByCollection(super.findListByProperty(NoticeReadUserEntity.class, "noticeId", noticeEntity.getId()));
        super.deleteByCollection(super.findListByProperty(NoticeAuthorityUserEntity.class, "noticeId", noticeEntity.getId()));
        super.deleteByCollection(super.findListByProperty(NoticeAuthorityRoleEntity.class, "noticeId", noticeEntity.getId()));
        super.delete(noticeEntity);
    }

}
