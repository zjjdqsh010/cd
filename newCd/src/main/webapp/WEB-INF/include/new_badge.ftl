<#macro new_badge cod label>
<#if cod??>
<!-- cod = cut off date 截止日期  传入形式是：191107 -->
<#assign now=((.now)?string("yyMMdd"))?number>
<#if now lt cod>
<span class="badge badge-danger badge-sm badge-outline badge-sm">${label!'新功能'}</span>
</#if>
</#if>
</#macro>