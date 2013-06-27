/*
 * ${className}.cpp
 *
 *  Created on: 2011-12-10
 *      Author: ct
 */
#include <limits.h>
#include <gtest/gtest.h>
#include "${parentDir}/${className}.h"

<#macro  initValue classInfo name suffix> 
<#list classInfo.elements as element>
<#if element.type?index_of("list<") != -1 >
	<#list classInfo.innerClasses as innerClass>
	  <#if element.type?index_of(innerClass.className) == 5 >
		  <#assign innerClassInfo=innerClass>
		  <#break>
	  </#if>
	</#list>
	<#local innerClassInfoLocal=innerClassInfo>
	
	${element.type} ${element.name};
	<#assign var=innerClassInfoLocal.className?uncap_first+"1">
	${innerClassInfoLocal.className} ${var};
	<@initValue classInfo=innerClassInfoLocal name=var suffix="1"/>
	${element.name}.push_back(${var});
	
    <#assign var=innerClassInfoLocal.className?uncap_first+"2" >
	${innerClassInfoLocal.className} ${var};
	<@initValue classInfo=innerClassInfoLocal name=var suffix="2"/>
	${element.name}.push_back(${var});
	
	${name}.set${element.name?cap_first}(${element.name});
<#elseif element.type?cap_first == element.type>

	${element.type} ${element.name}${suffix};
	<#list classInfo.innerClasses as innerClass>
		  <#if element.type?index_of(innerClass.className) == 0 >
		  <#assign innerClassInfo=innerClass>
	  <#break>
	  </#if>
	</#list>
    <@initValue classInfo=innerClassInfo name=element.name+suffix suffix=""/>
	${name}.set${element.name?cap_first}(${element.name}${suffix});
<#else>
	${name}.set${element.name?cap_first}(${ComMap[element.type]});
</#if>
</#list>
</#macro>

TEST(${className}, use) {
	string xmldata;
	${className}  v1,v2;
    <@initValue classInfo=this name="v1" suffix="" />
	
	Xml::createXml(xmldata,v1);
    printf("\n%s\n",xmldata.c_str());

	Xml::paseXml(xmldata.c_str(),v2);
	printf("%s\n",v2.toString().c_str());
}

