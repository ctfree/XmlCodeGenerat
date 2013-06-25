/*
 * ${className}.cpp
 *
 *  Created on: 2011-12-10
 *      Author: ct
 */
#include <limits.h>
#include <gtest/gtest.h>
#include "${parentDir}/${className}.h"

TEST(${className}, use) {
	string xmldata;
	${className}  v1 v2;
	Xml::createXml(xmldata,v1);
    printf("xmldata %s",xmldata.c_str());

	Xml::paseXml(xmldata.c_str(),v2);
	printf("%s",v2.toString());
}

