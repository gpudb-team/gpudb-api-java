#!/bin/bash
SCHEMADIR=../../gaiadb-schemas/obj_defs/

mkdir -p src/avro/schema
find "${SCHEMADIR}" -name "*.json" | while read -r file
do
	echo "${file}"
	#cp "${file}" ./temp/
	filename=$(basename ${file})

	# Add in "namespace" : "avro.java.gpudb",

	# This sed '1 a\ ....' syntax doesn't work in OSX
	#sed '1 a\   "namespace" : "avro.java.gpudb",' "${file}" > ./src/avro/schema/$filename
	sed 's/^{/{\
    "namespace" : "avro.java.gpudb",/g' "${file}" > ./src/avro/schema/$filename

done

java -cp "../gpudb-api/lib/avro-tools-1.7.2.jar:." org.apache.avro.tool.Main compile schema src/avro/schema/* src/
