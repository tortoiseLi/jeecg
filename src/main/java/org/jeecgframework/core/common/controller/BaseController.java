package org.jeecgframework.core.common.controller;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.interceptors.DateConvertEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 基础控制器
 * @author Administrator
 * @date 2019-05-08
 * @version V1.0
 */
@Controller
@RequestMapping("/baseController")
public class BaseController {

	/**
	 * 将前台传递过来的日期格式的字符串自动转化为Date类型
	 * @param binder
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
	}

	/**
	 * 分页公共方法
	 * @param request
	 * @param dc
	 * @param commonService
	 * @param pageRow
	 * @return
	 */
	public List<?> pageBaseMethod(HttpServletRequest request, DetachedCriteria dc, CommonService commonService, int pageRow) {
		// 当前页
		int currentPage;

		// 总条数
		int totalRow;

		// 总页数
		int totalPage;

		// 获取当前页
		String str_currentPage = request.getParameter("str_currentPage");
		currentPage = str_currentPage == null || "".equals(str_currentPage) ? 1 : Integer.parseInt(str_currentPage);
		// 获取每页的条数
		String str_pageRow = request.getParameter("str_pageRow");
		pageRow = str_pageRow == null || "".equals(str_pageRow) ? pageRow : Integer.parseInt(str_pageRow);

		// 统计的总行数
		dc.setProjection(Projections.rowCount());

		totalRow = Integer.parseInt(commonService.findListByDetached(dc).get(0).toString());
		totalPage = (totalRow + pageRow - 1) / pageRow;

		currentPage = currentPage < 1 ? 1 : currentPage;
		currentPage = currentPage > totalPage ? totalPage : currentPage;

		// 清空统计函数
		dc.setProjection(null);
		List<?> list = commonService.findListByDetachedCriteria(dc, (currentPage - 1) * pageRow, pageRow);

		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageRow", pageRow);
		request.setAttribute("totalRow", totalRow);
		request.setAttribute("totalPage", totalPage);
		return list;
	}

	/**
	 * 抽取由逗号分隔的主键列表
	 * @param ids
	 * @return
	 */
	protected List<String> extractIdListByComma(String ids) {
        List<String> result = new ArrayList<>();
        if (StringUtils.hasText(ids)) {
            for (String id : ids.split(",")) {
                if (StringUtils.hasLength(id)) {
                    result.add(id.trim());
                }
            }
        }

        return result;
    }
	
}
