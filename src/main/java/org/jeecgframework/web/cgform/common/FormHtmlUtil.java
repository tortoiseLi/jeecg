package org.jeecgframework.web.cgform.common;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.entity.config.CgFormFieldEntity;
import org.jeecgframework.web.cgform.service.impl.config.util.ExtendJsonConvert;
/**   
 * @author 张代浩
 * @date 2013-08-11 09:47:30
 * @version V1.0   
 */
public class FormHtmlUtil {
	
	
	/**
     *根据CgFormFieldEntity表属性配置，返回表单HTML代码
     */
    public static String getFormHTML(CgFormFieldEntity cgFormFieldEntity,String tableName){
    	String html="";
        if(cgFormFieldEntity.getShowType().equals("text")){

        	 if("only".equalsIgnoreCase(cgFormFieldEntity.getFieldValidType())){
        		 html=getTextOnlyFormHtml(cgFormFieldEntity,tableName);
        	 }else{
        		 html=getTextFormHtml(cgFormFieldEntity);
        	 }

        }else if(cgFormFieldEntity.getShowType().equals("password")){
        	html=getPwdFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("radio")){
        	html=getRadioFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("checkbox")){
        	html=getCheckboxFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("list")){
        	html=getListFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("date")){
        	html=getDateFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("datetime")){
        	html=getDatetimeFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("file")){

        	html=getFilePluploadFormHtml(cgFormFieldEntity);

        }else if(cgFormFieldEntity.getShowType().equals("textarea")){
        	html=getTextAreaFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("popup")){
        	html=getPopupFormHtml(cgFormFieldEntity);
        }
        else {
        	html=getTextFormHtml(cgFormFieldEntity);
        }
        return html;
    }
    /**
     * 返回textarea的表单html
     * @param cgFormFieldEntity
     * @return style="width: 300px" class="inputxt" rows="6"
     */
    private static String getTextAreaFormHtml(
			CgFormFieldEntity cgFormFieldEntity) {
    	StringBuilder html = new StringBuilder("");

    	 html.append("<textarea rows=\"6\" ");
    	 if(StringUtils.isNotEmpty(cgFormFieldEntity.getExtendJson())){
        	 html.append(" "+ExtendJsonConvert.json2Html(cgFormFieldEntity.getExtendJson())+" ");;
         }
    	 if(cgFormFieldEntity.getFieldLength()!=null&&cgFormFieldEntity.getFieldLength()>0){
       	  	html.append("style=\"width:").append(cgFormFieldEntity.getFieldLength()).append("px\" ");
         }
    	 html.append("id=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
         html.append("name=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
         if("Y".equals(cgFormFieldEntity.getFieldMustInput())){
	   	  	  //校验必填
	   	  	  html.append("ignore=\"checked\" ");
	   	  }else{
	   	        if("Y".equals(cgFormFieldEntity.getIsNull())){
	   	        	html.append("ignore=\"ignore\" ");
	   	        }else{
	   	        	html.append("ignore=\"checked\" ");
	   	        }
	   	  }
         if(cgFormFieldEntity.getFieldValidType()!=null&&cgFormFieldEntity.getFieldValidType().length()>0){
       	  html.append("datatype=\"").append(cgFormFieldEntity.getFieldValidType()).append("\" ");
         }else{
   		  html.append("datatype=\"*\" ");
   	  }
         html.append("\\>");
//         html.append("\\${").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}</textarea> ");
         html.append("\\${data\\['"+cgFormFieldEntity.getTable().getTableName()+"'\\]\\['").append(cgFormFieldEntity.getFieldName()).append("'\\]?if_exists?html}</textarea> ");

         return html.toString();
	}

	/**
     *返回text类型的表单html
     */
    private static String getTextFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
      StringBuilder html = new StringBuilder("");
      html.append("<input type=\"text\" ");
      html.append("id=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
      html.append("name=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");

      if(StringUtils.isNotEmpty(cgFormFieldEntity.getExtendJson())){
     	 html.append(" "+ExtendJsonConvert.json2Html(cgFormFieldEntity.getExtendJson())+" ");;
     }
      if(cgFormFieldEntity.getFieldLength()!=null&&cgFormFieldEntity.getFieldLength()>0){
    	  html.append("style=\"width:").append(cgFormFieldEntity.getFieldLength()).append("px\" ");
      }
//      html.append("value=\"\\${").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}\" ");
      html.append("value=\"\\${data\\['"+cgFormFieldEntity.getTable().getTableName()+"'\\]\\['").append(cgFormFieldEntity.getFieldName()).append("'\\]?if_exists?html}\" ");
      if("Y".equals(cgFormFieldEntity.getFieldMustInput())){
	  	  //校验必填
	  	  html.append("ignore=\"checked\" ");
	  }else{
	        if("Y".equals(cgFormFieldEntity.getIsNull())){
	        	html.append("ignore=\"ignore\" ");
	        }else{
	        	html.append("ignore=\"checked\" ");
	        }
	  }

      if(cgFormFieldEntity.getFieldValidType()!=null&&cgFormFieldEntity.getFieldValidType().length()>0){
    	  html.append("datatype=\"").append(cgFormFieldEntity.getFieldValidType()).append("\" ");
      }else{
    	  if("int".equals(cgFormFieldEntity.getType())){
    		  html.append("datatype=\"n\" ");
    	  }else if("double".equals(cgFormFieldEntity.getType())){
    		  html.append("datatype=\"\\/^(-?\\\\d+)(\\\\.\\\\d+)?\\$\\/\" ");
    	  }else{
    		  html.append("datatype=\"*\" ");
    	  }
      }
      html.append("\\/>");
      return html.toString();
    }

    /**
     *返回text类型的表单html(唯一值校验)
     */
    private static String getTextOnlyFormHtml(CgFormFieldEntity cgFormFieldEntity,String tableName)
    {
    	StringBuilder html = new StringBuilder("");
        html.append("<input type=\"text\" ");
        html.append("id=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
        html.append("name=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");

        if(StringUtils.isNotEmpty(cgFormFieldEntity.getExtendJson())){
        	 html.append(" "+ExtendJsonConvert.json2Html(cgFormFieldEntity.getExtendJson())+" ");;
        }
        if(cgFormFieldEntity.getFieldLength()!=null&&cgFormFieldEntity.getFieldLength()>0){
      	  html.append("style=\"width:").append(cgFormFieldEntity.getFieldLength()).append("px\" ");
        }
//        html.append("value=\"\\${").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}\" ");
        html.append("value=\"\\${data\\['"+cgFormFieldEntity.getTable().getTableName()+"'\\]\\['").append(cgFormFieldEntity.getFieldName()).append("'\\]?if_exists?html}\" ");
        if("Y".equals(cgFormFieldEntity.getFieldMustInput())){
      	  //校验必填
      	  html.append("ignore=\"checked\" ");
        }else{
	        if("Y".equals(cgFormFieldEntity.getIsNull())){
	        	html.append("ignore=\"ignore\" ");
	        }else{
	        	html.append("ignore=\"checked\" ");
	        }
        }

        html.append("validtype=\"").append(tableName).append(",").append(cgFormFieldEntity.getFieldName()).append(",id\" ");
  	  	html.append("datatype=\"*\" ");
        html.append("\\/>");
        return html.toString();
    }

    /**
     *返回password类型的表单html
     */
    private static String getPwdFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
      StringBuilder html = new StringBuilder("");
      html.append("<input type=\"password\" ");
      html.append("id=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
      html.append("name=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");

      if(StringUtils.isNotEmpty(cgFormFieldEntity.getExtendJson())){
     	 html.append(" "+ExtendJsonConvert.json2Html(cgFormFieldEntity.getExtendJson())+" ");;
      }
      if(cgFormFieldEntity.getFieldLength()!=null&&cgFormFieldEntity.getFieldLength()>0){
    	  html.append("style=\"width:").append(cgFormFieldEntity.getFieldLength()).append("px\" ");
      }
//      html.append("value=\"\\${").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}\" ");
      html.append("value=\"\\${data\\['"+cgFormFieldEntity.getTable().getTableName()+"'\\]\\['").append(cgFormFieldEntity.getFieldName()).append("'\\]?if_exists?html}\" ");
      if("Y".equals(cgFormFieldEntity.getFieldMustInput())){
	  	  //校验必填
	  	  html.append("ignore=\"checked\" ");
	  }else{
	        if("Y".equals(cgFormFieldEntity.getIsNull())){
	        	html.append("ignore=\"ignore\" ");
	        }else{
	        	html.append("ignore=\"checked\" ");
	        }
	  }

      if(cgFormFieldEntity.getFieldValidType()!=null&&cgFormFieldEntity.getFieldValidType().length()>0){
    	  html.append("datatype=\"").append(cgFormFieldEntity.getFieldValidType()).append("\" ");
      }else{
		  html.append("datatype=\"*\" ");
	  }
      html.append("\\/>");
      return html.toString();
    }
    
    
    /**
     *返回radio类型的表单html  
     */
    private static String getRadioFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
    	
    	if(StringUtil.isEmpty(cgFormFieldEntity.getDictField())){
      	  return getTextFormHtml(cgFormFieldEntity);
        }else{
  	      StringBuilder html = new StringBuilder("");
  	      html.append("<@DictData name=\""+cgFormFieldEntity.getDictField()+"\"");
  	      if(!StringUtil.isEmpty(cgFormFieldEntity.getDictTable())){
  	    	html.append(" tablename=\""+cgFormFieldEntity.getDictTable()+"\"");
  	      }
  	      html.append(" var=\"dictDataList\">");
  	      html.append("<#list dictDataList as dictdata>");
  	      html.append(" <input type=\"radio\" value=\"\\${dictdata.code?if_exists?html}\" name=\""+cgFormFieldEntity.getFieldName()+"\" ");

  	      html.append("<#if dictdata.code==\"\\${data\\['"+cgFormFieldEntity.getTable().getTableName()+"'\\]\\['").append(cgFormFieldEntity.getFieldName()).append("'\\]?if_exists?html}\">");

  	      html.append(" checked=\"true\" ");
  	      html.append("</#if> ");
  	      html.append(">");
  	      html.append("\\${dictdata.typename?if_exists?html}");
  	      html.append("</#list> ");
  	      html.append("</@DictData> ");
  	      return html.toString();
        }
    }
    
    
    /**
     *返回checkbox类型的表单html ${data['${po.field_name}']?if_exists?html}
     */
    private static String getCheckboxFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
    	  if(StringUtil.isEmpty(cgFormFieldEntity.getDictField())){
        	  return getTextFormHtml(cgFormFieldEntity);
          }else{
        	 
    	      StringBuilder html = new StringBuilder("");

//    	      html.append("<#assign checkboxstr>\\${").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}</#assign>");
    	      html.append("<#assign checkboxstr>\\${data\\['"+cgFormFieldEntity.getTable().getTableName()+"'\\]\\['").append(cgFormFieldEntity.getFieldName()).append("'\\]?if_exists?html}</#assign>");

    	      html.append("<#assign checkboxlist=checkboxstr?split(\",\")> ");
    	      html.append("<@DictData name=\""+cgFormFieldEntity.getDictField()+"\"");
      	      if(!StringUtil.isEmpty(cgFormFieldEntity.getDictTable())){
      	    	html.append(" tablename=\""+cgFormFieldEntity.getDictTable()+"\"");
      	      }
      	      html.append(" var=\"dictDataList\">");
    	      html.append("<#list dictDataList as dictdata>");
    	      html.append(" <input type=\"checkbox\" value=\"\\${dictdata.code?if_exists?html}\" name=\""+cgFormFieldEntity.getFieldName()+"\" ");
    	      html.append("<#list checkboxlist as x >");
    	      html.append("<#if dictdata.code=='\\${x?if_exists?html}'>");
    	      html.append(" checked=\"true\" ");
    	      html.append("</#if> ");
    	      html.append("</#list> ");
    	      html.append(">");
    	      html.append("\\${dictdata.typename?if_exists?html}");
    	      html.append("</#list> ");
    	      html.append("</@DictData> ");
    	      return html.toString();
          }
    }
    
    
    /**
     *返回list类型的表单html
     */
    private static String getListFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
      if(StringUtil.isEmpty(cgFormFieldEntity.getDictField())){
    	  return getTextFormHtml(cgFormFieldEntity);
      }else{
	      StringBuilder html = new StringBuilder("");
	      html.append("<@DictData name=\""+cgFormFieldEntity.getDictField()+"\"");
	      if(!StringUtil.isEmpty(cgFormFieldEntity.getDictText())){
  	    	html.append(" text=\""+cgFormFieldEntity.getDictText()+"\"");
  	      }
  	      if(!StringUtil.isEmpty(cgFormFieldEntity.getDictTable())){
  	    	html.append(" tablename=\""+cgFormFieldEntity.getDictTable()+"\"");
  	      }
  	      html.append(" var=\"dictDataList\">");
	      html.append("<select name=\""+cgFormFieldEntity.getFieldName()+"\" id=\""+cgFormFieldEntity.getFieldName()+"\"> ");
	      html.append("<#list dictDataList as dictdata>");
	      html.append(" <option value=\"\\${dictdata.code?if_exists?html}\" ");

	      html.append("<#if dictdata.code==\"\\${data\\['"+cgFormFieldEntity.getTable().getTableName()+"'\\]\\['").append(cgFormFieldEntity.getFieldName()).append("'\\]?if_exists?html}\">");

	      html.append("</#if> ");
	      html.append(">");
	      html.append("\\${dictdata.typename?if_exists?html}");
	      html.append("</option> ");
	      html.append("</#list> ");
	      html.append("</select>");
	      html.append("</@DictData> ");
	      return html.toString();
      }
    }
    
    
    /**
     *返回date类型的表单html
     */
    private static String getDateFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
      StringBuilder html = new StringBuilder("");
      html.append("<input type=\"text\" ");
      html.append("id=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
      html.append("name=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");

      if(StringUtils.isNotEmpty(cgFormFieldEntity.getExtendJson())){
    	  html.append(" "+ExtendJsonConvert.json2Html(cgFormFieldEntity.getExtendJson())+" ");;
      }
      html.append("class=\"Wdate\" ");
      html.append("onClick=\"WdatePicker()\" ");
      if(cgFormFieldEntity.getFieldLength()!=null&&cgFormFieldEntity.getFieldLength()>0){
    	  html.append("style=\"width:").append(cgFormFieldEntity.getFieldLength()).append("px\" ");
      }
//      html.append("value=\"\\${").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}\" ");
      html.append("value=\"\\${data\\['"+cgFormFieldEntity.getTable().getTableName()+"'\\]\\['").append(cgFormFieldEntity.getFieldName()).append("'\\]?if_exists?html}\" ");
      if("Y".equals(cgFormFieldEntity.getFieldMustInput())){
	  	  //校验必填
	  	  html.append("ignore=\"checked\" ");
	  }else{
	        if("Y".equals(cgFormFieldEntity.getIsNull())){
	        	html.append("ignore=\"ignore\" ");
	        }else{
	        	html.append("ignore=\"checked\" ");
	        }
	  }

      if(cgFormFieldEntity.getFieldValidType()!=null&&cgFormFieldEntity.getFieldValidType().length()>0){
    	  html.append("datatype=\"").append(cgFormFieldEntity.getFieldValidType()).append("\" ");
      }else{
		  html.append("datatype=\"*\" ");
	  }
      html.append("\\/>");
      return html.toString();
    }
    
    /**
     *返回datetime类型的表单html
     */
    private static String getDatetimeFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
      StringBuilder html = new StringBuilder("");
      html.append("<input type=\"text\" ");
      html.append("id=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
      html.append("name=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");

      if(StringUtils.isNotEmpty(cgFormFieldEntity.getExtendJson())){
     	 html.append(" "+ExtendJsonConvert.json2Html(cgFormFieldEntity.getExtendJson())+" ");;
     }
      html.append("class=\"Wdate\" ");
      html.append("onClick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})\" ");
      if(cgFormFieldEntity.getFieldLength()!=null&&cgFormFieldEntity.getFieldLength()>0){
    	  html.append("style=\"width:").append(cgFormFieldEntity.getFieldLength()).append("px\" ");
      }
//      html.append("value=\"\\${").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}\" ");
      html.append("value=\"\\${data\\['"+cgFormFieldEntity.getTable().getTableName()+"'\\]\\['").append(cgFormFieldEntity.getFieldName()).append("'\\]?if_exists?html}\" ");
      if("Y".equals(cgFormFieldEntity.getFieldMustInput())){
	  	  //校验必填
	  	  html.append("ignore=\"checked\" ");
	  }else{
	        if("Y".equals(cgFormFieldEntity.getIsNull())){
	        	html.append("ignore=\"ignore\" ");
	        }else{
	        	html.append("ignore=\"checked\" ");
	        }
	  }

      if(cgFormFieldEntity.getFieldValidType()!=null&&cgFormFieldEntity.getFieldValidType().length()>0){
    	  html.append("datatype=\"").append(cgFormFieldEntity.getFieldValidType()).append("\" ");
      }else{
		  html.append("datatype=\"*\" ");
	  }
      html.append("\\/>");
      return html.toString();
    }
    
    /**
     *返回file类型的表单html
     */
    @Deprecated 
	private static String getFileFormHtml(CgFormFieldEntity cgFormFieldEntity){
    	StringBuilder html = new StringBuilder("");

//    	html.append("<link rel=\"stylesheet\" href=\"plug-in/uploadify/css/uploadify.css\" type=\"text/css\"></link>");
//    	html.append("<script type=\"text/javascript\" src=\"plug-in/uploadify/jquery.uploadify-3.1.js\"></script>");

    	html.append("<table>");
    	html.append("<#list filesList as fileB>");
    	html.append("<tr style=\"height:34px;\">");
    	html.append("<td>\\${fileB['title']}</td>");
    	html.append("<td><a href=\"commonController.do?viewFile&fileid=\\${fileB['fileKey']}&subclassname=org.jeecgframework.web.cgform.entity.upload.CgUploadEntity\" title=\"下载\">下载</a></td>");
    	html.append("<td><a href=\"javascript:void(0);\" onclick=\"openwindow('预览','commonController.do?openViewFile&fileid=\\${fileB['fileKey']}&subclassname=org.jeecgframework.web.cgform.entity.upload.CgUploadEntity','fList',700,500)\">预览</a></td>");
    	html.append("<td><a href=\"javascript:void(0)\" class=\"jeecgDetail\" onclick=\"del('cgUploadController.do?delFile&id=\\${fileB['fileKey']}',this)\">删除</a></td>");
    	html.append("</tr></#list></table>");
    	html.append("<div class=\"form jeecgDetail\">");
    	html.append("<script type=\"text/javascript\">");
    	html.append("var serverMsg=\"\";");
    	html.append("var m = new Map();");
    	html.append("\\$(function(){\\$('#").append(cgFormFieldEntity.getFieldName()).append("').uploadify(");
    	html.append("{buttonText:'添加文件',auto:false,progressData:'speed',multi:true,height:25,overrideEvents:['onDialogClose'],fileTypeDesc:'文件格式:',");
    	html.append("queueID:'filediv_").append(cgFormFieldEntity.getFieldName()).append("',");

    	//html.append("fileTypeExts:'*.rar;*.zip;*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;*.jpg;*.gif;*.png',");

    	html.append("fileSizeLimit:'15MB',swf:'\\${basePath}/plug-in/uploadify/uploadify.swf',");
    	html.append("uploader:'\\${basePath}/cgUploadController.do?saveFiles&jsessionid='+\\$(\"#sessionUID\").val()+'',");
    	html.append("onUploadStart : function(file) { ");
    	html.append("var cgFormId=\\$(\"input[name='id']\").val();");
    	html.append("\\$('#").append(cgFormFieldEntity.getFieldName()).append("').uploadify(\"settings\", \"formData\", {'cgFormId':cgFormId,'cgFormName':'").append(cgFormFieldEntity.getTable().getTableName()).append("','cgFormField':'").append(cgFormFieldEntity.getFieldName()).append("'});} ,");
    	html.append("onQueueComplete : function(queueData) {var win = frameElement.api.opener;win.reloadTable();win.tip(serverMsg);frameElement.api.close();},");
    	html.append("onUploadSuccess : function(file, data, response) {var d=\\$.parseJSON(data);if(d.success){var win = frameElement.api.opener;serverMsg = d.msg;}},onFallback : function(){tip(\"您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试\")},onSelectError : function(file, errorCode, errorMsg){switch(errorCode) {case -100:tip(\"上传的文件数量已经超出系统限制的\"+\\$('#").append(cgFormFieldEntity.getFieldName()).append("').uploadify('settings','queueSizeLimit')+\"个文件！\");break;case -110:tip(\"文件 [\"+file.name+\"] 大小超出系统限制的\"+\\$('#").append(cgFormFieldEntity.getFieldName()).append("').uploadify('settings','fileSizeLimit')+\"大小！\");break;case -120:tip(\"文件 [\"+file.name+\"] 大小异常！\");break;case -130:tip(\"文件 [\"+file.name+\"] 类型不正确！\");break;}},");
    	html.append("onUploadProgress : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) { }});});");
    	html.append("</script><span id=\"file_uploadspan\"><input type=\"file\" name=\"").append(cgFormFieldEntity.getFieldName()).append("\" id=\"").append(cgFormFieldEntity.getFieldName()).append("\" /></span>");
    	html.append("</div><div class=\"form\" id=\"filediv_").append(cgFormFieldEntity.getFieldName()).append("\"> </div>");
    	
    	html.append("<script type=\"text/javascript\">");
//    	html.append("function uploadFile(data){");
//    	html.append("if(!\\$(\"input[name='id']\").val()){");
//    	html.append("if(data.obj != null && data.obj != 'undefined'){\\$(\"input[name='id']\").val(data.obj.id);}}");
//    	html.append("if(\\$(\".uploadify-queue-item\").length > 0){upload();}else{if (neibuClickFlag){alert(data.msg); neibuClickFlag = false;}else {var win = frameElement.api.opener; win.reloadTable(); win.tip(data.msg); frameElement.api.close();}}}");
    	html.append("function upload() {\\$('#").append(cgFormFieldEntity.getFieldName()).append("').uploadify('upload', '\\*');}");
    	html.append("function cancel() {\\$('#").append(cgFormFieldEntity.getFieldName()).append("').uploadify('cancel', '\\*');}");
//    	html.append("var neibuClickFlag = false;function neibuClick() {neibuClickFlag = true;\\$('#btn_sub').trigger('click');}");
    	html.append("</script>");

      	return html.toString();
    }
    
    
    /**
     *返回file类型的表单html
     */
	private static String getFilePluploadFormHtml(CgFormFieldEntity cgFormFieldEntity){
    	StringBuilder html = new StringBuilder("");
    	html.append("<div class='uploadify'>");
    	html.append("<table>");
    	html.append("<#list filesList as fileB>");
    	html.append("<tr style=\"height:34px;\">");
    	html.append("<td>\\${fileB['title']}</td>");
    	html.append("<td><a href=\"commonController.do?viewFile&fileid=\\${fileB['fileKey']}&subclassname=org.jeecgframework.web.cgform.entity.upload.CgUploadEntity\" title=\"下载\">下载</a></td>");
    	html.append("<td><a href=\"javascript:void(0);\" onclick=\"openwindow('预览','commonController.do?openViewFile&fileid=\\${fileB['fileKey']}&subclassname=org.jeecgframework.web.cgform.entity.upload.CgUploadEntity','fList',700,500)\">预览</a></td>");
    	html.append("<td><a href=\"javascript:void(0)\" class=\"jeecgDetail\" onclick=\"del('cgUploadController.do?delFile&id=\\${fileB['fileKey']}',this)\">删除</a></td>");
    	html.append("</tr></#list></table>");
    	html.append("<div class='plupload-btns'>");
    	html.append("<div id='"+cgFormFieldEntity.getFieldName()+"Upselector' class='uploadify-button' style='cursor:pointer;height:18px; line-height:18px; width:80px; position: relative; z-index: 1;'>");
    	html.append("<span class='uploadify-button-text'>添加文件</span></div><input type='button' id = '"+cgFormFieldEntity.getFieldName()+"' style='display:none'/>");
    	html.append("<div class='form' id='filediv_"+cgFormFieldEntity.getFieldName()+"'> </div></div>");
    	html.append("<script type=\"text/javascript\">");
    	html.append("\\$(function(){");
    	html.append("var serverMsg=\"\";");
    	html.append("var m = new Map();");
    	html.append("if(location.href.indexOf('load=detail')!=-1){\\$('.plupload-btns').hide();}");
    	html.append("var addtrFile = function(file) {var fileName = file.name;if (fileName.length > 20) {fileName = fileName.substring(0, 15) + '...';}var fileSize = Math.ceil(file.size/1024);");
    	html.append("var html = '<div id=\"'+file.id+'\" class=\"uploadify-queue-item\"><div class=\"cancel\"><a class=\"iqueueDel\" href=\"javascript:void(0)\">X</a></div><span class=\"fileName\" title=\"'+file.name+'\">'+fileName+'('+fileSize+'KB)</span><span class=\"sdata\"></span><div class=\"uploadify-progress\"><div class=\"uploadify-progress-bar\"></div></div></div>';");
    	html.append("\\$('#filediv_"+cgFormFieldEntity.getFieldName()+"').append(html);}\r\n");
    	html.append("var uploader = new plupload.Uploader({runtimes: 'html5,flash',");
    	html.append("browse_button: '"+cgFormFieldEntity.getFieldName()+"Upselector',");
    	html.append("url: '\\${basePath}/cgUploadController.do?saveFiles&jsessionid='+\\$(\"#sessionUID\").val()+'',");
    	html.append("flash_swf_url: '\\${basePath}/plug-in/plupload/Moxie.swf',");
    	html.append("filters: {max_file_size: \"15mb\", mime_types: [{title: \"Common files\", extensions:\"txt,doc,docx,xls,xlsx,ppt,pdf,jpg,jpeg,png,gif\"}],prevent_duplicates:false},");
    	html.append("multipart_params:{'cgFormName':'"+cgFormFieldEntity.getTable().getTableName()+"',");
    	html.append("'cgFormField':'"+cgFormFieldEntity.getFieldName()+"'},multi_selection: true,");
    	html.append("init: {PostInit: function() {\\$.iplupload('"+cgFormFieldEntity.getFieldName()+"',uploader);},");
    	html.append("FilesAdded: function(up, files) {for(var a = 0;a<files.length;a++){addtrFile(files[a]);}},");
    	html.append("UploadProgress: function(up, file) {var percent = file.percent;\\$('#' + file.id).find('.uploadify-progress-bar').css({'width': percent + '%'});},");
    	html.append("BeforeUpload: function(up, file) {var params = up.getOption('multipart_params');var cgFormId=\\$(\"input[name='id']\").val();params['cgFormId'] = cgFormId;up.setOption('multipart_params',params);},");
    	html.append("FileUploaded: function(up, file, info) {var response = jQuery.parseJSON(info.response);if (response.success) {serverMsg = response.msg;\\$(\"#\"+file.id).find(\".sdata\").text(' - Complete');setTimeout(function(){\\$(\"#\"+file.id).fadeOut(\"slow\");},500);}},");
    	html.append("UploadComplete: function(up, files) {if(files.length>0){var win = frameElement.api.opener;win.reloadTable();win.tip(serverMsg);frameElement.api.close();}},");
    	html.append("Error: function(up, err) {if(err.code == plupload.FILE_EXTENSION_ERROR){tip(\"文件类型不识别！\");}else if(plupload.FILE_SIZE_ERROR = err.code){tip(\"文件大小超标！\");}console.log(err);}}");
    	html.append("});\r\nuploader.init();");
    	html.append("\\$('#filediv_"+cgFormFieldEntity.getFieldName()+"').on('click','.iqueueDel',function(eve){");
    	html.append("var itemObj = \\$(eve.target).closest('.uploadify-queue-item');uploader.removeFile(uploader.getFile(itemObj.attr('id')));itemObj.find('.sdata').text(' - 已取消');setTimeout(function(){itemObj.fadeOut('slow');},500);});});");
    	html.append("function upload() {\\$('#").append(cgFormFieldEntity.getFieldName()).append("').uploadify('upload', '\\*');}");
    	html.append("function cancel() {\\$('#").append(cgFormFieldEntity.getFieldName()).append("').uploadify('cancel', '\\*');}");
    	html.append("</script>\r\n");
      	return html.toString();
    }
	
    /**
     *返回popup类型的表单html
     */
    private static String getPopupFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
      StringBuilder html = new StringBuilder("");
      html.append("<input type=\"text\" readonly=\"readonly\" class=\"searchbox-inputtext\" ");
      html.append("id=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
      html.append("name=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");

      if(StringUtils.isNotEmpty(cgFormFieldEntity.getExtendJson())){
     	 html.append(" "+ExtendJsonConvert.json2Html(cgFormFieldEntity.getExtendJson())+" ");;
      }
      if(cgFormFieldEntity.getFieldLength()!=null&&cgFormFieldEntity.getFieldLength()>0){
    	  html.append("style=\"width:").append(cgFormFieldEntity.getFieldLength()).append("px\" ");
      }
//      html.append("value=\"\\${").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}\" ");
      html.append("value=\"\\${data\\['"+cgFormFieldEntity.getTable().getTableName()+"'\\]\\['").append(cgFormFieldEntity.getFieldName()).append("'\\]?if_exists?html}\" ");

      html.append("onclick=\"popupClick(this,'"+cgFormFieldEntity.getDictText()+"','"+cgFormFieldEntity.getDictField()+"','"+cgFormFieldEntity.getDictTable()+"');\" ");

      if("Y".equals(cgFormFieldEntity.getFieldMustInput())){
	  	  //校验必填
	  	  html.append("ignore=\"checked\" ");
	  }else{
	        if("Y".equals(cgFormFieldEntity.getIsNull())){
	        	html.append("ignore=\"ignore\" ");
	        }else{
	        	html.append("ignore=\"checked\" ");
	        }
	  }

      if(cgFormFieldEntity.getFieldValidType()!=null&&cgFormFieldEntity.getFieldValidType().length()>0){
    	  html.append("datatype=\"").append(cgFormFieldEntity.getFieldValidType()).append("\" ");
      }else{
		  html.append("datatype=\"*\" ");
	  }
      html.append("\\/>");
      return html.toString();
    }
}
