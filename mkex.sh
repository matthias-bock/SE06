#!/bin/bash

read -p "Exercise Nr: " exnr

exDir="./Ex$(printf "%02d" "$exnr")"

echo "--- Creating Exercise folder ---"
echo "-- Folder: $exDir"

mkdir $exDir

taskDirBase="task$exnr"

echo "-- Creating task folders"

mkdir $exDir/$taskDirBase.1
mkdir $exDir/$taskDirBase.2
mkdir $exDir/$taskDirBase.3
mkdir $exDir/$taskDirBase.4
