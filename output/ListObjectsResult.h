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
class SystemMetadata;
class ObjectUnit;
class CommonPrefixes;
public:
	ListObjectsResult();
	virtual ~ListObjectsResult();

    template<typename Archive> void Serialize(Archive& anArchive)
	{ 	
	    anArchive&TAGGED_OBJECT_CLASS(name,"ContainerName")
                 &TAGGED_OBJECT_CLASS(prefix,"Prefix")
                 &TAGGED_OBJECT_CLASS(marker,"Marker")
                 &TAGGED_OBJECT_CLASS(truncated,"IsTruncated")
                 &TAGGED_OBJECT_CLASS(maxKeys,"MaxResults")
                 &TAGGED_CONTAINER(contents,"ObjectUnit","ObjectUnit")
                 &TAGGED_CONTAINER(commonPrefixes,"CommonPrefixes","CommonPrefixes");
	}
	
	virtual const char * entryName()
	{
	   return "ListObjectsResult";
	}

	string toString()
	{
		return "name "+name+" prefix "+prefix+" marker "+marker+" truncated "+truncated+" maxKeys "+maxKeys+" contents "+contents+" commonPrefixes "+commonPrefixes;
	}

private:
     string name;
     string prefix;
     string marker;
     bool truncated;
     int maxKeys;
     list<ObjectUnit> contents;
     list<CommonPrefixes> commonPrefixes;

private:
	class SystemMetadata {
	public:
		template<typename Archive> void Serialize(Archive& anArchive) {
		    anArchive&TAGGED_OBJECT_CLASS(ctime,"Ctime")
                 &TAGGED_OBJECT_CLASS(size,"Size");
		}

        const virtual char *entryName()
        {
            return "SystemMetadata";
        }

		string toString()
		{
			return "ctime "+ctime+" size "+size;
		}
    private:
	    string ctime;
	    long size;
    };
private:
	class ObjectUnit {
	public:
		template<typename Archive> void Serialize(Archive& anArchive) {
		    anArchive&TAGGED_OBJECT_CLASS(objectURI,"ObjectURI")
                 &TAGGED_OBJECT_CLASS(owner,"Owner")
                 &TAGGED_OBJECT_CLASS(storageClass,"StorageClass")
                 &TAGGED_OBJECT_CLASS(sysMeta,"SystemMetadata");
		}

        const virtual char *entryName()
        {
            return "ObjectUnit";
        }

		string toString()
		{
			return "objectURI "+objectURI+" owner "+owner+" storageClass "+storageClass+" sysMeta "+sysMeta;
		}
    private:
	    string objectURI;
	    string owner;
	    string storageClass;
	    SystemMetadata sysMeta;
    };
private:
	class CommonPrefixes {
	public:
		template<typename Archive> void Serialize(Archive& anArchive) {
		    anArchive&TAGGED_OBJECT_CLASS(prefix,"Prefix");
		}

        const virtual char *entryName()
        {
            return "CommonPrefixes";
        }

		string toString()
		{
			return "prefix "+prefix;
		}
    private:
	    string prefix;
    };
};
#endif /* LISTOBJECTSRESULT_H_ */
