/*
 * DeleteObjectsResult.cpp
 *
 *  Created on: 2011-12-10
 *      Author: ct
 */
#include <limits.h>
#include <gtest/gtest.h>
#include "result/DeleteObjectsResult.h"

TEST(DeleteObjectsResult, use) {
	string xmldata;
	DeleteObjectsResult  v1,v2;
	Xml::createXml(xmldata,v1);
    printf("xmldata %s",xmldata.c_str());

	Xml::paseXml(xmldata.c_str(),v2);
	printf("%s",v2.toString().c_str());
}

