/*
 * ${className}.cpp
 *
 *  Created on: 2011-12-10
 *      Author: ct
 */
#include <limits.h>
#include <gtest/gtest.h>
#include "${parentDir}/${className}.h"

<#macro  initValue classInfo name> 
<#list classInfo.elements as element>
<#if element.type?index_of("list<") != -1 >
	${element.type} ${element.name};
	<#list classInfo.innerClasses as innerClass>
	  <#if element.type?index_of(innerClass.className) == 5 >
	  <#assign innerClassInfo=innerClass>
	  <#break>
	  </#if>
	</#list>
	${innerClassInfo.className} entry;
	<@initValue classInfo=innerClassInfo name="entry" />
	${element.name}.add(entry);
	${name}.set${element.name}(${element.name});
<#elseif element.type?cap_first == element.type>

	${element.type} ${element.name};
	<#list classInfo.innerClasses as innerClass>
	  <#if element.type?index_of(innerClass.className) == 0 >
	  <#assign innerClassInfo=innerClass>
	  <#break>
	  </#if>
	</#list>
    <@initValue classInfo=innerClassInfo name=element.name />
	${name}.set${element.name}(${element.name});
<#else>
	${name}.set${element.name}(ComMap[${element.name}]);
</#if>
</#list>
</#macro>

TEST(${className}, use) {
	string xmldata;
	${className}  v1,v2;
    <@initValue classInfo=this name="v1" />
	
	Xml::createXml(xmldata,v1);
    printf("\n%s",xmldata.c_str());

	Xml::paseXml(xmldata.c_str(),v2);
	printf("%s",v2.toString().c_str());
}

