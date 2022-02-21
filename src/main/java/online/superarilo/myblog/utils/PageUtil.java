package online.superarilo.myblog.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 *
 * @author
 */
public class PageUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 总记录数
	 */
	private int totalCount;
	/**
	 * 每页记录数
	 */
	private int pageSize;
	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 当前页数
	 */
	private int currentPage;
	/**
	 * 列表数据
	 */
	private List<?> list;

	/**
	 * 总选择数
	 */
	private Integer allChoice;

	/**
	 * 分页
	 * @param list        列表数据
	 * @param totalCount  总记录数
	 * @param pageSize    每页记录数
	 * @param currentPage    当前页数
	 */
	public PageUtil(List<?> list, int totalCount, int pageSize, int currentPage) {
		this.list = list;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
	}

	/**
	 * 分页
	 */
	public PageUtil(IPage<?> page) {
		this.list = page.getRecords();
		this.totalCount = (int)page.getTotal();
		this.pageSize = (int)page.getSize();
		this.currentPage = (int)page.getCurrent();
		this.totalPage = (int)page.getPages();
	}

	/**
	 * 分页
	 */
	public PageUtil(PageInfo<?> pageInfo) {
		this.list = pageInfo.getList();
		this.totalCount = (int)pageInfo.getTotal();
		this.pageSize = (int)pageInfo.getPageSize();
		this.currentPage = (int)pageInfo.getPageNum();
		this.totalPage = (int)pageInfo.getPages();
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public Integer getAllChoice() {
		return allChoice;
	}

	public void setAllChoice(Integer allChoice) {
		this.allChoice = allChoice;
	}
}
