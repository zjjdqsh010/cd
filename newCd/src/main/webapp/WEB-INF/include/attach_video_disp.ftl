<!--附件显示宏 -->
<!-- attachField 附件对应的id名称 -->
<!-- attachDivId 附件上一层最大的divId -->
<!-- attachNum  附件的最大个数 如果值为1，则默认是单张上传  -->
<!-- fileType  a:无限制 w:word i:image e:excel 默认无限制：a -->
<!-- position  位置  m:中部  b:底部  默认中部 -->
<!-- readonly  true:只读 false:可删除 -->
<#macro attach_video_disp attaches attachField attachDivId attachNum fileType position readonly>
<#if !readonly>
	<!-- 上传编辑  -->
	<#if ((attachNum!1)==1 && attaches?? && (attaches?size)=0) || (attachNum!1) gt 1 >
	<!-- 条件：可编辑，如果是单张上传且没有附件 获取 是多张上传  -->
	<div style="padding: 0 0 0.5rem 1.0715rem; width: 150px;height:150px;" id="${attachDivId}_add">
		<figure class="overlay">
			<img class="overlay-figure img-fluid" src="${ctx}/assets/images/upload.png" alt="上传文件">
			<figcaption class="overlay-panel overlay-background overlay-fade overlay-icon">
				<a class="icon wb-upload" style="margin: 0 5px; font-size: 1.286rem; width: 1.286rem"
					onclick="uploadFile('${attachField}','${attachDivId}','${attachNum!1}','${fileType!}','${position!}')"></a>
			</figcaption>
		</figure>
	</div>
	</#if>
</#if>
<#if attaches?? >
    <#list attaches as attach>
    	<#if attach_index==0>
    	<div style="padding:0 0 0.9rem 0;width:150px;height:200px" id="attachDiv_${attach.id}">
    	<#else>
    	<div style="padding:0 0 0.9rem 1.0715rem;width:150px;height:200px" id="attachDiv_${attach.id}">
    	</#if>
			<figure class="overlay overlay-hover" style="height:100%;width:100%">
				<video id="video" style="width: 100%;height: 100%;"  >
					<source src="<@qcloud_url attachId="${attach.id!}"/>" type="video/mp4">
				</video>
				<figcaption class="overlay-panel overlay-background overlay-fade overlay-icon">
					<a class="icon wb-download" style="margin:0 5px;font-size:1.286rem;width:1.286rem"  onclick="downloadQcloudFile('${attach.id!}')" href="javascript:void(0)" target="_self" ></a>
					<a class="icon wb-search" style="margin:0 5px;font-size:1.286rem;width:1.286rem;"  href="javascript:void(0)" onclick="videoPlay('<@qcloud_url attachId="${attach.id!}"/>')" ></a>
					<#if !readonly>
						<a class="icon wb-close" style="margin:0 5px;font-size:1.286rem;width:1.286rem" onclick="_removeAttach('${attach.id!}','${attachField}','${attachDivId}','${attachNum!1}','${fileType!}','${position!}')"></a>
					</#if>
				</figcaption>
			</figure>
		</div>
    </#list>
</#if>
</#macro>