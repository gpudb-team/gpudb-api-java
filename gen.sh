#!/bin/bash
SCHEMADIR=../../gaiadb-schemas/obj_defs/

mkdir -p src/avro/schema
find "${SCHEMADIR}" -name "*.json" | while read -r file
do
	echo "${file}"
	#cp "${file}" ./temp/
	x="${file}"
	z=${x##*/}
	sed '1 a\   "namespace" : "avro.java.gaia",' "${file}" > ./src/avro/schema/$z
done

java -cp "lib/avro-tools-1.7.2.jar:." org.apache.avro.tool.Main compile schema src/avro/schema/* src/
