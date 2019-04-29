package org.jeecgframework.web.system.pojo.base;

import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 图标表
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
@Entity
@Table(name = "t_s_icon")
public class IconEntity extends IdEntity implements java.io.Serializable {

	/**
	 * 图标名称
	 */
	private String iconName;

	/**
	 * 图标类型
	 */
	private Short iconType;

	/**
	 * 图标路径
	 */
	private String iconPath;

	/**
	 * 图标内容
	 */
	private byte[] iconContent;

	/**
	 * 图标样式
	 */
	private String iconClas;

	/**
	 * 扩展
	 */
	private String extend;

	@Column(name = "name", nullable = false, length = 100)
	public String getIconName() {
		return this.iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	@Column(name = "type")
	public Short getIconType() {
		return this.iconType;
	}

	public void setIconType(Short iconType) {
		this.iconType = iconType;
	}

	@Column(name = "path", length = 300,precision =300)
	public String getIconPath() {
		return this.iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	@Column(name = "iconclas", length = 200)
	public String getIconClas() {
		return iconClas;
	}

	public void setIconClas(String iconClas) {
		this.iconClas = iconClas;
	}

	public void setIconContent(byte[] iconContent) {
		this.iconContent = iconContent;
	}

	@Column(name = "content",length = 1000,precision =3000)
	public byte[] getIconContent() {
		return iconContent;
	}

	@Column(name = "extend")
	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

}