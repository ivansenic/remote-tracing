## How to start all:

0. Make sure that remoting profiles are added to your mapped/default environment. There is additional profile in the *inspectit-config* directory that you can copy to your CMR and use in addition (activates manual sensor and invocation entry on the MQ listeners).
1. Run ./startMQBroker.sh to fire up Docker container which will serve as MQ Broker.
2. Update ./startAll.sh and change path to the inspectIT agent project on your local machine.
3. Run ./startAll.sh which will start 3 JVMs in separate terminals.

## Usage:

There are three projects that run in separate JVMs and communicate between each other:

1. clients - Web application running on 8002.
2. http - Web application running on 8001.
3. mq - Application listening on MQ messages.

Clients application can be used to call any url with different HTTP clients. Available are:

* http://localhost:8002/apache?url= and http://localhost:8002/apache/async?url= (Apache Http Client & Aysnc Apache Http Client)
* http://localhost:8002/jetty/async?url= (Jetty Http Client)
* http://localhostL8002/url-connection?url= (Url Connection Java)
* http://localhost:8002/springresttemplate?url= and http://localhost:8002/springresttemplate/async?url= (Spring Rest Template with URL Connection)
* http://localhost:8002/springresttemplate/apache?url= (Spring Rest Template with Apache Http client)
* http://localhost:8002/all?url=? (uses all HTTP clients to execute a call)

If no url is specified, default url is http://localhost:8001/, which in facts calls the second application. You can also specify any url you like, but then your trace will end there.

When http://localhost:8001/ is called the MQ message is sent to the broker and third application will receive it via the same broker.

