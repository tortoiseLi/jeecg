package org.jeecgframework.web.system.notice.service;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.notice.entity.NoticeEntity;

import java.util.Date;

/**
 * 通知公告Service接口
 * @author DELL
 * @version V1.0
 * @date 2019-04-30
 */
public interface NoticeService extends CommonService {

    /**
     * 新增通知公告
     * @param noticeTitle 标题
     * @param noticeContent 内容
     * @param noticeType 类型
     * @param noticeLevel 级别
     * @param noticeTerm 期限
     * @param createUser 创建者
     * @return
     */
    String addNotice(String noticeTitle, String noticeContent, String noticeType, String noticeLevel, Date noticeTerm, String createUser);

    /**
     * 新增通知公告授权
     * @param noticeId 通知公告ID
     * @param userId 用户ID
     */
    void addNoticeAuthorityUser(String noticeId, String userId);

    /**
     * 删除通知公告
     * @param noticeEntity
     * @param <T>
     */
    void deleteNotice(NoticeEntity noticeEntity);

}
