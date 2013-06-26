/*
 * ${className}.h
 *
 *  Created on: 2012-2-11
 *      Author: ct
 */

#ifndef ${CLASSNAMEUPPER}_H_
#define ${CLASSNAMEUPPER}_H_
#include <string>
#include <list>
#include "Xml.h"
<#list innerClasses as innerClass>
#include "model/${innerClass.className}.h"
</#list>
using std::string;
using namespace std;

<#macro  xmlFormat type name xmlName first last> 
<#if type?index_of("list<") != -1 >
<#if !first>                 </#if>&TAGGED_CONTAINER(${name},"${xmlName}","${type?replace("list<","")?replace(">","")}")<#if last>;</#if>
<#else>
<#if !first>                 </#if>&TAGGED_OBJECT_CLASS(${name},"${xmlName}")<#if last>;</#if>
</#if>
</#macro>

class ${className} : public Xml{
public:
	${className}();
	virtual ~${className}();
	
    <#list elements as element>
    ${element.type} get${element.name?cap_first}() const;
    void set${element.name?cap_first}(${element.type} ${element.name});
    </#list>

    template<typename Archive> void Serialize(Archive& anArchive)
	{ 	
	    anArchive<#rt>
		<#list elements as element>
		<#assign beFirst=false beLast =false >
		<#if element_index==elements?size-1 >
		   <#assign beLast=true >
		</#if>
	    <#if element_index==0>
	       <#assign beFirst=true >
	    </#if>
	     <@xmlFormat type=element.type name=element.name xmlName=element.xmlName first=beFirst last=beLast/>
		</#list>
	}
	
	virtual const char * entryName()
	{
	   return "${xmlRoot}";
	}
	
	string toString()
	{
	    stringstream temp;
		<#list elements as element>
	    <#if element.type?index_of("list<") != -1 >
	    temp<<" ${element.name}:"<<containerToString(${element.name});
		<#elseif element.type?cap_first == element.type>
		temp<<" ${element.name}:"<<${element.name}.toString();
		<#else>
	    temp<<" ${element.name}:"<<${element.name};
		</#if>
		</#list>
        return temp.str();
	}

private:
<#list elements as element>
     ${element.type} ${element.name};
</#list>

};
#endif /* ${CLASSNAMEUPPER}_H_ */
