/*
 * ListObjectsResult.h
 *
 *  Created on: 2012-2-11
 *      Author: ct
 */

#ifndef LISTOBJECTSRESULT_H_
#define LISTOBJECTSRESULT_H_
#include <string>
#include <list>
#include "Xml.h"
using std::string;
using namespace std;

class ListObjectsResult : public Xml{
class ObjectUnit;
class CommonPrefixes;
public:
	ListObjectsResult();
	virtual ~ListObjectsResult();

    template<typename Archive> void Serialize(Archive& anArchive)
	{ 	
	    anArchive<!-- FREEMARKER ERROR MESSAGE STARTS HERE --><script language=javascript>//"></script><script language=javascript>//'></script><script language=javascript>//"></script><script language=javascript>//'></script></title></xmp></script></noscript></style></object></head></pre></table></form></table></table></table></a></u></i></b><div align=left style='background-color:#FFFF00; color:#FF0000; display:block; border-top:double; padding:2pt; font-size:medium; font-family:Arial,sans-serif; font-style: normal; font-variant: normal; font-weight: normal; text-decoration: none; text-transform: none'><b style='font-size:medium'>FreeMarker template error!</b><pre><xmp>

Error on line 28, column 17 in SrcTemplate.txt
TAGGED_CONTAINER is undefined.
It cannot be assigned to test
The problematic instruction:
----------
==> assignment: test=TAGGED_CONTAINER [on line 28, column 17 in SrcTemplate.txt]
----------

Java backtrace for programmers:
----------
freemarker.core.InvalidReferenceException: Error on line 28, column 17 in SrcTemplate.txt
TAGGED_CONTAINER is undefined.
It cannot be assigned to test
	at freemarker.core.Assignment.accept(Assignment.java:111)
	at freemarker.core.Environment.visit(Environment.java:196)
	at freemarker.core.MixedContent.accept(MixedContent.java:92)
	at freemarker.core.Environment.visit(Environment.java:196)
	at freemarker.core.IteratorBlock$Context.runLoop(IteratorBlock.java:172)
	at freemarker.core.Environment.visit(Environment.java:351)
	at freemarker.core.IteratorBlock.accept(IteratorBlock.java:95)
	at freemarker.core.Environment.visit(Environment.java:196)
	at freemarker.core.MixedContent.accept(MixedContent.java:92)
	at freemarker.core.Environment.visit(Environment.java:196)
	at freemarker.core.Environment.process(Environment.java:176)
	at freemarker.template.Template.process(Template.java:232)
	at ct.XmlCodeGenerat.FreeMarkerUtil.createFile(FreeMarkerUtil.java:109)
	at ct.XmlCodeGenerat.Main.main(Main.java:25)
</xmp></pre></div></html>
