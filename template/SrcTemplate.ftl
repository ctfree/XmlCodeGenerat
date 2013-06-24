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
<#list innerClasses as innerClass>
class ${innerClass.name};
</#list>
public:
	${className}();
	virtual ~${className}();

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
		return <#rt>
		<#list elements as element>
		<#if element_index==0>
		"${element.name} "+${element.name}<#t>
		<#else>
		+" ${element.name} "+${element.name}<#t>
		</#if>
		</#list>;<#lt>
	}

private:
<#list elements as element>
     ${element.type} ${element.name};
</#list>

<#list innerClasses as innerClass>
private:
	class ${innerClass.name} {
	public:
	    <#assign elements=innerClass.elements>
		template<typename Archive> void Serialize(Archive& anArchive) {
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

        const virtual char *entryName()
        {
            return "${innerClass.name}";
        }

		string toString()
		{
			return <#rt>
			<#list elements as element>
			<#if element_index==0>
			"${element.name} "+${element.name}<#t>
			<#else>
			+" ${element.name} "+${element.name}<#t>
			</#if>
			</#list>;<#lt>
		}
    private:
	<#list innerClass.elements as element>
	    ${element.type} ${element.name};
	</#list>
    };
</#list>
};
#endif /* ${CLASSNAMEUPPER}_H_ */
