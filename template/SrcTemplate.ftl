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

<#macro  xmlFormat type name xmlName last> 
<#if type?index_of("list<") != -1 >
  &TAGGED_CONTAINER(${name},"${xmlName}","${type?replace("list<","")?replace(">","")}")<#if last>;</#if>
<#else>
  &TAGGED_OBJECT_CLASS(${name},"${xmlName}")<#if last>;</#if>
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
		<#if element_index==elements?size-1 >
          <@xmlFormat type=element.type name=element.name xmlName=element.xmlName last=true/>
	    <#else>
          <@xmlFormat type=element.type name=element.name xmlName=element.xmlName last=false/>
	    </#if>
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
			<#if element.type?index_of("list<") != -1 >
			&TAGGED_CONTAINER(${element.name},"${element.xmlName}","${element.type?replace("list<","")?replace(">","")}")<#lt>
			<#else>
			&TAGGED_OBJECT_CLASS(${element.name},"${element.xmlName}")<#lt>
			</#if>
			</#list>;<#lt>
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
