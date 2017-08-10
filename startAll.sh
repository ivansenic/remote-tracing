export AGENT="/home/ise/workspace/inspectIT/ivansenic-inspectit/inspectit.agent.java"
gnome-terminal -x sh -c "cd mq/ && ./gradlew clean bootRun -PAGENT_HOME=$AGENT; bash"
gnome-terminal -x sh -c "cd http/ && ./gradlew clean bootRun -PAGENT_HOME=$AGENT; bash"
gnome-terminal -x sh -c "cd clients/ && ./gradlew clean bootRun -PAGENT_HOME=$AGENT; bash"

