/*
 * ${className}.cpp
 *
 *  Created on: 2012-2-13
 *      Author: ct
 */

#include "${className}.h"

${className}::${className}() {
	// TODO Auto-generated constructor stub

}

${className}::~${className}() {
	// TODO Auto-generated destructor stub
}

<#list elements as element>
${element.type} ${className}::get${element.name?cap_first}() const
{
    return ${element.name};
}

void ${className}::set${element.name?cap_first}(${element.type} ${element.name})
{
    this->${element.name} = ${element.name};
}

</#list>




