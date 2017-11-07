#!/bin/bash

echo 'Init System data ...'

HOST='172.16.200.241'
KEY='A7p5Lah9o42ApC3g'

echo 'system properties insert'
curl -X POST http://${HOST}:9090/plugins/restapi/v1/system/properties -H "authorization: ${KEY}" -H 'content-type: application/json' -d "{\"@key\": \"rss.enabled\", \"@value\": false }" -v
curl -X POST http://${HOST}:9090/plugins/restapi/v1/system/properties -H "authorization: ${KEY}" -H 'content-type: application/json' -d "{\"@key\": \"websocket.idle.timeout\", \"@value\": \"1800000\" }" -v
curl -X POST http://${HOST}:9090/plugins/restapi/v1/system/properties -H "authorization: ${KEY}" -H 'content-type: application/json' -d "{\"@key\": \"xmpp.last.msg\", \"@value\": \"The network connection is down!-_-!\" }" -v
echo '========================='

echo 'create custemer group'
curl -X POST http://${HOST}:9090/plugins/restapi/v1/groups -H "authorization: ${KEY}" -H 'content-type: application/json' -d "{\"name\": \"group1\", \"description\": \"客服组\" }" -v
echo '========================='

echo 'start insert guest user ...'
for i in {10000..20000}
do
   curl -X POST http://${HOST}:9090/plugins/restapi/v1/users -H "authorization: ${KEY}" -H 'content-type: application/json' -d "{\"username\": \"${i}\",  \"name\":\"游客${n}\", \"password\": \"1234\" }" -v;
done
echo 'insert guest user end!'
echo '========================='

echo 'start insert custmer user ...'
for n in {80000..80010}
do
   curl -X POST http://${HOST}:9090/plugins/restapi/v1/users -H "authorization: ${KEY}" -H 'content-type: application/json' -d "{\"username\": \"${n}\", \"name\":\"客服${n}\", \"password\": \"1234\" }" -v;
   curl -X POST http://${HOST}:9090/plugins/restapi/v1/users/${n}/groups/group1 -H "authorization: ${KEY}"
done
echo 'insert custmer user end!'
