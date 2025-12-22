#!/bin/bash

thingsToZip=(
    "*.java"
    "*.txt"
    "*.jpeg"
    "*.pml"
)

zipPrefix="s1337023-"

echo "--- Building ---"
echo "Included file types: ${thingsToZip[@]}"
echo "----------------"

for item in ./*; do
    if [[ -e $item && -d $item ]]; then
        echo "-- Building ${item:l}.zip"
        zip -r $zipPrefix${item#"./"}.zip $item -i ${thingsToZip[@]}
    fi
done
