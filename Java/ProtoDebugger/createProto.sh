
rm -R gen
mkdir gen

for file in `ls protos/*.proto`
do
./protoc --java_out=./gen $file
done
