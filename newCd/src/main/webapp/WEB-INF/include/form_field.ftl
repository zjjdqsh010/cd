<#--form select -->
<#-- <#include "../include/form_field.ftl"/>  -->
<#-- <@f_select_list name="entity.psclResult" value=(entity.psclResult)  _list=list loop_val="id" loop_disp="value" id="psclResult" onchange="psclResultChange(obj)" ></@f_select> -->
<#-- <@f_select_list name="entity.psclResult" value=(entity.psclResult)  _list=list ></@f_select> loop_val、loop_disp、id、onchange 可不传，默认 -->
<#macro f_select_list name value _list loop_val="ordinal" loop_disp="value" id="" onchange="">
<select <#if (id!'') != ''>id="${id}"</#if>name="${name}" class="form-control" <#if (onchange!'') != ''>onchange="${onchange}"</#if>>
	<option value=""> 请选择 </option>
	<#if _list??>
	<#list _list as x>
	<option value="${x[loop_val]!}" <#if value?? && value==x[loop_val]!>selected</#if>>${x[loop_disp]!}</option>
	</#list>
	</#if>
</select>
</#macro>

<#-- <@f_radio_list name="entity.psclResult" value=(entity.psclResult)  _list=list loop_val="id" loop_disp="value" id="psclResult" ></@f_radio_list> -->
<#-- <@f_radio_list name="entity.psclResult" value=(entity.psclResult)  _list=list  ></@f_radio_list> loop_val、loop_disp、id、style 可不传，默认 -->
<#macro f_radio_list name value _list loop_val="ordinal" loop_disp="value" id="" style="radio-inline">
<#if _list??>
<#list _list as x>
	<div class="radio-custom radio-default ${style}">
		<input type="radio" <#if value?? && value==x[loop_val]!>checked</#if> 
			id="${name}_${x_index!}" name="${name}" value="${x[loop_val]!}"> 
		<label for="${name}_${x_index!}">${x[loop_disp]!!}</label>
	</div>
</#list>
</#if>
</#macro>

<#-- <@f_radio_map name="entity.psclResult" value=(entity.psclResult) _map={"Y","合格","N":"不合格"}   style="radio-inline" id="" ></@f_radio_map> -->
<#-- <@f_radio_map name="entity.psclResult" value=(entity.psclResult) _map={"Y","合格","N":"不合格"}   ></@f_radio_map>  id 和  style（默认选中） 可以不传，默认-->
<#macro f_radio_map name value _map id="" style="radio-inline" >
<#if _map??>
<#assign _keys = _map?keys>
<#list _keys as x>
	<div class="radio-custom radio-default ${style}">
		<input type="radio" <#if value?? && value==x!>checked</#if> 
			id="${name}_${x_index!}" name="${name}" value="${x!}"> 
		<label for="${name}_${x_index!}">${_map[x]!!}</label>
	</div>
</#list>
</#if>
</#macro>

<#-- <@f_checkbox name="entity.psclResult" value=(entity.psclResult)  _list=list loop_val="id" loop_disp="value" style="checkbox-primary checkbox-inline" ></@f_checkbox> -->
<#-- <@f_checkbox name="entity.psclResult" value=(entity.psclResult)  _list=list ></@f_checkbox>  loop_val、loop_disp、id、style 可不传，默认-->
<#macro f_checkbox name value _list loop_val="ordinal" loop_disp="value" id="" style="checkbox-primary checkbox-inline">
<#if _list??>
<#list _list as x>
	<div class="checkbox-custom  ${style}">
		<input type="checkbox" 
			<#if value??>
				<#list value as v>
					<#if v==x[loop_val]!>checked</#if>
				</#list>
			</#if>
			id="${name}_${x_index!}" name="${name}" value="${x[loop_val]!}"> 
		<label for="${name}_${x_index}">${x[loop_disp]!!}</label>
	</div>
</#list>
</#if>
</#macro>