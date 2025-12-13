#!/bin/bash

thingsToZip=(
    "*.java"
    "*.txt"
    "*.jpeg"
)

zipPrefix="s1337023-"

echo "--- Building ---"
echo "Included file types: ${thingsToZip[@]}"
echo "----------------"

for item in ./*; do
    if [[ -e $item && -d $item ]]; then
        echo "-- Building ${item:l}.zip"
        zip -r $zipPrefixi${item#"./"}.zip $item -i ${thingsToZip[@]}
    fi
done
